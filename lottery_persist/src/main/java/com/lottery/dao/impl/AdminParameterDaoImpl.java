package com.lottery.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.AdminParameter;
import com.lottery.dao.IAdminParameterDao;
import com.lottery.persist.generice.GenericDAO;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.util.DateUtil;

@Repository
public class AdminParameterDaoImpl extends GenericDAO<AdminParameter> implements IAdminParameterDao{

	public AdminParameterDaoImpl() {
		super(AdminParameter.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> queryParameterList(String parameterName, String[] parameterKeys) throws LotteryException {
		Map<String, String> returnMap = new HashMap<String, String>();
		List<String> keys = new ArrayList<String>();
		for(int i=0;i<parameterKeys.length;i++){
			keys.add(parameterKeys[i]);
			
		}
		String hql = "from AdminParameter t where 1=1 and t.name =? and t.key1 in (:keyList)";
		Query query = this.getSession().createQuery(hql);
		query.setParameter(0, parameterName);
		query.setParameterList("keyList", keys);
		List<AdminParameter> returnList = query.list();
		if(returnList.size()==0){
			return null;
		}
		for(AdminParameter parameter : returnList){
			returnMap.put(parameter.getKey1(),parameter.getValue());
		}
		return returnMap;
	}

	@Override
	public boolean setParameterValue(String parameterName, Map<String,String> keyValueMap,String userName) throws LotteryException {
		
		for(String key:keyValueMap.keySet()){
			int executeUpdateRow =0;
			String hql = "update AdminParameter set value=? where 1=1 and name =? and key1=?";
			Query query = this.getSession().createQuery(hql);
			query.setParameter(0, keyValueMap.get(key));
			query.setParameter(1, parameterName);
			query.setParameter(2, key);
			executeUpdateRow += query.executeUpdate();
			//如果参数表没有数据被update，则insert一条参数设置记录
			if(executeUpdateRow==0){
					AdminParameter parameter = new AdminParameter();
					parameter.setKey1(key);
					parameter.setValue(keyValueMap.get(key));
					parameter.setName(parameterName);
					parameter.setCreateTime(DateUtil.getNowDate());
					parameter.setUpdateTime(DateUtil.getNowDate());
					parameter.setCreateUser(userName);
					parameter.setUpdateUser(userName);
					this.getSession().save(parameter);
			}
		}
		
		return true;
	}

	@Override
	public String getParameterByNameAndIndex(String name, int keyIndex,String keyValue)
			throws Exception {
		// TODO Auto-generated method stub
		String hql = "from AdminParameter t where 1=1 and t.name =? ";
		switch (keyIndex) {
		case 1:
			hql+=" and key1=? ";
			break;
		case 2:
			hql+=" and key2=? ";
			break;
		default:
			hql+=" and key3=? ";
			break;
		}
		Query query = this.getSession().createQuery(hql);
		query.setParameter(0, name);
		query.setParameter(1, keyValue);
		AdminParameter entity = (AdminParameter) query.list().get(0);
		return entity.getValue();
	}

}
