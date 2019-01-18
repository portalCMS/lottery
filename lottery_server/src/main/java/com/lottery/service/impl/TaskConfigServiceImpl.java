package com.lottery.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.LotteryType;
import com.lottery.bean.entity.SpecialTaskConfig;
import com.lottery.bean.entity.TaskConfig;
import com.lottery.bean.entity.UserCardInventory;
import com.lottery.bean.entity.vo.TaskConfigVO;
import com.lottery.dao.IAdminParameterDao;
import com.lottery.dao.ILotteryTypeDao;
import com.lottery.dao.ISpecialTaskConfigDao;
import com.lottery.dao.ITaskConfigDao;
import com.lottery.dao.impl.AdminWriteLog;
import com.lottery.service.IAdminParameterService;
import com.lottery.service.ITaskConfigService;
import com.mchange.v2.beans.BeansUtils;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;
import com.xl.lottery.util.DateUtil;

@Service
public class TaskConfigServiceImpl implements ITaskConfigService{

	@Autowired
	private ITaskConfigDao taskDao;
	
	@Autowired
	private ISpecialTaskConfigDao specialConfigDao;
	
	@Autowired
	private ILotteryTypeDao lotteryDao;
	
	@Autowired
	private IAdminParameterService parameterService;
	
	/**
	 * 根据彩种代码查询出任务list
	 */
	@Override
	public List<TaskConfig> queryTaskByCode(Map<String, ?> param)
			throws Exception {
		TaskConfigVO taskVo = (TaskConfigVO) param.get("taskKey");
		
		List<TaskConfig> taskList = taskDao.queryTaskByCode(taskVo);
		return taskList;
	}

