package com.lottery.dao;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.OperationAdminLog;
import com.lottery.persist.generice.IGenericDao;

@Repository
public interface IOperationAdminLogDao extends IGenericDao<OperationAdminLog> {

}
