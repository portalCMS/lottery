package com.lottery.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.AdvertisingList;
import com.lottery.bean.entity.AdvertisingRegion;
import com.lottery.bean.entity.vo.AdvertisingRegionVO;
import com.lottery.dao.IAdvertisingListDao;
import com.lottery.dao.IAdvertisingRegionDao;
import com.lottery.dao.impl.AdminWriteLog;
import com.lottery.service.IAdvertisingRegionService;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DozermapperUtil;

@Service
public class AdvertisingRegionServiceImpl implements IAdvertisingRegionService{

	@Autowired
	private IAdvertisingListDao advertsDao;
	
	@Autowired
	private IAdvertisingRegionDao advertRegionDao;
	
	@Autowired
	private AdminWriteLog adminlog;
	
	@Override
	public List<AdvertisingRegionVO> queryAdvertRegion(Map<String, Object> param)
			throws Exception {
		// TODO Auto-generated method stub
		List<AdvertisingRegion> regions = advertRegionDao.queryAll();
		List<AdvertisingRegionVO> regionvos = new ArrayList<AdvertisingRegionVO>();
		for(AdvertisingRegion region : regions){
			AdvertisingRegionVO vo = new AdvertisingRegionVO();
			DozermapperUtil.getInstance().map(region, vo);
			List<AdvertisingList> adverts = advertsDao.queryAdvertsByRegionCode(vo.getRegionCode());
			if(adverts==null)adverts=new ArrayList<AdvertisingList>();
			vo.setAdvers(adverts);
			vo.setSettingCont(adverts.size());
			regionvos.add(vo);
		}
		return regionvos;
	}

	@Override
	public AdvertisingRegion queryAdvertRegionbyId(Map<String, Object> param)
			throws Exception {
		// TODO Auto-generated method stub
		long id = (long) param.get("id");
		AdvertisingRegion entity = advertRegionDao.queryById(id);
		return entity;
	}

	@Override
	public List<AdvertisingList> queryAdvertisingListByCode(
			Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		String regionCode = (String) param.get("regionCode");
		List<AdvertisingList> adverts = advertsDao.queryAdvertsByRegionCode(regionCode);
		return adverts;
	}

	@Override
	public String saveAdvertisingList(Map<String, Object> param)
			throws Exception {
		// TODO Auto-generated method stub
		AdvertisingRegionVO vo = (AdvertisingRegionVO) param.get("arvo");
		AdminUser user = (AdminUser) param.get(CommonUtil.USERKEY);
		List<AdvertisingList> entitys = vo.getAdvers();
		StringBuffer delString = new StringBuffer("delete from AdvertisingList t where regionCode = ? ");
		Query query = advertsDao.getSession().createQuery(delString.toString());
		query.setParameter(0, vo.getRegionCode());
		query.executeUpdate();
		for(AdvertisingList entity:entitys){
			if(entity==null||StringUtils.isEmpty(entity.getUrl())){
				throw new LotteryException("数据异常");
			}
			entity.addInit(user.getUserName());
			advertsDao.save(entity);
		}
		return "success";
	}

}