	/**
	 * 新增保存期号任务task
	 */
	@Override
	public List<TaskConfig> saveTaskConfig(Map<String, ?> param) throws Exception {
		TaskConfigVO taskVo = (TaskConfigVO) param.get("taskKey");
		String taskDate = DateUtil.getStringDateShort();
		String startTime = taskVo.getStartBetTime();
		taskVo.setStartBetTime(null);
		List<TaskConfig> taskList = taskDao.queryTaskByCode(taskVo);
		//循环以及生成的奖期任务，判断是否有跨天的奖期，如果有则设置其临时属性issueDate为跨天日期
		int today = Integer.parseInt(DateUtil.getStrDateShort());
		boolean haveNextDay=false;
		for(int i=taskList.size()-1;i>=0;i--){
			TaskConfig task = taskList.get(i);
			int st = Integer.parseInt(task.getStartBetTime().replaceAll(":", ""));
			int et = Integer.parseInt(task.getEndBetTime().replaceAll(":", ""));
			if(!haveNextDay){
				task.setIssueDate(today);
			}else{
				task.setIssueDate(today+1);
			}
			
			if(st>et){
				//即存在跨天的奖期
				haveNextDay = true;
			}
		}
		
		taskVo.setStartBetTime(startTime);
		List<TaskConfig> newTaskList = new ArrayList<TaskConfig>();
		
		LocalTime time = LocalTime.parse(taskVo.getLotTime());
		LocalTime stime = LocalTime.parse(taskVo.getStartBetTime());
		LocalTime etime = LocalTime.parse(taskVo.getEndBetTime());
		
		String[] spanArr = taskVo.getSpanTime().split(":");
		
		int munites = Integer.valueOf(spanArr[0]);
		int seconds = Integer.valueOf(spanArr[1]);
		int allseconds = munites*60+seconds;
		boolean isNextDay=false;
		int issueDate = Integer.parseInt(DateUtil.getStrDateShort());
		for(int i=0;i<taskVo.getRepeatTimes();i++){
			TaskConfig task = new TaskConfig();
			task.setTaskStatus(DataDictionaryUtil.STATUS_OPEN);
			task.setLotteryCode(taskVo.getLotteryCode());
			task.setLotteryGroup(taskVo.getLotteryGroup());
			task.setLotteryName(taskVo.getLotteryName());
			task.setCatchTimes(taskVo.getCatchTimes());
			//根据奖期类型生成奖期排序临时字段
			if(taskVo.getNewIssueType()==3){
				task.setIssueDate(today+1);
			}else if(taskVo.getNewIssueType()==1){
				task.setIssueDate(today);
			}else{
				task.setIssueDate(issueDate);
			}
			
			if(i==0){
				task.setLotTime(taskVo.getLotTime());
				task.setStartBetTime(taskVo.getStartBetTime());
				task.setEndBetTime(taskVo.getEndBetTime());
			}else{
				time= time.plusSeconds(allseconds);
				stime= stime.plusSeconds(allseconds);
				etime= etime.plusSeconds(allseconds);
				
				//如果时间变为0点，即时间跨天了
				if(time.getHourOfDay()==0&&stime.getHourOfDay()==23){
					isNextDay=true;
					haveNextDay = true;
					//跨天且为新疆时时彩
					if(taskVo.getLotteryName().trim().equals("新疆时时彩")){
						issueDate++;
					}
				}else{
					isNextDay=false;
				}
				
				
				task.setLotTime(time.toString("HH:mm:ss"));
				task.setStartBetTime(stime.toString("HH:mm:ss"));
				task.setEndBetTime(etime.toString("HH:mm:ss"));
			}
			//获取当地时间，构建任务的时间设置字符串（时分秒执行）
			String Month = taskDate.substring(5, 7);
			String day = taskDate.substring(8, 10);
			//或者是新疆时时彩的跨天的奖期，开奖日期时间也应该加1.
			String lotDate = taskDate;
			if(isNextDay||issueDate>today){
				//重庆时时彩的隔天即00:01:00开奖的时间应该是第二天的时间而非taskDate的零点
				lotDate =  DateUtil.getNextDay(taskDate, "1");
				day = lotDate.substring(8, 10);
				Month = lotDate.substring(5, 7);
			}
			
			String cronStr=  time.getSecondOfMinute()+" "+time.getMinuteOfHour()+" "+time.getHourOfDay()
					+" "+day+" "+Month+" ?";
			task.setTaskCornExpression(cronStr);
			//开奖执行日期设置
			task.setLotDate(lotDate);
			task.setLotteryJobName(CommonUtil.DEFAULT_LOTTERY_TASK_NAME);
			
			task.setTaskDate(DateUtil.getStringDateShort());
			task.setCreateTime(DateUtil.getNowDate());
			task.setCreateUser("admin");
			task.setUpdateTime(DateUtil.getNowDate());
			task.setUpdateUser("admin");
			
			newTaskList.add(task);
		}
		
		taskList.addAll(newTaskList);
		//重构期号,期号顺序是根据开奖截止时间的先后去排序。
		this.reArrangeSerial(taskList,taskVo.getLotteryCode(),taskVo.getSeriesRule(),DateUtil.getStringDateShort(),haveNextDay);
		
		for(TaskConfig task : newTaskList){
			taskDao.insert(task);
		}
		//更新首期投注的开始时间为末期投注的结束时间
		TaskConfig fristTask = taskList.get(0);
		TaskConfig lastTask = taskList.get(taskList.size()-1);
		fristTask.setStartBetTime(lastTask.getEndBetTime());
		taskDao.update(fristTask);
		
		return taskList;
	}

	
	//重新生成期号后缀
	private void reArrangeSerial(List<TaskConfig> taskList,final String lotteryCode, 
			String seriesRule, String taskDate,boolean haveNextDayTask) throws ParseException{
			final boolean isOk = haveNextDayTask;
			Collections.sort(taskList,new Comparator<TaskConfig>() {
			@Override
			public int compare(TaskConfig o1, TaskConfig o2) {
				if(!isOk){
						LocalTime time = LocalTime.parse(o1.getEndBetTime());
						LocalTime time2 = LocalTime.parse(o2.getEndBetTime());
						return time.compareTo(time2);
				}else{
					if(null!=o1.getIssueDate()&&null!=o2.getIssueDate()){
						if(o1.getIssueDate()>o2.getIssueDate()){
							return 1;
						}else if(o1.getIssueDate()<o2.getIssueDate()){
							return -1;
						}else{
							LocalTime time = LocalTime.parse(o1.getStartBetTime());
							LocalTime time2 = LocalTime.parse(o2.getStartBetTime());
							return time.compareTo(time2);
						}
					}else{
						LocalTime time = LocalTime.parse(o1.getStartBetTime());
						LocalTime time2 = LocalTime.parse(o2.getStartBetTime());
						return time.compareTo(time2);
					}
					
				}
				
			}
		});
		
		//奖期规则 ,eg:yyyymmdd[nnn]
		int numIndex = seriesRule.indexOf("[");
		String timeStr = seriesRule.substring(0, numIndex);
		String numStr = seriesRule.substring(numIndex+1, seriesRule.length()-1);
		int numLength = numStr.length();
		
		for(int i=0;i<taskList.size();i++){
			String dayStr = DateUtil.getFormatDate(timeStr, DateUtil.strToDate(taskDate));
			String serial= ""+(i+1);
			int addLen =numLength-serial.length();
			for(int j=0;j<addLen;j++){
				serial="0"+serial;
			}
			//补零
			taskList.get(i).setLotterySeries(dayStr+serial);
		}
	}
	/**
	 * 更新任务
	 */
	@Override
	public TaskConfig updateTask(Map<String, Object> param) throws Exception {
		TaskConfigVO taskVo = (TaskConfigVO) param.get("taskKey");
		TaskConfig task = taskDao.queryById(taskVo.getId());
		
		task.setCatchTimes(taskVo.getCatchTimes());
		task.setStartBetTime(taskVo.getStartBetTime());
		task.setEndBetTime(taskVo.getEndBetTime());
		task.setLotterySeries(taskVo.getLotterySeries());
		task.setLotTime(taskVo.getLotTime());
		LocalTime time = LocalTime.parse(taskVo.getLotTime());
		//获取当地时间，构建任务的时间设置字符串（时分秒执行）
		String[] ca = task.getTaskCornExpression().split(" ");
		String cronStr=  time.getSecondOfMinute()+" "+time.getMinuteOfHour()+" "+time.getHourOfDay()
				+" "+ca[3]+" "+ca[4]+" "+ca[5];
		task.setTaskCornExpression(cronStr);
		
		taskDao.update(task);
		
		return task;
	}

