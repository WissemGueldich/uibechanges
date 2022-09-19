package com.tn.uib.uibechanges.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPermissionRepository  extends JpaRepository<UserPermission, String> {
	Optional<UserPermission> findById(int id);	

	Optional<UserPermission> findByName(String name);
		
	Boolean existsById(int id);

	Boolean existsByName(String name);


}