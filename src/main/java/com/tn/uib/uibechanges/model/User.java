package com.tn.uib.uibechanges.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = { "email","matricule" }) })
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Size(max = 120)
	private String password;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotBlank
	@Size(max = 20)
	private String matricule;
	
	@NotBlank
	@Size(min = 4, max = 40)
	private String firstName;
	
	@NotBlank
	@Size(min = 4, max = 40)
	private String lastName;
	
	@Column(name = "created_user")
	@JsonFormat(pattern = "yyyy/MM/dd")
	private Date created;

	@Column(name = "updated_user")
	@JsonFormat(pattern = "yyyy/MM/dd")
	private Date updated;
	
    private boolean isEnabled;
	
	@ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.EAGER)
	@JoinTable(	name = "user_roles", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<UserRole> roles;
	
    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.EAGER)
    @JoinTable(name = "user_profiles", 
    			joinColumns = @JoinColumn(name = "user_id"), 
    			inverseJoinColumns = @JoinColumn(name = "profile_id"))
    private Set<Profile> profiles;

	public User() {

	}

	public User(@NotBlank @Size(max = 120) String matricule, @Size(max = 120) String password,
			@NotBlank @Size(max = 50) @Email String email, @NotBlank @Size(min = 4, max = 40) String firstName, 
			@NotBlank @Size(min = 4, max = 40) String lastName, boolean isEnabled, Set<UserRole> roles, Set<Profile> profiles) {
		this.password = password;
		this.isEnabled = isEnabled;
		this.email = email;
		this.matricule = matricule;
		this.firstName = firstName;
		this.lastName = lastName;
		this.roles =roles;
		this.profiles = profiles;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMatricule() {
		return matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public Set<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<UserRole> roles) {
		this.roles = roles;
	}

	public Set<Profile> getProfiles() {
		return profiles;
	}

	public void setProfiles(Set<Profile> profiles) {
		this.profiles = profiles;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
