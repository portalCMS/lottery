package com.lottery.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.CustomerOrder;
import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.CustomerOrderStaVo;
import com.lottery.bean.entity.vo.CustomerOrderVO;
import com.lottery.dao.IBetRecordDao;
import com.lottery.dao.ICustomerOrderDao;
import com.lottery.dao.ICustomerUserDao;
import com.lottery.persist.generice.GenericDAO;
import com.lottery.staticvalue.CommonStatic;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;
import com.xl.lottery.util.DateUtil;

@SuppressWarnings("unchecked")
@Repository
public class CustomerOrderDaoImpl extends GenericDAO<CustomerOrder> implements ICustomerOrderDao {

	@Autowired
	private ICustomerUserDao customerUserDao;
	@Autowired
	private IBetRecordDao betRecordDao;

	public CustomerOrderDaoImpl() {
		super(CustomerOrder.class);
	}

	@Override
	public void saveSignleRecharge(CustomerOrder order) throws Exception {
		insert(order);
	}

	@Override
	public int countDrawingTimesToday(CustomerOrderVO orderVo) throws Exception {
		String queryString = "select  count(t.id) as dt from t_customer_order t where  t.order_Type =? "
				+ " and t.order_Detail_Type =? ";
		if (orderVo.getOrderStatus() != null && orderVo.getOrderStatus() != 0) {
			queryString += " and t.order_status = ?";
		}
		if (orderVo.getCustomerId() != null && orderVo.getCustomerId() != 0) {
			queryString += " and t.customer_Id = ?";
		}
		if (null != orderVo.getOrderTime()) {
			queryString += " and t.order_Time >? and t.order_Time<? ";
		}
		Query query = getSession().createSQLQuery(queryString);
		int i = 0;
		query.setParameter(i++, orderVo.getOrderType());
		query.setParameter(i++, orderVo.getOrderDetailType());
		if (orderVo.getOrderStatus() != null && orderVo.getOrderStatus() != 0) {
			query.setParameter(i++, orderVo.getOrderStatus());
		}
		if (orderVo.getCustomerId() != null && orderVo.getCustomerId() != 0) {
			query.setParameter(i++, orderVo.getCustomerId());
		}
		if (null != orderVo.getOrderTime()) {
			query.setParameter(i++, DateUtil.getDayStartTime(orderVo.getOrderTime()));
			query.setParameter(i++, DateUtil.getDayEndTime(orderVo.getOrderTime()));
		}

		BigInteger returnInt = (BigInteger) query.list().get(0);
		return returnInt.intValue();

	}

	@Override
	public int countRechargeTimesToday(CustomerOrderVO orderVo) throws Exception {

		return countDrawingTimesToday(orderVo);
	}

	@Override
	public Page<CustomerOrderVO, CustomerOrder> queryUserOrderByPage(CustomerOrderVO orderVo) throws Exception {

		CustomerOrder entity = new CustomerOrder();
		List<String> formula = new ArrayList<String>();
		formula.add("=");
		formula.add("=");
		List<String> limitKeys = new ArrayList<String>();
		limitKeys.add("orderType");
		limitKeys.add("orderDetailType");
		List<Object> limitVals = new ArrayList<Object>();
		limitVals.add(orderVo.getOrderType());
		limitVals.add(orderVo.getOrderDetailType());
		if (0 != orderVo.getCustomerId()) {
			formula.add("=");
			limitKeys.add("customerId");
			limitVals.add(orderVo.getCustomerId());
		}
		if (null != orderVo.getOrderTimeBegin() && !"".equals(orderVo.getOrderTimeBegin())) {
			formula.add(">");
			limitKeys.add("orderTime");
			limitVals.add(DateUtil.strToDateLong(orderVo.getOrderTimeBegin().concat(":00")));
		}
		if (null != orderVo.getOrderTimeEnd() && !"".equals(orderVo.getOrderTimeEnd())) {
			formula.add("<");
			limitKeys.add("orderTime");
			limitVals.add(DateUtil.strToDateLong(orderVo.getOrderTimeEnd().concat(":00")));
		}
		if (null != orderVo.getOrderStatus() && 0 != orderVo.getOrderStatus()) {
			formula.add("=");
			limitKeys.add("orderStatus");
			limitVals.add(orderVo.getOrderStatus());
		}

		Page<CustomerOrderVO, CustomerOrder> page = (Page<CustomerOrderVO, CustomerOrder>) doPageQuery(orderVo, entity,
				formula, limitKeys, limitVals);
		return page;
	}

	/**
	 * 统计今天提款总金额
	 */
	@Override
	public BigDecimal sumTotalDrawingAmount(CustomerOrderVO orderVo) throws Exception {
		String queryString = "select sum(t.receive_Amount) as dt from t_customer_order t where  t.customer_Id=? and t.order_Type =? "
				+ " and t.order_Detail_Type =? and t.order_Time >? and t.order_Time<?";
		if (orderVo.getOrderStatus() != null && orderVo.getOrderStatus() != 0) {
			queryString += " and t.order_status = ?";
		}
		Query query = getSession().createSQLQuery(queryString);
		query.setParameter(0, new BigDecimal(orderVo.getCustomerId()));
		query.setParameter(1, orderVo.getOrderType());
		query.setParameter(2, orderVo.getOrderDetailType());
		query.setParameter(3, DateUtil.getDayStartTime(orderVo.getOrderTime()));
		query.setParameter(4, DateUtil.getDayEndTime(orderVo.getOrderTime()));
		if (orderVo.getOrderStatus() != null && orderVo.getOrderStatus() != 0) {
			query.setParameter(5, orderVo.getOrderStatus());
		}
		BigDecimal totalAmount = (BigDecimal) query.list().get(0);
		return totalAmount;
	}

	@Override
	public Page<CustomerOrderVO, CustomerOrder> queryAllOrderByPage(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		CustomerOrderVO orderVo = (CustomerOrderVO) param.get("customerOrderKey");
		String countHql = "select orderType,sum(orderAmount)  ";
		String countHqlTerm = " group by orderType ";
		List<String> limitKeys = new ArrayList<String>();
		List<String> formula = new ArrayList<String>();
		List<Object> limitVals = new ArrayList<Object>();
		String pj = "";
		if (null != orderVo.getOrderTimeBegin() && !"".equals(orderVo.getOrderTimeBegin())) {
			formula.add(">=");
			limitKeys.add("orderTime");
			limitVals.add(DateUtil.strToDateLong(orderVo.getOrderTimeBegin()));
		}
		if (null != orderVo.getOrderTimeEnd() && !"".equals(orderVo.getOrderTimeEnd())) {
			formula.add("<=");
			limitKeys.add("orderTime");
			limitVals.add(DateUtil.strToDateLong(orderVo.getOrderTimeEnd()));
		}
		if (0 != orderVo.getOrderStatus()) {
			formula.add("=");
			limitKeys.add("orderStatus");
			limitVals.add(orderVo.getOrderStatus());
		}
		if (null != orderVo.getOrderNumber()) {
			formula.add("=");
			limitKeys.add("orderNumber");
			limitVals.add(orderVo.getOrderNumber());
		}
		if (!StringUtils.isEmpty(orderVo.getCustomerName())) {
			formula.add("=");
			limitKeys.add("customerId");
			List<CustomerUser> users = customerUserDao.queryUserByName(orderVo);
			if (users.size() == 0)
				throw new LotteryException("用户不存在");
			limitVals.add(users.get(0).getId());
		}
		if (!orderVo.getOrderDetailTypes().equals("null") && !orderVo.getOrderDetailTypes().equals("0")) {
			formula.add("in");
			limitKeys.add("orderDetailType");
			List<Integer> detailTypes = new ArrayList<Integer>();
			for (String str : orderVo.getOrderDetailTypes().split(",")) {
				detailTypes.add(Integer.parseInt(str));
			}
			limitVals.add(detailTypes);
		}
		if (orderVo.getCustomerType() != null && !orderVo.getCustomerType().equals("0")) {
			// List<Long> ids
			// =customerUserDao.queryUserIdByType(orderVo.getCustomerType());
			String ids = "";
			if (orderVo.getCustomerType().equals("1")) {
				ids = "select t1.id from CustomerUser t1 where 1=1  and t1.customerType != 12003";
			} else {
				ids = "select t1.id from CustomerUser t1 where 1=1  and t1.customerType = 12003";
			}
			pj += "and customerId in (" + ids + ")";
		}
		Page<CustomerOrderVO, CustomerOrder> page = (Page<CustomerOrderVO, CustomerOrder>) doPageQuery(orderVo,
				CustomerOrder.class, formula, limitKeys, limitVals, countHql, countHqlTerm, pj, true);
		return page;
	}

	@Override
	public CustomerOrder queryOrderInfoByType(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		CustomerOrderVO vo = (CustomerOrderVO) param.get("ordervokey");
		StringBuffer queryString = new StringBuffer(" from CustomerOrder t where t.orderNumber = ? ");
		List<CustomerOrder> entitys = queryForList(queryString.toString(), null, new Object[] { vo.getOrderNumber() });
		if (entitys.size() > 0)
			return entitys.get(0);
		return null;
	}

