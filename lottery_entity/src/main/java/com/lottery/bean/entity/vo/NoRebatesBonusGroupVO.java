package com.lottery.bean.entity.vo;

import java.math.BigDecimal;
import java.util.List;



public class NoRebatesBonusGroupVO extends GenericEntityVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4002379110561417166L;

	/**
	 * 归属奖金组ID
	 */
	private long bonusGroupId;
	
	
	/**
	 * 返点率
	 */
	private BigDecimal rebates;
	
	/**
	 * 奖金
	 */
	private BigDecimal bonus;
	
	/**
	 * 状态
	 */
	private int status;
	
	private List<NoRebatesBonusGroupVO> vos;

	public long getBonusGroupId() {
		return bonusGroupId;
	}

	public void setBonusGroupId(long bonusGroupId) {
		this.bonusGroupId = bonusGroupId;
	}

	public BigDecimal getRebates() {
		return rebates;
	}

	public void setRebates(BigDecimal rebates) {
		this.rebates = rebates;
	}

	public BigDecimal getBonus() {
		return bonus;
	}

	public void setBonus(BigDecimal bonus) {
		this.bonus = bonus;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<NoRebatesBonusGroupVO> getVos() {
		return vos;
	}

	public void setVos(List<NoRebatesBonusGroupVO> vos) {
		this.vos = vos;
	}

}
