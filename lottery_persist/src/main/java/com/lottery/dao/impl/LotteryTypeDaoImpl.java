package com.lottery.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.LotteryType;
import com.lottery.bean.entity.vo.LotteryTypeVO;
import com.lottery.dao.ILotteryTypeDao;
import com.lottery.persist.generice.GenericDAO;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.util.DataDictionaryUtil;

@Repository
public class LotteryTypeDaoImpl extends GenericDAO<LotteryType> implements
		ILotteryTypeDao {

	private static Logger logger = LoggerFactory.getLogger(LotteryType.class);
	
	public LotteryTypeDaoImpl() {
		super(LotteryType.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<LotteryType> queryLotteryList(Map<String, Object> param)
			throws Exception {
		LotteryTypeVO lotteryVo = (LotteryTypeVO) param.get("lotteryKey");
		Boolean isMobile = (Boolean) param.get("isMobile");
		StringBuffer hql = new StringBuffer("from LotteryType ");
		int j=0;
		if(lotteryVo.getLotteryStatus()!=0){
			j++;
			hql.append("where lotteryStatus = :ls ");
		}else if(lotteryVo.getStatusList()!=null&&lotteryVo.getStatusList().size()>0){
			j++;
			hql.append("where lotteryStatus in (:sl) ");
		}
		if(!StringUtils.isEmpty(lotteryVo.getLotteryCode())){
			if(j>0){
				hql.append(" and lotteryCode = :lc ");
			}else{
				hql.append(" where lotteryCode = :lc ");
			}
			j++;
		}
		if(lotteryVo.getLotteryLevel()!=0){
			if(j>0){
				hql.append(" and lotteryLevel = :le ");
			}else{
				hql.append(" where lotteryLevel = :le");
			}
			j++;
			
		}
		if(!StringUtils.isEmpty(lotteryVo.getIdList())){
			if(j>0){
				hql.append(" and id in (:idList) ");
			}else{
				hql.append(" where id in (:idList) ");
			}
			j++;
		}
		//手机要加载的彩种控制
		if(isMobile!=null&&isMobile){
			if(j>0){
				hql.append(" and rsvst3 =:rs ");
			}else{
				hql.append(" where rsvst3 =:rs ");
			}
			j++;
		}
		if(!StringUtils.isEmpty(lotteryVo.getLotteryGroup())){
			if(j>0){
				hql.append(" and lotteryGroup = :lg");
			}else{
				hql.append(" where lotteryGroup = :lg");
			}
			j++;
		}
		
		Query query = getSession().createQuery(hql.toString());
		int i=0;
		if(lotteryVo.getLotteryStatus()!=0){
			query.setParameter("ls",lotteryVo.getLotteryStatus());
		}else if(lotteryVo.getStatusList()!=null&&lotteryVo.getStatusList().size()>0){
			query.setParameterList("sl",lotteryVo.getStatusList());
		}
		if(!StringUtils.isEmpty(lotteryVo.getLotteryCode())){
			query.setParameter("lc",lotteryVo.getLotteryCode());
		}
		
		if(lotteryVo.getLotteryLevel()!=0){
			query.setParameter("le",lotteryVo.getLotteryLevel());
		}
		if(!StringUtils.isEmpty(lotteryVo.getLotteryGroup())){
			query.setParameter("lg",lotteryVo.getLotteryGroup());
		}
		
		if(!StringUtils.isEmpty(lotteryVo.getIdList())){
			List<Long> idList = new ArrayList<Long>();
			String[] idArray = lotteryVo.getIdList().split(",");
			for(int k=0 ;k<idArray.length;k++){
				idList.add(Long.parseLong(idArray[k]));
			}
			query.setParameterList("idList", idList);
		}
		
		//手机要加载的彩种控制
		if(isMobile!=null&&isMobile){
			query.setParameter("rs", DataDictionaryUtil.COMMON_FLAG_1+"");
		}
		
		return query.list();
	}

	@Override
	public List<LotteryType> queryLotteryTypeByGroupCode(Map<String, ?> param)
			throws Exception {
		// TODO Auto-generated method stub
		String groupCode = (String) param.get("groupCodeKey");
		StringBuffer queryString = new StringBuffer(" from LotteryType t where t.lotteryLevel = 1 and t.lotteryGroup in (:groupCode) ");
		Query query = getSession().createQuery(queryString.toString());
		query.setParameterList("groupCode", groupCode.split(","));
		List<LotteryType> types = query.list();
		//if(types==null||types.size()==0)throw new LotteryException("彩种不存在");
		return types;
	}

	@Override
	public String queryLotteryTypeNameByCode(String code) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer queryString = new StringBuffer("select lotteryName from LotteryType t where t.lotteryCode = ?");
		Query query = getSession().createQuery(queryString.toString());
		query.setParameter(0, code);
		if(query.list().size() == 0)return "";
		String typeName = (String) query.list().get(0);
		return typeName;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LotteryType> queryLotteryTypeAll(Map<String, ?> param)
			throws Exception {
		// TODO Auto-generated method stub
		StringBuffer queryString = new StringBuffer("from LotteryType t where t.lotteryStatus = 10002 and t.lotteryLevel = 1 ");
		Query query = getSession().createQuery(queryString.toString());
		List<LotteryType> types = query.list();
		return types;
	}

	@Override
	public LotteryType updateLottery(Map<String, Object> param)
			throws Exception {
		LotteryTypeVO lotteryVo = (LotteryTypeVO) param.get("lotteryKey");
		LotteryType orignalLottery = this.queryById(lotteryVo.getId());
		orignalLottery.setLotteryCode(lotteryVo.getLotteryCode());
		orignalLottery.setLotteryName(lotteryVo.getLotteryName());
		orignalLottery.setLotteryStatus(lotteryVo.getLotteryStatus());
		this.update(orignalLottery);
		//对应的奖期任务状态也修改掉。
		String hql ="update TaskConfig set taskStatus = ? where lotteryCode =? ";
		Query query = getSession().createQuery(hql);
		query.setParameter(0, lotteryVo.getLotteryStatus());
		query.setParameter(1, lotteryVo.getLotteryCode());
		query.executeUpdate();
		return orignalLottery;
	}

}
