package com.tn.uib.uibechanges.security;

public enum PermissionType {
	READ(":read"),
	WRITE(":write");

	private final String type;
	
	private PermissionType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
