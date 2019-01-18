package com.lottery.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.lottery.bean.entity.Page;

@Service
public interface IStatisticService {

	/**
	 * 获取超级查询翻页数据
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Object, Object> getSuperQueryData(final Map<String,?> param)throws Exception;
	
}
