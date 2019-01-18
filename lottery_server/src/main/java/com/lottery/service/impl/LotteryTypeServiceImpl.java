package com.lottery.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.BonusGroup;
import com.lottery.bean.entity.LotteryPlayBonus;
import com.lottery.bean.entity.LotteryPlayModel;
import com.lottery.bean.entity.LotterySource;
import com.lottery.bean.entity.LotteryType;
import com.lottery.bean.entity.PlayAwardLevel;
import com.lottery.bean.entity.PlayModel;
import com.lottery.bean.entity.SourceLink;
import com.lottery.bean.entity.vo.LotteryJsonVO;
import com.lottery.bean.entity.vo.LotteryListVO;
import com.lottery.bean.entity.vo.LotteryListVOWebApp;
import com.lottery.bean.entity.vo.LotteryPlayBonusListVO;
import com.lottery.bean.entity.vo.LotteryPlayBonusVO;
import com.lottery.bean.entity.vo.LotteryPlayModelListVO;
import com.lottery.bean.entity.vo.LotteryPlayModelVO;
import com.lottery.bean.entity.vo.LotteryTypeForm;
import com.lottery.bean.entity.vo.LotteryTypeVO;
import com.lottery.bean.entity.vo.PlayAwardLevelVO;
import com.lottery.bean.entity.vo.SourceLinkVO;
import com.lottery.bean.entity.vo.TempMapVO;
import com.lottery.dao.IBonusGroupDao;
import com.lottery.dao.ILotteryPlayBonusDao;
import com.lottery.dao.ILotteryPlayModelDao;
import com.lottery.dao.ILotterySourceDao;
import com.lottery.dao.ILotteryTypeDao;
import com.lottery.dao.IPlayAwardLevelDao;
import com.lottery.dao.IPlayModelDao;
import com.lottery.dao.ISourceLinkDao;
import com.lottery.dao.ITaskConfigDao;
import com.lottery.service.ILotterySourceService;
import com.lottery.service.ILotteryTypeService;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;
import com.xl.lottery.util.DateUtil;
import com.xl.lottery.util.DozermapperUtil;

@Service
public class LotteryTypeServiceImpl implements ILotteryTypeService{

	@Autowired 
	private ILotteryTypeDao lotteryDao;
	
	@Autowired
	private ILotteryPlayModelDao playModelDao;
	
	@Autowired
	private IPlayModelDao modelDao;
	
	@Autowired
	private ILotteryPlayBonusDao playBonusDao;
	
	@Autowired
	private IBonusGroupDao bonusDao;
	
	@Autowired
	private ISourceLinkDao linkDao;
	
	@Autowired
	private ILotterySourceDao lotterySourceDao;
	
	@Autowired
	private ILotterySourceService sourceService;
	
	@Autowired
	private ITaskConfigDao taskDao;
	
	@Autowired
	private ILotteryTypeDao typeDao;

	@Autowired
	private IPlayAwardLevelDao levelDao;
	
	@Override
	public List<LotteryType> queryLotteryList(Map<String, Object> param)
			throws Exception {
		 List<LotteryType> list = lotteryDao.queryLotteryList(param);
		return list;
	}
	
