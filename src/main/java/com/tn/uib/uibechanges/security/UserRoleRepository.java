package com.tn.uib.uibechanges.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, String> {
	Optional<UserRole> findById(int id);	

	Optional<UserRole> findByName(String name);
		
	Boolean existsById(int id);

	Boolean existsByName(String name);
	
}
