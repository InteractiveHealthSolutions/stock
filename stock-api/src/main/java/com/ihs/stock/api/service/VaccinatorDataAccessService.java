package com.ihs.stock.api.service;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.management.InstanceAlreadyExistsException;


import com.ihs.stock.api.context.ServiceContextStock;
import com.ihs.stock.api.context.SessionFactoryUtil;
import com.ihs.stock.api.model.Inventory;

public class VaccinatorDataAccessService {

	public List<?> viewMyBalance(int locationId) throws InstanceAlreadyExistsException {

		ServiceContextStock scSTK = SessionFactoryUtil.getServiceContext();
		try {
			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR);

			List<Inventory> monthlyBalance = scSTK.inventoryDAO.getForLocationMonthYear(locationId, month, year);

			return monthlyBalance;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			scSTK.closeSession();
		}

		return null;

	}

	// public List<?> viewLocationBalance(int vac) {
	//
	// ServiceContextStock scSTK = SessionFactoryUtil.getServiceContext();
	//
	// Consumer consumer = scSTK.consumerDAO.getById(vac);
	//
	// Location loc = consumer.getlocation();
	// // List<Inventory> inventoryList = (List<Inventory>)
	// //
	// inventoryDAO.getBalanceForLocationAllItems(vaccinator.getvaccinatorLocation());
	// List<Inventory> inventoryList = (List<Inventory>)
	// scSTK.inventoryDAO.getBalanceForLocationAllItems(loc);
	// scSTK.commitTransaction();
	// scSTK.closeSession();
	// return inventoryList;
	//
	// }

	public List<?> viewDailyStats(int vac, Date date) throws ParseException {

		ServiceContextStock scSTK = SessionFactoryUtil.getServiceContext();
		try {
			List<?> dStats = scSTK.dailyStatsDAO.getDailyStatsForToday(vac, date);
			return dStats;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			scSTK.closeSession();
		}
		return null;

	}

}
