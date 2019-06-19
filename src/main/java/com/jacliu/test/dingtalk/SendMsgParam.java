package com.jacliu.test.dingtalk;

/**
 * @description 使用钉钉自定义机器人发送消息参数实体
 * @author jiangbin
 * @date 2018年12月24日 上午9:04:05
 * @version 1.0.0
 */
public class SendMsgParam {

	/**
	 * 此消息类型为固定text
	 */
	private String msgtype = "text";
	/**
	 * 消息内容
	 */
	private SendMsgContent text;
	/**
	 * 被@人的手机号
	 */
	private SendMsgAt at;

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public SendMsgContent getText() {
		return text;
	}

	public void setText(SendMsgContent text) {
		this.text = text;
	}

	public SendMsgAt getAt() {
		return at;
	}

	public void setAt(SendMsgAt at) {
		this.at = at;
	}

	@Override
	public String toString() {
		return "SendMsgParam [msgtype=" + msgtype + ", text=" + text + ", at=" + at + "]";
	}

}
