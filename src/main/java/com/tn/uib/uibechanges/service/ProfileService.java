package com.tn.uib.uibechanges.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tn.uib.uibechanges.model.Configuration;
import com.tn.uib.uibechanges.model.Profile;
import com.tn.uib.uibechanges.repository.ConfigurationRepository;
import com.tn.uib.uibechanges.repository.ProfileRepository;

@Service
@Transactional
public class ProfileService {
	
	
	@Autowired
	ProfileRepository profileRepository;
	
	@Autowired
	ConfigurationRepository configurationRepository;
	
	public ResponseEntity<?> addProfile(Profile profile) {
		Set<Configuration> newConfigs = new HashSet<>();
		if(profile.getConfigurations() != null) {
			profile.getConfigurations().forEach(config -> {newConfigs.add(configurationRepository.findById(config.getId()).get());});
		}
		profile.setConfigurations(newConfigs);
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
		if (profile.getConfigurations()!=null) {
			oldProfile.getConfigurations().clear();
			profile.getConfigurations().forEach(config -> { oldProfile.getConfigurations().add( configurationRepository.findById(config.getId()).get());});
		}
		oldProfile.setLibelle(profile.getLibelle());
		return new ResponseEntity<>(profileRepository.save(oldProfile), HttpStatus.OK);
	}

	
	public ResponseEntity<?> deleteProfile(int id) {
		Profile profile = profileRepository.findById(id);
		profile.getUsers().forEach(user->{
			user.getProfiles().remove(profile);
		});
		profile.getConfigurations().clear();
		profileRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
    public ResponseEntity<?> addConfigurationToProfile(int profileId, Set<Configuration> configurations) {
    	if(!profileRepository.existsById(profileId) ) {
			return new ResponseEntity<>("profile not found",HttpStatus.NOT_FOUND);
		}
    	Profile oldProfile = profileRepository.findById(profileId);
    	oldProfile.getConfigurations().addAll(configurations);
        return new ResponseEntity<>(profileRepository.save(oldProfile), HttpStatus.OK);

    }
    
    public ResponseEntity<?> getProfilesByUserId(int id) {
		return new ResponseEntity<>(profileRepository.findByUsersId(id), HttpStatus.OK);
	}
    
	public ResponseEntity<?> getUsersByProfileId(int id) {
		Profile profile = profileRepository.findById(id);
		return new ResponseEntity<>(profile.getUsers(),HttpStatus.OK);
	}



}
