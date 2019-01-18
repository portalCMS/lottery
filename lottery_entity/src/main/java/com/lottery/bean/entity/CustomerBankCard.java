package com.lottery.bean.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 用户银行卡表
 * @author Jeff
 */
@Entity
@Table(name="t_customer_bank_card")
public class CustomerBankCard extends GenericEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8792093908285386434L;

	/**
	 * 用户ID
	 */
	@Column(name="customer_id")
	private long customerId;
	
	/**
	 * 银行Id
	 */
	@Column(name="bank_id")
	private long bankId;
	
	/**
	 * 银行名称
	 */
	@Column(name="bank_name")
	private String bankName;
	
	/**
	 * 开会地址
	 */
	@Column(name="bankcard_address")
	private String bankcardAddress;
	
	/**
	 * 支行名称
	 */
	@Column(name="brance_bank_name")
	private String branceBankName;
	
	/**
	 * 开户姓名
	 */
	@Column(name="opencard_name")
	private String opencardName;
	
	/**
	 * 银行卡号
	 */
	@Column(name="card_no")
	private String cardNo;
	
	/**
	 * 银行卡状态
	 */
	@Column(name="bankcard_status")
	private int bankcardStatus;
	
	/**
	 * 备注
	 */
	@Column(name="remark")
	private String remark;
	
	@Column(name="card_inventory_id")
	private Long cardInventoryId;
	
	@Column(name="card_level")
	private Integer cardLevel;
	
	/**
	 * 关联的银行
	 * @return
	 */
	@Transient
	private BankManage bank;
	
	@Transient
	private List<CustomerUser> bindCardUsers;
	
	
	public List<CustomerUser> getBindCardUsers() {
		return bindCardUsers;
	}

	public void setBindCardUsers(List<CustomerUser> bindCardUsers) {
		this.bindCardUsers = bindCardUsers;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public Long getCardInventoryId() {
		return cardInventoryId;
	}

	public void setCardInventoryId(Long cardInventoryId) {
		this.cardInventoryId = cardInventoryId;
	}

	public Integer getCardLevel() {
		return cardLevel;
	}

	public void setCardLevel(Integer cardLevel) {
		this.cardLevel = cardLevel;
	} 

	public String getBankcardAddress() {
		return bankcardAddress;
	}

	public void setBankcardAddress(String bankcardAddress) {
		this.bankcardAddress = bankcardAddress;
	}

	

	public long getBankId() {
		return bankId;
	}

	public void setBankId(long bankId) {
		this.bankId = bankId;
	}

	public String getBranceBankName() {
		return branceBankName;
	}

	public void setBranceBankName(String branceBankName) {
		this.branceBankName = branceBankName;
	}

	public String getOpencardName() {
		return opencardName;
	}

	public void setOpencardName(String opencardName) {
		this.opencardName = opencardName;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public int getBankcardStatus() {
		return bankcardStatus;
	}

	public void setBankcardStatus(int bankcardStatus) {
		this.bankcardStatus = bankcardStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public BankManage getBank() {
		return bank;
	}

	public void setBank(BankManage bank) {
		this.bank = bank;
	}

	@Override
	public String toString() {
		return "CustomerBankCard [customerId=" + customerId + ", bankId="
				+ bankId + ", bankName=" + bankName + ", bankcardAddress="
				+ bankcardAddress + ", branceBankName=" + branceBankName
				+ ", opencardName=" + opencardName + ", cardNo=" + cardNo
				+ ", bankcardStatus=" + bankcardStatus + ", remark=" + remark
				+ ", bank=" + bank + ", getId()=" + getId()
				+ ", getCreateTime()=" + getCreateTime() + ", getCreateUser()="
				+ getCreateUser() + ", getUpdateTime()=" + getUpdateTime()
				+ ", getUpdateUser()=" + getUpdateUser() + ", getVersion()="
				+ getVersion() + ", toString()=" + super.toString() + "]";
	}
	
}
