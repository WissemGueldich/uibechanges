package com.tn.uib.uibechanges.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequestMapping("/api/servers")
public class ServerController {
	
	@Autowired
	private ServerService serverService;
	
	@PostMapping
	@PreAuthorize("hasAuthority('server:write')")
	public ResponseEntity<?> addServeur(@RequestBody Server server) {
		return serverService.addServer(server);
	};
	
	@GetMapping
	@PreAuthorize("hasAuthority('server:read')")
	public ResponseEntity<?> getServeurs() {
		return serverService.getServers();
	};
	
	@GetMapping(path = "{id}")
	@PreAuthorize("hasAuthority('server:read')")
	public ResponseEntity<?> getServeur(@PathVariable int id) {
		return serverService.getServer(id);
	};
	
	@PutMapping
	@PreAuthorize("hasAuthority('server:write')")
	public ResponseEntity<?> updateServeur(@RequestBody Server server) {
		return serverService.updateServer(server);
	};
	
	@DeleteMapping(path="{id}")
	@PreAuthorize("hasAuthority('server:write')")
	public ResponseEntity<?> deleteServeur(@PathVariable int id) {
		return serverService.deleteServer(id);
	};
	
};