package com.lottery.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.BonusGroup;
import com.lottery.bean.entity.DataDictionary;
import com.lottery.bean.entity.NoRebatesBonusGroup;
import com.lottery.bean.entity.vo.BonusGroupVO;
import com.lottery.bean.entity.vo.NoRebatesBonusGroupVO;
import com.lottery.dao.IBonusGroupDao;
import com.lottery.dao.INoRebatesBonusGroupDao;
import com.lottery.dao.impl.AdminWriteLog;
import com.lottery.service.IBonusGroupService;
import com.lottery.service.INoRebatesBonusGroupService;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.util.BeanPropertiesCopy;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;

@Service
public class BonusGroupServiceImpl implements IBonusGroupService{

	@Autowired
	private IBonusGroupDao bonusGroupDao;
	
	@Autowired
	private AdminWriteLog adminWriteLog;
	
	@Autowired
	private INoRebatesBonusGroupDao noRebatesBonusGroupDao;
	
	@Override
	public List<BonusGroup> findBonusGroupAll(Map<String, ?> param) {
		// TODO Auto-generated method stub
		return bonusGroupDao.findBonusGroupAll(param);
	}

	@Override
	public String saveBonusGroups(Map<String, ?> param) throws Exception {
		// TODO Auto-generated method stub
		BonusGroup entity = new BonusGroup();
		//保存奖金组
		BonusGroupVO vo = (BonusGroupVO) param.get("bonusvokey");
		AdminUser user = (AdminUser) param.get(CommonUtil.USERKEY);
		if(!checkBonusGroup(vo.getName(),0)){
			throw new LotteryException("奖金组名称已存在");
		}
		BeanPropertiesCopy.copyProperties(vo, entity);
		entity.addInit(user.getUserName());
		entity.setStatus(DataDictionaryUtil.STATUS_OPEN);
		bonusGroupDao.save(entity);
		adminWriteLog.saveWriteLog(user, CommonUtil.SAVE, "t_bonus_group", entity.toString());
		//保存无返点奖金组
		NoRebatesBonusGroupVO nrvos = (NoRebatesBonusGroupVO) param.get("norebatesvokey");
		for(NoRebatesBonusGroupVO nrvo:nrvos.getVos()){
			NoRebatesBonusGroup nrentity = new NoRebatesBonusGroup();
			BeanPropertiesCopy.copyProperties(nrvo, nrentity);
			nrentity.setStatus(DataDictionaryUtil.STATUS_OPEN);
			nrentity.addInit(user.getUserName());
			nrentity.setBonusGroupId(entity.getId());
			noRebatesBonusGroupDao.save(nrentity);
			adminWriteLog.saveWriteLog(user, CommonUtil.SAVE, "t_norebates_bonus_group", nrentity.toString());
		}
		return "success";
	}

	@Override
	public String updateBonusGroups(Map<String, ?> param) throws Exception {
		// TODO Auto-generated method stub
		BonusGroupVO vo = (BonusGroupVO) param.get("bonusvokey");
		AdminUser user = (AdminUser) param.get(CommonUtil.USERKEY);
		BonusGroup entity = bonusGroupDao.queryById(vo.getId());
		updateBonusGroup(vo, entity);
		if(!checkBonusGroup(vo.getName(),vo.getId())){
			throw new LotteryException("奖金组名称已存在");
		}
		entity.updateInit(user.getUserName());
		bonusGroupDao.update(entity);
		adminWriteLog.saveWriteLog(user, CommonUtil.UPDATE, "t_bonus_group", entity.toString());
		NoRebatesBonusGroupVO nrvos = (NoRebatesBonusGroupVO) param.get("norebatesvokey");
		for(NoRebatesBonusGroup nrentity :noRebatesBonusGroupDao.queryAll()){
			if(nrentity.getBonusGroupId() == entity.getId()){
				noRebatesBonusGroupDao.delete(nrentity);
			}
		}
		for(NoRebatesBonusGroupVO nrvo:nrvos.getVos()){
			NoRebatesBonusGroup nrentity = new NoRebatesBonusGroup();
			BeanPropertiesCopy.copyProperties(nrvo, nrentity);
			nrentity.setStatus(DataDictionaryUtil.STATUS_OPEN);
			nrentity.addInit(user.getUserName());
			nrentity.setBonusGroupId(entity.getId());
			noRebatesBonusGroupDao.save(nrentity);
			adminWriteLog.saveWriteLog(user, CommonUtil.SAVE, "t_norebates_bonus_group", nrentity.toString());
		}
		return "success";
	}

	private void updateBonusGroup(BonusGroupVO vo,BonusGroup entity){
		if(entity==null)entity = new BonusGroup();
		entity.setName(vo.getName());
		entity.setRebates(vo.getRebates());
		entity.setPayoutRatio(vo.getPayoutRatio());
		entity.setMargin(vo.getMargin());
		entity.setRemark(vo.getRemark());
	}
	
	@SuppressWarnings("unchecked")
	private boolean checkBonusGroup(String name,long id){
		StringBuffer sql = new StringBuffer("from BonusGroup t where t.name = ? and t.status = 10002 ");
		Query query = bonusGroupDao.getSession().createQuery(sql.toString());
		query.setParameter(0, name);
		List<BonusGroup> entitys = query.list();
		if(id==0&&entitys.size()>0)return false;
		if(id>0){
			BonusGroup entity = entitys.get(0);
			if(id==entity.getId())return true;
			return false;
		}
		return true;
	}

	@Override
	public BonusGroup findBonusGroupById(Map<String, ?> param) throws Exception {
		// TODO Auto-generated method stub
		BonusGroupVO vo = (BonusGroupVO) param.get("bonusvokey");
		if(vo.getId() == 0)throw new LotteryException("id不存在");
		BonusGroup entity = bonusGroupDao.queryById(vo.getId());
		if(entity==null)throw new LotteryException("无此奖金组");
		return entity;
	}
	
}
