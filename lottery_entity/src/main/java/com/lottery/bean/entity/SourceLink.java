package com.lottery.bean.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 号源表
 * @author CW-HP9
 *
 */
@Entity
@Table(name="t_source_link")
public class SourceLink extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2004421406275876852L;
	//号源名称
	@Column(name="source_name")
	private String sourceName;
	//状态
	@Column(name="status")
	private int status;
	//号源链接
	@Column(name="source_link")
	private String sourceLink;
	//号源级别
	@Column(name="source_level")
	private int sourceLevel;
	//备注
	@Column(name="remark")
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
	@Override
	public String toString() {
		return "SourceLink [sourceName=" + sourceName + ", status=" + status
				+ ", sourceLink=" + sourceLink + ", sourceLevel=" + sourceLevel
				+ ", remark=" + remark + ", getId()=" + getId()
				+ ", getCreateTime()=" + getCreateTime() + ", getCreateUser()="
				+ getCreateUser() + ", getUpdateTime()=" + getUpdateTime()
				+ ", getUpdateUser()=" + getUpdateUser() + ", getVersion()="
				+ getVersion() + "]";
	}
	
	
	
}
