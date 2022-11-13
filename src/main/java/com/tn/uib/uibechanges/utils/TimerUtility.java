package com.tn.uib.uibechanges.utils;

import java.util.Date;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import com.tn.uib.uibechanges.model.Job;
import com.tn.uib.uibechanges.scheduler.TimerInfo;

public class TimerUtility {
	
	private TimerUtility() {};
	
	public static JobDetail buildJobDetail(final Class jobClass, final TimerInfo info) {
		final JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put(jobClass.getSimpleName(), info);
		
		return JobBuilder
					.newJob(jobClass)
					.withIdentity(jobClass.getSimpleName())
					.setJobData(jobDataMap)
					.build();
	}
	
	public static Trigger buildTrigger(final Class jobClass, final TimerInfo info) {
		SimpleScheduleBuilder builder = SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(info.getRepeatIntervalMS());
		if (info.isRunForever()) {
			builder = builder.repeatForever();
		}else {
			builder = builder.withRepeatCount(info.getTotalFireCount()-1);
		}
		return TriggerBuilder
					.newTrigger()
					.withIdentity(jobClass.getSimpleName())
					.withSchedule(builder)
					.startAt(new Date(System.currentTimeMillis()+info.getInitialOffsetMS()))
					.build();
	}
	
	private JobDataMap getJobDataMap(Job jobData) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("idJob", jobData.getId());
        return jobDataMap;
    }
	
	
}
