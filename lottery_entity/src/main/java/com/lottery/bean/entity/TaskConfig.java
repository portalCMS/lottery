package com.lottery.bean.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name="t_lottery_task")
public class TaskConfig extends GenericEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="lottery_name")
	private String lotteryName;
	
	@Column(name="lottery_code")
	private String lotteryCode;
	
	@Column(name="lottery_group")
	private String lotteryGroup;
	
	@Column(name="lottery_job_name")
	private String lotteryJobName;
	
	@Column(name="lottery_series")
	private String lotterySeries;
	
	@Column(name="task_status")
	private int taskStatus;
	
	@Column(name="task_corn_expression")
	private String taskCornExpression;
	
	@Column(name="last_task_log_id")
	private long lastTaskLogId;
	
	@Column(name="lot_time")
	private String lotTime;
	
	@Column(name="catch_times")
	private int catchTimes;
	
	@Column(name="param")
	private String taskParam;
	
	@Column(name="start_bet_time")
	private String startBetTime;
	
	@Column(name="end_bet_time")
	private String endBetTime;
	
	@Column(name="task_date")
	private String taskDate;
	
	//奖期执行日期
	@Column(name="lot_date")
	private String lotDate;
	/**
	 * 如果奖期跨天并且跨天0点后的期号是非重新计算的，issueDate等于跨天后的日期，
	 * 否则仍然等于之前一天的日期。
	 */
	@Transient
	private Integer issueDate;
	
	@Transient
	private boolean running;
	
	public String getLotDate() {
		return lotDate;
	}
	public void setLotDate(String lotDate) {
		this.lotDate = lotDate;
	}
	public int getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(int taskStatus) {
		this.taskStatus = taskStatus;
	}
	public String getTaskCornExpression() {
		return taskCornExpression;
	}
	public void setTaskCornExpression(String taskCornExpression) {
		this.taskCornExpression = taskCornExpression;
	}
	public long getLastTaskLogId() {
		return lastTaskLogId;
	}
	public void setLastTaskLogId(long lastTaskLogId) {
		this.lastTaskLogId = lastTaskLogId;
	}
	public String getLotTime() {
		return lotTime;
	}
	public void setLotTime(String lotTime) {
		this.lotTime = lotTime;
	}
	
	public int getCatchTimes() {
		return catchTimes;
	}
	public void setCatchTimes(int catchTimes) {
		this.catchTimes = catchTimes;
	}
	public String getStartBetTime() {
		return startBetTime;
	}
	public void setStartBetTime(String startBetTime) {
		this.startBetTime = startBetTime;
	}
	public String getTaskParam() {
		return taskParam;
	}
	public void setTaskParam(String taskParam) {
		this.taskParam = taskParam;
	}
	public String getEndBetTime() {
		return endBetTime;
	}
	public void setEndBetTime(String endBetTime) {
		this.endBetTime = endBetTime;
	}
	public String getLotteryName() {
		return lotteryName;
	}
	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}
	public String getLotteryCode() {
		return lotteryCode;
	}
	public void setLotteryCode(String lotteryCode) {
		this.lotteryCode = lotteryCode;
	}
	public String getLotteryGroup() {
		return lotteryGroup;
	}
	public void setLotteryGroup(String lotteryGroup) {
		this.lotteryGroup = lotteryGroup;
	}
	public String getLotteryJobName() {
		return lotteryJobName;
	}
	public void setLotteryJobName(String lotteryJobName) {
		this.lotteryJobName = lotteryJobName;
	}
	public String getLotterySeries() {
		return lotterySeries;
	}
	public void setLotterySeries(String lotterySeries) {
		this.lotterySeries = lotterySeries;
	}
	
	public String getTaskDate() {
		return taskDate;
	}
	public void setTaskDate(String taskDate) {
		this.taskDate = taskDate;
	}
	
	public Integer getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(Integer issueDate) {
		this.issueDate = issueDate;
	}
	
	public boolean isRunning() {
		return running;
	}
	public void setRunning(boolean running) {
		this.running = running;
	}
	@Override
	public String toString() {
		return "TaskConfig [lotteryName=" + lotteryName + ", lotteryCode="
				+ lotteryCode + ", lotteryGroup=" + lotteryGroup
				+ ", lotteryJobName=" + lotteryJobName + ", lotterySeries="
				+ lotterySeries + ", taskStatus=" + taskStatus
				+ ", taskCornExpression=" + taskCornExpression
				+ ", lastTaskLogId=" + lastTaskLogId + ", lotTime=" + lotTime
				+ ", catchTimes=" + catchTimes + ", taskParam=" + taskParam
				+ ", startBetTime=" + startBetTime + ", endBetTime="
				+ endBetTime
				+ ", getId()=" + getId() + ", getCreateTime()="
				+ getCreateTime() + ", getCreateUser()=" + getCreateUser()
				+ ", getUpdateTime()=" + getUpdateTime() + ", getUpdateUser()="
				+ getUpdateUser() + ", getVersion()=" + getVersion() + "]";
	}
	
}
