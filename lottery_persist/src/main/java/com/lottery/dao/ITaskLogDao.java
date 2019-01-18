package com.lottery.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.TaskLog;
import com.lottery.bean.entity.vo.LotteryTypeVO;
import com.lottery.persist.generice.IGenericDao;

@Repository
public interface ITaskLogDao extends IGenericDao<TaskLog>{

	public List<TaskLog> queryLogByTaskId(long taskId) throws Exception;

	public List<TaskLog> queryLogByType(LotteryTypeVO vo) throws Exception;
}
