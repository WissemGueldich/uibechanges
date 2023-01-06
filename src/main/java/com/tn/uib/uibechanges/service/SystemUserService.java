package com.tn.uib.uibechanges.service;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tn.uib.uibechanges.model.Server;
import com.tn.uib.uibechanges.model.SystemUser;
import com.tn.uib.uibechanges.repository.ConfigurationRepository;
import com.tn.uib.uibechanges.repository.ServerRepository;
import com.tn.uib.uibechanges.repository.SystemUserRepository;

@Service
@Transactional
public class SystemUserService {
	
	@Autowired
	private SystemUserRepository systemUserRepository;
	
	@Autowired
	private ServerRepository serverRepository;
	
	@Autowired
	private ConfigurationRepository configurationRepository;
	
	public ResponseEntity<?> addSystemUser(SystemUser systemUser) {
		Set<Server> servers = new HashSet<>();
		if (systemUser.getServers()!=null) {
			systemUser.getServers().forEach(server -> {servers.add(serverRepository.findById(server.getId()));});
		}
		systemUser.setServers(servers);
		return new ResponseEntity<>(systemUserRepository.save(systemUser),HttpStatus.CREATED);
	}
	
	public ResponseEntity<?> updateSystemUser(SystemUser systemUser) {
		SystemUser oldUser = systemUserRepository.findById(systemUser.getId()).get();
		Set<Server> servers = new HashSet<>();
		if (systemUser.getServers()!=null) {
			systemUser.getServers().forEach(server -> {servers.add(serverRepository.findById(server.getId()));});
		}
		oldUser.setServers(servers);
		oldUser.setEnabled(systemUser.isEnabled());
		oldUser.setConfigurationsAsDestination(systemUser.getConfigurationsAsDestination());
		oldUser.setConfigurationsAsSource(systemUser.getConfigurationsAsSource());
		oldUser.setLibelle(systemUser.getLibelle());
		oldUser.setLogin(systemUser.getLogin());
		oldUser.setPassword(systemUser.getPassword());
		return new ResponseEntity<>(systemUserRepository.save(oldUser),HttpStatus.CREATED);
	}
	
	public ResponseEntity<?> getSystemUser(int id) {
		return new ResponseEntity<SystemUser>(systemUserRepository.findById(id).get(), HttpStatus.OK);
	}
	
	public ResponseEntity<?> getSystemUsers() {
		return new ResponseEntity<>(systemUserRepository.findAll(), HttpStatus.OK);
	}
	
	public ResponseEntity<?> getSystemUsersByServer(Server server) {
		return new ResponseEntity<>(systemUserRepository.findByServers(server), HttpStatus.OK);
	}
	
	public ResponseEntity<?> getSystemUsersByServerId(int id) {
		return new ResponseEntity<>(systemUserRepository.findByServersId(id), HttpStatus.OK);
	}
	
	public ResponseEntity<?> deleteSystemUsers(int id) {
		System.out.println("this is delete system user service");
		SystemUser oldUser = systemUserRepository.findById(id).get();
		oldUser.getConfigurationsAsDestination().forEach(config->{
			config.setDestinationServer(null);
			
		});
		oldUser.getConfigurationsAsDestination().clear();
		oldUser.getConfigurationsAsSource().forEach(config->{
			config.setSourceServer(null);
			System.out.println("savingggggggggggggggggg");
			configurationRepository.save(config);
		});
		oldUser.getConfigurationsAsSource().clear();
		oldUser.getServers().clear();
		systemUserRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	

}
