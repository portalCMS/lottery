package com.lottery.bean.entity.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.lottery.activity.rule.model.BetTempl;
import com.lottery.activity.rule.model.FrcTempl;
import com.lottery.activity.rule.model.LuckTempl;
import com.lottery.activity.rule.model.RegisterTempl;

public class CustomerActivityVO extends GenericEntityVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String pretime;
	
	private String closetime;
	
	private String starttime;
	
	private String endtime;
	
	private String title;
	
	private String picurl;
	
	private String summary;
	
	private String rule;
	
	private String model;
	
	private Integer betMultiple;
	
	private Integer status;
	
	private String type;
	
	private String blackCustomer;
	
	private String blackIp;
	
	private String sourceType;

	private BetTempl betTempl;
	
	private FrcTempl frecTemp;
	
	private RegisterTempl registerTempl;
	
	private List<LuckTempl> luckTempls;
	
	//参数传递
	private long customerId;
	
	private Integer customerCount;
	
	private String customerName;
	
	private String typeName;
	
	private String orderNumber;
	
	private BigDecimal rsvdc1;
	
	private String userType;
	
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getPretime() {
		return pretime;
	}

	public void setPretime(String pretime) {
		this.pretime = pretime;
	}

	public String getClosetime() {
		return closetime;
	}

	public void setClosetime(String closetime) {
		this.closetime = closetime;
	}

	public List<LuckTempl> getLuckTempls() {
		return luckTempls;
	}

	public void setLuckTempls(List<LuckTempl> luckTempls) {
		this.luckTempls = luckTempls;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public BigDecimal getRsvdc1() {
		return rsvdc1;
	}

	public void setRsvdc1(BigDecimal rsvdc1) {
		this.rsvdc1 = rsvdc1;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPicurl() {
		return picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public Integer getBetMultiple() {
		return betMultiple;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBlackCustomer() {
		return blackCustomer;
	}

	public void setBlackCustomer(String blackCustomer) {
		this.blackCustomer = blackCustomer;
	}

	public String getBlackIp() {
		return blackIp;
	}

	public void setBlackIp(String blackIp) {
		this.blackIp = blackIp;
	}

	public BetTempl getBetTempl() {
		return betTempl;
	}

	public void setBetTempl(BetTempl betTempl) {
		this.betTempl = betTempl;
	}

	public FrcTempl getFrecTemp() {
		return frecTemp;
	}

	public void setFrecTemp(FrcTempl frecTemp) {
		this.frecTemp = frecTemp;
	}

	public RegisterTempl getRegisterTempl() {
		return registerTempl;
	}

	public void setRegisterTempl(RegisterTempl registerTempl) {
		this.registerTempl = registerTempl;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public Integer getCustomerCount() {
		return customerCount;
	}

	public void setCustomerCount(Integer customerCount) {
		this.customerCount = customerCount;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public void setBetMultiple(Integer betMultiple) {
		this.betMultiple = betMultiple;
	}
}
