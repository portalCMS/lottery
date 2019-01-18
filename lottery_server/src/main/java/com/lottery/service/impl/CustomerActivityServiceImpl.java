package com.lottery.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.CustomerActivity;
import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.CustomerActivityVO;
import com.lottery.dao.ICustomerActivityDao;
import com.lottery.dao.ICustomerActivityLogDao;
import com.lottery.dao.ICustomerUserDao;
import com.lottery.service.ICustomerActivityService;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;
import com.xl.lottery.util.DateUtil;
import com.xl.lottery.util.DozermapperUtil;

@Service
public class CustomerActivityServiceImpl implements ICustomerActivityService{

	@Autowired 
	private ICustomerActivityDao activityDao;
	
	@Autowired
	private ICustomerUserDao userDao;
	
	@Autowired
	private ICustomerActivityLogDao activityLogDao;
	
	@Override
	public String saveRegTempl(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		AdminUser adminUser = (AdminUser) param.get(CommonUtil.USERKEY);
		CustomerActivityVO vo = (CustomerActivityVO) param.get("regTemplvo");
		CustomerActivity entity = new CustomerActivity();
		DozermapperUtil.getInstance().map(vo, entity);
		entity.addInit(adminUser.getUserName());
		boolean flag = activityDao.checkActivityTime(entity.getType(), entity.getStarttime(), entity.getEndtime());
		if(flag)throw new LotteryException("这个时间内已经有一个同类型冲突");
		activityDao.save(entity);
		return "success";
	}
	
	@Override
	public void saveBetActivity(Map<String, Object> param) throws Exception {
		activityDao.saveBetActivity(param);
	}
	@Override
	public List<CustomerActivity> queryActivityList(Map<String, Object> param)
			throws Exception {
		return activityDao.queryActivityList(param);
	}

	@Override
	public CustomerActivityVO queryActivityDetail(Map<String, Object> param)
			throws Exception {
		return activityDao.queryActivityDetail(param);
	}

	@Override
	public Map<String, Object> saveActivityAward(Map<String, Object> param)
			throws Exception {
		CustomerActivityVO actVo = (CustomerActivityVO) param.get("activityKey");
		CustomerUser user = (CustomerUser) param.get(CommonUtil.CUSTOMERUSERKEY);
		
		String isClient = (String) param.get("isClient");
		String cpuId = (String) param.get("cpuId");
		String diskId = (String) param.get("diskId");
		
		//公共检查部分：检查活动是否失效，用户是否在黑名单或黑ip中，活动是否在有效期间。
		CustomerActivity activity = activityDao.queryById(actVo.getId());
		if(activity.getStatus()!=DataDictionaryUtil.STATUS_OPEN){
			throw new LotteryException("亲，该活动已失效，无法领取奖励哦！");
		}
		
		if(activity.getBlackCustomer()!=null){
			String[] userArr = activity.getBlackCustomer().split(",");
			for(int i=0;i<userArr.length;i++){
				if(userArr[i].equals(user.getCustomerName())){
					throw new LotteryException("亲，您没有该活动的参与资格，无法领取奖励哦！");
				}
			}
		}
		
		if(activity.getBlackIp()!=null&&StringUtils.isEmpty(user.getIp())){
			String[] userArr = activity.getBlackCustomer().split(",");
			for(int i=0;i<userArr.length;i++){
				if(userArr[i].equals(user.getIp())){
					throw new LotteryException("亲，您没有该活动的参与资格，无法领取奖励哦！");
				}
			}
		}
		
		if(activity.getStarttime().compareTo(DateUtil.getStringDate())>0){
			throw new LotteryException("亲，该活动还未开始，无法领取奖励哦！");
		}else if(activity.getEndtime().compareTo(DateUtil.getStringDate())<0){
			throw new LotteryException("亲，该活动已经结束，无法领取奖励哦！");
		}
		
		//首先判断领取活动的来源途径，如果不符合来源要求直接提示无法领取奖励
		if(activity.getSourceType()!=null&&activity.getSourceType().equals(CommonUtil.ACTIVITY_SOURCE_CLIENT)){
			if(isClient==null||cpuId==null||!isClient.equals("true")){
				throw new LotteryException("亲，该活动必须使用客户端，才能领取奖励哦！");
			}
			//判断是否相同id的不同账号刷钱用户
			String sql ="SELECT u.customer_name,u.rsvst1,u.rsvst2 FROM t_customer_activity_log l "
					+ " ,t_customer_user u,t_customer_order o  "
					+ " WHERE activity_id = :aid AND l.customer_id = u.id AND l.order_id = o.id "
					+ " AND o.order_status in(:sts) GROUP BY u.id ";
			
			SQLQuery sqlQuery = activityDao.getSession().createSQLQuery(sql);
			sqlQuery.setParameter("aid", activity.getId());
			
			List<Integer> sts = new ArrayList<Integer>(2);
			sts.add(DataDictionaryUtil.ORDER_STATUS_SUCCESS);
			sts.add(DataDictionaryUtil.ORDER_STATUS_DISPOSING);
			sqlQuery.setParameterList("sts", sts.toArray());
			
			List<Object[]> objs = sqlQuery.list();
			for(Object[] obj : objs){
				if(obj[1]==null||obj[2]==null){
					continue;
				}
				String cId = obj[1].toString();
				String dId = obj[2].toString();
				if(cpuId.equals(cId)&&diskId.equals(dId)){
					throw new LotteryException("亲，该活动您已经申请领取过奖励了哦！");
				}
			}
			
		}else if(activity.getSourceType()!=null&&activity.getSourceType().equals(CommonUtil.ACTIVITY_SOURCE_WEB)){
			if(isClient!=null||cpuId!=null){
				throw new LotteryException("亲，该活动必须网页登录平台，才能领取奖励哦！");
			}
		}
		
		if(activity.getModel().equals("PAUTO")){
			throw new LotteryException("亲，该活动奖励无须点击领取，会自动发放！");
		}
		
		param.put("activity", activity);
		Map<String, Object> returnMap = null;
		switch (activity.getType()) {
		case DataDictionaryUtil.ACTIVITY_TYPE_REG:
			returnMap = activityDao.saveRegActivityAward(param);
			break;
		case DataDictionaryUtil.ACTIVITY_TYPE_FRC:
			returnMap = activityDao.saveFrcActivityAward(param);
			break;
		case DataDictionaryUtil.ACTIVITY_TYPE_TRC:
		case DataDictionaryUtil.ACTIVITY_TYPE_ORC:
		case DataDictionaryUtil.ACTIVITY_TYPE_BET:
		case DataDictionaryUtil.ACTIVITY_TYPE_AWA:
			returnMap = activityDao.saveBetActivityAward(param);
			break;
		case DataDictionaryUtil.ACTIVITY_LUCK_TYPE_ORC:
			returnMap = activityDao.saveLuckActivityAward(param);
			break;
		default:
			
			break;
		}
		return returnMap;
	}