	@Override
	public List<LotteryType> queryLotteryEngineInfo(Map<String, Object> param)
			throws Exception {
		 List<LotteryType> list = lotteryDao.queryLotteryList(param);
		 Map<String,List<TempMapVO>> countMap = taskDao.countTaskStatus(param);
		 //统计当天执行成功或失败的奖期期数
		 if(countMap.get("sfVos")!=null&&countMap.get("sfVos").size()>0){
			 List<TempMapVO> fcVos = countMap.get("sfVos");
			 for(TempMapVO fcVo : fcVos){
				 for(LotteryType lot: list){
					if(lot.getLotteryCode().equals(fcVo.getKey())){
						int status = Integer.parseInt(fcVo.getValue2());
						if(status==DataDictionaryUtil.STATUS_SUCCESS
								||status==DataDictionaryUtil.HAND_STATUS_SUCCESS){
							lot.setCountSuccessSeries(fcVo.getValue());
						}else{
							lot.setCountFailedSeries(fcVo.getValue());
						}
						break;
					} 
				 }
			 }
		 }
		 if(countMap.get("curVos")!=null&&countMap.get("curVos").size()>0){
			//查询彩种的当前期号
			List<TempMapVO> curVos = countMap.get("curVos");
			for(TempMapVO curVo : curVos){
				for(LotteryType lot: list){
					if(lot.getLotteryCode().equals(curVo.getKey())){
						lot.setCurrentSeries(curVo.getValue());
						break;
					} 
				}
			 }
		 }
		 
		 for(LotteryType lot : list){
			 if(StringUtils.isEmpty(lot.getCountFailedSeries())){
				 lot.setCountFailedSeries("0");
			 }
			 if(StringUtils.isEmpty(lot.getCountSuccessSeries())){
				 lot.setCountSuccessSeries("0");
			 }
		 }
		return list;
	}

	@Override
	public void updateTaskRunStatus(Map<String, Object> param) throws Exception {
		LotteryTypeVO lotteryVo = (LotteryTypeVO) param.get("lotteryKey");
		LotteryType lottery = lotteryDao.queryById(lotteryVo.getId());
		//将对应的彩种生成奖期任务设置为开启状态
		lottery.setTaskRunStatus(DataDictionaryUtil.STATUS_OPEN);
		lotteryDao.update(lottery);
	}

