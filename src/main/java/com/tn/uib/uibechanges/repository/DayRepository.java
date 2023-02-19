package com.tn.uib.uibechanges.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tn.uib.uibechanges.model.Day;

@Repository
public interface DayRepository extends JpaRepository<Day, Integer> {
	Day findById(int id);
	boolean existsById(int id);
}
