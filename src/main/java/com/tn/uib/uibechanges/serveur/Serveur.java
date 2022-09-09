package com.tn.uib.uibechanges.serveur;



public class Serveur {
	
    private int id;

    private String adresse;

    private int port;

    private String libelle;

    public int getId() {
		return id;
	}


	public Serveur(int id, String adresse, int port, String libelle, String adresseSecours,
			String adressePrincipale) {
		super();
		this.id = id;
		this.adresse = adresse;
		this.port = port;
		this.libelle = libelle;
		this.adresseSecours = adresseSecours;
		this.adressePrincipale = adressePrincipale;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getAdresse() {
		return adresse;
	}


	public void setAdresse(String adresse) {
		this.adresse = adresse;
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


	public String getAdresseSecours() {
		return adresseSecours;
	}


	public void setAdresseSecours(String adresseSecours) {
		this.adresseSecours = adresseSecours;
	}


	public String getAdressePrincipale() {
		return adressePrincipale;
	}


	public void setAdressePrincipale(String adressePrincipale) {
		this.adressePrincipale = adressePrincipale;
	}


	private String adresseSecours;
    
    
    private String adressePrincipale;
	

}
