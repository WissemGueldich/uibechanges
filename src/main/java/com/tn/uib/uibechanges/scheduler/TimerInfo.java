package com.tn.uib.uibechanges.scheduler;

import java.util.Date;

public class TimerInfo {
	private int totalFireCount;
	private boolean runForever;
	private long repeatIntervalMS;
	private long initialOffsetMS;
	private Date startDate;
	private Date endDate;

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
	public int getTotalFireCount() {
		return totalFireCount;
	}
	public void setTotalFireCount(int totalFireCount) {
		this.totalFireCount = totalFireCount;
	}
	public boolean isRunForever() {
		return runForever;
	}
	public void setRunForever(boolean runForever) {
		this.runForever = runForever;
	}
	public long getRepeatIntervalMS() {
		return repeatIntervalMS;
	}
	public void setRepeatIntervalMS(long repeatIntervalMS) {
		this.repeatIntervalMS = repeatIntervalMS;
	}
	public long getInitialOffsetMS() {
		return initialOffsetMS;
	}
	public void setInitialOffsetMS(long initialOffsetMS) {
		this.initialOffsetMS = initialOffsetMS;
	}

}