	@Override
	public void saveAllLotteryInfo(Map<String, Object> param) throws Exception {
		AdminUser user = (AdminUser) param.get(CommonUtil.USERKEY);
		LotteryTypeVO lotteryVo = (LotteryTypeVO) param.get("lotteryKey");
		LotteryListVO seriesListVo = (LotteryListVO) param.get("seriesListKey");
		LotteryPlayBonusListVO playBonusListVo = (LotteryPlayBonusListVO) param.get("playBonusListKey");
		List<PlayModel> playModelList = (List<PlayModel>) param.get("playListKey");
		LotteryPlayModelListVO lpmListVo = (LotteryPlayModelListVO) param.get("lpmListKey");
		//保存号源设置
		for(int i=0;i<lotteryVo.getLinks().size();i++){
			SourceLinkVO linkVo = lotteryVo.getLinks().get(i);
			SourceLink link = new SourceLink();
			link.setSourceLink(linkVo.getSourceLink());
			link.setSourceName(linkVo.getSourceName());
			link.setSourceLevel(linkVo.getSourceLevel());
			link.setStatus(DataDictionaryUtil.COMMON_FLAG_1);
			link.setCreateTime(DateUtil.getNowDate());
			link.setUpdateTime(DateUtil.getNowDate());
			link.setCreateUser(user.getUserName());
			link.setUpdateUser(user.getUserName());
			linkDao.save(link);
			
			LotterySource lotterySource = new LotterySource();
			lotterySource.setLotteryCode(lotteryVo.getLotteryCode());
			lotterySource.setLotteryGroup(lotteryVo.getLotteryGroup());
			lotterySource.setSourceLinkId(link.getId());
			lotterySource.setStatus(DataDictionaryUtil.COMMON_FLAG_1);
			lotterySource.setCreateTime(DateUtil.getNowDate());
			lotterySource.setUpdateTime(DateUtil.getNowDate());
			lotterySource.setCreateUser(user.getUserName());
			lotterySource.setUpdateUser(user.getUserName());
			lotterySourceDao.save(lotterySource);
		}
		
		//保存彩种信息和奖期规则信息
		//配置为每天4点钟执行奖期生成任务
		String timeConfig = seriesListVo.getTimeConfig();
		String taskCornExpression = "0 0 4 ? * "+timeConfig;
		for(int i=0;i<seriesListVo.getLotterys().size();i++){
			LotteryTypeVO seriesVo = seriesListVo.getLotterys().get(i);
			LotteryType lottery = new LotteryType();
			lottery.setLotteryCode(lotteryVo.getLotteryCode());
			lottery.setLotteryName(lotteryVo.getLotteryName());
			lottery.setLotteryGroup(lotteryVo.getLotteryGroup());
			lottery.setTaskCornExpression(taskCornExpression);
			lottery.setBeforeLotTime(seriesVo.getBeforeLotTime());
			lottery.setCatchTimes(seriesVo.getCatchTimes());
			lottery.setTotalTimes(seriesVo.getTotalTimes());
			lottery.setStartTime(seriesVo.getStartTime());
			lottery.setEndTime(seriesVo.getEndTime());
			lottery.setSpanTime(seriesVo.getSpanTime());
			lottery.setSeriesRule(lotteryVo.getSeriesRule());
			lottery.setAfterLotTime(seriesVo.getAfterLotTime());
			lottery.setUpdateTime(DateUtil.getNowDate());
			lottery.setCreateTime(DateUtil.getNowDate());
			lottery.setCreateUser(user.getUserName());
			lottery.setUpdateUser(user.getUserName());
			lottery.setLotteryStatus(DataDictionaryUtil.STATUS_OPEN);
			lottery.setLotteryLevel(i+1);
			lottery.setTotalLimitBets(lpmListVo.getTotalLimitBets());
			lottery.setTotalLimitAmount(lpmListVo.getTotalLimitAmount());
			lotteryDao.save(lottery);
		}
		
		//循环设置的限注金额，put进map中。
		Map<String,BigDecimal> limitAmountMap = new HashMap<String,BigDecimal>();
		for(LotteryPlayModelVO lpmVo : lpmListVo.getLotteryPlayModels()){
			limitAmountMap.put(lpmVo.getModelCode(), lpmVo.getLimitAmount());
		}
		//保存彩种配置的玩法
		for(PlayModel pm : playModelList){
			LotteryPlayModel lpm = new LotteryPlayModel();
			lpm.setLotteryCode(lotteryVo.getLotteryCode());
			lpm.setLotteryGroup(lotteryVo.getLotteryGroup());
			lpm.setModelCode(pm.getModelCode());
			lpm.setStatus(DataDictionaryUtil.COMMON_FLAG_1);
			//保存彩种的限额配置
			lpm.setLimitAmount(limitAmountMap.get(pm.getModelCode()));
			lpm.setCreateTime(DateUtil.getNowDate());
			lpm.setUpdateTime(DateUtil.getNowDate());
			lpm.setCreateUser(user.getUserName());
			lpm.setUpdateUser(user.getUserName());
			playModelDao.save(lpm);
			
			//保存玩法对应的奖级
			if(null!=pm.getLevelList()){
				for(PlayAwardLevelVO palvo : pm.getLevelList()){
					PlayAwardLevel pal = new PlayAwardLevel(); 
					pal.setAwardLevel(palvo.getAwardLevel());
					pal.setLevelName(palvo.getLevelName());
					pal.setWinAmount(palvo.getWinAmount());
					pal.setWiningRate(palvo.getWiningRate());
					pal.setPlayCode(pm.getModelCode());
					pal.setLotteryCode(lotteryVo.getLotteryCode());
					pal.setStatus(DataDictionaryUtil.COMMON_FLAG_1);
					pal.setCreateTime(DateUtil.getNowDate());
					pal.setUpdateTime(DateUtil.getNowDate());
					pal.setCreateUser(user.getUserName());
					pal.setUpdateUser(user.getUserName());
					levelDao.save(pal);
				}
			}
			
		}
		//保存彩种玩法对应的奖金组设置
		for(LotteryPlayBonusVO pbVo : playBonusListVo.getPlayBonusList()){
			LotteryPlayBonus pb = new LotteryPlayBonus();
			pb.setBonusGroupId(pbVo.getBonusGroupId());
			pb.setLotteryCode(lotteryVo.getLotteryCode());
			pb.setMargin(pbVo.getMargin());
			pb.setWinningRate(pbVo.getWinningRate());
			pb.setPayoutRatio(pbVo.getPayoutRatio());
			pb.setModelCode(pbVo.getModelCode());
			pb.setRebates(pbVo.getRebates());
			pb.setBonusAmount(pbVo.getBonusAmount());
			pb.setStatus(DataDictionaryUtil.COMMON_FLAG_1);
			pb.setUpdateTime(DateUtil.getNowDate());
			pb.setCreateTime(DateUtil.getNowDate());
			pb.setCreateUser(user.getUserName());
			pb.setUpdateUser(user.getUserName());
			playBonusDao.save(pb);
		}
		
	}

