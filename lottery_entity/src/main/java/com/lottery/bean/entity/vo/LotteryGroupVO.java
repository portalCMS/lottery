package com.lottery.bean.entity.vo;

public class LotteryGroupVO extends GenericEntityVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String lotteryGroupCode;
	
	private String lotteryGroupName;

	public String getLotteryGroupCode() {
		return lotteryGroupCode;
	}

	public void setLotteryGroupCode(String lotteryGroupCode) {
		this.lotteryGroupCode = lotteryGroupCode;
	}

	public String getLotteryGroupName() {
		return lotteryGroupName;
	}

	public void setLotteryGroupName(String lotteryGroupName) {
		this.lotteryGroupName = lotteryGroupName;
	}
	
	
}
