package com.lottery.dao.impl;



import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.BankManage;
import com.lottery.bean.entity.CustomerUrl;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.BankManageVO;
import com.lottery.bean.entity.vo.CustomerUrlVO;
import com.lottery.dao.IBankManageDao;
import com.lottery.persist.generice.GenericDAO;
import com.xl.lottery.util.DataDictionaryUtil;

@Repository
public class BankManageDao extends GenericDAO<BankManage> implements IBankManageDao {

	public BankManageDao() {
		super(BankManage.class);
	}

	@Override
	public Page<BankManageVO, BankManage> findBankeManageList(
			Map<String, ?> param) throws Exception {
		// TODO Auto-generated method stub
		BankManageVO customerUrlVo = (BankManageVO) param.get("bankmanagekey");
		BankManage bank = new BankManage();
		@SuppressWarnings("unchecked")
		Page<BankManageVO, BankManage> page = (Page<BankManageVO, BankManage>) doPageQuery(customerUrlVo, bank,null, null, null);
		return page;
	}

	protected int findBankCount() {
		StringBuffer queryString = new StringBuffer("select count(1) from t_bank_manage ");
		return ((BigInteger)getSession().createSQLQuery(queryString.toString()).list().get(0)).intValue();
	}

	@Override
	public int countBankManage() throws Exception {
		StringBuffer queryString = new StringBuffer("select count(1) from t_bank_manage where bank_status=10002");
		return ((BigInteger)getSession().createSQLQuery(queryString.toString()).list().get(0)).intValue();
	}

}
