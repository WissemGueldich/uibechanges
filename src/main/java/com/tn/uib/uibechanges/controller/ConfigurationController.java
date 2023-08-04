package com.tn.uib.uibechanges.controller;

import java.util.Map;

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

import com.tn.uib.uibechanges.model.Configuration;
import com.tn.uib.uibechanges.model.Server;
import com.tn.uib.uibechanges.service.ConfigurationService;


@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequestMapping(path = "/api/configs")
public class ConfigurationController {

	@Autowired
	private ConfigurationService configurationService;
	
	@PostMapping
	@PreAuthorize("hasAuthority('config:write')")
	public ResponseEntity<?> addConfiguration (@RequestBody Configuration configuration){
		return configurationService.addConfiguration(configuration);
	}
	
	@GetMapping
	@PreAuthorize("hasAuthority('config:read')")
	public ResponseEntity<?> getConfigurations (){
		return configurationService.getConfigurations();
	}
	
	@GetMapping(path = "{id}")
	@PreAuthorize("hasAuthority('config:read')")
	public ResponseEntity<?> getConfiguration (@PathVariable int id ){
		return configurationService.getConfiguration(id);

	}
	
	@PutMapping
	@PreAuthorize("hasAuthority('config:write')")
	public ResponseEntity<?> updateConfiguration (@RequestBody Configuration configuration ){
		return configurationService.updateConfiguration(configuration);
	}
	
	@DeleteMapping(path="{id}")
	@PreAuthorize("hasAuthority('config:write')")
	public ResponseEntity<?> deleteConfiguration (@PathVariable int id){
		return configurationService.deleteConfiguration(id);
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasAuthority('config:read')")
	public ResponseEntity<?> getUserConfigurationsBy(@RequestBody SearchRequest searchRequest){
		return configurationService.getUserConfigurations(searchRequest.getMatricule());
	}
	
	@GetMapping("/server")
	@PreAuthorize("hasAuthority('config:read')")
	public ResponseEntity<?> getServersConfigurations(@RequestBody Map<String, Server> json){
		return configurationService.getConfigurationsByServers(json.get("source"), json.get("destination"));
	}
	
	
	
	
}
