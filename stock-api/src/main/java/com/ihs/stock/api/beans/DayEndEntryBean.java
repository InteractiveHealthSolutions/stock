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

	private Double closingTemprature;

	private Status status;
	
	private List<String> itemName;

	private List<Integer> usedQuantityCount;

	private List<Integer> wastedQuantityCount;

	private int vaccinatorId;

	public DayEndEntryBean(int loc , int size) throws InstanceAlreadyExistsException {
		
		
		Integer[] integers = new Integer[size];
		Arrays.fill(integers, null);
		usedQuantityCount = Arrays.asList(integers);
		Integer[] comment = new Integer[size];
		Arrays.fill(comment, null);
		wastedQuantityCount = Arrays.asList(comment);
			
	}
	public void setclosingTemprature(Double temp) {
		this.closingTemprature = temp;
	}

	public Double getclosingTemprature() {
		return closingTemprature;
	}

	public void setitemName(List<String> item) {
		this.itemName = item;
	}

	public List<String> getitemName() {
		return itemName;
	}

	public void setusedQuantityCount(List<Integer> qty) {
		this.usedQuantityCount = qty;
	}

	public List<Integer> getusedQuantityCount() {
		return usedQuantityCount;
	}

	public void setwastedQuantityCount(List<Integer> qty) {
		this.wastedQuantityCount = qty;
	}

	public List<Integer> getwastedQuantityCount() {
		return wastedQuantityCount;
	}

	public void setvaccinatorId(int id) {
		this.vaccinatorId = id;
	}

	public int getvaccinatorId() {
		return vaccinatorId;
	}
	public Status getstatus() {
		return status;
	}

	public void setstatus(Status status) {
		this.status = status;
	}
}
