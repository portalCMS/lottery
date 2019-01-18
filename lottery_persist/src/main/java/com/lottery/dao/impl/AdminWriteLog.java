package com.lottery.dao.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.OperationAdminLog;
import com.lottery.dao.IOperationAdminLogDao;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.exception.LotteryExceptionDictionary;

@Component
public class AdminWriteLog {

	@Autowired
	private IOperationAdminLogDao operationAdminLogDao;
	
	public void saveWriteLog(AdminUser user,String type,String table,String sql) throws LotteryException{
		if(user==null||user.getUserName().equals("")){
			throw new LotteryException(LotteryExceptionDictionary.ADMINUSERMISS);
		}
		OperationAdminLog operationlog = new OperationAdminLog();
		operationlog.setOperationType(type);
		operationlog.setOperationTime(new Date());
		operationlog.setOperationUser(user.getUserName());
		operationlog.setOperationTable(table);
		operationlog.setOperationSql(sql);
		operationlog.setCreateTime(new Date());
		operationlog.setCreateUser(user.getUserName());
		operationlog.setUpdateTime(new Date());
		operationlog.setUpdateUser(user.getUserName());
		operationlog.setIpAddress(user.getIp());
		operationAdminLogDao.insert(operationlog);
	}
}
