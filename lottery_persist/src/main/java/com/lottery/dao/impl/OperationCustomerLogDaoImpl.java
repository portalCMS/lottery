package com.lottery.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.OperationAdminLog;
import com.lottery.bean.entity.OperationCustomerLog;
import com.lottery.dao.IOperationCustomerLogDao;
import com.lottery.persist.generice.GenericDAO;

@Repository
public class OperationCustomerLogDaoImpl extends
		GenericDAO<OperationCustomerLog> implements IOperationCustomerLogDao {

	private static Logger logger = LoggerFactory.getLogger(OperationCustomerLog.class);
	
	public OperationCustomerLogDaoImpl() {
		super(OperationCustomerLog.class);
		// TODO Auto-generated constructor stub
	}

}