	/**
	 * 查询所有的彩种信息
	 */
	@Override
	public Map<String, ?> queryLotteryAllInfo(Map<String, Object> param)
			throws Exception {
		LotteryTypeVO lotteryVo = (LotteryTypeVO) param.get("lotteryKey");
		Map infoMap = new HashMap();
		
		List<LotteryType> list = lotteryDao.queryLotteryList(param);
		List<LotterySource> links = sourceService.queryLinksByLotteryCode(param);
		LotteryPlayModelVO lpmVo = new LotteryPlayModelVO();
		lpmVo.setLotteryCode(lotteryVo.getLotteryCode());
		param.put("lpmKey", lpmVo);
		List<LotteryPlayModel> lps = playModelDao.queryPlayModelByLotteryCode(param);
		Map<String,PlayModel> pmMap = new HashMap<String,PlayModel>();
		for(LotteryPlayModel lp : lps){
			PlayModel pm = modelDao.queryPlayModelByCode(lp.getModelCode());
			pm.setLimitBets(list.get(0).getTotalLimitBets()/pm.getTotalBets());
			pm.setLimitAmount(lp.getLimitAmount().multiply(new BigDecimal(pm.getTotalBets())));
			lp.setPlayModel(pm);
			pmMap.put(pm.getModelCode(), pm);
		}
		
		
		
		List<BonusGroup> bonusGroupList = bonusDao.findBonusGroupAll(param);
		for(BonusGroup bg : bonusGroupList){
			LotteryPlayBonusVO pbVo = new LotteryPlayBonusVO();
			pbVo.setLotteryCode(lotteryVo.getLotteryCode());
			pbVo.setBonusGroupId(bg.getId());
			pbVo.setStatus(DataDictionaryUtil.COMMON_FLAG_1);
			List<LotteryPlayBonus> pbList = playBonusDao.queryPlayBonusBylotteryAndPlay(pbVo);
			bg.setBonusList(pbList);
			for(LotteryPlayBonus pb : pbList){
				pb.setModel(pmMap.get(pb.getModelCode()));
			}
		}
		infoMap.put("lotteryList", list);
		infoMap.put("linkList", links);
		infoMap.put("linkSize", links.size());
		infoMap.put("lpmList", lps);
		infoMap.put("lpmSize", lps.size());
		infoMap.put("bonusGroupList", bonusGroupList);
		
		return infoMap;
	}

	@Override
	public LotteryType saveLotteryInfo(Map<String, Object> param)
			throws Exception {
		LotteryType orignalLottery = lotteryDao.updateLottery(param);
		return orignalLottery;
	}

