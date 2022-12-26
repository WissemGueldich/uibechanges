package com.tn.uib.uibechanges.utils;

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
	
	public static JobDetail buildJobDetail(final Class jobClass, final TimerInfo info, Job job) {
		final JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put(jobClass.getSimpleName(), info);
		jobDataMap.put(job.getClass().getSimpleName(), job);

		return JobBuilder
					.newJob(jobClass)
					.withIdentity("job_id_"+job.getId())
					.setJobData(jobDataMap)
					.build();
	}
	
	public static Trigger buildTrigger(final Class jobClass, final TimerInfo info, Job job) {
		SimpleScheduleBuilder builder = SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(info.getRepeatIntervalMS());
		if (info.isRunForever()) {
			builder = builder.repeatForever();
		}else {
			builder = builder.withRepeatCount(info.getTotalFireCount()-1);
		}
		return TriggerBuilder
					.newTrigger()
					.withIdentity("job_id_"+job.getId())
					.withSchedule(builder)
					.startAt(info.getStartDate())
					.endAt(info.getEndDate())
					.build();
	}
	
	private JobDataMap getJobDataMap(Job jobData) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("idJob", jobData.getId());
        return jobDataMap;
    }
	
	
}
