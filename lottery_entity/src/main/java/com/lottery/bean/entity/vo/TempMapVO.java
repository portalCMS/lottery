package com.lottery.bean.entity.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 存储临时需要用到VO的键值对
 * @author CW-HP7
 *
 */
public class TempMapVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -12706583596740859L;

	private String key;
	
	private String value;
	
	private String value2;
	
	private String value3;
	
	private List<TempMapVO> vos;
	
	public String getValue3() {
		return value3;
	}

	public void setValue3(String value3) {
		this.value3 = value3;
	}

	public List<TempMapVO> getVos() {
		return vos;
	}

	public void setVos(List<TempMapVO> vos) {
		this.vos = vos;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}
	
}
