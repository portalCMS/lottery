package com.lottery.bean.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "v_customer_bind_card")
public class VCustomerBindCard implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1384297808519493032L;

	@Id
	@Column(name = "card_id")
	private long cardId;

	@Column(name = "bank_name")
	private String bankName;

	@Column(name = "customer_name")
	private String customerName;

	@Column(name = "card_no")
	private String cardNo;

	@Column(name = "card_count")
	private long cardCount;

	@Column(name = "card_money")
	private BigDecimal cardMoney;

	@Column(name = "extends_status")
	private int extendsStatus;

	@Column(name = "bankcard_status")
	private int bankcardStatus;

	@Column(name = "status")
	private int status;

	@Column(name = "bind_id")
	private long bindId;

	@Column(name = "customer_id")
	private long customerId;
	
	@Column(name="card_level")
	private int cardLevel;

	@Column(name="card_inventory_name")
	private String cardInventoryName;
	
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

	@Override
	public String toString() {
		return "VCustomerBindCard [cardId=" + cardId + ", bankName=" + bankName
				+ ", customerName=" + customerName + ", cardNo=" + cardNo
				+ ", cardCount=" + cardCount + ", cardMoney=" + cardMoney
				+ ", extendsStatus=" + extendsStatus + ", bankcardStatus="
				+ bankcardStatus + ", status=" + status + ", bindId=" + bindId
				+ ", customerId=" + customerId + "]";
	}

	public int getCardLevel() {
		return cardLevel;
	}

	public void setCardLevel(int cardLevel) {
		this.cardLevel = cardLevel;
	}

	public String getCardInventoryName() {
		return cardInventoryName;
	}

	public void setCardInventoryName(String cardInventoryName) {
		this.cardInventoryName = cardInventoryName;
	}


}
