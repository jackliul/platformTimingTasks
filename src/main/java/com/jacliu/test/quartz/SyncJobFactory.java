package com.jacliu.test.quartz;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.jacliu.test.model.ScheduleJob;
import com.jacliu.test.vo.ScheduleJobVo;

import cn.hutool.json.JSONUtil;

/**
 * author : jacliu createTime : 2018-06-28 description : 同步任务工厂 version : 1.0
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class SyncJobFactory extends QuartzJobBean {

	/* 日志对象 */
	private static final Logger LOG = LoggerFactory.getLogger(SyncJobFactory.class);

	@Autowired
	private AmqpTemplate amqpTemplate;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		System.out.println("amqpTemplate " + amqpTemplate);
		LOG.info("发送消息：{}", "xxxxxxxxxxdfsdfdsfdsfsdfsdfdsf");

		JobDataMap mergedJobDataMap = context.getMergedJobDataMap();
		ScheduleJob scheduleJob = (ScheduleJob) mergedJobDataMap.get(ScheduleJobVo.JOB_PARAM_KEY);

		String scheduleJobStr = JSONUtil.toJsonStr(scheduleJob);
		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setContentType("application/json");
		Message message = new Message(scheduleJobStr.getBytes(), messageProperties);

		System.out.println(" messages ,,, " + scheduleJobStr);
		LOG.info("发送消息 messages ,,, ：{}", scheduleJobStr);
		amqpTemplate.convertAndSend("timingTasks_X", "timingTasks_R", message);
	}

}
