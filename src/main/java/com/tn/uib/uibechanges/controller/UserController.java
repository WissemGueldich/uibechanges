package com.tn.uib.uibechanges.controller;


import static com.tn.uib.uibechanges.security.PermissionType.READ;
import static com.tn.uib.uibechanges.security.PermissionType.WRITE;

import java.util.HashSet;
import java.util.Set;

import javax.sound.midi.Soundbank;

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
import com.tn.uib.uibechanges.security.UserPermission;
import com.tn.uib.uibechanges.security.UserPermissionRepository;
import com.tn.uib.uibechanges.security.UserRole;
import com.tn.uib.uibechanges.security.UserRoleRepository;
import com.tn.uib.uibechanges.service.UserService;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequestMapping(path = "users")
public class UserController {
	
	@Autowired
	private UserService userService;
	//////////////////////////////////////////////////////////////
	@Autowired
	private UserPermissionRepository userPermissionRepository;
	@Autowired
	private UserRoleRepository userRoleRepository;
	//////////////////////////////////////////////////////////////
	@Autowired
	private PasswordEncoder encoder;
	
	@PostMapping
	private ResponseEntity<?> addUser (@RequestBody User user){
	/////////////////////////////////////////////////////////
		//no repository required
		user.setRoles(Set.of(new UserRole("test", new HashSet<UserPermission>())));
	/////////////////////////////////////////////////////////
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
		//////////////////////////////////////////////////////////////////////////////////////
		System.out.println(user.toString());
		System.out.println("________________________________________________");
		UserPermission servRead = new UserPermission("serveur",READ);
		if (!userPermissionRepository.existsByName(servRead.getName())) {
			userPermissionRepository.save(servRead);
		}
		UserPermission servWrite = new UserPermission("serveur",WRITE);
		if (!userPermissionRepository.existsByName(servWrite.getName())) {
			userPermissionRepository.save(servWrite);
		}
		Set<UserPermission> permissions = new HashSet<UserPermission>();
		permissions.add(servRead);
		permissions.add(servWrite);
		UserRole admin = new UserRole("admin", permissions);
		if (!userRoleRepository.existsByName(admin.getName())) {
			userRoleRepository.save(admin);
		}
		user.setRoles(Set.of(admin));
		//////////////////////////////////////////////////////////////////////////////////////
		return userService.updateUser(user);

	}
	@DeleteMapping(path="{id}")
	private ResponseEntity<?> deleteUser (@PathVariable int id){
		return userService.deleteUser(id);

	}


}