	@Override
	public Page<CustomerOrderVO, CustomerOrder> queryTeamOrdersByPage(Map<String, Object> param) throws Exception {
		CustomerOrderVO vo = (CustomerOrderVO) param.get("ordersKey");
		List<Long> ids = (List<Long>) param.get("idsKey");
		if (StringUtils.isNotEmpty(vo.getLotteryTypeName()) && vo.getRsvst1().equals("lotOdt")) {
			return queryTeamOrders(param);
		}
		StringBuffer sb = new StringBuffer();
		StringBuffer countsb = new StringBuffer();		
		// 收入支出金额统计
		StringBuffer inSb = new StringBuffer();
		StringBuffer outSb = new StringBuffer();

		inSb.append("select sum(o.receiveAmount) from CustomerOrder o where 1=1 ");
		outSb.append("select sum(o.receiveAmount) from CustomerOrder o where 1=1 ");
		
		StringBuilder conditionSql = new StringBuilder();
		sb.append("from CustomerOrder o where 1=1 ");
		countsb.append("select count(o.id) from CustomerOrder o where 1=1 ");
        if(vo.getRsvst1().equals("lotOdt")){
        	conditionSql.append(" and o.orderStatus =").append(DataDictionaryUtil.ORDER_STATUS_SUCCESS);
        }else if(vo.getRsvst1().equals("finOdt") && !vo.getOrderStatus().equals("0")){
        	conditionSql.append(" and o.orderStatus =").append(vo.getOrderStatus());
        }
		if (vo.getSetTime().equals("1") ||vo.getRsvst1().equals("finOdt") || vo.getRsvst1().equals("proOdt")|| vo.getRsvst1().equals("proOdt")) {
			conditionSql.append(" and o.orderTime >= '").append(vo.getSdate()).append("' and o.orderTime <= '")
					.append(vo.getEdate()).append("'");
		} else {
			// 今日记录（今日凌晨4点至明日凌晨4点数据）
			conditionSql.append(" and o.orderTime >= '").append(DateUtil.getHourOfDay(4, 0, 0, 0))
					.append("' and o.orderTime <= '").append(DateUtil.getHourOfDay(4, 0, 0, 1)).append("'");
		}
		
		if (vo.getOrderDetailType() != null && vo.getOrderDetailType() != 0) {
			conditionSql.append(" and o.orderDetailType = ").append(vo.getOrderDetailType());
		} else if (vo.getOrderDetailType() == 0) {
			conditionSql.append(" and o.orderDetailType in (:odts) ");
		}
		if (vo.getOrderType() != null && vo.getOrderType() != 0) {
			conditionSql.append(" and o.orderType = "+vo.getOrderType());
		} else {
			conditionSql.append(" and o.orderType in (:ots) ");
		}
		if (ids != null && ids.size() > 0) {
			conditionSql.append(" and o.customerId in (:ids) ");
		}
		sb.append(conditionSql);
		countsb.append(conditionSql);
		inSb.append(conditionSql).append(" and o.orderType = ").append(DataDictionaryUtil.ORDER_TYPE_INCOME);
		outSb.append(conditionSql).append(" and o.orderType = ").append(DataDictionaryUtil.ORDER_TYPE_OUT);

		sb.append(" order by o.orderTime desc ");

		List<String> limitKeys = new ArrayList<String>();
		List<Object> limitVals = new ArrayList<Object>();

		List<Integer> odts = null;
		if (vo.getOrderDetailType() != null && vo.getOrderDetailType() == 0) {
			// 根据当前选项卡id来查询对应的所有订单子类型
			limitKeys.add("odts");
			String curId = vo.getRsvst1();
			if (curId.equals("lotOdt")) {
				odts = new ArrayList<Integer>();
				odts.add(DataDictionaryUtil.ORDER_DETAIL_WINNING_REBATES);
				odts.add(DataDictionaryUtil.ORDER_DETAIL_BETTING_REBATES);
				odts.add(DataDictionaryUtil.ORDER_DETAIL_SYSTEM_REBATES);
				odts.add(DataDictionaryUtil.ORDER_DETAIL_CHASE_AFTER_REBATES);
				odts.add(DataDictionaryUtil.ORDER_DETAIL_ORDINARY_BETTING);
				odts.add(DataDictionaryUtil.ORDER_DETAIL_CHASE_FREEZE);
				odts.add(DataDictionaryUtil.ORDER_DETAIL_TRACK);
				odts.add(DataDictionaryUtil.ORDER_DETAIL_CANCEL_STATUS);
				limitVals.add(odts);
			} else if (curId.equals("finOdt")) {
				odts = new ArrayList<Integer>();
				odts.add(DataDictionaryUtil.ORDER_DETAIL_CASH_ADD);
				odts.add(DataDictionaryUtil.ORDER_DETAIL_FEE_REFUND);
				odts.add(DataDictionaryUtil.ORDER_DETAIL_BACKGROUND_ADD);
				odts.add(DataDictionaryUtil.ORDER_DETAIL_WITHDRAWALS);
				odts.add(DataDictionaryUtil.ORDER_DETAIL_BACKGROUND_DEDUCTIONS);
				odts.add(DataDictionaryUtil.ORDER_DETAIL_OTHER_PAY);
				limitVals.add(odts);
			}else if (curId.equals("proOdt")) {
				odts = new ArrayList<Integer>();
				odts.add(DataDictionaryUtil.ORDER_DETAIL_DISTRIBUTED_ACTIVITIES);
				limitVals.add(odts);
			}else{
				odts = new ArrayList<Integer>();
				odts.add(DataDictionaryUtil.ORDER_DETAIL_MONEY_TRANSFER);
				odts.add(DataDictionaryUtil.ORDER_DETAIL_MONEY_INTO);
				odts.add(DataDictionaryUtil.ORDER_DETAIL_SYSTEM_BONUS);
				limitVals.add(odts);
			}
		}
		if (vo.getOrderType() == 0) {
			List<Integer> ots = new ArrayList<Integer>();
			ots.add(DataDictionaryUtil.ORDER_TYPE_INCOME);
			ots.add(DataDictionaryUtil.ORDER_TYPE_OUT);

			limitKeys.add("ots");
			limitVals.add(ots);
		}
		if (ids != null && ids.size() > 0) {
			limitKeys.add("ids");
			limitVals.add(ids);
		}

		Page<CustomerOrderVO, CustomerOrder> page = (Page<CustomerOrderVO, CustomerOrder>) pageQuery(vo, sb, countsb,
				limitKeys, limitVals);
		for(CustomerOrder co :page.getEntitylist()){
			List<Object[]> bets = null;
			if(StringUtils.isNotEmpty(co.getRsvst1())){
				bets= getBetRecord(co.getRsvst1());
			}else{
				bets= getBetRecord(co.getOrderNumber());
			}
			co.setUser(customerUserDao.queryById(co.getCustomerId()));
			//彩种
			co.setRsvst2(CommonStatic.getCodeMap().get(CommonStatic.LOTTERYTYPE_HEAD + bets.get(0)[2].toString())
					.toString());
			// 开始期号
			if(co.getOrderDetailType().equals(DataDictionaryUtil.ORDER_DETAIL_TRACK)){//追号订单
				co.setRsvst3(bets.get(0)[1].toString()+"<span class='color_red'>（起始期）</span>");
			}else{
				co.setRsvst3(bets.get(0)[1].toString());
			}
		}

		if (vo.getOrderType() == DataDictionaryUtil.ORDER_TYPE_INCOME) {
			// 统计收入金额
			BigDecimal inAmount = (BigDecimal)this.sumMyChargeOrderAmount(inSb.toString(), limitKeys, limitVals).get(0);
			page.setRsvdc1(inAmount == null ? BigDecimal.ZERO : inAmount);
			page.setRsvsdc2(BigDecimal.ZERO);
		} else if (vo.getOrderType() == DataDictionaryUtil.ORDER_TYPE_OUT) {
			// 统计收入金额
			BigDecimal outAmount = (BigDecimal)this.sumMyChargeOrderAmount(outSb.toString(), limitKeys, limitVals).get(0);
			page.setRsvdc1(BigDecimal.ZERO);
			page.setRsvsdc2(outAmount == null ? BigDecimal.ZERO : outAmount);
		} else {
			// 统计收入金额
			BigDecimal inAmount = (BigDecimal)this.sumMyChargeOrderAmount(inSb.toString(), limitKeys, limitVals).get(0);
			// 统计支出金额
			BigDecimal outAmount = (BigDecimal)this.sumMyChargeOrderAmount(outSb.toString(), limitKeys, limitVals).get(0);
			page.setRsvdc1(inAmount == null ? BigDecimal.ZERO : inAmount);
			page.setRsvsdc2(outAmount == null ? BigDecimal.ZERO : outAmount);
		}
		return page;
	}
	
