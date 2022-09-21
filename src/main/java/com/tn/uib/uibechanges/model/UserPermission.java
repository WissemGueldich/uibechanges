package com.tn.uib.uibechanges.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tn.uib.uibechanges.security.PermissionType;

@Entity
@Table(name = "permissions", uniqueConstraints = {@UniqueConstraint(columnNames = { "name" }) })

public class UserPermission {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	
	@NotBlank
	private String name;
	
	@Enumerated(EnumType.STRING)
	@JsonIgnore
	private PermissionType permissionType;
	
	@ManyToMany(fetch = FetchType.LAZY,
		      cascade = CascadeType.ALL,
		      mappedBy = "permissions")
	@JsonIgnore
	Set<UserRole> userRoles = new HashSet<>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public UserPermission() {

	}

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
