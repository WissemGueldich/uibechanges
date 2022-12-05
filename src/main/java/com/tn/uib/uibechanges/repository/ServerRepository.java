package com.tn.uib.uibechanges.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tn.uib.uibechanges.model.Server;

@Repository
public interface ServerRepository extends JpaRepository<Server, Integer>{

	 List<Server> findAll();
	 
     Server findById(int id);
     Server findByAddress(String address);
     Server findByPort(int port);
     Server findByLibelle(String libelle);
     
     Boolean existsById(int id);
     Boolean existsByAddress(String address);
     Boolean existsByLibelle(String libelle);
     
     
     
}







