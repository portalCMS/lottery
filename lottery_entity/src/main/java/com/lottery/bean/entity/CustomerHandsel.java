package com.lottery.bean.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用户彩金账户
 * @author jeff
 *
 */
@Entity
@Table(name="t_customer_handsel")
public class CustomerHandsel extends GenericEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1447685719512653575L;

	/**
	 * 用户类型
	 * @author jeff
	 */
	@Column(name="customer_id")
	private long customerId;
	
	/**
	 * 彩种代码
	 */
	@Column(name="lottey_code")
	private int lotteyCode;
	
	/**
	 * 彩金
	 */
	@Column(name="handsel_cash")
	private int handselCash;
	
	/**
	 * 彩金账户状态
	 */
	@Column(name="handsel_status")
	private int handselStatus;

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public int getLotteyCode() {
		return lotteyCode;
	}

	public void setLotteyCode(int lotteyCode) {
		this.lotteyCode = lotteyCode;
	}

	public int getHandselCash() {
		return handselCash;
	}

	public void setHandselCash(int handselCash) {
		this.handselCash = handselCash;
	}

	public int getHandselStatus() {
		return handselStatus;
	}

	public void setHandselStatus(int handselStatus) {
		this.handselStatus = handselStatus;
	}

	@Override
	public String toString() {
		return "CustomerHandsel [customerId=" + customerId + ", lotteyCode="
				+ lotteyCode + ", handselCash=" + handselCash
				+ ", handselStatus=" + handselStatus + ", getId()=" + getId()
				+ ", getCreateTime()=" + getCreateTime() + ", getCreateUser()="
				+ getCreateUser() + ", getUpdateTime()=" + getUpdateTime()
				+ ", getUpdateUser()=" + getUpdateUser() + ", getVersion()="
				+ getVersion() + "]";
	}
	
}
