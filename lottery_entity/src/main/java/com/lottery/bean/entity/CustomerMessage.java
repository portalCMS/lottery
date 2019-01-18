package com.lottery.bean.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_customer_message")
public class CustomerMessage extends GenericEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6023918239684608560L;

	@Column(name="ref_customer_id")
	private long refUserId;
	
	@Column(name="ref_customer_name")
	private String refUserName;
	
	@Column(name="to_customer_id")
	private long toUserId;
	
	@Column(name="to_customer_name")
	private String toUserName;
	
	@Column(name="message")
	private String message;
	
	@Column(name="status")
	private Integer status;
	
	@Column(name="title")
	private String title;

	public long getRefUserId() {
		return refUserId;
	}

	public void setRefUserId(long refUserId) {
		this.refUserId = refUserId;
	}

	public String getRefUserName() {
		return refUserName;
	}

	public void setRefUserName(String refUserName) {
		this.refUserName = refUserName;
	}

	public long getToUserId() {
		return toUserId;
	}

	public void setToUserId(long toUserId) {
		this.toUserId = toUserId;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}
