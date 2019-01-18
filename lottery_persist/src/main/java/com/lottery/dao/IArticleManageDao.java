package com.lottery.dao;

import java.util.List;
import java.util.Map;
import java.math.BigInteger;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.ArticleManage;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.ArticleManageVO;
import com.lottery.persist.generice.IGenericDao;

@Repository
public interface IArticleManageDao extends IGenericDao<ArticleManage>{

	public Page<ArticleManageVO, ArticleManage> queryNoticeArticle(final Map<String, Object> param)throws Exception;

	
	public Page<ArticleManageVO,ArticleManage> showHelpCenter(final Map<String, Object> param)throws Exception;
	
	public Long getClassSortAboutCount(String detailType)throws Exception;
	
	/**
	 * 前台展示使用
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<ArticleManageVO, ArticleManage> queryWebAppNoticeArticle(final Map<String, Object> param)throws Exception;
	
	/**
	 * 帮助中心
	 */
	public List<ArticleManage> queryWebAppHelpCenterArticle(final Map<String, Object> param)throws Exception;
}
