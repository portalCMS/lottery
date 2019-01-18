package com.lottery.bean.entity.vo;

import java.util.Date;


public class LotteryAwardRecordVO extends GenericEntityVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2712690675391948263L;

	private String lotteryCode;
	
	private String openTime;
	
	private String lotteryNumber;
	
	private int status;

	private String issue;
	
	public String getLotteryCode() {
		return lotteryCode;
	}

	public void setLotteryCode(String lotteryCode) {
		this.lotteryCode = lotteryCode;
	}

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
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

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}
	 
}
