package com.lottery.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.BankManage;
import com.lottery.bean.entity.CustomerBankCard;
import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.VCustomerBindCard;
import com.lottery.bean.entity.vo.BankManageVO;
import com.lottery.bean.entity.vo.CustomerBankCardVO;
import com.lottery.dao.ICustomerBankCardDao;
import com.lottery.dao.ICustomerUserDao;
import com.lottery.dao.impl.AdminWriteLog;
import com.lottery.service.ICustomerBankCardService;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;
import com.xl.lottery.util.DateUtil;
import com.xl.lottery.util.DozermapperUtil;

@Service
public class CustomerBankCardServiceImpl implements ICustomerBankCardService{

	@Autowired
	private ICustomerBankCardDao bankCardDao;
	
	@Autowired
	private ICustomerUserDao customerUserDao;
	
	@Autowired
	private AdminWriteLog adminWriteLog;
	
	@Override
	public String saveBankCard(final Map<String,Object> param) throws LotteryException {
		CustomerBankCard entity = new CustomerBankCard();
		AdminUser admin = (AdminUser) param.get(CommonUtil.USERKEY);
		CustomerBankCardVO bankvo = (CustomerBankCardVO) param.get("bankCardKey");
		bankvo.setBankcardStatus(DataDictionaryUtil.STATUS_OPEN);
		bankvo.setCreateTime(DateUtil.getNowDate());
		bankvo.setCreateUser(admin.getUserName());
		bankvo.setUpdateTime(DateUtil.getNowDate());
		bankvo.setUpdateUser(admin.getUserName());
		BeanUtils.copyProperties(bankvo, entity);
		bankCardDao.insert(entity);
		adminWriteLog.saveWriteLog(admin, CommonUtil.SAVE, "CustomerBankCard",
				entity.toString());
		return "success";
	}

	@Override
	public int countBankCard() throws Exception {
		return bankCardDao.countBankCard();
	}

	@Override
	public List<CustomerBankCard> queryBankCardList() throws Exception {
		return bankCardDao.queryAll();
	}

	@Override
	public Page<CustomerBankCardVO, CustomerBankCard> findBankCardListByPage(
			Map<String, ?> param) throws Exception {
		Page<CustomerBankCardVO, CustomerBankCard> page = bankCardDao.findBankCardListByPage(param);
		List<CustomerBankCard> entitys = page.getEntitylist();
		List<CustomerBankCardVO> vos = new ArrayList<CustomerBankCardVO>();
		for(CustomerBankCard entity : entitys){
			CustomerBankCardVO vo = new CustomerBankCardVO();
			DozermapperUtil.getInstance().map(entity, vo);
			List<Object[]> objs = bankCardDao.getBackCardCountAndMoneyAndLevelAndInventoryName(vo.getId());
			if(objs.size()>0){
				vo.setCardCount(((BigInteger)objs.get(0)[1]).intValue());
				vo.setCardMoney(((BigDecimal)objs.get(0)[2]));
				vo.setCardInventoryName(objs.get(0)[3].toString());
				vo.setBindCount(((BigInteger)objs.get(0)[4]).intValue());
			}
			vos.add(vo);
		}
		page.setPagelist(vos);
		return page;
	}

	@Override
	public CustomerBankCard queryBankCardById(long parseLong) {
		return bankCardDao.queryById(parseLong);
	}

	@Override
	public void updateBankCard(Map<String, Object> param) throws Exception {
		AdminUser admin = (AdminUser) param.get(CommonUtil.USERKEY);
		CustomerBankCardVO cardVo = (CustomerBankCardVO) param.get("bankCardKey");
		CustomerBankCard bankCard =bankCardDao.queryById(cardVo.getId());
		bankCard.setBankName(cardVo.getBankName());
		bankCard.setBankId(cardVo.getBankId());
		bankCard.setBranceBankName(cardVo.getBranceBankName());
		bankCard.setRemark(cardVo.getRemark());
		bankCard.setBankcardAddress(cardVo.getBankcardAddress());
		bankCard.setBranceBankName(cardVo.getBranceBankName());
		bankCard.setCardNo(cardVo.getCardNo());
		bankCard.setOpencardName(cardVo.getOpencardName());
		bankCard.setCardInventoryId(cardVo.getCardInventoryId());
		bankCard.setCardLevel(cardVo.getCardLevel());
		bankCardDao.update(bankCard);
		adminWriteLog.saveWriteLog(admin, CommonUtil.UPDATE, "CustomerBankCard",
				bankCard.toString());
	}

