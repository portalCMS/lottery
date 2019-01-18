package com.lottery.dao.impl;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.CustomerIntegralLog;
import com.lottery.dao.ICustomerIntegralLogDao;
import com.lottery.persist.generice.GenericDAO;
import com.xl.lottery.util.DateUtil;

@Repository
public class CustomerIntegralLogDaoImpl extends GenericDAO<CustomerIntegralLog> implements ICustomerIntegralLogDao{

	public CustomerIntegralLogDaoImpl() {
		super(CustomerIntegralLog.class);
	}

	@Override
	public CustomerIntegralLog getSelfLastDayIntegralLog(long customerId)
			throws Exception {
		// TODO Auto-generated method stub
		StringBuffer queryString = new StringBuffer("from CustomerIntegralLog t where t.customerId = ? and t.createTime>=? and t.createTime<=? ");
		Date todayStart = DateUtil.strToDate2(DateUtil.getDayStartTime(DateUtils.addDays(new Date(), -1)));
		Date todayEnd = DateUtil.strToDate2(DateUtil.getDayEndTime(DateUtils.addDays(new Date(), -1)));
		Query query = getSession().createQuery(queryString.toString());
		query.setParameter(0, customerId);
		query.setTimestamp(1, todayStart);
		query.setTimestamp(2, todayEnd);
		if(query.list()==null||query.list().size()==0)return null;
		CustomerIntegralLog integralLog = (CustomerIntegralLog) query.list().get(0);
		return integralLog;
	}

	@Override
	public boolean getIsRegistration(long customerId)
			throws Exception {
		// TODO Auto-generated method stub
		StringBuffer queryString = new StringBuffer("from CustomerIntegralLog t where t.customerId = ? and t.createTime>=? and t.createTime<=? ");
		Date todayStart = DateUtil.strToDate2(DateUtil.getDayStartTime(new Date()));
		Date todayEnd = DateUtil.strToDate2(DateUtil.getDayEndTime(new Date()));
		Query query = getSession().createQuery(queryString.toString());
		query.setParameter(0, customerId);
		query.setTimestamp(1, todayStart);
		query.setTimestamp(2, todayEnd);
		if(query.list().size()>0)return true;
		return false;
	}
}
