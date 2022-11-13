package com.tn.uib.uibechanges.scheduler;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tn.uib.uibechanges.utils.TimerUtility;

@Service
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
	
	public void schedule(final Class jobClass, final TimerInfo info) {
		final JobDetail jobDetail = TimerUtility.buildJobDetail(jobClass, info);
		final Trigger trigger = TimerUtility.buildTrigger(jobClass, info);
		try {
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException e) {
			System.out.println("error scheduling job");
			System.out.println(e);
		}
	}
	
	

}
