package com.lottery.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.hibernate.Query;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.BankManage;
import com.lottery.bean.entity.CustomerBankCard;
import com.lottery.bean.entity.CustomerCash;
import com.lottery.bean.entity.CustomerOrder;
import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.LotteryType;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.UserCard;
import com.lottery.bean.entity.vo.CustomerMessageVO;
import com.lottery.bean.entity.vo.CustomerOrderStaVo;
import com.lottery.bean.entity.vo.CustomerOrderVO;
import com.lottery.bean.entity.vo.GenericEntityVO;
import com.lottery.bean.entity.vo.OrtherPayVO;
import com.lottery.bean.entity.vo.OrtherYBPayVO;
import com.lottery.dao.IAdminParameterDao;
import com.lottery.dao.IBankManageDao;
import com.lottery.dao.ICustomerBankCardDao;
import com.lottery.dao.ICustomerCashDao;
import com.lottery.dao.ICustomerOrderDao;
import com.lottery.dao.ICustomerUserDao;
import com.lottery.dao.ILotteryTypeDao;
import com.lottery.dao.IUserCardDao;
import com.lottery.dao.impl.AdminWriteLog;
import com.lottery.dao.impl.CustomerUserWriteLog;
import com.lottery.pey.encrypt.EncryUtil;
import com.lottery.service.ICustomerMessageService;
import com.lottery.service.ICustomerOrderService;
import com.lottery.service.IDataDictionaryService;
import com.lottery.service.IOrderSequenceService;
import com.xl.lottery.desutil.AesUtil;
import com.xl.lottery.desutil.Md5Manage;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.util.BeanPropertiesCopy;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;
import com.xl.lottery.util.DateUtil;
import com.xl.lottery.util.DozermapperUtil;
import com.xl.lottery.util.MD5Util;

@Service
public class CustomerOrderServiceImpl implements ICustomerOrderService {
	@Autowired
	private ICustomerMessageService msgService;

	@Autowired
	private ICustomerOrderDao orderDao;

	@Autowired
	private ICustomerCashDao userCashDao;

	@Autowired
	private IOrderSequenceService sequenceService;

	@Autowired
	private AdminWriteLog adminWriteLog;

	@Autowired
	private IAdminParameterDao parameterDao;

	@Autowired
	private ICustomerUserDao userDao;

	@Autowired
	private ICustomerBankCardDao cardDao;

	@Autowired
	private IBankManageDao bankDao;

	@Autowired
	private IUserCardDao userCardDao;

	@Autowired
	private IDataDictionaryService dataDictionaryService;

	@Autowired
	private ILotteryTypeDao lotteryTypeDao;

	@Autowired
	private CustomerUserWriteLog userlog;

	@Override
	public boolean saveSignleRechargeOrDarwing(Map<String, Object> param) throws Exception {
		CustomerOrderVO orderVo = (CustomerOrderVO) param.get("customerOrder");
		AdminUser admin = (AdminUser) param.get(CommonUtil.USERKEY);

		String orderNumber = "";
		// if(orderVo.getOrderDetailType()==DataDictionaryUtil.ORDER_DETAIL_BACKGROUND_ADD){
		// orderNumber = sequenceService.getOrderSequence(CommonUtil.ORDER_DETAIL_SINGLE_RECHARGE, 8);
		// }else if(orderVo.getOrderDetailType()==DataDictionaryUtil.ORDER_DETAIL_BACKGROUND_DEDUCTIONS){
		// orderNumber = sequenceService.getOrderSequence(CommonUtil.ORDER_DETAIL_SINGLE_DRAWING, 8);
		// }
		orderNumber = sequenceService.getOrderSequence("OO", 8);
		CustomerOrder order = customerUserVOTOEntity(orderVo, orderNumber, admin);

		adminWriteLog.saveWriteLog(admin, CommonUtil.SAVE, "t_customer_order", order.toString());
		// 更新用户金额记录
		CustomerCash userCash = userCashDao.queryUserCashByCustomerId(orderVo.getCustomerId());
		if (order.getOrderType() == DataDictionaryUtil.ORDER_TYPE_INCOME) {
			userCash.setCash(userCash.getCash().add(order.getReceiveAmount()));
		} else if (order.getOrderType() == DataDictionaryUtil.ORDER_TYPE_OUT) {
			if (userCash.getCash().compareTo(order.getReceiveAmount()) < 0) {
				throw new LotteryException("用户余额不足以扣款！");
			}
			userCash.setCash(userCash.getCash().subtract(order.getReceiveAmount()));
		}

		userCash.setUpdateUser(admin.getUserName());
		userCash.setUpdateTime(DateUtil.getNowDate());
		userCashDao.update(userCash);

		// 记录用户余额
		order.setAccountBalance(userCash.getCash());
		// 保存订单记录表(后台充值)
		orderDao.saveSignleRecharge(order);

		adminWriteLog.saveWriteLog(admin, CommonUtil.UPDATE, "t_customer_cash", "更变金额:"
				+ order.getReceiveAmount().toString());
		return true;
	}

	public CustomerOrder customerUserVOTOEntity(CustomerOrderVO orderVo, String orderNumber, AdminUser admin) {
		CustomerOrder order = new CustomerOrder();
		BeanUtils.copyProperties(orderVo, order);
		order.setOrderNumber(orderNumber);
		switch (order.getOrderDetailType()) {
		case 18011:
		case 18016:
		case 18019:
		case 18012:
		case 18009:
			order.setOrderType(DataDictionaryUtil.ORDER_TYPE_INCOME);
			break;

		default:
			order.setOrderType(DataDictionaryUtil.ORDER_TYPE_OUT);
			break;
		}
		order.setOrderTime(DateUtil.getNowDate());
		order.setOrderStatus(DataDictionaryUtil.ORDER_STATUS_SUCCESS);
		order.setOrderAmount(order.getReceiveAmount());
		order.setCashAmount(order.getReceiveAmount());
		order.setCreateTime(DateUtil.getNowDate());
		order.setUpdateTime(DateUtil.getNowDate());
		order.setCreateUser(admin.getUserName());
		order.setUpdateUser(admin.getUserName());
		order.setRemark(orderVo.getRemark());
		return order;
	}

