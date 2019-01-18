package com.lottery.bean.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="t_user_card_inventory")
public class UserCardInventory extends GenericEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6441541027720247599L;

	@Column(name="inventory_name")
	private String inventoryName;
	
	@Column(name="remark")
	private String remark;
	
	@Column(name="status")
	private Integer status;
	
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getInventoryName() {
		return inventoryName;
	}

	public void setInventoryName(String inventoryName) {
		this.inventoryName = inventoryName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "UserCardInventory [inventoryName=" + inventoryName
				+ ", remark=" + remark + ", status=" + status + ", getId()="
				+ getId() + "]";
	}


	
	
	
	
}
