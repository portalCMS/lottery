package com.lottery.service;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lottery.bean.entity.LotteryPlayModel;

@Service
public interface ILotteryPlayModelService {
	
	/**
	 * 根据彩种code查询彩种玩法关联信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<LotteryPlayModel> queryPlayModelByLotteryCode(final Map<String, Object> param) throws Exception;

	/**
	 * 查询彩种玩法关联信息集合
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<LotteryPlayModel> queryModelByLottery(final Map<String, Object> param) throws Exception;
}
