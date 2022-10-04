package com.tn.uib.uibechanges.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "applications")
public class Application {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
	
    @Column(name = "identifier")
    private String identifier;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "address")
    private String address;
    
    @OneToMany(mappedBy = "application", fetch = FetchType.EAGER)
    @JsonIgnore
	private Set<ApplicationConfiguration> configurations;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "application")
    @JsonIgnore
    private Set<ApplicationExecution> applicationExecutions;

    public Application() {
    }

    public Application(Integer id) {
        this.id = id;
    }

    public Application( String identifier, String name, String address) {
        this.identifier = identifier;
        this.name = name;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public Set<ApplicationConfiguration> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(Set<ApplicationConfiguration> configurations) {
        this.configurations = configurations;
    }

    public Set<ApplicationExecution> getApplicationExecutions() {
        return applicationExecutions;
    }

    public void setApplicationExecutions(Set<ApplicationExecution> applicationExecutions) {
        this.applicationExecutions = applicationExecutions;
    }
    
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Application)) {
            return false;
        }
        Application other = (Application) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public String toString() {
        return "com.tn.uib.uibechanges.dal.entities.Application[ id=" + id + " ]";
    }
}
