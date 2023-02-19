package com.tn.uib.uibechanges.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	@PostMapping("/schedule/{jobId}")
	private ResponseEntity<?> scheduleJob (@PathVariable Integer jobId){
		try {
			jobService.scheduleJob(jobId);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping
	private ResponseEntity<?> getJobs (){
		return jobService.getJobs();
	}
	
	@PostMapping
	private ResponseEntity<?> addJob (@RequestBody Job Job){
		return jobService.addJob(Job);
	}

	@GetMapping(path = "{id}")
	private ResponseEntity<?> getJob (@PathVariable int id ){
		return jobService.getJob(id);
	}
	
	@PutMapping
	private ResponseEntity<?> updateJob (@RequestBody Job Job ){
		return jobService.updateJob(Job);
	}
	
	@DeleteMapping(path="{id}")
	private ResponseEntity<?> deleteJob (@PathVariable int id){
		return jobService.deleteJob(id);
	}

}
