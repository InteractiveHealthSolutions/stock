package com.ihs.stock.web.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.management.InstanceAlreadyExistsException;

import org.hibernate.SessionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ihs.stock.api.beans.DayEndEntryBean;
import com.ihs.stock.api.context.ServiceContextStock;
import com.ihs.stock.api.context.SessionFactoryUtil;
import com.ihs.stock.api.model.Consumer;
import com.ihs.stock.api.model.DailyStats;
import com.ihs.stock.api.service.DailyEntryService;

@Controller
@RequestMapping("/dailystats")
@ResponseBody
public class DailyStatsResource {

	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@RequestBody List<DayEndEntryBean> deb) throws ParseException, InstanceAlreadyExistsException
	{
		if(deb == null)
		{
			return "0";
		}
		
		DailyEntryService des = new DailyEntryService();
		des.dailyStatsEntry(deb);
		
	return  "1";
	}
	
	
	@RequestMapping(value = "/delete/{identifier}" , method = RequestMethod.DELETE , consumes = "application/json")
	public ResponseEntity<DailyStats> delete(@PathVariable("identifier") String Identifier , @RequestBody DailyStats uDS) throws ParseException
	{
		SessionFactory sf = SessionFactoryUtil.getSessionFactory(null, "stk-hibernate.cfg.xml");
		ServiceContextStock scSTK = SessionFactoryUtil.getServiceContext();
		Integer id = Integer.parseInt(Identifier);
		DailyStats ds = scSTK.dailyStatsDAO.getDailyStatsbyId(id);
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
		
		scSTK.dailyStatsDAO.update(ds);
		scSTK.commitTransaction();
		scSTK.closeSession();
		return new ResponseEntity<DailyStats>(ds,HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/" , method = RequestMethod.GET , produces = "application/json")
	public ResponseEntity<List<DailyStats>> getAllDailyStats()
	{
		SessionFactory sf = SessionFactoryUtil.getSessionFactory(null, "stk-hibernate.cfg.xml");
		ServiceContextStock scSTK = SessionFactoryUtil.getServiceContext();
		List<DailyStats> dailyStats = scSTK.dailyStatsDAO.getAllDailyStats();
		scSTK.commitTransaction();
		scSTK.closeSession();
		return new ResponseEntity<List<DailyStats>>(dailyStats , HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{identifier}" , method = RequestMethod.GET , produces = "application/json")
	public ResponseEntity<DailyStats> getDailyStats(@PathVariable("identifier") String identifier , @RequestBody Consumer con) throws ParseException
	{
		SessionFactory sf = SessionFactoryUtil.getSessionFactory(null, "stk-hibernate.cfg.xml");
		ServiceContextStock scSTK = SessionFactoryUtil.getServiceContext();
		Integer id = Integer.parseInt(identifier);
		
		DailyStats ds = scSTK.dailyStatsDAO.getDailyStatsbyId(id);
		scSTK.commitTransaction();
		scSTK.closeSession();
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
