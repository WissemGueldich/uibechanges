package com.tn.uib.uibechanges.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tn.uib.uibechanges.model.Server;
import com.tn.uib.uibechanges.model.SystemUser;

@Repository
public interface SystemUserRepository extends JpaRepository<SystemUser, Integer>{
	
	Set<SystemUser> findByServers(Server Server);
	
	Set<SystemUser> findByServersId(int ServerId);


}
