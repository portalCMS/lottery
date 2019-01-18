package com.lottery.service.impl;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.bean.entity.ArticleManage;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.ArticleManageVO;
import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.ArticleManage;
import com.lottery.bean.entity.ClassSort;
import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.ArticleManageVO;
import com.lottery.bean.entity.vo.ClassSortVO;
import com.lottery.dao.IArticleManageDao;
import com.lottery.dao.IClassSortDao;
import com.lottery.dao.impl.AdminWriteLog;
import com.lottery.dao.impl.CustomerUserWriteLog;
import com.lottery.service.IArticleManageService;
import com.xl.lottery.util.DateUtil;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;
import com.xl.lottery.util.DozermapperUtil;

@Service
public class ArticleManageServiceImpl implements IArticleManageService{

	@Autowired
	private IArticleManageDao articleDao;
	
	@Autowired
	private IClassSortDao classSortDao;
	
	@Autowired
	private AdminWriteLog adminLog;
	
	@Autowired
	private CustomerUserWriteLog userlog;
	
	@Override
	public void saveNoticeArticle(Map<String, Object> param) throws Exception {
		ArticleManageVO vo = (ArticleManageVO) param.get("articleKey");
		AdminUser user = (AdminUser) param.get(CommonUtil.USERKEY);
		ArticleManage article = new ArticleManage();
		article.setTitle(vo.getTitle());
		article.setContent(vo.getContent());
		article.setType(vo.getType());
		article.setDetailType(vo.getDetailType());
		article.setTopMark(vo.getTopMark());
		article.setKeyMark(vo.getKeyMark());
		article.setStatus(vo.getStatus());
		article.addInit(user.getUserName());
		articleDao.save(article);
		adminLog.saveWriteLog(user, CommonUtil.SAVE, "t_article_manage", article.toString());
	}

	@Override
	public Page<ArticleManageVO, ArticleManage> queryNoticeArticle(Map<String, Object> param)
			throws Exception {
		return articleDao.queryNoticeArticle(param);
	}

	@Override
	public Page<ArticleManageVO,ArticleManage> showHelpCenter(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		Page<ArticleManageVO, ArticleManage> page = articleDao.showHelpCenter(param);
		List<ClassSort> sorts = classSortDao.findClassSortsByType(param);
		Map<String, String> temp = new HashMap<String, String>();
		for(ClassSort cs:sorts){
			temp.put(cs.getDetailType(), cs.getDatailName());
		}
		List<ArticleManage> entitys = page.getEntitylist();
		List<ArticleManageVO> vos = new ArrayList<ArticleManageVO>();
		for(ArticleManage entity:entitys){
			ArticleManageVO vo = new ArticleManageVO();
			DozermapperUtil.getInstance().map(entity, vo);
			vo.setDetailTypeName(temp.get(vo.getDetailType()));
			vos.add(vo);
		}
		page.setPagelist(vos);
		return page;
	}

	@Override
	public String updateArticleManage(Map<String, Object> param)throws Exception {
		// TODO Auto-generated method stub
		AdminUser user = (AdminUser) param.get(CommonUtil.USERKEY);
		ArticleManageVO vo = (ArticleManageVO) param.get("amvo");
		if(vo.getId()==0)throw new LotteryException("记录不存在");
		ArticleManage entity = articleDao.queryById(vo.getId());
		if(!StringUtils.isEmpty(vo.getTitle())){
			entity.setTitle(vo.getTitle());
		}
		if(!StringUtils.isEmpty(vo.getDetailType())){
			entity.setDetailType(vo.getDetailType());
		}
		if(!StringUtils.isEmpty(vo.getContent())){
			entity.setContent(vo.getContent());
		}
		if(vo.getStatus()!=0){
			entity.setStatus(vo.getStatus());
		}
		if(vo.getTopMark()!=0){
			entity.setTopMark(vo.getTopMark());
		}
		if(vo.getKeyMark()!=0){
			entity.setKeyMark(vo.getKeyMark());
		}
		entity.updateInit(user.getUserName());
		articleDao.update(entity);
		adminLog.saveWriteLog(user, CommonUtil.UPDATE, "t_article_manage", entity.toString());
		return "success";
	}

	@Override
	public ArticleManage showUpdateArticleMange(Map<String, Object> param)
			throws Exception {
		// TODO Auto-generated method stub
		ArticleManageVO vo = (ArticleManageVO) param.get("amvo");
		if(vo.getId()==0)throw new LotteryException("记录不存在");
		ArticleManage entity = articleDao.queryById(vo.getId());
		return entity;
	}

	@Override
	public String deleteArticleManage(Map<String, Object> param)
			throws Exception {
		// TODO Auto-generated method stub
		AdminUser user = (AdminUser) param.get(CommonUtil.USERKEY);
		ArticleManageVO vo = (ArticleManageVO) param.get("amvo");
		if(vo.getId()==0)throw new LotteryException("记录不存在");
		ArticleManage entity = articleDao.queryById(vo.getId());
		articleDao.delete(entity);
		adminLog.saveWriteLog(user, CommonUtil.DELETE, "t_article_manage", entity.toString());
		return "success";
	}

