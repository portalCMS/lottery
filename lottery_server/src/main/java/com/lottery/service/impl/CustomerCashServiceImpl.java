package com.lottery.service.impl;

import java.math.BigDecimal;
import java.util.Map;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.CustomerCash;
import com.lottery.bean.entity.CustomerOrder;
import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.vo.CustomerCashVO;
import com.lottery.bean.entity.vo.CustomerUserVO;
import com.lottery.dao.ICustomerCashDao;
import com.lottery.dao.ICustomerOrderDao;
import com.lottery.dao.ICustomerUserDao;
import com.lottery.dao.impl.AdminWriteLog;
import com.lottery.dao.impl.CustomerUserWriteLog;
import com.lottery.service.ICustomerCashService;
import com.lottery.service.IOrderSequenceService;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;
import com.xl.lottery.util.DateUtil;

@Service
public class CustomerCashServiceImpl implements ICustomerCashService {

	@Autowired
	private ICustomerCashDao customerCashDao;

	@Autowired
	private ICustomerUserDao customerUserDao;

	@Autowired
	private CustomerUserWriteLog customerUserWriteLog;

	@Autowired
	private ICustomerOrderDao customerOrderDao;

	@Autowired
	private IOrderSequenceService sequenceService;
	
	@Autowired
	private AdminWriteLog adminWriteLog;

	@Override
	public String updateCustomerCash(Map<String, ?> param) throws Exception {
		// TODO Auto-generated method stub
		CustomerUser user = (CustomerUser) param
				.get(CommonUtil.CUSTOMERUSERKEY);
		CustomerCashVO cashVO = (CustomerCashVO) param.get("customercashkey");

		CustomerCash fcash = findCustomerCashByCustomerId(user.getId());
		CustomerCash scash = findCustomerCashByCustomerId(cashVO.getCuId());
		if (fcash.getCash().doubleValue() - cashVO.getCash().doubleValue() < 0) {
			throw new LotteryException("余额不足");
		}
		
		//生成转出订单
		String orderNumber = sequenceService.getOrderSequence(
				"OO", 8);
		CustomerOrder orderout = customerUserVOTOEntity(cashVO.getCash(),
				orderNumber, user.getId(), DataDictionaryUtil.ORDER_TYPE_OUT,
				DataDictionaryUtil.ORDER_DETAIL_MONEY_TRANSFER, user.getCustomerName());
		orderout.setFromCustomerId(cashVO.getCuId());
		customerOrderDao.insert(orderout);
		customerUserWriteLog.saveWriteLog(user, CommonUtil.UPDATE,
				"CustomerOrder", "资金转出订单" + fcash.getCash());
		
		//资金转出
		fcash.setCash(new BigDecimal(fcash.getCash().doubleValue()
				- cashVO.getCash().doubleValue()));
		customerCashDao.update(fcash);
		customerUserWriteLog.saveWriteLog(user, CommonUtil.UPDATE,
				"CustomerCash", "资金转出" + fcash.getCash());
		
		//生成转入订单
		String orderNumber1 = sequenceService.getOrderSequence(
				"OO", 8);
		CustomerOrder orderin = customerUserVOTOEntity(cashVO.getCash(),
				orderNumber1, cashVO.getCuId(), DataDictionaryUtil.ORDER_TYPE_INCOME,
				DataDictionaryUtil.ORDER_DETAIL_MONEY_INTO, user.getCustomerName());
		orderin.setFromCustomerId(user.getId());
		customerOrderDao.insert(orderin);
		customerUserWriteLog.saveWriteLog(user, CommonUtil.UPDATE,
				"CustomerOrder", "资金转入订单" + fcash.getCash());
		
		//资金转入
		scash.setCash(new BigDecimal(scash.getCash().doubleValue()
				+ cashVO.getCash().doubleValue()));
		customerCashDao.update(scash);
		customerUserWriteLog.saveWriteLog(user, CommonUtil.UPDATE,
				"CustomerCash", "资金转入" + scash.getCash());
		
		return "success";
	}

	private CustomerCash findCustomerCashByCustomerId(long customerId) {
		StringBuffer queryString = new StringBuffer(
				" from CustomerCash t where t.customerId = ? and t.cashStatus = ? ");
		Query query = customerCashDao.getSession().createQuery(
				queryString.toString());
		query.setParameter(0, customerId);
		query.setParameter(1, DataDictionaryUtil.STATUS_OPEN);
		return (CustomerCash) query.list().get(0);
	}

