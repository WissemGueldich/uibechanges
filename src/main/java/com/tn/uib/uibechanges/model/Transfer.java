package com.tn.uib.uibechanges.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "transfers")
public class Transfer {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    private int type;
    
	@Basic(optional = false)
    @ManyToOne
    private Configuration configuration;
    
    private boolean result;
    
    private String error;
    
    @Column(name = "trigger_date")
	@JsonFormat(pattern = "yyyy/MM/dd")
	private Date date;

	public Transfer() {
	}

	public Transfer(int type, Configuration configuration, boolean result, String error) {
		this.type = type;
		this.configuration = configuration;
		this.result = result;
		this.error = error;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
    
    
}
