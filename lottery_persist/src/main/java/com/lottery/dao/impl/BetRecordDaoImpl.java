package com.lottery.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.BetRecord;
import com.lottery.bean.entity.CustomerCash;
import com.lottery.bean.entity.CustomerOrder;
import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.LotteryType;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.BetRecordVO;
import com.lottery.bean.entity.vo.CustomerOrderStaVo;
import com.lottery.bean.entity.vo.ReportVO;
import com.lottery.dao.IBetRecordDao;
import com.lottery.dao.ICustomerOrderDao;
import com.lottery.dao.ICustomerUserDao;
import com.lottery.dao.IOrderSequenceDao;
import com.lottery.dao.ITaskConfigDao;
import com.lottery.persist.generice.GenericDAO;
import com.xl.lottery.desutil.AesUtil;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;
import com.xl.lottery.util.DateUtil;

@Repository
@SuppressWarnings("unchecked")
public class BetRecordDaoImpl extends GenericDAO<BetRecord> implements IBetRecordDao {
	@Autowired
	private IOrderSequenceDao orderSequenceDao;

	@Autowired
	private ICustomerOrderDao orderDao;

	@Autowired
	private ITaskConfigDao configDao;

	@Autowired
	private ICustomerUserDao userDao;

	public BetRecordDaoImpl() {
		super(BetRecord.class);
	}

	@Override
	public String updateBetWinning(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		final List<String> sqls = (List<String>) param.get("bets");
		String status = getSession().doReturningWork(new ReturningWork<String>() {
			@Override
			public String execute(Connection connection) throws SQLException {
				Statement stmt = connection.createStatement();
				for (String sql : sqls) {
					stmt.addBatch(sql);
				}
				stmt.executeBatch();
				return "success";
			}

		});
		return status;
	}

