package com.lottery.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwx.lottery.form.vo.LowerManagerVO;
import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.CustomerBindCard;
import com.lottery.bean.entity.CustomerCash;
import com.lottery.bean.entity.CustomerIntegral;
import com.lottery.bean.entity.CustomerQuota;
import com.lottery.bean.entity.CustomerUrl;
import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.SecurityQuestion;
import com.lottery.bean.entity.UserBonusGroup;
import com.lottery.bean.entity.UserCard;
import com.lottery.bean.entity.vo.CustomerOrderVO;
import com.lottery.bean.entity.vo.CustomerUserVO;
import com.lottery.bean.entity.vo.UserCardVO;
import com.lottery.dao.IAdminParameterDao;
import com.lottery.dao.ICustomerBindCardDao;
import com.lottery.dao.ICustomerCashDao;
import com.lottery.dao.ICustomerIntegralDao;
import com.lottery.dao.ICustomerIpLogDao;
import com.lottery.dao.ICustomerOrderDao;
import com.lottery.dao.ICustomerQuotaDao;
import com.lottery.dao.ICustomerUrlDao;
import com.lottery.dao.ICustomerUserDao;
import com.lottery.dao.IOrderSequenceDao;
import com.lottery.dao.ISecurityQuestionDao;
import com.lottery.dao.IUserBonusGroupDao;
import com.lottery.dao.IUserCardDao;
import com.lottery.dao.impl.AdminWriteLog;
import com.lottery.dao.impl.CustomerUserWriteLog;
import com.lottery.service.ICustomerUserService;
import com.xl.lottery.desutil.AesUtil;
import com.xl.lottery.desutil.Md5Manage;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;
import com.xl.lottery.util.DateUtil;
import com.xl.lottery.util.MD5Util;
import com.xl.lottery.util.Util;

@Service
public class CustomerUserServiceImpl implements ICustomerUserService {

	@Autowired
	private ICustomerUserDao customerUserDao;

	@Autowired
	private ICustomerUrlDao customerUrlDao;

	@Autowired
	private ICustomerBindCardDao customerBindCardDao;

	@Autowired
	private ICustomerQuotaDao customerQuotaDao;

	@Autowired
	private ICustomerCashDao customerCashDao;

	@Autowired
	private AdminWriteLog adminWriteLog;

	@Autowired
	private CustomerUserWriteLog customerUserWriteLog;

	@Autowired
	private IUserBonusGroupDao userBonusGroupDao;

	@Autowired
	private IUserCardDao userCardDao;
	
	@Autowired
	private ISecurityQuestionDao securityQuestionDao;

	@Autowired
	private IAdminParameterDao adminParameterDao;
	
	@Autowired
	private ICustomerIntegralDao integralDao;
	
	@Autowired
	private ICustomerIpLogDao ipLogDao;
	
	@Autowired
	private ICustomerOrderDao orderDao;
	
	@Autowired
	private IOrderSequenceDao orderSequenceDao;
	
	@Autowired
	private IAdminParameterDao parameterDao;
	
	@Override
	public String saveMainCustomer(Map<String, ?> param) throws Exception {
		// TODO Auto-generated method stub
		CustomerUserVO vo = (CustomerUserVO) param.get("customeruserkey");
		AdminUser user = (AdminUser) param.get(CommonUtil.USERKEY);
		if (checkUserName(param)) {
			CustomerUser entity = CustomerUserVoToEntity(vo, user);
			entity.setActiveLevel(1);
			// 持久化用户
			customerUserDao.insert(entity);
			adminWriteLog.saveWriteLog(user, CommonUtil.SAVE, "CustomerUser",
					entity.toString());
			// 持久化用户配额
			List<CustomerQuota> quotas = customerQuotaVOToEntity(vo.getQuotas()
					.split(";"), user, entity.getId());
			for (CustomerQuota cq : quotas) {
				customerQuotaDao.insert(cq);
				adminWriteLog.saveWriteLog(user, CommonUtil.SAVE,
						"CustomerQuota", "insert into t_customer_quota ");
			}
			// 持久化用户银行卡
			List<CustomerBindCard> cards = customerBindCardVOToEntity(vo
					.getCards().split(";"), user, entity.getId());
			for (CustomerBindCard cbcard : cards) {
				customerBindCardDao.insert(cbcard);
				adminWriteLog
						.saveWriteLog(user, CommonUtil.SAVE,
								"CustomerBindCard",
								"insert into t_customer_bind_card ");
			}
			// 持久化用户URL
			List<CustomerUrl> urls = customerUrlVOToEntity(
					vo.getUrls().split(";"), user, entity.getId());
			for (CustomerUrl url : urls) {
				customerUrlDao.insert(url);
				adminWriteLog.saveWriteLog(user, CommonUtil.SAVE,
						"CustomerUrl", "insert into t_customer_url ");
			}
			// 初始化钱包
			CustomerCash cash = customerCashVOToEntity(user, entity.getId());
			customerCashDao.insert(cash);
			adminWriteLog.saveWriteLog(user, CommonUtil.SAVE, "CustomerCash",
					"insert into t_customer_cash ");
			// 持久化用户和奖金组关系
			UserBonusGroup userBonusGroup = cserBonusGroupVoToEntity(vo, user,
					entity.getId());
			userBonusGroupDao.insert(userBonusGroup);
			adminWriteLog.saveWriteLog(user, CommonUtil.SAVE, "UserBonusGroup",
					"insert into t_user_bonus_group ");
			//开启积分系统
			CustomerIntegral integral = new CustomerIntegral();
			integral.setCustomerId(entity.getId());
			integral.setIntegral(0);
			integral.setLevel(1);
			integral.addInit(user.getUserName());
			integralDao.save(integral);
			return "success";
		} else {
			throw new LotteryException("用户名已存在");
		}
	}

	private UserBonusGroup cserBonusGroupVoToEntity(CustomerUserVO vo,
			AdminUser admin, long userId) {
		UserBonusGroup entity = new UserBonusGroup();
		entity.setBid(Long.parseLong(vo.getPoint().split(",")[0]));
		entity.setCuid(userId);
		entity.setStatus(DataDictionaryUtil.STATUS_OPEN);
		entity.setCreateTime(DateUtil.getNowDate());
		entity.setCreateUser(admin.getUserName());
		entity.setUpdateTime(DateUtil.getNowDate());
		entity.setUpdateUser(admin.getUserName());
		return entity;
	}

	/**
	 * 后台用户开设总代时
	 * 
	 * @param admin
	 * @param userId
	 * @return
	 */
	private CustomerCash customerCashVOToEntity(AdminUser admin, long userId) {
		CustomerCash user = new CustomerCash();
		user.setCustomerId(userId);
		user.setCash(new BigDecimal(0));
		user.setCashStatus(DataDictionaryUtil.STATUS_OPEN);
		user.setCreateTime(DateUtil.getNowDate());
		user.setCreateUser(admin.getUserName());
		user.setUpdateTime(DateUtil.getNowDate());
		user.setUpdateUser(admin.getUserName());
		user.setFrozenCash(new BigDecimal(0));
		return user;
	}

