package test;


import static org.quartz.JobBuilder.newJob;  
import static org.quartz.TriggerBuilder.newTrigger;  

import java.util.Date;  

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;  
import org.quartz.Scheduler;  
import org.quartz.SchedulerFactory;  
import org.quartz.Trigger;  
import org.quartz.impl.StdSchedulerFactory;  
import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;  

public class SimpleExample {  
  
    private static Logger log = LoggerFactory.getLogger(SimpleExample.class);  
  
    public void run() throws Exception {  
        // 通过SchedulerFactory获取一个调度器实例  
        SchedulerFactory sf = new StdSchedulerFactory();      
        Scheduler sched = sf.getScheduler();  
        
        //模拟数据库查询数据
        ScheduleJob taskConfig = new ScheduleJob();
        taskConfig.setJobId("10001" );
        taskConfig.setJobName("data_import");
        taskConfig.setJobGroup("dataWork");
        taskConfig.setJobClassName("com.test.SimpleJob");
        taskConfig.setJobStatus("1");
        taskConfig.setCronExpression("0/5 * * * * ?");
        taskConfig.setDesc("数据导入任务");
        
        Class<? extends Job> jobClass = (Class<? extends Job>) Class.forName(taskConfig.getJobClassName());
        // 通过过JobDetail封装HelloJob，同时指定Job在Scheduler中所属组及名称，这里，组名为group1，而名称为job1。  
        JobDetail job =JobBuilder.newJob(TestLotteryJob.class).withIdentity(taskConfig.getJobName(),taskConfig.getJobGroup()).build();  
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(taskConfig.getCronExpression());
	 
		//按新的cronExpression表达式重新构建trigger
        Trigger	trigger = newTrigger().withIdentity(taskConfig.getJobName(),taskConfig.getJobGroup()).withSchedule(scheduleBuilder).build();
        // 注册并进行调度  
        Date date = sched.scheduleJob(job, trigger);  
        System.out.println("构建job的日期："+date);
        // 启动调度器  
        sched.start();  
        
        Scheduler sched1 = sf.getScheduler(); 
        taskConfig.setCronExpression("0/9 * * * * ?");
        // 通过过JobDetail封装HelloJob，同时指定Job在Scheduler中所属组及名称，这里，组名为group1，而名称为job1。  
        JobDetail job1 = newJob(jobClass).withIdentity(taskConfig.getJobName()+"1",taskConfig.getJobGroup()).build();  
        CronScheduleBuilder scheduleBuilder1 = CronScheduleBuilder.cronSchedule(taskConfig.getCronExpression()); 
        
      //按新的cronExpression表达式重新构建trigger
        Trigger	trigger1 = newTrigger().withIdentity(taskConfig.getJobName()+"1",taskConfig.getJobGroup()).withSchedule(scheduleBuilder1).build();
        // 注册并进行调度  
        sched1.scheduleJob(job1, trigger1); 
        // 启动调度器  
        sched1.start();  
        
    }  
  
    public static void main(String[] args) throws Exception {  
        SimpleExample example = new SimpleExample();  
        example.run();  
    }  
}