package com.lottery.service.impl;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.BetRecord;
import com.lottery.bean.entity.CustomerCash;
import com.lottery.bean.entity.CustomerOrder;
import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.LotteryPlayBonus;
import com.lottery.bean.entity.LotteryPlaySelect;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.PlayAwardLevel;
import com.lottery.bean.entity.PlayModel;
import com.lottery.bean.entity.TaskConfig;
import com.lottery.bean.entity.vo.BetRecordListVO;
import com.lottery.bean.entity.vo.BetRecordVO;
import com.lottery.bean.entity.vo.CustomerOrderStaVo;
import com.lottery.bean.entity.vo.LotteryPlayBonusVO;
import com.lottery.bean.entity.vo.LotteryPlayModelVO;
import com.lottery.bean.entity.vo.LotteryTypeVO;
import com.lottery.dao.IBetRecordDao;
import com.lottery.dao.ICustomerCashDao;
import com.lottery.dao.ICustomerOrderDao;
import com.lottery.dao.ICustomerUserDao;
import com.lottery.dao.ILotteryPlayBonusDao;
import com.lottery.dao.ILotteryPlaySelectDao;
import com.lottery.dao.ILotteryTypeDao;
import com.lottery.dao.IOrderSequenceDao;
import com.lottery.dao.IPlayAwardLevelDao;
import com.lottery.dao.IPlayModelDao;
import com.lottery.dao.impl.AdminWriteLog;
import com.lottery.dao.impl.CustomerUserWriteLog;
import com.lottery.service.IBetRecordService;
import com.lottery.service.ILotteryAwardRecordService;
import com.lottery.service.ILotteryPlayBonusService;
import com.lottery.service.ITaskConfigService;
import com.lottery.staticvalue.CommonStatic;
import com.xl.lottery.desutil.AesUtil;
import com.xl.lottery.desutil.Md5Manage;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.exception.LotteryExceptionLog;
import com.xl.lottery.util.BeanPropertiesCopy;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;
import com.xl.lottery.util.DateUtil;
import com.xl.lottery.util.DozermapperUtil;
import com.xl.lottery.util.Util;

@Service
public class BetRecordServiceImpl implements IBetRecordService {

	@Autowired
	private IBetRecordDao betRecordDao;

	@Autowired
	private ICustomerOrderDao orderDao;

	@Autowired
	private ICustomerCashDao cashDao;

	@Autowired
	private IOrderSequenceDao orderSequenceDao;

	@Autowired
	private CustomerUserWriteLog customerUserWriteLog;

	@Autowired
	private AdminWriteLog adminUserWriteLog;

	@Autowired
	private IPlayModelDao playModelDao;

	@Autowired
	private ILotteryPlayBonusDao lotteryPlayBonusDao;

	@Autowired
	private ILotteryPlaySelectDao lotteryPlaySelectDao;

	@Autowired
	private IPlayModelDao modelDao;

	@Autowired
	private ICustomerUserDao userDao;

	@Autowired
	private ILotteryTypeDao lotteryTypeDao;

	@Autowired
	private ITaskConfigService taskConfigService;

	@Autowired
	private IPlayAwardLevelDao playAwardLevelDao;

	@Autowired
	private ILotteryAwardRecordService awardRecodeService;

	@Autowired
	private ILotteryPlayBonusService lpbService;

