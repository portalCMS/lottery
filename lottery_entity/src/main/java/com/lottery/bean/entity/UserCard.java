package com.lottery.bean.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="t_user_card")
public class UserCard extends GenericEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2471950418420555005L;

	@Column(name="bank_id")
	private long bankId;
	
	@Column(name="customer_id")
	private long customerId;
	
	@Column(name="card_address")
	private String address;
	
	@Column(name="branch_bank_name")
	private String branchBankName;
	
	@Column(name="open_card_name")
	private String openCardName;
	
	@Column(name="card_no")
	private String cardNo;
	
	@Column(name="status")
	private int status;
	
	@Transient
	private BankManage bank;

	public long getBankId() {
		return bankId;
	}

	public void setBankId(long bankId) {
		this.bankId = bankId;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBranchBankName() {
		return branchBankName;
	}

	public void setBranchBankName(String branchBankName) {
		this.branchBankName = branchBankName;
	}

	public String getOpenCardName() {
		return openCardName;
	}

	public void setOpenCardName(String openCardName) {
		this.openCardName = openCardName;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public BankManage getBank() {
		return bank;
	}

	public void setBank(BankManage bank) {
		this.bank = bank;
	}

	@Override
	public String toString() {
		return "UserCard [bankId=" + bankId + ", customerId=" + customerId
				+ ", address=" + address + ", branchBankName=" + branchBankName
				+ ", openCardName=" + openCardName + ", cardNo=" + cardNo
				+ ", status=" + status + ", bank=" + bank + ", getId()="
				+ getId() + ", getCreateTime()=" + getCreateTime()
				+ ", getCreateUser()=" + getCreateUser() + ", getUpdateTime()="
				+ getUpdateTime() + ", getUpdateUser()=" + getUpdateUser()
				+ ", getVersion()=" + getVersion() + "]";
	}
	
}
