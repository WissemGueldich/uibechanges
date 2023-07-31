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

import com.tn.uib.uibechanges.model.Profile;
import com.tn.uib.uibechanges.model.User;
import com.tn.uib.uibechanges.model.UserRole;
import com.tn.uib.uibechanges.repository.ProfileRepository;
import com.tn.uib.uibechanges.repository.UserRepository;
import com.tn.uib.uibechanges.repository.UserRoleRepository;

@Service
@Transactional
public class UserService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Override
	public UserDetails loadUserByUsername(String matricule) throws UsernameNotFoundException {
		User user = userRepository.findByMatricule(matricule);
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
		return new org.springframework.security.core.userdetails.User( user.getMatricule(), user.getPassword(),authorities);
	}

	public ResponseEntity<?> addUser(User user) {
		if (userRepository.existsByEmail(user.getEmail())) {
			return new ResponseEntity<>("Email already taken !", HttpStatus.FOUND);
		}
		if (userRepository.existsByMatricule(user.getMatricule())) {
			return new ResponseEntity<>("Matricule already taken !", HttpStatus.FOUND);
		}
		
		user.setPassword(encoder.encode(user.getPassword()));
		user.setCreated(new Date());
		user.setUpdated(new Date());
		user.setEnabled(user.isEnabled());
		Set<UserRole> newRoles = new HashSet<>();
		newRoles.add(userRoleRepository.findByName("ROLE_SUPERVISOR"));
		if(user.getRoles()!=null){
			user.getRoles().forEach(role -> {newRoles.add(userRoleRepository.findById(role.getId()));});
		}
		user.setRoles(newRoles);
		Set<Profile> newProfiles = new HashSet<>();
		if(user.getProfiles()!=null){
			user.getProfiles().forEach(profile -> {newProfiles.add(profileRepository.findById(profile.getId()).get());});
		}
		user.setProfiles(newProfiles);
		
		return new ResponseEntity<User>(userRepository.save(user), HttpStatus.CREATED);
	}
	
	public ResponseEntity<?> addRolesToUser(int userId, Set<UserRole> roles) {
		if(!userRepository.existsById(userId) ) {
			return new ResponseEntity<>("user not found",HttpStatus.NOT_FOUND);
		}
		User oldUser = userRepository.findById(userId);
		oldUser.getRoles().addAll(roles);
		return new ResponseEntity<>(userRepository.save(oldUser),HttpStatus.OK);

	}
	
	public ResponseEntity<?> removeRolesFromUser(int userId, Set<UserRole> roles) {
		if(!userRepository.existsById(userId) ) {
			return new ResponseEntity<>("user not found",HttpStatus.NOT_FOUND);
		}

		User oldUser = userRepository.findById(userId);
		oldUser.getRoles().removeAll(roles);
		return new ResponseEntity<>(userRepository.save(oldUser),HttpStatus.OK);
	}

	public ResponseEntity<?> getUsers() {
		return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
	}

	public ResponseEntity<?> getUser(int id) {
		return new ResponseEntity<User>(userRepository.findById(id), HttpStatus.OK);
	}

	public ResponseEntity<?> getUser(String matricule) {
		return new ResponseEntity<User>(userRepository.findByMatricule(matricule), HttpStatus.OK);
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
		}else {
			if (!oldUser.getMatricule().equals(user.getMatricule()) && userRepository.existsByMatricule(user.getMatricule())) {
				return new ResponseEntity<>("Matricule already taken !", HttpStatus.FOUND);
			}
		}
		
		oldUser.setFirstName(user.getFirstName());
		oldUser.setLastName(user.getLastName());
		oldUser.setEmail(user.getEmail());
		oldUser.setUpdated(new Date());
		//uncomment to enable password changing
		/*if (user.getPassword()!=null && user.getPassword().length()>0 && !user.getPassword().equals(oldUser.getPassword())) {
			oldUser.setPassword(encoder.encode(user.getPassword()));
		}*/
		oldUser.setEnabled(user.isEnabled());
		oldUser.setMatricule(user.getMatricule());
		if (user.getRoles()!=null) {
			oldUser.getRoles().clear();
			user.getRoles().forEach(role -> { oldUser.getRoles().add( userRoleRepository.findById(role.getId())); });
		}
		if (user.getProfiles()!=null) {
			oldUser.getProfiles().clear();
			user.getProfiles().forEach(profile -> { oldUser.getProfiles().add( profileRepository.findById(profile.getId()).get());});
		}

		return new ResponseEntity<>(userRepository.save(oldUser), HttpStatus.OK);
	}

	public ResponseEntity<?> deleteUser(int id) {
		User user = userRepository.findById(id);
		user.getRoles().forEach(role->{role.getUsers().remove(user);});
		user.getRoles().clear();
		user.getProfiles().forEach(prof->{prof.getUsers().remove(user);});
		user.getProfiles().clear();
		userRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	public ResponseEntity<?> addProfileToUser(int userId, Set<Profile> profiles) {
		if(!userRepository.existsById(userId) ) {
			return new ResponseEntity<>("user not found",HttpStatus.NOT_FOUND);
		}
		User oldUser = userRepository.findById(userId);
		oldUser.getProfiles().addAll(profiles);
		return new ResponseEntity<>(userRepository.save(oldUser),HttpStatus.OK);

	}
	

}