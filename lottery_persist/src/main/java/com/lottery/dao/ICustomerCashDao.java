package com.lottery.dao;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.CustomerCash;
import com.lottery.persist.generice.IGenericDao;

@Repository
public interface ICustomerCashDao extends IGenericDao<CustomerCash> {

	public void updateUserCash(CustomerCash userCash) throws Exception;

	public CustomerCash queryUserCashByCustomerId(long customerId) throws Exception;

}
