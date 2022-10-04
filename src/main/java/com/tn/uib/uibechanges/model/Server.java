package com.tn.uib.uibechanges.model;

import java.util.Set;

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

    @OneToMany(fetch = FetchType.LAZY, cascade = {}, mappedBy = "sourceServer")
    @JsonIgnore
    private Set<Configuration> sourceConfigurations;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = {}, mappedBy = "destinationServer")
    @JsonIgnore
    private Set<Configuration> destionationConfigurations;
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = {}, mappedBy = "servers")
    @JsonIgnore
    private Set<SystemUser> systemUsers;

	public Server() {
	}
    
	public Server(String address, int port, String libelle, String secondaryAddress,
			String mainAddress) {
		this.address = address;
		this.port = port;
		this.libelle = libelle;
		this.secondaryAddress = secondaryAddress;
		this.mainAddress = mainAddress;
	}

	public Server(String address, int port, String libelle, String mainAddress, String secondaryAddress,
			Set<Configuration> sourceConfigurations, Set<Configuration> destionationConfigurations) {
		this.address = address;
		this.port = port;
		this.libelle = libelle;
		this.mainAddress = mainAddress;
		this.secondaryAddress = secondaryAddress;
		this.sourceConfigurations = sourceConfigurations;
		this.destionationConfigurations = destionationConfigurations;
	}
	
	public Server(String address, int port, String libelle, String mainAddress, String secondaryAddress,
			Set<Configuration> sourceConfigurations, Set<Configuration> destionationConfigurations,
			Set<SystemUser> systemUsers) {
		this.address = address;
		this.port = port;
		this.libelle = libelle;
		this.mainAddress = mainAddress;
		this.secondaryAddress = secondaryAddress;
		this.sourceConfigurations = sourceConfigurations;
		this.destionationConfigurations = destionationConfigurations;
		this.systemUsers = systemUsers;
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
