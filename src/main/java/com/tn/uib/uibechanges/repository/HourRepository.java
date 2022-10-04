package com.tn.uib.uibechanges.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tn.uib.uibechanges.model.Hour;

@Repository
public interface HourRepository extends JpaRepository<Hour, Integer>{

}