	private void releaseQuietly(ResultSet resultSet, PreparedStatement cs) {
		if (resultSet == null) {
			return;
		}
		try {
			resultSet.close();
		} catch (SQLException e) {
			// ignore
		}
		if (cs == null) {
			return;
		}
		try {
			cs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void updateAwardUser(Map<String, Object> param) throws Exception {
		final String winBets = (String) param.get("betsKey");
		// final String ordNos = (String) param.get("betOrdNosKey");
		final String awardCounts = (String) param.get("awardCountsKey");
		final String awardLevels = (String) param.get("awardLevelsKey");
		final String lotteryCode = (String) param.get("lotteryCode");
		final String issueNo = (String) param.get("issueNo");
		// 先拆分获奖的投注记录的ids
		getSession().doWork(new Work() {
			@Override
			public void execute(Connection connection) throws SQLException {
				CallableStatement cs = null;
				try {
					// 1.拆分中奖记录的ids字符串，和订单编号orderNos字符串;2.对中奖的bet对应的用户加奖。（生成中奖记录订单）。
					cs = connection.prepareCall("{ call p_update_award(?,?,?,?,?,?,?)}");
					cs.setString(1, winBets);
					// cs.setString(2, ordNos);
					cs.setString(2, awardCounts);
					cs.setString(3, awardLevels);
					cs.setString(4, ";");
					cs.setString(5, ",");
					cs.setString(6, lotteryCode);
					cs.setString(7, issueNo);
					cs.execute();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					cs.close();
				}
			}
		});

	}

	@Override
	public void updatePayBetRebates(Map<String, Object> param) throws Exception {
		final String lotteryCode = (String) param.get("lotteryCode");
		final String issueNo = (String) param.get("issueNo");
		getSession().doWork(new Work() {
			@Override
			public void execute(Connection connection) throws SQLException {
				CallableStatement cs1 = null;
				try {
					// 对此期的所有投注用户及其父级返还返点金额（生成返点订单），并且给追期订单的完成期数加1。
					cs1 = connection.prepareCall("{ call p_update_rebates(?,?)}");
					cs1.setString(1, lotteryCode);
					cs1.setString(2, issueNo);
					cs1.execute();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					cs1.close();
				}
			}
		});

	}

	@Override
	public Page<BetRecordVO, BetRecord> queryBetRecords(Map<String, ?> param) throws Exception {
		// TODO Auto-generated method stub
		BetRecordVO vo = (BetRecordVO) param.get("betRecordkeyVo");
		List<Object> params = new ArrayList<Object>();
		StringBuffer hqlquery = new StringBuffer("select count(id) from BetRecord t where 1=1 ");
		StringBuffer queryString = new StringBuffer(" from BetRecord t where 1=1 ");
		StringBuffer counthql = new StringBuffer("select t.betStatus,sum(t.betMoney * t.multiple), ");
		counthql.append("SUM(t.betMoney * t.multiple * t.rebates),SUM(t.winMoney) from BetRecord t where 1=1 ");
		if (!vo.getStartTime().equals("")) {
			hqlquery = hqlquery.append(" and createTime >= ? ");
			queryString = queryString.append(" and createTime >= ? ");
			counthql = counthql.append(" and createTime >= ? ");
			params.add(DateUtil.strToDate2(vo.getStartTime()));
		}
		if (!vo.getEndTime().equals("")) {
			hqlquery = hqlquery.append(" and createTime <= ? ");
			queryString = queryString.append(" and createTime <= ? ");
			counthql = counthql.append(" and createTime <= ? ");
			params.add(DateUtil.strToDate2(vo.getEndTime()));
		}
		if (vo.getCustomerId() != 0) {
			hqlquery = hqlquery.append(" and customerId = ? ");
			queryString = queryString.append(" and customerId = ? ");
			counthql = counthql.append(" and customerId = ? ");
			params.add(vo.getCustomerId());
		}
		if (vo.getBetStatus() != 0) {
			hqlquery = hqlquery.append(" and betStatus = ? ");
			queryString = queryString.append(" and betStatus = ? ");
			counthql = counthql.append(" and betStatus = ? ");
			params.add(vo.getBetStatus());
		}
		if (!vo.getLotteryCode().equals("0")) {
			hqlquery = hqlquery.append(" and lotteryCode in (:lotteryCode) ");
			queryString = queryString.append(" and lotteryCode in (:lotteryCode) ");
			counthql = counthql.append(" and lotteryCode in (:lotteryCode) ");
		}

		if (!vo.getPlayCode().equals("0")) {
			hqlquery = hqlquery.append(" and playCode in (:playCode) ");
			queryString = queryString.append(" and playCode in (:playCode) ");
			counthql = counthql.append(" and playCode in (:playCode) ");
		}

		if (!vo.getCustometTypes().equals("0")) {
			if (vo.getCustometTypes().equals("1")) {
				hqlquery = hqlquery
						.append(" and customerId in (select id from CustomerUser t where 1=1  and t.customerType != 12003) ");
				queryString = queryString
						.append(" and customerId in (select id from CustomerUser t where 1=1  and t.customerType != 12003) ");
				counthql = counthql
						.append(" and customerId in (select id from CustomerUser t where 1=1  and t.customerType != 12003) ");
			} else {
				hqlquery = hqlquery
						.append(" and customerId in (select id from CustomerUser t where 1=1  and t.customerType = 12003) ");
				queryString = queryString
						.append(" and customerId in (select id from CustomerUser t where 1=1  and t.customerType = 12003) ");
				counthql = counthql
						.append(" and customerId in (select id from CustomerUser t where 1=1  and t.customerType = 12003) ");
			}
		}

		if (!StringUtils.isEmpty(vo.getIssueNo())) {
			hqlquery = hqlquery.append(" and issueNo = :isu ");
			queryString = queryString.append(" and issueNo = :isu ");
			counthql = counthql.append(" and issueNo = :isu ");
		}
		hqlquery.append(" order by createTime desc ");
		queryString.append(" order by createTime desc ");
		counthql.append(" group by betStatus ");

		int maxCount = this.getbetRecordCount(hqlquery.toString(), params, vo);
		Page<BetRecordVO, BetRecord> page = new Page<BetRecordVO, BetRecord>();
		int pageNum = vo.getPageNum();
		int maxY = maxCount / vo.getMaxCount();
		if (maxCount % page.getMaxCount() != 0) {
			maxY += 1;
		}
		pageNum = pageNum <= 0 ? 1 : pageNum;
		pageNum = pageNum >= maxY ? maxY : pageNum;
		page.setPageNum(pageNum);
		Query query = getSession().createQuery(queryString.toString());
		int i = 0;
		for (Object obj : params) {
			query.setParameter(i, obj);
			i++;
		}
		if (queryString.indexOf(":lotteryCode") > -1) {
			query.setParameterList("lotteryCode", vo.getLotteryCode().split(","));
		}
		if (queryString.indexOf(":playCode") > -1) {
			query.setParameterList("playCode", vo.getPlayCode().split(","));
		}
		if (queryString.indexOf(":uids") > -1) {
			List<Long> ids = userDao.queryUserIdByType(vo.getCustometTypes());
			query.setParameterList("uids", ids.toArray());
		}
		if (queryString.indexOf(":isu") > -1) {
			query.setParameter("isu", vo.getIssueNo());
		}
		query.setMaxResults(vo.getMaxCount());
		query.setFirstResult((pageNum - 1) * vo.getMaxCount());
		List<BetRecord> entitylist = query.list();
		page.setEntitylist(entitylist);
		page.setPageCount(maxY);

		Query query1 = getSession().createQuery(counthql.toString());
		int x = 0;
		for (Object obj : params) {
			query1.setParameter(x, obj);
			x++;
		}
		if (counthql.indexOf(":lotteryCode") > -1) {
			query1.setParameterList("lotteryCode", vo.getLotteryCode().split(","));
		}
		if (counthql.indexOf(":playCode") > -1) {
			query1.setParameterList("playCode", vo.getPlayCode().split(","));
		}
		if (queryString.indexOf(":isu") > -1) {
			query1.setParameter("isu", vo.getIssueNo());
		}
		List<Object[]> objs = query1.list();
		page.setCellList(objs);
		return page;
	}

	private int getbetRecordCount(String hql, List<Object> params, BetRecordVO vo) throws Exception {
		Query query = getSession().createQuery(hql);
		int i = 0;
		for (Object obj : params) {
			query.setParameter(i, obj);
			i++;
		}
		if (hql.indexOf(":lotteryCode") > -1) {
			query.setParameterList("lotteryCode", vo.getLotteryCode().split(","));
		}
		if (hql.indexOf(":playCode") > -1) {
			query.setParameterList("playCode", vo.getPlayCode().split(","));
		}
		if (hql.indexOf(":uids") > -1) {
			List<Long> ids = userDao.queryUserIdByType(vo.getCustometTypes());
			query.setParameterList("uids", ids.toArray());
		}
		if (hql.indexOf(":isu") > -1) {
			query.setParameter("isu", vo.getIssueNo());
		}
		Long count = (Long) query.list().get(0);
		return Integer.parseInt(count.toString());
	}

	@Override
	public BigDecimal getLowerLevelSumAmount(Map<String, ?> param) throws Exception {
		// TODO Auto-generated method stub
		Long customerId = (Long) param.get("customerId");
		StringBuffer queryString = new StringBuffer("SELECT ");
		queryString.append(" IFNULL(SUM(t.`bet_money`*t.`Multiple`), 0) AS amount FROM t_bet_record t ");
		queryString
				.append(" WHERE t.`customer_id` IN (SELECT t1.id FROM t_customer_user t1 WHERE ( t1.`customer_level` = 1 ");
		queryString
				.append(" AND t1.`allParentAccount` = ?) OR t1.`allParentAccount` LIKE ? OR t1.`allParentAccount` LIKE ?)");
		queryString.append(" AND t.`bet_status` IN (21001,21002,21003)");
		Query query = getSession().createSQLQuery(queryString.toString());
		query.setParameter(0, customerId);
		query.setParameter(1, customerId.toString().concat(",%"));
		query.setParameter(2, "%,".concat(customerId.toString()));
		if (query.list().size() == 0)
			return BigDecimal.ZERO;
		BigDecimal amount = (BigDecimal) query.list().get(0);
		return amount;
	}

	@Override
	public Page<BetRecordVO, BetRecord> queryBetRecordsWebApp(Map<String, Object> param) throws Exception {
		BetRecordVO vo = (BetRecordVO) param.get("berRecordKey");
		List<Long> ids = (List<Long>) param.get("idsKey");
		StringBuffer sb = new StringBuffer();
		Map<String, String> issueNos = configDao.queryCurrentAllTask(param);
		String str = "(";
		for (String st : issueNos.keySet()) {
			if (str.length() == 1) {
				str = str.concat("(").concat(" o.lotteryCode=").concat(st).concat(" and o.issueNo <= ")
						.concat(issueNos.get(st)).concat(" )");
			} else {
				str = str.concat("or (").concat(" o.lotteryCode=").concat(st).concat(" and o.issueNo <= ")
						.concat(issueNos.get(st)).concat(" )");
			}
		}
		str = str.concat(")");
		sb.append("from BetRecord o where 1=1 and " + str);
		StringBuffer counsb = new StringBuffer(" select COUNT(o.id) from BetRecord o where 1=1 and " + str);
		if (StringUtils.isNotEmpty(vo.getStartTime()) && StringUtils.isNotEmpty(vo.getEndTime())) {
			sb.append(" and o.createTime >= '").append(vo.getStartTime()).append("' and o.createTime <= '")
					.append(vo.getEndTime()).append("'");
			counsb.append(" and o.createTime >= '").append(vo.getStartTime()).append("' and o.createTime <='")
					.append(vo.getEndTime()).append("'");
		}
		
		if (vo.getBetType() != null && vo.getBetType().equals("true")) {
			sb.append(" and o.betType =").append(DataDictionaryUtil.BET_TYPE_TRACK);
			counsb.append(" and o.betType =").append(DataDictionaryUtil.BET_TYPE_TRACK);
		}

		if (vo.getLotteryCode() != null && !vo.getLotteryCode().equals("")) {
			sb.append(" and o.lotteryCode = ? ");
			counsb.append(" and o.lotteryCode = ? ");
		}
		if (vo.getPlayCode() != null && !vo.getPlayCode().equals("")) {
			sb.append(" and o.playCode = ? ");
			counsb.append(" and o.playCode = ? ");
		}

		if (vo.getBetStatus() != 0) {
			sb.append(" and o.betStatus = ? ");
			counsb.append(" and o.betStatus = ? ");
		}
		if(StringUtils.isNotEmpty(vo.getIssueNo())){
			sb.append(" and o.issueNo = ?");
			counsb.append(" and o.issueNo = ?");
		}
		if(ids!=null&&ids.size()>0){
			sb.append(" and o.customerId in (:ids) ");
			counsb.append(" and o.customerId in (:ids) ");
		}
        
		sb.append(" order by o.createTime desc ");

		List<String> limitKeys = new ArrayList<String>();
		List<Object> limitVals = new ArrayList<Object>();

		if (vo.getLotteryCode() != null && !vo.getLotteryCode().equals("")) {
			limitKeys.add("lotteryCode");
			limitVals.add(vo.getLotteryCode());
		}
		if (vo.getPlayCode() != null && !vo.getPlayCode().equals("")) {
			limitKeys.add("playCode");
			limitVals.add(vo.getPlayCode());
		}
		if (vo.getBetStatus() != 0) {
			limitKeys.add("betStatus");
			limitVals.add(vo.getBetStatus());
		}
		if(StringUtils.isNotEmpty(vo.getIssueNo())){
			limitKeys.add("issueNo");
			limitVals.add(vo.getIssueNo());
		}
		if(ids!=null&&ids.size()>0){
			limitKeys.add("ids");
			limitVals.add(ids);
		}

		Page<BetRecordVO, BetRecord> page = (Page<BetRecordVO, BetRecord>) pageQuery(vo, sb, counsb, limitKeys,
				limitVals);
		return page;
	}

	@Override
	public Page<BetRecordVO, BetRecord> queryBetRecordsWebAppBySelf(Map<String, Object> param) throws Exception {
		BetRecordVO vo = (BetRecordVO) param.get("berRecordKey");
		CustomerUser user = (CustomerUser) param.get(CommonUtil.CUSTOMERUSERKEY);
		StringBuffer sb = new StringBuffer();
		Map<String, String> issueNos = configDao.queryCurrentAllTask(param);
		String str = "(";
		for (String st : issueNos.keySet()) {
			if (str.length() == 1) {
				str = str.concat("(").concat(" o.lotteryCode=").concat(st).concat(" and o.issueNo <= ")
						.concat(issueNos.get(st)).concat(" )");
			} else {
				str = str.concat("or (").concat(" o.lotteryCode=").concat(st).concat(" and o.issueNo <= ")
						.concat(issueNos.get(st)).concat(" )");
			}
		}
		str = str.concat(")");
		sb.append("from BetRecord o where 1=1 and " + str + " and o.customerId = " + user.getId());
		StringBuffer counsb = new StringBuffer(" select COUNT(o.id) from BetRecord o where 1=1 and " + str
				+ " and o.customerId = " + user.getId());
		if (StringUtils.isNotEmpty(vo.getStartTime()) && StringUtils.isNotEmpty(vo.getEndTime())) {
			sb.append(" and o.createTime >= '").append(vo.getStartTime()).append("' and o.createTime <= '")
					.append(vo.getEndTime()).append("'");
			counsb.append(" and o.createTime >= '").append(vo.getStartTime()).append("' and o.createTime <='")
					.append(vo.getEndTime()).append("'");
		}
		if (vo.getBetType() != null && vo.getBetType().equals("true")) {
			sb.append(" and o.betType =").append(DataDictionaryUtil.BET_TYPE_TRACK);
			counsb.append(" and o.betType =").append(DataDictionaryUtil.BET_TYPE_TRACK);
		}

		if (vo.getLotteryCode() != null && !vo.getLotteryCode().equals("")) {
			sb.append(" and o.lotteryCode = ? ");
			counsb.append(" and o.lotteryCode = ? ");
		}
		if (vo.getPlayCode() != null && !vo.getPlayCode().equals("")) {
			sb.append(" and o.playCode = ? ");
			counsb.append(" and o.playCode = ? ");
		}

		if (vo.getBetStatus() != 0) {
			sb.append(" and o.betStatus = ? ");
			counsb.append(" and o.betStatus = ? ");
		}

		sb.append(" order by o.createTime desc ");

		List<String> limitKeys = new ArrayList<String>();
		List<Object> limitVals = new ArrayList<Object>();

		if (vo.getLotteryCode() != null && !vo.getLotteryCode().equals("")) {
			limitKeys.add("lotteryCode");
			limitVals.add(vo.getLotteryCode());
		}
		if (vo.getPlayCode() != null && !vo.getPlayCode().equals("")) {
			limitKeys.add("playCode");
			limitVals.add(vo.getPlayCode());
		}
		if (vo.getBetStatus() != 0) {
			limitKeys.add("betStatus");
			limitVals.add(vo.getBetStatus());
		}
		Page<BetRecordVO, BetRecord> page = (Page<BetRecordVO, BetRecord>) pageQuery(vo, sb, counsb, limitKeys,
				limitVals);
		return page;
	}

	/**
	 * 没有任何投注中奖，更新当期的所有投注记录的betStatus为未中奖.
	 */
	@Override
	public void updateBetNoWinner(Map<String, Object> param) {
		String lotteryCode = (String) param.get("lotteryCode");
		String issueNo = (String) param.get("issueNo");
		// 更新当期的所有投注记录的betStatus为未中奖
		String sql = "update t_bet_record set bet_status= ? where bet_status=? "
				+ " and lottery_code = ? and issue_no=? ";

		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.setParameter(0, DataDictionaryUtil.BET_ORDER_TYPE_LOST);
		sqlQuery.setParameter(1, DataDictionaryUtil.BET_ORDER_TYPE_SUCCESS);
		sqlQuery.setParameter(2, lotteryCode);
		sqlQuery.setParameter(3, issueNo);
		sqlQuery.executeUpdate();

	}

	@Override
	public List<BetRecord> queryBetRecordsByOrderNo(Map<String, Object> param) throws Exception {
		String orderNo = (String) param.get("orderNo");
		CustomerOrder order = (CustomerOrder) param.get("orderKey");

		StringBuffer queryString = new StringBuffer("from BetRecord t where t.orderNo = ? ");
		if (null != param.get("betStatusKey")) {
			// 如果是手动撤销的追号投注
			if (!StringUtils.isEmpty(order.getRsvst2())) {
				queryString.append(" and t.issueNo = :ino ");
			} else {
				queryString.append(" and (t.rsvst1 is null or t.rsvst1 != :rs1 ) ");
			}

			if (!StringUtils.isEmpty(order.getRsvst3())) {
				queryString.append(" and t.id = :bid ");
			}
			queryString.append(" and betStatus = :bs group by t.issueNo,t.bileNum,t.betNum ");
		}

		Query query = getSession().createQuery(queryString.toString());
		query.setParameter(0, orderNo);
		if (null != param.get("betStatusKey")) {
			int betStatus = (int) param.get("betStatusKey");
			// 如果是手动撤销的追号投注
			if (!StringUtils.isEmpty(order.getRsvst2())) {
				String issueNo = order.getRsvst2().split(":")[1];
				query.setParameter("ino", issueNo);
				query.setParameter("bs", betStatus);
			} else {
				query.setParameter("rs1", "hand");
				query.setParameter("bs", betStatus);
			}

			if (!StringUtils.isEmpty(order.getRsvst3())) {
				query.setParameter("bid", Long.parseLong(order.getRsvst3()));
			}
		}
		List<BetRecord> betRecord = query.list();
		return betRecord;
	}

	@Override
	public void updateAwardEncrypt(Map<String, Object> param) throws Exception {
		String lotteryCode = (String) param.get("lotteryCode");
		String issueNo = (String) param.get("issueNo");
		String encrypt = AesUtil.encrypt("天下无贼", lotteryCode + issueNo);
		String sql = "update t_bet_record set rsvst1 = ? where lottery_code=?"
				+ " and issue_no = ?  and bet_status = ? ";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.setParameter(0, encrypt);
		sqlQuery.setParameter(1, lotteryCode);
		sqlQuery.setParameter(2, issueNo);
		sqlQuery.setParameter(3, DataDictionaryUtil.BET_ORDER_TYPE_WIN);
		sqlQuery.executeUpdate();
	}

	/**
	 * 中奖后终止追号
	 */
	@Override
	public void updateBetAwardStop(Map<String, Object> param) throws Exception {
		String lotteryCode = (String) param.get("lotteryCode");
		String issueNo = (String) param.get("issueNo");
		List<BetRecord> records = (List<BetRecord>) param.get("wingRecordsKey");
		Map<String, BetRecord> betMap = new HashMap<String, BetRecord>();
		List<String> orderNos = new ArrayList<String>();
		for (BetRecord record : records) {
			// 标记中奖的投注记录
			if (null == betMap.get(record.getOrderNo())) {
				betMap.put(record.getOrderNo(), record);
				orderNos.add(record.getOrderNo());
			} else {
				betMap.put(record.getOrderNo(), record);
				continue;
			}
		}

		String sql = "SELECT r.* FROM t_customer_order o,t_bet_record r " + " WHERE o.order_Number=r.order_No  "
				+ " AND o.awardStop=?  AND r.issue_no >? and r.lottery_code = ?  AND r.bet_status=? "
				+ " AND r.order_no IN (:orderNos) ";
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter(0, DataDictionaryUtil.COMMON_FLAG_1);
		query.setParameter(1, issueNo);
		query.setParameter(2, lotteryCode);
		query.setParameter(3, DataDictionaryUtil.BET_ORDER_TYPE_SUCCESS);
		query.setParameterList("orderNos", orderNos.toArray());
		query.addEntity(BetRecord.class);
		records = query.list();
		if (records.size() == 0) {
			return;
		}

		List<Long> betIds = new ArrayList<Long>();
		for (BetRecord record : records) {
			betIds.add(record.getId());
		}
		// 设置投注记录的状态为撤单
		sql = "update t_bet_record set bet_status =:cs where id in (:betIds)";
		query = getSession().createSQLQuery(sql);
		query.setParameter("cs", DataDictionaryUtil.BET_ORDER_TYPE_CANCEL);
		query.setParameterList("betIds", betIds.toArray());
		query.executeUpdate();
		// 分组查询统计订单应退金额
		sql = "SELECT r.order_no,sum(r.bet_money*r.multiple) as totalAmount,r.customer_id"
				+ " FROM t_bet_record r where r.`id` IN (:betIds) group by r.order_no ";
		query = getSession().createSQLQuery(sql);
		query.setParameterList("betIds", betIds.toArray());
		List<Object[]> results = query.list();

		for (Object[] result : results) {
			// 更新原始订单的returnAmount,并且设置订单的追期数为总追期数。即已撤销的追期算已完成。
			sql = "update t_customer_order set return_amount = return_amount + ? , rsvdc2 = rsvdc1 where order_number = ? and order_status = ? ";
			query = getSession().createSQLQuery(sql);
			query.setParameter(0, new BigDecimal(result[1].toString()));
			query.setParameter(1, result[0].toString());
			query.setParameter(2, DataDictionaryUtil.ORDER_STATUS_SUCCESS);
			query.executeUpdate();
			// 更新用户的余额
			sql = "update t_customer_cash set cash = cash+? where customer_id = ? and cash_status = ? ";
			query = getSession().createSQLQuery(sql);
			query.setParameter(0, new BigDecimal(result[1].toString()));
			query.setParameter(1, Long.parseLong(result[2].toString()));
			query.setParameter(2, DataDictionaryUtil.STATUS_OPEN);
			query.executeUpdate();

			// 记录用户余额
			sql = "select * from t_customer_cash where customer_id = ? and cash_status = ? ";
			query = getSession().createSQLQuery(sql);
			query.setParameter(0, new BigDecimal(result[2].toString()));
			query.setParameter(1, DataDictionaryUtil.STATUS_OPEN);
			query.addEntity(CustomerCash.class);
			CustomerCash userCash = (CustomerCash) query.list().get(0);

			// 生成撤单订单记录
			String orderNumber = orderSequenceDao.getOrderSequence(CommonUtil.ORDER_DETAIL_BET_ORDER, 8);
			CustomerOrder cancelOrder = new CustomerOrder();
			cancelOrder.setCustomerId(Long.parseLong(result[2].toString()));
			cancelOrder.setOrderStatus(DataDictionaryUtil.ORDER_STATUS_SUCCESS);
			cancelOrder.setOrderNumber(orderNumber);
			cancelOrder.setOrderType(DataDictionaryUtil.ORDER_TYPE_INCOME);
			cancelOrder.setOrderDetailType(DataDictionaryUtil.ORDER_DETAIL_CHASE_AFTER_REBATES);
			cancelOrder.setCashAmount(new BigDecimal(result[1].toString()));
			cancelOrder.setReceiveAmount(new BigDecimal(result[1].toString()));
			cancelOrder.setOrderAmount(new BigDecimal(result[1].toString()));
			cancelOrder.setRsvst1(result[0].toString());
			// 记录用户余额
			cancelOrder.setAccountBalance(userCash.getCash());

			cancelOrder.setCancelTime(DateUtil.getNowDate());
			cancelOrder.setOrderTime(DateUtil.getNowDate());
			cancelOrder.setCreateTime(DateUtil.getNowDate());
			cancelOrder.setUpdateTime(DateUtil.getNowDate());
			cancelOrder.setCreateUser("job");
			cancelOrder.setUpdateUser("job");
			orderDao.insert(cancelOrder);
		}

	}

	@Override
	public Map<String, BigDecimal> lotterySalesInfo(Map<String, Object> param) throws Exception {
		String sql = "";
		SQLQuery query = null;
		// query.setParameter(0, DataDictionaryUtil.STATUS_OPEN);
		List<LotteryType> types = (List<LotteryType>) param.get("lotteryListKey");

		Map<String, BigDecimal> lotMoneyMap = new HashMap<String, BigDecimal>();
		Map<String, BigDecimal> groupMoneyMap = new HashMap<String, BigDecimal>();

		for (LotteryType type : types) {
			if (groupMoneyMap.get(type.getLotteryGroup()) == null) {
				List<LotteryType> groupLots = new ArrayList<LotteryType>();
				groupLots.add(type);
				groupMoneyMap.put(type.getLotteryGroup(), BigDecimal.ZERO);
			}

		}

		BigDecimal allAmount = BigDecimal.ZERO;
		for (LotteryType type : types) {
			sql = "SELECT IFNULL(SUM(bet_money),0) FROM t_bet_record WHERE lottery_code = ? "
					+ "AND bet_status >= ? AND bet_status < ? AND DATE_FORMAT(create_time,'%Y-%m') = ? ";
			query = getSession().createSQLQuery(sql);
			query.setParameter(0, type.getLotteryCode());
			query.setParameter(1, DataDictionaryUtil.BET_ORDER_TYPE_SUCCESS);
			query.setParameter(2, DataDictionaryUtil.BET_ORDER_TYPE_CANCEL);
			query.setParameter(3, DateUtil.getPrevMonth());

			BigDecimal amount = (BigDecimal) query.list().get(0);
			lotMoneyMap.put(type.getLotteryCode(), amount);
			BigDecimal totalAmount = groupMoneyMap.get(type.getLotteryGroup());
			totalAmount = totalAmount.add(amount);
			groupMoneyMap.remove(type.getLotteryGroup());
			groupMoneyMap.put(type.getLotteryGroup(), totalAmount);
			allAmount = allAmount.add(amount);
		}
		groupMoneyMap.put("allLotsAmount", allAmount);
		groupMoneyMap.putAll(lotMoneyMap);
		return groupMoneyMap;
	}

	@Override
	public void addBetRecordsByTrack(final long orderid, final String issueNo) throws Exception {
		// TODO Auto-generated method stub
		getSession().doWork(new Work() {
			@Override
			public void execute(Connection connection) throws SQLException {
				CallableStatement cs1 = null;
				try {
					// 对此期的所有投注用户及其父级返还返点金额（生成返点订单），并且给追期订单的完成期数加1。
					cs1 = connection.prepareCall("{ call p_insert_trace_order(?,?,?,?)}");
					cs1.setLong(1, orderid);
					cs1.setString(2, issueNo);
					cs1.setString(3, ",");
					cs1.setString(4, ":");
					cs1.execute();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					cs1.close();
				}
			}
		});
	}

	@Override
	public Page<Object, Object> queryYkReport(Map<String, Object> param) throws Exception {
		CustomerUser user = (CustomerUser) param.get(CommonUtil.CUSTOMERUSERKEY);
		ReportVO vo = (ReportVO) param.get("reportVO");
		Page<Object, Object> page = new Page<Object, Object>();
		StringBuffer sql = new StringBuffer(
				"from CustomerUser u where u.customerStatus=?");
		StringBuffer sqlcount = new StringBuffer(
				"SELECT COUNT(u.id) from CustomerUser u where u.customerStatus=?");
		if(StringUtils.isNotEmpty(vo.getUname())) {
			sqlcount.append(" and u.customerSuperior = ? and u.customerName =?");
			sql.append(" and u.customerSuperior = ? and u.customerName =?");
		}else{
			sqlcount.append(" and (u.customerSuperior = ? or u.id =?)");
			sql.append(" and (u.customerSuperior = ? or u.id =?)");
		}
		Query query = getSession().createQuery(sqlcount.toString());
		if (StringUtils.isNotEmpty(vo.getUname())) {
			query.setParameter(1, user.getId());
			query.setParameter(2, vo.getUname());
		} else {
			query.setParameter(1, user.getId());
			query.setParameter(2, user.getId());
		}
		
		query.setParameter(0, DataDictionaryUtil.STATUS_OPEN);
		int totalCount = ((Long) query.list().get(0)).intValue();
		int pageNum = vo.getPageNum();
		int maxY = totalCount / vo.getMaxCount();
		if (totalCount % vo.getMaxCount() != 0) {
			maxY += 1;
		}
		pageNum = pageNum <= 0 ? 1 : pageNum;
		pageNum = pageNum >= maxY ? maxY : pageNum;
		page.setPageNum(pageNum);
		Query query1 = getSession().createQuery(sql.toString());
		if (StringUtils.isNotEmpty(vo.getUname())) {
			query1.setParameter(1, user.getId());
			query1.setParameter(2, vo.getUname());
		} else {
			query1.setParameter(1, user.getId());
			query1.setParameter(2, user.getId());
		}
		
		query1.setParameter(0, DataDictionaryUtil.STATUS_OPEN);
		query1.setMaxResults(vo.getMaxCount());
		query1.setFirstResult((pageNum - 1) * vo.getMaxCount());
		List<CustomerUser> userlis = query1.list();
		this.queryYkmx(userlis, page, vo);
		page.setPageCount(maxY);
		// 总记录数
		page.setTotalCount(totalCount);
		return page;
	}

	/**
	 * 查询盈亏明细
	 * 
	 * @return
	 */
	private void queryYkmx(List<CustomerUser> userlis, Page<Object, Object> page, ReportVO vo) {
		String temp = new String(
				"SELECT %id as id,%username as username,IFNULL(SUM(t.bet_money * t.Multiple),0) AS bet_money, ");
		temp = temp.concat(" IFNULL(SUM(t.bet_money * t.Multiple * t.rebates),0) AS re_monery, ");
		temp = temp.concat(" (SELECT IFNULL(SUM(o.order_amount), 0) FROM t_customer_order o  WHERE o.order_detail_type in (18015,18005) ");
		temp = temp.concat(" AND o.customer_id in (%id) AND o.create_time >=:sdate AND o.create_time <=:edate) AS lre_money, IFNULL(SUM(t.win_money),0) AS win_money,%customerType as customerType ");
		//活动
//		temp = temp.concat(" (SELECT IFNULL(SUM(o.order_amount), 0) FROM t_customer_order o  WHERE o.order_detail_type = 18012 ");
//		temp = temp.concat(" AND o.customer_id in (%ids) AND o.create_time >=:sdate AND o.create_time <=:edate) AS activity, ");
		//手续费
//		temp = temp.concat(" (SELECT IFNULL(SUM(o.order_amount), 0) FROM t_customer_order o  WHERE o.order_detail_type = 18010 ");
//		temp = temp.concat(" AND o.customer_id = %id AND o.create_time >=:sdate AND o.create_time <=:edate) AS fee ");
		temp = temp.concat(" FROM t_bet_record t ");
		temp = temp.concat(" WHERE t.bet_status IN (21002, 21003) AND t.customer_id in (%ids) ");
		temp = temp.concat(" AND t.create_time >= :sdate AND t.create_time <= :edate ");

		List<Object> objs = new ArrayList<Object>();
		for (CustomerUser user : userlis) {
			StringBuffer idsSql = new StringBuffer(
					"SELECT u.`id` FROM t_customer_user u WHERE u.customer_type != 12003 ");
			idsSql.append("AND (u.allParentAccount LIKE CONCAT(" + user.getId()
					+ ", ',', '%') OR u.allParentAccount LIKE CONCAT('%', ',', " + user.getId()
					+ ") OR u.allParentAccount LIKE CONCAT('%', ',', " + user.getId() + ", ',', '%') ");
			idsSql.append("OR (u.customer_level = 1 AND u.allParentAccount = " + user.getId() + ") OR u.id = "
					+ user.getId() + ")");
			Query query1 = getSession().createSQLQuery(idsSql.toString());
			String ids = "";
			for (BigInteger id : (List<BigInteger>) query1.list()) {
				if (id.intValue() == 0)
					continue;
				if (ids.equals("")) {
					ids += id;
				} else {
					ids += "," + id;
				}
			}
			if (ids.equals(""))
				ids = "0";
			String temp1 = temp.replaceAll("%ids", ids);
			temp1 = temp1.replaceAll("%id", Long.toString(user.getId()));
			temp1 = temp1.replaceAll("%username", "'" + user.getCustomerName() + "'");
			temp1=  temp1.replaceAll("%customerType", "'" + user.getCustomerType() + "'");
			if(StringUtils.isNotEmpty(vo.getSortType())){
				// 排序
				if(vo.getField().equals("total")){
					temp1="select * from (" +temp1+") tt order by tt.bet_money-(tt.re_monery+tt.lre_money) "+ vo.getSortType();
				}else{
					temp1="select * from (" +temp1+") tt order by tt.bet_money-(tt.re_monery+tt.lre_money+tt.win_money) "+ vo.getSortType();
				}
			}
			Query query = getSession().createSQLQuery(temp1.toString());
			query.setParameter("sdate", vo.getStartTime());
			query.setParameter("edate", vo.getEndTime());
			objs.addAll(query.list());
		}
		page.setPagelist(objs);
		page.setEntitylist(objs);
	}

	@Override
	public Page<Object, Object> queryYkReportAdmin(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		ReportVO vo = (ReportVO) param.get("reportVO");
		Page<Object, Object> page = new Page<Object, Object>();
		StringBuffer sql = new StringBuffer("from CustomerUser u where 1=1 and u.customerStatus = 10002 ");
		StringBuffer sqlcount = new StringBuffer(
				"SELECT COUNT(u.id) from CustomerUser u where 1=1 and u.customerStatus = 10002 ");
		if (vo.getUname() != null && !vo.getUname().equals("")) {
			sql.append(" and u.customerName = ? ");
			sqlcount.append(" and u.customerName = ? ");
		}
		if (vo.getUid() != 0) {
			sql.append(" and u.customerSuperior = ? ");
			sqlcount.append(" and u.customerSuperior = ? ");
		} else {
			sql.append(" and u.customerLevel = 0 ");
			sqlcount.append(" and u.customerLevel = 0 ");
		}
		sql.append(" order by customerLevel ");
		Query query = getSession().createQuery(sqlcount.toString());
		if (vo.getUname() != null && !vo.getUname().equals("")) {
			query.setParameter(0, vo.getUname());
		}
		if (vo.getUid() != 0) {
			query.setParameter(0, vo.getUid());
		}
		int totalCount = ((Long) query.list().get(0)).intValue();
		int pageNum = vo.getPageNum();
		int maxY = totalCount / vo.getMaxCount();
		if (totalCount % vo.getMaxCount() != 0) {
			maxY += 1;
		}
		pageNum = pageNum <= 0 ? 1 : pageNum;
		pageNum = pageNum >= maxY ? maxY : pageNum;
		page.setPageNum(pageNum);
		Query query1 = getSession().createQuery(sql.toString());
		if (vo.getUname() != null && !vo.getUname().equals("")) {
			query1.setParameter(0, vo.getUname());
		}
		if (vo.getUid() != 0) {
			query1.setParameter(0, vo.getUid());
		}
		query1.setMaxResults(vo.getMaxCount());
		query1.setFirstResult((pageNum - 1) * vo.getMaxCount());
		List<CustomerUser> userlis = query1.list();
		this.queryYkmx(userlis, page, vo);
		page.setPageCount(maxY);
		// 总记录数
		page.setTotalCount(totalCount);
		return page;
	}
	
	@Override
	public List<BetRecord> queryBetRecord(Map<String, Object> param) throws Exception {
		StringBuilder sql = new StringBuilder("from BetRecord t where 1=1 ");
		if (param.get("issueNo") != null) {
			sql.append(" and t.issueNo =").append(param.get("issueNo").toString());
		}
		if (param.get("orderNo") != null) {
			sql.append(" and t.orderNo ='").append(param.get("orderNo").toString()).append("'");
		}
		return getSession().createQuery(sql.toString()).list();
	}

	@Override
	public List<CustomerOrderStaVo> queryTeamYkReport(Map<String, Object> param) throws Exception {
		return null;
	}

	@Override
	public List<CustomerOrderStaVo> queryTeamYkRecords(Map<String, Object> param) throws Exception {
		List<CustomerOrderStaVo> result = new ArrayList<CustomerOrderStaVo>();
		CustomerUser customerUser = (CustomerUser)param.get(CommonUtil.CUSTOMERUSERKEY);
		ReportVO report =(ReportVO)param.get("reportVO");
		StringBuilder sqlstr = new StringBuilder();
		if(StringUtils.isNotEmpty(report.getUname())){
		    sqlstr.append(" select t.total_amount,t.rebate,t.win_amount,t.create_time,tt.customer_name,t.l_rebate from t_customer_order_sta t,t_customer_user tt where t.customer_id=tt.id");
		    sqlstr.append(" and tt.customer_superior=").append(report.getUid());
		    sqlstr.append(" and tt.customer_name = '").append(report.getUname()).append("'");
		}else{
			sqlstr.append("select t.total_amount,t.rebate,t.win_amount,t.create_time,t.l_rebate from t_customer_order_sta t where t.customer_id=").append(report.getUid());
		}
		
		if(StringUtils.isNotEmpty(report.getStartTime())){
			sqlstr.append(" and t.create_time >= '").append(report.getStartTime()).append("'");
		}
		if(StringUtils.isNotEmpty(report.getEndTime())){
			sqlstr.append(" and t.create_time <= '").append(report.getEndTime()).append("'");
		}
		SQLQuery query =getSession().createSQLQuery(sqlstr.toString());
		List<Object[]> results =(List<Object[]>)query.list();
		CustomerOrderStaVo vo = null;
		for(Object[] obj :results){
			vo = new CustomerOrderStaVo();
			vo.setTotalTetAmount(new BigDecimal(obj[0].toString()));
			vo.setRebateAmount(new BigDecimal(obj[1].toString()));
			vo.setWinAmount(new BigDecimal(obj[2].toString()));
			vo.setCreateTime(DateUtil.strToDate2(obj[3].toString()));
			if(StringUtils.isNotEmpty(report.getUname())){
				vo.setCreateUser(obj[4].toString());
			}else{
				vo.setCreateUser(customerUser.getCustomerName());
			}
			vo.setSaleAmount(vo.getTotalTetAmount().subtract(vo.getRebateAmount()).subtract(vo.getlRebateAmount()));
			vo.setYkAmount(vo.getWinAmount().subtract(vo.getSaleAmount()));
			vo.setlRebateAmount(vo.getlRebateAmount());
			
			result.add(vo);
		}
		return result;
	}

	@Override
	public Page<Object, Object> queryYkRecord(Map<String, Object> param) throws Exception {
		Page<Object, Object> page = new Page<Object, Object>();
		ReportVO vo = (ReportVO) param.get("reportVO");
		StringBuilder sql = new StringBuilder("select u.customer_name, u.customer_type,sum(t.total_amount),sum(t.rebate),sum(t.win_amount) from t_customer_order_sta t,t_customer_user u where t.customer_id = u.id");
		StringBuilder countSql = new StringBuilder("select count(1) from t_customer_order_sta t,t_customer_user u where t.customer_id = u.id ");
		StringBuilder conditionSql = new StringBuilder();
		if(StringUtils.isNotEmpty(vo.getStartTime())){
			conditionSql.append(" and t.create_time >= '").append(vo.getStartTime()).append("'");
		}
		if(StringUtils.isNotEmpty(vo.getEndTime())){
			conditionSql.append(" and t.create_time <= '").append(vo.getEndTime()).append("'");
		}
		if(StringUtils.isNotEmpty(vo.getUname())){
			// 针对直属代理
			conditionSql.append(" and u.customer_name = '").append(vo.getUname()).append("'");
			conditionSql.append(" and u.customer_superior =").append(vo.getUid());
		}else{
			StringBuilder sqlstr = new StringBuilder(" from CustomerUser u where u.customerSuperior = "+ vo.getUid()+" and u.customerStatus="+DataDictionaryUtil.STATUS_OPEN);
			
		}

		if(StringUtils.isNotEmpty(vo.getSortType())){
			if(vo.getField().equals("total")){
				conditionSql.append(" order by sum(t.total_amount)-sum(t.rebate) ").append(vo.getSortType());
			}else{
				conditionSql.append(" order by sum(t.total_amount)-sum(t.rebate)-sum(t.win_amount) ").append(vo.getSortType());
			}
		}
		sql.append(conditionSql);
		countSql.append(conditionSql);
		
		SQLQuery query = getSession().createSQLQuery(countSql.toString());
		
		int totalCount = Integer.valueOf(String.valueOf(query.list().get(0)));
		
	    query = getSession().createSQLQuery(sql.toString());
		int pageNum = vo.getPageNum();
		int maxY = totalCount / vo.getMaxCount();
		if (totalCount % vo.getMaxCount() != 0) {
			maxY += 1;
		}
		pageNum = pageNum <= 0 ? 1 : pageNum;
		pageNum = pageNum >= maxY ? maxY : pageNum;
		page.setPageNum(pageNum);
		query.setMaxResults(vo.getMaxCount());
		query.setFirstResult((pageNum - 1) * vo.getMaxCount());
		page.setPageCount(maxY);
		page.setPagelist(query.list());
		// 总记录数
		page.setTotalCount(totalCount);
		return page;
	}	
}