	@Override
	public void saveDrawingRqeuest(Map<String, Object> param) throws Exception {
		CustomerUser user = (CustomerUser) param.get(CommonUtil.CUSTOMERUSERKEY);
		CustomerOrderVO orderVo = (CustomerOrderVO) param.get("orderKey");
		BigDecimal amount = orderVo.getCashAmount();

		// 1.判断提款次数是否已经用完。2.判断提款金额是否在配置的最大提款金额和最小提款金额。3.判断当前提款时间是否在允许范围内.4.判断账户余额是否可能提款这么多金额.5.判断银行是否不允许提款。
		if (orderVo.getReferenceId() == null)
			throw new LotteryException("请绑定一张银行卡");
		long cardId = orderVo.getReferenceId();
		UserCard card = userCardDao.queryById(cardId);
		long bankId = card.getBankId();
		BankManage bank = bankDao.queryById(bankId);
		if (bank.getOut() != DataDictionaryUtil.COMMON_FLAG_1) {
			throw new LotteryException("尊敬的用户你所选的卡，对应的银行不允许进行提款业务，请选择其它卡!");
		}

		CustomerCash userCash = userCashDao.queryUserCashByCustomerId(user.getId());
		BigDecimal reminCash = userCash.getCash();
		if (userCash.getCashStatus() != DataDictionaryUtil.STATUS_OPEN) {
			throw new LotteryException("尊敬的用户你的账户金额状态已被冻结，不能进行提款业务！如有任何疑问，请联系系统管理员。");
		} else if (reminCash.compareTo(amount) < 0) {
			throw new LotteryException("尊敬的用户你的账户金额余额为[" + reminCash + "]不足以提款[" + amount + "]!");
		} else {
			// 更新设置冻结金额
			userCash.setCash(userCash.getCash().subtract(amount));
			userCash.setFrozenCash(userCash.getFrozenCash().add(amount));
			userCash.setUpdateTime(DateUtil.getNowDate());
			userCash.setUpdateUser(user.getCustomerName());
			userCashDao.update(userCash);
		}

		CustomerOrderVO orderVo1 = new CustomerOrderVO();
		orderVo1.setOrderType(DataDictionaryUtil.ORDER_TYPE_OUT);
		orderVo1.setOrderDetailType(DataDictionaryUtil.ORDER_DETAIL_WITHDRAWALS);
		orderVo1.setCustomerId(user.getId());
		orderVo1.setOrderTime(DateUtil.getNowDate());
		int drawingTimes = orderDao.countDrawingTimesToday(orderVo1);
		Map<String, String> timesMap = parameterDao
				.queryParameterList("financeConfig", new String[] { "drawingTimes" });
		int times = Integer.parseInt(timesMap.get("drawingTimes"));
		int reminTimes = times - drawingTimes;
		if (reminTimes <= 0) {
			throw new LotteryException("亲，当天的提款次数已经用完，请明天再进行提款!");
		}

		String[] keys = new String[] { "drawingMinAmount", "drawingMaxAmount", "drawingStartTime", "drawingEndTime" };
		Map<String, String> returnMap = parameterDao.queryParameterList("financeConfig", keys);
		String nowTimeStr = DateUtil.getTimeShort();
		String configStartTimeStr = returnMap.get("drawingStartTime");
		String configEndTimeStr = returnMap.get("drawingEndTime");
		nowTimeStr = nowTimeStr.replaceAll(":", "");
		nowTimeStr = nowTimeStr.substring(0, nowTimeStr.length() - 2);
		configStartTimeStr = configStartTimeStr.replaceAll(":", "");
		configEndTimeStr = configEndTimeStr.replaceAll(":", "");
		int nowInt = Integer.parseInt(nowTimeStr);
		int cendInt = Integer.parseInt(configEndTimeStr);
		int cstartInt = Integer.parseInt(configStartTimeStr);
		if ((cstartInt > cendInt && nowInt > cendInt && nowInt < cstartInt)
				|| (cstartInt < cendInt && (nowInt > cendInt || nowInt < cstartInt))) {
			throw new LotteryException("亲，当前时间,不在提款服务[" + returnMap.get("drawingStartTime") + ","
					+ returnMap.get("drawingEndTime") + "]的时间范围内哦！");
		}

		String drawingMinAmount = returnMap.get("drawingMinAmount");
		String drawingMaxAmount = returnMap.get("drawingMaxAmount");
		BigDecimal minAmount = new BigDecimal(drawingMinAmount);
		BigDecimal maxAmount = new BigDecimal(drawingMaxAmount);
		if (minAmount.compareTo(amount) > 0) {
			throw new LotteryException("亲，提款金额[" + amount + "]必须大于允许的，提款最小金额[" + minAmount + "]哦!");
		}

		if (maxAmount.compareTo(amount) < 0) {
			throw new LotteryException("亲，提款金额[" + amount + "]必须小于允许的，提款最大金额[" + maxAmount + "]哦!");
		}

		// int drawingTimes = Integer.parseInt(returnMap.get("drawingTimes"));

		CustomerOrder order = new CustomerOrder();
		order.setCashAmount(amount);
		order.setOrderAmount(amount);
		order.setReceiveAmount(amount);
		order.setReceiveAmount(amount);
		order.setOrderType(DataDictionaryUtil.ORDER_TYPE_OUT);
		order.setOrderDetailType(DataDictionaryUtil.ORDER_DETAIL_WITHDRAWALS);
		order.setOrderStatus(DataDictionaryUtil.ORDER_STATUS_DISPOSING);
		order.setCustomerId(user.getId());
		order.setCreateTime(DateUtil.getNowDate());
		order.setCreateUser(user.getCustomerName());
		order.setUpdateTime(DateUtil.getNowDate());
		order.setUpdateUser(user.getCustomerName());
		order.setOrderTime(DateUtil.getNowDate());

		String orderNumber = sequenceService.getOrderSequence(CommonUtil.ORDER_DETAIL_USER_DRAWING, 8);
		order.setOrderNumber(orderNumber);

		order.setReferenceId(orderVo.getReferenceId());
		orderDao.insert(order);
	}

