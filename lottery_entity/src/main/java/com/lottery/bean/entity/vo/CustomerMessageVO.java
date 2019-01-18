package com.lottery.bean.entity.vo;

public class CustomerMessageVO extends GenericEntityVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3785171781702622836L;

	private long refUserId;
	
	private String refUserName;
	
	private long toUserId;
	
	private String toUserName;
	
	private String message;
	
	private Integer status;
	
	private String title;
	
	/**
	 * 是否包含下级
	 */
	private String msgType;

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

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
}
