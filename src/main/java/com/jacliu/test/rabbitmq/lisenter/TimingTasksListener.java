package com.jacliu.test.rabbitmq.lisenter;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jacliu.test.model.ScheduleJob;
import com.jacliu.test.service.ScheduleJobService;
import com.jacliu.test.vo.ResultModel;
import com.rabbitmq.client.Channel;

public class TimingTasksListener implements ChannelAwareMessageListener {

	/* 日志对象 */
	private static final Logger LOG = LoggerFactory.getLogger(TimingTasksListener.class);

	@Autowired
	private ScheduleJobService scheduleJobService;

	public void onMessage(Message message, Channel channel) throws Exception {

		ScheduleJob scheduleJob = null;
		try {
			String data = new String(message.getBody());
			if (StringUtils.isBlank(data)) {
				channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
				return;
			}

			LOG.info("consumer received message: {} ", data);
			scheduleJob = JSON.parseObject(data, ScheduleJob.class);

			executeRemoteJob(scheduleJob);

			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		} catch (Exception e) {
			LOG.error("发送时错误", e.getMessage());
			e.printStackTrace();
			throw new Exception();
		}

	}

	private void executeRemoteJob(ScheduleJob scheduleJob) {
		String taskUrl = scheduleJob.getTaskUrl();
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(taskUrl);
		CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String result = EntityUtils.toString(response.getEntity());// 返回json格式：
				@SuppressWarnings("rawtypes")
				ResultModel resultModel = JSONObject.parseObject(result, ResultModel.class);
				changeJobRes(scheduleJob, resultModel.getResult_code(), resultModel.getMessage());
			} else {
				changeJobRes(scheduleJob, null, null);
			}
		} catch (IOException e) {
			LOG.error("请求报错：{}", e.getMessage());
		}
	}

	private void changeJobRes(ScheduleJob scheduleJob, Integer resultCode, String message) {
		scheduleJob.setGmtModify(new Date());
		scheduleJob.setLastExcutionResult(message);
		scheduleJob.setLastExcutionStatus(resultCode);
		scheduleJobService.changeJobRes(scheduleJob);
	}

	/*
	 * StringBuilder sb = new StringBuilder();
	 * sb.append("UPDATE SCHEDULE_JOB sjb ").
	 * append("SET sjb.last_excution_time = NOW(), ")
	 * .append("sjb.last_excution_status = " + resultCode + ", ")
	 * .append("sjb.last_excution_result = '" + message + "' ").append("WHERE ")
	 * .append("sjb.task_url = '" + taskUrl + "'");
	 * 
	 * // 执行成功了则不必要再记错误日志了，【调用方没返回logCode】 if (resultCode != 0) { String logCode =
	 * message.split("_")[1]; LOG.info("{} :: 更新 sql语句 :: {}", logCode,
	 * sb.toString()); }
	 * 
	 * JDBCUtil.executeUpdate(sb.toString());
	 */
}
