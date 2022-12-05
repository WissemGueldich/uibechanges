package com.tn.uib.uibechanges.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tn.uib.uibechanges.model.Configuration;
import com.tn.uib.uibechanges.model.Server;
import com.tn.uib.uibechanges.model.User;
import com.tn.uib.uibechanges.repository.ConfigurationRepository;
import com.tn.uib.uibechanges.repository.ServerRepository;
import com.tn.uib.uibechanges.repository.SystemUserRepository;
import com.tn.uib.uibechanges.repository.UserRepository;

@Service
@Transactional
public class ConfigurationService {
	@Autowired
	private ConfigurationRepository configurationRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ServerRepository serverRepository;
	
	@Autowired
	private SystemUserRepository systemUserRepository;
	
	public ResponseEntity<?> addConfiguration(Configuration configuration) {
		configuration.setSourceServer(serverRepository.findById(configuration.getSourceServer().getId()));
		configuration.setDestinationServer(serverRepository.findById(configuration.getDestinationServer().getId()));
		configuration.setSourceUser(systemUserRepository.findById(configuration.getSourceUser().getId()).get());
		configuration.setDestinationUser(systemUserRepository.findById(configuration.getDestinationUser().getId()).get());
		return new ResponseEntity<> (configurationRepository.save(configuration),HttpStatus.CREATED);
	}

	public ResponseEntity<?> getConfigurations() {
		return new ResponseEntity<>(configurationRepository.findAll(), HttpStatus.OK);
	}
	

	public ResponseEntity<?> getConfiguration(int id) {
		return new ResponseEntity<>(configurationRepository.findById(id), HttpStatus.OK);
	}
	
	public ResponseEntity<?> getConfigurationsByServers(Server sourceServer, Server destinationServer) {
		Set<Configuration> configs = new HashSet<>();
		Set<Configuration> sConfigs = configurationRepository.findBySourceServer(sourceServer);
		sConfigs.forEach(config ->{
			if (config.getDestinationServer()!=null && config.getDestinationServer().getId() == destinationServer.getId()) {
				configs.add(config);
			}
		});
		return new ResponseEntity<>(configs, HttpStatus.OK);
	}

	public ResponseEntity<?> updateConfiguration(Configuration configuration) {
		
		Configuration oldConfiguration = configurationRepository.findById(configuration.getId()).get();
		oldConfiguration.setLibelle(configuration.getLibelle());
		oldConfiguration.setSourceServer(configuration.getSourceServer());
		oldConfiguration.setSourceUser(configuration.getSourceUser());
		oldConfiguration.setSourcePath(configuration.getSourcePath());
		oldConfiguration.setSourceArchivingPath(configuration.getSourceArchivingPath());
		oldConfiguration.setDestinationServer(configuration.getDestinationServer());
		oldConfiguration.setDestinationUser(configuration.getDestinationUser());
		oldConfiguration.setDestinationPath(configuration.getDestinationPath());
		oldConfiguration.setDestinationArchivingPath(configuration.getDestinationArchivingPath());
		oldConfiguration.setOverwrite(configuration.getOverwrite());
		oldConfiguration.setMove(configuration.getMove());
		oldConfiguration.setArchive(configuration.getArchive());
		oldConfiguration.setAutomatic(configuration.getAutomatic());
		oldConfiguration.setFilter(configuration.getFilter());
		
		return new ResponseEntity<>(configurationRepository.save(oldConfiguration), HttpStatus.OK);
	}

	public ResponseEntity<?> deleteConfiguration(int id) {
		Configuration configuration = configurationRepository.findById(id);
		configuration.getProfiles().forEach(prof->{prof.getConfigurations().remove(configuration);});
		configuration.getProfiles().clear();
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
