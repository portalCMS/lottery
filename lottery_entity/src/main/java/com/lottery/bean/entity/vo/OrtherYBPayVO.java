package com.lottery.bean.entity.vo;


import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;



public class OrtherYBPayVO extends GenericEntityVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private String keyValue;
	
	private String hmac;

	/**
	 * 商家ID
	 */
	private String p1_MerId;
	
	/**
	 * 消息类型
	 */
	private String r0_Cmd;
	
	/**
	 * 业务返回码
	 */
	private String r1_Code;
	
	/**
	 * 交易ID
	 */
	private String r2_TrxId;
	
	/**
	 * 交易金额
	 */
	private String r3_Amt;
	
	/**
	 * 货币单位
	 */
	private String r4_Cur;
	
	/**
	 * 产品Id
	 */
	private String r5_Pid;
	
	/**
	 * 订单ID
	 */
	private String r6_Order;
	
	/**
	 * 用户ID
	 */
	private String r7_Uid;
	
	/**
	 * 商家扩展信息
	 */
	private String r8_MP;
	
	/**
	 * 交易结果返回类型
	 */
	private String r9_BType;

	public String getP1_MerId() {
		return p1_MerId;
	}

	public void setP1_MerId(String p1_MerId) {
		this.p1_MerId = p1_MerId;
	}

	public String getR0_Cmd() {
		return r0_Cmd;
	}

	public void setR0_Cmd(String r0_Cmd) {
		this.r0_Cmd = r0_Cmd;
	}

	public String getR1_Code() {
		return r1_Code;
	}

	public void setR1_Code(String r1_Code) {
		this.r1_Code = r1_Code;
	}

	public String getR2_TrxId() {
		return r2_TrxId;
	}

	public void setR2_TrxId(String r2_TrxId) {
		this.r2_TrxId = r2_TrxId;
	}

	public String getR3_Amt() {
		return r3_Amt;
	}

	public void setR3_Amt(String r3_Amt) {
		this.r3_Amt = r3_Amt;
	}

	public String getR4_Cur() {
		return r4_Cur;
	}

	public void setR4_Cur(String r4_Cur) {
		this.r4_Cur = r4_Cur;
	}

	public String getR5_Pid() throws UnsupportedEncodingException {
		return new String(Base64.decodeBase64(r5_Pid),"gb2312");
	}

	public void setR5_Pid(String r5_Pid) {
		this.r5_Pid = r5_Pid;
	}

	public String getR6_Order() {
		return r6_Order;
	}

	public void setR6_Order(String r6_Order) {
		this.r6_Order = r6_Order;
	}

	public String getR7_Uid() {
		return r7_Uid;
	}

	public void setR7_Uid(String r7_Uid) {
		this.r7_Uid = r7_Uid;
	}

	public String getR8_MP() {
		return r8_MP;
	}

	public void setR8_MP(String r8_MP) {
		this.r8_MP = r8_MP;
	}

	public String getR9_BType() {
		return r9_BType;
	}

	public void setR9_BType(String r9_BType) {
		this.r9_BType = r9_BType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String toDate(){
		String data = "";
		try {
			data = this.getP1_MerId()+this.getR0_Cmd()+this.getR1_Code()
							+this.getR2_TrxId()+this.getR3_Amt()+this.getR4_Cur()
								+this.getR5_Pid()+this.getR6_Order()+this.getR7_Uid()
									+this.getR8_MP()+this.getR9_BType();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	public String getHmac() {
		return hmac;
	}

	public void setHmac(String hmac) {
		this.hmac = hmac;
	}

}
