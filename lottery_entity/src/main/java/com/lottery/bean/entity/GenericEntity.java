package com.lottery.bean.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import com.xl.lottery.util.DateUtil;

@MappedSuperclass
public abstract class GenericEntity implements Serializable{



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	
	@Column(name="create_Time")
	private Date createTime;
	
	@Column(name="create_User")
	private String createUser;
	
	@Column(name="update_Time")
	private Date updateTime;
	
	@Column(name="update_User")
	private String updateUser;
	
	//@Column(name="version")
	@Version
	private int version;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	
	public void addInit(String username){
		this.createTime = new Date();
		this.updateTime = new Date();
		this.updateUser = username;
		this.createUser = username;
	}
	
	public void updateInit(String username){
		this.updateTime = new Date();
		this.updateUser = username;
	}
}
