package com.lottery.bean.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 订单表
 * 
 */
@Entity
@Table(name = "t_customer_order")
public class CustomerOrder extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7103933848574960482L;

	/**
	 * 用户ID
	 */
	@Column(name = "customer_id")
	private Long customerId;

	/**
	 * 订单编码
	 */
	@Column(name = "order_number")
	private String orderNumber;

	/**
	 * 订单时间
	 */
	@Column(name = "order_time")
	private Date orderTime;

	/**
	 * 订单金额
	 */
	@Column(name = "order_amount")
	private BigDecimal orderAmount = BigDecimal.ZERO;

	/**
	 * 实收金额
	 */
	@Column(name = "receive_amount")
	private BigDecimal receiveAmount = BigDecimal.ZERO;

	/**
	 * 现金金额
	 */
	@Column(name = "cash_amount")
	private BigDecimal cashAmount = BigDecimal.ZERO;

	/**
	 * 彩金金额
	 */
	@Column(name = "handsel_amount")
	private BigDecimal handselAmount = BigDecimal.ZERO;

	/**
	 * 订单类型
	 */
	@Column(name = "order_type")
	private Integer orderType = 0;

	/**
	 * 订单类型明细
	 */
	@Column(name = "order_detail_type")
	private Integer orderDetailType = 0;

	/**
	 * 订单状态
	 */
	@Column(name = "order_status")
	private Integer orderStatus = 0;

	/**
	 * 备注
	 * 
	 * @return
	 */
	@Column(name = "remark")
	private String remark;
	/**
	 * 来源id
	 */
	@Column(name = "source_id")
	private Long sourceId;

	/**
	 * 关联id
	 * 
	 * @return
	 */
	@Column(name = "reference_id")
	private Long referenceId;

	/**
	 * 手续费字段
	 */
	@Column(name = "transfer_amount")
	private BigDecimal transferAmount = BigDecimal.ZERO;
	/**
	 * 用户充值或提款订单的过期时间，超过该时间且处理状态为处理中的，则页面显示订单过期。
	 */
	@Column(name = "cancel_time")
	private Date cancelTime;

	/**
	 * 退款金额
	 */
	@Column(name = "return_amount")
	private BigDecimal returnAmount = BigDecimal.ZERO;

	/**
	 * 金额来自或去向给谁
	 */
	@Column(name = "from_customerId")
	private Long fromCustomerId;

	/**
	 * 是否终止追号
	 */
	@Column(name = "awardStop")
	private String awardStop;

	// ///////备用字段映射
	@Column(name = "rsvdc1")
	private Long rsvdc1;

	@Column(name = "rsvdc2")
	private Long rsvdc2;

	@Column(name = "rsvdc3")
	private Long rsvdc3;

	/**
	 * 返点
	 */
	@Column(name = "rsvdc4")
	private BigDecimal rsvdc4 = BigDecimal.ZERO;

	@Column(name = "rsvdc5")
	private BigDecimal rsvdc5 = BigDecimal.ZERO;

	/**
	 * 来源订单编号
	 */
	@Column(name = "rsvst1")
	private String rsvst1;

	/**
	 * 彩种
	 */
	@Column(name = "rsvst2")
	private String rsvst2;

	/**
	 * 期号
	 */
	@Column(name = "rsvst3")
	private String rsvst3;

	@Column(name = "rsvst4")
	private String rsvst4;

	// 追号订单，放的是所有追的期号，及倍数的字符串。
	@Column(name = "rsvst5")
	private String rsvst5;

	// 账户余额
	@Column(name = "account_balance")
	private BigDecimal accountBalance;

	/**
	 * 临时属性，关联的系统分配银行卡
	 */
	@Transient
	private CustomerBankCard card;

	/**
	 * 临时属性，用户
	 */
	@Transient
	private CustomerUser user;

	/**
	 * 临时属性，用户
	 */
	@Transient
	private BankManage bank;

	/**
	 * 临时属性，倍投号码
	 */
	@Transient
	private String bileNum;

	/**
	 * 临时属性，完成金额
	 */
	@Transient
	private BigDecimal finishAmount;

	/**
	 * 临时属性，投注号码
	 */
	@Transient
	private String betNum;

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

	public CustomerBankCard getCard() {
		return card;
	}

	public void setCard(CustomerBankCard card) {
		this.card = card;
	}

	public CustomerUser getUser() {
		return user;
	}

	public void setUser(CustomerUser user) {
		this.user = user;
	}

	public BigDecimal getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(BigDecimal transferAmount) {
		this.transferAmount = transferAmount;
	}

	public Long getSourceId() {
		return sourceId;
	}

	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}

	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public BankManage getBank() {
		return bank;
	}

	public void setBank(BankManage bank) {
		this.bank = bank;
	}

	public BigDecimal getReturnAmount() {
		return returnAmount;
	}

	public void setReturnAmount(BigDecimal returnAmount) {
		this.returnAmount = returnAmount;
	}

	public Long getFromCustomerId() {
		return fromCustomerId;
	}

	public void setFromCustomerId(Long fromCustomerId) {
		this.fromCustomerId = fromCustomerId;
	}

	public String getAwardStop() {
		return awardStop;
	}

	public void setAwardStop(String awardStop) {
		this.awardStop = awardStop;
	}

	@Override
	public String toString() {
		return "CustomerOrder [customerId=" + customerId + ", orderNumber=" + orderNumber + ", orderTime=" + orderTime
				+ ", orderAmount=" + orderAmount + ", receiveAmount=" + receiveAmount + ", cashAmount=" + cashAmount
				+ ", handselAmount=" + handselAmount + ", orderType=" + orderType + ", orderDetailType="
				+ orderDetailType + ", orderStatus=" + orderStatus + ", remark=" + remark + ", sourceId=" + sourceId
				+ ", referenceId=" + referenceId + ", transferAmount=" + transferAmount + ", cancelTime=" + cancelTime
				+ ", returnAmount=" + returnAmount + ", fromCustomerId=" + fromCustomerId + ", awardStop=" + awardStop
				+ ", card=" + card + ", user=" + user + ", bank=" + bank + ", getCustomerId()=" + getCustomerId()
				+ ", getOrderNumber()=" + getOrderNumber() + ", getOrderTime()=" + getOrderTime()
				+ ", getOrderAmount()=" + getOrderAmount() + ", getReceiveAmount()=" + getReceiveAmount()
				+ ", getCashAmount()=" + getCashAmount() + ", getHandselAmount()=" + getHandselAmount()
				+ ", getOrderType()=" + getOrderType() + ", getOrderDetailType()=" + getOrderDetailType()
				+ ", getOrderStatus()=" + getOrderStatus() + ", getRemark()=" + getRemark() + ", getReferenceId()="
				+ getReferenceId() + ", getCard()=" + getCard() + ", getUser()=" + getUser() + ", getTransferAmount()="
				+ getTransferAmount() + ", getSourceId()=" + getSourceId() + ", getCancelTime()=" + getCancelTime()
				+ ", getBank()=" + getBank() + ", getReturnAmount()=" + getReturnAmount() + ", getFromCustomerId()="
				+ getFromCustomerId() + ", getAwardStop()=" + getAwardStop() + "]";
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

	public String getBileNum() {
		return bileNum;
	}

	public void setBileNum(String bileNum) {
		this.bileNum = bileNum;
	}

	public String getBetNum() {
		return betNum;
	}

	public void setBetNum(String betNum) {
		this.betNum = betNum;
	}

	public BigDecimal getFinishAmount() {
		return finishAmount;
	}

	public void setFinishAmount(BigDecimal finishAmount) {
		this.finishAmount = finishAmount;
	}
}
