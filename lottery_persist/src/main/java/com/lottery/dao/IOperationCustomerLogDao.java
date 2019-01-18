package com.lottery.dao;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.OperationCustomerLog;
import com.lottery.persist.generice.IGenericDao;

@Repository
public interface IOperationCustomerLogDao extends IGenericDao<OperationCustomerLog> {

}
