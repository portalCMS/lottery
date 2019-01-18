package com.lottery.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.bean.entity.CustomerIpLog;
import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.CustomerIpLogVO;
import com.lottery.dao.ICustomerIpLogDao;
import com.lottery.dao.impl.CustomerUserWriteLog;
import com.lottery.service.ICustomerIpLogService;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.IPSeeker;

@Service
public class CustomerIpLogServiceImpl implements ICustomerIpLogService{

	@Autowired
	private ICustomerIpLogDao ipLogDao;
	
	@Autowired
	private CustomerUserWriteLog userlog;
	
	@Override
	public String saveIpLog(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		CustomerUser user = (CustomerUser) param.get(CommonUtil.CUSTOMERUSERKEY);
		CustomerIpLog entity = new CustomerIpLog();
		entity.setIp(user.getIp());
		entity.setType("登录");
		entity.setCustomerId(user.getId());
		entity.setIpAddress(IPSeeker.getInstance().getCountry(user.getIp()).concat(IPSeeker.getInstance().getArea(user.getIp())));
		entity.addInit(user.getCustomerName());
		ipLogDao.save(entity);
		userlog.saveWriteLog(user, CommonUtil.SAVE, "t_customer_ip_log", entity.toString());
		return "success";
	}

	@Override
	public Page<CustomerIpLogVO, CustomerIpLog> findIpLogs(
			Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return ipLogDao.findIpLogs(param);
	}

	@Override
	public Page<Object, Object> queryIplogs(Map<String, Object> param)
			throws Exception {
		// TODO Auto-generated method stub
		return ipLogDao.queryIplogs(param);
	}

}
