package com.lottery.api.entity;

import java.io.Serializable;

public class StatusData implements Serializable{

	private String lotteryid;
	private String issue;
	private String status;


	public String getLotteryid() {
		return lotteryid;
	}

	public void setLotteryid(String lotteryid) {
		this.lotteryid = lotteryid;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
