package com.tn.uib.uibechanges.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tn.uib.uibechanges.model.Job;
import com.tn.uib.uibechanges.model.JobExecution;

@Repository
public interface JobExecutionRepository extends JpaRepository<JobExecution, Integer>{
	JobExecution findById(int id);
	Set<JobExecution> findByJob(Job job);
}
