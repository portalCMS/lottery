package com.lottery.bean.entity.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用户彩金账户
 * @author jeff
 *
 */
public class CustomerHandselVO extends GenericEntityVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1447685719512653575L;

	/**
	 * 用户类型
	 * @author jeff
	 */
	private long customerId;
	
	/**
	 * 彩种代码
	 */
	private int lotteyCode;
	
	/**
	 * 彩金
	 */
	private int handselCash;
	
	/**
	 * 彩金账户状态
	 */
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
	
}
