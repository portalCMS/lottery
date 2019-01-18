package com.lottery.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lottery.bean.entity.BetRecord;
import com.lottery.bean.entity.LotteryAwardRecord;
import com.lottery.bean.entity.SourceLink;
import com.lottery.bean.entity.TaskConfig;
import com.lottery.bean.entity.TaskLog;
import com.lottery.bean.entity.vo.LotteryTypeVO;
import com.lottery.service.IBetRecordService;
import com.lottery.service.ILotteryAwardRecordService;
import com.lottery.service.ISourceLinkService;
import com.lottery.service.ITaskConfigService;
import com.lottery.service.ITaskLogService;
import com.lottery.servlet.WebSocketMessageInboundPool;
import com.xl.lottery.GrabNo.GrabNoAbstract;
import com.xl.lottery.GrabNo.LotteryVo;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.exception.LotteryExceptionLog;
import com.xl.lottery.util.ApplicationContextUtil;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;
import com.xl.lottery.util.DateUtil;
import com.xl.lottery.util.RandDomUtil;

public class LotteryTask implements Job {

	Logger log =LoggerFactory.getLogger(getClass());
	
	private IBetRecordService  betService
				=(IBetRecordService) ApplicationContextUtil.getBean("betRecordServiceImpl");
	
	private ITaskLogService  taskLogService
				=(ITaskLogService) ApplicationContextUtil.getBean("taskLogServiceImpl");
	
	private ISourceLinkService linkService
				=(ISourceLinkService) ApplicationContextUtil.getBean("sourceLinkServiceImpl");
	
	private ILotteryAwardRecordService awardRecordService 
				=(ILotteryAwardRecordService)ApplicationContextUtil.getBean("lotteryAwardRecordServiceImpl");
	
	private ITaskConfigService taskService 
				=(ITaskConfigService)ApplicationContextUtil.getBean("taskConfigServiceImpl");
	
