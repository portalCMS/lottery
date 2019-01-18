package com.lottery.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lottery.bean.entity.UserCard;

@Service
public interface IUserCardService {

	/**
	 * 根据用户ID 获取用户绑定银行卡 
	 * @param param
	 * @return 返回对象为用户绑定的卡集合
	 * @throws Exception
	 */
	public List<UserCard> queryUserCardByUserId(final Map<String, Object> param) throws Exception;
	
	/**
	 * 根据用户ID 获取用户绑定银行卡不改变实体bean的内容）
	 * @param param
	 * @return 返回对象为用户绑定的卡集合
	 * @throws Exception
	 */
	public List<UserCard> queryUserCardByUserIdNC(final Map<String, Object> param) throws Exception;
	
	/**
	 * 根据用户ID 获取用户绑定银行卡 
	 * @param param
	 * @return 返回字段数组
	 * @throws Exception
	 */
	public List<Object[]> queryUserCardBycustomerId(final Map<String,Object> param) throws Exception;

	/**
	 * 
	 * @param param
	 * @return 返回对象为用户绑定的卡
	 * @throws Exception
	 */
	public UserCard queryUserCardById(final Map<String, Object> param) throws Exception;

}
