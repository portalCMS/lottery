package com.lottery.bean.entity.vo;

import java.math.BigDecimal;

import com.xl.lottery.util.DateUtil;

/**
 * 个人订单统计。
 * 
 * @author sunshine
 */
@SuppressWarnings("serial")
public class CustomerOrderStaVo extends GenericEntityVO {
	// 总投注金额
	private BigDecimal totalTetAmount = new BigDecimal(0.00);
	// 返点总额
	private BigDecimal rebateAmount = new BigDecimal(0.00);
	//下级返点
	private BigDecimal lRebateAmount = new BigDecimal(0.00);
	// 实际销售总额
	private BigDecimal saleAmount = new BigDecimal(0.00);
	// 中奖总额
	private BigDecimal winAmount = new BigDecimal(0.00);
	// 总盈亏金额
	private BigDecimal ykAmount = new BigDecimal(0.00);
	
	//日期
	private String day;
	
	private Integer rsvdc1;
	
	private Integer rsvdc2;
	
	private BigDecimal rsvdc3;
	
	private BigDecimal rsvdc4;
	
	private String rsvst1;
	
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

	public BigDecimal getSaleAmount() {
		return saleAmount;
	}

	public void setSaleAmount(BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
	}

	public BigDecimal getWinAmount() {
		return winAmount;
	}

	public void setWinAmount(BigDecimal winAmount) {
		this.winAmount = winAmount;
	}

	public BigDecimal getYkAmount() {
		return ykAmount;
	}

	public void setYkAmount(BigDecimal ykAmount) {
		this.ykAmount = ykAmount;
	}

	public String getDay() {
		day = DateUtil.dateToStr(getCreateTime()).substring(5);
		if(day.indexOf("0")==0){
			day = DateUtil.dateToStr(getCreateTime()).substring(6);
		}
		return day;
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

	public BigDecimal getlRebateAmount() {
		return lRebateAmount;
	}

	public void setlRebateAmount(BigDecimal lRebateAmount) {
		this.lRebateAmount = lRebateAmount;
	}

	public void setDay(String day) {
		this.day = day;
	}
}
