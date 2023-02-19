package com.tn.uib.uibechanges.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tn.uib.uibechanges.model.Day;
import com.tn.uib.uibechanges.repository.DayRepository;

@Service
@Transactional
public class DayService {

	@Autowired
	private DayRepository dayRepository;
	
	public boolean addDay(Day day) {
		dayRepository.save(day);
		return true;
	}
}
