package com.lottery.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.lottery.bean.entity.BonusGroup;
import com.lottery.bean.entity.LotterySource;
import com.lottery.bean.entity.SourceLink;
import com.lottery.bean.entity.vo.LotterySourceVO;
import com.lottery.bean.entity.vo.LotteryTypeVO;
import com.lottery.dao.IBonusGroupDao;
import com.lottery.dao.ILotterySourceDao;
import com.lottery.dao.ISourceLinkDao;
import com.lottery.service.IBonusGroupService;
import com.lottery.service.ILotteryPlayBonusService;
import com.lottery.service.ILotterySourceService;
import com.lottery.service.IPlayModelService;
import com.xl.lottery.util.DataDictionaryUtil;
import com.xl.lottery.util.DateUtil;

@Service
public class LotterySourceServiceImpl implements ILotterySourceService{
	@Autowired
	private ILotterySourceDao sourceDao;
	
	@Autowired
	private ISourceLinkDao sourceLinkDao;

	@Override
	public List<LotterySource> queryLinksByLotteryCode(Map<String, Object> param)
			throws Exception {
		LotteryTypeVO lotteryVo = (LotteryTypeVO) param.get("lotteryKey");
		List<LotterySource> sources = sourceDao.querySourceByLotteryCode(lotteryVo);
		for(LotterySource source : sources){
			SourceLink link = sourceLinkDao.queryById(source.getSourceLinkId());
			source.setLink(link);
		}
		return sources;
	}

	@Override
	public List<LotterySource> saveSource(Map<String, Object> param)
			throws Exception {
		List<LotterySource> rerurnList =new ArrayList<LotterySource>();
		
		LotterySourceVO lsVo = (LotterySourceVO) param.get("lotterySourceKey");
		String[] idArr = lsVo.getIdListStr().split(",");
		String[] statusArr = lsVo.getStatusListStr().split(",");
		String[] newNameArr = null;
		String[] newLinkArr = null;
		String[] newLevelArr = null;
		if(!StringUtils.isEmpty(lsVo.getNewSourceNames())){
			newNameArr = lsVo.getNewSourceNames().split(",");
			newLinkArr = lsVo.getNewSourceLinks().split(",");
			newLevelArr = lsVo.getSourceLevelStrs().split(",");
		}
	
		//保存已有的号源的设置
		for(int i=0;i<idArr.length;i++){
			long id = Long.parseLong(idArr[i]);
			int status = Integer.parseInt(statusArr[i]);
			LotterySource s = sourceDao.queryById(id);
			s.setStatus(status);
			sourceDao.update(s);
			rerurnList.add(s);
		}
		//保存新增的号源的设置
		if(null!=newNameArr){
			for(int i=0;i<newNameArr.length;i++){
				String name = String.valueOf(newNameArr[i]);
				String link = String.valueOf(newLinkArr[i]);
				Integer level = Integer.parseInt(newLevelArr[i]);
				SourceLink sl = new SourceLink();
				sl.setSourceName(name);
				sl.setSourceLink(link);
				sl.setSourceLevel(level);
				sl.setCreateTime(DateUtil.getNowDate());
				sl.setUpdateTime(DateUtil.getNowDate());
				sl.setStatus(DataDictionaryUtil.COMMON_FLAG_1);
				sourceLinkDao.save(sl);
				
				LotterySource s = new LotterySource();
				s.setLotteryCode(lsVo.getLotteryCode());
				s.setLotteryGroup(lsVo.getLotteryGroup());
				s.setSourceLinkId(sl.getId());
				s.setStatus(DataDictionaryUtil.COMMON_FLAG_1);
				s.setCreateTime(DateUtil.getNowDate());
				s.setUpdateTime(DateUtil.getNowDate());
				sourceDao.save(s);
				
				rerurnList.add(s);
			}
		}
		return rerurnList;
	}

}
