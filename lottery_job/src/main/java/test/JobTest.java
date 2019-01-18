package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.joda.time.LocalTime;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.quartz.JobDetailBean;
import org.springframework.stereotype.Component;

import com.lottery.bean.entity.LotteryAwardRecord;
import com.xl.lottery.util.DataDictionaryUtil;
import com.xl.lottery.util.DateUtil;

public class JobTest {
	
	
	public void run() throws SchedulerException{
		LocalTime time = LocalTime.parse("09:10");
		int allseconds = 600;
		int befseconds = 30;
		LocalTime etime =time.plusSeconds(-befseconds);
		LocalTime stime = etime.plusSeconds(-allseconds);
		for(int i=0;i<84;i++){
			
			if(i!=0){
				time= time.plusSeconds(allseconds);
				stime= stime.plusSeconds(allseconds);
				etime= etime.plusSeconds(allseconds);
				//如果时间变为0点，即时间跨天了
				if(time.getHourOfDay()==0){
					System.out.println("------------跨天时间------------");
				}
			}
			String cronStr=  time.getSecondOfMinute()+" "+time.getMinuteOfHour()+" "+time.getHourOfDay()+" "+"* * ?";
			System.out.println("开奖时间表达式:"+cronStr);
			System.out.println("开始投注时间表达式:"+stime.toString("HH:mm:ss"));
			System.out.println("停止投注时间表达式:"+etime.toString("HH:mm:ss"));
		}
		
	}
	public static void main(String[] args) throws InterruptedException {
			StringBuffer lotNum=new StringBuffer("");
			while(true){
				String[] lots = lotNum.toString().split(",");
				if(lots.length==5){
					break;
				}
			 	int max=11;
		        int min=1;
		        Random random = new Random();

		        Integer s = random.nextInt(max)%(max-min+1) + min;
		        System.out.println(s);
		        if(lotNum.toString().equals("")){
		        	if(s<10){
		        		lotNum.append("0"+s);
		        	}else{
		        		lotNum.append(s);
		        	}
		        }else{
		        	boolean isContinue =false;
		        	
		        	for(int k=0;k<lots.length;k++){
		        		if(Integer.parseInt(lots[k])==s){
		        			isContinue = true;
		        			break;
		        		}
		        	}
		        	if(isContinue){
		        		continue;
		        	}
		        	if(s<10){
		        		lotNum.append(",0"+s);
		        	}else{
		        		lotNum.append(","+s);
		        	}
		        }
			}
	       System.out.println(lotNum.toString());
		
    }
	
	
	/**
	 * 内部list排序类，根据modelCode排序。
	 * @author CW-HP9
	 *
	 */
	static class Compare3 implements Comparator<LotteryAwardRecord>{
			@Override
			public int compare(LotteryAwardRecord o1, LotteryAwardRecord o2) {
				int code1 = Integer.parseInt(o1.getIssue());
				int code2 = Integer.parseInt(o1.getIssue());
				if(code1>code2){
					return -1;
				}else if(code1<code2){
					return 1;
				}
				return 0;
			}
	}
}
