package com.lottery.bean.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用户现金账户
 * @author jeff
 *
 */
@Entity
@Table(name="t_customer_cash")
public class CustomerCash extends GenericEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7952929816405999733L;

	/**
	 * 用户id
	 * @author jeff
	 */
	@Column(name="customer_id")
	private long customerId;
	
	/**
	 * 现金
	 * @author jeff
	 */
	@Column(name="cash")
	private BigDecimal cash;
	
	/**
	 * 冻结金额
	 * 
	 */
	@Column(name="frozen_cash")
	private BigDecimal frozenCash;
	
	/**
	 *现金账户状态
	 *@author jeff
	 */
	@Column(name="cash_status")
	private int cashStatus;

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

	public BigDecimal getFrozenCash() {
		return frozenCash;
	}

	public void setFrozenCash(BigDecimal frozenCash) {
		this.frozenCash = frozenCash;
	}

	@Override
	public String toString() {
		return "CustomerCash [customerId=" + customerId + ", cash=" + cash
				+ ", frozenCash=" + frozenCash + ", cashStatus=" + cashStatus
				+ ", getId()=" + getId() + ", getCreateTime()="
				+ getCreateTime() + ", getCreateUser()=" + getCreateUser()
				+ ", getUpdateTime()=" + getUpdateTime() + ", getUpdateUser()="
				+ getUpdateUser() + ", getVersion()=" + getVersion() + "]";
	}
	
}
