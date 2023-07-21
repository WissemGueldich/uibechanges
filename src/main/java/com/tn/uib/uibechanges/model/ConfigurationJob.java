package com.tn.uib.uibechanges.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "configuration_jobs")
public class ConfigurationJob implements Comparable<ConfigurationJob>{
	
	@EmbeddedId
    protected ConfigurationJobPK configurationJobPK;
	
    @Column(name = "rank")
    private Integer rank;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "job", referencedColumnName = "id", insertable = false, updatable = false)
    private Job job;
    
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "configuration", referencedColumnName = "id", insertable = false, updatable = false)
    private Configuration configuration;
   
    public ConfigurationJob() {
    }
    
    public ConfigurationJob(ConfigurationJobPK configurationJobPK) {
        this.configurationJobPK = configurationJobPK;
    }

    public ConfigurationJob(ConfigurationJobPK configurationJobPK, Configuration conf, Job job) {
        this.configurationJobPK = configurationJobPK;
        this.configuration = conf;
        this.job = job;
    }

    public ConfigurationJobPK getConfigurationJobPK() {
        return configurationJobPK;
    }

    public void setConfigurationJobPK(ConfigurationJobPK configurationJobPK) {
        this.configurationJobPK = configurationJobPK;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

	@Override
	public int compareTo(ConfigurationJob o) {
		return this.getRank().compareTo(o.getRank());
	}

}
