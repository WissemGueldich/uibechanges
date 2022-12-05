package com.tn.uib.uibechanges.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tn.uib.uibechanges.model.UserPermission;

@Repository
public interface UserPermissionRepository  extends JpaRepository<UserPermission, Integer> {
	UserPermission findById(int id);	

	UserPermission findByName(String name);
		
	Boolean existsById(int id);

	Boolean existsByName(String name);


}