	/**
	 * 保存奖期修改
	 * 保存旧的奖期添加新的奖期
	 */
	@Override
	public List<LotteryType> savePeriod(Map<String, Object> param) throws Exception {
		List<LotteryType> returnList = new ArrayList<LotteryType>();
		LotteryJsonVO[] listArr = (LotteryJsonVO[]) param.get("periodsKey");
		LotteryJsonVO[] newListArr = (LotteryJsonVO[]) param.get("newPeriodsKey");
		String taskCronExpression = null;
		for(int i=0;i<listArr.length;i++){
			LotteryJsonVO vo = listArr[i];
			long id = vo.getId();
			LotteryType lottery = lotteryDao.queryById(id);
			lottery.setBeforeLotTime(vo.getBeforeLotTime());
			lottery.setCatchTimes(vo.getCatchTimes());
			lottery.setEndTime(vo.getEndTime());
			lottery.setSpanTime(vo.getSpanTime());
			lottery.setStartTime(vo.getStartTime());
			lottery.setTotalTimes(vo.getTotalTimes());
			lottery.setLotteryStatus(vo.getLotteryStatus());
			lottery.setAfterLotTime(vo.getAfterLotTime());
			lottery.setSeriesRule(vo.getSeriesRule());
			
			String newExpression = lottery.getTaskCornExpression();
			newExpression = newExpression.substring(0, newExpression.lastIndexOf(" ")+1);
			newExpression += vo.getSeriesExpression();
			lottery.setTaskCornExpression(newExpression);
			
			taskCronExpression = newExpression;
			lotteryDao.update(lottery);
			returnList.add(lottery);
		}
		//保存新添加的奖期
		int startLevel = listArr.length;
		if(null!=newListArr){
			for(int i=0;i<newListArr.length;i++){
				LotteryJsonVO vo = newListArr[i];
				LotteryType lottery = new LotteryType();
				
				lottery.setLotteryCode(vo.getLotteryCode());
				lottery.setLotteryGroup(vo.getLotteryGroup());
				lottery.setLotteryName(vo.getLotteryName());
				lottery.setBeforeLotTime(vo.getBeforeLotTime());
				lottery.setCatchTimes(vo.getCatchTimes());
				lottery.setEndTime(vo.getEndTime());
				lottery.setSpanTime(vo.getSpanTime());
				lottery.setStartTime(vo.getStartTime());
				lottery.setTotalTimes(vo.getTotalTimes());
				lottery.setLotteryStatus(DataDictionaryUtil.STATUS_OPEN);
				lottery.setLotteryLevel(++startLevel);
				lottery.setTotalLimitBets(vo.getTotalLimitBets());
				lottery.setTotalLimitAmount(vo.getTotalLimitAmount());
				lottery.setAfterLotTime(vo.getAfterLotTime());
				lottery.setSeriesRule(vo.getSeriesRule());
				
				if(null==taskCronExpression){
					String newExpression = "0 0 4 ? * ";
					newExpression += vo.getSeriesExpression();
					taskCronExpression = newExpression;
				}
				lottery.setTaskCornExpression(taskCronExpression);
				
				lottery.setCreateTime(DateUtil.getNowDate());
				lottery.setUpdateTime(DateUtil.getNowDate());
				
				lotteryDao.insert(lottery);
				returnList.add(lottery);
			}
			
		}
		
		return returnList;
	}

