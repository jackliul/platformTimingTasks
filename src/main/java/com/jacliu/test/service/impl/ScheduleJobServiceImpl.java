package com.jacliu.test.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dexcoder.commons.bean.BeanConverter;
import com.dexcoder.dal.JdbcDao;
import com.dexcoder.dal.build.Criteria;
import com.jacliu.test.model.SFaultJobs;
import com.jacliu.test.model.ScheduleJob;
import com.jacliu.test.model.ScheduleJobForTasks;
import com.jacliu.test.redis.service.RedisService;
import com.jacliu.test.service.ScheduleJobService;
import com.jacliu.test.utils.DateUtils;
import com.jacliu.test.utils.ScheduleUtils;
import com.jacliu.test.vo.ScheduleJobVo;

/**
 * author : jacliu createTime : 2018-06-28 description : 定时任务服务实现 version : 1.0
 */
@Service
public class ScheduleJobServiceImpl implements ScheduleJobService {

	private static final Logger LOG = LoggerFactory.getLogger(ScheduleUtils.class);

	/** 调度工厂Bean */
	@Autowired
	private Scheduler scheduler;

	@Autowired()
	@Qualifier("developOrTestRedisService")
	private RedisService developOrTestRedisService;

	@Autowired()
	@Qualifier("intelinkTestRedisService")
	private RedisService intelinkTestRedisService;

	@Autowired()
	@Qualifier("intelinkRedisService")
	private RedisService intelinkRedisService;

	/** 通用dao */
	@Autowired
	private JdbcDao jdbcDao;

