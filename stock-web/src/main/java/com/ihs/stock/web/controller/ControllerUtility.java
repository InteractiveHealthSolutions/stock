package com.ihs.stock.web.controller;

import java.text.Collator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import com.ihs.stock.api.context.SessionFactoryUtil;
import javax.management.InstanceAlreadyExistsException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.ird.unfepi.context.LocationContext;
import org.ird.unfepi.context.LocationServiceContext;
import org.ird.unfepi.model.Location;
import org.springframework.web.servlet.ModelAndView;

import com.ihs.stock.api.DAO.DAOConsumer;
import com.ihs.stock.api.DAO.DAOInventory;
import com.ihs.stock.api.DAO.DAOItem;
import com.ihs.stock.api.DAO.DAOItemAttribute;
import com.ihs.stock.api.DAO.DAOItemAttributeType;
import com.ihs.stock.api.DAO.DAOItemType;

import com.ihs.stock.api.DAO.DAORequisition;
import com.ihs.stock.api.beans.AddItemAttributeBean;
import com.ihs.stock.api.beans.ApproveRequirementBean;
import com.ihs.stock.api.beans.DayEndEntryBean;
import com.ihs.stock.api.beans.SearchBean;
import com.ihs.stock.api.beans.UpdateRequirementBean;
import com.ihs.stock.api.context.ServiceContextStock;
import com.ihs.stock.api.model.Consumer;
import com.ihs.stock.api.model.ILRDailyStatus.Status;
import com.ihs.stock.api.model.Inventory;
import com.ihs.stock.api.model.Item;

import com.ihs.stock.api.model.MonthlyStats;
import com.ihs.stock.api.model.Requisition;
import com.ihs.stock.api.model.Item.ExpiryUnit;

import com.ihs.stock.api.model.ItemAttributeType;
import com.ihs.stock.api.model.ItemType;



public class ControllerUtility {

	public static ModelAndView setInventoryForm(ModelAndView modelAndView)
			throws InstanceAlreadyExistsException, ParseException {
		
		ServiceContextStock scSTK = SessionFactoryUtil.getServiceContext();
		List<String> i = (List<String>) scSTK.itemDAO.getAllItems();
		
		scSTK.closeSession();
		modelAndView.addObject("items", i);
		LocationServiceContext sc = LocationContext.getServices();
		
		List<Location> loc = sc.getLocationService().getAllLocation(false, null);
		sc.closeSession();

		List<String> locNames = new ArrayList<String>();
		for (int j = 0; j < loc.size(); j++) {
			String name = loc.get(j).getName();
			locNames.add(name);
		}

		modelAndView.addObject("locations", locNames);
		modelAndView.setViewName("add_inInventory");

		return modelAndView;

	}

	public static ModelAndView setAddItemForm(ModelAndView modelAndView) {
		
		ServiceContextStock scSTK = SessionFactoryUtil.getServiceContext();
		try {
			List<ItemType> types = (List<ItemType>) scSTK.itemTypeDAO.getAllItemTypes();
			modelAndView.addObject("type", types);
			List<ExpiryUnit> arrayList = new ArrayList<ExpiryUnit>();
			for (ExpiryUnit eu : ExpiryUnit.values()) {
				arrayList.add(eu);
			}

			modelAndView.addObject("expiryUnit", arrayList);

			List<Item> items = (List<Item>) scSTK.itemDAO.getallItems();
			modelAndView.addObject("list", items);

			List<ItemAttributeType> attr = scSTK.itemAttributeTypeDAO.getAllAttributesType();
			modelAndView.addObject("attr", attr);
			modelAndView.addObject("attrlist", attr);

			modelAndView.addObject("attrBean", new AddItemAttributeBean());
			modelAndView.addObject("items", items);
	        modelAndView.addObject("type", types);	
			modelAndView.setViewName("add_items");
		
			return modelAndView;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			scSTK.closeSession();
		}
		return null;

	}

//	public static ModelAndView setMonthlyUpdateform(ModelAndView modelAndView, int vac) {
//		
//		ServiceContextStock scSTK = SessionFactoryUtil.getServiceContext();
//		try {
//			Consumer consumer = scSTK.consumerDAO.getById(vac);
//			Location loc = consumer.getlocation();
//			List<Inventory> inventory = (List<Inventory>) scSTK.inventoryDAO.getBalanceForLocationAllItems(loc);
//			List<String> items = new ArrayList<String>();
//			for (int i = 0; i < inventory.size(); i++) {
//				Item item = scSTK.itemDAO.getById(inventory.get(i).getitem());
//				items.add(item.getname());
//			}
//			/// modelAndView.addObject("monthlyupdate", new
//			/// MonthlyUpdateVaccinatorBean());
//			modelAndView.addObject("items", items);
//			modelAndView.setViewName("update_monthlyStats");
//
//			return modelAndView;
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			scSTK.closeSession();
//		}
//		return null;
//	}