	/**
	 * 前台用户开设下级
	 * 
	 * @param admin
	 * @param userId
	 * @return
	 */
	private CustomerCash customerCashVOToEntity(CustomerUser admin, long userId) {
		CustomerCash user = new CustomerCash();
		user.setCustomerId(userId);
		user.setCash(new BigDecimal(0));
		user.setCashStatus(DataDictionaryUtil.STATUS_OPEN);
		user.setCreateTime(DateUtil.getNowDate());
		user.setCreateUser(admin.getCustomerName());
		user.setUpdateTime(DateUtil.getNowDate());
		user.setUpdateUser(admin.getCustomerName());
		user.setCash(new BigDecimal(0));
		user.setFrozenCash(new BigDecimal(0));
		return user;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	private boolean checkUserName(Map<String, ?> param) {
		CustomerUserVO vo = (CustomerUserVO) param.get("customeruserkey");
		Query query = customerUserDao
				.getSession()
				.createQuery(
						" from CustomerUser t where t.customerName = ? and t.customerStatus = ? ");
		query.setParameter(0, vo.getCustomerName());
		query.setParameter(1, DataDictionaryUtil.STATUS_OPEN);
		List<CustomerUser> list = query.list();
		if (list == null || list.size() == 0)
			return true;
		return false;
	}

	private CustomerUser CustomerUserVoToEntity(CustomerUserVO vo,
			AdminUser admin) {
		CustomerUser user = new CustomerUser();
		user.setCustomerName(vo.getCustomerName());
		user.setCustomerPwd(MD5Util.makeMD5(vo.getCustomerPwd()));
		user.setCustomerStatus(DataDictionaryUtil.STATUS_OPEN);
		user.setCustomerOnlineStatus(DataDictionaryUtil.STATUS_ONLINE_NO);
		user.setCustomerSuperior(0);
		user.setCustomerLevel(0);
		user.setCustomerError(0);
		user.setCreateTime(DateUtil.getNowDate());
		user.setCreateUser(admin.getUserName());
		//开设注册总代时标记
		if(vo.getCreateUser()!=null&&!vo.getCreateUser().equals("")){
			user.setCreateUser(vo.getCreateUser());
		}
		user.setUpdateTime(DateUtil.getNowDate());
		user.setUpdateUser(admin.getUserName());
		if(vo.getCustomerType() != 0){
			user.setCustomerType(DataDictionaryUtil.CUSTOMER_TYPE_TEST);
		}else{
			user.setCustomerType(DataDictionaryUtil.CUSTOMER_TYPE_PROXY);
		}
		
		user.setAllParentAccount("");
		if(StringUtils.isNotEmpty(vo.getStartQuota())){
			user.setRebates(new BigDecimal(vo.getStartQuota()).divide(new BigDecimal(100)));
		}else{
			user.setRebates(new BigDecimal(vo.getPoint().split(",")[1]));
		}
		return user;
	}

	private List<CustomerQuota> customerQuotaVOToEntity(String[] quotas,
			AdminUser admin, long userId) {
		List<CustomerQuota> list = new ArrayList<CustomerQuota>();
		for (int i = 0; i < quotas.length; i++) {
			CustomerQuota entity = new CustomerQuota();
			entity.setCustomerId(userId);
			entity.setProportion(new BigDecimal(quotas[i].split(":")[0]));
			entity.setQuota_count(Integer.parseInt(quotas[i].split(":")[1]));
			entity.setStatus(DataDictionaryUtil.STATUS_OPEN);
			entity.setCreateTime(DateUtil.getNowDate());
			entity.setCreateUser(admin.getUserName());
			entity.setUpdateTime(DateUtil.getNowDate());
			entity.setUpdateUser(admin.getUserName());
			list.add(entity);
		}
		return list;
	}

	private List<CustomerBindCard> customerBindCardVOToEntity(String[] cards,
			AdminUser admin, long userId) {
		List<CustomerBindCard> list = new ArrayList<CustomerBindCard>();
		for (int i = 0; i < cards.length; i++) {
			CustomerBindCard entity = new CustomerBindCard();
			entity.setCustomerId(userId);
			entity.setBankcardId(Long.parseLong(cards[i].split(":")[0]));
			entity.setStatus(DataDictionaryUtil.STATUS_OPEN);
			entity.setExtendsStatus(Integer.parseInt(cards[i].split(":")[1]));
			entity.setCreateTime(DateUtil.getNowDate());
			entity.setCreateUser(admin.getUserName());
			entity.setUpdateTime(DateUtil.getNowDate());
			entity.setUpdateUser(admin.getUserName());
			list.add(entity);
		}
		return list;
	}

	private List<CustomerUrl> customerUrlVOToEntity(String[] urls,
			AdminUser admin, long userId) {
		List<CustomerUrl> list = new ArrayList<CustomerUrl>();
		for (int i = 0; i < urls.length; i++) {
			CustomerUrl entity = new CustomerUrl();
			entity.setCustomerId(userId);
			entity.setUrlid(Long.parseLong(urls[i]));
			entity.setUrlStatus(DataDictionaryUtil.STATUS_OPEN);
			entity.setCreateTime(DateUtil.getNowDate());
			entity.setCreateUser(admin.getUserName());
			entity.setUpdateTime(DateUtil.getNowDate());
			entity.setUpdateUser(admin.getUserName());
			list.add(entity);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CustomerUser checkCustomerUser(Map<String, ?> param)
			throws Exception {
		// TODO Auto-generated method stub
		CustomerOrderVO vo = (CustomerOrderVO) param.get("customerOrder");
		List<CustomerUser> list = customerUserDao.queryUserByName(vo);
		if (list.size() == 0) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public CustomerUser loginCheckCustomerUser(Map<String, ?> param)
			throws Exception {
		// TODO Auto-generated method stub
		CustomerUserVO vo = (CustomerUserVO) param.get("customeruserkey");
		Query query = customerUserDao
				.getSession()
				.createQuery(
						" from CustomerUser t where t.customerName = ? and t.customerPwd=? and t.customerError<=3");
		query.setParameter(0, vo.getCustomerName());
		query.setParameter(1, MD5Util.makeMD5(vo.getCustomerPwd()));
		List<CustomerUser> entityList = query.list();
		if (entityList.size() == 0) {
			return null;
		}
		return entityList.get(0);
	}

	/**
	 * 用户密码重置
	 */
	@Override
	public CustomerUser saveCustomerUserPwd(Map<String, ?> param) throws Exception {
		CustomerUserVO vo = (CustomerUserVO) param.get("customeruserkey");
		CustomerUser sessionUser = (CustomerUser) param.get(CommonUtil.CUSTOMERUSERKEY);
		String type = (String) param.get("operationType");
		CustomerUser user = customerUserDao.queryById(vo.getId());
		String userpwd = MD5Util.makeMD5(vo.getCustomerPwd());
		if (!userpwd.equals(user.getCustomerPwd())) {
			throw new LotteryException("旧密码输入不正确！");
		} else if (!vo.getNewPwd().equals(vo.getConfirmPwd())) {
			throw new LotteryException("两次新密码输入不一致！");
		}
		String newPassword = MD5Util.makeMD5(vo.getNewPwd());
		user.setCustomerPwd(newPassword);
		if(type==null){
			//user.setCustomerOnlineStatus(DataDictionaryUtil.STATUS_ONLINE_ON);
		}
		user.setUpdateTime(DateUtil.getNowDate());
		user.setUpdateUser(user.getCustomerName());
		customerUserDao.update(user);
		
		if(type==null){
			customerUserWriteLog.saveWriteLog(user, CommonUtil.SAVE,
					"saveCustomerUserPwd",
					user.toString());
		}else {
			customerUserWriteLog.saveWriteLog(user, CommonUtil.SAVE,type,user.toString());
		}
		ipLogDao.saveIPlog(sessionUser, "修改登录密码");
		return user;
	}

	@Override
	public CustomerUser saveCustomerUserMoneyPwd(Map<String, Object> param)
			throws Exception {
		// 保存用户资金密码
		CustomerUserVO vo = (CustomerUserVO) param.get("customeruserkey");
		CustomerUser sessionUser = (CustomerUser) param.get(CommonUtil.CUSTOMERUSERKEY);
		String type = (String) param.get("operationType");
		CustomerUser user = customerUserDao.queryById(vo.getId());
		//如果类型为空，则是邀请注册的时候的保存资金密码，如果不为空则必须是账户安全的修改资金密码功能。
		if(type==null||type.equals("modifyMoneyPwd")){
			
			if (type==null&&!vo.getNewPwd().equals(vo.getConfirmPwd())) {
				throw new LotteryException("两次密码输入不一致！");
			}else if (type=="modifyMoneyPwd"){
				long questionId = vo.getQuestionId();
				SecurityQuestion que = securityQuestionDao.queryById(questionId);
				String inputAnswer = MD5Util.makeMD5(vo.getAnswer());
				if(!que.getAnswer().equals(inputAnswer)){
					throw new LotteryException("安全问题答案不正确！");
				}
				if(!user.getMoneyPwd().equals(MD5Util.makeMD5(vo.getMoneyPwd()))){
					throw new LotteryException("旧密码输入错误！");
				}
				if(!vo.getNewPwd().equals(vo.getConfirmPwd())){
					throw new LotteryException("两次密码输入不一致！");
				}
			}
			String moneyPwd = MD5Util.makeMD5(vo.getNewPwd());
			user.setMoneyPwd(moneyPwd);
			user.setUpdateTime(DateUtil.getNowDate());
			user.setUpdateUser(user.getCustomerName());
			customerUserDao.update(user);
			if(null==type){
				customerUserWriteLog.saveWriteLog(user, CommonUtil.SAVE,
						"saveCustomerUserMoneyPwd",
						user.toString());
			}else{
				customerUserWriteLog.saveWriteLog(user, CommonUtil.SAVE,
						type,user.toString());
			}
		}
		
		//如果securityVo不为空，则是邀请注册的时候的保存资金密码，如果为空则必须是账户安全的设置安全问题功能。
		// 保存用户安全问题
		if(vo.getQuestion()!=null){
			SecurityQuestion security = new SecurityQuestion();
			security.setQuestion(vo.getQuestion());
			security.setAnswer(MD5Util.makeMD5(vo.getAnswer()));
			security.setCreateTime(DateUtil.getNowDate());
			security.setUpdateTime(DateUtil.getNowDate());
			security.setCreateUser(user.getCustomerName());
			security.setUpdateUser(user.getCustomerName());
			security.setUserId(user.getId());
			security.setStatus(DataDictionaryUtil.STATUS_OPEN);
			securityQuestionDao.insert(security);
			customerUserWriteLog.saveWriteLog(user, CommonUtil.SAVE,
						"saveCustomerUserMoneyPwd",
						"saveCustomerUserMoneyPwd insert t_security_question ");
			
		}
		
		ipLogDao.saveIPlog(sessionUser, "修改资金密码");
		return user;
	}

	@Override
	public CustomerUser savePersonInfo(Map<String, Object> param) throws Exception {
		CustomerUserVO vo = (CustomerUserVO) param.get("customeruserkey");
		CustomerUser user = customerUserDao.queryById(vo.getId());
		user.setCustomerAlias(vo.getCustomerAlias());
		user.setQq(vo.getQq());
		user.setEmail(vo.getEmail());
		user.setUpdateTime(DateUtil.getNowDate());
		user.setUpdateUser(user.getCustomerName());
		//更新用户的激活状态，即下次登录后不会再要重新设置密码。
		user.setCustomerOnlineStatus(DataDictionaryUtil.STATUS_ONLINE_ON);
		customerUserDao.update(user);
		customerUserWriteLog
				.saveWriteLog(user, CommonUtil.SAVE, "savePersonInfo",
						user.toString());
		return user;
	}

	@Override
	public boolean updateCusomerUserPwd(Map<String, ?> param) throws Exception {
		// TODO Auto-generated method stub
		CustomerUser user = (CustomerUser) param
				.get(CommonUtil.CUSTOMERUSERKEY);
		user.setUpdateTime(DateUtil.getNow());
		user.setUpdateUser("admin"); // 找回密码用户没用登录，默认为admin操作修改
		try {
			customerUserDao.update(user);
			customerUserWriteLog.saveWriteLog(user, CommonUtil.UPDATE,
					"t_customer_user", user.toString());
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}

	@Override
	public CustomerUser findCustomerUserByName(Map<String, ?> param)
			throws Exception {
		CustomerUserVO vo = (CustomerUserVO) param
				.get(CommonUtil.CUSTOMERUSERKEY);
		Query query = customerUserDao
				.getSession()
				.createQuery(
						" from CustomerUser t where t.customerName = ? and t.customerStatus = ? ");
		query.setParameter(0, vo.getCustomerName());
		query.setParameter(1, DataDictionaryUtil.STATUS_OPEN);
		List<CustomerUser> list = query.list();
		if (list == null || list.size() == 0)
			return null;
		return list.get(0);
	}

	@Override
	public void saveUserCard(Map<String, Object> param) throws Exception {
		CustomerUser user = (CustomerUser) param
				.get(CommonUtil.CUSTOMERUSERKEY);
		UserCardVO cardVo = (UserCardVO) param.get("userCard");
		//检查绑卡的卡号是否已经存在
		customerUserDao.checkCardNoIsExist(param);
		
		UserCard userCard = new UserCard();
		userCard.setCreateTime(DateUtil.getNowDate());
		userCard.setUpdateTime(DateUtil.getNowDate());
		userCard.setCreateUser(user.getCustomerName());
		userCard.setUpdateUser(user.getCustomerName());
		userCard.setStatus(DataDictionaryUtil.STATUS_OPEN);
		userCard.setCustomerId(cardVo.getCustomerId());
		userCard.setAddress(AesUtil.encrypt(cardVo.getAddress(), Md5Manage.getInstance().getMd5()));
		userCard.setCardNo(AesUtil.encrypt(cardVo.getCardNo(), Md5Manage.getInstance().getMd5()));
		userCard.setBranchBankName(AesUtil.encrypt(cardVo.getBranchBankName(), Md5Manage.getInstance().getMd5()));
		userCard.setBankId(cardVo.getBankId());
		userCard.setOpenCardName(AesUtil.encrypt(cardVo.getOpenCardName(), Md5Manage.getInstance().getMd5()));
		userCardDao.insert(userCard);

		customerUserWriteLog.saveWriteLog(user, CommonUtil.SAVE, "t_user_card",
				userCard.toString());
		ipLogDao.saveIPlog(user, "绑定银行卡");
	}

	@Override
	public String saveOpenLowerUser(Map<String, ?> param) throws Exception {
		// TODO Auto-generated method stub
		CustomerUserVO vo = (CustomerUserVO) param.get("customeruserkey");
		CustomerUser user = (CustomerUser) param
				.get(CommonUtil.CUSTOMERUSERKEY);
		if(vo.getCustomerType() == 12001){
			return openProxy(vo,user,param);
		}else{
			return openMember(vo,user,param);
		}
	
	}
	
	private String openProxy(CustomerUserVO vo,CustomerUser user,Map<String, ?> param) throws Exception{
		if (vo.getQuotaId() > 0) { // 判断如果返点比小于最低返点比则可以无限制开户
			// 更新配额剩余数量
			CustomerQuota quota = customerQuotaDao.queryById(vo.getQuotaId());
			vo.setRebates(quota.getProportion());
			vo.setMinRebates(quota.getProportion());
			if (quota.getQuota_count() - 1 < 0) {
				throw new LotteryException("剩余配额数量不足");
			}
			quota.setQuota_count(quota.getQuota_count() - 1);
			customerQuotaDao.update(quota);
			customerUserWriteLog.saveWriteLog(user, CommonUtil.UPDATE,
					"CustomerQuota", quota.toString());
		}else{
			vo.setRebates(vo.getMinRebates());
		}
		if(vo.getRebates()!=null){
			if (vo.getRebates().floatValue() > user.getRebates().floatValue()||vo.getMinRebates().floatValue()>user.getRebates().floatValue()) {
				throw new LotteryException("返点比不能大于自己的返点比");
			}
		}
		String mix = adminParameterDao.getParameterByNameAndIndex("userConfig", 1, "minPoint");
		BigDecimal mixReb = new BigDecimal(Double.parseDouble(mix)/100);
		if (vo.getQuotaId() == 0 && vo.getMinRebates().compareTo(mixReb)>=0) {
			String rb = (vo.getMinRebates().multiply(new BigDecimal("100"))).toString();
			rb = Util.trimZero(rb);
			throw new LotteryException("亲，您链接开户的用户返点为 "+rb+"%，不能大于最小返点 "+mix+"%");
		}
		if (checkUserName(param)) {
			CustomerUser entity = openLowerCustomerUser(vo, user);
			if(vo.getQuotaId() == 0)entity.setRebates(vo.getMinRebates());
			// 持久化用户
			customerUserDao.insert(entity);
			customerUserWriteLog.saveWriteLog(user, CommonUtil.SAVE,
					"CustomerUser", entity.toString());
			// 持久化用户银行卡
			List<CustomerBindCard> cards = null;
			// 判断是否为总代
			if (user.getCustomerLevel() == 0) {
				cards = getCustomerBindCardList(user.getId()); // 总代直接取ID
			} else {
				long parentId = Long.parseLong(user.getAllParentAccount()
						.split(",")[0]); // 非总代取所有父ID字段的第一个记录，字段是用,分割
				cards = getCustomerBindCardList(parentId);
			}
			for (CustomerBindCard cbcard : cards) {
				CustomerBindCard newentity = getNewCustomerBindCard(cbcard,
						entity.getId(), user);
				customerBindCardDao.insert(newentity);
				customerUserWriteLog
						.saveWriteLog(user, CommonUtil.SAVE,
								"CustomerBindCard",
								newentity.toString());
			}
			// 初始化钱包
			CustomerCash cash = customerCashVOToEntity(user, entity.getId());
			customerCashDao.insert(cash);
			customerUserWriteLog.saveWriteLog(user, CommonUtil.SAVE,
					"CustomerCash", "insert into t_customer_cash ");
			//开启积分系统
			CustomerIntegral integral = new CustomerIntegral();
			integral.setCustomerId(entity.getId());
			integral.setIntegral(0);
			integral.setLevel(1);
			integral.addInit(user.getCustomerName());
			integralDao.save(integral);
			return "success";
		} else {
			throw new LotteryException("用户名已存在");
		}
	}

	private String openMember(CustomerUserVO vo,CustomerUser user,Map<String, ?> param) throws Exception{
		if (vo.getQuotaId() > 0) { // 判断如果返点比小于最低返点比则可以无限制开户
			// 更新配额剩余数量
			CustomerQuota quota = customerQuotaDao.queryById(vo.getQuotaId());
			vo.setRebates(quota.getProportion());
			vo.setMinRebates(quota.getProportion());
			if (quota.getQuota_count() - 1 < 0) {
				throw new LotteryException("剩余配额数量不足");
			}
			quota.setQuota_count(quota.getQuota_count() - 1);
			customerQuotaDao.update(quota);
			customerUserWriteLog.saveWriteLog(user, CommonUtil.UPDATE,
					"CustomerQuota", quota.toString());
		}else{
			vo.setRebates(vo.getMinRebates());
		}

		if (vo.getRebates().floatValue() > user.getRebates().floatValue()||vo.getMinRebates().floatValue()>user.getRebates().floatValue()) {
			throw new LotteryException("返点比不能大于自己的返点比");
		}
		String mix = adminParameterDao.getParameterByNameAndIndex("userConfig", 1, "minPoint");
		BigDecimal mixReb = new BigDecimal(Double.parseDouble(mix)/100);
		if (vo.getQuotaId() == 0 && vo.getMinRebates().compareTo(mixReb)>=0) {
			throw new LotteryException("其他返点比不能大于最小返点");
		}
		if (checkUserName(param)) {
			CustomerUser entity = openLowerCustomerUser(vo, user);
			if(vo.getQuotaId() == 0)entity.setRebates(vo.getMinRebates());
			//entity.setRebates(BigDecimal.ZERO);  会员是有 
			if(user.getCustomerType()==DataDictionaryUtil.CUSTOMER_TYPE_TEST){
				entity.setCustomerType(DataDictionaryUtil.CUSTOMER_TYPE_TEST);
			}else{
				entity.setCustomerType(DataDictionaryUtil.CUSTOMER_TYPE_MEMBER);
			}
			// 持久化用户
			customerUserDao.insert(entity);
			customerUserWriteLog.saveWriteLog(user, CommonUtil.SAVE,
					"CustomerUser", entity.toString());
			// 持久化用户银行卡
			List<CustomerBindCard> cards = null;
			// 判断是否为总代
			if (user.getCustomerLevel() == 0) {
				cards = getCustomerBindCardList(user.getId()); // 总代直接取ID
			} else {
				long parentId = Long.parseLong(user.getAllParentAccount()
						.split(",")[0]); // 非总代取所有父ID字段的第一个记录，字段是用,分割
				cards = getCustomerBindCardList(parentId);
			}
			for (CustomerBindCard cbcard : cards) {
				CustomerBindCard newentity = getNewCustomerBindCard(cbcard,
						entity.getId(), user);
				customerBindCardDao.insert(newentity);
				customerUserWriteLog
						.saveWriteLog(user, CommonUtil.SAVE,
								"CustomerBindCard",
								newentity.toString());
			}
			// 初始化钱包
			CustomerCash cash = customerCashVOToEntity(user, entity.getId());
			customerCashDao.insert(cash);
			customerUserWriteLog.saveWriteLog(user, CommonUtil.SAVE,
					"CustomerCash", "insert into t_customer_cash ");
			//开启积分系统
			CustomerIntegral integral = new CustomerIntegral();
			integral.setCustomerId(entity.getId());
			integral.setIntegral(0);
			integral.setLevel(1);
			integral.addInit(user.getCustomerName());
			integralDao.save(integral);
			return "success";
		} else {
			throw new LotteryException("用户名已存在");
		}
	}
	
	private CustomerUser openLowerCustomerUser(CustomerUserVO vo,
			CustomerUser pentity) {
		CustomerUser entity = new CustomerUser();
		entity.setCustomerName(vo.getCustomerName().trim());
		entity.setCustomerPwd(MD5Util.makeMD5(vo.getCustomerPwd()));
		entity.setCustomerStatus(DataDictionaryUtil.STATUS_OPEN);
		entity.setCustomerOnlineStatus(DataDictionaryUtil.STATUS_ONLINE_NO);
		entity.setCustomerSuperior(pentity.getId());
		entity.setCustomerLevel(pentity.getCustomerLevel() + 1);
		entity.setCustomerError(0);
		entity.setCreateTime(DateUtil.getNowDate());
		entity.setCreateUser(pentity.getCustomerName());
		entity.setUpdateTime(DateUtil.getNowDate());
		entity.setUpdateUser(pentity.getCustomerName());
		if(pentity.getCustomerType()==DataDictionaryUtil.CUSTOMER_TYPE_TEST){
			entity.setCustomerType(DataDictionaryUtil.CUSTOMER_TYPE_TEST);
		}else{
			entity.setCustomerType(DataDictionaryUtil.CUSTOMER_TYPE_PROXY);
		}
		//entity.setCustomerAlias(vo.getCustomerAlias().trim());
		if (pentity.getCustomerLevel() == 0) {
			entity.setAllParentAccount(String.valueOf(pentity.getId()));
		} else {
			entity.setAllParentAccount(pentity.getAllParentAccount() + ","
					+ pentity.getId());
		}
		entity.setRebates(vo.getRebates());
		//设置用户的默认等级为1
		entity.setActiveLevel(DataDictionaryUtil.COMMON_FLAG_1);
		return entity;
	}

	@SuppressWarnings("unchecked")
	private List<CustomerBindCard> getCustomerBindCardList(long userid) {
		StringBuffer sql = new StringBuffer(
				"from CustomerBindCard t where t.customerId=? and t.status=? and t.extendsStatus = ?");
		Query query = customerBindCardDao.getSession().createQuery(
				sql.toString());
		query.setParameter(0, userid);
		query.setParameter(1, DataDictionaryUtil.STATUS_OPEN);
		query.setParameter(2, DataDictionaryUtil.EXTENDS_STATUS_OK);
		return query.list();
	}

	private CustomerBindCard getNewCustomerBindCard(CustomerBindCard pentity,
			long userId, CustomerUser user) {
		CustomerBindCard entity = new CustomerBindCard();
		entity.setCustomerId(userId);
		entity.setBankcardId(pentity.getBankcardId());
		entity.setStatus(DataDictionaryUtil.STATUS_OPEN);
		entity.setExtendsStatus(DataDictionaryUtil.EXTENDS_STATUS_OK);
		entity.setCreateTime(DateUtil.getNowDate());
		entity.setCreateUser(user.getCustomerName());
		entity.setUpdateTime(DateUtil.getNowDate());
		entity.setUpdateUser(user.getCustomerName());
		return entity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findLowerLevelCustomerUserObjects(Map<String, ?> param)
			throws Exception {
		// TODO Auto-generated method stub
		CustomerUser user = (CustomerUser) param
				.get(CommonUtil.CUSTOMERUSERKEY);
		StringBuffer queryString = new StringBuffer("SELECT");
		queryString.append(" t.id,t.customer_name,t.customer_level,");
		queryString.append(" t.`rebates`,(SELECT COUNT(1) FROM t_customer_user u  WHERE (u.`allParentAccount` LIKE CONCAT(t.id,',','%') OR u.`allParentAccount` LIKE CONCAT('%',',',t.id) OR u.`allParentAccount` LIKE CONCAT('%',',',t.id,',','%') OR (u.`customer_level` = 1 AND u.`allParentAccount` = t.id)) and u.customer_Online_Status = 11002) AS lowerlevel,");
		queryString.append(" t.`customer_status`, (SELECT IFNULL(SUM(bet.`bet_money`*bet.multiple), 0) FROM t_bet_record bet ");
		queryString.append(" WHERE bet.`customer_id` = t.id ");
		queryString.append(" AND bet.`bet_status` IN (21002, 21003) AND YEAR(bet.`create_time`) = YEAR(NOW()) AND MONTH(bet.`create_time`) = MONTH(NOW())) AS amount FROM ");
		queryString.append(" t_customer_user t ");
		queryString.append(" where t.customer_superior = ?");
		queryString.append(" and t.customer_Online_Status = ? ");
		Query query = customerUserDao.getSession().createSQLQuery(
				queryString.toString());
		query.setParameter(0, user.getId());
		query.setParameter(1, DataDictionaryUtil.STATUS_ONLINE_ON);
		List<Object[]> objs = query.list();
		return objs;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerUser> findLowerLevelCustomerUser(Map<String, ?> param)
			throws Exception {
		// TODO Auto-generated method stub
		CustomerUser user = (CustomerUser) param
				.get(CommonUtil.CUSTOMERUSERKEY);
		StringBuffer queryString = new StringBuffer(
				"from CustomerUser t where t.customerSuperior = ?");
		queryString.append(" and t.customerOnlineStatus = ? ");
		Query query = customerUserDao.getSession().createQuery(
				queryString.toString());
		query.setParameter(0, user.getId());
		query.setParameter(1, DataDictionaryUtil.STATUS_ONLINE_ON);
		return query.list();
	}

	@Override
	public CustomerUser updateCustomerData(Map<String, ?> param)
			throws Exception {
		// TODO Auto-generated method stub
		CustomerUser user = (CustomerUser) param
				.get(CommonUtil.CUSTOMERUSERKEY);
		CustomerUserVO vo = (CustomerUserVO) param.get("userkey");
		if (!checkCustomerAlias(user.getId(),vo.getCustomerAlias())
				&& !(user.getCustomerAlias().equals(vo.getCustomerAlias()))) {
			throw new LotteryException("别名重复");
		}
		user.setCustomerAlias(vo.getCustomerAlias());
		user.setQq(vo.getQq());
		user.setEmail(vo.getEmail());
		customerUserDao.update(user);
		return user;
	}

	public boolean checkCustomerAlias(long userId,String alias) {
		StringBuffer queryString = new StringBuffer(
				"from CustomerUser t where t.customerAlias = ? and t.id<>? and t.customerStatus = ?");
		Query query = customerUserDao.getSession().createQuery(
				queryString.toString());
		query.setParameter(0, alias);
		query.setParameter(1, userId);
		query.setParameter(2, DataDictionaryUtil.STATUS_OPEN);
		if (query.list().size() == 0)
			return true;
		return false;
	}

	@Override
	public CustomerUser checkLowerCustomers(Map<String, ?> param)
			throws Exception {
		// TODO Auto-generated method stub
		CustomerUserVO vo = (CustomerUserVO) param.get("customeruservokey");
		StringBuffer queryString = new StringBuffer(
				"from CustomerUser t where t.customerName = ? and t.allParentAccount like ? and t.customerStatus = ? ");
		Query query = customerUserDao.getSession().createQuery(queryString.toString());
		query.setParameter(0, vo.getCustomerName());
		query.setParameter(1, "%"+vo.getUserMainId()+"%");
		query.setParameter(2, DataDictionaryUtil.STATUS_OPEN);
		if(query.list().size() == 0)return null;
		return (CustomerUser) query.list().get(0);
	}

	@Override
	public List<Object[]> findMainCustomers(Map<String, ?> param)
			throws Exception {
		// TODO Auto-generated method stub
		return customerUserDao.findMainCustomers(param);
	}

	@Override
	public List<Object[]> findCuserTree(Map<String, ?> param)
			throws Exception {
		// TODO Auto-generated method stub
		return customerUserDao.findCuserTree(param);
	}

	@Override
	public String updateUserInfo(Map<String, ?> param) throws Exception {
		// TODO Auto-generated method stub
		CustomerUserVO vo = (CustomerUserVO) param.get("uservokey");
		AdminUser admin = (AdminUser) param.get(CommonUtil.USERKEY);
		//主对象修改
		CustomerUser entity = customerUserDao.queryById(vo.getId());
		if(entity==null)throw new LotteryException("用户不存在");
		entity.setCustomerStatus(vo.getCustomerStatus());
		entity.setUpdateTime(DateUtil.getNow());
		entity.setUpdateUser(admin.getUserName());
		entity.setRebates(vo.getRebates());
		customerUserDao.update(entity);
		adminWriteLog.saveWriteLog(admin, CommonUtil.UPDATE, "CustomerUser", entity.toString());

		//对象配额修改
		//add by joseph
		java.math.BigDecimal rebates=vo.getRebates();	//总代返点值 	0.085
		Map<String, String> timesMap = parameterDao
				.queryParameterList("userConfig", new String[] { "stepPoint" });
		/*Long stepPoint =  Long.parseLong(timesMap.get("value"));		//步进点值 	0.1
		BigDecimal aa=(BigDecimal)stepPoint;*/
		String strStepPoint=timesMap.get("stepPoint");
		BigDecimal stepPoint = new BigDecimal("0.1");		//步进点值 	0.1
		if(StringUtils.isNotEmpty(strStepPoint)){
			stepPoint = new BigDecimal(strStepPoint);  	//步进点值 	0.1
		}
		BigDecimal bdPesent = new BigDecimal("0.01");
		stepPoint=stepPoint.multiply(bdPesent);
		String[] quotas = vo.getQuotas().split(":");
		for(int i=0;i<quotas.length;i++){
			String[] quota = quotas[i].split(",");
			if(quota[0].equals(""))continue;
			CustomerQuota qentity = customerQuotaDao.queryById(Long.parseLong(quota[0]));
			//qentity.getProportion() 0.074	qentity.getQuota_count() 19
			if(qentity.getProportion().compareTo(rebates)==-1){
				qentity.setQuota_count(Integer.parseInt(quota[1]));
				qentity.setUpdateTime(DateUtil.getNow());
				qentity.setUpdateUser(admin.getUserName());
				customerQuotaDao.update(qentity);
				adminWriteLog.saveWriteLog(admin, CommonUtil.UPDATE, "CustomerQuota", "修改配额数量");
			}else{
				//删除，由于降低总返点的多余数据
				customerQuotaDao.delete(qentity);				
				adminWriteLog.saveWriteLog(admin, CommonUtil.DELETE, "CustomerQuota", "删除配额数量");
			}
		}
		//添加 由于添加总返点数而需要增加的记录
		java.math.BigDecimal maxPoint= customerQuotaDao.getCustomerQuotaMaxProportionByCustomerId(vo.getId());
		//vo.getId()	customer_id

		//java.math.BigDecimal tempDecimal=rebates.subtract(stepPoint);
		java.math.BigDecimal tempDecimal=maxPoint.add(stepPoint);
		while(tempDecimal.compareTo(rebates)<0){
			//添加，由于增加总返点的多余数据
			CustomerQuota tAdd=new CustomerQuota();
			tAdd.setCustomerId(vo.getId());;
			tAdd.setProportion(tempDecimal);;
			tAdd.setQuota_count(0);
			tAdd.setCreateTime(DateUtil.getNow());
			tAdd.setCreateUser(admin.getUserName());
			tAdd.setUpdateTime(DateUtil.getNow());
			tAdd.setUpdateUser(admin.getUserName());
			tAdd.setStatus(DataDictionaryUtil.STATUS_OPEN);
			customerQuotaDao.insert(tAdd);
			adminWriteLog.saveWriteLog(admin, CommonUtil.SAVE, "CustomerQuota", "新增配额数量");
			
			tempDecimal=tempDecimal.add(stepPoint);
		}
		
		//对象分配银行卡状态修改
		String[] userbindcards = vo.getUserbindcards().split(":");
		for(int i=0;i<userbindcards.length;i++){
			String[] userbindcard = userbindcards[i].split(",");
			if(userbindcard[0].equals(""))continue;
			CustomerBindCard bindEntity = customerBindCardDao.queryById(Long.parseLong(userbindcard[0]));
			bindEntity.setStatus(Integer.parseInt(userbindcard[1]));
			bindEntity.setUpdateTime(DateUtil.getNow());
			bindEntity.setUpdateUser(admin.getUserName());
			customerBindCardDao.update(bindEntity);
			adminWriteLog.saveWriteLog(admin, CommonUtil.UPDATE, "CustomerBindCard", "修改分配银行卡状态");
		}
		//对象绑定银行卡状态修改
		String[] usercards = vo.getUsercards().split(":");
		for(int i=0;i<usercards.length;i++){
			String[] usercard = usercards[i].split(",");
			if(usercard[0].equals(""))continue;
			UserCard userCard = userCardDao.queryById(Long.parseLong(usercard[0]));
			userCard.setStatus(Integer.parseInt(usercard[1]));
			userCard.setUpdateTime(DateUtil.getNow());
			userCard.setUpdateUser(admin.getUserName());
			userCardDao.update(userCard);
			adminWriteLog.saveWriteLog(admin, CommonUtil.UPDATE, "UserCard", "修改绑定银行卡状态");
		}
		return "success";
	}

	@SuppressWarnings("unchecked")
	@Override
	public String checkAlias(Map<String, ?> param) throws Exception {
		CustomerUserVO vo = (CustomerUserVO) param.get("uservokey");
		CustomerUser entity = (CustomerUser) param.get(CommonUtil.CUSTOMERUSERKEY);
		if(vo==null||vo.getCustomerAlias()==null||vo.getCustomerAlias().equals("")){
			throw new LotteryException("昵称不能为空");
		}
		StringBuffer queryString = new StringBuffer("from CustomerUser t where t.customerAlias = ? and t.id<>? and t.customerStatus = 10002 and t.customerOnlineStatus = 11002");
		Query query = customerUserDao.getSession().createQuery(queryString.toString());
		query.setParameter(0, vo.getCustomerAlias().trim());
		query.setParameter(1, entity.getId());
		List<CustomerUser> entitys = query.list();
		if(entitys.size()==0)return "true";
		return "false";
	}

	@Override
	public List<Object> queryTeamMoneyInfo(Map<String, ?> param)
			throws Exception {
		// TODO Auto-generated method stub
		return customerUserDao.queryTeamMoneyInfo(param);
	}

	@Override
	public Page<CustomerUserVO, Object> queryLowerLevels(
			Map<String, ?> param) throws Exception {
		Page<CustomerUserVO, Object> page = customerUserDao.queryLowerLevels(param);
		List<Object> entitys = page.getEntitylist();
		List<CustomerUserVO> vos = new ArrayList<CustomerUserVO>();
		for(Object obj : entitys){
			Object[] objs = (Object[]) obj;
			CustomerUserVO vo = new CustomerUserVO();
			vo.setId(Long.parseLong(objs[0].toString()));
			//vo.setCustomerLevelName(Util.transition(objs[5]));
			vo.setRebates(new BigDecimal(objs[1].toString()));
			vo.setUserBetMoney(new BigDecimal(objs[4].toString()));
			vo.setCreateTime(DateUtil.strToDate2(objs[5].toString()));
			vo.setCustomerAlias(objs[3].toString());
			vo.setCustomerName(objs[2].toString());
			vo.setCustomerType(Integer.parseInt(objs[6].toString()));
			vo.setCash(new BigDecimal(objs[7].toString()));
			vo.setCustomerOnlineStatus(Integer.valueOf(objs[8].toString()));
			BigInteger lowerNum =(BigInteger)customerUserDao.queryTeamNum(String.valueOf(vo.getId())).get(1);
			vo.setCustomerLower(lowerNum);
			vos.add(vo);
		}
		page.setPagelist(vos);
		return page;
	}

	@Override
	public List<Object[]> queryUserCount() throws Exception {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer(" SELECT (SELECT COUNT(1) FROM t_customer_user) AS usercount, ");
		sql.append(" (SELECT COUNT(1) FROM t_customer_user WHERE customer_type=12001) AS proxycount, ");
		sql.append(" (SELECT COUNT(1) FROM t_customer_user WHERE customer_type=12002) AS membercount ");
		sql.append(" FROM DUAL ");
		Query query = customerUserDao.getSession().createSQLQuery(sql.toString());
		return query.list();
	}

	@Override
	public boolean checkRegUserStatus() throws Exception {
		// TODO Auto-generated method stub
		StringBuffer stringQuery = new StringBuffer("from CustomerUser t where t.createUser = ? ");
		Query query = customerUserDao.getSession().createQuery(stringQuery.toString());
		query.setParameter(0, CommonUtil.PROXYUSER);
		List<CustomerUser> users = query.list();
		if(users.size()>0)return true;
		return false;
	}

	@Override
	public CustomerUser getRegProxyUser() throws Exception {
		// TODO Auto-generated method stub
		StringBuffer stringQuery = new StringBuffer("from CustomerUser t where t.createUser = ? ");
		Query query = customerUserDao.getSession().createQuery(stringQuery.toString());
		query.setParameter(0, CommonUtil.PROXYUSER);
		List<CustomerUser> users = query.list();
		if(users.size()==0)throw new LotteryException("新用户注册暂未开放");
		return users.get(0);
	}

	@Override
	public Map<String, Integer> queryUserStatistic() throws Exception {
		return customerUserDao.queryUserStatistic();
	}

	@Override
	public Map<String, String> queryMonthActiveUser(Map<String, Object> param) throws Exception {
		return customerUserDao.queryMonthActiveUser(param);
	}

	@Override
	public Map<String, Integer> queryMonthAddUser(Map<String, Object> param) throws Exception {
		return customerUserDao.queryMonthAddUser(param);
	}

	@Override
	public Map<String, ?> queryProfileAmount(Map<String, Object> param)
			throws Exception {
		return customerUserDao.queryProfileAmount(param);
	}
	@Override
	public Map<String, ?> queryProfileAmountMap(Map<String, Object> param)
			throws Exception {
		return customerUserDao.queryProfileAmountMap(param);
	}

	@Override
	public Map<String, ?> queryProfileData(Map<String, Object> param)
			throws Exception {
		return customerUserDao.queryProfileData(param);
	}

	@Override
	public Map<String, ?> queryMarketStatistic(Map<String, Object> param)
			throws Exception {
		return customerUserDao.queryMarketStatistic(param);
	}

	@Override
	public Map<String, ?> queryAvgPerStasData(Map<String, Object> param)
			throws Exception {
		return customerUserDao.queryAvgPerStasData(param);
	}

	@Override
	public Map<String, ?> queryAmountStasData(Map<String, Object> param)
			throws Exception {
		return customerUserDao.queryAmountStasData(param);
	}

	@Override
	public CustomerUser queryUserById(long userId) throws Exception {
		// TODO Auto-generated method stub
		return customerUserDao.queryById(userId);
	}

	@Override
	public String queryUserFirends(CustomerUser user) throws Exception {
		// TODO Auto-generated method stub
		List<Object[]> friendObj = customerUserDao.queryUserFirends(user);
		String friends = "";
		for(int i=0;i<friendObj.size();i++){
			Object[] obj = friendObj.get(i);
			if(friends.equals("")){
				friends = obj[0].toString()+":"+obj[1].toString();
			}else{
				friends += ","+obj[0].toString()+":"+obj[1].toString();
			}
		}
		return friends;
	}

	@Override
	public CustomerUser queryUserByName(String userName) throws Exception {
		// TODO Auto-generated method stub
		CustomerUser user = customerUserDao.queryUserByName(userName);
		return user;
	}

	@Override
	public CustomerUser update(CustomerUser user) throws Exception {
		// TODO Auto-generated method stub
		customerUserDao.update(user);
		return user;
	}

	@Override
	public String updateLowerQuota(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		CustomerUser user = (CustomerUser) param.get(CommonUtil.CUSTOMERUSERKEY);
		LowerManagerVO vo = (LowerManagerVO) param.get("lmvo");
		String userMoneyPwd = vo.getMoneryPwd();
		if (!user.getMoneyPwd().equals(MD5Util.makeMD5(userMoneyPwd))) {
			throw new LotteryException("资金密码错误");
		}
		if(user.getRebates().compareTo(new BigDecimal(vo.getToQuota()))<0){
			throw new LotteryException("返点不能大于自己");
		}
		CustomerUser toUser = queryUserByName(vo.getToUserName());
		if(toUser.getRebates().compareTo(new BigDecimal(vo.getToQuota()))==1){
			throw new LotteryException("只能提升下级用户返点");
		}
		String minPoint = adminParameterDao.getParameterByNameAndIndex("userConfig", 1, "minPoint");
		if(new BigDecimal(minPoint).divide(new BigDecimal(100)).compareTo(new BigDecimal(vo.getToQuota()))==1){
			toUser.updateInit(user.getCustomerName());
			toUser.setRebates(new BigDecimal(vo.getToQuota()));
		}else{
			List<CustomerQuota> quotas = customerQuotaDao.getCustomerQuotaByCustomerId(user.getId());
			CustomerQuota quota = null;
			for(CustomerQuota cq:quotas){
				if(cq.getProportion().compareTo(new BigDecimal(vo.getToQuota()))==0){
					quota = cq;
				}
			}
			if(quota == null){
				throw new LotteryException("该返点不存在");
			}
			if(quota.getQuota_count()-1<0){
				throw new LotteryException("返点配额不足");
			}
			quota.updateInit(user.getCustomerName());
			customerQuotaDao.update(quota);
			toUser.updateInit(user.getCustomerName());
			toUser.setRebates(new BigDecimal(vo.getToQuota()));
		}
		return "success";
	}

	@Override
	public Page<CustomerUserVO, Object> queryTeamUserByKey(
			Map<String, Object> param) throws Exception {
		Page<CustomerUserVO, Object> page = customerUserDao.queryTeamUserByKey(param);
		List<Object> entitys = page.getEntitylist();
		List<CustomerUserVO> vos = new ArrayList<CustomerUserVO>();
		for(Object obj : entitys){
			Object[] objs = (Object[]) obj;
			CustomerUserVO vo = new CustomerUserVO();
			vo.setId(Long.parseLong(objs[0].toString()));
			vo.setCustomerLevelName(Util.transition(objs[4]));
			//vo.setQuotaCount(objs[3].toString());
			vo.setRebates(new BigDecimal(objs[1].toString()));
			//vo.setUserBetMoney(new BigDecimal(objs[4].toString()));
			vo.setCreateTime(DateUtil.strToDate2(objs[5].toString()));
			vo.setCustomerAlias(objs[3].toString());
			vo.setCustomerName(objs[2].toString());
			vo.setCustomerType(Integer.parseInt(objs[6].toString()));
			vo.setCash(new BigDecimal(objs[7].toString()));
			vo.setCustomerOnlineStatus(Integer.valueOf(objs[8].toString()));
			BigInteger lowerNum =(BigInteger)customerUserDao.queryTeamNum(String.valueOf(vo.getId())).get(1);
			vo.setCustomerLower(lowerNum);
			vos.add(vo);
		}
		page.setPagelist(vos);
		return page;
	}

	@Override
	public CustomerUser saveRestPwdByName(String userName) throws Exception {
		CustomerUser user = customerUserDao.queryUserByName(userName);
		if(user==null){
			throw new LotteryException("无该用户名对应的用户记录！");
		}
		user.setCustomerPwd(MD5Util.makeMD5("a123456"));
		customerUserDao.save(user);
		return user;
	}

	@Override
	public List<Object> queryTeamNum(String userId) throws LotteryException {
		return customerUserDao.queryTeamNum(userId);
	}

	@Override
	public Map<String, Integer> groupDirectSubCustomer(String userId) throws LotteryException, ParseException {
		Map<String, Integer> resultMap = new HashMap<String, Integer>();
		Map<String, String> param = new HashMap<String, String>();
		param.put("userId", userId);
		param.put("sdate", DateUtil.getHourOfDay(0, 0, 0, 0));
		// 今日新增直接下级人数
		resultMap.put("dayNum", customerUserDao.getDirectSubCustomer(param).size());
		
		// 昨日新增直接下级人数
		param.put("sdate", DateUtil.getHourOfDay(0, 0, 0, -1));
		param.put("edate", DateUtil.getHourOfDay(0, 0, 0, 0));
		resultMap.put("YesterdayNum", customerUserDao.getDirectSubCustomer(param).size());
		
		// 本周新增直接下级人数
		param.put("sdate", DateUtil.getHourOfDay(0, 0, 0, -7));
		param.put("edate", DateUtil.getHourOfDay(0, 0, 0, 0));
		resultMap.put("weekNum", customerUserDao.getDirectSubCustomer(param).size());
		// 下周新增直接下级人数
		param.put("sdate", DateUtil.getHourOfDay(0, 0, 0, -14));
		param.put("edate", DateUtil.getHourOfDay(0, 0, 0, -7));
		resultMap.put("lastWeekNum", customerUserDao.getDirectSubCustomer(param).size());
		
		// 本月新增直接下级人数
		param.put("sdate", DateUtil.getHourOfDay(0, 0, 0, -30));
		param.put("edate", DateUtil.getHourOfDay(0, 0, 0, 0));
		resultMap.put("monthNum", customerUserDao.getDirectSubCustomer(param).size());
		
		// 上月新增直接下级人数
		param.put("sdate", DateUtil.getHourOfDay(0, 0, 0, -60));
		param.put("edate", DateUtil.getHourOfDay(0, 0, 0, -30));
		resultMap.put("lastMonthNum", customerUserDao.getDirectSubCustomer(param).size());
		
		return resultMap;
	}

	@Override
	public Map<String, Integer> groupAllTeamCustomer(String userId) throws LotteryException, ParseException {
		Map<String, Integer> resultMap = new HashMap<String, Integer>();
		Map<String, String> param = new HashMap<String, String>();
		param.put("userId", userId);
		param.put("sdate", DateUtil.getHourOfDay(0, 0, 0, 0));
		// 今日新增直接下级人数
		resultMap.put("dayNum", customerUserDao.getAllTeamCustomer(param).size());
		
		// 昨日新增直接下级人数
		param.put("sdate", DateUtil.getHourOfDay(0, 0, 0, -1));
		param.put("edate", DateUtil.getHourOfDay(0, 0, 0, 0));
		resultMap.put("YesterdayNum", customerUserDao.getAllTeamCustomer(param).size());
		
		// 本周新增直接下级人数
		param.put("sdate", DateUtil.getHourOfDay(0, 0, 0, -7));
		param.put("edate", DateUtil.getHourOfDay(0, 0, 0, 0));
		resultMap.put("weekNum", customerUserDao.getAllTeamCustomer(param).size());
		// 下周新增直接下级人数
		param.put("sdate", DateUtil.getHourOfDay(0, 0, 0, -14));
		param.put("edate", DateUtil.getHourOfDay(0, 0, 0, -7));
		resultMap.put("lastWeekNum", customerUserDao.getAllTeamCustomer(param).size());
		
		// 本月新增直接下级人数
		param.put("sdate", DateUtil.getHourOfDay(0, 0, 0, -30));
		param.put("edate", DateUtil.getHourOfDay(0, 0, 0, 0));
		resultMap.put("monthNum", customerUserDao.getAllTeamCustomer(param).size());
		
		// 上月新增直接下级人数
		param.put("sdate", DateUtil.getHourOfDay(0, 0, 0, -60));
		param.put("edate", DateUtil.getHourOfDay(0, 0, 0, -30));
		resultMap.put("lastMonthNum", customerUserDao.getAllTeamCustomer(param).size());
		
		return resultMap;
	}
	

	@Override
	public BigDecimal getCustomerUserMaxRebatesByParentId(long parentId) throws Exception {
		java.math.BigDecimal resultMaxRebate=customerUserDao.getCustomerUserMaxRebatesByParentId(parentId);
		return resultMaxRebate;
	}
}
