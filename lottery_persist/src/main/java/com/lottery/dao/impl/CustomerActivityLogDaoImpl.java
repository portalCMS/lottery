package com.lottery.dao.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.CustomerActivityLog;
import com.lottery.dao.ICustomerActivityLogDao;
import com.lottery.persist.generice.GenericDAO;

@Repository
public class CustomerActivityLogDaoImpl extends GenericDAO<CustomerActivityLog> implements ICustomerActivityLogDao{

	public CustomerActivityLogDaoImpl() {
		super(CustomerActivityLog.class);
	}

	@Override
	public int getActivityPeoples(long activityId) {
		// TODO Auto-generated method stub
		StringBuffer count = new StringBuffer("select count(id) from CustomerActivityLog t where t.activityId = ? ");
		Query query = getSession().createQuery(count.toString());
		query.setParameter(0, activityId);
		Long countNum = (Long) query.list().get(0);
		return countNum.intValue();
	}

}
