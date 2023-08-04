package com.tn.uib.uibechanges.model;

public class JobStatus {
	private int jobId;
	private boolean isScheduled;
	private boolean isRunning;
	
	public JobStatus(int jobId) {
		this.jobId = jobId;
	}

	public JobStatus(int jobId, boolean isScheduled, boolean isRunning) {
		this.jobId = jobId;
		this.isScheduled = isScheduled;
		this.isRunning = isRunning;
	}

	public int getJobId() {
		return jobId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	public boolean isScheduled() {
		return isScheduled;
	}

	public void setScheduled(boolean isScheduled) {
		this.isScheduled = isScheduled;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

}
