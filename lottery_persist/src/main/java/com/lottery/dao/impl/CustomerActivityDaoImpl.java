package com.lottery.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.jsoup.helper.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ch.qos.logback.classic.util.LevelToSyslogSeverity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.lottery.activity.rule.model.AwardLevelTemp;
import com.lottery.activity.rule.model.BetTempl;
import com.lottery.activity.rule.model.ConfigTemp;
import com.lottery.activity.rule.model.FrcTempl;
import com.lottery.activity.rule.model.LuckTempl;
import com.lottery.activity.rule.model.RegisterTempl;
import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.CustomerActivity;
import com.lottery.bean.entity.CustomerActivityLog;
import com.lottery.bean.entity.CustomerCash;
import com.lottery.bean.entity.CustomerOrder;
import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.LuckAwardRecord;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.CustomerActivityVO;
import com.lottery.bean.entity.vo.CustomerOrderVO;
import com.lottery.dao.ICustomerActivityDao;
import com.lottery.dao.ICustomerActivityLogDao;
import com.lottery.dao.ICustomerCashDao;
import com.lottery.dao.ICustomerOrderDao;
import com.lottery.dao.ILuckAwardRecordDao;
import com.lottery.dao.IOrderSequenceDao;
import com.lottery.persist.generice.GenericDAO;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;
import com.xl.lottery.util.DateUtil;
import com.xl.lottery.util.DozermapperUtil;
import com.xl.lottery.util.JsonUtil;

@Repository
public class CustomerActivityDaoImpl extends GenericDAO<CustomerActivity> implements ICustomerActivityDao{
	@Autowired
	private IOrderSequenceDao orderSequenceDao;
	
	@Autowired
	private ICustomerOrderDao orderDao;
	
	@Autowired
	private ICustomerCashDao cashDao;
	
	@Autowired
	private ICustomerActivityLogDao logDao;
	
	@Autowired
	private ILuckAwardRecordDao luckDao;
	
	public CustomerActivityDaoImpl() {
		super(CustomerActivity.class);
	}

	@Override
	public void saveBetActivity(Map<String, Object> param) throws Exception {
		CustomerActivityVO vo = (CustomerActivityVO) param.get("activityKey");
		AdminUser user = (AdminUser) param.get(CommonUtil.USERKEY);
		
		String ruleJson = JsonUtil.objToJson(vo.getBetTempl());
		vo.setRule(ruleJson);
		
		CustomerActivity activity = new CustomerActivity();
		DozermapperUtil.getInstance().map(vo, activity);
		activity.setStatus(DataDictionaryUtil.STATUS_OPEN);
		activity.addInit(user.getUserName());
		
		this.save(activity);
	}

	@Override
	public Page<CustomerActivityVO, CustomerActivity> queryActivitys(
			Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		CustomerActivityVO vo = (CustomerActivityVO) param.get("activityVO");
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("from CustomerActivity t where 1=1 ");
		StringBuffer count = new StringBuffer("select count(t.id) from CustomerActivity t where 1=1 ");
		if(!StringUtils.isEmpty(vo.getStarttime())){
			hql = hql.append(" and starttime >= ? ");
			count = count.append(" and starttime >= ? ");
			params.add(vo.getStarttime());
		}
		if(!StringUtils.isEmpty(vo.getEndtime())){
			hql = hql.append(" and endtime <= ? ");
			count = count.append(" and endtime <= ? ");
			params.add(vo.getEndtime());
		}
		if(!StringUtils.isEmpty(vo.getTitle())){
			hql = hql.append(" and title = ? ");
			count = count.append(" and title = ? ");
			params.add(vo.getTitle());
		}
		if(!StringUtils.isEmpty(vo.getType())){
			hql = hql.append(" and type = ? ");
			count = count.append(" and type = ? ");
			params.add(vo.getType());
		}
		if(vo.getCustomerId()!=0){
			hql = hql.append(" and t.id in (select activityId from CustomerActivityLog t1 where t1.customerId=? ) ");
			count = count.append(" and t.id in (select activityId from CustomerActivityLog t1 where t1.customerId=? ) ");
			params.add(vo.getCustomerId());
		}
		hql.append(" order by createTime desc ");
		count.append(" order by createTime desc ");
		
		int maxCount = this.getActivityCount(count.toString(),params);
		Page<CustomerActivityVO, CustomerActivity> page = new Page<CustomerActivityVO, CustomerActivity>();
		int pageNum = vo.getPageNum();
		int maxY = maxCount/vo.getMaxCount();
		if(maxCount%page.getMaxCount()!=0){
			maxY+=1;
		}
		pageNum = pageNum<=0?1:pageNum;
		pageNum = pageNum>=maxY?maxY:pageNum;
		page.setPageNum(pageNum);
		Query query = getSession().createQuery(hql.toString());  
		int i = 0;
		for(Object obj : params){
			query.setParameter(i, obj);
			i++;
		}
		query.setMaxResults(vo.getMaxCount());  
		query.setFirstResult((pageNum-1)*vo.getMaxCount());    
		@SuppressWarnings("unchecked")
		List<CustomerActivity> entitylist = query.list();    
		page.setEntitylist(entitylist);
		page.setPageCount(maxY);
		return page;
	}
	
