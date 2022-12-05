package com.tn.uib.uibechanges.scheduler;

public class TimerInfo {
	private int totalFireCount;
	private boolean runForever;
	private long repeatIntervalMS;
	private long initialOffsetMS;
	private String callbackData;
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
	public String getCallbackData() {
		return callbackData;
	}
	public void setCallbackData(String callbackData) {
		this.callbackData = callbackData;
	}
	

}
