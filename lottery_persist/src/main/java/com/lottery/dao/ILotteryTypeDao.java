package com.lottery.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.LotteryType;
import com.lottery.persist.generice.IGenericDao;

@Repository
public interface ILotteryTypeDao extends IGenericDao<LotteryType> {

	public List<LotteryType> queryLotteryList(final Map<String, Object> param)throws Exception;
	
	public List<LotteryType> queryLotteryTypeByGroupCode(final Map<String, ?> param)throws Exception;
	
	public String queryLotteryTypeNameByCode(String code)throws Exception;
	
	public List<LotteryType> queryLotteryTypeAll(final Map<String,?> param)throws Exception;

	public LotteryType updateLottery(final Map<String, Object> param)throws Exception;

}
