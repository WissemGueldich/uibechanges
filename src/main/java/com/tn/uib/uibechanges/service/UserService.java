package com.tn.uib.uibechanges.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tn.uib.uibechanges.model.User;
import com.tn.uib.uibechanges.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	PasswordEncoder encoder;

	public ResponseEntity<?> addUser(User user) {
		if (userRepository.existsByEmail(user.getEmail())) {
			return new ResponseEntity<>("Email already taken !", HttpStatus.FOUND);
		} else {
			if (userRepository.existsByUsername(user.getUsername())) {
				return new ResponseEntity<>("Username already taken !", HttpStatus.FOUND);
				
			}else {
				if (userRepository.existsByMatricule(user.getMatricule())) {
					return new ResponseEntity<>("Matricule already taken !", HttpStatus.FOUND);
				}
			}
		}
		user.setCreated(new Date());
		user.setUpdated(new Date());
		return new ResponseEntity<User>(userRepository.save(user), HttpStatus.CREATED);
	}

	public ResponseEntity<?> getUsers() {
		return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
	}

	public ResponseEntity<?> getUser(int id) {
		return new ResponseEntity<>(userRepository.findById(id).get(), HttpStatus.OK);
	}

	public ResponseEntity<?> getUser(String matricule) {
		return new ResponseEntity<>(userRepository.findByMatricule(matricule).get(), HttpStatus.OK);
	}
	
	public ResponseEntity<?> changeState(Integer id, Boolean etat) {
	        User user = userRepository.findById(id).get();
	        user.setEnabled(etat);
	        return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
	}

	public ResponseEntity<?> updateUser(User user) {
		User oldUser = userRepository.findById(user.getId()).get();
		oldUser.setUsername(user.getUsername());
		oldUser.setFirstName(user.getFirstName());
		oldUser.setLastName(user.getLastName());
		oldUser.setEmail(user.getEmail());
		oldUser.setUpdated(new Date());
		if (!user.getPassword().equals(oldUser.getPassword())) {
			oldUser.setPassword(encoder.encode(user.getPassword()));
		}
		oldUser.setEnabled(user.isEnabled());
		oldUser.setMatricule(user.getMatricule());
		return new ResponseEntity<>(userRepository.save(oldUser), HttpStatus.OK);
	}

	public ResponseEntity<?> deleteUser(int id) {
		userRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}