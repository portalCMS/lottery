package com.lottery.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lottery.bean.entity.CardLevelConfig;

@Service
public interface ICardLevelConfigService {
	/**
	 * 更新保存卡等级设置
	 * @param param
	 * @throws Exception
	 */
	public void saveCardLevel(final Map<String, Object> param)throws Exception;
	/**
	 * 查询卡等级设置
	 * @param param
	 * @throws Exception
	 */
	public List<CardLevelConfig> queryCardLevel(final Map<String, Object> param)throws Exception;
}
