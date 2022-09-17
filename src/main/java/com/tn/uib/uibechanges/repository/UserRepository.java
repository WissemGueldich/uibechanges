package com.tn.uib.uibechanges.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tn.uib.uibechanges.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	Optional<User> findById(int id);
	
	Optional<User> findByUsername(String username);
	
	Optional<User> findByEmail(String email);

	Optional<User> findByMatricule(String matricule);
	
	Boolean existsById(int id);

	Boolean existsByUsername(String username);
	
	Boolean existsByEmail(String email);

	Boolean existsByMatricule(String matricule);


}