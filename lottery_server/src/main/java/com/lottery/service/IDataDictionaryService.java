package com.lottery.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lottery.bean.entity.DataDictionary;

@Service
public interface IDataDictionaryService{

	/**
	 * 根据父ID查询所有子字典信息集合
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<DataDictionary> getDataDictionarysByFid(final Map<String, Object> param)throws Exception;
	
	/**
	 * 根据SID查询字典名称
	 * @param sid
	 * @return
	 * @throws Exception
	 */
	public String getSnameBySid(long sid)throws Exception;
	
	/**
	 * 以map形式返回所有字典
	 * @return
	 * @throws Exception
	 */
	public Map<Long,String> queryAllDataDictionary()throws Exception;
	
	/**
	 * 启动加载所有字典
	 * @return
	 * @throws Exception
	 */
	public List<DataDictionary> getAllDataDictionary()throws Exception;
}
