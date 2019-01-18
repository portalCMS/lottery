package com.lottery.bean.entity.vo;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用户银行卡表
 * @author Jeff
 */
public class CustomerBankCardVO extends GenericEntityVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8792093908285386434L;

	/**
	 * 用户ID
	 */
	private long customerId;
	
	/**
	 * 银行Id
	 */
	private long bankId;
	
	/**
	 * 开会地址
	 */
	private String bankcardAddress;
	
	/**
	 * 支行名称
	 */
	private String branceBankName;
	
	/**
	 * 开户姓名
	 */
	private String opencardName;
	
	/**
	 * 银行卡号
	 */
	private String cardNo;
	
	/**
	 * 银行卡状态
	 */
	private int bankcardStatus;

	/**
	 * 备注
	 * @return
	 */
	private String remark;
	
	/**
	 * 银行名称
	 */
	private String bankName;
	
	private Long cardInventoryId;
	
	private Integer cardLevel;
	
	private int cardCount;
	
	private BigDecimal cardMoney;
	
	private String cardInventoryName;
	
	private int bindCount;
	
	public Long getCardInventoryId() {
		return cardInventoryId;
	}

	public void setCardInventoryId(Long cardInventoryId) {
		this.cardInventoryId = cardInventoryId;
	}

	public Integer getCardLevel() {
		return cardLevel;
	}

	public void setCardLevel(Integer cardLevel) {
		this.cardLevel = cardLevel;
	}
	
	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}


	public String getBankcardAddress() {
		return bankcardAddress;
	}

	public void setBankcardAddress(String bankcardAddress) {
		this.bankcardAddress = bankcardAddress;
	}

	

	public long getBankId() {
		return bankId;
	}

	public void setBankId(long bankId) {
		this.bankId = bankId;
	}

	public String getOpencardName() {
		return opencardName;
	}

	public void setOpencardName(String opencardName) {
		this.opencardName = opencardName;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public int getBankcardStatus() {
		return bankcardStatus;
	}

	public void setBankcardStatus(int bankcardStatus) {
		this.bankcardStatus = bankcardStatus;
	}

	public String getBranceBankName() {
		return branceBankName;
	}

	public void setBranceBankName(String branceBankName) {
		this.branceBankName = branceBankName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public int getCardCount() {
		return cardCount;
	}

	public void setCardCount(int cardCount) {
		this.cardCount = cardCount;
	}

	public BigDecimal getCardMoney() {
		return cardMoney;
	}

	public void setCardMoney(BigDecimal cardMoney) {
		this.cardMoney = cardMoney;
	}

	public String getCardInventoryName() {
		return cardInventoryName;
	}

	public void setCardInventoryName(String cardInventoryName) {
		this.cardInventoryName = cardInventoryName;
	}

	public int getBindCount() {
		return bindCount;
	}

	public void setBindCount(int bindCount) {
		this.bindCount = bindCount;
	}

}
