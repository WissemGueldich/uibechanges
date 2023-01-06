package com.tn.uib.uibechanges.model;

import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "roles", uniqueConstraints = {@UniqueConstraint(columnNames = { "name" })})
public class UserRole {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank
	private String name;
	
	@ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY)
	@JoinTable(	name = "role_permissions", 
				joinColumns = @JoinColumn(name = "role_id"), 
				inverseJoinColumns = @JoinColumn(name = "permission_id"))
	private Set<UserPermission> permissions;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,CascadeType.MERGE}, mappedBy = "roles")
	@JsonIgnore
	private Set<User> users;
	
	public UserRole(@NotBlank String name, Set<UserPermission> permissions) {
		this.name = name;
		this.permissions = permissions;
	}
	
	public UserRole(@NotBlank String name) {
		this.name = name;
	}


	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public void setPermissions(Set<UserPermission> permissions) {
		this.permissions = permissions;
	}

	public Set<UserPermission> getPermissions() {
		return permissions;
	}	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public UserRole() {}

	@JsonIgnore
	public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
		Set<SimpleGrantedAuthority> permissions=getPermissions().stream()
			.map(permission -> new SimpleGrantedAuthority(permission.getName()))
			.collect(Collectors.toSet());		
		permissions.add(new SimpleGrantedAuthority(this.getName()));
		return permissions;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
