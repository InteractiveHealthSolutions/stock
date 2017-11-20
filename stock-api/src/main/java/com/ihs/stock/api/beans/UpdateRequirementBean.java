package com.ihs.stock.api.beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ihs.locationmanagement.api.model.Location;
import com.ihs.stock.api.DAO.DAOItem;
import com.ihs.stock.api.model.Item;

public class UpdateRequirementBean {

	
	
	private List<String> quantity = new ArrayList<String>();
	
	private String location;
	
	private List<String> comments = new ArrayList<String>();
	
	public UpdateRequirementBean() {
		// TODO Auto-generated constructor stub
		DAOItem itemDAO = new DAOItem();
		String[] integers = new String[itemDAO.getallItems().size()];
		Arrays.fill(integers, null);
		quantity = Arrays.asList(integers);
		String[] comment = new String[quantity.size()];
		Arrays.fill(comment, null);
		comments = Arrays.asList(comment);
	}
	
	public void setquantity(List<String> q)
	{
		this.quantity = q;
	}
	
	public List<String> getquantity()
	{
		return quantity;
	}
	
	public String getlocation() {
		return location;
	}
	
	public void setlocation(String location) {
		this.location = location;
	}
	
	public List<String> getcomments() {
		return comments;
	}
	
	public void setcomments(List<String> comments) {
		this.comments = comments;
	}
}
