package com.tn.uib.uibechanges.job;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.tn.uib.uibechanges.model.Configuration;
import com.tn.uib.uibechanges.model.ConfigurationJob;
import com.tn.uib.uibechanges.model.Job;
import com.tn.uib.uibechanges.service.TransferService;
import com.tn.uib.uibechanges.utils.FileTransferUtility;

@Component
public class TransferJob implements org.quartz.Job{
	
	@Autowired
	TransferService transferService;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		final Job job = (Job)context.getJobDetail().getJobDataMap().get(Job.class.getSimpleName());
		
		if (job == null) {
			System.out.println("Job not found.");
			System.out.println("Scheduling aborted.");
			return;
		}
		
		Set<ConfigurationJob> configurationJobs = job.getConfigurations();
		List<Configuration> configurations = new ArrayList<>();
		
		if (configurationJobs==null || configurationJobs.size()<1) {
			System.out.println("Scheduled job with id : "+job.getId()+" with label "+job.getLibelle()+" has no configurations linked");
			System.out.println("Scheduling aborted.");
			return;
		}
		
		configurationJobs.forEach(configurationJob->{
			if (configurationJob != null && configurationJob.getConfiguration()!=null ) {
				//error here (maybe when job has no configs)
				configurations.add(configurationJob.getRank(),configurationJob.getConfiguration());
			}
		});
		
		configurations.forEach(conf->{
			FileTransferUtility fileTransferUtility = new FileTransferUtility(1);
			fileTransferUtility.setConfig(conf);
			try {
				try {
					if (fileTransferUtility.transfer().isResult()) {
						transferService.addTransfer(fileTransferUtility.getTransfer());
						System.out.println("transfer performed successfully for configuration " + conf.getLibelle() + " for job with id "+job.getId());
					}
				} catch (InterruptedException e) {
					transferService.addTransfer(fileTransferUtility.getTransfer());
					System.out.println("SSH command execution failed, cause: "+fileTransferUtility.getTransfer().getError());
				}
			} catch (JSchException e) {
				transferService.addTransfer(fileTransferUtility.getTransfer());
				System.out.println(fileTransferUtility.getTransfer().getError());
			} catch (IOException e) {
				transferService.addTransfer(fileTransferUtility.getTransfer());
				System.out.println(fileTransferUtility.getTransfer().getError());
			} catch (SftpException e) {
				transferService.addTransfer(fileTransferUtility.getTransfer());
				System.out.println(fileTransferUtility.getTransfer().getError());
			}
			transferService.addTransfer(fileTransferUtility.getTransfer());
			System.out.println(fileTransferUtility.getTransfer().getError());                                                                                                 
		});
		if (job.getState()) {
	        try {
	            Trigger trigger = context.getTrigger();
	            Scheduler scheduler = context.getScheduler();
	            scheduler.rescheduleJob(trigger.getKey(), trigger);
	        } catch (SchedulerException e) {
	            e.printStackTrace();
	        }
		}
	}

}
