package com.tn.uib.uibechanges.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "servers", uniqueConstraints = {@UniqueConstraint(columnNames = { "mainAddress" }) })
public class Server {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String address;

    private int port;

    private String libelle;
    
    private String mainAddress;

	private String secondaryAddress;

//    @OneToMany(fetch = FetchType.LAZY, cascade = {}, mappedBy = "serveurSource")
//    private Set<Configuration> configurationSet;
//    @OneToMany(fetch = FetchType.LAZY, cascade = {}, mappedBy = "serveurDestination")
//    private Set<Configuration> configurationSet1;
//    @ManyToMany(fetch = FetchType.EAGER, cascade = {}, mappedBy = "serveurSet")
//    private Set<UtilisateurSysteme> utilisateurSystemeSet;


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

}