	/**
	 * 删除任务
	 */
	@Override
	public TaskConfig deleteTask(Map<String, Object> param) throws Exception {
		TaskConfigVO taskVo = (TaskConfigVO) param.get("taskKey");
		TaskConfig task = taskDao.queryById(taskVo.getId());
		taskDao.delete(task);
		return task;
	}

	/**
	 * 期号重新排序
	 */
	@Override
	public void refreshLotterySeries(Map<String, Object> param)
			throws Exception {
		TaskConfigVO taskVo = (TaskConfigVO) param.get("taskKey");
		List<TaskConfig> taskList = taskDao.queryTaskByCode(taskVo);
		//重构期号,期号顺序是根据开奖截止时间的先后去排序。
		this.reArrangeSerial(taskList,taskVo.getLotteryCode(),taskVo.getSeriesRule(),DateUtil.getStringDateShort(),false);
	}

	/**
	 *删除之前的旧task任务。
	 *如果当天的任务数量等于总的期数则不删除当天的奖期，如果不相同则删除掉重新生成。
	 */
	@Override
	public  void deleteTaskByJob(Map<String, Object> param) throws Exception {
		taskDao.deleteTaskByJob(param);
	}
	/**
	 * 根据彩种生成一天奖期(需要判断特例表是否配置了特例)
	 */
	@Override
	public  List<TaskConfig>  saveTaskByJob(Map<String, Object> param) throws Exception {
		List<LotteryType>  lotteryList   =(List<LotteryType>) param.get("lotteryKey");
		String taskDate = (String) param.get("taskDateKey");
		
		List<TaskConfig> taskList = new ArrayList<TaskConfig>();
		for(int j=0;j<lotteryList.size();j++){
			LotteryType lottery =lotteryList.get(j);
			
			int totalTimes = lottery.getTotalTimes();
			LocalTime time = LocalTime.parse(lottery.getStartTime());
			//末期时间
			LocalTime lastTime = LocalTime.parse(lotteryList.get(lotteryList.size()-1).getEndTime());
			
			int allseconds = lottery.getSpanTime();
			LocalTime etime =time.plusSeconds(-lottery.getBeforeLotTime());
			LocalTime stime = etime.plusSeconds(-allseconds);
			
			boolean isNextDay =false;
			boolean haveNextDay = false;
			
			int issueDate = Integer.parseInt(DateUtil.getStrDateShort());
			int today = Integer.parseInt(DateUtil.getStrDateShort());
			
			for(int i=0;i<totalTimes;i++){
				TaskConfig task = new TaskConfig();
				task.setTaskStatus(DataDictionaryUtil.STATUS_OPEN);
				task.setLotteryCode(lottery.getLotteryCode());
				task.setLotteryGroup(lottery.getLotteryGroup());
				task.setLotteryName(lottery.getLotteryName());
				task.setCatchTimes(lottery.getCatchTimes());
				task.setTaskDate(taskDate);
				
				task.setIssueDate(issueDate);
				
				if(i==0){
					task.setLotTime(time.toString("HH:mm:ss"));
					//如果是首期则satrtBetTime = 最尾期时间开始
					if(lottery.getLotteryLevel()==DataDictionaryUtil.COMMON_FLAG_1){
						task.setStartBetTime(lastTime.toString("HH:mm:ss"));
					}else{
						//确保设置了5分奖期和10分奖期，截止投注时间不一致的bug。
						task.setStartBetTime(taskList.get(taskList.size()-1).getEndBetTime());
					}
					task.setEndBetTime(etime.toString("HH:mm:ss"));
				}else{
					if(null!= lottery.getAfterLotTime()){
						time= time.plusSeconds(allseconds+lottery.getAfterLotTime());
					}else{
						time= time.plusSeconds(allseconds);
					}
					
					stime= stime.plusSeconds(allseconds);
					etime= etime.plusSeconds(allseconds);
					//如果时间变为0点，即时间跨天了
					if(time.getHourOfDay()==0&&stime.getHourOfDay()==23){
						isNextDay=true;
						haveNextDay = true;
						//跨天且为新疆时时彩
						if(lottery.getLotteryName().trim().equals("新疆时时彩")){
							issueDate++;
						}
					}else{
						isNextDay=false;
					}
					
					task.setLotTime(time.toString("HH:mm:ss"));
					task.setStartBetTime(stime.toString("HH:mm:ss"));
					task.setEndBetTime(etime.toString("HH:mm:ss"));
					
					
				}
				//获取当地时间，构建任务的时间设置字符串（时分秒执行）
				String Month = taskDate.substring(5, 7);
				String day = taskDate.substring(8, 10);
				//或者是新疆时时彩的跨天的奖期，开奖日期时间也应该加1.
				String lotDate = taskDate;
				if(isNextDay||issueDate>today){
					//重庆时时彩的隔天即00:01:00开奖的时间应该是第二天的时间而非taskDate的零点
					lotDate =  DateUtil.getNextDay(taskDate, "1");
					day = lotDate.substring(8, 10);
					Month = lotDate.substring(5, 7);
				}
				
				String cronStr=  time.getSecondOfMinute()+" "+time.getMinuteOfHour()+" "+time.getHourOfDay()
						+" "+day+" "+Month+" ?";
				task.setTaskCornExpression(cronStr);
				task.setLotDate(lotDate);
				task.setLotteryJobName(CommonUtil.DEFAULT_LOTTERY_TASK_NAME);
				
				task.setCreateTime(DateUtil.getNowDate());
				task.setCreateUser("admin");
				task.setUpdateTime(DateUtil.getNowDate());
				task.setUpdateUser("admin");
				
				taskList.add(task);
			}
			
			//重构期号,期号顺序是根据开奖截止时间的先后去排序。
			this.reArrangeSerial(taskList,lottery.getLotteryCode(),lottery.getSeriesRule(),taskDate,haveNextDay);
			//需要判断特例表是否配置了特例
			checkSpecialTask(taskList,lottery);
			for(TaskConfig task : taskList){
				taskDao.insert(task);
			}
		}
		
		return taskList;
	}

