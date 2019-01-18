package com.lottery.bean.entity.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 字典表
 * @author jeff
 *
 */
public class DataDictionaryVO extends GenericEntityVO{

	private static final long serialVersionUID = -7101248328313707628L;

	/**
	 * 父id
	 */
	private long fid;
	
	/**
	 * 子ID
	 */
	private long sid;
	
	/**
	 * 父名称
	 */
	private String fname;
	
	/**
	 * 子名称
	 */
	private String sname;
	
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
	
}
