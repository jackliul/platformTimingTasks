package com.jacliu.test.dingtalk;

/**
 * @description 消息发送人
 * @author jiangbin
 * @date 2018年12月24日 上午9:09:13
 * @version 1.0.0
 */
public class SendMsgAt {
	/**
	 * 被@人的手机号
	 */
	private String atMobiles;
	/**
	 * @所有人时:true,否则为:false
	 */
	private boolean isAtAll = false;

	public String getAtMobiles() {
		return atMobiles;
	}

	public void setAtMobiles(String atMobiles) {
		this.atMobiles = atMobiles;
	}

	public boolean isAtAll() {
		return isAtAll;
	}

	public void setAtAll(boolean isAtAll) {
		this.isAtAll = isAtAll;
	}

	@Override
	public String toString() {
		return "SendMsgAt [atMobiles=" + atMobiles + ", isAtAll=" + isAtAll + "]";
	}

}
