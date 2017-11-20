package com.ihs.stock.api.beans;

import com.ihs.stock.api.model.ILRDailyStatus.Status;

public class MorningEntryBean {

	private String itemName;

	private Status status;

	private int vaccinatorId;

	private String ilrIdentifier;

	private Double openingTemprature;

	private String location;

	public String getlocation() {
		return location;
	}

	public void setlocation(String location) {
		this.location = location;
	}

	public Double getopeningTemprature() {
		return openingTemprature;
	}

	public void setopeningTemprature(Double openingTemprature) {
		this.openingTemprature = openingTemprature;
	}

	public Status getstatus() {
		return status;
	}

	public void setstatus(Status status) {
		this.status = status;
	}

	public void setitemName(String item) {
		this.itemName = item;
	}

	public String getilrIdentifier() {
		return ilrIdentifier;
	}

	public void setilrIdentifier(String ilrIdentifier) {
		this.ilrIdentifier = ilrIdentifier;
	}

	public String getitemName() {
		return itemName;
	}

	public void setvaccinatorId(int id) {
		this.vaccinatorId = id;
	}

	public int getvaccinatorId() {
		return vaccinatorId;
	}

}