	private Page<CustomerOrderVO, CustomerOrder> queryTeamOrders(Map<String, Object> param){
		CustomerOrderVO vo = (CustomerOrderVO) param.get("ordersKey");
		List<Long> ids = (List<Long>) param.get("idsKey");
		StringBuffer sb = new StringBuffer();
		StringBuffer countsb = new StringBuffer();
		// 收入支出金额统计
		StringBuffer inSb = new StringBuffer();
		StringBuffer outSb = new StringBuffer();
		String field = " o.id,o.order_time,o.order_detail_type,ifnull(o.rsvst2,''),ifnull(o.rsvst3,''),o.receive_amount,o.account_balance,o.order_status,o.order_type,o.order_number,ifnull(o.rsvst1,''),o.customer_id";
		String sql1 =" AND o.`rsvst1` IS NOT NULL AND EXISTS (SELECT 1 FROM t_customer_order o2,t_bet_record b WHERE o2.order_number = o.`rsvst1` AND o2.`order_number` = b.`order_no` AND b.`lottery_code` = '"+vo.getLotteryTypeName()+"' AND b.`customer_id`=o.`customer_id`)";
		String sql2 =" AND o.`rsvst1` IS NULL AND o.`order_detail_type` IN (18001, 18017)  AND EXISTS (SELECT 1 FROM t_bet_record b WHERE o.`order_number` = b.`order_no` AND b.`customer_id`=o.`customer_id`AND b.`lottery_code` = '"+vo.getLotteryTypeName()+"')";

		sb.append("select * from (select %1 from t_customer_order o where 1 = 1 ");
		countsb.append(" select ifnull(count(o.id),0) from t_customer_order o where 1=1 ");
		inSb.append("select ifnull(sum(o.receive_amount),0) from t_customer_order o where 1=1 ");
		outSb.append("select ifnull(sum(o.receive_amount),0) from t_customer_order o where 1=1 ");
		
		StringBuilder conditionSql  = new StringBuilder();
		if (vo.getSetTime().equals("1")) {
			conditionSql.append(" and o.order_time >= '").append(vo.getSdate()).append("' and o.order_time <= '")
					.append(vo.getEdate()).append("'");
		} else {
			// 今日记录（今日凌晨4点至明日凌晨4点数据）
			conditionSql.append(" and o.order_time >= '").append(DateUtil.getHourOfDay(4, 0, 0, 0))
					.append("' and o.order_time <= '").append(DateUtil.getHourOfDay(4, 0, 0, 1)).append("'");
		}
		if (vo.getOrderStatus() != null && vo.getOrderStatus() != 0) {
			conditionSql.append(" and o.order_status = " + vo.getOrderStatus().intValue());
		}

		if (vo.getOrderType() != null && vo.getOrderType() != 0) {
			conditionSql.append(" and o.order_type = ").append(vo.getOrderType());
		}
		if (vo.getOrderDetailType() != null && vo.getOrderDetailType() != 0) {
			conditionSql.append(" and o.order_detail_type = ").append(vo.getOrderDetailType());
		} else if (vo.getOrderDetailType() == 0) {
			conditionSql.append(" and o.order_detail_type in (:odts) ");
		}
		conditionSql.append(" and o.customer_id in (:ids)");
		
		sb.append(conditionSql).append(sql1).append(" union all select %1 from t_customer_order o where 1 = 1 ").append(conditionSql).append(sql2);
		countsb.append(conditionSql).append(sql1).append(" union all select ifnull(count(o.id),0) from t_customer_order o where 1=1 ").append(conditionSql).append(sql2);
		
		inSb.append(conditionSql).append(sql1).append(" and o.order_type = " + DataDictionaryUtil.ORDER_TYPE_INCOME).append(" union all select ifnull(sum(o.receive_amount),0) from t_customer_order o where 1=1 ").append(conditionSql).append(sql2).append(" and o.order_type = " + DataDictionaryUtil.ORDER_TYPE_INCOME);
		outSb.append(conditionSql).append(sql1).append(" and o.order_type = " + DataDictionaryUtil.ORDER_TYPE_OUT).append(" union all select ifnull(sum(o.receive_amount),0) from t_customer_order o where 1=1 ").append(conditionSql).append(sql2).append(" and o.order_type = " + DataDictionaryUtil.ORDER_TYPE_OUT);
		sb.append(") tt order by tt.order_time desc ");

		List<String> limitKeys = new ArrayList<String>();
		List<Object> limitVals = new ArrayList<Object>();
		List<Integer> odts = new ArrayList<Integer>();
		if (vo.getOrderDetailType() != null && vo.getOrderDetailType() == 0) {
			// 根据当前选项卡id来查询对应的所有订单子类型
			limitKeys.add("odts");
			odts.add(DataDictionaryUtil.ORDER_DETAIL_WINNING_REBATES);
			odts.add(DataDictionaryUtil.ORDER_DETAIL_BETTING_REBATES);
			odts.add(DataDictionaryUtil.ORDER_DETAIL_SYSTEM_REBATES);
			odts.add(DataDictionaryUtil.ORDER_DETAIL_CHASE_AFTER_REBATES);
			odts.add(DataDictionaryUtil.ORDER_DETAIL_ORDINARY_BETTING);
			odts.add(DataDictionaryUtil.ORDER_DETAIL_CHASE_FREEZE);
			odts.add(DataDictionaryUtil.ORDER_DETAIL_TRACK);
			odts.add(DataDictionaryUtil.ORDER_DETAIL_CANCEL_STATUS);
			limitVals.add(odts);
		}

		SQLQuery orderQuery = getSession().createSQLQuery(sb.toString().replaceAll("%1", field));
		SQLQuery countQuery = getSession().createSQLQuery(countsb.toString());
		if (vo.getOrderDetailType() != null && vo.getOrderDetailType() == 0) {
			orderQuery.setParameterList("odts", odts);
			countQuery.setParameterList("odts", odts);
		}
		if(ids!=null &&ids.size()>0){
			orderQuery.setParameterList("ids", ids);
			countQuery.setParameterList("ids", ids);
			limitKeys.add("ids");
			limitVals.add(ids);
		}
		List<Object>  countResult = countQuery.list();
		Integer totalCount = ((BigInteger)countResult.get(0)).intValue()+((BigInteger)countResult.get(1)).intValue();
		Page<CustomerOrderVO, CustomerOrder> page = new Page<CustomerOrderVO, CustomerOrder>();
		page.setPageNum(vo.getPageNum());
		int pageNum = vo.getPageNum();
		int maxY = totalCount / vo.getMaxCount();
		if (totalCount % vo.getMaxCount() != 0) {
			maxY += 1;
		}
		pageNum = pageNum <= 0 ? 1 : pageNum;
		pageNum = pageNum >= maxY ? maxY : pageNum;

		orderQuery.setFirstResult((pageNum - 1) * vo.getMaxCount());
		orderQuery.setMaxResults(vo.getMaxCount());
		List<Object[]> objs = orderQuery.list();

		List<CustomerOrder> orderList = new ArrayList<CustomerOrder>();
		Map<String, Object> codeMap = CommonStatic.getCodeMap();
		for (int i = 0; i < objs.size(); i++) {
			Object[] objAttrs = objs.get(i);
			CustomerOrder order = new CustomerOrder();
			order.setId(Long.parseLong(objAttrs[0].toString()));
			order.setOrderTime((Date) objAttrs[1]);
			order.setOrderDetailType(Integer.parseInt(objAttrs[2].toString()));
			order.setOrderNumber(objAttrs[9].toString());
			List<Object[]> bets = null;
			if(StringUtils.isNotEmpty(objAttrs[10].toString())){
				bets= getBetRecord(objAttrs[10].toString());
			}else{
				bets= getBetRecord(order.getOrderNumber());
			}
			//彩种
			order.setRsvst2(codeMap.get(CommonStatic.LOTTERYTYPE_HEAD + bets.get(0)[2].toString())
					.toString());
			// 开始期号
			if(order.getOrderDetailType().equals(DataDictionaryUtil.ORDER_DETAIL_TRACK)){//追号订单
				order.setRsvst3(bets.get(0)[1].toString()+"<span class='color_red'>（起始期）</span>");
			}else{
				order.setRsvst3(bets.get(0)[1].toString());
			}
			order.setReceiveAmount((BigDecimal) objAttrs[5]);
			order.setAccountBalance((BigDecimal) objAttrs[6]);
			order.setOrderStatus((Integer) objAttrs[7]);
			order.setOrderType((Integer) objAttrs[8]);
			Query query = getSession().createQuery(" from CustomerUser where id="+objAttrs[11]);
			order.setUser((CustomerUser)query.list().get(0));
			orderList.add(order);
		}
		page.setEntitylist(orderList);
		page.setPageCount(maxY);
		// 总记录数
		page.setTotalCount(totalCount);
		List<Object>  result =null;
		if (vo.getOrderType() == DataDictionaryUtil.ORDER_TYPE_INCOME) {
			// 统计收入金额
			result =this.sumMyOrderAmount(inSb.toString(), limitKeys, limitVals);
			BigDecimal inAmount = ((BigDecimal)result.get(0)).add((BigDecimal)result.get(1));
			page.setRsvdc1(inAmount == null ? BigDecimal.ZERO : inAmount);
			page.setRsvsdc2(BigDecimal.ZERO);
		} else if (vo.getOrderType() == DataDictionaryUtil.ORDER_TYPE_OUT) {
			// 统计收入金额
			result =this.sumMyOrderAmount(outSb.toString(), limitKeys, limitVals);
			BigDecimal outAmount = ((BigDecimal)result.get(0)).add((BigDecimal)result.get(1));
			page.setRsvdc1(BigDecimal.ZERO);
			page.setRsvsdc2(outAmount == null ? BigDecimal.ZERO : outAmount);
		} else {
			// 统计收入金额
			result =this.sumMyOrderAmount(inSb.toString(), limitKeys, limitVals);
			// 统计收入金额
			BigDecimal inAmount =  ((BigDecimal)result.get(0)).add((BigDecimal)result.get(1));
			// 统计支出金额
			result =this.sumMyOrderAmount(outSb.toString(), limitKeys, limitVals);
			BigDecimal outAmount =  ((BigDecimal)result.get(0)).add((BigDecimal)result.get(1));
			page.setRsvdc1(inAmount == null ? BigDecimal.ZERO : inAmount);
			page.setRsvsdc2(outAmount == null ? BigDecimal.ZERO : outAmount);
		}
		return page;
	}

	/**
	 * 统计所有订单的总金额
	 * 
	 * @param hql
	 * @param limitKeys
	 * @param limitVals
	 * @return
	 */
	private List<Object> sumMyOrderAmount(String hql, List<String> limitKeys, List<Object> limitVals) {
		Object[] params = null;
		Object[] keys = null;
		if (limitVals != null) {
			params = limitVals.toArray();
			keys = limitKeys.toArray();
		}
		SQLQuery query = getSession().createSQLQuery(hql);
		if (null != params) {
			for (int i = 0; i < params.length; i++) {
				if (params[i] instanceof Date) {
					query.setTimestamp(i, (Date) params[i]);
				} else if (params[i] instanceof List) {
					query.setParameterList((String) keys[i], (Collection) params[i]);
				} else {
					query.setParameter(i, params[i]);
				}

			}
		}
		return query.list();
	}

