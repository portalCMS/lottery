package com.lottery.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.CustomerMessage;
import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.CustomerMessageVO;
import com.lottery.dao.ICustomerMessageDao;
import com.lottery.dao.ICustomerUserDao;
import com.lottery.service.ICustomerMessageService;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DozermapperUtil;

@Service
public class CustomerMessageServiceImpl implements ICustomerMessageService{

	@Autowired
	private ICustomerMessageDao msgDao;
	
	@Autowired
	private ICustomerUserDao userDao;
	
	@Override
	public Integer getMsgCount(Map<String, Object> param) throws Exception {
		return msgDao.getMsgCount(param);
	}

	@Override
	public Page<CustomerMessageVO, CustomerMessage> queryMsgPage(
			Map<String, Object> param) throws Exception {
		return msgDao.queryMsgPage(param);
	}
	
	@Override
	public String saveMsg(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		CustomerMessageVO vo = (CustomerMessageVO) param.get("msgVO");
		AdminUser adminuser = (AdminUser) param.get(CommonUtil.USERKEY);
		
		CustomerUser toUser = null;
		if(!StringUtils.isEmpty(vo.getToUserName())){
			toUser = userDao.queryUserByName(vo.getToUserName());
		}else{
			toUser = userDao.queryById(vo.getToUserId());
		}
		
		CustomerMessage entity = new CustomerMessage();
		entity.setRefUserId(0);
		entity.setRefUserName("系统消息");
		entity.setToUserId(toUser.getId());
		entity.setToUserName(toUser.getCustomerName());
		entity.setTitle(vo.getTitle());
		entity.setMessage(vo.getMessage());
		entity.setStatus(CommonUtil.MSG_STATUS_UNREAD);
		if(adminuser != null && adminuser.getUserName()!=null){
			entity.addInit(adminuser.getUserName());
		}else{
			entity.addInit("sys");
		}
		msgDao.save(entity);
		if(!vo.getMsgType().equals("0")){
			List<CustomerUser> lowerUser = userDao.querySunUsersByParent(toUser);
			for(CustomerUser customer : lowerUser){
				CustomerMessage sunEntity = new CustomerMessage();
				DozermapperUtil.getInstance().map(entity, sunEntity);
				sunEntity.setToUserId(customer.getId());
				sunEntity.setToUserName(customer.getCustomerName());
				msgDao.save(sunEntity);
			}
		}
		return "success";
	}

	@Override
	public String updateReadMsg(long id,CustomerUser user) throws Exception {
		// TODO Auto-generated method stub
		CustomerMessage entity = msgDao.queryById(id);
		entity.setStatus(CommonUtil.MSG_STATUS_READ);
		entity.updateInit(user.getCustomerName());
		msgDao.update(entity);
		return "success";
	}

	@Override
	public String deleteMsg(long id,CustomerUser user) throws Exception {
		// TODO Auto-generated method stub
		CustomerMessage entity = msgDao.queryById(id);
		msgDao.delete(entity);
		return "success";
	}

	@Override
	public String saveWebMsg(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		CustomerMessageVO vo = (CustomerMessageVO) param.get("msgVO");
		CustomerUser user = (CustomerUser) param.get(CommonUtil.CUSTOMERUSERKEY);
		CustomerUser toUser = this.checkGoodFriend(vo, user);
		CustomerMessage msg = new CustomerMessage();
		msg.addInit(user.getCustomerName());
		msg.setTitle(vo.getTitle());
		msg.setMessage(vo.getMessage());
		msg.setStatus(CommonUtil.MSG_STATUS_UNREAD);
		msg.setRefUserId(user.getId());
		msg.setRefUserName(user.getCustomerName());
		msg.setToUserId(toUser.getId());
		msg.setToUserName(toUser.getCustomerName());
		msgDao.save(msg);
		return "success";
	}
	
	/**
	 * 检查是否是上下级好友
	 * @param vo
	 * @param user
	 * @return
	 * @throws Exception 
	 */
	public CustomerUser checkGoodFriend(CustomerMessageVO vo,CustomerUser user) throws Exception{
		CustomerUser toUser = null;
		if(vo.getToUserName().equals("toMyUp")){
			String pc = user.getAllParentAccount();
			if(StringUtils.isEmpty(pc)){
				throw new LotteryException("您没有上级，无法发送站内信！");
			}
			//查询直属上级
			String[] pcs = pc.split(",");
			Long upId = Long.parseLong(pcs[pcs.length-1]);
			toUser = userDao.queryById(upId);
		}else{
			toUser = userDao.queryUserByName(vo.getToUserName());
		}
		
		if(toUser == null)throw new LotteryException("用户不存在,无法发送站内信！");
		if(toUser.getId() != user.getCustomerSuperior() && toUser.getCustomerSuperior() != user.getId()){
			throw new LotteryException("该用户非亲您的上级或是直属下级,无法发送站内信！");
		}
		return toUser;
	}

}