	public static ModelAndView setDailyEntryMorningForm(ModelAndView modelAndView, int vac , int loc) {
		List<Status> arrayList = new ArrayList<Status>();
		for (Status eu : Status.values()) {
			arrayList.add(eu);
		}
		modelAndView.addObject("array", arrayList);
        modelAndView.addObject("user", vac);
        modelAndView.addObject("loc", loc);
		modelAndView.setViewName("daily_morningEntry");

		return modelAndView;
	}

	public static ModelAndView setDailyEntryDayEnd(ModelAndView modelAndView, int loc)
			throws InstanceAlreadyExistsException, ParseException {

		
		ServiceContextStock scSTK = SessionFactoryUtil.getServiceContext();
		try {
			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR);
			List<Inventory> monthlyStats = scSTK.inventoryDAO.getForLocationMonthYear(loc, month, year);
			DayEndEntryBean deb = new DayEndEntryBean(loc, monthlyStats.size());
			List<String> items = new ArrayList<String>();
			for (int i = 0; i < monthlyStats.size(); i++) {
				Item item = scSTK.itemDAO.getById(monthlyStats.get(i).getitem());
				items.add(item.getname());
			}

			List<Status> arrayList = new ArrayList<Status>();
			for (Status eu : Status.values()) {
				arrayList.add(eu);
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			Date date = sdf.parse(sdf.format(new Date()));
			modelAndView.addObject("date", date);
			modelAndView.addObject("array", arrayList);
			modelAndView.addObject("deb", deb);
			modelAndView.addObject("loc", loc);
			modelAndView.addObject("item", items);
			modelAndView.setViewName("daily_dayEndEntry");

			return modelAndView;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			scSTK.closeSession();
		}
		return null;

	}

	public static ModelAndView setSearchInventory(ModelAndView modelAndView) {

		
		ServiceContextStock scSTK = SessionFactoryUtil.getServiceContext();
		try {
			List<Integer> months = scSTK.inventoryDAO.getDistinctMonths();
			List<Integer> years = scSTK.inventoryDAO.getDistinctYears();
			List<Location> locationsObj = scSTK.inventoryDAO.getDistinctLocation();
			List<String> locations = new ArrayList<String>();
			for (int i = 0; i < locationsObj.size(); i++) {
				locations.add(locationsObj.get(i).getName());
			}
			modelAndView.addObject("months", months);
			modelAndView.addObject("years", years);
			modelAndView.addObject("locations", locations);
			modelAndView.setViewName("searchInventory");

			return modelAndView;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			scSTK.closeSession();
		}
		return null;

	}

