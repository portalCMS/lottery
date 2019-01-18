package com.lottery.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.UserCardInventory;
import com.lottery.bean.entity.vo.UserCardInventoryVO;
import com.lottery.dao.IUserCardInventoryDao;
import com.lottery.dao.impl.AdminWriteLog;
import com.lottery.service.IUserCardInventoryService;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;
import com.xl.lottery.util.DateUtil;

@Service
public class UserCardInventoryServiceImpl implements IUserCardInventoryService{
	
	@Autowired
	private IUserCardInventoryDao inventoryDao;
	
	@Autowired
	private AdminWriteLog adminWriteLog;
	@Override
	public List<UserCardInventory> queryInventorys(Map<String, Object> param) throws Exception {
		return inventoryDao.queryInventorys(param);
	}
	@Override
	public void insertCardInv(Map<String, Object> param) throws Exception {
		AdminUser user = (AdminUser) param.get(CommonUtil.USERKEY);
		UserCardInventoryVO vo = (UserCardInventoryVO) param.get("cardInvVoKey");
		UserCardInventory cardInv = new UserCardInventory();
		cardInv.setInventoryName(vo.getCardInventoryName());
		cardInv.setRemark(vo.getRemark());
		cardInv.setStatus(DataDictionaryUtil.STATUS_OPEN);
		cardInv.setCreateTime(DateUtil.getNowDate());
		cardInv.setUpdateTime(DateUtil.getNowDate());
		cardInv.setCreateUser(user.getUserName());
		cardInv.setUpdateUser(user.getUserName());
		inventoryDao.save(cardInv);
		adminWriteLog.saveWriteLog(user, CommonUtil.SAVE, "t_user_card_inventory", cardInv.toString());
	}
	@Override
	public void updateCardInv(Map<String, Object> param) throws Exception {
		AdminUser user = (AdminUser) param.get(CommonUtil.USERKEY);
		UserCardInventoryVO vo = (UserCardInventoryVO) param.get("cardInvVoKey");
		UserCardInventory cardInv = inventoryDao.queryById(vo.getId());
		cardInv.setInventoryName(vo.getCardInventoryName());
		cardInv.setRemark(vo.getRemark());
		cardInv.setUpdateTime(DateUtil.getNowDate());
		cardInv.setUpdateUser(user.getUserName());
		inventoryDao.update(cardInv);
		adminWriteLog.saveWriteLog(user, CommonUtil.UPDATE, "t_user_card_inventory", cardInv.toString());
	}

}
