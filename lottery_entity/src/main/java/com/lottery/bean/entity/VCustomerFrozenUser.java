package com.lottery.bean.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name="v_customer_frozen_user")
public class VCustomerFrozenUser{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8744928199493081415L;

	@Id
	@Column(name="userId")
	private long userId;
	
	@Column(name="customer_name")
	private String customerName;
	
	@Column(name="customer_level")
	private int customerLevel; 
	
	@Column(name="lowercount")
	private int lowercount;
	
	@Column(name="cash")
	private BigDecimal cash;
	
	@Column(name="frozen_cash")
	private BigDecimal frozenCash;
	
	@Column(name="cashId")
	private long cashId;
	
	@Column(name="create_time")
	private Date createTime;
	
	@Column(name="update_time")
	private Date updateTime;
	

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "VCustomerFrozenUser [userId=" + userId + ", customerName="
				+ customerName + ", customerLevel=" + customerLevel
				+ ", lowercount=" + lowercount + ", cash=" + cash
				+ ", frozenCash=" + frozenCash + ", cashId=" + cashId
				+ ", createTime=" + createTime + ", updateTime=" + updateTime
				+ "]";
	}

}
