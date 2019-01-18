package com.lottery.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.BankManage;
import com.lottery.bean.entity.CustomerUrl;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.BankManageVO;
import com.lottery.bean.entity.vo.CustomerUrlVO;
import com.lottery.dao.IBankManageDao;
import com.lottery.dao.impl.AdminWriteLog;
import com.lottery.service.IBankManageService;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.util.BeanPropertiesCopy;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;
import com.xl.lottery.util.DateUtil;
import com.xl.lottery.util.RandDomUtil;

@Service
public class BankManageServiceImpl implements IBankManageService {

	@Autowired
	private IBankManageDao bankManageDao;

	@Autowired
	private AdminWriteLog adminWriteLog;

	@Override
	public Page<BankManageVO, BankManage> findBankeManageList(Map<String, ?> param) throws Exception {
		// TODO Auto-generated method stub
		Page<BankManageVO, BankManage> page = bankManageDao.findBankeManageList(param);
		return page;
	}

	@Override
	public String saveBank(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		BankManageVO bankvo = (BankManageVO) param.get("bankmanagekey");
		AdminUser admin = (AdminUser) param.get(CommonUtil.USERKEY);
		BankManage entity = new BankManage();
		bankvo.setStatus(DataDictionaryUtil.STATUS_OPEN);
		bankvo.setCreateTime(DateUtil.getNowDate());
		bankvo.setCreateUser(admin.getUserName());
		bankvo.setUpdateTime(DateUtil.getNowDate());
		bankvo.setUpdateUser(admin.getUserName());
		BeanUtils.copyProperties(entity, bankvo);
		if (checkBankName(entity.getName())) {
			bankManageDao.insert(entity);
			adminWriteLog.saveWriteLog(admin, CommonUtil.SAVE, "BankManage",
					entity.toString());
		} else {
			throw new LotteryException("银行已存在");
		}
		return "success";
	}

	@SuppressWarnings("unchecked")
	protected boolean checkBankName(String bankName) {
		StringBuffer sqlquery = new StringBuffer(
				"from BankManage t where t.status = "+DataDictionaryUtil.STATUS_OPEN+" and t.name = '" + bankName.trim() + "'");
		List<BankManage> banklike = bankManageDao
				.getSession().createQuery(sqlquery.toString()).list();
		return banklike.size() == 0 ? true : false;
	}

	@Override
	public String updateBank(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		BankManageVO bankvo = (BankManageVO) param.get("bankmanagekey");
		AdminUser admin = (AdminUser) param.get(CommonUtil.USERKEY);
		BankManage entityOld = bankManageDao.queryById(bankvo.getId());
		entityOld.setUpdateTime(DateUtil.getNowDate());
		entityOld.setUpdateUser(admin.getUserName());
		//BeanUtils.copyProperties(entityOld, bankvo);
		BeanPropertiesCopy.copyProperties(bankvo, entityOld);
		bankManageDao.update(entityOld);
		adminWriteLog.saveWriteLog(admin, CommonUtil.UPDATE, "BankManage",
				entityOld.toString());
		return "success";
	}

	@Override
	public String deleteBank(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BankManage findBankById(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		BankManageVO bankvo = (BankManageVO) param.get("bankmanagekey");
		return bankManageDao.queryById(bankvo.getId());
	}
	
	@Override
	public List<BankManage> findBankeManageList() throws Exception {
		return bankManageDao.queryAll();
	}

	@Override
	public int countBankManage() throws Exception {
		return bankManageDao.countBankManage();
	}

	@Override
	public List<BankManage> findBanks(Map<String, Object> param)
			throws Exception {
		// TODO Auto-generated method stub
		List<BankManage> banks = bankManageDao.queryAll();
		for (Iterator<BankManage> iterator = banks.iterator(); iterator.hasNext();) {  
			BankManage bank = iterator.next();
			if(bank.getStatus()==DataDictionaryUtil.STATUS_CLOSE){
				iterator.remove();  
			}
        }  
		return banks;
	}

	@Override
	public List<BankManage> findCanBindBankeList(Map<String, Object> param)
			throws Exception {
		List<BankManage> banks = bankManageDao.queryAll();
		List<BankManage> removeBanks = new ArrayList<BankManage>();
		for(BankManage bank : banks){
			if(bank.getBind()!=DataDictionaryUtil.COMMON_FLAG_1){
				removeBanks.add(bank);
			}
		}
		banks.removeAll(removeBanks);
		return banks;
	}

}
