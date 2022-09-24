package com.tn.uib.uibechanges.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tn.uib.uibechanges.model.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer>{
	
	List<Profile> findAll();

	Profile findById(int id);
	
	Profile findByLibelle(String libelle);

	Boolean existsById(int id);
	
	Boolean existsByLibelle(String libelle);
	
}
