package com.lottery.bean.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="t_bank_manage")
public class BankManage extends GenericEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8247165295008442766L;

	@Column(name="bank_name")
	private String name;
	
	@Column(name="bank_url")
	private String url;
	
	@Column(name="bank_money_max")
	private BigDecimal moneyMax;
	
	@Column(name="bank_money_min")
	private BigDecimal moneyMin;
	
	@Column(name="bank_cancel_time")
	private int cancelTime;
	
	@Column(name="bank_describe")
	private String describe;
	
	@Column(name="bank_ps")
	private String ps;
	
	@Column(name="bank_ps_status")
	private int psStatus;
	
	@Column(name="bank_add")
	private int add;
	
	@Column(name="bank_out")
	private int out;
	
	@Column(name="bank_bind")
	private int bind;

	@Column(name="bank_status")
	private int status;
	
	@Column(name="bank_ps_num")
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

	public int getPsStatus() {
		return psStatus;
	}

	public void setPsStatus(int psStatus) {
		this.psStatus = psStatus;
	}

	public int getPsNum() {
		return psNum;
	}

	public void setPsNum(int psNum) {
		this.psNum = psNum;
	}

	@Override
	public String toString() {
		return "BankManage [name=" + name + ", url=" + url + ", moneyMax="
				+ moneyMax + ", moneyMin=" + moneyMin + ", cancelTime="
				+ cancelTime + ", describe=" + describe + ", ps=" + ps
				+ ", psStatus=" + psStatus + ", add=" + add + ", out=" + out
				+ ", bind=" + bind + ", status=" + status + ", psNum=" + psNum
				+ ", getId()=" + getId() + ", getCreateTime()="
				+ getCreateTime() + ", getCreateUser()=" + getCreateUser()
				+ ", getUpdateTime()=" + getUpdateTime() + ", getUpdateUser()="
				+ getUpdateUser() + ", getVersion()=" + getVersion() + "]";
	}
	
}
