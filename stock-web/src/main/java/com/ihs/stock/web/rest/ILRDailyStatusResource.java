package com.ihs.stock.web.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.management.InstanceAlreadyExistsException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ihs.stock.api.context.ServiceContextStock;
import com.ihs.stock.api.context.SessionFactoryUtil;
import com.ihs.stock.api.model.ILRDailyStatus;
import com.ihs.stock.api.service.DailyEntryService;
import com.mysql.jdbc.StringUtils;

@Controller
//@CrossOrigin//(origins = {"http://localhost:8080"}, maxAge = 4800, allowCredentials = "false")
@RequestMapping("/ilrws")
public class ILRDailyStatusResource {
     ReportsDownloadResource reportsDownloadResource = new ReportsDownloadResource();
	@RequestMapping(value="/add" , method = RequestMethod.POST /*, consumes = "application/json" , produces = "application/json"*/)
	@ResponseBody
	public String add( @RequestBody List<ILRDailyStatus> ilr) throws ParseException, InstanceAlreadyExistsException
	{
		if(ilr == null)
		{
			return "0";
		}
		
		DailyEntryService des = new DailyEntryService();
		for(int i = 0 ; i < ilr.size() ; i++)
		{
			try
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
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				//return "0";
			}
					
		}
		return "1";
		
	}
	
	
    
   
	@RequestMapping(path="/view" , method = RequestMethod.GET /*, produces = "application/json"*/)
	@ResponseBody
	public List<ILRDailyStatus> getForMonth(HttpServletRequest req,
			HttpServletResponse resp,
			@RequestParam(value = "province", required = false) String province,
			@RequestParam(value = "division", required = false) String division,
			@RequestParam(value = "city", required = false) String district,
			@RequestParam(value = "town", required = false) String town,
			@RequestParam(value = "uc", required = false) String UC,
			@RequestParam(value = "vaccinationcenter", required = false) String vaccinationcenter,
			@RequestParam(value = "filterDatefrom", required = false) String filterDatefrom,
			@RequestParam(value = "filterDateto", required = false) String filterDateto,
			@RequestParam(value = "type", required = false , defaultValue = "") String type,
			@RequestParam(value = "breadcrumb", required = false) String crumb) throws ParseException, InstanceAlreadyExistsException
	{
		
		ServiceContextStock sc = SessionFactoryUtil.getServiceContext();
		try
     	{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			String month = null;
			int year =0;
			int mon = 0;
			if (!StringUtils.isEmptyOrWhitespaceOnly(filterDatefrom)) {
				Date date = sdf.parse(filterDatefrom);
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
			   mon = cal.get(Calendar.MONTH)+1;
			   year = cal.get(Calendar.YEAR) ;
			//	year = "'" + cal.get(Calendar.YEAR) + "'";
				//month = "'" + mon + "'";
			} else {// to make sure that procedure gets a null value for dates
				filterDatefrom = null;
			}
			System.out.println("in ilr resource");
     		List<ILRDailyStatus> dailyStatus = sc.ilrDailyStatusDAO.getForYearMonthLocation(Integer.parseInt(vaccinationcenter), mon, year);
     		if(type.equals("PDF"))
    		{
     			System.out.println("in download ilr");
    			HashMap<String,Object> additionalDetails = new HashMap<String,Object>();
				additionalDetails.put("Location", crumb);
				additionalDetails.put("fromDate", new SimpleDateFormat("yyyy-MM").parse(filterDatefrom));
				//additionalDetails.put("toDate",new SimpleDateFormat("yyyy-MM").parse(filterDateto));
				additionalDetails.put("totalRows", dailyStatus.size());
				reportsDownloadResource.generatePDF(resp,dailyStatus  , "Temperature_Monitoring_Chart.jrxml","Temperature_Monitoring_Chart" , additionalDetails);
				return null;

    		}
     		else
     		{
     			if(dailyStatus.size() < 0)
             	{
            		return null;
            	}
        	
            	return dailyStatus;
     		}
     		
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
