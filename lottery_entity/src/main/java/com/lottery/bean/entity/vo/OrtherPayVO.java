package com.lottery.bean.entity.vo;

public class OrtherPayVO extends GenericEntityVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 订单号
	 */
	private String billno;
	
	/**
	 * 订单金额
	 */
	private String amount;
	
	/**
	 * 订单时间
	 */
	private String date;
	
	/**
	 * 是否成功 成功Y
	 */
	private String succ;
	
	/**
	 * 附加信息 post附加信息
	 */
	private String msg;
	
	/**
	 * 账号附加信息
	 */
	private String attach;
	
	/**
	 * 第三方订单号
	 */
	private String ipsbillno;
	
	/**
	 * 传输编码
	 */
	private String retencodetype;
	
	/**
	 * 交易币种
	 */
	private String currency_type;
	
	/**
	 * 验证签名
	 */
	private String signature;
	
	/**
	 * 银行交易订单
	 */
	private String bankbillno;
	
	/**
	 * 银行交易时间
	 */
	private String ipsbanktime;
	
	/**
	 * 商户号
	 */
	private String mercode;
	

	public String getBillno() {
		return billno;
	}

	public void setBillno(String billno) {
		this.billno = billno;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSucc() {
		return succ;
	}

	public void setSucc(String succ) {
		this.succ = succ;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getIpsbillno() {
		return ipsbillno;
	}

	public void setIpsbillno(String ipsbillno) {
		this.ipsbillno = ipsbillno;
	}

	public String getCurrency_type() {
		return currency_type;
	}

	public void setCurrency_type(String currency_type) {
		this.currency_type = currency_type;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getBankbillno() {
		return bankbillno;
	}

	public void setBankbillno(String bankbillno) {
		this.bankbillno = bankbillno;
	}

	public String getIpsbanktime() {
		return ipsbanktime;
	}

	public void setIpsbanktime(String ipsbanktime) {
		this.ipsbanktime = ipsbanktime;
	}

	public String getMercode() {
		return mercode;
	}

	public void setMercode(String mercode) {
		this.mercode = mercode;
	}

	public String getRetencodetype() {
		return retencodetype;
	}

	public void setRetencodetype(String retencodetype) {
		this.retencodetype = retencodetype;
	}

}
