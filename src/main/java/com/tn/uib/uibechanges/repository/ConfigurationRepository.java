package com.tn.uib.uibechanges.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tn.uib.uibechanges.model.Configuration;
import com.tn.uib.uibechanges.model.Server;

@Repository
//@EnableJpaRepositories
public interface ConfigurationRepository extends JpaRepository<Configuration, Integer>{
	Configuration findBySourceServerAndDestinationServer(Server sourceServer, Server destionationServer);
		
//	@Query(value = "SELECT c FROM users u JOIN user_profiles p JOIN profile_configurations c  WHERE c.automatic = :automatic and u.matricule=:matricule", 
//			nativeQuery = true)
//	Set<Configuration> findByAutomaticAndMatricule(boolean automatic, String matricule );
	

}
