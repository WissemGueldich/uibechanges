package com.tn.uib.uibechanges.controller;

import java.sql.Date;

public class DateBetween {
	private Date startDate;
	private Date endDate;

	public DateBetween() {
	}

	public DateBetween(Date startDate, Date endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
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
	
	
}