package com.jacliu.test.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * author : jacliu createTime : 2018-06-28 description : 异步任务工厂 version : 1.0
 */
public class AsyncJobFactory extends QuartzJobBean {

	/* 日志对象 */
//	private static final Logger LOG = LoggerFactory.getLogger(AsyncJobFactory.class);

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
//		LOG.info("AsyncJobFactory execute");
//		ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get(ScheduleJobVo.JOB_PARAM_KEY);
//		System.out.println("jobName:" + scheduleJob.getJobName() + "  " + scheduleJob);
	}
}
