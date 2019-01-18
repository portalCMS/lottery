package com.lottery.bean.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="t_advertising_list")
public class AdvertisingList extends GenericEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="region_code")
	private String regionCode;
	
	@Column(name="img_index")
	private int index;
	
	@Column(name="url")
	private String url;
	
	@Column(name="alt")
	private String alt;
	
	@Column(name="open_type")
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
