package com.ihs.stock.api.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.management.InstanceAlreadyExistsException;

import org.hibernate.SessionFactory;
import org.ird.unfepi.context.LocationContext;
import org.ird.unfepi.context.LocationServiceContext;
import org.ird.unfepi.model.Location;

import com.ihs.stock.api.DAO.DAODailyStats;
import com.ihs.stock.api.DAO.DAOInventory;
import com.ihs.stock.api.DAO.DAOItem;
import com.ihs.stock.api.DAO.DAOItemAttributeType;
import com.ihs.stock.api.context.ServiceContextStock;
import com.ihs.stock.api.context.SessionFactoryUtil;
import com.ihs.stock.api.model.Inventory;
import com.ihs.stock.api.model.Item;
import com.ihs.stock.api.model.ItemAttributeType;

public class GeneralDataViewService {

	public List<?> getAllItems() {
		ServiceContextStock scSTK = SessionFactoryUtil.getServiceContext();

		try
		{
			List<Item> items = (List<Item>) scSTK.itemDAO.getallItems();
			return items;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			scSTK.closeSession();
		}
		
		return null;

	}

	public List<?> getYears() {
		ServiceContextStock scSTK = SessionFactoryUtil.getServiceContext();
		try
		{
			List<Integer> years = scSTK.inventoryDAO.getDistinctYears();
			return years;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			scSTK.closeSession();
		}
		return null;
		
	}

	public HashMap viewInventory() {
		ServiceContextStock scSTK = SessionFactoryUtil.getServiceContext();

		try
		{
			List<Integer> years = scSTK.inventoryDAO.getDistinctYears();
			List<Item> items = scSTK.inventoryDAO.getDistinctItems();

			HashMap<Integer, List<Inventory>> monthly = new HashMap<Integer, List<Inventory>>();
			for (int i = 0; i < years.size(); i++) {
				// List<Integer> stats = (List<Integer>)
				// invDAO.aggregateBalanceYearly(years.get(i));
				// yearlyAggregate.put(years.get(i), stats);
				List<Inventory> monthlyInventory = (List<Inventory>) scSTK.inventoryDAO.getAllMonthsInventory(years.get(i));
				monthly.put(years.get(i), monthlyInventory);
			}
			return monthly;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			scSTK.closeSession();
		}
		
		return null;

	}

	public List<?> getLocation() {
		
		ServiceContextStock scSTK = SessionFactoryUtil.getServiceContext();
        try
        {
        	List<Location> loc = scSTK.inventoryDAO.getDistinctLocation();
        	return loc;
        }
		catch(Exception e)
        {
			e.printStackTrace();
        }
        finally
        {
        	scSTK.closeSession();
        }
		return null;
		
		
	}