	/**
	 * 统计充提订单金额。
	 * 
	 * @param hql
	 *            查询语句
	 * @param limitKeys
	 *            分页参数 limitKeys
	 * @param limitVals
	 *            分页参数值 limitVals
	 * @return 返回符合条件的订单金额。
	 */
	private List<Object> sumMyChargeOrderAmount(String hql, List<String> limitKeys, List<Object> limitVals) {
		Object[] params = null;
		Object[] keys = null;
		if (limitVals != null) {
			params = limitVals.toArray();
			keys = limitKeys.toArray();
		}
		Query query = getSession().createQuery(hql);
		if (null != params) {
			for (int i = 0; i < params.length; i++) {
				if (params[i] instanceof Date) {
					query.setTimestamp(i, (Date) params[i]);
				} else if (params[i] instanceof List) {
					query.setParameterList((String) keys[i], (Collection) params[i]);
				} else {
					query.setParameter(i, params[i]);
				}

			}
		}
		return query.list();
	}

	@Override
	public CustomerOrder queryOrderByNum(String sourceOrderNumber) {
		String hql = "from CustomerOrder where orderNumber = ? ";
		CustomerOrder order = (CustomerOrder) getSession().createQuery(hql).setParameter(0, sourceOrderNumber).list()
				.get(0);
		return order;
	}

	@Override
	public CustomerOrder queryDrawingOrderById(Long orderId) throws Exception {
		CustomerOrder order = queryById(orderId);
		String sql = "SELECT c.`card_no`,b.`bank_name` FROM t_user_card c ,t_bank_manage b  "
				+ " WHERE c.`id`=? AND c.`bank_id` = b.`id` " + " AND c.`status`=? AND b.`bank_status`=? ";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.setParameter(0, order.getReferenceId());
		sqlQuery.setParameter(1, DataDictionaryUtil.STATUS_OPEN);
		sqlQuery.setParameter(2, DataDictionaryUtil.STATUS_OPEN);
		Object[] objs = (Object[]) sqlQuery.list().get(0);
		order.setRsvst4(objs[0].toString());
		order.setRsvst5(objs[1].toString());

		return order;
	}

