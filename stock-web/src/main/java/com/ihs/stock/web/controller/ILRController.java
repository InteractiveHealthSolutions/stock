package com.ihs.stock.web.controller;

import java.text.DateFormatSymbols;
import java.util.List;

import org.ird.unfepi.context.LocationContext;
import org.ird.unfepi.context.LocationServiceContext;
import org.ird.unfepi.model.Location;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ihs.stock.api.beans.SearchBean;
import com.ihs.stock.api.context.ServiceContextStock;
import com.ihs.stock.api.context.SessionFactoryUtil;
import com.ihs.stock.api.model.ILRDailyStatus;
import com.ihs.stock.web.validator.ILRValidator;

@Controller
@RequestMapping("/viewgraph")
public class ILRController {

	private ILRValidator ilrValidator;
	
	@InitBinder("sb")
	public void initBinder(WebDataBinder binder)
	{
		binder.setValidator(ilrValidator);
	}
	@RequestMapping(value="/ilr" , method = RequestMethod.POST)
	public ModelAndView viewGraph(ModelAndView modelAndView , @ModelAttribute("sb") SearchBean sb , BindingResult results)
	{
		ilrValidator = new ILRValidator();
		ilrValidator.validate(sb, results);
		if(results.hasErrors())
		{
			modelAndView = ControllerUtility.setSearchILRGraph(modelAndView);
			modelAndView.addObject("sb", sb);
			return modelAndView;
		}
		modelAndView = ControllerUtility.setSearchILRGraph(modelAndView);
		ServiceContextStock sc = SessionFactoryUtil.getServiceContext();
		LocationServiceContext scL = LocationContext.getServices();
		try
		{
			Location location = scL.getLocationService().findLocationByName(sb.getlocationName(), false, null);
			List<ILRDailyStatus> dailyStatus = sc.ilrDailyStatusDAO.getForYearMonthLocation(location.getLocationId(), sb.getmonth(), sb.getyear());
			if(dailyStatus == null)
			{
				modelAndView.addObject("error", "No data Found");
				modelAndView.addObject("sb", sb);
				return modelAndView;
			}
			modelAndView.addObject("loc", location);
			modelAndView.addObject("mon", sb.getmonth());
			modelAndView.addObject("yr", sb.getyear());
			
			modelAndView.addObject("sb", sb);
			modelAndView.addObject("monName" , new DateFormatSymbols().getMonths()[sb.getmonth()-1]);
			return modelAndView;
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			scL.closeSession();
			sc.closeSession();
		}
		
		return null;
		
	}
}
