package com.jacliu.test.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jacliu.test.service.ScheduleJobService;
import com.jacliu.test.vo.ScheduleJobVo;

/**
 * author : jacliu createTime : 2018-06-28 description : 定时任务控制器 version : 1.0
 */
@Controller
public class ScheduleJobController {

	/** job service */
	@Autowired
	private ScheduleJobService scheduleJobService;

	private static final Logger LOG = LoggerFactory.getLogger(ScheduleJobController.class);

	/**
	 * 任务页面
	 *
	 * @return
	 */
	@RequestMapping(value = "inputScheduleJob", method = RequestMethod.GET)
	public String inputScheduleJob(ScheduleJobVo scheduleJobVo, ModelMap modelMap) {

		if (scheduleJobVo.getScheduleJobId() != null) {
			ScheduleJobVo scheduleJob;
			try {
				scheduleJob = scheduleJobService.get(scheduleJobVo.getScheduleJobId());
				scheduleJob.setKeywords(scheduleJobVo.getKeywords());
				modelMap.put("scheduleJobVo", scheduleJob);
			} catch (Exception e) {
				LOG.error("inputScheduJob:: " + e.getMessage());
			}

		}

		return "inputScheduleJob";
	}

	/**
	 * 删除任务
	 *
	 * @return
	 */
	@RequestMapping(value = "deleteScheduleJob", method = RequestMethod.GET)
	public String deleteScheduleJob(Long scheduleJobId) {
		try {
			scheduleJobService.deleteScheduleJob(scheduleJobId);
		} catch (Exception e) {
			LOG.error("deleteScheduleJob:: " + e.getMessage());
		}
		// scheduleJobService.delete(scheduleJobId);
		return "redirect:scheduleJobs.shtml";
	}

	/**
	 * 修改启用状态
	 *
	 * @return
	 */
	@RequestMapping(value = "changScheduleJobActive", method = RequestMethod.GET)
	public String changScheduleJobActive(Long scheduleJobId, Integer isActived) {
		try {
			scheduleJobService.changScheduleJobActive(scheduleJobId, isActived);
		} catch (Exception e) {
			LOG.error("changScheduleJobActive:: " + e.getMessage());
		}
		return "redirect:scheduleJobs.shtml";
	}

	/**
	 * 运行一次
	 *
	 * @return
	 */
	@RequestMapping(value = "runOnceScheduleJob", method = RequestMethod.GET)
	public String runOnceScheduleJob(Long scheduleJobId) {
		try {
			scheduleJobService.runOnce(scheduleJobId);
		} catch (Exception e) {
			LOG.error("runOnceScheduleJob:: " + e.getMessage());
		}
		return "redirect:scheduleJobs.shtml";
	}

	/**
	 * 暂停
	 *
	 * @return
	 */
	@RequestMapping(value = "pauseScheduleJob", method = RequestMethod.GET)
	public String pauseScheduleJob(Long scheduleJobId) {
		try {
			scheduleJobService.pauseJob(scheduleJobId);
		} catch (Exception e) {
			LOG.error("pauseScheduleJob:: " + e.getMessage());
		}
		return "redirect:scheduleJobs.shtml";
	}

	/**
	 * 恢复
	 *
	 * @return
	 */
	@RequestMapping(value = "resumeScheduleJob", method = RequestMethod.GET)
	public String resumeScheduleJob(Long scheduleJobId) {
		try {
			scheduleJobService.resumeJob(scheduleJobId);
		} catch (Exception e) {
			LOG.error("resumeScheduleJob:: " + e.getMessage());
		}
		return "redirect:scheduleJobs.shtml";
	}

	/**
	 * 保存任务
	 *
	 * @param scheduleJobVo
	 * @return
	 */
	@RequestMapping(value = "saveScheduleJob", method = RequestMethod.POST)
	public String saveScheduleJob(ScheduleJobVo scheduleJobVo) {

		if (scheduleJobVo.getScheduleJobId() == null) {
			try {
				// 生成jobName,jobGruop
				scheduleJobVo
						.setJobName(scheduleJobVo.getCompanyCode() + "_jobName_" + scheduleJobVo.getFunctionalName());
				scheduleJobVo
						.setJobGroup(scheduleJobVo.getCompanyCode() + "_jobGroup_" + scheduleJobVo.getFunctionalName());
				scheduleJobVo.setJobTrigger(
						scheduleJobVo.getCompanyCode() + "_jobTrigger_" + scheduleJobVo.getFunctionalName());
				scheduleJobVo.setCreateUser("platform");
				scheduleJobVo.setGmtCreate(new Date());
				// 测试用随便设个状态 TODO
				scheduleJobVo.setRunStatus("1");

				scheduleJobService.insert(scheduleJobVo);
			} catch (Exception e) {
				LOG.error("saveScheduleJob insert :: " + e.getMessage());
			}
		} else if (StringUtils.equalsIgnoreCase(scheduleJobVo.getKeywords(), "delUpdate")) {
			// 直接拿keywords存一下，就不另外重新弄了
			scheduleJobVo.setGmtModify(new Date());
			try {
				scheduleJobService.delUpdate(scheduleJobVo);
			} catch (Exception e) {
				LOG.error("saveScheduleJob delUpdate :: " + e.getMessage());
			}
		} else {
			scheduleJobVo.setModifiedUser("platform");
			try {
				scheduleJobService.update(scheduleJobVo);
			} catch (Exception e) {
				LOG.error("saveScheduleJob update :: " + e.getMessage());
			}
		}
		return "redirect:scheduleJobs.shtml";
	}

	/**
	 * 任务列表页
	 *
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "scheduleJobs", method = RequestMethod.GET)
	public String scheduleJobs(ScheduleJobVo scheduleJobVo, ModelMap modelMap) {
		List<ScheduleJobVo> scheduleJobVoList;
		try {
			scheduleJobVoList = scheduleJobService.queryList(scheduleJobVo);
			modelMap.put("scheduleJobVoList", scheduleJobVoList);
		} catch (Exception e) {
			LOG.error("scheduleJobs queryList :: " + e.getMessage());
		}

		List<ScheduleJobVo> executingJobList;
		try {
			executingJobList = scheduleJobService.queryExecutingJobList();
			modelMap.put("executingJobList", executingJobList);
		} catch (Exception e) {
			LOG.error("scheduleJobs executingJobList :: " + e.getMessage());
		}
		return "scheduleJobs";
	}

}
