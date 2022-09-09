package com.tn.uib.uibechanges.security;

public enum UserPermission {
	
	SERVEUR_READ("serveur:read"),
	SERVEUR_WRITE("serveur:write");
	
	private final String permission;

	private UserPermission(String permission) {
		this.permission = permission;
	}

	public String getPermission() {
		return permission;
	}
	

}
