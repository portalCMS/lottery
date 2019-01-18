package com.lottery.dao.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.object.SqlQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.lottery.bean.entity.AdminParameter;
import com.lottery.bean.entity.LotteryAwardRecord;
import com.lottery.bean.entity.LotteryType;
import com.lottery.bean.entity.TaskConfig;
import com.lottery.bean.entity.TaskLog;
import com.lottery.bean.entity.vo.LotteryTypeVO;
import com.lottery.bean.entity.vo.TaskConfigVO;
import com.lottery.bean.entity.vo.TempMapVO;
import com.lottery.dao.IAdminParameterDao;
import com.lottery.dao.ILotteryTypeDao;
import com.lottery.dao.ITaskConfigDao;
import com.lottery.persist.generice.GenericDAO;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;
import com.xl.lottery.util.DateUtil;

@Repository
public class TaskConfigDaoImpl extends GenericDAO<TaskConfig> implements ITaskConfigDao{

	@Autowired
	private ILotteryTypeDao typeDao;
	
	public TaskConfigDaoImpl() {
		super(TaskConfig.class);
	}

	@Override
	public List<TaskConfig> queryTaskByCode(TaskConfigVO taskVo) throws Exception {
		StringBuffer hql = new StringBuffer("from TaskConfig where  lotteryCode = ?  ");
		//获取追期时设置了该属性
		String startBetTime =taskVo.getStartBetTime();
		if(!StringUtils.isEmpty(startBetTime)){
			//开始时间大于结束时间，即跨天的奖期
			hql.append("and ((endBetTime >= ? and taskDate=?) or taskDate > ? or (startBetTime>endBetTime and taskDate=? )) ");
		}else if(!StringUtils.isEmpty(taskVo.getLotteryGroup())
				&&taskVo.getLotteryGroup().equals(CommonUtil.LOTTERY_GROUP_3D)
				&&taskVo.getRepeatTimes()==1){
			//如果是低频彩种,则查询taskDate大于等于昨天的奖期。
			hql.append(" and taskDate >= ?  ");
		}else if(StringUtils.isEmpty(taskVo.getTaskParam())){
			hql.append(" and taskDate = ?  ");
		}
		if(!StringUtils.isEmpty(startBetTime)||
				(!StringUtils.isEmpty(taskVo.getLotteryGroup())
						&&taskVo.getLotteryGroup().equals(CommonUtil.LOTTERY_GROUP_3D)
						&&taskVo.getRepeatTimes()==1)){
			hql.append("order by lotterySeries asc ");
		}else{
			hql.append("order by lotterySeries desc ");
		}
		
		
		Query query = getSession().createQuery(hql.toString());
		query.setParameter(0, taskVo.getLotteryCode());
		if(!StringUtils.isEmpty(startBetTime)){
			startBetTime = startBetTime.substring(startBetTime.length()-8,startBetTime.length());
			query.setParameter(1, startBetTime);
			query.setParameter(2, DateUtil.getStringDateShort());
			query.setParameter(3, DateUtil.getStringDateShort());
			query.setParameter(4, DateUtil.getStringDateShort());
		}else if(!StringUtils.isEmpty(taskVo.getLotteryGroup())
				&&taskVo.getLotteryGroup().equals(CommonUtil.LOTTERY_GROUP_3D)
				&&taskVo.getRepeatTimes()==1){
			//如果是低频彩种,则查询taskDate大于等于昨天的奖期。
			query.setParameter(1, DateUtil.getNextDay(DateUtil.getStringDateShort(), "-1"));
		}else if(StringUtils.isEmpty(taskVo.getTaskParam())){
			query.setParameter(1, DateUtil.getStringDateShort());
		}
		return query.list();
	}

