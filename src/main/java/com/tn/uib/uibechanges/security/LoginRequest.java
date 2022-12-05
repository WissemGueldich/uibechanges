package com.tn.uib.uibechanges.security;



import javax.validation.constraints.NotBlank;

public class LoginRequest {
	@NotBlank
	private String matricule;

	@NotBlank
	private String password;

	public String getMatricule() {
		return matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}