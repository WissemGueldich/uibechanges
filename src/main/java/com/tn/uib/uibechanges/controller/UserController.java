package com.tn.uib.uibechanges.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.tn.uib.uibechanges.service.UserService;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequestMapping(path = "users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@PostMapping
	private ResponseEntity<?> addUser (@RequestBody User user){
		
		user.setPassword(encoder.encode(user.getPassword()));
		return userService.addUser(user);
	}
	@GetMapping
	private ResponseEntity<?> getUsers (){
		return userService.getUsers();
	}
	@GetMapping(path = "{id}")
	private ResponseEntity<?> getUser (@PathVariable int id ){
		return userService.getUser(id);

	}
	@PutMapping
	private ResponseEntity<?> updateUser (@RequestBody User user ){
		System.out.println(user.toString());
		return userService.updateUser(user);

	}
	@DeleteMapping(path="{id}")
	private ResponseEntity<?> deleteUser (@PathVariable int id){
		return userService.deleteUser(id);

	}


}


