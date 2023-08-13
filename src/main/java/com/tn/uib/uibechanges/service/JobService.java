package com.tn.uib.uibechanges.service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import com.tn.uib.uibechanges.model.*;
import com.tn.uib.uibechanges.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tn.uib.uibechanges.controller.JobConfigs;
import com.tn.uib.uibechanges.job.TransferJob;
import com.tn.uib.uibechanges.scheduler.SchedulerService;
import com.tn.uib.uibechanges.scheduler.TimerInfo;

@Service
@Transactional
public class JobService {
	
	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	private JobExecutionRepository jobExecutionRepository;
	
	@Autowired
	private ConfigurationJobRepository configurationJobRepository;
	
	@Autowired
	private DayRepository dayRepository;
	
	@Autowired
	private ConfigurationRepository configurationRepository;
	
	@Autowired
	private SchedulerService schedulerService;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> addJob(JobConfigs jobConfigs) {
    	
    	Set<Configuration> configurations = new HashSet<>();
    	
    	jobConfigs.getConfigurations().forEach(id->{
    		configurations.add(configurationRepository.findById(id).get());
    	});
    	
    	Job job = jobConfigs.getJob();

    	if (job.getConfigurations() == null) {
            job.setConfigurations(new HashSet<ConfigurationJob>());
        }
		Set<Day> days = new HashSet<>();
		if(job.getDays() != null) {
			job.getDays().forEach(day -> {days.add(dayRepository.findById(day.getId()).get());});
		}
        job.setDays(days);
        Set<User> mailRecipients = new HashSet<User>();
        if(job.getMailRecipients() != null) {
            job.getMailRecipients().forEach(recipient -> {mailRecipients.add(userRepository.findById(recipient.getId()));});
        }
        job.setMailRecipients(mailRecipients);
        job = jobRepository.save(job);
        int i = 1;
        for (Configuration conf : configurations) {
            ConfigurationJob confJob = new ConfigurationJob();
            confJob.setConfiguration(conf);
            confJob.setJob(job);
            confJob.setRank(i);
            confJob.setConfigurationJobPK(new ConfigurationJobPK(job.getId(), conf.getId()));
            configurationJobRepository.save(confJob);
            i++;
        }
        return new ResponseEntity<>(jobRepository.save(job), HttpStatus.CREATED);
    }
    
    public ResponseEntity<?> addJob(Job job) {

        if (job.getConfigurations() == null) {
            job.setConfigurations(new HashSet<ConfigurationJob>());
        }
		Set<Day> days = new HashSet<>();
		if(job.getDays() != null) {
			job.getDays().forEach(day -> {days.add(dayRepository.findById(day.getId()).get());});
		}
		job.setDays(days);

        return new ResponseEntity<>(jobRepository.save(job), HttpStatus.CREATED);
    }
 
    public ResponseEntity<?> getJob(Integer id) {
    	JobConfigs jobConfigs = new JobConfigs();
    	Job job = jobRepository.findById(id).get();
    	if (job!=null) {
    		job.setState(this.isRunning(id).isScheduled());
        	jobConfigs.setJob(job);
        	Map<String, String> configurationsMap = new HashMap<>();
        	if (job.getConfigurations()!=null && job.getConfigurations().size()>0) {
        		job.getConfigurations().forEach(confJob->{
            		configurationsMap.put(confJob.getConfiguration().getId().toString(), confJob.getConfiguration().getLibelle());
            	});
    		}
        	jobConfigs.setConfigurationsMap(configurationsMap);
    	}
    	    	
        return new ResponseEntity<>(jobConfigs,HttpStatus.OK);
    }

    public ResponseEntity<?> updateJob(JobConfigs jobConfigs) {

    	Set<Configuration> configurations = new HashSet<>();
    	
    	jobConfigs.getConfigurations().forEach(id->{
    		configurations.add(configurationRepository.findById(id).get());
    	});
    	
    	Job job = jobConfigs.getJob();
    	
        Job oldJob = jobRepository.findById(job.getId()).get();
        oldJob.setEndHour(job.getEndHour());
        oldJob.setFrequency(job.getFrequency());
        oldJob.setLibelle(job.getLibelle());
        oldJob.setStartHour(job.getStartHour());
        oldJob.setState(job.getState());
        for (ConfigurationJob confJob : oldJob.getConfigurations()) {
        	confJob.getConfiguration().getJobs().remove(confJob);
        	configurationRepository.save(confJob.getConfiguration());
            configurationJobRepository.delete(confJob);
        }
        oldJob.getConfigurations().clear();

        Set<ConfigurationJob> configurationsJob = new HashSet<>();
        int i = 0;
        for (Configuration conf : configurations) {
            ConfigurationJob confJob = new ConfigurationJob();
            confJob.setConfiguration(conf);
            confJob.setJob(oldJob);
            confJob.setRank(i);
            i++;
            confJob.setConfigurationJobPK(new ConfigurationJobPK(oldJob.getId(), conf.getId()));
            configurationsJob.add(confJob);
            configurationJobRepository.save(confJob);
        }
        oldJob.getConfigurations().addAll(configurationsJob);
        if (oldJob.getDays() == null) {
        	oldJob.setDays(new HashSet<Day>());
        }
        if (job.getDays()!=null) {
			oldJob.getDays().clear();
			job.getDays().forEach(day -> { oldJob.getDays().add( dayRepository.findById(day.getId()).get());});
		}
        oldJob.getDays().clear();
        oldJob.getDays().addAll(job.getDays());

        if (oldJob.getMailRecipients() == null) {
            oldJob.setMailRecipients(new HashSet<User>());
        }
        if (job.getMailRecipients()!=null) {
            oldJob.getMailRecipients().clear();
            job.getMailRecipients().forEach(user -> { oldJob.getMailRecipients().add( userRepository.findById(user.getId()));});
        }

        return new ResponseEntity<>(jobRepository.save(oldJob),HttpStatus.OK);
    }

