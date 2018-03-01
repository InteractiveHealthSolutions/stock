package com.ihs.stock.api.beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.SessionFactory;


import com.ihs.stock.api.DAO.DAOItem;
import com.ihs.stock.api.context.ServiceContextStock;
import com.ihs.stock.api.context.SessionFactoryUtil;
import com.ihs.stock.api.model.Item;
import com.ihs.stock.api.model.Requisition;

public class UpdateRequirementBean {

	
	
	private List<String> quantity = new ArrayList<String>();
	
	private String location;
	
	private List<String> comments = new ArrayList<String>();
	
	private Boolean[] check ;
	
	public UpdateRequirementBean() {
		// TODO Auto-generated constructor stub
		ServiceContextStock scSTK = SessionFactoryUtil.getServiceContext();
		String[] integers = new String[scSTK.itemDAO.getAllItemNames().size()];
		scSTK.commitTransaction();
		scSTK.closeSession();
		Arrays.fill(integers, null);
		quantity = Arrays.asList(integers);
		String[] comment = new String[quantity.size()];
		Arrays.fill(comment, null);
		comments = Arrays.asList(comment);
				check = new Boolean[comments.size()];
		//Arrays.fill(check, null);
		//checks = Arrays.asList(check);
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
	public Boolean[] getcheck() {
		return check;
	}
	
	public void setchecks(Boolean[] checks) {
		this.check = checks;
	}
}
