package com.lottery.bean.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 订单统计。
 * 
 * @author sunshine
 * 
 */
@Entity
@Table(name = "t_customer_order_sta")
public class CustomerOrderSta extends GenericEntity {
	private static final long serialVersionUID = 1L;
	/**
	 * 用户ID
	 */
	@Column(name = "customer_id")
	private Long customerId;
	/**
	 * 总投注金额
	 */
	@Column(name = "total_amount")
	private BigDecimal totalTetAmount;
	/**
	 * 返点总额
	 */
	@Column(name = "rebate")
	private BigDecimal rebateAmount;
	
	/**
	 * 下级返点
	 */
	@Column(name="l_rebate")
	private BigDecimal lRebateAmount;
	/**
	 * 中奖总额
	 */
	@Column(name = "win_amount")
	private BigDecimal winAmount;

	@Column(name = "rsvdc1")
	private Integer rsvdc1;

	@Column(name = "rsvdc2")
	private Integer rsvdc2;

	@Column(name = "rsvdc3")
	private BigDecimal rsvdc3 = BigDecimal.ZERO;

	@Column(name = "rsvdc4")
	private BigDecimal rsvdc4 = BigDecimal.ZERO;
	@Column(name = "rsvst1")
	private String rsvst1;
	@Column(name = "rsvst2")
	private String rsvst2;

	public BigDecimal getTotalTetAmount() {
		return totalTetAmount;
	}

	public void setTotalTetAmount(BigDecimal totalTetAmount) {
		this.totalTetAmount = totalTetAmount;
	}

	public BigDecimal getRebateAmount() {
		return rebateAmount;
	}

	public void setRebateAmount(BigDecimal rebateAmount) {
		this.rebateAmount = rebateAmount;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public BigDecimal getlRebateAmount() {
		return lRebateAmount;
	}

	public void setlRebateAmount(BigDecimal lRebateAmount) {
		this.lRebateAmount = lRebateAmount;
	}

	public BigDecimal getWinAmount() {
		return winAmount;
	}

	public void setWinAmount(BigDecimal winAmount) {
		this.winAmount = winAmount;
	}

	public Integer getRsvdc1() {
		return rsvdc1;
	}

	public void setRsvdc1(Integer rsvdc1) {
		this.rsvdc1 = rsvdc1;
	}

	public Integer getRsvdc2() {
		return rsvdc2;
	}

	public void setRsvdc2(Integer rsvdc2) {
		this.rsvdc2 = rsvdc2;
	}

	public BigDecimal getRsvdc3() {
		return rsvdc3;
	}

	public void setRsvdc3(BigDecimal rsvdc3) {
		this.rsvdc3 = rsvdc3;
	}

	public BigDecimal getRsvdc4() {
		return rsvdc4;
	}

	public void setRsvdc4(BigDecimal rsvdc4) {
		this.rsvdc4 = rsvdc4;
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
}
