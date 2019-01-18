package com.lottery.bean.entity.vo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用户现金账户
 *
 */
public class CustomerCashVO extends GenericEntityVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7952929816405999733L;

	/**
	 * 用户id
	 */
	private long customerId;
	
	/**
	 * 现金
	 */
	private BigDecimal cash;
	
	/**
	 *现金账户状态
	 */
	private int cashStatus;
	
	private long cuId;
	
	/**
	 * 资金密码
	 */
	private String userpwd;

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public BigDecimal getCash() {
		return cash;
	}

	public void setCash(BigDecimal cash) {
		this.cash = cash;
	}

	public int getCashStatus() {
		return cashStatus;
	}

	public void setCashStatus(int cashStatus) {
		this.cashStatus = cashStatus;
	}

	public long getCuId() {
		return cuId;
	}

	public void setCuId(long cuId) {
		this.cuId = cuId;
	}

	public String getUserpwd() {
		return userpwd;
	}

	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}
	
}
