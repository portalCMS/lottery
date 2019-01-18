package com.lottery.bean.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 后台用户操作记录表
 * @author jeff
 *
 */
@Entity
@Table(name="t_operation_admin_log")
public class OperationAdminLog extends GenericEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1800516167293872137L;

	/**
	 * 操作类型
	 */
	@Column(name="operation_type")
	private String operationType;
	
	/**
	 * 操作时间
	 */
	@Column(name="operation_time")
	private Date operationTime;
	
	/**
	 * 操作人
	 */
	@Column(name="operation_user")
	private String operationUser;
	
	/**
	 * 操作业务表
	 */
	@Column(name="operation_table")
	private String operationTable;
	
	/**
	 * 执行语句
	 */
	@Column(name="operation_sql")
	private String operationSql;
	
	/**
	 * IP地址
	 */
	@Column(name="ip_address")
	private String ipAddress;

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public Date getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}

	public String getOperationUser() {
		return operationUser;
	}

	public void setOperationUser(String operationUser) {
		this.operationUser = operationUser;
	}

	public String getOperationTable() {
		return operationTable;
	}

	public void setOperationTable(String operationTable) {
		this.operationTable = operationTable;
	}

	public String getOperationSql() {
		return operationSql;
	}

	public void setOperationSql(String operationSql) {
		this.operationSql = operationSql;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	@Override
	public String toString() {
		return "OperationAdminLog [operationType=" + operationType
				+ ", operationTime=" + operationTime + ", operationUser="
				+ operationUser + ", operationTable=" + operationTable
				+ ", operationSql=" + operationSql + ", ipAddress=" + ipAddress
				+ ", getId()=" + getId() + ", getCreateTime()="
				+ getCreateTime() + ", getCreateUser()=" + getCreateUser()
				+ ", getUpdateTime()=" + getUpdateTime() + ", getUpdateUser()="
				+ getUpdateUser() + ", getVersion()=" + getVersion() + "]";
	}
	
}
