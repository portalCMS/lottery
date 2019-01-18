package com.lottery.service;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lottery.bean.entity.LotteryPlayBonus;

@Service
public interface ILotteryPlayBonusService {

	/**
	 * 查询用户返点组信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> queryUserModelBonus(final Map<String, Object> param) throws Exception;
	
	
	/**
	 * 查询用户返点组信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> queryUserModelNoBonus(final Map<String, Object> param) throws Exception;
	
	/**
	 * 查询彩种奖金组通过彩种代码和玩法代码
	 * @param param
	 * @return
	 */
	public LotteryPlayBonus queryBonusByCode(final Map<String, Object> param) throws Exception;
}
