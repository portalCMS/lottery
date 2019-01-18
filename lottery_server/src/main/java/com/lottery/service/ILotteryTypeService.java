package com.lottery.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lottery.bean.entity.LotteryType;
import com.lottery.bean.entity.vo.LotteryListVOWebApp;

@Service
public interface ILotteryTypeService{

	/**
	 * 获取彩种集合
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<LotteryType> queryLotteryList(final Map<String, Object> param)throws Exception;

	/**
	 * 更新彩种任务状态
	 * @param param
	 * @throws Exception
	 */
	public void updateTaskRunStatus(final Map<String, Object> param)throws Exception;

	/**
	 * 保存所有彩种信息
	 * @param param
	 * @throws Exception
	 */
	public void saveAllLotteryInfo(final Map<String, Object> param)throws Exception;

	/**
	 * 查询彩种引擎信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<LotteryType> queryLotteryEngineInfo(final Map<String, Object> param)throws Exception;

	/**
	 * 获取所有彩种信息并以map形式返回
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, ?> queryLotteryAllInfo(final Map<String, Object> param)throws Exception;

	/**
	 * 保存彩种信息并返回
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public LotteryType saveLotteryInfo(final Map<String, Object> param)throws Exception;

	/**
	 * 保存奖期修改
	 * 保存旧的奖期添加新的奖期
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<LotteryType> savePeriod(final Map<String, Object> param)throws Exception;

	/**
	 * 更新所有彩种的玩法相关的配置
	 * @param param
	 * @throws Exception
	 */
	public void savePlayRelated(final Map<String, Object> param)throws Exception;
	
	/**
	 * 根据彩种组查询彩种信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<LotteryType> queryLotteryTypeByGroupCode(final Map<String,?> param)throws Exception;
	
	/**
	 * 获取所有有效彩种集合（无查询条件）
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<LotteryType> queryLotteryTypeAll(final Map<String,?> param)throws Exception;
	
	/**
	 * 获取彩种名称根据彩种code
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public String queryLotteryTypeNameByCode(String code) throws Exception;

	/**
	 * 更新彩种任务
	 * @param param
	 * @throws Exception
	 */
	public void updateJobCron(final Map<String, Object> param)throws Exception;
	
	/**
	 * 获取所有彩种集合（无查询条件）
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<LotteryType> getAllType()throws Exception;
	
	/**
	 * 获取所有彩种集合（分组）
	 * @param param
	 * @return
	 * @throws Exception
	 */
	List<LotteryListVOWebApp> getLotteryGroups(final Map<String, Object> param)throws Exception;
}
