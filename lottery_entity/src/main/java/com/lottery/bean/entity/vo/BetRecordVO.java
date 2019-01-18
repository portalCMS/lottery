package com.lottery.bean.entity.vo;

import java.math.BigDecimal;


public class BetRecordVO extends GenericEntityVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7855909147918307986L;

	private String orderNo;
	
	private String lotteryCode;
	
	private String playCode;
	
	private BigDecimal betModel = BigDecimal.ZERO;
	
	private BigDecimal betMoney = BigDecimal.ZERO;
	
	private String betType;
	
	private String bileNum;
	
	private String betNum;
	
	private long customerId;
	
	private String issueNo;
	
	private int betStatus;
	
	private int multiple;

	private BigDecimal winMoney = BigDecimal.ZERO;
	
	private BigDecimal baseMoney = BigDecimal.ZERO;
	
	private BigDecimal rebates = BigDecimal.ZERO;
	
	private String selectCode;
	
	private int betCount = 0;
	
	private String awardCount;
	
	private String awardLevel;
	////////////////部分查询数据属性
	private String startTime;
	
	private String endTime;
	
	private String uName;
	
	private String groupCode;
	
	private String betModelName;
	
	private String level;
	
	private String time;
	
	private String checkLower;
	
	private BigDecimal userRebates = BigDecimal.ZERO;
	
	private String urlName;
	
	private String opernBetNumber;
	
	private String lotteryCodeCopy;
	
	private String playCodeCopy;
	
	private BigDecimal payoutRatio;
	
	private String fromUrl;
	
	private Integer custometType;
	
	private String selectCodeName;
	
	private String custometTypes;
	
	private String rsvst1;
	
	private String fromType;
	
	public String getRsvst1() {
		return rsvst1;
	}

	public void setRsvst1(String rsvst1) {
		this.rsvst1 = rsvst1;
	}

	public String getSelectCodeName() {
		return selectCodeName;
	}

	public void setSelectCodeName(String selectCodeName) {
		this.selectCodeName = selectCodeName;
	}

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

	public String getuName() {
		return uName;
	}

	public void setuName(String uName) {
		this.uName = uName;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getBetModelName() {
		return betModelName;
	}

	public void setBetModelName(String betModelName) {
		this.betModelName = betModelName;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getCheckLower() {
		return checkLower;
	}

	public void setCheckLower(String checkLower) {
		this.checkLower = checkLower;
	}

	public BigDecimal getUserRebates() {
		return userRebates;
	}

	public void setUserRebates(BigDecimal userRebates) {
		this.userRebates = userRebates;
	}

	public String getUrlName() {
		return urlName;
	}

	public void setUrlName(String urlName) {
		this.urlName = urlName;
	}

	public String getOpernBetNumber() {
		return opernBetNumber;
	}

	public void setOpernBetNumber(String opernBetNumber) {
		this.opernBetNumber = opernBetNumber;
	}

	public String getLotteryCodeCopy() {
		return lotteryCodeCopy;
	}

	public void setLotteryCodeCopy(String lotteryCodeCopy) {
		this.lotteryCodeCopy = lotteryCodeCopy;
	}

	public String getPlayCodeCopy() {
		return playCodeCopy;
	}

	public void setPlayCodeCopy(String playCodeCopy) {
		this.playCodeCopy = playCodeCopy;
	}

	public String getAwardCount() {
		return awardCount;
	}

	public void setAwardCount(String awardCount) {
		this.awardCount = awardCount;
	}
	
	public String getTempString(){
		return this.playCode+this.selectCode+this.betMoney.toString()+this.betModel.toString()+this.multiple+this.baseMoney;
	}

	public BigDecimal getPayoutRatio() {
		return payoutRatio;
	}

	public void setPayoutRatio(BigDecimal payoutRatio) {
		this.payoutRatio = payoutRatio;
	}

	public String getAwardLevel() {
		return awardLevel;
	}

	public void setAwardLevel(String awardLevel) {
		this.awardLevel = awardLevel;
	}

	public String getFromUrl() {
		return fromUrl;
	}

	public void setFromUrl(String fromUrl) {
		this.fromUrl = fromUrl;
	}

	public Integer getCustometType() {
		return custometType;
	}

	public void setCustometType(Integer custometType) {
		this.custometType = custometType;
	}

	public String getCustometTypes() {
		return custometTypes;
	}

	public void setCustometTypes(String custometTypes) {
		this.custometTypes = custometTypes;
	}

	public String getFromType() {
		return fromType;
	}

	public void setFromType(String fromType) {
		this.fromType = fromType;
	}
}
