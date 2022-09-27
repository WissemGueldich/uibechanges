package com.tn.uib.uibechanges.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tn.uib.uibechanges.model.UserPermission;
import com.tn.uib.uibechanges.model.UserRole;
import com.tn.uib.uibechanges.repository.UserPermissionRepository;
import com.tn.uib.uibechanges.repository.UserRoleRepository;

@Service
@Transactional
public class UserRoleService {

	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@Autowired
	private UserPermissionRepository userPermissionRepository;

	public ResponseEntity<?> addRole(UserRole role) {
		if (userRoleRepository.existsByName(role.getName())) {
			return new ResponseEntity<>("Role name already taken !", HttpStatus.FOUND);
		}
		Set<UserPermission> newPermissions = new HashSet<>();
		if(role.getPermissions()!=null){
			role.getPermissions().forEach(permission -> {newPermissions.add(userPermissionRepository.findById(permission.getId()));});
		}
		role.setPermissions(newPermissions);
		return new ResponseEntity<UserRole>(userRoleRepository.save(role), HttpStatus.CREATED);
	}

	public ResponseEntity<?> getRoles() {
		return new ResponseEntity<>(userRoleRepository.findAll(), HttpStatus.OK);
	}
	

	public ResponseEntity<?> getRole(int id) {
		return new ResponseEntity<>(userRoleRepository.findById(id), HttpStatus.OK);
	}
	
	public ResponseEntity<?> getRole(String name) {
		return new ResponseEntity<>(userRoleRepository.findByName(name), HttpStatus.OK);
	}

	public ResponseEntity<?> updateRole(UserRole role) {
		
		UserRole oldRole = userRoleRepository.findById(role.getId());
		if (!oldRole.getName().equals(role.getName()) && userRoleRepository.existsByName(role.getName())) {
			return new ResponseEntity<>("Role name already taken !", HttpStatus.FOUND);
		}
		oldRole.setName(role.getName());
		if(role.getPermissions() != null) {
			oldRole.getPermissions().clear();
			role.getPermissions().forEach(permission -> { oldRole.getPermissions().add( userPermissionRepository.findById(permission.getId())); });
		}
		
		return new ResponseEntity<>(userRoleRepository.save(oldRole), HttpStatus.OK);
	}

	public ResponseEntity<?> deleteRole(int id) {
		UserRole role =	userRoleRepository.findById(id);
		role.getPermissions().clear();
		userRoleRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	public ResponseEntity<?> addPermission(UserPermission permission) {
		if (userPermissionRepository.existsByName(permission.getName()) ) {
			return new ResponseEntity<>("permission already exists !", HttpStatus.FOUND);
		}
		return new ResponseEntity<>(userPermissionRepository.save(permission),HttpStatus.CREATED);
	}
	
	public ResponseEntity<?> deletePermission(int id) {
		if (!userPermissionRepository.existsById(id)) {
			return new ResponseEntity<>("permission does not exists !", HttpStatus.NOT_FOUND);
		}
		userPermissionRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	public ResponseEntity<?> addPermissionToRole(int roleId, int permissionId) {
		if(userRoleRepository.existsById(roleId) ) {
			return new ResponseEntity<>("role not found",HttpStatus.NOT_FOUND);
		}
		if(userPermissionRepository.existsById(permissionId)) {
			return new ResponseEntity<>("per;ission not found",HttpStatus.NOT_FOUND);
		}
		UserRole role = userRoleRepository.findById(roleId);
		UserPermission permission = userPermissionRepository.findById(permissionId);
		role.getPermissions().add(permission);
		return new ResponseEntity<>(userRoleRepository.save(role),HttpStatus.OK);
	}
	
	public ResponseEntity<?> removePermissionFromRole(int roleId, int permissionId) {
		if(userRoleRepository.existsById(roleId) ) {
			return new ResponseEntity<>("role not found",HttpStatus.NOT_FOUND);
		}
		if(userPermissionRepository.existsById(permissionId)) {
			return new ResponseEntity<>("per;ission not found",HttpStatus.NOT_FOUND);
		}
		UserRole role = userRoleRepository.findById(roleId);
		UserPermission permission = userPermissionRepository.findById(permissionId);
		role.getPermissions().remove(permission);
		return new ResponseEntity<>(userRoleRepository.save(role),HttpStatus.OK);
	}
	
}