	@Override
	public void execute(JobExecutionContext jobCtx){
		JobDataMap jdm = jobCtx.getTrigger().getJobDataMap();
		
		TaskConfig task = (TaskConfig)jdm.get("taskConfig");
		String typeCode = (String) (jdm.get("typeCode")==null?"":jdm.get("typeCode"));
		
		//新建log对象记录任务开始时间
		TaskLog taskLog = new TaskLog();
		taskLog.setTaskId(task.getId());
		taskLog.setTaskType(CommonUtil.JOB_TYPE_SERIES);
		taskLog.setTaskName(task.getLotteryName()+"-"+task.getLotterySeries());
		taskLog.setRunStatus(DataDictionaryUtil.STATUS_RUNNING);
		taskLog.setProcessStartTime(DateUtil.getStringDate());
		
		//查询彩种对应的号源配置
		List<SourceLink> links = getLinks(task);
		try {
			//判断当前task的状态是否已经失效，如果是则不再执行。
			TaskConfig nowTask = taskService.queryTaskById(task.getId());
			if(nowTask.getTaskStatus()!=DataDictionaryUtil.STATUS_OPEN){
				return;
			}
			// 根据号源配置获取开奖号码
			Map<String, Object> param = new HashMap<String, Object>();
			Map<String, Object> awardMap = this.getAwardNumber(task, links,typeCode);
			if(null!=awardMap.get("stopKey")&&awardMap.get("stopKey").equals(true)){
				//更新任务日志表。
				taskLog.setRunStatus(DataDictionaryUtil.STATUS_FAILED);
				taskLog.setErrorMessage("手工停止自动开奖！");
				taskLog.setProcessEndTime(DateUtil.getStringDate());
				param.clear();
				param.put("taskLogKey", taskLog);
				taskLogService.saveTaskLog(param);
				return;
			}
			boolean ifGetResult = (Boolean) awardMap.get("openResult");
			LotteryVo vo = (LotteryVo) awardMap.get("awardvo");
			LotteryAwardRecord record = (LotteryAwardRecord) awardMap.get("awardRecord");
			if(ifGetResult){
				boolean noCenter = (boolean) awardMap.get("noCenter");
				if(null!=record){
					//更新开奖记录表
					param.remove("awardRecordKey");
					record.setStatus(DataDictionaryUtil.STATUS_SUCCESS);
					record.setOpenTime(vo.getOpentime());
					record.setEndTime(DateUtil.getStringDate());
					record.setLotteryNumber(vo.getNum());
					if(noCenter){
						record.setOpenType(DataDictionaryUtil.AWARD_OPEN_TYPE_PAUTO);
					}
					param.put("awardRecordKey", record);
					awardRecordService.updateAwardRecord(param);
				}
				//开始开奖验奖派奖。
				param.put("lotteryCode", task.getLotteryCode());
				param.put("issueNo", task.getLotterySeries());
				param.put("lotteryNum", vo.getNum());
				param.put("betStatus", 1);
				long startLong = System.currentTimeMillis();
				List<BetRecord> wingRecords = betService.openLotteryResult(param);
				//结束开奖验奖派奖
				
				//开始派发返点金额，并且给追期订单的完成期数加1
				betService.updatePayBetRebates(param);
				
				log.info(task.getLotteryName()+"第"+task.getLotterySeries()
						+"期，处理完成耗费时间："+(System.currentTimeMillis()-startLong));
				
				//检查追号订单是否有中奖中止设置，有的话要撤销之后的追期投注记录。
				if(wingRecords!=null&&wingRecords.size()>0){
					param.put("wingRecordsKey", wingRecords);
					betService.updateBetAwardStop(param);
					//执行中奖数据标识设置
					betService.updateAwardEncrypt(param);
				}
				//中奖信息广播
//				if(wingRecords.size()>0){
//					StringBuffer userIdStrs =new StringBuffer("");
//					Map<Long,Long> userIdMap = new HashMap<Long,Long>();
//					for(BetRecord br : wingRecords){
//						if(userIdStrs.length()==0){
//							userIdStrs.append(br.getCustomerId());
//							userIdMap.put(br.getCustomerId(), br.getCustomerId());
//						}else{
//							if(null==userIdMap.get(br.getCustomerId())){
//								userIdStrs.append(",").append(br.getCustomerId());
//								userIdMap.put(br.getCustomerId(), br.getCustomerId());
//							}
//						}
//					}
//					WebSocketMessageInboundPool.sendMessage("userIdStrs:"+userIdStrs);
//				}
				
				//更新任务日志表。
				taskLog.setRunStatus(DataDictionaryUtil.STATUS_SUCCESS);
				taskLog.setProcessEndTime(DateUtil.getStringDate());
				
				if(noCenter){
//					log.info("获取自主号源,彩种["+task.getLotteryName()+"],第"+task.getLotterySeries()+"期的开奖结果失败！"
//							+ "但已以随机数工具类产生了开奖号码["+vo.getNum()+"]");
//					WebSocketMessageInboundPool.sendMessage(
//							"获取自主号源,彩种["+task.getLotteryName()+"],第"+task.getLotterySeries()+"期的开奖结果失败！"
//									+ "但已以随机数工具类产生了开奖号码["+vo.getNum()+"]");
				}else{
					log.info("成功获取"+vo.getExpectStr()+"期的开奖结果："+" " +vo.getNum() +" " +vo.getOpentime());
				}
			}else{
				String errorMessage ="error:无法获取"+task.getLotterySeries()+"期的开奖结果！";
				log.error(errorMessage);
				//更新开奖记录表
				param.remove("awardRecordKey");
				record.setStatus(DataDictionaryUtil.STATUS_FAILED);
				record.setEndTime(DateUtil.getStringDate());
				param.put("awardRecordKey", record);
				awardRecordService.updateAwardRecord(param);
				
				//更新任务日志表。
				taskLog.setRunStatus(DataDictionaryUtil.STATUS_FAILED);
				taskLog.setErrorMessage(errorMessage);
				taskLog.setProcessEndTime(DateUtil.getStringDate());
				
				WebSocketMessageInboundPool.sendMessage(
						"彩种["+task.getLotteryName()+"],第["+task.getLotterySeries()+"]期无法获取开奖结果！");
			}
			
			param.clear();
			param.put("taskLogKey", taskLog);
			taskLogService.saveTaskLog(param);
			
			
			//将已经执行的奖期任务在内存中移除掉
			Scheduler scheduler = jobCtx.getScheduler();
			JobKey jobkey = JobKey.jobKey(task.getLotterySeries(), task.getLotteryGroup()+"-"+task.getLotteryCode());
			scheduler.deleteJob(jobkey);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e,"奖期任务获取["+task.getLotteryName()+"]"+task.getLotterySeries()+"期开奖结果异常");
			WebSocketMessageInboundPool.sendMessage(
					"彩种["+task.getLotteryName()+"],第["+task.getLotterySeries()+"]期开奖异常！异常信息为："+e.getMessage());
		}	

	}
	
	/**
	 * 获取号源配置
	 * @param task
	 * @return
	 */
	private List<SourceLink> getLinks(TaskConfig task){
		Map<String, Object> param = new HashMap<String, Object>();
		LotteryTypeVO lotteryVo = new LotteryTypeVO();
		lotteryVo.setLotteryCode(task.getLotteryCode());
		lotteryVo.setLotteryGroup(task.getLotteryGroup());
		param.put("lotteryKey", lotteryVo);
		//查询彩种对应的号源设置（可能有多个号源）
		List<SourceLink> sources=null;
		try {
			sources = linkService.queryServiceByLotteryCode(param);
			if(sources==null||sources.size()==0){
				throw new LotteryException("["+task.getLotteryName()+"]未配置号源，请检查并添加配置！");
			}
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e);
		}
		return sources;
	}
	
	/**
	 * 根据号源配置获取开奖号码
	 * @param param
	 * @param list
	 * @param task
	 * @param links
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> getAwardNumber(TaskConfig task,List<SourceLink> sourcelinks,String typeCode) throws Exception{
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		
		List<String> links = new ArrayList<String>(sourcelinks.size());
		Map<String, Integer> linkLevelMap = new HashMap<String, Integer>();
		Map<String, Long> linkIdMap = new HashMap<String, Long>();
		for(SourceLink link : sourcelinks){
			linkLevelMap.put(link.getSourceLink(), link.getSourceLevel());
			linkIdMap.put(link.getSourceLink(), link.getId());
			links.add(link.getSourceLink());
		}
		boolean getResult=false;
		param.put("typeKey", "1");
		param.put("url", links);
		param.put("linkLevelMap", linkLevelMap);
		param.put("typeCode",typeCode);
		param.put("issueNo",task.getLotterySeries());
		param.put("lotTime",task.getLotTime());
		param.put("lotteryType",  task.getLotteryCode());
		param.put("lotteryGroup",  task.getLotteryGroup());
		param.put("minusFlag", false);
		Map<String, List<LotteryVo>> linkNumMap = null;
		LotteryVo vo = null;
		LotteryAwardRecord record =null;
		boolean iscwxlot=false;
		
		for(String link : links){
			if(link.startsWith("http://www.kaihaoma.com/center")){
				iscwxlot = true;
				break;
			}
		}
		for(int i=0;i<task.getCatchTimes();i++){
			if(i!=0){
				//如果状态变为手动停止则终止循环，不再继续开奖。
				Long id = record.getId();
				param.put("recordIdKey", id);
				record = awardRecordService.queryRecordById(param);
				if(record.getStatus()==DataDictionaryUtil.HAND_STATUS_READY){
					returnMap.put("stopKey", true);
					break;
				}
				Thread.sleep(3000);
			}else{
				//第一次开始抓号后，在开奖记录表中新建记录，状态为执行中，即开奖中。
				record = new LotteryAwardRecord();
				record.setIssue(task.getLotterySeries());
				record.setLotteryCode(task.getLotteryCode());
				record.setStartTime(DateUtil.getStringDate());
				record.setOpenType(DataDictionaryUtil.AWARD_OPEN_TYPE_AUTO);
				record.setLotteryName(task.getLotteryName());
				record.setLotteryGroup(task.getLotteryGroup());
				param.put("awardRecordKey", record);
				record = awardRecordService.insertAwardRecord(param);
			}
			try {
				linkNumMap = GrabNoAbstract.getGrabNO(param);
			} catch (Exception e) {
				//如果抓号有异常，没关系打印错误，继续循环。
				LotteryExceptionLog.wirteLog(e,"job 第"+i+"次获取["+task.getLotteryName()+"]"
							+task.getLotterySeries()+"期开奖结果,未获取到开奖结果。异常信息为："+e.getMessage());
				continue;
			}
				
			if(linkNumMap.size()>0){
				for(String key : linkNumMap.keySet()){
					vo = linkNumMap.get(key).get(0);
					if(vo!=null)  getResult = true;
					record.setResultLink(key);
					record.setLinkId(linkIdMap.get(key));
					break;
				}
			}
			
			if(getResult){
				break;
			}
		}
		//如果是自主彩种的话，无法获取号源开奖号码，则调用随机数工具类自动生成号码。
		if(vo==null&&iscwxlot){
			vo = new LotteryVo();
			vo.setExpectStr(task.getLotterySeries());
			if(task.getLotteryGroup().equals(CommonUtil.LOTTERY_GROUP_SYXW)){
				vo.setNum(RandDomUtil.get11X5RandomBetNum(5, 1, 11));
			}else if(task.getLotteryGroup().equals(CommonUtil.LOTTERY_GROUP_SSC)){
					vo.setNum(RandDomUtil.getSSCRandomBetNum(5, 0, 10));
			}else if(task.getLotteryGroup().equals(CommonUtil.LOTTERY_GROUP_3D)){
					vo.setNum(RandDomUtil.get3DRandomBetNum(3, 0, 10));
			}
			vo.setOpentime(DateUtil.getStringDate());
			returnMap.put("noCenter", true);
			returnMap.put("openResult", true);
		}else{
			returnMap.put("noCenter", false);
			returnMap.put("openResult", getResult);
		}
		returnMap.put("awardRecord", record);
		returnMap.put("awardvo", vo);
		/*启动将最新的开奖结果发送到前台的map中子线程
		if(getResult){
			vo.setLotteryCode(task.getLotteryCode());
			vo.setLotteryGroup(task.getLotteryGroup());
			Thread th1 = new Thread(new RefreshOpenNumResult(vo));
			th1.start();
		}
		*/
		return returnMap;
	}
	

}
