package com.lottery.persist.generice;

import java.util.List;

import org.hibernate.Session;

public interface IGenericDao<T> {

	public void insert(T t);  
	  
	public void delete(T t);  
  
	public void update(T t) throws Exception;  
  
	public T queryById(long id);  
  
	public List<T> queryAll(); 
	
	public Session getSession();
	
	public void persist(T t);

	public void save(T t);
}