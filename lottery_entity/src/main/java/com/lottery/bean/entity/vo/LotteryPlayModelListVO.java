package com.lottery.bean.entity.vo;

import java.math.BigDecimal;
import java.util.List;

import com.lottery.bean.entity.LotteryPlayModel;
import com.lottery.bean.entity.LotteryType;

public class LotteryPlayModelListVO extends GenericEntityVO {

	private static final long serialVersionUID = 2004421406275876852L;

	private List<LotteryPlayModelVO> lotteryPlayModels;
	
	private List<LotteryPlayModel> lpmList;
	
	private Integer balCount;
	
	private String groupName;
	
	private String modelCode;
	
	private BigDecimal totalLimitAmount;
	
	private int totalLimitBets;
	
	private int sortIndex;
	
	public int getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(int sortIndex) {
		this.sortIndex = sortIndex;
	}

	public List<LotteryPlayModelVO> getLotteryPlayModels() {
		return lotteryPlayModels;
	}

	public void setLotteryPlayModels(List<LotteryPlayModelVO> lotteryPlayModels) {
		this.lotteryPlayModels = lotteryPlayModels;
	}

	public BigDecimal getTotalLimitAmount() {
		return totalLimitAmount;
	}

	public void setTotalLimitAmount(BigDecimal totalLimitAmount) {
		this.totalLimitAmount = totalLimitAmount;
	}

	public int getTotalLimitBets() {
		return totalLimitBets;
	}

	public void setTotalLimitBets(int totalLimitBets) {
		this.totalLimitBets = totalLimitBets;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<LotteryPlayModel> getLpmList() {
		return lpmList;
	}

	public void setLpmList(List<LotteryPlayModel> lpmList) {
		this.lpmList = lpmList;
	}

	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	public Integer getBalCount() {
		return balCount;
	}

	public void setBalCount(Integer balCount) {
		this.balCount = balCount;
	}

	
	
	
}
