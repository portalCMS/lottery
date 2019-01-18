package com.lottery.bean.entity.vo;

import java.math.BigDecimal;


public class VCustomerFrozenUserVO extends GenericEntityVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8744928199493081415L;

	private long userId;
	
	private String customerName;
	
	private int customerLevel; 
	
	private int lowercount;
	
	private BigDecimal cash;
	
	private BigDecimal frozenCash;
	
	private long cashId;
	
	private String createUser;
	
	private String updateUser;
	
	private int version;
	
	private String frozenBeginTime;
	
	private String frozenEndTime;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public int getCustomerLevel() {
		return customerLevel;
	}

	public void setCustomerLevel(int customerLevel) {
		this.customerLevel = customerLevel;
	}

	public int getLowercount() {
		return lowercount;
	}

	public void setLowercount(int lowercount) {
		this.lowercount = lowercount;
	}


	public BigDecimal getCash() {
		return cash;
	}

	public void setCash(BigDecimal cash) {
		this.cash = cash;
	}

	public BigDecimal getFrozenCash() {
		return frozenCash;
	}

	public void setFrozenCash(BigDecimal frozenCash) {
		this.frozenCash = frozenCash;
	}

	public long getCashId() {
		return cashId;
	}

	public void setCashId(long cashId) {
		this.cashId = cashId;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getFrozenBeginTime() {
		return frozenBeginTime;
	}

	public void setFrozenBeginTime(String frozenBeginTime) {
		this.frozenBeginTime = frozenBeginTime;
	}

	public String getFrozenEndTime() {
		return frozenEndTime;
	}

	public void setFrozenEndTime(String frozenEndTime) {
		this.frozenEndTime = frozenEndTime;
	}
}
