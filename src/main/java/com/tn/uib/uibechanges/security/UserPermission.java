package com.tn.uib.uibechanges.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "permissions", uniqueConstraints = {@UniqueConstraint(columnNames = { "name" }) })

public class UserPermission {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	
	@NotBlank
	private String name;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private PermissionType permissionType;

	public UserPermission(@NotBlank String name, PermissionType permissionType) {
		this.name = name + permissionType.getType();
		this.permissionType = permissionType;
	}
	
	public PermissionType getPermissionType() {
		return permissionType;
	}

	public void setPermissionType(PermissionType permissionType) {
		this.permissionType = permissionType;
	}

	public String getName() {
		return name ;
	}

	public void setName(String name) {
		this.name = name + permissionType.getType();
	}

	
	

}
