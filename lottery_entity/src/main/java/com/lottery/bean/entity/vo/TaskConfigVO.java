package com.lottery.bean.entity.vo;

import java.util.List;

public class TaskConfigVO extends GenericEntityVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String lotteryName;
	
	private String lotteryCode;
	
	private String lotteryGroup;
	
	private String lotteryJobName;
	
	private String lotterySeries;
	
	private int taskStatus;
	
	private String taskCornExpression;
	
	private String taskDesc;
	
	private long lastTaskLogId;
	
	private String lotTime;
	
	private String sourceLink;
	
	private int catchTimes;
	
	private String taskParam;
	
	private String startBetTime;
	
	private String endBetTime;
	
	private String spanTime;
	
	private int repeatTimes;
	
	private String seriesRule;
	
	private String idList;
	
	private int newIssueType;
	
	public int getNewIssueType() {
		return newIssueType;
	}
	public void setNewIssueType(int newIssueType) {
		this.newIssueType = newIssueType;
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
	public String getTaskDesc() {
		return taskDesc;
	}
	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
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
	public String getSourceLink() {
		return sourceLink;
	}
	public void setSourceLink(String sourceLink) {
		this.sourceLink = sourceLink;
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
	public String getSpanTime() {
		return spanTime;
	}
	public void setSpanTime(String spanTime) {
		this.spanTime = spanTime;
	}
	public int getRepeatTimes() {
		return repeatTimes;
	}
	public void setRepeatTimes(int repeatTimes) {
		this.repeatTimes = repeatTimes;
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
	public String getIdList() {
		return idList;
	}
	public void setIdList(String idList) {
		this.idList = idList;
	}
	public String getSeriesRule() {
		return seriesRule;
	}
	public void setSeriesRule(String seriesRule) {
		this.seriesRule = seriesRule;
	}
	
	
}
