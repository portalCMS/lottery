package com.lottery.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.ClassSort;
import com.lottery.dao.IClassSortDao;
import com.lottery.persist.generice.GenericDAO;

@Repository
public class ClassSortDaoImpl extends GenericDAO<ClassSort> implements IClassSortDao{

	public ClassSortDaoImpl() {
		super(ClassSort.class);
	}

	@Override
	public List<ClassSort> findClassSortsByType(Map<String, Object> param)throws Exception {
		String code = (String) param.get("code");
		StringBuffer queryString = new StringBuffer("from ClassSort t where t.type = ? order by t.createTime desc");
		Query query = getSession().createQuery(queryString.toString());
		query.setParameter(0, code);
		List<ClassSort> list = query.list();
		return list;
	}

	@Override
	public ClassSort findClassSortByName(String name)throws Exception {
		// TODO Auto-generated method stub
		StringBuffer queryString  = new StringBuffer("from ClassSort t where t.datailName = ?");
		Query query = getSession().createQuery(queryString.toString());
		query.setParameter(0, name);
		List<ClassSort> list = query.list();
		if(list.size()==0)
		return null;
		return list.get(0);
	}

	@Override
	public String getDatailNameByDatailType(String datailType) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer queryString  = new StringBuffer("from ClassSort t where t.detailType = ?");
		Query query = getSession().createQuery(queryString.toString());
		query.setParameter(0, datailType);
		List<ClassSort> list = query.list();
		if(list.size()==0)
		return null;
		return list.get(0).getDatailName();
	}

}
