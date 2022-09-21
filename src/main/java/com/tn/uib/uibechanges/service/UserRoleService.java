package com.tn.uib.uibechanges.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	@Autowired
	PasswordEncoder encoder;

	public ResponseEntity<?> addRole(UserRole role) {
		if (userRoleRepository.existsByName(role.getName())) {
			return new ResponseEntity<>("Role name already taken !", HttpStatus.FOUND);
		} 

		return new ResponseEntity<UserRole>(userRoleRepository.save(role), HttpStatus.CREATED);
	}

	public ResponseEntity<?> getRoles() {
		return new ResponseEntity<>(userRoleRepository.findAll(), HttpStatus.OK);
	}
	
	//TODO methods to add/remove permissions

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
		oldRole.getPermissions().clear();
		role.getPermissions().forEach(permission -> { oldRole.getPermissions().add( userPermissionRepository.findById(permission.getId())); });
		
		return new ResponseEntity<>(userRoleRepository.save(oldRole), HttpStatus.OK);
	}

	public ResponseEntity<?> deleteRole(int id) {
		userRoleRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
