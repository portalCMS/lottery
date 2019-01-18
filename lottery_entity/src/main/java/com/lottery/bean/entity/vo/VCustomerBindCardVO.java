package com.lottery.bean.entity.vo;

import java.math.BigDecimal;


public class VCustomerBindCardVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1384297808519493032L;
 

	private long cardId;
	
	private String bankName;
	
	private String customerName;
	
	private String cardNo;
	
	private long cardCount;
	
	private BigDecimal cardMoney;
	
	private int extendsStatus;
	
	private int bankcardStatus;
	
 	private int status;
	
	private long bindId;
	
	private long customerId;

	public long getCardId() {
		return cardId;
	}

	public void setCardId(long cardId) {
		this.cardId = cardId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public long getCardCount() {
		return cardCount;
	}

	public void setCardCount(long cardCount) {
		this.cardCount = cardCount;
	}

	public BigDecimal getCardMoney() {
		return cardMoney;
	}

	public void setCardMoney(BigDecimal cardMoney) {
		this.cardMoney = cardMoney;
	}

	public int getExtendsStatus() {
		return extendsStatus;
	}

	public void setExtendsStatus(int extendsStatus) {
		this.extendsStatus = extendsStatus;
	}

	public int getBankcardStatus() {
		return bankcardStatus;
	}

	public void setBankcardStatus(int bankcardStatus) {
		this.bankcardStatus = bankcardStatus;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getBindId() {
		return bindId;
	}

	public void setBindId(long bindId) {
		this.bindId = bindId;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	
	
}
