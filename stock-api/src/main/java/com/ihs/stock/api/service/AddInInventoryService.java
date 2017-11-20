package com.ihs.stock.api.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.management.InstanceAlreadyExistsException;



import com.ihs.stock.api.DAO.DAOInventory;
import com.ihs.stock.api.DAO.DAOItem;

import com.ihs.stock.api.DAO.DAOReceival;

import com.ihs.stock.api.DAO.DAORequisition;
import com.ihs.stock.api.beans.InventoryBean;
import com.ihs.stock.api.beans.UpdateRequirementBean;
import com.ihs.stock.api.model.Inventory;
import com.ihs.stock.api.model.Item;
import com.ihs.locationmanagement.api.context.Context;
import com.ihs.locationmanagement.api.context.ServiceContext;
import com.ihs.locationmanagement.api.model.Location;
import com.ihs.stock.api.model.Receival;

import com.ihs.stock.api.model.Requisition;

public class AddInInventoryService {

	Inventory inventory = new Inventory();

	public Inventory insertInInventory(InventoryBean ib) throws Exception {
		String itemName = ib.getitemName();
		DAOItem itemDAO = new DAOItem();

		Item item = itemDAO.getByName(itemName);
		inventory.setitem(item);

		String locationName = ib.getconsumerLocation();
		Context.instantiate(null);
		ServiceContext sc = Context.getServices();
		// DAOLocationImpl locationDAO = new DAOLocationImpl(sc.);
		sc.beginTransaction();
		List location = sc.getLocationService().findLocationByName(locationName, false, null);

		inventory.setconsumerLocation((Location) location.get(0));

		locationName = ib.getparentLocation();
		location = sc.getLocationService().findLocationByName(locationName, false, null);

		sc.commitTransaction();
		sc.closeSession();
		inventory.setparentLocation((Location) location.get(0));

	
		DAOInventory inventoryDAO = new DAOInventory();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(sdf.format(new Date()));
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);

		Receival req = new Receival();
		req.setdateCreated(date);
		req.setitem(item);
		req.setlocation((Location) location.get(0));
		req.setmonth(month);
		req.setyear(year);
		req.setrequiredAmount(ib.getinventoryInitialVialsCount());
		Integer initial = 0;

		Inventory parentInventory = inventoryDAO.getBalanceForLocationMonthItem((Location) location.get(0), item);
		if (parentInventory.gettotalContainers() < ib.getinventoryInitialVialsCount()) {
			initial = parentInventory.gettotalContainers();
			req.setreceivedAmount(initial);
			req.setstatus("Requirement not fulfilled");
		} else {
			initial = ib.getinventoryInitialVialsCount();
			req.setreceivedAmount(initial);
			req.setstatus("Requirement fulfilled");
		}

		if (inventoryDAO.prevMonthExist((Location) location.get(0) , item)) {
			Inventory inv = inventoryDAO.getPrevMonthInventory((Location) location.get(0) , item);
			inventory.setbalanceContainer(inv.getprevMonthBalance());
			inventory.settotalContainers(inv.getprevMonthBalance() + initial);
		} else {
			inventory.setbalanceContainer(0);
			inventory.settotalContainers(initial);
		}

		inventory.setcurrentMonthContainers(ib.getinventoryInitialVialsCount());

		inventory.setyear(year);
		inventory.setmonth(month);
		inventory.setdateCreated(date);

		inventoryDAO.save(inventory);

		parentInventory.settotalContainers(parentInventory.gettotalContainers() - initial);
		inventoryDAO.update(parentInventory);

		DAOReceival receivalDAO = new DAOReceival();
		receivalDAO.save(req);
		return inventory;
	}

	public Inventory insertInParentInventory(InventoryBean ib) throws ParseException, InstanceAlreadyExistsException {
		DAOInventory inventoryDAO = new DAOInventory();
		String locationName = ib.getconsumerLocation();
		Context.instantiate(null);
		ServiceContext sc = Context.getServices();
		// DAOLocationImpl locationDAO = new DAOLocationImpl(sc.);
		sc.beginTransaction();
		List location = sc.getLocationService().findLocationByName(locationName, false, null);
		sc.commitTransaction();
		sc.closeSession();
		DAOItem itemDAO = new DAOItem();
		String itemName = ib.getitemName();
		Item item = itemDAO.getByName(itemName);
        Inventory prevEntry ;
		if(inventoryDAO.inventoryForMonthExist(item, (Location) location.get(0)))
		{
			prevEntry = inventoryDAO.getPrevForSameMonth(item,(Location) location.get(0));
			inventory.settotalContainers(prevEntry.gettotalContainers());
			prevEntry.setvoided(true);
		}
		
		inventory.setitem(item);
       
		inventory.setconsumerLocation((Location) location.get(0));
		
		Integer initial = ib.getinventoryInitialVialsCount();
		
		if (inventoryDAO.prevMonthExist((Location) location.get(0) , item)) {
			Inventory inv = inventoryDAO.getPrevMonthInventory((Location) location.get(0) , item);
			inventory.setbalanceContainer(inv.getprevMonthBalance());
			inventory.settotalContainers(inventory.gettotalContainers()+inv.getprevMonthBalance() + ib.getinventoryInitialVialsCount());
		} else {
			inventory.setbalanceContainer(0);
			inventory.settotalContainers(inventory.gettotalContainers()+initial);
		}

		inventory.setcurrentMonthContainers(ib.getinventoryInitialVialsCount());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(sdf.format(new Date()));
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		inventory.setday(day);
		inventory.setyear(year);
		inventory.setmonth(month);
		inventory.setdateCreated(date);

		inventoryDAO.save(inventory);

		return inventory;
	}
	
	public void updateRequirement(UpdateRequirementBean urb) throws InstanceAlreadyExistsException, ParseException
	{
		Requisition req ;
		DAORequisition requirementDAO = new DAORequisition();
		DAOItem itemDAO = new DAOItem();
		List<Item> items = (List<Item>) itemDAO.getallItems();
		Context.instantiate(null);
		ServiceContext sc = Context.getServices();
		sc.beginTransaction();
		List location = sc.getLocationService().findLocationByName(urb.getlocation(), false, null);
	    sc.commitTransaction();
	    sc.closeSession();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(sdf.format(new Date()));
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		int day = cal.get(Calendar.DAY_OF_MONTH);
	    for(int i = 0 ; i < urb.getquantity().size() ;i++)
	    {
	    	if(!urb.getquantity().get(i).isEmpty())
	    	{
	    		req = new Requisition();
	    		req.setitem(items.get(i));
	    		req.setconsumerLocation((Location) location.get(0));
	    		req.setcomments(urb.getcomments().get(i));
	    		//req.setRequisitionBy(id of the person submitting the form);
	    		//req.setRequisitionLocation(location of the person submitting the form);
	    		req.setquantity(Integer.parseInt(urb.getquantity().get(i)));
	    		req.setdateCreated(date);
	    		req.setmonth(month);
	    		req.setyear(year);
	    		req.setDay(day);
	    		req.setApprovalStatus("Pending");
	    		requirementDAO.save(req);
	    	}
	    	
	    }
	}
}
