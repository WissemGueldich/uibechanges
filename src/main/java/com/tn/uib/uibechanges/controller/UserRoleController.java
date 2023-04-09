package com.tn.uib.uibechanges.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tn.uib.uibechanges.service.UserRoleService;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequestMapping("/api/roles")
public class UserRoleController {
	
	@Autowired
	private UserRoleService userRoleService;	
	
	@GetMapping
	@PreAuthorize("hasAuthority('user:read')")
	public ResponseEntity<?> getRoles (){
		return userRoleService.getRoles();
	}
	
	@GetMapping("/permissions")
	@PreAuthorize("hasAuthority('user:read')")
	public ResponseEntity<?> getPermissions (){
		return userRoleService.getPermissions();
	}
	
	@GetMapping(path = "{id}")
	@PreAuthorize("hasAuthority('user:read')")
	public ResponseEntity<?> getRole (@PathVariable int id ){
		return userRoleService.getRole(id);
	}
	
	/*@PostMapping
	public ResponseEntity<?> addRole (@RequestBody UserRole role){	
		return userRoleService.addRole(role);
	}
	
	@PutMapping
	public ResponseEntity<?> updateRole (@RequestBody UserRole role ){
		return userRoleService.updateRole(role);

	}
	@DeleteMapping(path="{id}")
	public ResponseEntity<?> deleteUser (@PathVariable int id){
		return userRoleService.deleteRole(id);

	}*/

}
