package com.lottery.bean.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 特例任务配置表
 * @author CW-HP9
 *
 */
@Entity
@Table(name="t_special_task_config")
public class SpecialTaskConfig extends GenericEntity{
	private static final long serialVersionUID = 1L;
	
	//彩种代码
	@Column(name="lottery_code")
	private String lotteryCode;
	//彩种组代码
	@Column(name="lottery_group")
	private String lotteryGroup;
	//特例任务本身的状态
	@Column(name="status")
	private int status;
	//特例任务控制任务状态（0标识从开始时间到结束时间内任务都无效，1标识从开始时间到结束时间按照特例规则来替换控制奖期任务，
	//2标识从开始时间到结束时间按照特例规则插入新的奖期任务（添加而不替换））
	@Column(name="task_control_status")
	private int taskControlStatus;
	//特例时间内每期奖期间的间隔时间,以秒为单位
	@Column(name="span_time")
	private int spanTime;
	//特例时间内号源设置(如果有多号源用{}分隔)	
	@Column(name="source_links")
	private String sourceLinks;
	//特例时间内奖期的抓号次数设置
	@Column(name="catch_times")
	private int catchTimes;
	//特例任务开始时间
	@Column(name="start_time")
	private String startTime;
	//特例任务结束时间
	@Column(name="end_time")
	private String endTime;
	//特例任务开始期号
	@Column(name="start_series")
	private String startSeries;
	//特例任务结束期号
	@Column(name="end_series")
	private String endSeries;
	//特例时间段 首期开始投注时间
	@Column(name="start_bet_time")
	private String startBetTime;
	//特例时间段 首期截止投注时间
	@Column(name="end_bet_time")
	private String endBetTime;
	//特例时间段 首期开奖投注时间
	@Column(name="lot_time")
	private String lotTime;
	
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getTaskControlStatus() {
		return taskControlStatus;
	}
	public void setTaskControlStatus(int taskControlStatus) {
		this.taskControlStatus = taskControlStatus;
	}
	public int getSpanTime() {
		return spanTime;
	}
	public void setSpanTime(int spanTime) {
		this.spanTime = spanTime;
	}
	public String getSourceLinks() {
		return sourceLinks;
	}
	public void setSourceLinks(String sourceLinks) {
		this.sourceLinks = sourceLinks;
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
	public String getStartSeries() {
		return startSeries;
	}
	public void setStartSeries(String startSeries) {
		this.startSeries = startSeries;
	}
	public String getEndSeries() {
		return endSeries;
	}
	public void setEndSeries(String endSeries) {
		this.endSeries = endSeries;
	}
	public String getStartBetTime() {
		return startBetTime;
	}
	public void setStartBetTime(String startBetTime) {
		this.startBetTime = startBetTime;
	}
	public String getEndBetTime() {
		return endBetTime;
	}
	public void setEndBetTime(String endBetTime) {
		this.endBetTime = endBetTime;
	}
	public String getLotTime() {
		return lotTime;
	}
	public void setLotTime(String lotTime) {
		this.lotTime = lotTime;
	}
	@Override
	public String toString() {
		return "SpecialTaskConfig [lotteryCode=" + lotteryCode
				+ ", lotteryGroup=" + lotteryGroup + ", status=" + status
				+ ", taskControlStatus=" + taskControlStatus + ", spanTime="
				+ spanTime + ", sourceLinks=" + sourceLinks + ", catchTimes="
				+ catchTimes + ", startTime=" + startTime + ", endTime="
				+ endTime + ", startSeries=" + startSeries + ", endSeries="
				+ endSeries + ", startBetTime=" + startBetTime
				+ ", endBetTime=" + endBetTime + ", lotTime=" + lotTime
				+ ", getId()=" + getId() + ", getCreateTime()="
				+ getCreateTime() + ", getCreateUser()=" + getCreateUser()
				+ ", getUpdateTime()=" + getUpdateTime() + ", getUpdateUser()="
				+ getUpdateUser() + ", getVersion()=" + getVersion() + "]";
	}
	
	
}
