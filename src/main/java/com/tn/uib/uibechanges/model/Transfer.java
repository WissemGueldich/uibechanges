package com.tn.uib.uibechanges.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "transfers")
public class Transfer {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "auteur")
    private String auteur;
    
    @Basic(optional = false)
    @Column(name = "adresse_serveur_source")
    private String adresseServeurSource;
    
    @Basic(optional = false)
    @Column(name = "adresse_serveur_destination")
    private String adresseServeurDestination;
    
    @Basic(optional = false)
    @Column(name = "nom_serveur_source")
    private String nomServeurSource;
    
    @Basic(optional = false)
    @Column(name = "nom_serveur_destination")
    private String nomServeurDestination;
    
    @Basic(optional = false)
    @Column(name = "utilisateur_source")
    private String utilisateurSource;
    
    @Basic(optional = false)
    @Column(name = "utilisateur_destination")
    private String utilisateurDestination;
    
    @Basic(optional = false)
    @Column(name = "port_serveur_source")
    private int portServeurSource;
    
    @Basic(optional = false)
    @Column(name = "port_serveur_destination")
    private int portServeurDestination;
    
    @Basic(optional = false)
    @Column(name = "dossier_source")
    private String dossierSource;
    
    @Basic(optional = false)
    @Column(name = "dossier_destination")
    private String dossierDestination;
    
    @Column(name = "dossier_archive")
    private String dossierArchive;
    
    @Basic(optional = false)
    @Column(name = "fichier_source")
    private String fichierSource;
    
    @Basic(optional = false)
    @Column(name = "fichier_destination")
    private String fichierDestination;
    
    @Column(name = "fichier_archive")
    private String fichierArchive;
    
    @Basic(optional = false)
    @Column(name = "deplacer")
    private boolean deplacer;
    
    @Basic(optional = false)
    @Column(name = "ecraser")
    private boolean ecraser;
    
    @Basic(optional = false)
    @Column(name = "configuration")
    private String configuration;
    
    @Basic(optional = false)
    @Column(name = "filtre_applique")
    private String filtreApplique;
    
    @Basic(optional = false)
    @Column(name = "date_transfert")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTransfert;
    
    @Basic(optional = false)
    @Column(name = "duree")
    private long duree;
    
    @Basic(optional = false)
    @Column(name = "taille_fichier")
    private long tailleFichier;
    
    @Basic(optional = false)
    @Column(name = "avec_succes")
    private boolean avecSucces;
    
    @Basic(optional = false)
    @Column(name = "trouve_destination")
    private boolean trouveDestination;
    
    @Lob
    @Column(name = "message_erreur")
    private String messageErreur;
    
    @Lob
    @Column(name = "cause_erreur")
    private String causeErreur;
    
    @Basic(optional = false)
    @Column(name = "restaurer")
    private boolean restaurer;
    
    @Lob
    @Column(name = "message_erreur_restauration")
    private String messageErreurRestauration;
    
    @Lob
    @Column(name = "cause_erreur_restauration")
    private String causeErreurRestauration;
    
    @JoinColumn(name = "application", referencedColumnName = "id")
    @ManyToOne
    private ApplicationExecution application;
    
    @JoinColumn(name = "job", referencedColumnName = "id")
    @ManyToOne
    private JobExecution job;
}
