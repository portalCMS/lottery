package com.lottery.bean.entity.vo;

import java.util.List;

import com.lottery.bean.entity.LotteryType;

public class LotteryListVO extends GenericEntityVO {

	private static final long serialVersionUID = 2004421406275876852L;

	private List<LotteryTypeVO> lotterys;
	
	private String timeConfig;
	
	private String lotteryGroup;
	
	private String lotteryGroupName;
	
	private List<LotteryType> lotteryList;
	
	private String lotteryObjs;
	
	private String newLotteryObjs;

	public List<LotteryTypeVO> getLotterys() {
		return lotterys;
	}

	public void setLotterys(List<LotteryTypeVO> lotterys) {
		this.lotterys = lotterys;
	}

	public String getTimeConfig() {
		return timeConfig;
	}

	public void setTimeConfig(String timeConfig) {
		this.timeConfig = timeConfig;
	}

	public String getLotteryGroup() {
		return lotteryGroup;
	}

	public void setLotteryGroup(String lotteryGroup) {
		this.lotteryGroup = lotteryGroup;
	}

	public List<LotteryType> getLotteryList() {
		return lotteryList;
	}

	public void setLotteryList(List<LotteryType> lotteryList) {
		this.lotteryList = lotteryList;
	}

	public String getLotteryGroupName() {
		return lotteryGroupName;
	}

	public void setLotteryGroupName(String lotteryGroupName) {
		this.lotteryGroupName = lotteryGroupName;
	}

	public String getLotteryObjs() {
		return lotteryObjs;
	}

	public void setLotteryObjs(String lotteryObjs) {
		this.lotteryObjs = lotteryObjs;
	}

	public String getNewLotteryObjs() {
		return newLotteryObjs;
	}

	public void setNewLotteryObjs(String newLotteryObjs) {
		this.newLotteryObjs = newLotteryObjs;
	}
	
}
