package com.tn.uib.uibechanges.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tn.uib.uibechanges.model.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer>{
	Job findById(int id);

}
