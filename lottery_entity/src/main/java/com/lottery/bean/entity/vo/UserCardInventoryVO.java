package com.lottery.bean.entity.vo;


public class UserCardInventoryVO extends GenericEntityVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6441541027720247599L;

	private String cardInventoryName;
	
	private String remark;

	public String getCardInventoryName() {
		return cardInventoryName;
	}

	public void setCardInventoryName(String cardInventoryName) {
		this.cardInventoryName = cardInventoryName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "UserCardInventory [cardInventoryName=" + cardInventoryName
				+ ", remark=" + remark + ", getId()=" + getId() + "]";
	}

}
