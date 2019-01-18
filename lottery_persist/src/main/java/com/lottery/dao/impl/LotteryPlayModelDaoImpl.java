package com.lottery.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.BonusGroup;
import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.LotteryPlayBonus;
import com.lottery.bean.entity.LotteryPlayModel;
import com.lottery.bean.entity.PlayAwardLevel;
import com.lottery.bean.entity.PlayModel;
import com.lottery.bean.entity.SourceLink;
import com.lottery.bean.entity.vo.LotteryPlayModelVO;
import com.lottery.bean.entity.vo.LotteryTypeVO;
import com.lottery.dao.ILotteryPlayBonusDao;
import com.lottery.dao.ILotteryPlayModelDao;
import com.lottery.dao.IPlayModelDao;
import com.lottery.dao.ISourceLinkDao;
import com.lottery.persist.generice.GenericDAO;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.util.DataDictionaryUtil;

@Repository
public class LotteryPlayModelDaoImpl extends GenericDAO<LotteryPlayModel> implements ILotteryPlayModelDao{

	public LotteryPlayModelDaoImpl() {
		super(LotteryPlayModel.class);
	}

	@Override
	public List<LotteryPlayModel> queryPlayModelByLotteryCode(Map<String, Object> param)
			throws Exception {
		LotteryPlayModelVO vo = (LotteryPlayModelVO) param.get("lpmKey");
		String hql ="from LotteryPlayModel lp where lp.lotteryCode=?";
		if(vo.getStatus()!=0){
			hql += " and status = ?";
		}
		Query query = getSession().createQuery(hql);
		query.setParameter(0, vo.getLotteryCode());
		if(vo.getStatus()!=0){
			query.setParameter(1, vo.getStatus());
		}
		List<LotteryPlayModel>  lpms= query.list();
		//查询配置的中奖等级
		for(LotteryPlayModel lpm : lpms){
			hql="from PlayAwardLevel where status=? and playCode = ? and lotteryCode=? ";
			query = getSession().createQuery(hql);
			query.setParameter(0, DataDictionaryUtil.COMMON_FLAG_1);
			query.setParameter(1, lpm.getModelCode());
			query.setParameter(2, lpm.getLotteryCode());
			List<PlayAwardLevel> levelList = query.list();
			lpm.setLevelList(levelList);
		}
		return lpms;
	}