	@SuppressWarnings({ "rawtypes", "unused" })
	private Object invoke(String className, String methodName, Object[] args) throws Exception {

		Class clz = Class.forName(className);
		Method[] methods = clz.getMethods();
		Object obj = null;
		try {
			for (Method method : methods) {
				if (method.getName().equals(methodName)) {
					obj = method.invoke(clz.newInstance(), args);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return obj;
	}

	@Override
	public List<BetRecord> openLotteryResult(Map<String, Object> param) throws Exception {
		// 查询当前的投注记录并检验是否已经中奖。
		String lotteryCode = (String) param.get("lotteryCode");
		String issueNo = (String) param.get("issueNo");
		String lotteryNum = (String) param.get("lotteryNum");
		List<BetRecord> bets = getBetRecordlist(lotteryCode, issueNo);
		List<BetRecord> newBets = new ArrayList<BetRecord>();
		DozermapperUtil.getInstance().map(bets, newBets);
		List<BetRecord> betWinnings = new ArrayList<BetRecord>();
		List<BetRecord> unbetWinnings = new ArrayList<BetRecord>();
		Map<String, LotteryPlaySelect> selectCodeMap = new HashMap<String, LotteryPlaySelect>();
		List<LotteryPlaySelect> selectCodeList = lotteryPlaySelectDao.queryAll();
		for (LotteryPlaySelect sc : selectCodeList) {
			selectCodeMap.put(sc.getSelectCode(), sc);
		}
		for (BetRecord br : newBets) {
			String betInfoKey = br.getTempString();
			String bile = "";
			String betNum = "";
			// 无法解密的中奖投注号码记录，继续循环其它的中奖记录，日志中记录一下是哪个投注记录即可。
			try {
				if (!StringUtils.isEmpty(br.getBileNum())) {
					bile = AesUtil.decrypt(br.getBileNum(), Md5Manage.getInstance().getMd5() + betInfoKey);
				}
				betNum = AesUtil.decrypt(br.getBetNum(), Md5Manage.getInstance().getMd5() + betInfoKey);
			} catch (LotteryException e) {
				LotteryExceptionLog.wirteLog(e, "投注记录订单id：" + br.getId() + ",投注记录订单编号：" + br.getOrderNo());
				continue;
			}
			LotteryPlaySelect playSelect = selectCodeMap.get(br.getSelectCode());
			String awardInfos = "";
			if (playSelect.getMethodArgs() == 3) {
				Object[] args = null;
				if (bile != null && !bile.equals("")) {
					args = new Object[] { bile, betNum, lotteryNum };
				} else {
					args = new Object[] { playSelect.getFew(), betNum, lotteryNum };
				}
				awardInfos = (String) this.invoke(playSelect.getClassName(), playSelect.getMethodName(), args);
			} else if (playSelect.getMethodArgs() == 4) {
				Object[] args = { playSelect.getFew(), bile, betNum, lotteryNum };
				awardInfos = (String) this.invoke(playSelect.getClassName(), playSelect.getMethodName(), args);
			} else {
				Object[] args = { betNum, lotteryNum };
				awardInfos = (String) this.invoke(playSelect.getClassName(), playSelect.getMethodName(), args);
			}

			// 拆分字符串，获取中奖等级和中奖注数。
			String[] awards = awardInfos.split(";");
			boolean isWin = false;
			StringBuffer counts = new StringBuffer();
			StringBuffer levels = new StringBuffer();
			for (String awardInfo : awards) {
				String awardLevel = awardInfo.split(",")[0];
				String count = awardInfo.split(",")[1];
				if (Integer.parseInt(count) > 0) {
					isWin = true;
					if (counts.length() == 0) {
						counts.append(count);
						levels.append(awardLevel);
					} else {
						counts.append(",").append(count);
						levels.append(",").append(awardLevel);
					}
				}

			}

			if (isWin) {
				br.setAwardCount(counts.toString());
				br.setAwardLevel(levels.toString());
				betWinnings.add(br);
			} else {
				unbetWinnings.add(br);
			}

		}
		List<String> strTemp = new ArrayList<String>();
		String wingRecordIds = "";
		// String wingOrderNumbers="";
		String awardCounts = "";
		String awardLevels = "";
		for (BetRecord wingRecord : betWinnings) {
			// 标记中奖的投注记录
			strTemp.add(String.valueOf(wingRecord.getId()));
			if (wingRecordIds == "") {
				wingRecordIds = String.valueOf(wingRecord.getId());
				// wingOrderNumbers = wingRecord.getOrderNo();
				awardCounts = wingRecord.getAwardCount() + "";
				awardLevels = wingRecord.getAwardLevel() + "";
			} else {
				wingRecordIds = wingRecordIds + ";" + wingRecord.getId();
				// wingOrderNumbers = wingOrderNumbers+";"+wingRecord.getOrderNo();
				awardCounts = awardCounts + ";" + wingRecord.getAwardCount();
				awardLevels = awardLevels + ";" + wingRecord.getAwardLevel();
			}
		}

		// 更新中奖记录的投注状态为已中奖，未中奖记录的投注状态为未中奖，并给中奖用户派奖，生成中奖记录订单。
		if (wingRecordIds.length() > 0) {
			param.put("betsKey", wingRecordIds);
			// param.put("betOrdNosKey", wingOrderNumbers);
			param.put("awardCountsKey", awardCounts);
			param.put("awardLevelsKey", awardLevels);
			betRecordDao.updateAwardUser(param);
		} else {
			// 如果没有任何投注中奖，则更新当前的所有投注记录的betStatus为未中奖.
			betRecordDao.updateBetNoWinner(param);
		}
		return betWinnings;
	}

	private List<BetRecord> getBetRecordlist(String lotteryCode, String issueNo) {
		StringBuffer sqlQuery = new StringBuffer(
				" from BetRecord t where t.lotteryCode = ? and t.issueNo = ? and t.betStatus = ? ");
		Query query = betRecordDao.getSession().createQuery(sqlQuery.toString());
		query.setParameter(0, lotteryCode);
		query.setParameter(1, issueNo);
		query.setParameter(2, DataDictionaryUtil.BET_ORDER_TYPE_SUCCESS);
		return query.list();
	}

	@Override
	public String saveBetRecordOrder(Map<String, ?> param) throws Exception {
		// TODO Auto-generated method stub
		BetRecordListVO volist = (BetRecordListVO) param.get("volistkey");
		CustomerUser user = (CustomerUser) param.get(CommonUtil.CUSTOMERUSERKEY);
		if (volist.getOrderAmount().compareTo(BigDecimal.ZERO) != 1) {
			throw new LotteryException("注单异常");
		}
		// 检查扣款
		CustomerCash cash = cashDao.queryUserCashByCustomerId(user.getId());
		if (cash.getCash().doubleValue() - volist.getOrderAmount().doubleValue() < 0)
			throw new LotteryException("余额不足");
		// 扣款
		cash.setCash(cash.getCash().subtract(volist.getOrderAmount()));
		cash.updateInit(user.getCustomerName());
		cashDao.update(cash);
		customerUserWriteLog.saveWriteLog(user, CommonUtil.SAVE, "CustomerOrder", cashDao.toString()); // 生成扣款日志
		// 生成订单
		CustomerOrder order = this.createBetOrder(volist, user, cash.getCash());
		if (volist.getIsTrack().equals(CommonUtil.TRACK_Z) || volist.getIsTrack().equals(CommonUtil.TRACK_L)) {
			order.setRsvst5(volist.getIssueNos().substring(volist.getIssueNos().indexOf(",") + 1,
					volist.getIssueNos().length()));
		}
		orderDao.save(order);
		customerUserWriteLog.saveWriteLog(user, CommonUtil.SAVE, "CustomerOrder", order.toString()); // 生成订单日志
		List<BetRecordVO> vos = volist.getVolist();

		// 获取玩法code
		Map<String, PlayModel> pmMap = new HashMap<String, PlayModel>();
		List<PlayModel> pms = playModelDao.queryAll();
		for (PlayModel pm : pms) {
			pmMap.put(pm.getModelCode(), pm);
		}
		// 获取返点集合
		Map<String, LotteryPlayBonus> lbpMap = new HashMap<String, LotteryPlayBonus>();
		LotteryPlayBonusVO lpbVo = new LotteryPlayBonusVO();
		lpbVo.setUserId(user.getId());
		List<LotteryPlayBonus> lpbs = lotteryPlayBonusDao.queryPlayBonusByUserId(lpbVo);
		for (LotteryPlayBonus lpb : lpbs) {
			lbpMap.put(lpb.getModelCode() + ":" + lpb.getLotteryCode(), lpb);
		}
		Map<String, BigDecimal> awardLevels = new HashMap<String, BigDecimal>();
		List<PlayAwardLevel> pals = playAwardLevelDao.queryAll();
		for (PlayAwardLevel pal : pals) {
			if (pal.getStatus() != 1)
				continue;
			awardLevels.put(pal.getPlayCode() + ":" + pal.getLotteryCode(), pal.getWinAmount());
		}
		// 查询用户的总代返点值
		String allParents = user.getAllParentAccount();
		String rootProxyId = allParents.split(",")[0];
		if (rootProxyId.equals(""))
			rootProxyId = Long.toString(user.getId());
		// CustomerUser proxyUser = userDao.queryById(Long.parseLong(rootProxyId));
		// BigDecimal stepRb = proxyUser.getRebates().subtract(user.getRebates());

		// 查询用户返点值及奖金组名称
		Map<String, Object> param1 = new HashMap<String, Object>();
		LotteryPlayModelVO lpmVo = new LotteryPlayModelVO();
		lpmVo.setLotteryCode(volist.getVolist().get(0).getLotteryCode());
		lpmVo.setUserName(user.getCustomerName());
		param1.put("lpmKey", lpmVo);
		Map<String, Object> bonusMap = lpbService.queryUserModelNoBonus(param1);
		BigDecimal stepRb = (BigDecimal) bonusMap.get("stepRebates");

		for (BetRecordVO vo : vos) {
			BetRecord entity = this.createBetRecord(vo, user);
			// 查询玩法对应的奖金组，需要用到返奖率计算0返点的中奖金额。
			PlayModel play = pmMap.get(entity.getPlayCode());
			LotteryPlayBonus playBonu = lbpMap.get(entity.getPlayCode() + ":" + entity.getLotteryCode());
			// 返奖率等于玩家返点减去选择的返点值,加上用户对应的奖金组的返奖率。
			BigDecimal ratio = playBonu.getRebates().subtract(stepRb).subtract(entity.getRebates());
			if (ratio.compareTo(BigDecimal.ZERO) < 0 && entity.getRebates().compareTo(BigDecimal.ZERO) != 0) {
				throw new LotteryException("返点值异常，大于用户的最大返点值[" + playBonu.getRebates() + "]!");
			}

			entity.setPayoutRatio(playBonu.getPayoutRatio().add(ratio));

			BigDecimal baseMoney = awardLevels.get(entity.getPlayCode() + ":" + entity.getLotteryCode());
			if (baseMoney == null) {
				baseMoney = play.getWinAmount();
			}
			baseMoney = baseMoney.multiply(entity.getPayoutRatio()).multiply(entity.getBetModel());
			entity.setBaseMoney(baseMoney);
			entity.setOrderNo(order.getOrderNumber());
			if (volist.getIsTrack().equals(CommonUtil.TRACK_P)) {
				entity.setBetType(DataDictionaryUtil.BET_TYPE);
			} else {
				String issues = volist.getIssueNos().split(",")[0];
				entity.setIssueNo(issues.split(":")[0]);
				entity.setMultiple(Integer.parseInt(issues.split(":")[1]));
				entity.setBetType(DataDictionaryUtil.BET_TYPE_TRACK);
			}
			entity.setBetStatus(DataDictionaryUtil.BET_ORDER_TYPE_SUCCESS);
			entity.addInit(user.getCustomerName());
			entity.setCustomerId(user.getId());

			// 加密前先要格式化
			DecimalFormat df1 = new DecimalFormat("#####0.0000");
			String betMoney = df1.format(entity.getBetMoney());
			String betModel = df1.format(entity.getBetModel());
			String baseMoneyDfStr = df1.format(entity.getBaseMoney());
			entity.setBetModel(new BigDecimal(betModel));
			entity.setBetMoney(new BigDecimal(betMoney));
			entity.setBaseMoney(new BigDecimal(baseMoneyDfStr));

			if (volist.getIsTrack().equals(CommonUtil.TRACK_Z) || volist.getIsTrack().equals(CommonUtil.TRACK_L)) {
				BigDecimal tempbetMoney = entity.getBetMoney();
				BigDecimal tempBaseMoney = entity.getBaseMoney();
				String tempbileNum = new String(entity.getBileNum());
				String tempbetNum = new String(entity.getBetNum());
				String bileNumMD5s = "";
				String betNumMD5s = "";
				String[] issues = volist.getIssueNos()
						.substring(volist.getIssueNos().indexOf(",") + 1, volist.getIssueNos().length()).split(",");
				for (String issue : issues) {
					String key = entity.getPlayCode() + entity.getSelectCode() + tempbetMoney + entity.getBetModel()
							+ issue.split(":")[1] + tempBaseMoney;
					if (bileNumMD5s.equals("")) {
						if (tempbileNum.trim().equals("")) {
							bileNumMD5s += " ";
						} else {
							bileNumMD5s += AesUtil.encrypt(tempbileNum.trim(), Md5Manage.getInstance().getMd5() + key);
						}
					} else {
						if (tempbileNum.trim().equals("")) {
							bileNumMD5s += "," + " ";
						} else {
							bileNumMD5s += ","
									+ AesUtil.encrypt(tempbileNum.trim(), Md5Manage.getInstance().getMd5() + key);
						}
					}
					if (betNumMD5s.equals("")) {
						betNumMD5s += AesUtil.encrypt(tempbetNum.trim(), Md5Manage.getInstance().getMd5() + key);
					} else {
						betNumMD5s += "," + AesUtil.encrypt(tempbetNum.trim(), Md5Manage.getInstance().getMd5() + key);
					}
				}
				entity.setTempBileNum(bileNumMD5s);
				entity.setTempBetNum(betNumMD5s);
			}

			String betInfoKey = entity.getTempString();

			if (!StringUtils.isEmpty(entity.getBileNum())) {
				entity.setBileNum(AesUtil.encrypt(entity.getBileNum().trim(), Md5Manage.getInstance().getMd5()
						+ betInfoKey));
			}
			entity.setBetNum(AesUtil.encrypt(entity.getBetNum().trim(), Md5Manage.getInstance().getMd5() + betInfoKey));

			betRecordDao.save(entity);
			// customerUserWriteLog.saveWriteLog(user, CommonUtil.SAVE, "BetRecord", entity.toString()); //生成投注日志
		}
		if (volist.getIsTrack().equals(CommonUtil.TRACK_Z) || volist.getIsTrack().equals(CommonUtil.TRACK_L)) {
			String issue = volist.getIssueNos().split(",")[0];
			betRecordDao.addBetRecordsByTrack(order.getId(), issue.split(":")[0]);
			// Session session = betRecordDao.getSession();
			// String[] issues =
			// volist.getIssueNos().substring(volist.getIssueNos().indexOf(",")+1,volist.getIssueNos().length()).split(",");
			// for(String str:issues){
			// String sql = "INSERT INTO t_bet_record(order_no,lottery_code,play_code,bet_model,bet_money,bet_type,"
			// +" bile_num,bet_num,customer_id,issue_no,bet_status,create_time,create_user,update_time,"
			// +" update_user, VERSION,Multiple,win_money,base_money,rebates,select_code,bet_count,award_count,"
			// +" award_level,rsvst1,payout_ratio)SELECT  b2.order_no,b2.lottery_code,b2.play_code, b2.bet_model,"
			// +" b2.bet_money/b2.Multiple*"+str.split(":")[1]+" AS bet_money,b2.bet_type,b2.bile_num,b2.bet_num,b2.customer_id,"
			// +" "+str.split(":")[0]+" AS issue_no,b2.bet_status,b2.create_time,b2.create_user, b2.update_time,b2.update_user,"
			// +" b2.version,"+str.split(":")[1]+" AS Multiple,b2.win_money,b2.base_money/b2.Multiple*"+str.split(":")[1]+" AS base_money,b2.rebates,"
			// +" b2.select_code,b2.bet_count,b2.award_count,b2.award_level,b2.rsvst1,b2.payout_ratio FROM t_bet_record  b2"
			// +" WHERE b2.id IN (SELECT b.id FROM t_bet_record b WHERE b.order_no = '"+order.getOrderNumber()+"' AND b.issue_no = "+issue.split(":")[0]
			// +" AND b.bet_status !=21004)";
			// Query query = session.createSQLQuery(sql);
			// query.executeUpdate();
			// }
		}
		return "success";
	}

	private CustomerOrder createBetOrder(BetRecordListVO volist, CustomerUser user, BigDecimal cashAmount)
			throws Exception {
		CustomerOrder order = new CustomerOrder();
		order.setCustomerId(user.getId());
		order.setOrderNumber(orderSequenceDao.getOrderSequence(CommonUtil.ORDER_DETAIL_BET_ORDER, 8));
		order.setOrderTime(DateUtil.getNowDate());
		order.setOrderAmount(volist.getOrderAmount());
		order.setOrderType(DataDictionaryUtil.ORDER_TYPE_OUT);
		// 设置账户余额字段
		order.setAccountBalance(cashAmount);
		if (volist.getIsTrack().equals(CommonUtil.TRACK_P)) {
			// 普通投注订单
			order.setOrderDetailType(DataDictionaryUtil.ORDER_DETAIL_ORDINARY_BETTING);
		} else {
			// 追号订单
			order.setOrderDetailType(DataDictionaryUtil.ORDER_DETAIL_TRACK);
		}
		order.setRsvdc1(Long.parseLong(Integer.toString(volist.getTrackNo())));
		// 设置一下追号订单的完成期数为0
		order.setRsvdc2(0l);
		order.setOrderStatus(DataDictionaryUtil.ORDER_STATUS_SUCCESS);
		order.addInit(user.getCustomerName());
		order.setCashAmount(volist.getOrderAmount());
		order.setReceiveAmount(volist.getOrderAmount());
		if (volist.getAwardStop() != null && !volist.getAwardStop().equals("")) {
			order.setAwardStop(volist.getAwardStop());
		} else {
			order.setAwardStop("0");
		}
		return order;
	}

	private BetRecord createBetRecord(BetRecordVO betRecordvo, CustomerUser user) throws Exception {
		BetRecord betRecord = new BetRecord();
		betRecord.addInit(user.getCustomerName());
		BeanPropertiesCopy.copyProperties(betRecordvo, betRecord);
		return betRecord;
	}

	/**
	 * 派发对应彩种奖期的返点金额，并且给追期订单的完成期数加1
	 */
	@Override
	public void updatePayBetRebates(Map<String, Object> param) throws Exception {

		betRecordDao.updatePayBetRebates(param);
	}

	@Override
	public Page<BetRecordVO, BetRecord> queryBetRecords(Map<String, ?> param) throws Exception {
		// TODO Auto-generated method stub
		Page<BetRecordVO, BetRecord> page = betRecordDao.queryBetRecords(param);
		List<BetRecord> entitys = page.getEntitylist();
		List<BetRecordVO> vos = new ArrayList<BetRecordVO>();
		Map<String, Object> codeMap = CommonStatic.getCodeMap();
		for (BetRecord entity : entitys) {
			BetRecordVO vo = new BetRecordVO();
			DozermapperUtil.getInstance().map(entity, vo);
			String typename = (String) codeMap.get(CommonStatic.LOTTERYTYPE_HEAD + vo.getLotteryCode());
			String playModelName = (String) codeMap.get(CommonStatic.PLAYMODEL_HEAD + vo.getPlayCode());
			CustomerUser user = new CustomerUser();
			DozermapperUtil.getInstance().map(userDao.queryById(vo.getCustomerId()), user);
			String userName = user.getCustomerName();
			vo.setLotteryCode(typename);
			vo.setPlayCode(playModelName);
			vo.setuName(userName);
			vos.add(vo);
		}
		page.setPagelist(vos);
		return page;
	}

	@Override
	public BetRecordVO getBetRecordInfoById(Map<String, ?> param) throws Exception {
		BetRecordVO betRecordVO = (BetRecordVO) param.get("betRecordkeyVo");
		StringBuffer queryString = new StringBuffer(" from BetRecord t where t.id = ? ");
		Query query = betRecordDao.getSession().createQuery(queryString.toString());
		query.setParameter(0, betRecordVO.getId());
		BetRecord entity = (BetRecord) query.list().get(0);
		if (entity.getBileNum() != null && !entity.getBileNum().equals("")) {
			entity.setBileNum(AesUtil.decrypt(entity.getBileNum(),
					Md5Manage.getInstance().getMd5() + entity.getTempString()));
		}
		entity.setBetNum(AesUtil.decrypt(entity.getBetNum(), Md5Manage.getInstance().getMd5() + entity.getTempString()));
		BetRecordVO vo = new BetRecordVO();
		DozermapperUtil.getInstance().map(entity, vo);
		vo.setLotteryCodeCopy(vo.getLotteryCode());
		vo.setPlayCodeCopy(vo.getPlayCode());
		Map<String, Object> codeMap = CommonStatic.getCodeMap();
		String typename = (String) codeMap.get(CommonStatic.LOTTERYTYPE_HEAD + vo.getLotteryCode());
		String playModelName = (String) codeMap.get(CommonStatic.PLAYMODEL_HEAD + vo.getPlayCode());
		String selectCodeName = (String) codeMap.get(CommonStatic.LOTTERYPLAYSELECT_HEAD + vo.getSelectCode());
		String userName = userDao.queryById(vo.getCustomerId()).getCustomerName();
		if (vo.getBetStatus() != DataDictionaryUtil.BET_ORDER_TYPE_SUCCESS) {
			Map<String, Object> newparam = new HashMap<String, Object>();
			newparam.put("lotteryCode", vo.getLotteryCode());
			newparam.put("issue", vo.getIssueNo());
			String openNumber = awardRecodeService.queryOpenbetNumberbylotteryCodeAndissue(newparam);
			vo.setOpernBetNumber(openNumber);
		}
		vo.setLotteryCode(typename);
		vo.setPlayCode(playModelName);
		vo.setuName(userName);
		vo.setSelectCodeName(selectCodeName);
		if (vo.getBetModel().doubleValue() == 1.0000) {
			vo.setBetModelName("元模式");
		} else if (vo.getBetModel().doubleValue() == 0.1000) {
			vo.setBetModelName("角模式");
		} else {
			vo.setBetModelName("分模式");
		}
		return vo;
	}

	@Override
	public BigDecimal getLowerLevelSumAmount(Map<String, ?> param) throws Exception {
		// TODO Auto-generated method stub
		return betRecordDao.getLowerLevelSumAmount(param);
	}

	@Override
	public Page<BetRecordVO, BetRecord> queryBetRecordsWebApp(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		// 如果有用户限制
		List<CustomerUser> users = userDao.quUserByTeam(param);
		if (users.size() > 0) {
			List<Long> ids = new ArrayList<Long>();
			for (CustomerUser user : users) {
				ids.add(user.getId());
			}
			param.put("idsKey", ids);

			Page<BetRecordVO, BetRecord> page = betRecordDao.queryBetRecordsWebApp(param);

			List<BetRecordVO> vos = new ArrayList<BetRecordVO>();
			for (BetRecord br : page.getEntitylist()) {
				if (br.getBileNum() != null && !br.getBileNum().equals("")) {
					br.setBileNum(AesUtil.decrypt(br.getBileNum(),
							Md5Manage.getInstance().getMd5() + br.getTempString()));
				}
				br.setBetNum(AesUtil.decrypt(br.getBetNum(), Md5Manage.getInstance().getMd5() + br.getTempString()));
				BetRecordVO brvo = new BetRecordVO();
				DozermapperUtil.getInstance().map(br, brvo);
				brvo.setLotteryCodeCopy(brvo.getLotteryCode());
				brvo.setPlayCodeCopy(brvo.getPlayCode());
				if (brvo.getBetStatus() != DataDictionaryUtil.BET_ORDER_TYPE_SUCCESS) {
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("lotteryCode", brvo.getLotteryCode());
					params.put("issue", brvo.getIssueNo());
					String openNumber = awardRecodeService.queryOpenbetNumberbylotteryCodeAndissue(params);
					brvo.setOpernBetNumber(openNumber);
				}
				Map<String, Object> codeMap = CommonStatic.getCodeMap();
				String typename = (String) codeMap.get(CommonStatic.LOTTERYTYPE_HEAD + brvo.getLotteryCode());
				String playModelName = (String) codeMap.get(CommonStatic.PLAYMODEL_HEAD + brvo.getPlayCode());
				String selectName = (String) codeMap.get(CommonStatic.LOTTERYPLAYSELECT_HEAD + brvo.getSelectCode());
				CustomerUser user = userDao.queryById(brvo.getCustomerId());
				String userName = user.getCustomerName();
				String level = Util.transition(user.getCustomerLevel());
				brvo.setLotteryCode(typename);
				brvo.setPlayCode(playModelName);
				brvo.setuName(userName);
				brvo.setSelectCodeName(selectName);
				brvo.setUserRebates(user.getRebates());
				brvo.setLevel(level);
				brvo.setCustometType(user.getCustomerType());
				vos.add(brvo);
			}
			page.setPagelist(vos);
			return page;
		} else {
			return null;
		}
	}

	@Override
	public Page<BetRecordVO, BetRecord> queryBetRecordsWebAppBySelf(Map<String, Object> param) throws Exception {
		Page<BetRecordVO, BetRecord> page = betRecordDao.queryBetRecordsWebAppBySelf(param);
		List<BetRecordVO> vos = new ArrayList<BetRecordVO>();
		Map<String, Object> codeMap = CommonStatic.getCodeMap();
		for (BetRecord br : page.getEntitylist()) {
			String key = br.getTempString();
			String betNum = "";
			if(br.getBileNum()== null|| br.getBileNum().equals("") ||org.apache.commons.lang3.StringUtils.isBlank(br.getBileNum())){
				betNum=AesUtil.decrypt(br.getBetNum(), Md5Manage.getInstance().getMd5()+key);
			}else{
				betNum = "胆码:"+AesUtil.decrypt(br.getBileNum(), Md5Manage.getInstance().getMd5()+key)+"托码:"+AesUtil.decrypt(br.getBetNum(), Md5Manage.getInstance().getMd5()+key);
			}
			BetRecordVO brvo = new BetRecordVO();
			DozermapperUtil.getInstance().map(br, brvo);
			brvo.setLotteryCodeCopy(brvo.getLotteryCode());
			brvo.setPlayCodeCopy(brvo.getPlayCode());
			String bileNum = brvo.getBileNum();
			if (bileNum != null && !bileNum.equals("")) {
				brvo.setBileNum(AesUtil.decrypt(bileNum, Md5Manage.getInstance().getMd5() + br.getTempString()));
			}
			if (brvo.getBetStatus() != DataDictionaryUtil.BET_ORDER_TYPE_SUCCESS) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("lotteryCode", brvo.getLotteryCode());
				params.put("issue", brvo.getIssueNo());
				String openNumber = awardRecodeService.queryOpenbetNumberbylotteryCodeAndissue(params);
				brvo.setOpernBetNumber(openNumber);
			}
			// String typename = lotteryTypeDao.queryLotteryTypeNameByCode(brvo.getLotteryCode());
			// String playModelName = modelDao.queryPlayModelByCode(brvo.getPlayCode()).getModelName();
			String typename = (String) codeMap.get(CommonStatic.LOTTERYTYPE_HEAD + brvo.getLotteryCode());
			String playModelName = (String) codeMap.get(CommonStatic.PLAYMODEL_HEAD + brvo.getPlayCode());
			String selectCodeName = (String) codeMap.get(CommonStatic.LOTTERYPLAYSELECT_HEAD + brvo.getSelectCode());
			// CustomerUser user = userDao.queryById(brvo.getCustomerId());
			// String userName = user.getCustomerName();
			// String level = Util.transition(user.getCustomerLevel());
			brvo.setLotteryCode(typename);
			brvo.setPlayCode(playModelName);
			brvo.setSelectCodeName(selectCodeName);
			// brvo.setuName(userName);
			// brvo.setUserRebates(userplayBonu.getRebates()// brvo.setLevel(level);
			brvo.setBetNum(betNum);
			vos.add(brvo);
		}
		page.setPagelist(vos);
		return page;
	}

	@Override
	public List<BetRecord> queryBetRecordsByOrderNo(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return betRecordDao.queryBetRecordsByOrderNo(param);
	}

	@Override
	public List<BetRecordVO> queryBetRecordVOsByOrderNo(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		List<BetRecord> brs = betRecordDao.queryBetRecordsByOrderNo(param);
		List<BetRecordVO> vos = new ArrayList<BetRecordVO>();
		for (BetRecord br : brs) {
			if (br.getBileNum() != null && !br.getBileNum().equals("")) {
				br.setBileNum(AesUtil.decrypt(br.getBileNum(), Md5Manage.getInstance().getMd5() + br.getTempString()));
			}
			br.setBetNum(AesUtil.decrypt(br.getBetNum(), Md5Manage.getInstance().getMd5() + br.getTempString()));
			BetRecordVO brvo = new BetRecordVO();
			DozermapperUtil.getInstance().map(br, brvo);
			brvo.setLotteryCodeCopy(brvo.getLotteryCode());
			brvo.setPlayCodeCopy(brvo.getPlayCode());
			String typename = lotteryTypeDao.queryLotteryTypeNameByCode(brvo.getLotteryCode());
			String playModelName = modelDao.queryPlayModelByCode(brvo.getPlayCode()).getModelName();
			brvo.setLotteryCode(typename);
			brvo.setPlayCode(playModelName);
			vos.add(brvo);
		}
		return vos;
	}

	@Override
	public void updateAwardEncrypt(Map<String, Object> param) throws Exception {
		betRecordDao.updateAwardEncrypt(param);
	}

	@Override
	public void updateBetAwardStop(Map<String, Object> param) throws Exception {
		betRecordDao.updateBetAwardStop(param);

	}

	@Override
	public String cancelBetRecord(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		BetRecordVO vo = (BetRecordVO) param.get("brvo");
		CustomerUser user = (CustomerUser) param.get(CommonUtil.CUSTOMERUSERKEY);
		BetRecord entity = betRecordDao.queryById(vo.getId());
		// 获取当前期
		LotteryTypeVO typevo = new LotteryTypeVO();
		typevo.setLotteryCode(entity.getLotteryCode());
		param.put("lotteryKey", typevo);
		TaskConfig taskConfig = taskConfigService.queryCurrentTask(param);
		if (entity.getBetStatus() == DataDictionaryUtil.BET_ORDER_TYPE_CANCEL) {
			throw new LotteryException("亲,该投注单已撤销");
		}
		if (Integer.parseInt(entity.getIssueNo()) < Integer.parseInt(taskConfig.getLotterySeries())) {
			throw new LotteryException("亲,该投注单已过期不能撤销");
		}
		if (entity.getCustomerId() != user.getId()) {
			throw new LotteryException("亲,不能撤销非自己的投注单哦");
		}
		// 撤销该投注单
		entity.updateInit(user.getCustomerName());
		entity.setBetStatus(DataDictionaryUtil.BET_ORDER_TYPE_CANCEL);
		betRecordDao.update(entity);
		customerUserWriteLog.saveWriteLog(user, CommonUtil.UPDATE, "t_bet_record", entity.toString());
		CustomerOrder orderEntity = orderDao.queryOrderByNum(entity.getOrderNo());
		if (orderEntity.getOrderDetailType() == DataDictionaryUtil.ORDER_DETAIL_TRACK) {
			throw new LotteryException("亲,请移步追号记录撤该投注单");
		}
		BigDecimal returnAmount = new BigDecimal(entity.getMultiple()).multiply(entity.getBetMoney());
		BigDecimal orignalAmount = new BigDecimal(entity.getMultiple()).multiply(entity.getBetMoney());

		BigDecimal rtAmount = orderEntity.getReturnAmount() == null ? BigDecimal.ZERO : orderEntity.getReturnAmount();
		returnAmount = rtAmount.add(returnAmount);
		orderEntity.setReturnAmount(returnAmount);
		orderEntity.updateInit(user.getCustomerName());
		orderDao.update(orderEntity);
		customerUserWriteLog.saveWriteLog(user, CommonUtil.UPDATE, "t_customer_order", orderEntity.toString());
		// 返回金额
		CustomerCash cash = cashDao.queryUserCashByCustomerId(user.getId());
		cash.setCash(cash.getCash().add(orignalAmount));
		cashDao.update(cash);
		customerUserWriteLog.saveWriteLog(user, CommonUtil.UPDATE, "t_customer_cash", cash.toString());
		// 生成退款订单
		CustomerOrder order = new CustomerOrder();
		String orderNo = orderSequenceDao.getOrderSequence(CommonUtil.ORDER_DETAIL_BET_ORDER, 8);
		order.setOrderNumber(orderNo);
		order.setCustomerId(user.getId());
		order.setOrderTime(new Date());
		order.setOrderAmount(orignalAmount);
		order.setOrderType(DataDictionaryUtil.ORDER_TYPE_INCOME);
		order.setOrderDetailType(DataDictionaryUtil.ORDER_DETAIL_CANCEL_STATUS);
		order.setOrderStatus(DataDictionaryUtil.ORDER_STATUS_SUCCESS);
		order.addInit(user.getCustomerName());
		order.setCashAmount(orignalAmount);
		order.setReceiveAmount(orignalAmount);
		// 记录用户余额
		order.setAccountBalance(cash.getCash());
		// 原订单号
		order.setRsvst1(orderEntity.getOrderNumber());
		orderDao.save(order);
		customerUserWriteLog.saveWriteLog(user, CommonUtil.SAVE, "t_customer_order", order.toString());
		return "success";
	}

	@SuppressWarnings("unchecked")
	@Override
	public String cancelPlanInfo(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		BetRecordVO vo = (BetRecordVO) param.get("brvo");
		CustomerUser user = (CustomerUser) param.get(CommonUtil.CUSTOMERUSERKEY);
		List<BetRecord> entitys = null;
		if (vo.getId() > 0) {
			Query query = betRecordDao.getSession().createQuery("from BetRecord t where id=?");
			query.setParameter(0, vo.getId());
			entitys = query.list();
		} else {
			Query query = betRecordDao.getSession().createQuery("from BetRecord t where t.orderNo = ? and t.issueNo=?");
			query.setParameter(0, vo.getOrderNo());
			query.setParameter(1, vo.getIssueNo());
			entitys = query.list();
		}

		// 获取当前期
		LotteryTypeVO typevo = new LotteryTypeVO();
		typevo.setLotteryCode(entitys.get(0).getLotteryCode());
		param.put("lotteryKey", typevo);
		TaskConfig taskConfig = taskConfigService.queryCurrentTask(param);
		if (entitys.get(0).getBetStatus() == DataDictionaryUtil.BET_ORDER_TYPE_CANCEL) {
			throw new LotteryException("亲,该投注单已撤销");
		}
		if (Integer.parseInt(entitys.get(0).getIssueNo()) < Integer.parseInt(taskConfig.getLotterySeries())) {
			throw new LotteryException("亲,该投注单已过期不能撤销");
		}
		if (entitys.get(0).getCustomerId() != user.getId()) {
			throw new LotteryException("亲,不能撤销非自己的投注单哦");
		}
		BigDecimal returnSumAmount = BigDecimal.ZERO;
		for (BetRecord entity : entitys) {
			// 撤销该投注单
			entity.updateInit(user.getCustomerName());
			entity.setBetStatus(DataDictionaryUtil.BET_ORDER_TYPE_CANCEL);
			// 区分是否是手动撤单
			entity.setRsvst1("hand");
			betRecordDao.update(entity);
			customerUserWriteLog.saveWriteLog(user, CommonUtil.UPDATE, "t_bet_record", entity.toString());
			BigDecimal returnAmount = new BigDecimal(entity.getMultiple()).multiply(entity.getBetMoney());
			returnSumAmount = returnSumAmount.add(returnAmount);
		}
		// 添加原订单退款金额
		CustomerOrder orderEntity = orderDao.queryOrderByNum(entitys.get(0).getOrderNo());
		BigDecimal rtAmount = orderEntity.getReturnAmount() == null ? BigDecimal.ZERO : orderEntity.getReturnAmount();
		BigDecimal sumAmount = rtAmount.add(returnSumAmount);
		orderEntity.setReturnAmount(sumAmount);
		orderEntity.updateInit(user.getCustomerName());
		orderDao.update(orderEntity);
		customerUserWriteLog.saveWriteLog(user, CommonUtil.UPDATE, "t_customer_order", orderEntity.toString());

		// 返回金额
		CustomerCash cash = cashDao.queryUserCashByCustomerId(user.getId());
		cash.setCash(cash.getCash().add(returnSumAmount));
		cashDao.update(cash);
		customerUserWriteLog.saveWriteLog(user, CommonUtil.UPDATE, "t_customer_cash", cash.toString());
		// 生成退款订单
		CustomerOrder order = new CustomerOrder();
		String orderNo = orderSequenceDao.getOrderSequence(CommonUtil.ORDER_DETAIL_BET_ORDER, 8);
		order.setOrderNumber(orderNo);
		order.setCustomerId(user.getId());
		order.setOrderTime(new Date());
		order.setOrderAmount(returnSumAmount);
		order.setOrderType(DataDictionaryUtil.ORDER_TYPE_INCOME);
		// 看订单类型是追号的话，撤单类型是追号撤单返款
		if (orderEntity.getOrderDetailType() == DataDictionaryUtil.ORDER_DETAIL_TRACK) {
			order.setOrderDetailType(DataDictionaryUtil.ORDER_DETAIL_CHASE_AFTER_REBATES);
			// 如果是追号撤单返款，则设置rsvst2标识为订单编号加期号
			order.setRsvst2(entitys.get(0).getOrderNo() + ":" + entitys.get(0).getIssueNo());
		} else {
			order.setOrderDetailType(DataDictionaryUtil.ORDER_DETAIL_CANCEL_STATUS);
		}
		order.setOrderStatus(DataDictionaryUtil.ORDER_STATUS_SUCCESS);
		order.addInit(user.getCustomerName());
		order.setCashAmount(returnSumAmount);
		order.setReceiveAmount(returnSumAmount);
		// 记录用户余额
		order.setAccountBalance(cash.getCash());
		// 原订单号
		order.setRsvst1(orderEntity.getOrderNumber());
		// 如果是在近期投注中撤销的单个投注记录，则记录id
		if (vo.getId() > 0) {
			order.setRsvst3(vo.getId() + "");
		}
		orderDao.save(order);
		customerUserWriteLog.saveWriteLog(user, CommonUtil.SAVE, "t_customer_order", order.toString());
		return "success";
	}

	@SuppressWarnings("unchecked")
	@Override
	public String updateCancelBetByAdmin(Map<String, Object> param) throws Exception {
		// 如果是撤销整期投注单，则vo的id为空，否则为撤销单条投注记录
		BetRecordVO vo = (BetRecordVO) param.get("betRecordkeyVo");
		AdminUser adminUser = (AdminUser) param.get(CommonUtil.USERKEY);
		StringBuffer hqlsb = new StringBuffer("from BetRecord t where 1=1 ");
		if (vo.getId() != 0) {
			hqlsb.append(" and t.id = :bid ");
		} else if (!StringUtils.isEmpty(vo.getIssueNo())) {
			hqlsb.append(" and t.issueNo = :isu and t.lotteryCode = :ltc ");
		}

		Query query = betRecordDao.getSession().createQuery(hqlsb.toString());
		if (vo.getId() != 0) {
			query.setParameter("bid", vo.getId());
		} else if (!StringUtils.isEmpty(vo.getIssueNo())) {
			query.setParameter("isu", vo.getIssueNo());
			query.setParameter("ltc", vo.getLotteryCode());
		}
		List<BetRecord> entitys = query.list();
		if (vo.getId() != 0 && entitys.get(0).getBetStatus() == DataDictionaryUtil.BET_ORDER_TYPE_CANCEL) {
			throw new LotteryException("亲,该投注单已撤销");
		}
		// BigDecimal returnSumAmount = BigDecimal.ZERO;
		Map<String, BigDecimal> orderReturnMap = new HashMap<String, BigDecimal>();

		Map<String, Long> orderUserIdMap = new HashMap<String, Long>();

		for (BetRecord entity : entitys) {
			if (entity.getBetStatus() == DataDictionaryUtil.BET_ORDER_TYPE_CANCEL) {
				continue;
			}
			// 撤销该投注单
			entity.updateInit(adminUser.getUserName());
			entity.setBetStatus(DataDictionaryUtil.BET_ORDER_TYPE_CANCEL);
			betRecordDao.update(entity);
			adminUserWriteLog.saveWriteLog(adminUser, CommonUtil.UPDATE, "t_bet_record", entity.toString());
			BigDecimal returnAmount = new BigDecimal(entity.getMultiple()).multiply(entity.getBetMoney());
			if (orderReturnMap.get(entity.getOrderNo()) == null) {
				orderReturnMap.put(entity.getOrderNo(), returnAmount);
				orderUserIdMap.put(entity.getOrderNo(), entity.getCustomerId());
			} else {
				BigDecimal orignalAmount = orderReturnMap.get(entity.getOrderNo());
				orignalAmount = orignalAmount.add(returnAmount);
				orderReturnMap.remove(entity.getOrderNo());
				orderReturnMap.put(entity.getOrderNo(), orignalAmount);
			}
		}

		for (String key : orderReturnMap.keySet()) {
			CustomerUser user = userDao.queryById(orderUserIdMap.get(key));
			// 添加原订单退款金额
			CustomerOrder orderEntity = orderDao.queryOrderByNum(key);
			BigDecimal rtAmount = orderEntity.getReturnAmount() == null ? BigDecimal.ZERO : orderEntity
					.getReturnAmount();
			BigDecimal sumAmount = rtAmount.add(orderReturnMap.get(key));
			orderEntity.setReturnAmount(sumAmount);
			orderEntity.updateInit(adminUser.getUserName());
			orderDao.update(orderEntity);
			adminUserWriteLog.saveWriteLog(adminUser, CommonUtil.UPDATE, "t_customer_order", orderEntity.toString());

			// 返回金额
			CustomerCash cash = cashDao.queryUserCashByCustomerId(user.getId());
			cash.setCash(cash.getCash().add(orderReturnMap.get(key)));
			cashDao.update(cash);
			adminUserWriteLog.saveWriteLog(adminUser, CommonUtil.UPDATE, "t_customer_cash", cash.toString());

			// 生成退款订单
			CustomerOrder order = new CustomerOrder();
			String orderNo = orderSequenceDao.getOrderSequence(CommonUtil.ORDER_DETAIL_BET_ORDER, 8);
			order.setOrderNumber(orderNo);
			order.setCustomerId(user.getId());
			order.setOrderTime(new Date());
			order.setOrderAmount(orderReturnMap.get(key));
			order.setOrderType(DataDictionaryUtil.ORDER_TYPE_INCOME);
			// 系统撤单返款
			order.setOrderDetailType(DataDictionaryUtil.ORDER_DETAIL_SYSTEM_REBATES);
			order.setOrderStatus(DataDictionaryUtil.ORDER_STATUS_SUCCESS);
			order.addInit(adminUser.getUserName());
			order.setCashAmount(orderReturnMap.get(key));
			order.setReceiveAmount(orderReturnMap.get(key));
			// 记录用户余额
			order.setAccountBalance(cash.getCash());
			// 原订单号
			order.setRsvst1(orderEntity.getOrderNumber());
			orderDao.save(order);
			adminUserWriteLog.saveWriteLog(adminUser, CommonUtil.SAVE, "t_customer_order", order.toString());
		}

		return "success";
	}

	@Override
	public Map<String, BigDecimal> lotterySalesInfo(Map<String, Object> param) throws Exception {
		Map<String, BigDecimal> returnMap = betRecordDao.lotterySalesInfo(param);
		return returnMap;
	}

	@Override
	public List<BetRecord> queryBetRecord(Map<String, Object> param) throws Exception {
		List<BetRecord> records = betRecordDao.queryBetRecord(param);
		for(BetRecord br:records){
			br.setBetModelName(CommonStatic.getCodeMap().get(CommonStatic.PLAYMODEL_HEAD+br.getPlayCode()).toString());
			String key = br.getTempString();
			if (br.getBileNum() == null || br.getBileNum().equals("") || org.apache.commons.lang3.StringUtils.isBlank(br.getBileNum())) {
				br.setBetContent(AesUtil.decrypt(br.getBetNum().trim(), Md5Manage.getInstance().getMd5() + key));
			} else {
				br.setBetContent( "胆码:" + AesUtil.decrypt(br.getBileNum(), Md5Manage.getInstance().getMd5() + key) + "托码:"
						+ AesUtil.decrypt(br.getBetNum().trim(), Md5Manage.getInstance().getMd5() + key));
			}
			br.setLotteryName(CommonStatic.getCodeMap().get(CommonStatic.LOTTERYTYPE_HEAD+br.getLotteryCode()).toString());
			br.setPlayName(CommonStatic.getCodeMap().get(CommonStatic.PLAYMODEL_HEAD+br.getPlayCode()).toString());
		}
		return records;
	}

	@Override
	public List<CustomerOrderStaVo> queryTeamYkReport(Map<String, Object> param) throws Exception {
		return null;
	}

	@Override
	public List<CustomerOrderStaVo> queryTeamYkRecords(Map<String, Object> param) throws Exception {
		return betRecordDao.queryTeamYkRecords(param);
	}
}
