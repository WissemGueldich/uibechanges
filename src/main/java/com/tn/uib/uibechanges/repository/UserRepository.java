package com.tn.uib.uibechanges.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tn.uib.uibechanges.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	User findById(int id);
		
	User findByEmail(String email);

	User findByMatricule(String matricule);
	
	Boolean existsById(int id);
	
	Boolean existsByEmail(String email);

	Boolean existsByMatricule(String matricule);


}