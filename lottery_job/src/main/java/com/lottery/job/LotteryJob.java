package com.lottery.job;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.quartz.CronScheduleBuilder;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.lottery.bean.entity.LotteryType;
import com.lottery.bean.entity.TaskConfig;
import com.lottery.bean.entity.TaskLog;
import com.lottery.bean.entity.vo.LotteryTypeVO;
import com.lottery.bean.entity.vo.TaskConfigVO;
import com.lottery.service.ITaskConfigService;
import com.lottery.service.ITaskLogService;
import com.xl.lottery.exception.LotteryExceptionLog;
import com.xl.lottery.util.ApplicationContextUtil;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;
import com.xl.lottery.util.DateUtil;

@Component
@DisallowConcurrentExecution
public class LotteryJob implements Job {
	Logger log =LoggerFactory.getLogger(getClass());
	private ITaskConfigService  taskService=(ITaskConfigService) ApplicationContextUtil.getBean("taskConfigServiceImpl");
	
	private ITaskLogService  taskLogService=(ITaskLogService) ApplicationContextUtil.getBean("taskLogServiceImpl");
	@Override
	public void execute(JobExecutionContext jobCtx)throws JobExecutionException {
		JobDataMap jdm = jobCtx.getTrigger().getJobDataMap();
		List<LotteryType>  lotteryList  = (List<LotteryType>)jdm.get("lotteryList");
		Boolean isDelete = false;
		if(null!=jdm.get("isDeleteKey")){
			isDelete = (Boolean) jdm.get("isDeleteKey");
		}
		//彩种记录排序
		Collections.sort(lotteryList,new Comparator<LotteryType>() {
			@Override
			public int compare(LotteryType o1, LotteryType o2) {
				int level1 = o1.getLotteryLevel();
				int level2 = o2.getLotteryLevel();
				if(level1>level2){
					return 1;
				}else if(level1<level2){
					return -1;
				}else{
					return 0;
				}
			}
		});
		
		//新建log对象记录任务开始时间
		TaskLog taskLog = new TaskLog();
		taskLog.setTaskId(lotteryList.get(0).getId());
		taskLog.setTaskType(CommonUtil.JOB_TYPE_LOTTERY);
		taskLog.setTaskName(lotteryList.get(0).getLotteryName());
		taskLog.setRunStatus(DataDictionaryUtil.STATUS_RUNNING);
		taskLog.setProcessStartTime(DateUtil.getStringDate());
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("lotteryKey", lotteryList);
		param.put("deleteKey",isDelete);
		
		List<TaskConfig> returnList =null;
		try {
			//先删除之前的旧task.
			boolean deleteSuccess=false;
			try{
				for(int j=0;j<5&&!deleteSuccess;j++){
					//低频彩需要特殊处理
					if(lotteryList.get(0).getLotteryGroup().equals(CommonUtil.LOTTERY_GROUP_3D)&&lotteryList.get(0).getTotalTimes()<2){
						 param.remove("deleteKey");
						 param.put("deleteKey",true);
					}
					taskService.deleteTaskByJob(param);
					deleteSuccess = true;
				}
			}catch(Exception e){
				LotteryExceptionLog.wirteLog(e);
				Thread.sleep(3000);
			}
			
			//低频彩需要特殊处理
			if(lotteryList.get(0).getLotteryGroup().equals(CommonUtil.LOTTERY_GROUP_3D)&&lotteryList.get(0).getTotalTimes()<2){
				param.clear();
				param.put("lotteryKey", lotteryList);
				param.put("taskDateKey",DateUtil.getStringDateShort());
				taskService.saveLowTaskByJob(param);
			}else{
				//高频彩
				if(isDelete){
					//数据库插入当天的task
					param.put("taskDateKey", DateUtil.getStringDateShort());
					taskService.saveTaskByJob(param);
				}
				
				//数据库插入第二天的task
				param.clear();
				param.put("lotteryKey", lotteryList);
				param.put("taskDateKey", DateUtil.getNextDay(DateUtil.getStringDateShort(),"1"));
				taskService.saveTaskByJob(param);
			}
			
			
			
			//重新查询数据库中该彩种对应的大于当前时间的奖期任务。
			TaskConfigVO vo = new TaskConfigVO();
			vo.setLotteryCode(lotteryList.get(0).getLotteryCode());
			vo.setStartBetTime(DateUtil.getStringDate());
			param.put("taskKey", vo);
			
			//查询大于当前期的奖期
			LotteryTypeVO lotVo = new LotteryTypeVO();
			lotVo.setLotteryCode(lotteryList.get(0).getLotteryCode());
			lotVo.setLotteryGroup(lotteryList.get(0).getLotteryGroup());
			param.put("lotteryKey", lotVo);
			
			TaskConfig curTask = taskService.queryCurrentTask(param);
			//如果无法查询到当前期，则启动所的奖期
			if(curTask==null||StringUtils.isEmpty(curTask.getLotterySeries())){
				param.put("curIssueNo", "0");
			}else{
				param.put("curIssueNo", curTask.getLotterySeries());
			}
			
			param.put("lotCode", lotteryList.get(0).getLotteryCode());
			returnList = taskService.queryFurtherTask(param);
			
			Scheduler scheduler = jobCtx.getScheduler();
			//每天生成下一批奖期任务时，将之前的那批任务移除掉。
			deleteOldTask(scheduler, lotteryList.get(0).getLotteryGroup(),lotteryList.get(0).getLotteryCode());
			
			//就绪新一批的奖期任务	
			startNewTask(scheduler, returnList);
			
			//更新lotteryJob的taskRunStatus字段为已成功执行。
			for(LotteryType lottery: lotteryList){
				lottery.setTaskRunStatus(DataDictionaryUtil.STATUS_SUCCESS);
			}
			//更新任务日志表。
			taskLog.setRunStatus(DataDictionaryUtil.STATUS_SUCCESS);
			taskLog.setProcessEndTime(DateUtil.getStringDate());
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e);
			//更新任务日志表。
			taskLog.setRunStatus(DataDictionaryUtil.STATUS_FAILED);
			taskLog.setProcessEndTime(DateUtil.getStringDate());
			taskLog.setErrorMessage(e.getMessage());
			//更新lotteryJob的taskRunStatus字段为执行失败。
			for(LotteryType lottery: lotteryList){
				lottery.setTaskRunStatus(DataDictionaryUtil.STATUS_FAILED);
			}
			
		}
		//任务日志记录
		param.clear();
		param.put("taskLogKey", taskLog);
		try {
			taskLogService.saveTaskLog(param);
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e);
		}
		
	}
	
	/**
	 * 每天生成下一批奖期任务时，将之前的那批任务移除掉。
	 * @param scheduler
	 * @param list
	 * @throws SchedulerException
	 */
	private void deleteOldTask(Scheduler scheduler,String lotteryGroup,String lotteryCode) throws SchedulerException{
		String jobGroup =  lotteryGroup+"-"+lotteryCode;
		Set<JobKey> jobs =scheduler.getJobKeys(GroupMatcher.jobGroupEquals(jobGroup));
		if(jobs.size()>0){
			List<JobKey> keyList = new ArrayList<JobKey>();
			for(JobKey job : jobs){
				log.info("删除的任务名:"+job.getGroup()+"-"+job.getName());
				keyList.add(job);
			}
			scheduler.deleteJobs(keyList);
		}
	}
	
	/**
	 * 就绪下一天的定时任务
	 * @param scheduler
	 * @param list
	 * @throws SchedulerException
	 */
	private void startNewTask(Scheduler scheduler,List<TaskConfig> list) throws SchedulerException{
		for(TaskConfig task : list){
			JobKey jobkey = JobKey.jobKey(task.getLotterySeries(), 
					task.getLotteryGroup()+"-"+task.getLotteryCode());
			JobDataMap map = new JobDataMap();
			map.put("taskConfig", task);
			//存在，就把旧的移除掉新建。
			if(scheduler.checkExists(jobkey)){
				scheduler.deleteJob(jobkey);
			}
	        JobDetail job = newJob(LotteryTask.class).withIdentity(task.getLotterySeries(), task.getLotteryGroup()+"-"+task.getLotteryCode()).build();  
	        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(task.getTaskCornExpression());
			//按新的cronExpression表达式重新构建trigger
	        Trigger	trigger1 = newTrigger()
	        		.withIdentity(task.getLotterySeries(), task.getLotteryGroup()+"-"+task.getLotteryCode())
	        		.usingJobData(map)
	        		.withSchedule(scheduleBuilder).build();
	        // 注册并进行调度  
	        scheduler.scheduleJob(job, trigger1);
		}
	}

}
