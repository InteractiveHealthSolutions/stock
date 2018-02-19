package com.ihs.stock.api.beans;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.management.InstanceAlreadyExistsException;


import com.ihs.stock.api.DAO.DAOInventory;
import com.ihs.stock.api.DAO.DAOItem;
import com.ihs.stock.api.model.Inventory;
import com.ihs.stock.api.model.ILRDailyStatus.Status;

public class DayEndEntryBean {

	private Integer location;

	private String dateToday;
	
	private String itemName;

	private Integer usedQuantityCount;

	private Integer wastedQuantityCount;

	private Integer vaccinatorId;


	public void setlocation(Integer temp) {
		this.location = temp;
	}

	public Integer getlocation() {
		return location;
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

	public void setvaccinatorId(Integer id) {
		this.vaccinatorId = id;
	}

	public int getvaccinatorId() {
		return vaccinatorId;
	}
	public String getdateToday() {
		return dateToday;
	}

	public void setdateToday(String date) {
		this.dateToday = date;
	}
}
