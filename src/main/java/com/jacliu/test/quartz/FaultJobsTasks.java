package com.jacliu.test.quartz;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.jacliu.test.dingtalk.DingtalkWebhook;
import com.jacliu.test.model.SFaultJobs;
import com.jacliu.test.model.ScheduleJob;
import com.jacliu.test.model.ScheduleJobForTasks;
import com.jacliu.test.service.ScheduleJobService;
import com.jacliu.test.utils.DateUtils;
import com.jacliu.test.vo.ResultModel;

@Component("faultJobsTasks")
public class FaultJobsTasks {

	private final Logger logger = LoggerFactory.getLogger(FaultJobsTasks.class);

	@Autowired
	private ScheduleJobService scheduleJobService;

	/**
	 * 每隔7分钟定时上次执行成功时间距离现在超过15分钟的定时任务并记录
	 * 
	 * @throws Exception
	 */
	public void findAndUpdateFaultJobsRecord() throws Exception {
		try {
			// 查找系统中所有的定时任务，并遍历其中上次执行成功时间距离现在超过15分钟以上的记录
			List<ScheduleJobForTasks> dbScheduleJobs = scheduleJobService.findAll();
			List<ScheduleJobForTasks> needAgainRequestJobs = new ArrayList<ScheduleJobForTasks>();
			for (ScheduleJobForTasks scheduleJob : dbScheduleJobs) {
				boolean needAgainRequest = needAgainRequest(scheduleJob);
				if (needAgainRequest) {
					needAgainRequestJobs.add(scheduleJob);
				}
			}
			// 对这些失败的定时任务再执行两遍，
			List<ScheduleJobForTasks> faultJobs = manualRequest(needAgainRequestJobs);
			// 继续失败则加入到失败记录表中 并发送失败消息
			scheduleJobService.batchInseatFaults(faultJobs);
			// 查找失败表里所有的失败记录发送钉钉消息，发现错误消息必须人工查看原因，解决并删除记录，否则一直发送错误消息
			pushToDingTalk();
		} catch (Exception e) {
			logger.error("findAndUpdateFaultJobsRecord " + e.getMessage());
		}
	}

	private void pushToDingTalk() {
		List<SFaultJobs> exitsFaultJobs = scheduleJobService.findAllFaultsJobs();
		for (SFaultJobs faultJob : exitsFaultJobs) {
			String contentText = String.format("承运商【%s】的任务【%s】超过15分钟执行失败，最近失败原因【%s】，距离最近失败间隔时间是【%s分钟】，如查阅数据还是未同步，请及时同步",
					faultJob.getCompanyNo(), faultJob.getTaskUrl(), faultJob.getFaultResult(), faultJob.getSpaceTime());
			DingtalkWebhook.exceptionMsgHint(contentText);
		}
	}

	private List<ScheduleJobForTasks> manualRequest(List<ScheduleJobForTasks> needAgainRequestJobs) {
		List<ScheduleJobForTasks> aginFaultJobs = new ArrayList<ScheduleJobForTasks>();
		for (ScheduleJobForTasks scheduleJob : needAgainRequestJobs) {
			// 每个失败的定时任务都继续执行两次，还失败则记录到失败列表中
			for (int i = 0; i < 2; i++) {
				ScheduleJobForTasks remoteJob = executeRemoteJob(scheduleJob);
				if (null == remoteJob) {
					continue;
				}
				if (i == 1 && remoteJob != null) {
					aginFaultJobs.add(remoteJob);
				}
			}
		}
		return aginFaultJobs;
	}

	private boolean needAgainRequest(ScheduleJobForTasks scheduleJob) {

		Date lastExcutionErrTime = scheduleJob.getLastExcutionErrTime();
		// 如果没有取到上次更新成功时间，则直接记错误日志
		// if (null == lastRealExcutionTime) {
		// return true;
		// }
		// if ("null".equals(lastExcutionErrTime)) {
		// return true;
		// }
		// if (lastRealExcutionTime.contains("T")) {
		// lastRealExcutionTime = lastRealExcutionTime.replaceAll("T", " ");
		// }
		long spaceMinutis = DateUtils.getTime(lastExcutionErrTime, null);
		if (spaceMinutis >= 15) {
			return true;
		}
		return false;
	}

	private ScheduleJobForTasks executeRemoteJob(ScheduleJobForTasks scheduleJob) {
		ScheduleJobForTasks faultJob = null;

		String taskUrl = scheduleJob.getTaskUrl();
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(taskUrl); // 1800000
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(360000)
					.setConnectionRequestTimeout(9000).setSocketTimeout(360000).build();
			// 设置请求和传输超时时间
			httpGet.setConfig(requestConfig);
			CloseableHttpResponse response = httpclient.execute(httpGet);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String result = EntityUtils.toString(response.getEntity());// 返回json格式：
				@SuppressWarnings("rawtypes")
				ResultModel resultModel = JSONObject.parseObject(result, ResultModel.class);
				int result_code = resultModel.getResult_code();
				if (0 != result_code) {
					faultJob = scheduleJob;
					faultJob.setLastExcutionResult(resultModel.getMessage());
				} else {
					changeJobRes(scheduleJob, resultModel.getMessage(), resultModel.getResult_code());
				}
			} else {
				return scheduleJob;
			}
		} catch (ConnectException e) {
			logger.error("连接不上：{}：{}", taskUrl, e);
			faultJob = scheduleJob;
			faultJob.setLastExcutionResult("连接不上__" + taskUrl + "@@" + e.getMessage());
		} catch (SocketTimeoutException e) {
			logger.error("请求超时：{}：{}", taskUrl, e);
			faultJob = scheduleJob;
			faultJob.setLastExcutionResult("请求超时__" + taskUrl + "@@" + e.getMessage());
		} catch (Exception e) {
			logger.error("请求报错：{}：{}", taskUrl, e);
			faultJob = scheduleJob;
			faultJob.setLastExcutionResult("请求报错__" + taskUrl + "@@" + e.getMessage());
		}
		return faultJob;
	}

	private void changeJobRes(ScheduleJobForTasks scheduleJob, String message, int resultCode) {

		scheduleJob.setLastExcutionResult(message);
		scheduleJob.setLastExcutionStatus(resultCode);
		scheduleJob.setModifiedUser("againRequest");

		ScheduleJob toUpdateJobTasks = new ScheduleJob();
		BeanUtils.copyProperties(scheduleJob, toUpdateJobTasks, new String[] { "isActived", "isDelete", "isSync" });
		scheduleJobService.changeJobRes(toUpdateJobTasks);

	}

}