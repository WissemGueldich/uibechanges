package com.tn.uib.uibechanges.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tn.uib.uibechanges.model.GlobalSetting;
import com.tn.uib.uibechanges.repository.GlobalSettingRepository;

@Service
public class GlobalSettingService {

	@Autowired
	private GlobalSettingRepository globalSettingRepository;

    
    public ResponseEntity<?> addGlobalSetting(GlobalSetting globalSetting) {
        return new ResponseEntity<>(globalSettingRepository.save(globalSetting),HttpStatus.CREATED);
    }

    
    public ResponseEntity<?> getGlobalSetting(String setting) {
        return new ResponseEntity<>(globalSettingRepository.findBySetting(setting),HttpStatus.OK);
    }

    
    public ResponseEntity<?> updateGlobalSetting(GlobalSetting globalSetting) { 
        GlobalSetting globalSettingToEdit = globalSettingRepository.findBySetting(globalSetting.getSetting());
        globalSettingToEdit.setSetting(globalSetting.getSetting());
        globalSettingToEdit.setValue(globalSetting.getValue());
        return new ResponseEntity<>(globalSettingRepository.save(globalSettingToEdit),HttpStatus.OK);
    }

    
    public ResponseEntity<?> getALLGlobalSetting() {
        return new ResponseEntity<>(globalSettingRepository.findAll(),HttpStatus.OK);    
    }

    
    public ResponseEntity<?> deleteGlobalSetting(GlobalSetting globalSetting) {
        globalSettingRepository.delete(globalSetting);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
