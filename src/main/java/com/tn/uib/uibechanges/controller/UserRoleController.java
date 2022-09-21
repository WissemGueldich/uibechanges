package com.tn.uib.uibechanges.controller;

import static com.tn.uib.uibechanges.security.PermissionType.READ;
import static com.tn.uib.uibechanges.security.PermissionType.WRITE;

import java.util.Set;

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

import com.tn.uib.uibechanges.model.UserPermission;
import com.tn.uib.uibechanges.model.UserRole;
import com.tn.uib.uibechanges.service.UserRoleService;

@RestController
@RequestMapping(path = "roles")
public class UserRoleController {
	
	@Autowired
	private UserRoleService userRoleService;
	
	@PostMapping
	private ResponseEntity<?> addRole (@RequestBody UserRole role){
	/////////////////////////////////////////////////////////

	role.setPermissions(Set.of(new UserPermission("serveur",READ),new UserPermission("serveur",WRITE)));
	/////////////////////////////////////////////////////////
	
		return userRoleService.addRole(role);
	}
	
	@GetMapping
	private ResponseEntity<?> getRoles (){
		return userRoleService.getRoles();
	}
	
	@GetMapping(path = "{id}")
	private ResponseEntity<?> getRole (@PathVariable int id ){
		return userRoleService.getRole(id);
	}
	@PutMapping
	private ResponseEntity<?> updateRole (@RequestBody UserRole role ){
		return userRoleService.updateRole(role);

	}
	@DeleteMapping(path="{id}")
	private ResponseEntity<?> deleteUser (@PathVariable int id){
		return userRoleService.deleteRole(id);

	}

}
