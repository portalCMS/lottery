package com.lottery.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.ClassSort;
import com.lottery.bean.entity.vo.ClassSortVO;
import com.lottery.dao.IArticleManageDao;
import com.lottery.dao.IClassSortDao;
import com.lottery.dao.impl.AdminWriteLog;
import com.lottery.service.IClassSortService;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;
import com.xl.lottery.util.DozermapperUtil;
import com.xl.lottery.util.RandDomUtil;

@Service
public class ClassSortServiceImol implements IClassSortService{

	@Autowired
	private IClassSortDao classDao;
	
	@Autowired
	private IArticleManageDao articleManageDao;
	
	@Autowired
	private AdminWriteLog adminWriteLog;
	
	@Override
	public List<ClassSortVO> findClassSorts(Map<String, Object> param)
			throws Exception {
		// TODO Auto-generated method stub
		List<ClassSort> list = classDao.findClassSortsByType(param);
		List<ClassSortVO> vos = new ArrayList<ClassSortVO>();
		for(ClassSort entity : list){
			ClassSortVO vo = new ClassSortVO();
			DozermapperUtil.getInstance().map(entity, vo);
			vo.setCount(articleManageDao.getClassSortAboutCount(vo.getDetailType()).intValue());
			if(vo.getStatus()==DataDictionaryUtil.STATUS_OPEN){
				vo.setStatusStr("显示");
			}else{
				vo.setStatusStr("隐藏");
			}
			vos.add(vo);
		}
		return vos;
	}

	@Override
	public ClassSort saveHelpCenterSort(Map<String, Object> param)
			throws Exception {
		// TODO Auto-generated method stub
		AdminUser user = (AdminUser) param.get(CommonUtil.USERKEY);
		ClassSortVO vo = (ClassSortVO) param.get("amvo");
		ClassSort obj = null;
		if(classDao.findClassSortByName(vo.getDatailName())==null){
			ClassSort entity = new ClassSort();
			DozermapperUtil.getInstance().map(vo, entity);
			entity.setStatus(DataDictionaryUtil.STATUS_OPEN);
			entity.addInit(user.getUserName());
			entity.setDetailType(UUID.randomUUID().toString().replaceAll("-", ""));
			classDao.save(entity);
			adminWriteLog.saveWriteLog(user, CommonUtil.SAVE, "t_class_sort", entity.toString());
			obj = entity;
		}else{
			throw new LotteryException("分类已存在");
		}
		return obj;
	}

	@Override
	public ClassSort updateHelpCenterSort(Map<String, Object> param)
			throws Exception {
		// TODO Auto-generated method stub
		AdminUser user = (AdminUser) param.get(CommonUtil.USERKEY);
		ClassSortVO vo = (ClassSortVO) param.get("amvo");
		ClassSort obj = null;
		if(vo.getId()==0)throw new LotteryException("该记录不存在");
		if(classDao.findClassSortByName(vo.getDatailName())==null||classDao.findClassSortByName(vo.getDatailName()).getId()==vo.getId()){
			ClassSort entity = classDao.queryById(vo.getId());
			if(entity==null)throw new LotteryException("该记录不存在");
			entity.setDatailName(vo.getDatailName());
			entity.setStatus(vo.getStatus());
			entity.updateInit(user.getUserName());
			classDao.update(entity);
			adminWriteLog.saveWriteLog(user, CommonUtil.UPDATE, "t_class_sort", entity.toString());
			obj = entity;
		}else{
			throw new LotteryException("分类已存在");
		}
		return obj;
	}

	@Override
	public List<ClassSort> findClassSortsbyother(Map<String, Object> param)
			throws Exception {
		// TODO Auto-generated method stub
		ClassSortVO vo = (ClassSortVO) param.get("amvo");
		StringBuffer queryString = new StringBuffer("from ClassSort t where t.type = ? and t.id != ? ");
		Query query = classDao.getSession().createQuery(queryString.toString());
		query.setParameter(0, vo.getType());
		query.setParameter(1, vo.getId());
		return query.list();
	}

	@Override
	public ClassSort deleteHelpCenterSort(Map<String, Object> param)
			throws Exception {
		// TODO Auto-generated method stub
		AdminUser user = (AdminUser) param.get(CommonUtil.USERKEY);
		ClassSortVO vo = (ClassSortVO) param.get("amvo");
		ClassSort entity = classDao.queryById(vo.getId());
		StringBuffer updateString = new StringBuffer("update ArticleManage set detailType = ? where detailType = ?");
		Query query = articleManageDao.getSession().createQuery(updateString.toString());
		query.setParameter(0, vo.getDetailType());
		query.setParameter(1, entity.getDetailType());
		query.executeUpdate();
		classDao.delete(entity);
		adminWriteLog.saveWriteLog(user, CommonUtil.DELETE, "t_class_sort", entity.toString());
		return null;
	}

	@Override
	public List<ClassSort> findClassSortsbyWebApp(Map<String, Object> param)
			throws Exception {
		// TODO Auto-generated method stub
		String type = (String) param.get("type");
		StringBuffer queryString = new StringBuffer("from ClassSort t where t.type = ? and t.status != 10001 ");
		Query query = classDao.getSession().createQuery(queryString.toString());
		query.setParameter(0, type);
		return query.list();
	}

}
