package com.lottery.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.LotteryPlaySelect;
import com.lottery.bean.entity.vo.LotteryPlaySelectVO;
import com.lottery.dao.ILotteryPlaySelectDao;
import com.lottery.persist.generice.GenericDAO;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.util.DataDictionaryUtil;

@Repository
public class LotteryPlaySelectDaoImpl extends GenericDAO<LotteryPlaySelect> implements ILotteryPlaySelectDao{

	public LotteryPlaySelectDaoImpl() {
		super(LotteryPlaySelect.class);
	}

	@Override
	public List<LotteryPlaySelect> querySelectByModel(
			LotteryPlaySelectVO selectVo) throws Exception {
		String hql ="from LotteryPlaySelect where status = ? and modelCode=? order by modelCode asc";
		Query query = getSession().createQuery(hql);
		query.setParameter(0, DataDictionaryUtil.COMMON_FLAG_1);
		query.setParameter(1, selectVo.getModelCode());
		return query.list();
	}

	@Override
	public LotteryPlaySelect queryPlaySelectByCode(String selectCode)
			throws Exception {
		// TODO Auto-generated method stub
		String hql ="from LotteryPlaySelect where status = ? and selectCode=? order by selectCode asc";
		Query query = getSession().createQuery(hql);
		query.setParameter(0, DataDictionaryUtil.COMMON_FLAG_1);
		query.setParameter(1, selectCode);
		if(query.list().size()==0)throw new LotteryException("选号方式不存在");
		return (LotteryPlaySelect) query.list().get(0);
	}

}
