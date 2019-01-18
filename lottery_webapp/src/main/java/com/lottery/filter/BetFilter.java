package com.lottery.filter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.LotteryPlayModel;
import com.lottery.bean.entity.PlayModel;
import com.lottery.bean.entity.TaskConfig;
import com.lottery.bean.entity.vo.BetRecordListVO;
import com.lottery.bean.entity.vo.BetRecordVO;
import com.lottery.bean.entity.vo.LotteryPlayModelVO;
import com.lottery.bean.entity.vo.LotteryTypeVO;
import com.lottery.core.play.FuCai3D;
import com.lottery.init.NumberCheck;
import com.lottery.limit.ElevenSelectedFivePlayLimit;
import com.lottery.limit.FuCai3DLimit;
import com.lottery.limit.OftenPlayLimit;
import com.lottery.service.ICustomerOrderService;
import com.lottery.service.ILotteryPlayModelService;
import com.lottery.service.IPlayModelService;
import com.lottery.service.ITaskConfigService;
import com.lottery.staticvalue.CommonStatic;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;

@Component
public class BetFilter {

	@Autowired
	private ITaskConfigService taskConfigService;
	
	@Autowired
	private ElevenSelectedFivePlayLimit  elevenSelectedFivePlayLimit;
	
	@Autowired
	private ILotteryPlayModelService iLotteryPlayModelService;
	
	@Autowired
	private OftenPlayLimit oftenPlayLimit;
	
	@Autowired
	private FuCai3DLimit fuCai3DLimit;

	@Autowired
	private IPlayModelService plyaModelService;
	
	@Autowired
	private ICustomerOrderService orderService;
	/**
	 * 投注时间限制
	 * 
	 * @param vos
	 * @return
	 * @throws Exception 
	 */
	public void betTimeLimit(String issueNo,TaskConfig taskConfig) throws Exception {
		String[] issues = issueNo.split(",");
		for (String str : issues) {
			if (taskConfig == null)
				throw new LotteryException("投注单错误，请检查投注期数");
			if (Integer.parseInt(str.split(":")[0]) < Integer.parseInt(taskConfig
					.getLotterySeries()))
				throw new LotteryException("投注单错误，请检查投注期数");
		}
	}
	
	public void customerLevelLimit(CustomerUser user) throws LotteryException{
		if(user.getRebates().compareTo(new BigDecimal("0.125"))>0){
			throw new LotteryException("直属总代理不能投注");
		}
		if(user.getCustomerLevel() == 0) throw new LotteryException("总代不能投注");
	}

