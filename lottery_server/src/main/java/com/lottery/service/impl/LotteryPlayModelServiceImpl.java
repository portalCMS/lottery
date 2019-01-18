package com.lottery.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.LotteryPlayBonus;
import com.lottery.bean.entity.LotteryPlayModel;
import com.lottery.bean.entity.LotteryPlaySelect;
import com.lottery.bean.entity.LotteryType;
import com.lottery.bean.entity.PlayModel;
import com.lottery.bean.entity.vo.LotteryPlayBonusVO;
import com.lottery.bean.entity.vo.LotteryPlayModelVO;
import com.lottery.bean.entity.vo.LotteryPlaySelectVO;
import com.lottery.bean.entity.vo.LotteryTypeVO;
import com.lottery.dao.ILotteryPlayBonusDao;
import com.lottery.dao.ILotteryPlayModelDao;
import com.lottery.dao.ILotteryPlaySelectDao;
import com.lottery.dao.ILotteryTypeDao;
import com.lottery.dao.IPlayModelDao;
import com.lottery.service.ILotteryPlayModelService;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;

@Service
public class LotteryPlayModelServiceImpl implements ILotteryPlayModelService{

	@Autowired
	private ILotteryPlayModelDao lpmDao;
	
	@Autowired
	private IPlayModelDao modelDao;
	
	@Autowired
	private ILotteryTypeDao lotteryDao;
	
	@Autowired
	private ILotteryPlaySelectDao selectDao;
	
	@Autowired
	private ILotteryPlayBonusDao bonusDao;
	
	@Override
	public List<LotteryPlayModel> queryPlayModelByLotteryCode(
			Map<String, Object> param) throws Exception {
		return lpmDao.queryPlayModelByLotteryCode(param);
	}
	/**
	 * 查询彩种下的所有玩法信息
	 */
	@Override
	public List<LotteryPlayModel> queryModelByLottery(Map<String, Object> param)
			throws Exception {
		LotteryTypeVO lotteryVo = (LotteryTypeVO) param.get("lotteryKey");
		List<LotteryType> list = lotteryDao.queryLotteryList(param);
		
		CustomerUser user = (CustomerUser) param.get(CommonUtil.CUSTOMERUSERKEY);
		
		LotteryPlayModelVO lpmVo = new LotteryPlayModelVO();
		lpmVo.setLotteryCode(lotteryVo.getLotteryCode());
		lpmVo.setStatus(DataDictionaryUtil.COMMON_FLAG_1);
		param.put("lpmKey", lpmVo);
		Boolean isMobile = (Boolean) param.get("isMobile");
		List<LotteryPlayModel> lps = lpmDao.queryPlayModelByLotteryCode(param);
		List<LotteryPlayModel> rmLps = new ArrayList<LotteryPlayModel>();
		for(LotteryPlayModel lp : lps){
			PlayModel pm = modelDao.queryPlayModelByCode(lp.getModelCode());
			if(isMobile!=null&&isMobile&&(pm.getMobileFlag()==null||pm.getMobileFlag()!=DataDictionaryUtil.COMMON_FLAG_1)){
				rmLps.add(lp);
				continue;
			}
			//查询玩法对应的配置的选号方式
			LotteryPlaySelectVO selectVo = new LotteryPlaySelectVO();
			selectVo.setModelCode(pm.getModelCode());
			List<LotteryPlaySelect> selectList = selectDao.querySelectByModel(selectVo);
			
			pm.setLimitBets(list.get(0).getTotalLimitBets()/pm.getTotalBets());
			pm.setLimitAmount(lp.getLimitAmount().multiply(new BigDecimal(pm.getTotalBets())));
			lp.setLottery(list.get(0));
			lp.setPlayModel(pm);
			lp.setSelectList(selectList);
			
			//查询玩法对应的奖金组，需要用到返奖率计算中奖金额。
			LotteryPlayBonusVO lpbVo = new LotteryPlayBonusVO();
			lpbVo.setLotteryCode(lotteryVo.getLotteryCode());
			lpbVo.setModelCode(lp.getModelCode());
			lpbVo.setModelName(pm.getModelName());
			lpbVo.setUserId(user.getId());
			LotteryPlayBonus lpb = bonusDao.queryPlayBonusBylpm(lpbVo);
			lp.setBonus(lpb);
		}
		
		lps.removeAll(rmLps);
		
		return lps;
	}
}
