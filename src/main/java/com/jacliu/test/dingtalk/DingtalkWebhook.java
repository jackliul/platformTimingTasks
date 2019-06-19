package com.jacliu.test.dingtalk;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jacliu.test.utils.JsonUtil;
import com.jacliu.test.utils.SystemPropertiesUtil;

/**
 * @description 钉钉机器人相关工具
 * @author jiangbin
 * @date 2018年12月24日 下午4:28:36
 * @version 1.0.0
 */
public class DingtalkWebhook {

	private static final Logger log = LoggerFactory.getLogger(DingtalkWebhook.class);
	private static ObjectMapper mapper = new ObjectMapper();

	public static void main(String[] args) {
		String contentText = String.format("承运商【%s】的任务【%s】超过10分钟执行失败，日志编号%s，现已重复执行3次，如查阅数据还是未同步，请及时同步", "DDS",
				"http://www.i-oms.cn/oms-jobs/fetchClienteleData/1.0?companyNo=DDS", "aaaa");
		DingtalkWebhook.exceptionMsgHint(contentText);
	}

	/**
	 * @description 钉钉机器人消息推送
	 * @throws Exception
	 * @author jiangbin
	 * @date 2018年12月24日 上午9:27:56
	 * @version 1.0.0
	 */
	public static void exceptionMsgHint(String contentText) {
		String dingTalkToken = SystemPropertiesUtil.getContextProperty("dingTalk.token");
		if (dingTalkToken.equals("propertiesIsNull")) {
			dingTalkToken = "08e43e17adc7dbbca62809bd8e5e5eaa7a23866b2b99eae9bf5100dfd42bce1d";
		}
		String dingTalkEnv = SystemPropertiesUtil.getContextProperty("dingTalk.token.env");
		if (dingTalkEnv.equals("propertiesIsNull")) {
			dingTalkEnv = "xxx ";
		}

		String WEBHOOK_TOKEN = "https://oapi.dingtalk.com/robot/send?access_token=" + dingTalkToken;

		HttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(WEBHOOK_TOKEN);
		httppost.addHeader("Content-Type", "application/json; charset=utf-8");
		SendMsgParam sendMsgParam = new SendMsgParam();
		SendMsgContent sendMsgContent = new SendMsgContent();
		sendMsgContent.setContent(" ------ " + contentText);
		// sendMsgContent.setContent(dingTalkEnv + " ------ " + contentText);
		SendMsgAt sendMsgAt = new SendMsgAt();
		sendMsgAt.setAtMobiles("15118833526");
		sendMsgParam.setAt(sendMsgAt);
		sendMsgParam.setText(sendMsgContent);
		String textMsg = JsonUtil.toJson(sendMsgParam);
		log.info("钉钉机器人消息推送信息：{}", textMsg);

		StringEntity se = new StringEntity(textMsg, "utf-8");
		httppost.setEntity(se);
		HttpResponse response;
		try {
			response = httpclient.execute(httppost);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String result = EntityUtils.toString(response.getEntity(), "utf-8");
				log.info("钉钉机器人消息推送响应结果：{}", result);
			}
		} catch (Exception e) {
			log.error("钉钉机器人消息推送失败！ ", e);
		}

	}
}