    public ResponseEntity<?> updateJob(Job job) {
        Job oldJob = jobRepository.findById(job.getId()).get();
    	oldJob.setEndHour(job.getEndHour());
        oldJob.setFrequency(job.getFrequency());
        oldJob.setLibelle(job.getLibelle());
        oldJob.setStartHour(job.getStartHour());
        oldJob.setState(job.getState());
        oldJob.setDays(job.getDays());
        return new ResponseEntity<>(jobRepository.save(oldJob),HttpStatus.OK);
    }

    public ResponseEntity<?> getJobs() {
    	
    	List<Job> jobs = jobRepository.findAll();
    	jobs.forEach(job->{
    		job.setState(this.isRunning(job.getId()).isScheduled());
    	});
        return new ResponseEntity<>(jobs,HttpStatus.OK);
    }

    public ResponseEntity<?> deleteJob(int id) {
    	Job job = jobRepository.findById(id);
    	job.getConfigurations().forEach(confJob->{
    		confJob.getConfiguration().getJobs().remove(confJob);
    		confJob.setConfiguration(null);
    		confJob.setJob(null);
    		configurationJobRepository.deleteById(confJob.getConfigurationJobPK());
    	});
    	jobRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> getJobExecutions(Job job) {
        return new ResponseEntity<>(jobExecutionRepository.findByJob(job),HttpStatus.OK);
    }
   
    public ResponseEntity<?> getAllJobExecutions() {
        return new ResponseEntity<>(jobExecutionRepository.findAll(),HttpStatus.OK);
    }
    
    public ResponseEntity<?> addJobExecution(JobExecution jobExecution) {
        return new ResponseEntity<>(jobExecutionRepository.save(jobExecution),HttpStatus.CREATED);
    }
   
    public ResponseEntity<?> updateJobExecution(JobExecution jobExecution) {
        JobExecution oldJobExecution = jobExecutionRepository.findById(jobExecution.getId()).get();
        oldJobExecution.setEndDate(jobExecution.getEndDate());
        oldJobExecution.setError(jobExecution.getError());
        oldJobExecution.setErrorMessage(jobExecution.getErrorMessage());
        oldJobExecution.setFoundFiles(jobExecution.getFoundFiles());
        oldJobExecution.setJob(jobExecution.getJob());
        oldJobExecution.setStartDate(jobExecution.getStartDate());
        oldJobExecution.setSuccess(jobExecution.getSuccess());
        oldJobExecution.setTransferredFiles(jobExecution.getFoundFiles());
        return new ResponseEntity<>(jobExecutionRepository.save(oldJobExecution),HttpStatus.OK);
    }
    
    public void scheduleJob(Integer jobId)  {
        if(jobRepository.findById(jobId).isPresent()) {
            Job job = jobRepository.findById(jobId).get();
            final TimerInfo info = new TimerInfo();
            info.setRunForever(true);
            info.setStartDate(job.getStartHour());
            info.setEndDate(job.getEndHour());
            info.setRepeatInterval(job.getFrequency());
            info.setDays(job.getDays());
            schedulerService.schedule(TransferJob.class, info, job);
        }
	}

    public void unscheduleJob(Integer jobId) {

        if(jobRepository.findById(jobId).isPresent()){
            Job job = jobRepository.findById(jobId).get();
            final TimerInfo info = new TimerInfo();
            info.setRunForever(true);
            info.setStartDate(job.getStartHour());
            info.setEndDate(job.getEndHour());
            info.setRepeatInterval(job.getFrequency());
            info.setDays(job.getDays());
            schedulerService.unschedule(TransferJob.class, info, job);
        }

	}

    public JobStatus isRunning(Integer jobId)  {
        if(jobRepository.findById(jobId).isPresent()){
            Job job = jobRepository.findById(jobId).get();
            final TimerInfo info = new TimerInfo();
            info.setRunForever(true);
            info.setStartDate(job.getStartHour());
            info.setEndDate(job.getEndHour());
            info.setRepeatInterval(job.getFrequency());
            info.setDays(job.getDays());
            return schedulerService.isJobRunning(TransferJob.class, info, job);
        }
        return new JobStatus(0,false,true);
	}

}