	@Override
	public Map<String, Object> queryUserModelBonus(
			LotteryPlayModelVO lpmVo) throws Exception {
		//根据当前用户查询对应的总代用户是谁。
		String sql1 = "SELECT u.* FROM t_customer_user u WHERE u.customer_name=? ";
		SQLQuery query = getSession().createSQLQuery(sql1);
		query.setParameter(0, lpmVo.getUserName());
		query.addEntity(CustomerUser.class);
		CustomerUser user = (CustomerUser) query.list().get(0);
		String allParents = user.getAllParentAccount();
		String rootProxyId = allParents.split(",")[0];
		if(rootProxyId.equals(""))rootProxyId = Long.toString(user.getId());
		//用户返点值
		BigDecimal userRebates = user.getRebates();
		
		//根据当前用户查询对应的总代用户是谁。
		String sql3 = "SELECT u.* FROM t_customer_user u WHERE u.id = ? ";
		query = getSession().createSQLQuery(sql3);
		query.setParameter(0,rootProxyId);
		query.addEntity(CustomerUser.class);
		CustomerUser proxyUser = (CustomerUser) query.list().get(0);
		//计算总代与代理的点差
		BigDecimal proxyRebates = proxyUser.getRebates();
		BigDecimal rb = proxyRebates.subtract(userRebates);
		
		//查询用户对应的奖金组名称。
		String sql2 ="SELECT g1.bonus_name,nbg.rebates,nbg.bonus,g1.id,g1.payout_ratio FROM t_bonus_group g1 "
				+ ",t_norebates_bonus_group nbg WHERE g1.id = ("
				+ "SELECT g.bonus_id FROM t_user_bonus_group g WHERE g.customer_id = ?)"
				+ " and g1.id = nbg.bonus_group_id and nbg.rebates=? and nbg.status = ?";
		query = getSession().createSQLQuery(sql2);
		query.setParameter(0, rootProxyId);
		query.setParameter(1, userRebates);
		query.setParameter(2, DataDictionaryUtil.STATUS_OPEN);
		List<Object> objects = query.list();
		Map<String,Object> map1 = new HashMap<String,Object>();
		if(objects.size()==0)return map1;
		Object[] objs =  (Object[]) objects.get(0);
		map1.put("bonusName", objs[0]);
		map1.put("rebates", Double.parseDouble(objs[1].toString()));
		map1.put("bonus", objs[2]);
		map1.put("bgId", objs[3]);
		map1.put("payRatio", objs[4]);
		//总代与用户点差
		map1.put("stepRebates",rb);
		map1.put("pxRebates",proxyRebates);
		
		return map1;
	}
	
	
	@Override
	public Map<String, Object> queryUserModelNoBonus(
			LotteryPlayModelVO lpmVo) throws Exception {
		//根据当前用户查询对应的总代用户是谁。
		String sql1 = "SELECT u.* FROM t_customer_user u WHERE u.customer_name=? ";
		SQLQuery query = getSession().createSQLQuery(sql1);
		query.setParameter(0, lpmVo.getUserName());
		query.addEntity(CustomerUser.class);
		CustomerUser user = (CustomerUser) query.list().get(0);
		String allParents = user.getAllParentAccount();
		String rootProxyId = allParents.split(",")[0];
		if(rootProxyId.equals(""))rootProxyId = Long.toString(user.getId());
		//用户返点值
		BigDecimal userRebates = user.getRebates();
		
		//根据当前用户查询对应的总代用户是谁。
		String sql3 = "SELECT MAX(t.`rebates`) FROM t_norebates_bonus_group t,t_user_bonus_group t1 WHERE t.`bonus_group_id` = t1.`bonus_id` AND t1.`customer_id` = ? ";
		query = getSession().createSQLQuery(sql3);
		query.setParameter(0,rootProxyId);
		//query.addEntity(CustomerUser.class);
		//计算总代与代理的点差
		BigDecimal proxyRebates = (BigDecimal) query.list().get(0);
		BigDecimal rb = proxyRebates.subtract(userRebates);
		
		//查询用户对应的奖金组名称。
		String sql2 ="SELECT g1.bonus_name,nbg.rebates,nbg.bonus,g1.id,g1.payout_ratio FROM t_bonus_group g1 "
				+ ",t_norebates_bonus_group nbg WHERE g1.id = ("
				+ "SELECT g.bonus_id FROM t_user_bonus_group g WHERE g.customer_id = ?)"
				+ " and g1.id = nbg.bonus_group_id and nbg.rebates=? and nbg.status = ?";
		query = getSession().createSQLQuery(sql2);
		query.setParameter(0, rootProxyId);
		query.setParameter(1, userRebates);
		query.setParameter(2, DataDictionaryUtil.STATUS_OPEN);
		List<Object> objects = query.list();
		Map<String,Object> map1 = new HashMap<String,Object>();
		if(objects.size()==0)return map1;
		Object[] objs =  (Object[]) objects.get(0);
		map1.put("bonusName", objs[0]);
		map1.put("rebates", Double.parseDouble(objs[1].toString()));
		map1.put("bonus", objs[2]);
		map1.put("bgId", objs[3]);
		map1.put("payRatio", objs[4]);
		//总代与用户点差
		map1.put("stepRebates",rb);
		map1.put("pxRebates",proxyRebates);
		
		return map1;
	}

	@Override
	public LotteryPlayBonus queryBonusByCode(Map<String, Object> param)
			throws Exception {
		LotteryTypeVO lotVo = (LotteryTypeVO) param.get("lotteryKey");
		
		String hql = "from LotteryPlayBonus lpb where lpb.lotteryCode = :lc"
				+ " and status = :st ";
		Query query = getSession().createQuery(hql);
		query.setParameter("lc", lotVo.getLotteryCode());
		query.setParameter("st", DataDictionaryUtil.COMMON_FLAG_1);
		List<LotteryPlayBonus> list = query.list();
		return list.get(0);
	}


}
