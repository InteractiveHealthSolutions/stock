package com.ihs.stock.web.controller;

import java.sql.ResultSet;
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
import org.ird.unfepi.context.LocationContext;
import org.ird.unfepi.context.LocationServiceContext;
import org.ird.unfepi.model.Location;
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

import com.ihs.stock.api.DAO.DAOInventory;
import com.ihs.stock.api.DAO.DAOItem;
import com.ihs.stock.api.DAO.DAOItemAttribute;
import com.ihs.stock.api.DAO.DAOItemAttributeType;
import com.ihs.stock.api.DAO.DAORequisition;
import com.ihs.stock.api.beans.AddItemAttributeBean;
import com.ihs.stock.api.beans.AddItemBean;
import com.ihs.stock.api.beans.ApproveRequirementBean;
import com.ihs.stock.api.beans.InventoryBean;
import com.ihs.stock.api.beans.UpdateRequirementBean;
import com.ihs.stock.api.context.ServiceContextStock;
import com.ihs.stock.api.context.SessionFactoryUtil;
import com.ihs.stock.api.model.Inventory;
import com.ihs.stock.api.model.Item;
import com.ihs.stock.api.model.ItemAttributeType;
import com.ihs.stock.api.model.Requisition;
import com.ihs.stock.api.service.AddInInventoryService;
import com.ihs.stock.api.service.AddItemsService;
import com.ihs.stock.web.validator.AddItemValidator;
import com.ihs.stock.web.validator.ApproveRequirementValidator;
import com.ihs.stock.web.validator.InventoryFormValidator;
import com.ihs.stock.web.validator.RequisitionFormValidator;

import javassist.bytecode.Descriptor.Iterator;

import com.ihs.stock.api.model.ItemAttribute;

@Controller
@RequestMapping("/add")

public class AddController {

	private InventoryFormValidator inventoryFormValidator;
	private AddItemValidator addItemValidator;
	private RequisitionFormValidator requisitionFormValidator;
	private ApproveRequirementValidator approveRequirementValidator;

	@InitBinder("item")
	private void itemInitBinder(WebDataBinder binder) {
		binder.setValidator(addItemValidator);
	}

	@InitBinder("inventory")
	private void inventoryInitBinder(WebDataBinder binder) {
		binder.setValidator(inventoryFormValidator);
	}

	@InitBinder("reqList")
	private void requisitionInitBinder(WebDataBinder binder) {
		binder.setValidator(requisitionFormValidator);
	}

	@InitBinder("urb")
	private void approvalInitBinder(WebDataBinder binder) {
		binder.setValidator(approveRequirementValidator);
	}

	@RequestMapping(value = "/item", method = RequestMethod.POST)
	public ModelAndView addItems(@ModelAttribute("item") @Validated AddItemBean item, BindingResult results,
			HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView)
			throws ParseException {
		addItemValidator = new AddItemValidator();
		addItemValidator.validate(item, results);
		if (results.hasErrors()) {
			modelAndView = ControllerUtility.setAddItemForm(modelAndView);
			// modelAndView.addObject("item", item);
			modelAndView.setViewName("add_items");

			return modelAndView;
		}
		AddItemsService sr = new AddItemsService();
		sr.AddItems(item);

		modelAndView = ControllerUtility.setAddItemForm(modelAndView);
		// modelAndView.setViewName("add_items");
		return modelAndView;
	}

	@RequestMapping(value = "/itemattr", method = RequestMethod.POST)
	public ModelAndView addItemAttributes(@ModelAttribute("attrBean") AddItemAttributeBean aiab,
			ModelAndView modelAndView) throws ParseException {

		ServiceContextStock scSTK = SessionFactoryUtil.getServiceContext();

		Item item = scSTK.itemDAO.getByName(aiab.getitemName());

		ItemAttribute itemAttr = new ItemAttribute();
		itemAttr.setitem(item.getitemId());

		List<ItemAttributeType> attrtype = scSTK.itemAttributeTypeDAO.getById(aiab.getatype());
		itemAttr.setitemAttributeType(attrtype.get(0).getitemAttributeTypeId());
		itemAttr.setValue(aiab.getvalue());
		scSTK.itemAttributeDAO.save(itemAttr);
		scSTK.commitTransaction();
		scSTK.closeSession();
		modelAndView = ControllerUtility.setAddItemForm(modelAndView);
		modelAndView.setViewName("add_items");
		return modelAndView;

	}

