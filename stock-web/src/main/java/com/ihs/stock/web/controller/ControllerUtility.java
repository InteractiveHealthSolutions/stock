package com.ihs.stock.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.management.InstanceAlreadyExistsException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.web.servlet.ModelAndView;

import com.ihs.locationmanagement.mode.dao.hibernatedimpl.DAOLocationImpl;
import com.ihs.stock.api.DAO.DAOConsumer;
import com.ihs.stock.api.DAO.DAOInventory;
import com.ihs.stock.api.DAO.DAOItem;
import com.ihs.stock.api.DAO.DAOItemAttribute;
import com.ihs.stock.api.DAO.DAOItemAttributeType;
import com.ihs.stock.api.DAO.DAOItemType;

import com.ihs.stock.api.DAO.DAOMonthlyStats;
import com.ihs.stock.api.beans.AddItemAttributeBean;
import com.ihs.stock.api.beans.UpdateRequirementBean;
import com.ihs.stock.api.model.Consumer;
import com.ihs.stock.api.model.ILRDailyStatus.Status;
import com.ihs.stock.api.model.Inventory;
import com.ihs.stock.api.model.Item;

import com.ihs.stock.api.model.MonthlyStats;

import com.ihs.stock.api.model.Item.ExpiryUnit;

import com.ihs.stock.api.model.ItemAttributeType;

import com.ihs.stock.api.service.SessionFactoryUtil;
import com.ihs.locationmanagement.api.context.Context;
import com.ihs.locationmanagement.api.context.ServiceContext;
import com.ihs.locationmanagement.api.model.Location;

public class ControllerUtility {
	
	
	public static ModelAndView setInventoryForm(ModelAndView modelAndView) throws InstanceAlreadyExistsException
	{
		DAOItem items = new DAOItem();
		List<String> i = (List<String>) items.getAllItems();
	
		modelAndView.addObject("items", i);
		SessionFactory sf = SessionFactoryUtil.getSessionFactory();
		Session s = sf.getCurrentSession();
		Transaction tx = s.beginTransaction();
		DAOLocationImpl daoLocationImpl = new DAOLocationImpl(s);
		List<Location> loc = daoLocationImpl.getAll(true, null);
		tx.commit();
		List<String> locNames = new ArrayList<String>();
		for(int j = 0 ; j < loc.size() ; j++)
		{
			locNames.add(loc.get(j).getName());
		}
		modelAndView.addObject("locations", locNames);
		modelAndView.setViewName("add_inInventory");
	
		return modelAndView;
		
	}
	public static ModelAndView setAddItemForm(ModelAndView modelAndView)
	{
		DAOItemType itemTypeDAO = new DAOItemType();
		List<?> types = itemTypeDAO.getAllItemTypes();
		modelAndView.addObject("type", types);
		List<ExpiryUnit> arrayList = new ArrayList<ExpiryUnit>();
	    for(ExpiryUnit eu : ExpiryUnit.values())
	    {
	    	arrayList.add(eu);
	    }
	
	    modelAndView.addObject("expiryUnit", arrayList);
	    
	    DAOItem itemDAO = new DAOItem();
	    List<Item> items = (List<Item>) itemDAO.getallItems();
		modelAndView.addObject("list", items);
		DAOItemAttributeType attributeType = new DAOItemAttributeType();
	    List<ItemAttributeType> attr = attributeType.getAllAttributesType();
	    modelAndView.addObject("attr", attr);
		modelAndView.addObject("attrlist", attr);
	   
	  
	    modelAndView.addObject("attrBean", new AddItemAttributeBean());
	    modelAndView.addObject("items", items);
	    modelAndView.setViewName("add_items");
	    return modelAndView;
		
	}
	public static ModelAndView setMonthlyUpdateform(ModelAndView modelAndView , int vac)
	{
		DAOConsumer consumerDAO = new DAOConsumer();
		Consumer consumer = consumerDAO.getById(vac);
		Location loc = consumer.getlocation();
		
		DAOInventory inventoryDAO = new DAOInventory();
		List<Inventory> inventory = (List<Inventory>) inventoryDAO.getBalanceForLocationAllItems(loc);
		
		List<String> items = new ArrayList<String>();
		for(int i = 0 ; i < inventory.size() ; i++)
		{
			items.add(inventory.get(i).getitem().getname());
		}
		///modelAndView.addObject("monthlyupdate", new MonthlyUpdateVaccinatorBean());
		modelAndView.addObject("items", items);
		modelAndView.setViewName("update_monthlyStats");
		
		return modelAndView;
		
	}
	
	public static ModelAndView setDailyEntryMorningForm(ModelAndView modelAndView , int vac) 
	{
		List<Status> arrayList = new ArrayList<Status>();
	    for(Status eu : Status.values())
	    {
	    	arrayList.add(eu);
	    }
	    modelAndView.addObject("array", arrayList);
	    
		
		modelAndView.setViewName("daily_morningEntry");
		
		return modelAndView;
	}
	
	public static ModelAndView setDailyEntryDayEnd(ModelAndView modelAndView , int vac)
	{
		DAOConsumer consumerDAO = new DAOConsumer();
		Consumer vaccinator = consumerDAO.getById(vac);
		DAOMonthlyStats monthlyStatsDAO = new DAOMonthlyStats();
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH)+1;
		int year = cal.get(Calendar.YEAR);
		List<MonthlyStats> monthlyStats = (List<MonthlyStats>) monthlyStatsDAO.getMonthlyRecordForAllItems(vaccinator, month, year);
		
		List<String> items = new ArrayList<String>();
		for(int i = 0 ; i < monthlyStats.size() ; i++)
		{
			items.add(monthlyStats.get(i).getitem().getname());
		}
		
		modelAndView.addObject("items", items);
		modelAndView.setViewName("daily_dayEndEntry");
		
		return modelAndView;
	}
	
	public static ModelAndView setSearchInventory(ModelAndView modelAndView)
	{
		DAOInventory invDAO = new DAOInventory();
		List<Integer> months = invDAO.getDistinctMonths();
		List<Integer> years = invDAO.getDistinctYears();
		List<Location> locationsObj = invDAO.getDistinctLocation();
		List<String> locations = new ArrayList<String>();
		for(int i = 0 ; i < locationsObj.size() ; i++)
		{
			locations.add(locationsObj.get(i).getName());
		}
		modelAndView.addObject("months", months);
		modelAndView.addObject("years", years);
		modelAndView.addObject("locations", locations);
		modelAndView.setViewName("searchInventory");
		
		return modelAndView;
	}
	
	public static ModelAndView updateMonthlyRequirement(ModelAndView mD /*, int locationId*/) throws InstanceAlreadyExistsException
	{
		Context.instantiate(null);
		ServiceContext sc = Context.getServices();
		sc.beginTransaction();
		List location = (List) sc.getLocationService().findLocationById(1, false, null);
		Location Location = (com.ihs.locationmanagement.api.model.Location) location.get(0);
		sc.commitTransaction();
		sc.closeSession();
		Set<Location> childLocations =Location.getChildLocations();
		List<String> loc = new ArrayList<String>();
		for (Iterator<Location> it = childLocations.iterator(); it.hasNext();) {
			loc.add(it.next().getName());

		}
		mD.addObject("cLoc", loc);
	    DAOItem itemDAO = new DAOItem();
	    mD.addObject("item", itemDAO.getAllItems());
	    
	    mD.setViewName("updateRequirement");
		return mD;
		
	}

}
