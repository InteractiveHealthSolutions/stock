package com.ihs.stock.api.beans;

public class MonthlyReceivalFormBean {

	private Integer userId;
	
	private Integer locationId;
	
	private String itemName;
	
	private String expiryDate;

	private String manufactureDate;
	
	private String receivalDate;
	
	private Integer dosesInAVial;
	
	private Integer totalDosesReceived;
	
	private String referenceNumber;
	
	private String receivedFrom;
	
	private Integer balanceInHand;	
	
	private Integer vialsConsumed;
	
   public void setitemName(String item) {
		this.itemName = item;
	}

	public String getitemName() {
		return itemName;
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
	
	public void setbalanceInHand(Integer balanceInHand) {
		this.balanceInHand = balanceInHand;
	}
	
	public void setdosesInAVial(Integer dosesInAVial) {
		this.dosesInAVial = dosesInAVial;
	}
	
	public void settotalDosesReceived(Integer totalDosesReceived) {
		this.totalDosesReceived = totalDosesReceived;
	}
	
	public Integer getbalanceInHand() {
		return balanceInHand;
	}
	public Integer getdosesInAVial() {
		return dosesInAVial;
	}
	public Integer gettotalDosesReceived() {
		return totalDosesReceived;
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
	public Integer getvialsConsumed() {
		return vialsConsumed;
	}
	
	public void setvialsConsumed(Integer vialsConsumed) {
		this.vialsConsumed = vialsConsumed;
	}
}
