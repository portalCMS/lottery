package com.lottery.activity.rule.model;

import java.math.BigDecimal;
import java.util.List;

public class BetTempl {
	/**
	 * 限制名额的天数范围
	 */
	private Integer limitDays;
	/**
	 * 限制人数
	 */
	private Integer limitCount;
	
	private BigDecimal minGameAmount;
	
	private BigDecimal maxAwardAmount;
	
	private Integer cycleDays;
	
	private String cycleType;
	
	private List<ConfigTemp> amountConfig;
	
	private BigDecimal rateAmount;
	//查询的是历史表还是主表
	private String isHistory;
	//活动统计的开始时间
	private String countStartTime;
	//活动统计的结束时间
	private String countEndTime;

	public String getIsHistory() {
		return isHistory;
	}

	public void setIsHistory(String isHistory) {
		this.isHistory = isHistory;
	}

	public String getCountStartTime() {
		return countStartTime;
	}

	public void setCountStartTime(String countStartTime) {
		this.countStartTime = countStartTime;
	}

	public String getCountEndTime() {
		return countEndTime;
	}

	public void setCountEndTime(String countEndTime) {
		this.countEndTime = countEndTime;
	}

	public Integer getLimitDays() {
		return limitDays;
	}

	public void setLimitDays(Integer limitDays) {
		this.limitDays = limitDays;
	}

	public Integer getLimitCount() {
		return limitCount;
	}

	public void setLimitCount(Integer limitCount) {
		this.limitCount = limitCount;
	}

	public String getCycleType() {
		return cycleType;
	}

	/**
	 * 
	 * @param cycleType
	 */
	public void setCycleType(String cycleType) {
		this.cycleType = cycleType;
	}

	/**
	 * 
	 * @return
	 */
	public BigDecimal getMinGameAmount() {
		return minGameAmount;
	}

	public void setMinGameAmount(BigDecimal minGameAmount) {
		this.minGameAmount = minGameAmount;
	}

	public BigDecimal getMaxAwardAmount() {
		return maxAwardAmount;
	}

	public void setMaxAwardAmount(BigDecimal maxAwardAmount) {
		this.maxAwardAmount = maxAwardAmount;
	}

	public Integer getCycleDays() {
		return cycleDays;
	}

	public void setCycleDays(Integer cycleDays) {
		this.cycleDays = cycleDays;
	}

	public List<ConfigTemp> getAmountConfig() {
		return amountConfig;
	}

	public void setAmountConfig(List<ConfigTemp> amountConfig) {
		this.amountConfig = amountConfig;
	}

	public BigDecimal getRateAmount() {
		return rateAmount;
	}

	public void setRateAmount(BigDecimal rateAmount) {
		this.rateAmount = rateAmount;
	}
	
}
