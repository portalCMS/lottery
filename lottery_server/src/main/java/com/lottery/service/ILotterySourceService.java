package com.lottery.service;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lottery.bean.entity.LotterySource;
import com.lottery.bean.entity.SourceLink;

@Service
public interface ILotterySourceService {

	/**
	 * 根据彩种code查询号源中间表信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<LotterySource> queryLinksByLotteryCode(final Map<String, Object> param)throws Exception;

	/**
	 * 保存号源中间表信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<LotterySource> saveSource(final Map<String, Object> param)throws Exception;

}
