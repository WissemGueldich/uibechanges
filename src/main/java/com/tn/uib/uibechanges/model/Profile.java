package com.tn.uib.uibechanges.model;

import java.util.HashSet;
import java.util.Set;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "profiles")
public class Profile {
	
   

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String libelle;
    
    
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "profile_configurations", 
    		joinColumns = @JoinColumn(name = "profile_id"), 
			inverseJoinColumns = @JoinColumn(name = "configuration_id"))
    private Set<Configuration> configurations;
    
    @ManyToMany(fetch = FetchType.LAZY, 
    		cascade = CascadeType.ALL, 
    		mappedBy = "profiles")
    @JsonIgnore
    private Set<User> users = new HashSet<>();


	public Profile() {
    }

    public Profile(Integer id) {
        this.id = id;
    }

    public Profile(Integer id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }
    
    public Profile( String libelle) {
        this.libelle = libelle;
    }
    
    public Profile(String libelle, Set<Configuration> configurations) {
		this.libelle = libelle;
		this.configurations = configurations;
	}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
    
    public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

    public Set<Configuration> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(Set<Configuration> configurations) {
        this.configurations = configurations;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Profile)) {
            return false;
        }
        Profile other = (Profile) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tn.uib.uibechanges.dal.entities.Profile[ id=" + id + " ]";
    }

}
