package com.lottery.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.bean.entity.LotteryPlaySelect;
import com.lottery.dao.ILotteryPlaySelectDao;
import com.lottery.service.ILotteryPlaySelectService;

@Service
public class LotteryPlaySelectServiceImpl implements ILotteryPlaySelectService {

	@Autowired
	private ILotteryPlaySelectDao lotteryPlaySelectDao;
	
	@Override
	public List<LotteryPlaySelect> getAllSelectCode() throws Exception {
		// TODO Auto-generated method stub
		return lotteryPlaySelectDao.queryAll();
	}

}