	private Integer getActivityCount(String count,List<Object> params){
		Query query = getSession().createQuery(count);  
		for(int i=0;i<params.size();i++){
			query.setParameter(i, params.get(i));
		}
		Long countNum= (Long) query.list().get(0);
		return countNum.intValue();
	}
	

	@Override
	public List<CustomerActivity> queryActivityList(Map<String, Object> param)
			throws Exception {
		
		String sql = "select * from t_customer_activity where activity_status = :sts order by id desc ";
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("sts", DataDictionaryUtil.STATUS_OPEN);
//		query.setParameter("nt", DateUtil.getStringDate());
		query.addEntity(CustomerActivity.class);
		List<CustomerActivity> list = query.list();
		return list;
	}

	@Override
	public CustomerActivityVO queryActivityDetail(Map<String, Object> param)
			throws Exception {
		CustomerActivityVO actVo = (CustomerActivityVO) param.get("activityKey");
		CustomerActivity act = this.queryById(actVo.getId());
		CustomerActivityVO returnVo = new CustomerActivityVO();
		DozermapperUtil.getInstance().map(act, returnVo);
		returnVo.setTypeName(CommonUtil.actNameMap.get(returnVo.getType()));
		
		return returnVo;
	}

	@Override
	public Map<String, Object> saveRegActivityAward(
			Map<String, Object> param) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		CustomerUser user = (CustomerUser) param.get(CommonUtil.CUSTOMERUSERKEY);
		CustomerActivity activity = (CustomerActivity) param.get("activity");
		
		RegisterTempl reg = JsonUtil.jsonToObject(activity.getRule(), RegisterTempl.class);
		
		String regsql = "select * from t_customer_user where create_time >= :st and create_time <= :et "
				+ " and customer_status = :usts and id = :uid ";
		SQLQuery query =getSession().createSQLQuery(regsql);
		query.setParameter("st", reg.getRegStartTime());
		query.setParameter("et", reg.getRegEndTime());
		query.setParameter("uid", user.getId());
		query.setParameter("usts", DataDictionaryUtil.STATUS_OPEN);
		query.addEntity(CustomerUser.class);
		List ru = query.list();
		if(ru==null||ru.size()==0){
			throw new LotteryException("亲，您不具备该活动的参与条件，无法领取奖励!");
		}
		
		//判断用户是否已经领取过该奖励(只要是注册送的无论是什么时候的活动奖励)
		String sql = "select * from t_customer_order where order_status in (:sts) and order_detail_type = :dt "
				+ " and customer_id = :uid and rsvst3 = :at ";
		query =getSession().createSQLQuery(sql);
		query.setParameter("uid", user.getId());
		
		List<Integer> sts = new ArrayList<Integer>(2);
		sts.add(DataDictionaryUtil.ORDER_STATUS_SUCCESS);
		sts.add(DataDictionaryUtil.ORDER_STATUS_DISPOSING);
		query.setParameterList("sts", sts.toArray());
		query.setParameter("dt", DataDictionaryUtil.ORDER_DETAIL_DISTRIBUTED_ACTIVITIES);
		query.setParameter("at", activity.getType());
		query.addEntity(CustomerOrder.class);
		List orderList = query.list();
		if(orderList!=null&&orderList.size()>0){
			throw new LotteryException("亲，该活动的奖励您已经申请领取过了！");
		}
		
		//判断用户是否已经领取过该奖励(只要是注册送的无论是什么时候的活动奖励)
		sql = "select * from t_customer_order where order_status in (:sts) and order_detail_type = :dt "
				+ " and customer_id = :uid and rsvst3 = :at ";
		query =getSession().createSQLQuery(sql);
		query.setParameter("uid", user.getId());
		
