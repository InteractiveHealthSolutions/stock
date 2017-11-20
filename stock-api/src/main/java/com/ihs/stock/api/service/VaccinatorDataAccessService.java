package com.ihs.stock.api.service;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;


import com.ihs.stock.api.DAO.DAOConsumer;
import com.ihs.stock.api.DAO.DAODailyStats;
import com.ihs.stock.api.DAO.DAOInventory;
import com.ihs.stock.api.DAO.DAOMonthlyStats;
import com.ihs.stock.api.model.Consumer;
import com.ihs.stock.api.model.Inventory;
import com.ihs.locationmanagement.api.model.Location;
import com.ihs.stock.api.model.MonthlyStats;

public class VaccinatorDataAccessService {

	
	public List<?> viewMyBalance(int vac)
	{
		DAOConsumer consumerDAO = new DAOConsumer();
		Consumer vaccinator = consumerDAO.getById(vac);
		
		DAOMonthlyStats vacMonStatsDAO = new DAOMonthlyStats();
		
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH)+1;
		int year = cal.get(Calendar.YEAR);
		
		List<MonthlyStats> monthlyBalance = vacMonStatsDAO.getMonthlyRecordForAllItems(vaccinator, month, year);  
		
		return monthlyBalance;
	}
	
	public List<?> viewLocationBalance(int vac)
	{
		DAOConsumer consumerDAO = new DAOConsumer();
		Consumer consumer = consumerDAO.getById(vac);
		
		DAOInventory inventoryDAO = new DAOInventory();
		Location loc = consumer.getlocation();
		//List<Inventory> inventoryList = (List<Inventory>) inventoryDAO.getBalanceForLocationAllItems(vaccinator.getvaccinatorLocation());
		List<Inventory> inventoryList = (List<Inventory>) inventoryDAO.getBalanceForLocationAllItems(loc);
		
		return inventoryList;
		
	}
	
	public List<?> viewDailyStats(int vac) throws ParseException
	{
		DAODailyStats dailyStatsDAO = new DAODailyStats();
		List<?> dStats = dailyStatsDAO.getDailyStatsForToday(vac);
		
		return dStats;
		
	}
	
	
}
