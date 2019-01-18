package com.lottery.bean.entity.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用户可访问URL
 * @author JEFF
 *
 */
public class CustomerUrlVO extends GenericEntityVO{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8232857598273808833L;

	/**
	 * 用户ID
	 */
	private long customerId;
	
	/**
	 * url
	 */
	private String url;
	
	/**
	 * 状态
	 * 0:不可以用   1:可以用
	 */
	private String urlStatus;
	
	private String remark;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrlStatus() {
		return urlStatus;
	}

	public void setUrlStatus(String urlStatus) {
		this.urlStatus = urlStatus;
	}
	
}
