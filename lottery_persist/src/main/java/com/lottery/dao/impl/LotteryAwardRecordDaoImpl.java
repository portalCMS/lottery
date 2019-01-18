package com.lottery.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.lottery.bean.entity.ArticleManage;
import com.lottery.bean.entity.LotteryAwardRecord;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.ArticleManageVO;
import com.lottery.bean.entity.vo.LotteryAwardRecordVO;
import com.lottery.bean.entity.vo.LotteryTypeVO;
import com.lottery.dao.ILotteryAwardRecordDao;
import com.lottery.persist.generice.GenericDAO;
import com.xl.lottery.util.DataDictionaryUtil;

@Repository
public class LotteryAwardRecordDaoImpl extends GenericDAO<LotteryAwardRecord> implements ILotteryAwardRecordDao{

	public LotteryAwardRecordDaoImpl() {
		super(LotteryAwardRecord.class);
	}

	@Override
	public List<LotteryAwardRecord> queryRecentAwardRecord(
			Map<String, Object> param) throws Exception {
		LotteryTypeVO vo = (LotteryTypeVO) param.get("lotteryKey");
		String hql = "from LotteryAwardRecord where lottery_code=? and status != ? order by id desc";
		Query query = getSession().createQuery(hql);
		query.setParameter(0, vo.getLotteryCode());
		query.setParameter(1, DataDictionaryUtil.STATUS_DELETE);
		query.setFirstResult(0);
		query.setMaxResults(9);
		return query.list();
	}

	@Override
	public Page<LotteryAwardRecordVO, LotteryAwardRecord> queryFailedTask(Map<String, Object> param)
			throws Exception {
		LotteryAwardRecordVO vo = (LotteryAwardRecordVO) param.get("awardKey");
		
		StringBuffer hql=new StringBuffer("from LotteryAwardRecord where status != ? "
				+ " or openType in (:ots) order by createTime desc");
		StringBuffer countHql = new StringBuffer("select count(id) from LotteryAwardRecord "
				+ " where status != ? or openType in (:ots) ");
		
		List<String> limitKeys = new ArrayList<String>();
		List<Object> limitVals = new ArrayList<Object>();
		
		limitKeys.add("status");
		limitVals.add(DataDictionaryUtil.STATUS_SUCCESS);
		
		List<String> ots = new ArrayList<String>();
		ots.add(DataDictionaryUtil.AWARD_OPEN_TYPE_HAUTO);
		ots.add(DataDictionaryUtil.AWARD_OPEN_TYPE_HAND);
		
		limitKeys.add("ots");
		limitVals.add(ots);
		
		Page<LotteryAwardRecordVO, LotteryAwardRecord> page = 
				(Page<LotteryAwardRecordVO, LotteryAwardRecord>)pageQuery(vo, hql,countHql, limitKeys,limitVals);
		
		return page;
	}

	/**
	 * 调用撤销中奖记录的存储过程
	 */
	@Override
	public void cancelAward(Map<String, Object> param) throws Exception {
		final String lotteryCode = (String) param.get("lotCodeKey");
		final String issueNo = (String) param.get("issueKey");
		getSession().doWork(new Work() {
			@Override
			public void execute(Connection connection) throws SQLException {
				 CallableStatement cs1 = null;
				try {
					//对此期的所有中奖记录进行撤销的操作。
					 cs1 = connection.prepareCall("{ call p_cancel_award(?,?)}");
					 cs1.setString(1, lotteryCode);
					 cs1.setString(2, issueNo);
					 cs1.execute();
				} catch (SQLException e) {
					e.printStackTrace();
				}finally{
					cs1.close();
				}
			}
		});
	}

