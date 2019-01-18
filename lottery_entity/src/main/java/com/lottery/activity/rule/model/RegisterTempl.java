package com.lottery.activity.rule.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 累计充值、投注奖励、中奖奖励
 * @author CW-HP7
 *
 */
public class RegisterTempl {

	private String regStartTime;
	
	private String regEndTime;
	
	private BigDecimal ativityMoney;
	
	public String getRegStartTime() {
		return regStartTime;
	}

	public void setRegStartTime(String regStartTime) {
		this.regStartTime = regStartTime;
	}

	public String getRegEndTime() {
		return regEndTime;
	}

	public void setRegEndTime(String regEndTime) {
		this.regEndTime = regEndTime;
	}

	public BigDecimal getAtivityMoney() {
		return ativityMoney;
	}

	public void setAtivityMoney(BigDecimal ativityMoney) {
		this.ativityMoney = ativityMoney;
	}
	
}
