package com.lottery.bean.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 卡等级配置表
 * @author CW-HP9
 *
 */
@Entity
@Table(name="t_card_level_config")
public class CardLevelConfig extends GenericEntity{

	/**
	 *
	 */
	private static final long serialVersionUID = 6441541027720247599L;

	@Column(name="card_inventory_id")
	private Long cardInventoryId;
	
	@Column(name="card_inventory_name")
	private String cardInventoryName;
	
	@Column(name="card_level")
	private Integer cardLevel;
	
	@Column(name="min_amount")
	private BigDecimal minAmount;
	
	@Column(name="max_amount")
	private BigDecimal maxAmount;
	
	@Column(name="active_level")
	private Integer activeLevel;
	
	@Column(name="status")
	private Integer status;
	
	public Long getCardInventoryId() {
		return cardInventoryId;
	}

	public void setCardInventoryId(Long cardInventoryId) {
		this.cardInventoryId = cardInventoryId;
	}

	public String getCardInventoryName() {
		return cardInventoryName;
	}

	public void setCardInventoryName(String cardInventoryName) {
		this.cardInventoryName = cardInventoryName;
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

	public Integer getActiveLevel() {
		return activeLevel;
	}

	public void setActiveLevel(Integer activeLevel) {
		this.activeLevel = activeLevel;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCardLevel() {
		return cardLevel;
	}

	public void setCardLevel(Integer cardLevel) {
		this.cardLevel = cardLevel;
	}

	@Override
	public String toString() {
		return "CardLevelConfig [cardInventoryId=" + cardInventoryId
				+ ", cardInventoryName=" + cardInventoryName + ", minAmount="
				+ minAmount + ", maxAmount=" + maxAmount + ", activeLevel="
				+ activeLevel + ", status=" + status + ",cardLevel="+cardLevel+", getId()=" + getId()
				+ "]";
	}



	
	
}
