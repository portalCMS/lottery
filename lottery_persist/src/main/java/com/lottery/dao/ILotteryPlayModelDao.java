package com.lottery.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.LotteryPlayBonus;
import com.lottery.bean.entity.LotteryPlayModel;
import com.lottery.bean.entity.PlayModel;
import com.lottery.bean.entity.SourceLink;
import com.lottery.bean.entity.vo.LotteryPlayModelVO;
import com.lottery.persist.generice.IGenericDao;

@Repository
public interface ILotteryPlayModelDao extends IGenericDao<LotteryPlayModel>{

	public List<LotteryPlayModel> queryPlayModelByLotteryCode(final Map<String, Object> param)throws Exception;

	public Map<String, Object> queryUserModelBonus(LotteryPlayModelVO lpmVo)throws Exception;
	
	public Map<String, Object> queryUserModelNoBonus(LotteryPlayModelVO lpmVo)throws Exception;

	public LotteryPlayBonus queryBonusByCode(final Map<String, Object> param)throws Exception;

}
