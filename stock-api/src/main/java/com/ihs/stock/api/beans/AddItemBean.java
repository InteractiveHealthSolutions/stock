package com.ihs.stock.api.beans;

import java.util.ArrayList;

import com.ihs.stock.api.model.Item.ExpiryUnit;

public class AddItemBean {

	private String name;

	private String shortName;

	private String description;

	private String type;

	private String manufacturer;

	private String brand;

	private ExpiryUnit expiryUnit;

	private Integer expirationDurationAfterOpening;

	private Integer enclosedQuantity;

	private String barcode;

	private ArrayList<String> columnName = new ArrayList<String>();

	private ArrayList<String> columnValue = new ArrayList<String>();

	public ArrayList<String> getcolumnValue() {
		return columnValue;
	}

	public void setcolumnValue(ArrayList<String> columnValue) {
		this.columnValue = columnValue;
	}

	public ArrayList<String> getcolumnName() {
		return columnName;
	}

	public void setcolumnName(ArrayList<String> columnName) {
		this.columnName = columnName;
	}

	public Integer getenclosedQuantity() {
		return enclosedQuantity;
	}

	public void setenclosedQuantity(Integer enclosedQuantity) {
		this.enclosedQuantity = enclosedQuantity;
	}

	public Integer getexpirationDurationAfterOpening() {
		return expirationDurationAfterOpening;
	}

	public void setexpirationDurationAfterOpening(Integer expirationDurationAfterOpening) {
		this.expirationDurationAfterOpening = expirationDurationAfterOpening;
	}

	public String getbrand() {
		return brand;
	}

	public String getdescription() {
		return description;
	}

	public ExpiryUnit getexpiryUnit() {
		return expiryUnit;
	}

	public String getmanufacturer() {
		return manufacturer;
	}

	public String getname() {
		return name;
	}

	public String getshortName() {
		return shortName;
	}

	public String gettype() {
		return type;
	}

	public void setbrand(String brand) {
		this.brand = brand;
	}

	public void setdescription(String description) {
		this.description = description;
	}

	public void setexpiryUnit(ExpiryUnit expiryUnit) {
		this.expiryUnit = expiryUnit;
	}

	public void setmanufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public void setname(String name) {
		this.name = name;
	}

	public void setshortName(String shortName) {
		this.shortName = shortName;
	}

	public void settype(String type) {
		this.type = type;
	}

	public String getbarcode() {
		return barcode;
	}

	public void setbarcode(String barcode) {
		this.barcode = barcode;
	}

}