	@Override
	public List<ArticleManageVO> findNewNoticeArrticle(Map<String, Object> param)
			throws Exception {
		// TODO Auto-generated method stub
		String type = (String)param.get("type");
		int maxResults = 4;
		if(param.get("max")!=null)maxResults=(int) param.get("max");
		StringBuffer queryString = new StringBuffer("from ArticleManage t where t.type=? order by createTime desc ");
		Query query = articleDao.getSession().createQuery(queryString.toString());
		query.setParameter(0, type);
		query.setMaxResults(maxResults);
		query.setFirstResult(0);
		List<ArticleManage> entitys = query.list();
		List<ArticleManageVO> vos = new ArrayList<ArticleManageVO>();
		for(ArticleManage entity:entitys){
			ArticleManageVO vo = new ArticleManageVO();
			DozermapperUtil.getInstance().map(entity, vo);
			vo.setDetailTypeName(classSortDao.getDatailNameByDatailType(vo.getDetailType()));
			vo.setContent(StringEscapeUtils.unescapeHtml3(vo.getContent()));
			vos.add(vo);
		}
		return vos;
	}

	@Override
	public ArticleManageVO getNoticeArticleById(Map<String, Object> param)
			throws Exception {
		// TODO Auto-generated method stub
		ArticleManageVO vo = (ArticleManageVO) param.get("amvo");
		if(vo.getId()==0)throw new LotteryException("记录不存在");
		ArticleManage entity = articleDao.queryById(vo.getId());
		ArticleManageVO voNew = new ArticleManageVO();
		DozermapperUtil.getInstance().map(entity, voNew);
		voNew.setDetailTypeName(classSortDao.getDatailNameByDatailType(voNew.getDetailType()));
		return voNew;
	}

	@Override
	public List<ArticleManageVO> findDefaultNoticeArrticle(
			Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		String type = (String)param.get("type");
		int maxResults = 4;
		if(param.get("max")!=null)maxResults=(int) param.get("max");
		StringBuffer queryString = new StringBuffer("from ArticleManage t where t.type=? order by topMark desc, createTime desc ");
		Query query = articleDao.getSession().createQuery(queryString.toString());
		query.setParameter(0, type);
		query.setMaxResults(maxResults);
		query.setFirstResult(0);
		List<ArticleManage> entitys = query.list();
		List<ArticleManageVO> vos = new ArrayList<ArticleManageVO>();
		for(ArticleManage entity:entitys){
			ArticleManageVO vo = new ArticleManageVO();
			DozermapperUtil.getInstance().map(entity, vo);
			vo.setDetailTypeName(classSortDao.getDatailNameByDatailType(vo.getDetailType()));
			vos.add(vo);
		}
		return vos;
	}

	@Override
	public Page<ArticleManageVO, ArticleManage> queryWebAppNoticeArticle(
			Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		Page<ArticleManageVO, ArticleManage> page = articleDao.queryWebAppNoticeArticle(param);
		List<ArticleManage> entitys = page.getEntitylist();
		List<ArticleManageVO> vos = new ArrayList<ArticleManageVO>();
		for(ArticleManage entity:entitys){
			ArticleManageVO vo = new ArticleManageVO();
			DozermapperUtil.getInstance().map(entity, vo);
			vo.setDetailTypeName(classSortDao.getDatailNameByDatailType(vo.getDetailType()));
			vo.setContent(StringEscapeUtils.unescapeHtml3(vo.getContent()));
			vos.add(vo);
		}
		page.setPagelist(vos);
		return page;
	}

	@Override
	public List<ArticleManageVO> queryWebAppHelpCenterArticle(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stubList<ClassSort> sorts = classSortDao.findClassSortsByType(param);
		Map<String, String> temp = new HashMap<String, String>();
		List<ClassSort> sorts = classSortDao.findClassSortsByType(param);
		for(ClassSort cs:sorts){
			temp.put(cs.getDetailType(), cs.getDatailName());
		}
		List<ArticleManage> entitys = articleDao.queryWebAppHelpCenterArticle(param);
		List<ArticleManageVO> vos = new ArrayList<ArticleManageVO>();
		for(ArticleManage entity:entitys){
			ArticleManageVO vo = new ArticleManageVO();
			DozermapperUtil.getInstance().map(entity, vo);
			vo.setDetailTypeName(temp.get(vo.getDetailType()));
			vo.setContent(StringEscapeUtils.unescapeHtml3(vo.getContent()));
			vos.add(vo);
		}
		return vos;
	}

	@Override
	public String updateHelpArticle(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		ArticleManageVO vo = (ArticleManageVO) param.get("amvo");
		CustomerUser user = (CustomerUser) param.get(CommonUtil.CUSTOMERUSERKEY);
		if(vo.getId()==0)throw new LotteryException("记录不存在");
		ArticleManage entity = articleDao.queryById(vo.getId());
		if(!StringUtils.isEmpty(vo.getIsApplaud())){
			entity.setApplaud(entity.getApplaud()+1);
		}
		if(!StringUtils.isEmpty(vo.getIsOppose())){
			entity.setOppose(entity.getOppose()+1);
		}
		entity.updateInit(user.getCustomerName());
		articleDao.update(entity);
		userlog.saveWriteLog(user, CommonUtil.UPDATE, "ArticleManage", entity.toString());
		return "success";
	}


}
