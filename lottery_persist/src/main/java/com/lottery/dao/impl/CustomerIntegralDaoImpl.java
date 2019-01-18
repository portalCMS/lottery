package com.lottery.dao.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.CustomerIntegral;
import com.lottery.bean.entity.vo.CustomerIntegralVO;
import com.lottery.dao.ICustomerIntegralDao;
import com.lottery.persist.generice.GenericDAO;

@Repository
public class CustomerIntegralDaoImpl extends GenericDAO<CustomerIntegral> implements ICustomerIntegralDao{

	public CustomerIntegralDaoImpl() {
		super(CustomerIntegral.class);
	}

	@Override
	public CustomerIntegral getSelfIntegral(long customerId) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer queryString  = new StringBuffer("from CustomerIntegral where customerId = ? ");
		Query query = getSession().createQuery(queryString.toString());
		query.setParameter(0, customerId);
		CustomerIntegral entity = (CustomerIntegral) query.list().get(0);
		return entity;
	}

}
