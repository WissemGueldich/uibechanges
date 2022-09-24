package com.tn.uib.uibechanges.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tn.uib.uibechanges.model.Profile;
import com.tn.uib.uibechanges.repository.ProfileRepository;

@Service
@Transactional
public class ProfileService {
	
	
	@Autowired
	ProfileRepository profileRepository;
	
	public ResponseEntity<?> addProfile(Profile profile) {
		
		return new ResponseEntity<Profile>(profileRepository.save(profile), HttpStatus.CREATED);
	}
	
	public ResponseEntity<?> getProfiles() {
		
		return new ResponseEntity<>(profileRepository.findAll(), HttpStatus.CREATED);
	}
	
	public ResponseEntity<?> getProfile(int id) {
		
		return new ResponseEntity<Profile>(profileRepository.findById(id), HttpStatus.CREATED);
	}
	
	public ResponseEntity<?> updateProfile(Profile profile) {
		Profile oldProfile = profileRepository.findById(profile.getId()).get();
		//TODO update all relational attributes before update
		oldProfile.setLibelle(profile.getLibelle());
		return new ResponseEntity<>(profileRepository.save(oldProfile), HttpStatus.OK);
	}

	
	public ResponseEntity<?> deleteProfile(int id) {
		
		//Profile profile = profileRepository.findById(id);
		//TODO clear all relational attributes before delete
		profileRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
