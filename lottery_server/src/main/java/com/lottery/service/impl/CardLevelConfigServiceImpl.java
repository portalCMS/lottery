package com.lottery.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.CardLevelConfig;
import com.lottery.bean.entity.vo.CardLevelConfigVO;
import com.lottery.dao.ICardLevelConfigDao;
import com.lottery.service.ICardLevelConfigService;
import com.xl.lottery.util.BeanPropertiesCopy;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;
import com.xl.lottery.util.DateUtil;

@Service
public class CardLevelConfigServiceImpl implements ICardLevelConfigService{

	@Autowired
	private ICardLevelConfigDao configDao;
	@Override
	public void saveCardLevel(Map<String, Object> param) throws Exception {
		CardLevelConfigVO configVo = (CardLevelConfigVO) param.get("levelVoKey");
		AdminUser user = (AdminUser) param.get(CommonUtil.USERKEY);
		//保存先设置旧数据的状态为失效。
		configDao.deleteCardLevel(param);
		
		for(CardLevelConfigVO vo : configVo.getVoList()){
			CardLevelConfig config = new CardLevelConfig();
			config.setActiveLevel(vo.getActiveLevel());
			config.setCardInventoryId(vo.getCardInventoryId());
			config.setCardInventoryName(vo.getCardInventoryName());
			config.setMinAmount(vo.getMinAmount());
			config.setMaxAmount(vo.getMaxAmount());
			config.setCardLevel(vo.getCardLevel());
			config.setStatus(DataDictionaryUtil.STATUS_OPEN);
			config.setCreateTime(DateUtil.getNowDate());
			config.setUpdateTime(DateUtil.getNowDate());
			config.setCreateUser(user.getUserName());
			config.setUpdateUser(user.getUserName());
			configDao.save(config);
		}
		
	}
	@Override
	public List<CardLevelConfig> queryCardLevel(Map<String, Object> param) throws Exception {
		return configDao.queryCardLevel(param);
		
	}

}
