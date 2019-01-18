package com.lottery.dao;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.CustomerIntegral;
import com.lottery.bean.entity.vo.CustomerIntegralVO;
import com.lottery.persist.generice.IGenericDao;

@Repository
public interface ICustomerIntegralDao extends IGenericDao<CustomerIntegral>{

	public CustomerIntegral getSelfIntegral(long customerId)throws Exception;
}
