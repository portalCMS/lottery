package com.lottery.service;

import org.springframework.stereotype.Service;

@Service
public interface IOrderSequenceService {

	/**
	 * 获取订单编号
	 * @param orderType
	 * @param num
	 * @return
	 * @throws Exception
	 */
	public String getOrderSequence(String orderType,int num)throws Exception;
}