	public CustomerOrder customerUserVOTOEntity(BigDecimal cash,
			String orderNumber, long userId, int orderType,
			int orderDetailType, String username) {
		CustomerOrder order = new CustomerOrder();
		order.setCustomerId(userId);
		order.setOrderNumber(orderNumber);
		order.setOrderAmount(cash);
		order.setOrderType(orderType);
		order.setOrderDetailType(orderDetailType);
		order.setOrderTime(DateUtil.getNowDate());
		order.setOrderStatus(DataDictionaryUtil.ORDER_STATUS_SUCCESS);
		order.setOrderAmount(cash);
		order.setCashAmount(cash);
		order.setReceiveAmount(cash);
		order.setCreateTime(DateUtil.getNowDate());
		order.setUpdateTime(DateUtil.getNowDate());
		order.setCreateUser(username);
		order.setUpdateUser(username);
		return order;
	}

	@Override
	public String updateCustomerCashToLowerCustomerCash(Map<String, ?> param)
			throws Exception {
		// TODO Auto-generated method stub
		AdminUser user = (AdminUser) param
				.get(CommonUtil.USERKEY);
		CustomerUserVO vo = (CustomerUserVO) param.get("customeruservokey");

		CustomerCash fcash = findCustomerCashByCustomerId(vo.getUserMainId());
		CustomerCash scash = findCustomerCashByCustomerId(vo.getUserSunId());
		if (fcash.getCash().doubleValue() - vo.getCash().doubleValue() < 0) {
			throw new LotteryException("余额不足");
		}
		
		//生成转出订单
		String orderNumber = sequenceService.getOrderSequence(
				CommonUtil.ORDER_DETAIL_MOVE_MONRY, 8);
		CustomerOrder orderout = customerUserVOTOEntity(vo.getCash(),
				orderNumber, vo.getUserMainId(), DataDictionaryUtil.ORDER_TYPE_OUT,
				DataDictionaryUtil.ORDER_DETAIL_MONEY_TRANSFER, user.getUserName());
		orderout.setFromCustomerId(vo.getUserSunId());
		
		//资金转出
		fcash.setCash(new BigDecimal(fcash.getCash().doubleValue()
				- vo.getCash().doubleValue()));
		customerCashDao.update(fcash);
		adminWriteLog.saveWriteLog(user, CommonUtil.UPDATE,
				"CustomerCash", "资金转出" + fcash.getCash());
		
		//生成转入订单
		String orderNumber1 = sequenceService.getOrderSequence(
				CommonUtil.ORDER_DETAIL_MOVE_MONRY, 8);
		CustomerOrder orderin = customerUserVOTOEntity(vo.getCash(),
				orderNumber1, vo.getUserSunId(), DataDictionaryUtil.ORDER_TYPE_INCOME,
				DataDictionaryUtil.ORDER_DETAIL_MONEY_INTO, user.getUserName());
		orderin.setFromCustomerId(vo.getUserMainId());
		customerOrderDao.insert(orderin);
		adminWriteLog.saveWriteLog(user, CommonUtil.UPDATE,
				"CustomerOrder", "资金转入订单" + fcash.getCash());
		
		//资金转入
		scash.setCash(new BigDecimal(scash.getCash().doubleValue()
				+ vo.getCash().doubleValue()));
		customerCashDao.update(scash);
		adminWriteLog.saveWriteLog(user, CommonUtil.UPDATE,
				"CustomerCash", "资金转入" + scash.getCash());
		
		//记录账户余额
		orderout.setAccountBalance(scash.getCash());
		
		customerOrderDao.insert(orderout);
		adminWriteLog.saveWriteLog(user, CommonUtil.UPDATE,
				"CustomerOrder", "资金转出订单" + orderout.toString());
		return "success";
	}

	@Override
	public CustomerCash findCustomerCashByUserId(Map<String, ?> param)
			throws Exception {
		// TODO Auto-generated method stub
		long userId = (Long) param.get("userId");
		StringBuffer queryString = new StringBuffer(" from CustomerCash t where t.customerId = ? and t.cashStatus = ? ");
		Query query = customerCashDao.getSession().createQuery(queryString.toString());
		query.setParameter(0, userId);
		query.setParameter(1, DataDictionaryUtil.STATUS_OPEN);
		return (CustomerCash) query.list().get(0);
	}
}
