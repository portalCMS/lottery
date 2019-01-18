package com.lottery.api.entity;

import java.io.Serializable;

public class SendData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8708888338379263323L;
	private String lotteryid;
	private String issue;
	private String code;

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
