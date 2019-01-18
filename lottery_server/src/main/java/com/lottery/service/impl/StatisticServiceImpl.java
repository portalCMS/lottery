package com.lottery.service.impl;

import java.util.Map;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.bean.entity.Page;
import com.lottery.dao.ICustomerUserDao;
import com.lottery.service.IStatisticService;

@Service
public class StatisticServiceImpl implements IStatisticService{
	
	@Autowired
	private ICustomerUserDao userDao;

	@Override
	public Page<Object, Object> getSuperQueryData(Map<String, ?> param)
			throws Exception {
		// TODO Auto-generated method stub
		Page<Object, Object> pageObj = userDao.getSuperQueryData(param);
		return pageObj;
	}

}
