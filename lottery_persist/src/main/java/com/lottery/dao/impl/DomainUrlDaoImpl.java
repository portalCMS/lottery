package com.lottery.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.CustomerUrl;
import com.lottery.bean.entity.DomainUrl;
import com.lottery.bean.entity.GenericEntity;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.CustomerUrlVO;
import com.lottery.bean.entity.vo.DomainUrlVO;
import com.lottery.bean.entity.vo.GenericEntityVO;
import com.lottery.dao.IDomainUrlDao;
import com.lottery.persist.generice.GenericDAO;
import com.xl.lottery.util.DataDictionaryUtil;
import com.xl.lottery.util.DozermapperUtil;

@Repository
public class DomainUrlDaoImpl extends GenericDAO<DomainUrl> implements
IDomainUrlDao{

	public DomainUrlDaoImpl() {
		super(DomainUrl.class);
	}

	@Override
	public Page<DomainUrlVO, DomainUrl> findDomainUrlList(
			Map<String, ?> param) throws Exception {
		// TODO Auto-generated method stub
		DomainUrlVO domainVo = (DomainUrlVO) param.get("domainUrlkey");
		DomainUrl entity = new DomainUrl();
		List<String> keys = new ArrayList<String>();
		keys.add("status");
		List<Object> vals = new ArrayList<Object>();
		vals.add(DataDictionaryUtil.STATUS_OPEN);
		List<String> formula = new ArrayList<String>();
		formula.add("=");
		@SuppressWarnings("unchecked")
		Page<DomainUrlVO, DomainUrl> page =(Page<DomainUrlVO, DomainUrl>) doPageQuery(domainVo,entity,formula,keys,vals);
		List<DomainUrl> entitys = page.getEntitylist();
		List<DomainUrlVO> vos = new ArrayList<DomainUrlVO>();
		StringBuffer sql = new StringBuffer("SELECT COUNT(1) FROM t_customer_url t WHERE t.`url_id` = ? and t.url_status = 10002 ");
		Query query = getSession().createSQLQuery(sql.toString());
		for(DomainUrl domainUrl:entitys){
			DomainUrlVO vo = new DomainUrlVO();
			DozermapperUtil.getInstance().map(domainUrl, vo);
			query.setParameter(0, vo.getId());
			List<BigInteger> count = query.list();
			if(count.size()==0){
				vo.setBindcount(0);
			}else{
				vo.setBindcount(count.get(0).intValue());
			}
			vos.add(vo);
		}
		page.setPagelist(vos);
		return page;
	}
	
	
	protected int findCustomerUrlCount() {
		int count = ((BigInteger) getSession().createSQLQuery("select count(1) from t_domain_url t where t.url_status="+DataDictionaryUtil.STATUS_OPEN).list().get(0)).intValue();
		return count;
	}

	@Override
	public DomainUrl findDomainUrlList(String urlName) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer sqlquery = new StringBuffer("from DomainUrl t where t.url = ? and t.status=?");
		DomainUrl domainUrl = queryForObject(sqlquery.toString(),new Object[]{urlName,DataDictionaryUtil.STATUS_OPEN});
		return domainUrl;
	}

	@Override
	public List<DomainUrl> findDomainUrllist(Map<String,?> param) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer sqlquery = new StringBuffer("from DomainUrl t where t.status=?");
		List<DomainUrl> urllist = queryForList(sqlquery.toString(),null,new Object[]{DataDictionaryUtil.STATUS_OPEN});
		return urllist;
	}
}
