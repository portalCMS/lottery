package com.lottery.bean.entity.vo;


public class AdvertisingListVO extends GenericEntityVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String regionCode;
	
	private int index;
	
	private String url;
	
	private String alt;
	
	private String openType;

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	public String getOpenType() {
		return openType;
	}

	public void setOpenType(String openType) {
		this.openType = openType;
	}
	
}
