package com.lottery.bean.entity.vo;


public class DomainUrlVO extends GenericEntityVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4713964475844260896L;

	private String url;
	
	private int status;
	
	private String remark;

	private int bindcount;
	
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

	public int getBindcount() {
		return bindcount;
	}

	public void setBindcount(int bindcount) {
		this.bindcount = bindcount;
	}
	
	
}
