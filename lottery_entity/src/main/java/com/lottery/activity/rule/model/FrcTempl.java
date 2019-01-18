package com.lottery.activity.rule.model;

import java.math.BigDecimal;
import java.util.List;

public class FrcTempl {

	private BigDecimal minGameAmount;
	
	private BigDecimal maxAwardAmount;
	
	private BigDecimal rateAmount;

	public BigDecimal getMinGameAmount() {
		return minGameAmount;
	}

	public void setMinGameAmount(BigDecimal minGameAmount) {
		this.minGameAmount = minGameAmount;
	}

	public BigDecimal getMaxAwardAmount() {
		return maxAwardAmount;
	}

	public void setMaxAwardAmount(BigDecimal maxAwardAmount) {
		this.maxAwardAmount = maxAwardAmount;
	}

	public BigDecimal getRateAmount() {
		return rateAmount;
	}

	public void setRateAmount(BigDecimal rateAmount) {
		this.rateAmount = rateAmount;
	}
}