	/**
	 * 获取当天剩余的提款次数
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Override
	public int countDrawingTimesToday(Map<String, Object> param) throws Exception {

		CustomerOrderVO orderVo = (CustomerOrderVO) param.get("orderKey");
		orderVo.setOrderTime(DateUtil.getNowDate());
		int drawingTimes = orderDao.countDrawingTimesToday(orderVo);
		Map<String, String> timesMap = parameterDao
				.queryParameterList("financeConfig", new String[] { "drawingTimes" });
		int times = Integer.parseInt(timesMap.get("drawingTimes"));
		int reminTimes = times - drawingTimes;
		return reminTimes;
	}

	@Override
	public int countRechargeTimesToday(Map<String, Object> param) throws Exception {
		CustomerOrderVO orderVo = (CustomerOrderVO) param.get("orderKey");
		orderVo.setOrderTime(DateUtil.getNowDate());
		int rechargeTimes = orderDao.countRechargeTimesToday(orderVo);
		Map<String, String> timesMap = parameterDao.queryParameterList("financeConfig",
				new String[] { "rechargeTimes" });
		int times = Integer.parseInt(timesMap.get("rechargeTimes"));
		int reminTimes = times - rechargeTimes;
		return reminTimes;
	}

	@Override
	public void saveUserRecharge(Map<String, Object> param) throws Exception {
		CustomerUser user = (CustomerUser) param.get(CommonUtil.CUSTOMERUSERKEY);
		CustomerOrderVO orderVo = (CustomerOrderVO) param.get("orderkey");
		BigDecimal amount = orderVo.getCashAmount();

		// 1.判断提款次数是否已经用完。2.判断提款金额是否在配置的最大提款金额和最小提款金额。3.判断当前提款时间是否在允许范围内.4.检查银行是否允许充值。
		CustomerOrderVO orderVo1 = new CustomerOrderVO();
		orderVo1.setOrderType(DataDictionaryUtil.ORDER_TYPE_INCOME);
		orderVo1.setOrderDetailType(DataDictionaryUtil.ORDER_DETAIL_CASH_ADD);
		orderVo1.setCustomerId(user.getId());
		orderVo1.setOrderTime(DateUtil.getNowDate());
		int rechargeTimes = orderDao.countDrawingTimesToday(orderVo1);
		Map<String, String> timesMap = parameterDao.queryParameterList("financeConfig",
				new String[] { "rechargeTimes" });
		int times = Integer.parseInt(timesMap.get("rechargeTimes"));
		int reminTimes = times - rechargeTimes;
		if (reminTimes <= 0) {
			throw new LotteryException("亲，当天的充值次数已经用完，请明天再进行充值!");
		}

		String[] keys = new String[] { "rechargeMinAmount", "rechargeMaxAmount", "rechargeStartTime", "rechargeEndTime" };
		Map<String, String> returnMap = parameterDao.queryParameterList("financeConfig", keys);
		String nowTimeStr = DateUtil.getTimeShort();
		String configStartTimeStr = returnMap.get("rechargeStartTime");
		String configEndTimeStr = returnMap.get("rechargeEndTime");
		nowTimeStr = nowTimeStr.replaceAll(":", "");
		nowTimeStr = nowTimeStr.substring(0, nowTimeStr.length() - 2);
		configStartTimeStr = configStartTimeStr.replaceAll(":", "");
		configEndTimeStr = configEndTimeStr.replaceAll(":", "");

		int nowInt = Integer.parseInt(nowTimeStr);
		int cendInt = Integer.parseInt(configEndTimeStr);
		int cstartInt = Integer.parseInt(configStartTimeStr);
		if ((cstartInt > cendInt && nowInt > cendInt && nowInt < cstartInt)
				|| (cstartInt < cendInt && (nowInt > cendInt || nowInt < cstartInt))) {
			throw new LotteryException("亲，当前时间,不在充值服务时间[" + returnMap.get("rechargeStartTime") + ","
					+ returnMap.get("rechargeEndTime") + "]的范围内哦！");
		}

		String rechargeMinAmount = returnMap.get("rechargeMinAmount");
		String rechargeMaxAmount = returnMap.get("rechargeMaxAmount");
		BigDecimal minAmount = new BigDecimal(rechargeMinAmount);
		BigDecimal maxAmount = new BigDecimal(rechargeMaxAmount);
		if (minAmount.compareTo(amount) > 0) {
			throw new LotteryException("亲，充值金额[" + amount + "]必须大于允许的，充值最小金额[" + minAmount + "]哦!");
		}

		if (maxAmount.compareTo(amount) < 0) {
			throw new LotteryException("亲，充值金额[" + amount + "]必须小于允许的，充值最大金额[" + maxAmount + "]哦!");
		}

		// int drawingTimes = Integer.parseInt(returnMap.get("drawingTimes"));

		CustomerOrder order = new CustomerOrder();
		order.setCashAmount(amount);
		order.setOrderAmount(amount);
		order.setReceiveAmount(amount);
		order.setReceiveAmount(amount);
		order.setOrderType(DataDictionaryUtil.ORDER_TYPE_INCOME);
		order.setOrderDetailType(DataDictionaryUtil.ORDER_DETAIL_CASH_ADD);
		order.setOrderStatus(DataDictionaryUtil.ORDER_STATUS_DISPOSING);
		order.setCustomerId(user.getId());
		order.setCreateTime(DateUtil.getNowDate());
		order.setCreateUser(user.getCustomerName());
		order.setUpdateTime(DateUtil.getNowDate());
		order.setUpdateUser(user.getCustomerName());
		order.setOrderTime(DateUtil.getNowDate());

		String orderNumber = sequenceService.getOrderSequence(CommonUtil.ORDER_DETAIL_USER_RECHARGE, 8);
		order.setOrderNumber(orderNumber);
		order.setReferenceId(orderVo.getReferenceId());
		order.setRemark(orderVo.getRemark());
		order.setSourceId(orderVo.getSourceId());

		// 设置过期时间
		CustomerBankCard card = cardDao.queryById(orderVo.getReferenceId());
		long bankId = card.getBankId();
		BankManage bank = bankDao.queryById(bankId);

		int cancelMinute = bank.getCancelTime();
		String cancelTime = DateUtil.getPreTime(DateUtil.dateToStrLong(order.getOrderTime()),
				String.valueOf(cancelMinute));
		order.setCancelTime(DateUtil.strToDateLong(cancelTime));
		orderDao.insert(order);
	}

	@Override
	public Page<CustomerOrderVO, CustomerOrder> updateQueryRechargeOrderByPage(Map<String, Object> param)
			throws Exception {
		CustomerOrderVO orderVo = (CustomerOrderVO) param.get("customerOrderKey");
		if (null != orderVo.getCustomerName() && !orderVo.getCustomerName().equals("")) {
			List<CustomerUser> userList = userDao.queryUserByName(orderVo);
			if (userList.size() == 0) {
				throw new LotteryException("用户名" + orderVo.getCustomerName() + "对应的用户记录不存在，请确认用户名是否正确！");
			}
			orderVo.setCustomerId(userList.get(0).getId());
		}
		Page<CustomerOrderVO, CustomerOrder> pagelist = orderDao.queryUserOrderByPage(orderVo);
		for (CustomerOrder order : pagelist.getEntitylist()) {
			CustomerUser user = userDao.queryById(order.getCustomerId());
			order.setUser(user);
			if (order.getOrderType() == DataDictionaryUtil.ORDER_TYPE_INCOME) {
				CustomerBankCard card = cardDao.queryById(order.getReferenceId());
				order.setCard(card);
			}
			// 如果订单过期则修改订单状态为过期
			if (null != order.getCancelTime() && order.getOrderType() == DataDictionaryUtil.ORDER_TYPE_INCOME
					&& order.getCancelTime().before(DateUtil.getNowDate())
					&& order.getOrderStatus() == DataDictionaryUtil.ORDER_STATUS_DISPOSING) {
				order.setOrderStatus(DataDictionaryUtil.ORDER_STATUS_OVERDUE);
				orderDao.update(order);
			}
		}
		return pagelist;
	}

	@Override
	public Page<CustomerOrderVO, CustomerOrder> queryDrawingOrderByPage(Map<String, Object> param) throws Exception {
		return this.updateQueryRechargeOrderByPage(param);
	}

	/**
	 * 通过审核充值订单 通过参数配置生成手续费
	 */
	@Override
	public CustomerOrder saveApproveRechargeOrder(Map<String, Object> param) throws Exception {
		AdminUser user = (AdminUser) param.get(CommonUtil.USERKEY);
		CustomerOrderVO orderVo = (CustomerOrderVO) param.get("orderKey");
		long orderId = orderVo.getId();
		CustomerOrder order = orderDao.queryById(orderId);
		// 验证订单状态必须是处理中
		if (order.getOrderStatus() != DataDictionaryUtil.ORDER_STATUS_DISPOSING) {
			throw new LotteryException("订单状态必须是处理中，才可以进行通过审核的操作！");
		}
		// 查询订单关联的公司分配卡的对应银行的转账过期时间设置。
		if (null != order.getCancelTime() && DateUtil.getNowDate().after(order.getCancelTime())) {
			throw new LotteryException("该订单已于[" + DateUtil.dateToStr(order.getCancelTime()) + "]过期，若要通过审核订单，请先重启订单！");
		}

		// 更新手续费字段.如果没有手续费则按照默认配置去生成手续费.
		BigDecimal transferAmount = orderVo.getTransferAmount();
		if (null == transferAmount) {
			transferAmount = BigDecimal.ZERO;
		}
		if (transferAmount.compareTo(BigDecimal.ZERO) < 1) {
			String[] keys = new String[] { "transferPercent" };
			Map<String, String> returnMap = parameterDao.queryParameterList("chargeConfig", keys);
			String percent = returnMap.get("transferPercent");
			transferAmount = order.getOrderAmount().multiply(new BigDecimal(percent));
		}
		order.setTransferAmount(transferAmount);

		// 根据实收金额更新用户的customerCash表。
		CustomerCash customerCash = userCashDao.queryUserCashByCustomerId(order.getCustomerId());

		// 生成手续费订单
		this.createTransferOrder(order, user, customerCash.getCash());

		// SUP-99 银行转账现金充值逻辑错误
		BigDecimal cash = customerCash.getCash().add(order.getReceiveAmount()).add(transferAmount);
		customerCash.setCash(cash);
		userCashDao.update(customerCash);

		// 如果实收金额不输入的，即表格中直接审核的，将按照默认减去手续费算实收金额。
		if (null == orderVo.getReceiveAmount()) {
			order.setReceiveAmount(order.getOrderAmount());
		} else {
			order.setReceiveAmount(orderVo.getReceiveAmount());
		}

		order.setOrderStatus(DataDictionaryUtil.ORDER_STATUS_SUCCESS);
		order.setUpdateTime(DateUtil.getNowDate());
		order.setUpdateUser(user.getUserName());
		// 备注信息
		order.setRsvst4(orderVo.getRsvst4());
		// 记录用户账户余额
		order.setAccountBalance(customerCash.getCash());
		orderDao.update(order);

		// 查询一遍相关信息，方便页面重构。
		this.queryOrderUserAndCard(order);

		return order;
	}

