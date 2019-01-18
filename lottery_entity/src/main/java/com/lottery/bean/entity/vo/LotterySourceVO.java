package com.lottery.bean.entity.vo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author CW-HP9
 *
 */
public class LotterySourceVO extends GenericEntityVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2004421406275876852L;
	
	private String idListStr;
	
	private String statusListStr;
	
	private String newSourceNames;
	
	private String newSourceLinks;
	
	private String lotteryCode;
	
	private String lotteryGroup;
	
	private int countSourceSize;
	
	private String sourceLevelStrs;

	public String getSourceLevelStrs() {
		return sourceLevelStrs;
	}

	public void setSourceLevelStrs(String sourceLevelStrs) {
		this.sourceLevelStrs = sourceLevelStrs;
	}

	public String getIdListStr() {
		return idListStr;
	}

	public void setIdListStr(String idListStr) {
		this.idListStr = idListStr;
	}

	public String getStatusListStr() {
		return statusListStr;
	}

	public void setStatusListStr(String statusListStr) {
		this.statusListStr = statusListStr;
	}

	public String getNewSourceNames() {
		return newSourceNames;
	}

	public void setNewSourceNames(String newSourceNames) {
		this.newSourceNames = newSourceNames;
	}

	public String getNewSourceLinks() {
		return newSourceLinks;
	}

	public void setNewSourceLinks(String newSourceLinks) {
		this.newSourceLinks = newSourceLinks;
	}

	public String getLotteryCode() {
		return lotteryCode;
	}

	public void setLotteryCode(String lotteryCode) {
		this.lotteryCode = lotteryCode;
	}

	public String getLotteryGroup() {
		return lotteryGroup;
	}

	public void setLotteryGroup(String lotteryGroup) {
		this.lotteryGroup = lotteryGroup;
	}

	public int getCountSourceSize() {
		return countSourceSize;
	}

	public void setCountSourceSize(int countSourceSize) {
		this.countSourceSize = countSourceSize;
	}
	
	
	
	
}
