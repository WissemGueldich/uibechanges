package com.tn.uib.uibechanges.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tn.uib.uibechanges.model.ApplicationExecution;

@Repository
public interface ApplicationExecutionRepository extends JpaRepository<ApplicationExecution, Integer>{

}
