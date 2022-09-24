package com.tn.uib.uibechanges.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tn.uib.uibechanges.model.Server;
import com.tn.uib.uibechanges.repository.ServerRepository;

@Service
@Transactional
public class ServerService {

	@Autowired
	private ServerRepository serverRepository;
	


	public ResponseEntity<?> addServer(Server server) {
		if (serverRepository.existsByAddress(server.getAddress())) {
			return new ResponseEntity<>("address already taken !", HttpStatus.FOUND);
		} 
		//TODO update relational attributes before saving
		
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
		//TODO update relational attributes before updating

		return new ResponseEntity<>(serverRepository.save(oldServer), HttpStatus.OK);
	}

	public ResponseEntity<?> deleteServer(int id) {
		//Server server = serverRepository.findById(id);
		//TODO clear relational attributes before deleting
		serverRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
