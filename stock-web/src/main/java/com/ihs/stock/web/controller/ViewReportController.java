package com.ihs.stock.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/report")
public class ViewReportController {

	@RequestMapping(value="/monthlyreport", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView viewMonthlyReport(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "province", required = false) String province,
			@RequestParam(value = "division", required = false) String division,
			@RequestParam(value = "city", required = false) String district,
			@RequestParam(value = "town", required = false) String town,
			@RequestParam(value = "uc", required = false) String UC,
			@RequestParam(value = "vaccinationcenter", required = false) String vaccinationcenter,
			@RequestParam(value = "filterDatefrom", required = false) String filterDatefrom,
			@RequestParam(value = "filterDateto", required = false) String filterDateto,ModelAndView modelAndView) throws ParseException
	{
		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		filterDatefrom =  new SimpleDateFormat("dd-MM-YYYY").format(sdf.parse(filterDatefrom));
//		filterDateto =  new SimpleDateFormat("dd-MM-YYYY").format(sdf.parse(filterDateto));
		modelAndView.addObject("filterDateto", filterDateto);
		modelAndView.addObject("filterDatefrom", filterDatefrom);
		modelAndView.addObject("province", province);
		modelAndView.addObject("division", division);
		modelAndView.addObject("city", district);
		modelAndView.addObject("uc", UC);
		modelAndView.addObject("town", town);
		modelAndView.addObject("vaccinationcenter", vaccinationcenter);
	    modelAndView.setViewName("stockmonthlyreport");
	    return modelAndView;
		
	}
}