	/**
	 * 生成手续费订单
	 * 
	 * @param order
	 * @param cashAmount
	 * @throws Exception
	 */
	private void createTransferOrder(CustomerOrder order, AdminUser user, BigDecimal cashAmount) throws Exception {
		CustomerOrder tOrder = new CustomerOrder();
		String orderNumber = sequenceService.getOrderSequence(CommonUtil.ORDER_DETAIL_BET_ORDER, 8);
		tOrder.setOrderNumber(orderNumber);
		tOrder.setOrderAmount(order.getTransferAmount());
		tOrder.setReceiveAmount(order.getTransferAmount());
		tOrder.setCashAmount(order.getTransferAmount());
		tOrder.setOrderType(DataDictionaryUtil.ORDER_TYPE_INCOME);
		tOrder.setOrderDetailType(DataDictionaryUtil.ORDER_DETAIL_FEE_REFUND);
		tOrder.setCustomerId(order.getCustomerId());
		tOrder.setOrderStatus(DataDictionaryUtil.ORDER_STATUS_SUCCESS);
		// 来源订单编号
		tOrder.setRsvst1(order.getOrderNumber());
		tOrder.setOrderTime(DateUtil.getNowDate());
		tOrder.setCreateTime(DateUtil.getNowDate());
		tOrder.setCreateUser(user.getUserName());
		tOrder.setUpdateTime(DateUtil.getNowDate());
		tOrder.setUpdateUser(user.getUserName());
		// 记录用户余额
		tOrder.setAccountBalance(cashAmount);
		orderDao.insert(tOrder);
	}

	/**
	 * 驳回充值
	 */
	@Override
	public CustomerOrder saveRejectRechargeOrder(Map<String, Object> param) throws Exception {
		AdminUser user = (AdminUser) param.get(CommonUtil.USERKEY);
		CustomerOrderVO orderVo = (CustomerOrderVO) param.get("orderKey");
		long orderId = orderVo.getId();
		CustomerOrder order = orderDao.queryById(orderId);
		// 验证订单状态必须是处理中
		if (order.getOrderStatus() != DataDictionaryUtil.ORDER_STATUS_DISPOSING) {
			throw new LotteryException("订单状态必须是处理中，才可以进行驳回订单的操作！");
		}

		order.setOrderStatus(DataDictionaryUtil.ORDER_STATUS_FAILURE);
		order.setUpdateTime(DateUtil.getNowDate());
		order.setUpdateUser(user.getUserName());
		// 备注信息
		order.setRsvst4(orderVo.getRsvst4());
		// 根据实收金额更新用户的customerCash表。
		CustomerCash customerCash = userCashDao.queryUserCashByCustomerId(order.getCustomerId());
		// 记录用户余额
		order.setAccountBalance(customerCash.getCash());
		orderDao.update(order);
		// 查询一遍相关信息，方便页面重构。
		this.queryOrderUserAndCard(order);
		return order;
	}

	/**
	 * 显示提款订单明细
	 */
	@Override
	public Map<String, Object> showDrawingOrderInfo(Map<String, Object> param) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();

		CustomerOrderVO orderVo = (CustomerOrderVO) param.get("orderKey");
		long orderId = orderVo.getId();
		CustomerOrder order = orderDao.queryById(orderId);
		// 查询关联的用户，用户账号余额，用户绑定的银行卡，银行信息。
		CustomerUser customerUser = userDao.queryById(order.getCustomerId());
		order.setUser(customerUser);

		CustomerCash cash = userCashDao.queryUserCashByCustomerId(customerUser.getId());
		returnMap.put("cash", cash);
		// 用户今日申请提款次数及总金额
		CustomerOrderVO newVo = new CustomerOrderVO();
		newVo.setCustomerId(order.getCustomerId());
		newVo.setOrderType(order.getOrderType());
		newVo.setOrderDetailType(order.getOrderDetailType());
		newVo.setOrderTime(order.getOrderTime());
		// newVo.setOrderStatus(DataDictionaryUtil.ORDER_STATUS_DISPOSING);
		int drawingTimes = orderDao.countDrawingTimesToday(newVo);
		Map<String, String> timesMap = parameterDao
				.queryParameterList("financeConfig", new String[] { "drawingTimes" });
		int times = Integer.parseInt(timesMap.get("drawingTimes"));
		returnMap.put("totalTimes", times);
		returnMap.put("drawingTimes", drawingTimes);
		// 统计今天的提款总金额
		BigDecimal totalAmount = orderDao.sumTotalDrawingAmount(newVo);
		returnMap.put("totalAmount", totalAmount);

		// 用户今日成功提款次数及成功提款总金额
		newVo.setOrderStatus(DataDictionaryUtil.ORDER_STATUS_SUCCESS);
		int successTimes = orderDao.countDrawingTimesToday(newVo);
		returnMap.put("successTimes", successTimes);
		BigDecimal successAmount = orderDao.sumTotalDrawingAmount(newVo);
		returnMap.put("successAmount", successAmount);

		long userCardId = order.getReferenceId();
		UserCard userCard = userCardDao.queryById(userCardId);
		userCard.setCardNo(AesUtil.decrypt(userCard.getCardNo(), Md5Manage.getInstance().getMd5()));
		userCard.setAddress(AesUtil.decrypt(userCard.getAddress(), Md5Manage.getInstance().getMd5()));
		userCard.setBranchBankName(AesUtil.decrypt(userCard.getBranchBankName(), Md5Manage.getInstance().getMd5()));
		userCard.setOpenCardName(AesUtil.decrypt(userCard.getOpenCardName(), Md5Manage.getInstance().getMd5()));
		returnMap.put("userCard", userCard);

