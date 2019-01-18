package com.lottery.persist.generice;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.lottery.bean.entity.GenericEntity;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.GenericEntityVO;

public abstract class GenericDAO<T> implements IGenericDao<T> {  
	  
    private Class<T> entityClass;  
  
    public GenericDAO(Class<T> clazz) {  
        this.entityClass = clazz;  
    }  
  
    @Autowired  
    private SessionFactory sessionFactory;  
  
    public void insert(T t) {  
        sessionFactory.getCurrentSession().save(t);  
    }  
    
    @Override
    public void save(T t){
    	sessionFactory.getCurrentSession().save(t);  
    }
  
    @Override  
    public void delete(T t) {  
        sessionFactory.getCurrentSession().delete(t);  
    }  
  
    @Override  
    public void update(T t) {  
        sessionFactory.getCurrentSession().update(t);  
    }
    
    @Override  
    public void persist(T t) {  
        sessionFactory.getCurrentSession().persist(t);  
    }
  
    @SuppressWarnings("unchecked")  
    @Override  
    public T queryById(long id) {  
        return (T) sessionFactory.getCurrentSession().get(entityClass, id);  
    }  
  
    @Override  
    public List<T> queryAll() {  
        String hql = "from " + entityClass.getSimpleName();  
        return queryForList(hql,null, null);  
    }  
  
    @SuppressWarnings("unchecked")  
    protected T queryForObject(String hql, Object[] params) {  
        Query query = sessionFactory.getCurrentSession().createQuery(hql);  
        setQueryParams(query, params);  
        return (T) query.uniqueResult();  
    }  
  
    @SuppressWarnings("unchecked")  
    protected T queryForTopObject(String hql, Object[] params) {  
        Query query = sessionFactory.getCurrentSession().createQuery(hql);  
        setQueryParams(query, params);  
        return (T) query.setFirstResult(0).setMaxResults(1).uniqueResult();  
    }  
  
    @SuppressWarnings("unchecked")  
    protected List<T> queryForList(String hql,Object[] keys, Object[] values) {  
        Query query = sessionFactory.getCurrentSession().createQuery(hql); 
        if(null!=values){
        	for(int i=0;i<values.length;i++){
            	if(values[i] instanceof Date){
            		query.setTimestamp(i, (Date) values[i]);
            	}else if(values[i] instanceof List){
            		query.setParameterList((String) keys[i], (Collection) values[i]);
            	}
            	else{
            		query.setParameter(i, values[i]);
            	}
            	
            }
        }
        
          
        return query.list();  
    }  
    
    @SuppressWarnings("rawtypes")
	protected int queryListCount(String hql,Object[] keys, Object[] values) { 
    	if(hql.indexOf("select count")==-1){
    		hql = "select count(*) "+hql;
    	}
        Query query = sessionFactory.getCurrentSession().createQuery(hql); 
        if(null!=values){
        	for(int i=0;i<values.length;i++){
            	if(values[i] instanceof Date){
            		query.setTimestamp(i, (Date) values[i]);
            	}else if(values[i] instanceof List){
            		query.setParameterList((String) keys[i], (Collection) values[i]);
            	}
            	else{
            		query.setParameter(i, values[i]);
            	}
            	
            }
        }
        if(query.list().size() == 0) return 0;
        Long count = (Long) query.list().get(0);
        return count.intValue();  
    }
  
