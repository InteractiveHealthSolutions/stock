package com.ihs.stock.api.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.ihs.stock.api.DAO.DAOConsumer;
import com.ihs.stock.api.DAO.DAODailyStats;
import com.ihs.stock.api.DAO.DAOILRDailyStatus;
import com.ihs.stock.api.DAO.DAOInventory;
import com.ihs.stock.api.DAO.DAOItem;
import com.ihs.stock.api.DAO.DAOMonthlyStats;
import com.ihs.stock.api.beans.DayEndEntryBean;
import com.ihs.stock.api.model.Consumer;
import com.ihs.stock.api.model.DailyStats;
import com.ihs.stock.api.model.ILRDailyStatus;
import com.ihs.stock.api.model.Inventory;
import com.ihs.stock.api.model.Item;
import com.ihs.stock.api.model.MonthlyStats;

public class DailyEntryService {

	DailyStats dailyStats;
	
	
	public void morningEntry(ILRDailyStatus ilrDailyStatus, int vac) throws ParseException
	{
	//	ilrDailyStatus = new ILRDailyStatus();
		DAOConsumer consumerDAO = new DAOConsumer();
		Consumer consumer = consumerDAO.getById(vac);
		ilrDailyStatus.setconsumer(consumer);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(sdf.format(new Date()));
		ilrDailyStatus.setdateCreated(date);

		ilrDailyStatus.setlocation(consumer.getlocation());
		
		DAOILRDailyStatus ilrDailyStatusDAO = new DAOILRDailyStatus();
		ilrDailyStatusDAO.save(ilrDailyStatus);
	}
	
	public void dayEndEntry(DayEndEntryBean deb , int vac) throws ParseException
	{
		dailyStats = new DailyStats();
		DAOConsumer consumerDAO = new DAOConsumer();
		Consumer consumer = consumerDAO.getById(vac);
		dailyStats.setconsumer(consumer);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(sdf.format(new Date()));
		dailyStats.setdateCreated(date);

		DAOItem itemDAO = new DAOItem();
		Item item = itemDAO.getByName(deb.getitemName());
		dailyStats.setitem(item);
        dailyStats.setusedQuantity(deb.getusedQuantityCount());
		dailyStats.setwastedQuantity(deb.getwastedQuantityCount());
		
		int quantityInAContainer = item.getenclosedQuantity();
		int containerUsed = 0 ;
		int containerWasted = 0;
		int quantityUsed = deb.getusedQuantityCount();
		int quantityWasted = deb.getwastedQuantityCount();
		
		if(quantityWasted > quantityInAContainer)
		{
			if((quantityWasted % quantityInAContainer) == 0)
			{
				containerWasted = quantityWasted / quantityInAContainer;
				dailyStats.setwastedContainers(containerWasted);
			}
			else
			{
				containerWasted = (quantityWasted / quantityInAContainer) + 1;
				dailyStats.setwastedContainers(containerWasted);
			}
			if((quantityUsed % quantityInAContainer) == 0)
			{
				containerUsed = quantityUsed / quantityInAContainer;
				dailyStats.setusedContainers(containerUsed);
			}
			else
			{
				containerUsed = (quantityUsed / quantityInAContainer)+1;
				dailyStats.setusedContainers(containerUsed);
			}
		}
		else
		{
			if(((quantityWasted + quantityUsed) % quantityInAContainer) == 0)
			{
				containerUsed = (quantityWasted + quantityUsed)/quantityInAContainer;
				dailyStats.setusedContainers(containerUsed);
			}
			else
			{
				containerUsed = ((quantityWasted + quantityUsed)/quantityInAContainer)+1;
				dailyStats.setusedContainers(containerUsed);
			}
			dailyStats.setwastedContainers(0);
		}
		DAODailyStats dailyStatsDAO = new DAODailyStats();
		dailyStatsDAO.save(dailyStats);

		DAOILRDailyStatus ilrDAO = new DAOILRDailyStatus();
		ILRDailyStatus ilr = ilrDAO.getMorningStatus(consumer);
		ilrDAO.update(ilr);
		
//		DAOMonthlyStats monthlyStatsDAO = new DAOMonthlyStats();
//		
//		Calendar cal = Calendar.getInstance();
//		int month = cal.get(Calendar.MONTH) + 1;
//		int year = cal.get(Calendar.YEAR);
		
		int totalContainers = containerUsed + containerWasted;
		int totalQuantity = quantityUsed + quantityWasted;
	
	
//		MonthlyStats monthlyStats = monthlyStatsDAO.getMonthBalance(consumer, month, year, item);
//		// do this for inventory
//	
//		
//		monthlyStats.settotalQuantity(monthlyStats.gettotalQuantity() - totalQuantity);
//		monthlyStats.settotalContainers(monthlyStats.gettotalContainers() - totalContainers);
//		monthlyStatsDAO.update(monthlyStats);
//		
		DAOInventory inventoryDAO = new DAOInventory();
		Inventory inventory = inventoryDAO.getBalanceForLocationMonthItem(consumer.getlocation(), item);
		inventory.setwastedContainers(inventory.getwastedContainers()+containerWasted);
		inventory.setusedContainers(inventory.getusedContainers() + containerUsed);
		inventory.settotalContainers(inventory.gettotalContainers() - totalContainers);
		inventoryDAO.update(inventory);
		//inventoryDAO.updateInventory(consumer.getlocation(), item, monthlyStats.gettotalContainers());
	}

}