	/**
	 * 特例表判断
	 * @param taskList
	 * @param lottery
	 * @throws Exception
	 */
	private void checkSpecialTask(List<TaskConfig> taskList,LotteryType lottery) throws Exception{
		//需要判断特例表是否配置了特例
		List<TaskConfig> specialTask = new ArrayList<TaskConfig>();
		List<SpecialTaskConfig> specialTaskList = specialConfigDao.queryByLotteryCode(lottery.getLotteryCode());
		if(specialTaskList.size()>0){
			for(SpecialTaskConfig config : specialTaskList){
				//期号优先级高于时间
				if(null!=config.getStartSeries()){
					//如果是休市
					if(config.getTaskControlStatus()==DataDictionaryUtil.COMMON_FLAG_0){
						for(TaskConfig task : taskList){
							int lotterySeries = Integer.parseInt(task.getLotterySeries());
							int startSeries = Integer.parseInt(config.getStartSeries());
							if(lotterySeries>=startSeries){
								specialTask.add(task);
							}
						}
						//将休市的期号删除掉，其它期号不变
						taskList.removeAll(specialTask);
					}//如果是XXX? else if{}
				}else if(null!=config.getStartTime()){
					//如果是休市
					if(config.getTaskControlStatus()==DataDictionaryUtil.COMMON_FLAG_0){
						LocalTime time1 = LocalTime.parse(config.getStartTime());
						for(TaskConfig task : taskList){
							LocalTime time2 = LocalTime.parse(task.getStartBetTime());
							if(time1.isAfter(time2)){
								specialTask.add(task);
							}
						}
						//将休市的期号删除掉，其它期号不变
						taskList.removeAll(specialTask);
					}//如果是XXX? else if{}
				}
				
			}
		}
	}
	