	@Override
	public Page<CustomerActivityVO, CustomerActivity> queryActivitys(
			Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		CustomerActivityVO activityvo = (CustomerActivityVO) param.get("activityVO");
		CustomerUser customer = userDao.queryUserByName(activityvo.getCustomerName());
		activityvo.setCustomerId(customer==null?0:customer.getId());
		param.put("activityVO", activityvo);
		Page<CustomerActivityVO, CustomerActivity> page= activityDao.queryActivitys(param);
		List<CustomerActivity> entitys = page.getEntitylist();
		List<CustomerActivityVO> vos = new ArrayList<CustomerActivityVO>();
		for(CustomerActivity entity:entitys){
			CustomerActivityVO vo = new CustomerActivityVO();
			DozermapperUtil.getInstance().map(entity, vo);
			int count = activityLogDao.getActivityPeoples(vo.getId());
			vo.setCustomerCount(count);
			vos.add(vo);
		}
		page.setPagelist(vos);
		return page;
	}

	@Override
	public Page<CustomerActivityVO, CustomerActivity> querMyActivityRecord(Map<String, Object> param) throws Exception {
		return activityDao.querMyActivityRecord(param);
	}

	@Override
	public void saveRegActivity(Map<String, Object> param) throws Exception {
		activityDao.saveRegActivity(param);
	}

	/**
	 * 修改
	 * george
	 * @param param
	 * @throws Exception
	 */
	@Override
	public void updateRegActivity(Map<String, Object> param) throws Exception {
		activityDao.updateRegActivity(param);
	}
	
	
	@Override
	public void saveFrcActivity(Map<String, Object> param) throws Exception {
		activityDao.saveFrcActivity(param);
	}

	@Override
	public CustomerActivity showActivityOrder(Map<String, Object> param)
			throws Exception {
		CustomerActivityVO vo = (CustomerActivityVO) param.get("activityKey");
		CustomerActivity activity = activityDao.queryById(vo.getId());
		return activity;
	}
	
	/**
	 * 根据ID查询
	 * george
	 */
	@Override
	public CustomerActivityVO queryCustomerActivityById(long id)
			throws Exception {
		// TODO Auto-generated method stub
		CustomerActivityVO vo=new CustomerActivityVO();
		CustomerActivity activity = activityDao.queryById(id);
		PropertyUtils.copyProperties(vo, activity);
		return vo;
	}
	
	/**
	 * 修改 george
	 */
	@Override
	public void updateFrcActivity(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		activityDao.updateFrcActivity(param);
	}

	/**
	 * 游戏活动保存修改
	 * george
	 */
	@Override
	public void updatesBetActivity(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		activityDao.updatesBetActivity(param);
	}
	/**
	 * 抽奖活动保存
	 */
	@Override
	public void saveLuckActivity(Map<String, Object> param) throws Exception {
		activityDao.saveLuckActivity(param);
	}
}
