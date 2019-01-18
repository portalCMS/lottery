package com.lottery.bean.entity.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单表
 * 
 * @author jeff
 * 
 */
public class CustomerOrderVO extends GenericEntityVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7713924858010138536L;

	/**
	 * 用户ID
	 */
	private long customerId;

	/**
	 * 订单编码
	 */
	private String orderNumber;

	/**
	 * 订单时间
	 */
	private Date orderTime;

	/**
	 * 订单金额
	 */
	private BigDecimal orderAmount;

	/**
	 * 实收金额
	 */
	private BigDecimal receiveAmount;

	/**
	 * 现金金额
	 */
	private BigDecimal cashAmount;

	/**
	 * 彩金金额
	 */
	private BigDecimal handselAmount;

	/**
	 * 订单类型
	 */
	private Integer orderType;

	/**
	 * 订单类型明细
	 */
	private Integer orderDetailType;

	/**
	 * 订单状态
	 */
	private Integer orderStatus;

	/**
	 * 退款金额
	 */
	private BigDecimal returnAmount;

	/**
	 * 账户类型
	 */
	private String accountType;

	/**
	 * 订单类型明细
	 */
	private String orderDetailTypes;

	// ///////备用字段映射
	private Long rsvdc1;

	private Long rsvdc2;

	private Long rsvdc3;

	/**
	 * 返点
	 */
	private BigDecimal rsvdc4;

	private BigDecimal rsvdc5;

	/**
	 * 来源订单编号
	 */
	private String rsvst1;

	/**
	 * 彩种
	 */
	private String rsvst2;

	/**
	 * 期号
	 */
	private String rsvst3;

	private String rsvst4;

	private String rsvst5;

	/**
	 * 金额来自或去向给谁
	 */
	private Long fromCustomerId;

	private String adminPwd;

	private String customerName;

	private String remark;

	private Long sourceId;

	private Long referenceId;

	private String orderTimeBegin;

	private String orderTimeEnd;

	private BigDecimal transferAmount;

	private String picCode;

	private String orderDetailTypeName;

	private String orderTypeName;

	private String orderStatusName;

	private String customerNameOut;

	private String lotteryTypeName;

	private String customerType;
	
	private String fromType;

	// 账户余额
	private BigDecimal accountBalance;
	// 设定时间
	private String setTime;
	// 开始时间
	private String sdate;
	// 结束时间
	private String edate;

	public BigDecimal getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public Integer getOrderDetailType() {
		return orderDetailType;
	}

	public void setOrderDetailType(Integer orderDetailType) {
		this.orderDetailType = orderDetailType;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public BigDecimal getReceiveAmount() {
		return receiveAmount;
	}

	public void setReceiveAmount(BigDecimal receiveAmount) {
		this.receiveAmount = receiveAmount;
	}

	public BigDecimal getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(BigDecimal cashAmount) {
		this.cashAmount = cashAmount;
	}

	public BigDecimal getHandselAmount() {
		return handselAmount;
	}

	public void setHandselAmount(BigDecimal handselAmount) {
		this.handselAmount = handselAmount;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAdminPwd() {
		return adminPwd;
	}

	public void setAdminPwd(String adminPwd) {
		this.adminPwd = adminPwd;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(Long referenceId) {
		this.referenceId = referenceId;
	}

	public String getOrderTimeBegin() {
		return orderTimeBegin;
	}

	public void setOrderTimeBegin(String orderTimeBegin) {
		this.orderTimeBegin = orderTimeBegin;
	}

	public String getOrderTimeEnd() {
		return orderTimeEnd;
	}

	public void setOrderTimeEnd(String orderTimeEnd) {
		this.orderTimeEnd = orderTimeEnd;
	}

	public Long getSourceId() {
		return sourceId;
	}

	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}

	public BigDecimal getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(BigDecimal transferAmount) {
		this.transferAmount = transferAmount;
	}

	public String getPicCode() {
		return picCode;
	}

	public void setPicCode(String picCode) {
		this.picCode = picCode;
	}

	public BigDecimal getReturnAmount() {
		return returnAmount;
	}

	public void setReturnAmount(BigDecimal returnAmount) {
		this.returnAmount = returnAmount;
	}

	public String getOrderDetailTypeName() {
		return orderDetailTypeName;
	}

	public void setOrderDetailTypeName(String orderDetailTypeName) {
		this.orderDetailTypeName = orderDetailTypeName;
	}

	public String getOrderTypeName() {
		return orderTypeName;
	}

	public void setOrderTypeName(String orderTypeName) {
		this.orderTypeName = orderTypeName;
	}

	public String getOrderStatusName() {
		return orderStatusName;
	}

	public void setOrderStatusName(String orderStatusName) {
		this.orderStatusName = orderStatusName;
	}

	public String getCustomerNameOut() {
		return customerNameOut;
	}

	public void setCustomerNameOut(String customerNameOut) {
		this.customerNameOut = customerNameOut;
	}

	public Long getFromCustomerId() {
		return fromCustomerId;
	}

	public void setFromCustomerId(Long fromCustomerId) {
		this.fromCustomerId = fromCustomerId;
	}

	public Long getRsvdc1() {
		return rsvdc1;
	}

	public void setRsvdc1(Long rsvdc1) {
		this.rsvdc1 = rsvdc1;
	}

	public Long getRsvdc2() {
		return rsvdc2;
	}

	public void setRsvdc2(Long rsvdc2) {
		this.rsvdc2 = rsvdc2;
	}

	public Long getRsvdc3() {
		return rsvdc3;
	}

	public void setRsvdc3(Long rsvdc3) {
		this.rsvdc3 = rsvdc3;
	}

	public BigDecimal getRsvdc4() {
		return rsvdc4;
	}

	public void setRsvdc4(BigDecimal rsvdc4) {
		this.rsvdc4 = rsvdc4;
	}

	public BigDecimal getRsvdc5() {
		return rsvdc5;
	}

	public void setRsvdc5(BigDecimal rsvdc5) {
		this.rsvdc5 = rsvdc5;
	}

	public String getRsvst1() {
		return rsvst1;
	}

	public void setRsvst1(String rsvst1) {
		this.rsvst1 = rsvst1;
	}

	public String getRsvst2() {
		return rsvst2;
	}

	public void setRsvst2(String rsvst2) {
		this.rsvst2 = rsvst2;
	}

	public String getRsvst3() {
		return rsvst3;
	}

	public void setRsvst3(String rsvst3) {
		this.rsvst3 = rsvst3;
	}

	public String getRsvst4() {
		return rsvst4;
	}

	public void setRsvst4(String rsvst4) {
		this.rsvst4 = rsvst4;
	}

	public String getRsvst5() {
		return rsvst5;
	}

	public void setRsvst5(String rsvst5) {
		this.rsvst5 = rsvst5;
	}

	public String getLotteryTypeName() {
		return lotteryTypeName;
	}

	public void setLotteryTypeName(String lotteryTypeName) {
		this.lotteryTypeName = lotteryTypeName;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getOrderDetailTypes() {
		return orderDetailTypes;
	}

	public void setOrderDetailTypes(String orderDetailTypes) {
		this.orderDetailTypes = orderDetailTypes;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getSetTime() {
		return setTime;
	}

	public void setSetTime(String setTime) {
		this.setTime = setTime;
	}

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public String getEdate() {
		return edate;
	}

	public void setEdate(String edate) {
		this.edate = edate;
	}

	public String getFromType() {
		return fromType;
	}

	public void setFromType(String fromType) {
		this.fromType = fromType;
	}
}
