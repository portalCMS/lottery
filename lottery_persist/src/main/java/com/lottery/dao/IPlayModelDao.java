package com.lottery.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.LotteryPlayBonus;
import com.lottery.bean.entity.PlayModel;
import com.lottery.persist.generice.IGenericDao;

@Repository
public interface IPlayModelDao extends IGenericDao<PlayModel>{

	public List<PlayModel> queryPlayModel(final Map<String, Object> param);

	public PlayModel queryPlayModelByCode(final String modelCode);
	
	public List<PlayModel> queryPlayModelByGroupCode(final Map<String,?> param) throws Exception;
	
	public List<PlayModel> queryPlayModelByLotteryCode(final Map<String,?> param)throws Exception;
}
