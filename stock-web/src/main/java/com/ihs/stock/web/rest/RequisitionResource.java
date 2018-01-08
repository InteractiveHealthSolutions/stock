package com.ihs.stock.web.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.management.InstanceAlreadyExistsException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ihs.stock.api.beans.UpdateRequirementBeanMobile;
import com.ihs.stock.api.model.Item;
import com.ihs.stock.api.model.Requisition;
import com.ihs.stock.api.service.AddInInventoryService;

@RestController
@RequestMapping("/req")
public class RequisitionResource {
	
	@RequestMapping(path="/add/{user}/{location}" /*, consumes = "application/json" , produces = "application/json"*/)
	public String add(@PathVariable("user") Integer userId, @PathVariable("location") Integer locationId,@RequestBody List<UpdateRequirementBeanMobile> urb) throws InstanceAlreadyExistsException, ParseException
	{
		if(urb == null)
		{
			return "0";
		}
		AddInInventoryService ais = new AddInInventoryService();
		ais.updateRequirementMobile(urb, userId, locationId);
		return "1";
		
	}
	
//	@RequestMapping(path="/" , produces = "application/json")
//	public ResponseEntity<List<Requisition>> getAll() throws InstanceAlreadyExistsException
//	{
//		ServiceContext sc = Context.getServices();
//		sc.beginTransaction();
//		
//		List<Requisition> reqList = sc.requisitionDAO.getAll();
//		sc.commitTransaction();
//		sc.closeSession();
//		return new ResponseEntity<List<Requisition>>(reqList, HttpStatus.OK);
//		
//	}
	
//	@RequestMapping(path="/{location}" , produces = "application/json")
//	public ResponseEntity<List<Requisition>> getForLocation(@PathVariable("location") String identifier) throws InstanceAlreadyExistsException
//	{
//		
//		ServiceContext sc = Context.getServices();
//		sc.beginTransaction();
//		Location location = sc.getLocationService().findLocationByName(identifier, false, null);
//		
//		if(location == null)
//		{
//			location = sc.getLocationService().findLocationById(Integer.parseInt(identifier), false, null);
//		}
//		if(location == null)
//		{
//			return new ResponseEntity<List<Requisition>>(HttpStatus.NOT_FOUND);
//		}
//		
//		List<Requisition> reqList = sc.requisitionDAO.getForLocation((Location) location);
//		sc.commitTransaction();
//		sc.closeSession();
//		return new ResponseEntity<List<Requisition>>(reqList , HttpStatus.OK);
//		
//	}
//	
//	@RequestMapping(path="/update/{location}/{item}" , consumes = "application/json" , produces = "application/json")
//	public ResponseEntity<Requisition> update(@PathVariable("location") String locationIdentifier , @PathVariable("item") String itemIdentifier, @RequestBody Requisition req) throws ParseException, InstanceAlreadyExistsException
//	{
//		if(req == null)
//		{
//			return new ResponseEntity<Requisition>(HttpStatus.NOT_ACCEPTABLE);
//		}
//		
//		ServiceContext sc = Context.getServices();
//		sc.beginTransaction();
//		Location locationList =sc.getLocationService().findLocationByName(locationIdentifier, false, null);
//		
//		if(locationList == null)
//		{
//			locationList = sc.getLocationService().findLocationById(Integer.parseInt(locationIdentifier), false, null);
//		}
//	
//		if(locationList == null)
//		{
//			return new ResponseEntity<Requisition>(HttpStatus.NOT_FOUND);
//		}
//		
//		
//		Item item = sc.itemDAO.getByName(itemIdentifier);
//		if(item == null)
//		{
//			item = sc.itemDAO.getById(Integer.parseInt(itemIdentifier));
//		}
//		if(item == null)
//		{
//			return new ResponseEntity<Requisition>(HttpStatus.NOT_FOUND);
//		}
//		
//		Requisition requisition = sc.requisitionDAO.getForLocationItem((Location) locationList, item);
//		if(requisition == null)
//		{
//			return new ResponseEntity<Requisition>(HttpStatus.NOT_FOUND);
//		}
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		Date dateToday = sdf.parse(sdf.format(new Date()));
//		if(req.getapprovalStatus() != null)
//		{
//			requisition.setapprovalStatus(req.getapprovalStatus());
//		}
//		if(req.getcomments() != null)
//		{
//			requisition.setcomments(req.getcomments());
//		}
//		if(req.getquantity() != null)
//		{
//			requisition.setquantity(req.getquantity());
//		}
//		requisition.setdateEdited(dateToday);
//		requisition = sc.requisitionDAO.update(requisition);
//		sc.commitTransaction();
//		sc.closeSession();
//		return new ResponseEntity<Requisition>(requisition , HttpStatus.OK);
//	}
//	
//	@RequestMapping(path="/delete/{location}/{item}" , consumes = "application/json" , produces = "application/json")
//	public ResponseEntity<Requisition> delete(@PathVariable("location") String locationIdentifier , @PathVariable("item") String itemIdentifier) throws ParseException, InstanceAlreadyExistsException
//	{
//		ServiceContext sc = Context.getServices();
//		sc.beginTransaction();
//		Location locationList = sc.getLocationService().findLocationByName(locationIdentifier, false, null);
//		
//		if(locationList == null)
//		{
//			locationList = sc.getLocationService().findLocationById(Integer.parseInt(locationIdentifier), false, null);
//		}
//		if(locationList == null)
//		{
//			return new ResponseEntity<Requisition>(HttpStatus.NOT_FOUND);
//		}
//		Item item = sc.itemDAO.getByName(itemIdentifier);
//		if(item == null)
//		{
//			item = sc.itemDAO.getById(Integer.parseInt(itemIdentifier));
//		}
//		if(item == null)
//		{
//			return new ResponseEntity<Requisition>(HttpStatus.NOT_FOUND);
//		}
//		Requisition requisition = sc.requisitionDAO.getForLocationItem((Location) locationList, item);
//		if(requisition == null)
//		{
//			return new ResponseEntity<Requisition>(HttpStatus.NOT_FOUND);
//		}
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		Date dateToday = sdf.parse(sdf.format(new Date()));
//		requisition.setvoided(true);
//		requisition.setdateVoided(dateToday);
//		requisition = sc.requisitionDAO.update(requisition);
//		sc.commitTransaction();
//		sc.closeSession();
//		return new ResponseEntity<Requisition>(requisition , HttpStatus.OK);
//	}
//	
//	

}
