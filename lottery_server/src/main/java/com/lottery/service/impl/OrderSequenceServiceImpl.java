package com.lottery.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.dao.IOrderSequenceDao;
import com.lottery.service.IOrderSequenceService;

@Service
public class OrderSequenceServiceImpl implements IOrderSequenceService{

	@Autowired
	private IOrderSequenceDao orderSequenceDao;
	
	@Override
	public String getOrderSequence(String orderType, int num) throws Exception {
		// TODO Auto-generated method stub
		return orderSequenceDao.getOrderSequence(orderType, num);
	}

}
