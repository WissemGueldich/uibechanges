package com.tn.uib.uibechanges.security;

import static com.tn.uib.uibechanges.security.UserPermission.SERVEUR_READ;
import static com.tn.uib.uibechanges.security.UserPermission.SERVEUR_WRITE;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.google.common.collect.Sets;

public enum UserRole {
	USER(Sets.newHashSet()),
	ADMIN(Sets.newHashSet(SERVEUR_READ,SERVEUR_WRITE)),
	NWADMIN(Sets.newHashSet(SERVEUR_READ));
	
	
	private final Set<UserPermission> permissions;
	
	UserRole(Set<UserPermission> permissions) {
		this.permissions=permissions;
	}

	public Set<UserPermission> getPermissions() {
		return permissions;
	}	

	public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
		Set<SimpleGrantedAuthority> permissions=getPermissions().stream()
			.map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
			.collect(Collectors.toSet());		
		permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
		return permissions;
	}
}
