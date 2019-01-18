package com.lottery.api.entity;

import java.io.Serializable;

public class SendMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8401205488884402735L;
	private String key;
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	private String platid;
	private SendData data;
	private String errorCode;
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getPlatid() {
		return platid;
	}

	public void setPlatid(String platid) {
		this.platid = platid;
	}

	public SendData getData() {
		return data;
	}

	public void setData(SendData data) {
		this.data = data;
	}

}
