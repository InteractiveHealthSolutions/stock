package com.ihs.stock.web.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.management.InstanceAlreadyExistsException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ihs.stock.api.context.ServiceContextStock;
import com.ihs.stock.api.context.SessionFactoryUtil;
import com.ihs.stock.api.model.ILRDailyStatus;
import com.ihs.stock.api.service.DailyEntryService;
import com.mysql.jdbc.StringUtils;

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
	
	
    
   
	@RequestMapping(path="/view" , method = RequestMethod.GET /*, produces = "application/json"*/)
	public List<ILRDailyStatus> getForMonth(HttpServletRequest req,
			@RequestParam(value = "province", required = false) String province,
			@RequestParam(value = "division", required = false) String division,
			@RequestParam(value = "city", required = false) String district,
			@RequestParam(value = "town", required = false) String town,
			@RequestParam(value = "uc", required = false) String UC,
			@RequestParam(value = "vaccinationcenter", required = false) String vaccinationcenter,
			@RequestParam(value = "filterDatefrom", required = false) String filterDatefrom,
			@RequestParam(value = "filterDateto", required = false) String filterDateto) throws ParseException, InstanceAlreadyExistsException
	{
		
		ServiceContextStock sc = SessionFactoryUtil.getServiceContext();
		try
     	{
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String month = null;
			String year = null;
			if (!StringUtils.isEmptyOrWhitespaceOnly(filterDatefrom)) {
				Date date = sdf.parse(filterDatefrom);
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				int mon = cal.get(Calendar.MONTH)+1;
				year = "'" + cal.get(Calendar.YEAR) + "'";
				month = "'" + mon + "'";
			} else {// to make sure that procedure gets a null value for dates
				filterDatefrom = null;
			}
     		List<ILRDailyStatus> dailyStatus = sc.ilrDailyStatusDAO.getForYearMonthLocation(Integer.parseInt(vaccinationcenter), Integer.parseInt(month), Integer.parseInt(year));
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
