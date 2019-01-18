package com.lottery.bean.entity.vo;

import java.util.List;

import com.lottery.bean.entity.AdvertisingList;


public class AdvertisingRegionVO extends GenericEntityVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String pageName;
	
	private String regionCode;
	
	private int count;
	
	private String size;
	
	private String dsce;

	private int status;
	
	private List<AdvertisingList> advers;
	
	private int settingCont;
	
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<AdvertisingList> getAdvers() {
		return advers;
	}

	public void setAdvers(List<AdvertisingList> advers) {
		this.advers = advers;
	}

	public int getSettingCont() {
		return settingCont;
	}

	public void setSettingCont(int settingCont) {
		this.settingCont = settingCont;
	}
	
}
