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
    
    @Column(name = "type")
    private String type;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "job_days", 
    			joinColumns = @JoinColumn(name = "job_id"), 
    			inverseJoinColumns = @JoinColumn(name = "day_id"))
    private Set<Day> days;
    
    @OneToMany(mappedBy = "job", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<ConfigurationJob> configurations;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "job")
    @JsonIgnore
    private Set<JobExecution> jobExecutions;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "job", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Hour> hours;

    public Job() {
    }

    public Job(Integer id) {
        this.id = id;
    }

    public Job( String libelle, String startHour, String endHour, int frequency, boolean state, String type) {
        this.libelle = libelle;
        this.startHour = startHour;
        this.endHour = endHour;
        this.frequency = frequency;
        this.state = state;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Set<Hour> getHours() {
        return hours;
    }

    public void setHours(Set<Hour> hours) {
        this.hours = hours;
    }

    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Job)) {
            return false;
        }
        Job other = (Job) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public String toString() {
        return "com.tn.uib.uibechanges.dal.entities.Job[ id=" + id + " ]";
    }
}
