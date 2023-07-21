package com.tn.uib.uibechanges.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import com.tn.uib.uibechanges.model.Job;
import com.tn.uib.uibechanges.scheduler.TimerInfo;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class TimerUtility {

	private TimerUtility() {
	};


	public static JobDetail buildJobDetail(final Class jobClass, final TimerInfo info, Job job, int dayId) {
		final JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put(jobClass.getSimpleName(), info);
		jobDataMap.put(job.getClass().getSimpleName(), job);

		return JobBuilder.newJob(jobClass).withIdentity("job_id_" + job.getId()+"_"+dayId).setJobData(jobDataMap).build();
	}

	public static Trigger buildTrigger(final Class jobClass, final TimerInfo info, Job job, int dayId) throws ParseException {
		Calendar now = Calendar.getInstance();
		int weekday = now.get(Calendar.DAY_OF_WEEK);
		if (weekday != dayId)
		{
		    int days = (Calendar.SATURDAY - weekday + dayId) % 7;
		    now.add(Calendar.DAY_OF_YEAR, days);
		}
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		String date = formatter.format(now.getTime());
		SimpleDateFormat parser = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		
		SimpleScheduleBuilder builder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(info.getRepeatInterval()).repeatForever();
		return TriggerBuilder.newTrigger()
				.withIdentity("job_id_" + job.getId().toString()+"_"+dayId)
				.withSchedule(builder)
				.startAt(parser.parse(date+" "+info.getStartDate()))
				.endAt(parser.parse(date+" "+info.getEndDate()))
				.build();	
	}

}
