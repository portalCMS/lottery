package com.lottery.api.entity;

import java.io.Serializable;



public class StatusMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6648989278462639189L;
	private String key;
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	private String platid;
	private StatusData data;
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

	public StatusData getData() {
		return data;
	}

	public void setData(StatusData data) {
		this.data = data;
	}
}
