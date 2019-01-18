package com.lottery.bean.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="t_domain_url")
public class DomainUrl extends GenericEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4713964475844260896L;

	@Column(name="url")
	private String url;
	
	@Column(name="url_status")
	private int status;
	
	@Column(name="remark")
	private String remark;

	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "DomainUrl [url=" + url + ", status=" + status + ", remark="
				+ remark + ", getId()=" + getId() + ", getCreateTime()="
				+ getCreateTime() + ", getCreateUser()=" + getCreateUser()
				+ ", getUpdateTime()=" + getUpdateTime() + ", getUpdateUser()="
				+ getUpdateUser() + ", getVersion()=" + getVersion() + "]";
	}
	
	
}
