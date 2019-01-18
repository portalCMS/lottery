package com.lottery.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.CustomerUrl;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.CustomerUrlVO;
import com.lottery.dao.ICustomerUrlDao;
import com.lottery.dao.impl.AdminWriteLog;
import com.lottery.service.ICustomerUrlService;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;

@Service
public class CustomerUrlServiceImpl implements ICustomerUrlService{

	@Autowired
	private ICustomerUrlDao customerUrlDao;
	
	@Autowired
	private AdminWriteLog adminWriteLog;

	@Override
	public Page<CustomerUrlVO, CustomerUrl> findCustomerUrlList(
			Map<String, ?> param) throws Exception {
		// TODO Auto-generated method stub
		Page<CustomerUrlVO, CustomerUrl> page = customerUrlDao.findCustomerUrlList(param);
		return page;
	}

	@Override
	public String saveCustomerUrl(Map<String, ?> param) throws Exception {
		// TODO Auto-generated method stub
		
		return "success";
	}

	@Override
	public String updateCustomerUrl(Map<String, ?> param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteCustomerUrl(Map<String, ?> param) throws Exception {
		// TODO Auto-generated method stub
		CustomerUrl customerUrl = (CustomerUrl) param.get("customerurlkey");
		AdminUser adminUser = (AdminUser) param.get(CommonUtil.USERKEY);
		CustomerUrl entity = customerUrlDao.queryById(customerUrl.getId());
		entity.setUrlStatus(DataDictionaryUtil.STATUS_CLOSE);
		try {
			customerUrlDao.update(entity);
			adminWriteLog.saveWriteLog(adminUser, CommonUtil.DELETE,
					"CustomerUrl", entity.toString());
		} catch (Exception e) {
			// TODO: handle exception
			return "faile";
		}
		return "success";
	}

	@Override
	public List<CustomerUrl> findCustomerUrllist(Map<String, ?> param) throws Exception {
		// TODO Auto-generated method stub
		return customerUrlDao.findCustomerUrllist(param);
	}
	

}
