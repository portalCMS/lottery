package test;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
@Component("quartzJob")
@Scope("prototype")
public class TestLotteryJob implements Job{
	
//	@Override
//	protected void executeInternal(JobExecutionContext context)
//			throws JobExecutionException {
//		
//	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("I love u baby!**************************");
		
	}
		
	
}
