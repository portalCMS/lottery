package com.lottery.bean.entity.vo;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 */
public class LotteryJsonVO extends GenericEntityVO{

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
	 * 状态
	 */
	private int lotteryStatus;
	
	/**
	 * 总限注数
	 */
	private int totalLimitBets;
	/**
	 * 总限金额
	 */
	private BigDecimal totalLimitAmount;
	
	private String lotteryJobName;
	
	private int lotteryLevel;
	
	private String seriesRule;
	
	private int catchTimes;
	
	private String startTime;

	private String endTime;
	
	private int spanTime;
	
	private int beforeLotTime;
	
	private int totalTimes;
	
	private String seriesExpression;
	
	private Integer afterLotTime;
	
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

	public String getSeriesExpression() {
		return seriesExpression;
	}

	public void setSeriesExpression(String seriesExpression) {
		this.seriesExpression = seriesExpression;
	}

	public int getTotalLimitBets() {
		return totalLimitBets;
	}

	public void setTotalLimitBets(int totalLimitBets) {
		this.totalLimitBets = totalLimitBets;
	}

	public BigDecimal getTotalLimitAmount() {
		return totalLimitAmount;
	}

	public void setTotalLimitAmount(BigDecimal totalLimitAmount) {
		this.totalLimitAmount = totalLimitAmount;
	}

	public Integer getAfterLotTime() {
		return afterLotTime;
	}

	public void setAfterLotTime(Integer afterLotTime) {
		this.afterLotTime = afterLotTime;
	}
	
	

	
}
