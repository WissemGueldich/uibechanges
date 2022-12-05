package com.tn.uib.uibechanges.model;

import java.util.Date;

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
@Table(name = "application_executions")
public class ApplicationExecution {
	@Id    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    @Column(name = "id")
    private Integer id;
	
	@Column(name = "success")
    private boolean success;

    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    
    @Lob
    @Column(name = "error_message")
    private String errorMessage;
    
    @Lob
    @Column(name = "error")
    private String error;
	    
//    @OneToMany(mappedBy = "application")
//    private Set<Transfert> transfertSet;
    
    @JoinColumn(name = "application", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Application application;

    public ApplicationExecution() {
    }

    public ApplicationExecution(Integer id) {
        this.id = id;
    }

    public ApplicationExecution(Application application, boolean success, Date startDate, Date endDate) {
        this.application = application;
        this.success = success;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

//    public Set<Transfert> getTransfertSet() {
//        return transfertSet;
//    }
//
//    public void setTransfertSet(Set<Transfert> transfertSet) {
//        this.transfertSet = transfertSet;
//    }

    public Application getApplication() {
        return application;
    }

    public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public void setApplication(Application application) {
        this.application = application;
    }

    
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ApplicationExecution)) {
            return false;
        }
        ApplicationExecution other = (ApplicationExecution) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    public String toString() {
        return "com.tn.uib.uibechanges.dal.entities.ApplicationExecution[ id=" + id + " ]";
    }
    
}
