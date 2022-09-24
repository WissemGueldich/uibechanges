package com.tn.uib.uibechanges.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tn.uib.uibechanges.model.Server;
import com.tn.uib.uibechanges.service.ServerService;


@RestController
@RequestMapping("api/v1/serveurs")
public class ServerController {
	
	@Autowired
	ServerService serverService;
			
	
	@PostMapping
	//@PreAuthorize("hasAuthority('serveur:write')")
	public ResponseEntity<?> addServeur(@RequestBody Server server) {
		return serverService.addServer(server);
		
	};
	
	@GetMapping
	//@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_NWADMIN')")
	public ResponseEntity<?> getServeurs() {
		return serverService.getServers();

	};
	
	@GetMapping(path = "{id}")
	public ResponseEntity<?> getServeur(@PathVariable int id) {
		return serverService.getServer(id);

	};
	
	@PutMapping
	public ResponseEntity<?> updateServeur(@RequestBody Server server) {
		return serverService.updateServer(server);
		
	};
	
	@DeleteMapping(path="{id}")
	//@PreAuthorize("hasAuthority('job:write')")
	public ResponseEntity<?> deleteServeur(@PathVariable int id) {
		return serverService.deleteServer(id);
	};
	
};