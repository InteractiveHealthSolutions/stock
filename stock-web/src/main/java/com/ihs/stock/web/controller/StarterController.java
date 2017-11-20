package com.ihs.stock.web.controller;

import java.util.List;
import java.util.Set;

import javax.management.InstanceAlreadyExistsException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ihs.locationmanagement.api.context.Context;
import com.ihs.locationmanagement.api.context.ServiceContext;
import com.ihs.locationmanagement.api.model.Location;
import com.ihs.stock.api.DAO.DAOItem;
import com.ihs.stock.api.beans.AddItemBean;
import com.ihs.stock.api.beans.DayEndEntryBean;
import com.ihs.stock.api.beans.InventoryBean;
import com.ihs.stock.api.beans.MonthlyUpdateVaccinatorBean;
import com.ihs.stock.api.beans.MorningEntryBean;
import com.ihs.stock.api.beans.SearchInventoryBean;
import com.ihs.stock.api.beans.UpdateRequirementBean;
import com.ihs.stock.api.model.ILRDailyStatus;
import com.ihs.stock.api.model.Item;

@Controller
@RequestMapping("/start")

public class StarterController {
	
	@RequestMapping(value = "/additem" ,method = RequestMethod.GET)
	public ModelAndView item(ModelAndView mD)
	{
		mD.addObject("item", new AddItemBean());
		//mD.setViewName("add_items");
		mD = ControllerUtility.setAddItemForm(mD);
		return mD;
	}
	
	@RequestMapping(value = "/addinventory" ,method = RequestMethod.GET)
	public ModelAndView inventory(ModelAndView mD) throws InstanceAlreadyExistsException
	{
		mD.addObject("inventory", new InventoryBean());
		return ControllerUtility.setInventoryForm(mD);
	}
	
	@RequestMapping(value = "/updatemonthlystats" ,method = RequestMethod.GET)
	public ModelAndView monthlyUpdate(ModelAndView mD)
	{
		mD.addObject("monthlyUpdate", new MonthlyUpdateVaccinatorBean());
		return ControllerUtility.setMonthlyUpdateform(mD,1);
	}
	
	@RequestMapping(value = "/morningentry" ,method = RequestMethod.GET)
	public ModelAndView morningEntry(ModelAndView mD)
	{
		mD.addObject("morningentry", new ILRDailyStatus());
		return ControllerUtility.setDailyEntryMorningForm(mD, 1);
	}
	
	@RequestMapping(value = "/dayendentry" , method = RequestMethod.GET)
	public ModelAndView dayEndEntry(ModelAndView mD)
	{
		mD.addObject("dayendentry", new DayEndEntryBean());
		return ControllerUtility.setDailyEntryDayEnd(mD, 1);
	}
	
	@RequestMapping(value="/searchinventory" , method = RequestMethod.GET)
	public ModelAndView searchInventory(ModelAndView mD)
	{
		mD.addObject("search",new SearchInventoryBean());
		
		return ControllerUtility.setSearchInventory(mD);
		
	}
	@RequestMapping(value = "/updaterquirement", method = RequestMethod.GET)
	public ModelAndView updateMonthlyRequirement(ModelAndView mD/* , int locationId*/) throws InstanceAlreadyExistsException
	{
		mD.addObject("reqList", new UpdateRequirementBean());
		return ControllerUtility.updateMonthlyRequirement(mD/*, locationId*/);
		
	}
	
	
	
	

}
