package com.lottery.service;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lottery.bean.entity.SourceLink;

@Service
public interface ISourceLinkService {

	/**
	 * 获取所有号源配置信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<SourceLink> queryServiceByLotteryCode(final Map<String, Object> param) throws Exception;

}
