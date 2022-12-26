package com.tn.uib.uibechanges.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.tn.uib.uibechanges.model.SystemUser;
import com.tn.uib.uibechanges.service.SystemUserService;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequestMapping("/api/systemUsers")
public class SystemUserController {
	
	@Autowired
	private SystemUserService systemUserService;
	
	@PostMapping
	//@PreAuthorize("hasAuthority('serveur:write')")
	public ResponseEntity<?> addSystemUser(@RequestBody SystemUser systemUser) {
		return systemUserService.addSystemUser(systemUser);
	};
	
	@GetMapping
	//@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_NWADMIN')")
	public ResponseEntity<?> getSystemUsers() {
		return systemUserService.getSystemUsers();
	};
	
	@GetMapping("/server")
	public ResponseEntity<?> getSystemUsersBySourceServer(@RequestBody Server server) {
		return systemUserService.getSystemUsersByServer(server);
	};
	
	@GetMapping("/server/{id}")
	public ResponseEntity<?> getSystemUsersBySourceServer(@PathVariable int id) {
		return systemUserService.getSystemUsersByServerId(id);
	};
	
	@GetMapping(path = "{id}")
	public ResponseEntity<?> getSystemUser(@PathVariable int id) {
		return systemUserService.getSystemUser(id);
	};
	
	@PutMapping
	public ResponseEntity<?> updateSystemUser(@RequestBody SystemUser systemUser) {
		return systemUserService.updateSystemUser(systemUser);
	};
	
	@DeleteMapping(path="{id}")
	//@PreAuthorize("hasAuthority('job:write')")
	public ResponseEntity<?> deleteSystemUser(@PathVariable int id) {
		return systemUserService.deleteSystemUsers(id);
	};
//update systemuser with server set
}