package com.lottery.controller;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import test.TestLotteryJob;

import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.LotteryType;
import com.lottery.bean.entity.SourceLink;
import com.lottery.bean.entity.TaskConfig;
import com.lottery.bean.entity.TaskLog;
import com.lottery.bean.entity.vo.AdminUserVO;
import com.lottery.bean.entity.vo.LotteryListVO;
import com.lottery.bean.entity.vo.LotteryTypeVO;
import com.lottery.bean.entity.vo.TaskConfigVO;
import com.lottery.job.LotteryJob;
import com.lottery.job.LotteryTask;
import com.lottery.service.ILotteryTypeService;
import com.lottery.service.ISourceLinkService;
import com.lottery.service.ITaskConfigService;
import com.lottery.service.ITaskLogService;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.exception.LotteryExceptionLog;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;
import com.xl.lottery.util.DateUtil;


/**
 * job任务控制器
 */
@Controller
public class TaskController {
	@Autowired
	private ITaskConfigService taskConfigService;
	
	@Autowired
	private Scheduler lotteryScheduler;
	
	@Autowired ILotteryTypeService lotteryService;
	
	@Autowired
	private ITaskLogService taskLogService;
	
	@Autowired
	private ISourceLinkService linkService;
	
	/**
	 *奖期任务查询页面初始化
	 * @param admin
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/initTaskList")
	public ModelAndView initTaskList(LotteryTypeVO lotteryVo,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> model = this.initJob(lotteryVo, request, response);
		return new ModelAndView("lottery_tasks",model);
	}
	
	/**
	 * 定时任务初始化
	 * @param lotteryVo
	 * @param request
	 * @param response
	 * @return
	 */
	private Map<String, Object> initJob(LotteryTypeVO lotteryVo,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		lotteryVo.setLotteryLevel(DataDictionaryUtil.COMMON_FLAG_1);
		
		List<Integer> statusList = new ArrayList<Integer>();
		statusList.add(DataDictionaryUtil.STATUS_OPEN);
		statusList.add(DataDictionaryUtil.STATUS_DELETE);
		lotteryVo.setStatusList(statusList);
		
		param.put("lotteryKey", lotteryVo);
		List<LotteryType> lotteryList = null;
		Map<String, String> groupMap = new HashMap<String, String>();
		try {
			lotteryList = lotteryService.queryLotteryList(param);
			
			for(LotteryType lot : lotteryList){
				if(null==groupMap.get(lot.getLotteryCode())){
					groupMap.put(lot.getLotteryGroup(), CommonUtil.lotteryGroupMap.get(lot.getLotteryGroup()));
				}
			}
			List<LotteryTypeVO> groupList = new ArrayList<LotteryTypeVO>();
			String lotGroupCode="";
			for(String groupCode : groupMap.keySet()){
				if(lotGroupCode.equals("")){
					lotGroupCode = groupCode;
				}
				LotteryTypeVO temp = new LotteryTypeVO(); 
				temp.setLotteryGroup(groupCode);
				temp.setLotteryGroupName(groupMap.get(groupCode));
				groupList.add(temp);
			}
			model.put("groupList", groupList);
			
			List<LotteryType> removeList = new ArrayList<LotteryType>();
			for(LotteryType lot : lotteryList){
				if(!lot.getLotteryGroup().equals(lotGroupCode)){
					removeList.add(lot);
				}
			}
			lotteryList.removeAll(removeList);
			model.put("lotteryList", lotteryList);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	/**
	 * 奖期生成页面初始化
	 * @param admin
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/initJobs")
	public ModelAndView initJobs(LotteryTypeVO lotteryVo,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> model = this.initJob(lotteryVo, request, response);
		List<LotteryType> lotteryList = (List<LotteryType>) model.get("lotteryList");
		try{
			for(LotteryType lottery:lotteryList){
				TriggerKey triggerKey = TriggerKey.triggerKey(lottery.getLotteryCode(), lottery.getLotteryGroup());
				//如果存在即任务已被启动，否则未启动。
				if(lotteryScheduler.checkExists(triggerKey)){
					JobDataMap jobMap = lotteryScheduler.getTrigger(triggerKey).getJobDataMap();
					if(jobMap.get("lotteryList")!=null){
						List<LotteryType> returnJobList = (List<LotteryType>) jobMap.get("lotteryList");
						lottery.setTaskRunStatus(returnJobList.get(0).getTaskRunStatus());
					}
				}else{
					lottery.setTaskRunStatus(DataDictionaryUtil.STATUS_CLOSE);
				}
			}
		}catch(Exception e){
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("lottery_jobs",model);
	}
	
	/**
	 * 切换彩种组
	 * @param lotteryVo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/changeLotGroup")
	@ResponseBody
	public Map<String, Object> changeLotGroup(LotteryTypeVO lotteryVo,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("groupCodeKey", lotteryVo.getLotteryGroup());
		List<LotteryType> lotteryList = null;
		try {
			lotteryList = lotteryService.queryLotteryTypeByGroupCode(param);
			List<LotteryType> removeList = new ArrayList<LotteryType>();
			for(LotteryType lot : lotteryList){
				if((lot.getLotteryStatus()!=DataDictionaryUtil.STATUS_OPEN
						&&lot.getLotteryStatus()!=DataDictionaryUtil.STATUS_DELETE)
							||lot.getLotteryLevel()!=DataDictionaryUtil.COMMON_FLAG_1){
					removeList.add(lot);
				}
			}
			lotteryList.removeAll(removeList);
			
			for(LotteryType lottery:lotteryList){
				TriggerKey triggerKey = TriggerKey.triggerKey(lottery.getLotteryCode(), lottery.getLotteryGroup());
				//如果存在即任务已被启动，否则未启动。
				if(lotteryScheduler.checkExists(triggerKey)){
					JobDataMap jobMap = lotteryScheduler.getTrigger(triggerKey).getJobDataMap();
					if(jobMap.get("lotteryList")!=null){
						List<LotteryType> returnJobList = (List<LotteryType>) jobMap.get("lotteryList");
						lottery.setTaskRunStatus(returnJobList.get(0).getTaskRunStatus());
					}
				}else{
					lottery.setTaskRunStatus(DataDictionaryUtil.STATUS_CLOSE);
				}
			}
			
			model.put("lotteryList", lotteryList);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	/**
	 * 显示彩种对应的奖期任务
	 * @param lotteryVo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/showLotTasks")
	@ResponseBody
	public Map<String, Object> showLotTasks(TaskConfigVO taskVo,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("taskKey", taskVo);
		List<TaskConfig> taskList = null;
		try {
			taskList = taskConfigService.queryTaskByCode(param);
			for(TaskConfig task : taskList){
				JobKey jobkey = JobKey.jobKey(task.getLotterySeries(), task.getLotteryGroup()+"-"+task.getLotteryCode());
				//存在，即在内存中正在运行
				if(lotteryScheduler.checkExists(jobkey)){
					task.setRunning(true);
				}else{
					task.setRunning(false);
				}
			}
			
			model.put("taskList", taskList);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	/**
	 * 查询数据库根据最新的task设置，去显示tasklist。
	 * @param admin
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("refreshTasks")
	@ResponseBody
	public Map<String, Object> refreshTasks(TaskConfigVO taskVo,HttpServletRequest request, HttpServletResponse response
			,RedirectAttributes rdAttrs){
		
		Map<String,?> contextMap = RequestContextUtils.getInputFlashMap(request);
		
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		if(null!=contextMap){
			taskVo=(TaskConfigVO) contextMap.get("taskVo");
		}
		taskVo.setTaskParam("all");
		taskVo.setStartBetTime(DateUtil.getStringDate());
		param.put("taskKey", taskVo);
		List<TaskConfig> taskList = null;
		try {
			//查询大于当前期的奖期
			LotteryTypeVO lotVo = new LotteryTypeVO();
			lotVo.setLotteryCode(taskVo.getLotteryCode());
			param.put("lotteryKey", lotVo);
			
			TaskConfig curTask = taskConfigService.queryCurrentTask(param);
			//如果无法查询到当前期，则启动所有当天之后的奖期
			if(curTask==null||StringUtils.isEmpty(curTask.getLotterySeries())){
				param.put("curIssueNo", "0");
			}else{
				param.put("curIssueNo", curTask.getLotterySeries());
			}
			param.put("lotCode", taskVo.getLotteryCode());
			taskList = taskConfigService.queryFurtherTask(param);

		
			for(TaskConfig task : taskList){
				JobKey jobkey = JobKey.jobKey(task.getLotterySeries(), task.getLotteryGroup()+"-"+task.getLotteryCode());
				JobDataMap map = new JobDataMap();
				map.put("taskConfig", task);
				//存在，就把旧的移除掉新建。
				if(lotteryScheduler.checkExists(jobkey)){
					lotteryScheduler.deleteJob(jobkey);
				}
		        JobDetail job = JobBuilder.newJob(LotteryTask.class).withIdentity(task.getLotterySeries(), task.getLotteryGroup()+"-"+task.getLotteryCode()).build();  
		        
		        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(task.getTaskCornExpression());
				//按新的cronExpression表达式重新构建trigger
		        Trigger	trigger1 = newTrigger()
		        		.withIdentity(task.getLotterySeries(), task.getLotteryGroup()+"-"+task.getLotteryCode())
		        		.usingJobData(map)
		        		.withSchedule(scheduleBuilder).build();
		        // 注册并进行调度  
		        lotteryScheduler.scheduleJob(job, trigger1);
			}
			model.put("success", "奖期任务刷新成功！");
		} catch (Exception e) {
			rdAttrs.addFlashAttribute("errorMsg", "刷新奖期抓号异常！");
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	/**
	 * 添加定时任务
	 * @param admin
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("createTask")
	@ResponseBody
	public Map<String, Object> createTask(TaskConfigVO taskVo,HttpServletRequest request, HttpServletResponse response,RedirectAttributes rdAttrs) {
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		AdminUser admin = (AdminUser) request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY);
		param.put("taskKey", taskVo);
		param.put(CommonUtil.CUSTOMERUSERKEY, admin);
		try {
			taskConfigService.saveTaskConfig(param);
			model.put("success", "添加奖期任务成功！");
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	
	/**
	 * 修改定时任务
	 * @param admin
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateTask")
	@ResponseBody
	public Map<String, Object> updateTask(TaskConfigVO taskVo,HttpServletRequest request, HttpServletResponse response
			,RedirectAttributes reAttrs) {
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("taskKey", taskVo);
		TaskConfig task=null;
		try {
			task=taskConfigService.updateTask(param);
			
			Map<String, Object> autoResult = this.autoRefreshTask(task, model);
			if(null==autoResult){
				model.put("success", "修改任务成功!");
			}else{
				model.put("errorMsg", "刷新任务失败!");
			}
			JobKey jobkey = JobKey.jobKey(task.getLotterySeries(), task.getLotteryGroup()+"-"+task.getLotteryCode());
			//存在，即在内存中正在运行
			if(lotteryScheduler.checkExists(jobkey)){
				task.setRunning(true);
			}else{
				task.setRunning(false);
			}
			model.put("task",task);
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e, model);
		}
		
		return model;
	}
	
	
	/**
	 * 删除定时任务
	 * @param admin
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("deleteTask")
	@ResponseBody
	public Map<String, Object> deleteTask(TaskConfigVO taskVo,HttpServletRequest request, HttpServletResponse response
			,RedirectAttributes reAttrs) {
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("taskKey", taskVo);
		TaskConfig task = null;
		try {
			task = taskConfigService.deleteTask(param);
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e, model);
		}
		//自动移除任务
		JobKey jobKey = JobKey.jobKey(task.getLotterySeries(), task.getLotteryGroup()+"-"+task.getLotteryCode());
		try {
			lotteryScheduler.deleteJob(jobKey);
		} catch (SchedulerException e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		model.put("success", "删除任务成功!");
		return model;
	}
	
	/**
	 * 删除定时任务
	 * @param admin
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("batchDeleteTask")
	@ResponseBody
	public Map<String, Object> batchDeleteTask(TaskConfigVO taskVo,HttpServletRequest request, HttpServletResponse response
			,RedirectAttributes reAttrs) {
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		if(null!=taskVo.getIdList()&&""!=taskVo.getIdList()){
			param.put("taskKey", taskVo);
			List<TaskConfig> taskList = null;
			try {
				taskList = taskConfigService.deleteBatchTask(param);
			} catch (Exception e) {
				e.printStackTrace();
				LotteryExceptionLog.wirteLog(e, model);
			}
			//自动移除任务
			for(TaskConfig task : taskList){
				JobKey jobKey = JobKey.jobKey(task.getLotterySeries(), task.getLotteryGroup()+"-"+task.getLotteryCode());
				try {
					lotteryScheduler.deleteJob(jobKey);
				} catch (SchedulerException e) {
					reAttrs.addFlashAttribute("errorMsg", "删除任务失败!");
					LotteryExceptionLog.wirteLog(e, model);
				}
			}
			model.put("success", "批量删除任务成功!");
		}else{
			model.put("errorMsg", "没有任务被选中!");
		}
		
		return model;
	}
	
	/**
	 *自动刷新任务
	 */
	private Map<String, Object> autoRefreshTask(TaskConfig task,Map<String, Object> model){
		
		try {
			JobKey jobkey = JobKey.jobKey(task.getLotterySeries(), task.getLotteryGroup()+"-"+task.getLotteryCode());
			JobDataMap map = new JobDataMap();
			map.put("taskConfig", task);
			//存在，就把旧的移除掉新建。
			if(lotteryScheduler.checkExists(jobkey)){
				lotteryScheduler.deleteJob(jobkey);
			}
	        JobDetail job =JobBuilder.newJob(LotteryTask.class).withIdentity(task.getLotterySeries(), task.getLotteryGroup()+"-"+task.getLotteryCode()).build();  
	        
	        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(task.getTaskCornExpression());
			//按新的cronExpression表达式重新构建trigger
	        Trigger	trigger1 = newTrigger()
	        		.withIdentity(task.getLotterySeries(), task.getLotteryGroup()+"-"+task.getLotteryCode())
	        		.usingJobData(map)
	        		.withSchedule(scheduleBuilder).build();
	        // 注册并进行调度  
	        lotteryScheduler.scheduleJob(job, trigger1);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", "刷新任务失败!");
			LotteryExceptionLog.wirteLog(e, model);
			return model;
		}
		return null;
	}
	
	
	/**
	 * 单个或批量启动任务
	 * @param lotteryVo
	 * @param request
	 * @param response
	 * @param reAttrs
	 * @return
	 */
	@RequestMapping("/startJobs")
	@ResponseBody
	public Map<String, Object> startJobs(LotteryTypeVO lotteryVo,HttpServletRequest request, HttpServletResponse response
			,RedirectAttributes reAttrs){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		lotteryVo.setLotteryStatus(DataDictionaryUtil.STATUS_OPEN);
		//lotteryVo.setLotteryLevel(DataDictionaryUtil.COMMON_FLAG_1);
		try {
			if(!StringUtils.isEmpty(lotteryVo.getLotteryCodes())){
				String[] lotteryArr = lotteryVo.getLotteryCodes().split(",");
				for(int i=0;i<lotteryArr.length;i++){
					String lotCode = lotteryArr[i];
					lotteryVo.setLotteryCode(lotCode);
					createJobTask(param,lotteryVo);
				}
			}
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e,model);
		}
		model.put("success", "任务启动成功！");
		return model;
	}
	
	/**
	 * 创建产生新奖期的job任务。
	 * @param lotteryList
	 * @throws Exception 
	 */
	private void createJobTask(Map<String, Object> param,LotteryTypeVO lotteryVo) throws Exception{
		param.put("lotteryKey", lotteryVo);
		List<LotteryType> lotteryList = null;
			lotteryList = lotteryService.queryLotteryList(param);
			if(lotteryList.size()==0){
				throw new LotteryException("未查询到对应的彩种记录！");
			}
		
		//之所以传list是因为重庆时时彩有双阶段奖期。
		JobKey jobkey = JobKey.jobKey(String.valueOf(lotteryList.get(0).getLotteryCode()),
				lotteryList.get(0).getLotteryGroup());
		
		JobDataMap map = new JobDataMap();
		for(LotteryType l : lotteryList){
			l.setTaskRunStatus(DataDictionaryUtil.STATUS_READY);
		}
		map.put("lotteryList", lotteryList);
		map.put("isDeleteKey", lotteryVo.isRsvbl());
		
		//存在，就把旧的移除掉新建。
		if(lotteryScheduler.checkExists(jobkey)){
			lotteryScheduler.deleteJob(jobkey);
		}
        JobDetail job = JobBuilder.newJob(LotteryJob.class).withIdentity(
        		String.valueOf(lotteryList.get(0).getLotteryCode()),lotteryList.get(0).getLotteryGroup()).build();  
        
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(lotteryList.get(0).getTaskCornExpression());
		//按新的cronExpression表达式重新构建trigger
        Trigger	trigger1 = newTrigger()
        		.withIdentity(String.valueOf(lotteryList.get(0).getLotteryCode()),lotteryList.get(0).getLotteryGroup())
        		.usingJobData(map)
        		.withSchedule(scheduleBuilder).build();
        // 注册并进行调度  
        lotteryScheduler.scheduleJob(job, trigger1);
			
	}
	
	@RequestMapping("/stopJobs")
	@ResponseBody
	public Map<String, Object> stopJobs(LotteryTypeVO lotteryVo,HttpServletRequest request, HttpServletResponse response
			,RedirectAttributes reAttrs){
		Map<String, Object> model = new HashMap<String, Object>();
		//自动移除任务
		String[] lotteryArr = lotteryVo.getLotteryCodes().split(",");
		try {
			for(int i=0;i<lotteryArr.length;i++){
				String lotCode = lotteryArr[i];
				TriggerKey triggerKey = TriggerKey.triggerKey(lotCode, lotteryVo.getLotteryGroup());
				lotteryScheduler.unscheduleJob(triggerKey);//移除触发器  ;
			}
			model.put("success","任务停止成功");
		} catch (SchedulerException e) {
			model.put("errorMsg", "任务停止失败!");
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	@RequestMapping("/viewJobLog")
	@ResponseBody
	public Map<String,?> viewJobLog(LotteryTypeVO lotteryVo,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("lotteryKey", lotteryVo);
		List<TaskLog> logList=null;
		try {
			logList = taskLogService.queryTaskLog(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e);
		}
		model.put("logList", logList);
		return model;
	}
	
	@RequestMapping("/modifyJobCron")
	@ResponseBody
	public Map<String, Object> modifyJobCron(LotteryListVO listVo,HttpServletRequest request, HttpServletResponse response
			,RedirectAttributes reAttrs){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("listVoKey", listVo);
		//自动移除任务
		try {
			lotteryService.updateJobCron(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, reAttrs);
		}
		model.put("success","修改成功!");
		return model;
	}
	
	/**
	 * 
	 * @param listVo
	 * @param request
	 * @param response
	 * @param reAttrs
	 * @return
	 */
	@RequestMapping("/removeTasks")
	@ResponseBody
	public Map<String, Object> removeTasks(TaskConfigVO taskVo,HttpServletRequest request, HttpServletResponse response
			,RedirectAttributes reAttrs){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("taskIdKeys", taskVo.getIdList());
		try {
			List<TaskConfig> taskList = taskConfigService.queryTaskList(param);
			//移除任务
			for(TaskConfig task : taskList){
				JobKey jobKey = JobKey.jobKey(task.getLotterySeries(), task.getLotteryGroup()+"-"+task.getLotteryCode());
				try {
					lotteryScheduler.deleteJob(jobKey);
				} catch (SchedulerException e) {
					reAttrs.addFlashAttribute("errorMsg", "删除任务失败!");
					LotteryExceptionLog.wirteLog(e, model);
				}
			}
			model.put("success", "批量删除任务成功!");
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, reAttrs);
		}
		return model;
	}
}
