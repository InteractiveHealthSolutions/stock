package com.ihs.stock.api.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.management.InstanceAlreadyExistsException;



import com.ihs.stock.api.DAO.DAODailyStats;
import com.ihs.stock.api.DAO.DAOInventory;
import com.ihs.stock.api.DAO.DAOItem;
import com.ihs.stock.api.DAO.DAOItemAttributeType;

import com.ihs.stock.api.model.Inventory;
import com.ihs.stock.api.model.Item;
import com.ihs.stock.api.model.ItemAttributeType;
import com.ihs.locationmanagement.api.context.Context;
import com.ihs.locationmanagement.api.context.ServiceContext;
import com.ihs.locationmanagement.api.model.Location;

public class GeneralDataViewService {

	public List<?> getAllItems()
	{
		DAOItem itemDAO = new DAOItem();
		
		return itemDAO.getallItems();
		
	}
	public List<?> getYears()
	{
		DAOInventory invDAO = new DAOInventory();
		List<Integer> years = invDAO.getDistinctYears();
		return years;
	}
	public HashMap viewInventory()
	{
		DAOInventory invDAO = new DAOInventory();
		List<Integer> years = invDAO.getDistinctYears();
		List<Item> items = invDAO.getDistinctItems();
		
		HashMap<Integer , List<Inventory>> monthly = new HashMap<Integer , List<Inventory>>();
		for (int i = 0; i < years.size(); i++) {
//			List<Integer> stats = (List<Integer>) invDAO.aggregateBalanceYearly(years.get(i));
//			yearlyAggregate.put(years.get(i), stats);
			List<Inventory> monthlyInventory = (List<Inventory>) invDAO.getAllMonthsInventory(years.get(i));
			monthly.put(years.get(i), monthlyInventory);
		}
		
		return monthly;
		
		
	}
	
	public List<?> getLocation()
	{
		DAOInventory inventoryDAO = new DAOInventory();
		List<Location> loc = inventoryDAO.getDistinctLocation();
		return loc;
	}
	public HashMap<Location, HashMap<Item, List<String>>> viewCurrentMonthStatsLocation()
	{
		List<Location> locations = (List<Location>) getLocation();
		DAOInventory invDAO = new DAOInventory();
		List<Item> items = invDAO.getDistinctItems();
		HashMap<Location , HashMap<Item , List<String>>> mapLocInv = new HashMap<Location , HashMap<Item , List<String>>>();
		DAODailyStats dailyStatsDAO = new DAODailyStats();
		String wasted = "";
		String used = "";
		Integer balance = 0 ;
		Integer initial = 0 ;
		Integer total = 0;
		
		List<String> itemStats = new ArrayList<String>();
		Inventory inv ;
		HashMap<Item , List<String>> itemMap = new HashMap<Item, List<String>>();
		for(int i = 0 ; i < locations.size() ; i++)
		{
			itemMap = new HashMap<Item, List<String>>();
			List<Item> itemsList = (List<Item>) invDAO.getCurrentMonthItems(locations.get(i));
			for (int j = 0; j < itemsList.size(); j++) {
				itemStats = new ArrayList<String>();
				if(!dailyStatsDAO.existSumWastedQuantity(locations.get(i), itemsList.get(j)))
				{
				   wasted = "not available";	
				}
				else
				{
					wasted = dailyStatsDAO.getSumWastedQuantity(locations.get(i), itemsList.get(j));
				}
				if(!dailyStatsDAO.existSumUsedQuantity(locations.get(i), itemsList.get(j)))
				{
					used = "not available ";
				}
				else
				{
					used = dailyStatsDAO.getSumUsedQuantity(locations.get(i), itemsList.get(j));
				}
				
				
				inv = invDAO.getBalanceForLocationMonthItem(locations.get(i), itemsList.get(j));
				initial = inv.getcurrentMonthContainers() * itemsList.get(j).getenclosedQuantity();
				itemStats.add(initial.toString());
				balance = inv.getprevMonthBalance() * itemsList.get(j).getenclosedQuantity();
				itemStats.add(balance.toString());
				total = inv.gettotalContainers() * itemsList.get(j).getenclosedQuantity();
				
				itemStats.add(wasted);
				itemStats.add(used);
				itemStats.add(total.toString());
				itemMap.put(itemsList.get(j), itemStats);
			}
			mapLocInv.put(locations.get(i), itemMap);
		}
		return mapLocInv;
		
	}
	