	/**
	 * 更新所有彩种的玩法相关的配置
	 */
	@Override
	public void savePlayRelated(Map<String, Object> param) throws Exception {
		AdminUser user = (AdminUser) param.get(CommonUtil.USERKEY);
		LotteryTypeVO lotteryVo = (LotteryTypeVO) param.get("lotteryKey");
		LotteryPlayBonusListVO playBonusListVo = (LotteryPlayBonusListVO) param.get("playBonusListKey");
		List<PlayModel> playModelList = (List<PlayModel>) param.get("playListKey");
		LotteryPlayModelListVO lpmListVo = (LotteryPlayModelListVO) param.get("lpmListKey");
		
		//所有玩法相关的旧设置都标识为删除状态
		LotteryPlayModelVO lpmVo = new LotteryPlayModelVO();
		lpmVo.setLotteryCode(lotteryVo.getLotteryCode());
		param.put("lpmKey", lpmVo);
		List<LotteryPlayModel> oldLpmList = playModelDao.queryPlayModelByLotteryCode(param);
		for(LotteryPlayModel lpm : oldLpmList){
			//设置为2状态即删除标识。
			lpm.setStatus(DataDictionaryUtil.COMMON_FLAG_2);
			lpm.setUpdateTime(DateUtil.getNowDate());
			lpm.setUpdateUser(user.getUserName());
			playModelDao.update(lpm);
			//旧奖级设置也设置为2即标识删除
			for(PlayAwardLevel level : lpm.getLevelList()){
				level.setUpdateTime(DateUtil.getNowDate());
				level.setUpdateUser(user.getUserName());
				level.setStatus(DataDictionaryUtil.COMMON_FLAG_2);
				levelDao.update(level);
			}
		}
		
		LotteryPlayBonusVO lpbVo  = new LotteryPlayBonusVO();
		lpbVo.setLotteryCode(lotteryVo.getLotteryCode());
		List<LotteryPlayBonus> lpbs = playBonusDao.queryPlayBonusBylotteryAndPlay(lpbVo);
		for(LotteryPlayBonus lpb : lpbs){
			//设置为2状态即删除标识。
			lpb.setStatus(DataDictionaryUtil.COMMON_FLAG_2);
			lpb.setUpdateTime(DateUtil.getNowDate());
			lpb.setUpdateUser(user.getUserName());
			playBonusDao.update(lpb);
		}
		
		//设置更改后的总限额配置
		List<LotteryType> lotteryList = lotteryDao.queryLotteryList(param);
		for(LotteryType lot : lotteryList){
			lot.setTotalLimitBets(lpmListVo.getTotalLimitBets());
			lot.setTotalLimitAmount(lpmListVo.getTotalLimitAmount());
			lot.setUpdateTime(DateUtil.getNowDate());
			lot.setUpdateUser(user.getUserName());
			lotteryDao.update(lot);
		}
		
		//保存所有新的变更设置......................
		//循环设置的限注金额，put进map中。
		Map<String,BigDecimal> limitAmountMap = new HashMap<String,BigDecimal>();
		for(LotteryPlayModelVO lpmVo1 : lpmListVo.getLotteryPlayModels()){
			limitAmountMap.put(lpmVo1.getModelCode(), lpmVo1.getLimitAmount());
		}
		//保存彩种配置的玩法
		for(PlayModel pm : playModelList){
			LotteryPlayModel lpm = new LotteryPlayModel();
			lpm.setLotteryCode(lotteryVo.getLotteryCode());
			lpm.setLotteryGroup(lotteryVo.getLotteryGroup());
			lpm.setModelCode(pm.getModelCode());
			lpm.setStatus(DataDictionaryUtil.COMMON_FLAG_1);
			//保存彩种的限额配置
			lpm.setLimitAmount(limitAmountMap.get(pm.getModelCode()));
			lpm.setCreateTime(DateUtil.getNowDate());
			lpm.setUpdateTime(DateUtil.getNowDate());
			lpm.setCreateUser(user.getUserName());
			lpm.setUpdateUser(user.getUserName());
			playModelDao.save(lpm);
			
			//保存玩法对应的奖级
			if(null!=pm.getLevelList()){
				for(PlayAwardLevelVO palvo : pm.getLevelList()){
					PlayAwardLevel pal = new PlayAwardLevel(); 
					pal.setAwardLevel(palvo.getAwardLevel());
					pal.setLevelName(palvo.getLevelName());
					pal.setWinAmount(palvo.getWinAmount());
					pal.setWiningRate(palvo.getWiningRate());
					pal.setPlayCode(pm.getModelCode());
					pal.setLotteryCode(lotteryVo.getLotteryCode());
					pal.setStatus(DataDictionaryUtil.COMMON_FLAG_1);
					pal.setCreateTime(DateUtil.getNowDate());
					pal.setUpdateTime(DateUtil.getNowDate());
					pal.setCreateUser(user.getUserName());
					pal.setUpdateUser(user.getUserName());
					levelDao.save(pal);
				}
			}
			
		}
		//保存彩种玩法对应的奖金组设置
		for(LotteryPlayBonusVO pbVo : playBonusListVo.getPlayBonusList()){
			LotteryPlayBonus pb = new LotteryPlayBonus();
			pb.setBonusGroupId(pbVo.getBonusGroupId());
			pb.setLotteryCode(lotteryVo.getLotteryCode());
			pb.setMargin(pbVo.getMargin());
			pb.setWinningRate(pbVo.getWinningRate());
			pb.setPayoutRatio(pbVo.getPayoutRatio());
			pb.setModelCode(pbVo.getModelCode());
			pb.setRebates(pbVo.getRebates());
			pb.setBonusAmount(pbVo.getBonusAmount());
			pb.setStatus(DataDictionaryUtil.COMMON_FLAG_1);
			pb.setUpdateTime(DateUtil.getNowDate());
			pb.setCreateTime(DateUtil.getNowDate());
			pb.setCreateUser(user.getUserName());
			pb.setUpdateUser(user.getUserName());
			playBonusDao.save(pb);
		}
		
	}

