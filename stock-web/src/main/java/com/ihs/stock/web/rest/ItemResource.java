package com.ihs.stock.web.rest;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ihs.stock.api.context.ServiceContextStock;
import com.ihs.stock.api.context.SessionFactoryUtil;
import com.ihs.stock.api.model.Item;

@Controller
@RequestMapping("/item")

public class ItemResource {

//	private AddItemValidator addItemValidator;
//
//	@InitBinder("item")
//	private void itemInitBinder(WebDataBinder binder) {
//		binder.setValidator(addItemValidator);
//	}
//
//	@PostMapping(path="/add" , consumes = "application/json")
//	public ResponseEntity<Void> addItem(@RequestBody @Validated AddItemBean item, BindingResult results)
//			throws ParseException {
//		addItemValidator = new AddItemValidator();
//		addItemValidator.validate(item, results);
//		if (results.hasErrors()) {
//
//			return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
//		}
//		AddItemsService sr = new AddItemsService();
//		Item item1 = sr.AddItems(item);
//
//		return new ResponseEntity<Void>(HttpStatus.OK);
//	}
//
//	@PutMapping(path = "/update/{identifier}" , consumes = "application/json")
//	//@RequestMapping(value = "/update/{identifier}", consumes = "application/json", method = RequestMethod.PUT)
//	public ResponseEntity<Item> updateItem(@PathVariable("identifier") String itemName, @RequestBody Item itemUpdate)
//			throws ParseException {
//		SessionFactory sf = SessionFactoryUtil.getSessionFactory(null, "stk-hibernate.cfg.xml");
//		ServiceContextStock scSTK = SessionFactoryUtil.getServiceContext();
//		Item item = scSTK.itemDAO.getByName(itemName);
//		if (item == null) {
//			Integer id = Integer.parseInt(itemName);
//			item = scSTK.itemDAO.getById(id);
//
//		}
//		if (item == null) {
//			new ResponseEntity<Item>(HttpStatus.NOT_FOUND);
//		}
//		// item.setname(itemUpdate.getname());
//		if (itemUpdate.getbarcode() != null) {
//			item.setbarcode(itemUpdate.getbarcode());
//		}
//		if (itemUpdate.getbrand() != null) {
//			item.setbrand(itemUpdate.getbrand());
//		}
//		if (itemUpdate.getdescription() != null) {
//			item.setdescription(itemUpdate.getdescription());
//		}
//		if (itemUpdate.getenclosedQuantity() != null) {
//			item.setenclosedQuantity(itemUpdate.getenclosedQuantity());
//		}
//		if (itemUpdate.getexpiryAfterOpening() != null) {
//			item.setexpiryAfterOpening(itemUpdate.getexpiryAfterOpening());
//		}
//		if (itemUpdate.getexpiryUnit() != null) {
//			item.setexpiryUnit(itemUpdate.getexpiryUnit());
//		}
//		if (itemUpdate.getitemType() != null) {
//			item.setitemType(itemUpdate.getitemType());
//		}
//		if (itemUpdate.getmanufacturer() != null) {
//			item.setmanufacturer(itemUpdate.getmanufacturer());
//		}
//		if (itemUpdate.getname() != null) {
//			item.setname(itemUpdate.getname());
//		}
//		if (itemUpdate.getshortName() != null) {
//			item.setshortName(itemUpdate.getshortName());
//		}
//
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		Date date = sdf.parse(sdf.format(new Date()));
//
//		item.setdateEdited(date);
//
//		scSTK.itemDAO.update(item);
//        scSTK.commitTransaction();
//        scSTK.closeSession();
//		return new ResponseEntity<Item>(item, HttpStatus.OK);
//
//	}

	@RequestMapping(value="/view" , method = RequestMethod.GET )
	public @ResponseBody List<Item> getAllItems() {
		ServiceContextStock sc = SessionFactoryUtil.getServiceContext();
		
		List<Item> itemList = (List<Item>) sc.itemDAO.getallItems();

		if (itemList.size() == 0) {
			return null;
		}

		
		sc.closeSession();
		return itemList;

	}

//	@GetMapping(path="/{identifier}" , produces="application/json")
//	//@RequestMapping(value = "/{identifier}", produces = "application/json", method = RequestMethod.GET)
//	public ResponseEntity<Item> getItem(@PathVariable("identifier") String itemName) {
//		SessionFactory sf = SessionFactoryUtil.getSessionFactory(null, "stk-hibernate.cfg.xml");
//		ServiceContextStock scSTK = SessionFactoryUtil.getServiceContext();
//		
//		Item item = scSTK.itemDAO.getByName(itemName);
//
//		if (item == null) {
//			Integer id = Integer.parseInt(itemName);
//			item = scSTK.itemDAO.getById(id);
//		}
//		if (item == null) {
//			return new ResponseEntity<Item>(HttpStatus.NOT_FOUND);
//		}
//		scSTK.commitTransaction();
//		scSTK.closeSession();
//		return new ResponseEntity<Item>(item, HttpStatus.OK);
//
//	}
//
//	@DeleteMapping(path="/delete/{identifier}" , produces = "application/json")
//	public ResponseEntity<Item> deleteItem(@PathVariable("identifier") String itemName) throws ParseException {
//		SessionFactory sf = SessionFactoryUtil.getSessionFactory(null, "stk-hibernate.cfg.xml");
//		ServiceContextStock scSTK = SessionFactoryUtil.getServiceContext();
//		
//		Item item = scSTK.itemDAO.getByName(itemName);
//		// item = itemDAO.getById(itemName);
//
//		if (item == null) {
//			Integer id = Integer.parseInt(itemName);
//			item = scSTK.itemDAO.getById(id);
//		}
//		if (item == null) {
//			return new ResponseEntity<Item>(HttpStatus.NOT_FOUND);
//		}
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		Date date = sdf.parse(sdf.format(new Date()));
//
//		item.setdateVoided(date);
//		// itemDAO.delete(item);
//		scSTK.itemDAO.update(item);
//		scSTK.commitTransaction();
//		scSTK.closeSession();
//		return new ResponseEntity<Item>(item, HttpStatus.OK);
//	}

}