		long bankId = userCard.getBankId();
		BankManage bank = bankDao.queryById(bankId);
		order.setBank(bank);

		returnMap.put("order", order);
		return returnMap;
	}

	/**
	 * 显示充值订单明细
	 */
	@Override
	public CustomerOrder showRechargeOrderInfo(Map<String, Object> param) throws Exception {
		CustomerOrderVO orderVo = (CustomerOrderVO) param.get("orderKey");
		long orderId = orderVo.getId();
		CustomerOrder order = orderDao.queryById(orderId);
		// 查询关联的用户，银行卡，银行信息。
		this.queryOrderUserAndCard(order);
		long bankId = order.getCard().getBankId();
		BankManage bank = bankDao.queryById(bankId);
		order.setBank(bank);
		return order;
	}

	private CustomerOrder queryOrderUserAndCard(CustomerOrder order) {
		// 查询一遍相关信息，方便页面重构。
		CustomerUser customerUser = userDao.queryById(order.getCustomerId());
		order.setUser(customerUser);
		if (order.getOrderType() == DataDictionaryUtil.ORDER_TYPE_INCOME) {
			CustomerBankCard card2 = cardDao.queryById(order.getReferenceId());
			order.setCard(card2);
		}
		return order;
	}

	/**
	 * 过期订单重启（订单状态设置为处理中，过期时间调整为当前时间加上配置的过期时间）
	 */
	@Override
	public CustomerOrder updateRestartOrder(Map<String, Object> param) throws Exception {
		AdminUser user = (AdminUser) param.get(CommonUtil.USERKEY);
		CustomerOrderVO orderVo = (CustomerOrderVO) param.get("orderKey");
		long orderId = orderVo.getId();
		CustomerOrder order = orderDao.queryById(orderId);
		// 验证用户输入的财务密码是否正确
		String inputPwd = MD5Util.makeMD5(orderVo.getAdminPwd());
		if (!inputPwd.equals(user.getUserRolePwd())) {
			throw new LotteryException("财务密码输入不正确，请重新输入！");
		}
		// 验证订单状态必须是过期
		if (order.getOrderStatus() != DataDictionaryUtil.ORDER_STATUS_OVERDUE) {
			throw new LotteryException("订单状态必须是已过期，才可以进行通过重启订单的操作！");
		}

		order.setOrderStatus(DataDictionaryUtil.ORDER_STATUS_DISPOSING);
		// 获取配置的过期时间，并重新设置过期时间。
		CustomerBankCard card = cardDao.queryById(order.getReferenceId());
		long bankId = card.getBankId();
		BankManage bank = bankDao.queryById(bankId);
		int cancelMinute = bank.getCancelTime();
		String cancelTimeStr = DateUtil.getPreTime(DateUtil.getStringDate(), String.valueOf(cancelMinute));
		order.setCancelTime(DateUtil.strToDateLong(cancelTimeStr));
		orderDao.update(order);
		return order;
	}

	/**
	 * 审核驳回提款订单（修改订单状态为失败，userCash的冻结金额减少）
	 */
	@Override
	public CustomerOrder saveRejectDrawingOrder(Map<String, Object> param) throws Exception {
		AdminUser user = (AdminUser) param.get(CommonUtil.USERKEY);
		CustomerOrderVO orderVo = (CustomerOrderVO) param.get("orderKey");
		long orderId = orderVo.getId();
		CustomerOrder order = orderDao.queryById(orderId);
		// userCash的冻结金额减少
		long customerId = order.getCustomerId();
		CustomerCash cash = userCashDao.queryUserCashByCustomerId(customerId);
		BigDecimal orignalCash = cash.getFrozenCash();
		BigDecimal newCash = orignalCash.subtract(order.getReceiveAmount());
		cash.setFrozenCash(newCash);
		// 前台减去了金额，更新回正常金额 2014-08-27 by jeff
		cash.setCash(cash.getCash().add(order.getReceiveAmount()));
		userCashDao.update(cash);
		// 修改订单状态为失败
		order.setOrderStatus(DataDictionaryUtil.ORDER_STATUS_FAILURE);
		order.setUpdateTime(DateUtil.getNowDate());
		order.setUpdateUser(user.getUserName());
		// 备注信息
		order.setRsvst4(orderVo.getRsvst4());
		// 记录用户余额
		order.setAccountBalance(cash.getCash());
		orderDao.update(order);
		return order;
	}

	/**
	 * 审核通过提款订单（修改订单状态为成功，userCash的冻结金额减少，实际金额也减少）
	 */
	@Override
	public CustomerOrder saveApproveDrawingOrder(Map<String, Object> param) throws Exception {
		AdminUser user = (AdminUser) param.get(CommonUtil.USERKEY);
		CustomerOrderVO orderVo = (CustomerOrderVO) param.get("orderKey");
		long orderId = orderVo.getId();
		CustomerOrder order = orderDao.queryById(orderId);
		// userCash的冻结金额减少
		long customerId = order.getCustomerId();
		CustomerCash cash = userCashDao.queryUserCashByCustomerId(customerId);
		BigDecimal frozenCash = cash.getFrozenCash();
		BigDecimal newCash = frozenCash.subtract(order.getReceiveAmount());
		cash.setFrozenCash(newCash);
		// 可用金额减少 前台已经减去并更新在冻结金额中 2014/08/27 by jeff
		// BigDecimal cashAmount = cash.getCash();
		// BigDecimal newCashAmount = cashAmount.subtract(order.getReceiveAmount());
		// if(newCashAmount.compareTo(BigDecimal.ZERO)<1){
		// throw new LotteryException("用户余额不足以提款"+order.getReceiveAmount()+"元！");
		// }
		// cash.setCash(newCashAmount);
		userCashDao.update(cash);
		// 修改订单状态为成功
		order.setOrderStatus(DataDictionaryUtil.ORDER_STATUS_SUCCESS);
		order.setUpdateTime(DateUtil.getNowDate());
		order.setUpdateUser(user.getUserName());
		// 备注信息
		order.setRsvst4(orderVo.getRsvst4());
		// 记录用户余额
		order.setAccountBalance(cash.getCash());

		orderDao.update(order);
		return order;
	}

	@Override
	public int countDisposingOrder(Map<String, Object> param) throws Exception {
		CustomerOrderVO orderVo = (CustomerOrderVO) param.get("orderKey");
		orderVo.setOrderStatus(DataDictionaryUtil.ORDER_STATUS_DISPOSING);
		int count = orderDao.countDrawingTimesToday(orderVo);
		return count;
	}

	@Override
	public long getAllOrderCount(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer queryString = new StringBuffer("select count(t.id) from CustomerOrder t ");
		Query query = orderDao.getSession().createQuery(queryString.toString());
		Long orderCount = (Long) query.list().get(0);
		return orderCount;
	}

	@Override
	public Page<CustomerOrderVO, CustomerOrder> queryAllOrderByPage(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		Page<CustomerOrderVO, CustomerOrder> objects = orderDao.queryAllOrderByPage(param);
		List<CustomerOrder> entitys = objects.getEntitylist();
		List<CustomerOrderVO> vos = new ArrayList<CustomerOrderVO>();
		for (CustomerOrder order : entitys) {
			CustomerOrderVO vo = new CustomerOrderVO();
			DozermapperUtil.getInstance().map(order, vo);
			vo.setOrderDetailTypeName(DataDictionaryUtil.getDataString().get((long) vo.getOrderDetailType()));
			CustomerUser user = new CustomerUser();
			DozermapperUtil.getInstance().map(userDao.queryById(vo.getCustomerId()), user);
			String userName = user.getCustomerName();
			vo.setCustomerName(userName);
			vo.setOrderTypeName(DataDictionaryUtil.getDataString().get((long) vo.getOrderType()));
			vo.setOrderStatusName(DataDictionaryUtil.getDataString().get((long) vo.getOrderStatus()));
			vos.add(vo);
		}
		objects.setPagelist(vos);
		return objects;
	}

	@Override
	public CustomerOrderVO queryOrderInfoByType(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		CustomerOrder entity = orderDao.queryOrderInfoByType(param);
		if (entity == null)
			throw new LotteryException("订单异常");
		CustomerOrderVO vo = new CustomerOrderVO();
		BeanPropertiesCopy.copyProperties(entity, vo);
		vo.setOrderDetailTypeName(DataDictionaryUtil.getDataString().get((long) vo.getOrderDetailType()));
		vo.setCustomerName(userDao.queryById(vo.getCustomerId()).getCustomerName());
		if (vo.getFromCustomerId() != null && vo.getFromCustomerId() != 0)
			vo.setCustomerNameOut(userDao.queryById(vo.getFromCustomerId()).getCustomerName());
		vo.setOrderTypeName(DataDictionaryUtil.getDataString().get((long) vo.getOrderType()));
		vo.setOrderStatusName(DataDictionaryUtil.getDataString().get((long) vo.getOrderStatus()));
		return vo;
	}

	/**
	 * 前台订单分页查询
	 */
	@Override
	public Page<CustomerOrderVO, CustomerOrder> queryTeamOrdersByPage(Map<String, Object> param) throws Exception {
		// 如果有用户限制
		List<CustomerUser> users = userDao.quUserByTeam(param);
		if (users.size() > 0) {
			List<Long> ids = new ArrayList<Long>();
			for (CustomerUser user : users) {
				ids.add(user.getId());
			}
			param.put("idsKey", ids);

			Page<CustomerOrderVO, CustomerOrder> orders = orderDao.queryTeamOrdersByPage(param);
			return orders;
		} else {
			return null;
		}

	}

	@Override
	public CustomerOrder queryOrderById(Map<String, Object> param) throws Exception {
		Long orderId = (Long) param.get("ordIdKey");
		CustomerOrder order = orderDao.queryById(orderId);
		String sourceOrderNumber = order.getRsvst1();
		if (!StringUtils.isEmpty(sourceOrderNumber)
				&& order.getOrderDetailType() != DataDictionaryUtil.ORDER_DETAIL_DISTRIBUTED_ACTIVITIES) {
			// 来源订单信息
			CustomerOrder betOrder = orderDao.queryOrderByNum(sourceOrderNumber);
			order.setRsvdc5(betOrder.getOrderAmount());
			order.setRsvst4(betOrder.getCreateUser());
			order.setRsvdc1(betOrder.getId());
		}
		
		return order;
	}
	

	@Override
	public CustomerOrder queryResultOrderById(Map<String, Object> param) throws Exception {
		Long orderId = (Long) param.get("ordIdKey");
		CustomerOrder order = orderDao.queryById(orderId);
		return order;
	}
	

	@Override
	public CustomerOrder queryDrawingOrderById(Map<String, Object> param) throws Exception {
		Long orderId = (Long) param.get("ordIdKey");
		CustomerOrder order = orderDao.queryDrawingOrderById(orderId);
		return order;
	}

	@Override
	public Page<CustomerOrderVO, CustomerOrder> queryMyOrdersByPage(Map<String, Object> param) throws Exception {
		Page<CustomerOrderVO, CustomerOrder> orders = orderDao.queryMyLotteryOrdersByPage(param);
		return orders;
	}

	@Override
	public Page<CustomerOrderVO, CustomerOrder> queryTraceOrders(Map<String, Object> param) throws Exception {
		Page<CustomerOrderVO, CustomerOrder> orders = orderDao.queryTraceOrdersByPage(param);
		return orders;
	}

	@Override
	public Page<CustomerOrderVO, CustomerOrder> queryRevenueLower(Map<String, Object> param) throws Exception {
		Page<CustomerOrderVO, CustomerOrder> orders = orderDao.queryRevenueLower(param);
		return orders;
	}

	@Override
	public List<CustomerOrderVO> queryNewWinningOrder(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		List<LotteryType> types = lotteryTypeDao.queryAll();
		Map<String, String> tyepMap = new HashMap<String, String>();
		for (LotteryType type : types) {
			tyepMap.put(type.getLotteryCode(), type.getLotteryName());
		}
		List<CustomerOrder> entitys = orderDao.queryNewWinningOrder(param);
		if (entitys == null)
			entitys = new ArrayList<CustomerOrder>();
		List<CustomerOrderVO> vos = new ArrayList<CustomerOrderVO>();
		for (CustomerOrder entity : entitys) {
			CustomerOrderVO vo = new CustomerOrderVO();
			DozermapperUtil.getInstance().map(entity, vo);
			CustomerUser user = userDao.queryById(vo.getCustomerId());
			if (user == null)
				continue;
			if (user.getCustomerAlias() == null || user.getCustomerAlias().equals("")) {
				vo.setCustomerName(user.getCustomerName().charAt(0) + "*****");
			} else {
				vo.setCustomerName(user.getCustomerAlias().charAt(0) + "*****");
			}
			vo.setLotteryTypeName(tyepMap.get(vo.getRsvst2()));
			vos.add(vo);
		}
		return vos;
	}

	@Override
	public Map<String, Object> chargeDrawCount(Map<String, Object> param) throws Exception {
		return orderDao.chargeDrawCount(param);
	}

	@Override
	public CustomerOrder saveOtherPayOrder(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		CustomerUser user = (CustomerUser) param.get(CommonUtil.CUSTOMERUSERKEY);
		String orderNumber = sequenceService.getOrderSequence("OO", 8);
		String orderAmount = (String) param.get("orderAmount");
		String payCode = (String) param.get("payCode");
		CustomerOrder order = new CustomerOrder();
		order.setOrderNumber(orderNumber);
		order.setOrderType(DataDictionaryUtil.ORDER_TYPE_INCOME);
		order.setOrderDetailType(DataDictionaryUtil.ORDER_DETAIL_OTHER_PAY);
		order.setOrderTime(DateUtil.getNowDate());
		order.setOrderStatus(DataDictionaryUtil.ORDER_STATUS_DISPOSING);
		order.setOrderAmount(new BigDecimal(orderAmount));
		order.setCashAmount(new BigDecimal(orderAmount));
		order.setReceiveAmount(new BigDecimal(orderAmount));
		order.addInit(user.getCustomerName());
		order.setCustomerId(user.getId());
		order.setRemark(payCode);
		orderDao.save(order);
		userlog.saveWriteLog(user, CommonUtil.SAVE, "t_customer_order", order.toString());
		return order;
	}

	@Override
	public Page<CustomerOrderVO, CustomerOrder> queryActiveOrder(Map<String, Object> param) throws Exception {
		CustomerOrderVO orderVo = (CustomerOrderVO) param.get("customerOrderKey");
		if (null != orderVo.getCustomerName() && !orderVo.getCustomerName().equals("")) {
			List<CustomerUser> userList = userDao.queryUserByName(orderVo);
			if (userList.size() == 0) {
				throw new LotteryException("用户名" + orderVo.getCustomerName() + "对应的用户记录不存在，请确认用户名是否正确！");
			}
			orderVo.setCustomerId(userList.get(0).getId());
		}
		Page<CustomerOrderVO, CustomerOrder> pagelist = orderDao.queryUserOrderByPage(orderVo);
		for (CustomerOrder order : pagelist.getEntitylist()) {
			order.setRsvst3(CommonUtil.actNameMap.get(order.getRsvst3()));
			CustomerUser user = userDao.queryById(order.getCustomerId());
			order.setUser(user);
		}
		return pagelist;
	}

	@Override
	public CustomerOrder saveApproveActivityOrder(Map<String, Object> param) throws Exception {
		AdminUser user = (AdminUser) param.get(CommonUtil.USERKEY);
		CustomerOrderVO orderVo = (CustomerOrderVO) param.get("orderKey");
		long orderId = orderVo.getId();
		CustomerOrder order = orderDao.queryById(orderId);
		// 验证订单状态必须是处理中
		if (order.getOrderStatus() != DataDictionaryUtil.ORDER_STATUS_DISPOSING) {
			throw new LotteryException("订单状态必须是处理中，才可以进行通过审核的操作！");
		}
		order.setOrderStatus(DataDictionaryUtil.ORDER_STATUS_SUCCESS);
		order.setUpdateTime(DateUtil.getNowDate());
		order.setUpdateUser(user.getUserName());
		// 根据实收金额更新用户的customerCash表。
		long customerId = order.getCustomerId();
		CustomerCash customerCash = userCashDao.queryUserCashByCustomerId(customerId);
		BigDecimal cash = customerCash.getCash().add(order.getReceiveAmount());
		customerCash.setCash(cash);
		userCashDao.update(customerCash);
		// 记录用户余额
		order.setAccountBalance(customerCash.getCash());
		orderDao.update(order);
		return order;
	}

	@Override
	public CustomerOrder saveRejectActivityOrder(Map<String, Object> param) throws Exception {
		AdminUser user = (AdminUser) param.get(CommonUtil.USERKEY);
		CustomerOrderVO orderVo = (CustomerOrderVO) param.get("orderKey");
		long orderId = orderVo.getId();
		CustomerOrder order = orderDao.queryById(orderId);
		// 验证订单状态必须是处理中
		if (order.getOrderStatus() != DataDictionaryUtil.ORDER_STATUS_DISPOSING) {
			throw new LotteryException("订单状态必须是处理中，才可以进行通过审核的操作！");
		}
		order.setOrderStatus(DataDictionaryUtil.ORDER_STATUS_FAILURE);
		order.setUpdateTime(DateUtil.getNowDate());
		order.setUpdateUser(user.getUserName());
		// 活动申请驳回原因
		order.setRsvst5(orderVo.getRsvst5());
		// 根据实收金额更新用户的customerCash表。
		long customerId = order.getCustomerId();
		CustomerCash customerCash = userCashDao.queryUserCashByCustomerId(customerId);

		// 记录用户余额
		order.setAccountBalance(customerCash.getCash());

		orderDao.update(order);

		return order;
	}

	@Override
	public String updateOtherPayOrderSuccess(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		String msg = "";
		String callBackType = (String) param.get("callBackType");
		GenericEntityVO payvo = (GenericEntityVO) param.get("payvo");
		if (callBackType.equals(CommonUtil.PAYCODE_YB)) {
			msg = this.otherPayYB(payvo);
		} else {
			msg = this.otherPayHx(payvo);
		}
		return msg;
	}

	private String otherPayHx(GenericEntityVO gevo) throws Exception {
		OrtherPayVO payvo = (OrtherPayVO) gevo;
		if (payvo.getBillno() == null || payvo.getBillno().equals("")) {
			throw new LotteryException("充值信息异常,请联系客户");
		}
		String signature = payvo.getSignature();
		String tempStr = "billno"
				+ payvo.getBillno().concat("currencytype").concat(payvo.getCurrency_type()).concat("amount")
						.concat(payvo.getAmount()).concat("date").concat(payvo.getDate()).concat("succ")
						.concat(payvo.getSucc()).concat("ipsbillno").concat(payvo.getIpsbillno())
						.concat("retencodetype").concat(payvo.getRetencodetype());

		String checkStr = MD5Util.makeMD5(tempStr + CommonUtil.hxKeyMap.get(payvo.getMercode()));
		if (signature.equals(checkStr)) {
			CustomerOrder order = orderDao.queryOrderByNum(payvo.getBillno());
			if (order.getOrderStatus() == DataDictionaryUtil.ORDER_STATUS_SUCCESS) {
				return "充值成功";
			}
			order.setCashAmount(new BigDecimal(payvo.getAmount()));
			order.setOrderAmount(new BigDecimal(payvo.getAmount()));
			order.setRemark(order.getRemark() + ":" + payvo.getIpsbillno());
			CustomerUser user = userDao.queryUserByName(payvo.getAttach());
			if (order.getCustomerId() == user.getId()) {
				CustomerCash cash = userCashDao.queryUserCashByCustomerId(user.getId());
				cash.setCash(cash.getCash().add(new BigDecimal(payvo.getAmount())));
				cash.updateInit(user.getCustomerName());
				userCashDao.update(cash);
				order.setOrderStatus(DataDictionaryUtil.ORDER_STATUS_SUCCESS);
				order.updateInit(user.getCustomerName());
				order.setAccountBalance(cash.getCash());
				orderDao.update(order);

				// 站内信
				Map<String, Object> param = new HashMap<String, Object>();
				CustomerMessageVO msgVo = new CustomerMessageVO();
				msgVo.setToUserId(order.getCustomerId());
				msgVo.setTitle("充值成功");
				String msg = "亲，您的第三方充值订单[" + order.getOrderNumber() + "]已经成功，请查看游戏账户余额是否已经增加 "
						+ order.getReceiveAmount() + " 元。 \n如有任何问题，可咨询客服，祝您游戏愉快！";
				msgVo.setMessage(msg);
				msgVo.setMsgType("0");
				param.put("msgVO", msgVo);
				msgService.saveMsg(param);
			} else {
				throw new LotteryException("充值信息异常,请联系客户");
			}
		} else {
			throw new LotteryException("充值信息异常,请联系客户");
		}
		return "充值成功";
	}

	private String otherPayYB(GenericEntityVO gevo) throws Exception {
		OrtherYBPayVO payvo = (OrtherYBPayVO) gevo;
		if (payvo.getR6_Order() == null || payvo.getR6_Order().equals("")) {
			throw new LotteryException("充值信息异常,请联系客户");
		}
		if (EncryUtil.verifyCallback(payvo.getHmac(), payvo.toDate(), CommonUtil.ybKeyMap.get(payvo.getP1_MerId()))) {
			CustomerOrder order = orderDao.queryOrderByNum(payvo.getR6_Order());
			if (order.getOrderStatus() == DataDictionaryUtil.ORDER_STATUS_SUCCESS) {
				return "充值成功";
			}
			order.setCashAmount(new BigDecimal(payvo.getR3_Amt()));
			order.setOrderAmount(new BigDecimal(payvo.getR3_Amt()));
			order.setRemark(order.getRemark() + ":" + payvo.getR2_TrxId());
			CustomerUser user = null;
			if (payvo.getR7_Uid() != null && !payvo.getR7_Uid().equals("")) {
				user = userDao.queryUserByName(payvo.getR7_Uid());
			} else {
				user = userDao.queryById(order.getCustomerId());
			}
			if (order.getCustomerId() == user.getId()) {
				CustomerCash cash = userCashDao.queryUserCashByCustomerId(user.getId());
				cash.setCash(cash.getCash().add(new BigDecimal(payvo.getR3_Amt())));
				cash.updateInit(user.getCustomerName());
				userCashDao.update(cash);
				order.setOrderStatus(DataDictionaryUtil.ORDER_STATUS_SUCCESS);
				order.updateInit(user.getCustomerName());
				order.setAccountBalance(cash.getCash());
				orderDao.update(order);

				// 站内信
				Map<String, Object> param = new HashMap<String, Object>();
				CustomerMessageVO msgVo = new CustomerMessageVO();
				msgVo.setToUserId(order.getCustomerId());
				msgVo.setTitle("充值成功");
				String msg = "亲，您的第三方充值订单[" + order.getOrderNumber() + "]已经成功，请查看游戏账户余额是否已经增加 "
						+ order.getReceiveAmount() + " 元。 \n如有任何问题，可咨询客服，祝您游戏愉快！";
				msgVo.setMessage(msg);
				msgVo.setMsgType("0");
				param.put("msgVO", msgVo);
				msgService.saveMsg(param);
			} else {
				throw new LotteryException("充值信息异常,请联系客户");
			}
		} else {
			throw new LotteryException("充值信息异常,请联系客户");
		}
		return "充值成功";
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean checkCustomerIsSafe(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		CustomerUser user = (CustomerUser) param.get(CommonUtil.CUSTOMERUSERKEY);
		Integer rechargeCount = Integer.parseInt(param.get("rechargeCount").toString());
		Integer rechargeMoney = Integer.parseInt(param.get("rechargeMoney").toString());
		StringBuffer sql = new StringBuffer(
				"SELECT COUNT(1),IFNULL(SUM(t.order_amount),0) FROM t_customer_order t WHERE t.customer_id = ?");
		sql.append(" AND t.`order_status` = 17002 and t.`order_type` = 14001 AND t.`order_detail_type` = 18019  ");
		Query query = orderDao.getSession().createSQLQuery(sql.toString());
		// query.setParameter(0, CommonUtil.PAYCODE_HX);
		query.setParameter(0, user.getId());
		List<Object[]> objs = query.list();
		if (objs.size() == 0)
			return false;
		Integer count = Integer.parseInt(objs.get(0)[0].toString());
		Long money = new BigDecimal(objs.get(0)[1] + "").longValue();
		if (count < rechargeCount && money < rechargeMoney) {
			return false;
		}
		return true;
	}

	@Override
	public int countOtherPlayTimes(Map<String, Object> param) throws Exception {
		CustomerUser user = (CustomerUser) param.get(CommonUtil.CUSTOMERUSERKEY);

		StringBuffer sql = new StringBuffer("SELECT COUNT(1) FROM t_customer_order t WHERE t.customer_id = :uid ");
		sql.append(" AND t.`order_status` = :sts and t.`order_type` = :ot AND t.`order_detail_type` in (:odts) "
				+ " GROUP BY t.`remark` ");
		Query query = orderDao.getSession().createSQLQuery(sql.toString());
		query.setParameter("uid", user.getId());
		query.setParameter("sts", DataDictionaryUtil.ORDER_STATUS_SUCCESS);
		query.setParameter("ot", DataDictionaryUtil.ORDER_TYPE_INCOME);

		List<Integer> odts = new ArrayList<Integer>();
		odts.add(DataDictionaryUtil.ORDER_DETAIL_OTHER_PAY);
		if (param.get("cashRechargeKey") != null) {
			odts.add(DataDictionaryUtil.ORDER_DETAIL_CASH_ADD);
		}
		query.setParameterList("odts", odts.toArray());

		List<BigInteger> objs = query.list();
		Integer count = 0;
		if (null == objs || objs.size() == 0) {
			return 0;
		} else {
			count = objs.get(0).intValue();
		}
		return count;
	}

	@Override
	public List<CustomerOrderStaVo> queryDayYkRecords(Map<String, Object> param) throws Exception {
		return orderDao.queryDayYkRecords(param);
	}

	@Override
	public List<CustomerOrderStaVo> queryHistoryYkRecords(Map<String, Object> param) throws Exception {
		// 最多查询31天记录
		if(Math.abs(DateUtil.getTwoDay(param.get("sdate").toString(),param.get("edate").toString()))>31){
			param.put("sdate", DateUtil.getNextDay(param.get("edate").toString(), "-31")+" 04:00:00");
		}
		return  orderDao.queryHistoryYkRecords(param);
	}

	@Override
	public List<CustomerOrderStaVo> queryHistoryYkExport(Map<String, Object> param) throws Exception {
		if(Math.abs(DateUtil.getTwoDay(param.get("sdate").toString(),param.get("edate").toString()))>31){
			param.put("sdate", DateUtil.getNextDay(param.get("edate").toString(), "-31")+" 04:00:00");
		}
		List<CustomerOrderStaVo> results = new ArrayList<CustomerOrderStaVo>();
		List<CustomerOrderStaVo> origresults = orderDao.queryHistoryYkRecords(param);
		long days = Math.abs(DateUtil.getTwoDay(param.get("sdate").toString(),param.get("edate").toString()));
		Map<String,CustomerOrderStaVo> resultMap = new TreeMap<String, CustomerOrderStaVo>();
		Date date = DateUtil.strToDateLong(param.get("sdate").toString());
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		CustomerOrderStaVo vo = null;
		String day ="";
		for(int i=0;i<days;i++){
			vo = new CustomerOrderStaVo();
			vo.setCreateTime(cal.getTime());
			if(cal.get(Calendar.DATE)<10){
				day="0"+cal.get(Calendar.DATE);
			}else{
				day=String.valueOf(cal.get(Calendar.DATE));
			}
			resultMap.put(cal.get(Calendar.MONTH)+1+"-"+day,vo);
			cal.add(Calendar.DATE, 1);
		}
		for(CustomerOrderStaVo order : origresults){
			 resultMap.put(order.getDay(), order);
		}
		Iterator<Entry<String, CustomerOrderStaVo>> iter =resultMap.entrySet().iterator();
		while(iter.hasNext()){
			Entry<String, CustomerOrderStaVo>  entry = iter.next();
			results.add(entry.getValue());
		}
		return results;
 	}
}