	@Override
	public List<LotteryType> queryLotteryTypeByGroupCode(Map<String, ?> param)
			throws Exception {
		// TODO Auto-generated method stub
		return typeDao.queryLotteryTypeByGroupCode(param);
	}

	@Override
	public List<LotteryType> queryLotteryTypeAll(Map<String, ?> param)
			throws Exception {
		// TODO Auto-generated method stub
		return typeDao.queryLotteryTypeAll(param);
	}
	
	@Override
	public void updateJobCron(Map<String, Object> param) throws Exception {
		LotteryListVO listVo = (LotteryListVO) param.get("listVoKey");
		for(LotteryTypeVO vo : listVo.getLotterys()){
			LotteryType lot = typeDao.queryById(vo.getId());
			lot.setTaskCornExpression(vo.getTaskCornExpression());
			lot.setUpdateTime(DateUtil.getNowDate());
			typeDao.update(lot);
		}
	}
	

	@Override
	public String queryLotteryTypeNameByCode(String code) throws Exception {
		// TODO Auto-generated method stub
		return typeDao.queryLotteryTypeNameByCode(code);
	}

	@Override
	public List<LotteryType> getAllType() throws Exception {
		// TODO Auto-generated method stub
		List<LotteryType> types = lotteryDao.queryAll();
		return types;
	}

	@Override
	public List<LotteryListVOWebApp> getLotteryGroups(Map<String, Object> param) throws Exception {
		List<LotteryType> lotteryList = this.queryLotteryList(param);
		// 存放彩种组对应的彩种集合。
		Map<String, List<LotteryTypeForm>> groupMap = new HashMap<String, List<LotteryTypeForm>>();
		for (LotteryType lottery : lotteryList) {
			String group = lottery.getLotteryGroup();
			if (null == groupMap.get(group)) {
				List<LotteryTypeForm> list = new ArrayList<LotteryTypeForm>();
				LotteryTypeForm ltf = new LotteryTypeForm();
				DozermapperUtil.getInstance().map(lottery, ltf);
				list.add(ltf);
				groupMap.put(group, list);
			} else {
				LotteryTypeForm ltf = new LotteryTypeForm();
				DozermapperUtil.getInstance().map(lottery, ltf);
				groupMap.get(group).add(ltf);
			}
		}

		List<LotteryListVOWebApp> voList = new ArrayList<LotteryListVOWebApp>();
		for (String groupKey : groupMap.keySet()) {
			LotteryListVOWebApp listVO = new LotteryListVOWebApp();
			listVO.setLotteryGroup(groupKey);
			listVO.setLotteryGroupName(CommonUtil.lotteryGroupMap.get(groupKey));
			listVO.setLotteryList(groupMap.get(groupKey));
			listVO.setRsvst1(groupMap.get(groupKey).get(0).getRsvst1());
			voList.add(listVO);
		}
		
		//用彩种的rsvst1字段作为排序依据
		Compare2 cp2 = new Compare2();
		Collections.sort(voList, cp2);
		
		return voList;
	}
	
	/**
	 * 彩种排序根据备用字段排序
	 * @author CW-HP9
	 *
	 */
	class Compare2 implements Comparator<LotteryListVOWebApp>{
			@Override
			public int compare(LotteryListVOWebApp o1, LotteryListVOWebApp o2) {
				if(StringUtils.isEmpty(o1.getRsvst1())){
					o1.setRsvst1("9999");
				}
				if(StringUtils.isEmpty(o2.getRsvst1())){
					o2.setRsvst1("9999");
				}
				int code1 = Integer.parseInt(o1.getRsvst1());
				int code2 = Integer.parseInt(o2.getRsvst1());
				if(code1>code2){
					return 1;
				}else if(code1<code2){
					return -1;
				}
				return 0;
			}
	}
}
