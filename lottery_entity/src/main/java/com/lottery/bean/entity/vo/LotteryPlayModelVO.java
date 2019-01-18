package com.lottery.bean.entity.vo;

import java.math.BigDecimal;
import java.util.List;


public class LotteryPlayModelVO extends GenericEntityVO {

	private static final long serialVersionUID = 2004421406275876852L;
	
	private String playModelIdList;

	private BigDecimal limitAmount;
	
	private String modelCode;
	
	private String lotteryCode;
	
	private String lotteryGroup;
	
	private String GroupName;
	
	private int status;
	
	private String userName;
	
	private List<PlayModelVO> playList;
	
	public String getPlayModelIdList() {
		return playModelIdList;
	}

	public void setPlayModelIdList(String playModelIdList) {
		this.playModelIdList = playModelIdList;
	}

	public BigDecimal getLimitAmount() {
		return limitAmount;
	}

	public void setLimitAmount(BigDecimal limitAmount) {
		this.limitAmount = limitAmount;
	}

	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	public String getLotteryCode() {
		return lotteryCode;
	}

	public void setLotteryCode(String lotteryCode) {
		this.lotteryCode = lotteryCode;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getLotteryGroup() {
		return lotteryGroup;
	}

	public void setLotteryGroup(String lotteryGroup) {
		this.lotteryGroup = lotteryGroup;
	}

	public String getGroupName() {
		return GroupName;
	}

	public void setGroupName(String groupName) {
		GroupName = groupName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<PlayModelVO> getPlayList() {
		return playList;
	}

	public void setPlayList(List<PlayModelVO> playList) {
		this.playList = playList;
	}
	
}
