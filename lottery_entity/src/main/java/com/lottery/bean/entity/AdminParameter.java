package com.lottery.bean.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="t_admin_parameter")
public class AdminParameter extends GenericEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6441541027720247599L;

	@Column(name="name")
	private String name;
	
	@Column(name="key1")
	private String key1;
	
	@Column(name="key2")
	private String key2;
	
	@Column(name="key3")
	private String key3;
	
	@Column(name="value")
	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey1() {
		return key1;
	}

	public void setKey1(String key1) {
		this.key1 = key1;
	}

	public String getKey2() {
		return key2;
	}

	public void setKey2(String key2) {
		this.key2 = key2;
	}

	public String getKey3() {
		return key3;
	}

	public void setKey3(String key3) {
		this.key3 = key3;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "AdminParameter [name=" + name + ", key1=" + key1 + ", key2="
				+ key2 + ", key3=" + key3 + ", value=" + value + ", getId()="
				+ getId() + ", getCreateTime()=" + getCreateTime()
				+ ", getCreateUser()=" + getCreateUser() + ", getUpdateTime()="
				+ getUpdateTime() + ", getUpdateUser()=" + getUpdateUser()
				+ ", getVersion()=" + getVersion() + "]";
	}
}
