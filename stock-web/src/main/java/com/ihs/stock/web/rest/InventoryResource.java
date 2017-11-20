package com.ihs.stock.web.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ihs.stock.api.DAO.DAOInventory;
import com.ihs.stock.api.beans.InventoryBean;
import com.ihs.stock.api.model.Inventory;
import com.ihs.stock.api.service.AddInInventoryService;

@RestController
@RequestMapping("/inventory")
@ResponseBody
public class InventoryResource {
	
	@RequestMapping(value = "/" , method = RequestMethod.GET , produces = "application/json")
	public ResponseEntity<List<Inventory>> getAllInventory()
	{
		DAOInventory invDAO = new DAOInventory();
		List<Inventory> inventories = invDAO.getAllInventory();
		return new ResponseEntity<List<Inventory>>(inventories , HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{identifier}" , method = RequestMethod.GET , produces = "application/json")
	public ResponseEntity<Inventory> getInventory(@PathVariable("identifier") String identifier)
	{
		DAOInventory invDAO = new DAOInventory();
		Integer id = Integer.parseInt(identifier);
		Inventory inventories = (Inventory) invDAO.getById(id);
		if(inventories == null)
		{
			return new ResponseEntity<Inventory>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Inventory>(inventories , HttpStatus.OK);
	}
	
	@RequestMapping(value="/update/{identifier}", method = RequestMethod.PUT , consumes = "application/json")
	public ResponseEntity<Inventory> updateInventory(@PathVariable("identifier") String identifier , @RequestBody Inventory inv) throws ParseException
	{
		DAOInventory daoInventory = new DAOInventory();
		Integer id = Integer.parseInt(identifier);
		Inventory inventory = (Inventory) daoInventory.getById(id);
		
		if(inv.getcurrentMonthContainers()!=null)
		{
			inventory.setcurrentMonthContainers(inv.getcurrentMonthContainers());
			inventory.settotalContainers(inv.getcurrentMonthContainers()+inventory.getprevMonthBalance());
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(sdf.format(new Date()));
		
		daoInventory.update(inventory);
		
		return new ResponseEntity<Inventory>(inventory,HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/add" , method = RequestMethod.POST , consumes = "application/json")
	public ResponseEntity<Inventory> addInInventory(@RequestBody InventoryBean aib) throws Exception
	{
	    AddInInventoryService ais = new AddInInventoryService();
	    Inventory inv = ais.insertInInventory(aib);
	    return new ResponseEntity<Inventory>(inv,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/delete/{identifier}" , method = RequestMethod.DELETE)
	public ResponseEntity<Inventory> deleteInventory(@PathVariable("identifier") String identifier) throws ParseException
	{
		DAOInventory daoInventory = new DAOInventory();
		Integer id = Integer.parseInt(identifier);
		Inventory inv = (Inventory) daoInventory.getById(id);
		
		if(inv == null)
		{
			return new ResponseEntity<Inventory>(HttpStatus.NOT_FOUND);
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(sdf.format(new Date()));	
		inv.setdateVoided(date);
		daoInventory.update(inv);
		return new ResponseEntity<Inventory>(inv, HttpStatus.OK);
	}
	

}
