package com.lottery.bean.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 开奖记录表
 * @author CW-HP9
 *
 */
@Entity
@Table(name="t_lottery_award_record")
public class LotteryAwardRecord extends GenericEntity{

	private static final long serialVersionUID = 2712690675391948263L;

	@Column(name="lottery_code")
	private String lotteryCode;
	
	@Column(name="start_time")
	private String startTime;
	
	@Column(name="open_time")
	private String openTime;
	
	@Column(name="lottery_number")
	private String lotteryNumber;
	
	@Column(name="status")
	private int status;

	@Column(name="issue")
	private String issue;
	
	@Column(name="end_time")
	private String endTime;
	
	@Column(name="open_type")
	private String openType;
	
	@Column(name="lottery_name")
	private String lotteryName;
	
	@Column(name="lottery_group")
	private String lotteryGroup;
	
	@Column(name="result_link")
	private String resultLink;

	@Column(name="link_id")
	private Long linkId;
	
	
	public String getResultLink() {
		return resultLink;
	}

	public void setResultLink(String resultLink) {
		this.resultLink = resultLink;
	}

	public Long getLinkId() {
		return linkId;
	}

	public void setLinkId(Long linkId) {
		this.linkId = linkId;
	}

	public String getLotteryCode() {
		return lotteryCode;
	}

	public void setLotteryCode(String lotteryCode) {
		this.lotteryCode = lotteryCode;
	}

	public String getLotteryNumber() {
		return lotteryNumber;
	}

	public void setLotteryNumber(String lotteryNumber) {
		this.lotteryNumber = lotteryNumber;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getOpenType() {
		return openType;
	}

	public void setOpenType(String openType) {
		this.openType = openType;
	}

	public String getLotteryGroup() {
		return lotteryGroup;
	}

	public void setLotteryGroup(String lotteryGroup) {
		this.lotteryGroup = lotteryGroup;
	}

	@Override
	public String toString() {
		return "LotteryAwardRecord [lotteryCode=" + lotteryCode + ", startTime="
				+ startTime + ", endTime="+ endTime + ", lotteryNumber=" + lotteryNumber + ", status="
				+ status + ", getId()=" + getId() + ", getCreateTime()="
				+ getCreateTime() + ", getCreateUser()=" + getCreateUser()
				+ ", getCreateUser()=" + getOpenType()+ ", getUpdateTime()=" 
				+ getUpdateTime() + ", getUpdateUser()="+ getUpdateUser() 
				+ ", getVersion()=" + getVersion() + "]";
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
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

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getLotteryName() {
		return lotteryName;
	}

	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}
	 
}
