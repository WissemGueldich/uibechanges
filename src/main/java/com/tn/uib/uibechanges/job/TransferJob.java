package com.tn.uib.uibechanges.job;

import java.io.IOException;
import java.util.*;

import com.tn.uib.uibechanges.model.Email;
import com.tn.uib.uibechanges.service.EmailService;
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

	@Autowired
	private EmailService emailService;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		final Job job = (Job)context.getJobDetail().getJobDataMap().get(Job.class.getSimpleName());
		
		if (job == null) {
			System.out.println("Job not found.");
			System.out.println("Scheduling aborted.");
			return;
		}
		
		Set<ConfigurationJob> configurationJobs = job.getConfigurations();
		Map<Integer, Configuration> configurations = new HashMap();
		
		if (configurationJobs==null || configurationJobs.isEmpty()) {
			System.out.println("Scheduled job with id : "+job.getId()+" with label "+job.getLibelle()+" has no configurations linked");
			System.out.println("Scheduling aborted.");
			return;
		}

		configurationJobs.forEach(configurationJob->{
			
			if (configurationJob != null && configurationJob.getConfiguration()!=null ) {
				//potential order bug
				configurations.put(configurationJob.getRank(),configurationJob.getConfiguration());
			}
		});

		SortedSet<Integer> ranks = new TreeSet<>(configurations.keySet());
		for (Integer key : ranks) {
			FileTransferUtility fileTransferUtility = new FileTransferUtility(1);
			fileTransferUtility.getTransfer().setUser(job.getLibelle());
			fileTransferUtility.setConfig(configurations.get(key));
			try {
				try {
					if (fileTransferUtility.transfer().isResult()) {
						transferService.addTransfer(fileTransferUtility.getTransfer());
						System.out.println("transfer performed successfully for configuration " + configurations.get(key).getLibelle() + " for job with id "+job.getId());
					}
				} catch (InterruptedException e) {
					transferService.addTransfer(fileTransferUtility.getTransfer());
					System.out.println("SSH command execution failed, cause: "+fileTransferUtility.getTransfer().getError());
				}
			} catch (JSchException | IOException | SftpException e) {
				transferService.addTransfer(fileTransferUtility.getTransfer());
				System.out.println(fileTransferUtility.getTransfer().getError());
			}
            transferService.addTransfer(fileTransferUtility.getTransfer());
			if (!fileTransferUtility.getTransfer().isResult()){
				job.getMailRecipients().forEach(emailRecipient->{
					Email email = new Email();
					email.setSubject("Transfert échoué");
					email.setRecipient(emailRecipient.getEmail());
					email.setMsgBody("Ceci est un email automatique de UIB Echanges, pour vous informer que le job '"+ job.getLibelle() +"' a été interropu durant le transfert avec la configuration '" + fileTransferUtility.getTransfer().getConfiguration().getLibelle() + "'\n"+fileTransferUtility.getTransfer().getError()+".\n(" + new Date() + ").");
					emailService.sendSimpleMail(email);
				});

			}

			System.out.println(fileTransferUtility.getTransfer().getError());   
		}

        try {
            Trigger trigger = context.getTrigger();
            Scheduler scheduler = context.getScheduler();
            scheduler.rescheduleJob(trigger.getKey(), trigger);
        } catch (SchedulerException e) {
            System.out.println(e);
        }
	}

}
