package com.lottery.bean.entity.vo;

import java.math.BigDecimal;
import java.util.List;


public class CardLevelConfigVO extends GenericEntityVO{

	/**
	 *
	 */
	private static final long serialVersionUID = 6441541027720247599L;

	private Long cardInventoryId;
	
	private String cardInventoryName;
	
	private Integer cardLevel;
	
	private BigDecimal minAmount;
	
	private BigDecimal maxAmount;
	
	private Integer activeLevel;
	
	private BigDecimal rsvdc1;
	
	private BigDecimal rsvdc2;
	
	private String rsvst1;
	
	private String rsvst2;
	
	private String webws;
	
	private String jobws;
	
	private List<CardLevelConfigVO> voList;
	
	public String getRsvst1() {
		return rsvst1;
	}

	public void setRsvst1(String rsvst1) {
		this.rsvst1 = rsvst1;
	}

	public String getRsvst2() {
		return rsvst2;
	}

	public void setRsvst2(String rsvst2) {
		this.rsvst2 = rsvst2;
	}

	public BigDecimal getRsvdc1() {
		return rsvdc1;
	}

	public void setRsvdc1(BigDecimal rsvdc1) {
		this.rsvdc1 = rsvdc1;
	}

	public BigDecimal getRsvdc2() {
		return rsvdc2;
	}

	public void setRsvdc2(BigDecimal rsvdc2) {
		this.rsvdc2 = rsvdc2;
	}

	public List<CardLevelConfigVO> getVoList() {
		return voList;
	}

	public void setVoList(List<CardLevelConfigVO> voList) {
		this.voList = voList;
	}

	public Integer getCardLevel() {
		return cardLevel;
	}

	public void setCardLevel(Integer cardLevel) {
		this.cardLevel = cardLevel;
	}
	
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

	@Override
	public String toString() {
		return "CardLevelConfig [cardInventoryId=" + cardInventoryId
				+ ", cardInventoryName=" + cardInventoryName + ", minAmount="
				+ minAmount + ", maxAmount=" + maxAmount + ", activeLevel="
				+ activeLevel + ", getCardInventoryId()="
				+ getCardInventoryId() + ", getCardInventoryName()="
				+ getCardInventoryName() + ", getMinAmount()=" + getMinAmount()
				+ ", getMaxAmount()=" + getMaxAmount() + ", getActiveLevel()="
				+ getActiveLevel() + ", getId()=" + getId()+ "]";
	}

	public String getWebws() {
		return webws;
	}

	public void setWebws(String webws) {
		this.webws = webws;
	}

	public String getJobws() {
		return jobws;
	}

	public void setJobws(String jobws) {
		this.jobws = jobws;
	}
}
