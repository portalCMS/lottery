package com.lottery.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lottery.bean.entity.TaskLog;

@Service
public interface ITaskLogService {

	/**
	 * 保存job日志
	 * @param param1
	 * @throws Exception
	 */
	public void saveTaskLog(final Map<String, Object> param1) throws Exception;

	/**
	 * 查询JOB集合
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<TaskLog> queryTaskLog(final Map<String, Object> param) throws Exception;

}
