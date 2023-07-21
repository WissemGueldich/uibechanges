package com.tn.uib.uibechanges.scheduler;

import java.text.ParseException;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tn.uib.uibechanges.model.Job;
import com.tn.uib.uibechanges.utils.TimerUtility;

@Service
@SuppressWarnings("rawtypes")
public class SchedulerService {
	
	private final Scheduler scheduler;

	@Autowired
	public SchedulerService(Scheduler scheduler) {
		this.scheduler = scheduler;
	}
	
	@PostConstruct
	public void init() {
		try {
			scheduler.start();
		} catch (SchedulerException e) {
			System.out.println("error starting scheduler");
			System.out.println(e);
		}
	}
	
	@PreDestroy
	public void preDestroy() {
		try {
			scheduler.shutdown();
		} catch (SchedulerException e) {
			System.out.println("error stopping scheduler");
			System.out.println(e);
		}
	}
	
	public void schedule( final Class jobClass, final TimerInfo info, Job job) throws ParseException {
		info.getDays().forEach(day->{
			try {
			final JobDetail jobDetail = TimerUtility.buildJobDetail(jobClass, info, job, day.getId());
			final Trigger trigger = TimerUtility.buildTrigger(jobClass, info, job, day.getId());
				scheduler.unscheduleJob(trigger.getKey());
				scheduler.scheduleJob(jobDetail, trigger);
				System.out.println("Job Triggered at :"+new Date());
				System.out.println("job id : "+job.getId());
				System.out.println("job scheduled to start at :"+job.getStartHour());
				System.out.println("Job scheduled to end at:"+ job.getEndHour());
			} catch (SchedulerException | ParseException e) {
				System.out.println("error scheduling job");
				System.out.println(e);
			}
		});

	}
	
	

}
