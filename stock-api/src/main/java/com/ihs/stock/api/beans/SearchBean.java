package com.ihs.stock.api.beans;

public class SearchBean {

	private String locationName;

	private Integer year;

	private Integer month;

	public void setyear(Integer year) {
		this.year = year;
	}

	public void setmonth(Integer month) {
		this.month = month;
	}

	public void setlocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getlocationName() {
		return locationName;
	}

	public Integer getmonth() {
		return month;
	}

	public Integer getyear() {
		return year;
	}
	
	
}
