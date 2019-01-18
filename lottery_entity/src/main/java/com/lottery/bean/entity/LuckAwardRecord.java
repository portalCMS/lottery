package com.lottery.bean.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="t_luck_award_record")
public class LuckAwardRecord extends GenericEntity{

	private static final long serialVersionUID = 285201049306738987L;
//	活动id
	@Column(name="activity_id")
	private Long activityId;
//	用户id
	@Column(name="customer_id")
	private Long customerId;
//	活动中奖奖区
	@Column(name="award_area")
	private Integer awardArea;
//	活动奖区奖级
	@Column(name="award_level")
	private Integer awardLevel;
//	已抽奖次数
	@Column(name="award_count")
	private Integer awardCount;
	public Long getActivityId() {
		return activityId;
	}
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Integer getAwardArea() {
		return awardArea;
	}
	public void setAwardArea(Integer awardArea) {
		this.awardArea = awardArea;
	}
	public Integer getAwardLevel() {
		return awardLevel;
	}
	public void setAwardLevel(Integer awardLevel) {
		this.awardLevel = awardLevel;
	}
	public Integer getAwardCount() {
		return awardCount;
	}
	public void setAwardCount(Integer awardCount) {
		this.awardCount = awardCount;
	}
	
}
