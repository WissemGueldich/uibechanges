package com.tn.uib.uibechanges.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "configurations")
public class Configuration {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String filter;

	private boolean overwrite;

	private String libelle;

	private boolean move;

	private boolean automatic;

	private boolean archive;

	private String sourcePath;

	private String sourceArchivingPath;

	private String destinationPath;

	private String destinationArchivingPath;

	@ManyToOne
	@JoinColumn(name = "destination_server", referencedColumnName = "id")
	private Server destinationServer;
	
	@ManyToOne
	@JoinColumn(name = "source_server", referencedColumnName = "id")
	private Server sourceServer;

	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,CascadeType.MERGE}, mappedBy = "configurations")
	@JsonIgnore
	private Set<Profile> profiles;

    @ManyToOne
    private SystemUser sourceUser;

    @ManyToOne
    private SystemUser destinationUser;

    @OneToMany(mappedBy = "job", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<ConfigurationJob> jobs;

    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE}, mappedBy = "configuration", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<ApplicationConfiguration> applications ;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "configuration")
    @JsonIgnore
    private Set<Transfer> transfers;

	public Configuration() {
	}
	
	public Configuration(String filter, String libelle, boolean move, boolean automatic, boolean overwrite,
			boolean archive, String sourcePath, String sourceArchivingPath, String destinationPath,
			String destinationArchivingPath, Server sourceServer, SystemUser sourceUser, Server destinationServer,
			SystemUser destinationUser) {
		this.filter = filter;
		this.overwrite = overwrite;
		this.libelle = libelle;
		this.move = move;
		this.automatic = automatic;
		this.archive = archive;
		this.sourcePath = sourcePath;
		this.sourceArchivingPath = sourceArchivingPath;
		this.destinationPath = destinationPath;
		this.destinationArchivingPath = destinationArchivingPath;
		this.destinationServer = destinationServer;
		this.sourceServer = sourceServer;
		this.sourceUser = sourceUser;
		this.destinationUser = destinationUser;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Set<Transfer> getTransfers() {
		return transfers;
	}

	public void setTransfers(Set<Transfer> transfers) {
		this.transfers = transfers;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public boolean getOverwrite() {
		return overwrite;
	}

	public void setOverwrite(boolean overwrite) {
		this.overwrite = overwrite;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public boolean getMove() {
		return move;
	}

	public void setMove(boolean move) {
		this.move = move;
	}

	public boolean getAutomatic() {
		return automatic;
	}

	public void setAutomatic(boolean automatic) {
		this.automatic = automatic;
	}

	public boolean getArchive() {
		return archive;
	}

	public void setArchive(boolean archive) {
		this.archive = archive;
	}

	public Set<Profile> getProfiles() {
		return profiles;
	}

	public void setProfiles(Set<Profile> profiles) {
		this.profiles = profiles;
	}

	public Server getDestinationServer() {
		return destinationServer;
	}

	public void setDestinationServer(Server destinationServer) {
		this.destinationServer = destinationServer;
	}

	public Server getSourceServer() {
		return sourceServer;
	}

	public void setSourceServer(Server sourceServer) {
		this.sourceServer = sourceServer;
	}

    public Set<ConfigurationJob> getJobs() {
        return jobs;
    }

    public void setJobs(Set<ConfigurationJob> jobs) {
        this.jobs = jobs;
    }

    public Set<ApplicationConfiguration> getApplications() {
        return applications;
    }

    public void setApplications(Set<ApplicationConfiguration> applications) {
        this.applications = applications;
    }

	public String getSourcePath() {
		return sourcePath;
	}

	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}

	public String getSourceArchivingPath() {
		return sourceArchivingPath;
	}

	public void setSourceArchivingPath(String sourceArchivingPath) {
		this.sourceArchivingPath = sourceArchivingPath;
	}

	public String getDestinationPath() {
		return destinationPath;
	}

	public void setDestinationPath(String destinationPath) {
		this.destinationPath = destinationPath;
	}

	public String getDestinationArchivingPath() {
		return destinationArchivingPath;
	}

	public void setDestinationArchivingPath(String destinationArchivingPath) {
		this.destinationArchivingPath = destinationArchivingPath;
	}

    public SystemUser getSourceUser() {
        return sourceUser;
    }

    public void setSourceUser(SystemUser sourceUser) {
        this.sourceUser = sourceUser;
    }

    public SystemUser getDestinationUser() {
        return destinationUser;
    }

    public void setDestinationUser(SystemUser destinationUser) {
        this.destinationUser = destinationUser;
    }
    
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	public boolean equals(Object object) {
		if (!(object instanceof Configuration)) {
			return false;
		}
		Configuration othiser = (Configuration) object;
		if ((this.id == null && othiser.id != null) || (this.id != null && !this.id.equals(othiser.id))) {
			return false;
		}
		return true;
	}
	
	public String toString() {
		return "com.tn.uib.uibechanges.dal.entities.Configuration[ id=" + id + " ]";
	}
	
}
