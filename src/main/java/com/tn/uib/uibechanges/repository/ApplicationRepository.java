package com.tn.uib.uibechanges.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tn.uib.uibechanges.model.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer>{
	Application findByIdentifier(String identifier);
}
