package com.lottery.dao.impl;



import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.CustomerBindCard;
import com.lottery.bean.entity.CustomerUser;
import com.lottery.dao.ICustomerBindCardDao;
import com.lottery.persist.generice.GenericDAO;
import com.xl.lottery.util.DataDictionaryUtil;

@Repository
public class CustomerBindCardDaoImpl extends GenericDAO<CustomerBindCard>
		implements ICustomerBindCardDao {

	private static Logger logger = LoggerFactory.getLogger(AdminUserDaoImpl.class);
	
	public CustomerBindCardDaoImpl() {
		super(CustomerBindCard.class);
	}

	/**
	 * 查询总代对应的绑定银行卡。
	 */
	@Override
	public List<CustomerBindCard> queryBindCardByUserAgentId(long userId)
			throws Exception {
		//根据当前用户查询对应的总代用户是谁。
		String sql1 = "SELECT u.* FROM t_customer_user u WHERE u.id =? ";
		SQLQuery query = getSession().createSQLQuery(sql1);
		query.setParameter(0, userId);
		query.addEntity(CustomerUser.class);
		CustomerUser user = (CustomerUser) query.list().get(0);
		String allParents = user.getAllParentAccount();
		String rootProxyId = allParents.split(",")[0];
		
		int extendStatus = DataDictionaryUtil.EXTENDS_STATUS_NO;
		//如果上级总代为空，则该用户本身是总代。
		List<CustomerBindCard> list =null;
		List<CustomerBindCard> mylist =null;
		if(!rootProxyId.trim().equals("")){
			Long proxyId = Long.parseLong(rootProxyId);
			//非总代必须查总代对应的继承绑定的卡
			extendStatus = DataDictionaryUtil.EXTENDS_STATUS_OK;
			sql1 ="select c.* from t_customer_bind_card c where c.customer_id = ? and c.extends_status = ?";
			query = getSession().createSQLQuery(sql1);
			query.setParameter(0, proxyId);
			query.setParameter(1, extendStatus);
			query.addEntity(CustomerBindCard.class);
			list = query.list();
		}
		//如果自己是总代，或者总代没有分配继承的银行卡给代理，则代理查询系统给予他的单点绑定的银行卡。
//		if(null==list||list.size()==0){
			sql1 ="select c.* from t_customer_bind_card c where c.customer_id = ?";
			query = getSession().createSQLQuery(sql1);
			query.setParameter(0, userId);
			query.addEntity(CustomerBindCard.class);
			mylist = query.list();
//		}
			list.addAll(mylist);
		return list;
	}

	

}