	@Override
	public void updateDisableBankCard(Map<String, Object> param) throws Exception {
		AdminUser admin = (AdminUser) param.get(CommonUtil.USERKEY);
		CustomerBankCardVO cardVo = (CustomerBankCardVO) param.get("bankCardKey");
		CustomerBankCard bankCard =bankCardDao.queryById(cardVo.getId());
		bankCard.setBankcardStatus(DataDictionaryUtil.STATUS_CLOSE);
		bankCardDao.update(bankCard);
		adminWriteLog.saveWriteLog(admin, CommonUtil.UPDATE, "CustomerBankCard",
				bankCard.toString());
	}

	@Override
	public void deleteBankCard(Map<String, Object> param) throws Exception {
		AdminUser admin = (AdminUser) param.get(CommonUtil.USERKEY);
		CustomerBankCardVO cardVo = (CustomerBankCardVO) param.get("bankCardKey");
		CustomerBankCard bankCard =bankCardDao.queryById(cardVo.getId());
		bankCard.setBankcardStatus(DataDictionaryUtil.STATUS_DELETE);
		bankCardDao.update(bankCard);
		adminWriteLog.saveWriteLog(admin, CommonUtil.UPDATE, "CustomerBankCard",
				bankCard.toString());
	}

	@Override
	public void updateEnableBankCard(Map<String, Object> param)throws Exception {
		AdminUser admin = (AdminUser) param.get(CommonUtil.USERKEY);
		CustomerBankCardVO cardVo = (CustomerBankCardVO) param.get("bankCardKey");
		CustomerBankCard bankCard =bankCardDao.queryById(cardVo.getId());
		bankCard.setBankcardStatus(DataDictionaryUtil.STATUS_OPEN);
		bankCardDao.update(bankCard);
		adminWriteLog.saveWriteLog(admin, CommonUtil.UPDATE, "CustomerBankCard",
				bankCard.toString());
	}

	@Override
	public List<CustomerBankCard> getBankCard(Map<String, Object> param)
			throws Exception {
		// TODO Auto-generated method stub
		return bankCardDao.getBankCard(param);
	}
	
	@Override
	public List<CustomerBankCardVO> getBankCardVO(Map<String, Object> param)
			throws Exception {
		// TODO Auto-generated method stub
		List<CustomerBankCard> entitys = bankCardDao.getBankCard(param);
		List<CustomerBankCardVO> vos = new ArrayList<CustomerBankCardVO>();
		for(CustomerBankCard entity : entitys){
			CustomerBankCardVO vo = new CustomerBankCardVO();
			DozermapperUtil.getInstance().map(entity, vo);
			List<Object[]> objs = bankCardDao.getBackCardCountAndMoneyAndLevelAndInventoryName(vo.getId());
			if(objs.size()>0){
				vo.setCardCount(((BigInteger)objs.get(0)[1]).intValue());
				vo.setCardMoney(((BigDecimal)objs.get(0)[2]));
				vo.setCardInventoryName(objs.get(0)[3].toString());
				vo.setBindCount(((BigInteger)objs.get(0)[4]).intValue());
			}
			vos.add(vo);
		}
		return vos;
	}

	@Override
	public List<VCustomerBindCard> findCardsByUserId(Map<String, ?> param)
			throws Exception {
		// TODO Auto-generated method stub
		return bankCardDao.findCardsByUserId(param);
	}

	@Override
	public List<CustomerBankCard> findBankCardsNotByUserId(Map<String, ?> param)
			throws Exception {
		return bankCardDao.findBankCardsNotByUserId(param);
	}

	@Override
	public List<CustomerBankCard> queryCardListByInv(Map<String, Object> param)
			throws Exception {
		List<CustomerBankCard> list = bankCardDao.queryCardListByInv(param);
		for(CustomerBankCard card : list ){
			param.clear();
			param.put("cardIdKey", card.getId());
			List<CustomerUser> users = customerUserDao.queryBindCardUser(param);
			List<CustomerUser> agent0Users = new ArrayList<CustomerUser>();
			for(CustomerUser user : users){
				if(user.getCustomerLevel()==0){
					agent0Users.add(user);
				}
			}
			card.setBindCardUsers(agent0Users);
		}
		return list;
	}
	

}
