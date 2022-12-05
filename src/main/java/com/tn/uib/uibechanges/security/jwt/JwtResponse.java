package com.tn.uib.uibechanges.security.jwt;

import java.util.Set;

public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private String username;
	private Set<String> authorities;
	
	public JwtResponse(String accessToken, String username, Set<String> authorities) {
		this.token = accessToken;
		this.username = username;
		this.authorities = authorities;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Set<String> getAuthorities() {
		return authorities;
	}
	
	public void setAuthorities(Set<String> authorities) {
		this.authorities = authorities;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


}