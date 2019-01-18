package com.lottery.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lottery.bean.entity.CustomerActivity;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.CustomerActivityVO;

@Service
public interface ICustomerActivityService {

	/**
	 * 保存投注、中奖、累计充值模板
	 * @param param
	 * @throws Exception
	 */
	public void saveBetActivity(Map<String, Object> param) throws Exception;
	
	/**
	 * 保存首次充值、注册就送模板
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String saveRegTempl(final Map<String,Object> param)throws Exception;
	/**
	 * 查询当前活动
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<CustomerActivity> queryActivityList(Map<String, Object> param) throws Exception;
	/**
	 * 查询活动介绍
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public CustomerActivityVO queryActivityDetail(Map<String, Object> param) throws Exception;
	/**
	 * 领取活动奖励
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> saveActivityAward(Map<String, Object> param) throws Exception;
	
	/**
	 * 后台查询活动翻页
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<CustomerActivityVO, CustomerActivity> queryActivitys(final Map<String,Object> param)throws Exception;
	/**
	 * 查询我的活动记录
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	public Page<CustomerActivityVO, CustomerActivity> querMyActivityRecord(Map<String, Object> param) throws Exception;
	/**
	 * 保存注册活动
	 * @param param
	 * @throws Exception
	 */
	public void saveRegActivity(Map<String, Object> param)throws Exception;
	/**
	 * 保存首次充值活动
	 * @param param
	 * @throws Exception
	 */
	public void saveFrcActivity(Map<String, Object> param)throws Exception;
	
	
	/**
	 * 修改 george
	 * @param param
	 * @throws Exception
	 */
	public void updateFrcActivity(Map<String, Object> param) throws Exception;
	
	/**
	 * 查询活动信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public CustomerActivity showActivityOrder(Map<String, Object> param)throws Exception;
	
	/**
	 * 修改
	 * george
	 * @param param
	 * @throws Exception
	 */
	public void updateRegActivity(Map<String, Object> param) throws Exception;
	
	/**
	 * 根据ID查询
	 * george
	 * @param id
	 * @return
	 */
	public CustomerActivityVO queryCustomerActivityById(long id)throws Exception;
	
	/**
	 * 游戏活动保存修改
	 * george
	 * @param param
	 * @throws Exception
	 */
	public void updatesBetActivity(Map<String, Object> param)throws Exception;
	/**
	 * 抽奖活动保存修改
	 * george
	 * @param param
	 * @throws Exception
	 */
	public void saveLuckActivity(Map<String, Object> param)throws Exception;
}
