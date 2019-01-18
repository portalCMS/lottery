package com.lottery.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.AdminParameter;
import com.lottery.bean.entity.TaskConfig;
import com.lottery.bean.entity.vo.TaskConfigVO;
import com.lottery.bean.entity.vo.TempMapVO;
import com.lottery.persist.generice.IGenericDao;
import com.xl.lottery.exception.LotteryException;

@Repository
public interface ITaskConfigDao extends IGenericDao<TaskConfig>{

	public List<TaskConfig> queryTaskByCode(TaskConfigVO taskVo) throws Exception;

	public TaskConfig queryCurrentTask(final Map<String, Object> param) throws Exception;

	public Map<String, List<TempMapVO>> countTaskStatus(final Map<String, Object> param) throws Exception;

	public void deleteTaskByJob(final Map<String, Object> param) throws Exception;

	public List<TaskConfig> batchDeleteTask(final Map<String, Object> param)throws Exception;

	public Map<String,String> queryCurrentAllTask(final Map<String, Object> param) throws Exception;

	public List<TaskConfig> queryFurtherTask(final Map<String, Object> param) throws Exception;

	public TaskConfig queryTaskByCodeAndIssue(final Map<String, Object> param) throws Exception;
}
