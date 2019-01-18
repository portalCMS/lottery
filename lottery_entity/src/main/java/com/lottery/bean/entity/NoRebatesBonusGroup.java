package com.lottery.bean.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="t_norebates_bonus_group")
public class NoRebatesBonusGroup extends GenericEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5341057081289680430L;

	/**
	 * 归属奖金组ID
	 */
	@Column(name="bonus_group_id")
	private long bonusGroupId;
	
	
	/**
	 * 返点率
	 */
	@Column(name="rebates")
	private BigDecimal rebates;
	
	/**
	 * 奖金
	 */
	@Column(name="bonus")
	private BigDecimal bonus;
	
	/**
	 * 属性
	 */
	@Column(name="status")
	private int status;

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

	@Override
	public String toString() {
		return "NoRebatesBonusGroup [bonusGroupId=" + bonusGroupId
				+ ", rebates=" + rebates + ", bonus=" + bonus + ", status="
				+ status + ", getBonusGroupId()=" + getBonusGroupId()
				+ ", getRebates()=" + getRebates() + ", getBonus()="
				+ getBonus() + ", getStatus()=" + getStatus() + "]";
	}
	
	
}
