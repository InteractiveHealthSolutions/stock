package com.ihs.stock.web.controller;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.management.InstanceAlreadyExistsException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ihs.stock.api.model.DailyStats;
import com.ihs.stock.api.model.Inventory;
import com.ihs.stock.api.model.MonthlyStats;
import com.ihs.stock.api.service.VaccinatorDataAccessService;

@Controller
@RequestMapping("/vacDataAccess")

public class VaccinatorDataAccessController {
	
	VaccinatorDataAccessService vacDataAccess;
	
	@RequestMapping(value= "/balance", method = RequestMethod.GET )
	@ResponseBody
	public ModelAndView getVaccinatorBalanceStats( /*@PathVariable int consumer,*/HttpServletRequest request, HttpServletResponse response,
			ModelAndView modelAndView,HttpSession session) throws InstanceAlreadyExistsException
	{
		vacDataAccess = new VaccinatorDataAccessService();
		
		List<MonthlyStats> vacMonStats = (List<MonthlyStats>) vacDataAccess.viewMyBalance(1);
		
		//modelAndView = ControllerUtility.setViewMyBalancePage(vacMonStats);
		
		String monthString = new DateFormatSymbols().getMonths()[vacMonStats.get(0).getmonth()-1];
	    modelAndView.addObject("year", vacMonStats.get(0).getyear());
		modelAndView.addObject("month", monthString);
		modelAndView.addObject("list", vacMonStats);
		modelAndView.setViewName("vaccinator_viewBalance");
		return modelAndView;
		
	}
	
//	@RequestMapping(value = "/locationBalance/{consumer}" , method = RequestMethod.GET)
//	@ResponseBody
//	public ModelAndView viewLocationStats(@PathVariable int consumer,ModelAndView modelAndView)
//	{
//		vacDataAccess = new VaccinatorDataAccessService();
//		List<Inventory> vacMonStats = (List<Inventory>) vacDataAccess.viewLocationBalance(consumer);
//		
//		String monthString = new DateFormatSymbols().getMonths()[vacMonStats.get(0).getmonth()-1];
//	    modelAndView.addObject("year", vacMonStats.get(0).getyear());
//		modelAndView.addObject("month", monthString);
//		modelAndView.addObject("list", vacMonStats);
//		modelAndView.setViewName("vaccinator_locationBalance");
//		
//		return modelAndView;
//		
//	}
//	@RequestMapping(value = "/dailystats" , method = RequestMethod.GET)
//	public ModelAndView viewDailyStats(/*@PathVariable int customer ,*/ ModelAndView modelAndView) throws ParseException
//	{
//		vacDataAccess = new VaccinatorDataAccessService();
//		List<DailyStats> dailyStats = (List<DailyStats>) vacDataAccess.viewDailyStats(1);
//	    String date = dailyStats.get(0).getdateCreated().toString().substring(0, 10);
//		modelAndView.addObject("date", date);
//		modelAndView.addObject("dailyStats", dailyStats);
//		modelAndView.setViewName("view_dailystats");
//		return modelAndView;
//		
//	}
	
	

}
