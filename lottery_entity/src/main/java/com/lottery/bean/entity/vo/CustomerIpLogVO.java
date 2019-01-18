package com.lottery.bean.entity.vo;


public class CustomerIpLogVO extends GenericEntityVO{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long customerId;
	
	private String ip;
	
	private String type;

	private String ipAddress;
	
	private String uname;
	
	private String cpuid;
	
	private String diskid;
	/** 银行卡号 */
	private String openCardName;
	/** 卡号 */
	private String cardNo;
	
	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getCpuid() {
		return cpuid;
	}

	public void setCpuid(String cpuid) {
		this.cpuid = cpuid;
	}

	public String getDiskid() {
		return diskid;
	}

	public void setDiskid(String diskid) {
		this.diskid = diskid;
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
}
