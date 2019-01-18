package com.lottery.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.ClassSort;
import com.lottery.persist.generice.IGenericDao;

@Repository
public interface IClassSortDao extends IGenericDao<ClassSort>{

	public List<ClassSort> findClassSortsByType(Map<String, Object> param)throws Exception;
	
	public ClassSort findClassSortByName(String name)throws Exception;
	
	public String getDatailNameByDatailType(String datailType)throws Exception;
	
}
