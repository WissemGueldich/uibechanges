package com.tn.uib.uibechanges.controller;

import java.util.Map;
import java.util.Set;

import com.tn.uib.uibechanges.model.Job;

public class JobConfigs {
	
	private Job job;
	private Set<Integer> configurations;
	private Map<String, String> configurationsMap;
	
	
	public JobConfigs() {
	}
	public JobConfigs(Job job, Set<Integer> configurations) {
		this.job = job;
		this.configurations = configurations;
	}
	public Job getJob() {
		return job;
	}
	public void setJob(Job job) {
		this.job = job;
	}
	public Set<Integer> getConfigurations() {
		return configurations;
	}
	public void setConfigurations(Set<Integer> configurations) {
		this.configurations = configurations;
	}
	public Map<String, String> getConfigurationsMap() {
		return configurationsMap;
	}
	public void setConfigurationsMap(Map<String, String> configurationsMap) {
		this.configurationsMap = configurationsMap;
	}
	
}
