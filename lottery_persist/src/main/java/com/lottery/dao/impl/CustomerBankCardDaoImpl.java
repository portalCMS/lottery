package com.lottery.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.BankManage;
import com.lottery.bean.entity.CustomerBankCard;
import com.lottery.bean.entity.CustomerBindCard;
import com.lottery.bean.entity.DomainUrl;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.VCustomerBindCard;
import com.lottery.bean.entity.vo.BankManageVO;
import com.lottery.bean.entity.vo.CustomerBankCardVO;
import com.lottery.bean.entity.vo.CustomerUserVO;
import com.lottery.bean.entity.vo.DomainUrlVO;
import com.lottery.dao.ICustomerBankCardDao;
import com.lottery.dao.VCustomerBindCardDao;
import com.lottery.persist.generice.GenericDAO;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;

@Repository
public class CustomerBankCardDaoImpl extends GenericDAO<CustomerBankCard>
		implements ICustomerBankCardDao {

	@Autowired
	private VCustomerBindCardDao vCustomerBindCardDao;
	
	private static Logger logger = LoggerFactory
			.getLogger(AdminUserDaoImpl.class);

	public CustomerBankCardDaoImpl() {
		super(CustomerBankCard.class);
	}

	@Override
	public int countBankCard() {
		StringBuffer queryString = new StringBuffer(
				"select count(1) from t_customer_bank_card where bankcard_status != "
						+ DataDictionaryUtil.STATUS_DELETE);
		return ((BigInteger) getSession()
				.createSQLQuery(queryString.toString()).list().get(0))
				.intValue();
	}

	@Override
	public Page<CustomerBankCardVO, CustomerBankCard> findBankCardListByPage(
			Map<String, ?> param) {
		CustomerBankCardVO cardVo = (CustomerBankCardVO) param.get("bankCardKey");
		CustomerBankCard entity = new CustomerBankCard();
		List<String> keys = new ArrayList<String>();
		keys.add("bankcardStatus");
		List<Object> vals = new ArrayList<Object>();
		vals.add(DataDictionaryUtil.STATUS_DELETE);
		List<String> formula = new ArrayList<String>();
		formula.add("!=");
		@SuppressWarnings("unchecked")
		Page<CustomerBankCardVO, CustomerBankCard> page =(Page<CustomerBankCardVO, CustomerBankCard>) doPageQuery(cardVo,entity,formula,keys,vals);
		return page;
	}

	@Override
	public List<CustomerBankCard> getBankCard(Map<String, Object> param)
			throws Exception {
		Session session = getSession();
		CustomerBankCardVO vo = (CustomerBankCardVO) param
				.get("customerbankcardkey");
		StringBuffer query = new StringBuffer(
				"from CustomerBankCard t where t.bankcardStatus = ?");
		if (vo.getBankId() > 0) {
			query.append(" and t.bankId = " + vo.getBankId());
		}
		Query querylist = session.createQuery(query.toString());
		querylist.setParameter(0, DataDictionaryUtil.STATUS_OPEN);
		return querylist.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VCustomerBindCard> findCardsByUserId(Map<String, ?> param)
			throws Exception {
		CustomerUserVO vo = (CustomerUserVO) param.get("uservokey");
		StringBuffer queryString = new StringBuffer("from VCustomerBindCard t where t.customerId = ? ");
		Query query = vCustomerBindCardDao.getSession().createQuery(queryString.toString());
		query.setParameter(0, vo.getId());
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerBankCard> findBankCardsNotByUserId(Map<String, ?> param)
			throws Exception {
		// TODO Auto-generated method stub
		long userId = (Long) param.get("userId");
		long bankId = (Long) param.get("bankId");
		StringBuffer queryString = new StringBuffer(" from CustomerBindCard t where t.customerId = ? and t.status = ? ");
		Query query = getSession().createQuery(queryString.toString());
		query.setParameter(0, userId);
		query.setParameter(1, DataDictionaryUtil.STATUS_OPEN);
		List<CustomerBindCard> bindCards = query.list();
		List<Long> cardIds = new ArrayList<Long>();
		for(CustomerBindCard bindCard:bindCards){
			cardIds.add(bindCard.getBankcardId());
		}
		StringBuffer queryString1 = new StringBuffer("select t from CustomerBankCard t  ");
		queryString1.append(" where t.bankId = ? and t.bankcardStatus = ?  ");
		if(cardIds.size()>0){
			queryString1.append(" and t.id not in (:cardIds) ");
		}
		Query query1 = getSession().createQuery(queryString1.toString());
		query1.setParameter(0, bankId);
		query1.setParameter(1, DataDictionaryUtil.STATUS_OPEN);
		if(cardIds.size()>0){
			query1.setParameterList("cardIds", cardIds);
		}
		return query1.list();
	}

	@Override
	public List<CustomerBankCard> queryCardListByInv(Map<String, Object> param)
			throws Exception {
		CustomerBankCardVO vo = (CustomerBankCardVO) param.get("cardVokey");
		String hql="from CustomerBankCard where cardInventoryId = ? and bankcardStatus = ? ";
		Query query = getSession().createQuery(hql);
		query.setParameter(0, vo.getCardInventoryId());
		query.setParameter(1, DataDictionaryUtil.STATUS_OPEN);
		return query.list();
	}
	
	@Override
	public List<Object[]> getBackCardCountAndMoneyAndLevelAndInventoryName(long cardId) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer sqlString = new StringBuffer("SELECT `t2`.id,(SELECT  COUNT(1) FROM `t_customer_order` `t4` ");
		sqlString.append("WHERE (`t4`.`reference_id` = `t2`.`id`)) AS `card_count`,(SELECT ");
		sqlString.append("IFNULL(SUM(`t5`.`receive_amount`),0) FROM `t_customer_order` `t5` ");
		sqlString.append("WHERE (`t5`.`reference_id` = `t2`.`id`)) AS `card_money`, ");
		sqlString.append("t3.`card_inventory_name`, ");
		sqlString.append(" (SELECT COUNT(1) FROM t_customer_bind_card t4 WHERE t4.bankcard_id = t2.`id`) AS bindCount ");
		sqlString.append("FROM  `t_customer_bank_card` `t2`,t_card_level_config t3 ");
		sqlString.append( "WHERE t2.`card_level` = t3.`card_level` and t2.id=? ");
		Query query = getSession().createSQLQuery(sqlString.toString());
		query.setParameter(0, cardId);
		return query.list();
	}

}