	@Override
	public Page<CustomerOrderVO, CustomerOrder> queryMyLotteryOrdersByPage(Map<String, Object> param) throws Exception {
		CustomerOrderVO vo = (CustomerOrderVO) param.get("ordersKey");
		if(vo.getRsvst1().equals("lotOdt") && StringUtils.isNotEmpty(vo.getLotteryTypeName())){
			return queryMyOrders(param);
		}
		CustomerUser curUser = (CustomerUser)param.get(CommonUtil.CUSTOMERUSERKEY);
		StringBuffer sb = new StringBuffer();
		StringBuffer countsb = new StringBuffer();
		// 收入支出金额统计
		StringBuffer inSb = new StringBuffer();
		StringBuffer outSb = new StringBuffer();

		sb.append("select o.id,o.order_time,o.order_detail_type,ifnull(o.rsvst2,''),ifnull(o.rsvst3,''),o.receive_amount,o.account_balance,o.order_status,o.order_type,o.order_number,ifnull(o.rsvst1,'') ");
		sb.append("  from t_customer_order o where 1 = 1 ");
		countsb.append("select count(o.id) from t_customer_order o where 1=1 ");
		inSb.append("select sum(o.receive_amount) from t_customer_order o where 1=1 ");
		outSb.append("select sum(o.receive_amount) from t_customer_order o where 1=1 ");
		
		StringBuilder conditionSql  = new StringBuilder();
		if (vo.getSetTime().equals("1") ||vo.getRsvst1().equals("finOdt") || vo.getRsvst1().equals("proOdt")) {
			conditionSql.append(" and o.order_time >= '").append(vo.getSdate()).append("' and o.order_time <= '")
					.append(vo.getEdate()).append("'");
		} else {
			// 今日记录（今日凌晨4点至明日凌晨4点数据）
			conditionSql.append(" and o.order_time >= '").append(DateUtil.getHourOfDay(4, 0, 0, 0))
					.append("' and o.order_time <= '").append(DateUtil.getHourOfDay(4, 0, 0, 1)).append("'");
		}
		if (vo.getOrderStatus() != null && vo.getOrderStatus() != 0) {
			conditionSql.append(" and o.order_status = " + vo.getOrderStatus().intValue());
		}

		if (vo.getOrderType() != null && vo.getOrderType() != 0) {
			conditionSql.append(" and o.order_type = ").append(vo.getOrderType());
		}
		conditionSql.append(" and o.customer_id = ").append(curUser.getId());

		if (vo.getOrderDetailType() != null && vo.getOrderDetailType() != 0) {
			conditionSql.append(" and o.order_detail_type = ").append(vo.getOrderDetailType());
		} else if (vo.getOrderDetailType() == 0) {
			conditionSql.append(" and o.order_detail_type in (:odts) ");
		}
		sb.append(conditionSql);
		countsb.append(conditionSql);
		inSb.append(conditionSql).append(" and o.order_type = " + DataDictionaryUtil.ORDER_TYPE_INCOME);
		outSb.append(conditionSql).append(" and o.order_type = " + DataDictionaryUtil.ORDER_TYPE_OUT);;
		sb.append(" order by o.create_time desc ");

		List<String> limitKeys = new ArrayList<String>();
		List<Object> limitVals = new ArrayList<Object>();
		List<Integer> odts = null;
		if (vo.getOrderDetailType() != null && vo.getOrderDetailType() == 0) {
			// 根据当前选项卡id来查询对应的所有订单子类型
			limitKeys.add("odts");
			String curId = vo.getRsvst1();
			if (curId.equals("lotOdt")) {
				odts = new ArrayList<Integer>();
				odts.add(DataDictionaryUtil.ORDER_DETAIL_WINNING_REBATES);
				odts.add(DataDictionaryUtil.ORDER_DETAIL_BETTING_REBATES);
				odts.add(DataDictionaryUtil.ORDER_DETAIL_SYSTEM_REBATES);
				odts.add(DataDictionaryUtil.ORDER_DETAIL_CHASE_AFTER_REBATES);
				odts.add(DataDictionaryUtil.ORDER_DETAIL_ORDINARY_BETTING);
				odts.add(DataDictionaryUtil.ORDER_DETAIL_CHASE_FREEZE);
				odts.add(DataDictionaryUtil.ORDER_DETAIL_TRACK);
				odts.add(DataDictionaryUtil.ORDER_DETAIL_CANCEL_STATUS);
				limitVals.add(odts);
			} else if (curId.equals("finOdt")) {
				odts = new ArrayList<Integer>();
				odts.add(DataDictionaryUtil.ORDER_DETAIL_CASH_ADD);
				odts.add(DataDictionaryUtil.ORDER_DETAIL_FEE_REFUND);
				odts.add(DataDictionaryUtil.ORDER_DETAIL_BACKGROUND_ADD);
				odts.add(DataDictionaryUtil.ORDER_DETAIL_WITHDRAWALS);
				odts.add(DataDictionaryUtil.ORDER_DETAIL_BACKGROUND_DEDUCTIONS);
				odts.add(DataDictionaryUtil.ORDER_DETAIL_OTHER_PAY);
				limitVals.add(odts);
			}else if (curId.equals("proOdt")) {
				odts = new ArrayList<Integer>();
				odts.add(DataDictionaryUtil.ORDER_DETAIL_DISTRIBUTED_ACTIVITIES);
				limitVals.add(odts);
			}
		}

		SQLQuery orderQuery = getSession().createSQLQuery(sb.toString());
		SQLQuery countQuery = getSession().createSQLQuery(countsb.toString());
		if (vo.getOrderDetailType() != null && vo.getOrderDetailType() == 0) {
			orderQuery.setParameterList("odts", odts);
			countQuery.setParameterList("odts", odts);
		}

		Integer totalCount = ((BigInteger) countQuery.list().get(0)).intValue();
		Page<CustomerOrderVO, CustomerOrder> page = new Page<CustomerOrderVO, CustomerOrder>();
		page.setPageNum(vo.getPageNum());
		int pageNum = vo.getPageNum();
		int maxY = totalCount / vo.getMaxCount();
		if (totalCount % vo.getMaxCount() != 0) {
			maxY += 1;
		}
		pageNum = pageNum <= 0 ? 1 : pageNum;
		pageNum = pageNum >= maxY ? maxY : pageNum;

		// 追号订单查询
		orderQuery.setFirstResult((pageNum - 1) * vo.getMaxCount());
		orderQuery.setMaxResults(vo.getMaxCount());
		List<Object[]> objs = orderQuery.list();

		List<CustomerOrderVO> orderList = new ArrayList<CustomerOrderVO>();
		Map<String, Object> codeMap = CommonStatic.getCodeMap();
		for (int i = 0; i < objs.size(); i++) {
			Object[] objAttrs = objs.get(i);
			CustomerOrderVO order = new CustomerOrderVO();
			order.setId(Long.parseLong(objAttrs[0].toString()));
			order.setOrderTime((Date) objAttrs[1]);
			order.setOrderDetailType(Integer.parseInt(objAttrs[2].toString()));
			order.setOrderNumber(objAttrs[9].toString());
			List<Object[]> bets = null;
			if(StringUtils.isNotEmpty(objAttrs[10].toString())){
				bets= getBetRecord(objAttrs[10].toString());
			}else{
				bets= getBetRecord(order.getOrderNumber());
			}
			//彩种
			order.setRsvst2(codeMap.get(CommonStatic.LOTTERYTYPE_HEAD + bets.get(0)[2].toString())
					.toString());
			// 开始期号
			if(order.getOrderDetailType().equals(DataDictionaryUtil.ORDER_DETAIL_TRACK)){//追号订单
				order.setRsvst3(bets.get(0)[1].toString()+"<span class='color_red'>（起始期）</span>");
			}else{
				order.setRsvst3(bets.get(0)[1].toString());
			}
			order.setReceiveAmount((BigDecimal) objAttrs[5]);
			order.setAccountBalance((BigDecimal) objAttrs[6]);
			order.setOrderStatus((Integer) objAttrs[7]);
			order.setOrderType((Integer) objAttrs[8]);
			orderList.add(order);
		}
		page.setPagelist(orderList);
		page.setPageCount(maxY);
		// 总记录数
		page.setTotalCount(totalCount);

		if (vo.getOrderType() == DataDictionaryUtil.ORDER_TYPE_INCOME) {
			// 统计收入金额
			BigDecimal inAmount = (BigDecimal)this.sumMyOrderAmount(inSb.toString(), limitKeys, limitVals).get(0);
			page.setRsvdc1(inAmount == null ? BigDecimal.ZERO : inAmount);
			page.setRsvsdc2(BigDecimal.ZERO);
		} else if (vo.getOrderType() == DataDictionaryUtil.ORDER_TYPE_OUT) {
			// 统计收入金额
			BigDecimal outAmount = (BigDecimal)this.sumMyOrderAmount(outSb.toString(), limitKeys, limitVals).get(0);
			page.setRsvdc1(BigDecimal.ZERO);
			page.setRsvsdc2(outAmount == null ? BigDecimal.ZERO : outAmount);
		} else {
			// 统计收入金额
			BigDecimal inAmount = (BigDecimal)this.sumMyOrderAmount(inSb.toString(), limitKeys, limitVals).get(0);
			// 统计支出金额
			BigDecimal outAmount = (BigDecimal)this.sumMyOrderAmount(outSb.toString(), limitKeys, limitVals).get(0);
			page.setRsvdc1(inAmount == null ? BigDecimal.ZERO : inAmount);
			page.setRsvsdc2(outAmount == null ? BigDecimal.ZERO : outAmount);
		}
		return page;
	}
	
	
	private Page<CustomerOrderVO, CustomerOrder> queryMyOrders(Map<String, Object> param){
		CustomerOrderVO vo = (CustomerOrderVO) param.get("ordersKey");
		CustomerUser curUser = (CustomerUser)param.get(CommonUtil.CUSTOMERUSERKEY);
		StringBuffer sb = new StringBuffer();
		StringBuffer countsb = new StringBuffer();
		// 收入支出金额统计
		StringBuffer inSb = new StringBuffer();
		StringBuffer outSb = new StringBuffer();
		String field = " o.id,o.order_time,o.order_detail_type,ifnull(o.rsvst2,''),ifnull(o.rsvst3,''),o.receive_amount,o.account_balance,o.order_status,o.order_type,o.order_number,ifnull(o.rsvst1,'')";
		String sql1 =" AND o.`rsvst1` IS NOT NULL AND EXISTS (SELECT 1 FROM t_customer_order o2,t_bet_record b WHERE o2.order_number = o.`rsvst1` AND o2.`order_number` = b.`order_no` AND b.`lottery_code` = '"+vo.getLotteryTypeName()+"' AND b.`customer_id`=o.`customer_id`)";
		String sql2 =" AND o.`rsvst1` IS NULL AND o.`order_detail_type` IN (18001, 18017)  AND EXISTS (SELECT 1 FROM t_bet_record b WHERE o.`order_number` = b.`order_no` AND b.`customer_id`=o.`customer_id`AND b.`lottery_code` = '"+vo.getLotteryTypeName()+"')";

		sb.append("select * from (select %1 from t_customer_order o where 1 = 1 ");
		countsb.append(" select ifnull(count(o.id),0) from t_customer_order o where 1=1 ");
		inSb.append("select ifnull(sum(o.receive_amount),0) from t_customer_order o where 1=1 ");
		outSb.append("select ifnull(sum(o.receive_amount),0) from t_customer_order o where 1=1 ");
		
		StringBuilder conditionSql  = new StringBuilder();
		if (vo.getSetTime().equals("1")) {
			conditionSql.append(" and o.order_time >= '").append(vo.getSdate()).append("' and o.order_time <= '")
					.append(vo.getEdate()).append("'");
		} else {
			// 今日记录（今日凌晨4点至明日凌晨4点数据）
			conditionSql.append(" and o.order_time >= '").append(DateUtil.getHourOfDay(4, 0, 0, 0))
					.append("' and o.order_time <= '").append(DateUtil.getHourOfDay(4, 0, 0, 1)).append("'");
		}
		if (vo.getOrderStatus() != null && vo.getOrderStatus() != 0) {
			conditionSql.append(" and o.order_status = " + vo.getOrderStatus().intValue());
		}

		if (vo.getOrderType() != null && vo.getOrderType() != 0) {
			conditionSql.append(" and o.order_type = ").append(vo.getOrderType());
		}
		if (vo.getOrderDetailType() != null && vo.getOrderDetailType() != 0) {
			conditionSql.append(" and o.order_detail_type = ").append(vo.getOrderDetailType());
		} else if (vo.getOrderDetailType() == 0) {
			conditionSql.append(" and o.order_detail_type in (:odts) ");
		}
		conditionSql.append(" and o.customer_id = ").append(curUser.getId());
		
		sb.append(conditionSql).append(sql1).append(" union all select %1 from t_customer_order o where 1 = 1 ").append(conditionSql).append(sql2);
		countsb.append(conditionSql).append(sql1).append(" union all select ifnull(count(o.id),0) from t_customer_order o where 1=1 ").append(conditionSql).append(sql2);
		
		inSb.append(conditionSql).append(sql1).append(" and o.order_type = " + DataDictionaryUtil.ORDER_TYPE_INCOME).append(" union all select ifnull(sum(o.receive_amount),0) from t_customer_order o where 1=1 ").append(conditionSql).append(sql2).append(" and o.order_type = " + DataDictionaryUtil.ORDER_TYPE_INCOME);
		outSb.append(conditionSql).append(sql1).append(" and o.order_type = " + DataDictionaryUtil.ORDER_TYPE_OUT).append(" union all select ifnull(sum(o.receive_amount),0) from t_customer_order o where 1=1 ").append(conditionSql).append(sql2).append(" and o.order_type = " + DataDictionaryUtil.ORDER_TYPE_OUT);
		sb.append(") tt order by tt.order_time desc ");

		List<String> limitKeys = new ArrayList<String>();
		List<Object> limitVals = new ArrayList<Object>();
		List<Integer> odts = new ArrayList<Integer>();
		if (vo.getOrderDetailType() != null && vo.getOrderDetailType() == 0) {
			// 根据当前选项卡id来查询对应的所有订单子类型
			limitKeys.add("odts");
			odts.add(DataDictionaryUtil.ORDER_DETAIL_WINNING_REBATES);
			odts.add(DataDictionaryUtil.ORDER_DETAIL_BETTING_REBATES);
			odts.add(DataDictionaryUtil.ORDER_DETAIL_SYSTEM_REBATES);
			odts.add(DataDictionaryUtil.ORDER_DETAIL_CHASE_AFTER_REBATES);
			odts.add(DataDictionaryUtil.ORDER_DETAIL_ORDINARY_BETTING);
			odts.add(DataDictionaryUtil.ORDER_DETAIL_CHASE_FREEZE);
			odts.add(DataDictionaryUtil.ORDER_DETAIL_TRACK);
			odts.add(DataDictionaryUtil.ORDER_DETAIL_CANCEL_STATUS);
			limitVals.add(odts);
		}

		SQLQuery orderQuery = getSession().createSQLQuery(sb.toString().replaceAll("%1", field));
		SQLQuery countQuery = getSession().createSQLQuery(countsb.toString());
		if (vo.getOrderDetailType() != null && vo.getOrderDetailType() == 0) {
			orderQuery.setParameterList("odts", odts);
			countQuery.setParameterList("odts", odts);
		}
		List<Object>  countResult = countQuery.list();
		Integer totalCount = ((BigInteger)countResult.get(0)).intValue()+((BigInteger)countResult.get(1)).intValue();
		Page<CustomerOrderVO, CustomerOrder> page = new Page<CustomerOrderVO, CustomerOrder>();
		page.setPageNum(vo.getPageNum());
		int pageNum = vo.getPageNum();
		int maxY = totalCount / vo.getMaxCount();
		if (totalCount % vo.getMaxCount() != 0) {
			maxY += 1;
		}
		pageNum = pageNum <= 0 ? 1 : pageNum;
		pageNum = pageNum >= maxY ? maxY : pageNum;

		// 追号订单查询
		orderQuery.setFirstResult((pageNum - 1) * vo.getMaxCount());
		orderQuery.setMaxResults(vo.getMaxCount());
		List<Object[]> objs = orderQuery.list();

		List<CustomerOrderVO> orderList = new ArrayList<CustomerOrderVO>();
		Map<String, Object> codeMap = CommonStatic.getCodeMap();
		for (int i = 0; i < objs.size(); i++) {
			Object[] objAttrs = objs.get(i);
			CustomerOrderVO order = new CustomerOrderVO();
			order.setId(Long.parseLong(objAttrs[0].toString()));
			order.setOrderTime((Date) objAttrs[1]);
			order.setOrderDetailType(Integer.parseInt(objAttrs[2].toString()));
			order.setOrderNumber(objAttrs[9].toString());
			List<Object[]> bets = null;
			if(StringUtils.isNotEmpty(objAttrs[10].toString())){
				bets= getBetRecord(objAttrs[10].toString());
			}else{
				bets= getBetRecord(order.getOrderNumber());
			}
			//彩种
			order.setRsvst2(codeMap.get(CommonStatic.LOTTERYTYPE_HEAD + bets.get(0)[2].toString())
					.toString());
			// 开始期号
			if(order.getOrderDetailType().equals(DataDictionaryUtil.ORDER_DETAIL_TRACK)){//追号订单
				order.setRsvst3(bets.get(0)[1].toString()+"<span class='color_red'>（起始期）</span>");
			}else{
				order.setRsvst3(bets.get(0)[1].toString());
			}
			order.setReceiveAmount((BigDecimal) objAttrs[5]);
			order.setAccountBalance((BigDecimal) objAttrs[6]);
			order.setOrderStatus((Integer) objAttrs[7]);
			order.setOrderType((Integer) objAttrs[8]);
			orderList.add(order);
		}
		page.setPagelist(orderList);
		page.setPageCount(maxY);
		// 总记录数
		page.setTotalCount(totalCount);
		List<Object>  result =null;
		if (vo.getOrderType() == DataDictionaryUtil.ORDER_TYPE_INCOME) {
			// 统计收入金额
			result =this.sumMyOrderAmount(inSb.toString(), limitKeys, limitVals);
			BigDecimal inAmount = ((BigDecimal)result.get(0)).add((BigDecimal)result.get(1));
			page.setRsvdc1(inAmount == null ? BigDecimal.ZERO : inAmount);
			page.setRsvsdc2(BigDecimal.ZERO);
		} else if (vo.getOrderType() == DataDictionaryUtil.ORDER_TYPE_OUT) {
			// 统计收入金额
						result =this.sumMyOrderAmount(outSb.toString(), limitKeys, limitVals);
			BigDecimal outAmount = ((BigDecimal)result.get(0)).add((BigDecimal)result.get(1));
			page.setRsvdc1(BigDecimal.ZERO);
			page.setRsvsdc2(outAmount == null ? BigDecimal.ZERO : outAmount);
		} else {
			// 统计收入金额
			result =this.sumMyOrderAmount(inSb.toString(), limitKeys, limitVals);
			// 统计收入金额
			BigDecimal inAmount =  ((BigDecimal)result.get(0)).add((BigDecimal)result.get(1));
			// 统计支出金额
			result =this.sumMyOrderAmount(outSb.toString(), limitKeys, limitVals);
			BigDecimal outAmount =  ((BigDecimal)result.get(0)).add((BigDecimal)result.get(1));
			page.setRsvdc1(inAmount == null ? BigDecimal.ZERO : inAmount);
			page.setRsvsdc2(outAmount == null ? BigDecimal.ZERO : outAmount);
		}
		return page;
	}

