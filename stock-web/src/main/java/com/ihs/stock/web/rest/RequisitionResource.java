package com.ihs.stock.web.rest;

import java.text.ParseException;

import javax.management.InstanceAlreadyExistsException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ihs.stock.api.beans.UpdateRequirementBean;
import com.ihs.stock.api.service.AddInInventoryService;


@RestController
@RequestMapping("/req")
public class RequisitionResource {
	
	@PostMapping(path="/add" , consumes = "application/json")
	public ResponseEntity<Void> add(@RequestBody UpdateRequirementBean urb)
	{
		if(urb == null)
		{
			return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
		}
		AddInInventoryService ais = new AddInInventoryService();
		try {
			ais.updateRequirement(urb);
		} catch (InstanceAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ResponseEntity<Void>(HttpStatus.OK);
		
	}

}
