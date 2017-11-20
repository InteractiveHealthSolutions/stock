package com.ihs.stock.web.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ihs.stock.api.DAO.DAOConsumer;
import com.ihs.stock.api.DAO.DAODailyStats;
import com.ihs.stock.api.DAO.DAOInventory;
import com.ihs.stock.api.model.Consumer;
import com.ihs.stock.api.model.DailyStats;
import com.ihs.stock.api.model.Inventory;

@RestController
@RequestMapping("/dailystats")
@ResponseBody
public class DailyStatsResource {

	
	@RequestMapping(value = "/add" , method = RequestMethod.POST , consumes = "application/json")
	public ResponseEntity<Void> add(@RequestBody DailyStats ds) throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date date = sdf.parse(sdf.format(new Date()));
		ds.setdateCreated(date);
		DAODailyStats dsDAO = new DAODailyStats();
		dsDAO.save(ds);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/update/{identifier}" , method = RequestMethod.PUT , consumes = "application/json")
	public ResponseEntity<DailyStats> update(@PathVariable("identifier") String Identifier , @RequestBody DailyStats uDS) throws ParseException
	{
		DAODailyStats dsDAO = new DAODailyStats();
		Integer id = Integer.parseInt(Identifier);
		DailyStats ds = dsDAO.getDailyStatsbyId(id);
		if(ds == null)
		{
			//uuid search
		}
		if(ds == null)
		{
			return new ResponseEntity<DailyStats>(HttpStatus.NOT_FOUND);
		}
		
		if(uDS.getconsumer() != null)
		{
			ds.setconsumer(uDS.getconsumer());
		}
		if(uDS.getitem() != null)
		{
			ds.setitem(uDS.getitem());
		}
		if(uDS.getusedContainers() != null)
		{
			ds.setusedContainers(uDS.getusedContainers());
		}
		if(uDS.getwastedContainers() != null)
		{
			ds.setwastedContainers(uDS.getwastedContainers());
		}
		if(uDS.getwastedQuantity() != null)
		{
			ds.setwastedQuantity(uDS.getwastedQuantity());
		}
		if(uDS.getusedQuantity() != null)
		{
			ds.setusedQuantity(uDS.getusedQuantity());
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date date = sdf.parse(sdf.format(new Date()));
		
		ds.setdateEdited(date);
		
		dsDAO.update(ds);
		
		return new ResponseEntity<DailyStats>(ds,HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/delete/{identifier}" , method = RequestMethod.DELETE , consumes = "application/json")
	public ResponseEntity<DailyStats> delete(@PathVariable("identifier") String Identifier , @RequestBody DailyStats uDS) throws ParseException
	{
		DAODailyStats dsDAO = new DAODailyStats();
		Integer id = Integer.parseInt(Identifier);
		DailyStats ds = dsDAO.getDailyStatsbyId(id);
		if(ds == null)
		{
			//search by uuid
		}
		if(ds == null)
		{
			return new ResponseEntity<DailyStats>(HttpStatus.NOT_FOUND);
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date date = sdf.parse(sdf.format(new Date()));
		
		ds.setdateVoided(date);
		
		dsDAO.update(ds);
		
		return new ResponseEntity<DailyStats>(ds,HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/" , method = RequestMethod.GET , produces = "application/json")
	public ResponseEntity<List<DailyStats>> getAllDailyStats()
	{
		DAODailyStats dsDAO = new DAODailyStats();
		List<DailyStats> dailyStats = dsDAO.getAllDailyStats();
		return new ResponseEntity<List<DailyStats>>(dailyStats , HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{identifier}" , method = RequestMethod.GET , produces = "application/json")
	public ResponseEntity<DailyStats> getDailyStats(@PathVariable("identifier") String identifier , @RequestBody Consumer con) throws ParseException
	{
		DAODailyStats dsDAO = new DAODailyStats();
		Integer id = Integer.parseInt(identifier);
		
		DailyStats ds = dsDAO.getDailyStatsbyId(id);
		if(ds == null)
		{
			//uuid function
		}
		if(ds == null)
		{
			return new ResponseEntity<DailyStats>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<DailyStats>(ds , HttpStatus.OK);
		
	}
}
