package com.ihs.stock.web.controller;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.management.InstanceAlreadyExistsException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ihs.locationmanagement.api.context.Context;
import com.ihs.locationmanagement.api.context.ServiceContext;
import com.ihs.locationmanagement.api.model.Location;
import com.ihs.stock.api.DAO.DAOItem;
import com.ihs.stock.api.DAO.DAOItemAttribute;
import com.ihs.stock.api.DAO.DAOItemAttributeType;
import com.ihs.stock.api.DAO.DAORequisition;
import com.ihs.stock.api.beans.AddItemAttributeBean;
import com.ihs.stock.api.beans.AddItemBean;
import com.ihs.stock.api.beans.InventoryBean;
import com.ihs.stock.api.beans.UpdateRequirementBean;
import com.ihs.stock.api.model.Item;
import com.ihs.stock.api.model.ItemAttributeType;
import com.ihs.stock.api.model.Requisition;
import com.ihs.stock.api.service.AddInInventoryService;
import com.ihs.stock.api.service.AddItemsService;
import com.ihs.stock.web.validator.AddItemValidator;
import com.ihs.stock.web.validator.InventoryFormValidator;
import com.ihs.stock.web.validator.RequisitionFormValidator;
import com.ihs.stock.api.model.ItemAttribute;
@Controller
@RequestMapping("/add")

public class AddController {
	
	
	private InventoryFormValidator inventoryFormValidator;
	private AddItemValidator addItemValidator;
	private RequisitionFormValidator requisitionFormValidator;
	private static final Logger logger = Logger.getLogger(AddController.class);
	
	
	@InitBinder("item")
	private void itemInitBinder(WebDataBinder binder)
	{
		binder.setValidator(addItemValidator);
	}
	@InitBinder("inventory")
	private void inventoryInitBinder(WebDataBinder binder)
	{
		binder.setValidator(inventoryFormValidator);
	}
	@InitBinder("reqList")
	private void requisitionInitBinder(WebDataBinder binder)
	{
		binder.setValidator(requisitionFormValidator);
	}
	@RequestMapping(value = "/item" , method=RequestMethod.POST)
	public ModelAndView addItems(@ModelAttribute("item") @Validated AddItemBean item, BindingResult results,HttpSession session , 
			 HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) throws ParseException
	{
		addItemValidator = new AddItemValidator();
		addItemValidator.validate(item, results);
		if(results.hasErrors())
		{
			modelAndView = ControllerUtility.setAddItemForm(modelAndView);
			//modelAndView.addObject("item", item);
			modelAndView.setViewName("add_items");	
			
			return modelAndView;
		}
		AddItemsService sr = new AddItemsService();
		sr.AddItems(item);
		
		System.out.println(item.getcolumnName().toString());
		modelAndView = ControllerUtility.setAddItemForm(modelAndView);
		//modelAndView.setViewName("add_items");
		return modelAndView;
	}
	
	@RequestMapping(value = "/itemattr" , method = RequestMethod.POST)
	public ModelAndView addItemAttributes(@ModelAttribute("attrBean") AddItemAttributeBean aiab , ModelAndView modelAndView) throws ParseException
	{
		DAOItem itemDAO = new DAOItem();
		Item item = itemDAO.getByName(aiab.getitemName());
		DAOItemAttribute itemAttributeDAO = new DAOItemAttribute();
		ItemAttribute itemAttr = new ItemAttribute() ;
		itemAttr.setitem(item);
	    DAOItemAttributeType itemAttrType = new DAOItemAttributeType();
	    List<ItemAttributeType> attrtype = itemAttrType.getById(aiab.getatype());
		itemAttr.setitemAttributeType(attrtype.get(0));
		itemAttr.setValue(aiab.getvalue());
		itemAttributeDAO.save(itemAttr);
		modelAndView = ControllerUtility.setAddItemForm(modelAndView);
		modelAndView.setViewName("add_items");
		return modelAndView;
		
	}
	
	@RequestMapping(value = "/attributetype" , method = RequestMethod.POST)
	public ModelAndView addItemAttributeType(@ModelAttribute("attributeType") @Validated ItemAttributeType iat , BindingResult results , 
			ModelAndView modelAndView)
	{
		AddItemsService addItemsService = new AddItemsService();
		addItemsService.addItemAttributeType(iat);
		modelAndView = ControllerUtility.setAddItemForm(modelAndView);
		modelAndView.setViewName("add_items");
		return modelAndView;
		
	}
	
	
	@RequestMapping(value = "/inventory" , method = RequestMethod.POST)
	public ModelAndView addInInventory(@ModelAttribute("inventory") @Validated InventoryBean ib, BindingResult results,HttpSession session , 
			 HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) throws InstanceAlreadyExistsException
	{
		
		inventoryFormValidator = new InventoryFormValidator();
		inventoryFormValidator.validate(ib, results);
		if(results.hasErrors())
		{
			modelAndView = ControllerUtility.setInventoryForm(modelAndView);
			//modelAndView.addObject("inventory", ib);
		//	modelAndView.setViewName("add_inInventory");
			return modelAndView;
			
		}
		
		AddInInventoryService addInInventory = new AddInInventoryService();
		try {
			if(!ib.getparentLocation().isEmpty())
			{
				addInInventory.insertInInventory(ib);
			}
			else
			{
				addInInventory.insertInParentInventory(ib);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(logger.isDebugEnabled())
		{
			logger.debug(ib.getinventoryInitialVialsCount()+" containers transferred to "+ib.getconsumerLocation()+""
					+ "from "+ib.getparentLocation());
		}
		return new ModelAndView("done");
		
	}
	
	@RequestMapping(value= "/updatereq" , method = RequestMethod.POST)
	public ModelAndView addRequirement(@ModelAttribute("reqList") UpdateRequirementBean urb ,BindingResult result , ModelAndView modelAndView) throws InstanceAlreadyExistsException, ParseException
	{
		requisitionFormValidator = new RequisitionFormValidator();
		requisitionFormValidator.validate(urb, result);
		if(result.hasErrors())
		{
			modelAndView = ControllerUtility.updateMonthlyRequirement(modelAndView);
			return modelAndView;
		}
		AddInInventoryService ais = new AddInInventoryService();
		ais.updateRequirement(urb);
		
		return new ModelAndView("done");
		
	}
	
	
	
}
