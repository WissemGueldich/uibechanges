package com.tn.uib.uibechanges.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "servers", uniqueConstraints = {@UniqueConstraint(columnNames = { "address" }) })
public class Server {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String address;

    private int port;

    private String libelle;
    
    private String mainAddress;

	private String secondaryAddress;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "sourceServer")
    @JsonIgnore
    private Set<Configuration> sourceConfigurations;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "destinationServer")
    @JsonIgnore
    private Set<Configuration> destionationConfigurations;
    
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "servers")
    @JsonIgnore
    private Set<SystemUser> systemUsers;

	public Server() {
	}
    
	public Server(String address, int port, String libelle) {
		this.address = address;
		this.port = port;
		this.libelle = libelle;
	}

	public Server(String address, int port, String libelle, String mainAddress, String secondaryAddress) {
		this.address = address;
		this.port = port;
		this.libelle = libelle;
		this.mainAddress = mainAddress;
		this.secondaryAddress = secondaryAddress;
	}

	public int getId() {
		return id;
	}
    
	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getSecondaryAddress() {
		return secondaryAddress;
	}

	public void setSecondaryAddress(String secondaryAddress) {
		this.secondaryAddress = secondaryAddress;
	}

	public String getMainAddress() {
		return mainAddress;
	}

	public void setMainAddress(String mainAddress) {
		this.mainAddress = mainAddress;
	}
	
    public Set<SystemUser> getSystemUsers() {
		return systemUsers;
	}

	public void setSystemUsers(Set<SystemUser> systemUsers) {
		this.systemUsers = systemUsers;
	}

	public Set<Configuration> getSourceConfigurations() {
		return sourceConfigurations;
	}
	public void setSourceConfigurations(Set<Configuration> sourceConfigurations) {
		this.sourceConfigurations = sourceConfigurations;
	}

	public Set<Configuration> getDestionationConfigurations() {
		return destionationConfigurations;
	}

	public void setDestionationConfigurations(Set<Configuration> destionationConfigurations) {
		this.destionationConfigurations = destionationConfigurations;
	}
	
    public void changeToSecodaryAddress(boolean secondary){
        if(secondary){
            this.address = this.secondaryAddress;
        } else {
            this.address = this.mainAddress;
        }
    }

}
