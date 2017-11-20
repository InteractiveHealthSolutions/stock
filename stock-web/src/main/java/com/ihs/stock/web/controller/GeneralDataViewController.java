package com.ihs.stock.web.controller;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.management.InstanceAlreadyExistsException;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ihs.stock.api.beans.SearchInventoryBean;
import com.ihs.stock.api.model.Inventory;
import com.ihs.stock.api.model.Item;
import com.ihs.locationmanagement.api.model.Location;
import com.ihs.stock.api.service.GeneralDataViewService;
import com.ihs.stock.web.validator.SearchBoxValidator;

@Controller
@RequestMapping("/view")
public class GeneralDataViewController {
	
	private SearchBoxValidator sbv;
	
	@InitBinder
	public void searchInitBinder(WebDataBinder binder)
	{
		binder.setValidator(sbv);
	}
	
	@RequestMapping(value= "/items" , method = RequestMethod.GET)
	public ModelAndView viewAllItems(ModelAndView modelAndView)
	{
		GeneralDataViewService gdvs = new GeneralDataViewService();
		modelAndView.addObject("list", gdvs.getAllItems());
		modelAndView.setViewName("view_allItems");
		return modelAndView;
		
	}
	
	@RequestMapping(value="/inventory" , method = RequestMethod.GET)
	public ModelAndView viewInventory(ModelAndView modelAndView)
	{
	
		GeneralDataViewService gdvs = new GeneralDataViewService();
		HashMap<Integer , List<Inventory>> data = gdvs.viewInventory();
		List<?> yearlyStats = gdvs.getYears();
		modelAndView.addObject("yearlyStats", yearlyStats);
		modelAndView.addObject("monthlyStats", data);
		modelAndView.setViewName("view_inventory");
		return modelAndView;
		
	}
	
	@RequestMapping(value="/statslocation" , method = RequestMethod.GET)
	public ModelAndView viewCurrentMonthStatsLocation(ModelAndView modelAndView)
	{
		
		GeneralDataViewService gdv = new GeneralDataViewService();
		
		HashMap<Location , HashMap<Item , List<String>>> mapLocInv = gdv.viewCurrentMonthStatsLocation();
		List<Location> loc = (List<Location>) gdv.getLocation();
		
	    Calendar cal = Calendar.getInstance();
	    String month = new DateFormatSymbols().getMonths()[(cal.get(Calendar.MONTH)+1)-1];
	    int year = cal.get(Calendar.YEAR);
	    
	    modelAndView.addObject("mon", month);
	    modelAndView.addObject("yr", year);
		modelAndView.addObject("loc", loc);
		modelAndView.addObject("map", mapLocInv);
		modelAndView.setViewName("view_inventorylocations");
		
		return modelAndView;
		
	}
	
	@RequestMapping(value = "/searchinventory" , method = RequestMethod.POST)
	public ModelAndView searchInventory(@ModelAttribute("search") @Validated SearchInventoryBean sib ,BindingResult result, ModelAndView modelAndView) throws InstanceAlreadyExistsException
	{
		SearchBoxValidator searchBoxValidator = new SearchBoxValidator();
		searchBoxValidator.validate(sib, result);
		
		if(result.hasErrors())
		{
			modelAndView = ControllerUtility.setSearchInventory(modelAndView);
			modelAndView.addObject("search", sib);
			modelAndView.setViewName("searchInventory");
		}
	    
		GeneralDataViewService gdvs = new GeneralDataViewService();
	    
	    List<Inventory> inventory = new ArrayList<Inventory>() ;
	    
	    if(sib.getlocationName().isEmpty() && sib.getmonth() != null && sib.getyear() != null)
	    {
	    	//year and month all locations
	    	
	    	inventory = gdvs.searchInventoryMonthYear(sib.getmonth(), sib.getyear());
	    }
	    else if(sib.getmonth() == null && !sib.getlocationName().isEmpty() && sib.getyear() != null)
	    {
	    	//whole year for a particular location
	    	inventory = gdvs.searchInventoryYearLocation(sib.getlocationName(), sib.getyear());
	    }
	    else if(sib.getyear() == null && !sib.getlocationName().isEmpty() && sib.getmonth() != null)
	    {
	    	//particular month and location
	    	inventory = gdvs.searchInventoryMonthLocation(sib.getlocationName(), sib.getmonth());
	    }
	    else if(sib.getyear() == null && sib.getmonth() == null && !sib.getlocationName().isEmpty())
	    {
	    	//all inventory for a particular location
	    	inventory = gdvs.searchInventoryLocation(sib.getlocationName());
	    }
	    else if(sib.getlocationName().isEmpty() && sib.getyear() == null && sib.getmonth() != null)
	    {
	    	//all inventory for a particular month
	    	inventory = gdvs.searchInventoryMonth(sib.getmonth());
	    }
	    else if(sib.getlocationName().isEmpty() && sib.getmonth() == null && sib.getyear() != null)
	    {
	    	//all inventory for a particular year
	    	inventory = gdvs.searchInventoryYear(sib.getyear());
	    }
	    else if(!sib.getlocationName().isEmpty() && sib.getmonth() != null && sib.getyear() != null)
	    {
	    	inventory = gdvs.searchInventory(sib.getlocationName(), sib.getyear(), sib.getmonth());
	    }
	    if(inventory.size() == 0 && !result.hasErrors())
	    {
	    	String errorMessage = "Data with this combination does not exist";
	    	modelAndView =ControllerUtility.setSearchInventory(modelAndView);
	    	modelAndView.addObject("error", errorMessage);
	    	modelAndView.setViewName("searchInventory");
	    	return modelAndView;
	    }
	   
	    modelAndView =ControllerUtility.setSearchInventory(modelAndView);
	    modelAndView.addObject("inventory", inventory);
	    
	    modelAndView.setViewName("searchInventory");
		return modelAndView;
		
	}

}