	public List<Inventory> searchInventory(String locationName , int year , int month) throws InstanceAlreadyExistsException
	{
		Context.instantiate(null);
		ServiceContext sc = Context.getServices();
		// DAOLocationImpl locationDAO = new DAOLocationImpl(sc.);
		sc.beginTransaction();
		List loc = sc.getLocationService().findLocationByName(locationName, false, null);
sc.commitTransaction();
sc.closeSession();
		DAOInventory inventoryDAO = new DAOInventory();
		List<Inventory> inventory = inventoryDAO.getForLocationMonthYear((Location) loc.get(0), month, year);
		return inventory;
		
	}
	
	public List<Inventory> searchInventoryMonthYear(int month , int year)
	{
		DAOInventory inventoryDAO = new DAOInventory();
		List<Inventory> inventory = inventoryDAO.getForYearMonth(month, year);
		return inventory;
	}
	
	public List<Inventory> searchInventoryYearLocation(String locationName , int year) throws InstanceAlreadyExistsException
	{
		Context.instantiate(null);
		ServiceContext sc = Context.getServices();
		// DAOLocationImpl locationDAO = new DAOLocationImpl(sc.);
		sc.beginTransaction();
		List loc = sc.getLocationService().findLocationByName(locationName, false, null);
sc.commitTransaction();
sc.closeSession();
		DAOInventory inventoryDAO = new DAOInventory();
		List<Inventory> inventory = inventoryDAO.getForYearLocation((Location) loc.get(0), year);
		return inventory;
	}
	
	public List<Inventory> searchInventoryMonthLocation(String locationName , int month) throws InstanceAlreadyExistsException
	{
		Context.instantiate(null);
		ServiceContext sc = Context.getServices();
		// DAOLocationImpl locationDAO = new DAOLocationImpl(sc.);
		sc.beginTransaction();
		List loc = sc.getLocationService().findLocationByName(locationName, false, null);
sc.commitTransaction();
sc.closeSession();
		DAOInventory inventoryDAO = new DAOInventory();
		List<Inventory> inventory = inventoryDAO.getForMonthLocation((Location) loc.get(0), month);
		return inventory;
	}
	
	public List<Inventory> searchInventoryLocation(String locationName) throws InstanceAlreadyExistsException
	{
		
		Context.instantiate(null);
		ServiceContext sc = Context.getServices();
		// DAOLocationImpl locationDAO = new DAOLocationImpl(sc.);
		sc.beginTransaction();
		List loc = sc.getLocationService().findLocationByName(locationName, false, null);
sc.commitTransaction();
sc.closeSession();
		DAOInventory inventoryDAO = new DAOInventory();
		List<Inventory> inventory = inventoryDAO.getForLocation((Location) loc.get(0));
		return inventory;
	}
	
	public List<Inventory> searchInventoryMonth(int month)
	{
		DAOInventory inventoryDAO = new DAOInventory();
		List<Inventory> inventory = inventoryDAO.getForMonth(month);
		return inventory;
	}
	
	public List<Inventory> searchInventoryYear(int year)
	{
		DAOInventory inventoryDAO = new DAOInventory();
		List<Inventory> inventory = (List<Inventory>) inventoryDAO.getAllMonthsInventory(year);
		return inventory;
	}
	
	public List<ItemAttributeType> getAllAttributeTypes()
	{
		DAOItemAttributeType attributeType = new DAOItemAttributeType();
		List<ItemAttributeType> attributeTypes = attributeType.getAllAttributesType();
		return attributeTypes;
	}
}
