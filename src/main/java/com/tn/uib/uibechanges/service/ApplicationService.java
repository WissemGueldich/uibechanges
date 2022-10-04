package com.tn.uib.uibechanges.service;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tn.uib.uibechanges.model.Application;
import com.tn.uib.uibechanges.model.ApplicationExecution;
import com.tn.uib.uibechanges.model.Configuration;
import com.tn.uib.uibechanges.repository.ApplicationExecutionRepository;
import com.tn.uib.uibechanges.repository.ApplicationRepository;
import com.tn.uib.uibechanges.repository.ConfigurationRepository;

@Service
@Transactional
public class ApplicationService {

	@Autowired
	private ApplicationRepository applicationRepository;
	
	@Autowired
	private ApplicationExecutionRepository applicationExecutionRepository;
	
	@Autowired
	private ConfigurationRepository configurationRepository;
	
	 public ResponseEntity<?> addApplication(Application application, Set<Configuration> configurations) {
	        if (application.getConfigurations() == null) {
	            application.setConfigurations(new HashSet<Configuration>());
	        }
	        configurations.forEach(config -> {
	        	application.getConfigurations().add(configurationRepository.findById(config.getId()).get()); 
	        });
	        application.getConfigurations().addAll(configurations);
	        return new ResponseEntity<>(applicationRepository.save(application), HttpStatus.CREATED);
	    }

	    
    public  ResponseEntity<?> getApplication(Integer id) {
        return new  ResponseEntity<>(applicationRepository.findById(id),HttpStatus.OK);
    }

    
    public  ResponseEntity<?> getApplication(String identifier) {
        return new  ResponseEntity<>(applicationRepository.findByIdentifier(identifier),HttpStatus.OK);
    }
    
    public ResponseEntity<?> updateApplication(Application application, Set<Configuration> configurations) {
        if (application.getConfigurations() == null) {
            application.setConfigurations(new HashSet<Configuration>());
        }
    	Application oldApplication = applicationRepository.findById(application.getId()).get();
    	oldApplication.getConfigurations().clear();
    	oldApplication.getConfigurations().addAll(configurations);
    	oldApplication.setAddress(application.getAddress());
    	oldApplication.setIdentifier(application.getIdentifier());
    	oldApplication.setName(application.getName());
        return new ResponseEntity<>(applicationRepository.save(oldApplication),HttpStatus.OK);
    }

    public ResponseEntity<?> getApplications() {
        return new ResponseEntity<>(applicationRepository.findAll(),HttpStatus.OK);
    }
    
    public ResponseEntity<?> deleteApplication(Application application) {
    	applicationRepository.deleteById(application.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> getApplicationExecutions(Integer applicationId) {
        return new ResponseEntity<>(applicationRepository.findById(applicationId).get().getApplicationExecutions(),HttpStatus.OK);
    }


    public ResponseEntity<?> getAllApplicationExecutions() {
        return new ResponseEntity<>(applicationExecutionRepository.findAll(),HttpStatus.OK);
    }

    public ResponseEntity<?> addApplicationExecution(ApplicationExecution applicationExecution) {
        return new ResponseEntity<>(applicationExecutionRepository.save(applicationExecution),HttpStatus.CREATED);
    }


}
