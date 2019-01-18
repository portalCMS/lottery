package com.lottery.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.BetRecord;
import com.lottery.bean.entity.LotteryAwardRecord;
import com.lottery.bean.entity.LotteryType;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.TaskConfig;
import com.lottery.bean.entity.vo.LotteryAwardRecordVO;
import com.lottery.bean.entity.vo.LotteryPlayModelListVO;
import com.lottery.bean.entity.vo.LotteryPlaySelectVO;
import com.lottery.bean.entity.vo.LotterySourceVO;
import com.lottery.bean.entity.vo.LotteryTypeVO;
import com.lottery.core.play.OftenPlay;
import com.lottery.dao.ICustomerOrderDao;
import com.lottery.dao.ILotteryAwardRecordDao;
import com.lottery.dao.ILotteryTypeDao;
import com.lottery.dao.ITaskConfigDao;
import com.lottery.service.IBetRecordService;
import com.lottery.service.ILotteryAwardRecordService;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;
import com.xl.lottery.util.DateUtil;

@Service
public class LotteryAwardRecordServiceImpl implements ILotteryAwardRecordService{
	@Autowired
	private ILotteryAwardRecordDao awardDao;
	
	@Autowired
	private ICustomerOrderDao orderDao;
	
	@Autowired
	private IBetRecordService  betService;
	
	@Autowired
	private ILotteryTypeDao lotteryDao;
	
	@Autowired
	private ITaskConfigDao taskDao;
	
	@Override
	public LotteryAwardRecord insertAwardRecord(Map<String, Object> param)
			throws Exception {
		LotteryAwardRecord ar = (LotteryAwardRecord) param.get("awardRecordKey");
		ar.setCreateTime(DateUtil.getNowDate());
		ar.setUpdateTime(DateUtil.getNowDate());
		ar.setStatus(DataDictionaryUtil.STATUS_RUNNING);
		awardDao.insert(ar);
		return ar;
	}

	@Override
	public LotteryAwardRecord updateAwardRecord(Map<String, Object> param)
			throws Exception {
		LotteryAwardRecord ar = (LotteryAwardRecord) param.get("awardRecordKey");
		ar.setUpdateTime(DateUtil.getNowDate());
		awardDao.update(ar);
		return ar;
	}

