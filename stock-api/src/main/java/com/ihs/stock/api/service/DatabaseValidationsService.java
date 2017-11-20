package com.ihs.stock.api.service;

import java.text.ParseException;
import java.util.Calendar;

import com.ihs.stock.api.DAO.DAOConsumer;
import com.ihs.stock.api.DAO.DAODailyStats;
import com.ihs.stock.api.DAO.DAOInventory;
import com.ihs.stock.api.DAO.DAOItem;
import com.ihs.stock.api.DAO.DAOMonthlyStats;
import com.ihs.stock.api.model.Consumer;
import com.ihs.stock.api.model.Inventory;
import com.ihs.stock.api.model.Item;
import com.ihs.stock.api.model.MonthlyStats;

public class DatabaseValidationsService {

	public static boolean validateNoofVials(int vacId , String itemName , int noOfVials)
	{
		DAOConsumer consumerDAO = new DAOConsumer();
		Consumer consumer = consumerDAO.getById(vacId);
		
		DAOItem itemDAO = new DAOItem();
		Item item = itemDAO.getByName(itemName);
		
		DAOInventory inventoryDAO = new DAOInventory();
		
		Inventory inv = inventoryDAO.getBalanceForLocationMonthItem(consumer.getlocation(), item);
		return inv.gettotalContainers() > noOfVials? true :false;
		
	}
	
	public static boolean vacMonStatsExist(int vac , String itemName)
	{
		DAOConsumer consumerDAO = new DAOConsumer();
		Consumer consumer = consumerDAO.getById(vac);
		
		DAOItem itemDAO = new DAOItem();
		Item item = itemDAO.getByName(itemName);
		
		DAOMonthlyStats vacMonStats = new DAOMonthlyStats();
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH)+1;
		int year = cal.get(Calendar.YEAR);
		
		return vacMonStats.ifExist(consumer, month, year, item);
		
	}
	
	public static boolean morningEntryExist(int vac , String itemName) throws ParseException
	{
		DAOConsumer consumerDAO = new DAOConsumer();
		Consumer consumer = consumerDAO.getById(vac);
		
		DAOItem itemDAO = new DAOItem();
		Item item  = itemDAO.getByName(itemName);
		
		DAODailyStats dailyStatsDAO = new DAODailyStats();
		
		return dailyStatsDAO.getMorningEntry(item, consumer).size() > 0 ? true : false;
		
	}
	
	public static boolean quantityIsAvailable(int quantity , int vacId , String itemName)
	{
		DAOConsumer consumerDAO = new DAOConsumer();
		Consumer consumer = consumerDAO.getById(vacId);
		
		DAOItem itemDAO = new DAOItem();
		Item item = itemDAO.getByName(itemName);
		
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		
		DAOMonthlyStats monthlyStatsDAO = new DAOMonthlyStats();
	    MonthlyStats monthlyStats = monthlyStatsDAO.getMonthBalance(consumer, month, year, item);
		
	    return monthlyStats.gettotalQuantity() > quantity ? true : false;
		
	}
	
}
