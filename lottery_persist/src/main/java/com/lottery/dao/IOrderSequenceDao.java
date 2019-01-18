package com.lottery.dao;

import org.springframework.stereotype.Repository;


@Repository
public interface IOrderSequenceDao{

	public String getOrderSequence(String orderType,int num)throws Exception;
}
