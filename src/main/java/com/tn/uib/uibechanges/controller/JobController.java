package com.tn.uib.uibechanges.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tn.uib.uibechanges.model.JobStatus;
import com.tn.uib.uibechanges.service.JobService;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequestMapping(path = "/api/jobs")
public class JobController {
	
	@Autowired
	private JobService jobService;
	
	@PostMapping("/schedule/{jobId}")
	@PreAuthorize("hasAuthority('job:execute')")
	public ResponseEntity<?> scheduleJob (@PathVariable Integer jobId){
		try {
			jobService.scheduleJob(jobId);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/unschedule/{jobId}")
	@PreAuthorize("hasAuthority('job:execute')")
	public ResponseEntity<?> unscheduleJob (@PathVariable Integer jobId){
		try {
			jobService.unscheduleJob(jobId);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/status/{jobId}")
	@PreAuthorize("hasAuthority('job:execute')")
	public ResponseEntity<?> isJobRunning (@PathVariable Integer jobId){
		JobStatus running = jobService.isRunning(jobId);
		return new ResponseEntity<JobStatus>(running,HttpStatus.OK);

	}
	
	@GetMapping
	@PreAuthorize("hasAuthority('job:read')")
	public ResponseEntity<?> getJobs (){
		return jobService.getJobs();
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('job:write')")
	public ResponseEntity<?> addJob (@RequestBody JobConfigs jobConfigs){
		return jobService.addJob(jobConfigs);
	}


	@GetMapping(path = "{id}")
	@PreAuthorize("hasAuthority('job:read')")
	public ResponseEntity<?> getJob (@PathVariable int id ){
		return jobService.getJob(id);
	}
	
	@PutMapping
	@PreAuthorize("hasAuthority('job:write')")
	public ResponseEntity<?> updateJob (@RequestBody JobConfigs jobConfigs ){
		return jobService.updateJob(jobConfigs);
	}
	
	@DeleteMapping(path="{id}")
	@PreAuthorize("hasAuthority('job:write')")
	public ResponseEntity<?> deleteJob (@PathVariable int id){
		return jobService.deleteJob(id);
	}

}
