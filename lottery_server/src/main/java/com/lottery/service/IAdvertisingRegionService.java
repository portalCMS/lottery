package com.lottery.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lottery.bean.entity.AdvertisingList;
import com.lottery.bean.entity.AdvertisingRegion;
import com.lottery.bean.entity.vo.AdvertisingRegionVO;


@Service
public interface IAdvertisingRegionService {
	/**
	 * 根据广告区域查询广告信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<AdvertisingRegionVO> queryAdvertRegion(final Map<String,Object> param)throws Exception;
	/**
	 * 根据广告区域Id查询广告信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public AdvertisingRegion queryAdvertRegionbyId(final Map<String,Object> param)throws Exception;
	/**
	 * 根据广告区域code查询广告信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<AdvertisingList> queryAdvertisingListByCode(final Map<String,Object> param)throws Exception;
	/**
	 * 保存广告信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String saveAdvertisingList(final Map<String,Object> param)throws Exception;
}