	@Override
	public TaskConfig queryCurrentTask(Map<String, Object> param)
			throws Exception {
		LotteryTypeVO l = (LotteryTypeVO) param.get("lotteryKey");
		
		List<Integer> statusList = new ArrayList<Integer>();
		statusList.add(DataDictionaryUtil.STATUS_OPEN);
		statusList.add(DataDictionaryUtil.STATUS_DELETE);
		//查询当前期时，从奖期执行时间表达式中截取到执行日期，再加上奖期的截止投注时间必须大于当前时间。
		//另外，执行日期加上奖期的开奖时间也必须大于当前时间（这个是防止0点执行的任务查询错误）
		String sql ="select * from t_lottery_task where lottery_code =:ltc and task_status in (:sts) "
				+ " and CONCAT(lot_date,' ',end_bet_time)>:ebt  AND CONCAT(lot_date,' ',lot_time)>:ebt "
				+ " order by lottery_series asc ";
		
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("ltc", l.getLotteryCode());
		query.setParameterList("sts", statusList);
		query.setParameter("ebt", DateUtil.getStringDate());
		query.addEntity(TaskConfig.class);
		List<TaskConfig> taskList = query.list();
		if(taskList.size()>0){
			return taskList.get(0);
		}else{
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String,String> queryCurrentAllTask(Map<String, Object> param)
			throws Exception {
		LotteryTypeVO typevo = new LotteryTypeVO();
		typevo.setLotteryStatus(10002);
		param.put("lotteryKey",typevo);
//		List<LotteryType> types = typeDao.queryLotteryList(param);
//		String type = "";
//		for(LotteryType typ:types){
//			if(type.equals("")){
//				type+=typ.getLotteryCode();
//			}else{
//				type+=","+typ.getLotteryCode();
//			}
//		}
		String sql ="select t.lottery_code,min(t.lottery_series) from t_lottery_task t where task_status=:sts "
				+ " and CONCAT(lot_date,' ',end_bet_time)>:ebt AND CONCAT(lot_date,' ',lot_time)>:ebt "
				+ " group by lottery_code ";
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("sts", DataDictionaryUtil.STATUS_OPEN);
		String nowStr = DateUtil.getStringDate();
		query.setParameter("ebt", nowStr);
		//query.addEntity(TaskConfig.class);
		List<Object[]> taskList = query.list();
		Map<String,String> map = new HashMap<String, String>();
		for(Object[] obj:taskList){
			map.put(obj[0].toString(), obj[1].toString());
		}
		return map;
	}
	
	@Override
	public Map<String, List<TempMapVO>> countTaskStatus(Map<String, Object> param)
			throws Exception {
		LotteryTypeVO l = (LotteryTypeVO) param.get("lotteryKey");
		
		Map<String,List<TempMapVO>> countMap = new HashMap<String,List<TempMapVO>>();
		
		String sql =" SELECT COUNT(1),STATUS,lottery_code,lottery_name "
				+ " FROM t_lottery_award_record "
				+ " WHERE STATUS IN (:sts) AND SUBSTRING(issue,1,6) = :td "
				+ " GROUP BY lottery_code "
				+ " UNION ALL SELECT COUNT(1),STATUS,lottery_code,lottery_name "
				+ " FROM t_lottery_award_record "
				+ " WHERE STATUS IN (:fts) AND SUBSTRING(issue,1,6) = :td "
				+ " GROUP BY lottery_code ";
		//统计彩种执行成功和失败的奖期任务数量
		SQLQuery query = getSession().createSQLQuery(sql);
		List<Integer> sts = new ArrayList<Integer>();
		sts.add(DataDictionaryUtil.STATUS_SUCCESS);
		sts.add(DataDictionaryUtil.HAND_STATUS_SUCCESS);
		
		List<Integer> fts = new ArrayList<Integer>();
		fts.add(DataDictionaryUtil.STATUS_FAILED);
		fts.add(DataDictionaryUtil.HAND_STATUS_FAILED);
		
		query.setParameterList("sts", sts.toArray());
		query.setParameterList("fts", fts.toArray());
		query.setParameter("td", DateUtil.getStringDateShort2());
		List<Object[]> counts = query.list();
		
		List<TempMapVO> vos = new ArrayList<TempMapVO>();
		for(Object[] objs : counts){
			TempMapVO tempVo = new TempMapVO();
			tempVo.setKey(objs[2].toString());
			tempVo.setValue(objs[0].toString());
			tempVo.setValue2(objs[1].toString());
			tempVo.setValue3(objs[3].toString());
			vos.add(tempVo);
		}
		
		countMap.put("sfVos", vos);
		
		//查询彩种的当前期号
		String sql1 = "SELECT lottery_code,MIN(lottery_series),lottery_name FROM t_lottery_task "
				+ " WHERE task_status= :st "
				+ " AND CONCAT(lot_date,' ',end_bet_time)>:td AND CONCAT(lot_date,' ',lot_time)>:td"
				+ " GROUP BY lottery_code ";
		query = getSession().createSQLQuery(sql1);
		query.setParameter("st", DataDictionaryUtil.STATUS_OPEN);
		query.setParameter("td", DateUtil.getStringDate());
		List<Object[]> counts2 = query.list();
		
		List<TempMapVO> curVos = new ArrayList<TempMapVO>();
		for(Object[] objs : counts2){
			TempMapVO tempVo = new TempMapVO();
			tempVo.setKey(objs[0].toString());
			tempVo.setValue(objs[1].toString());
			tempVo.setValue2(objs[2].toString());
			curVos.add(tempVo);
		}
		
		countMap.put("curVos", curVos);
		
		return countMap;
	}

	@Override
	public void deleteTaskByJob(Map<String, Object> param) throws Exception {
		List<LotteryType> list = (List<LotteryType>) param.get("lotteryKey");
		boolean isDelete = (Boolean) param.get("deleteKey");
		//判断当天已生成的总奖期任务是否与
		String sql ="";
		if(isDelete){
			sql ="delete from t_lottery_task where lottery_code=?  ";
		}else{
			sql ="delete from t_lottery_task where lottery_code=? and task_date != ?  ";
		}
		
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter(0, list.get(0).getLotteryCode());
		if(!isDelete){
			query.setParameter(1, DateUtil.getStringDateShort());
		}
		query.executeUpdate();
	}

	@Override
	public List<TaskConfig> batchDeleteTask(Map<String, Object> param)
			throws Exception {
		TaskConfigVO taskVo = (TaskConfigVO) param.get("taskKey");
		String idListStr = taskVo.getIdList();
		String[] idList = idListStr.split(",");
		
		String sql ="select * from t_lottery_task where id in (:ids) ";
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameterList("ids", idList);
		query.addEntity(TaskConfig.class);
		List<TaskConfig> list = query.list();
		
		sql ="delete from t_lottery_task where id in (:ids) ";
		query = getSession().createSQLQuery(sql);
		query.setParameterList("ids", idList);
		query.executeUpdate();
		return list;
	}

	@Override
	public List<TaskConfig> queryFurtherTask(Map<String, Object> param)
			throws Exception {
		String lotteryCode = (String) param.get("lotCode");
		String curIssueNo = (String) param.get("curIssueNo");
		//查询大于当前期的奖期任务
		String sql ="select * from t_lottery_task where lottery_code = ? and task_status = ? and lottery_series >= ? ";
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter(0, lotteryCode);
		query.setParameter(1, DataDictionaryUtil.STATUS_OPEN);
		query.setParameter(2, curIssueNo);
		query.addEntity(TaskConfig.class);
		List<TaskConfig> list = query.list();
		return list;
	}

	@Override
	public TaskConfig queryTaskByCodeAndIssue(Map<String, Object> param)
			throws Exception {
		LotteryTypeVO lotVo = (LotteryTypeVO) param.get("lotteryKey");
		String curIssueNo = (String) param.get("issueNo");
		//查询大于当前期的奖期任务
		String sql ="select * from t_lottery_task where lottery_code = ? and task_status = ? and lottery_series = ? ";
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter(0, lotVo.getLotteryCode());
		query.setParameter(1, DataDictionaryUtil.STATUS_OPEN);
		query.setParameter(2, curIssueNo);
		query.addEntity(TaskConfig.class);
		List<TaskConfig> list = query.list();
		if(list==null||list.size()==0){
			return null;
		}
		return list.get(0);
	}

	
}
