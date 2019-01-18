package com.lottery.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.LotteryPlayBonus;
import com.lottery.bean.entity.LotteryType;
import com.lottery.bean.entity.PlayModel;
import com.lottery.dao.ILotteryPlayBonusDao;
import com.lottery.dao.IPlayModelDao;
import com.lottery.persist.generice.GenericDAO;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.util.DataDictionaryUtil;

@Repository
public class PlayModelDaoImpl extends GenericDAO<PlayModel> implements IPlayModelDao{

	public PlayModelDaoImpl() {
		super(PlayModel.class);
	}

	@Override
	public List<PlayModel> queryPlayModel(Map<String, Object> param) {
		String groupCode = (String) param.get("groupKey");
		List<Long> idList =(List<Long>) param.get("idListKey");
		String hql = "from PlayModel where status = ? and lotteryGroup = ? ";
		if(idList!=null&&idList.size()>0){
			hql += " and id in (:idList)";
		}
		Query query = getSession().createQuery(hql);
		query.setParameter(0, DataDictionaryUtil.COMMON_FLAG_1);
		query.setParameter(1, groupCode);
		if(idList!=null&&idList.size()>0){
			query.setParameterList("idList", idList);
		}
		List<PlayModel> list = query.list();
		return list;
	}

	@Override
	public PlayModel queryPlayModelByCode(String modelCode) {
		String hql ="from PlayModel where status =? and modelCode=?";
		Query query = getSession().createQuery(hql);
		query.setParameter(0, DataDictionaryUtil.COMMON_FLAG_1);
		query.setParameter(1, modelCode);
		return (PlayModel) query.list().get(0);
	}

	@Override
	public List<PlayModel> queryPlayModelByGroupCode(Map<String, ?> param)
			throws Exception {
		String groupCode = (String) param.get("groupCodeKey");
		StringBuffer queryString = new StringBuffer(" from PlayModel t where t.lotteryGroup in (:groupCode) ");
		Query query = getSession().createQuery(queryString.toString());
		query.setParameterList("groupCode", groupCode.split(","));
		List<PlayModel> playModels = query.list();
		//if(playModels==null||playModels.size()==0)throw new LotteryException("彩种不存在");
		return playModels;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PlayModel> queryPlayModelByLotteryCode(Map<String, ?> param)
			throws Exception {
		// TODO Auto-generated method stub
		String lotteryCode = (String) param.get("lotteryCodeKey");
		StringBuffer queryString = new StringBuffer("select t from PlayModel t,LotteryPlayModel t1 where t.modelCode = t1.modelCode ");
		queryString.append(" and t1.status = 1 ");
		queryString.append(" and t1.lotteryCode = ? ");
		Query query = getSession().createQuery(queryString.toString());
		query.setParameter(0, lotteryCode);
		List<PlayModel> playModels = query.list();
		return playModels;
	}


}
