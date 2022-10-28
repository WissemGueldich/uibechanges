package com.tn.uib.uibechanges.security;

public enum PermissionType {
	READ(":lire"),
	WRITE(":ecrire");

	private final String type;
	
	private PermissionType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
