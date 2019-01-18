package com.lottery.bean.entity.vo;

import javax.persistence.Column;


public class LotteryPlaySelectVO extends GenericEntityVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2448697943459246748L;

	private String lotteryCode;
	/**
	 * 玩法CODE
	 */
	private String modelCode;
	
	/**
	 * 选号code
	 */
	private String selectCode;
	
	/**
	 * 选号玩法描述
	 */
	private String selectDesc;
	
	/**
	 * 选号名称
	 */
	private String selectName;
	
	/**
	 * id位置
	 */
	private String selectIndex;
	
	/**
	 * 处理类全路径
	 */
	private String className;
	
	/**
	 * 处理方法
	 */
	private String methodName;
	
	/**
	 * 参数位数
	 */
	private int methodArgs;
	
	private int status;
	
	/**
	 * 任选胆拖参数
	 */
	private int few;

	private int totalTimes;
	
	private String type;
	
	private String lotGroup;
	
	public String getLotGroup() {
		return lotGroup;
	}

	public void setLotGroup(String lotGroup) {
		this.lotGroup = lotGroup;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getTotalTimes() {
		return totalTimes;
	}

	public void setTotalTimes(int totalTimes) {
		this.totalTimes = totalTimes;
	}

	public String getLotteryCode() {
		return lotteryCode;
	}

	public void setLotteryCode(String lotteryCode) {
		this.lotteryCode = lotteryCode;
	}

	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	public String getSelectCode() {
		return selectCode;
	}

	public void setSelectCode(String selectCode) {
		this.selectCode = selectCode;
	}

	public String getSelectDesc() {
		return selectDesc;
	}

	public void setSelectDesc(String selectDesc) {
		this.selectDesc = selectDesc;
	}

	public String getSelectName() {
		return selectName;
	}

	public void setSelectName(String selectName) {
		this.selectName = selectName;
	}

	public String getSelectIndex() {
		return selectIndex;
	}

	public void setSelectIndex(String selectIndex) {
		this.selectIndex = selectIndex;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public int getMethodArgs() {
		return methodArgs;
	}

	public void setMethodArgs(int methodArgs) {
		this.methodArgs = methodArgs;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getFew() {
		return few;
	}

	public void setFew(int few) {
		this.few = few;
	}

	
}
