package com.tn.uib.uibechanges.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tn.uib.uibechanges.model.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer>{
	UserRole findById(int id);	

	UserRole findByName(String name);
		
	Boolean existsById(int id);

	Boolean existsByName(String name);

	void deleteById(int id);
	
}
