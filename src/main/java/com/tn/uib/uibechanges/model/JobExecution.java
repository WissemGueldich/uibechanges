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
@Table(name = "job_executions")
public class JobExecution {
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

    @Column(name = "found_files")
    private int foundFiles;

    @Column(name = "transferred_files")
    private int transferredFiles;
    
    @Lob
    @Column(name = "error_message")
    private String errorMessage;
    
    @Lob
    @Column(name = "error")
    private String error;
    
//    @OneToMany(mappedBy = "job")
//    private Set<Transfert> transfertSet;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "job", referencedColumnName = "id")
    private Job job;

    public JobExecution() {
    }

    public JobExecution(Integer id) {
        this.id = id;
    }

    public JobExecution(Job job, boolean success, Date startDate, Date endDate, int foundFiles, int transferredFiles) {
        this.job = job;
        this.success = success;
        this.startDate = startDate;
        this.endDate = endDate;
        this.transferredFiles = transferredFiles;
        this.foundFiles = foundFiles;
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

//    public Set<Transfert> getTransfertSet() {
//        return transfertSet;
//    }
//
//    public void setTransfertSet(Set<Transfert> transfertSet) {
//        this.transfertSet = transfertSet;
//    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
    
    public int getFoundFiles() {
        return foundFiles;
    }

    public void setFoundFiles(int foundFiles) {
        this.foundFiles = foundFiles;
    }

    public int getTransferredFiles() {
        return transferredFiles;
    }

    public void setTransferredFiles(int transferredFiles) {
        this.transferredFiles = transferredFiles;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof JobExecution)) {
            return false;
        }
        JobExecution other = (JobExecution) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tn.uib.uibechanges.dal.entities.JobExecution[ id=" + id + " ]";
    }


}