		sts.clear();
		sts.add(DataDictionaryUtil.ORDER_STATUS_FAILURE);
		query.setParameterList("sts", sts.toArray());
		query.setParameter("dt", DataDictionaryUtil.ORDER_DETAIL_DISTRIBUTED_ACTIVITIES);
		query.setParameter("at", activity.getType());
		query.addEntity(CustomerOrder.class);
		orderList = query.list();
		if(orderList!=null&&orderList.size()>2){
			throw new LotteryException("亲，该活动奖励您申请被拒绝次数已超过3次，无法再进行申请，请联系客服咨询原因，谢谢！");
		}
		//生成活动奖励订单
		returnMap = this.saveActivityOrder(user, reg.getRegStartTime(), reg.getRegEndTime(),
				activity, reg.getAtivityMoney(), returnMap);
		return returnMap;
	}

	@Override
	public Map<String, Object> saveFrcActivityAward(
			Map<String, Object> param) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		CustomerUser user = (CustomerUser) param.get(CommonUtil.CUSTOMERUSERKEY);
		CustomerActivity activity = (CustomerActivity) param.get("activity");
		
		FrcTempl frc = JsonUtil.jsonToObject(activity.getRule(), FrcTempl.class);
		
		//判断用户是否已经领取过该奖励(只要是首次充值送的无论是什么时候的活动奖励)
		String sql = "select * from t_customer_order where order_status in (:sts) and order_detail_type = :dt"
				+ " and customer_id = :uid and rsvst3 = :at ";
		SQLQuery query =getSession().createSQLQuery(sql);
		query.setParameter("uid", user.getId());
		
		List<Integer> sts = new ArrayList<Integer>(2);
		sts.add(DataDictionaryUtil.ORDER_STATUS_SUCCESS);
		sts.add(DataDictionaryUtil.ORDER_STATUS_DISPOSING);
		query.setParameterList("sts", sts.toArray());
		query.setParameter("dt", DataDictionaryUtil.ORDER_DETAIL_DISTRIBUTED_ACTIVITIES);
		query.setParameter("at", activity.getType());
		query.addEntity(CustomerOrder.class);
		List orderList = query.list();
		if(orderList!=null&&orderList.size()>0){
			throw new LotteryException("亲，该活动的奖励您已经申请领取过了！");
		}
		//判断用户是否已经领取过该奖励(只要是注册送的无论是什么时候的活动奖励)
		sql = "select * from t_customer_order where order_status in (:sts) and order_detail_type = :dt "
				+ " and customer_id = :uid and rsvst3 = :at ";
		query =getSession().createSQLQuery(sql);
		query.setParameter("uid", user.getId());
		
		sts.clear();
		sts.add(DataDictionaryUtil.ORDER_STATUS_FAILURE);
		query.setParameterList("sts", sts.toArray());
		query.setParameter("dt", DataDictionaryUtil.ORDER_DETAIL_DISTRIBUTED_ACTIVITIES);
		query.setParameter("at", activity.getType());
		query.addEntity(CustomerOrder.class);
		orderList = query.list();
		if(orderList!=null&&orderList.size()>2){
			throw new LotteryException("亲，该活动奖励您申请被拒绝次数已超过3次，无法再进行申请，请联系客服咨询原因，谢谢！");
		}
				
		String sql2 = " select o.* from t_customer_order o where o.order_status = :sts and o.customer_id = :uid "
				+ " and order_detail_type in (:odts) order by id asc ";
		
		query = getSession().createSQLQuery(sql2);
		query.setParameter("uid", user.getId());
		query.setParameter("sts", DataDictionaryUtil.ORDER_STATUS_SUCCESS);
		
		List<Integer> odts = new ArrayList<Integer>();
		odts.add(DataDictionaryUtil.ORDER_DETAIL_CASH_ADD);
		odts.add(DataDictionaryUtil.ORDER_DETAIL_OTHER_PAY);
		query.setParameterList("odts", odts);
		query.addEntity(CustomerOrder.class);
		List<CustomerOrder> list = query.list();
		BigDecimal sum = BigDecimal.ZERO;
		if(list!=null&&list.size()>0){
			sum = list.get(0).getReceiveAmount();
		}
		
		if(null!=list&&list.size()>0){
			if(sum.compareTo(frc.getMinGameAmount())<0){
				throw new LotteryException("亲，您首次充值的金额，未到达最低金额"+frc.getMinGameAmount()+"元的标准,无法领取奖励!");
			}
		}else{
			throw new LotteryException("亲，您还未进行过充值,无法领取奖励!");
		}
		
		
		//如果是按比例奖励则，直接计算
		BigDecimal awardAmount = BigDecimal.ZERO;
		awardAmount = sum.multiply(frc.getRateAmount().divide(new BigDecimal(100)));
		
		//如果超过封顶金额，则设置为封顶金额
		if(awardAmount.compareTo(frc.getMaxAwardAmount())>0){
			awardAmount = frc.getMaxAwardAmount();
		}
		
		//生成活动奖励订单
		returnMap = this.saveActivityOrder(user, activity.getStarttime(), activity.getEndtime(),
				activity, awardAmount, returnMap);
		
		return returnMap;
	}

	@Override
	public Map<String, Object> saveBetActivityAward(
			Map<String, Object> param) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		CustomerUser user = (CustomerUser) param.get(CommonUtil.CUSTOMERUSERKEY);
		CustomerActivity activity = (CustomerActivity) param.get("activity");
		
		BetTempl bt = JsonUtil.jsonToObject(activity.getRule(), BetTempl.class);
		
		if(bt.getLimitDays()!=null&&bt.getLimitCount()!=null&&bt.getLimitDays()>0&&bt.getLimitCount()>0){
			String startTime = DateUtil.getNextDay(DateUtil.getStringDateShort(), (-bt.getLimitDays()+1)+"");
			startTime = startTime+" 00:00:00";
			//判断活动名额限制是否已经到达
			String sql =" SELECT o.id FROM t_customer_order o ,t_customer_activity_log l "
					+ " WHERE o.`order_detail_type`= :odt AND o.create_time >= :st "
					+ " AND o.`customer_id` = l.`customer_id` AND l.`activity_id` = o.`source_id` "
					+ " AND o.`order_status`= :sts AND activity_id = :aid GROUP BY o.`customer_id`";
			SQLQuery query = getSession().createSQLQuery(sql);
			query.setParameter("st", startTime);
			query.setParameter("sts", DataDictionaryUtil.ORDER_STATUS_SUCCESS);
			query.setParameter("aid", activity.getId());
			query.setParameter("odt", DataDictionaryUtil.ORDER_DETAIL_DISTRIBUTED_ACTIVITIES);
			List<Object[]> orders = query.list();
			if(orders.size()>=bt.getLimitCount()){
				throw new LotteryException("亲，本活动"+bt.getLimitDays()+"天内的领取名额，已经发放完毕！");
			}
		}
		
		//判断用户在该是否是否已经申请领取过奖励(目前逻辑是 一个区间中只能领取一次奖励，不可以重复领取)
		String awardSql ="select * from t_customer_order where order_status in (:sts) "
				+ " and customer_id = :uid and order_detail_type = :odt and source_id = :sid "
				+ " order by id desc ";
		SQLQuery query = getSession().createSQLQuery(awardSql);
		
		List<Integer> sts = new ArrayList<Integer>(2);
		sts.add(DataDictionaryUtil.ORDER_STATUS_DISPOSING);
		sts.add(DataDictionaryUtil.ORDER_STATUS_SUCCESS);
		
		query.setParameterList("sts", sts.toArray());
		query.setParameter("uid", user.getId());
		query.setParameter("odt", DataDictionaryUtil.ORDER_DETAIL_DISTRIBUTED_ACTIVITIES);
		query.setParameter("sid", activity.getId());
		query.addEntity(CustomerOrder.class);
		List<CustomerOrder> orders = query.list();
		
		String startTime="";
		String endTime="";
		//判断是否是统计历史的数据的活动
		if(!StringUtils.isEmpty(bt.getIsHistory())&&bt.getIsHistory().equals("HIS")){
			if(orders!=null&&orders.size()>0){
				throw new LotteryException("亲，该活动的奖励您已经申请领取过了！");
			}
			startTime = bt.getCountStartTime();
			endTime = bt.getCountEndTime();
		}else{
			//循环周期相关计算
			String startDate = activity.getStarttime().substring(0, activity.getStarttime().length()-9);
			String endDate = activity.getEndtime().substring(0, activity.getEndtime().length()-9);
			Long tdays = DateUtil.getTwoDay(endDate,startDate);
			Long ndays = DateUtil.getTwoDay(DateUtil.getStringDateShort(), activity.getStarttime());
			Long index = ndays/bt.getCycleDays();
			Long maxIndex = tdays/bt.getCycleDays();
			if(bt.getCycleType().equals(DataDictionaryUtil.CYCLE_TYPE_CUR)){
				//当前区间
				startTime = DateUtil.getNextDay(startDate, index*bt.getCycleDays()+"")+" 00:00:00";
				endTime = DateUtil.getStringDate();
				//当前区间已经在最后一个区间的话endTime设置为活动结束时间
				if(maxIndex==index){
					endTime = activity.getEndtime();
				}
				if(null!=orders&&orders.size()>0){
					if(orders.get(0).getRsvst1().equals(startTime)){
						throw new LotteryException("亲，当前活动周期的奖励您已经申请领取过了！");
					}
				}
				
			}else if(bt.getCycleType().equals(DataDictionaryUtil.CYCLE_TYPE_LAS)){
				if(index==0){
					//String time = endTime.substring(activity.getStarttime().length()-8);
					String showTime = DateUtil.getNextDay(startDate, bt.getCycleDays()+"");
					throw new LotteryException("亲，该活动奖励尚不可领取，请在["+showTime+"]再尝试领取！");
				}else{
					//上一区间
					startTime = DateUtil.getNextDay(startDate, (index-1)*bt.getCycleDays()+"")+" 00:00:00";
					endTime = DateUtil.getNextDay(startDate, index*bt.getCycleDays()+"")+" 00:00:00";
					
				}
				
				if(null!=orders&&orders.size()>0){
					if(orders.get(0).getRsvst1().equals(startTime)){
						throw new LotteryException("亲，当前活动周期的奖励您已经申请领取过了！");
					}
				}
				
			}else if(bt.getCycleType().equals(DataDictionaryUtil.CYCLE_TYPE_SUB)){
				if(index==0){
					startTime = activity.getStarttime();
					endTime =  DateUtil.getStringDate();
				}else{
					//递减区间
					endTime = DateUtil.getStringDate();
					//获取当前时间
					String time = endTime.substring(endTime.length()-8);
					startTime = DateUtil.getNextDay(endTime, "-"+index*bt.getCycleDays())+" "+time;
				}
				if(null!=orders&&orders.size()>0){
					//如果是递减区间的，则从上次领取时间开始计算，新的累计区间是否可以领奖。
					startTime = DateUtil.dateToStrLong(orders.get(0).getCreateTime());
				}
			}
		}
		
		
		//统计的订单类型
		List<Integer> odts = new ArrayList<Integer>(2);
		if(activity.getType().equals(DataDictionaryUtil.ACTIVITY_TYPE_AWA)){
			odts.add(DataDictionaryUtil.ORDER_DETAIL_WINNING_REBATES);
		}else if(activity.getType().equals(DataDictionaryUtil.ACTIVITY_TYPE_TRC)){
			odts.add(DataDictionaryUtil.ORDER_DETAIL_CASH_ADD);
			odts.add(DataDictionaryUtil.ORDER_DETAIL_OTHER_PAY);
		}else if(activity.getType().equals(DataDictionaryUtil.ACTIVITY_TYPE_BET)){
			odts.add(DataDictionaryUtil.ORDER_DETAIL_ORDINARY_BETTING);
			odts.add(DataDictionaryUtil.ORDER_DETAIL_TRACK);
		}
		//判断是否是要查询历史表的数据
		String order_table = "t_customer_order";
		if(!StringUtils.isEmpty(bt.getIsHistory())&&bt.getIsHistory().equals("HIS")){
			order_table = "t_customer_order_history";
		}
		String sumSql = "select ifnull(sum(o.receive_amount),0) from  "+order_table+" o where o.customer_id = :uid "
				+ " and o.order_status = :osts and o.order_detail_type in (:odts) "
				+ " and o.create_time >= :st and o.create_time <:et ";
		
		query = getSession().createSQLQuery(sumSql);
		query.setParameter("uid", user.getId());
		query.setParameter("osts", DataDictionaryUtil.ORDER_STATUS_SUCCESS);
		query.setParameter("st", startTime);
		query.setParameter("et", endTime);
		query.setParameterList("odts", odts.toArray());
		
		List<BigDecimal> list = query.list();
		BigDecimal sum = list.get(0);
		if(sum.compareTo(bt.getMinGameAmount())<0){
			throw new LotteryException("亲，本活动您目前累计的参与金额为"
					+ sum + "元，还未到达最低金额"+bt.getMinGameAmount()+"元的标准,请继续努力!");
		}
		
		//如果是按比例奖励则，直接计算
		BigDecimal awardAmount = BigDecimal.ZERO;
		if(null!=bt.getRateAmount()){
			awardAmount = sum.multiply(bt.getRateAmount().divide(new BigDecimal(100)));
		}else{
			//如果是按区间，则循环区间看在哪个区间。
			List<ConfigTemp> cfs = bt.getAmountConfig();
			for(ConfigTemp cf : cfs){
				if(cf.getMaxAmount().compareTo(sum)>0&&cf.getMinAmount().compareTo(sum)<=0){
					awardAmount = cf.getAwardAmount();
					break;
				}
			}
		}
		
		//如果超过封顶金额，则设置为封顶金额
		if(awardAmount.compareTo(bt.getMaxAwardAmount())>0){
			awardAmount = bt.getMaxAwardAmount();
		}
		
		//生成活动奖励订单
		returnMap = this.saveActivityOrder(user, startTime, endTime, activity, awardAmount, returnMap);
		return returnMap;
	}
	
	@Override
	public boolean checkActivityTime(String type, String startTime,
			String endTime) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer("select * from t_customer_activity t where t.activity_status =:sta and t.activity_type =:type ");
		sql.append(" and ((t.activity_starttime <=:stime and t.activity_endtime>=:stime ) ");
		sql.append(" or (t.activity_starttime <=:etime and t.activity_endtime>=:etime )) ");
		Query query = getSession().createSQLQuery(sql.toString());
		query.setParameter("sta", DataDictionaryUtil.STATUS_OPEN);
		query.setParameter("type", type);
		query.setParameter("stime", startTime);
		query.setParameter("etime", endTime);
		if(query.list().size()>0)return true;
		return false;
	}

	@Override
	public Page<CustomerActivityVO, CustomerActivity> querMyActivityRecord(Map<String, Object> param)
			throws Exception {
		CustomerActivityVO actVo = (CustomerActivityVO) param.get("activityKey");
		CustomerUser user = (CustomerUser) param.get(CommonUtil.CUSTOMERUSERKEY);
		String sql ="select t.activity_title,t.activity_picurl,l.create_time"
				+ " ,o.order_number,o.receive_amount,t.id,o.order_status from t_customer_activity_log l,"
				+ " t_customer_activity t,t_customer_order o where t.id=l.activity_id "
				+ " and o.order_status in (:sts) "
				+ " and l.customer_id = :uid and l.order_id = o.id order by l.id desc";
		
		String countsql ="select count(o.id) from t_customer_activity_log l,"
				+ " t_customer_activity t,t_customer_order o where t.id=l.activity_id "
				+ " and o.order_status in (:sts) "
				+ " and l.customer_id = :uid and l.order_id = o.id order by l.id desc";
		
		SQLQuery query = getSession().createSQLQuery(sql);
		SQLQuery countQuery = getSession().createSQLQuery(countsql);
		query.setParameter("uid", user.getId());
		List<Integer> sts = new ArrayList<Integer>(2);
		sts.add(DataDictionaryUtil.ORDER_STATUS_SUCCESS);
		sts.add(DataDictionaryUtil.ORDER_STATUS_DISPOSING);
		query.setParameterList("sts", sts.toArray());
		countQuery.setParameter("uid", user.getId());
		countQuery.setParameterList("sts", sts.toArray());
		
		List<BigInteger> counts = countQuery.list();
		int totalCount = counts.get(0).intValue();
		
		Page<CustomerActivityVO, CustomerActivity> page = new Page<CustomerActivityVO, CustomerActivity>();
		page.setPageNum(actVo.getPageNum());
		int pageNum = actVo.getPageNum();
		int maxY = totalCount/actVo.getMaxCount();
		if(totalCount%actVo.getMaxCount()!=0){
			maxY+=1;
		}
		pageNum = pageNum<=0?1:pageNum;
		pageNum = pageNum>=maxY?maxY:pageNum;
		
		query.setFirstResult((pageNum-1)*actVo.getMaxCount());
		query.setMaxResults(actVo.getMaxCount());
		List<Object[]> objs = query.list();
		List<CustomerActivityVO> voList = new ArrayList<CustomerActivityVO>();
		for(Object[] obj : objs){
			CustomerActivityVO vo = new CustomerActivityVO();
			vo.setBetMultiple(0);
			vo.setTitle(obj[0].toString());
			vo.setPicurl(obj[1].toString());
			vo.setEndtime(obj[2].toString());
			vo.setOrderNumber(obj[3].toString());
			vo.setRsvdc1((BigDecimal) obj[4]);
			vo.setId(Long.parseLong(obj[5].toString()));
			vo.setStatus(Integer.parseInt(obj[6].toString()));
			voList.add(vo);
		}
		page.setPagelist(voList);
		page.setPageCount(maxY);
//		总记录数
		page.setTotalCount(totalCount);
		page.setPageNum(pageNum);
		
		return page;
	}

	@Override
	public void saveRegActivity(Map<String, Object> param) throws Exception {
		CustomerActivityVO vo = (CustomerActivityVO) param.get("activityKey");
		AdminUser user = (AdminUser) param.get(CommonUtil.USERKEY);
		
		String ruleJson = JsonUtil.objToJson(vo.getRegisterTempl());
		vo.setRule(ruleJson);
		
		CustomerActivity activity = new CustomerActivity();
		DozermapperUtil.getInstance().map(vo, activity);
		activity.setStatus(DataDictionaryUtil.STATUS_OPEN);
		activity.addInit(user.getUserName());
		
		this.save(activity);
	}

	/**
	 * 修改
	 * george
	 */
	@Override
	public void updateRegActivity(Map<String, Object> param) throws Exception {
		CustomerActivityVO vo = (CustomerActivityVO) param.get("activityKey");
		AdminUser user = (AdminUser) param.get(CommonUtil.USERKEY);
		CustomerActivity po=this.queryById(vo.getId());
		String ruleJson = JsonUtil.objToJson(vo.getRegisterTempl());
		vo.setRule(ruleJson);
		DozermapperUtil.getInstance().map(vo, po);
		
		po.setStatus(DataDictionaryUtil.STATUS_OPEN);
		po.addInit(user.getUserName());
		po.setUpdateUser(user.getUserName());
		po.setUpdateTime(new Date());
		this.update(po);
	}
	
	
	@Override
	public void saveFrcActivity(Map<String, Object> param) throws Exception {
		CustomerActivityVO vo = (CustomerActivityVO) param.get("activityKey");
		AdminUser user = (AdminUser) param.get(CommonUtil.USERKEY);
		
		String ruleJson = JsonUtil.objToJson(vo.getFrecTemp());
		vo.setRule(ruleJson);
		
		CustomerActivity activity = new CustomerActivity();
		DozermapperUtil.getInstance().map(vo, activity);
		activity.setStatus(DataDictionaryUtil.STATUS_OPEN);
		activity.addInit(user.getUserName());
		
		this.save(activity);
	}
	
	/**
	 * 修改 george
	 * @param param
	 * @throws Exception
	 */
	@Override
	public void updateFrcActivity(Map<String, Object> param) throws Exception {
		CustomerActivityVO vo = (CustomerActivityVO) param.get("activityKey");
		AdminUser user = (AdminUser) param.get(CommonUtil.USERKEY);
		
		String ruleJson = JsonUtil.objToJson(vo.getFrecTemp());
		vo.setRule(ruleJson);
		
		 CustomerActivity po = this.queryById(vo.getId());
		DozermapperUtil.getInstance().map(vo, po);
		po.setStatus(DataDictionaryUtil.STATUS_OPEN);
		po.addInit(user.getUserName());
		po.setUpdateUser(user.getUserName());
		po.setUpdateTime(new Date());
		this.update(po);
	}
	
	/**
	 * 游戏活动修改 george
	 * @param param
	 * @throws Exception
	 */
	@Override
	public void updatesBetActivity(Map<String, Object> param) throws Exception {
		CustomerActivityVO vo = (CustomerActivityVO) param.get("activityKey");
		AdminUser user = (AdminUser) param.get(CommonUtil.USERKEY);
		
		String ruleJson = JsonUtil.objToJson(vo.getBetTempl());
		vo.setRule(ruleJson);
		CustomerActivity po = this.queryById(vo.getId());
		DozermapperUtil.getInstance().map(vo, po);
		po.setStatus(DataDictionaryUtil.STATUS_OPEN);
		po.addInit(user.getUserName());
		po.setUpdateUser(user.getUserName());
		po.setUpdateTime(new Date());
		this.save(po);
	}
	
	private Map<String,Object> saveActivityOrder(CustomerUser user,String startTime,String endTime,
			CustomerActivity activity,BigDecimal awardAmount,Map<String,Object> returnMap) throws Exception{
		CustomerOrder actOrder = new CustomerOrder();
		actOrder.addInit(user.getCustomerName());
		actOrder.setCustomerId(user.getId());
		actOrder.setOrderAmount(awardAmount);
		actOrder.setReceiveAmount(awardAmount);
		actOrder.setCashAmount(awardAmount);
		actOrder.setOrderType(DataDictionaryUtil.ORDER_TYPE_INCOME);
		
		String orderNo = orderSequenceDao.getOrderSequence(CommonUtil.ORDER_DETAIL_BET_ORDER, 8);
		actOrder.setOrderNumber(orderNo);
		actOrder.setOrderDetailType(DataDictionaryUtil.ORDER_DETAIL_DISTRIBUTED_ACTIVITIES);
		actOrder.setOrderStatus(DataDictionaryUtil.ORDER_STATUS_DISPOSING);
		actOrder.setOrderTime(new Date());
		//来源活动id,活动标题
		actOrder.setSourceId(activity.getId());
		actOrder.setRemark(activity.getTitle());
		//保存当次领取活动的所在活动区间时间
		actOrder.setRsvst1(startTime);
		actOrder.setRsvst2(endTime);
		actOrder.setRsvst3(activity.getType());
		
		//如果是手动，需要审核
		if(activity.getModel().equals("HAND")){
			orderDao.insert(actOrder);
			returnMap.put("showInfo", "恭喜，您的活动奖励领取申请已经提交，稍后审核后即可获得奖励金额"+awardAmount+"元!");
		}else{
			//如果非手动，则直接生成订单，并增加金额。
			actOrder.setOrderStatus(DataDictionaryUtil.ORDER_STATUS_SUCCESS);
			
			CustomerCash cash = cashDao.queryUserCashByCustomerId(user.getId());
			BigDecimal userMoney = cash.getCash();
			cash.setCash(userMoney.add(awardAmount));
			cash.updateInit(user.getCustomerName());
			cashDao.update(cash);
			//记录用户余额
			actOrder.setAccountBalance(cash.getCash());
			orderDao.insert(actOrder);
			
			returnMap.put("showInfo", "恭喜，您的活动奖励已经发放，奖励金额为"+awardAmount+"元!");
		}
		
		//插入活动领取日志。
		CustomerActivityLog log = new CustomerActivityLog();
		log.setActivityId(activity.getId());
		log.setCustomerId(user.getId());
		log.setOrderId(actOrder.getId());
		log.addInit(user.getCustomerName());
		logDao.insert(log);
		
		returnMap.put("result", "success");
		
		return returnMap;
	}

	@Override
	public void saveLuckActivity(Map<String, Object> param) throws Exception {
		CustomerActivityVO vo = (CustomerActivityVO) param.get("activityKey");
		AdminUser user = (AdminUser) param.get(CommonUtil.USERKEY);
		
		String ruleJson = JsonUtil.objToJson(vo.getLuckTempls());
		vo.setRule(ruleJson);
		
		CustomerActivity activity = new CustomerActivity();
		DozermapperUtil.getInstance().map(vo, activity);
		activity.setStatus(DataDictionaryUtil.STATUS_OPEN);
		activity.addInit(user.getUserName());
		
		this.save(activity);
	}

	@Override
	public Map<String, Object> saveLuckActivityAward(Map<String, Object> param)
			throws Exception {
		// 解析活动模板
		Map<String, Object> returnMap = new HashMap<String, Object>();
		CustomerUser user = (CustomerUser) param.get(CommonUtil.CUSTOMERUSERKEY);
		CustomerActivity activity = (CustomerActivity) param.get("activity");
		
		List<LuckTempl> lucks = JsonUtil.getMapper().readValue(activity.getRule(), new TypeReference<List<LuckTempl>>() { });  
		//将区循环，计算用户当前拥有的抽奖机会
		SQLQuery query=null;
		Map<String,List<AwardLevelTemp>> map = new HashMap<String, List<AwardLevelTemp>>(lucks.size());
		for(int i=0;i<lucks.size();i++){
			BigDecimal minAmount = lucks.get(i).getMinAmount();
			BigDecimal maxAmount = lucks.get(i).getMaxAmount();
			String sql="select count(1) from t_customer_order o where o.order_status =:sts "
					+ " and o.customer_id =:uid and o.receive_amount >=:min and o.receive_amount <=:max ";
			
			query = getSession().createSQLQuery(sql);
			query.setParameter("uid", user.getId());
			query.setParameter("sts", DataDictionaryUtil.STATUS_OPEN);
			query.setParameter("min", minAmount);
			query.setParameter("max", maxAmount);
			
			List<BigInteger> list = query.list();
			if(list.get(0).intValue()>0){
				map.put(lucks.get(i).getAwardRank()+":"+list.get(0).intValue(),lucks.get(i).getLevelTemps());
			}
		}
		
		if(map.size()==0){
			throw new LotteryException("亲，您没有获得抽奖机会，请继续努力！");
		}
		//查询用户已抽奖记录表，如果所有抽奖机会已经用完，则抛异常
		String sql ="select r.activity_id,r.award_area,sum(award_count) from t_luck_award_record r where r.customer_id =:uid "
				+ " and r.activity_id =:aid group by r.award_area ";
		query = getSession().createSQLQuery(sql);
		query.setParameter("uid", user.getId());
		query.setParameter("aid", activity.getId());
		
		List<Object[]> lists = query.list();
		
		List<AwardLevelTemp> levels = null;
		int awardArea = 0;
		for(String key : map.keySet()){
			boolean isBreak=false;
			for(Object[] objs : lists){
				if(key.split(":")[0].equals(objs[1].toString())){
					int hc = Integer.parseInt(key.split(":")[1]);
					if(hc>Integer.parseInt(objs[2].toString())){
						isBreak=true;
						break;
					}
				}
			}
			if(isBreak){
				//如果还有抽奖机会，则拿其中的一个奖区间去抽奖
				levels=map.get(key);
				awardArea = Integer.parseInt(key.split(":")[0]);
				break;
			}
		}
		
		if(levels==null){
			throw new LotteryException("亲，您的抽奖机会已经用完啦，请继续努力！");
		}
		//10000里随机一个数，概率数值乘以100，看数值在哪个阶段
		Random random = new Random();
	    Integer s = random.nextInt(10000)%(10000-1+1) + 1;
	    int total=0;
	    
	    AwardLevelTemp awardLevel = null;
	    for(AwardLevelTemp levelTemp : levels){
	    	int levelInt =levelTemp.getAwardChance().multiply(new BigDecimal(100)).intValue();
	    	if(s<levelInt){
	    		//判断该奖项发放情况，如果已经发放完了，则循环到下一奖级去。否则，获得该奖级奖项
	    		sql ="select ifnull(sum(award_count),0) from t_luck_award_record r where r.activity_id =:aid "
	    				+ " and r.award_area =:ar and r.award_level =:al ";
	    		query = getSession().createSQLQuery(sql);
	    		query.setParameter("aid", activity.getId());
	    		query.setParameter("ar", awardArea);
	    		query.setParameter("al", levelTemp.getAwardLevel());
	    		
	    		Integer usedCount = ((BigInteger)query.list().get(0)).intValue();
	    		if(levelTemp.getAwardCount()<=usedCount){
	    			continue;
	    		}
	    		awardLevel = levelTemp;
	    		break;
	    	}else{
	    		//未获得该奖级奖项，继续循环
	    		total += levelInt;
	    	}
	    }
	    
		//根据获得的抽奖奖级生成活动派发订单，增加用户金额，返回抽奖结果提示
	    this.saveActivityOrder(user, DateUtil.getStringDate(), DateUtil.getStringDate(), activity, awardLevel.getAwardAmount(), returnMap);
	    //插入抽奖记录表
	    LuckAwardRecord lar = new LuckAwardRecord();
	    lar.addInit(user.getCustomerName());
	    lar.setActivityId(activity.getId());
	    lar.setAwardArea(awardArea);
	    lar.setAwardCount(1);
	    lar.setAwardLevel(awardLevel.getAwardLevel());
	    lar.setCustomerId(user.getId());
	    luckDao.save(lar);
	    
	    returnMap.put("showInfo", "恭喜，您抽中了"+awardArea+"等奖，奖励金额为"+awardLevel.getAwardAmount()+"元!");
	    returnMap.put("awardLevel", awardLevel);
	    return returnMap;
	}
}
