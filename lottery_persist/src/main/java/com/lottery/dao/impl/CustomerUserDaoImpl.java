package com.lottery.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.lottery.bean.entity.CustomerBindCard;
import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.UserCard;
import com.lottery.bean.entity.vo.CustomerOrderVO;
import com.lottery.bean.entity.vo.CustomerUserVO;
import com.lottery.bean.entity.vo.ReportVO;
import com.lottery.bean.entity.vo.TempMapVO;
import com.lottery.bean.entity.vo.UserCardVO;
import com.lottery.dao.ICustomerUserDao;
import com.lottery.persist.generice.GenericDAO;
import com.xl.lottery.desutil.AesUtil;
import com.xl.lottery.desutil.Md5Manage;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;
import com.xl.lottery.util.DateUtil;
import com.xl.lottery.util.Util;

@SuppressWarnings("unchecked")
@Repository
public class CustomerUserDaoImpl extends GenericDAO<CustomerUser> implements
		ICustomerUserDao {

	private static Logger logger = LoggerFactory
			.getLogger(CustomerUserDaoImpl.class);

	public CustomerUserDaoImpl() {
		super(CustomerUser.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Object[]> findMainCustomers(Map<String, ?> param)
			throws Exception {
		// TODO Auto-generated method stub
		CustomerUserVO vo = (CustomerUserVO) param.get("customeruservokey");
		StringBuffer queryString = new StringBuffer("SELECT");
		queryString.append(" t.id,t.customer_name,(SELECT  COUNT(1) ");
		queryString.append(" FROM  t_customer_bind_card bcard  WHERE bcard.customer_id = t.`id`) AS cardcount,");
		queryString.append(" t.`rebates`,(SELECT COUNT(1) FROM t_customer_user u "
				+ " WHERE (u.`allParentAccount` LIKE CONCAT(t.id,',','%') OR u.`allParentAccount` "
				+ " LIKE CONCAT('%',',',t.id) OR (u.`customer_level` = 1 AND u.`allParentAccount` = t.id))"
				+ " AND u.customer_level>=1) AS lowerlevel,");
		queryString.append(" t.`customer_status`");
//				+ ", (SELECT IFNULL(SUM(bet.`bet_money`*bet.multiple), 0) FROM t_bet_record bet ");
//		queryString.append(" WHERE bet.`customer_id` IN (SELECT id FROM t_customer_user u1 WHERE (u1.`allParentAccount` LIKE CONCAT('%',',',t.id) OR u1.allparentaccount LIKE CONCAT('%',',',t.`id`,',','%') OR (u1.`customer_level` = 1 AND u1.`allParentAccount` = t.id) ) and u1.customer_Online_Status = 11002) ");
//		queryString.append(" AND bet.`bet_status` IN (21002, 21003) AND YEAR(bet.`create_time`) = YEAR(NOW()) "
//				+ " AND MONTH(bet.`create_time`) = MONTH(NOW())) AS amount "
		queryString.append(",(SELECT bg.`bonus_name` FROM t_user_bonus_group ug,t_bonus_group bg "
				+ " WHERE ug.`bonus_id`=bg.`id`AND ug.`customer_id` = t.`id` ) AS bgname , "
				+ " (SELECT bg.bonus_rebates FROM t_user_bonus_group ug,t_bonus_group bg "
				+ " WHERE ug.`bonus_id`=bg.`id` AND ug.`customer_id` = t.`id` ) AS bgrb FROM ");
		queryString.append(" t_customer_user t ");
		queryString.append(" WHERE t.`customer_level` = 0 ");
		queryString.append(" AND t.`customer_status` = '10002' ");
		List<Object> objs = new ArrayList<Object>();
		if (vo != null && !StringUtils.isEmpty(vo.getCustomerName())) {
			queryString.append(" and t.customer_name like ?");
			objs.add(vo.getCustomerName().trim() + "%");
		}
		List<Object[]> users = queryForObjectList(queryString.toString(),objs.toArray());
		return users;
	}

	@Override
	public List<CustomerUser> queryUserByName(CustomerOrderVO vo)
			throws Exception {
		Query query = getSession()
				.createQuery(
						" from CustomerUser t where t.customerName = ? and t.customerStatus = ? ");
		query.setParameter(0, vo.getCustomerName());
		query.setParameter(1, DataDictionaryUtil.STATUS_OPEN);
		@SuppressWarnings("unchecked")
		List<CustomerUser> list = query.list();
		return list;
	}
	
	@Override
	public CustomerUser queryUserByName(String userName)
			throws Exception {
		Query query = getSession()
				.createQuery(
						" from CustomerUser t where t.customerName = ? ");
		query.setParameter(0, userName);
		@SuppressWarnings("unchecked")
		List<CustomerUser> list = query.list();
		return list.size()==0?null:list.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findCuserTree(Map<String, ?> param)
			throws Exception {
		// TODO Auto-generated method stub
		CustomerUserVO vo = (CustomerUserVO) param.get("uservokey");
		CustomerUser entity = queryById(vo.getId());
		List<Long> ids = new ArrayList<Long>();
		if (entity.getCustomerLevel() == 0) {
			ids.add(entity.getId());
		} else {
			String[] temp = entity.getAllParentAccount().split(",");
			for (String id : temp) {
				ids.add(Long.parseLong(id));
			}
			ids.add(entity.getId());
		}
		
//		StringBuffer queryString = new StringBuffer(" SELECT t.id,t.customer_name,(SELECT IFNULL(SUM(bet.`bet_money`*bet.multiple), 0) FROM ");
//		queryString.append(" t_bet_record bet WHERE bet.`customer_id` IN (SELECT id FROM  t_customer_user u1 WHERE (u1.`allParentAccount` LIKE CONCAT('%',',',t.id) OR u1.allparentaccount LIKE CONCAT('%',',',t.`id`,',','%') OR (u1.`customer_level` = 1 AND u1.`allParentAccount` = t.id) ) and u1.customer_Online_Status = 11002 ) ");
//		queryString.append(" AND bet.`bet_status` IN (21002, 21003)  AND YEAR(bet.`create_time`) = YEAR(NOW()) AND MONTH(bet.`create_time`) = MONTH(NOW())) AS amount, ");
//		queryString.append(" t.`customer_level`,t.`rebates`, t.`create_time`,t.customer_status,t.customer_email,t.customer_qq FROM t_customer_user t where t.id in (:ids) order by t.customer_level asc ");
		
		StringBuffer queryString = new StringBuffer("SELECT  t.id,t.customer_name,t.`customer_level`,"
				+ "t.`rebates`,t.`create_time`,t.customer_status, t.customer_email,t.customer_qq,t.customer_alias "
				+ "FROM t_customer_user t WHERE t.id IN (:ids) ");
		
		
		Query query = getSession().createSQLQuery(queryString.toString());
		query.setParameterList("ids", ids);
		List<Object[]> tree = query.list();
		return tree;
	}

	/**
	 * 查询符合条件的团队成员信息。
	 */
	@Override
	public List<CustomerUser> quUserByTeam(Map<String, Object> param)
			throws Exception {
		Boolean isRevenue = (Boolean) param.get("revenueKey");
		CustomerOrderVO vo = (CustomerOrderVO) param.get("ordersKey");
		CustomerUser curUser = (CustomerUser) param.get(CommonUtil.CUSTOMERUSERKEY);
		StringBuffer sql =new StringBuffer("SELECT * FROM  t_customer_user u1 where u1.customer_status=? ");
		
		if(!StringUtils.isEmpty(vo.getRsvst3())){
			sql.append("and u1.customer_name=? ");
		}
		
		if(!StringUtils.isEmpty(vo.getRsvst2())&&!vo.getRsvst2().equals("all")){
			sql.append("and  u1.customer_type=?  ");
			
//			String type = vo.getRsvst2();
//			String userType = type.substring(0, type.indexOf("_"));
//			
//			if(Integer.parseInt(userType)==DataDictionaryUtil.CUSTOMER_TYPE_PROXY
//					&&vo.getRsvst4().equals("true")){
//				//查询下级
//				sql.append(" and u1.customer_level >= ?  ");
//			}else if(Integer.parseInt(userType)==DataDictionaryUtil.CUSTOMER_TYPE_PROXY){
//				sql.append(" and u1.customer_level = ?  ");
//			}
		}else{
			sql.append("and  u1.customer_type in (:cts) ");
		}
		
		String lowersql = "and (u1.`allParentAccount` LIKE CONCAT("+curUser.getId()+",',%') "
				+ "OR u1.`allParentAccount` LIKE CONCAT('%,',"+curUser.getId()+")"
				+ "OR u1.`allParentAccount` LIKE CONCAT('%,',"+curUser.getId()+",',%') "
				+ "OR (u1.`customer_level` = 1 AND u1.`allParentAccount` = "+curUser.getId()+")";
		if(isRevenue){
				lowersql += " or u1.id = "+curUser.getId()+" )";
		}else{
			lowersql += ") and u1.id != "+curUser.getId()+" ";
		}
		
		sql.append(lowersql);
		//是否包含下级
		if(vo.getRsvst4().equals("true")&&!StringUtils.isEmpty(vo.getRsvst3())){
			CustomerUser luser = this.queryUserByName(vo.getRsvst3());
			if(luser == null){
				return new ArrayList<CustomerUser>();
			}
			StringBuffer lowersql1 =new StringBuffer("SELECT * FROM  t_customer_user u1 where u1.customer_status=10002 ");
			lowersql1.append("and  u1.customer_type in (:cts) ");
			lowersql1.append("and (u1.`allParentAccount` LIKE CONCAT("+luser.getId()+",',%') "
					+ "OR u1.`allParentAccount` LIKE CONCAT('%,',"+luser.getId()+")"
					+ "OR u1.`allParentAccount` LIKE CONCAT('%,',"+luser.getId()+",',%') "
					+ "OR (u1.`customer_level` = 1 AND u1.`allParentAccount` = "+luser.getId()+")");
			if(isRevenue){
				lowersql1.append(" or u1.id = "+curUser.getId()+" )");
			}else{
				lowersql1.append(") and u1.id != "+curUser.getId()+" ");
			}
			sql.append(" union all ").append(lowersql1);
		}
				
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		
		query.setParameter(0, DataDictionaryUtil.STATUS_OPEN);
		int i=1;
		if(!StringUtils.isEmpty(vo.getRsvst3())){
			query.setParameter(i, vo.getRsvst3());
			i++;
		}
		
//		if(!vo.getRsvst2().equals("all")){
//			String type = vo.getRsvst2();
//			String userType = type.substring(0, type.indexOf("_"));
//			String userLevel = type.substring(type.indexOf("_")+1,type.length());
//			query.setParameter(i++, type);
//			if(Integer.parseInt(userType)==DataDictionaryUtil.CUSTOMER_TYPE_PROXY){
//				query.setParameter(i++, userLevel);
//			}
//		}else{
			List<Integer> cts = new ArrayList<Integer>();
			cts.add(DataDictionaryUtil.CUSTOMER_TYPE_MEMBER);
			cts.add(DataDictionaryUtil.CUSTOMER_TYPE_PROXY);
			query.setParameterList("cts", cts);
//		}
		
		query.addEntity(CustomerUser.class);
		List<CustomerUser> users = query.list();
		
		return users;
	}

	@Override
	public List<Object> queryTeamMoneyInfo(Map<String, ?> param)
			throws Exception {
		String[] ids = (String[]) param.get("ids");
		StringBuffer queryString = new StringBuffer(" SELECT t.id,t.`rebates`,t.`customer_name`,IFNULL(t.`customer_alias`,'') AS alias,(SELECT ");
		queryString.append(" IFNULL(SUM(t1.bet_Money * t1.multiple), 0) FROM t_bet_record t1 WHERE t1.customer_id IN ");
		queryString.append(" (SELECT id FROM t_customer_user t2 WHERE (  t2.`allParentAccount` LIKE CONCAT(t.id, ',', '%') ");
		queryString.append(" OR t2.`allParentAccount` LIKE CONCAT('%', ',', t.id) OR ( t2.`customer_level` = 1 AND t2.`allParentAccount` = t.id ) )) ");
		queryString.append("  AND t1.bet_status IN (21001, 21002, 21003)) ");
		queryString.append(" FROM t_customer_user t WHERE t.`id` IN (:ids) AND t.`customer_status` = 10002 ");
		Query query = getSession().createSQLQuery(queryString.toString());
		query.setParameterList("ids", ids);
		return query.list();
	}

	@Override
	public Page<CustomerUserVO, Object> queryLowerLevels(
			Map<String, ?> param) throws Exception {
		Long userId = (Long) param.get("userId");
		CustomerUserVO vo = (CustomerUserVO) param.get("uservokey");
		//SELECT IFNULL(SUM(t3.`quota_count`),0) FROM t_customer_quota t3 WHERE t3.customer_id = t.`id`) 查询剩余配额
		StringBuffer queryString = new StringBuffer("SELECT t.id,t.`rebates`,t.`customer_name`,");
		queryString.append(" IFNULL(t.`customer_alias`, '') AS alias,");
		//queryString.append(" (SELECT IFNULL(SUM(t1.bet_Money * t1.multiple), 0) FROM t_bet_record t1 WHERE t1.customer_id = t.`id` AND t1.bet_status IN (21001, 21002, 21003) AND CONCAT(YEAR(t1.`create_time`),'-',MONTH(t1.`create_time`)) = ?) AS amount,");
		queryString.append(" t.`customer_level`, t.`create_time`,t.customer_type,c.cash,t.customer_online_status ");
		queryString.append(" FROM t_customer_user t left join t_customer_cash c on t.id = c.customer_id WHERE t.`customer_status` = 10002 AND t.`customer_superior` = ? order by t.create_time desc");
		
		StringBuffer countString = new StringBuffer("SELECT count(1) FROM t_customer_user t WHERE t.`customer_status` = 10002  AND t.`customer_superior` = ? ");
		
		int totalCount = this.queryLowerLevelsCount(countString.toString(),userId);
		Page<CustomerUserVO, Object> page = new Page<CustomerUserVO, Object>();
		int pageNum = vo.getPageNum();
		int maxY = totalCount/vo.getMaxCount();
		if(totalCount%page.getMaxCount()!=0){
			maxY+=1;
		}
		pageNum = pageNum<=0?1:pageNum;
		pageNum = pageNum>=maxY?maxY:pageNum;
		page.setPageNum(pageNum);
		Query query = getSession().createSQLQuery(queryString.toString());  
		query.setParameter(0, userId);
		query.setMaxResults(vo.getMaxCount());  
		query.setFirstResult((pageNum-1)*vo.getMaxCount());    
		List<Object> entitylist = query.list();    
		page.setEntitylist(entitylist);
		page.setPageCount(maxY);
		//总记录数
		page.setTotalCount(totalCount);
		return page;
	}
	
	private int queryLowerLevelsCount(String sql,Long userId){
		Query query = getSession().createSQLQuery(sql);
		query.setParameter(0, userId);
        BigInteger temp = (BigInteger) query.list().get(0);
        //int count = Integer.parseInt(temp.toString());
        return temp.intValue();  
	}

	/**
	 * 查询银行卡对应的绑定用户集合
	 */
	@Override
	public List<CustomerUser> queryBindCardUser(Map<String, ?> param)
			throws Exception {
		Long cardId = (Long) param.get("cardIdKey");
		String hql = "from CustomerBindCard where bankcardId = ? and status = ? ";
		Query query = getSession().createQuery(hql);
		query.setParameter(0, cardId);
		query.setParameter(1, DataDictionaryUtil.STATUS_OPEN);
		List<CustomerBindCard> binds = query.list();
		if(binds.size()==0){
			return null;
		}
		List<Long> userIds = new ArrayList<Long>();
		for(CustomerBindCard bindCard : binds){
			userIds.add(bindCard.getCustomerId());
		}
		
		hql = "from CustomerUser where id in (:userIds)";
		query = getSession().createQuery(hql);
		query.setParameterList("userIds", userIds);
		return query.list();
	}

	@Override
	public Map<String, Integer> queryUserStatistic() throws Exception {
		Map<String, Integer> map = new HashMap<String,Integer>();
		//总用户数，剔除测试用户
		String sql ="SELECT  COUNT(id) FROM t_customer_user WHERE customer_status = :sts and customer_type != :tat "+
			//代理用户数
			" UNION ALL  SELECT COUNT(id) FROM t_customer_user WHERE customer_status =:sts and  customer_type = :uat "+
			//会员用户数
			" UNION ALL  SELECT COUNT(id) FROM t_customer_user WHERE customer_status =:sts and customer_type = :umt "+
			//未激活用户数，剔除测试用户
			" UNION ALL  SELECT COUNT(id) FROM t_customer_user WHERE customer_status =:sts "
			+ "and customer_online_status = :olsts and customer_type != :tat  "+
			//未充值用户数，剔除测试用户
			" UNION ALL SELECT COUNT(id) FROM t_customer_user u WHERE u.customer_status =:sts and u.customer_type != :tat  and "+
			" 		NOT EXISTS (SELECT 1 FROM t_customer_order o WHERE u.id = o.customer_id AND o.`order_detail_type`in (:odt) )"+
			//未绑卡用户数，剔除测试用户
			" UNION ALL SELECT COUNT(id) FROM t_customer_user u2 WHERE u2.customer_status =:sts and u2.customer_type != :tat and "+
			" 		NOT EXISTS (SELECT 1 FROM t_user_card c WHERE u2.id = c.customer_id AND c.`status`=:sts )"+
			//新增用户数，剔除测试用户
			" UNION ALL SELECT COUNT(id) FROM t_customer_user u WHERE u.customer_status =:sts and u.customer_type != :tat and "+
			" 		u.create_time>:st AND u.`create_time`<=:et "+
			//未投注用户数，剔除测试用户
			" UNION ALL SELECT COUNT(id)- (SELECT COUNT(1) FROM (SELECT  u.`id` FROM t_customer_user u "+
			" 		LEFT JOIN t_customer_order o ON u.`id` = o.`customer_id` WHERE u.customer_status = :sts "+
			" 		AND u.customer_type != :tat AND u.id = o.customer_id AND o.`order_detail_type` IN (:odts) GROUP BY u.`id`)t ) "+
			" 		FROM t_customer_user   WHERE customer_status = :sts and customer_type != :tat "+
			//昨天活跃用户数,剔除测试用户
			" UNION ALL SELECT COUNT(1) FROM ( SELECT t.`customer_type`,t.`id`,COUNT(t1.`id`) "+ 
			" 		FROM t_customer_user t LEFT JOIN t_customer_order t1 ON t.`id` = t1.`customer_id`"+ 
			" WHERE t1.`create_time` >= :st AND t1.`create_time` <= :et and t.customer_type != :tat "+ 
			" 		AND t1.`order_detail_type` IN (:odts) GROUP BY t.`customer_type`,t.`id`) tt " +
			" UNION ALL SELECT IF(NULL,SUM(quota_count),0) FROM t_customer_quota WHERE STATUS = :sts ";
		
		
		SQLQuery query = getSession().createSQLQuery(sql);
		List<Integer> odts = new ArrayList<Integer>();
		odts.add(DataDictionaryUtil.ORDER_DETAIL_ORDINARY_BETTING);
		odts.add(DataDictionaryUtil.ORDER_DETAIL_TRACK);
		query.setParameter("sts", DataDictionaryUtil.STATUS_OPEN);
		query.setParameter("tat", DataDictionaryUtil.CUSTOMER_TYPE_TEST);
		query.setParameter("uat", DataDictionaryUtil.CUSTOMER_TYPE_PROXY);
		query.setParameter("umt", DataDictionaryUtil.CUSTOMER_TYPE_MEMBER);
		//未充值用户应该包含统计第三方充值
		List<Integer> odt = new ArrayList<Integer>();
		odt.add(DataDictionaryUtil.ORDER_DETAIL_CASH_ADD);
		odt.add(DataDictionaryUtil.ORDER_DETAIL_OTHER_PAY);
		query.setParameterList("odt", odt.toArray());
		
		query.setParameter("olsts", DataDictionaryUtil.STATUS_ONLINE_NO);
		query.setParameter("st", DateUtil.getNextDay(DateUtil.getStringDateShort(), "-1")+" 00:00:00");
		query.setParameter("et", DateUtil.getNextDay(DateUtil.getStringDateShort(), "-1")+" 23:59:59");
		query.setParameterList("odts", odts.toArray());
		
		List<Object> objs = query.list();
		map.put("allUser", Integer.parseInt(objs.get(0).toString()));
		map.put("agentUser", Integer.parseInt(objs.get(1).toString()));
		map.put("memberUser", Integer.parseInt(objs.get(2).toString()));
		map.put("inactive", Integer.parseInt(objs.get(3).toString()));
		map.put("unbindCard", Integer.parseInt(objs.get(4).toString()));
		map.put("unRecharge", Integer.parseInt(objs.get(5).toString()));
		map.put("yesterday", Integer.parseInt(objs.get(6).toString()));
		map.put("unbet", Integer.parseInt(objs.get(7).toString()));
		map.put("ydActive", Integer.parseInt(objs.get(8).toString()));
		map.put("remainQuota", Integer.parseInt(objs.get(9).toString()));
		return map;
	}
	
	@Override
	public Map<String, String> queryMonthActiveUser(Map<String,?> param) throws Exception {
		Map<String, String> map = new HashMap<String,String>();
		ReportVO vo = (ReportVO) param.get("reprotKey");
		Long days = DateUtil.getTwoDay(vo.getEndTime(), vo.getStartTime());
		String sql = ",tt.customer_type,COUNT(1) FROM (SELECT u.`customer_type`,u.`id`,COUNT(u.`id`) FROM "+
				 "t_customer_user u LEFT JOIN t_customer_order o ON u.`id` = o.`customer_id`"+
				"WHERE u.customer_status =:sts AND o.`order_detail_type` IN (:odts) ";
		StringBuffer sb = new StringBuffer();
		for(int i=1;i<days;i++){
			String sql2 = "SELECT "+(DateUtil.getNextDay3(vo.getEndTime(), "-"+(i+1)))+sql;
			if(i==1){
				sb.append(sql2 +"  AND o.create_time>:st"+i+" AND o.`create_time`<= :et"
							+i+" GROUP BY u.`customer_type`,u.`id`) tt GROUP BY tt.`customer_type` ");
			}else{
				sb.append(" union all "+sql2);
				sb.append("  AND o.create_time>:st"+i+" AND o.`create_time`<= :et"+
						i+" GROUP BY u.`customer_type`,u.`id`) tt GROUP BY tt.`customer_type` ");
			}
		}
		
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		List<Integer> odts = new ArrayList<Integer>();
		odts.add(DataDictionaryUtil.ORDER_DETAIL_ORDINARY_BETTING);
		odts.add(DataDictionaryUtil.ORDER_DETAIL_TRACK);
		query.setParameter("sts", DataDictionaryUtil.STATUS_OPEN);
		for(int i=1;i<days;i++){
			query.setParameter("st"+i, DateUtil.getNextDay(vo.getEndTime(), "-"+(i+1))+" 00:04:00");
			query.setParameter("et"+i, DateUtil.getNextDay(vo.getEndTime(), "-"+i)+" 00:04:00");
		}
		query.setParameterList("odts", odts.toArray());
		
		List<Object> objs = query.list();
		for(int j=0;j<objs.size();j++){
			Object objArr = objs.get(j);
			if(objArr instanceof Object[]){
				Object[] obj = (Object[]) objArr;
				if(obj[1]==null){
					map.put(obj[0]+"_p", "0");
					map.put(obj[0]+"_m", "0");
				}else if(Integer.valueOf(obj[1].toString())==DataDictionaryUtil.CUSTOMER_TYPE_PROXY){
					map.put(obj[0]+"_p", obj[2]+"");
				}else if(Integer.valueOf(obj[1].toString())==DataDictionaryUtil.CUSTOMER_TYPE_MEMBER){
					map.put(obj[0]+"_m", obj[2]+"");
				}
			}
		}
		map.put("days", days+"");
		return map;
	}

	@Override
	public Map<String, Integer> queryMonthAddUser(Map<String,?> param) throws Exception {
		Map<String, Integer> map = new HashMap<String,Integer>();
		ReportVO vo = (ReportVO) param.get("reprotKey");
		Long days = DateUtil.getTwoDay(vo.getEndTime(), vo.getStartTime());
		
		String sql = ",t.`customer_type`,COUNT(1) FROM  t_customer_user t WHERE t.customer_status =:sts ";
		StringBuffer sb = new StringBuffer();
		for(int i=1;i<days;i++){
			String sql2 = "SELECT "+(DateUtil.getNextDay3(vo.getEndTime(), "-"+(i+1)))+sql;
			if(i==1){
				sb.append(sql2 +"  AND t.create_time>:st"+i+" AND t.`create_time`<= :et"+i
						+ " AND t.`customer_type` != :tat  GROUP BY t.`customer_type` ");
			}else{
				sb.append(" union all "+sql2);
				sb.append(" AND t.`customer_type` != :tat  AND t.create_time>:st"+i
						+" AND t.`create_time`<= :et"+i+" GROUP BY t.`customer_type` ");
			}
		}
		
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		query.setParameter("sts", DataDictionaryUtil.STATUS_OPEN);
		query.setParameter("tat", DataDictionaryUtil.CUSTOMER_TYPE_TEST);
		for(int i=1;i<days;i++){
			query.setParameter("st"+i, DateUtil.getNextDay(DateUtil.getStringDateShort(), "-"+i)+" 00:04:00");
			query.setParameter("et"+i, DateUtil.getNextDay(DateUtil.getStringDateShort(), "-"+(i-1))+" 00:04:00");
		}
		
		List<Object> objs = query.list();
		for(int j=0;j<objs.size();j++){
			Object objArr = objs.get(j);
			if(objArr instanceof Object[]){
				Object[] obj = (Object[]) objArr;
				if(obj[1]==null){
					map.put(obj[0]+"_p", 0);
					map.put(obj[0]+"_m", 0);
				}else if(Integer.valueOf(obj[1].toString())==DataDictionaryUtil.CUSTOMER_TYPE_PROXY){
					map.put(obj[0]+"_p", Integer.parseInt(obj[2].toString()));
				}else if(Integer.valueOf(obj[1].toString())==DataDictionaryUtil.CUSTOMER_TYPE_MEMBER){
					map.put(obj[0]+"_m", Integer.parseInt(obj[2].toString()));
				}
			}
		}
		map.put("days", days.intValue());
		
		sql = "SELECT t.id FROM t_customer_user t WHERE t.`create_time` >= :mbd "
				+ " AND t.`create_time` <= :myd AND t.`create_user`= :su AND t.customer_type != :tat ";
		query = getSession().createSQLQuery(sql);
		query.setParameter("mbd", vo.getStartTime()+" 00:04:00");
		query.setParameter("myd", vo.getEndTime()+" 00:04:00");
		query.setParameter("su", CommonUtil.PROXYUSER);
		query.setParameter("tat", DataDictionaryUtil.CUSTOMER_TYPE_TEST);
		List list =query.list();
		if(list==null){
			map.put("mtsc", 0);
		}else{
			int selfCount = query.list().size();
			map.put("mtsc", selfCount);
		}
		
		sql = "SELECT t.id FROM t_customer_user t WHERE t.`create_time` >= :mbd "
				+ " AND t.`create_time` <= :myd AND t.`create_user`= :su AND t.customer_type != :tat ";
		query = getSession().createSQLQuery(sql);
		query.setParameter("mbd", vo.getStartTime()+" 00:04:00");
		query.setParameter("myd", vo.getEndTime()+" 00:04:00");
		query.setParameter("su", CommonUtil.LINKUSER);
		query.setParameter("tat", DataDictionaryUtil.CUSTOMER_TYPE_TEST);
		list =query.list();
		if(list==null){
			map.put("ltsc", 0);
		}else{
			int selfCount = query.list().size();
			map.put("ltsc", selfCount);
		}
		
		return map;
	}
	
	@Override
	public Page<Object, Object> getSuperQueryData(Map<String, ?> param)
			throws Exception {
		// TODO Auto-generated method stub
		CustomerUserVO vo = (CustomerUserVO) param.get("uservokey");
		StringBuffer sql = new StringBuffer("SELECT t.id,t.create_user,t.`customer_alias`,t.`customer_name`,t1.`id` AS fid,t1.`customer_alias` AS fname,");
		sql.append("t.`customer_level`,(SELECT COUNT(t2.`id`) FROM t_customer_user t2 WHERE t2.`customer_superior` = t.`id`) AS lowercount,");
		sql.append("(SELECT IFNULL(SUM(t3.`quota_count`),0) FROM t_customer_quota t3 WHERE t3.customer_id = t.`id`) AS quotacount,");
		sql.append("(SELECT COUNT(1) FROM t_user_card t4 WHERE t4.customer_id = t.`id`) AS usercard,");
		sql.append("(SELECT COUNT(1) FROM t_customer_bind_card t5 WHERE t5.customer_id = t.id) AS bindcard,");
		sql.append("(SELECT IFNULL(SUM(t6.`receive_amount` - t6.`return_amount`),0) FROM t_customer_order t6 WHERE t6.order_status = 17002 and t6.customer_id = t.`id` AND t6.`order_detail_type` IN (18017,18001)) AS betmoney,");
		sql.append("(SELECT IFNULL(SUM(t7.`receive_amount`),0) FROM t_customer_order t7 WHERE t7.order_status = 17002 and t7.customer_id = t.`id` AND t7.`order_detail_type` IN (18009,18011)) AS recharge,");
		sql.append("(SELECT IFNULL(SUM(t8.`receive_amount`),0) FROM t_customer_order t8 WHERE t8.order_status = 17002 and t8.customer_id = t.`id` AND t8.`order_detail_type` = 18007) AS withdrawals,");
		sql.append("(SELECT cash FROM t_customer_cash t9 WHERE t9.customer_id = t.`id`) AS cash,");
		sql.append("t.`create_time`,t.customer_type,t.rebates FROM t_customer_user t,t_customer_user t1 ");
		sql.append("WHERE t.`customer_superior` = t1.id AND t.`customer_online_status` = 11002 ");
		sql.append("AND t.`customer_status` = 10002 AND t.`customer_level` >= 1 ");
		StringBuffer sqlcount = new StringBuffer("SELECT count(1) FROM t_customer_user t,t_customer_user t1 ");
		sqlcount.append("WHERE t.`customer_superior` = t1.id AND t.`customer_online_status` = 11002 ");
		sqlcount.append("AND t.`customer_status` = 10002 AND t.`customer_level` >= 1 ");
		
		List<String> limitKeys = new ArrayList<String>();
		List<String> formula = new ArrayList<String>();
		List<Object> limitVals = new ArrayList<Object>();
		
		if(!StringUtils.isEmpty(vo.getCustomerAlias())){
			if(StringUtils.isEmpty(vo.getNoLike())){
				formula.add("like");
				limitKeys.add("t.customer_Alias");
				limitVals.add("%"+vo.getCustomerAlias()+"%");
			}else{
				formula.add("=");
				limitKeys.add("t.customer_Alias");
				limitVals.add(vo.getCustomerAlias());
			}
		}
		
		if(!StringUtils.isEmpty(vo.getCustomerName())){
			if(StringUtils.isEmpty(vo.getNoLike())){
				formula.add("like");
				limitKeys.add("t.customer_Name");
				limitVals.add("%"+vo.getCustomerName()+"%");
			}else{
				formula.add("=");
				limitKeys.add("t.customer_Name");
				limitVals.add(vo.getCustomerName());
			}
		}
		
		if(!StringUtils.isEmpty(vo.getStrartTime())){
			formula.add(">=");
			limitKeys.add("t.create_Time");
			limitVals.add(vo.getStrartTime());
		}
		
		if(!StringUtils.isEmpty(vo.getEndTime())){
			formula.add("<=");
			limitKeys.add("t.create_Time");
			limitVals.add(vo.getEndTime());
		}
		
		if(!StringUtils.isEmpty(vo.getQuotaCount())){
			if(vo.getQuotaCount().indexOf("-")>-1){
				formula.add(">=");
				limitKeys.add("(SELECT IFNULL(SUM(t3.`quota_count`),0) FROM t_customer_quota t3 WHERE t3.customer_id = t.`id`)");
				limitVals.add(vo.getQuotaCount().split("-")[0]);
				formula.add("<=");
				limitKeys.add("(SELECT IFNULL(SUM(t3.`quota_count`),0) FROM t_customer_quota t3 WHERE t3.customer_id = t.`id`)");
				limitVals.add(vo.getQuotaCount().split("-")[1]);
			}else{
				formula.add("=");
				limitKeys.add("(SELECT IFNULL(SUM(t3.`quota_count`),0) FROM t_customer_quota t3 WHERE t3.customer_id = t.`id`)");
				limitVals.add(vo.getQuotaCount());
			}
		}
		
		if(!StringUtils.isEmpty(vo.getQuotaStr())){
			if(vo.getQuotaStr().indexOf("-")>-1){
				formula.add(">=");
				limitKeys.add("t.rebates");
				limitVals.add(vo.getQuotaStr().split("-")[0]);
				formula.add("<=");
				limitKeys.add("t.rebates");
				limitVals.add(vo.getQuotaStr().split("-")[1]);
			}else{
				formula.add("=");
				limitKeys.add("t.rebates");
				limitVals.add(vo.getQuotaStr());
			}
		}
		
		if(vo.getCustomerLevel()!=-1){
			if(vo.getCustomerLevel() == 0){
				formula.add("=");
				limitKeys.add("t.customer_Type");
				limitVals.add(DataDictionaryUtil.CUSTOMER_TYPE_MEMBER);
			}else{
				formula.add("=");
				limitKeys.add("t.customer_Level");
				limitVals.add(vo.getCustomerLevel());
			}
		}
		
		if(!StringUtils.isEmpty(vo.getBetMoney())){
			if(vo.getBetMoney().indexOf("-")>-1){
				formula.add(">=");
				limitKeys.add("(SELECT IFNULL(SUM(t6.`receive_amount` - t6.`return_amount`),0) FROM t_customer_order t6 WHERE t6.order_status = 17002 and t6.customer_id = t.`id` AND t6.`order_detail_type` IN (18017,18001))");
				limitVals.add(vo.getBetMoney().split("-")[0]);
				formula.add("<=");
				limitKeys.add("(SELECT IFNULL(SUM(t6.`receive_amount` - t6.`return_amount`),0) FROM t_customer_order t6 WHERE t6.order_status = 17002 and t6.customer_id = t.`id` AND t6.`order_detail_type` IN (18017,18001))");
				limitVals.add(vo.getBetMoney().split("-")[1]);
			}else{
				formula.add("=");
				limitKeys.add("(SELECT IFNULL(SUM(t6.`receive_amount` - t6.`return_amount`),0) FROM t_customer_order t6 WHERE t6.order_status = 17002 and t6.customer_id = t.`id` AND t6.`order_detail_type` IN (18017,18001))");
				limitVals.add(vo.getBetMoney());
			}
		}
		
		if(!StringUtils.isEmpty(vo.getCashMoney())){
			if(vo.getCashMoney().indexOf("-")>-1){
				formula.add(">=");
				limitKeys.add("(SELECT cash FROM t_customer_cash t9 WHERE t9.customer_id = t.`id`)");
				limitVals.add(vo.getCashMoney().split("-")[0]);
				formula.add("<=");
				limitKeys.add("(SELECT cash FROM t_customer_cash t9 WHERE t9.customer_id = t.`id`)");
				limitVals.add(vo.getCashMoney().split("-")[1]);
			}else{
				formula.add("=");
				limitKeys.add("(SELECT cash FROM t_customer_cash t9 WHERE t9.customer_id = t.`id`)");
				limitVals.add(vo.getCashMoney());
			}
		}
		
		if(!StringUtils.isEmpty(vo.getIsUserCard())){
			formula.add(">");
			limitKeys.add("(SELECT COUNT(1) FROM t_user_card t4 WHERE t4.customer_id = t.`id`)");
			limitVals.add(0);
		}
		
		if(!StringUtils.isEmpty(vo.getIsBindCard())){
			formula.add(">");
			limitKeys.add("(SELECT COUNT(1) FROM t_customer_bind_card t5 WHERE t5.customer_id = t.id) ");
			limitVals.add(0);
		}
		
		if(!StringUtils.isEmpty(vo.getIsBetMoney())){
			formula.add(">");
			limitKeys.add("(SELECT IFNULL(SUM(t6.`receive_amount` - t6.`return_amount`),0) FROM t_customer_order t6 WHERE t6.order_status = 17002 and t6.customer_id = t.`id` AND t6.`order_detail_type` IN (18017,18001)) ");
			limitVals.add(0);
		}
		
		if(!StringUtils.isEmpty(vo.getIsWithdrawals())){
			formula.add(">");
			limitKeys.add("(SELECT IFNULL(SUM(t8.`receive_amount`),0) FROM t_customer_order t8 WHERE t8.order_status = 17002 and t8.customer_id = t.`id` AND t8.`order_detail_type` = 18007) ");
			limitVals.add(0);
		}
		
		if(!StringUtils.isEmpty(vo.getIsRecharge())){
			formula.add(">");
			limitKeys.add("(SELECT IFNULL(SUM(t7.`receive_amount`),0) FROM t_customer_order t7 WHERE t7.order_status = 17002 and t7.customer_id = t.`id` AND t7.`order_detail_type` IN (18009,18011)) ");
			limitVals.add(0);
		}
		boolean isScort = true;
		if(StringUtils.isEmpty(vo.getIsDesc()))isScort = false;
		Page<Object, Object> pageObj = this.doPageSqlQuery(vo, sql, sqlcount, formula, limitKeys, limitVals, isScort,param);
		return pageObj;
	}

	@Override
	public Map<String, ?> queryProfileAmount(Map<String, Object> param)
			throws Exception {
		Map map = new HashMap();
		ReportVO reportVo = (ReportVO) param.get("reportKey");
		//先查询符合条件的大额投注用户
		map = this.queryProfileAmountMap(param);
		
		List<Long> userIds = (List<Long>) map.get("userIds");
		Integer tr = (Integer) map.get("totalRecord");
		String sql ="SELECT o.order_detail_type,d.sname FROM t_customer_order o "
				+ "LEFT JOIN t_data_dictionary d ON d.sid=o.order_detail_type where o.order_status=:sts "
				+ " and o.create_time >= :st and o.create_time <= :et  GROUP BY o.order_detail_type ";
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("sts", DataDictionaryUtil.ORDER_STATUS_SUCCESS);
		query.setParameter("st", reportVo.getStartTime());
		query.setParameter("et", reportVo.getEndTime());
		List<Object[]> list = query.list();
		
		StringBuffer sb = new StringBuffer("");
		String sql1 = " SELECT IFNULL(SUM(o.`order_amount`),0),";
		String sql2 = " FROM t_customer_order o , t_customer_user u WHERE order_detail_type = ";
		String sql3=" AND o.`order_status`=:sts  and o.create_time >= :st AND u.id = o.customer_id"
				//将测试人员数据剔除掉
				+ " and u.customer_type != :tat "
				+ " and o.create_time <= :et AND o.`customer_id` IN (:userIds)";
		for(int i=0;i<list.size();i++){
			Object[] objs = list.get(i);
			String asql = sql1+objs[0].toString()+",\'"+objs[1].toString()+"\' "+sql2+objs[0].toString()+sql3;
			if(i==0){
				sb.append(asql);
			}else{
				sb.append(" union all "+asql);
			}
		}
		if(sb.toString().equals("")){
			List<TempMapVO> voList = new ArrayList<TempMapVO>();
			map.put("voList", voList);
			return map;
		};
		query = getSession().createSQLQuery(sb.toString());
		query.setParameter("sts", DataDictionaryUtil.ORDER_STATUS_SUCCESS);
		if(userIds==null){
			userIds = new ArrayList<Long>();
			userIds.add(0L);
		}
		query.setParameterList("userIds", userIds.toArray());
		query.setParameter("st", reportVo.getStartTime());
		query.setParameter("et", reportVo.getEndTime());
		query.setParameter("tat", DataDictionaryUtil.CUSTOMER_TYPE_TEST);
		list = query.list();
		
		List<TempMapVO> voList = new ArrayList<TempMapVO>(list.size());
		TempMapVO vo = new TempMapVO();
		vo.setKey("userCount");
		vo.setValue("人数 ：");
		vo.setValue2(String.valueOf(tr));
		voList.add(vo);
		
		for(Object[] objs : list){
			vo = new TempMapVO();
			vo.setKey(objs[1].toString());
			vo.setValue(objs[2].toString()+"总额：");
			vo.setValue2(objs[0].toString());
			voList.add(vo);
		}
		map.put("voList", voList);
		return map;
	}
	/**
	 * 查询符合条件的大额投注用户
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, ?> queryProfileAmountMap(Map<String, Object> param)
			throws Exception {
		Map map = new HashMap();
		ReportVO vo = (ReportVO) param.get("reportKey");
		String sql1 = " SELECT o.`customer_id`,IFNULL(SUM(o.`order_amount`),0),u.`customer_name`,u.`customer_alias`"
				+ ",u.`rebates`,IFNULL(SUM(o.`order_amount`-o.return_amount),0) "
				+ " FROM t_customer_order o INNER JOIN t_customer_user u ON u.id = o.customer_id "
				+ " WHERE o.order_detail_type IN (:odts) AND o.`order_status`= :sts AND o.create_time >=:st  "
				+ " And o.create_time<=:et  GROUP BY o.`customer_id` HAVING SUM(o.`order_amount`) > :amount "
				+ " ORDER BY SUM(o.`order_amount`) DESC";
		
		SQLQuery query = getSession().createSQLQuery(sql1);
		query.setParameter("sts", DataDictionaryUtil.ORDER_STATUS_SUCCESS);
		query.setParameter("st", vo.getStartTime());
		query.setParameter("et", vo.getEndTime());
		
		List<Integer> odts = new ArrayList<Integer>();
		odts.add(DataDictionaryUtil.ORDER_DETAIL_ORDINARY_BETTING);
		odts.add(DataDictionaryUtil.ORDER_DETAIL_TRACK);
		query.setParameterList("odts", odts.toArray());
		query.setParameter("amount", vo.getRsvdc2());
		
		//查询总记录数，算分页。
		List<Object[]> count = query.list();
		if(count.size()==0){
			return new HashMap<String, Object>();
		}
		String names ="";
		for(Object[] objs : count){
			if(names.equals("")){
				names += objs[2].toString();
			}else{
				names += ","+objs[2].toString();
			}
			
		}
		Integer totalCount = count.size();
		Integer pageNum = vo.getPageNum();
		Integer maxY = totalCount.intValue()/vo.getMaxCount();
		if(totalCount.intValue()%vo.getMaxCount()!=0){
			maxY+=1;
		}
		pageNum = pageNum<=0?1:pageNum;
		pageNum = pageNum>=maxY?maxY:pageNum;
		query.setFirstResult((pageNum-1)*vo.getMaxCount());
		query.setMaxResults(vo.getMaxCount());
		
		List<Object[]> list = query.list();
		
		List<Long> userIds = new ArrayList<Long>(list.size());
		List<BigDecimal> betAmountList = new ArrayList<BigDecimal>(list.size());
		List<BigDecimal> rebatesList = new ArrayList<BigDecimal>(list.size());
		List<String> nameList = new ArrayList<String>(list.size());
		List<String> aliasList = new ArrayList<String>(list.size());
		//真实投注金额，去掉了撤销订单的金额
		List<BigDecimal> realbaList = new ArrayList<BigDecimal>(list.size());
		for(Object[] objs : list){
			userIds.add(Long.parseLong(objs[0].toString()));
			betAmountList.add(new BigDecimal(objs[1].toString()));
			nameList.add(String.valueOf(objs[2].toString()));
			if(StringUtils.isEmpty(objs[3])){
				objs[3]="别名"+objs[0].toString();
			}
			aliasList.add(String.valueOf(objs[3].toString()));
			BigDecimal rebates = new BigDecimal(objs[4].toString());
			rebatesList.add(rebates.multiply(BigDecimal.valueOf(100)));
			//真实投注金额，去掉了撤销订单的金额
			realbaList.add(new BigDecimal(objs[5].toString()));
		}
		map.put("userIds", userIds);
		map.put("betAmountList", betAmountList);
		//真实投注金额，去掉了撤销订单的金额
		map.put("realbaList", realbaList);
		map.put("rebatesList", rebatesList);
		map.put("nameList", nameList);
		map.put("aliasList", aliasList);
		map.put("pageNo", pageNum);
		map.put("totalPage", maxY);
		map.put("totalRecord", totalCount);
		map.put("allUser", names);
		return map;
	}

	@Override
	public Map<String, ?> queryProfileData(Map<String, Object> param)
			throws Exception {
		 Map<String, Object> map = new HashMap<String,Object>();
		ReportVO vo = (ReportVO) param.get("reportKey");
		String sql ="SELECT t2.`customer_name` AS 'fname',t.`rebates`,t.`customer_type`, "
				+ " t2.id as 'fid',t.id as 'mid' FROM t_customer_user t , t_customer_user t2  "
				+ " WHERE  t.`customer_name`=:name  AND t.`customer_level`=t2.`customer_level`+1" 
				+ " AND (t.`allParentAccount` LIKE CONCAT('%,',t2.id) OR t.`allParentAccount` LIKE CONCAT('%',t2.id))";
		
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("name", vo.getRsvst1());
		List<Object[]> list = query.list();
		if(list==null||list.size()==0){
			return map;
		}
		map.put("fName", list.get(0)[0].toString());
		map.put("rebates", list.get(0)[1].toString());
		if(Integer.parseInt(list.get(0)[2].toString())==DataDictionaryUtil.CUSTOMER_TYPE_PROXY){
			map.put("userType","代理");
		}else if(Integer.parseInt(list.get(0)[2].toString())==DataDictionaryUtil.CUSTOMER_TYPE_MEMBER){
			map.put("userType","会员");
		}
		map.put("fid", list.get(0)[3].toString());
		map.put("mid", list.get(0)[4].toString());
		
		sql ="SELECT IFNULL(SUM(o.`order_amount` - o.return_amount),0) FROM t_customer_order o "
				+ "LEFT JOIN t_customer_user u ON u.id=o.customer_id "
				+ "WHERE order_detail_type IN (:odts) AND o.`order_status`=:sts "
				+ "AND o.`create_time` BETWEEN :st AND :et AND u.customer_name = :name ";
		query = getSession().createSQLQuery(sql);
		query.setParameter("sts", DataDictionaryUtil.ORDER_STATUS_SUCCESS);
		query.setParameter("st", vo.getStartTime());
		query.setParameter("et", vo.getEndTime());
		query.setParameter("name", vo.getRsvst1());
		
		List<Integer> dts = new ArrayList<Integer>();
		
		List<Integer> odts = new ArrayList<Integer>();
		odts.add(DataDictionaryUtil.ORDER_DETAIL_ORDINARY_BETTING);
		odts.add(DataDictionaryUtil.ORDER_DETAIL_TRACK);
		
		dts.addAll(odts);
		
		query.setParameterList("odts", odts.toArray());
		List list2 = query.list();
		map.put("betAmount", list2.get(0));
		
		StringBuffer sb = new StringBuffer("");
		for(int i=0;i<=vo.getRsvdc1();i++){
			String date = DateUtil.getNextDay3(vo.getEndTime(), "-"+(i+1))+",";
			String st = "st"+i;
			String et = "et"+i;
			String pt = "pt"+i;
			sql =" tt3.`cash`,tt.betAmount-tt2.lossAmount,tt.betAmount,tt4.awardAmount,tt5.rechargeAmount,"
					+ "tt6.drawingAmount,tt7.rebatesAmount,tt8.revenuesAmount,tt9.inAmount,tt10.outAmount FROM "
					+ " (SELECT IFNULL(SUM(o.`order_amount`-IFNULL(o.`return_amount`,0)),0)"
					+ " AS betAmount,u.`id` AS uid "
					+ " FROM t_customer_order o , t_customer_user u "
					+ " WHERE order_detail_type IN (:adts) AND o.`order_status`=:sts"
					+ " AND u.id=o.customer_id AND u.customer_type != :tat "
					+ " AND o.`create_time` BETWEEN :"+st+" AND :"+et 
					+ " AND u.id = :uid ) tt,"
					+ "(SELECT IFNULL(SUM(o.`order_amount`),0) AS lossAmount "
					+ "FROM t_customer_order o,t_customer_user u "
					+ "WHERE order_detail_type IN (:bdts) AND o.`order_status`=:sts "
					+ "AND u.id=o.customer_id  AND u.customer_type != :tat "
					+ "  AND o.`create_time`  BETWEEN :"+st+" AND :"+et 
					+ "  AND u.id = :uid ) tt2,"
					+ "(SELECT IFNULL(SUM(o.`order_amount`),0) AS awardAmount "
					+ "FROM t_customer_order o,t_customer_user u "
					+ "WHERE order_detail_type IN (:cdts) AND o.`order_status`=:sts "
					+ "AND u.id=o.customer_id  AND u.customer_type != :tat "
					+ "  AND o.`create_time`  BETWEEN :"+st+" AND :"+et 
					+ "  AND u.id = :uid ) tt4,"
					+ "(SELECT IFNULL(SUM(o.`order_amount`),0) AS rechargeAmount "
					+ "FROM t_customer_order o,t_customer_user u "
					+ "WHERE order_detail_type IN (:ddts) AND o.`order_status`=:sts "
					+ "AND u.id=o.customer_id  AND u.customer_type != :tat "
					+ "  AND o.`create_time`  BETWEEN :"+st+" AND :"+et 
					+ "  AND u.id = :uid ) tt5,"
					+ "(SELECT IFNULL(SUM(o.`order_amount`),0) AS drawingAmount "
					+ "FROM t_customer_order o,t_customer_user u "
					+ "WHERE order_detail_type IN (:edts) AND o.`order_status`=:sts "
					+ "AND u.id=o.customer_id  AND u.customer_type != :tat "
					+ "  AND o.`create_time`  BETWEEN :"+st+" AND :"+et 
					+ "  AND u.id = :uid ) tt6,"
					+ "(SELECT IFNULL(SUM(o.`order_amount`),0) AS rebatesAmount "
					+ "FROM t_customer_order o,t_customer_user u "
					+ "WHERE order_detail_type IN (:fdts) AND o.`order_status`=:sts "
					+ "AND u.id=o.customer_id  AND u.customer_type != :tat "
					+ "  AND o.`create_time`  BETWEEN :"+st+" AND :"+et 
					+ "  AND u.id = :uid ) tt7,"
					+ "(SELECT IFNULL(SUM(o.`order_amount`),0) AS revenuesAmount "
					+ "FROM t_customer_order o,t_customer_user u "
					+ "WHERE order_detail_type IN (:gdts) AND o.`order_status`=:sts "
					+ "AND u.id=o.customer_id  AND u.customer_type != :tat "
					+ "  AND o.`create_time`  BETWEEN :"+st+" AND :"+et 
					+ "  AND u.id = :uid ) tt8,"
					+ "(SELECT IFNULL(SUM(o.`order_amount`),0) AS inAmount "
					+ "FROM t_customer_order o,t_customer_user u "
					+ "WHERE order_type =:in and order_detail_type NOT IN (:dts) AND o.`order_status`=:sts "
					+ "AND u.id=o.customer_id  AND u.customer_type != :tat "
					+ "  AND o.`create_time`  BETWEEN :"+st+" AND :"+et 
					+ "  AND u.id = :uid ) tt9,"
					+ "(SELECT IFNULL(SUM(o.`order_amount`),0) AS outAmount "
					+ "FROM t_customer_order o,t_customer_user u "
					+ "WHERE order_type =:out and order_detail_type NOT IN (:dts) AND o.`order_status`=:sts "
					+ "AND u.id=o.customer_id  AND u.customer_type != :tat "
					+ "  AND o.`create_time`  BETWEEN :"+st+" AND :"+et 
					+ "  AND u.id = :uid ) tt10,"
					+ " t_customer_cash_h tt3 WHERE tt3.`customer_id` = tt.uid AND tt3.`process_date`=:"+pt ;
			
			String sql1 = "select " + date + sql;
			
			if(i==0){
				sql =" tt3.`cash`,tt.betAmount-tt2.lossAmount,tt.betAmount,tt4.awardAmount,tt5.rechargeAmount,"
						+ "tt6.drawingAmount,tt7.rebatesAmount,tt8.revenuesAmount,tt9.inAmount,tt10.outAmount FROM "
						+ " (SELECT IFNULL(SUM(o.`order_amount`-IFNULL(o.`return_amount`,0)),0)"
						+ " AS betAmount,u.`id` AS uid "
						+ " FROM t_customer_order o , t_customer_user u "
						+ " WHERE order_detail_type IN (:adts) AND o.`order_status`=:sts"
						+ " AND u.id=o.customer_id  AND u.customer_type != :tat "
						+ " AND o.`create_time` BETWEEN :"+st+" AND :"+et 
						+ " AND u.id = :uid ) tt,"
						+ "(SELECT IFNULL(SUM(o.`order_amount`),0) AS lossAmount "
						+ "FROM t_customer_order o,t_customer_user u "
						+ "WHERE order_detail_type IN (:bdts) AND o.`order_status`=:sts "
						+ "AND u.id=o.customer_id  AND u.customer_type != :tat "
						+ "  AND o.`create_time`  BETWEEN :"+st+" AND :"+et 
						+ "  AND u.id = :uid ) tt2,"
						+ "(SELECT IFNULL(SUM(o.`order_amount`),0) AS awardAmount "
						+ "FROM t_customer_order o,t_customer_user u "
						+ "WHERE order_detail_type IN (:cdts) AND o.`order_status`=:sts "
						+ "AND u.id=o.customer_id  AND u.customer_type != :tat "
						+ "  AND o.`create_time`  BETWEEN :"+st+" AND :"+et 
						+ "  AND u.id = :uid ) tt4,"
						+ "(SELECT IFNULL(SUM(o.`order_amount`),0) AS rechargeAmount "
						+ "FROM t_customer_order o,t_customer_user u "
						+ "WHERE order_detail_type IN (:ddts) AND o.`order_status`=:sts "
						+ "AND u.id=o.customer_id  AND u.customer_type != :tat "
						+ "  AND o.`create_time`  BETWEEN :"+st+" AND :"+et 
						+ "  AND u.id = :uid ) tt5,"
						+ "(SELECT IFNULL(SUM(o.`order_amount`),0) AS drawingAmount "
						+ "FROM t_customer_order o,t_customer_user u "
						+ "WHERE order_detail_type IN (:edts) AND o.`order_status`=:sts "
						+ "AND u.id=o.customer_id  AND u.customer_type != :tat "
						+ "  AND o.`create_time`  BETWEEN :"+st+" AND :"+et 
						+ "  AND u.id = :uid ) tt6,"
						+ "(SELECT IFNULL(SUM(o.`order_amount`),0) AS rebatesAmount "
						+ "FROM t_customer_order o,t_customer_user u "
						+ "WHERE order_detail_type IN (:fdts) AND o.`order_status`=:sts "
						+ "AND u.id=o.customer_id  AND u.customer_type != :tat "
						+ "  AND o.`create_time`  BETWEEN :"+st+" AND :"+et 
						+ "  AND u.id = :uid ) tt7,"
						+ "(SELECT IFNULL(SUM(o.`order_amount`),0) AS revenuesAmount "
						+ "FROM t_customer_order o,t_customer_user u "
						+ "WHERE order_detail_type IN (:gdts) AND o.`order_status`=:sts "
						+ "AND u.id=o.customer_id  AND u.customer_type != :tat "
						+ "  AND o.`create_time`  BETWEEN :"+st+" AND :"+et 
						+ "  AND u.id = :uid ) tt8,"
						+ "(SELECT IFNULL(SUM(o.`order_amount`),0) AS inAmount "
						+ "FROM t_customer_order o,t_customer_user u "
						+ "WHERE order_type =:in and order_detail_type NOT IN (:dts) AND o.`order_status`=:sts "
						+ "AND u.id=o.customer_id  AND u.customer_type != :tat "
						+ "  AND o.`create_time`  BETWEEN :"+st+" AND :"+et 
						+ "  AND u.id = :uid ) tt9,"
						+ "(SELECT IFNULL(SUM(o.`order_amount`),0) AS outAmount "
						+ "FROM t_customer_order o,t_customer_user u "
						+ "WHERE order_type =:out and order_detail_type NOT IN (:dts) AND o.`order_status`=:sts "
						+ "AND u.id=o.customer_id  AND u.customer_type != :tat "
						+ "  AND o.`create_time`  BETWEEN :"+st+" AND :"+et 
						+ "  AND u.id = :uid ) tt10,"
						+ " t_customer_cash tt3 WHERE tt3.`customer_id` = tt.uid";
				sql1 = "select " + date + sql;
				sb.append(sql1);
			}else{
				sb.append(" union all "+sql1);
			}
		}
		
		query = getSession().createSQLQuery(sb.toString());
		query.setParameter("sts", DataDictionaryUtil.ORDER_STATUS_SUCCESS);
		query.setParameter("uid", vo.getRsvdc3());
		query.setParameterList("adts", odts.toArray());
		//剔除测试人员数据
		query.setParameter("tat", DataDictionaryUtil.CUSTOMER_TYPE_TEST);
		
		List<Integer> bdts = new ArrayList<Integer>();
		bdts.add(DataDictionaryUtil.ORDER_DETAIL_WINNING_REBATES);
		bdts.add(DataDictionaryUtil.ORDER_DETAIL_BETTING_REBATES);
		bdts.add(DataDictionaryUtil.ORDER_DETAIL_BETTING_REVENUES);
		query.setParameterList("bdts", bdts.toArray());
		
		List<Integer> cdts = new ArrayList<Integer>();
		cdts.add(DataDictionaryUtil.ORDER_DETAIL_WINNING_REBATES);
		dts.addAll(cdts);
		query.setParameterList("cdts", cdts.toArray());
		
		List<Integer> ddts = new ArrayList<Integer>();
		ddts.add(DataDictionaryUtil.ORDER_DETAIL_CASH_ADD);
		ddts.add(DataDictionaryUtil.ORDER_DETAIL_OTHER_PAY);
		dts.addAll(ddts);
		query.setParameterList("ddts", ddts.toArray());
		
		List<Integer> edts = new ArrayList<Integer>();
		edts.add(DataDictionaryUtil.ORDER_DETAIL_WITHDRAWALS);
		dts.addAll(edts);
		query.setParameterList("edts", edts.toArray());
		
		List<Integer> fdts = new ArrayList<Integer>();
		fdts.add(DataDictionaryUtil.ORDER_DETAIL_BETTING_REBATES);
		dts.addAll(fdts);
		query.setParameterList("fdts", fdts.toArray());
		
		List<Integer> gdts = new ArrayList<Integer>();
		gdts.add(DataDictionaryUtil.ORDER_DETAIL_BETTING_REVENUES);
		dts.addAll(gdts);
		query.setParameterList("gdts", gdts.toArray());
		
		//所有已查询的订单类型之外的收入或支出订单类型
		query.setParameter("in", DataDictionaryUtil.ORDER_TYPE_INCOME);
		query.setParameter("out", DataDictionaryUtil.ORDER_TYPE_OUT);
		query.setParameterList("dts", dts.toArray());
		
		for(int i=0;i<=vo.getRsvdc1();i++){
			query.setParameter("st"+i, DateUtil.getNextDay(DateUtil.getStringDateShort(), "-"+i)+" 00:04:00");
			
			if(i!=0){
				query.setParameter("et"+i, DateUtil.getNextDay(DateUtil.getStringDateShort(), "-"+(i-1))+" 00:04:00");
				
				query.setParameter("pt"+i, DateUtil.getNextDay(DateUtil.getStringDateShort(), "-"+i));
				
			}else{
				query.setParameter("et"+i, DateUtil.getNextDay(DateUtil.getStringDateShort(), "1")+" 00:04:00");
			}
		}
		
		List<Object[]> list3 = query.list();
		
		List<TempMapVO> list4 = new ArrayList<TempMapVO>(list3.size());
		//装投注金额和中奖金额
		List<TempMapVO> list5 = new ArrayList<TempMapVO>(list3.size());
		//装充值金额和提款金额
		List<TempMapVO> list6 = new ArrayList<TempMapVO>(list3.size());
		//装投注返点金额和投注营收金额
		List<TempMapVO> list7 = new ArrayList<TempMapVO>(list3.size());
		//装其他收入金额和其他支出金额
		List<TempMapVO> list8 = new ArrayList<TempMapVO>(list3.size());
		for(Object[] objs : list3 ){
			TempMapVO tempVo = new TempMapVO();
			tempVo.setKey(objs[0].toString());
			tempVo.setValue(objs[1].toString());
			tempVo.setValue2(objs[2].toString());
			list4.add(tempVo);
			
			//装投注金额和中奖金额
			TempMapVO tempVo2 = new TempMapVO();
			tempVo2.setKey(objs[0].toString());
			tempVo2.setValue(objs[3].toString());
			tempVo2.setValue2(objs[4].toString());
			list5.add(tempVo2);
			
			//装充值金额和提款金额
			TempMapVO tempVo3 = new TempMapVO();
			tempVo3.setKey(objs[0].toString());
			tempVo3.setValue(objs[5].toString());
			tempVo3.setValue2(objs[6].toString());
			list6.add(tempVo3);
			
			//装投注返点金额和投注营收金额
			TempMapVO tempVo4 = new TempMapVO();
			tempVo4.setKey(objs[0].toString());
			tempVo4.setValue(objs[7].toString());
			tempVo4.setValue2(objs[8].toString());
			list7.add(tempVo4);
			
			//装其他收入金额和其他支出金额
			TempMapVO tempVo5 = new TempMapVO();
			tempVo5.setKey(objs[0].toString());
			tempVo5.setValue(objs[9].toString());
			tempVo5.setValue2(objs[10].toString());
			list8.add(tempVo5);
			
		}
		map.put("profitList", list4);
		map.put("baList", list5);
		map.put("rdList", list6);
		map.put("brList", list7);
		map.put("otherList", list8);
		
		//投注玩法分布
		String playSql = "SELECT t.`lottery_name`,b.`play_code`,b.`bet_money`,COUNT(1),p.`model_name` "
				+ " FROM t_bet_record b,t_lottery_type t,t_play_model p "
				+ " WHERE b.`customer_id`=:uid AND b.bet_status != :bsts "
				+ " AND b.`lottery_code` = t.`lottery_code` AND b.`play_code`=p.`model_code` "
				+ " AND b.`create_time` BETWEEN :st AND :et  AND t.`lottery_status`=:sts "
				+ " GROUP BY b.`lottery_code`,b.`play_code`,b.`bet_money` ";
		
		query = getSession().createSQLQuery(playSql);
		query.setParameter("sts", DataDictionaryUtil.STATUS_OPEN);
		query.setParameter("bsts", DataDictionaryUtil.BET_ORDER_TYPE_CANCEL);
		query.setParameter("uid", vo.getRsvdc3());
		query.setParameter("st", vo.getStartTime());
		query.setParameter("et", vo.getEndTime());
		List<Object[]> brList =query.list();
		//装其他收入金额和其他支出金额
		List<TempMapVO> list9 = new ArrayList<TempMapVO>(list3.size());
		//装玩法对应的玩法名称和被玩次数
		List<TempMapVO> list10 = new ArrayList<TempMapVO>(list3.size());
		int i=0;
		for(Object[] objs : brList){
			i++;
			//投注玩法分布
			TempMapVO tempVo = new TempMapVO();
			tempVo.setKey(objs[0].toString());
			tempVo.setValue(i+"");
			tempVo.setValue2(objs[2].toString());
			tempVo.setValue3(objs[3].toString());
			list9.add(tempVo);
			
			//投注玩法分布
			TempMapVO tempVo2 = new TempMapVO();
			tempVo2.setKey(i+""+"-"+objs[2].toString());
			tempVo2.setValue(objs[3].toString());
			tempVo2.setValue2(objs[4].toString());
			list10.add(tempVo2);
		}
		map.put("betPlayList", list9);
		map.put("playCountList",list10);
		return map;
	}

	@Override
	public Map<String, ?> queryMarketStatistic(Map<String, Object> param)
			throws Exception {
		Map<String, Object> map = new HashMap<String,Object>();
		ReportVO vo = (ReportVO) param.get("reportKey");
		boolean isPage = vo.getRsvst1().equals("true") ? true : false;
		String sql =
				//注册人数
				"SELECT 'register',COUNT(1),'注册人数' FROM t_customer_user u  "
				+ " WHERE u.create_time BETWEEN  :st AND :et AND u.customer_type != :tat "
				+ " UNION ALL "
				//充值人数
				+" SELECT 'recharge',COUNT(tt.c1),'充值人数' FROM ( SELECT COUNT(1) AS c1 FROM t_customer_order o"
				+ ",t_customer_user u WHERE u.id = o.customer_id AND u.customer_type != :tat "
				+ " AND  o.`order_detail_type` in (:rdts) AND o.order_status=:os "
				+ "AND  o.`create_time` BETWEEN  :st AND :et GROUP BY o.`customer_id`) tt"
				//首次充值人数
				+ " UNION ALL "
				+ " SELECT 'firstRc',COUNT(tt.c1),'首充人数' FROM ("
				+ " SELECT COUNT(1) AS c1  FROM t_customer_order o "
				+ ",t_customer_user u WHERE u.id = o.customer_id AND u.customer_type != :tat "
				+ " AND o.`order_detail_type` in (:rdts) "
				+ " AND o.order_status=:os AND  o.`create_time` BETWEEN  :st "
				+ " AND :et AND NOT EXISTS (SELECT 1 FROM "
				+ " t_customer_order o2 WHERE  o2.`order_detail_type` in (:rdts) AND o2.order_status=:os "
				+ " AND  o2.`create_time` <:st AND o2.customer_id = o.customer_id ) "
				+ " GROUP BY o.`customer_id`) tt "
				//提款人数
				+ " UNION ALL "
				+ " SELECT 'drawingCount',COUNT(tt.c1),'提款人数' FROM ("
				+ " SELECT COUNT(1) AS c1 FROM t_customer_order o "
				+ ",t_customer_user u WHERE u.id = o.customer_id AND u.customer_type != :tat "
				+ " AND o.`order_detail_type` = :dt2 "
				+ " AND o.order_status=:os AND  o.`create_time` "
				+ " BETWEEN  :st AND :et GROUP BY o.`customer_id`) tt "
				//提款次数
				+ " UNION ALL "
				+ " SELECT 'drawingTimes',COUNT(1),'提款次数' AS c1 FROM t_customer_order o "
				+ ",t_customer_user u WHERE u.id = o.customer_id AND u.customer_type != :tat "
				+ " AND  o.`order_detail_type` = :dt2"
				+ " AND o.order_status=:os "
				+ " AND  o.`create_time` BETWEEN  :st AND :et "
				//活跃人数
				+ " UNION ALL "
				+ " SELECT 'hot',COUNT(tt.c1),'活跃人数' FROM ("
				+ " SELECT COUNT(1) AS c1 FROM t_customer_order o "
				+ ",t_customer_user u WHERE u.id = o.customer_id AND u.customer_type != :tat "
				+ " AND  o.`order_detail_type` IN (:odts) AND o.order_status=:os "
				+ " AND  o.`create_time` BETWEEN  :st AND :et "
				+ " GROUP BY o.customer_id) tt "
				//充值金额
				+ " UNION ALL "
				+ " SELECT 'rcAmount',IFNULL(SUM(o.order_amount),0),'充值金额'  FROM t_customer_order o "
				+ ",t_customer_user u WHERE u.id = o.customer_id AND u.customer_type != :tat "
				+ " AND  o.`order_detail_type` in (:rdts) "
				+ " AND o.order_status=:os AND  o.`create_time` "
				+ " BETWEEN  :st AND :et "
				//提款金额
				+ " UNION ALL "
				+ " SELECT 'dwAmount',IFNULL(SUM(o.order_amount),0),'提款金额'  FROM t_customer_order o "
				+ ",t_customer_user u WHERE u.id = o.customer_id AND u.customer_type != :tat "
				+ " AND  o.`order_detail_type` = :dt2 "
				+ " AND o.order_status=:os AND  o.`create_time` "
				+ " BETWEEN  :st AND :et "
				//投注记录数
				+ " UNION ALL "
				+ " SELECT 'betCount',COUNT(1),'投注数'  FROM t_bet_record b "
				+ ",t_customer_user u WHERE u.id = b.customer_id AND u.customer_type != :tat "
				+ " AND b.bet_status != :bcs "
				+ " AND  b.`create_time` BETWEEN  :st AND :et"
				//中奖记录数
				+ " UNION ALL "
				+ " SELECT 'awardCount',COUNT(1),'中奖数'  FROM t_bet_record b "
				+ ",t_customer_user u WHERE u.id = b.customer_id AND u.customer_type != :tat "
				+ " AND b.bet_status = :bws "
				+ " AND  b.`create_time` BETWEEN  :st AND :et"
				//投注总金额
				+ " UNION ALL "
				+ " SELECT 'betAmount',IFNULL(SUM(o.order_amount),0),'投注总金额'  FROM t_customer_order o "
				+ ",t_customer_user u WHERE u.id = o.customer_id AND u.customer_type != :tat "
				+ " AND  o.`order_detail_type` IN (:odts)"
				+ " AND o.order_status=:os "
				+ " AND  o.`create_time` BETWEEN  :st AND :et"
				//中奖总金额
				+ " UNION ALL "
				+ " SELECT 'awardAmount',IFNULL(SUM(o.order_amount),0),'中奖总金额'  FROM t_customer_order o "
				+ ",t_customer_user u WHERE u.id = o.customer_id AND u.customer_type != :tat "
				+ " AND  o.`order_detail_type` = :dt3 "
				+ " AND o.order_status=:os"
				+ " AND  o.`create_time` BETWEEN  :st AND :et"
				//投注返款总金额
				+ " UNION ALL "
				+ " SELECT 'rebatesAmount',IFNULL(SUM(o.order_amount),0),'投注返款总金额'  FROM t_customer_order o "
				+ ",t_customer_user u WHERE u.id = o.customer_id AND u.customer_type != :tat "
				+ " AND  o.`order_detail_type` = :dt4 "
				+ " AND o.order_status=:os"
				+ " AND  o.`create_time` BETWEEN  :st AND :et"
				//投注盈收总金额
				+ " UNION ALL "
				+ " SELECT 'revenusAmount',IFNULL(SUM(o.order_amount),0),'投注盈收总金额'  FROM t_customer_order o "
				+ ",t_customer_user u WHERE u.id = o.customer_id AND u.customer_type != :tat "
				+ " AND  o.`order_detail_type` = :dt5 "
				+ " AND o.order_status=:os "
				+ " AND  o.`create_time` BETWEEN  :st AND :et ";
		//如果统计结束时间为今天，则统计现在的网站余额
		if(vo.getEndTime().substring(0, 10).equals(DateUtil.getStringDateShort())){
			sql = sql + " UNION ALL SELECT 'cashAmount',SUM(o.cash),'网站余额'  FROM t_customer_cash o,t_customer_user u"
					+ " WHERE o.customer_id=u.id AND  o.cash_status = :cs AND u.customer_type != :tat and u.customer_status =:cs ";
		}else{
			//如果统计结束时间，为之前的日期则查结束日期的最后网站余额
			sql = sql + " UNION ALL SELECT 'cashAmount',SUM(o.cash),'网站余额'  FROM t_customer_cash_h o,t_customer_user u"
					+ " WHERE  o.cash_status = :cs AND  o.process_date = :etd AND u.customer_type != :tat and u.customer_status =:cs ";
		}
		//撤单总金额
		sql = sql + " UNION ALL "
		+ " SELECT 'cancelAmount',IFNULL(SUM(o.order_amount),0),'撤单总金额'  FROM t_customer_order o "
		+ ",t_customer_user u WHERE u.id = o.customer_id AND u.customer_type != :tat "
		+ " AND  o.`order_detail_type` IN (:cdts) "
		+ " AND  o.order_status=:os "
		+ " AND  o.`create_time` BETWEEN  :st AND :et ";
					
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("cs", DataDictionaryUtil.STATUS_OPEN);
		query.setParameter("os", DataDictionaryUtil.ORDER_STATUS_SUCCESS);
		query.setParameter("st", vo.getStartTime());
		query.setParameter("et", vo.getEndTime());
		
		//如果统计结束时间为今天，则统计现在的网站余额
		if(!vo.getEndTime().substring(0, 10).equals(DateUtil.getStringDateShort())){
			query.setParameter("etd", vo.getEndTime().substring(0, 10));
		}
		
		//充值应统计第三方充值
		List<Integer> rdts = new ArrayList<Integer>(2);
		rdts.add(DataDictionaryUtil.ORDER_DETAIL_CASH_ADD);
		rdts.add(DataDictionaryUtil.ORDER_DETAIL_OTHER_PAY);
		query.setParameterList("rdts",rdts.toArray());
		
		query.setParameter("dt2", DataDictionaryUtil.ORDER_DETAIL_WITHDRAWALS);
		query.setParameter("dt3", DataDictionaryUtil.ORDER_DETAIL_WINNING_REBATES);
		query.setParameter("dt4", DataDictionaryUtil.ORDER_DETAIL_BETTING_REBATES);
		query.setParameter("dt5", DataDictionaryUtil.ORDER_DETAIL_BETTING_REVENUES);
		query.setParameter("bcs", DataDictionaryUtil.BET_ORDER_TYPE_CANCEL);
		query.setParameter("bws", DataDictionaryUtil.BET_ORDER_TYPE_WIN);
		//剔除测试人员数据
		query.setParameter("tat", DataDictionaryUtil.CUSTOMER_TYPE_TEST);
		
		List<Integer> odts = new ArrayList<Integer>();
		odts.add(DataDictionaryUtil.ORDER_DETAIL_ORDINARY_BETTING);
		odts.add(DataDictionaryUtil.ORDER_DETAIL_TRACK);
		query.setParameterList("odts", odts.toArray());
		
		List<Integer> cdts = new ArrayList<Integer>();
		cdts.add(DataDictionaryUtil.ORDER_DETAIL_CANCEL_STATUS);
		cdts.add(DataDictionaryUtil.ORDER_DETAIL_CHASE_AFTER_REBATES);
		cdts.add(DataDictionaryUtil.ORDER_DETAIL_SYSTEM_REBATES);
		query.setParameterList("cdts", cdts.toArray());
		
		List<Object[]> list = query.list();
		List<TempMapVO> mapVos = new ArrayList<TempMapVO>();
		for(Object[] objs : list){
			map.put(objs[0].toString(), objs[1]==null?"0":objs[1].toString());
			TempMapVO mapVo = new TempMapVO();
			mapVo.setKey(objs[2]==null?"0":objs[2].toString());
			mapVo.setValue(objs[1]==null?"0":objs[1].toString());
			mapVos.add(mapVo);
		}
		map.put("mapVos", mapVos);
		if(!isPage){
			return map;
		}
		sql = "SELECT t.id,o.`order_number`,o.`create_time`,o.`order_amount`,t.`customer_name`,"
				+ " t.`rebates`,t.`customer_level`,IFNULL(o.`account_balance`,0),t2.`customer_name` AS 'fname',"
				+ " o.id as 'oId',o.order_detail_type,o.rsvst1 "
				+ " FROM t_customer_user t ,t_customer_order o ,t_customer_cash c,t_customer_user t2"
				+ " WHERE t.id =o.customer_id AND c.customer_id = t.id AND t.customer_type != :tat  "
				+ " AND o.`order_detail_type` in (:rdts) And o.order_status =:sts "
				+ " AND o.create_time BETWEEN :st AND :et "
				+ " AND t.`customer_level`=t2.`customer_level`+1 "
				+ " AND (t.`allParentAccount` LIKE CONCAT('%,',t2.id) OR t.`allParentAccount` LIKE CONCAT('%',t2.id))";
		
		  query = getSession().createSQLQuery(sql);

		  //充值应统计第三方充值
		  query.setParameterList("rdts",rdts.toArray());
			
		  query.setParameter("st", vo.getStartTime());
		  //剔除测试人员数据
		  query.setParameter("tat", DataDictionaryUtil.CUSTOMER_TYPE_TEST);
		  query.setParameter("et", vo.getEndTime());
		  query.setParameter("sts", DataDictionaryUtil.ORDER_STATUS_SUCCESS);
		  int totalCount = query.list().size();
		  if(totalCount==0){
			  return map;
		  }
		  
		  int pageNum = vo.getPageNum();
		  int maxY = totalCount/vo.getMaxCount();
		  if(totalCount%vo.getMaxCount()!=0){
			maxY+=1;
		  }
		  pageNum = pageNum<=0?1:pageNum;
		  pageNum = pageNum>=maxY?maxY:pageNum;
			
		  query.setMaxResults(vo.getMaxCount());  
		  query.setFirstResult((pageNum-1)*vo.getMaxCount()); 
		  
		  List<Object[]> list2 = query.list();
		  List<Long> uIds = new ArrayList<Long>();
		  List<CustomerOrderVO> vos = new ArrayList<CustomerOrderVO>();
		  for(Object[] objs : list2){
			  uIds.add(Long.parseLong(objs[0].toString()));
			  CustomerOrderVO tempVo = new CustomerOrderVO();
			  tempVo.setCustomerId(Long.parseLong(objs[0].toString()));
			  tempVo.setOrderNumber(objs[1].toString());
			  tempVo.setCreateTime(DateUtil.strToDate2(objs[2].toString()));
			  tempVo.setOrderAmount(new BigDecimal(objs[3].toString()));
			  tempVo.setRsvst1(objs[4].toString());
			  tempVo.setRsvdc4(new BigDecimal(objs[5].toString()));
			  tempVo.setRsvst4(Util.transition(Long.parseLong(objs[6].toString()))+"级代理");
			  tempVo.setRsvdc5(new BigDecimal(objs[7].toString()));
			  tempVo.setId(Long.parseLong(objs[9].toString()));
			  tempVo.setRsvst2(objs[8].toString());
			  tempVo.setOrderDetailType(Integer.parseInt(objs[10].toString()));
			  if(objs[11]==null){
				  objs[11]="";
			  }
			  tempVo.setRsvst5(objs[11].toString());
			  vos.add(tempVo);
		  }
		  
		  //是否首次充值 	
		  String fsql = "SELECT COUNT(1),o.`customer_id`,min(o.id) FROM t_customer_order o "
		  + " WHERE  o.`order_detail_type` in (:rdts) "
		  + " AND o.order_status=:sts AND  o.`create_time` BETWEEN  :st AND :et AND o.customer_id in (:uids)"
		  + " AND NOT EXISTS (SELECT 1 FROM t_customer_order o2 WHERE  o2.`order_detail_type` in (:rdts) "
		  + " AND o2.order_status=:sts AND  o2.`create_time` <:st "
		  + " AND o2.customer_id = o.customer_id ) GROUP BY o.`customer_id` ";
		  
		  query = getSession().createSQLQuery(fsql);
		  //充值应统计第三方充值
		  query.setParameterList("rdts",rdts.toArray());
		  query.setParameter("st", vo.getStartTime());
		  query.setParameter("et", vo.getEndTime());
		  query.setParameter("sts", DataDictionaryUtil.ORDER_STATUS_SUCCESS);
		  query.setParameterList("uids", uIds.toArray());
		  List<Object[]> list3 = query.list();
		  if(list3.size()>0){
			  for(CustomerOrderVO tempVo : vos){
				  for(Object[] objs : list3){
					  if(tempVo.getCustomerId()==Long.parseLong(objs[1].toString())
							  &&tempVo.getId()==Long.parseLong(objs[2].toString())){
						  //标识为首充的用户
						  tempVo.setRsvst3("frc");
						  break;
					  }
				  }
			  }
		  }
		  //充值次数 
		  String tsql ="SELECT COUNT(1),o.`customer_id` FROM t_customer_order o WHERE  o.`order_detail_type` in (:rdts) "
		  		+ " AND o.order_status=:sts AND  o.`create_time` BETWEEN  :st AND :et AND o.customer_id in (:uids) "
		  		+ "GROUP BY o.`customer_id`";
		  query = getSession().createSQLQuery(tsql);
		  //充值应统计第三方充值
		  query.setParameterList("rdts",rdts.toArray());
		  query.setParameter("st", vo.getStartTime());
		  query.setParameter("et", vo.getEndTime());
		  query.setParameter("sts", DataDictionaryUtil.ORDER_STATUS_SUCCESS);
		  query.setParameterList("uids", uIds.toArray());
		  List<Object[]> list4 = query.list();
		 
		for(CustomerOrderVO tempVo : vos){
			for(Object[] objs : list4){
				if(tempVo.getCustomerId()==Long.parseLong(objs[1].toString())){
					//标识充值次数
					tempVo.setRsvdc2(Long.parseLong(objs[0].toString()));
					break;
				}
			}
		 }
		map.put("rcList", vos);
		map.put("pageCount", maxY);
		map.put("pageNum", pageNum);
		return map;
	}

	@Override
	public Map<String, ?> queryAvgPerStasData(Map<String, Object> param)
			throws Exception {
		Map<String, Object> map = new HashMap<String,Object>();
		ReportVO vo = (ReportVO) param.get("reportKey");
		long days = DateUtil.getTwoDay(vo.getEndTime(), vo.getStartTime());
		
		StringBuffer sb = new StringBuffer("");
		StringBuffer yksb = new StringBuffer("");
		for(int i=0;i<days;i++){
			String date = DateUtil.getNextDay3(vo.getEndTime(), "-"+(i+1));
			String st = "st"+i;
			String et = "et"+i;
			String pt = "pt"+i;
			String sql ="SELECT 'betAmount','投注人均值','"+date+"',SUM(tt.oa)/COUNT(1) FROM ("
				+ " SELECT SUM(o.order_amount) AS oa,o.`customer_id`  FROM t_customer_order o "
				+ ",t_customer_user u WHERE u.id = o.customer_id AND u.customer_type != :tat "
				+ " AND o.`order_detail_type` IN (:odts) AND o.order_status=:sts "
				+ " AND  o.`create_time` BETWEEN  :"+st+" AND :"+et
				+ " GROUP BY o.`customer_id`) tt "
				+ " UNION ALL "
				+ " SELECT 'awardAmount','派奖人均值','"+date+"',SUM(tt.oa)/COUNT(1) FROM ("
				+ " SELECT  SUM(o.order_amount) AS oa,o.`customer_id` FROM t_customer_order o "
				+ ",t_customer_user u WHERE u.id = o.customer_id AND u.customer_type != :tat "
				+ " AND  o.`order_detail_type` = :dt1 AND o.order_status=:sts "
				+ " AND  o.`create_time` BETWEEN  :"+st+" AND :"+et
				+ " GROUP BY o.`customer_id`) tt "
				+ " UNION ALL "
				+ " SELECT 'rcAmount','充值人均值','"+date+"',SUM(tt.oa)/COUNT(1) FROM ("
				+ " SELECT  SUM(o.order_amount) AS oa,o.`customer_id` FROM t_customer_order o "
				+ ",t_customer_user u WHERE u.id = o.customer_id AND u.customer_type != :tat "
				+ " AND  o.`order_detail_type` in (:rdts) AND o.order_status=:sts "
				+ " AND  o.`create_time` BETWEEN  :"+st+" AND :"+et
				+ " GROUP BY o.`customer_id`) tt "
				+ " UNION ALL "
				+ " SELECT 'drAmount','提款人均值','"+date+"',SUM(tt.oa)/COUNT(1) FROM ("
				+ " SELECT  SUM(o.order_amount) AS oa,o.`customer_id` FROM t_customer_order o "
				+ ",t_customer_user u WHERE u.id = o.customer_id AND u.customer_type != :tat "
				+ " AND  o.`order_detail_type` = :dt3 AND o.order_status=:sts"
				+ " AND  o.`create_time` BETWEEN  :"+st+" AND :"+et
				+ " GROUP BY o.`customer_id`) tt ";
				if(i==0){
					//网站余额
					sql = sql + " UNION ALL SELECT 'cashAmount','余额人均值','"+date+"',SUM(tt.oa)/COUNT(1) FROM ("
							+ " SELECT SUM(o.cash) AS oa,o.id  FROM t_customer_cash o "
							+ ",t_customer_user u WHERE u.id = o.customer_id AND u.customer_type != :tat "
							+ " AND  o.cash_status = :cs "
							+ " GROUP BY o.id ) tt ";
				}else{
					//网站余额
					sql = sql + " UNION ALL SELECT 'cashAmount','余额人均值','"+date+"',SUM(tt.oa)/COUNT(1) FROM ("
							+ " SELECT SUM(o.cash) AS oa,o.id  FROM t_customer_cash_h o "
							+ ",t_customer_user u WHERE u.id = o.customer_id AND u.customer_type != :tat "
							+ " AND  o.cash_status = :cs "
							+ " AND  o.process_date = :"+pt+" GROUP BY o.id ) tt ";
				}
				
				String ykSql = "SELECT 'betAmount','"+date+"',IFNULL(SUM(o.order_amount),0)  FROM t_customer_order o "
						+ ",t_customer_user u WHERE u.id = o.customer_id AND u.customer_type != :tat "
						+ " AND  o.`order_detail_type` IN (:odts) AND o.order_status=:sts "
						+ " AND  o.`create_time` BETWEEN  :"+st+" AND :"+et
						+ " UNION ALL "
						+ " SELECT 'awardAmount','"+date+"',IFNULL(SUM(o.order_amount),0)  FROM t_customer_order o "
						+ ",t_customer_user u WHERE u.id = o.customer_id AND u.customer_type != :tat "
						+ " AND  o.`order_detail_type` IN (:dt1) AND o.order_status=:sts "
						+ " AND  o.`create_time` BETWEEN  :"+st+" AND :"+et
						+ " UNION ALL "
						+ " SELECT 'rebatesAmount','"+date+"',IFNULL(SUM(o.order_amount),0)  FROM t_customer_order o "
						+ ",t_customer_user u WHERE u.id = o.customer_id AND u.customer_type != :tat "
						+ " AND  o.`order_detail_type` IN (:dt2) AND o.order_status=:sts "
						+ " AND  o.`create_time` BETWEEN  :"+st+" AND :"+et
						+ " UNION ALL "
						+ " SELECT 'revenusAmount','"+date+"',IFNULL(SUM(o.order_amount),0)  FROM t_customer_order o "
						+ ",t_customer_user u WHERE u.id = o.customer_id AND u.customer_type != :tat "
						+ " AND  o.`order_detail_type` IN (:dt3) AND o.order_status=:sts "
						+ " AND  o.`create_time` BETWEEN  :"+st+" AND :"+et ;
				
				
				if(i==0){
					sb.append(sql);
					yksb.append(ykSql);
				}else{
					sb.append(" UNION ALL "+sql);
					yksb.append(" UNION ALL "+ykSql);
				}
		}
		
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		
		SQLQuery ykquery = getSession().createSQLQuery(yksb.toString());
		
		List<Integer> odts = new ArrayList<Integer>();
		odts.add(DataDictionaryUtil.ORDER_DETAIL_ORDINARY_BETTING);
		odts.add(DataDictionaryUtil.ORDER_DETAIL_TRACK);
		query.setParameter("cs", DataDictionaryUtil.STATUS_OPEN);
		query.setParameter("tat", DataDictionaryUtil.CUSTOMER_TYPE_TEST);
		query.setParameter("dt1", DataDictionaryUtil.ORDER_DETAIL_WINNING_REBATES);
		//充值应统计第三方充值
		List<Integer> rdts = new ArrayList<Integer>(2);
		rdts.add(DataDictionaryUtil.ORDER_DETAIL_CASH_ADD);
		rdts.add(DataDictionaryUtil.ORDER_DETAIL_OTHER_PAY);
		query.setParameterList("rdts",rdts.toArray());
		
		query.setParameter("dt3", DataDictionaryUtil.ORDER_DETAIL_WITHDRAWALS);
		query.setParameter("sts", DataDictionaryUtil.ORDER_STATUS_SUCCESS);
		query.setParameterList("odts", odts.toArray());
		
		ykquery.setParameter("dt1", DataDictionaryUtil.ORDER_DETAIL_WINNING_REBATES);
		ykquery.setParameter("dt2", DataDictionaryUtil.ORDER_DETAIL_BETTING_REBATES);
		ykquery.setParameter("dt3", DataDictionaryUtil.ORDER_DETAIL_BETTING_REVENUES);
		ykquery.setParameter("sts", DataDictionaryUtil.ORDER_STATUS_SUCCESS);
		ykquery.setParameterList("odts", odts.toArray());
		ykquery.setParameter("tat", DataDictionaryUtil.CUSTOMER_TYPE_TEST);
		
		for(int i=0;i<days;i++){
			query.setParameter("st"+i, DateUtil.getNextDay(DateUtil.getStringDateShort(), "-"+i)+" 00:04:00");
			
			ykquery.setParameter("st"+i, DateUtil.getNextDay(DateUtil.getStringDateShort(), "-"+i)+" 00:04:00");
			
			if(i!=0){
				query.setParameter("pt"+i, DateUtil.getNextDay(DateUtil.getStringDateShort(), "-"+i));
				query.setParameter("et"+i, DateUtil.getNextDay(DateUtil.getStringDateShort(), "-"+(i-1))+" 00:04:00");
				
				ykquery.setParameter("et"+i, DateUtil.getNextDay(DateUtil.getStringDateShort(), "-"+(i-1))+" 00:04:00");
			}else{
				query.setParameter("et"+i, DateUtil.getNextDay(DateUtil.getStringDateShort(), "1")+" 00:04:00");
				ykquery.setParameter("et"+i, DateUtil.getNextDay(DateUtil.getStringDateShort(), "1")+" 00:04:00");
			}
		}
		
		List<Object[]> list = query.list();
		List<TempMapVO> mapVos = new ArrayList<TempMapVO>();
		Map<String,List<TempMapVO>> tempMap = new HashMap<String,List<TempMapVO>>();
		for(Object[] objs : list){
			TempMapVO mapVo = new TempMapVO();
			mapVo.setKey(objs[0].toString());
			mapVo.setValue(objs[3]==null?"0.00":objs[3].toString());
			if(null==tempMap.get(objs[2].toString())){
				List<TempMapVO> tempVos = new ArrayList<TempMapVO>();
				tempVos.add(mapVo);
				tempMap.put(objs[2].toString(), tempVos);
			}else{
				List<TempMapVO> tempVos = tempMap.get(objs[2].toString());
				tempVos.add(mapVo);
				tempMap.remove(objs[2].toString());
				tempMap.put(objs[2].toString(), tempVos);
			}
		}
		
		for(String key : tempMap.keySet()){
			TempMapVO mapVo = new TempMapVO();
			mapVo.setKey(key);
			mapVo.setVos(tempMap.get(key));
			mapVos.add(mapVo);
		}
		map.put("mapVos", mapVos);
		
		//盈亏统计
		Map<String,TempMapVO> tempMap2 = new HashMap<String,TempMapVO>();
		List<Object[]> yklist = ykquery.list();
		
		BigDecimal totalBet = BigDecimal.ZERO;
		BigDecimal totalAward = BigDecimal.ZERO;
		BigDecimal totalRebates = BigDecimal.ZERO;
		BigDecimal totalRevenus = BigDecimal.ZERO;
		
		for(Object[] objs : yklist){
			//计算总金额
			if(objs[0].toString().equals("betAmount")){
				totalBet= totalBet.add(new BigDecimal(objs[2].toString()));
			}else{
				if(objs[0].toString().equals("awardAmount")){
					totalAward= totalAward.add(new BigDecimal(objs[2].toString()));
				}else if(objs[0].toString().equals("rebatesAmount")){
					totalRebates= totalRebates.add(new BigDecimal(objs[2].toString()));
				}else if(objs[0].toString().equals("revenusAmount")){
					totalRevenus= totalRevenus.add(new BigDecimal(objs[2].toString()));
				}
			}
			
			if(null==tempMap2.get(objs[1].toString())){
				TempMapVO mapVo = new TempMapVO();
				mapVo.setKey(objs[1].toString());
				if(objs[0].toString().equals("betAmount")){
					mapVo.setValue(objs[2].toString());
				}else{
					mapVo.setValue2(objs[2].toString());
				}
				tempMap2.put(objs[1].toString(), mapVo);
			}else{
				TempMapVO mapVo = tempMap2.get(objs[1].toString());
				if(objs[0].toString().equals("betAmount")){
					mapVo.setValue(objs[2].toString());
				}else{
					if(!StringUtils.isEmpty(mapVo.getValue2())){
						BigDecimal lossAmount = new BigDecimal(mapVo.getValue2());
						BigDecimal amount = new BigDecimal(objs[2].toString());
						lossAmount = lossAmount.add(amount);
						mapVo.setValue2(lossAmount.toString());
					}else{
						mapVo.setValue2(objs[2].toString());
					}
				}
				tempMap2.remove(objs[1].toString());
				tempMap2.put(objs[1].toString(), mapVo);
			}
		}
		
		List<TempMapVO> mapVos2 = new ArrayList<TempMapVO>();
		
		for(String key : tempMap2.keySet()){
			TempMapVO mapVo = new TempMapVO();
			mapVo.setKey(key);
			
			TempMapVO vo2 = tempMap2.get(key);
			BigDecimal betAmount = new BigDecimal(vo2.getValue());
			BigDecimal lossAmount = new BigDecimal(vo2.getValue2());
			BigDecimal ykAmount = betAmount.subtract(lossAmount);
			mapVo.setValue(ykAmount.toString());
			
			mapVos2.add(mapVo);
		}
		map.put("ykVos", mapVos2);
		map.put("totalBet", totalBet);
		map.put("totalAward", totalAward);
		map.put("totalRebates", totalRebates);
		map.put("totalRevenus", totalRevenus);
		return map;
	}

	@Override
	public Map<String, ?> queryAmountStasData(Map<String, Object> param)
			throws Exception {
		Map<String, Object> map = new HashMap<String,Object>();
		ReportVO vo = (ReportVO) param.get("reportKey");
		long days = DateUtil.getTwoDay(vo.getEndTime(), vo.getStartTime());
		
		StringBuffer sb = new StringBuffer("");
		for(int i=0;i<days;i++){
			String date = DateUtil.getNextDay3(vo.getEndTime(), "-"+(i+1));
			String st = "st"+i;
			String et = "et"+i;
			String pt = "pt"+i;
			String sql ="SELECT 'betAmount','投注总额','"+date+"',SUM(o.order_amount)  FROM t_customer_order o "
				+ ",t_customer_user u WHERE u.id = o.customer_id AND u.customer_type != :tat "
				+ " AND  o.`order_detail_type` IN (:odts) AND o.order_status=:sts "
				+ " AND  o.`create_time` BETWEEN  :"+st+" AND :"+et 
				+ " UNION ALL "
				+ " SELECT 'awardAmount','返奖总额','"+date+"',SUM(o.order_amount)  FROM t_customer_order o "
				+ ",t_customer_user u WHERE u.id = o.customer_id AND u.customer_type != :tat "
				+ " AND  o.`order_detail_type` = :dt1 AND o.order_status=:sts "
				+ " AND  o.`create_time` BETWEEN  :"+st+" AND :"+et 
				+ " UNION ALL "
				+ " SELECT 'rcAmount','充值总额','"+date+"',SUM(o.order_amount) FROM t_customer_order o "
				+ ",t_customer_user u WHERE u.id = o.customer_id AND u.customer_type != :tat "
				+ " AND  o.`order_detail_type` in (:rdts) AND o.order_status=:sts "
				+ " AND  o.`create_time` BETWEEN  :"+st+" AND :"+et 
				+ " UNION ALL "
				+ " SELECT 'drAmount','提款总额','"+date+"',SUM(o.order_amount) FROM t_customer_order o "
				+ ",t_customer_user u WHERE u.id = o.customer_id AND u.customer_type != :tat "
				+ " AND  o.`order_detail_type` = :dt3 AND o.order_status=:sts"
				+ " AND  o.`create_time` BETWEEN  :"+st+" AND :"+et 
				+ " UNION ALL "
				+ " SELECT 'rbAmount','返点总额','"+date+"',SUM(o.order_amount)  FROM t_customer_order o "
				+ ",t_customer_user u WHERE u.id = o.customer_id AND u.customer_type != :tat "
				+ " AND  o.`order_detail_type` in (:odts2) AND o.order_status=:sts"
				+ " AND  o.`create_time` BETWEEN  :"+st+" AND :"+et ;
				if(i==0){
					//网站余额
					sql = sql + " UNION ALL SELECT 'cashAmount','网站余额','"+date+"',SUM(o.cash) "
							+ " FROM t_customer_cash o "
							+ ",t_customer_user u WHERE u.id = o.customer_id AND u.customer_type != :tat "
							+ " AND  o.cash_status = :cs ";
				}else{
					//网站余额
					sql = sql + " UNION ALL SELECT 'cashAmount','网站余额','"+date+"',SUM(o.cash) "
							+ " FROM t_customer_cash_h o "
							+ ",t_customer_user u WHERE u.id = o.customer_id AND u.customer_type != :tat "
							+ " AND  o.cash_status = :cs "
							+ " AND  o.process_date = :"+pt;
				}
				
				if(i==0){
					sb.append(sql);
				}else{
					sb.append(" UNION ALL "+sql);
				}
		}
		
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		
		List<Integer> odts = new ArrayList<Integer>();
		odts.add(DataDictionaryUtil.ORDER_DETAIL_ORDINARY_BETTING);
		odts.add(DataDictionaryUtil.ORDER_DETAIL_TRACK);
		
		List<Integer> odts2 = new ArrayList<Integer>();
		odts2.add(DataDictionaryUtil.ORDER_DETAIL_BETTING_REBATES);
		odts2.add(DataDictionaryUtil.ORDER_DETAIL_BETTING_REVENUES);
		
		query.setParameter("tat", DataDictionaryUtil.CUSTOMER_TYPE_TEST);
		query.setParameter("cs", DataDictionaryUtil.STATUS_OPEN);
		query.setParameter("dt1", DataDictionaryUtil.ORDER_DETAIL_WINNING_REBATES);
		//充值应统计第三方充值
		List<Integer> rdts = new ArrayList<Integer>(2);
		rdts.add(DataDictionaryUtil.ORDER_DETAIL_CASH_ADD);
		rdts.add(DataDictionaryUtil.ORDER_DETAIL_OTHER_PAY);
		query.setParameterList("rdts",rdts.toArray());
		
		query.setParameter("dt3", DataDictionaryUtil.ORDER_DETAIL_WITHDRAWALS);
		query.setParameter("sts", DataDictionaryUtil.ORDER_STATUS_SUCCESS);
		query.setParameterList("odts", odts.toArray());
		query.setParameterList("odts2", odts2.toArray());
		
		for(int i=0;i<days;i++){
			query.setParameter("st"+i, DateUtil.getNextDay(DateUtil.getStringDateShort(), "-"+i)+" 00:04:00");
			if(i!=0){
				query.setParameter("pt"+i, DateUtil.getNextDay(DateUtil.getStringDateShort(), "-"+i));
				query.setParameter("et"+i, DateUtil.getNextDay(DateUtil.getStringDateShort(), "-"+(i-1))+" 00:04:00");
			}else{
				query.setParameter("et"+i, DateUtil.getNextDay(DateUtil.getStringDateShort(), "1")+" 00:04:00");
			}
		}
		
		List<Object[]> list = query.list();
		List<TempMapVO> mapVos = new ArrayList<TempMapVO>();
		Map<String,List<TempMapVO>> tempMap = new HashMap<String,List<TempMapVO>>();
		for(Object[] objs : list){
			TempMapVO mapVo = new TempMapVO();
			mapVo.setKey(objs[0].toString());
			mapVo.setValue(objs[3]==null?"0.00":objs[3].toString());
			if(null==tempMap.get(objs[2].toString())){
				List<TempMapVO> tempVos = new ArrayList<TempMapVO>();
				tempVos.add(mapVo);
				tempMap.put(objs[2].toString(), tempVos);
			}else{
				List<TempMapVO> tempVos = tempMap.get(objs[2].toString());
				tempVos.add(mapVo);
				tempMap.remove(objs[2].toString());
				tempMap.put(objs[2].toString(), tempVos);
			}
		}
		
		for(String key : tempMap.keySet()){
			TempMapVO mapVo = new TempMapVO();
			mapVo.setKey(key);
			mapVo.setVos(tempMap.get(key));
			mapVos.add(mapVo);
		}
		map.put("amountVos", mapVos);
		return map;
	}

	@Override
	public List<Object[]> queryUserFirends(CustomerUser user) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer("SELECT t.`id`,t.`customer_name` FROM t_customer_user t");
		sql.append("WHERE t.`customer_online_status` = 11002");
		sql.append("AND t.`customer_status` = 10002");
		sql.append("AND t.`id` = ?");
		sql.append("UNION ALL");
		sql.append("SELECT t.`id`,t.`customer_name` FROM t_customer_user t");
		sql.append("WHERE t.`customer_online_status` = 11002");
		sql.append("AND t.`customer_status` = 10002");
		sql.append("AND t.`customer_superior` = ?");
		Query query = getSession().createSQLQuery(sql.toString());
		query.setParameter(0, user.getCustomerSuperior());
		query.setParameter(1, user.getId());
		return query.list();
	}

	@Override
	public List<Long> queryUserIdByType(String customer) throws Exception {
		// TODO Auto-generated method stub
		List<Long> ids = new ArrayList<Long>();
		StringBuffer queryString = new StringBuffer(" from CustomerUser t where 1=1 ");
		if(customer.equals("1")){
			queryString.append(" and t.customerType != 12003");
		}else{
			queryString.append(" and t.customerType = 12003");
		}
		Query query = getSession().createQuery(queryString.toString());
		List<CustomerUser> users = query.list();
		for(CustomerUser user : users){
			ids.add(user.getId());
		}
		if(ids.size()==0)ids.add(0L);
		return ids;
	}

	@Override
	public List<CustomerUser> querySunUsersByParent(CustomerUser customerUser)
			throws Exception {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer(" from CustomerUser t where 1=1 and t.customerOnlineStatus = 11002 and t.customerStatus = 10002 ");
		if(customerUser.getCustomerLevel() == 0){
			hql.append(" and (t.allParentAccount = :pid or t.allParentAccount like :lpid) ");
		}else{
			hql.append(" and (t.allParentAccount like :lpid or t.allParentAccount like :llpid) ");
		}
		Query query = getSession().createQuery(hql.toString());
		if(customerUser.getCustomerLevel() == 0){
			query.setParameter("pid", Long.toString(customerUser.getId()));
			query.setParameter("lpid", Long.toString(customerUser.getId())+",%");
		}else{
			query.setParameter("lpid", "%,"+Long.toString(customerUser.getId())+",%");
			query.setParameter("llpid", "%,"+Long.toString(customerUser.getId()));
		}
		List<CustomerUser> list = query.list();
		return list;
	}

	@Override
	public void checkCardNoIsExist(Map<String, Object> param) throws LotteryException {
		UserCardVO cardVo = (UserCardVO) param.get("userCard");
		String hql ="from UserCard where cardNo = :cn and status = :st ";
		Query query = getSession().createQuery(hql);
		String cardNo = AesUtil.encrypt(cardVo.getCardNo(), Md5Manage.getInstance().getMd5());
		query.setParameter("cn", cardNo);
		query.setParameter("st", DataDictionaryUtil.STATUS_OPEN);
		List<UserCard> ucs = query.list();
		if(ucs!=null&&ucs.size()>0){
			throw new LotteryException("亲，该银行卡号已经被绑定过了哦！");
		}
	}

	@Override
	public Page<CustomerUserVO, Object> queryTeamUserByKey(Map<String, Object> param) throws LotteryException {
		CustomerUserVO userVo = (CustomerUserVO) param.get("uservokey");
		CustomerUser user = (CustomerUser) param.get(CommonUtil.CUSTOMERUSERKEY);
		StringBuilder conditionsql = new StringBuilder();
		StringBuffer queryString = new StringBuffer("SELECT t.id,t.`rebates`,t.`customer_name`,");
				//+ "(SELECT IFNULL(SUM(t3.`quota_count`),0) FROM t_customer_quota t3 WHERE t3.customer_id = t.`id`) AS quotacount,");
		queryString.append(" IFNULL(t.`customer_alias`, '') AS alias,");
//		queryString.append(" (SELECT IFNULL(SUM(t1.bet_Money * t1.multiple), 0) FROM t_bet_record t1 "
//				+ "WHERE t1.customer_id = t.`id` AND t1.bet_status IN (21001, 21002, 21003) AND CONCAT(YEAR(t1.`create_time`),'-',MONTH(t1.`create_time`)) = :m ) AS amount"+ ",");
		queryString.append(" t.`customer_level`, t.`create_time`,t.customer_type,c.cash,t.customer_online_status ");
		queryString.append(" FROM t_customer_user t left join t_customer_cash c on t.id = c.customer_id "
				+ " WHERE t.`customer_status` = 10002 ");
		if (!StringUtils.isEmpty(userVo.getCustomerName())) {
			conditionsql
					.append(" AND (t.allParentAccount LIKE CONCAT(20028, ',', '%') OR t.allParentAccount LIKE CONCAT('%', ',', :userId )");
			conditionsql.append(" OR t.allParentAccount LIKE CONCAT('%', ',', :userId , ',', '%') OR (t.customer_level = 1 AND t.allParentAccount = :userId ))");
			
			conditionsql.append(" and t.customer_name = :un");
		} else {
			conditionsql.append(" and (t.allParentAccount = :pid or t.allParentAccount like :lpid) ");
		}
		
		if(org.apache.commons.lang3.StringUtils.isNotEmpty(userVo.getRsvst1())){
			conditionsql.append(" AND t.`customer_online_status` = :onlineStatus");
		}
		
		StringBuffer countString = new StringBuffer("SELECT count(1) FROM t_customer_user t "
				+ " left join t_customer_cash c on t.id = c.customer_id "
				+ " WHERE t.`customer_status` = 10002 ");
		
		// 返点只针对直接下级
		if(userVo.getRebates()!=null && userVo.getRebates().compareTo(BigDecimal.ZERO)>0 && StringUtils.isEmpty(userVo.getCustomerName())){
			conditionsql.append(" and t.rebates = :rb ");
		}
		// 余额查询只针对直接下级
		if(!userVo.getCashMoney().equals("-") && StringUtils.isEmpty(userVo.getCustomerName())){
			conditionsql.append(" and c.cash >= :ba and c.cash <= :ea ");
		}
		// 排序只针对直接下属
		if(!userVo.getIsDesc().equals("cash") && StringUtils.isEmpty(userVo.getCustomerName())){
			queryString.append(conditionsql).append(" order by t."+userVo.getIsDesc()+" desc ");
		}else{
			queryString.append(conditionsql).append(" order by c."+userVo.getIsDesc()+" desc ");
		}
		
		SQLQuery query = getSession().createSQLQuery(queryString.toString());  
		
		//查询总记录数
		SQLQuery countquery = getSession().createSQLQuery(countString.append(conditionsql).toString());
		if(org.apache.commons.lang3.StringUtils.isNotEmpty(userVo.getRsvst1())){
			countquery.setParameter("onlineStatus", userVo.getRsvst1());
			query.setParameter("onlineStatus", userVo.getRsvst1());
		}
		if (StringUtils.isEmpty(userVo.getCustomerName())) {
			countquery.setParameter("pid", Long.toString(user.getId()));
			countquery.setParameter("lpid", "%," + Long.toString(user.getId()));
			query.setParameter("pid", Long.toString(user.getId()));
			query.setParameter("lpid", "%,"+Long.toString(user.getId()));
		}else{
			query.setParameter("userId", user.getId());
			countquery.setParameter("userId", user.getId());
			query.setParameter("un", userVo.getCustomerName());
			countquery.setParameter("un", userVo.getCustomerName());
		}
		
		if(userVo.getRebates()!=null && userVo.getRebates().compareTo(BigDecimal.ZERO)>0 && StringUtils.isEmpty(userVo.getCustomerName())){
			countquery.setParameter("rb", userVo.getRebates().divide(new BigDecimal("100")));
		}
		if(!userVo.getCashMoney().equals("-") && StringUtils.isEmpty(userVo.getCustomerName())){
			String ba = userVo.getCashMoney().split("-")[0];
			String ea = userVo.getCashMoney().split("-")[1];
			countquery.setParameter("ba", new BigDecimal(ba));
			countquery.setParameter("ea", new BigDecimal(ea));
		}
        BigInteger temp = (BigInteger) countquery.list().get(0);
        int totalCount =  temp.intValue();  
		
        //查询具体数据
		Page<CustomerUserVO, Object> page = new Page<CustomerUserVO, Object>();
		int pageNum = userVo.getPageNum();
		int maxY = totalCount/userVo.getMaxCount();
		if(totalCount%page.getMaxCount()!=0){
			maxY+=1;
		}
		pageNum = pageNum<=0?1:pageNum;
		pageNum = pageNum>=maxY?maxY:pageNum;
		page.setPageNum(pageNum);
		if(totalCount!=0){
			if(userVo.getRebates()!=null && userVo.getRebates().compareTo(BigDecimal.ZERO)>0 && StringUtils.isEmpty(userVo.getCustomerName())){
				query.setParameter("rb", userVo.getRebates().divide(new BigDecimal("100")));
			}
			if(!userVo.getCashMoney().equals("-") && StringUtils.isEmpty(userVo.getCustomerName())){
				String ba = userVo.getCashMoney().split("-")[0];
				String ea = userVo.getCashMoney().split("-")[1];
				query.setParameter("ba", new BigDecimal(ba));
				query.setParameter("ea", new BigDecimal(ea));
			}
			List<Object> entitylist = query.list();    
			page.setEntitylist(entitylist);
		}else{
			List<Object> entitylist = new ArrayList<Object>();    
			page.setEntitylist(entitylist);
		}
		page.setPageCount(maxY);
		//总记录数
		page.setTotalCount(totalCount);
		return page;
	}

	@Override
	public List<Object> queryTeamNum(String userId) throws LotteryException {
		// 查询直接下级和全体成员，其中全体成员不包含自己。
		StringBuilder hql = new StringBuilder("select count(1) from t_customer_user t where t.customer_superior =").append(userId);
		hql.append(" and t.`customer_status` = 10002 and t.`customer_online_status` = 11002 "); // 启用、激活的用户
		hql.append(" union all select count(u.id) from t_customer_user u WHERE u.customer_type != 12003 ");
		hql.append(" and (u.allParentAccount like CONCAT(").append(userId).append(", ',', '%') or u.allParentAccount like CONCAT('%', ',',").append(userId);
		hql.append(" ) or u.allParentAccount like CONCAT('%', ',',").append(userId).append(", ',', '%') ");
		hql.append(" or (u.customer_level = 1 and u.allParentAccount = ").append(userId).append("))");
		hql.append(" and u.`customer_status` = 10002 and u.`customer_online_status` = 11002 "); // 启用、激活的用户
		SQLQuery query = getSession().createSQLQuery(hql.toString());
		return query.list();
	}

	@Override
	public List<CustomerUser> getDirectSubCustomer(Map<String, String> param) throws LotteryException,ParseException {
		StringBuilder hql = new StringBuilder("select * from t_customer_user t where t.customer_superior =:userId ");
		hql.append(" and t.`customer_status` = 10002 and t.`customer_online_status` = 11002 ");
		if(!StringUtils.isEmpty(param.get("sdate"))){
			 hql.append(" and t.create_time > :sdate");
		}
		if(!StringUtils.isEmpty(param.get("edate"))){
			 hql.append(" and t.create_time <= :edate");
		}
		SQLQuery query = getSession().createSQLQuery(hql.toString());
		query.setParameter("userId", param.get("userId"));
		if(!StringUtils.isEmpty(param.get("sdate"))){
			 query.setParameter("sdate", param.get("sdate").toString());
		}
		if(!StringUtils.isEmpty(param.get("edate"))){
			 query.setParameter("edate", param.get("edate").toString());
		}
		return query.list();
	}

	@Override
	public List<CustomerUser> getAllTeamCustomer(Map<String, String> param) throws LotteryException,ParseException {
		StringBuilder hql = new StringBuilder("select count(u.id) from t_customer_user u WHERE ");
		hql.append(" (u.allParentAccount like CONCAT( :userId, ',', '%') or u.allParentAccount like CONCAT('%', ',',:userId ");
		hql.append(" ) or u.allParentAccount like CONCAT('%', ',',:userId, ',', '%') ");
		hql.append(" or (u.customer_level = 1 and u.allParentAccount = :userId ))");
		hql.append(" and u.`customer_status` = 10002 and u.`customer_online_status` = 11002 ");
		if(!StringUtils.isEmpty(param.get("sdate"))){
			 hql.append(" and u.create_time > :sdate");
		}
		if(!StringUtils.isEmpty(param.get("edate"))){
			 hql.append(" and u.create_time <= :edate");
		}
		SQLQuery query = getSession().createSQLQuery(hql.toString());
		query.setParameter("userId", param.get("userId"));
		// 默认查询全部
		if(!StringUtils.isEmpty(param.get("sdate"))){
			 query.setParameter("sdate", param.get("sdate"));
		}
		if(!StringUtils.isEmpty(param.get("edate"))){
			 query.setParameter("edate", param.get("edate"));
		}
		return query.list();
	}


	@Override
	public BigDecimal getCustomerUserMaxRebatesByParentId(long parentId) {
		// TODO Auto-generated method stub
		//SELECT customer_name, MAX(rebates) from t_customer_user where allParentAccount LIKE CONCAT('20027',',%')
		StringBuffer hql = new StringBuffer(" SELECT MAX(rebates) from t_customer_user where   ");
		hql.append( " allParentAccount LIKE CONCAT("+parentId+",',%') "
				+ "OR allParentAccount LIKE CONCAT('%,',"+parentId+")"
				+ "OR allParentAccount LIKE CONCAT('%,',"+parentId+",',%') "
				+ "OR (customer_level= 1 AND allParentAccount = "+parentId+")" );
		
		/*Transaction tx = getSession().beginTransaction();   
		tx.commit();*/
		//Query query = sessionFactory.getCurrentSession().createSQLQuery(sql); 
		Query query = getSession().createSQLQuery(hql.toString());
		//Long count = (Long)session.createQuery("select count(*) from Student").uniqueResult();
		return (BigDecimal)query.uniqueResult();
	}
	
	
}
