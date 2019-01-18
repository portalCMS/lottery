package com.lottery.bean.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 用户绑定银行卡表
 * @author JEFF
 *
 */
@Entity
@Table(name="t_customer_bind_card")
public class CustomerBindCard extends GenericEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4834431720207450699L;

	/**
	 * 用户ID
	 */
	@Column(name="customer_id")
	private long customerId;
	
	/**
	 * 银行卡ID
	 */
	@Column(name="bankcard_id")
	private long bankcardId;
	
	/**
	 * 状态
	 */
	@Column(name="status")
	private int status;
	
	/**
	 * 是否可继承
	 */
	@Column(name="extends_status")
	private int extendsStatus;
	
	/**
	 * 关联银行卡
	 */
	@Transient
	private CustomerBankCard bankCard;

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
	
	
	public CustomerBankCard getBankCard() {
		return bankCard;
	}

	public void setBankCard(CustomerBankCard bankCard) {
		this.bankCard = bankCard;
	}

	@Override
	public String toString() {
		return "CustomerBindCard [customerId=" + customerId + ", bankcardId="
				+ bankcardId + ", status=" + status + ", extendsStatus="
				+ extendsStatus + ", bankCard=" + bankCard + ", getId()="
				+ getId() + ", getCreateTime()=" + getCreateTime()
				+ ", getCreateUser()=" + getCreateUser() + ", getUpdateTime()="
				+ getUpdateTime() + ", getUpdateUser()=" + getUpdateUser()
				+ ", getVersion()=" + getVersion() + "]";
	}
	
}