	public void initScheduleJob() {
		List<ScheduleJob> scheduleJobList = jdbcDao.queryList(Criteria.select(ScheduleJob.class));
		if (CollectionUtils.isEmpty(scheduleJobList)) {
			return;
		}
		for (ScheduleJob scheduleJob : scheduleJobList) {

			CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobName(),
					scheduleJob.getJobGroup());

			// 不存在，创建一个
			if (cronTrigger == null) {
				ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
			} else {
				// 已存在，那么更新相应的定时设置
				ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
			}
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public Long insert(ScheduleJobVo scheduleJobVo) {
		scheduleJobVo.setTaskUrl(StringUtils.trim(scheduleJobVo.getTaskUrl()));
		scheduleJobVo.setCompanyCode(StringUtils.trim(scheduleJobVo.getCompanyCode()));
		scheduleJobVo.setCronExpression(StringUtils.trim(scheduleJobVo.getCronExpression()));
		ScheduleJob scheduleJob = scheduleJobVo.getTargetObject(ScheduleJob.class);
		ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
		return jdbcDao.insert(scheduleJob);
	}

	@Transactional(rollbackFor = Exception.class)
	public void update(ScheduleJobVo scheduleJobVo) {
		ScheduleJob scheduleJob = jdbcDao.get(ScheduleJob.class, scheduleJobVo.getScheduleJobId());
		scheduleJob.setIsSync(scheduleJobVo.getIsSync());
		scheduleJob.setCronExpression(scheduleJobVo.getCronExpression());
		scheduleJob.setDescription(scheduleJobVo.getDescription());
		scheduleJobVo.setGmtModify(new Date());

		ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
		jdbcDao.update(scheduleJob);
	}

	@Transactional(rollbackFor = Exception.class)
	public void delUpdate(ScheduleJobVo scheduleJobVo) {
		ScheduleJob scheduleJob = scheduleJobVo.getTargetObject(ScheduleJob.class);
		// 先删除
		ScheduleUtils.deleteScheduleJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
		// 再创建
		ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
		// 数据库直接更新即可
		jdbcDao.update(scheduleJob);
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(Long scheduleJobId) {
		ScheduleJob scheduleJob = jdbcDao.get(ScheduleJob.class, scheduleJobId);
		// 删除运行的任务
		ScheduleUtils.deleteScheduleJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
		// 删除数据
		jdbcDao.delete(ScheduleJob.class, scheduleJobId);
	}

	public void runOnce(Long scheduleJobId) {
		ScheduleJob scheduleJob = jdbcDao.get(ScheduleJob.class, scheduleJobId);
		ScheduleUtils.runOnce(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
	}

	public void pauseJob(Long scheduleJobId) {
		ScheduleJob scheduleJob = jdbcDao.get(ScheduleJob.class, scheduleJobId);
		ScheduleUtils.pauseJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
		// 演示数据库就不更新了
	}

	public void resumeJob(Long scheduleJobId) {
		ScheduleJob scheduleJob = jdbcDao.get(ScheduleJob.class, scheduleJobId);
		ScheduleUtils.resumeJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
		// 演示数据库就不更新了
	}

	public ScheduleJobVo get(Long scheduleJobId) {
		ScheduleJob scheduleJob = jdbcDao.get(ScheduleJob.class, scheduleJobId);
		ScheduleJobVo scheduleJobVo = scheduleJob.getTargetObject(ScheduleJobVo.class);
		return scheduleJobVo;
	}

	public List<ScheduleJobVo> queryList(ScheduleJobVo scheduleJobVo) {
		scheduleJobVo.setIsDelete(0);
		List<ScheduleJob> scheduleJobs = jdbcDao.queryList(scheduleJobVo.getTargetObject(ScheduleJob.class));
		List<ScheduleJobVo> scheduleJobVoList = BeanConverter.convert(ScheduleJobVo.class, scheduleJobs);
		try {
			for (ScheduleJobVo vo : scheduleJobVoList) {

				JobKey jobKey = ScheduleUtils.getJobKey(vo.getJobName(), vo.getJobGroup());
				List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
				if (CollectionUtils.isEmpty(triggers)) {
					continue;
				}

				// 这里一个任务可以有多个触发器， 但是我们一个任务对应一个触发器，所以只取第一个即可，清晰明了
				Trigger trigger = triggers.iterator().next();
				vo.setJobTrigger(trigger.getKey().getName());

				Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
				vo.setRunStatus(triggerState.name());

				if (trigger instanceof CronTrigger) {
					CronTrigger cronTrigger = (CronTrigger) trigger;
					String cronExpression = cronTrigger.getCronExpression();
					vo.setCronExpression(cronExpression);
				}
			}
		} catch (SchedulerException e) {
			// 演示用，就不处理了
		}
		return scheduleJobVoList;
	}

	/**
	 * 获取运行中的job列表
	 * 
	 * @return
	 */
	public List<ScheduleJobVo> queryExecutingJobList(String companyNo) {
		try {
			// 存放结果集
			List<ScheduleJobVo> jobList = new ArrayList<ScheduleJobVo>();

			// 获取scheduler中的JobGroupName
			for (String group : scheduler.getJobGroupNames()) {
				if (!group.contains(companyNo))
					continue;
				// 获取JobKey 循环遍历JobKey
				for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.<JobKey>groupEquals(group))) {
					JobDetail jobDetail = scheduler.getJobDetail(jobKey);
					JobDataMap jobDataMap = jobDetail.getJobDataMap();
					ScheduleJob scheduleJob = (ScheduleJob) jobDataMap.get(ScheduleJobVo.JOB_PARAM_KEY);
					ScheduleJobVo scheduleJobVo = new ScheduleJobVo();
					BeanConverter.convert(scheduleJobVo, scheduleJob);
					List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
					Trigger trigger = triggers.iterator().next();
					Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
					scheduleJobVo.setJobTrigger(trigger.getKey().getName());
					scheduleJobVo.setRunStatus(triggerState.name());
					if (trigger instanceof CronTrigger) {
						CronTrigger cronTrigger = (CronTrigger) trigger;
						String cronExpression = cronTrigger.getCronExpression();
						scheduleJobVo.setCronExpression(cronExpression);
					}
					// 获取正常运行的任务列表
					if (triggerState.name().equals("NORMAL")) {
						jobList.add(scheduleJobVo);
					}
				}
			}

			/** 非集群环境获取正在执行的任务列表 */
			/**
			 * List<JobExecutionContext> executingJobs =
			 * scheduler.getCurrentlyExecutingJobs(); List<ScheduleJobVo> jobList = new
			 * ArrayList<ScheduleJobVo>(executingJobs.size()); for (JobExecutionContext
			 * executingJob : executingJobs) { ScheduleJobVo job = new ScheduleJobVo();
			 * JobDetail jobDetail = executingJob.getJobDetail(); JobKey jobKey =
			 * jobDetail.getKey(); Trigger trigger = executingJob.getTrigger();
			 * job.setJobName(jobKey.getName()); job.setJobGroup(jobKey.getGroup());
			 * job.setJobTrigger(trigger.getKey().getName()); Trigger.TriggerState
			 * triggerState = scheduler.getTriggerState(trigger.getKey());
			 * job.setStatus(triggerState.name()); if (trigger instanceof CronTrigger) {
			 * CronTrigger cronTrigger = (CronTrigger) trigger; String cronExpression =
			 * cronTrigger.getCronExpression(); job.setCronExpression(cronExpression); }
			 * jobList.add(job); }
			 */

			return jobList;
		} catch (SchedulerException e) {
			return null;
		}

	}

	@Transactional(rollbackFor = Exception.class)
	public void deleteScheduleJob(Long scheduleJobId) {
		ScheduleJobVo scheduleJobVo = get(scheduleJobId);
		// 删除运行的任务
		ScheduleUtils.deleteScheduleJob(scheduler, scheduleJobVo.getJobName(), scheduleJobVo.getJobGroup());

		scheduleJobVo.setIsDelete(1);
		ScheduleJob scheduleJob = scheduleJobVo.getTargetObject(ScheduleJob.class);
		jdbcDao.update(scheduleJob);
	}

	@Transactional(rollbackFor = Exception.class)
	public void changScheduleJobActive(Long scheduleJobId, Integer isActived) {
		ScheduleJob scheduleJob = jdbcDao.get(ScheduleJob.class, scheduleJobId);
		if (1 == isActived) {
			resumeJob(scheduleJobId);
		}
		// 禁用任务，暂停任务
		if (0 == isActived) {
			pauseJob(scheduleJobId);
		}

		ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
		scheduleJob.setIsActived(isActived);
		scheduleJob.setGmtModify(new Date());
		jdbcDao.update(scheduleJob);
	}

	@Transactional(rollbackFor = Exception.class)
	public void externalUpdate(ScheduleJobVo scheduleJobVo) {
		ScheduleJob scheduleJob = jdbcDao.get(ScheduleJob.class, scheduleJobVo.getScheduleJobId());
		scheduleJob.setIsActived(scheduleJobVo.getIsActived());
		scheduleJob.setCronExpression(scheduleJobVo.getCronExpression());
		scheduleJob.setModifiedUser(scheduleJobVo.getModifiedUser());
		scheduleJob.setGmtModify(new Date());

		ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
		jdbcDao.update(scheduleJob);
	}

	@Transactional(rollbackFor = Exception.class)
	public void updateStartOrForbidden(ScheduleJobVo scheduleJobVo) {
		// 启用任务，恢复任务
		Integer isActived = scheduleJobVo.getIsActived();
		Long scheduleJobId = scheduleJobVo.getScheduleJobId();
		if (1 == isActived) {
			resumeJob(scheduleJobId);
		}
		// 禁用任务，暂停任务
		if (0 == isActived) {
			pauseJob(scheduleJobId);
		}

		ScheduleJob scheduleJob = jdbcDao.get(ScheduleJob.class, scheduleJobId);
		scheduleJob.setIsActived(isActived);
		scheduleJob.setModifiedUser(scheduleJobVo.getModifiedUser());
		scheduleJob.setGmtModify(new Date());
		jdbcDao.update(scheduleJob);
	}

	@Transactional(rollbackFor = Exception.class)
	public void changeJobRes(ScheduleJob scheduleJob) {

		String lastRealExcutionTime = getLastRealExcutionTime(scheduleJob);
		if (lastRealExcutionTime.contains("T")) {
			lastRealExcutionTime = lastRealExcutionTime.replace("T", " ");
		}

		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE SCHEDULE_JOB sjb ").append("SET sjb.last_excution_time = NOW(), ")
				.append("sjb.last_excution_status = " + scheduleJob.getLastExcutionStatus() + ", ")
				.append("sjb.last_real_excution_time = '" + lastRealExcutionTime + "', ")
				.append("sjb.modified_user = '" + scheduleJob.getModifiedUser() + "', ")
				.append("sjb.last_excution_result = '" + scheduleJob.getLastExcutionResult() + "' ").append("WHERE ")
				.append("sjb.task_url = '" + scheduleJob.getTaskUrl() + "'");

		LOG.info("updateSql :: {} ", sb.toString());
		jdbcDao.updateForSql(sb.toString());

		// 执行成功了则不必要再记错误日志了，【调用方没返回logCode】
		// -100 手动加的是请求不了服务器时的状态码
		if (scheduleJob.getLastExcutionStatus() != 0 && scheduleJob.getLastExcutionStatus() != -100) {
			String lastExcutionResult = scheduleJob.getLastExcutionResult();
			if (lastExcutionResult.contains("_")) {
				String logCode = lastExcutionResult.split("_")[1];
				LOG.info("{} :: 更新 sql语句 :: {}", logCode, sb.toString());
			}

			StringBuilder findScheduleJobSql = new StringBuilder();
			findScheduleJobSql.append("SELECT task_url,company_code,last_excution_err_time FROM SCHEDULE_JOB sjb ")
					.append("WHERE ").append("sjb.task_url = '" + scheduleJob.getTaskUrl() + "'");
			List<ScheduleJobForTasks> dbScheduleJobForTasks = jdbcDao.queryListForSql(findScheduleJobSql.toString(),
					ScheduleJobForTasks.class);
			// 执行错误时间【默认为空，只要有错误了就更新时间，以后再次发生错误不再更新，保留最远的错误时间】
			if (dbScheduleJobForTasks.size() != 0 && dbScheduleJobForTasks.get(0).getLastExcutionErrTime() == null) {
				StringBuilder updateErrtimeSql = new StringBuilder();
				updateErrtimeSql.append("UPDATE SCHEDULE_JOB sjb ").append("SET sjb.last_excution_err_time = NOW()")
						.append("WHERE ").append("sjb.task_url = '" + scheduleJob.getTaskUrl() + "'");

				jdbcDao.updateForSql(updateErrtimeSql.toString());
			}
		}

	}

	public String getLastRealExcutionTime(ScheduleJob scheduleJob) {
		String hostName = getHostName(scheduleJob.getTaskUrl());
		String interfaceName = getInterfaceName(scheduleJob.getTaskUrl());
		String companyCode = scheduleJob.getCompanyCode();
		// String key = new
		// StringBuilder().append("SESSION:").append(interfaceName).append("_UPDATEMARK").toString();
		// String hashKey = new
		// StringBuilder().append("SESSION:").append(companyCode).append(":").append(interfaceName)
		// .append("_UPDATEMARK").toString();

		String key = "UPDATE_AND_PUSH:" + scheduleJob.getCompanyCode().toUpperCase();
		String hashKey = interfaceName;
		LOG.info("companyCode:: {} hostName:: {} key:: {} hashKey:: {} ", companyCode, hostName, key, hashKey);

		String lastRealExcutionTime = "";
		if ("192.168.8.33:8081".equals(hostName)) {
			lastRealExcutionTime = developOrTestRedisService.hget(key, hashKey);
		}
		if ("demo.i-oms.cn".equals(hostName)) {
			lastRealExcutionTime = intelinkTestRedisService.hget(key, hashKey);
		}
		if ("www.i-oms.cn".equals(hostName)) {
			lastRealExcutionTime = intelinkRedisService.hget(key, hashKey);
		}

		// 测试用
		if ("localhost:9089".equals(hostName)) {
			lastRealExcutionTime = developOrTestRedisService.hget(key, hashKey);
		}

		return lastRealExcutionTime;
	}

	private String getInterfaceName(String taskUrl) {
		String[] split = taskUrl.split("//");
		String[] split2 = split[1].split("/");
		String interfaceName = split2[2];
		// return interfaceName.toUpperCase();
		return interfaceName;
	}

	private String getHostName(String taskUrl) {
		// String taskUrl =
		// "http://192.168.8.33:8081/oms-web/fetchClienteleData/1.0?companyNo=FWGJ";
		String[] split = taskUrl.split("//");
		// System.out.println(split[1]);
		String[] split2 = split[1].split("/");
		// System.out.println(split2[0]);
		String hostName = split2[0];
		return hostName;
	}

	@Transactional(rollbackFor = Exception.class)
	public void batchInseatFaults(List<ScheduleJobForTasks> scheduleJobs) {
		for (ScheduleJobForTasks scheduleJob : scheduleJobs) {
			SFaultJobs faultJobs = new SFaultJobs();
			faultJobs.setCompanyNo(scheduleJob.getCompanyCode());
			faultJobs.setTaskUrl(scheduleJob.getTaskUrl());
			faultJobs.setEnv(scheduleJob.getEnv());
			// 先删掉已存在的错误记录 根据 env + companyNo + taskUrl
			String refSql = "SELECT\r\n" + "	COMPANY_NO,\r\n" + "	ENV,\r\n" + "	FAULT_EXCUTION_TIME,\r\n"
					+ "	FAULT_RESULT,\r\n" + "	GMT_CREATE,\r\n" + "	ID,\r\n" + "	SPACE_TIME,\r\n"
					+ "	TASK_URL\r\n" + "FROM\r\n" + "	S_FAULT_JOBS\r\n" + "WHERE\r\n" + "	COMPANY_NO = '"
					+ scheduleJob.getCompanyCode() + "'\r\n" + "AND ENV = '" + scheduleJob.getEnv() + "'\r\n"
					+ "AND TASK_URL = '" + scheduleJob.getTaskUrl() + "'\r\n" + "ORDER BY\r\n" + "	ID DESC";

			List<SFaultJobs> dbSfaultJobList = jdbcDao.queryListForSql(refSql, SFaultJobs.class);
			if (null != dbSfaultJobList && dbSfaultJobList.size() > 0) {
				jdbcDao.delete(dbSfaultJobList.get(0));
			}
			// 再新增本次的
			faultJobs.setGmtCreate(new Date());
			Date lastExcutionErrTime = scheduleJob.getLastExcutionErrTime();
			String timeStr = DateUtils.getTimeStr(lastExcutionErrTime);
			faultJobs.setFaultExcutionTime(timeStr);
			long spaceMinutis = DateUtils.getTime(lastExcutionErrTime, null);
			faultJobs.setSpaceTime(spaceMinutis + "");
			faultJobs.setFaultResult(scheduleJob.getLastExcutionResult());
			jdbcDao.insert(faultJobs);
		}
	}

	public List<ScheduleJobForTasks> findAll() {
		String refSql = "SELECT\r\n" + "	SCHEDULE_JOB.schedule_job_id,\r\n" + "	SCHEDULE_JOB.env,\r\n"
				+ "	SCHEDULE_JOB.task_url,\r\n" + "	SCHEDULE_JOB.company_code,\r\n"
				+ "	SCHEDULE_JOB.cron_expression,\r\n" + "	SCHEDULE_JOB.last_excution_status,\r\n"
				+ "	SCHEDULE_JOB.last_excution_result,\r\n" + "	SCHEDULE_JOB.last_real_excution_time,\r\n"
				+ "	SCHEDULE_JOB.last_excution_err_time,\r\n" + "	SCHEDULE_JOB.last_excution_time,\r\n"
				+ "	SCHEDULE_JOB.is_actived,\r\n" + "	SCHEDULE_JOB.is_delete,\r\n"
				+ "	SCHEDULE_JOB.functional_name,\r\n" + "	SCHEDULE_JOB.functional_type,\r\n"
				+ "	SCHEDULE_JOB.create_user,\r\n" + "	SCHEDULE_JOB.modified_user,\r\n"
				+ "	SCHEDULE_JOB.description,\r\n" + "	SCHEDULE_JOB.gmt_create,\r\n"
				+ "	SCHEDULE_JOB.gmt_modify,\r\n" + "	SCHEDULE_JOB.run_status,\r\n" + "	SCHEDULE_JOB.job_name,\r\n"
				+ "	SCHEDULE_JOB.job_group,\r\n" + "	SCHEDULE_JOB.job_trigger\r\n" + "FROM\r\n"
				+ "	SCHEDULE_JOB\r\n" + "WHERE\r\n" + "	is_delete = 0";
		List<ScheduleJobForTasks> scheduleJobs = jdbcDao.queryListForSql(refSql, new Object[] {},
				ScheduleJobForTasks.class);
		return scheduleJobs;

		/*
		 * List<ScheduleJobVo> scheduleJobVos = new ArrayList<ScheduleJobVo>(); for
		 * (ScheduleJob scheduleJob : scheduleJobs) { ScheduleJobVo scheduleJobVo = new
		 * ScheduleJobVo(); BeanUtils.copyProperties(scheduleJob, scheduleJobVo, new
		 * String[] {}); scheduleJobVos.add(scheduleJobVo); } return scheduleJobVos;
		 */
	}

	public List<SFaultJobs> findAllFaultsJobs() {
		String refSql = "SELECT * FROM S_FAULT_JOBS";
		List<SFaultJobs> dFfaultJobs = jdbcDao.queryListForSql(refSql, new Object[] {}, SFaultJobs.class);
		return dFfaultJobs;
	}

	public void delFaultJobsRecord(String taskUrl) {
		String refSql = "DELETE FROM S_FAULT_JOBS WHERE task_url = '" + taskUrl + "'";
		jdbcDao.updateForSql(refSql);

		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE `SCHEDULE_JOB` SET last_excution_err_time = NULL,last_excution_status = 0 WHERE task_url = '")
				.append(taskUrl).append("'");
		jdbcDao.updateForSql(sb.toString());

	}
}
