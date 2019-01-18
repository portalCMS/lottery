package com.lottery.bean.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 广告位区域
 * @author CW-HP7
 *
 */
@Entity
@Table(name="t_advertising_region")
public class AdvertisingRegion extends GenericEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="pageName")
	private String pageName;
	
	@Column(name="region_code")
	private String regionCode;
	
	@Column(name="count")
	private int count;
	
	@Column(name="size")
	private String size;
	
	@Column(name="dsce")
	private String dsce;
	
	@Column(name="status")
	private int status;

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getDsce() {
		return dsce;
	}

	public void setDsce(String dsce) {
		this.dsce = dsce;
	}
	
}
