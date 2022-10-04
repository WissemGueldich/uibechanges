package com.tn.uib.uibechanges.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ApplicationConfigurationPK implements Serializable{

	private static final long serialVersionUID = 1L;
	@Basic(optional = false)
    @Column(name = "application")
    private int application;
    @Basic(optional = false)
    @Column(name = "configuration")
    private int configuration;

    public ApplicationConfigurationPK() {
    }

    public ApplicationConfigurationPK(int application, int configuration) {
        this.application = application;
        this.configuration = configuration;
    }

    public int getApplication() {
        return application;
    }

    public void setApplication(int application) {
        this.application = application;
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
        hash += (int) application;
        hash += (int) configuration;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ApplicationConfigurationPK)) {
            return false;
        }
        ApplicationConfigurationPK other = (ApplicationConfigurationPK) object;
        if (this.application != other.application) {
            return false;
        }
        if (this.configuration != other.configuration) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tn.uib.uibechanges.dal.entities.ApplicationConfigurationPK[ application=" + application + ", configuration=" + configuration + " ]";
    }

}