	/**
	 * 获取追号订单
	 */
	@Override
	public Page<CustomerOrderVO, CustomerOrder> queryTraceOrdersByPage(Map<String, Object> param) throws Exception {
		CustomerOrderVO vo = (CustomerOrderVO) param.get("ordersKey");
		if(StringUtils.isNotEmpty(vo.getRsvst2()) || StringUtils.isNotEmpty(vo.getRsvst3())){
			return queryTraceOrders(param);
		}
		CustomerUser user = (CustomerUser) param.get(CommonUtil.CUSTOMERUSERKEY);
		StringBuffer inSb = new StringBuffer("select sum(t.receive_amount) ");
		StringBuffer outSb = new StringBuffer("select sum(t.receive_amount) ");
		StringBuilder listSql = new StringBuilder("select t.id,t.customer_id,t.`order_number`,t.`create_time`, ");
		listSql.append(" t.`order_status`,t.`order_amount`,t.`order_detail_type`,t.`awardStop`,t.`rsvdc1`,ifnull(t.rsvdc2,0),t.return_amount ");
		StringBuilder countSql = new StringBuilder("select count(t.`order_number`) ");

		StringBuilder conditionSql = new StringBuilder(" from t_customer_order t where t.`order_detail_type`= ")
				.append(DataDictionaryUtil.ORDER_DETAIL_TRACK);
		// 追号记录应该只能查询到自己的追号记录
		conditionSql.append(" and t.`customer_id`= ").append(user.getId());

		conditionSql.append(" and t.create_time >= '").append(vo.getSdate()).append("'  and t.create_time <= '")
				.append(vo.getEdate()).append("'");

		// 完成期数不等于总期数，则表示该订单在追号中。
		if (vo.getOrderStatus() == DataDictionaryUtil.COMMON_FLAG_1) {
			conditionSql.append(" and t.rsvdc1 !=t.rsvdc2 ");
		} else if (vo.getOrderStatus() == DataDictionaryUtil.COMMON_FLAG_2) {
			conditionSql.append(" and t.rsvdc1 =t.rsvdc2 ");
		}

		listSql.append(conditionSql).append(" order by t.create_time desc");
		countSql.append(conditionSql);
		inSb.append(conditionSql).append(" and t.order_type = " + DataDictionaryUtil.ORDER_TYPE_INCOME);
		outSb.append(conditionSql).append(" and t.order_type = " + DataDictionaryUtil.ORDER_TYPE_OUT);
		SQLQuery countQuery = getSession().createSQLQuery(countSql.toString());
		SQLQuery orderQuery = getSession().createSQLQuery(listSql.toString());

		// 查询总记录数，算分页。
		BigInteger count = (BigInteger) (countQuery.list().size() == 0 ? 0 : countQuery.list().get(0));
		if (count.intValue() == 0) {
			return null;
		}
		int totalCount = count.intValue();
		Page<CustomerOrderVO, CustomerOrder> page = new Page<CustomerOrderVO, CustomerOrder>();
		page.setPageNum(vo.getPageNum());
		int pageNum = vo.getPageNum();
		int maxY = totalCount / vo.getMaxCount();
		if (totalCount % vo.getMaxCount() != 0) {
			maxY += 1;
		}
		pageNum = pageNum <= 0 ? 1 : pageNum;
		pageNum = pageNum >= maxY ? maxY : pageNum;

		// 追号订单查询
		orderQuery.setFirstResult((pageNum - 1) * vo.getMaxCount());
		orderQuery.setMaxResults(vo.getMaxCount());
		List<Object[]> objs = orderQuery.list();

		List<CustomerOrder> orderList = new ArrayList<CustomerOrder>();
		for (int i = 0; i < objs.size(); i++) {
			Object[] objAttrs = objs.get(i);
			CustomerOrder order = new CustomerOrder();
			order.setId(Long.parseLong(objAttrs[0].toString()));
			order.setCustomerId(Long.parseLong(objAttrs[1].toString()));
			order.setOrderNumber(objAttrs[2].toString());
			order.setCreateTime((Date) objAttrs[3]);
			order.setOrderStatus(Integer.parseInt(objAttrs[4].toString()));
			order.setOrderAmount(new BigDecimal(objAttrs[5].toString()));
			order.setOrderDetailType(Integer.parseInt(objAttrs[6].toString()));
			order.setAwardStop(objAttrs[7].toString());
			// 总追号期数
			order.setRsvdc1(Long.parseLong(objAttrs[8].toString()));
			// 已完成期数
			order.setRsvdc2(Long.parseLong(objAttrs[9].toString()));
			// 取消金额
			order.setReturnAmount(new BigDecimal(objAttrs[10].toString()));

			List<Object[]> bets = getTraceRecorderData(objAttrs[2].toString());
			// 开始期号
			order.setRsvst2(bets.get(0)[1].toString());
			// 彩种名称
			order.setRsvst1(CommonStatic.getCodeMap().get(CommonStatic.LOTTERYTYPE_HEAD + bets.get(0)[2].toString())
					.toString());
			// 中奖金额，有中奖则大于0。
			order.setRsvdc4(new BigDecimal(bets.get(0)[0].toString()));
			// 已完成金额
			order.setFinishAmount(new BigDecimal(bets.get(1)[0].toString()));

			orderList.add(order);
		}
		page.setEntitylist(orderList);
		page.setPageCount(maxY);
		// 总记录数
		page.setTotalCount(totalCount);
		// 统计收入金额
		BigDecimal inAmount = (BigDecimal)this.sumMyOrderAmount(inSb.toString(), null, null).get(0);
		// 统计支出金额
		BigDecimal outAmount = (BigDecimal)this.sumMyOrderAmount(outSb.toString(), null, null).get(0);
		page.setRsvdc1(inAmount == null ? BigDecimal.ZERO : inAmount);
		page.setRsvsdc2(outAmount == null ? BigDecimal.ZERO : outAmount);
		return page;
	}
	
