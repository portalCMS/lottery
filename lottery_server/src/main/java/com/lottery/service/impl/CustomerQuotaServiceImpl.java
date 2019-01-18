package com.lottery.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.bean.entity.CustomerQuota;
import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.vo.CustomerQuotaVO;
import com.lottery.dao.ICustomerQuotaDao;
import com.lottery.dao.ICustomerUserDao;
import com.lottery.dao.impl.CustomerUserWriteLog;
import com.lottery.persist.generice.GenericDAO;
import com.lottery.service.ICustomerQuotaService;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;
import com.xl.lottery.util.DateUtil;

@Service
public class CustomerQuotaServiceImpl implements ICustomerQuotaService {

	@Autowired
	private ICustomerQuotaDao customerQuotaDao;

	@Autowired
	private CustomerUserWriteLog customerUserWriteLog;
	
	@Autowired
	private ICustomerUserDao userDao;

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerQuota> findCustomerUser(Map<String, ?> param)
			throws Exception {
		// TODO Auto-generated method stub
		CustomerUser user = (CustomerUser) param
				.get(CommonUtil.CUSTOMERUSERKEY);
		StringBuffer sql = new StringBuffer(
				"from CustomerQuota t where t.customerId = ? and t.status = ? order by proportion desc ");
		Query query = customerQuotaDao.getSession().createQuery(sql.toString());
		query.setParameter(0, user.getId());
		query.setParameter(1, DataDictionaryUtil.STATUS_OPEN);
		return query.list();
	}

	@Override
	public String saveOrUpdateCustomerQuota(Map<String, ?> param)
			throws Exception {
		// TODO Auto-generated method stub
		CustomerQuotaVO vo = (CustomerQuotaVO) param.get("customerquotakey");

		// 判断为分配配额还是回收配额
		if (vo.getChangeQuotaType().equals(
				DataDictionaryUtil.DISTRIBUTION_QUOTA)) {
			return distributionQuota(param);
		} else {
			return recycleQuota(param);
		}
	}

	// 分配配额
	private String distributionQuota(Map<String, ?> param) throws Exception {
		CustomerQuotaVO vo = (CustomerQuotaVO) param.get("customerquotakey");
		CustomerUser user = (CustomerUser) param.get(CommonUtil.CUSTOMERUSERKEY);
		//修改为可以多个配额同时分配
		String qcs = vo.getChangeCounts();
		String qids = vo.getQids();
		String[] quotaIds = qids.split(",");
		String[] quotaCounts = qcs.split(",");
		
		for(int i=0;i<quotaIds.length;i++){
			int quotaCount = Integer.parseInt(quotaCounts[i]); // 分配数量
			long quotaId = Long.parseLong(quotaIds[i]);
			BigDecimal proportion = null;
			CustomerQuota myentity = null;
			List<CustomerQuota> plist = findCustomerQuotaList(user.getId());
			for (CustomerQuota cq : plist) {
				if (quotaId == cq.getId()) { // 验证自己剩余数量是否充足，并且获得分配类型
					if (cq.getQuota_count() - quotaCount < 0)
						throw new LotteryException("剩余配额数量不足");
					proportion = cq.getProportion();
					myentity = cq;
					break;
				}
			}
			List<CustomerQuota> slist = findCustomerQuotaList(vo.getCuId());
			CustomerUser lowerUser = userDao.queryById(vo.getCuId());
			if(proportion.doubleValue()>lowerUser.getRebates().doubleValue()){
				throw new LotteryException("分配配置不能高于下级返点");
			}
			int temp = 0;
			for (CustomerQuota scq : slist) {
				if (scq.getProportion().doubleValue() == proportion.doubleValue()) {
					scq.setQuota_count(scq.getQuota_count() + quotaCount);
					customerQuotaDao.update(scq);
					customerUserWriteLog.saveWriteLog(user, CommonUtil.UPDATE,
							"CustomerQuota", "增加调配" + proportion.intValue());
					myentity.setQuota_count(myentity.getQuota_count() - quotaCount);
					customerQuotaDao.update(myentity);
					customerUserWriteLog.saveWriteLog(user, CommonUtil.UPDATE,
							"CustomerQuota", "分配调配" + myentity.toString());
					temp++;
					break;
				}
			}

			if (temp == 0) {
				CustomerQuota eneity = getCustomerQuotaObject(vo.getCuId(),
						proportion, quotaCount, user);
				customerQuotaDao.insert(eneity);
				customerUserWriteLog.saveWriteLog(user, CommonUtil.SAVE,
						"CustomerQuota", "新增配额" + proportion.intValue());
				myentity.setQuota_count(myentity.getQuota_count() - quotaCount);
				customerQuotaDao.update(myentity);
				customerUserWriteLog.saveWriteLog(user, CommonUtil.UPDATE,
						"CustomerQuota", "分配调配" + proportion.intValue());
			}
		}
		
		return "success";
	}

