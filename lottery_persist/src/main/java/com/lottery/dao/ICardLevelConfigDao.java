package com.lottery.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.CardLevelConfig;
import com.lottery.persist.generice.IGenericDao;

@Repository
public interface ICardLevelConfigDao extends IGenericDao<CardLevelConfig>{

	public List<CardLevelConfig> queryCardLevel(final Map<String, Object> param) throws Exception;

	public void deleteCardLevel(final Map<String, Object> param) throws Exception;

}
