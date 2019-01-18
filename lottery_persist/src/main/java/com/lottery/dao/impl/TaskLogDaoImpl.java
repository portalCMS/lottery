package com.lottery.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.TaskLog;
import com.lottery.bean.entity.vo.LotteryTypeVO;
import com.lottery.dao.ITaskLogDao;
import com.lottery.persist.generice.GenericDAO;

@Repository
public class TaskLogDaoImpl extends GenericDAO<TaskLog> implements ITaskLogDao{

	public TaskLogDaoImpl() {
		super(TaskLog.class);
	}

	/**
	 * 只返回最近的10条日志记录
	 */
	@Override
	public List<TaskLog> queryLogByTaskId(long taskId)
			throws Exception {
		String hql ="from TaskLog t where t.taskId = ? order by t.id desc";
		Query query = getSession().createQuery(hql);
		query.setParameter(0, taskId);
		query.setMaxResults(10);  
		query.setFirstResult(0);  
		List<TaskLog> logList = query.list();
		return logList;
	}

	@Override
	public List<TaskLog> queryLogByType(LotteryTypeVO vo) throws Exception {
		String hql ="from TaskLog t where t.taskId = ? and t.taskType = ? order by t.id desc";
		Query query = getSession().createQuery(hql);
		query.setParameter(0, vo.getId());
		query.setParameter(1, vo.getLotteryJobName());
		query.setMaxResults(10);  
		query.setFirstResult(0);  
		List<TaskLog> logList = query.list();
		return logList;
	}

	
}
