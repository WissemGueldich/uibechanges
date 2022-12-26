package com.tn.uib.uibechanges.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tn.uib.uibechanges.model.Job;
import com.tn.uib.uibechanges.service.JobService;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequestMapping(path = "/api/jobs")
public class JobController {
	
	@Autowired
	private JobService jobService;
	
	@PostMapping("/schedule")
	private ResponseEntity<?> scheduleJob (@RequestBody Integer JobId){
		try {
			jobService.scheduleJob(JobId);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping
	private ResponseEntity<?> getJobs (){
		return jobService.getJobs();
	}

}
