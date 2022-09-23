package com.tn.uib.uibechanges.security;

import java.util.Set;

import javax.validation.constraints.*;
 
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;
 
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    
    private Set<String> role;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
    
    @NotBlank(message = "First name must not be empty")
	@Size(min = 4, max = 40, message = "Last name must contain more than 4 characters")
	private String lastName;
	@NotBlank(message = "First name must not be empty")
	@Size(min = 4, max = 40, message = "First name must contain more than 4 characters")
	private String firstName;

	private Long phone;

	private String addresse;

  
    public SignupRequest(@NotBlank @Size(min = 3, max = 20) String username,
			@NotBlank @Size(max = 50) @Email String email, Set<String> role,
			@NotBlank @Size(min = 6, max = 40) String password,
			@NotBlank(message = "First name must not be empty") @Size(min = 4, max = 40, message = "Last name must contain more than 4 characters") String lastName,
			@NotBlank(message = "First name must not be empty") @Size(min = 4, max = 40, message = "First name must contain more than 4 characters") String firstName,
			Long phone, String addresse) {
		super();
		this.username = username;
		this.email = email;
		this.role = role;
		this.password = password;
		this.lastName = lastName;
		this.firstName = firstName;
		this.phone = phone;
		this.addresse = addresse;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

	public String getAddresse() {
		return addresse;
	}

	public void setAddresse(String addresse) {
		this.addresse = addresse;
	}
	public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Set<String> getRole() {
      return this.role;
    }
    
    public void setRole(Set<String> role) {
      this.role = role;
    }
}