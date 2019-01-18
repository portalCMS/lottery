package com.lottery.bean.entity.vo;

import javax.persistence.Column;


public class ArticleManageVO extends GenericEntityVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -784208377065746098L;

	private String type;
	
	private String detailType;
	
	private String title;
	
	private String url;
	
	private String content;
	
	private String imgSrc1;
	
	private String imgSrc2;
	
	private String imgSrc3;
	
	private int topMark;
	
	private int keyMark;

	private int applaud;
	
	private int oppose;
	
	private int status;
	
	private String detailTypeName;
	
	private String isApplaud;
	
	private String isOppose;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDetailType() {
		return detailType;
	}

	public void setDetailType(String detailType) {
		this.detailType = detailType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImgSrc1() {
		return imgSrc1;
	}

	public void setImgSrc1(String imgSrc1) {
		this.imgSrc1 = imgSrc1;
	}

	public String getImgSrc2() {
		return imgSrc2;
	}

	public void setImgSrc2(String imgSrc2) {
		this.imgSrc2 = imgSrc2;
	}

	public String getImgSrc3() {
		return imgSrc3;
	}

	public void setImgSrc3(String imgSrc3) {
		this.imgSrc3 = imgSrc3;
	}

	public int getTopMark() {
		return topMark;
	}

	public void setTopMark(int topMark) {
		this.topMark = topMark;
	}

	public int getKeyMark() {
		return keyMark;
	}

	public void setKeyMark(int keyMark) {
		this.keyMark = keyMark;
	}

	public int getApplaud() {
		return applaud;
	}

	public void setApplaud(int applaud) {
		this.applaud = applaud;
	}

	public int getOppose() {
		return oppose;
	}

	public void setOppose(int oppose) {
		this.oppose = oppose;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDetailTypeName() {
		return detailTypeName;
	}

	public void setDetailTypeName(String detailTypeName) {
		this.detailTypeName = detailTypeName;
	}

	public String getIsApplaud() {
		return isApplaud;
	}

	public void setIsApplaud(String isApplaud) {
		this.isApplaud = isApplaud;
	}

	public String getIsOppose() {
		return isOppose;
	}

	public void setIsOppose(String isOppose) {
		this.isOppose = isOppose;
	}
}
