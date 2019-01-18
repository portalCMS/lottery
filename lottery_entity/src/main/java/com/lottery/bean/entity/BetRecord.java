package com.lottery.bean.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "t_bet_record")
public class BetRecord extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7855909147918307986L;

	/**
	 * 订单编号
	 */
	@Column(name = "order_no")
	private String orderNo;

	/**
	 * 彩种
	 */
	@Column(name = "lottery_code")
	private String lotteryCode;

	/**
	 * 玩法
	 */
	@Column(name = "play_code")
	private String playCode;

	/**
	 * 圆角分模式
	 */
	@Column(name = "bet_model")
	private BigDecimal betModel = BigDecimal.ZERO;

	/**
	 * 投注金额
	 */
	@Column(name = "bet_money")
	private BigDecimal betMoney = BigDecimal.ZERO;

	/**
	 * 投注类型
	 */
	@Column(name = "bet_type")
	private String betType;

	/**
	 * 倍投号码
	 */
	@Column(name = "bile_num")
	private String bileNum;

	/**
	 * 投注号码
	 */
	@Column(name = "bet_num")
	private String betNum;

	/**
	 * 用户ID
	 */
	@Column(name = "customer_id")
	private long customerId;

	/**
	 * 奖期
	 */
	@Column(name = "issue_no")
	private String issueNo;

	/**
	 * 投注单状态
	 */
	@Column(name = "bet_status")
	private int betStatus;

	/**
	 * 投注倍数
	 */
	@Column(name = "multiple")
	private int multiple;
	
	/**
	 * 中奖金额
	 */
	@Column(name = "win_money")
	private BigDecimal winMoney;
	
	/**
	 * 理论奖金
	 */
	@Column(name = "base_money")
	private BigDecimal baseMoney;

	/**
	 * 返点
	 */
	@Column(name = "rebates")
	private BigDecimal rebates;

	/**
	 * 具体选号方式
	 */
	@Column(name = "select_code")
	private String selectCode;
	
	/**
	 * 投注注数
	 */
	@Column(name = "bet_count")
	private int betCount = 0;
	
	/**
	 * 中奖注数
	 */
	@Column(name = "award_count")
	private String awardCount;
	
	/**
	 * 中奖等级
	 */
	@Column(name = "award_level")
	private String awardLevel;
	
	@Column(name = "rsvst1")
	private String rsvst1;
	
	/**
	 * 返奖率
	 */
	@Column(name="payout_ratio")
	private BigDecimal payoutRatio;
	
	@Column(name="temp_Bile_Num")
	private String tempBileNum;
	
	@Column(name="temp_Bet_Num")
	private String tempBetNum;
	
	@Transient
	private String betModelName;
	
	@Transient
	private String betContent;
	
	@Transient
	private String playName;
	
	@Transient
	private String lotteryName;
	
	@Transient
	private String model;
	
	
	public String getLotteryCode() {
		return lotteryCode;
	}

	public void setLotteryCode(String lotteryCode) {
		this.lotteryCode = lotteryCode;
	}

	public String getPlayCode() {
		return playCode;
	}

	public void setPlayCode(String playCode) {
		this.playCode = playCode;
	}

	public BigDecimal getBetModel() {
		return betModel;
	}

	public void setBetModel(BigDecimal betModel) {
		this.betModel = betModel;
	}

	public BigDecimal getBetMoney() {
		return betMoney;
	}

	public void setBetMoney(BigDecimal betMoney) {
		this.betMoney = betMoney;
	}

	public String getBetType() {
		return betType;
	}

	public void setBetType(String betType) {
		this.betType = betType;
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

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getIssueNo() {
		return issueNo;
	}

	public void setIssueNo(String issueNo) {
		this.issueNo = issueNo;
	}

	public int getBetStatus() {
		return betStatus;
	}

	public void setBetStatus(int betStatus) {
		this.betStatus = betStatus;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public int getMultiple() {
		return multiple;
	}

	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}

	public BigDecimal getWinMoney() {
		return winMoney;
	}

	public void setWinMoney(BigDecimal winMoney) {
		this.winMoney = winMoney;
	}

	public BigDecimal getBaseMoney() {
		return baseMoney;
	}

	public void setBaseMoney(BigDecimal baseMoney) {
		this.baseMoney = baseMoney;
	}

	public BigDecimal getRebates() {
		return rebates;
	}

	public void setRebates(BigDecimal rebates) {
		this.rebates = rebates;
	}

	public String getSelectCode() {
		return selectCode;
	}

	public void setSelectCode(String selectCode) {
		this.selectCode = selectCode;
	}

	public int getBetCount() {
		return betCount;
	}

	public void setBetCount(int betCount) {
		this.betCount = betCount;
	}
	
	public String getAwardCount() {
		return awardCount;
	}

	public void setAwardCount(String awardCount) {
		this.awardCount = awardCount;
	}

	public String getAwardLevel() {
		return awardLevel;
	}

	public void setAwardLevel(String awardLevel) {
		this.awardLevel = awardLevel;
	}

	public String getRsvst1() {
		return rsvst1;
	}

	public void setRsvst1(String rsvst1) {
		this.rsvst1 = rsvst1;
	}
	
	public BigDecimal getPayoutRatio() {
		return payoutRatio;
	}

	public void setPayoutRatio(BigDecimal payoutRatio) {
		this.payoutRatio = payoutRatio;
	}
	
	public String getBetModelName() {
		return betModelName;
	}

	public void setBetModelName(String betModelName) {
		this.betModelName = betModelName;
	}

	@Override
	public String toString() {
		return "BetRecord [orderNo=" + orderNo + ", lotteryCode=" + lotteryCode
				+ ", playCode=" + playCode + ", betModel=" + betModel
				+ ", betMoney=" + betMoney + ", betType=" + betType
				+ ", bileNum=" + bileNum + ", betNum=" + betNum
				+ ", customerId=" + customerId + ", issueNo=" + issueNo
				+ ", betStatus=" + betStatus + ", multiple=" + multiple
				+ ", winMoney=" + winMoney + ", baseMoney=" + baseMoney
				+ ", rebates=" + rebates + ", selectCode=" + selectCode
				+ ", betCount=" + betCount + ", getLotteryCode()="
				+ getLotteryCode() + ", getPlayCode()=" + getPlayCode()
				+ ", getBetModel()=" + getBetModel() + ", getBetMoney()="
				+ getBetMoney() + ", getBetType()=" + getBetType()
				+ ", getBileNum()=" + getBileNum() + ", getBetNum()="
				+ getBetNum() + ", getCustomerId()=" + getCustomerId()
				+ ", getIssueNo()=" + getIssueNo() + ", getBetStatus()="
				+ getBetStatus() + ", getOrderNo()=" + getOrderNo()
				+ ", getMultiple()=" + getMultiple() + ", getWinMoney()="
				+ getWinMoney() + ", getBaseMoney()=" + getBaseMoney()
				+ ", getRebates()=" + getRebates() + ", getSelectCode()="
				+ getSelectCode() + ", getBetCount()=" + getBetCount() + "]";
	}
	
	public String getTempString(){
		return this.playCode+this.selectCode+this.betMoney.toString()+this.betModel.toString()+this.multiple+this.baseMoney;
	}

	public String getTempBileNum() {
		return tempBileNum;
	}

	public void setTempBileNum(String tempBileNum) {
		this.tempBileNum = tempBileNum;
	}

	public String getTempBetNum() {
		return tempBetNum;
	}

	public void setTempBetNum(String tempBetNum) {
		this.tempBetNum = tempBetNum;
	}


	public void setBetContent(String betContent) {
		this.betContent = betContent;
	}


	public void setPlayName(String playName) {
		this.playName = playName;
	}


	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getBetContent() {
		return betContent;
	}

	public String getPlayName() {
		return playName;
	}

	public String getLotteryName() {
		return lotteryName;
	}

	public String getModel() {
		return model;
	}
}
