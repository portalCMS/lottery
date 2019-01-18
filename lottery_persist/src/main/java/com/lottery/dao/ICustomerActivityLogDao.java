package com.lottery.dao;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.CustomerActivityLog;
import com.lottery.persist.generice.IGenericDao;

@Repository
public interface ICustomerActivityLogDao extends IGenericDao<CustomerActivityLog>{

	public int getActivityPeoples(long activityId);
}
