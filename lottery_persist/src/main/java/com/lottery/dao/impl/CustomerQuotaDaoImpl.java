package com.lottery.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.CustomerQuota;
import com.lottery.dao.ICustomerQuotaDao;
import com.lottery.persist.generice.GenericDAO;

@Repository
public class CustomerQuotaDaoImpl extends GenericDAO<CustomerQuota> implements
		ICustomerQuotaDao {

	private static Logger logger = LoggerFactory.getLogger(CustomerQuotaDaoImpl.class);
	
	public CustomerQuotaDaoImpl() {
		super(CustomerQuota.class);
	}

	@Override
	public List<CustomerQuota> getCustomerQuotaByCustomerId(long customer) {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer(" from CustomerQuota t where t.customerId = ? ");
		Query query = getSession().createQuery(hql.toString());
		query.setParameter(0, customer);
		return query.list();
	}
	

	@Override
	public BigDecimal getCustomerQuotaMaxProportionByCustomerId(long customer) {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer(" select max(t.proportion) from CustomerQuota t where t.customerId = ? ");
		/*Transaction tx = getSession().beginTransaction();   
		tx.commit();*/
		Query query = getSession().createQuery(hql.toString());
		query.setParameter(0, customer);
		//Long count = (Long)session.createQuery("select count(*) from Student").uniqueResult();
		return (BigDecimal)query.uniqueResult();
	}

}