	private Page<CustomerOrderVO, CustomerOrder> queryTraceOrders(Map<String, Object> param){
		CustomerOrderVO vo = (CustomerOrderVO) param.get("ordersKey");
		CustomerUser user = (CustomerUser) param.get(CommonUtil.CUSTOMERUSERKEY);
		StringBuffer inSb = new StringBuffer("select ifnull(sum(t.receive_amount),0) from t_customer_order t where 1=1");
		StringBuffer outSb = new StringBuffer("select ifnull(sum(t.receive_amount),0) from t_customer_order t where 1=1");
		String field ="t.id,t.customer_id,t.`order_number`,t.`create_time`,t.`order_status`,t.`order_amount`,t.`order_detail_type`,t.`awardStop`,t.`rsvdc1`,ifnull(t.rsvdc2,0),t.return_amount";
		String sql1 =" AND t.`rsvst1` IS NOT NULL AND EXISTS (SELECT 1 FROM t_customer_order o2,t_bet_record b WHERE o2.order_number = t.`rsvst1` AND o2.`order_number` = b.`order_no` AND b.`lottery_code` = '"+vo.getRsvst2()+"' AND b.`customer_id`=t.`customer_id` %2 )";
		String sql2 =" AND t.`rsvst1` IS NULL AND t.`order_detail_type` IN (18001, 18017)  AND EXISTS (SELECT 1 FROM t_bet_record b WHERE t.`order_number` = b.`order_no` AND b.`customer_id`=t.`customer_id` AND b.`lottery_code` = '"+vo.getRsvst2()+"' %2 )";
		
		StringBuilder listSql = new StringBuilder("select %1 from t_customer_order t where 1=1 ");
		StringBuilder countSql = new StringBuilder("select ifnull(count(t.`order_number`),0) from t_customer_order t where 1=1 ");

		StringBuilder conditionSql = new StringBuilder(" and t.`order_detail_type`= ").append(DataDictionaryUtil.ORDER_DETAIL_TRACK);
		// 追号记录应该只能查询到自己的追号记录
		conditionSql.append(" and t.`customer_id`= ").append(user.getId());

		conditionSql.append(" and t.create_time >= '").append(vo.getSdate()).append("'  and t.create_time <= '")
				.append(vo.getEdate()).append("'");
		if (StringUtils.isNotEmpty(vo.getRsvst3())) {
			sql1=sql1.replaceAll("%2", " and b.issue_no = "+vo.getRsvst3());
			sql2 =sql2.replaceAll("%2"," and b.issue_no = "+vo.getRsvst3());
		}else{
			sql1=sql1.replaceAll("%2", "");
			sql2 =sql2.replaceAll("%2","");
		}

		// 完成期数不等于总期数，则表示该订单在追号中。
		if (vo.getOrderStatus() == DataDictionaryUtil.COMMON_FLAG_1) {
			conditionSql.append(" and t.rsvdc1 !=t.rsvdc2 ");
		} else if (vo.getOrderStatus() == DataDictionaryUtil.COMMON_FLAG_2) {
			conditionSql.append(" and t.rsvdc1 =t.rsvdc2 ");
		}

		listSql.append(conditionSql).append(sql1).append(" union all select %1 from t_customer_order t where t.`order_detail_type`= ")
				.append(DataDictionaryUtil.ORDER_DETAIL_TRACK).append(conditionSql).append(sql2);
		countSql.append(conditionSql).append(sql1).append(" union all select ifnull(count(t.`order_number`),0) from t_customer_order t where 1=1 ").append(conditionSql).append(sql2);
		inSb.append(conditionSql).append(sql1).append(" and t.order_type = " + DataDictionaryUtil.ORDER_TYPE_INCOME).append(" union all select ifnull(sum(t.receive_amount),0) from t_customer_order t where 1=1 ").append(conditionSql).append(sql2).append(" and t.order_type = " + DataDictionaryUtil.ORDER_TYPE_INCOME);
		outSb.append(conditionSql).append(sql1).append(" and t.order_type = " + DataDictionaryUtil.ORDER_TYPE_OUT).append(" union all select ifnull(sum(t.receive_amount),0) from t_customer_order t where 1=1 ").append(conditionSql).append(sql2).append(" and t.order_type = " + DataDictionaryUtil.ORDER_TYPE_OUT);
		SQLQuery countQuery = getSession().createSQLQuery(countSql.toString());
		SQLQuery orderQuery = getSession().createSQLQuery("select * from ("+listSql.toString().replaceAll("%1", field)+" ) tt order by tt.create_time desc");

		// 查询总记录数，算分页。
		List<Object> countReulst = countQuery.list();
		BigInteger count = ((BigInteger)countReulst.get(0)).add((BigInteger)countReulst.get(1));
		if (count.intValue() == 0) {
			return null;
		}
		int totalCount = count.intValue();
		Page<CustomerOrderVO, CustomerOrder> page = new Page<CustomerOrderVO, CustomerOrder>();
		page.setPageNum(vo.getPageNum());
		int pageNum = vo.getPageNum();
		int maxY = totalCount / vo.getMaxCount();
		if (totalCount % vo.getMaxCount() != 0) {
			maxY += 1;
		}
		pageNum = pageNum <= 0 ? 1 : pageNum;
		pageNum = pageNum >= maxY ? maxY : pageNum;

		// 追号订单查询
		orderQuery.setFirstResult((pageNum - 1) * vo.getMaxCount());
		orderQuery.setMaxResults(vo.getMaxCount());
		List<Object[]> objs = orderQuery.list();

		List<CustomerOrder> orderList = new ArrayList<CustomerOrder>();
		for (int i = 0; i < objs.size(); i++) {
			Object[] objAttrs = objs.get(i);
			CustomerOrder order = new CustomerOrder();
			order.setId(Long.parseLong(objAttrs[0].toString()));
			order.setCustomerId(Long.parseLong(objAttrs[1].toString()));
			order.setOrderNumber(objAttrs[2].toString());
			order.setCreateTime((Date) objAttrs[3]);
			order.setOrderStatus(Integer.parseInt(objAttrs[4].toString()));
			order.setOrderAmount(new BigDecimal(objAttrs[5].toString()));
			order.setOrderDetailType(Integer.parseInt(objAttrs[6].toString()));
			order.setAwardStop(objAttrs[7].toString());
			// 总追号期数
			order.setRsvdc1(Long.parseLong(objAttrs[8].toString()));
			// 已完成期数
			order.setRsvdc2(Long.parseLong(objAttrs[9].toString()));
			// 取消金额
			order.setReturnAmount(new BigDecimal(objAttrs[10].toString()));

			List<Object[]> bets = getTraceRecorderData(objAttrs[2].toString());
			// 开始期号
			order.setRsvst2(bets.get(0)[1].toString());
			// 彩种名称
			order.setRsvst1(CommonStatic.getCodeMap().get(CommonStatic.LOTTERYTYPE_HEAD + bets.get(0)[2].toString())
					.toString());
			// 中奖金额，有中奖则大于0。
			order.setRsvdc4(new BigDecimal(bets.get(0)[0].toString()));
			// 已完成金额
			order.setFinishAmount(new BigDecimal(bets.get(1)[0].toString()));

			orderList.add(order);
		}
		page.setEntitylist(orderList);
		page.setPageCount(maxY);
		// 总记录数
		page.setTotalCount(totalCount);
		// 统计收入金额
		List<Object>  amounts = this.sumMyOrderAmount(inSb.toString(), null, null);
		BigDecimal inAmount = ((BigDecimal)amounts.get(0)).add((BigDecimal)amounts.get(1));
		// 统计支出金额
		amounts = this.sumMyOrderAmount(outSb.toString(), null, null);
		BigDecimal outAmount = ((BigDecimal)amounts.get(0)).add((BigDecimal)amounts.get(1));
		page.setRsvdc1(inAmount == null ? BigDecimal.ZERO : inAmount);
		page.setRsvsdc2(outAmount == null ? BigDecimal.ZERO : outAmount);
		return page;
	}

	private List<Object[]> getTraceRecorderData(String orderNo) {
		String sql = "select sum(t4.win_money),min(t4.`issue_no`),min(t4.lottery_code) from"
				+ " t_bet_record t4 where t4.`order_no` = ? and t4.bet_type = 20001 "
				+ " union all select ifnull(sum(t4.bet_money*t4.Multiple),0),min(t4.`issue_no`),min(t4.lottery_code) from"
				+ " t_bet_record t4 where t4.`order_no` = ? and t4.bet_status in (21002,21003) ";
		Query query = getSession().createSQLQuery(sql);
		query.setParameter(0, orderNo);
		query.setParameter(1, orderNo);
		return query.list();
	}
	
	private List<Object[]> getBetRecord(String orderNo) {
		String sql = "select sum(t4.win_money),min(t4.`issue_no`),min(t4.lottery_code) from"
				+ " t_bet_record t4 where t4.`order_no` = ? ";
		Query query = getSession().createSQLQuery(sql);
		query.setParameter(0, orderNo);
		return query.list();
	}

