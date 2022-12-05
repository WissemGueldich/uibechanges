package com.tn.uib.uibechanges.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tn.uib.uibechanges.model.ConfigurationJob;
import com.tn.uib.uibechanges.model.ConfigurationJobPK;

@Repository
public interface ConfigurationJobRepository extends JpaRepository<ConfigurationJob, ConfigurationJobPK>{

}
