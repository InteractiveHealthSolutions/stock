package com.ihs.stock.api.beans;

public class DayEndEntryBean {

	private Double closingTemprature;

	private String itemName;

	private Integer usedQuantityCount;

	private Integer wastedQuantityCount;

	private int vaccinatorId;

	public void setclosingTemprature(Double temp) {
		this.closingTemprature = temp;
	}

	public Double getclosingTemprature() {
		return closingTemprature;
	}

	public void setitemName(String item) {
		this.itemName = item;
	}

	public String getitemName() {
		return itemName;
	}

	public void setusedQuantityCount(Integer qty) {
		this.usedQuantityCount = qty;
	}

	public Integer getusedQuantityCount() {
		return usedQuantityCount;
	}

	public void setwastedQuantityCount(Integer qty) {
		this.wastedQuantityCount = qty;
	}

	public Integer getwastedQuantityCount() {
		return wastedQuantityCount;
	}

	public void setvaccinatorId(int id) {
		this.vaccinatorId = id;
	}

	public int getvaccinatorId() {
		return vaccinatorId;
	}
}
