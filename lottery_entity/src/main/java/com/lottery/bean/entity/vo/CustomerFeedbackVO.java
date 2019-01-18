package com.lottery.bean.entity.vo;


public class CustomerFeedbackVO extends GenericEntityVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6601027204661181249L;

	/**
	 * 类型名称
	 */
	private String typeName;
	
	/**
	 * 页面名称
	 */
	private String pageName;
	
	/**
	 * 描述
	 */
	private String dsce;
	
	/**
	 * 处理状态
	 */
	private int status;
	
	private String picCode;

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
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

	public String getPicCode() {
		return picCode;
	}

	public void setPicCode(String picCode) {
		this.picCode = picCode;
	}
}
