package com.lottery.bean.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="t_customer_activity")
public class CustomerActivity extends GenericEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 285201049306738987L;
//	活动开始时间
	@Column(name="activity_starttime")
	private String starttime;
//	活动结束时间
	@Column(name="activity_endtime")
	private String endtime;
//	活动标题
	@Column(name="activity_title")
	private String title;
//	活动图片路径
	@Column(name="activity_picurl")
	private String picurl;
//	活动内容介绍
	@Column(name="activity_summary")
	private String summary;
//	活动规则
	@Column(name="activity_rule")
	private String rule;
//	活动领奖方式
	@Column(name="activity_model")
	private String model;
//	活动流水倍数
	@Column(name="bet_multiple")
	private Integer betMultiple;
//	活动状态
	@Column(name="activity_status")
	private Integer status;
//	活动类型
	@Column(name="activity_type")
	private String type;
//	黑名单用户
	@Column(name="black_customer")
	private String blackCustomer;
//	黑名单ip
	@Column(name="black_ip")
	private String blackIp;
//	领奖来源限制
	@Column(name="source_type")
	private String sourceType;
//	活动预热时间
	@Column(name="activity_pretime")
	private String pretime;
//	活动关闭时间
	@Column(name="activity_closetime")
	private String closetime;
//	活动用户限定
	@Column(name="activity_usertype")
	private String userType;
	
	public String getPretime() {
		return pretime;
	}

	public void setPretime(String pretime) {
		this.pretime = pretime;
	}

	public String getClosetime() {
		return closetime;
	}

	public void setClosetime(String closetime) {
		this.closetime = closetime;
	}
	
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBlackCustomer() {
		return blackCustomer;
	}

	public void setBlackCustomer(String blackCustomer) {
		this.blackCustomer = blackCustomer;
	}

	public String getBlackIp() {
		return blackIp;
	}

	public void setBlackIp(String blackIp) {
		this.blackIp = blackIp;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPicurl() {
		return picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Integer getBetMultiple() {
		return betMultiple;
	}

	public void setBetMultiple(Integer betMultiple) {
		this.betMultiple = betMultiple;
	}
	
}
