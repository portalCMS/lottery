package com.lottery.bean.entity.vo;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;


public class BonusGroupVO extends GenericEntityVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2004421406275876852L;

	private BigDecimal rebates;
	
	private String name;
	
	private int status;
	
	private String remark;
	
	private BigDecimal payoutRatio;
	
	private BigDecimal margin;
	
	private BigDecimal theoryBonus;
	
	private List<LotteryPlayBonusVO> lpbList;

	public BigDecimal getRebates() {
		return rebates;
	}

	public void setRebates(BigDecimal rebates) {
		this.rebates = rebates;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getPayoutRatio() {
		return payoutRatio;
	}

	public void setPayoutRatio(BigDecimal payoutRatio) {
		this.payoutRatio = payoutRatio;
	}

	public BigDecimal getMargin() {
		return margin;
	}

	public void setMargin(BigDecimal margin) {
		this.margin = margin;
	}

	public List<LotteryPlayBonusVO> getLpbList() {
		return lpbList;
	}

	public void setLpbList(List<LotteryPlayBonusVO> lpbList) {
		this.lpbList = lpbList;
	}

	public BigDecimal getTheoryBonus() {
		return theoryBonus;
	}

	public void setTheoryBonus(BigDecimal theoryBonus) {
		this.theoryBonus = theoryBonus;
	}
	
}
