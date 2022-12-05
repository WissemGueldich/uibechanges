package com.tn.uib.uibechanges.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tn.uib.uibechanges.model.GlobalSetting;

@Repository
public interface GlobalSettingRepository extends JpaRepository<GlobalSetting, Integer>{
	GlobalSetting findBySetting(String setting);

}
