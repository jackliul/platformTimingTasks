package com.jacliu.test.dingtalk;

/**
 * @description 消息内容
 * @author jiangbin
 * @date 2018年12月24日 上午9:07:04
 * @version 1.0.0
 */
public class SendMsgContent {
	/**
	 * 消息内容
	 */
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "SendMsgContent [content=" + content + "]";
	}
}