	/**
	 * 最近一天开奖结果的号码冷热
	 */
	@Override
	public List<LotteryAwardRecord> queryLimitAwardRecord(Map<String, Object> param)
			throws Exception {
		LotteryTypeVO vo = (LotteryTypeVO) param.get("lotteryKey");
		String hql = "from LotteryAwardRecord where lotteryCode=? and status in (:sts) order by issue desc";
		Query query = getSession().createQuery(hql);
		query.setParameter(0, vo.getLotteryCode());
		List<Integer> sts = new ArrayList<Integer>();
		sts.add(DataDictionaryUtil.STATUS_SUCCESS);
		sts.add(DataDictionaryUtil.HAND_STATUS_SUCCESS);
		query.setParameterList("sts", sts);
		query.setFirstResult(0);
		query.setMaxResults(vo.getTotalTimes());
		return query.list();
	}
	/**
	 * 最近一天开奖结果的号码冷热
	 */
	@Override
	public List<LotteryAwardRecord> queryLimitAwardRecord2(Map<String, Object> param)
			throws Exception {
		LotteryTypeVO vo = (LotteryTypeVO) param.get("lotteryKey");
		String sql = "select * from t_lottery_award_record where lottery_code= ? ";
		if(null!=vo.getStartTime()){
			sql +=" and create_time >= ? and create_time <? ";
		}
		sql	+=  "and status in (:sts) order by issue desc";
		
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter(0, vo.getLotteryCode());
		
		if(null!=vo.getStartTime()){
			query.setParameter(1, vo.getStartTime());
			query.setParameter(2, vo.getEndTime());
		}
		
		List<Integer> sts = new ArrayList<Integer>();
		sts.add(DataDictionaryUtil.STATUS_SUCCESS);
		sts.add(DataDictionaryUtil.HAND_STATUS_SUCCESS);
		query.setParameterList("sts", sts.toArray());
		
		if(vo.getTotalTimes()>0){
			query.setFirstResult(0);
			query.setMaxResults(vo.getTotalTimes());
		}
		
		query.addEntity(LotteryAwardRecord.class);
		return query.list();
	}

	@Override
	public Page<LotteryAwardRecordVO, LotteryAwardRecord> queryOneRecord(
			Map<String, Object> param) throws Exception {
		LotteryAwardRecordVO vo = (LotteryAwardRecordVO) param.get("awardKey");
		
		StringBuffer hql=new StringBuffer("from LotteryAwardRecord where lotteryCode = ? "
												+ " and issue = ?  order by createTime desc");
		StringBuffer countHql = new StringBuffer("select count(id) from LotteryAwardRecord where lotteryCode = ? "
													+ " and issue = ?  ");
		
		List<String> limitKeys = new ArrayList<String>();
		List<Object> limitVals = new ArrayList<Object>();
		
		limitVals.add(vo.getLotteryCode());
		limitVals.add(vo.getIssue());
		
		Page<LotteryAwardRecordVO, LotteryAwardRecord> page = 
				(Page<LotteryAwardRecordVO, LotteryAwardRecord>)pageQuery(vo, hql,countHql, limitKeys,limitVals);
		
		return page;
	}
	
	@Override
	public Page<LotteryAwardRecordVO, LotteryAwardRecord> queryCodesRecord(
			Map<String, Object> param) throws Exception {
		LotteryAwardRecordVO vo = (LotteryAwardRecordVO) param.get("awardKey");
		
		StringBuffer hql=new StringBuffer("from LotteryAwardRecord where lotteryCode = ? "
												+ " order by createTime desc");
		StringBuffer countHql = new StringBuffer("select count(id) from LotteryAwardRecord where lotteryCode = ? "
													+ "");
		
		List<String> limitKeys = new ArrayList<String>();
		List<Object> limitVals = new ArrayList<Object>();
		limitVals.add(vo.getLotteryCode());
		//limitVals.add(vo.getIssue());
		
		Page<LotteryAwardRecordVO, LotteryAwardRecord> page = 
				(Page<LotteryAwardRecordVO, LotteryAwardRecord>)pageQuery(vo, hql,countHql, limitKeys,limitVals);
		
		return page;
	}
}
