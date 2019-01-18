package com.lottery.bean.entity.vo;


public class ClassSortVO extends GenericEntityVO{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1737723604225435450L;

	/**
	 * 文章大类code
	 */
	private String type;
	
	/**
	 * 小类名称
	 */
	private String datailName;
	
	/**
	 * 状态
	 */
	private int status;
	
	/**
	 * 文章小分类code
	 */
	private String detailType;
	
	private int count;
	
	private String statusStr;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDetailType() {
		return detailType;
	}

	public void setDetailType(String detailType) {
		this.detailType = detailType;
	}

	public String getDatailName() {
		return datailName;
	}

	public void setDatailName(String datailName) {
		this.datailName = datailName;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}
}
