package com.tn.uib.uibechanges.controller;


import java.util.Set;

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

import com.tn.uib.uibechanges.model.User;
import com.tn.uib.uibechanges.model.UserRole;
import com.tn.uib.uibechanges.service.UserService;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequestMapping(path = "/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping
	@PreAuthorize("hasAuthority('user:write')")
	public ResponseEntity<?> addUser (@RequestBody User user){
		return userService.addUser(user);
	}
	
	@GetMapping
	@PreAuthorize("hasAuthority('user:read')")
	public ResponseEntity<?> getUsers (){
		return userService.getUsers();
	}
	
	@GetMapping(path = "{id}")
	@PreAuthorize("hasAuthority('user:read')")
	public ResponseEntity<?> getUser (@PathVariable int id ){
		return userService.getUser(id);
	}
	
	@GetMapping(path = "/m/{matricule}")
	@PreAuthorize("hasAuthority('transfer:read')")
	public ResponseEntity<?> getUser (@PathVariable String matricule ){
		return userService.getUser(matricule);
	}
	
	@PutMapping
	@PreAuthorize("hasAuthority('user:write')")
	public ResponseEntity<?> updateUser (@RequestBody User user ){
		return userService.updateUser(user);
	}
	
	@DeleteMapping(path="{id}")
	@PreAuthorize("hasAuthority('user:write')")
	public ResponseEntity<?> deleteUser (@PathVariable int id){
		return userService.deleteUser(id);
	}
	
	@PostMapping(path="/add/{userId}")
	@PreAuthorize("hasAuthority('user:write')")
	public ResponseEntity<?> addRoleToUser(@PathVariable int userId, @RequestBody Set<UserRole> roles){

		return userService.addRolesToUser(userId, roles);
	}
	
	@PostMapping(path="/remove/{userId}")
	@PreAuthorize("hasAuthority('user:write')")
	public ResponseEntity<?> removeRoleFromUser(@PathVariable int userId, @RequestBody Set<UserRole> roles){

		return userService.removeRolesFromUser(userId, roles);
	}

}








