package com.tn.uib.uibechanges.controller;

public class SearchRequest {
	
	public SearchRequest(String matricule, Boolean automatic) {
		this.matricule = matricule;
		this.automatic = automatic;
	}
	public String getMatricule() {
		return matricule;
	}
	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}
	public Boolean getAutomatic() {
		return automatic;
	}
	public void setAutomatic(Boolean automatic) {
		this.automatic = automatic;
	}
	private String matricule;
	private Boolean automatic;
}
