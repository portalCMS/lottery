package com.lottery.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.LotteryType;
import com.lottery.bean.entity.OperationAdminLog;
import com.lottery.dao.IOperationAdminLogDao;
import com.lottery.persist.generice.GenericDAO;

@Repository
public class OperationAdminLogDaoImpl extends GenericDAO<OperationAdminLog>
		implements IOperationAdminLogDao {

	private static Logger logger = LoggerFactory.getLogger(OperationAdminLog.class);
	
	public OperationAdminLogDaoImpl() {
		super(OperationAdminLog.class);
	}

}