	@RequestMapping(value = "/attributetype", method = RequestMethod.POST)
	public ModelAndView addItemAttributeType(@ModelAttribute("attributeType") @Validated ItemAttributeType iat,
			BindingResult results, ModelAndView modelAndView) {
		AddItemsService addItemsService = new AddItemsService();
		addItemsService.addItemAttributeType(iat);
		modelAndView = ControllerUtility.setAddItemForm(modelAndView);
		modelAndView.setViewName("add_items");
		return modelAndView;

	}

	@RequestMapping(value = "/inventory", method = RequestMethod.POST)
	public ModelAndView addInInventory(@ModelAttribute("inventory") @Validated InventoryBean ib, BindingResult results,
			HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView)
			throws InstanceAlreadyExistsException, ParseException {

		inventoryFormValidator = new InventoryFormValidator();
		inventoryFormValidator.validate(ib, results);
		if (results.hasErrors()) {
			modelAndView = ControllerUtility.setInventoryForm(modelAndView);
			return modelAndView;

		}

		AddInInventoryService addInInventory = new AddInInventoryService();
		try {
			if (!ib.getparentLocation().isEmpty()) {
				addInInventory.insertInInventory(ib);
			} else {
				addInInventory.insertInParentInventory(ib);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("done");

	}

	@RequestMapping(value = "/updatereq/{userID}/{location}", method = RequestMethod.POST)
	public ModelAndView addRequirement(@ModelAttribute("reqList") UpdateRequirementBean urb, BindingResult result,
			ModelAndView modelAndView, @PathVariable("userID") Integer userId,
			@PathVariable("location") Integer location) throws InstanceAlreadyExistsException, ParseException {
		requisitionFormValidator = new RequisitionFormValidator();
		requisitionFormValidator.validate(urb, result);
		if (result.hasErrors()) {

			modelAndView = ControllerUtility.updateMonthlyRequirement(location, userId);
			// modelAndView.setViewName("updateRequirement");

			return modelAndView;
		}

		AddInInventoryService ais = new AddInInventoryService();
		ais.updateRequirement(urb, userId, location);
        modelAndView = ControllerUtility.updateMonthlyRequirement(location,userId); 
        modelAndView.addObject("message","Form Submitted" );
		return modelAndView;

	}

	@RequestMapping(value = "/approvereq/{user}/{locationId}")
	public ModelAndView approveReq(@ModelAttribute("urb") ApproveRequirementBean arb, BindingResult results,
			@PathVariable("user") Integer user, @PathVariable("locationId") Integer locationId,
			ModelAndView modelAndView) throws InstanceAlreadyExistsException {

		LocationServiceContext sc = LocationContext.getServices();

		ServiceContextStock scSTK = SessionFactoryUtil.getServiceContext();

		try {

			AddInInventoryService inv = new AddInInventoryService();
			Location location = sc.getLocationService().findLocationById(locationId, false, null);
			List<Location> childLocations = sc.getLocationService().getAllChildLocations(locationId, false, null);
			int arbSize = 0;
			List<Location> vacCenter = new ArrayList<Location>();
			List<Requisition> requisitionsUnApproved = new ArrayList<Requisition>();
			//List<Item> items = (List<Item>) scSTK.itemDAO.getallItems();
			for(int i = 0 ; i < childLocations.size() ; i++)
			{
				List<Location> vaccinationcenter =sc.getLocationService().getAllChildLocations(childLocations.get(i).getLocationId(), false, null);
				vacCenter.addAll(vaccinationcenter);
			    for(int j = 0 ; j < vaccinationcenter.size() ; j++)
			    {
			    	requisitionsUnApproved.addAll( scSTK.requisitionDAO.getForLocationPending(vaccinationcenter.get(j).getLocationId())) ;
			    }
				
				//requisitionsUnApproved.addAll(scSTK.requisitionDAO.getForLocationPending(childLocations.get(i).getLocationId()));
				
			}

//			approveRequirementValidator = new ApproveRequirementValidator();
//			approveRequirementValidator.validate(arb, results, requisitionsUnApproved);
//			if (results.getErrorCount() > 0) {
//				inv.ApproveReq(arb, requisitions);
//				modelAndView = ControllerUtility.setRequirementApproval(modelAndView, locationId, user);
//
//				return modelAndView;
//			}
			inv.ApproveReq(arb, requisitionsUnApproved);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sc.closeSession();
		}
		modelAndView = ControllerUtility.setRequirementApproval(modelAndView, locationId, user);
		modelAndView.addObject("message", "Approval Status has been submitted");
		return modelAndView;

	}

}