	/**
	 * 未充值用户，2元最高投注金额限制。
	 * @param user
	 * @throws Exception 
	 */
	public void noRechargeLimit(BetRecordListVO vo,CustomerUser user) throws Exception{
//		Map<String, Object> param = new HashMap<String, Object>();
//		param.put("betVoKey", vo);
//		param.put(CommonUtil.CUSTOMERUSERKEY, user);
//		//统计第三方充值次数
//		param.put("cashRechargeKey", true);
//		int times = orderService.countOtherPlayTimes(param);
//		if(times==0&&vo.getOrderAmount().compareTo(new BigDecimal("2"))>0){
//			throw new LotteryException("亲，未充值用户，单笔订单投注金额必须小于或等于2元哦！");
//		}
	}
	/**
	 * 11x5投注限制
	 * 
	 * @param vos
	 * @return
	 * @throws Exception 
	 */
	public void betNumberLimit(BetRecordListVO vos,String issues) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		LotteryPlayModelVO playModelvo = new LotteryPlayModelVO();
		//获取限制金额
		playModelvo.setLotteryCode(vos.getVolist().get(0).getLotteryCode());
		playModelvo.setStatus(1);
		param.put("lpmKey",playModelvo);
		List<LotteryPlayModel> lotteryPlayModels = iLotteryPlayModelService.queryPlayModelByLotteryCode(param);
		//获取当前期
		LotteryTypeVO typevo = new LotteryTypeVO();
		typevo.setLotteryCode(vos.getVolist().get(0).getLotteryCode());
		param.put("lotteryKey", typevo);
		TaskConfig taskConfig = taskConfigService.queryCurrentTask(param);
		String lotteryCode = taskConfig.getLotteryCode();
		Integer issueNo = Integer.parseInt(taskConfig.getLotterySeries());
		//将限制金额做成Map
		Map<String, BigDecimal> tempMap = new HashMap<String, BigDecimal>();
		for(LotteryPlayModel lpm:lotteryPlayModels){
			tempMap.put(lpm.getModelCode(), lpm.getLimitAmount());
		}
		//中奖金额Map
		Map<String,BigDecimal> winMap = new HashMap<String, BigDecimal>();
		List<PlayModel> playModels = plyaModelService.getAllPlayModel();
		for(PlayModel playModel : playModels){
			if(playModel.getStatus()!=1)continue;
			winMap.put(playModel.getModelCode(), playModel.getWinAmount());
		}
		Map<String, Map<String,BigDecimal>> limits = WebAppStaticCollection.getLimitMap();
		Map<String, Set<String>> limitNumberMap = WebAppStaticCollection
				.getLimitNumberMap();
		this.removeMap(limits, limitNumberMap,lotteryCode,issueNo);
		String[] nos = issues.split(",");
		//订单总金额检查
		BigDecimal orderAmount = BigDecimal.ZERO;
		for(String str:nos){
			for(BetRecordVO br:vos.getVolist()){
				br.setIssueNo(str.split(":")[0]);
				br.setMultiple(Integer.parseInt(str.split(":")[1]));
				if(!br.getLotteryCode().equals(lotteryCode))throw new LotteryException("投注单异常,请检查该投注单");
				String key = br.getLotteryCode()+","+br.getIssueNo();
				Map<String,BigDecimal> moneylimit = limits.get(key);
				if(moneylimit == null){
					moneylimit = new HashMap<String, BigDecimal>();
					limits.put(key, moneylimit);
				}
				List<String> betNumbers = elevenSelectedFivePlayLimit.invok(br.getSelectCode(), br.getBileNum(), br.getBetNum());
				//限号检查
				orderAmount = orderAmount.add(this.checkBetNum(betNumbers, moneylimit, br, winMap, tempMap, limits,key));
			}
		}
		if(orderAmount.compareTo(vos.getOrderAmount())!=0){
			throw new LotteryException("投注单异常,请检查该投注单");
		}
	}
	
	
	/**
	 * SSC投注限制
	 * 
	 * @param vos
	 * @return
	 * @throws Exception 
	 */
	public void sscBetNumberLimit(BetRecordListVO vos,String issues) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		LotteryPlayModelVO playModelvo = new LotteryPlayModelVO();
		//获取限制金额
		playModelvo.setLotteryCode(vos.getVolist().get(0).getLotteryCode());
		playModelvo.setStatus(1);
		param.put("lpmKey",playModelvo);
		List<LotteryPlayModel> lotteryPlayModels = iLotteryPlayModelService.queryPlayModelByLotteryCode(param);
		//获取当前期
		LotteryTypeVO typevo = new LotteryTypeVO();
		typevo.setLotteryCode(vos.getVolist().get(0).getLotteryCode());
		param.put("lotteryKey", typevo);
		TaskConfig taskConfig = taskConfigService.queryCurrentTask(param);
		String lotteryCode = taskConfig.getLotteryCode();
		Integer issueNo = Integer.parseInt(taskConfig.getLotterySeries());
		//将限制金额做成Map
		Map<String, BigDecimal> tempMap = new HashMap<String, BigDecimal>();
		for(LotteryPlayModel lpm:lotteryPlayModels){
			tempMap.put(lpm.getModelCode(), lpm.getLimitAmount());
		}
		//中奖金额Map
		Map<String,BigDecimal> winMap = new HashMap<String, BigDecimal>();
		for(String key : CommonStatic.getCodeObjectMap().keySet()){
			if(key.startsWith(CommonStatic.PLAYMODEL_HEAD)){
				PlayModel playModel = (PlayModel) CommonStatic.getCodeObjectMap().get(key);
				winMap.put(playModel.getModelCode(), playModel.getWinAmount());
			}
		}
		Map<String, Map<String,BigDecimal>> limits = WebAppStaticCollection.getLimitMap();
		Map<String, Set<String>> limitNumberMap = WebAppStaticCollection
				.getLimitNumberMap();
		this.removeMap(limits, limitNumberMap,lotteryCode,issueNo);
		String[] nos = issues.split(",");
		//订单总金额检查
		BigDecimal orderAmount = BigDecimal.ZERO;
		for(String str:nos){
			for(BetRecordVO br:vos.getVolist()){
				br.setIssueNo(str.split(":")[0]);
				br.setMultiple(Integer.parseInt(str.split(":")[1]));
				if(!br.getLotteryCode().equals(lotteryCode))throw new LotteryException("投注单异常,请检查该投注单");
				String key = br.getLotteryCode()+","+br.getIssueNo();
				Map<String,BigDecimal> moneylimit = limits.get(key);
				if(moneylimit == null){
					moneylimit = new HashMap<String, BigDecimal>();
					limits.put(key, moneylimit);
				}
				List<String> betNumbers = oftenPlayLimit.invok(br.getSelectCode(), br.getBileNum(), br.getBetNum());
				//限号检查
				orderAmount = orderAmount.add(this.checkBetNum(betNumbers, moneylimit, br, winMap, tempMap, limits,key));
			}
		}
		if(orderAmount.compareTo(vos.getOrderAmount())!=0){
			throw new LotteryException("投注单异常,请检查该投注单");
		}
	}
	
	/**
	 * p3投注限制
	 * 
	 * @param vos
	 * @return
	 * @throws Exception 
	 */
	public void p3BetNumberLimit(BetRecordListVO vos,String issues) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		LotteryPlayModelVO playModelvo = new LotteryPlayModelVO();
		//获取限制金额
		playModelvo.setLotteryCode(vos.getVolist().get(0).getLotteryCode());
		playModelvo.setStatus(1);
		param.put("lpmKey",playModelvo);
		List<LotteryPlayModel> lotteryPlayModels = iLotteryPlayModelService.queryPlayModelByLotteryCode(param);
		//获取当前期
		LotteryTypeVO typevo = new LotteryTypeVO();
		typevo.setLotteryCode(vos.getVolist().get(0).getLotteryCode());
		param.put("lotteryKey", typevo);
		TaskConfig taskConfig = taskConfigService.queryCurrentTask(param);
		String lotteryCode = taskConfig.getLotteryCode();
		Integer issueNo = Integer.parseInt(taskConfig.getLotterySeries());
		//将限制金额做成Map
		Map<String, BigDecimal> tempMap = new HashMap<String, BigDecimal>();
		for(LotteryPlayModel lpm:lotteryPlayModels){
			tempMap.put(lpm.getModelCode(), lpm.getLimitAmount());
		}
		//中奖金额Map
		Map<String,BigDecimal> winMap = new HashMap<String, BigDecimal>();
		List<PlayModel> playModels = plyaModelService.getAllPlayModel();
		for(PlayModel playModel : playModels){
			if(playModel.getStatus()!=1)continue;
			winMap.put(playModel.getModelCode(), playModel.getWinAmount());
		}
		Map<String, Map<String,BigDecimal>> limits = WebAppStaticCollection.getLimitMap();
		Map<String, Set<String>> limitNumberMap = WebAppStaticCollection
				.getLimitNumberMap();
		this.removeMap(limits, limitNumberMap,lotteryCode,issueNo);
		String[] nos = issues.split(",");
		//订单总金额检查
		BigDecimal orderAmount = BigDecimal.ZERO;
		for(String str:nos){
			for(BetRecordVO br:vos.getVolist()){
				br.setIssueNo(str.split(":")[0]);
				br.setMultiple(Integer.parseInt(str.split(":")[1]));
				if(!br.getLotteryCode().equals(lotteryCode))throw new LotteryException("投注单异常,请检查该投注单");
				String key = br.getLotteryCode()+","+br.getIssueNo();
				Map<String,BigDecimal> moneylimit = limits.get(key);
				if(moneylimit == null){
					moneylimit = new HashMap<String, BigDecimal>();
					limits.put(key, moneylimit);
				}
				List<String> betNumbers = fuCai3DLimit.invok(br.getSelectCode(), br.getBileNum(), br.getBetNum());
				//限号检查
				orderAmount = orderAmount.add(this.checkBetNum(betNumbers, moneylimit, br, winMap, tempMap, limits,key));
			}
		}
		if(orderAmount.compareTo(vos.getOrderAmount())!=0){
			throw new LotteryException("投注单异常,请检查该投注单");
		}
	}
	
	private BigDecimal checkBetNum(List<String> betNumbers,Map<String,BigDecimal> moneylimit,BetRecordVO br,
						Map<String,BigDecimal> winMap,Map<String, BigDecimal> tempMap,Map<String, Map<String,BigDecimal>> limits,String key) throws Exception{
		BigDecimal orderAmount = BigDecimal.ZERO;
		int size = 0;
		if(betNumbers.get(0).indexOf(":")>-1){
			for(String num:betNumbers){
				if(num.split(":")[1].indexOf(",")>-1){
					size = betNumbers.size();
					break;
				}
				if(num.indexOf(":")>-1){
					size+=Integer.parseInt(num.split(":")[1]);
				}
			}
		}else{
			size = betNumbers.size();
		}
		BigDecimal tempBetMoney = new BigDecimal(2).multiply(new BigDecimal(size)).multiply(br.getBetModel()).multiply(new BigDecimal(br.getMultiple()));
		BigDecimal brBetMoney = br.getBetMoney().multiply(new BigDecimal(br.getMultiple()));
		if(br.getBetCount() != size){
			throw new LotteryException("投注注单异常");
		}
		if(tempBetMoney.compareTo(brBetMoney)!=0){
			throw new LotteryException("投注金额异常");
		}
		orderAmount = orderAmount.add(tempBetMoney);
		//动态金额
		BigDecimal actAmount = BigDecimal.ZERO;
		for(String moneykey:moneylimit.keySet()){
			actAmount = actAmount.add(moneylimit.get(moneykey.trim()));
		}
		int betCount = 0;
		for(String betNumber:betNumbers){
			if(NumberCheck.sscMap.get(br.getSelectCode()).get(betNumber.trim())==null){
				throw new LotteryException("投注单异常,请检查该投注单");
			}
			BigDecimal subMoney = moneylimit.get(betNumber) == null ? BigDecimal.ZERO :moneylimit.get(betNumber);
			actAmount = actAmount.subtract(subMoney);
			BigDecimal winAmount = winMap.get(br.getPlayCode());
			betCount = actAmount.intValue()/winAmount.intValue();
			actAmount = new BigDecimal(actAmount.intValue()/winAmount.intValue()).multiply(new BigDecimal(2));
			//END
			BigDecimal tempMoney = BigDecimal.ZERO;
			if(moneylimit.get(betNumber.trim())!=null){
				tempMoney = moneylimit.get(betNumber.trim());
			}
			BigDecimal betMoney = new BigDecimal(2).multiply(new BigDecimal(br.getMultiple())).multiply(br.getBetModel());
			tempMoney = tempMoney.add(betMoney);
			BigDecimal tempLimitMoney = tempMap.get(br.getPlayCode());
			tempLimitMoney = tempLimitMoney.add(actAmount);
			if(tempMoney.intValue()>tempLimitMoney.intValue()){
				if(moneylimit.get(betNumber)!=null&&tempMoney.subtract(betMoney).intValue()>tempLimitMoney.multiply(new BigDecimal(0.98)).intValue()){
					String limitNumberKey = br.getLotteryCode()+","+br.getPlayCode()+","+br.getIssueNo();
					Set<String> numbers =  WebAppStaticCollection.getLimitNumberMap().get(limitNumberKey);
					if(numbers==null)numbers = new HashSet<>();
					if(betNumber.indexOf(":")<0){
						numbers.add(betNumber.trim());
					}else if(betNumber.indexOf(";")>-1){
						numbers.add(betNumber.split(";")[1].trim());
					}else{
						numbers.add(betNumber.split(":")[0].trim());
					}
					WebAppStaticCollection.getLimitNumberMap().put(limitNumberKey, numbers);
				}
				throw new LotteryException("第"+br.getIssueNo()+"期号码"+betNumber+"达到限制");
			}
		}
		//end
		for(String betNumber:betNumbers){
			//判断Map中该号码是否已经存在
			BigDecimal tempMoney = BigDecimal.ZERO;
			if(moneylimit.get(betNumber.trim())!=null){
				tempMoney = moneylimit.get(betNumber.trim());
			}
			BigDecimal betMoney = new BigDecimal(2).multiply(new BigDecimal(br.getMultiple())).multiply(br.getBetModel());
			tempMoney = tempMoney.add(betMoney);
			BigDecimal tempLimitMoney = tempMap.get(br.getPlayCode());
			tempLimitMoney = tempLimitMoney.add(actAmount);
			moneylimit.put(betNumber.trim(), tempMoney);
			if(tempMoney.intValue()>=tempLimitMoney.multiply(new BigDecimal(0.98)).intValue()){
				String limitNumberKey = br.getLotteryCode()+","+br.getPlayCode()+","+br.getIssueNo();
				Set<String> numbers =  WebAppStaticCollection.getLimitNumberMap().get(limitNumberKey);
				if(numbers==null)numbers = new HashSet<>();
				if(betCount>0)numbers.clear();
				if(betNumber.indexOf(":")<0){
					numbers.add(betNumber.trim());
				}else if(betNumber.indexOf(";")>-1){
					numbers.add(betNumber.split(";")[1].trim());
				}else{
					numbers.add(betNumber.split(":")[0].trim());
				}
				WebAppStaticCollection.getLimitNumberMap().put(limitNumberKey, numbers);
			}
		}
		return orderAmount;
	}
	
	private void removeMap(Map<String, Map<String,BigDecimal>> limits,Map<String, Set<String>> limitNumberMap,
									String lotteryCode,int issueNo)throws Exception{
			List<String> keys = new ArrayList<String>();
			//移除当前期二期前数据Map
			if (limits != null && limits.keySet() != null) {
				for (String key :limits.keySet()) {
					String str = key;
					if (str == null)
						continue;
					String tempLotteryCode = str.split(",")[0];
					Integer tempIssueNo = Integer.parseInt(str.split(",")[1]);
					if (lotteryCode.equals(tempLotteryCode)
							&& issueNo - tempIssueNo >= 1) {
						if (limits.get(str) == null)
							continue;
						keys.add(key);
					}
				}
			}
			for(String key:keys){
				limits.remove(key);
			}
			List<String> keys1 = new ArrayList<String>();
			//移除当前期二期前数据
			if (limitNumberMap != null && limitNumberMap.keySet() != null) {
				for (String key :limits.keySet()) {
					String str = key;
					if (str == null)
						continue;
					String tempLotteryCode = str.split(",")[0];
					Integer tempIssueNo = Integer.parseInt(str.split(",")[1]);
					if (lotteryCode.equals(tempLotteryCode)
							&& issueNo - tempIssueNo >= 1) {
						if (limits.get(str) == null)
							continue;
						keys1.add(key);
					}
				}
			}
			for(String key:keys1){
				limitNumberMap.remove(key);
			}
	}
	
	public void checkBlackList(BetRecordListVO vos){
//		for(BetRecordVO br:vos.getVolist()){
//			String result = GetProperty.getPropertyByName("blacklist",br.getPlayCode());
//		}
	}
	
	/**
	 * 彩种奖期状态检查
	 * @param vos
	 * @return
	 * @throws Exception 
	 */
	public void checkTaskStatus(TaskConfig taskConfig) throws Exception {
		if(taskConfig==null)throw new LotteryException("亲，无法获取当前奖期，无法投注！");
		if(taskConfig.getTaskStatus()==DataDictionaryUtil.STATUS_DELETE){
			throw new LotteryException("亲，当前彩种已暂停销售！");
		}else if(taskConfig.getTaskStatus()==DataDictionaryUtil.STATUS_CLOSE){
			throw new LotteryException("亲，当前彩种已关闭！");
		}
	}
	
	public static void main(String[] args) {
		String str ="swim3:10000,swim4:0,swim5:0,swim6:0,";
		String regex = "(.+?),";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		if (matcher.matches()) {
			String group = matcher.group(0);
			System.out.println(group);
		}else {
			System.out.println("no matches!!");
		}
		///////////////
		Pattern pattern1 = Pattern.compile("href=\"(.+?)\"");   
		Matcher matcher1 = pattern1.matcher("<a href=\"index.html\">主页</a>");   
		if(matcher1.find()) {   
		  System.out.println(matcher1.group(1));   
		} 
	}
}
