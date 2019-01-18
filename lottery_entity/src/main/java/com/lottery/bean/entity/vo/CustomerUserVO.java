package com.lottery.bean.entity.vo;

import java.math.BigDecimal;
import java.math.BigInteger;


/**
 * 前台用户表
 *
 */
public class CustomerUserVO extends GenericEntityVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3466976519036911319L;

	/**
	 * 用户名
	 */
	private String customerName;
	
	/**
	 * 用户密码
	 * 
	 * 注意事项:必须MD5加密
	 */
	private String customerPwd;
	
	/**
	 * 用户新密码
	 * 
	 * 注意事项:必须MD5加密
	 */
	private String newPwd;
	
	/**
	 * 用户确认信密码
	 * 
	 * 注意事项:必须MD5加密
	 */
	private String confirmPwd;
	
	/**
	 *QQ
	 */
	private String qq;
	
	/**
	 * email
	 */
	private String email;
	
	/**
	 * 用户状态
	 * 0:冻结,1:正常
	 */
	private int customerStatus;
	
	/**
	 * 用户在线状态
	 * 0:不在线 1:在线
	 */
	private int customerOnlineStatus;
	
	/**
	 * 上级代理
	 * 上级用户ID
	 */
	private long customerSuperior;
	
	/**
	 * 直接下级代理
	 */
	private BigInteger customerLower;
	
	/**
	 * 级别
	 * 代理当前用户级别，非代理用户可为null
	 */
	private int customerLevel;
	
	/**
	 * 用户昵称
	 */
	private String customerAlias;
	
	/**
	 * 用户登录错误次数
	 * 超过三次冻结用户禁止登陆
	 */
	private int customerError;
	
	/**
	 * 用户类型
	 * 17001：代理  17002：会员
	 */
	private int customerType;
	
	/**
	 * 金额密码
	 * MD5加密
	 */
	private String moneyPwd;
	
	
	/**
	 * 配额
	 */
	private String quotas;
	
	/**
	 * 用户URLS
	 */
	private String urls;

	/**
	 * 银行卡
	 */
	private String cards;
	
	/**
	 * 返点比
	 */
	private BigDecimal rebates;
	
	/**
	 * cpu序列号
	 */
	private String rsvst1;
	
	/**
	 * 硬盘序列号
	 */
	private String rsvst2;
	
	
	/**
	 * 是否开放向下充值
	 */
	private String rsvst4;
	
	/**
	 * 用户活跃等级
	 */
	private Integer activeLevel;
	
	private String adminPwd;
	
	private String pickey;
	
	private String code;
	
	private long quotaId;
	
	/**
	 * 返点组ID ,分割数组下标[0]
	 */
	private String point;
	
	private long userMainId;
	
	private long userSunId;
	
	private BigDecimal cash;
	
	private long bankId;
	private long questionId;
	private String question;
	private String answer;
	
	//////////////////////////////////////////////修改用户提交参//////////////////////////////////////////////
	private String userbindcards;
	private String usercards;
	
	private String idsTree;
	
	private String month;
	
	
	private String customerLevelName;
	private BigDecimal userBetMoney;
	private BigDecimal minRebates;
	
	//查询用开始时间和结束时间
	private String strartTime;
	
	private String endTime;
	
	//传输返点剩余数量区间
	private String quotaCount;
	//传输返点区间 
	private String quotaStr;
	
	//投注量区间
	private String betMoney;
	
	//账户余额区间
	private String cashMoney;
	
	//是否绑卡
	private String isUserCard;
	
	//是否分配卡
	private String isBindCard;
	
	//是否投注
	private String isBetMoney;
	
	//是否提款
	private String isWithdrawals;
	
	//是否充值
	private String isRecharge;
	
	//倒序结果
	private String isDesc;
	
	//精确查询
	private String noLike;
	
	private String ip;
	
	private String startQuota;
	
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerPwd() {
		return customerPwd;
	}

	public void setCustomerPwd(String customerPwd) {
		this.customerPwd = customerPwd;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getCustomerStatus() {
		return customerStatus;
	}

	public void setCustomerStatus(int customerStatus) {
		this.customerStatus = customerStatus;
	}

	public int getCustomerOnlineStatus() {
		return customerOnlineStatus;
	}

	public void setCustomerOnlineStatus(int customerOnlineStatus) {
		this.customerOnlineStatus = customerOnlineStatus;
	}

	public long getCustomerSuperior() {
		return customerSuperior;
	}

	public void setCustomerSuperior(long customerSuperior) {
		this.customerSuperior = customerSuperior;
	}

	public int getCustomerLevel() {
		return customerLevel;
	}

	public void setCustomerLevel(int customerLevel) {
		this.customerLevel = customerLevel;
	}

	public String getCustomerAlias() {
		return customerAlias;
	}

	public void setCustomerAlias(String customerAlias) {
		this.customerAlias = customerAlias;
	}

	public int getCustomerError() {
		return customerError;
	}

	public void setCustomerError(int customerError) {
		this.customerError = customerError;
	}

	public int getCustomerType() {
		return customerType;
	}

	public void setCustomerType(int customerType) {
		this.customerType = customerType;
	}

	public String getMoneyPwd() {
		return moneyPwd;
	}

	public void setMoneyPwd(String moneyPwd) {
		this.moneyPwd = moneyPwd;
	}


	public String getUrls() {
		return urls;
	}

	public void setUrls(String urls) {
		this.urls = urls;
	}

	public String getCards() {
		return cards;
	}

	public void setCards(String cards) {
		this.cards = cards;
	}

	public String getAdminPwd() {
		return adminPwd;
	}

	public void setAdminPwd(String adminpwd) {
		this.adminPwd = adminpwd;
	}

	public String getPickey() {
		return pickey;
	}

	public void setPickey(String pickey) {
		this.pickey = pickey;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getQuotas() {
		return quotas;
	}

	public void setQuotas(String quotas) {
		this.quotas = quotas;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	public String getConfirmPwd() {
		return confirmPwd;
	}

	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
	}

	public BigDecimal getRebates() {
		return rebates;
	}

	public void setRebates(BigDecimal rebates) {
		this.rebates = rebates;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	public long getQuotaId() {
		return quotaId;
	}

	public void setQuotaId(long quotaId) {
		this.quotaId = quotaId;
	}

	public long getUserMainId() {
		return userMainId;
	}

	public void setUserMainId(long userMainId) {
		this.userMainId = userMainId;
	}

	public long getUserSunId() {
		return userSunId;
	}

	public void setUserSunId(long userSunId) {
		this.userSunId = userSunId;
	}

	public BigDecimal getCash() {
		return cash;
	}

	public void setCash(BigDecimal cash) {
		this.cash = cash;
	}

	public long getBankId() {
		return bankId;
	}

	public void setBankId(long bankId) {
		this.bankId = bankId;
	}

	public String getUserbindcards() {
		return userbindcards;
	}

	public void setUserbindcards(String userbindcards) {
		this.userbindcards = userbindcards;
	}

	public String getUsercards() {
		return usercards;
	}

	public void setUsercards(String usercards) {
		this.usercards = usercards;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}

	public String getIdsTree() {
		return idsTree;
	}

	public void setIdsTree(String idsTree) {
		this.idsTree = idsTree;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getQuotaCount() {
		return quotaCount;
	}

	public void setQuotaCount(String quotaCount) {
		this.quotaCount = quotaCount;
	}

	public String getCustomerLevelName() {
		return customerLevelName;
	}

	public void setCustomerLevelName(String customerLevelName) {
		this.customerLevelName = customerLevelName;
	}

	public BigDecimal getUserBetMoney() {
		return userBetMoney;
	}

	public void setUserBetMoney(BigDecimal userBetMoney) {
		this.userBetMoney = userBetMoney;
	}

	public Integer getActiveLevel() {
		return activeLevel;
	}

	public void setActiveLevel(Integer activeLevel) {
		this.activeLevel = activeLevel;
	}

	public BigDecimal getMinRebates() {
		return minRebates;
	}

	public void setMinRebates(BigDecimal minRebates) {
		this.minRebates = minRebates;
	}

	public String getStrartTime() {
		return strartTime;
	}

	public void setStrartTime(String strartTime) {
		this.strartTime = strartTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getQuotaStr() {
		return quotaStr;
	}

	public void setQuotaStr(String quotaStr) {
		this.quotaStr = quotaStr;
	}

	public String getBetMoney() {
		return betMoney;
	}

	public void setBetMoney(String betMoney) {
		this.betMoney = betMoney;
	}

	public String getCashMoney() {
		return cashMoney;
	}

	public void setCashMoney(String cashMoney) {
		this.cashMoney = cashMoney;
	}

	public String getIsUserCard() {
		return isUserCard;
	}

	public void setIsUserCard(String isUserCard) {
		this.isUserCard = isUserCard;
	}

	public String getIsBindCard() {
		return isBindCard;
	}

	public void setIsBindCard(String isBindCard) {
		this.isBindCard = isBindCard;
	}

	public String getIsBetMoney() {
		return isBetMoney;
	}

	public void setIsBetMoney(String isBetMoney) {
		this.isBetMoney = isBetMoney;
	}

	public String getIsWithdrawals() {
		return isWithdrawals;
	}

	public void setIsWithdrawals(String isWithdrawals) {
		this.isWithdrawals = isWithdrawals;
	}

	public String getIsRecharge() {
		return isRecharge;
	}

	public void setIsRecharge(String isRecharge) {
		this.isRecharge = isRecharge;
	}

	public String getIsDesc() {
		return isDesc;
	}

	public void setIsDesc(String isDesc) {
		this.isDesc = isDesc;
	}

	public String getNoLike() {
		return noLike;
	}

	public void setNoLike(String noLike) {
		this.noLike = noLike;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
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

	public String getStartQuota() {
		return startQuota;
	}

	public void setStartQuota(String startQuota) {
		this.startQuota = startQuota;
	}

	public String getRsvst4() {
		return rsvst4;
	}

	public void setRsvst4(String rsvst4) {
		this.rsvst4 = rsvst4;
	}

	public BigInteger getCustomerLower() {
		return customerLower;
	}

	public void setCustomerLower(BigInteger customerLower) {
		this.customerLower = customerLower;
	}
}
