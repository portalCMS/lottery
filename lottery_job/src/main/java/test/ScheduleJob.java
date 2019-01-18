package test;

import org.quartz.Job;

public class ScheduleJob {

	/** ����id */
    private String jobId;
 
    /** ������� */
    private String jobName;
    
    private String jobClassName;
 
    /** ������� */
    private String jobGroup;
 
    /** ����״̬ 0���� 1���� 2ɾ��*/
    private String jobStatus;
 
    /** ��������ʱ����ʽ */
    private String cronExpression;
 
    /** �������� */
    private String desc;

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getJobClassName() {
		return jobClassName;
	}

	public void setJobClassName(String jobClassName) {
		this.jobClassName = jobClassName;
	}

	


    
    
}
