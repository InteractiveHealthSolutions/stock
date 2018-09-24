package com.ihs.stock.api.beans;

public class MonthlyReceivalFormBean {

	private Integer userId;
	
	private Integer locationId;
	
	private Integer noOfVials;
	
	private String expiryDate;

	private String manufactureDate;
	
	private String receivalDate;
	
	private String referenceNumber;
	
	private String receivedFrom;
	/*
	 * Below fields are required in 
	 * the new monthly summary form
	 */
	private String vaccineName;
	
	private Integer closingBalance;	

	private Integer dosesConsumed;
	
	private Integer dosesReceived;
	
	private Integer dosesWasted;
	
	private Integer openingBalance;
	
	
	public String getvaccineName() {
		return vaccineName;
	}
	public Integer getclosingBalance() {
		return closingBalance;
	}
	
	public void setclosingBalance(Integer closingBalance) {
		this.closingBalance = closingBalance;
	}
	public void setdosesConsumed(Integer dosesConsumed) {
		this.dosesConsumed = dosesConsumed;
	}
	
	public Integer getdosesConsumed() {
		return dosesConsumed;
	}
	
	public void setdosesReceived(Integer dosesReceived) {
		this.dosesReceived = dosesReceived;
	}
	
	public Integer getdosesReceived() {
		return dosesReceived;
	}
	
	public Integer getdosesWasted() {
		return dosesWasted;
	}
	
	public void setDoseswasted(Integer dosesWasted) {
		this.dosesWasted = dosesWasted;
	}
	
	public Integer getopeningBalance() {
		return openingBalance;
	}
	
	public void setopeningBalance(Integer openingBalance) {
		this.openingBalance = openingBalance;
	}
	
	public void setvaccineName(String vaccineName) {
		this.vaccineName = vaccineName;
	}
	public String getreceivalDate() {
		return receivalDate;
	}
	
	public void setreceivalDate(String receivalDate) {
		this.receivalDate = receivalDate;
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
	
	
	public void setuserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getuserId() {
		return userId;
	}
	public void setlocationId(Integer locationId) {
		this.locationId = locationId;
	}
	public Integer getlocationId() {
		return locationId;
	}
	public String getreferenceNumber() {
		return referenceNumber;
	}
	
	public void setreferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}
	public String getreceivedFrom() {
		return receivedFrom;
	}
	
	public void setreceivedFrom(String receivedFrom) {
		this.receivedFrom = receivedFrom;
	}
	public Integer getnoOfVials() {
		return noOfVials;
	}
	
	public void setnoOfVials(Integer vialsConsumed) {
		this.noOfVials = vialsConsumed;
	}
}
