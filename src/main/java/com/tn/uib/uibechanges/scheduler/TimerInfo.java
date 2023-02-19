package com.tn.uib.uibechanges.scheduler;

import java.util.Set;

import com.tn.uib.uibechanges.model.Day;

public class TimerInfo {
	private int totalFireCount;
	private boolean runForever;
	private int repeatInterval;
	private long initialOffset;
	private String startDate;
	private String endDate;
	private Set<Day> days ;
	
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
	public int getRepeatInterval() {
		return repeatInterval;
	}
	public void setRepeatInterval(int repeatInterval) {
		this.repeatInterval = repeatInterval;
	}
	public long getInitialOffset() {
		return initialOffset;
	}
	public void setInitialOffset(long initialOffset) {
		this.initialOffset = initialOffset;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Set<Day> getDays() {
		return days;
	}
	public void setDays(Set<Day> days) {
		this.days = days;
	}

}
