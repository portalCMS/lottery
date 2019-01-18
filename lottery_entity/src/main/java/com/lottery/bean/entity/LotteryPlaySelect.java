package com.lottery.bean.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="t_lottery_play_select")
public class LotteryPlaySelect extends GenericEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2448697943459246748L;

	/**
	 * 玩法CODE
	 */
	@Column(name="model_code")
	private String modelCode;
	
	/**
	 * 选号code
	 */
	@Column(name="select_code")
	private String selectCode;
	
	/**
	 * 选号玩法描述
	 */
	@Column(name="select_desc")
	private String selectDesc;
	
	/**
	 * 选号名称
	 */
	@Column(name="select_name")
	private String selectName;
	
	/**
	 * id位置
	 */
	@Column(name="select_index")
	private String selectIndex;
	
	/**
	 * 处理类全路径
	 */
	@Column(name="class_name")
	private String className;
	
	/**
	 * 处理方法
	 */
	@Column(name="method_Name")
	private String methodName;
	
	/**
	 * 参数位数
	 */
	@Column(name="method_args")
	private int methodArgs;
	
	@Column(name="status")
	private int status;
	
	/**
	 * 任选胆拖参数
	 */
	@Column(name="few")
	private int few;
	
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
