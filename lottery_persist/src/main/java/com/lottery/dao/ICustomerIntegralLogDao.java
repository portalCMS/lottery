package com.lottery.dao;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.CustomerIntegralLog;
import com.lottery.persist.generice.IGenericDao;

@Repository
public interface ICustomerIntegralLogDao extends IGenericDao<CustomerIntegralLog>{

	public CustomerIntegralLog getSelfLastDayIntegralLog(long customerId)throws Exception;
	
	public boolean getIsRegistration(long customerId)throws Exception; 
}