	// 回收配额
	private String recycleQuota(Map<String, ?> param) throws Exception {
		CustomerQuotaVO vo = (CustomerQuotaVO) param.get("customerquotakey");
		CustomerUser user = (CustomerUser) param
				.get(CommonUtil.CUSTOMERUSERKEY);
		//修改为可以多个配额同时分配
		String qcs = vo.getChangeCounts();
		String qids = vo.getQids();
		String[] quotaIds = qids.split(",");
		String[] quotaCounts = qcs.split(",");
		
		for(int i=0;i<quotaIds.length;i++){
			int quotaCount = Integer.parseInt(quotaCounts[i]); // 分配数量
			long quotaId = Long.parseLong(quotaIds[i]);
			BigDecimal proportion = null;
			CustomerQuota sentity = null;
	
			List<CustomerQuota> slist = findCustomerQuotaList(vo.getCuId());
			for (CustomerQuota cq : slist) {
				if (cq.getId()==quotaId) { // 验证下级剩余数量是否充足，并且获得分配类型
					if (cq.getQuota_count() - quotaCount < 0)
						throw new LotteryException("下级剩余配额数量不足");
					proportion = cq.getProportion();
					sentity = cq;
					break;
				}
			}
	
			if(proportion==null){
				throw new LotteryException("下级剩余配额数量为0，无法回收配额");
			}
			// 上级回收下级配额无须判断类型是否存在
			List<CustomerQuota> plist = findCustomerQuotaList(user.getId());
			for (CustomerQuota pcq : plist) {
				if (pcq.getProportion().doubleValue() == proportion.doubleValue()) {
					pcq.setQuota_count(pcq.getQuota_count() + quotaCount);
					customerQuotaDao.update(pcq);
					customerUserWriteLog.saveWriteLog(user, CommonUtil.UPDATE,
							"CustomerQuota", "增加调配" + pcq.toString());
					sentity.setQuota_count(sentity.getQuota_count() - quotaCount);
					customerQuotaDao.update(sentity);
					customerUserWriteLog.saveWriteLog(user, CommonUtil.UPDATE,
							"CustomerQuota", "分配调配" + sentity.toString());
					break;
				}
			}
		}
		return "success";
	}

	private List<CustomerQuota> findCustomerQuotaList(long customerId) {
		StringBuffer sql = new StringBuffer(
				"from CustomerQuota t where t.customerId = ? and t.status = ?");
		Query query = customerQuotaDao.getSession().createQuery(sql.toString());
		query.setParameter(0, customerId);
		query.setParameter(1, DataDictionaryUtil.STATUS_OPEN);
		return query.list();
	}

	private CustomerQuota getCustomerQuotaObject(long uid,
			BigDecimal proportion, int count, CustomerUser user) {
		CustomerQuota cq = new CustomerQuota();
		cq.setCustomerId(uid);
		cq.setProportion(proportion);
		cq.setQuota_count(count);
		cq.setStatus(DataDictionaryUtil.STATUS_OPEN);
		cq.setCreateTime(DateUtil.getNow());
		cq.setCreateUser(user.getCustomerName());
		cq.setUpdateTime(DateUtil.getNow());
		cq.setUpdateUser(user.getCustomerName());
		return cq;
	}

}
