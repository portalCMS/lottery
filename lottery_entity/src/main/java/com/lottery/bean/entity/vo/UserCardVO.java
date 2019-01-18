package com.lottery.bean.entity.vo;


public class UserCardVO extends GenericEntityVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1702046769329877139L;

	private long bankId;
	
	private long customerId;
	
	private String address;
	
	private String branchBankName;
	
	private String openCardName;
	
	private String cardNo;
	
	private int status;

	private String moneyPwd;
	
	private String cashAmount;
	
	private String onlineAmount;
	
	private String payCode;
	
	private String bankCode;
	
	/**
	 * 快速充值通道选择
	 */
	private String group;

	public String getOnlineAmount() {
		return onlineAmount;
	}

	public void setOnlineAmount(String onlineAmount) {
		this.onlineAmount = onlineAmount;
	}

	public String getPayCode() {
		return payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

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

	public String getMoneyPwd() {
		return moneyPwd;
	}

	public void setMoneyPwd(String moneyPwd) {
		this.moneyPwd = moneyPwd;
	}

	public String getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(String cashAmount) {
		this.cashAmount = cashAmount;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}
	
}
