package com.tn.uib.uibechanges.model;

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
@Table(name = "jobs")
public class Job {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "libelle")
    private String libelle;
    
    @Column(name = "start_hour")
    private String startHour;
    
    @Column(name = "end_hour")
    private String endHour;
    
    @Column(name = "frequency")
    private int frequency;
    
    @Column(name = "state")
    private boolean state;
    
    @Column(name="active")
    private boolean active;
    
    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "job_days", 
    		joinColumns = @JoinColumn(name = "job_id"), 
			inverseJoinColumns = @JoinColumn(name = "day_id"))
    private Set<Day> days;
    
    @OneToMany(mappedBy = "job", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<ConfigurationJob> configurations;
    
    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE}, mappedBy = "job")
    @JsonIgnore
    private Set<JobExecution> jobExecutions;

	@ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE}, fetch = FetchType.EAGER)
	@JoinTable(name = "job_mails",
			joinColumns = @JoinColumn(name = "job_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> mailRecipients;

    public Job() {
    }

    public Job(Integer id) {
        this.id = id;
    }

    public Job(String libelle, String startHour, String endHour, int frequency, boolean state,
			Set<Day> days) {
		this.libelle = libelle;
		this.startHour = startHour;
		this.endHour = endHour;
		this.frequency = frequency;
		this.state = state;
		this.days = days;
	}

	public Job(String libelle, String startHour, String endHour, int frequency, boolean state) {
		this.libelle = libelle;
		this.startHour = startHour;
		this.endHour = endHour;
		this.frequency = frequency;
		this.state = state;
		
	}

	public Job(String libelle, String startHour, String endHour, int frequency, boolean state, 
			Set<Day> days, Set<ConfigurationJob> configurations, Set<JobExecution> jobExecutions) {
		this.libelle = libelle;
		this.startHour = startHour;
		this.endHour = endHour;
		this.frequency = frequency;
		this.state = state;
		this.days = days;
		this.configurations = configurations;
		this.jobExecutions = jobExecutions;
	}

	public Set<User> getMailRecipients() {
		return mailRecipients;
	}

	public void setMailRecipients(Set<User> mailRecipients) {
		this.mailRecipients = mailRecipients;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getStartHour() {
		return startHour;
	}

	public void setStartHour(String startHour) {
		this.startHour = startHour;
	}

	public String getEndHour() {
		return endHour;
	}

	public void setEndHour(String endHour) {
		this.endHour = endHour;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public boolean getState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public Set<Day> getDays() {
		return days;
	}

	public void setDays(Set<Day> days) {
		this.days = days;
	}

	public Set<ConfigurationJob> getConfigurations() {
		return configurations;
	}

	public void setConfigurations(Set<ConfigurationJob> configurations) {
		this.configurations = configurations;
	}

	public Set<JobExecution> getJobExecutions() {
		return jobExecutions;
	}

	public void setJobExecutions(Set<JobExecution> jobExecutions) {
		this.jobExecutions = jobExecutions;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
