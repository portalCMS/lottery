package com.lottery.bean.entity.vo;

import java.util.List;

/**
 * 彩种表
 * 
 * @author jeff
 * 
 */
public class LotteryTypeVO extends GenericEntityVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -125917859007520096L;

	/**
	 * 彩种组代码
	 */
	private String lotteryGroup;
	/**
	 * 彩种代码
	 */
	private String lotteryCode;

	/**
	 * 彩种名称
	 */
	private String lotteryName;

	/**
	 * 彩种组名称
	 */
	private String lotteryGroupName;

	/**
	 * 状态
	 */
	private int lotteryStatus;

	/**
	 * 任务执行时间表达式
	 */
	private String taskCornExpression;

	private String lotteryJobName;

	private int lotteryLevel;

	private String seriesRule;

	private int catchTimes;

	private String startTime;

	private String endTime;

	private int spanTime;

	private int beforeLotTime;

	private int totalTimes;

	private List<SourceLinkVO> links;

	private String idList;

	private String lotteryCodes;

	private boolean rsvbl = false;

	private Integer afterLotTime;

	private String lastOpenIssue;

	private List<Integer> statusList;

	// 玩法种类
	private List<PlayModelVO> playModelVOs;
	// 当前期号
	private String currentIssueNo;

	public List<Integer> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<Integer> statusList) {
		this.statusList = statusList;
	}

	public String getLastOpenIssue() {
		return lastOpenIssue;
	}

	public void setLastOpenIssue(String lastOpenIssue) {
		this.lastOpenIssue = lastOpenIssue;
	}

	public List<SourceLinkVO> getLinks() {
		return links;
	}

	public void setLinks(List<SourceLinkVO> links) {
		this.links = links;
	}

	public String getLotteryCode() {
		return lotteryCode;
	}

	public void setLotteryCode(String lotteryCode) {
		this.lotteryCode = lotteryCode;
	}

	public String getLotteryName() {
		return lotteryName;
	}

	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}

	public int getLotteryStatus() {
		return lotteryStatus;
	}

	public void setLotteryStatus(int lotteryStatus) {
		this.lotteryStatus = lotteryStatus;
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

	public String getTaskCornExpression() {
		return taskCornExpression;
	}

	public void setTaskCornExpression(String taskCornExpression) {
		this.taskCornExpression = taskCornExpression;
	}

	public int getLotteryLevel() {
		return lotteryLevel;
	}

	public void setLotteryLevel(int lotteryLevel) {
		this.lotteryLevel = lotteryLevel;
	}

	public String getSeriesRule() {
		return seriesRule;
	}

	public void setSeriesRule(String seriesRule) {
		this.seriesRule = seriesRule;
	}

	public int getCatchTimes() {
		return catchTimes;
	}

	public void setCatchTimes(int catchTimes) {
		this.catchTimes = catchTimes;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public int getSpanTime() {
		return spanTime;
	}

	public void setSpanTime(int spanTime) {
		this.spanTime = spanTime;
	}

	public int getBeforeLotTime() {
		return beforeLotTime;
	}

	public void setBeforeLotTime(int beforeLotTime) {
		this.beforeLotTime = beforeLotTime;
	}

	public int getTotalTimes() {
		return totalTimes;
	}

	public void setTotalTimes(int totalTimes) {
		this.totalTimes = totalTimes;
	}

	public String getIdList() {
		return idList;
	}

	public void setIdList(String idList) {
		this.idList = idList;
	}

	public String getLotteryCodes() {
		return lotteryCodes;
	}

	public void setLotteryCodes(String lotteryCodes) {
		this.lotteryCodes = lotteryCodes;
	}

	public boolean isRsvbl() {
		return rsvbl;
	}

	public void setRsvbl(boolean rsvbl) {
		this.rsvbl = rsvbl;
	}

	public String getLotteryGroupName() {
		return lotteryGroupName;
	}

	public void setLotteryGroupName(String lotteryGroupName) {
		this.lotteryGroupName = lotteryGroupName;
	}

	public Integer getAfterLotTime() {
		return afterLotTime;
	}

	public void setAfterLotTime(Integer afterLotTime) {
		this.afterLotTime = afterLotTime;
	}

	public List<PlayModelVO> getPlayModelVOs() {
		return playModelVOs;
	}

	public void setPlayModelVOs(List<PlayModelVO> playModelVOs) {
		this.playModelVOs = playModelVOs;
	}

	public String getCurrentIssueNo() {
		return currentIssueNo;
	}

	public void setCurrentIssueNo(String currentIssueNo) {
		this.currentIssueNo = currentIssueNo;
	}
}
