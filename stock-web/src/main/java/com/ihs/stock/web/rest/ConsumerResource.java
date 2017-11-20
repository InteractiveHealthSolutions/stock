package com.ihs.stock.web.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ihs.stock.api.DAO.DAOConsumer;
import com.ihs.stock.api.beans.AddItemBean;
import com.ihs.stock.api.model.Consumer;
import com.ihs.stock.api.model.Item;
import com.ihs.stock.api.service.AddItemsService;
import com.ihs.stock.web.validator.AddItemValidator;

@RestController
@RequestMapping("/consumer")
@ResponseBody
public class ConsumerResource {

	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Void> addItem(@RequestBody Consumer consumer)
			throws ParseException {
		
		DAOConsumer daoConsumer = new DAOConsumer();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(sdf.format(new Date()));
		consumer.setdateCreated(date);
		daoConsumer.save(consumer);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/" , method = RequestMethod.GET , produces = "application/json")
	public ResponseEntity<List<Consumer>> getAllConsumer()
	{
		DAOConsumer consumerDAO = new DAOConsumer();
		List<Consumer> consumers = consumerDAO.getAllConsumers();
		if(consumers.size() == 0)
		{
			return new ResponseEntity<List<Consumer>>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<Consumer>>(consumers , HttpStatus.OK);
		
	}
	@RequestMapping(value = "/{identifier}" , method = RequestMethod.GET , produces = "application/json")
	public ResponseEntity<Consumer> getConsumer(@PathVariable("identifier") String identifier) throws ParseException
	{
		DAOConsumer consumerDAO = new DAOConsumer();
		Integer id = Integer.parseInt(identifier);
		
		Consumer consumer = consumerDAO.getById(id);
		if(consumer == null)
		{
			//uuid function
		}
		if(consumer == null)
		{
			return new ResponseEntity<Consumer>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Consumer>(consumer , HttpStatus.OK);
		
	}
	
	
	@RequestMapping(value = "/update/{identifier}" , method = RequestMethod.PUT , consumes = "application/json")
	public ResponseEntity<Consumer> update(@PathVariable("identifier") String identifier , @RequestBody Consumer con) throws ParseException
	{
		DAOConsumer consumerDAO = new DAOConsumer();
		Integer id = Integer.parseInt(identifier);
		
		Consumer consumer = consumerDAO.getById(id);
		if(con == null)
		{
			//uuid function
		}
		if(con == null)
		{
			return new ResponseEntity<Consumer>(HttpStatus.NOT_FOUND);
		}
		if(con.getlocation() != null)
		{
			consumer.setlocation(con.getlocation());
		}
		if(con.getname() != null)
		{
			consumer.setname(con.getname());
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(sdf.format(new Date()));
		consumer.setdateEdited(date);
		consumerDAO.update(consumer);
		return new ResponseEntity<Consumer>(consumer , HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/delete/{identifier}" , method = RequestMethod.DELETE , consumes = "application/json")
	public ResponseEntity<Consumer> delete(@PathVariable("identifier") String identifier , @RequestBody Consumer con) throws ParseException
	{
		DAOConsumer consumerDAO = new DAOConsumer();
		Integer id = Integer.parseInt(identifier);
		
		Consumer consumer = consumerDAO.getById(id);
		if(con == null)
		{
			//uuid function
		}
		if(con == null)
		{
			return new ResponseEntity<Consumer>(HttpStatus.NOT_FOUND);
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(sdf.format(new Date()));
		consumer.setdateVoided(date);
		consumerDAO.update(consumer);
		return new ResponseEntity<Consumer>(consumer , HttpStatus.OK);
		
	}
	
}
