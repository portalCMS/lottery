package com.lottery.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.AdminParameter;
import com.lottery.persist.generice.IGenericDao;
import com.xl.lottery.exception.LotteryException;

@Repository
public interface IAdminParameterDao extends IGenericDao<AdminParameter>{

	public Map<String, String> queryParameterList(String parameterName, String[] parameterKeys) throws LotteryException;

	public boolean setParameterValue(String parameterName, Map<String,String> keyValueMap,String userName) throws LotteryException;
	
	public String getParameterByNameAndIndex(String name,int keyIndex,String keyValue)throws Exception;

}
