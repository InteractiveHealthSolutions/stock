package com.ihs.stock.api.beans;

public class InventoryBean {

	private String itemName;

	private String consumerLocation;

	private String parentLocation;

	private Integer inventoryInitialVialsCount;

	public void setitemName(String item) {
		this.itemName = item;
	}

	public String getitemName() {
		return itemName;
	}

	public String getconsumerLocation() {
		return consumerLocation;
	}

	public void setconsumerLocation(String consumerlocation) {
		this.consumerLocation = consumerlocation;
	}

	public void setparentLocation(String parentLocation) {
		this.parentLocation = parentLocation;
	}

	public String getparentLocation() {
		return parentLocation;
	}

	public void setinventoryInitialVialsCount(Integer inv) {
		this.inventoryInitialVialsCount = inv;
	}

	public Integer getinventoryInitialVialsCount() {
		return inventoryInitialVialsCount;
	}

}