    @SuppressWarnings("unchecked")  
    protected List<Object[]> queryForObjectList(String sql, Object[] params) {  
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql); 
        if(null!=params){
        	for(int i=0;i<params.length;i++){
            	if(params[i] instanceof Date){
            		query.setTimestamp(i, (Date) params[i]);
            	}else{
            		query.setParameter(i, params[i]);
            	}
            	
            }
        }
        return query.list();  
    } 
    
    @SuppressWarnings("unchecked")  
    protected List<T> queryForList(final String hql, final Object[] params,  
            final int recordNum) {  
        Query query = sessionFactory.getCurrentSession().createQuery(hql);  
        setQueryParams(query, params);  
        return query.setFirstResult(0).setMaxResults(recordNum).list();  
    }  
  
    private void setQueryParams(Query query, Object[] params) {  
        if (null == params) {  
            return;  
        }  
        for (int i = 0; i < params.length; i++) {  
            query.setParameter(i, params[i]);  
        }  
    }
    
    public Session getSession(){
    	return sessionFactory.getCurrentSession();
    }
    
    /**
     * 查询分页通用方法
     * @author swim
     * @param vo 
     * @param entity
     * @param formula 判断公式（例如：‘=’，‘！=’，‘>’,'<'）
     * @param limitKeys 条件列（例如：‘status’）
     * @param limitVals 条件值列（例如：‘1’）
     * @return
     */
    public Page<?, ?> doPageQuery(GenericEntityVO vo,GenericEntity entity,
    		List<String> formula ,List<String> limitKeys,List<Object> limitVals){
    	
    	StringBuffer hqlquery = new StringBuffer("from "+entity.getClass().getName());
    	if(limitKeys!=null){
    		hqlquery.append(" where ");
    		for(int i=0;i<limitKeys.size();i++){
    			Object limitKey =limitKeys.get(i);
    			if(i==limitKeys.size()-1){
    				hqlquery.append(limitKey + formula.get(i).toString() + " ? ");
    			}else{
    				hqlquery.append(limitKey +formula.get(i).toString() +"?  and ");
    			}
    			
    		}
    	}
    	hqlquery.append(" order by createTime desc");
    	Object[] params = null;
    	if(limitVals!=null){
    		params = limitVals.toArray();
    	}
		
		return this.pageQuery(vo, hqlquery,limitKeys, limitVals);
		
	}
    
    
    /**
     * 查询分页通用方法
     * @author swim
     * @param vo 
     * @param entity
     * @param formula 判断公式（例如：‘=’，‘！=’，‘>’,'<'）
     * @param limitKeys 条件列（例如：‘status’）
     * @param limitVals 条件值列（例如：‘1’）
     * @return
     */
    public Page<?, ?> doPageQuery(GenericEntityVO vo,Class<T> entity,List<String> formula ,List<String> limitKeys,List<Object> limitVals,boolean isScort){
    	
    	StringBuffer hqlquery = new StringBuffer("from "+entity.getSimpleName());
    	if(limitKeys!=null&&limitKeys.size()!=0){
    		hqlquery.append(" where ");
    		for(int i=0;i<limitKeys.size();i++){
    			Object limitKey =limitKeys.get(i);
    			if(i==limitKeys.size()-1){
    				String values = " ? ";
    				if(formula.get(i).toString().equals("in"))values = " (:"+limitKey+")";
    				hqlquery.append(" "+limitKey +" "+ formula.get(i).toString() + values);
    			}else{
    				String values = " ? and ";
    				if(formula.get(i).toString().equals("in"))values = " (:"+limitKey+") and";
    				hqlquery.append(" "+limitKey +" "+ formula.get(i).toString() +values);
    			}
    			
    		}
    	}
    	if(isScort)hqlquery.append(" order by createTime desc");
    	
		return this.pageQuery(vo, hqlquery, limitKeys,limitVals);
	}
    
    /**
     * 查询分页通用方法并且统计对应数据
     * @author swim
     * @param vo 
     * @param entity
     * @param formula 判断公式（例如：‘=’，‘！=’，‘>’,'<','in'）
     * @param limitKeys 条件列（例如：‘status’）
     * @param limitVals 条件值列（例如：‘1’）
     * @param countHql 统计语句
     * @param term 统计其他条件
     * @param isScort
     * @return
     */
    public Page<?, ?> doPageQuery(GenericEntityVO vo,Class<T> entity,List<String> formula ,List<String> limitKeys,List<Object> limitVals,String countHql,String term,String pj,boolean isScort){
    	
    	StringBuffer hqlquery = new StringBuffer("from "+entity.getSimpleName());
    	countHql = countHql.concat("from "+entity.getSimpleName());
    	String inTerm = "";
    	if(limitKeys!=null&&limitKeys.size()!=0){
    		hqlquery.append(" where 1=1 ");
    		countHql = countHql.concat(" where 1=1 ");
    		for(int i=0;i<limitKeys.size();i++){
    			Object limitKey =limitKeys.get(i);
    			String values = " ? ";
    			if(formula.get(i).toString().equals("in")){
    				inTerm = inTerm.concat(" and "+limitKey+" "+formula.get(i).toString()+" (:"+limitKey+") ");
    			}else{
    				hqlquery.append(" and "+limitKey +" "+ formula.get(i).toString() +values);
        			countHql = countHql.concat(" and "+limitKey +" "+ formula.get(i).toString() +values);
    			}
    		}
    	}
    	hqlquery.append(inTerm).append(pj);
    	countHql = countHql.concat(inTerm).concat(pj);
    	if(!StringUtils.isEmpty(term))countHql = countHql.concat(term);
    	if(isScort){
    		hqlquery.append(" order by createTime desc");
    		countHql = countHql.concat(" order by createTime desc");
    	}
    	
		return this.pageQuery(vo, hqlquery,countHql, limitKeys,limitVals);
	}
    
    /**
     * 通用查询分页执行的实际方法 带统计
     * @param vo
     * @param maxCount
     * @param hqlquery
     * @param limitVals
     * @return
     */
    @SuppressWarnings("unchecked")
	public Page<?, ?> pageQuery(GenericEntityVO vo,
    		StringBuffer hqlquery,String countHql,List<String> limitKeys,List<Object> limitVals){
    	Object[] params = null;
    	Object[] keys =null;
    	if(limitVals!=null){
    		params = limitVals.toArray();
    		keys =limitKeys.toArray();
    	}
    	int totalCount = this.queryListCount(hqlquery.toString(),keys, params);
    	Page<GenericEntityVO, GenericEntity> page = new Page<GenericEntityVO, GenericEntity>();
		
		int pageNum = vo.getPageNum();
		int maxY = totalCount/vo.getMaxCount();
		if(totalCount%vo.getMaxCount()!=0){
			maxY+=1;
		}
		pageNum = pageNum<=0?1:pageNum;
		pageNum = pageNum>=maxY?maxY:pageNum;
		page.setPageNum(pageNum);
		final Query query = getSession().createQuery(hqlquery.toString());  
		final Query query1 = getSession().createQuery(countHql.toString());  
		if(limitVals!=null&&limitVals.size()!=0){
			for(int i=0; i<limitVals.size();i++){
	        	if(limitVals.get(i) instanceof Date){
	        		query.setTimestamp(i, (Date) limitVals.get(i));
	        		query1.setTimestamp(i, (Date) limitVals.get(i));
	        	}else if(limitVals.get(i) instanceof List){
	        		query.setParameterList(limitKeys.get(i), (Collection) limitVals.get(i));
	        		query1.setParameterList(limitKeys.get(i), (Collection) limitVals.get(i));
	        	}else{
	        		query.setParameter(i, limitVals.get(i));
	        		query1.setParameter(i, limitVals.get(i));
	        	}
	        	
	        }
		}
		query.setMaxResults(vo.getMaxCount());  
		query.setFirstResult((pageNum-1)*vo.getMaxCount());    
		List<GenericEntity> entitylist = query.list();
		List<Object[]> objs = query1.list();
		page.setCellList(objs);
		page.setEntitylist(entitylist);
		page.setPageCount(maxY);
		//总记录数
		page.setTotalCount(totalCount);
		return page;
    }
    
    
    /**
     * 通用查询分页执行的实际方法
     * @param vo
     * @param maxCount
     * @param hqlquery
     * @param limitVals
     * @return
     */
    public Page<?, ?> pageQuery(GenericEntityVO vo,
    		StringBuffer hqlquery,List<String> limitKeys,List<Object> limitVals){
    	Object[] params = null;
    	Object[] keys =null;
    	if(limitVals!=null){
    		params = limitVals.toArray();
    		keys =limitKeys.toArray();
    	}
    	int totalCount = this.queryListCount(hqlquery.toString(),keys, params);
    	Page<GenericEntityVO, GenericEntity> page = new Page<GenericEntityVO, GenericEntity>();
		
		int pageNum = vo.getPageNum();
		int maxY = totalCount/vo.getMaxCount();
		if(totalCount%vo.getMaxCount()!=0){
			maxY+=1;
		}
		pageNum = pageNum<=0?1:pageNum;
		pageNum = pageNum>=maxY?maxY:pageNum;
		page.setPageNum(pageNum);
		final Query query = getSession().createQuery(hqlquery.toString());  
		if(limitVals!=null&&limitVals.size()!=0){
			for(int i=0; i<limitVals.size();i++){
	        	if(limitVals.get(i) instanceof Date){
	        		query.setTimestamp(i, (Date) limitVals.get(i));
	        	}else if(limitVals.get(i) instanceof List){
	        		query.setParameterList(limitKeys.get(i), (Collection) limitVals.get(i));
	        	}
	        	else{
	        		query.setParameter(i, limitVals.get(i));
	        	}
	        	
	        }
		}
		query.setMaxResults(vo.getMaxCount());  
		query.setFirstResult((pageNum-1)*vo.getMaxCount());    
		@SuppressWarnings("unchecked")
		List<GenericEntity> entitylist = query.list();    
		page.setEntitylist(entitylist);
		page.setPageCount(maxY);
		//总记录数
		page.setTotalCount(totalCount);
		return page;
    }
    
    /**
     * 通用查询分页执行的实际方法
     * @param vo
     * @param maxCount
     * @param hqlquery
     * @param limitVals
     * @return
     */
    public Page<?, ?> pageQuery(GenericEntityVO vo,
    		StringBuffer hqlquery,StringBuffer hqlQueryCount,List<String> limitKeys,List<Object> limitVals){
    	Object[] params = null;
    	Object[] keys =null;
    	if(limitVals!=null){
    		params = limitVals.toArray();
    		keys =limitKeys.toArray();
    	}
    	int totalCount = this.queryCount(hqlQueryCount.toString(),keys, params);
    	Page<GenericEntityVO, GenericEntity> page = new Page<GenericEntityVO, GenericEntity>();
		
		int pageNum = vo.getPageNum();
		int maxY = totalCount/vo.getMaxCount();
		if(totalCount%vo.getMaxCount()!=0){
			maxY+=1;
		}
		pageNum = pageNum<=0?1:pageNum;
		pageNum = pageNum>=maxY?maxY:pageNum;
		page.setPageNum(pageNum);
		final Query query = getSession().createQuery(hqlquery.toString());  
		if(limitVals!=null&&limitVals.size()!=0){
			for(int i=0; i<limitVals.size();i++){
	        	if(limitVals.get(i) instanceof Date){
	        		query.setTimestamp(i, (Date) limitVals.get(i));
	        	}else if(limitVals.get(i) instanceof List){
	        		query.setParameterList(limitKeys.get(i), (Collection) limitVals.get(i));
	        	}
	        	//else if(!StringUtils.isEmpty(limitKeys.get(i))){
            	//	query.setParameter(limitKeys.get(i), limitVals.get(i));
            	//}
				else{
            		query.setParameter(i,  limitVals.get(i));
            	}
	        	
	        }
		}
		query.setMaxResults(vo.getMaxCount());  
		query.setFirstResult((pageNum-1)*vo.getMaxCount());    
		@SuppressWarnings("unchecked")
		List<GenericEntity> entitylist = query.list();    
		page.setEntitylist(entitylist);
		page.setPageCount(maxY);
		//总记录数
		page.setTotalCount(totalCount);
		return page;
    }
    
    /**
     * 统计总数量通用方法
     * @param statusName
     * @param status
     * @return
     */
    public int getObjectSumCount(final String statusName,int status) {
		StringBuffer queryString = new StringBuffer("select count(id) from ");
		queryString.append(entityClass.getSimpleName());
		queryString.append(" t where t.").append(statusName).append("=?");
		Query query = sessionFactory.getCurrentSession().createQuery(queryString.toString());
		query.setParameter(0, status);
		Long count = (Long)query.list().get(0);
		return Integer.parseInt(count.toString());
	}
    
    @SuppressWarnings("unchecked")  
    protected int queryCount(String hql,Object[] keys, Object[] values) {  
        Query query = sessionFactory.getCurrentSession().createQuery(hql); 
        if(null!=values){
        	for(int i=0;i<values.length;i++){
            	if(values[i] instanceof Date){
            		query.setTimestamp(i, (Date) values[i]);
            	}else if(values[i] instanceof List){
            		query.setParameterList((String) keys[i], (Collection) values[i]);
            	}
            	//else if(!StringUtils.isEmpty(keys[i])){
            	//	query.setParameter((String) keys[i], values[i]);
            	//}
        		else{
            		query.setParameter(i, values[i]);
            	}
            	
            }
        }
        Long temp = (Long) query.list().get(0);
        int count = Integer.parseInt(temp.toString());
        return count;  
    } 
    
 /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @SuppressWarnings("unchecked")  
    protected int querySqlCount(String sql,Object[] keys, Object[] values) {  
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        if(null!=values){
        	for(int i=0;i<values.length;i++){
            	if(values[i] instanceof Date){
            		query.setTimestamp(i, (Date) values[i]);
            	}else if(values[i] instanceof List){
            		query.setParameterList((String) keys[i], (Collection) values[i]);
            	}
            	//else if(!StringUtils.isEmpty(keys[i])){
            	//	query.setParameter((String) keys[i], values[i]);
            	//}
        		else{
            		query.setParameter(i, values[i]);
            	}
            	
            }
        }
        BigInteger temp = (BigInteger) query.list().get(0);
        int count = temp.intValue();
        return count;  
    }
    
    
    /**
     * 通用查询分页执行的实际方法
     * @param vo
     * @param maxCount
     * @param hqlquery
     * @param limitVals
     * @return
     */
    public Page<Object, Object> pageSqlQuery(GenericEntityVO vo,
    		StringBuffer sqlquery,StringBuffer sqlQueryCount,List<String> limitKeys,List<Object> limitVals,Map<String, ?> param){
    	Object[] params = null;
    	Object[] keys =null;
    	if(limitVals!=null){
    		params = limitVals.toArray();
    		keys =limitKeys.toArray();
    	}
    	int totalCount = this.querySqlCount(sqlQueryCount.toString(),keys, params);
    	Page<Object, Object> page = new Page<Object, Object>();
		
		int pageNum = vo.getPageNum();
		int maxY = totalCount/vo.getMaxCount();
		if(totalCount%page.getMaxCount()!=0){
			maxY+=1;
		}
		pageNum = pageNum<=0?1:pageNum;
		pageNum = pageNum>=maxY?maxY:pageNum;
		page.setPageNum(pageNum);
		final Query query = getSession().createSQLQuery(sqlquery.toString());  
		if(limitVals!=null&&limitVals.size()!=0){
			for(int i=0; i<limitVals.size();i++){
	        	if(limitVals.get(i) instanceof Date){
	        		query.setTimestamp(i, (Date) limitVals.get(i));
	        	}else if(limitVals.get(i) instanceof List){
	        		query.setParameterList(limitKeys.get(i), (Collection) limitVals.get(i));
	        	}
	        	else{
	        		query.setParameter(i, limitVals.get(i));
	        	}
	        	
	        }
		}
		query.setMaxResults(vo.getMaxCount());  
		query.setFirstResult((pageNum-1)*vo.getMaxCount());    
		@SuppressWarnings("unchecked")
		List<Object> entitylist = query.list();    
		page.setEntitylist(entitylist);
		page.setPageCount(maxY);
		//总记录数
		page.setTotalCount(totalCount);
		page.setRsvst1(sqlquery.toString());
		String isTotal = (String) param.get("total");
		if(isTotal!=null&&isTotal.equals("1")){
			StringBuffer totalSql = new StringBuffer();
			totalSql.append(param.get("totalStart")).append(sqlquery).append(param.get("totalEnd"));
			Query totalQuery = getSession().createSQLQuery(totalSql.toString());  
			if(limitVals!=null&&limitVals.size()!=0){
				for(int i=0; i<limitVals.size();i++){
		        	if(limitVals.get(i) instanceof Date){
		        		totalQuery.setTimestamp(i, (Date) limitVals.get(i));
		        	}else if(limitVals.get(i) instanceof List){
		        		totalQuery.setParameterList(limitKeys.get(i), (Collection) limitVals.get(i));
		        	}
		        	else{
		        		totalQuery.setParameter(i, limitVals.get(i));
		        	}
		        	
		        }
			}
			Object[] objs = (Object[]) totalQuery.list().get(0);
			page.setCells(objs);
		}
		return page;
    }
    
    /**
     * 查询分页通用方法
     * @author swim
     * @param vo 
     * @param entity
     * @param formula 判断公式（例如：‘=’，‘！=’，‘>’,'<'）
     * @param limitKeys 条件列（例如：‘status’）
     * @param limitVals 条件值列（例如：‘1’）
     * @return
     */
    public Page<Object, Object> doPageSqlQuery(GenericEntityVO vo,StringBuffer sql,StringBuffer sqlCount,List<String> formula ,List<String> limitKeys,List<Object> limitVals,boolean isScort,Map<String, ?> param){
    	
    	StringBuffer hqlquery = sql;
    	if(limitKeys!=null&&limitKeys.size()!=0){
    		hqlquery.append(" and ");
    		for(int i=0;i<limitKeys.size();i++){
    			Object limitKey =limitKeys.get(i);
    			if(i==limitKeys.size()-1){
    				hqlquery.append(" "+limitKey +" "+ formula.get(i).toString() + " ? ");
    			}else{
    				hqlquery.append(" "+limitKey +" "+ formula.get(i).toString() +" ?  and ");
    			}
    			
    		}
    	}
    	if(isScort)hqlquery.append(" order by create_Time desc");
    	
    	if(limitKeys!=null&&limitKeys.size()!=0){
    		sqlCount.append(" and ");
    		for(int i=0;i<limitKeys.size();i++){
    			Object limitKey =limitKeys.get(i);
    			if(i==limitKeys.size()-1){
    				sqlCount.append(" "+limitKey +" "+ formula.get(i).toString() + " ? ");
    			}else{
    				sqlCount.append(" "+limitKey +" "+ formula.get(i).toString() +" ?  and ");
    			}
    			
    		}
    	}
    	
		return this.pageSqlQuery(vo, hqlquery,sqlCount,limitKeys,limitVals,param);
	}
}  
