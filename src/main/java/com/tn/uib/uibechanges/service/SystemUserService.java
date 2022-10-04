package com.tn.uib.uibechanges.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tn.uib.uibechanges.model.SystemUser;
import com.tn.uib.uibechanges.repository.SystemUserRepository;

@Service
public class SystemUserService {
	
	@Autowired
	SystemUserRepository systemUserRepository;
	
	public ResponseEntity<?> addSystemUser(SystemUser systemUser) {
		return new ResponseEntity<>(systemUserRepository.save(systemUser),HttpStatus.CREATED);
	}

}