	@Override
	public List<LotteryAwardRecord> queryRecentAwardRecord(
			Map<String, Object> param) throws Exception {
		List<LotteryAwardRecord> list = awardDao.queryRecentAwardRecord(param);
		for(LotteryAwardRecord record : list){
			//截取时分秒，去掉日期。
			String openTime = record.getOpenTime();
			String startTime = record.getStartTime();
			String endTime = record.getEndTime();
			if(!StringUtils.isEmpty(openTime)){
				record.setOpenTime(openTime.substring(openTime.length()-8));
			}
			if(!StringUtils.isEmpty(startTime)){
				record.setStartTime(startTime.substring(startTime.length()-8));
			}
			if(!StringUtils.isEmpty(endTime)){
				record.setEndTime(endTime.substring(endTime.length()-8));
			}
			
			
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String queryOpenbetNumberbylotteryCodeAndissue(Map<String, ?> param)
			throws Exception {
		// TODO Auto-generated method stub
		String lotteryCode = (String)param.get("lotteryCode");
		String issue = (String)param.get("issue");
		StringBuffer queryString = new StringBuffer("select t.lotteryNumber from LotteryAwardRecord t where t.lotteryCode = ? and t.issue=? and t.status in (:stas)");
		Query query = awardDao.getSession().createQuery(queryString.toString());
		query.setParameter(0, lotteryCode);
		query.setParameter(1, issue);
		List<Integer> stas = new ArrayList<Integer>();
		stas.add(DataDictionaryUtil.STATUS_SUCCESS);
		stas.add(DataDictionaryUtil.HAND_STATUS_SUCCESS);
		query.setParameterList("stas", stas);
		List<String> lotteryNumber = query.list();
		if(lotteryNumber.size()==0)return "";
		return lotteryNumber.get(0);
	}

	@Override
	public Page<LotteryAwardRecordVO, LotteryAwardRecord> queryFailedTask(Map<String, Object> param)
			throws Exception {
		Page<LotteryAwardRecordVO, LotteryAwardRecord> page = awardDao.queryFailedTask(param);
		return page;
	}

	/**
	 * 停止自动开奖
	 */
	@Override
	public LotteryAwardRecord updateAutoTask(Map<String, Object> param)
			throws Exception {
		LotteryAwardRecordVO vo = (LotteryAwardRecordVO) param.get("awardKey");
		AdminUser user =  (AdminUser) param.get(CommonUtil.USERKEY);
		Long id = vo.getId();
		LotteryAwardRecord record = awardDao.queryById(id);
		record.setStatus(DataDictionaryUtil.HAND_STATUS_READY);
		record.setOpenType(DataDictionaryUtil.AWARD_OPEN_TYPE_HAND);
		record.setUpdateTime(DateUtil.getNowDate());
		record.setUpdateUser(user.getUserName());
		awardDao.update(record);
		return record;
	}

	@Override
	public LotteryAwardRecord queryRecordById(Map<String, Object> param)
			throws Exception {
		Long id = (Long) param.get("recordIdKey");
		LotteryAwardRecord record = awardDao.queryById(id);
		return record;
	}

	/**
	 * 手动开奖
	 */
	@Override
	public void updateHandAward(Map<String, Object> param) throws Exception {
		LotteryAwardRecordVO vo = (LotteryAwardRecordVO) param.get("awardKey");
		AdminUser user =  (AdminUser) param.get(CommonUtil.USERKEY);
		Long id = vo.getId();
		LotteryAwardRecord record = awardDao.queryById(id);
		//验证输入的开奖号码是否正确
		this.checkLotNum(vo.getLotteryNumber(), record.getLotteryGroup());
		//验证是否是开奖后的重新开奖,如果是则必须在五分钟内重新开奖。
		if(!StringUtils.isEmpty(record.getLotteryNumber())){
			Long lastOpenTime = DateUtil.strToDateLong(record.getOpenTime()).getTime();
			Long nowTime = DateUtil.getNowDate().getTime();
//			if(nowTime-lastOpenTime>144800000){
//				throw new LotteryException("重新开奖，必须在上次开奖的240分钟内进行操作！");
//			}
		};
		
		//验证该彩种该期是否已经做过开奖，如果是则要求必须先执行撤单操作。
		param.put("lotCodeKey", record.getLotteryCode());
		param.put("issueKey", record.getIssue());
		boolean isOpened = orderDao.checkIssueAward(param);
		if(isOpened){
			throw new LotteryException("彩种["+record.getLotteryName()+"]，奖期["+record.getIssue()+"]已经做过开奖，"
							+ "请先对该奖期进行中奖撤单操作，再手动开奖！");
		}
		//检查该奖期是否已经返还返点。
		boolean isPayBetRebates= orderDao.checkBetRebates(param);
		
		//开始开奖验奖派奖。
		param.put("lotteryCode", record.getLotteryCode());
		param.put("issueNo", record.getIssue());
		param.put("lotteryNum", vo.getLotteryNumber());
		List<BetRecord> wingRecords = betService.openLotteryResult(param);
		//结束开奖验奖派奖
		
		//开始派发返点金额，并且给追期订单的完成期数加1
		if(!isPayBetRebates){
			betService.updatePayBetRebates(param);
		}
		
		//检查追号订单是否有中奖中止设置，有的话要撤销之后的追期投注记录。
		if(wingRecords!=null&&wingRecords.size()>0){
			param.put("wingRecordsKey", wingRecords);
			betService.updateBetAwardStop(param);
			//执行中奖数据标识设置
			betService.updateAwardEncrypt(param);
		}
		
		record.setStatus(DataDictionaryUtil.HAND_STATUS_SUCCESS);
		record.setOpenType(DataDictionaryUtil.AWARD_OPEN_TYPE_HAND);
		record.setLotteryNumber(vo.getLotteryNumber());
		record.setOpenTime(DateUtil.getStringDate());
		record.setEndTime(DateUtil.getStringDate());
		record.setUpdateTime(DateUtil.getNowDate());
		record.setUpdateUser(user.getUserName());
		awardDao.update(record);
		
	}

	/**
	 * 验证开奖号码输入是否正确
	 * @param lotNum
	 * @return
	 * @throws LotteryException 
	 */
	private void checkLotNum(String lotteryNumber,String lotGroup) throws LotteryException{
		String[] numArr = lotteryNumber.split(",");
		if(StringUtils.isEmpty(lotteryNumber)||(numArr.length!=5&&numArr.length!=3)){
			throw new LotteryException("开奖号码必须输入5个或者3个号码！");
		}else{
			int maxNum =0;
			int numLength=0;
			if(lotGroup.equals("SYXW")){
				Set<String> set = new HashSet<String>();
				for (String str : numArr) {
					set.add(str);
				}
				if (set.size() != numArr.length){
					throw new LotteryException("开奖号码不能有重复号码！");
				}
				numLength=2;
				maxNum = 11;
			}else if(lotGroup.equals("SSC")||lotGroup.equals("3D")){
				numLength=1;
				maxNum = 9;
			}
			for(int i=0;i<numArr.length;i++){
				String num = numArr[i];
				int lotNum = Integer.parseInt(num);
				if(num.length()!=numLength||lotNum>maxNum){
					String message = "开奖号码格式错误,号码位数必须是"+numLength+"位，号码最大值不能大于"+maxNum+"!";
					throw new LotteryException(message);
				}
				
				if(lotGroup.equals("SYXW")&&lotNum<1){
					throw new LotteryException("开奖号码必须大于1！");
				}else if(lotGroup.equals("SSC")&&lotNum<0){
					throw new LotteryException("开奖号码必须大于0！");
				}
			}
		}
	}
	
	
	/**
	 * 撤销某期中奖
	 */
	@Override
	public void updateCancelAward(Map<String, Object> param) throws Exception {
		LotteryAwardRecordVO vo = (LotteryAwardRecordVO) param.get("awardKey");
		//验证该彩种该期是否已经做过开奖，如果是则要求必须先执行撤单操作。
		param.put("lotCodeKey", vo.getLotteryCode());
		param.put("issueKey", vo.getIssue());
		awardDao.cancelAward(param);
	}

	@Override
	public Map<String, Map<String,Integer>> queryHotMissingRecord(Map<String, Object> param)
			throws Exception {
		Map<String, Map<String,Integer>> returnMap =new HashMap<String, Map<String,Integer>>();
		LotteryPlaySelectVO vo = (LotteryPlaySelectVO) param.get("lotSelectKey");
		LotteryTypeVO lotteryVo = new LotteryTypeVO();
		lotteryVo.setLotteryCode(vo.getLotteryCode());
		lotteryVo.setLotteryStatus(DataDictionaryUtil.STATUS_OPEN);
		param.put("lotteryKey",lotteryVo);
		List<LotteryType> list = lotteryDao.queryLotteryList(param);
		int totalTimes =0 ;
		for(LotteryType t : list){
			totalTimes += t.getTotalTimes();
		}
		lotteryVo.setTotalTimes(3000);
		
		param.put("lotteryKey", lotteryVo);
		//查询该彩种的冷热、遗漏
		List<LotteryAwardRecord> records = awardDao.queryLimitAwardRecord(param);
		
		//防止出现数组无法截取的错误
		List<LotteryAwardRecord> hotRecords =null;
		if(records.size()==0){
			return returnMap;
		}else if(records.size()<totalTimes){
			hotRecords = records;
		}else if(list.get(0).getLotteryGroup().equals(CommonUtil.LOTTERY_GROUP_3D)
				&&totalTimes==DataDictionaryUtil.COMMON_FLAG_1){
			//如果是3d,而且不是极速3D。
			hotRecords = records;
		}else{
			hotRecords = records.subList(0, totalTimes);
		}
		
		if(list.get(0).getLotteryGroup().equals(CommonUtil.LOTTERY_GROUP_SSC)||
				list.get(0).getLotteryGroup().equals(CommonUtil.LOTTERY_GROUP_3D)){
			returnMap = hmCountSSC(vo,records,hotRecords);
		}else if(list.get(0).getLotteryGroup().equals(CommonUtil.LOTTERY_GROUP_SYXW)){
			returnMap = hmCountSYXW(vo,records,hotRecords);
		}
		
		return returnMap;
	}

	/**
	 * 十一选五冷热遗漏统计计算
	 * @param records
	 * @param hotRecords
	 * @return
	 */
	private Map<String, Map<String, Integer>> hmCountSYXW(LotteryPlaySelectVO vo,List<LotteryAwardRecord> records,
			List<LotteryAwardRecord> hotRecords) {
		Map<String, Map<String,Integer>> returnMap =new HashMap<String, Map<String,Integer>>();
		Map<String,Integer> timesMap = new HashMap<String, Integer>();
		//冷热
		for(LotteryAwardRecord record : hotRecords){
			String[] nums = record.getLotteryNumber().split(",");
			for(int i = 0 ;i<nums.length;i++){
				String key ="";
				if(vo.getType().equals("rx")||vo.getType().equals("dt")){
					key ="0:"+nums[i];
				}else{
					key =i+":"+nums[i];
				}
				 
				Integer count = timesMap.get(key);
				if(count!=null){
					timesMap.remove(key);
					timesMap.put(key, ++count);
				}else{
					timesMap.put(key, 1);
				}
			}
		}
		
		//遗漏
		Map<String,Integer> missingMap = new HashMap<String, Integer>();
		for(int j=0;j<records.size();j++){
			if(missingMap.size()==55){
				break;
			}
			LotteryAwardRecord record = records.get(j);
			String[] tempNums = record.getLotteryNumber().split(",");
			for(int i = 0 ;i<tempNums.length;i++){
				String key ="";
				if(vo.getType().equals("rx")||vo.getType().equals("dt")){
					key ="0_"+tempNums[i];
				}else{
					key =i+"_"+tempNums[i];
				}
				if(missingMap.get(key)==null){
					missingMap.put(key, j);
				}
			}
		}
		//如果是胆拖投注，因为有两行球，但冷热和遗漏都是相同的，所以复制一行数据。
		if(vo.getType().equals("dt")){
			Map<String,Integer> tempMap = new HashMap<String, Integer>();
			for(String key : timesMap.keySet()){
				String newKey = key.replace("0:", "1:");
				tempMap.put(newKey, timesMap.get(key));
			}
			timesMap.putAll(tempMap);
			
			tempMap.clear();
			for(String key : missingMap.keySet()){
				String newKey = key.replace("0_", "1_");
				tempMap.put(newKey, missingMap.get(key));
			}
			missingMap.putAll(tempMap);
		}
		returnMap.put("hMap", timesMap);
		returnMap.put("mMap", missingMap);
		return returnMap;
	}

	/**
	 * 时时彩冷热遗漏统计计算
	 * @param records
	 * @param hotRecords
	 * @return
	 */
	private Map<String, Map<String,Integer>> hmCountSSC(LotteryPlaySelectVO vo,List<LotteryAwardRecord> records,
			List<LotteryAwardRecord> hotRecords) {
		Map<String, Map<String,Integer>> returnMap =new HashMap<String, Map<String,Integer>>();
		Map<String,Integer> timesMap = new HashMap<String, Integer>();
		int numLen = records.get(0).getLotteryNumber().split(",").length-1;
		if(vo.getType().indexOf("2zs")>-1||vo.getType().indexOf("3zs")>-1
				||vo.getType().indexOf("3z3s")>-1||vo.getType().indexOf("3z6s")>-1){
			//二星直选和值,三星直选和值(组三组六)
			returnMap = this.hmCountzs(vo, records, hotRecords);
			return returnMap;
		}else if(vo.getType().indexOf("3kd")>-1){
			//三星直选跨度
			returnMap = this.hmCount3kd(vo, records, hotRecords);
			return returnMap;
		}else if(vo.getType().indexOf("dxds")!=-1){
			//大小单双
			returnMap = this.hmCountdxds(vo, records, hotRecords);
			return returnMap;
		}else if(vo.getType().equals("q3bd1")||vo.getType().equals("q3bd2")
				||vo.getType().equals("z3bd1")||vo.getType().equals("z3bd2")
				||vo.getType().equals("h3bd1")||vo.getType().equals("h3bd2")){
			//3码不定位
			returnMap = this.hmCount3bd(vo, records, hotRecords);
			return returnMap;
		}else if(vo.getType().equals("3bd1")||vo.getType().equals("3bd2")){
			//3D的3码不定位
			returnMap = this.hmCount3D3bd(vo, records, hotRecords);
			return returnMap;
		}else if(vo.getType().equals("zx120")||vo.getType().equals("zx24")||vo.getType().equals("zx6")){
			//组选120 24  6 
			returnMap = this.hmCountZX120_24_6(vo, records, hotRecords);
			return returnMap;
		}else if(vo.getType().equals("zx60")||vo.getType().equals("zx12")){
			//组选60
			returnMap = this.hmCountZX60_12(vo, records, hotRecords);
			return returnMap;
		}else if(vo.getType().equals("zx30")){
			//组选30
			returnMap = this.hmCountZX30(vo, records, hotRecords);
			return returnMap;
		}else if(vo.getType().equals("zx20")||vo.getType().equals("zx4")){
			//组选20
			returnMap = this.hmCountZX20_4(vo, records, hotRecords);
			return returnMap;
		}else if(vo.getType().equals("zx10")){
			//组选10
			returnMap = this.hmCountZX10(vo, records, hotRecords);
			return returnMap;
		}else if(vo.getType().equals("zx5")){
			//组选5
			returnMap = this.hmCountZX5(vo, records, hotRecords);
			return returnMap;
		}
		//冷热
		for(LotteryAwardRecord record : hotRecords){
			String[] nums = record.getLotteryNumber().split(",");
			for(int i = 0 ;i<nums.length;i++){
				String key =i+":"+nums[i];
				Integer count = timesMap.get(key);
				if(count!=null){
					timesMap.remove(key);
					timesMap.put(key, ++count);
				}else{
					timesMap.put(key, 1);
				}
			}
		}
		
		//遗漏
		Map<String,Integer> missingMap = new HashMap<String, Integer>();
		for(int j=0;j<records.size();j++){
			if(missingMap.size()==50){
				break;
			}
			LotteryAwardRecord record = records.get(j);
			String[] tempNums = record.getLotteryNumber().split(",");
			for(int i = 0 ;i<tempNums.length;i++){
				String key =i+"_"+tempNums[i];
				if(missingMap.get(key)==null){
					missingMap.put(key, j);
				}
			}
		}
		
		if(vo.getType().indexOf("2bh")>-1||vo.getType().indexOf("2dt")>-1){
			this.hmCount2bhdt(vo, timesMap, missingMap,numLen);
		}else if(vo.getType().indexOf("3bh")>-1||vo.getType().indexOf("3dt")>-1){
			this.hmCount3bhdt(vo, timesMap, missingMap,numLen);
		}
		
		if(missingMap.size()<19){
			for(int i=0;i<9;i++){
				if(missingMap.get((numLen-1)+"_"+i)==null){
					missingMap.put((numLen-1)+"_"+i, 3000);
				}
			}
			for(int i=9;i<19;i++){
				if(missingMap.get(numLen+"_"+i)==null){
					missingMap.put(numLen+"_"+i, 3000);
				}
			}
		}
		returnMap.put("hMap", timesMap);
		returnMap.put("mMap", missingMap);
		return returnMap;
	}

	/**
	 * 3D3码不定位
	 * @param vo
	 * @param records
	 * @param hotRecords
	 * @return
	 */
	private Map<String, Map<String, Integer>> hmCount3D3bd(
			LotteryPlaySelectVO vo, List<LotteryAwardRecord> records,
			List<LotteryAwardRecord> hotRecords) {
		Map<String, Map<String,Integer>> returnMap =new HashMap<String, Map<String,Integer>>();
		Map<String,Integer> timesMap = new HashMap<String, Integer>();
		Map<String,Integer> hotMap = new HashMap<String, Integer>();
		
		//冷热
		int numLen = records.get(0).getLotteryNumber().split(",").length-1;
		for(LotteryAwardRecord record : hotRecords){
			String lotNum = record.getLotteryNumber();
			String[] lotNums = lotNum.split(",");
			for(String num : lotNums){
				String key = numLen+":"+num;
				if(null==timesMap.get(key)){
					timesMap.put(key, 1);
				}else{
					int t = timesMap.get(key);
					timesMap.remove(key);
					timesMap.put(key, t+1);
				}
			}
		}
		
		//遗漏
		for(int i=0;i<hotRecords.size();i++){
			LotteryAwardRecord record = hotRecords.get(i);
			String lotNum = record.getLotteryNumber();
			String[] nums = lotNum.split(",");
			for(String num : nums){
				String key = numLen+"_"+num;
				if(null==hotMap.get(key)){
					hotMap.put(key, i);
				}
			}
		}
		
		returnMap.put("hMap", timesMap);
		returnMap.put("mMap", hotMap);
		return returnMap;
	}

	/**
	 * 三码不定位一码
	 * @param vo
	 * @param records
	 * @param hotRecords
	 * @return
	 */
	private Map<String, Map<String, Integer>> hmCount3bd(LotteryPlaySelectVO vo,
			List<LotteryAwardRecord> records,List<LotteryAwardRecord> hotRecords) {
		Map<String, Map<String,Integer>> returnMap =new HashMap<String, Map<String,Integer>>();
		Map<String,Integer> timesMap = new HashMap<String, Integer>();
		Map<String,Integer> hotMap = new HashMap<String, Integer>();
		
		//冷热
		int numLen = 4;
		for(LotteryAwardRecord record : hotRecords){
			String lotNum = record.getLotteryNumber();
			String[] nums = lotNum.split(",");
			int start = 0;
			int end = 3;
			if(vo.getType().indexOf("z3bd")!=-1){
				start =1;
				end = 4;
			}else if(vo.getType().indexOf("h3bd")!=-1){
				start =2;
				end = 5;
			}
			
			String[] lotNums = new String[3];
			for(int k=0,j=start;j<end;j++,k++){
				 lotNums[k] = nums[j];
			}
			
			for(String num : lotNums){
				String key = numLen+":"+num;
				if(null==timesMap.get(key)){
					timesMap.put(key, 1);
				}else{
					int t = timesMap.get(key);
					timesMap.remove(key);
					timesMap.put(key, t+1);
				}
			}
		}
		
		//遗漏
		for(int i=0;i<hotRecords.size();i++){
			LotteryAwardRecord record = hotRecords.get(i);
			String lotNum = record.getLotteryNumber();
			String[] nums = lotNum.split(",");
			int start = 0;
			int end = 3;
			if(vo.getType().indexOf("z3bd")!=-1){
				start =1;
				end = 4;
			}else if(vo.getType().indexOf("h3bd")!=-1){
				start =2;
				end = 5;
			}
			
			String[] lotNums = new String[3];
			for(int k=0,j=start;j<end;j++,k++){
				 lotNums[k] = nums[j];
			}
			for(String num : lotNums){
				String key = numLen+"_"+num;
				if(null==hotMap.get(key)){
					hotMap.put(key, i);
				}
			}
		}
		
		returnMap.put("hMap", timesMap);
		returnMap.put("mMap", hotMap);
		return returnMap;
	}

	/**
	 * 大小单双冷热遗漏
	 * @param vo
	 * @param records
	 * @param hotRecords
	 * @return
	 */
	private Map<String, Map<String, Integer>> hmCountdxds(
			LotteryPlaySelectVO vo, List<LotteryAwardRecord> records,
			List<LotteryAwardRecord> hotRecords) {
		Map<String, Map<String,Integer>> returnMap =new HashMap<String, Map<String,Integer>>();
		Map<String,Integer> timesMap = new HashMap<String, Integer>();
		//冷热
		int numLen = records.get(0).getLotteryNumber().split(",").length-1;
		int mk = numLen;
		int mk1 = numLen-1;
		//如果是前二的大小单双的话
		if(vo.getType().equals("q2dxds")){
			mk = 1;
			mk1=0;
		}
		for(LotteryAwardRecord record : hotRecords){
			String[] nums = record.getLotteryNumber().split(",");
			List<String> keylist = new ArrayList<String>(4);
			if(Integer.parseInt(nums[mk1])>4){
				keylist.add(mk1+":大");
			}else{
				keylist.add(mk1+":小");
			}
			if(Integer.parseInt(nums[mk1])%2==0){
				keylist.add(mk1+":双");
			}else{
				keylist.add(mk1+":单");
			}
			if(Integer.parseInt(nums[mk])>4){
				keylist.add(mk+":大");
			}else{
				keylist.add(mk+"::小");
			}
			if(Integer.parseInt(nums[mk])%2==0){
				keylist.add(mk+":双");
			}else{
				keylist.add(mk+":单");
			}
			for(String key : keylist){
				Integer count = timesMap.get(key);
				if(count!=null){
					timesMap.remove(key);
					timesMap.put(key, ++count);
				}else{
					timesMap.put(key, 1);
				}
			}
			
		}
		
		//遗漏
		Map<String,Integer> missingMap = new HashMap<String, Integer>();
		for(int j=0;j<records.size();j++){
			if(missingMap.size()==8){
				break;
			}
			String[] nums = records.get(j).getLotteryNumber().split(",");
			
			List<String> keylist = new ArrayList<String>(4);
			if(Integer.parseInt(nums[mk1])>4){
				keylist.add(mk1+"_大");
			}else{
				keylist.add(mk1+"_小");
			}
			if(Integer.parseInt(nums[mk1])%2==0){
				keylist.add(mk1+"_双");
			}else{
				keylist.add(mk1+"_单");
			}
			if(Integer.parseInt(nums[mk])>4){
				keylist.add(mk+"_大");
			}else{
				keylist.add(mk+"_小");
			}
			if(Integer.parseInt(nums[mk])%2==0){
				keylist.add(mk+"_双");
			}else{
				keylist.add(mk+"_单");
			}
			for(String key : keylist){
				if(missingMap.get(key)==null){
					missingMap.put(key, j);
				}
			}
		}
		returnMap.put("hMap", timesMap);
		returnMap.put("mMap", missingMap);
		return returnMap;
	}

	private Map<String, Map<String, Integer>> hmCount3kd(
			LotteryPlaySelectVO vo, List<LotteryAwardRecord> records,
			List<LotteryAwardRecord> hotRecords) {
		Map<String, Map<String,Integer>> returnMap =new HashMap<String, Map<String,Integer>>();
		Map<String,Integer> timesMap = new HashMap<String, Integer>();
		
		String[] nums1 = hotRecords.get(0).getLotteryNumber().split(",");
		int numLen = nums1.length-1;
		int mk = numLen;
		int mk1 = numLen-1;
		int mk2 = numLen-2;
		//如果是前二的大小单双的话
		if(vo.getType().equals("q3kd")){
			mk = 0;
			mk1 = 1;
			mk2 = 2;
		}else if(vo.getType().equals("z3kd")){
			mk = 1;
			mk1 = 2;
			mk2 = 3;
		}
		//冷热
		for(LotteryAwardRecord record : hotRecords){
			String[] nums = record.getLotteryNumber().split(",");
			List<Integer> num3List = new ArrayList<Integer>(3);
			num3List.add(Integer.parseInt(nums[mk]));
			num3List.add(Integer.parseInt(nums[mk1]));
			num3List.add(Integer.parseInt(nums[mk2]));
			
			Compare c1 = new Compare();
			Collections.sort(num3List, c1);
			
			String key = numLen+":"+(num3List.get(2)-num3List.get(0));
			 
			Integer count = timesMap.get(key);
			if(count!=null){
				timesMap.remove(key);
				timesMap.put(key, ++count);
			}else{
				timesMap.put(key, 1);
			}
		}
		
		//遗漏
		Map<String,Integer> missingMap = new HashMap<String, Integer>();
		for(int j=0;j<records.size();j++){
			if(missingMap.size()==10){
				break;
			}
			String[] nums = records.get(j).getLotteryNumber().split(",");
			List<Integer> num3List = new ArrayList<Integer>(3);
			num3List.add(Integer.parseInt(nums[mk]));
			num3List.add(Integer.parseInt(nums[mk1]));
			num3List.add(Integer.parseInt(nums[mk2]));
			
			Compare c1 = new Compare();
			Collections.sort(num3List, c1);
			
			String key = numLen+"_"+(num3List.get(2)-num3List.get(0));
				
			if(missingMap.get(key)==null){
				missingMap.put(key, j);
			}
		}
		if(missingMap.size()<10){
			for(int i=0;i<10;i++){
				if(missingMap.get(numLen+"_"+i)==null){
					missingMap.put(numLen+"_"+i, 3000);
				}
			}
		}
		returnMap.put("hMap", timesMap);
		returnMap.put("mMap", missingMap);
		return returnMap;
	}

	/**
	 * 二星直选和值,三星直选和值(组三组六)
	 * @param vo
	 * @param records
	 * @param hotRecords
	 * @return
	 */
	private Map<String, Map<String, Integer>> hmCountzs(
			LotteryPlaySelectVO vo, List<LotteryAwardRecord> records,
			List<LotteryAwardRecord> hotRecords) {
		Map<String, Map<String,Integer>> returnMap =new HashMap<String, Map<String,Integer>>();
		Map<String,Integer> timesMap = new HashMap<String, Integer>();
		String[] nums1 = hotRecords.get(0).getLotteryNumber().split(",");
		int numLen = nums1.length-1;
		int mk = numLen;
		int mk1 = numLen-1;
		int mk2 = numLen-2;
		//如果是前三和值的话
		if(vo.getType().equals("q3zs")||vo.getType().equals("q3z3s")||vo.getType().equals("q3z6s")){
			mk = 2;
			mk1 = 1;
			mk2 = 0;
		}else if(vo.getType().equals("z3zs")||vo.getType().equals("z3z3s")||vo.getType().equals("z3z6s")){
			//如果是中三和值的话
			mk = 3;
			mk1 = 2;
			mk2 = 1;
		}else if(vo.getType().equals("q2zs")){
			mk = 1;
			mk1 = 0;
		}
		//冷热
		for(LotteryAwardRecord record : hotRecords){
			String[] nums = record.getLotteryNumber().split(",");
			//组三和值
			if(vo.getType().indexOf("3z3s")>-1&&!nums[mk].equals(nums[mk1])
					&&!nums[mk].equals(nums[mk2])&&!nums[mk1].equals(nums[mk2])){
				continue;
			}
			//组六和值
			if(vo.getType().indexOf("3z6s")>-1&&(
					nums[mk].equals(nums[mk1])||nums[mk].equals(nums[mk2])
					||nums[mk1].equals(nums[mk2]))){
				continue;
			}
			Integer sumBall = Integer.parseInt(nums[mk])+ Integer.parseInt(nums[mk1])+Integer.parseInt(nums[mk2]);
			Integer line1 = 14;
			if(vo.getType().indexOf("2zs")>-1){
				sumBall = Integer.parseInt(nums[mk])+ Integer.parseInt(nums[mk1]);
				line1 = 9;
			}
			String key = "";
			if(sumBall<line1){
				key =(numLen-1)+":"+sumBall;
			}else{
				key = numLen +":"+sumBall;
			}
			
			Integer count = timesMap.get(key);
			if(count!=null){
				timesMap.remove(key);
				timesMap.put(key, ++count);
			}else{
				timesMap.put(key, 1);
			}
		}
		
		//遗漏
		Map<String,Integer> missingMap = new HashMap<String, Integer>();
		for(int j=0;j<records.size();j++){
			String[] nums = records.get(j).getLotteryNumber().split(",");
			//组三和值
			if(vo.getType().indexOf("3z3s")>-1&&(
					!nums[mk].equals(nums[mk1])&&!nums[mk].equals(nums[mk2])&&!nums[mk1].equals(nums[mk2]))
					//豹子也不算组三(eg:1,1,1)
					||(nums[mk].equals(nums[mk1])&&nums[mk].equals(nums[mk2]))){
				continue;
			}
			//组六和值
			if(vo.getType().indexOf("3z6s")>-1&&(
					nums[mk].equals(nums[mk1])||nums[mk].equals(nums[mk2])
					||nums[mk1].equals(nums[mk2]))){
				continue;
			}
			
			Integer sumBall = Integer.parseInt(nums[mk])+ Integer.parseInt(nums[mk1])+Integer.parseInt(nums[mk2]);
			Integer line1 = 14;
			if(vo.getType().indexOf("2zs")>-1){
				sumBall = Integer.parseInt(nums[mk])+ Integer.parseInt(nums[mk1]);
				line1 = 9;
			}
			String key = "";
			if(sumBall<line1){
				key =(numLen-1)+"_"+sumBall;
			}else{
				key =numLen+"_"+sumBall;
			}
			if(missingMap.get(key)==null){
				missingMap.put(key, j);
			}
		}
		if(missingMap.size()<28){
			for(int i=0;i<14;i++){
				if(missingMap.get((numLen-1)+"_"+i)==null){
					missingMap.put((numLen-1)+"_"+i, 3000);
				}
			}
			for(int i=14;i<28;i++){
				if(missingMap.get(numLen+"_"+i)==null){
					missingMap.put(numLen+"_"+i, 3000);
				}
			}
		}
		returnMap.put("hMap", timesMap);
		returnMap.put("mMap", missingMap);
		return returnMap;
	}

	/**
	 * 二星包号胆拖冷热遗漏
	 * @param timesMap
	 * @param missingMap
	 * @return
	 */
	private void hmCount2bhdt(LotteryPlaySelectVO vo,Map<String, Integer> timesMap,
			Map<String, Integer> missingMap,int len) {
		
		Map<String,Integer> tempMap = new HashMap<String, Integer>();
		int mk = len;
		int mk1 = len - 1;
		if(vo.getType().equals("q2dt")||vo.getType().equals("q2bh")){
			mk = 0;
			mk1 = 1;
		}
		//冷热，包号的话冷热是相加
		for(String key : timesMap.keySet()){
			String ball = key.substring(2);
			if((key.startsWith(mk+":")||key.startsWith(mk1+":"))&&tempMap.get(mk+":"+ball)==null){
				Integer bg= timesMap.get(mk+":"+ball)==null?0:timesMap.get(mk+":"+ball);
				Integer bs= timesMap.get(mk1+":"+ball)==null?0:timesMap.get(mk1+":"+ball);;
				tempMap.put(len+":"+ball, bg+bs);
				if(vo.getType().indexOf("2dt")>-1){
					tempMap.put((len - 1)+":"+ball, bg+bs);
				}
			}
			
		}
		timesMap.clear();
		timesMap.putAll(tempMap);
		
		//遗漏，包号的话取得遗漏最小值
		tempMap.clear();
		for(String key : missingMap.keySet()){
			String ball = key.substring(2);
			if(tempMap.size()==10&&vo.getType().equals("2bh")){
				break;
			}else if(tempMap.size()==20&&vo.getType().equals("2dt")){
				break;
			}
			if((key.startsWith(mk+"_")||key.startsWith(mk1+"_"))&&tempMap.get(mk+"_"+ball)==null){
				Integer bg= missingMap.get(mk+"_"+ball)==null?0:missingMap.get(mk+"_"+ball);
				Integer bs= missingMap.get(mk1+"_"+ball)==null?0:missingMap.get(mk1+"_"+ball);
				if(bg<bs){
					tempMap.put(len+"_"+ball, bg);
					if(vo.getType().indexOf("2dt")>-1){
						tempMap.put((len - 1)+"_"+ball, bg);
					}
				}else{
					tempMap.put(len+"_"+ball, bs);
					if(vo.getType().indexOf("2dt")>-1){
						tempMap.put((len - 1)+"_"+ball, bs);
					}
				}
			}
			
		}
		missingMap.clear();
		missingMap.putAll(tempMap);
	}
	/**
	 * 三星包号胆拖冷热遗漏
	 * @param timesMap
	 * @param missingMap
	 * @return
	 */
	private void hmCount3bhdt(LotteryPlaySelectVO vo,Map<String, Integer> timesMap,
			Map<String, Integer> missingMap,int len) {
		Map<String,Integer> tempMap = new HashMap<String, Integer>();
		
		int mk = len;
		int mk1 = len - 1;
		int mk2 = len - 2;
		if(vo.getType().equals("q3dt")||vo.getType().equals("q3bh")){
			mk = 0;
			mk1 = 1;
			mk2 = 2;
		}else if(vo.getType().equals("z3dt")||vo.getType().equals("z3bh")){
			mk = 1;
			mk1 = 2;
			mk2 = 3;
		}
		//冷热，包号的话冷热是相加
		for(String key : timesMap.keySet()){
			String ball = key.substring(2);
			if((key.startsWith(mk+":")||key.startsWith(mk1+":")
					||key.startsWith(mk2+":"))&&tempMap.get(len+":"+ball)==null){
				Integer bg= timesMap.get(mk+":"+ball)==null?0:timesMap.get(mk+":"+ball);
				Integer bs= timesMap.get(mk1+":"+ball)==null?0:timesMap.get(mk1+":"+ball);
				Integer bb= timesMap.get(mk2+":"+ball)==null?0:timesMap.get(mk2+":"+ball);
				tempMap.put(len+":"+ball, bg+bs+bb);
				if(vo.getType().indexOf("3dt")>-1){
					tempMap.put((len-1)+":"+ball, bg+bs+bb);
				}
			}
			
		}
		timesMap.clear();
		timesMap.putAll(tempMap);
		
		//遗漏，包号的话取得遗漏最小值,如果统计期数内没有该号码则遗漏值设置为3000
		tempMap.clear();
		for(String key : missingMap.keySet()){
			String ball = key.substring(2);
			if(tempMap.size()==10&&vo.getType().indexOf("3bh")>-1){
				break;
			}else if(tempMap.size()==20&&vo.getType().indexOf("3dt")>-1){
				break;
			}
			if((key.startsWith(mk+"_")||key.startsWith(mk1+"_")
					||key.startsWith(mk2+"_"))&&tempMap.get(mk+"_"+ball)==null){
				Integer bg= missingMap.get(mk+"_"+ball)==null?3000:missingMap.get(mk+"_"+ball);
				Integer bs= missingMap.get(mk1+"_"+ball)==null?3000:missingMap.get(mk1+"_"+ball);
				Integer bb= missingMap.get(mk2+"_"+ball)==null?3000:missingMap.get(mk2+"_"+ball);
				if(bg<=bs&&bg<=bb){
					tempMap.put(len+"_"+ball, bg);
					if(vo.getType().indexOf("3dt")>-1){
						tempMap.put((len-1)+"_"+ball, bg);
					}
				}else if(bs<=bg&&bs<=bb){
					tempMap.put(len+"_"+ball, bs);
					if(vo.getType().indexOf("3dt")>-1){
						tempMap.put((len-1)+"_"+ball, bs);
					}
				}else {
					tempMap.put(len+"_"+ball, bb);
					if(vo.getType().indexOf("3dt")>-1){
						tempMap.put((len-1)+"_"+ball, bb);
					}
				}
			}
			
		}
		missingMap.clear();
		missingMap.putAll(tempMap);
	}
	
	
	/**
	 * 组选120 24 冷热遗漏(杂牌包号)
	 * @param vo
	 * @param records
	 * @param hotRecords
	 * @return
	 */
	private Map<String, Map<String, Integer>> hmCountZX120_24_6(
			LotteryPlaySelectVO vo, List<LotteryAwardRecord> records,
			List<LotteryAwardRecord> hotRecords) {
		Map<String, Map<String,Integer>> returnMap =new HashMap<String, Map<String,Integer>>();
		Map<String,Integer> timesMap = new HashMap<String, Integer>();
		Map<String,Integer> hotMap = new HashMap<String, Integer>();
		
		//冷热
		int numLen = records.get(0).getLotteryNumber().split(",").length-1;
		for(LotteryAwardRecord record : hotRecords){
			String lotNum = record.getLotteryNumber();
			if(vo.getType().equals("zx24")){
				//四星杂牌
				lotNum = lotNum.trim().substring(2);
				//验证号码形态是否通过
				if(this.checkIsZX(lotNum,4,1,1)){
					continue;
				}
			}else if(vo.getType().equals("zx6")){
				//四星双对
				lotNum = lotNum.trim().substring(2);
				//验证号码形态是否通过
				if(this.checkIsZX(lotNum,2,2,2)){
					continue;
				}
				Map<String,Integer> dNumMap = new HashMap<String,Integer>(5);
				for(int i=0;i<lotNum.split(",").length;i++){
					if(dNumMap.get(lotNum.split(",")[i])==null){
						dNumMap.put(lotNum.split(",")[i], 1);
					}else{
						int count = dNumMap.get(lotNum.split(",")[i]);
						count++;
						dNumMap.remove(lotNum.split(",")[i]);
						dNumMap.put(lotNum.split(",")[i], count);
					}
				}
				//修改lotNum为两个重号
				lotNum = "";
				for(String key : dNumMap.keySet()){
					if(lotNum.equals("")){
						lotNum = key;
					}else{
						lotNum = lotNum+","+key;
					}
				}
			}else{
				//验证号码形态是否通过
				if(this.checkIsZX(lotNum,5,1,1)){
					continue;
				}
			}
			
			String[] lotNums = lotNum.split(",");
			for(String num : lotNums){
				String key = numLen+":"+num;
				if(null==timesMap.get(key)){
					timesMap.put(key, 1);
				}else{
					int t = timesMap.get(key);
					timesMap.remove(key);
					timesMap.put(key, t+1);
				}
			}
		}
		
		//遗漏
		for(int i=0;i<hotRecords.size();i++){
			LotteryAwardRecord record = hotRecords.get(i);
			String lotNum = record.getLotteryNumber();
			if(vo.getType().equals("zx24")){
				//四星杂牌
				lotNum = lotNum.trim().substring(2);
				//验证号码形态是否通过
				if(this.checkIsZX(lotNum,4,1,1)){
					continue;
				}
			}else if(vo.getType().equals("zx6")){
				//四星双对
				lotNum = lotNum.trim().substring(2);
				//验证号码形态是否通过
				if(this.checkIsZX(lotNum,2,2,2)){
					continue;
				}
				Map<String,Integer> dNumMap = new HashMap<String,Integer>(5);
				for(int j=0;j<lotNum.split(",").length;j++){
					if(dNumMap.get(lotNum.split(",")[j])==null){
						dNumMap.put(lotNum.split(",")[j], 1);
					}else{
						int count = dNumMap.get(lotNum.split(",")[j]);
						count++;
						dNumMap.remove(lotNum.split(",")[j]);
						dNumMap.put(lotNum.split(",")[j], count);
					}
				}
				//修改lotNum为两个重号
				lotNum = "";
				for(String key : dNumMap.keySet()){
					if(lotNum.equals("")){
						lotNum = key;
					}else{
						lotNum = lotNum+","+key;
					}
				}
			}else{
				//验证号码形态是否通过
				if(this.checkIsZX(lotNum,5,1,1)){
					continue;
				}
			}
			String[] nums = lotNum.split(",");
			for(String num : nums){
				String key = numLen+"_"+num;
				if(null==hotMap.get(key)){
					hotMap.put(key, i);
				}
			}
		}
		//补全遗漏数据
		this.resumeAllMap(hotMap, numLen);
		
		returnMap.put("hMap", timesMap);
		returnMap.put("mMap", hotMap);
		return returnMap;
	}
	
	/**
	 * 组选60冷热遗漏
	 * @param vo
	 * @param records
	 * @param hotRecords
	 * @return
	 */
	private Map<String, Map<String, Integer>> hmCountZX60_12(
			LotteryPlaySelectVO vo, List<LotteryAwardRecord> records,
			List<LotteryAwardRecord> hotRecords) {
		Map<String, Map<String,Integer>> returnMap =new HashMap<String, Map<String,Integer>>();
		Map<String,Integer> timesMap = new HashMap<String, Integer>();
		Map<String,Integer> hotMap = new HashMap<String, Integer>();
		
		//冷热
		int numLen = records.get(0).getLotteryNumber().split(",").length-1;
		for(LotteryAwardRecord record : hotRecords){
			String lotNum = record.getLotteryNumber();
			if(vo.getType().equals("zx12")){
				//四星一对两单
				lotNum = lotNum.trim().substring(2);
				//验证号码形态是否通过
				if(this.checkIsZX(lotNum,3,2,1)){
					continue;
				}
			}else{
				//验证号码形态是否通过
				if(this.checkIsZX(lotNum,4,2,1)){
					continue;
				}
			}
			String[] lotNums = lotNum.split(",");
			Map<String,Integer> dNumMap = new HashMap<String,Integer>(5);
			for(int i=0;i<lotNums.length;i++){
				if(dNumMap.get(lotNums[i])==null){
					dNumMap.put(lotNums[i], 1);
				}else{
					int count = dNumMap.get(lotNums[i]);
					count++;
					dNumMap.remove(lotNums[i]);
					dNumMap.put(lotNums[i], count);
				}
			}
			//确认2重号是哪个号码
			String dNum = "";
			for(String key : dNumMap.keySet()){
				if(dNumMap.get(key)==2){
					dNum = key;
					break;
				}
			}
			
			for(String num : lotNums){
				String key = numLen+":"+num;
				if(num.equals(dNum)){
					key = (numLen-1)+":"+num;
				}
				
				if(null==timesMap.get(key)){
					timesMap.put(key, 1);
				}else{
					int t = timesMap.get(key);
					timesMap.remove(key);
					timesMap.put(key, t+1);
				}
			}
		}
		
		//遗漏
		for(int i=0;i<hotRecords.size();i++){
			LotteryAwardRecord record = hotRecords.get(i);
			String lotNum = record.getLotteryNumber();
			if(vo.getType().equals("zx12")){
				//四星一对两单
				lotNum = lotNum.trim().substring(2);
				//验证号码形态是否通过
				if(this.checkIsZX(lotNum,3,2,1)){
					continue;
				}
			}else{
				//验证号码形态是否通过
				if(this.checkIsZX(lotNum,4,2,1)){
					continue;
				}
			}
			
			String[] nums = lotNum.split(",");
			Map<String,Integer> dNumMap = new HashMap<String,Integer>(5);
			for(int j=0;j<nums.length;j++){
				if(dNumMap.get(nums[j])==null){
					dNumMap.put(nums[j], 1);
				}else{
					int count = dNumMap.get(nums[j]);
					count++;
					dNumMap.remove(nums[j]);
					dNumMap.put(nums[j], count);
				}
			}
			//确认2重号是哪个号码
			String dNum = "";
			for(String key : dNumMap.keySet()){
				if(dNumMap.get(key)==2){
					dNum = key;
					break;
				}
			}
			for(String num : nums){
				String key = numLen+"_"+num;
				if(num.equals(dNum)){
					key = (numLen-1)+"_"+num;
				}
				if(null==hotMap.get(key)){
					hotMap.put(key, i);
				}
			}
		}
		
		//补全遗漏数据
		this.resumeAllMap(hotMap, numLen);
		
		returnMap.put("hMap", timesMap);
		returnMap.put("mMap", hotMap);
		return returnMap;
	}
	/**
	 * 补全遗漏数据
	 * @param hotMap
	 * @param numLen
	 */
	private void resumeAllMap(Map<String,Integer> hotMap,int numLen){
		for(int i=0;i<10;i++){
			String key = numLen+"_"+i;
			String key2 = (numLen-1)+"_"+i;
			if(hotMap.get(key)==null){
				hotMap.put(key, 3000);
			}
			if(hotMap.get(key2)==null){
				hotMap.put(key2, 3000);
			}
		}
	}
	/**
	 * 组选30冷热遗漏
	 * @param vo
	 * @param records
	 * @param hotRecords
	 * @return
	 */
	private Map<String, Map<String, Integer>> hmCountZX30(
			LotteryPlaySelectVO vo, List<LotteryAwardRecord> records,
			List<LotteryAwardRecord> hotRecords) {
		Map<String, Map<String,Integer>> returnMap =new HashMap<String, Map<String,Integer>>();
		Map<String,Integer> timesMap = new HashMap<String, Integer>();
		Map<String,Integer> hotMap = new HashMap<String, Integer>();
		
		//冷热
		int numLen = records.get(0).getLotteryNumber().split(",").length-1;
		for(LotteryAwardRecord record : hotRecords){
			String lotNum = record.getLotteryNumber();
			String[] lotNums = lotNum.split(",");
			//验证号码形态是否通过
			if(this.checkIsZX(lotNum,3,2,1)){
				continue;
			}
			Map<String,Integer> dNumMap = new HashMap<String,Integer>(5);
			for(int i=0;i<lotNums.length;i++){
				if(dNumMap.get(lotNums[i])==null){
					dNumMap.put(lotNums[i], 1);
				}else{
					int count = dNumMap.get(lotNums[i]);
					count++;
					dNumMap.remove(lotNums[i]);
					dNumMap.put(lotNums[i], count);
				}
			}
			//确认2重号是哪个号码
			String dNum1 = "";
			String dNum2 = "";
			for(String key : dNumMap.keySet()){
				if(dNumMap.get(key)==2&&dNum1.equals("")){
					dNum1 = key;
				}else if(dNumMap.get(key)==2&&!dNum1.equals("")){
					dNum2 = key;
					break;
				}
			}
			
			for(String num : lotNums){
				String key = numLen+":"+num;
				if(num.equals(dNum1)||num.equals(dNum2)){
					key = (numLen-1)+":"+num;
				}
				
				if(null==timesMap.get(key)){
					timesMap.put(key, 1);
				}else{
					int t = timesMap.get(key);
					timesMap.remove(key);
					timesMap.put(key, t+1);
				}
			}
		}
		
		//遗漏
		for(int i=0;i<hotRecords.size();i++){
			LotteryAwardRecord record = hotRecords.get(i);
			String lotNum = record.getLotteryNumber();
			//验证号码形态是否通过
			if(this.checkIsZX(lotNum,3,2,1)){
				continue;
			}
			
			String[] nums = lotNum.split(",");
			Map<String,Integer> dNumMap = new HashMap<String,Integer>(5);
			for(int j=0;j<nums.length;j++){
				if(dNumMap.get(nums[j])==null){
					dNumMap.put(nums[j], 1);
				}else{
					int count = dNumMap.get(nums[j]);
					count++;
					dNumMap.remove(nums[j]);
					dNumMap.put(nums[j], count);
				}
			}
			//确认2重号是哪个号码
			String dNum1 = "";
			String dNum2 = "";
			for(String key : dNumMap.keySet()){
				if(dNumMap.get(key)==2&&dNum1.equals("")){
					dNum1 = key;
				}else if(dNumMap.get(key)==2&&!dNum1.equals("")){
					dNum2 = key;
					break;
				}
			}
			for(String num : nums){
				String key = numLen+"_"+num;
				if(num.equals(dNum1)||num.equals(dNum2)){
					key = (numLen-1)+"_"+num;
				}
				if(null==hotMap.get(key)){
					hotMap.put(key, i);
				}
			}
		}
		
		//补全遗漏数据
		this.resumeAllMap(hotMap, numLen);
		
		returnMap.put("hMap", timesMap);
		returnMap.put("mMap", hotMap);
		return returnMap;
	}
	
	/**
	 * 组选20冷热遗漏
	 * @param vo
	 * @param records
	 * @param hotRecords
	 * @return
	 */
	private Map<String, Map<String, Integer>> hmCountZX20_4(
			LotteryPlaySelectVO vo, List<LotteryAwardRecord> records,
			List<LotteryAwardRecord> hotRecords) {
		Map<String, Map<String,Integer>> returnMap =new HashMap<String, Map<String,Integer>>();
		Map<String,Integer> timesMap = new HashMap<String, Integer>();
		Map<String,Integer> hotMap = new HashMap<String, Integer>();
		
		//冷热
		int numLen = records.get(0).getLotteryNumber().split(",").length-1;
		for(LotteryAwardRecord record : hotRecords){
			String lotNum = record.getLotteryNumber();
			if(vo.getType().equals("zx4")){
				//四星3条1单
				lotNum = lotNum.trim().substring(2);
				//验证号码形态是否通过
				if(this.checkIsZX(lotNum,2,3,1)){
					continue;
				}
			}else{
				//验证号码形态是否通过
				if(this.checkIsZX(lotNum,3,3,1)){
					continue;
				}
			}
			String[] lotNums = lotNum.split(",");
			Map<String,Integer> dNumMap = new HashMap<String,Integer>(5);
			for(int i=0;i<lotNums.length;i++){
				if(dNumMap.get(lotNums[i])==null){
					dNumMap.put(lotNums[i], 1);
				}else{
					int count = dNumMap.get(lotNums[i]);
					count++;
					dNumMap.remove(lotNums[i]);
					dNumMap.put(lotNums[i], count);
				}
			}
			//确认三条是哪个号码
			String dNum1 = "";
			for(String key : dNumMap.keySet()){
				if(dNumMap.get(key)==3){
					dNum1 = key;
					break;
				}
			}
			
			for(String num : lotNums){
				String key = numLen+":"+num;
				if(num.equals(dNum1)){
					key = (numLen-1)+":"+num;
				}
				
				if(null==timesMap.get(key)){
					timesMap.put(key, 1);
				}else{
					int t = timesMap.get(key);
					timesMap.remove(key);
					timesMap.put(key, t+1);
				}
			}
		}
		
		//遗漏
		for(int i=0;i<hotRecords.size();i++){
			LotteryAwardRecord record = hotRecords.get(i);
			String lotNum = record.getLotteryNumber();
			if(vo.getType().equals("zx4")){
				//四星3条1单
				lotNum = lotNum.trim().substring(2);
				//验证号码形态是否通过
				if(this.checkIsZX(lotNum,2,3,1)){
					continue;
				}
			}else{
				//验证号码形态是否通过
				if(this.checkIsZX(lotNum,3,3,1)){
					continue;
				}
			}
			String[] nums = lotNum.split(",");
			Map<String,Integer> dNumMap = new HashMap<String,Integer>(5);
			for(int j=0;j<nums.length;j++){
				if(dNumMap.get(nums[j])==null){
					dNumMap.put(nums[j], 1);
				}else{
					int count = dNumMap.get(nums[j]);
					count++;
					dNumMap.remove(nums[j]);
					dNumMap.put(nums[j], count);
				}
			}
			//确认三条是哪个号码
			String dNum1 = "";
			for(String key : dNumMap.keySet()){
				if(dNumMap.get(key)==3){
					dNum1 = key;
					break;
				}
			}
			for(String num : nums){
				String key = numLen+"_"+num;
				if(num.equals(dNum1)){
					key = (numLen-1)+"_"+num;
				}
				if(null==hotMap.get(key)){
					hotMap.put(key, i);
				}
			}
		}
		
		//补全遗漏数据
		this.resumeAllMap(hotMap, numLen);
		
		returnMap.put("hMap", timesMap);
		returnMap.put("mMap", hotMap);
		return returnMap;
	}
	
	/**
	 * 组选10冷热遗漏
	 * @param vo
	 * @param records
	 * @param hotRecords
	 * @return
	 */
	private Map<String, Map<String, Integer>> hmCountZX10(
			LotteryPlaySelectVO vo, List<LotteryAwardRecord> records,
			List<LotteryAwardRecord> hotRecords) {
		Map<String, Map<String,Integer>> returnMap =new HashMap<String, Map<String,Integer>>();
		Map<String,Integer> timesMap = new HashMap<String, Integer>();
		Map<String,Integer> hotMap = new HashMap<String, Integer>();
		
		//冷热
		int numLen = records.get(0).getLotteryNumber().split(",").length-1;
		for(LotteryAwardRecord record : hotRecords){
			String lotNum = record.getLotteryNumber();
			String[] lotNums = lotNum.split(",");
			//验证号码形态是否通过
			if(this.checkIsZX(lotNum,2,3,2)){
				continue;
			}
			Map<String,Integer> dNumMap = new HashMap<String,Integer>(5);
			for(int i=0;i<lotNums.length;i++){
				if(dNumMap.get(lotNums[i])==null){
					dNumMap.put(lotNums[i], 1);
				}else{
					int count = dNumMap.get(lotNums[i]);
					count++;
					dNumMap.remove(lotNums[i]);
					dNumMap.put(lotNums[i], count);
				}
			}
			//确认三条是哪个号码
			String dNum1 = "";
			for(String key : dNumMap.keySet()){
				if(dNumMap.get(key)==3){
					dNum1 = key;
					break;
				}
			}
			
			for(String num : lotNums){
				String key = numLen+":"+num;
				if(num.equals(dNum1)){
					key = (numLen-1)+":"+num;
				}
				
				if(null==timesMap.get(key)){
					timesMap.put(key, 1);
				}else{
					int t = timesMap.get(key);
					timesMap.remove(key);
					timesMap.put(key, t+1);
				}
			}
		}
		
		//遗漏
		for(int i=0;i<hotRecords.size();i++){
			LotteryAwardRecord record = hotRecords.get(i);
			String lotNum = record.getLotteryNumber();
			//验证号码形态是否通过
			if(this.checkIsZX(lotNum,2,3,2)){
				continue;
			}
			
			String[] nums = lotNum.split(",");
			Map<String,Integer> dNumMap = new HashMap<String,Integer>(5);
			for(int j=0;j<nums.length;j++){
				if(dNumMap.get(nums[j])==null){
					dNumMap.put(nums[j], 1);
				}else{
					int count = dNumMap.get(nums[j]);
					count++;
					dNumMap.remove(nums[j]);
					dNumMap.put(nums[j], count);
				}
			}
			//确认三条是哪个号码
			String dNum1 = "";
			for(String key : dNumMap.keySet()){
				if(dNumMap.get(key)==3){
					dNum1 = key;
					break;
				}
			}
			for(String num : nums){
				String key = numLen+"_"+num;
				if(num.equals(dNum1)){
					key = (numLen-1)+"_"+num;
				}
				if(null==hotMap.get(key)){
					hotMap.put(key, i);
				}
			}
		}
		//补全遗漏数据
		this.resumeAllMap(hotMap, numLen);
		returnMap.put("hMap", timesMap);
		returnMap.put("mMap", hotMap);
		return returnMap;
	}
	
	/**
	 * 组选5冷热遗漏
	 * @param vo
	 * @param records
	 * @param hotRecords
	 * @return
	 */
	private Map<String, Map<String, Integer>> hmCountZX5(
			LotteryPlaySelectVO vo, List<LotteryAwardRecord> records,
			List<LotteryAwardRecord> hotRecords) {
		Map<String, Map<String,Integer>> returnMap =new HashMap<String, Map<String,Integer>>();
		Map<String,Integer> timesMap = new HashMap<String, Integer>();
		Map<String,Integer> hotMap = new HashMap<String, Integer>();
		
		//冷热
		int numLen = records.get(0).getLotteryNumber().split(",").length-1;
		for(LotteryAwardRecord record : hotRecords){
			String lotNum = record.getLotteryNumber();
			String[] lotNums = lotNum.split(",");
			//验证号码形态是否通过
			if(this.checkIsZX(lotNum,2,4,1)){
				continue;
			}
			Map<String,Integer> dNumMap = new HashMap<String,Integer>(5);
			for(int i=0;i<lotNums.length;i++){
				if(dNumMap.get(lotNums[i])==null){
					dNumMap.put(lotNums[i], 1);
				}else{
					int count = dNumMap.get(lotNums[i]);
					count++;
					dNumMap.remove(lotNums[i]);
					dNumMap.put(lotNums[i], count);
				}
			}
			//确认四条是哪个号码
			String dNum1 = "";
			for(String key : dNumMap.keySet()){
				if(dNumMap.get(key)==4){
					dNum1 = key;
					break;
				}
			}
			
			for(String num : lotNums){
				String key = numLen+":"+num;
				if(num.equals(dNum1)){
					key = (numLen-1)+":"+num;
				}
				
				if(null==timesMap.get(key)){
					timesMap.put(key, 1);
				}else{
					int t = timesMap.get(key);
					timesMap.remove(key);
					timesMap.put(key, t+1);
				}
			}
		}
		
		//遗漏
		for(int i=0;i<hotRecords.size();i++){
			LotteryAwardRecord record = hotRecords.get(i);
			String lotNum = record.getLotteryNumber();
			//验证号码形态是否通过
			if(this.checkIsZX(lotNum,2,4,1)){
				continue;
			}
			
			String[] nums = lotNum.split(",");
			Map<String,Integer> dNumMap = new HashMap<String,Integer>(5);
			for(int j=0;j<nums.length;j++){
				if(dNumMap.get(nums[j])==null){
					dNumMap.put(nums[j], 1);
				}else{
					int count = dNumMap.get(nums[j]);
					count++;
					dNumMap.remove(nums[j]);
					dNumMap.put(nums[j], count);
				}
			}
			//确认四条是哪个号码
			String dNum1 = "";
			for(String key : dNumMap.keySet()){
				if(dNumMap.get(key)==4){
					dNum1 = key;
					break;
				}
			}
			for(String num : nums){
				String key = numLen+"_"+num;
				if(num.equals(dNum1)){
					key = (numLen-1)+"_"+num;
				}
				if(null==hotMap.get(key)){
					hotMap.put(key, i);
				}
			}
		}
		//补全遗漏数据
		this.resumeAllMap(hotMap, numLen);
		returnMap.put("hMap", timesMap);
		returnMap.put("mMap", hotMap);
		return returnMap;
	}
	
	
	private boolean checkIsZX(String lotNum,int mapSize,int num2,int num3){
		String[] lotNums = lotNum.split(",");
		//验证号码形态是否是组选60
		Map<String,Integer> dNumMap = new HashMap<String,Integer>(5);
		for(int i=0;i<lotNums.length;i++){
			if(dNumMap.get(lotNums[i])==null){
				dNumMap.put(lotNums[i], 1);
			}else{
				int count = dNumMap.get(lotNums[i]);
				count++;
				dNumMap.remove(lotNums[i]);
				dNumMap.put(lotNums[i], count);
			}
		}
		
		boolean checkUnPass=false;
		
		if(dNumMap.keySet().size()!=mapSize){
			return true;
		}
		
		for(String key : dNumMap.keySet()){
			if(dNumMap.get(key)!=num2 && dNumMap.get(key)!=num3){
				return true;
			}
		}
		
		return checkUnPass;
	}
	
	
	/**
	 * 内部list排序类，根据modelCode排序。
	 * @author CW-HP9
	 *
	 */
	class Compare implements Comparator<Integer>{
			@Override
			public int compare(Integer o1, Integer o2) {
				if(o1>o2){
					return 1;
				}else if(o1<o2){
					return -1;
				}
				return 0;
			}
	}
	
	@Override
	public List<LotteryAwardRecord> queryLimitAwardRecord2(
			Map<String, Object> param) throws Exception {
		return awardDao.queryLimitAwardRecord2(param);
	}

	@Override
	public Page<LotteryAwardRecordVO, LotteryAwardRecord> queryOneRecord(
			Map<String, Object> param) throws Exception {
		Page<LotteryAwardRecordVO, LotteryAwardRecord> page = awardDao.queryOneRecord(param);
		return page;
	}
	
	@Override
	public Page<LotteryAwardRecordVO, LotteryAwardRecord> queryCodesRecord(
			Map<String, Object> param) throws Exception {
		Page<LotteryAwardRecordVO, LotteryAwardRecord> page = awardDao.queryCodesRecord(param);
		return page;
	}
}
