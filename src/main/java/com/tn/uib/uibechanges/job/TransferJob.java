package com.tn.uib.uibechanges.job;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import com.tn.uib.uibechanges.model.Job;

@Component
public class TransferJob implements org.quartz.Job{
	
	
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Job job = new Job();
		System.out.println(context);
		job=(Job)context.getJobDetail().getJobDataMap().get(job.getClass().getSimpleName());
		
		System.out.println("hello job is working "+new Date()+" is the time it was triggered");
		System.out.println("this is job id : "+job.getId());
		System.out.println("this is job start date :"+job.getStartHour()+" end date :"+ job.getEndHour());
	}

}