	/**
	 * 统计盈收，从下级获得的钱。
	 */
	@Override
	public Page<CustomerOrderVO, CustomerOrder> queryRevenueLower(Map<String, Object> param) throws Exception {

		CustomerOrderVO vo = (CustomerOrderVO) param.get("ordersKey");
		Long userId = (Long) param.get("userIdKey");

		StringBuffer orderSb = new StringBuffer();
		StringBuffer countsb = new StringBuffer();
		StringBuffer sumAmountsb = new StringBuffer();
		
		orderSb.append("select o1.id,o1.createTime ,o1.receiveAmount,o1.orderNumber,o1.rsvdc4,o1.rsvst1,"
				+ "o1.rsvst2,o1.rsvst3,u.customerName as rsvst4,u.customerAlias as rsvst5,u.customerLevel as rsvdc1 ");

		countsb.append("select count(o1.id) ");

		sumAmountsb.append("select sum(o1.receiveAmount) ");

		String content = " from CustomerOrder o1,CustomerOrder o2,CustomerUser u "
				+ " where o1.rsvst1=o2.orderNumber AND o2.customerId = u.id " + "and o1.orderDetailType = "
				+ DataDictionaryUtil.ORDER_DETAIL_BETTING_REVENUES + " and o1.customerId = " + userId
				+ " and o1.orderStatus= " + DataDictionaryUtil.ORDER_STATUS_SUCCESS;

		orderSb.append(content);
		countsb.append(content);
		sumAmountsb.append(content);

		if (!StringUtils.isEmpty(vo.getSdate())) {
			orderSb.append(" and o1.orderTime >= '").append(vo.getSdate()).append("'");
			countsb.append(" and o1.orderTime >= '").append(vo.getSdate()).append("'");
			sumAmountsb.append(" and o1.orderTime >= '").append(vo.getSdate()).append("'");
		}
		
		if (!StringUtils.isEmpty(vo.getEdate())) {
			orderSb.append(" and o1.orderTime <= '").append(vo.getEdate()).append("'");
			countsb.append(" and o1.orderTime <= '").append(vo.getEdate()).append("'");
			sumAmountsb.append(" and o1.orderTime <= '").append(vo.getEdate()).append("'");
		}
		if (!StringUtils.isEmpty(vo.getRsvst3())) {
			if(vo.getRsvst4().equals("false")){
				orderSb.append(" and u.customerName = ? ");
				countsb.append(" and u.customerName = ? ");
				sumAmountsb.append(" and u.customerName = ? ");
			}else{
				orderSb.append(" and u.id in (:idsKey) ");
				countsb.append(" and u.id in (:idsKey) ");
				sumAmountsb.append(" and u.id in (:idsKey) ");
			}
		}

		orderSb.append(" order by o1.createTime desc ");

		List<String> limitKeys = new ArrayList<String>();
		List<Object> limitVals = new ArrayList<Object>();

		if (!StringUtils.isEmpty(vo.getRsvst3())) {
			if(vo.getRsvst4().equals("false")){
				limitKeys.add("un");
				limitVals.add(vo.getRsvst3());
			}else{
				param.put("revenueKey", true);
				List<CustomerUser> users = customerUserDao.quUserByTeam(param);
				if (users.size() > 0) {
					List<Long> ids = new ArrayList<Long>();
					for (CustomerUser user : users) {
						ids.add(user.getId());
					}
					limitKeys.add("idsKey");
					limitVals.add(ids);
				}else{
					return new Page<CustomerOrderVO, CustomerOrder>();
				}
			}
		}

		Page<CustomerOrderVO, CustomerOrder> page = (Page<CustomerOrderVO, CustomerOrder>) pageQuery(vo, orderSb,
				countsb, limitKeys, limitVals);
		// 将总金额放到page的备用子段中 方便页面显示。
		page.setRsvsdc1((BigDecimal)this.sumMyChargeOrderAmount(sumAmountsb.toString(), limitKeys, limitVals).get(0));
		return page;
	}

	@Override
	public boolean checkIssueAward(Map<String, Object> param) throws Exception {
		String lotCode = (String) param.get("lotCodeKey");
		String issue = (String) param.get("issueKey");
		String hql = "select count(id) from CustomerOrder where orderDetailType = ? and orderStatus = ? and rsvst2 =? and rsvst3 =? ";
		Query query = getSession().createQuery(hql);
		query.setParameter(0, DataDictionaryUtil.ORDER_DETAIL_WINNING_REBATES);
		query.setParameter(1, DataDictionaryUtil.ORDER_STATUS_SUCCESS);
		query.setParameter(2, lotCode);
		query.setParameter(3, issue);
		Long count = (Long) query.list().get(0);
		if (count == 0) {
			return false;
		}
		return true;
	}

	@Override
	public boolean checkBetRebates(Map<String, Object> param) throws Exception {
		String lotCode = (String) param.get("lotCodeKey");
		String issue = (String) param.get("issueKey");
		String hql = "select count(id) from CustomerOrder where orderDetailType = ? and orderStatus = ? and rsvst2 =? and rsvst3 =? ";
		Query query = getSession().createQuery(hql);
		query.setParameter(0, DataDictionaryUtil.ORDER_DETAIL_BETTING_REBATES);
		query.setParameter(1, DataDictionaryUtil.ORDER_STATUS_SUCCESS);
		query.setParameter(2, lotCode);
		query.setParameter(3, issue);
		Long count = (Long) query.list().get(0);
		if (count == 0) {
			return false;
		}
		return true;
	}

	@Override
	public List<CustomerOrder> queryNewWinningOrder(Map<String, Object> param) throws Exception {
		int maxResult = 15;
		if (param.get("max") != null)
			maxResult = (int) param.get("max");
		//最小中奖金额
		BigDecimal minAmount = BigDecimal.ZERO;
		if(param.get("minAmountKey")!=null){
			minAmount = (BigDecimal) param.get("minAmountKey");
		}
		
		StringBuffer queryString = new StringBuffer(
				"from CustomerOrder o where o.orderDetailType = :odt and o.orderStatus = :ost "
				+ "and o.receiveAmount >= :mam order by createTime desc");
		Query query = getSession().createQuery(queryString.toString());
		query.setMaxResults(maxResult);
		query.setFirstResult(0);
		query.setParameter("odt", DataDictionaryUtil.ORDER_DETAIL_WINNING_REBATES);
		query.setParameter("mam", minAmount);
		query.setParameter("ost", DataDictionaryUtil.ORDER_STATUS_SUCCESS);
		return query.list();
	}

	@Override
	public Map<String, Object> chargeDrawCount(Map<String, Object> param) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String queryString = "select  count(t.id) as dt from t_customer_order t where "
				+ " t.order_Detail_Type =?  and t.order_status = ?";
		List<Integer> types = new ArrayList<Integer>(2);
		types.add(DataDictionaryUtil.ORDER_DETAIL_WITHDRAWALS);
		types.add(DataDictionaryUtil.ORDER_DETAIL_CASH_ADD);

		SQLQuery query = getSession().createSQLQuery(queryString);
		query.setParameter(0, DataDictionaryUtil.ORDER_DETAIL_WITHDRAWALS);
		query.setParameter(1, DataDictionaryUtil.ORDER_STATUS_DISPOSING);

		BigInteger drawCount = (BigInteger) query.list().get(0);

		query.setParameter(0, DataDictionaryUtil.ORDER_DETAIL_CASH_ADD);
		query.setParameter(1, DataDictionaryUtil.ORDER_STATUS_DISPOSING);

		BigInteger chargeCount = (BigInteger) query.list().get(0);

		query.setParameter(0, DataDictionaryUtil.ORDER_DETAIL_DISTRIBUTED_ACTIVITIES);
		query.setParameter(1, DataDictionaryUtil.ORDER_STATUS_DISPOSING);

		BigInteger activityCount = (BigInteger) query.list().get(0);

		returnMap.put("drawCount", drawCount);
		returnMap.put("chargeCount", chargeCount);
		returnMap.put("activityCount", activityCount);
		return returnMap;
	}
	
	@Override
	public List<CustomerOrderStaVo> queryDayYkRecords(Map<String, Object> param) throws Exception {
		List<CustomerOrderStaVo> customerOrderStaVos = new ArrayList<CustomerOrderStaVo>();
		CustomerOrderStaVo vo = new CustomerOrderStaVo();
		CustomerUser customerUser = (CustomerUser) param.get("customerUser");
		// t.bet_status <> 21004 撤销 t.bet_status <> 21001 投注成功
		StringBuilder sbsql = new StringBuilder(
				"SELECT ifnull(sum(t.win_money), 0),ifnull(sum(t.bet_money * t.Multiple),0),ifnull(sum(t.rebates*t.bet_money * t.Multiple),0)");
		sbsql.append(" from t_bet_record t where t.bet_status <> 21004 and t.bet_status <> 21001");
		sbsql.append(" and t.create_time >= '").append(param.get("sdate")).append("' and t.create_time < '")
				.append(param.get("edate")).append("'").append(" and t.customer_id = ").append(customerUser.getId());

		SQLQuery query = getSession().createSQLQuery(sbsql.toString());
		Object[] result = (Object[]) query.list().get(0);
		vo.setWinAmount(new BigDecimal(result[0].toString()));
		vo.setTotalTetAmount(new BigDecimal(result[1].toString()));
		vo.setRebateAmount(new BigDecimal(result[2].toString()));
		vo.setCreateTime(new Date());
		vo.setCreateUser(customerUser.getCustomerName());
		vo.setSaleAmount(vo.getTotalTetAmount().subtract(vo.getRebateAmount()));
		vo.setYkAmount(vo.getWinAmount().subtract(vo.getSaleAmount()));

		customerOrderStaVos.add(vo);
		return customerOrderStaVos;
	}

	@Override
	public List<CustomerOrderStaVo> queryHistoryYkRecords(Map<String, Object> param) throws Exception {
		List<CustomerOrderStaVo> customerOrderStaVos = new ArrayList<CustomerOrderStaVo>();
		CustomerOrderStaVo vo  =null;
		CustomerUser customerUser = (CustomerUser) param.get("customerUser");
		StringBuilder sqlStr = new StringBuilder("select t.total_amount,t.rebate,t.win_amount,t.create_time from t_customer_order_sta t where t.customer_id=").append(customerUser.getId());
		sqlStr.append(" and t.create_time >= '").append(param.get("sdate")).append("' and t.create_time < '").append(param.get("edate")).append("'").append(" order by t.create_time desc");
		SQLQuery query = getSession().createSQLQuery(sqlStr.toString());
		List<Object[]> objs =  (List<Object[]>)query.list();
		for(Object[] obj :objs){
			vo = new CustomerOrderStaVo();
			vo.setTotalTetAmount(new BigDecimal(obj[0].toString()));
			vo.setRebateAmount(new BigDecimal(obj[1].toString()));
			vo.setWinAmount(new BigDecimal(obj[2].toString()));
			vo.setCreateTime(DateUtil.strToDate2(obj[3].toString()));
			vo.setCreateUser(customerUser.getCustomerName());
			vo.setSaleAmount(vo.getTotalTetAmount().subtract(vo.getRebateAmount()));
			vo.setYkAmount(vo.getWinAmount().subtract(vo.getSaleAmount()));

			customerOrderStaVos.add(vo);
		}
		return customerOrderStaVos;
	}
}
