package com.ihs.stock.web.rest;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ihs.stock.api.context.ServiceContextStock;
import com.ihs.stock.api.context.SessionFactoryUtil;
import com.mysql.jdbc.StringUtils;
import com.ihs.stock.web.utils.StringUtilities;;
@Controller
public class ReportingResource {

	@RequestMapping(value = "stock-monthly-report", method = RequestMethod.GET)
	public @ResponseBody List getStockMonthlyReport(HttpServletRequest req,
			@RequestParam(value = "province", required = false) String province,
			@RequestParam(value = "division", required = false) String division,
			@RequestParam(value = "city", required = false) String district,
			@RequestParam(value = "town", required = false) String town,
			@RequestParam(value = "uc", required = false) String UC,
			@RequestParam(value = "vaccinationcenter", required = false) String vaccinationcenter,
			@RequestParam(value = "filterDatefrom", required = false) String filterDatefrom,
			@RequestParam(value = "filterDateto", required = false) String filterDateto) throws ParseException {

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

//		if (!StringUtils.isEmptyOrWhitespaceOnly(filterDateto)) {
//			filterDateto =new SimpleDateFormat("yyyy-MM-dd").format(sdf.parse(filterDateto));
//		} else {
//			filterDateto = null;
//		}

		ServiceContextStock sc = SessionFactoryUtil.getServiceContext();
		String locId = StringUtilities.coalesce(vaccinationcenter,town, district, division,province);
		try {
			
			String q = "CALL StockMonthlyReportVaccines('"+locId+"',"+year+","+month+")";
			System.out.println(q);
			List items = sc.customQueryDAO.getDataBySQLMapResult(q);
			return items;
			
		} finally {
			sc.closeSession();
		}
		//return null;

	}
}
