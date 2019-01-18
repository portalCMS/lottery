package com.lottery.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lottery.bean.entity.LotteryType;
import com.lottery.bean.entity.TaskConfig;

@Service
public interface ITaskConfigService {

	/**
	 * 查询根据彩种code查询奖期配置
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<TaskConfig> queryTaskByCode(final Map<String,?> param)throws Exception;

	/**
	 * 保存并返回奖期信息集合
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<TaskConfig> saveTaskConfig(final Map<String, ?> param)throws Exception;

	/**
	 * 更新并返回奖期信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public TaskConfig updateTask(final Map<String, Object> param)throws Exception;

	/**
	 * 删除并返回奖期信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public TaskConfig deleteTask(final Map<String, Object> param)throws Exception;

	/**
	 * 刷新奖期信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public void refreshLotterySeries(Map<String, Object> param)throws Exception;

	/**
	 * 保存并返回奖期信息集合根据job
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<TaskConfig> saveTaskByJob(Map<String, Object> param)throws Exception;

	/**
	 * 批量删除奖期信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<TaskConfig> deleteBatchTask(Map<String, Object> param)throws Exception;

	/**
	 * 更新奖期信息状态
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public void updateTaskStatus(final Map<String, Object> param)throws Exception;

	/**
	 * 查询现在的奖期
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public TaskConfig queryCurrentTask(final Map<String, Object> param)throws Exception;

	/**
	 * 查询奖期任务
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public void deleteTaskByJob(final Map<String, Object> param) throws Exception;

	/**
	 * 查询奖期信息根据奖期ID
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public TaskConfig queryTaskById(final long id)throws Exception;

	/**
	 * 查询大于当前期的将来要执行的奖期任务
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<TaskConfig> queryFurtherTask(final Map<String, Object> param)throws Exception;
	/**
	 * 保存并返回奖期信息集合根据job
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<TaskConfig> saveLowTaskByJob(Map<String, Object> param)throws Exception;
	/**
	 * 根据彩种代码和奖期号查询奖期
	 * @param param
	 */
	public TaskConfig queryTaskByCodeAndIssue(final Map<String, Object> param)throws Exception;
	/**
	 * 查询奖期任务集合
	 * @param param
	 * @throws Exception
	 */
	public List<TaskConfig> queryTaskList(final Map<String, Object> param)throws Exception;
}
