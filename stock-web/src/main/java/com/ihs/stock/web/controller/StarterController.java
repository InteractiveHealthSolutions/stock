package com.ihs.stock.web.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Set;

import javax.management.InstanceAlreadyExistsException;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ihs.stock.api.DAO.DAOItem;
import com.ihs.stock.api.DAO.DAORequisition;
import com.ihs.stock.api.beans.AddItemBean;
import com.ihs.stock.api.beans.DayEndEntryBean;
import com.ihs.stock.api.beans.InventoryBean;
import com.ihs.stock.api.beans.MonthlyUpdateVaccinatorBean;
import com.ihs.stock.api.beans.MorningEntryBean;
import com.ihs.stock.api.beans.SearchBean;

import com.ihs.stock.api.beans.UpdateRequirementBean;
import com.ihs.stock.api.context.SessionFactoryUtil;
import com.ihs.stock.api.model.ILRDailyStatus;
import com.ihs.stock.api.model.Item;
import com.ihs.stock.api.model.Requisition;

@Controller
@RequestMapping("/start")
public class StarterController {
	@RequestMapping(method = RequestMethod.GET)
	public void get() {
		System.out.println("I m here finally");
	}
	
	@RequestMapping(value="/mainpage" , method = RequestMethod.GET)
	public ModelAndView mainPage(ModelAndView mD)
	{
       SessionFactory sf = SessionFactoryUtil.getSessionFactory(null, null);
	   mD.setViewName("mainpage");
	   return mD;
	}
	@RequestMapping(value = "/additem" ,method = RequestMethod.GET)
	public ModelAndView item(ModelAndView mD)
	{
		mD.addObject("item", new AddItemBean());
		//mD.setViewName("add_items");
		mD = ControllerUtility.setAddItemForm(mD);
		return mD;
	}
	@RequestMapping(value = "/ilr" , method = RequestMethod.GET)
	public ModelAndView ilrGraph(ModelAndView mD)
	{
		//SessionFactory sf = SessionFactoryUtil.getSessionFactory(null, null);
		mD.addObject("sb", new SearchBean());
		return ControllerUtility.setSearchILRGraph(mD);
	}
	@RequestMapping(value = "/req/{locationId}" , method = RequestMethod.GET)
	public ModelAndView AllReq(@PathVariable("locationId") Integer locationId, ModelAndView mD) throws InstanceAlreadyExistsException, ParseException
	{
		//SessionFactory sf = SessionFactoryUtil.getSessionFactory(null, null);
		
			return ControllerUtility.setRequirement(mD,locationId);
	}
	
	@RequestMapping(value = "/addinventory" ,method = RequestMethod.GET)
	public ModelAndView inventory(ModelAndView mD) throws InstanceAlreadyExistsException, ParseException
	{
		mD.addObject("inventory", new InventoryBean());
		return ControllerUtility.setInventoryForm(mD);
	}
	
//	@RequestMapping(value = "/updatemonthlystats" ,method = RequestMethod.GET)
//	public ModelAndView monthlyUpdate(ModelAndView mD)
//	{
//		mD.addObject("monthlyUpdate", new MonthlyUpdateVaccinatorBean());
//		return ControllerUtility.setMonthlyUpdateform(mD,1);
//	}
	
	@RequestMapping(value = "/morningentry/{user}/{location}" ,method = RequestMethod.GET)
	public ModelAndView morningEntry(ModelAndView mD , @PathVariable("user") Integer user
			, @PathVariable("location") Integer location)
	{
		mD.addObject("morningentry", new ILRDailyStatus());
		return ControllerUtility.setDailyEntryMorningForm(mD, user , location);
	}
	
	@RequestMapping(value = "/dayendentry/{location}" , method = RequestMethod.GET)
	public ModelAndView dayEndEntry(@PathVariable("location") Integer loc,ModelAndView mD) throws InstanceAlreadyExistsException, ParseException
	{
		
		//mD.addObject("dayendentry", new DayEndEntryBean(loc));
		return ControllerUtility.setDailyEntryDayEnd(mD, loc);
	}
	
	@RequestMapping(value="/searchinventory" , method = RequestMethod.GET)
	public ModelAndView searchInventory(ModelAndView mD)
	{
		mD.addObject("search",new SearchBean());
		
		return ControllerUtility.setSearchInventory(mD);
		
	}
	@RequestMapping(value = "/updaterquirement/{user}/{location}", method = RequestMethod.GET)
	public ModelAndView updateMonthlyRequirement(ModelAndView mD , @PathVariable("user") Integer user, @PathVariable("location") int locationId) throws InstanceAlreadyExistsException
	{
		mD.addObject("reqList", new UpdateRequirementBean());
		return ControllerUtility.updateMonthlyRequirement(locationId , user);
		
	}
	
	@RequestMapping(value="/approvereq/{user}/{location}")
	public ModelAndView approveRequest(ModelAndView mD , @PathVariable("user") int user, @PathVariable("location") int locationId) throws InstanceAlreadyExistsException
	{
		return ControllerUtility.setRequirementApproval(mD, locationId , user);
	}
	
	
	
	
	
	

}
