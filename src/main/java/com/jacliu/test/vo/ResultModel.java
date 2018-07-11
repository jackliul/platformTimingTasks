package com.jacliu.test.vo;

import java.io.Serializable;

/**
 * 返回给客户端的类
 *
 * {"ret": 201,"data": {},"msg": "success"}
 *
 * @author suzhongqiang
 * @date 2017.05.31
 */
public class ResultModel<T> implements Serializable {
	/**
	 */
	private static final long serialVersionUID = 1L;

	int result_code;// 为0时成功，非0时见错误代码附录 0
	String message; // 返回的消息内容
	String solution; // 出错时的解决方案提示信息
	String sign; // 数字签名MD5_16(appid+body+ encrypt_type+ AppSecret)
	int encrypt_type; // 响应的实体加密方式 0
	T body;// 返回实体类

	public int getResult_code() {
		return result_code;
	}

	public void setResult_code(int result_code) {
		this.result_code = result_code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public int getEncrypt_type() {
		return encrypt_type;
	}

	public void setEncrypt_type(int encrypt_type) {
		this.encrypt_type = encrypt_type;
	}

	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}
}
