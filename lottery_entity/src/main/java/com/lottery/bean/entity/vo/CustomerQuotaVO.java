package com.lottery.bean.entity.vo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用户配额表
 * @author jeff
 */
public class CustomerQuotaVO extends GenericEntityVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1866434264784183097L;

	/**
	 * 用户ID
	 */
	private long customerId;
	
	/**
	 * 配额类型
	 */
	private BigDecimal proportion;
	
	/**
	 * 配额数
	 */
	private int quota_count;
	
	/**
	 * 状态
	 */
	private int status;
	
	private String changeQuotaType;
	
	private String qids;
	
	private String changeCounts;
	
	private long cuId;
	
	private long qid;
	
	private int changeCount;
	
	public String getQids() {
		return qids;
	}

	public void setQids(String qids) {
		this.qids = qids;
	}

	public String getChangeCounts() {
		return changeCounts;
	}

	public void setChangeCounts(String changeCounts) {
		this.changeCounts = changeCounts;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public BigDecimal getProportion() {
		return proportion;
	}

	public void setProportion(BigDecimal proportion) {
		this.proportion = proportion;
	}

	public int getQuota_count() {
		return quota_count;
	}

	public void setQuota_count(int quota_count) {
		this.quota_count = quota_count;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getChangeQuotaType() {
		return changeQuotaType;
	}

	public void setChangeQuotaType(String changeQuotaType) {
		this.changeQuotaType = changeQuotaType;
	}

	public long getCuId() {
		return cuId;
	}

	public void setCuId(long cuId) {
		this.cuId = cuId;
	}

	public long getQid() {
		return qid;
	}

	public void setQid(long qid) {
		this.qid = qid;
	}

	public int getChangeCount() {
		return changeCount;
	}

	public void setChangeCount(int changeCount) {
		this.changeCount = changeCount;
	}
}
