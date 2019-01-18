package com.lottery.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.lottery.bean.entity.Page;

@Service
public interface IReportService {

	/**
	 * 前台用户获取盈亏报表
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Object,Object> queryYkReport(Map<String, Object> param)throws Exception;
	
	/**
	 * 后台用户获取盈亏报表
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Object,Object> queryYkReportAdmin(Map<String, Object> param)throws Exception;
	/**
	 * 分页查询团队盈亏历史记录。
	 * @param param
	 * @return
	 * @throws Exception
	 */
	
	Page<Object, Object> queryYkRecord(Map<String, Object> param) throws Exception;
}
