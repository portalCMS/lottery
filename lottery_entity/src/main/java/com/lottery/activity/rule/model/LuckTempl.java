package com.lottery.activity.rule.model;

import java.math.BigDecimal;
import java.util.List;

public class LuckTempl {
//	最小金额	
	private BigDecimal minAmount;
//	最大金额
	private BigDecimal maxAmount;
//	循环天数
	private Integer cycleDays;
//	抽奖奖区等级
	private Integer awardRank;
//	奖级设定模板
	private List<AwardLevelTemp> levelTemps;

	
	public Integer getAwardRank() {
		return awardRank;
	}

	public void setAwardRank(Integer awardRank) {
		this.awardRank = awardRank;
	}

	public BigDecimal getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(BigDecimal minAmount) {
		this.minAmount = minAmount;
	}

	public BigDecimal getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(BigDecimal maxAmount) {
		this.maxAmount = maxAmount;
	}

	public Integer getCycleDays() {
		return cycleDays;
	}

	public void setCycleDays(Integer cycleDays) {
		this.cycleDays = cycleDays;
	}

	public List<AwardLevelTemp> getLevelTemps() {
		return levelTemps;
	}

	public void setLevelTemps(List<AwardLevelTemp> levelTemps) {
		this.levelTemps = levelTemps;
	}

	

}