	public HashMap<Location, HashMap<Item, List<String>>> viewCurrentMonthStatsLocation() {
		List<Location> locations = (List<Location>) getLocation();
		
		ServiceContextStock scSTK = SessionFactoryUtil.getServiceContext();
		try
		{
			List<Item> items = scSTK.inventoryDAO.getDistinctItems();
			HashMap<Location, HashMap<Item, List<String>>> mapLocInv = new HashMap<Location, HashMap<Item, List<String>>>();

			String wasted = "";
			String used = "";
			Integer balance = 0;
			Integer initial = 0;
			Integer total = 0;

			List<String> itemStats = new ArrayList<String>();
			Inventory inv;
			HashMap<Item, List<String>> itemMap = new HashMap<Item, List<String>>();
			for (int i = 0; i < locations.size(); i++) {
				itemMap = new HashMap<Item, List<String>>();
				List<Item> itemsList = (List<Item>) scSTK.inventoryDAO.getCurrentMonthItems(locations.get(i).getLocationId());
				for (int j = 0; j < itemsList.size(); j++) {
					itemStats = new ArrayList<String>();
					if (!scSTK.dailyStatsDAO.existSumWastedQuantity(locations.get(i).getLocationId(), itemsList.get(j).getitemId())) {
						wasted = "not available";
					} else {
						wasted = scSTK.dailyStatsDAO.getSumWastedQuantity(locations.get(i).getLocationId(), itemsList.get(j).getitemId());
					}
					if (!scSTK.dailyStatsDAO.existSumUsedQuantity(locations.get(i).getLocationId(), itemsList.get(j).getitemId())) {
						used = "not available ";
					} else {
						used = scSTK.dailyStatsDAO.getSumUsedQuantity(locations.get(i).getLocationId(), itemsList.get(j).getitemId());
					}

					inv = scSTK.inventoryDAO.getBalanceForLocationMonthItem(locations.get(i).getLocationId(), itemsList.get(j).getitemId());
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
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			scSTK.closeSession();
		}
		return null;
		

	}

	public List<Inventory> searchInventory(String locationName, int year, int month)
			throws InstanceAlreadyExistsException {
		LocationServiceContext sc = LocationContext.getServices();
		ServiceContextStock scSTK = SessionFactoryUtil.getServiceContext();

		// DAOLocationImpl locationDAO = new DAOLocationImpl(sc.);
		try
		{
			Location loc = sc.getLocationService().findLocationByName(locationName, false, null);
			List<Inventory> inventory = scSTK.inventoryDAO.getForLocationMonthYear( loc.getLocationId(), month, year);
			return inventory;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			scSTK.closeSession();
			sc.closeSession();
		}
		
		return null;
	

	}

	public List<Inventory> searchInventoryMonthYear(int month, int year) {
		
		ServiceContextStock scSTK = SessionFactoryUtil.getServiceContext();
		try
		{
			List<Inventory> inventory = scSTK.inventoryDAO.getForYearMonth(month, year);
			return inventory;
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally
		{
			scSTK.closeSession();
		}
		return null;
		
	}

	public List<Inventory> searchInventoryYearLocation(String locationName, int year)
			throws InstanceAlreadyExistsException {
		LocationServiceContext sc = LocationContext.getServices();
		// DAOLocationImpl locationDAO = new DAOLocationImpl(sc.);
		ServiceContextStock scSTK = SessionFactoryUtil.getServiceContext();
        try
        {
        	Location loc = sc.getLocationService().findLocationByName(locationName, false, null);
        	List<Inventory> inventory = scSTK.inventoryDAO.getForYearLocation(loc.getLocationId(), year);
        	return inventory;
        }
		catch(Exception e)
        {
			e.printStackTrace();
        }
        finally
        {
        	scSTK.closeSession();
        }
		return null;
	}

	public List<Inventory> searchInventoryMonthLocation(String locationName, int month)
			throws InstanceAlreadyExistsException {
		LocationServiceContext sc = LocationContext.getServices();
		// DAOLocationImpl locationDAO = new DAOLocationImpl(sc.);
		ServiceContextStock scSTK = SessionFactoryUtil.getServiceContext();
		try
		{
			Location loc = sc.getLocationService().findLocationByName(locationName, false, null);
			
			List<Inventory> inventory = scSTK.inventoryDAO.getForMonthLocation(loc.getLocationId(), month);
			return inventory;
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally
		{
			scSTK.closeSession();
			sc.closeSession();
		}
		return null;
		
		
	}

	public List<Inventory> searchInventoryLocation(String locationName) throws InstanceAlreadyExistsException {

		LocationServiceContext sc = LocationContext.getServices();
		ServiceContextStock scSTK = SessionFactoryUtil.getServiceContext();
		try
		{
			Location loc = sc.getLocationService().findLocationByName(locationName, false, null);
			List<Inventory> inventory = scSTK.inventoryDAO.getForLocation(loc.getLocationId());	
			return inventory;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			scSTK.closeSession();
			sc.closeSession();
		}
		
	 return null;
		
	}

	public List<Inventory> searchInventoryMonth(int month) {
		
		ServiceContextStock scSTK = SessionFactoryUtil.getServiceContext();
		try
		{
			List<Inventory> inventory = scSTK.inventoryDAO.getForMonth(month);
			return inventory;
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally
		{
			scSTK.closeSession();
		}
		return null;
		
	}

	public List<Inventory> searchInventoryYear(int year) {
		
		ServiceContextStock scSTK = SessionFactoryUtil.getServiceContext();
		try
		{
			List<Inventory> inventory = (List<Inventory>) scSTK.inventoryDAO.getAllMonthsInventory(year);
			return inventory;
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally
		{
			scSTK.closeSession();
		}
		return null;
	
		
	}

	public List<ItemAttributeType> getAllAttributeTypes() {
		
		ServiceContextStock scSTK = SessionFactoryUtil.getServiceContext();
		try
		{
			List<ItemAttributeType> attributeTypes = scSTK.itemAttributeTypeDAO.getAllAttributesType();
			return attributeTypes;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			scSTK.closeSession();
		}
		return null;
		
	
	}
}
