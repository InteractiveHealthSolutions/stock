package com.ihs.stock.api.beans;

import java.util.HashMap;

import com.ihs.stock.api.model.ItemAttributeType;
public class AddItemAttributeBean {

	private String itemName;
	
	private String atype;
	
	private String value;
	
	public String getatype() {
		return atype;
	}
	public void setatype(String atype) {
		this.atype = atype;
	}
	public String getvalue() {
		return value;
	}
	public void setvalue(String value) {
		this.value = value;
	}
	public String getitemName() {
		return itemName;
	}
	public void setitemName(String itemName) {
		this.itemName = itemName;
	}
}
