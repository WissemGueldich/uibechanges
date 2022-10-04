package com.tn.uib.uibechanges.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tn.uib.uibechanges.model.ApplicationConfiguration;
import com.tn.uib.uibechanges.model.ApplicationConfigurationPK;

@Repository
public interface ApplicationConfigurationRepository extends JpaRepository<ApplicationConfiguration, ApplicationConfigurationPK> {

}
