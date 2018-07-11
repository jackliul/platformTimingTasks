package com.jacliu.test.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.dexcoder.commons.pager.Pageable;

/**
 * author : jacliu createTime : 2018-06-28 description : 计划任务模型 version : 1.0
 */
public class ScheduleJob extends Pageable {

	private static final long serialVersionUID = 4888005949821878223L;

	private Long scheduleJobId;

	// 执行该任务的后台地址：如http://192.168.8.33:8081/oms-web/fetchClienteleData/1.0?companyNo=FDE
	private String taskUrl;

	// 公司编号
	private String companyCode;

	// 该任务执行的cron表达式：如每隔5分钟：0 0/5 * * * ?
	private String cronExpression;

	// 上次执行结果：0：失败；1：成功
	private Integer lastExcutionStatus;

	// 该任务上次执行结果：【当上次执行成功时可以为Null，执行失败时必须填写】
	private String lastExcutionResult;

	// 上次执行时间
	private Date lastExcutionTime;

	// 是否启用 0：未启用；1：已启用
	private Integer isActived;

	// 是否删除：0：未删除；1：已删除
	private int isDelete = 0;

	// 该任务执行的功能名称
	private String functionalName;

	// 该任务的任务类型 1：增量更新；2：推送，，，
	private String functionalType;

	// 创建人账号
	private String createUser;

	// 修改人账号
	private String modifiedUser;

	// 描述
	private String description;

	// 新增时间
	private Date gmtCreate;

	// 修改时间
	private Date gmtModify;

	private String jobName;

	private String jobGroup;

	private String jobTrigger;

	private Boolean isSync;

	// 该任务运行状态
	private String runStatus;

	public Long getScheduleJobId() {
		return this.scheduleJobId;
	}

	public String getTaskUrl() {
		return this.taskUrl;
	}

	public void setTaskUrl(String taskUrl) {
		this.taskUrl = taskUrl;
	}

	public String getCompanyCode() {
		return this.companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCronExpression() {
		return this.cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public Integer getLastExcutionStatus() {
		return this.lastExcutionStatus;
	}

	public void setLastExcutionStatus(Integer lastExcutionStatus) {
		this.lastExcutionStatus = lastExcutionStatus;
	}

	public String getLastExcutionResult() {
		return this.lastExcutionResult;
	}

	public void setLastExcutionResult(String lastExcutionResult) {
		this.lastExcutionResult = lastExcutionResult;
	}

	public Date getLastExcutionTime() {
		return this.lastExcutionTime;
	}

	public void setLastExcutionTime(Date lastExcutionTime) {
		this.lastExcutionTime = lastExcutionTime;
	}

	public Integer getIsActived() {
		return isActived;
	}

	public void setIsActived(Integer isActived) {
		this.isActived = isActived;
	}

	public int getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public String getFunctionalName() {
		return this.functionalName;
	}

	public void setFunctionalName(String functionalName) {
		this.functionalName = functionalName;
	}

	public String getFunctionalType() {
		return this.functionalType;
	}

	public void setFunctionalType(String functionalType) {
		this.functionalType = functionalType;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getModifiedUser() {
		return this.modifiedUser;
	}

	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getGmtCreate() {
		return this.gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModify() {
		return this.gmtModify;
	}

	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}

	public String getJobName() {
		return this.jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroup() {
		return this.jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getJobTrigger() {
		return this.jobTrigger;
	}

	public void setJobTrigger(String jobTrigger) {
		this.jobTrigger = jobTrigger;
	}

	public String getRunStatus() {
		return runStatus;
	}

	public void setRunStatus(String runStatus) {
		this.runStatus = runStatus;
	}

	public void setScheduleJobId(Long scheduleJobId) {
		this.scheduleJobId = scheduleJobId;
	}

	public Boolean getIsSync() {
		return isSync;
	}

	public void setIsSync(Boolean isSync) {
		this.isSync = isSync;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
}
