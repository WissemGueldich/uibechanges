package com.tn.uib.uibechanges.service;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tn.uib.uibechanges.model.Application;
import com.tn.uib.uibechanges.model.ApplicationConfiguration;
import com.tn.uib.uibechanges.model.ApplicationConfigurationPK;
import com.tn.uib.uibechanges.model.ApplicationExecution;
import com.tn.uib.uibechanges.model.Configuration;
import com.tn.uib.uibechanges.repository.ApplicationConfigurationRepository;
import com.tn.uib.uibechanges.repository.ApplicationExecutionRepository;
import com.tn.uib.uibechanges.repository.ApplicationRepository;

@Service
@Transactional
public class ApplicationService {

	@Autowired
	private ApplicationRepository applicationRepository;
	
	@Autowired
	private ApplicationExecutionRepository applicationExecutionRepository;
	
	@Autowired
	private ApplicationConfigurationRepository applicationConfigurationRepository;
	
	 public ResponseEntity<?> addApplication(Application application, Set<Configuration> configurations) {
	        if (application.getConfigurations() == null) {
	            application.setConfigurations(new HashSet<ApplicationConfiguration>());
	        }
	        applicationRepository.save(application);
	        
	        int i = 1;
	        for (Configuration conf : configurations) {
	            ApplicationConfiguration appConfig = new ApplicationConfiguration();
	            appConfig.setConfiguration(conf);
	            appConfig.setApplication(application);
	            appConfig.setRank(i);
	            appConfig.setApplicationConfigurationPK(new ApplicationConfigurationPK(application.getId(), conf.getId()));
	            applicationConfigurationRepository.save(appConfig);
	            i++;
	        }
	        return new ResponseEntity<>(applicationRepository.save(application), HttpStatus.CREATED);
	    }

	    
    public  ResponseEntity<?> getApplication(Integer id) {
        return new  ResponseEntity<>(applicationRepository.findById(id),HttpStatus.OK);
    }

    
    public  ResponseEntity<?> getApplication(String identifier) {
        return new  ResponseEntity<>(applicationRepository.findByIdentifier(identifier),HttpStatus.OK);
    }
    
    public ResponseEntity<?> updateApplication(Application oldApplication, Set<Configuration> configurations) {
    	Application application = applicationRepository.findById(oldApplication.getId()).get();
    	if (application.getConfigurations() == null) {
            application.setConfigurations(new HashSet<ApplicationConfiguration>());
        }

        for (ApplicationConfiguration appConfig : application.getConfigurations()) {
            applicationConfigurationRepository.delete(appConfig);
        }
        application.getConfigurations().clear();

        Set<ApplicationConfiguration> applicationConfigurations = new HashSet<>();
        int i = 1;
        for (Configuration conf : configurations) {
            ApplicationConfiguration appConf = new ApplicationConfiguration();
            appConf.setConfiguration(conf);
            appConf.setApplication(application);
            appConf.setRank(i);
            i++;
            appConf.setApplicationConfigurationPK(new ApplicationConfigurationPK(application.getId(), conf.getId()));
            applicationConfigurations.add(appConf);
            applicationConfigurationRepository.save(appConf);
        }
        application.getConfigurations().addAll(applicationConfigurations);
        applicationRepository.save(application);
        return new ResponseEntity<>(applicationRepository.save(oldApplication),HttpStatus.OK);
    }

    public ResponseEntity<?> getApplications() {
        return new ResponseEntity<>(applicationRepository.findAll(),HttpStatus.OK);
    }
    
    public ResponseEntity<?> deleteApplication(Application application) {
        Application app = applicationRepository.findById(application.getId()).get();
        for (ApplicationConfiguration appConf : app.getConfigurations()) {
            applicationConfigurationRepository.delete(appConf);
        }
    	applicationRepository.delete(app);
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
