package com.tn.uib.uibechanges.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tn.uib.uibechanges.model.Configuration;
import com.tn.uib.uibechanges.model.Server;
import com.tn.uib.uibechanges.model.User;
import com.tn.uib.uibechanges.repository.ConfigurationRepository;
import com.tn.uib.uibechanges.repository.UserRepository;

@Service
public class ConfigurationService {
	@Autowired
	private ConfigurationRepository configurationRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	//TODO link server to configuration

	public ResponseEntity<?> addConfiguration(Configuration configuration) {
		return new ResponseEntity<> (configurationRepository.save(configuration),HttpStatus.CREATED);
	}

	public ResponseEntity<?> getConfigurations() {
		return new ResponseEntity<>(configurationRepository.findAll(), HttpStatus.OK);
	}
	

	public ResponseEntity<?> getConfiguration(int id) {
		return new ResponseEntity<>(configurationRepository.findById(id), HttpStatus.OK);
	}
	
	public ResponseEntity<?> getConfigurationsByServers(Server sourceServer, Server DestinationServer) {
		return new ResponseEntity<>(
				configurationRepository.findBySourceServerAndDestinationServer(sourceServer,DestinationServer), HttpStatus.OK);
	}

	public ResponseEntity<?> updateConfiguration(Configuration configuration) {
		
		Configuration oldConfiguration = configurationRepository.findById(configuration.getId()).get();
		oldConfiguration.setArchive(configuration.getArchive());
		oldConfiguration.setAutomatic(configuration.getAutomatic());
		oldConfiguration.setDestinationArchivingPath(configuration.getDestinationArchivingPath());
		oldConfiguration.setFilter(configuration.getFilter());
		oldConfiguration.setLibelle(configuration.getLibelle());
		oldConfiguration.setMove(configuration.getMove());
		oldConfiguration.setOverwrite(configuration.getOverwrite());
		return new ResponseEntity<>(configurationRepository.save(oldConfiguration), HttpStatus.OK);
	}

	public ResponseEntity<?> deleteConfiguration(int id) {
		configurationRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
    public ResponseEntity<?> getUserConfigurations( boolean automatic, String matricule) {
    	User user = userRepository.findByMatricule(matricule);
    	Set<Configuration> configs = new HashSet<>();
    	user.getProfiles().forEach(profile -> { profile.getConfigurations().forEach(config -> {
    		if(config.getAutomatic()==automatic) {configs.add(config);}
    	}); });
		return new ResponseEntity<>(configs,HttpStatus.OK);
    }
}
