package com.tn.uib.uibechanges.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "application_configurations")
public class ApplicationConfiguration implements Comparable<ApplicationConfiguration>{
	
	@EmbeddedId
    protected ApplicationConfigurationPK applicationConfigurationPK;
	
    @Column(name = "rank")
    private Integer rank;
    
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "application", referencedColumnName = "id", insertable = false, updatable = false)
    private Application application;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "configuration", referencedColumnName = "id", insertable = false, updatable = false)
    private Configuration configuration;

    public ApplicationConfiguration() {
    }

    public ApplicationConfiguration(ApplicationConfigurationPK applicationConfigurationPK) {
        this.applicationConfigurationPK = applicationConfigurationPK;
    }

    public ApplicationConfiguration(int application, int configuration) {
        this.applicationConfigurationPK = new ApplicationConfigurationPK(application, configuration);
    }

    public ApplicationConfigurationPK getApplicationConfigurationPK() {
        return applicationConfigurationPK;
    }

    public void setApplicationConfigurationPK(ApplicationConfigurationPK applicationConfigurationPK) {
        this.applicationConfigurationPK = applicationConfigurationPK;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

	@Override
	public int compareTo(ApplicationConfiguration o) {
		return this.getRank().compareTo(o.getRank());
	}
}
