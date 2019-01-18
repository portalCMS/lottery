package com.lottery.bean.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 字典表
 * @author jeff
 *
 */
@Entity
@Table(name="t_data_dictionary")
public class DataDictionary extends GenericEntity{

	private static final long serialVersionUID = -7101248328313707628L;

	/**
	 * 父id
	 */
	@Column(name="fid")
	private long fid;
	
	/**
	 * 子ID
	 */
	@Column(name="sid")
	private long sid;
	
	/**
	 * 父名称
	 */
	@Column(name="fname")
	private String fname;
	
	/**
	 * 子名称
	 */
	@Column(name="sname")
	private String sname;
	
	@Column(name="status")
	private int status;

	public long getFid() {
		return fid;
	}

	public void setFid(long fid) {
		this.fid = fid;
	}

	public long getSid() {
		return sid;
	}

	public void setSid(long sid) {
		this.sid = sid;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "DataDictionary [fid=" + fid + ", sid=" + sid + ", fname="
				+ fname + ", sname=" + sname + ", status=" + status
				+ ", getId()=" + getId() + ", getCreateTime()="
				+ getCreateTime() + ", getCreateUser()=" + getCreateUser()
				+ ", getUpdateTime()=" + getUpdateTime() + ", getUpdateUser()="
				+ getUpdateUser() + ", getVersion()=" + getVersion() + "]";
	}
	
}
