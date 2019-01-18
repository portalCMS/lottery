package com.lottery.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.bean.entity.TaskLog;
import com.lottery.bean.entity.vo.LotteryTypeVO;
import com.lottery.dao.ITaskLogDao;
import com.lottery.service.ITaskLogService;
import com.xl.lottery.util.DateUtil;


@Service
public class TaskLogServiceImpl implements ITaskLogService{

	@Autowired
	private ITaskLogDao logDao;
	@Override
	public void saveTaskLog(Map<String, Object> param) throws Exception {
		TaskLog taskLog = (TaskLog) param.get("taskLogKey");
		taskLog.setCreateTime(DateUtil.getNowDate());
		taskLog.setUpdateTime(DateUtil.getNowDate());
		taskLog.setCreateUser("t");
		taskLog.setUpdateUser("t");
		
		logDao.insert(taskLog);
	}
	@Override
	public List<TaskLog> queryTaskLog(Map<String, Object> param)
			throws Exception {
		LotteryTypeVO vo = (LotteryTypeVO) param.get("lotteryKey");
		return logDao.queryLogByType(vo);
	}

	
}
