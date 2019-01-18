package com.lottery.api.entity;

import java.io.Serializable;

public class ReadMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4635240668946131461L;
	private String key;
	private String platid;
	private ReadData data;
	
	public String getPlatid() {
		return platid;
	}

	public void setPlatid(String platid) {
		this.platid = platid;
	}

	public ReadData getData() {
		return data;
	}

	public void setData(ReadData data) {
		this.data = data;
	}
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
}
