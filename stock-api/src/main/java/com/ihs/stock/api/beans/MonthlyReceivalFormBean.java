package com.ihs.stock.api.beans;

public class MonthlyReceivalFormBean {

	private Integer userId;
	
	private Integer locationId;
	
	private String itemName;

	private Integer noOfVials;

	private String expiryDate;

	private String manufactureDate;
	
	private String receivalDate;
	
	private Integer dosesInAVial;
	
	private Integer totalDosesReceived;
	
	private Integer balanceInHand;
	
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
}