package com.tn.uib.uibechanges.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tn.uib.uibechanges.model.Configuration;
import com.tn.uib.uibechanges.model.Server;
import com.tn.uib.uibechanges.model.SystemUser;
import com.tn.uib.uibechanges.repository.ServerRepository;
import com.tn.uib.uibechanges.repository.SystemUserRepository;

@Service
@Transactional
public class ServerService {

	@Autowired
	private ServerRepository serverRepository;
	
	@Autowired
	private SystemUserRepository systemUserRepository;
	
	@Autowired
	private SystemUserService systemUserService;
	
	//TODO link server to configuration

	public ResponseEntity<?> addServer(Server server) {
		if (serverRepository.existsByAddress(server.getAddress())) {
			return new ResponseEntity<>("address already taken !", HttpStatus.FOUND);
		} 
		//TODO update relational attributes before saving
		if(server.getDestionationConfigurations()==null) {
			server.setDestionationConfigurations(new HashSet<>());
		}
		if(server.getSourceConfigurations()==null) {
			server.setSourceConfigurations(new HashSet<>());
		}
		return new ResponseEntity<Server>(serverRepository.save(server), HttpStatus.CREATED);
	}
	
	public ResponseEntity<?> getServers() {
		return new ResponseEntity<>(serverRepository.findAll(), HttpStatus.OK);
	}

	public ResponseEntity<?> getServer(int id) {
		return new ResponseEntity<Server>(serverRepository.findById(id), HttpStatus.OK);
	}

	public ResponseEntity<?> getServer(String address) {
		return new ResponseEntity<Server>(serverRepository.findByAddress(address), HttpStatus.OK);
	}
	

	public ResponseEntity<?> updateServer(Server server) {
		Server oldServer = serverRepository.findById(server.getId());
		if (!oldServer.getAddress().equals(server.getAddress()) && serverRepository.existsByAddress(server.getAddress())) {
			return new ResponseEntity<>("address already taken !", HttpStatus.FOUND);
		}
		oldServer.setAddress(server.getAddress());
		oldServer.setLibelle(server.getLibelle());
		oldServer.setMainAddress(server.getMainAddress());
		oldServer.setSecondaryAddress(server.getSecondaryAddress());
		oldServer.setPort(server.getPort());
		if (server.getDestionationConfigurations()!=null) {
			oldServer.setSourceConfigurations(server.getSourceConfigurations());
		}
		if (server.getSourceConfigurations()!=null){
			oldServer.setDestionationConfigurations(server.getDestionationConfigurations());
		}
		Set<SystemUser> users = new HashSet<>();
		if (server.getSystemUsers()!=null) {
			server.getSystemUsers().forEach(user -> {users.add(systemUserRepository.findById(user.getId()).get());});
		}
		oldServer.setSystemUsers(users);
		return new ResponseEntity<>(serverRepository.save(oldServer), HttpStatus.OK);
	}

	public ResponseEntity<?> deleteServer(int id) {
		Server server = serverRepository.findById(id);
		server.getSourceConfigurations().forEach(config->{
			config.setSourceServer(null);
		});
		server.getDestionationConfigurations().forEach(config->{config.setDestinationServer(null);});
		server.getSystemUsers().forEach(user->{
			if (user.getServers().size()>1) {
				user.getServers().remove(server);
			}else {
				user.getServers().remove(server);
				systemUserService.deleteSystemUsers(user.getId());
			}
		});
		server.getSystemUsers().clear();
		serverRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
    public ResponseEntity<?> changeToSecondaryAddress(boolean secondary) {
        List<Server> list = serverRepository.findAll();
        for(Server s : list){
            s.changeToSecodaryAddress(secondary);
            serverRepository.save(s);
        }
		return new ResponseEntity<>(HttpStatus.OK);
    }
    
    public ResponseEntity<?> addSourceConfigurationsToServer(int serverId,Set<Configuration> configurations){
    	Server oldServer = serverRepository.findById(serverId);
    	oldServer.getSourceConfigurations().addAll(configurations);
    	return new ResponseEntity<>(serverRepository.save(oldServer),HttpStatus.OK);
    }
    
    public ResponseEntity<?> addDestinationConfigurationsToServer(int serverId,Set<Configuration> configurations){
    	if(!serverRepository.existsById(serverId) ) {
			return new ResponseEntity<>("server not found",HttpStatus.NOT_FOUND);
		}
    	Server oldServer = serverRepository.findById(serverId);
    	oldServer.getDestionationConfigurations().addAll(configurations);
    	return new ResponseEntity<>(serverRepository.save(oldServer),HttpStatus.OK);
    }

}
