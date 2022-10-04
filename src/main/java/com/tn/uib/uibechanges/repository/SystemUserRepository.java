package com.tn.uib.uibechanges.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tn.uib.uibechanges.model.SystemUser;

@Repository
public interface SystemUserRepository extends JpaRepository<SystemUser, Integer>{

}
