package com.ihs.stock.web.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ihs.stock.api.DAO.DAOMonthlyStats;
import com.ihs.stock.api.beans.MonthlyReceivalFormBean;
import com.ihs.stock.api.context.ServiceContextStock;
import com.ihs.stock.api.context.SessionFactoryUtil;
import com.ihs.stock.api.model.Consumer;
import com.ihs.stock.api.model.Inventory;
import com.ihs.stock.api.model.Item;
import com.ihs.stock.api.model.MonthlyStats;
import com.ihs.stock.api.service.MonthlyReceivalUpdateService;

@RestController
@RequestMapping("/monthlystats")
public class MonthlyStatsResource {

	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody String add(@RequestBody List<MonthlyReceivalFormBean> ms) throws ParseException {
		//SessionFactory sf = SessionFactoryUtil.getSessionFactory(null, null);
		ServiceContextStock scSTK = SessionFactoryUtil.getServiceContext();
		//HashMap<String , String> response = new HashMap<>();
		try {
			for (int i = 0; i < ms.size(); i++) {
				try {
					Item item = scSTK.itemDAO.getByName(ms.get(i).getitemName());
					/*
					 * Inventory inv =
					 * scSTK.inventoryDAO.getBalanceForLocationMonthItem(ms.get(
					 * i).getlocationId(),item.getitemId());
					 * if((inv.gettotalContainers() - ms.get(i).getnoOfVials())
					 * < 0) { response = response+"0"; } else {
					 */ MonthlyReceivalUpdateService mrs = new MonthlyReceivalUpdateService();
					MonthlyStats monthlyStats = mrs.updateMonthlyReceival(ms.get(i));
					// response = response + "1";
					if(monthlyStats == null)
					{
						//response.put(item.getname(), "success");
						return "0";
						
					}
					else
					{
						//response.put(item.getname(), "failure");
						scSTK.monthlyStatsDAO.save(monthlyStats);
					}
					// }
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

			}
		} finally {
			scSTK.commitTransaction();
			scSTK.closeSession();
		}

		return "1";

	}

}
