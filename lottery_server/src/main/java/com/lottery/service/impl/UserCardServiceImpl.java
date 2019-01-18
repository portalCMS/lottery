package com.lottery.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.bean.entity.BankManage;
import com.lottery.bean.entity.UserCard;
import com.lottery.dao.IBankManageDao;
import com.lottery.dao.IUserCardDao;
import com.lottery.dao.impl.BankManageDao;
import com.lottery.service.IUserCardService;
import com.xl.lottery.desutil.AesUtil;
import com.xl.lottery.desutil.Md5Manage;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;

@Service
public class UserCardServiceImpl implements IUserCardService{

	@Autowired
	private IUserCardDao userCardDao;
	
	@Autowired
	private IBankManageDao bankDao;
	
	@Override
	public List<UserCard> queryUserCardByUserId(Map<String, Object> param) throws Exception {
		long userId = (Long) param.get("userId");
		String operationType = (String) param.get("operationType");
		List<UserCard> cards=userCardDao.queryUserCardByUserId(userId);
		//判断是用户提款还是充值，将没有权限的银行卡去掉
		if(operationType!=null){
			List<UserCard> removeCards = new ArrayList<UserCard>();
			int status=0;
			for(UserCard card : cards){
				BankManage bank = bankDao.queryById(card.getBankId());
				card.setBank(bank);
				if(operationType.equals(CommonUtil.ORDER_DETAIL_USER_RECHARGE)){
					status=bank.getAdd();
				}else if(operationType.equals(CommonUtil.ORDER_DETAIL_USER_DRAWING)){
					status=bank.getOut();
				}
				card.setOpenCardName(AesUtil.decrypt(card.getOpenCardName(), Md5Manage.getInstance().getMd5()).substring(0,1)+"*****");
				String cardNo = AesUtil.decrypt(card.getCardNo(), Md5Manage.getInstance().getMd5());
				card.setCardNo(cardNo.substring(0,4)+" **** **** **** "+cardNo.substring(cardNo.length()-3,cardNo.length()));
//				if(status!=DataDictionaryUtil.COMMON_STATUS_EFFECTIVE){
//					removeCards.add(card);
//				}
			}
//			cards.removeAll(removeCards);
		}
		
		
		return cards;
	}
	
	@Override
	public List<UserCard> queryUserCardByUserIdNC(Map<String, Object> param) throws Exception {
		long userId = (Long) param.get("userId");
		String operationType = (String) param.get("operationType");
		List<UserCard> cards=userCardDao.queryUserCardByUserId(userId);
		return cards;
	}

	@Override
	public List<Object[]> queryUserCardBycustomerId(Map<String, Object> param)
			throws Exception {
		long userId = (Long) param.get("userId");
		String hql ="select t1.name,t from UserCard t,BankManage t1 where t.bankId = t1.id and t.customerId = ? ";
		Query query = userCardDao.getSession().createQuery(hql);
		query.setParameter(0, userId);
		//query.setParameter(1, DataDictionaryUtil.STATUS_OPEN);
		return query.list();
	}

	@Override
	public UserCard queryUserCardById(Map<String, Object> param)
			throws Exception {
		Long id = (Long) param.get("cardIdKey");
		UserCard card = userCardDao.queryById(id);
		Long bankId = card.getBankId();
		BankManage bank = bankDao.queryById(bankId);
		card.setBank(bank);
		return card;
	}


}
