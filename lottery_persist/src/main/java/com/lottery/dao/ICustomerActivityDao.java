package com.lottery.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.CustomerActivity;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.CustomerActivityVO;
import com.lottery.bean.entity.vo.CustomerActivityVO;
import com.lottery.persist.generice.IGenericDao;

@Repository
public interface ICustomerActivityDao extends IGenericDao<CustomerActivity>{
	
	public boolean checkActivityTime(String type,String startTime,String endTime)throws Exception;
	
	/**
	 * 保存活动
	 * @param param
	 * @throws Exception
	 */
	public void saveBetActivity(Map<String, Object> param)throws Exception;
	/**
	 * 查询活动
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<CustomerActivity> queryActivityList(Map<String, Object> param)throws Exception;
	/**
	 * 前台查询活动明细
	 * @param param
	 * @return
	 */
	public CustomerActivityVO queryActivityDetail(Map<String, Object> param)throws Exception;
	/**
	 * 检查用户是否可以获得注册送活动奖励
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> saveRegActivityAward(Map<String, Object> param)throws Exception;
	/**
	 * 检查用户是否可以获得首充送活动奖励
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> saveFrcActivityAward(Map<String, Object> param)throws Exception;
	/**
	 * 检查用户是否可以获得游戏送活动奖励
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> saveBetActivityAward(Map<String, Object> param)throws Exception;

	
	/**
	 * 后台查询活动翻页
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<CustomerActivityVO, CustomerActivity> queryActivitys(final Map<String,Object> param)throws Exception;

	public Page<CustomerActivityVO, CustomerActivity> querMyActivityRecord(final Map<String,Object> param)throws Exception;

	public void saveRegActivity(Map<String, Object> param)throws Exception;

	public void saveFrcActivity(Map<String, Object> param)throws Exception;
	
	/**
	 * 修改
	 * george
	 * @param param
	 * @throws Exception
	 */
	public void updateRegActivity(Map<String, Object> param) throws Exception;
	
	/**
	 * 修改 george
	 * @param param
	 * @throws Exception
	 */
	public void updateFrcActivity(Map<String, Object> param) throws Exception;
	
	/**
	 * 游戏活动保存修改
	 * @param param
	 * @throws Exception
	 */
	public void updatesBetActivity(Map<String,Object> param)throws Exception;

	/**
	 * 抽奖活动保存修改
	 * @param param
	 * @throws Exception
	 */
	public void saveLuckActivity(Map<String,Object> param)throws Exception;
	/**
	 * 抽奖活动获取奖励
	 * @param param
	 * @throws Exception
	 */
	public Map<String, Object> saveLuckActivityAward(Map<String, Object> param)throws Exception;
}
