//package com.ihs.stock.api.service;
//
//import java.text.ParseException;
//import java.util.Calendar;
//
//import org.hibernate.SessionFactory;
//
//import com.ihs.locationmanagement.api.model.Location;
//import com.ihs.stock.api.DAO.DAOConsumer;
//import com.ihs.stock.api.DAO.DAODailyStats;
//import com.ihs.stock.api.DAO.DAOInventory;
//import com.ihs.stock.api.DAO.DAOItem;
//
//import com.ihs.stock.api.DAO.DAORequisition;
//import com.ihs.stock.api.context.ServiceContextStock;
//import com.ihs.stock.api.context.SessionFactoryUtil;
//import com.ihs.stock.api.model.Consumer;
//import com.ihs.stock.api.model.Inventory;
//import com.ihs.stock.api.model.Item;
//import com.ihs.stock.api.model.MonthlyStats;
//
//public class DatabaseValidationsService {
//
//	public static boolean validateNoofVials(int vacId , String itemName , int noOfVials)
//	{
//		 
//		ServiceContextStock scSTK = SessionFactoryUtil.getServiceContext();
//		
//		Consumer consumer = scSTK.consumerDAO.getById(vacId);
//		
//		Item item = scSTK.itemDAO.getByName(itemName);
//		
//		
//		Inventory inv = scSTK.inventoryDAO.getBalanceForLocationMonthItem(consumer.getlocation(), item);
//		scSTK.commitTransaction();
//		scSTK.closeSession();
//		return inv.gettotalContainers() > noOfVials? true :false;
//		
//	}
//	
////	public static boolean vacMonStatsExist(int vac , String itemName)
////	{
////		 
////		ServiceContextStock scSTK = SessionFactoryUtil.getServiceContext();
////		
////		
////		Consumer consumer = consumerDAO.getById(vac);
////		
////	
////		Item item = itemDAO.getByName(itemName);
////		
////		DAOMonthlyStats vacMonStats = new DAOMonthlyStats();
////		Calendar cal = Calendar.getInstance();
////		int month = cal.get(Calendar.MONTH)+1;
////		int year = cal.get(Calendar.YEAR);
////		
////		return vacMonStats.ifExist(consumer, month, year, item);
////		
////	}
//	
////	public static boolean morningEntryExist(int vac , String itemName) throws ParseException
////	{
////		 
////		ServiceContextStock scSTK = SessionFactoryUtil.getServiceContext();
////		
////		Consumer consumer = scSTK.consumerDAO.getById(vac);
////		
////		Item item  = scSTK.itemDAO.getByName(itemName);
////		
////		List<DailyStst>
////		return scSTK.dailyStatsDAO.getMorningEntry(item, consumer).size() > 0 ? true : false;
////		
////	}
//	
////	public static boolean quantityIsAvailable(int quantity , int vacId , String itemName)
////	{
////		 
////		ServiceContextStock scSTK = SessionFactoryUtil.getServiceContext();
////		
////		Consumer consumer = consumerDAO.getById(vacId);
////		
////		Item item = itemDAO.getByName(itemName);
////		
////		Calendar cal = Calendar.getInstance();
////		int month = cal.get(Calendar.MONTH);
////		int year = cal.get(Calendar.YEAR);
////		
////		DAOMonthlyStats monthlyStatsDAO = new DAOMonthlyStats();
////	    MonthlyStats monthlyStats = monthlyStatsDAO.getMonthBalance(consumer, month, year, item);
////		
////	    return monthlyStats.gettotalQuantity() > quantity ? true : false;
////		
////	}
////	
//	
//	
//}
