package com.tn.uib.uibechanges.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ConfigurationJobPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
    @Column(name = "job")
    private int job;

    @Basic(optional = false)
    @Column(name = "configuration")
    private int configuration;

    public ConfigurationJobPK() {
    }

    public ConfigurationJobPK(int job, int configuration) {
        this.job = job;
        this.configuration = configuration;
    }

    public int getJob() {
        return job;
    }

    public void setJob(int job) {
        this.job = job;
    }

    public int getConfiguration() {
        return configuration;
    }

    public void setConfiguration(int configuration) {
        this.configuration = configuration;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) job;
        hash += (int) configuration;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConfigurationJobPK)) {
            return false;
        }
        ConfigurationJobPK other = (ConfigurationJobPK) object;
        if (this.job != other.job) {
            return false;
        }
        if (this.configuration != other.configuration) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tn.uib.uibechanges.dal.entities.ConfigurationJobPK[ job=" + job + ", configuration=" + configuration + " ]";
    }
    
}

