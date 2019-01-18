package com.lottery.activity.rule.model;

import java.math.BigDecimal;
import java.util.List;

public class AwardLevelTemp {
//  中奖金额 
	private BigDecimal awardAmount;
//	中奖个数
	private Integer awardCount;
//	中奖概率
	private BigDecimal awardChance;
//	奖级
	private Integer awardLevel;
	
	public Integer getAwardLevel() {
		return awardLevel;
	}
	public void setAwardLevel(Integer awardLevel) {
		this.awardLevel = awardLevel;
	}
	public BigDecimal getAwardAmount() {
		return awardAmount;
	}
	public void setAwardAmount(BigDecimal awardAmount) {
		this.awardAmount = awardAmount;
	}
	public Integer getAwardCount() {
		return awardCount;
	}
	public void setAwardCount(Integer awardCount) {
		this.awardCount = awardCount;
	}
	public BigDecimal getAwardChance() {
		return awardChance;
	}
	public void setAwardChance(BigDecimal awardChance) {
		this.awardChance = awardChance;
	}

	
}
