package com.tn.uib.uibechanges.service;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.tn.uib.uibechanges.model.Configuration;
import com.tn.uib.uibechanges.model.Day;
import com.tn.uib.uibechanges.model.Job;
import com.tn.uib.uibechanges.model.JobExecution;
import com.tn.uib.uibechanges.repository.ConfigurationRepository;
import com.tn.uib.uibechanges.repository.DayRepository;
import com.tn.uib.uibechanges.repository.JobExecutionRepository;
import com.tn.uib.uibechanges.repository.JobRepository;

@Service
@Transactional
public class JobService {
	
	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	private ConfigurationRepository configurationRepository;
	
	@Autowired
	private DayRepository dayRepository;
	
	@Autowired
	private JobExecutionRepository jobExecutionRepository;
	

    public ResponseEntity<?> addJob(Job job, Set<Configuration> configurations, Set<Day> days) {

        if (job.getConfigurations() == null) {
            job.setConfigurations(new HashSet<Configuration>());
        }

        if (job.getDays() == null) {
            job.setDays(new HashSet<Day>());
        }
        days.forEach(day -> {
        	job.getDays().add(dayRepository.findById(day.getId()).get());
        });
        configurations.forEach(config -> {
        	job.getConfigurations().add(configurationRepository.findById(config.getId()).get());
        });
        return new ResponseEntity<>(jobRepository.save(job), HttpStatus.CREATED);
    }
    
    public ResponseEntity<?> addJob(Job job) {

        if (job.getConfigurations() == null) {
            job.setConfigurations(new HashSet<Configuration>());
        }

        if (job.getDays() == null) {
            job.setDays(new HashSet<Day>());
        }
        return new ResponseEntity<>(jobRepository.save(job), HttpStatus.CREATED);
    }
 
    public ResponseEntity<?> getJob(Integer id) {
        return new ResponseEntity<>(jobRepository.findById(id).get(),HttpStatus.OK);
    }

    
    public ResponseEntity<?> updateJob(Job job, Set<Configuration> configurations, Set<Day> days) {
        if (job.getConfigurations() == null) {
            job.setConfigurations(new HashSet<Configuration>());
        }
        Job oldJob = jobRepository.findById(job.getId()).get();
        oldJob.setEndHour(job.getEndHour());
        oldJob.setFrequency(job.getFrequency());
        oldJob.setLibelle(job.getLibelle());
        oldJob.setStartHour(job.getStartHour());
        oldJob.setState(job.getState());
        oldJob.setType(job.getType());
        oldJob.getConfigurations().clear();
        configurations.forEach(config -> {oldJob.getConfigurations().add(configurationRepository.findById(config.getId()).get());});
        oldJob.getDays().clear();
        days.forEach(day -> {job.getDays().add(dayRepository.findById(day.getId()).get());});
        return new ResponseEntity<>(jobRepository.save(oldJob),HttpStatus.OK);
    }

    
    public ResponseEntity<?> updateJob(Job job) {
        Job oldJob = jobRepository.findById(job.getId()).get();
    	oldJob.setEndHour(job.getEndHour());
        oldJob.setFrequency(job.getFrequency());
        oldJob.setLibelle(job.getLibelle());
        oldJob.setStartHour(job.getStartHour());
        oldJob.setState(job.getState());
        oldJob.setType(job.getType());
        return new ResponseEntity<>(jobRepository.save(oldJob),HttpStatus.OK);
    }

    
    public ResponseEntity<?> getJobs() {
        return new ResponseEntity<>(jobRepository.findAll(),HttpStatus.OK);
    }

    
    public ResponseEntity<?> deleteJob(Job job) {
    	jobRepository.deleteById(job.getId());
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

}
