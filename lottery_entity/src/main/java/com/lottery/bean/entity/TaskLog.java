package com.lottery.bean.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 定时任务运行日志表
 * @author CW-HP9
 *
 */
@Entity
@Table(name="t_task_log")
public class TaskLog extends GenericEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 对应的定时任务id
	 */
	@Column(name="task_id")
	private long taskId;
	
	/**
	 * 任务名称
	 */
	@Column(name="task_name")
	private String taskName;
	
	/**
	 * 任务类型
	 */
	@Column(name="task_type")
	private String taskType;
	
	/**
	 * 任务运行状态
	 */
	@Column(name="run_status")
	private int runStatus;
	
	/**
	 * 错误日志
	 */
	@Column(name="error_message")
	private String errorMessage;
	
	/**
	 * 任务开始时间
	 */
	@Column(name="process_start_time")
	private String processStartTime;
	
	/**
	 * 任务结束时间
	 */
	@Column(name="process_end_time")
	private String processEndTime;

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public int getRunStatus() {
		return runStatus;
	}

	public void setRunStatus(int runStatus) {
		this.runStatus = runStatus;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getProcessStartTime() {
		return processStartTime;
	}

	public void setProcessStartTime(String processStartTime) {
		this.processStartTime = processStartTime;
	}

	public String getProcessEndTime() {
		return processEndTime;
	}

	public void setProcessEndTime(String processEndTime) {
		this.processEndTime = processEndTime;
	}

	@Override
	public String toString() {
		return "TaskLog [taskId=" + taskId + ", taskName=" + taskName
				+ ", taskType=" + taskType + ", runStatus=" + runStatus
				+ ", errorMessage=" + errorMessage + ", processStartTime="
				+ processStartTime + ", processEndTime=" + processEndTime
				+ ", getId()=" + getId() + ", getCreateTime()="
				+ getCreateTime() + ", getCreateUser()=" + getCreateUser()
				+ ", getUpdateTime()=" + getUpdateTime() + ", getUpdateUser()="
				+ getUpdateUser() + ", getVersion()=" + getVersion() + "]";
	}

	
	
}
