package com.ihs.stock.api.beans;

public class MonthlyUpdateVaccinatorBean {

	private String itemName;

	private Integer noOfVials;

	private String expiryDate;

	private String manufactureDate;

	public void setitemName(String item) {
		this.itemName = item;
	}

	public String getitemName() {
		return itemName;
	}

	public void setnoOfVials(Integer vials) {
		this.noOfVials = vials;
	}

	public Integer getnoOfVials() {
		return noOfVials;
	}

	public void setexpiryDate(String expiry) {
		this.expiryDate = expiry;
	}

	public String getexpiryDate() {
		return expiryDate;
	}

	public void setmanufactureDate(String manufacture) {
		this.manufactureDate = manufacture;
	}

	public String getmanufactureDate() {
		return manufactureDate;
	}

}
