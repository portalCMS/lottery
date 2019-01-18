package com.lottery.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.DataDictionary;
import com.lottery.persist.generice.IGenericDao;

@Repository
public interface IDataDictionaryDao extends IGenericDao<DataDictionary> {

	/**
	 * 根据父ID返回父名称
	 * @param fid
	 * @author CW-HP7
	 * @return
	 * @throws Exception
	 */
	public String findFnameByFid(final Map<String,?> param)throws Exception;
	
	/**
	 * 根据子ID返回子名称
	 * @param sid
	 * @author CW-HP7
	 * @return
	 * @throws Exception
	 */
	public String findSnameBySid(final Map<String,?> param)throws Exception;
	
}
