package com.tn.uib.uibechanges.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tn.uib.uibechanges.model.Transfer;


@Repository
public interface TransferRepository extends JpaRepository<Transfer, Integer>{

	Transfer findById(int id);
	
	Boolean existsById(int id);
	
}