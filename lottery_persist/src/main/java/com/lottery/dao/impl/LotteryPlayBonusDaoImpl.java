package com.lottery.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.LotteryPlayBonus;
import com.lottery.bean.entity.vo.LotteryPlayBonusVO;
import com.lottery.bean.entity.vo.LotteryTypeVO;
import com.lottery.dao.ILotteryPlayBonusDao;
import com.lottery.persist.generice.GenericDAO;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.util.DataDictionaryUtil;

@Repository
public class LotteryPlayBonusDaoImpl extends GenericDAO<LotteryPlayBonus> implements ILotteryPlayBonusDao{

	public LotteryPlayBonusDaoImpl() {
		super(LotteryPlayBonus.class);
	}

	@Override
	public List<LotteryPlayBonus> queryPlayBonusBylotteryAndPlay(
			LotteryPlayBonusVO lpbVo ) {
		String hql ="from LotteryPlayBonus where lotteryCode=?  ";
		if(lpbVo.getStatus()!=0){
			hql +="  and status=? ";
		}
		if(lpbVo.getBonusGroupId()!=0){
			hql +=" and bonusGroupId=? ";
		}
		Query query = getSession().createQuery(hql);
		query.setParameter(0, lpbVo.getLotteryCode());
		if(lpbVo.getStatus()!=0){
			query.setParameter(1, lpbVo.getStatus());
		}
		
		if(lpbVo.getBonusGroupId()!=0){
			query.setParameter(2, lpbVo.getBonusGroupId());
		}
		
		return query.list();
	}

	@Override
	public LotteryPlayBonus queryPlayBonusBylpm(LotteryPlayBonusVO lpbVo) throws LotteryException {
		//根据当前用户查询对应的总代用户是谁。
		String sql1 = "SELECT u.* FROM t_customer_user u WHERE u.id=? ";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql1);
		sqlQuery.setParameter(0, lpbVo.getUserId());
		sqlQuery.addEntity(CustomerUser.class);
		CustomerUser user = (CustomerUser) sqlQuery.list().get(0);
		String allParents = user.getAllParentAccount();
		String rootProxyId = allParents.split(",")[0];
		if(rootProxyId.equals(""))
			rootProxyId = Long.toString(user.getId());
		
		String hql ="from LotteryPlayBonus lpb where lpb.lotteryCode=? and lpb.modelCode=? and lpb.status=?"
				+ "and lpb.bonusGroupId = (select ubg.bid from UserBonusGroup ubg where ubg.cuid = ? )";
		Query query = getSession().createQuery(hql);
		query.setParameter(0, lpbVo.getLotteryCode());
		query.setParameter(1, lpbVo.getModelCode());
		query.setParameter(2, DataDictionaryUtil.COMMON_FLAG_1);
		query.setParameter(3, Long.parseLong(rootProxyId));
		List<LotteryPlayBonus> lps = query.list();
		if(lps.size()==0){
			throw new LotteryException("无法查询到对应彩种玩法"+lpbVo.getModelName()+"的奖金组设置！");
		}
		
		LotteryPlayBonus bonus = lps.get(0);
		return bonus;
	}

	@Override
	public List<LotteryPlayBonus> queryPlayBonusByUserId(LotteryPlayBonusVO lpbVo) throws LotteryException {
		//根据当前用户查询对应的总代用户是谁。
		String sql1 = "SELECT u.* FROM t_customer_user u WHERE u.id=? ";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql1);
		sqlQuery.setParameter(0, lpbVo.getUserId());
		sqlQuery.addEntity(CustomerUser.class);
		CustomerUser user = (CustomerUser) sqlQuery.list().get(0);
		String allParents = user.getAllParentAccount();
		String rootProxyId = allParents.split(",")[0];
		if(rootProxyId.equals(""))
			rootProxyId = Long.toString(user.getId());
		
		String hql ="from LotteryPlayBonus lpb where lpb.status=?"
				+ "and lpb.bonusGroupId = (select ubg.bid from UserBonusGroup ubg where ubg.cuid = ? )";
		Query query = getSession().createQuery(hql);
		query.setParameter(0, DataDictionaryUtil.COMMON_FLAG_1);
		query.setParameter(1, Long.parseLong(rootProxyId));
		List<LotteryPlayBonus> lps = query.list();
		if(lps.size()==0){
			throw new LotteryException("无法查询到对应彩种玩法"+lpbVo.getModelName()+"的奖金组设置！");
		}
		return lps;
	}

}
