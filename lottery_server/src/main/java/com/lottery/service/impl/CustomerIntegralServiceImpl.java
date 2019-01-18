package com.lottery.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.bean.entity.CustomerIntegral;
import com.lottery.bean.entity.CustomerIntegralLog;
import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.vo.CustomerIntegralVO;
import com.lottery.dao.ICustomerIntegralDao;
import com.lottery.dao.ICustomerIntegralLogDao;
import com.lottery.service.ICustomerIntegralService;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.util.DozermapperUtil;

@Service
public class CustomerIntegralServiceImpl implements ICustomerIntegralService{

	@Autowired
	private ICustomerIntegralDao integralDao; 
	
	@Autowired
	private ICustomerIntegralLogDao integralLogDao;
	
	@Override
	public CustomerIntegralVO getSelfIntegral(long customerId) throws Exception {
		// TODO Auto-generated method stub
		CustomerIntegralVO vo = new CustomerIntegralVO();
		CustomerIntegral integral = integralDao.getSelfIntegral(customerId);
		DozermapperUtil.getInstance().map(integral, vo);
		boolean isRegistration = integralLogDao.getIsRegistration(customerId);
		vo.setRegistration(isRegistration);
		if(!isRegistration){
			CustomerIntegralLog integralLog = integralLogDao.getSelfLastDayIntegralLog(customerId);
			if(integralLog==null){
				vo.setContinuou(0);
				vo.setTodyIntegral(10);
			}else{
				switch (integralLog.getIntegral()) {
				case 100:
					vo.setTodyIntegral(10);
					break;
				case 28:
					vo.setTodyIntegral(100);
					break;
				default:
					vo.setTodyIntegral(integralLog.getIntegral()+2);
					break;
				}
			}
		}else{
			vo.setTodyIntegral(10);
		}
		return vo;
	}

	@Override
	public CustomerIntegralVO updateRegistration(CustomerUser user) throws Exception {
		// TODO Auto-generated method stub
		long customerId = user.getId();
		CustomerIntegral integral = integralDao.getSelfIntegral(customerId);
		boolean isRegistration = integralLogDao.getIsRegistration(customerId);
		CustomerIntegralLog log = new CustomerIntegralLog();
		if(!isRegistration){
			CustomerIntegralLog integralLog = integralLogDao.getSelfLastDayIntegralLog(customerId);
			if(integralLog==null){
				plusIntegralLevel(integral,10);
				integral.setContinuou(1);
				log.setIntegral(10);
			}else{
				integral.setContinuou(integral.getContinuou()+1);
				switch (integralLog.getIntegral()) {
				case 100:
					plusIntegralLevel(integral,10);
					log.setIntegral(10);
					break;
				case 28:
					plusIntegralLevel(integral,100);
					log.setIntegral(100);
					break;
				default:
					plusIntegralLevel(integral,integralLog.getIntegral()+2);
					log.setIntegral(integralLog.getIntegral()+2);
					break;
				}
			}
		}else{
			throw new LotteryException("今日已经完成签到");
		}
		integralDao.update(integral);
		log.setLevel(integral.getLevel());
		log.addInit(user.getCustomerName());
		log.setCustomerId(user.getId());
		integralLogDao.insert(log);
		CustomerIntegralVO vo = new CustomerIntegralVO();
		DozermapperUtil.getInstance().map(integral, vo);
		vo.setRegistration(isRegistration);
		return vo;
	}
	
	private CustomerIntegral plusIntegralLevel(CustomerIntegral entity,Integer integral){
		//如果达到9级 则不用升级
		if(entity.getLevel()==9){
			entity.setIntegral(entity.getIntegral()+integral);
			return entity;
		}
		entity.setIntegral(entity.getIntegral()+integral);
		int tempIntegral = entity.getIntegral();
		if(tempIntegral<800){
			entity.setLevel(1);
		}else if(tempIntegral>800&&tempIntegral<1200){
			entity.setLevel(2);
		}else if(tempIntegral>1200&&tempIntegral<1800){
			entity.setLevel(3);
		}else if(tempIntegral>1800&&tempIntegral<2700){
			entity.setLevel(4);
		}else if(tempIntegral>2700&&tempIntegral<4050){
			entity.setLevel(5);
		}else if(tempIntegral>4050&&tempIntegral<6075){
			entity.setLevel(6);
		}else if(tempIntegral>6075&&tempIntegral<9112){
			entity.setLevel(7);
		}else if(tempIntegral>9112&&tempIntegral<13668){
			entity.setLevel(8);
		}else if(tempIntegral>13668&&tempIntegral<20502){
			entity.setLevel(9);
		}
		return entity;
	}

}
