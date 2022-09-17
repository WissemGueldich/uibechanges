package com.tn.uib.uibechanges.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = { "email" }) })
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_user")
	private int id;

	@NotBlank
	@Size(max = 20)
	private String username;
	
	@NotBlank
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
	
	//private Set<? extends GrantedAuthority> grantedAuthorities;
	
    @Column(name = "is_enabled")
    private boolean isEnabled;

	public User(int id, @NotBlank @Size(max = 20) String username, @NotBlank @Size(max = 120) String password, boolean isEnabled,
			@NotBlank @Size(max = 50) @Email String email, @NotBlank @Size(max = 120) String matricule,
			@NotBlank @Size(min = 4, max = 40) String firstName, @NotBlank @Size(min = 4, max = 40) String lastName,
			Date created, Date updated
			//,Set<? extends GrantedAuthority> grantedAuthorities
			) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.isEnabled = isEnabled;
		this.email = email;
		this.matricule = matricule;
		this.firstName = firstName;
		this.lastName = lastName;
		this.created = created;
		this.updated = updated;
		//this.grantedAuthorities = grantedAuthorities;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public String getMatricule() {
		return matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

//	public void setGrantedAuthorities(Set<? extends GrantedAuthority> grantedAuthorities) {
//		this.grantedAuthorities = grantedAuthorities;
//	}

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


//	@ManyToMany(fetch = FetchType.LAZY)
//	@JoinTable(	name = "user_roles", 
//				joinColumns = @JoinColumn(name = "user_id"), 
//				inverseJoinColumns = @JoinColumn(name = "role_id"))
//	private Set<Role> roles = new HashSet<>();

	public User() {
	}

	public User( String email, String matricule) {
		this.email = email;
		this.matricule = matricule;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}



	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


//	public Set<? extends GrantedAuthority> getGrantedAuthorities () {
//		return grantedAuthorities;
//	}
//
//	public void setRoles(List<? extends GrantedAuthority> grantedAuthorities) {
//		this.grantedAuthorities = grantedAuthorities;
//	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", matricule=" + matricule
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", created=" + created + ", updated="
				+ updated + 
				//", authorities=" + grantedAuthorities +
				"]";
	}
	
}
