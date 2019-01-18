package com.lottery.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.bean.entity.LotterySource;
import com.lottery.bean.entity.SourceLink;
import com.lottery.bean.entity.vo.LotteryListVO;
import com.lottery.bean.entity.vo.LotteryTypeVO;
import com.lottery.dao.ILotterySourceDao;
import com.lottery.dao.ISourceLinkDao;
import com.lottery.service.ISourceLinkService;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;

@Service
public class SourceLinkServiceImpl implements ISourceLinkService{
	
	@Autowired
	private ILotterySourceDao lotterySourceDao;
	
	@Autowired
	private ISourceLinkDao sourceLinkDao;

	@Override
	public List<SourceLink> queryServiceByLotteryCode(Map<String, Object> param)
			throws Exception {
		LotteryTypeVO lotteryVo = (LotteryTypeVO) param.get("lotteryKey");
		List<LotterySource> sources = lotterySourceDao.querySourceByLotteryCode(lotteryVo);
		List<SourceLink> links = new ArrayList<SourceLink>(0);
		for(LotterySource source : sources){
			if(source.getStatus()==DataDictionaryUtil.COMMON_FLAG_1){
				SourceLink link = sourceLinkDao.queryById(source.getSourceLinkId());
				if(link.getStatus()==DataDictionaryUtil.COMMON_FLAG_1){
					links.add(link);
				}
			}
			
		}
		return links;
	}

}
