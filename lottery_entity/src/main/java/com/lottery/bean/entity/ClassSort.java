package com.lottery.bean.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="t_class_sort")
public class ClassSort extends GenericEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5154235202832101274L;

	/**
	 * 文章大类code
	 */
	@Column(name="type")
	private String type;
	
	/**
	 * 小类名称
	 */
	@Column(name="datail_name")
	private String datailName;
	
	/**
	 * 状态
	 */
	@Column(name="status")
	private int status;
	
	/**
	 * 文章小分类code
	 */
	@Column(name="detail_type")
	private String detailType;

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

	@Override
	public String toString() {
		return "ClassSort [type=" + type + ", datailName=" + datailName
				+ ", status=" + status + ", detailType=" + detailType
				+ ", getId()=" + getId() + ", getCreateTime()="
				+ getCreateTime() + ", getCreateUser()=" + getCreateUser()
				+ ", getUpdateTime()=" + getUpdateTime() + ", getUpdateUser()="
				+ getUpdateUser() + ", getVersion()=" + getVersion() + "]";
	}
}
