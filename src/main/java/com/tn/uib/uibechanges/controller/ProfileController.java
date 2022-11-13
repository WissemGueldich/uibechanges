package com.tn.uib.uibechanges.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tn.uib.uibechanges.model.Profile;
import com.tn.uib.uibechanges.service.ProfileService;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequestMapping(path = "/api/profiles")
public class ProfileController {
	@Autowired
	private ProfileService profileService;
	
	@PostMapping
	private ResponseEntity<?> addProfile (@RequestBody Profile profile){
		return profileService.addProfile(profile);
	}
	
	@GetMapping
	private ResponseEntity<?> getProfiles (){
		return profileService.getProfiles();
	}
	
	@GetMapping(path = "{id}")
	private ResponseEntity<?> getProfile (@PathVariable int id ){
		return profileService.getProfile(id);
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<?> getProfilesByUserId(@PathVariable int id) {
		return profileService.getProfilesByUserId(id);
	};
	
	@PutMapping
	private ResponseEntity<?> updateProfile (@RequestBody Profile profile ){
		return profileService.updateProfile(profile);
	}
	
	@DeleteMapping(path="{id}")
	private ResponseEntity<?> deleteProfile (@PathVariable int id){
		return profileService.deleteProfile(id);

	}
}
