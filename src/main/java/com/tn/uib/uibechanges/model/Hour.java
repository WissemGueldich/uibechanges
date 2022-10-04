package com.tn.uib.uibechanges.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "hours")
public class Hour {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	Integer id;
	
	@Column(name = "hour")
	private String hour;
	
	@ManyToOne
	@JoinColumn(name = "job", referencedColumnName = "id")
	private Job job;

	public Hour() {
	}

	public Hour(String hour, Job job) {
		this.hour = hour;
		this.job = job;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}
	

}
