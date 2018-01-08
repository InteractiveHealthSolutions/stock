package com.ihs.stock.api.beans;

import java.util.ArrayList;

import java.util.List;

import javax.management.InstanceAlreadyExistsException;

import org.hibernate.SessionFactory;
import org.ird.unfepi.context.LocationContext;
import org.ird.unfepi.context.LocationServiceContext;
import org.ird.unfepi.model.Location;

import com.ihs.stock.api.DAO.DAORequisition;
import com.ihs.stock.api.context.ServiceContextStock;
import com.ihs.stock.api.context.SessionFactoryUtil;
import com.ihs.stock.api.model.Requisition;

public class ApproveRequirementBean {

	// private List<Requisition> requisition = new ArrayList<Requisition>();

	private String[] check;

	public ApproveRequirementBean() {
		// TODO Auto-generated constructor stub
	}

	public ApproveRequirementBean(int size) {
		check = new String[size];

	}

	public String[] getcheck() {
		return check;
	}

	public void setcheck(String[] check) {
		this.check = check;
	}

	// public List<Requisition> getrequisition() {
	// return requisition;
	// }
	// public void setrequisition(List<Requisition> requisition) {
	// this.requisition = requisition;
	// }

}
