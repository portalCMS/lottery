package com.lottery.service.impl;



import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.BankManage;
import com.lottery.bean.entity.CustomerBankCard;
import com.lottery.bean.entity.CustomerBindCard;
import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.vo.CustomerBindCardVO;
import com.lottery.bean.entity.vo.UserCardVO;
import com.lottery.dao.IBankManageDao;
import com.lottery.dao.ICustomerBankCardDao;
import com.lottery.dao.ICustomerBindCardDao;
import com.lottery.dao.ICustomerUserDao;
import com.lottery.dao.impl.AdminWriteLog;
import com.lottery.service.ICustomerBindCardService;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;
import com.xl.lottery.util.DateUtil;

@Service
public class CustomerBindCardServiceImpl implements ICustomerBindCardService{


	@Autowired
	private ICustomerBindCardDao bindCardDao;
	
	@Autowired
	private ICustomerBankCardDao bankCardDao;
	
	@Autowired
	private AdminWriteLog adminWriteLog;
	
	@Autowired
	private IBankManageDao bankDao;
	
	@Autowired
	private ICustomerUserDao userDao;
	
	@Override
	public List<CustomerBindCard> queryBindCardById(Map<String, ?> param)
			throws Exception {
		long userId =  (Long) param.get("userId");
		UserCardVO cardVo = (UserCardVO) param.get("cardVoKey");
		//查询总代对应绑定的继承卡。
		List<CustomerBindCard> bindCards = bindCardDao.queryBindCardByUserAgentId(userId);
		List<CustomerBindCard> removeBindCards = new ArrayList<CustomerBindCard>();
		for(CustomerBindCard bindCard : bindCards){
			CustomerBankCard card = bankCardDao.queryById(bindCard.getBankcardId());
			BankManage bank =bankDao.queryById(card.getBankId());
			CustomerUser user = userDao.queryById(bindCard.getCustomerId());
			// 将用户分配的银行卡循环一下，将不能充值的银行对应得银行卡去掉;将用户等级不等于卡等级的卡也去掉。
			if(user.getActiveLevel()==null){
				user.setActiveLevel(DataDictionaryUtil.COMMON_FLAG_1);
			}
			if (user.getActiveLevel()!= card.getCardLevel()||bank.getAdd() != DataDictionaryUtil.COMMON_FLAG_1) {
				removeBindCards.add(bindCard);
				continue;
			}
			card.setBank(bank);
			bindCard.setBankCard(card);
		}
		bindCards.removeAll(removeBindCards);
		
		if(null==bindCards||bindCards.size()==0){
			return bindCards;
		}
		//循环符合条件的银行卡，如果有相同银行的卡优先选择同行卡
		removeBindCards.clear();
		for(CustomerBindCard bc : bindCards){
			if(!bc.getBankCard().getBank().getName().equals(cardVo.getBranchBankName())){
				removeBindCards.add(bc);
			}
		}
		if(removeBindCards.size()!=bindCards.size()){
			bindCards.removeAll(removeBindCards);
		}
		
		//随机一个银行卡
		CustomerBindCard bindCard = null;
		Random random = new Random();

		if (bindCards.size() == 1) {
			bindCard = bindCards.get(0);
		} else {
			bindCard = bindCards.get(Math.abs(random.nextInt()
					% (bindCards.size() - 1)));
		}
		bindCards.clear();
		bindCards.add(bindCard);
		
		return bindCards;
		
	}

	@Override
	public String saveBindCardByUser(Map<String, ?> param) throws Exception {
		// TODO Auto-generated method stub
		long userID = (Long) param.get("userId");
		long cardId = (Long) param.get("cardId");
		if(userID==0)throw new LotteryException("用户不存在");
		if(cardId==0)throw new LotteryException("银行卡不存在");
		AdminUser admin = (AdminUser) param.get(CommonUtil.USERKEY);
		//持久化用户和卡关系  单一用户绑定卡只能是单点绑定
		CustomerBindCard entity = new CustomerBindCard();
		entity.setBankcardId(cardId);
		entity.setCustomerId(userID);
		entity.setStatus(DataDictionaryUtil.STATUS_OPEN);
		entity.setExtendsStatus(DataDictionaryUtil.EXTENDS_STATUS_NO);
		entity.setCreateTime(DateUtil.getNow());
		entity.setCreateUser(admin.getUserName());
		entity.setUpdateTime(DateUtil.getNow());
		entity.setUpdateUser(admin.getUserName());
		bindCardDao.insert(entity);
		adminWriteLog.saveWriteLog(admin, CommonUtil.SAVE, "CustomerBindCard", entity.toString());
		return "success";
	}

	@Override
	public Map<String,Object> queryCardUserInfo(Map<String, ?> param)
			throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String, Object>();
		CustomerBindCardVO vo = (CustomerBindCardVO) param.get("cbcvo");
		StringBuffer sqlString = new StringBuffer("SELECT t.id,t.`customer_name`,t.customer_level,t2.extends_status, ");
		sqlString.append("(SELECT COUNT(1) FROM t_customer_order t1 ");
		sqlString.append("WHERE t1.customer_id = t.`id` AND t1.reference_id = t2.`bankcard_id` AND t1.`order_detail_type` = 18009 ) AS ccount, ");
		sqlString.append("(SELECT SUM(t3.order_amount) FROM t_customer_order t3 ");
		sqlString.append("WHERE t3.customer_id = t.`id` AND t3.reference_id = t2.`bankcard_id` AND t3.`order_detail_type` = 18009 ) AS smoney,t2.status ");
		sqlString.append("FROM t_customer_user t,t_customer_bind_card t2 ");
		sqlString.append("WHERE t.`id` = t2.`customer_id` ");
		sqlString.append("AND t2.`bankcard_id` = ? LIMIT ?,10 ");
		
		StringBuffer sqlCount = new StringBuffer("SELECT count(1) ");
		sqlCount.append("FROM t_customer_user t,t_customer_bind_card t2 ");
		sqlCount.append("WHERE t.`id` = t2.`customer_id` ");
		sqlCount.append("AND t2.`bankcard_id` = ? ");
		Query countQuery = bindCardDao.getSession().createSQLQuery(sqlCount.toString());
		countQuery.setParameter(0, vo.getBankcardId());
		BigInteger totalCount = (BigInteger) countQuery.list().get(0);
		
		Query query = bindCardDao.getSession().createSQLQuery(sqlString.toString());
		query.setParameter(0, vo.getBankcardId());
		int pageNum = vo.getPageNum();
		int maxY = totalCount.intValue()/vo.getMaxCount();
		if(totalCount.intValue()%vo.getMaxCount()!=0){
			maxY+=1;
		}
		pageNum = pageNum<=0?0:pageNum;
		pageNum = pageNum>=maxY?maxY:pageNum;
		query.setParameter(1, pageNum);
		map.put("entitys", query.list());
		map.put("pageNum", pageNum);
		map.put("totalCount", totalCount.intValue());
		return map;
	}


}
