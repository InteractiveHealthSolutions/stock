package com.ihs.stock.web.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ihs.stock.api.DAO.DAOItem;
import com.ihs.stock.api.beans.AddItemBean;
import com.ihs.stock.api.model.Item;
import com.ihs.stock.api.service.AddItemsService;
import com.ihs.stock.web.validator.AddItemValidator;

@RestController
@RequestMapping("/item")

public class ItemResource {

	private AddItemValidator addItemValidator;

	@InitBinder("item")
	private void itemInitBinder(WebDataBinder binder) {
		binder.setValidator(addItemValidator);
	}

	@PostMapping(path="/add" , consumes = "application/json")
	public ResponseEntity<Void> addItem(@RequestBody @Validated AddItemBean item, BindingResult results)
			throws ParseException {
		addItemValidator = new AddItemValidator();
		addItemValidator.validate(item, results);
		if (results.hasErrors()) {

			return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
		}
		AddItemsService sr = new AddItemsService();
		Item item1 = sr.AddItems(item);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@PutMapping(path = "/update/{identifier}" , consumes = "application/json")
	//@RequestMapping(value = "/update/{identifier}", consumes = "application/json", method = RequestMethod.PUT)
	public ResponseEntity<Item> updateItem(@PathVariable("identifier") String itemName, @RequestBody Item itemUpdate)
			throws ParseException {
		DAOItem itemDAO = new DAOItem();
		Item item = itemDAO.getByName(itemName);
		if (item == null) {
			Integer id = Integer.parseInt(itemName);
			item = itemDAO.getById(id);

		}
		if (item == null) {
			new ResponseEntity<Item>(HttpStatus.NOT_FOUND);
		}
		// item.setname(itemUpdate.getname());
		if (itemUpdate.getbarcode() != null) {
			item.setbarcode(itemUpdate.getbarcode());
		}
		if (itemUpdate.getbrand() != null) {
			item.setbrand(itemUpdate.getbrand());
		}
		if (itemUpdate.getdescription() != null) {
			item.setdescription(itemUpdate.getdescription());
		}
		if (itemUpdate.getenclosedQuantity() != null) {
			item.setenclosedQuantity(itemUpdate.getenclosedQuantity());
		}
		if (itemUpdate.getexpiryAfterOpening() != null) {
			item.setexpiryAfterOpening(itemUpdate.getexpiryAfterOpening());
		}
		if (itemUpdate.getexpiryUnit() != null) {
			item.setexpiryUnit(itemUpdate.getexpiryUnit());
		}
		if (itemUpdate.getitemType() != null) {
			item.setitemType(itemUpdate.getitemType());
		}
		if (itemUpdate.getmanufacturer() != null) {
			item.setmanufacturer(itemUpdate.getmanufacturer());
		}
		if (itemUpdate.getname() != null) {
			item.setname(itemUpdate.getname());
		}
		if (itemUpdate.getshortName() != null) {
			item.setshortName(itemUpdate.getshortName());
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(sdf.format(new Date()));

		item.setdateEdited(date);

		itemDAO.update(item);

		return new ResponseEntity<Item>(item, HttpStatus.OK);

	}

	@GetMapping(path="/" , produces="application/json")
//	@RequestMapping(value = "/", produces = "application/json", method = RequestMethod.GET)
	public ResponseEntity<List<Item>> getAllItems() {
		DAOItem itemDAO = new DAOItem();
		List<Item> itemList = (List<Item>) itemDAO.getallItems();

		if (itemList.size() == 0) {
			new ResponseEntity<List<Item>>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<Item>>(itemList, HttpStatus.OK);

	}

	@GetMapping(path="/{identifier}" , produces="application/json")
	//@RequestMapping(value = "/{identifier}", produces = "application/json", method = RequestMethod.GET)
	public ResponseEntity<Item> getItem(@PathVariable("identifier") String itemName) {
		DAOItem itemDAO = new DAOItem();
		Item item = itemDAO.getByName(itemName);

		if (item == null) {
			Integer id = Integer.parseInt(itemName);
			item = itemDAO.getById(id);
		}
		if (item == null) {
			return new ResponseEntity<Item>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Item>(item, HttpStatus.OK);

	}

	@DeleteMapping(path="/delete/{identifier}" , produces = "application/json")
	///@RequestMapping(value = "/delete/{identifier}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Item> deleteItem(@PathVariable("identifier") String itemName) throws ParseException {
		DAOItem itemDAO = new DAOItem();
		Item item = itemDAO.getByName(itemName);
		// item = itemDAO.getById(itemName);

		if (item == null) {
			Integer id = Integer.parseInt(itemName);
			item = itemDAO.getById(id);
		}
		if (item == null) {
			return new ResponseEntity<Item>(HttpStatus.NOT_FOUND);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(sdf.format(new Date()));

		item.setdateVoided(date);
		// itemDAO.delete(item);
		itemDAO.update(item);
		return new ResponseEntity<Item>(item, HttpStatus.OK);
	}

}
