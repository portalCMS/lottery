package com.lottery.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lottery.bean.entity.AdvertisingList;

@Service
public interface IAdvertisingListService {
	/**
	 * 查询广告信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<AdvertisingList> getAdvertisingLists(final Map<String,Object> param)throws Exception;
}
