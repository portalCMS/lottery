package com.lottery.bean.entity.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用户绑定银行卡表
 * @author JEFF
 *
 */
public class CustomerBindCardVO extends GenericEntityVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4834431720207450699L;

	/**
	 * 用户ID
	 */
	private long customerId;
	
	/**
	 * 银行卡ID
	 */
	private long bankcardId;
	
	/**
	 * 状态
	 */
	private int status;
	
	/**
	 * 是否可继承
	 */
	private int extendsStatus;
	

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public long getBankcardId() {
		return bankcardId;
	}

	public void setBankcardId(long bankcardId) {
		this.bankcardId = bankcardId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getExtendsStatus() {
		return extendsStatus;
	}

	public void setExtendsStatus(int extendsStatus) {
		this.extendsStatus = extendsStatus;
	}
}
