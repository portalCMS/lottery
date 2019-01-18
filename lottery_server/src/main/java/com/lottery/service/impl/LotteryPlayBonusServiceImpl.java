package com.lottery.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.bean.entity.BonusGroup;
import com.lottery.bean.entity.LotteryPlayBonus;
import com.lottery.bean.entity.vo.LotteryPlayModelVO;
import com.lottery.dao.IBonusGroupDao;
import com.lottery.dao.ILotteryPlayModelDao;
import com.lottery.service.IBonusGroupService;
import com.lottery.service.ILotteryPlayBonusService;

@Service
public class LotteryPlayBonusServiceImpl implements ILotteryPlayBonusService{

	@Autowired
	private ILotteryPlayModelDao lpmDao;
	
	
	@Override
	public Map<String, Object> queryUserModelBonus(
			Map<String, Object> param) throws Exception {
		LotteryPlayModelVO lpmVo = (LotteryPlayModelVO) param.get("lpmKey");
		Map<String,Object> map = lpmDao.queryUserModelBonus(lpmVo);
		return map;
	}


	@Override
	public LotteryPlayBonus queryBonusByCode(Map<String, Object> param)
			throws Exception {
		return lpmDao.queryBonusByCode(param);
	}


	@Override
	public Map<String, Object> queryUserModelNoBonus(Map<String, Object> param)
			throws Exception {
		LotteryPlayModelVO lpmVo = (LotteryPlayModelVO) param.get("lpmKey");
		Map<String,Object> map = lpmDao.queryUserModelNoBonus(lpmVo);
		return map;
	}

}
