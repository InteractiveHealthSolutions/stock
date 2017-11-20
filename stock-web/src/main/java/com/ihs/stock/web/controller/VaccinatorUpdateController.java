package com.ihs.stock.web.controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ihs.stock.api.beans.DayEndEntryBean;
import com.ihs.stock.api.beans.MonthlyUpdateVaccinatorBean;
import com.ihs.stock.api.beans.MorningEntryBean;
import com.ihs.stock.api.model.ILRDailyStatus;
import com.ihs.stock.api.service.DailyEntryService;
import com.ihs.stock.api.service.MonthlyUpdateVaccinatorService;
import com.ihs.stock.web.validator.DailyMorningEntryValidator;
import com.ihs.stock.web.validator.VaccinatorMonthlyUpdateValidator;


@Controller
@RequestMapping("/vaccinator")
public class VaccinatorUpdateController {
	
	private VaccinatorMonthlyUpdateValidator monthlyUpdateValidator;
	
	private DailyMorningEntryValidator dailyMorningEntryValidator;
	
	@InitBinder("monthlyupdate")
	private void monthlyUpdateInitBinder(WebDataBinder binder)
	{
		binder.setValidator(monthlyUpdateValidator);
	}
	
	@InitBinder("morningentry")
	private void dailyMorningEntryInitBinder(WebDataBinder binder)
	{
		binder.setValidator(dailyMorningEntryValidator);
	}
		/*
	 * The  "1" used in this function is assumed to be vaccinator id that will be 
	 * replaced vac request parameter vac (actual vaccinator id)
	 */
	@RequestMapping(value = "/monthlystatsupdate" , method=RequestMethod.POST)
    public ModelAndView addVaccinatorMonthlyStats(@ModelAttribute("monthlyUpdate") @Validated MonthlyUpdateVaccinatorBean muvb, BindingResult results,
			/*@PathVariable("id") int vaccinator,*/ HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) throws ParseException
	{
		monthlyUpdateValidator = new VaccinatorMonthlyUpdateValidator();
		monthlyUpdateValidator.validate(muvb, results);
		
		if(results.hasErrors())
		{
			modelAndView = ControllerUtility.setMonthlyUpdateform(modelAndView,1);
			modelAndView.addObject("monthlyUpdate", muvb);
		    modelAndView.setViewName("update_monthlyStats");
			
			return modelAndView;
		}
		
		MonthlyUpdateVaccinatorService muvs = new MonthlyUpdateVaccinatorService();
		muvs.updateMonthlyStats(muvb, 1);
		
		return new ModelAndView("done");
	}
	
	@RequestMapping(value = "/monthlystatsupdate" , method=RequestMethod.POST ,
			consumes="application/json")
	@ResponseBody
    public ModelAndView addVaccinatorMonthlyStatsJSON( @RequestBody @Validated MonthlyUpdateVaccinatorBean muvb, BindingResult results,
			/*@PathVariable("id") int vaccinator,*/ HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) throws ParseException
	{
		monthlyUpdateValidator = new VaccinatorMonthlyUpdateValidator();
		monthlyUpdateValidator.validate(muvb, results);
		
		if(results.hasErrors())
		{
			modelAndView = ControllerUtility.setMonthlyUpdateform(modelAndView,1);
			modelAndView.addObject("monthlyUpdate", muvb);
		    modelAndView.setViewName("update_monthlyStats");
			
			return modelAndView;
		}
		
		MonthlyUpdateVaccinatorService muvs = new MonthlyUpdateVaccinatorService();
		muvs.updateMonthlyStats(muvb, 1);
		
		return new ModelAndView("done");
	}
	
	@RequestMapping(value = "/dailymorningentry" , method = RequestMethod.POST)
	public ModelAndView dailyEntryMorning(@ModelAttribute("morningentry") @Validated ILRDailyStatus ilr ,BindingResult results
			, HttpServletRequest request, HttpServletResponse response,ModelAndView modelAndView 
			/*,@RequestParam("vac") int vaccinatorId*/) throws ParseException 
	{
		dailyMorningEntryValidator = new DailyMorningEntryValidator();
		dailyMorningEntryValidator.validate(ilr, results);
		if(results.hasErrors())
		{
			modelAndView = ControllerUtility.setDailyEntryMorningForm(modelAndView,1);
			//modelAndView.addObject("morningentry", meb);
		    modelAndView.setViewName("daily_morningEntry");
			return modelAndView;
		}
		DailyEntryService des = new DailyEntryService();
		des.morningEntry(ilr , 1);
		return new ModelAndView("done");
		
	}
	@RequestMapping(value = "/dailymorningentry" , method = RequestMethod.POST ,
			consumes= "application/json")
	@ResponseBody
	public ModelAndView dailyEntryMorningJSON(@RequestBody @Validated ILRDailyStatus ilr ,BindingResult results
			, HttpServletRequest request, HttpServletResponse response,ModelAndView modelAndView 
			/*,@RequestParam("vac") int vaccinatorId*/) throws ParseException 
	{
		dailyMorningEntryValidator = new DailyMorningEntryValidator();
		dailyMorningEntryValidator.validate(ilr, results);
		if(results.hasErrors())
		{
			modelAndView = ControllerUtility.setDailyEntryMorningForm(modelAndView,1);
			//modelAndView.addObject("morningentry", meb);
		    modelAndView.setViewName("daily_morningEntry");
			return modelAndView;
		}
		DailyEntryService des = new DailyEntryService();
		des.morningEntry(ilr , 1);
		return new ModelAndView("done");
		
	}
	
	
	@RequestMapping(value = "/dailydayendentry" , method = RequestMethod.POST)
	public ModelAndView dailyEntryDayEnd(ModelAndView modelAndView , @ModelAttribute("dayendentry") @Validated DayEndEntryBean deeb,
			BindingResult results/*, @RequestParam("vac") int vaccinatorId*/) throws ParseException
	{
	    DailyEntryService des = new DailyEntryService();
	    des.dayEndEntry(deeb, 1);
		return new ModelAndView("done");
		
	}
	
	@RequestMapping(value = "/dailydayendentry" , method = RequestMethod.POST,
			consumes="application/json")
	public ModelAndView dailyEntryDayEndJSON(ModelAndView modelAndView , @RequestBody @Validated DayEndEntryBean deeb,
			BindingResult results/*, @RequestParam("vac") int vaccinatorId*/) throws ParseException
	{
	    DailyEntryService des = new DailyEntryService();
	    des.dayEndEntry(deeb, 1);
		return new ModelAndView("done");
		
	}

}
