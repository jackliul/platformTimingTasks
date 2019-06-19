package com.jacliu.test.rabbitmq.lisenter;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
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
			LOG.info("consumer received message: {} ", data);
			if (StringUtils.isBlank(data)) {
				// channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
				return;
			}

			scheduleJob = JSON.parseObject(data, ScheduleJob.class);

			executeRemoteJob(scheduleJob);
			// 告诉服务器收到这条消息 已经被我消费了 可以在队列删掉 这样以后就不会再发了 否则消息服务器以为这条消息没处理掉 后续还会在发
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		} catch (Exception e) {
			/*
			 * LOG.error("发送时错误", e.getMessage()); if ("连接不上：".equals(e.getMessage())) { //
			 * 如果是服务连不上，则同样需要保持到数据库上 changeJobRes(scheduleJob, -100,
			 * "http请求时报错,请检查omsWeb启动是否正常。"); } if ("请求超时：".equals(e.getMessage())) { //
			 * 如果是请求超时，则同样需要保持到数据库上 changeJobRes(scheduleJob, -100,
			 * "http请求超时,请在网页上手动更新该接口。"); }
			 */

			// 丢弃这条消息
			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
			// throw new Exception(e);
		}

	}

	// 4.3版本不设置超时的话，一旦服务器没有响应，等待时间N久(>24小时)。
	private void executeRemoteJob(ScheduleJob scheduleJob) throws Exception {
		String taskUrl = scheduleJob.getTaskUrl();
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(taskUrl); // 1800000
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(360000)
					.setConnectionRequestTimeout(9000).setSocketTimeout(360000).build();
			// RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(1800)
			// .setConnectionRequestTimeout(9000).setSocketTimeout(1800).build();
			// 设置请求和传输超时时间
			httpGet.setConfig(requestConfig);
			CloseableHttpResponse response = httpclient.execute(httpGet);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String result = EntityUtils.toString(response.getEntity());// 返回json格式：
				@SuppressWarnings("rawtypes")
				ResultModel resultModel = JSONObject.parseObject(result, ResultModel.class);
				changeJobRes(scheduleJob, resultModel.getResult_code(), resultModel.getMessage());
			} else {
				changeJobRes(scheduleJob, -200, "HttpStatus.SC_OK 不就ok的情況");
			}
		} catch (ConnectException e) {
			LOG.error("连接不上：{}", e);
			changeJobRes(scheduleJob, -300, "http请求时报错,请检查omsJobs启动是否正常。");
			throw new Exception("连接不上：", e);
		} catch (SocketTimeoutException e) {
			LOG.error("请求超时：{}", e);
			changeJobRes(scheduleJob, -400, "http请求超时,请在网页上手动更新该接口。");
			throw new Exception("请求超时：", e);
		} catch (Exception e) {
			changeJobRes(scheduleJob, -500, "http请求时报错：");
			LOG.error("请求报错：{}", e);
			throw new Exception("http请求时报错：", e);
		} finally {

		}
	}

	private void changeJobRes(ScheduleJob scheduleJob, Integer resultCode, String message) {
		scheduleJob.setLastExcutionResult(message);
		scheduleJob.setLastExcutionStatus(resultCode);
		scheduleJob.setModifiedUser("jobPlatform");
		scheduleJobService.changeJobRes(scheduleJob);
	}
}
