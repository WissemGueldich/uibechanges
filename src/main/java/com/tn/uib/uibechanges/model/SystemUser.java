package com.tn.uib.uibechanges.model;

import java.util.HashSet;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table(name = "system_users")
public class SystemUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY )
    @JoinTable(name = "system_user_servers", 
    			joinColumns = @JoinColumn(name = "system_user_id"), 
				inverseJoinColumns = @JoinColumn(name = "server_id"))
    private Set<Server> servers=new HashSet<Server>();

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,CascadeType.MERGE}, orphanRemoval=false, mappedBy = "sourceUser")
    @JsonIgnore
    private Set<Configuration> configurationsAsSource;
    
	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,CascadeType.MERGE}, orphanRemoval=false, mappedBy = "destinationUser")
	@JsonIgnore
    private Set<Configuration> configurationsAsDestination;

    public SystemUser() {
    }

    public SystemUser(Integer id) {
        this.id = id;
    }
    
    public SystemUser(String libelle, String login, String password, boolean enabled) {
		this.libelle = libelle;
		this.login = login;
		this.password = password;
		this.enabled = enabled;
	}

	public SystemUser(String libelle, String login, String password, boolean enabled, Set<Server> servers) {
		this.libelle = libelle;
		this.login = login;
		this.password = password;
		this.enabled = enabled;
		this.servers = servers;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Server> getServers() {
        return servers;
    }

    public void setServers(Set<Server> servers) {
        this.servers = servers;
    }

    public Set<Configuration> getConfigurationsAsSource() {
        return configurationsAsSource;
    }

    public void setConfigurationsAsSource(Set<Configuration> configurationsAsSource) {
        this.configurationsAsSource = configurationsAsSource;
    }

    public Set<Configuration> getConfigurationsAsDestination() {
        return configurationsAsDestination;
    }

    public void setConfigurationsAsDestination(Set<Configuration> configurationsAsDestination) {
        this.configurationsAsDestination = configurationsAsDestination;
    }
    
    public int hashCode() {
        Integer hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    public boolean equals(Object object) {
        if (!(object instanceof SystemUser)) {
            return false;
        }
        SystemUser other = (SystemUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public String toString() {
        return "com.tn.uib.uibechanges.dal.entities.SystemUser[ id=" + id + " ]";
    }

}
