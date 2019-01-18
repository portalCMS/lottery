package com.lottery.bean.entity.vo;


public class SourceLinkVO extends GenericEntityVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2004421406275876852L;
	//号源名称
	private String sourceName;
	//状态
	private int status;
	//号源链接
	private String sourceLink;
	//号源级别
	private int sourceLevel;
	//备注
	private String remark;
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getSourceLink() {
		return sourceLink;
	}
	public void setSourceLink(String sourceLink) {
		this.sourceLink = sourceLink;
	}
	public int getSourceLevel() {
		return sourceLevel;
	}
	public void setSourceLevel(int sourceLevel) {
		this.sourceLevel = sourceLevel;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}
