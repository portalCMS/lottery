package com.lottery.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.lottery.bean.entity.ArticleManage;
import com.lottery.bean.entity.CustomerOrder;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.ArticleManageVO;
import com.lottery.bean.entity.vo.CustomerOrderVO;
import com.lottery.dao.IArticleManageDao;
import com.lottery.persist.generice.GenericDAO;
import com.xl.lottery.util.DataDictionaryUtil;
import com.xl.lottery.util.DateUtil;
@Repository
public class ArticleManageDaoImpl extends GenericDAO<ArticleManage> implements IArticleManageDao{

	public ArticleManageDaoImpl() {
		super(ArticleManage.class);
	}

	@Override
	public Page<ArticleManageVO, ArticleManage> queryNoticeArticle(Map<String, Object> param)
			throws Exception {
		ArticleManageVO vo = (ArticleManageVO) param.get("articleKey");
		
		List<String> limitKeys = new ArrayList<String>();
		List<String> formula = new ArrayList<String>();
		List<Object> limitVals = new ArrayList<Object>();
		
		formula.add("=");
		limitKeys.add("type");
		limitVals.add(DataDictionaryUtil.ARTICLE_TYPE_NOTICE);
		
		if(!StringUtils.isEmpty(vo.getDetailType())){
			formula.add("=");
			limitKeys.add("detailType");
			limitVals.add(vo.getDetailType());
		}
		if(vo.getKeyMark()>0){
			formula.add("=");
			limitKeys.add("keyMark");
			limitVals.add(vo.getKeyMark());
		}
		if(vo.getTopMark()>0){
			formula.add("=");
			limitKeys.add("topMark");
			limitVals.add(vo.getTopMark());
		}
		
		Page<ArticleManageVO, ArticleManage> page = (Page<ArticleManageVO, ArticleManage>)doPageQuery
				(vo, ArticleManage.class,formula, limitKeys, limitVals,true);
		
		return page;
	}

	@Override
	public Page<ArticleManageVO,ArticleManage> showHelpCenter(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		List<String> formula = new ArrayList<String>();
		List<String> limitKeys = new ArrayList<String>();
		List<Object> limitVals = new ArrayList<Object>();
		String code = (String) param.get("code");
		ArticleManageVO vo = (ArticleManageVO) param.get("articleManageVO");
		if(code!=null&&!code.equals("")){
			formula.add("=");
			limitKeys.add("type");
			limitVals.add(code);
		}
		if(vo!=null&&vo.getDetailType()!=null&&!vo.getDetailType().equals("")){
			formula.add("=");
			limitKeys.add("detailType");
			limitVals.add(vo.getDetailType());
		}
		Page<ArticleManageVO,ArticleManage> page = (Page<ArticleManageVO, ArticleManage>) doPageQuery(vo, ArticleManage.class, formula, limitKeys, limitVals, true);
		return page;
	}

	@Override
	public Long getClassSortAboutCount(String detailType) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer queryString = new StringBuffer("select count(t.id) from ArticleManage t where t.detailType = ?");
		Query query = getSession().createQuery(queryString.toString());
		query.setParameter(0, detailType);
		Long count = (Long) query.list().get(0);
		return count;
	}

	@Override
	public Page<ArticleManageVO, ArticleManage> queryWebAppNoticeArticle(
			Map<String, Object> param) throws Exception {
		ArticleManageVO vo = (ArticleManageVO) param.get("articleKey");
		StringBuffer queryString = new StringBuffer("from ArticleManage t where t.type = 'A2' order by t.topMark desc,createTime desc");
		StringBuffer hqlQueryCount = new StringBuffer("select count(t.id) from ArticleManage t where t.type = 'A2' order by t.topMark desc,createTime desc");
		List<String> limitKeys = new ArrayList<String>();
		List<Object> limitVals = new ArrayList<Object>();
		Page<ArticleManageVO, ArticleManage> page = (Page<ArticleManageVO, ArticleManage>)pageQuery(vo, queryString,hqlQueryCount,limitKeys, limitVals);
		return page;
	}

	@Override
	public List<ArticleManage> queryWebAppHelpCenterArticle(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		String type = (String) param.get("type");
		StringBuffer queryString = new StringBuffer("from ArticleManage t where t.type = ? and t.status != 10001 order by createTime desc");
		Query query = getSession().createQuery(queryString.toString());
		query.setParameter(0, type);
		return query.list();
	}

}
