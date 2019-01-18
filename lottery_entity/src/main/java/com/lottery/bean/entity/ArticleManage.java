package com.lottery.bean.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name="t_article_manage")
public class ArticleManage extends GenericEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2951866874563553377L;

	@Column(name="type")
	private String type;
	
	@Column(name="detail_type")
	private String detailType;
	
	@Column(name="title")
	private String title;
	
	@Column(name="url")
	private String url;
	
	@Lob
	@Column(name="content",columnDefinition = "TEXT", length = 65535)
	private String content;
	
	@Column(name="img_src1")
	private String imgSrc1;
	
	@Column(name="img_src2")
	private String imgSrc2;
	
	@Column(name="img_src3")
	private String imgSrc3;
	
	@Column(name="top_mark")
	private int topMark;
	
	@Column(name="key_mark")
	private int keyMark;
	
	@Column(name="applaud")
	private int applaud;
	
	@Column(name="oppose")
	private int oppose;
	
	@Column(name="status")
	private int status;

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

	@Override
	public String toString() {
		return "ArticleManage [type=" + type + ", detailType=" + detailType
				+ ", title=" + title + ", url=" + url + ", content=" + content
				+ ", imgSrc1=" + imgSrc1 + ", imgSrc2=" + imgSrc2
				+ ", imgSrc3=" + imgSrc3 + ", topMark=" + topMark
				+ ", keyMark=" + keyMark + ", applaud=" + applaud + ", oppose="
				+ oppose + ", status=" + status + ", getId()=" + getId()
				+ ", getCreateTime()=" + getCreateTime() + ", getCreateUser()="
				+ getCreateUser() + ", getUpdateTime()=" + getUpdateTime()
				+ ", getUpdateUser()=" + getUpdateUser() + ", getVersion()="
				+ getVersion() + "]";
	}
}
