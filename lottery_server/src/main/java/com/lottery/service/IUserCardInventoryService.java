package com.lottery.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lottery.bean.entity.UserCardInventory;

@Service
public interface IUserCardInventoryService {
	
	/**
	 * 查询卡库信息集合
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<UserCardInventory> queryInventorys(final Map<String, Object> param)throws Exception;

	/**
	 * 保存卡库
	 * @param param
	 * @throws Exception
	 */
	public void insertCardInv(final Map<String, Object> param)throws Exception;

	/**
	 * 更新卡库
	 * @param param
	 * @throws Exception
	 */
	public void updateCardInv(final Map<String, Object> param)throws Exception;

}
