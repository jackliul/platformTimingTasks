package com.jacliu.test.service;

import java.util.List;

import com.jacliu.test.model.SFaultJobs;
import com.jacliu.test.model.ScheduleJob;
import com.jacliu.test.model.ScheduleJobForTasks;
import com.jacliu.test.vo.ScheduleJobVo;

/**
 * author : jacliu createTime : 2018-06-28 description : 定时任务服务 version : 1.0
 */
public interface ScheduleJobService {

	/**
	 * 初始化定时任务
	 */
	public void initScheduleJob() throws Exception;

	/**
	 * 新增
	 * 
	 * @param scheduleJobVo
	 * @return
	 */
	public Long insert(ScheduleJobVo scheduleJobVo) throws Exception;

	/**
	 * 直接修改 只能修改运行的时间，参数、同异步等无法修改
	 * 
	 * @param scheduleJobVo
	 */
	public void update(ScheduleJobVo scheduleJobVo) throws Exception;

	/**
	 * 删除重新创建方式
	 * 
	 * @param scheduleJobVo
	 */
	public void delUpdate(ScheduleJobVo scheduleJobVo) throws Exception;

	/**
	 * 删除
	 * 
	 * @param scheduleJobId
	 */
	public void delete(Long scheduleJobId) throws Exception;

	/**
	 * 运行一次任务
	 *
	 * @param scheduleJobId
	 *            the schedule job id
	 * @return
	 */
	public void runOnce(Long scheduleJobId) throws Exception;

	/**
	 * 暂停任务
	 *
	 * @param scheduleJobId
	 *            the schedule job id
	 * @return
	 */
	public void pauseJob(Long scheduleJobId) throws Exception;

	/**
	 * 恢复任务
	 *
	 * @param scheduleJobId
	 *            the schedule job id
	 * @return
	 */
	public void resumeJob(Long scheduleJobId) throws Exception;

	/**
	 * 获取任务对象
	 * 
	 * @param scheduleJobId
	 * @return
	 */
	public ScheduleJobVo get(Long scheduleJobId) throws Exception;

	/**
	 * 查询任务列表
	 * 
	 * @param scheduleJobVo
	 * @return
	 */
	public List<ScheduleJobVo> queryList(ScheduleJobVo scheduleJobVo) throws Exception;

	/**
	 * 获取运行中的任务列表
	 * 
	 * @param companyNo
	 *
	 * @return
	 */
	public List<ScheduleJobVo> queryExecutingJobList(String companyNo) throws Exception;

	/**
	 * 删除任务
	 * 
	 * @param scheduleJobId
	 */
	public void deleteScheduleJob(Long scheduleJobId) throws Exception;

	/**
	 * 启用禁用该任务
	 * 
	 * @param scheduleJobId
	 */
	public void changScheduleJobActive(Long scheduleJobId, Integer isActive) throws Exception;

	/**
	 * 接口更新时使用
	 * 
	 * @param scheduleJobVo
	 */
	public void externalUpdate(ScheduleJobVo scheduleJobVo);

	/**
	 * 远程启用或禁用定时任务
	 * 
	 * @param scheduleJobVo
	 */
	public void updateStartOrForbidden(ScheduleJobVo scheduleJobVo);

	/**
	 * 更改返回状态
	 * 
	 * @param scheduleJobVo
	 */
	public void changeJobRes(ScheduleJob scheduleJob);

	/**
	 * 批量添加失败的任务
	 * 
	 * @param scheduleJobVo
	 */
	public void batchInseatFaults(List<ScheduleJobForTasks> faultJobs);

	/**
	 * 查找所有的已存在的定时任务列表
	 * 
	 * @return
	 */
	public List<ScheduleJobForTasks> findAll();

	/**
	 * 查找所有失败的定时任务
	 * 
	 * @return
	 */
	public List<SFaultJobs> findAllFaultsJobs();

	/**
	 * 1：删除添加的错误记录 S_FAULT_JOBS 2：更改 SCHEDULE_JOB中的最后错误时间为null
	 */
	public void delFaultJobsRecord(String taskUrl);

}
