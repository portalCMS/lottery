package com.lottery.bean.entity.vo;

import java.math.BigDecimal;



/**
 * 报表vo
 * @author JEFF
 *
 */
public class ReportVO extends GenericEntityVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5704510826067220652L;

	private String title;
	
	private String startTime;
	
	private String endTime;
	
	private String saveFileName;
	
	private String downLoadFileName;
	
	private String rsvst1;
	
	private int rsvdc1;
	
	private BigDecimal rsvdc2;
	
	private Long rsvdc3;
	
	private String uname;
	
	private long uid = 0;
	
	private String field;
	
	private String sortType;

	public Long getRsvdc3() {
		return rsvdc3;
	}

	public void setRsvdc3(Long rsvdc3) {
		this.rsvdc3 = rsvdc3;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getSaveFileName() {
		return saveFileName;
	}

	public void setSaveFileName(String saveFileName) {
		this.saveFileName = saveFileName;
	}

	public String getDownLoadFileName() {
		return downLoadFileName;
	}

	public void setDownLoadFileName(String downLoadFileName) {
		this.downLoadFileName = downLoadFileName;
	}

	public String getRsvst1() {
		return rsvst1;
	}

	public void setRsvst1(String rsvst1) {
		this.rsvst1 = rsvst1;
	}

	public int getRsvdc1() {
		return rsvdc1;
	}

	public void setRsvdc1(int rsvdc1) {
		this.rsvdc1 = rsvdc1;
	}

	public BigDecimal getRsvdc2() {
		return rsvdc2;
	}

	public void setRsvdc2(BigDecimal rsvdc2) {
		this.rsvdc2 = rsvdc2;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}	
}
