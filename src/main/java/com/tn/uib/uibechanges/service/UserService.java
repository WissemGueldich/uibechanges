package com.tn.uib.uibechanges.service;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tn.uib.uibechanges.model.User;
import com.tn.uib.uibechanges.model.UserRole;
import com.tn.uib.uibechanges.repository.UserRepository;
import com.tn.uib.uibechanges.repository.UserRoleRepository;

@Service
@Transactional
public class UserService implements UserDetailsService{
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user==null) {
			throw new UsernameNotFoundException("user not found");
		}
		Collection<SimpleGrantedAuthority> authorities = new HashSet<>();
		if (user.getRoles()!=null) {
			user.getRoles().forEach(role -> {
				role.getGrantedAuthorities().forEach(authority -> {
					authorities.add(authority);
				});
			});
		}
		return new org.springframework.security.core.userdetails.User( user.getUsername(), user.getPassword(),authorities);
	}

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserRoleRepository userRoleRepository;
	
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
		user.setPassword(encoder.encode(user.getPassword()));
		user.setCreated(new Date());
		user.setUpdated(new Date());
		user.setEnabled(true);
		if(user.getRoles()!=null){
			Set<UserRole> newRoles = new HashSet<>();
		user.getRoles().forEach(role -> {newRoles.add(userRoleRepository.findById(role.getId()));});
		user.setRoles(newRoles);
		}else {
			user.setRoles(Set.of(userRoleRepository.findByName("ROLE_USER")));
		}
		return new ResponseEntity<User>(userRepository.save(user), HttpStatus.CREATED);
	}
	
	public ResponseEntity<?> addRoleToUser(int userId, int roleId) {
		User user = userRepository.findById(userId);
		UserRole role = userRoleRepository.findById(roleId);
		user.getRoles().add(role);
		return new ResponseEntity<>(userRepository.save(user),HttpStatus.OK);
	}
	
	public ResponseEntity<?> removeRoleFromUser(int userId, int roleId) {
		User user = userRepository.findById(userId);
		UserRole role = userRoleRepository.findById(roleId);
		user.getRoles().remove(role);
		return new ResponseEntity<>(userRepository.save(user),HttpStatus.OK);
	}

	public ResponseEntity<?> getUsers() {
		return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
	}

	public ResponseEntity<?> getUser(int id) {
		return new ResponseEntity<>(userRepository.findById(id), HttpStatus.OK);
	}

	public ResponseEntity<?> getUser(String matricule) {
		return new ResponseEntity<>(userRepository.findByMatricule(matricule), HttpStatus.OK);
	}
	
	public ResponseEntity<?> changeState(Integer id, Boolean etat) {
	        User user = userRepository.findById(id).get();
	        user.setEnabled(etat);
	        return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
	}

	public ResponseEntity<?> updateUser(User user) {
		User oldUser = userRepository.findById(user.getId());
		if (!oldUser.getEmail().equals(user.getEmail()) && userRepository.existsByEmail(user.getEmail())) {
			return new ResponseEntity<>("Email already taken !", HttpStatus.FOUND);
		} else {
			if (!oldUser.getUsername().equals(user.getUsername()) && userRepository.existsByUsername(user.getUsername())) {
				return new ResponseEntity<>("Username already taken !", HttpStatus.FOUND);
				
			}else {
				if (!oldUser.getMatricule().equals(user.getMatricule()) && userRepository.existsByMatricule(user.getMatricule())) {
					return new ResponseEntity<>("Matricule already taken !", HttpStatus.FOUND);
				}
			}
		}
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
		oldUser.getRoles().clear();
		user.getRoles().forEach(role -> { oldUser.getRoles().add( userRoleRepository.findById(role.getId())); });
		return new ResponseEntity<>(userRepository.save(oldUser), HttpStatus.OK);
	}

	public ResponseEntity<?> deleteUser(int id) {
		User user = userRepository.findById(id);
		user.getRoles().clear();
		userRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}