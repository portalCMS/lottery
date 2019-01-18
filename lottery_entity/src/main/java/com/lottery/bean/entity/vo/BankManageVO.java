package com.lottery.bean.entity.vo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

public class BankManageVO extends GenericEntityVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8247165295008442766L;

	private String name;
	
	private String url;
	
	private BigDecimal moneyMax;
	
	private BigDecimal moneyMin;
	
	private int cancelTime;
	
	private String describe;
	
	private String ps;
	
	private int psStatus;
	
	private int add;
	
	private int out;
	
	private int bind;

	private int status;
	
	private String pwd;
	
	private String code;
	
	private String pickey;
	
	private int psNum;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public BigDecimal getMoneyMax() {
		return moneyMax;
	}

	public void setMoneyMax(BigDecimal moneyMax) {
		this.moneyMax = moneyMax;
	}

	public BigDecimal getMoneyMin() {
		return moneyMin;
	}

	public void setMoneyMin(BigDecimal moneyMin) {
		this.moneyMin = moneyMin;
	}

	public int getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(int cancelTime) {
		this.cancelTime = cancelTime;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getPs() {
		return ps;
	}

	public void setPs(String ps) {
		this.ps = ps;
	}

	public int getAdd() {
		return add;
	}

	public void setAdd(int add) {
		this.add = add;
	}

	public int getOut() {
		return out;
	}

	public void setOut(int out) {
		this.out = out;
	}

	public int getBind() {
		return bind;
	}

	public void setBind(int bind) {
		this.bind = bind;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPickey() {
		return pickey;
	}

	public void setPickey(String pickey) {
		this.pickey = pickey;
	}

	public int getPsNum() {
		return psNum;
	}

	public void setPsNum(int psNum) {
		this.psNum = psNum;
	}

	public int getPsStatus() {
		return psStatus;
	}

	public void setPsStatus(int psStatus) {
		this.psStatus = psStatus;
	}
	
}
