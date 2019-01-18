package com.lottery.bean.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 彩种表
 * @author jeff
 *
 */
@Entity
@Table(name="t_lottery_type")
public class LotteryType extends GenericEntity{
	private static final long serialVersionUID = -125917859007520096L;

	/**
	 * 彩种组
	 */
	@Column(name="lottery_group")
	private String lotteryGroup;
	/**
	 * 彩种代码
	 */
	@Column(name="lottery_code")
	private String lotteryCode;
	
	/**
	 * 彩种名称
	 */
	@Column(name="lottery_name")
	private String lotteryName;
	
	/**
	 * 状态
	 */
	@Column(name="lottery_status")
	private int lotteryStatus;
	/**
	 * 总期数
	 * @return
	 */
	@Column(name="total_times")
	private int totalTimes;
	/**
	 * 抓取次数
	 */
	@Column(name="catch_times")
	private int catchTimes;
	/**
	 * 定时生成奖期任务的时间表达式(默认为每天凌晨4点钟执行)
	 */
	@Column(name="task_corn_expression")
	private String taskCornExpression;
	
	/**
	 * 定时生成奖期任务的运行状态(默认为停用10001)
	 */
	@Column(name="lottery_level")
	private int lotteryLevel;
	/**
	 * 首期开奖时间
	 */
	@Column(name="start_time")
	private String startTime;
	/**
	 * 末期开奖时间
	 */
	@Column(name="end_time")
	private String endTime;
	/**
	 * 间隔时间(秒)
	 */
	@Column(name="span_time")
	private int spanTime;
	/**
	 * 开奖前的截止投注时间(秒)
	 */
	@Column(name="before_lot_time")
	private int beforeLotTime;
	/**
	 * 奖期规则 yyyyMMdd[nnn]
	 */
	@Column(name="series_rule")
	private String seriesRule;
	/**
	 * 总限注数
	 */
	@Column(name="total_limit_bets")
	private int totalLimitBets;
	/**
	 * 总限金额
	 */
	@Column(name="total_limit_amount")
	private BigDecimal totalLimitAmount;
	
	/**
	 * 开奖任务延时开奖时间（秒）
	 */
	@Column(name="after_lot_time")
	private Integer afterLotTime;
	/**
	 * 备用字段1(排序)
	 */
	@Column(name="rsvst1")
	private String rsvst1;
	
	/**
	 * 备用字段2(火，新，热显示控制)
	 */
	@Column(name="rsvst2")
	private String rsvst2;
	
	/**
	 * 备用字段3(手机端显示控制)
	 */
	@Column(name="rsvst3")
	private String rsvst3;
	
	/**
	 * 备用字段4
	 */
	@Column(name="rsvst4")
	private String rsvst4;
	
	@Transient
	private int taskRunStatus;

	@Transient
	private String currentSeries;
	
	@Transient
	private String countSuccessSeries;
	
	@Transient
	private String countFailedSeries;
	
	@Transient
	private String failedSeriesStr;
	
	
	
	
	public String getRsvst3() {
		return rsvst3;
	}

	public void setRsvst3(String rsvst3) {
		this.rsvst3 = rsvst3;
	}

	public String getRsvst4() {
		return rsvst4;
	}

	public void setRsvst4(String rsvst4) {
		this.rsvst4 = rsvst4;
	}

	/**
	 * 彩种组名称
	 */
	@Transient
	private String lotteryGroupName;
	
	public String getLotteryGroup() {
		return lotteryGroup;
	}

	public void setLotteryGroup(String lotteryGroup) {
		this.lotteryGroup = lotteryGroup;
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

	public int getTotalTimes() {
		return totalTimes;
	}

	public void setTotalTimes(int totalTimes) {
		this.totalTimes = totalTimes;
	}

	public int getCatchTimes() {
		return catchTimes;
	}

	public void setCatchTimes(int catchTimes) {
		this.catchTimes = catchTimes;
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

	public String getSeriesRule() {
		return seriesRule;
	}

	public void setSeriesRule(String seriesRule) {
		this.seriesRule = seriesRule;
	}

	public int getTaskRunStatus() {
		return taskRunStatus;
	}

	public void setTaskRunStatus(int taskRunStatus) {
		this.taskRunStatus = taskRunStatus;
	}

	public String getCurrentSeries() {
		return currentSeries;
	}

	public void setCurrentSeries(String currentSeries) {
		this.currentSeries = currentSeries;
	}

	public String getCountSuccessSeries() {
		return countSuccessSeries;
	}

	public void setCountSuccessSeries(String countSuccessSeries) {
		this.countSuccessSeries = countSuccessSeries;
	}

	public String getCountFailedSeries() {
		return countFailedSeries;
	}

	public void setCountFailedSeries(String countFailedSeries) {
		this.countFailedSeries = countFailedSeries;
	}

	public String getFailedSeriesStr() {
		return failedSeriesStr;
	}

	public void setFailedSeriesStr(String failedSeriesStr) {
		this.failedSeriesStr = failedSeriesStr;
	}

	public String getLotteryGroupName() {
		return lotteryGroupName;
	}

	public void setLotteryGroupName(String lotteryGroupName) {
		this.lotteryGroupName = lotteryGroupName;
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
	
	public String getRsvst1() {
		return rsvst1;
	}

	public void setRsvst1(String rsvst1) {
		this.rsvst1 = rsvst1;
	}

	public String getRsvst2() {
		return rsvst2;
	}

	public void setRsvst2(String rsvst2) {
		this.rsvst2 = rsvst2;
	}

	@Override
	public String toString() {
		return "LotteryType [lotteryGroup=" + lotteryGroup + ", lotteryCode="
				+ lotteryCode + ", lotteryName=" + lotteryName
				+ ", lotteryStatus=" + lotteryStatus + ", totalTimes="
				+ totalTimes + ", catchTimes=" + catchTimes
				+ ", taskCornExpression=" + taskCornExpression
				+ ", lotteryLevel=" + lotteryLevel + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", spanTime=" + spanTime
				+ ", beforeLotTime=" + beforeLotTime + ", seriesRule="
				+ seriesRule + ", totalLimitBets=" + totalLimitBets
				+ ", totalLimitAmount=" + totalLimitAmount + ", taskRunStatus="
				+ taskRunStatus + ", currentSeries=" + currentSeries
				+ ", countSuccessSeries=" + countSuccessSeries
				+ ", countFailedSeries=" + countFailedSeries
				+ ", failedSeriesStr=" + failedSeriesStr
				+ ", lotteryGroupName=" + lotteryGroupName + ", getId()="
				+ getId() + ", getCreateTime()=" + getCreateTime()
				+ ", getCreateUser()=" + getCreateUser() + ", getUpdateTime()="
				+ getUpdateTime() + ", getUpdateUser()=" + getUpdateUser()
				+ ", getVersion()=" + getVersion() + "]";
	}

	
	
	
}
