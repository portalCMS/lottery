package com.lottery.dao.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.OperationAdminLog;
import com.lottery.bean.entity.OperationCustomerLog;
import com.lottery.dao.IOperationAdminLogDao;
import com.lottery.dao.IOperationCustomerLogDao;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.exception.LotteryExceptionDictionary;

@Component
public class CustomerUserWriteLog {

	@Autowired
	private IOperationCustomerLogDao operationCustomerUserLogDao;
	
	public void saveWriteLog(CustomerUser user,String type,String table,String sql) throws LotteryException{
		if(user==null||user.getCustomerName().equals("")){
			throw new LotteryException(LotteryExceptionDictionary.CUSTOMERUSERMISS);
		}
		OperationCustomerLog operationlog = new OperationCustomerLog();
		operationlog.setOperationType(type);
		operationlog.setOperationTime(new Date());
		operationlog.setOperationUser(user.getCustomerName());
		operationlog.setOperationTable(table);
		operationlog.setOperationSql(sql);
		operationlog.setCreateTime(new Date());
		operationlog.setCreateUser(user.getCustomerName());
		operationlog.setUpdateTime(new Date());
		operationlog.setUpdateUser(user.getCustomerName());
		operationlog.setIpAddress(user.getIp());
		operationCustomerUserLogDao.insert(operationlog);
	}
}