	/**
	 * 批量删除任务
	 */
	@Override
	public List<TaskConfig> deleteBatchTask(Map<String, Object> param)
			throws Exception {
		List<TaskConfig> taskList = taskDao.batchDeleteTask(param);
		return taskList;
	}

	/**
	 * 刷新任务保存任务状态
	 */
	@Override
	public void updateTaskStatus(Map<String, Object> param) throws Exception {
		List<TaskConfig> taskList = (List<TaskConfig>) param.get("taskListKey");
		for(TaskConfig t:taskList){
			taskDao.update(t);
		}
	}

	@Override
	public TaskConfig queryCurrentTask(Map<String, Object> param)
			throws Exception {
		return taskDao.queryCurrentTask(param);
	}

	@Override
	public TaskConfig queryTaskById(long id) throws Exception {
		return taskDao.queryById(id);
	}

	@Override
	public List<TaskConfig> queryFurtherTask(Map<String, Object> param)
			throws Exception {
		return taskDao.queryFurtherTask(param);
	}

	/**
	 * 低频彩（福彩3d和排列三）奖期自动生成任务，默认生成370期。
	 */
	@Override
	public List<TaskConfig> saveLowTaskByJob(Map<String, Object> param)
			throws Exception {
		List<LotteryType>  lotteryList   =(List<LotteryType>) param.get("lotteryKey");
		String taskDate = (String) param.get("taskDateKey");
		
		List<TaskConfig> taskList = new ArrayList<TaskConfig>();
		
		
		//设置低频彩的奖期号
		String yy = DateUtil.getCurrYear().substring(2,4);
		
		String key = "lotRestDays"+lotteryList.get(0).getLotteryCode();
		String[] keys = new String[]{key};
		param.put("parameterName", "lotConfig");
		param.put("parameterKeys", keys);
		Map<String,String> returnMap = parameterService.getParameterList(param);
		
		String cdy = DateUtil.getCurrDayInYear(
				Integer.parseInt(returnMap==null?"0":returnMap.get(key)));
		int issueIndex = Integer.parseInt(cdy);
		int currYear = Integer.parseInt(DateUtil.getCurrYear());
		
		for(int p=0;p<100;p++){
			
			for(int j=0;j<lotteryList.size();j++){
				LotteryType lottery =lotteryList.get(j);
				
				int totalTimes = lottery.getTotalTimes();
				LocalTime time = LocalTime.parse(lottery.getStartTime());
				//末期时间
				LocalTime lastTime = LocalTime.parse(lotteryList.get(lotteryList.size()-1).getEndTime());
				
				int allseconds = lottery.getSpanTime();
				LocalTime etime =time.plusSeconds(-lottery.getBeforeLotTime());
				LocalTime stime = etime.plusSeconds(-allseconds);
				String seriesRule = lottery.getSeriesRule();
				
				for(int i=0;i<totalTimes;i++){
					TaskConfig task = new TaskConfig();
					task.setTaskStatus(DataDictionaryUtil.STATUS_OPEN);
					task.setLotteryCode(lottery.getLotteryCode());
					task.setLotteryGroup(lottery.getLotteryGroup());
					task.setLotteryName(lottery.getLotteryName());
					task.setCatchTimes(lottery.getCatchTimes());
					
					
					String issueDate =DateUtil.getNextDay(DateUtil.getStringDateShort(), p+"");
					int issueYear = Integer.parseInt(issueDate.substring(0,4));
					if(issueYear>currYear){
						issueIndex = 1;
						yy = issueDate.substring(2,4);
						currYear = issueYear;
					}
					int len =seriesRule.indexOf("]")-seriesRule.indexOf("[")-1;
					String ncdy = issueIndex+"";
					for(int k=ncdy.length();k<len;k++){
						ncdy = "0"+ncdy;
					}
					task.setLotterySeries(yy+ncdy);
					issueIndex++;
					
					task.setTaskDate(issueDate);
					if(i==0){
						task.setLotTime(time.toString("HH:mm:ss"));
						//如果是首期则satrtBetTime = 最尾期时间开始
						task.setStartBetTime(lastTime.toString("HH:mm:ss"));
						task.setEndBetTime(etime.toString("HH:mm:ss"));
					}else{
						if(null!= lottery.getAfterLotTime()){
							time= time.plusSeconds(allseconds+lottery.getAfterLotTime());
						}else{
							time= time.plusSeconds(allseconds);
						}
						
						stime= stime.plusSeconds(allseconds);
						etime= etime.plusSeconds(allseconds);
						task.setLotTime(time.toString("HH:mm:ss"));
						task.setStartBetTime(stime.toString("HH:mm:ss"));
						task.setEndBetTime(etime.toString("HH:mm:ss"));
						
					}
					//获取当地时间，构建任务的时间设置字符串（时分秒执行）
					String Month = issueDate.substring(5, 7);
					String day = issueDate.substring(8, 10);
					
					String cronStr=  time.getSecondOfMinute()+" "+time.getMinuteOfHour()+" "+time.getHourOfDay()
							+" "+day+" "+Month+" ?";
					task.setTaskCornExpression(cronStr);
					task.setLotteryJobName(CommonUtil.DEFAULT_LOTTERY_TASK_NAME);
					task.setLotDate(issueDate);
					task.setCreateTime(DateUtil.getNowDate());
					task.setCreateUser("admin");
					task.setUpdateTime(DateUtil.getNowDate());
					task.setUpdateUser("admin");
					
					taskList.add(task);
				}
				
				//需要判断特例表是否配置了特例
				checkSpecialTask(taskList,lottery);
				for(TaskConfig task : taskList){
					taskDao.insert(task);
				}
				
				//更新首期投注的开始时间为末期投注的结束时间
				TaskConfig fristTask = taskList.get(0);
				TaskConfig lastTask = taskList.get(taskList.size()-1);
				fristTask.setStartBetTime(lastTask.getEndBetTime());
				taskDao.update(fristTask);
			}
		}
		
		
		return taskList;
	}

	@Override
	public TaskConfig queryTaskByCodeAndIssue(Map<String, Object> param)
			throws Exception {
		return taskDao.queryTaskByCodeAndIssue(param);
	}

	@Override
	public List<TaskConfig> queryTaskList(Map<String, Object> param) throws Exception {
		String taskIds = (String) param.get("taskIdKeys");
		String[] tIds = taskIds.trim().split(",");
		List<TaskConfig> list = new ArrayList<TaskConfig>();
		for(int i=0;i<tIds.length;i++){
			list.add(taskDao.queryById(Long.parseLong(tIds[i])));
		}
		return list;
	}
	

	
	

	
}
