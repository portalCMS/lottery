package com.lottery.api.entity;

import java.io.Serializable;

public class ReadData  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5213064939211973764L;
	
	private String lotteryid;
	private String issue;

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
}
