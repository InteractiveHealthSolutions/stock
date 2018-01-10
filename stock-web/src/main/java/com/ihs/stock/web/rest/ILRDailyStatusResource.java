package com.ihs.stock.web.rest;

import java.text.ParseException;
import java.util.List;

import javax.management.InstanceAlreadyExistsException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ihs.stock.api.context.ServiceContextStock;
import com.ihs.stock.api.context.SessionFactoryUtil;
import com.ihs.stock.api.model.ILRDailyStatus;
import com.ihs.stock.api.service.DailyEntryService;

@RestController
@CrossOrigin//(origins = {"http://localhost:8080"}, maxAge = 4800, allowCredentials = "false")
@RequestMapping("/ilrws")
public class ILRDailyStatusResource {

	@RequestMapping(value="/add" , method = RequestMethod.POST /*, consumes = "application/json" , produces = "application/json"*/)
	public String add( @RequestBody List<ILRDailyStatus> ilr) throws ParseException, InstanceAlreadyExistsException
	{
		if(ilr == null)
		{
			return "0";
		}
		
		DailyEntryService des = new DailyEntryService();
		for(int i = 0 ; i < ilr.size() ; i++)
		{
					int user = ilr.get(i).getuser();
					if(ilr.get(i).getopeningTemprature() != null && ilr.get(i).getclosingTemprature() == null)
					{
						des.morningEntryILR(ilr.get(i));
					}
					else if(ilr.get(i).getclosingTemprature() != null && ilr.get(i).getopeningTemprature() == null)
					{
						des.dayEndEntryILR(ilr.get(i));
					}
					else if(ilr.get(i).getopeningTemprature() == null && ((ilr.get(i).getMorningILRStatus() != null)|| (ilr.get(i).getmorningTMStatus() !=null)))
					{
						des.morningEntryILR(ilr.get(i));
						
					}
					else if(ilr.get(i).getclosingTemprature() == null &&((ilr.get(i).getdayendTMStatus() != null) ||(ilr.get(i).getdayendILRStatus() != null)))
					{
						des.dayEndEntryILR(ilr.get(i));
					}
		}
		return "1";
		
	}
	
	
    
   
	@RequestMapping(path="/view/{location}/{month}/{year}" , method = RequestMethod.GET /*, produces = "application/json"*/)
	public List<ILRDailyStatus> getForMonth(ModelAndView mD ,@PathVariable("location") Integer locationId ,
			@PathVariable("month") Integer month ,@PathVariable("year") Integer year) throws ParseException, InstanceAlreadyExistsException
	{
		
		ServiceContextStock sc = SessionFactoryUtil.getServiceContext();
		try
     	{
     		List<ILRDailyStatus> dailyStatus = sc.ilrDailyStatusDAO.getForYearMonthLocation(locationId, month, year);
    		if(dailyStatus == null)
         	{
        		return null;
        	}
        	return dailyStatus;
     	}
		catch(Exception e)
     	{
			e.printStackTrace();
     	}
     	finally
     	{
     		sc.closeSession();
     	}
		return null;
		
	}
}
