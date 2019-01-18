package com.lottery.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lottery.bean.entity.CustomerBindCard;

@Service
public interface ICustomerBindCardService{

	/**
	 * 查询用户绑定卡集合
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<CustomerBindCard> queryBindCardById(final Map<String,?>param) throws Exception;
	
	/**
	 * 保存用户绑定卡信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String saveBindCardByUser(final Map<String,?> param) throws Exception;
	
	/**
	 * 查询用户卡信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> queryCardUserInfo(final Map<String,?> param)throws Exception;  
}
