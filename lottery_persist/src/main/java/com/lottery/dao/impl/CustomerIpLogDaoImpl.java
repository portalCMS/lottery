package com.lottery.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.CustomerIpLog;
import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.CustomerIpLogVO;
import com.lottery.dao.ICustomerIpLogDao;
import com.lottery.persist.generice.GenericDAO;
import com.xl.lottery.desutil.AesUtil;
import com.xl.lottery.desutil.Md5Manage;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.IPSeeker;

@Repository
public class CustomerIpLogDaoImpl extends GenericDAO<CustomerIpLog> implements ICustomerIpLogDao{

	public CustomerIpLogDaoImpl() {
		super(CustomerIpLog.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<CustomerIpLogVO, CustomerIpLog> findIpLogs(
			Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		CustomerUser user = (CustomerUser) param.get(CommonUtil.CUSTOMERUSERKEY);
		CustomerIpLogVO vo = (CustomerIpLogVO) param.get("ipvo");
		StringBuffer queryString = new StringBuffer("from CustomerIpLog t where t.customerId = ? order by t.createTime desc");
		StringBuffer hqlQueryCount = new StringBuffer("select count(t.id) from CustomerIpLog t where t.customerId = ? ");
		List<String> limitKeys = new ArrayList<String>();
		List<Object> limitVals = new ArrayList<Object>();
		limitVals.add(user.getId());
		Page<CustomerIpLogVO, CustomerIpLog> page = (Page<CustomerIpLogVO, CustomerIpLog>)pageQuery(vo, queryString,hqlQueryCount,limitKeys, limitVals);
		return page;
	}

	@Override
	public void saveIPlog(CustomerUser user,String log) throws Exception {
		// TODO Auto-generated method stub
		CustomerIpLog entity = new CustomerIpLog();
		entity.setIp(user.getIp());
		entity.setType(log);
		entity.setCustomerId(user.getId());
		entity.setIpAddress(IPSeeker.getInstance().getCountry(user.getIp()).concat(IPSeeker.getInstance().getArea(user.getIp())));
		entity.addInit(user.getCustomerName());
		save(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<Object, Object> queryIplogs(Map<String, Object> param)
			throws Exception {
		CustomerIpLogVO vo = (CustomerIpLogVO) param.get("ipvo");
		Page<Object, Object> page = new Page<Object, Object>();
		StringBuffer sql = new StringBuffer("SELECT u.`id`,u.customer_name,i.`ip`,u.`create_time`,i.`ipAddress`,i.`create_time` as opttime,u.rsvst1,u.rsvst2,uc.open_card_name,uc.card_no,u.rsvst3 FROM ");
		sql.append(" t_customer_user u LEFT JOIN t_customer_ip_log i on u.id= i.customer_id LEFT JOIN t_user_card uc on uc.customer_id = u.id LEFT JOIN t_bank_manage bm on bm.id= uc.bank_id where u.`id` = i.`customer_id` ");
		StringBuffer count = new StringBuffer("SELECT count(1) FROM ");
		count.append(" t_customer_user u LEFT JOIN t_customer_ip_log i on u.id= i.customer_id LEFT JOIN t_user_card uc on uc.customer_id = u.id LEFT JOIN t_bank_manage bm on bm.id= uc.bank_id where u.`id` = i.`customer_id`");
		Query query=null;
		if(StringUtils.isNotEmpty(vo.getUname())){
			sql.append(" and i.create_user = :uname ");
			count.append(" and i.create_user = :uname ");
		}
		if(StringUtils.isNotEmpty(vo.getIp())){
			sql.append(" and i.ip = :uip ");
			count.append(" and i.ip = :uip ");
		}
		if(StringUtils.isNotEmpty(vo.getCpuid())){
			sql.append(" and u.rsvst1 = :cupid ");
			count.append(" and u.rsvst1 = :cupid ");
		}
		if(StringUtils.isNotEmpty(vo.getDiskid())){
			sql.append(" and u.rsvst2 = :diskid ");
			count.append(" and u.rsvst2 = :diskid ");
		}
		if(StringUtils.isNotEmpty(vo.getOpenCardName())){
			sql.append(" and uc.open_card_name = :openCardName ");
			count.append(" and uc.open_card_name = :openCardName ");
		}
		if(StringUtils.isNotEmpty(vo.getCardNo())){
			sql.append(" and uc.card_no  = :cardNo ");
			count.append(" and uc.card_no  = :cardNo ");
		}
		if(StringUtils.isNotEmpty(vo.getOpenCardName())){
			sql.append(" group by uc.card_no");
			count.append(" group by uc.card_no");
			query = getSession().createSQLQuery("select count(1) from ("+count.toString()+") tt");
		}else{
			query = getSession().createSQLQuery(count.toString());
		}
		sql.append(" order by i.`create_time` desc ");
		if(StringUtils.isNotEmpty(vo.getUname())){
			query.setParameter("uname", vo.getUname());
		}
		if(StringUtils.isNotEmpty(vo.getIp())){
			query.setParameter("uip", vo.getIp());
		}
		if(StringUtils.isNotEmpty(vo.getCpuid())){
			query.setParameter("cupid", vo.getCpuid());
		}
		if(StringUtils.isNotEmpty(vo.getDiskid())){
			query.setParameter("diskid", vo.getDiskid());
		}
		if(StringUtils.isNotEmpty(vo.getOpenCardName())){
			query.setParameter("openCardName", AesUtil.encrypt(vo.getOpenCardName(),Md5Manage.getInstance().getMd5()));
		}
		if(StringUtils.isNotEmpty(vo.getCardNo())){
			query.setParameter("cardNo", AesUtil.encrypt(vo.getCardNo(), Md5Manage.getInstance().getMd5()));
		}
	    int totalCount = ((BigInteger)query.list().get(0)).intValue();
		int pageNum = vo.getPageNum();
		int maxY = totalCount/vo.getMaxCount();
		if(totalCount%vo.getMaxCount()!=0){
			maxY+=1;
		}
		pageNum = pageNum<=0?1:pageNum;
		pageNum = pageNum>=maxY?maxY:pageNum;
		page.setPageNum(pageNum);
		Query query1 = getSession().createSQLQuery(sql.toString());
		if(StringUtils.isNotEmpty(vo.getUname())){
			query1.setParameter("uname", vo.getUname());
		}
		if(StringUtils.isNotEmpty(vo.getIp())){
			query1.setParameter("uip", vo.getIp());
		}
		if(StringUtils.isNotEmpty(vo.getCpuid())){
			query1.setParameter("cupid", vo.getCpuid());
		}
		if(StringUtils.isNotEmpty(vo.getDiskid())){
			query1.setParameter("diskid", vo.getDiskid());
		}
		if(StringUtils.isNotEmpty(vo.getOpenCardName())){
			query1.setParameter("openCardName", AesUtil.encrypt(vo.getOpenCardName(),Md5Manage.getInstance().getMd5()));
			
		}
		if(StringUtils.isNotEmpty(vo.getCardNo())){
			query1.setParameter("cardNo", AesUtil.encrypt(vo.getCardNo(), Md5Manage.getInstance().getMd5()));
		}
		query1.setMaxResults(vo.getMaxCount());  
		query1.setFirstResult((pageNum-1)*vo.getMaxCount());  
		List<Object> objs = query1.list();
		
		page.setPageCount(maxY);
		for(Object obj: objs){
			Object[] o = (Object[]) obj;
			if (o[8] != null) {
				o[8] = AesUtil.decrypt(String.valueOf(o[8]), Md5Manage.getInstance().getMd5());
			} else {
				o[8] = "";
			}
			if (o[9] != null) {
				o[9] = AesUtil.decrypt(String.valueOf(o[9]), Md5Manage.getInstance().getMd5());
			} else {
				o[9] = "";
			}
		}
		page.setPagelist(objs);
		page.setEntitylist(objs);
		
		//总记录数
		page.setTotalCount(totalCount);
		return page;
	}
}
