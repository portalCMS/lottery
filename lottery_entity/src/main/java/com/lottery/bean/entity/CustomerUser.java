package com.lottery.bean.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 前台用户表
 *
 */
@Entity
@Table(name="t_customer_user")
public class CustomerUser extends GenericEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3466976519036911319L;

	/**
	 * 用户名
	 */
	@Column(name="customer_name")
	private String customerName;
	
	/**
	 * 用户密码
	 * @author jeff
	 * 
	 * 注意事项:必须MD5加密
	 */
	@Column(name="customer_pwd")
	private String customerPwd;
	
	/**
	 *QQ
	 */
	@Column(name="customer_qq")
	private String qq;
	
	/**
	 * email
	 */
	@Column(name="customer_email")
	private String email;
	
	/**
	 * 用户状态
	 * 0:冻结,1:正常
	 */
	@Column(name="customer_status")
	private int customerStatus;
	
	/**
	 * 用户在线状态
	 * 0:不在线 1:在线
	 */
	@Column(name="customer_online_status")
	private int customerOnlineStatus;
	
	/**
	 * 上级代理
	 * 上级用户ID
	 */
	@Column(name="customer_superior")
	private long customerSuperior;
	
	/**
	 * 级别
	 * 代理当前用户级别，非代理用户可为null
	 */
	@Column(name="customer_level")
	private int customerLevel;
	
	/**
	 * 用户昵称
	 */
	@Column(name="customer_alias")
	private String customerAlias;
	
	/**
	 * 用户登录错误次数
	 * 超过三次冻结用户禁止登陆
	 */
	@Column(name="customer_error")
	private int customerError;
	
	/**
	 * 用户类型
	 * 17001：代理  17002：会员
	 */
	@Column(name="customer_type")
	private int customerType;
	
	/**
	 * 金额密码
	 * MD5加密
	 */
	@Column(name="money_pwd")
	private String moneyPwd;
	
	/**
	 * 安全问题
	 */
	@Column(name="security_question")
	private String securityQuestion;
	
	/**
	 * 安全答案
	 */
	@Column(name="security_answer")
	private String securityAnswer;
	
	/**
	 * 上级用户账户集合
	 */
	@Column(name="allParentAccount")
	private String allParentAccount;
	
	/**
	 * 返点比
	 */
	@Column(name="rebates")
	private BigDecimal rebates;
	/**
	 * 用户活跃等级
	 */
	@Column(name="active_level")
	private Integer activeLevel;
	
	/**
	 * cpu序列号
	 */
	@Column(name="rsvst1")
	private String rsvst1;
	
	/**
	 * 硬盘序列号
	 */
	@Column(name="rsvst2")
	private String rsvst2;
	
	/**
	 * 文件名称
	 */
	@Column(name="rsvst3")
	private String rsvst3;
	
	/**
	 * 是否开放向下充值
	 */
	@Column(name="rsvst4")
	private String rsvst4;
	
	@Transient
	private String ip;

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

	public String getSecurityQuestion() {
		return securityQuestion;
	}

	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}

	public String getSecurityAnswer() {
		return securityAnswer;
	}

	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}

	public String getAllParentAccount() {
		return allParentAccount;
	}

	public void setAllParentAccount(String allParentAccount) {
		this.allParentAccount = allParentAccount;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public BigDecimal getRebates() {
		return rebates;
	}

	public void setRebates(BigDecimal rebates) {
		this.rebates = rebates;
	}
	
	public Integer getActiveLevel() {
		return activeLevel;
	}

	public void setActiveLevel(Integer activeLevel) {
		this.activeLevel = activeLevel;
	}

	@Override
	public String toString() {
		return "CustomerUser [customerName=" + customerName + ", customerPwd="
				+ customerPwd + ", qq=" + qq + ", email=" + email
				+ ", customerStatus=" + customerStatus
				+ ", customerOnlineStatus=" + customerOnlineStatus
				+ ", customerSuperior=" + customerSuperior + ", customerLevel="
				+ customerLevel + ", customerAlias=" + customerAlias
				+ ", customerError=" + customerError + ", customerType="
				+ customerType + ", moneyPwd=" + moneyPwd
				+ ", securityQuestion=" + securityQuestion
				+ ", securityAnswer=" + securityAnswer + ", allParentAccount="
				+ allParentAccount + ", rebates=" + rebates + ", activeLevel="
				+ activeLevel + ", ip=" + ip + ", getId()=" + getId() + "]";
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

	
	
}
