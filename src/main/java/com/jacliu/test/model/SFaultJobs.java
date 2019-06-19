package com.jacliu.test.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SFaultJobs {

	private long id;

	// 环境
	private String env;

	// 执行该任务的后台地址：如http://192.168.8.33:8081/oms-web/fetchClienteleData/1.0?companyNo=DDS
	private String taskUrl;

	// 上次执行失败原因
	private String faultResult;

	// 上次执行失败时间
	private String faultExcutionTime;

	// 间隔时间【分钟数】
	private String spaceTime;

	// 新增时间
	private Date gmtCreate;

	// 公司代号
	private String companyNo;

	public long getId() {
		return this.id;
	};

	public void setId(long id) {
		this.id = id;
	}

	public String getEnv() {
		return this.env;
	};

	public void setEnv(String env) {
		this.env = env;
	}

	public String getTaskUrl() {
		return this.taskUrl;
	};

	public void setTaskUrl(String taskUrl) {
		this.taskUrl = taskUrl;
	}

	public String getFaultResult() {
		return this.faultResult;
	};

	public void setFaultResult(String faultResult) {
		this.faultResult = faultResult;
	}

	public String getFaultExcutionTime() {
		return this.faultExcutionTime;
	};

	public void setFaultExcutionTime(String faultExcutionTime) {
		this.faultExcutionTime = faultExcutionTime;
	}

	public String getSpaceTime() {
		return this.spaceTime;
	};

	public void setSpaceTime(String spaceTime) {
		this.spaceTime = spaceTime;
	}

	public Date getGmtCreate() {
		return this.gmtCreate;
	};

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public String getCompanyNo() {
		return this.companyNo;
	};

	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
}