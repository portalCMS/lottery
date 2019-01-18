package com.lottery.dao.impl;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.CustomerCash;
import com.lottery.dao.ICustomerCashDao;
import com.lottery.persist.generice.GenericDAO;

@Repository
public class CustomerCashDaoImpl extends GenericDAO<CustomerCash> implements
		ICustomerCashDao {

	private static Logger logger = LoggerFactory.getLogger(CustomerCashDaoImpl.class);
	
	public CustomerCashDaoImpl() {
		super(CustomerCash.class);
	}

	@Override
	public void updateUserCash(CustomerCash userCash) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CustomerCash queryUserCashByCustomerId(long customerId)throws Exception {
		String hqlSql = "from CustomerCash t where t.customerId = ?";
		Query query = getSession().createQuery(hqlSql);
		query.setParameter(0, customerId);
		return (CustomerCash) query.list().get(0);
	}

}
