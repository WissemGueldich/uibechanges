package com.tn.uib.uibechanges.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tn.uib.uibechanges.model.Day;
import com.tn.uib.uibechanges.repository.DayRepository;

@Service
@Transactional
public class DayService {
	
	@Autowired
	DayRepository dayRepository;
	 
    public ResponseEntity<?> addDay(Day day) {
        return new ResponseEntity<>( dayRepository.save(day),HttpStatus.CREATED);
    }

    
    public ResponseEntity<?> getDay(Integer id) {
    return new ResponseEntity<>(dayRepository.findById(id),HttpStatus.OK);
    }

    
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(dayRepository.findAll(),HttpStatus.OK);
    }
    
    public ResponseEntity<?> updateDay(Day day) {
        Day oldDay = dayRepository.findById(day.getId()).get();
        oldDay.setName(day.getName());
        return new ResponseEntity<>(dayRepository.save(oldDay),HttpStatus.OK);
    }

    
    public ResponseEntity<?> deleteDay(Day day) {
        dayRepository.deleteById(day.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
