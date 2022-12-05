package com.tn.uib.uibechanges.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tn.uib.uibechanges.model.Configuration;
import com.tn.uib.uibechanges.model.Server;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Integer>{
	
	Set<Configuration> findBySourceServer(Server sourceServer);
	Set<Configuration> findByDestinationServer(Server sourceServer);
	Configuration findById(int id);
	

}