	public static ModelAndView updateMonthlyRequirement(/* ModelAndView mD , */int locationId , int user)
			throws InstanceAlreadyExistsException {
		LocationServiceContext sc = LocationContext.getServices();
		ServiceContextStock scSTK = SessionFactoryUtil.getServiceContext();
		try
		{
			Location location = sc.getLocationService().findLocationById(locationId, false, null);
			List<Location> childLocations = sc.getLocationService().getAllChildLocations(locationId, false, null);
			List<String> loc = new ArrayList<String>();
//			if(sc.getLocationService().getAllChildLocations(childLocations.get(0).getLocationId(), false, null).size() < 1)
//			{
//				for(int j = 0 ; j < childLocations.size() ; j++)
//				{
//					loc.add(childLocations.get(j).getName());
//				}
//			}
//			else
//			{
				for (int i = 0 ; i < childLocations.size() ; i++) {
					List<Location> UCLocations = sc.getLocationService().getAllChildLocations(childLocations.get(i).getLocationId(), false, null);
					for(int j = 0 ; j < UCLocations.size() ; j++)
					{
						loc.add(UCLocations.get(j).getName());
					}

				}
	//		}
			
			ModelAndView mD = new ModelAndView();
			Collections.sort(loc, Collator.getInstance());
			mD.addObject("town", location.getName());
			mD.addObject("cLoc", loc);
		
			
			mD.addObject("item", scSTK.itemDAO.getAllItems());
			mD.addObject("id", locationId);
			mD.addObject("user" , user);
			mD.setViewName("updateRequirement");
			return mD;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			scSTK.closeSession();
			sc.closeSession();
		}
		return null;

	}
	public static ModelAndView setRequirement(ModelAndView mD, int locationId)
			throws InstanceAlreadyExistsException {
		SessionFactory sf = SessionFactoryUtil.getSessionFactory(null, null);
		LocationServiceContext sc = LocationContext.getServices();
		ServiceContextStock scSTK = SessionFactoryUtil.getServiceContext();
	    try
	    {
	    	Location location = sc.getLocationService().findLocationById(locationId, false, null);
			List<Location> UCs = sc.getLocationService().getAllChildLocations(locationId, false, null);
			HashMap<String, ArrayList<Requisition>> map = new HashMap<String , ArrayList<Requisition>>();
			List<Location> vacCenter = new ArrayList<Location>();
			List<Item> items = (List<Item>) scSTK.itemDAO.getallItems();
			for(int i = 0 ; i < UCs.size() ; i++)
			{
				List<Requisition> allUCReq = new ArrayList<Requisition>();
				List<Location> vaccinationcenter = sc.getLocationService().getAllChildLocations(UCs.get(i).getLocationId(), false, null);
				vacCenter.addAll(vaccinationcenter);
				for(int j= 0 ; j < vaccinationcenter.size() ; j++)
				{
					List<Requisition> requisitions = scSTK.requisitionDAO.getForLocationAllMonths(vaccinationcenter.get(j).getLocationId());
					
					allUCReq.addAll(requisitions);
				}
			
			//	List<Requisition> requisitions = scSTK.requisitionDAO.getForLocationAllMonths(childLocations.get(i));
				if(allUCReq.size() > 0)
				{
					map.put(UCs.get(i).getName(), (ArrayList<Requisition>) allUCReq);
				}
				
			}
			mD.addObject("vacCenter" , vacCenter);
	    	mD.addObject("locId", location);
			mD.addObject("req", map);
			mD.addObject("i" , items);
			mD.setViewName("requisition");
			return mD;	
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

	public static ModelAndView setRequirementApproval(ModelAndView mD, int locationId , int user)
			throws InstanceAlreadyExistsException {

		
		LocationServiceContext sc = LocationContext.getServices();
		ServiceContextStock scSTK = SessionFactoryUtil.getServiceContext();
		try
		{
			Location location = sc.getLocationService().findLocationById(locationId, false, null);
			List<Location> childLocations = sc.getLocationService().getAllChildLocations(locationId, false, null);
			int arbSize = 0;
			List<Location> vacCenter = new ArrayList<Location>();
			List<Requisition> requisitionsUnApproved = new ArrayList<Requisition>();
			List<Item> items = (List<Item>) scSTK.itemDAO.getallItems();
			for(int i = 0 ; i < childLocations.size() ; i++)
			{
				List<Location> vaccinationcenter = sc.getLocationService().getAllChildLocations(childLocations.get(i).getLocationId(), false, null);
				vacCenter.addAll(vaccinationcenter);
			
				//requisitionsUnApproved = sc.requisitionDAO.getForLocationUnApproved(childLocations.get(i));
				requisitionsUnApproved.addAll(scSTK.requisitionDAO.getForLocationPending(childLocations.get(i).getLocationId()));
				
			}
	    	mD.addObject("locId", (Location)location);
			mD.addObject("req", requisitionsUnApproved);
			mD.addObject("vacCenter" , vacCenter);
			mD.addObject("i" , items);
			ApproveRequirementBean arb = new ApproveRequirementBean(requisitionsUnApproved.size());
			mD.addObject("urb", arb.getcheck());
			mD.setViewName("approve_req");
			return mD;
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			sc.closeSession();
		}
	    return null;

	}

	public static ModelAndView setDonePage(ModelAndView mD) {

		mD.setViewName("done");
		return mD;
	}
	
	public static ModelAndView setSearchILRGraph(ModelAndView modelAndView) {
		
		ServiceContextStock scSTK = SessionFactoryUtil.getServiceContext();
		LocationServiceContext sc = LocationContext.getServices();
		try
		{
			List<Integer> months = scSTK.ilrDailyStatusDAO.getDistinctMonths();
			List<Integer> years = scSTK.ilrDailyStatusDAO.getDistinctYears();
			List<Location> locationsObj = sc.getLocationService().findLocationByLocationType(12, false, null);
			
			List<String> locations = new ArrayList<String>();
			for (int i = 0; i < locationsObj.size(); i++) {
				locations.add(locationsObj.get(i).getName());
			}
			modelAndView.addObject("months", months);
			modelAndView.addObject("years", years);
			modelAndView.addObject("locations", locations);
			SearchBean sb = new SearchBean();
			sb.setmonth(months.get(0));
			sb.setyear(years.get(0));
			modelAndView.addObject("sb", sb);
			modelAndView.setViewName("ilrGraph");

			return modelAndView;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			sc.closeSession();
			scSTK.closeSession();
		}
		return null;
	}

}
