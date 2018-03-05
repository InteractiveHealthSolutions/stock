package com.ihs.stock.web.controller;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.SessionFactory;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ihs.stock.api.context.ServiceContextStock;
import com.ihs.stock.api.context.SessionFactoryUtil;
import com.ihs.stock.api.model.ILRDailyStatus;
import com.ihs.stock.web.validator.ILRValidator;
import com.mysql.jdbc.StringUtils;

@Controller
@RequestMapping("/view")
public class ILRController {

	private ILRValidator ilrValidator;

	@InitBinder("sb")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(ilrValidator);
	}
	/*
	 * @RequestMapping(value="/ilrgraph" , method = RequestMethod.POST) public
	 * ModelAndView viewGraph(ModelAndView modelAndView , @ModelAttribute("sb")
	 * SearchBean sb , BindingResult results) { ilrValidator = new
	 * ILRValidator(); ilrValidator.validate(sb, results);
	 * if(results.hasErrors()) { modelAndView =
	 * ControllerUtility.setSearchILRGraph(modelAndView);
	 * modelAndView.addObject("sb", sb); return modelAndView; } modelAndView =
	 * ControllerUtility.setSearchILRGraph(modelAndView); ServiceContextStock sc
	 * = SessionFactoryUtil.getServiceContext(); LocationServiceContext scL =
	 * LocationContext.getServices(); try { Location location =
	 * scL.getLocationService().findLocationByName(sb.getlocationName(), false,
	 * null); List<ILRDailyStatus> dailyStatus =
	 * sc.ilrDailyStatusDAO.getForYearMonthLocation(location.getLocationId(),
	 * sb.getmonth(), sb.getyear()); if(dailyStatus.size() < 1) {
	 * modelAndView.addObject("error", "No data Found");
	 * modelAndView.addObject("sb", sb); return modelAndView; }
	 * modelAndView.addObject("loc", location); modelAndView.addObject("mon",
	 * sb.getmonth()); modelAndView.addObject("yr", sb.getyear());
	 * 
	 * modelAndView.addObject("sb", sb); modelAndView.addObject("monName" , new
	 * DateFormatSymbols().getMonths()[sb.getmonth()-1]); return modelAndView;
	 * 
	 * } catch(Exception e) { e.printStackTrace(); } finally {
	 * scL.closeSession(); sc.closeSession(); }
	 * 
	 * return null;
	 * 
	 * }
	 */

	@RequestMapping(value = "/ilrgraph", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView viewGraph(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "province", required = false) String province,
			@RequestParam(value = "division", required = false) String division,
			@RequestParam(value = "city", required = false) String district,
			@RequestParam(value = "town", required = false) String town,
			@RequestParam(value = "uc", required = false) String UC,
			@RequestParam(value = "vaccinationcenter", required = false) String vaccinationcenter,
			@RequestParam(value = "filterDatefrom", required = false) String filterDatefrom,
			ModelAndView modelAndView)
			throws ParseException {

		// modelAndView = ControllerUtility.setSearchILRGraph(modelAndView);
		// SessionFactory sf = SessionFactoryUtil.getSessionFactory(null, null);
		ServiceContextStock sc = SessionFactoryUtil.getServiceContext();
		LocationServiceContext scL = LocationContext.getServices();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

		try {
			if (vaccinationcenter != null && filterDatefrom != null) {

				Location location = scL.getLocationService().findLocationById(Integer.parseInt(vaccinationcenter),
						false, null);
				String month = null;
				String year = null;
				if (!StringUtils.isEmptyOrWhitespaceOnly(filterDatefrom)) {
					// filterDatefrom = new
					// SimpleDateFormat("yyyy-MM-dd").format(sdf.parse(filterDatefrom));
					Date date = sdf.parse(filterDatefrom);
					Calendar cal = Calendar.getInstance();
					cal.setTime(date);
					int mon = cal.get(Calendar.MONTH) + 1;
					year = "" + cal.get(Calendar.YEAR) + "";
					month = "" + mon + "";
				}

				List<ILRDailyStatus> dailyStatus = sc.ilrDailyStatusDAO.getForYearMonthLocation(
						location.getLocationId(), Integer.parseInt(month), Integer.parseInt(year));
			//	modelAndView.addObject("filterDateto", filterDateto);
				modelAndView.addObject("filterDatefrom", filterDatefrom);
				modelAndView.addObject("province", province);
				modelAndView.addObject("division", division);
				modelAndView.addObject("city", district);
				modelAndView.addObject("uc", UC);
				modelAndView.addObject("town", town);
				modelAndView.addObject("vaccinationcenter", vaccinationcenter);

				if (dailyStatus.size() < 1) {
					modelAndView.addObject("error", "No data Found");
					modelAndView.setViewName("ilrGraph");

					return modelAndView;
				}
			//	sdf = new SimpleDateFormat("yyyy-MM-dd");
			//	filterDatefrom = new SimpleDateFormat("dd-MM-YYYY").format(sdf.parse(filterDatefrom));
		//		filterDateto = new SimpleDateFormat("dd-MM-YYYY").format(sdf.parse(filterDateto));
				modelAndView.addObject("loc", location);
				modelAndView.addObject("mon", month);
				modelAndView.addObject("yr", year);
				modelAndView.addObject("monName", new DateFormatSymbols().getMonths()[Integer.parseInt(month) - 1]);
			}

			modelAndView.setViewName("ilrGraph");
			return modelAndView;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			scL.closeSession();
			sc.closeSession();
		}

		return null;

	}

	@RequestMapping(value = "/ilrtable", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView viewilrTable(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "province", required = false) String province,
			@RequestParam(value = "division", required = false) String division,
			@RequestParam(value = "city", required = false) String district,
			@RequestParam(value = "town", required = false) String town,
			@RequestParam(value = "uc", required = false) String UC,
			@RequestParam(value = "vaccinationcenter", required = false) String vaccinationcenter,
			@RequestParam(value = "filterDatefrom", required = false) String filterDatefrom,
			ModelAndView modelAndView) {
		ServiceContextStock sc = SessionFactoryUtil.getServiceContext();
		LocationServiceContext scL = LocationContext.getServices();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		try {
			if (vaccinationcenter != null && filterDatefrom != null) {

				Location location = scL.getLocationService().findLocationById(Integer.parseInt(vaccinationcenter),
						false, null);
				String month = null;
				String year = null;
				if (!StringUtils.isEmptyOrWhitespaceOnly(filterDatefrom)) {
					Date date = sdf.parse(filterDatefrom);
					Calendar cal = Calendar.getInstance();
					cal.setTime(date);
					int mon = cal.get(Calendar.MONTH) + 1;
					year = "" + cal.get(Calendar.YEAR) + "";
					month = "" + mon + "";
				}
				List<ILRDailyStatus> dailyStatus = sc.ilrDailyStatusDAO.getForYearMonthLocation(
						location.getLocationId(), Integer.parseInt(month), Integer.parseInt(year));
			//	modelAndView.addObject("filterDateto", filterDateto);
				modelAndView.addObject("filterDatefrom", filterDatefrom);
				modelAndView.addObject("province", province);
				modelAndView.addObject("division", division);
				modelAndView.addObject("city", district);
				modelAndView.addObject("uc", UC);
				modelAndView.addObject("town", town);
				modelAndView.addObject("vaccinationcenter", vaccinationcenter);

				if (dailyStatus.size() < 1) {
					modelAndView.addObject("error", "No data Found");
					modelAndView.setViewName("ilrTable");
					return modelAndView;
				}
				//sdf = new SimpleDateFormat("yyyy-MM-dd");
			//	filterDatefrom = new SimpleDateFormat("dd-MM-YYYY").format(sdf.parse(filterDatefrom));
	//			filterDateto = new SimpleDateFormat("dd-MM-YYYY").format(sdf.parse(filterDateto));
				modelAndView.addObject("loc", location);
				modelAndView.addObject("mon", month);
				modelAndView.addObject("yr", year);
				modelAndView.addObject("monName", new DateFormatSymbols().getMonths()[Integer.parseInt(month) - 1]);
			}
			

			modelAndView.setViewName("ilrTable");
			return modelAndView;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			scL.closeSession();
			sc.closeSession();
		}

		return null;

	}
}
