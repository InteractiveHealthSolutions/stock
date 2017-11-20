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

import com.ihs.stock.api.DAO.DAOMonthlyStats;
import com.ihs.stock.api.model.Consumer;
import com.ihs.stock.api.model.MonthlyStats;

@RestController
@RequestMapping("/monthlystats")
public class MonthlyStatsResource {
	

	@RequestMapping(value = "/add" , method = RequestMethod.POST , consumes = "application/json")
	public ResponseEntity<Void> add(@RequestBody MonthlyStats ms) throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date date = sdf.parse(sdf.format(new Date()));
		ms.setdateCreated(date);
		DAOMonthlyStats msDAO = new DAOMonthlyStats();
		msDAO.save(ms);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/update/{identifier}" , method = RequestMethod.PUT , consumes = "application/json")
	public ResponseEntity<MonthlyStats> update(@PathVariable("identifier") String Identifier , @RequestBody MonthlyStats ums) throws ParseException
	{
		DAOMonthlyStats msDAO = new DAOMonthlyStats();
		Integer id = Integer.parseInt(Identifier);
		MonthlyStats ms = msDAO.getById(id);
		if(ms == null)
		{
			//uuid search
		}
		if(ms == null)
		{
			return new ResponseEntity<MonthlyStats>(HttpStatus.NOT_FOUND);
		}
		
		if(ums.getconsumer() != null)
		{
			ms.setconsumer(ums.getconsumer());
		}
		if(ums.getitem() != null)
		{
			ms.setitem(ums.getitem());
		}
		if(ums.getinitialContainers() != null)
		{
			ms.setinitialContainersCount(ums.getinitialContainers());
		}
		if(ums.getbalanceContainers() != null)
		{
			ms.setbalanceContainers(ums.getbalanceContainers());
		}
		if(ums.getbalanceQuantity() != null)
		{
			ms.setbalanceQuantity(ums.getbalanceQuantity());
		}
		if(ums.gettotalContainers() != null)
		{
			ms.settotalContainers(ums.gettotalContainers());
		}
		if(ums.gettotalQuantity() != null)
		{
			ms.settotalQuantity(ums.gettotalQuantity());
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date date = sdf.parse(sdf.format(new Date()));
		
		ms.setdateEdited(date);
		
		msDAO.update(ms);
		
		return new ResponseEntity<MonthlyStats>(ms,HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/delete/{identifier}" , method = RequestMethod.DELETE , consumes = "application/json")
	public ResponseEntity<MonthlyStats> delete(@PathVariable("identifier") String Identifier , @RequestBody MonthlyStats ums) throws ParseException
	{
		DAOMonthlyStats msDAO = new DAOMonthlyStats();
		Integer id = Integer.parseInt(Identifier);
		MonthlyStats ms = msDAO.getById(id);
		if(ms == null)
		{
			//search by uuid
		}
		if(ms == null)
		{
			return new ResponseEntity<MonthlyStats>(HttpStatus.NOT_FOUND);
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date date = sdf.parse(sdf.format(new Date()));
		
		ms.setdateVoided(date);
		
		msDAO.update(ms);
		
		return new ResponseEntity<MonthlyStats>(ms,HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/" , method = RequestMethod.GET , produces = "application/json")
	public ResponseEntity<List<MonthlyStats>> getAllMonthlyStats()
	{
		DAOMonthlyStats msDAO = new DAOMonthlyStats();
		List<MonthlyStats> MonthlyStats = msDAO.getAllMonthLyStats();
		return new ResponseEntity<List<MonthlyStats>>(MonthlyStats , HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{identifier}" , method = RequestMethod.GET , produces = "application/json")
	public ResponseEntity<MonthlyStats> getMonthlyStats(@PathVariable("identifier") String identifier , @RequestBody Consumer con) throws ParseException
	{
		DAOMonthlyStats msDAO = new DAOMonthlyStats();
		Integer id = Integer.parseInt(identifier);
		
		MonthlyStats ms = msDAO.getById(id);
		if(ms == null)
		{
			//uuid function
		}
		if(ms == null)
		{
			return new ResponseEntity<MonthlyStats>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<MonthlyStats>(ms , HttpStatus.OK);
		
	}

}
