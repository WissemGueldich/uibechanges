package com.tn.uib.uibechanges.utils;

import java.text.ParseException;

import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import com.tn.uib.uibechanges.model.Job;
import com.tn.uib.uibechanges.scheduler.TimerInfo;

//minutesOffSet is for every hour
@SuppressWarnings(value = { "rawtypes","unchecked","unused" })
public class TimerUtility {

	private TimerUtility() {
	};
	
	private static String infoToCron(TimerInfo info) {
//		int minutes = 0 ;
//		if (info.getRepeatInterval() >= 60) {
//			minutes = info.getRepeatInterval()/60;
//			info.setRepeatInterval(info.getRepeatInterval()%60);
//		}
		
		String minutesOffSet="*";
		if (Integer.parseInt(info.getStartDate().split(":")[1])!=0) {
			minutesOffSet = info.getStartDate().split(":")[1]+"/1";
		}
		StringBuilder cronExpressionBuilder = (new StringBuilder()).append(
				"0/" + info.getRepeatInterval() + " " + minutesOffSet + " " + info.getStartDate().split(":")[0] + "-" + info.getEndDate().split(":")[0] + " ? * ");
		if (info.getDays().isEmpty()) {
			cronExpressionBuilder.append("*");
		} else {
			info.getDays().forEach(day -> {
				cronExpressionBuilder.append(day.getDay() + ",");
			});
			cronExpressionBuilder.deleteCharAt(cronExpressionBuilder.length() - 1);
		}
		String cronExpression = cronExpressionBuilder.append(" *").toString();
		return cronExpression;
	}

	public static JobDetail buildJobDetail(final Class jobClass, final TimerInfo info, Job job) {
		final JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put(jobClass.getSimpleName(), info);
		jobDataMap.put(job.getClass().getSimpleName(), job);

		return JobBuilder.newJob(jobClass).withIdentity("job_id_" + job.getId()).setJobData(jobDataMap).build();
	}

	public static Trigger buildTrigger(final Class jobClass, final TimerInfo info, Job job) throws ParseException {
		System.out.println(infoToCron(info));
		return TriggerBuilder.newTrigger().withIdentity("job_id_" + job.getId())
				.withSchedule(CronScheduleBuilder.cronSchedule(new CronExpression(infoToCron(info)))).startNow().build();
	}

	private JobDataMap getJobDataMap(Job jobData) {
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put("idJob", jobData.getId());
		return jobDataMap;
	}

}
