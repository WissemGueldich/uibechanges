package com.tn.uib.uibechanges.repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tn.uib.uibechanges.model.Transfer;


@Repository
public interface TransferRepository extends JpaRepository<Transfer, Integer>{

	Transfer findById(int id);
	
	Boolean existsById(int id);
	
	void deleteByDateBetween(Date date, Date date2);
	
	Set<Transfer> findAllByDateBetween(java.util.Date date, java.util.Date date2);
	
}