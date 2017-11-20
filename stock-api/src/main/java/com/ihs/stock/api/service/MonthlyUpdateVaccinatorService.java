package com.ihs.stock.api.service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import com.ihs.stock.api.DAO.DAOConsumer;
import com.ihs.stock.api.DAO.DAOInventory;
import com.ihs.stock.api.DAO.DAOItem;
import com.ihs.stock.api.DAO.DAOMonthlyStats;

import com.ihs.stock.api.beans.MonthlyUpdateVaccinatorBean;
import com.ihs.stock.api.model.App;
import com.ihs.stock.api.model.Consumer;
import com.ihs.stock.api.model.Inventory;
import com.ihs.stock.api.model.Item;
import com.ihs.stock.api.model.MonthlyStats;


public class MonthlyUpdateVaccinatorService {

	
	public void updateMonthlyStats(MonthlyUpdateVaccinatorBean muvb , int vacId) throws ParseException
	{
		//if exist same month , year and vaccinator record in the DB throw exception
		//do this in validator
		//else
		MonthlyStats vms = new MonthlyStats();
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH)+1;
		int year = cal.get(Calendar.YEAR);
		vms.setmonth(month);
		vms.setyear(year);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(sdf.format(new Date()));
	    
		vms.setdateCreated(date);
		
		vms.setexpiryDate(convertStringToDate(muvb.getexpiryDate()));
		vms.setmanufactureDate(convertStringToDate(muvb.getmanufactureDate()));
	    DAOConsumer consumerDAO = new DAOConsumer();
	    Consumer vac = consumerDAO.getById(vacId);
		vms.setconsumer(vac);
		
		DAOItem itemDAO = new DAOItem();
		Item item = itemDAO.getByName(muvb.getitemName());
		
		vms.setitem(item);
		
		int antigensInAVial = item.getenclosedQuantity();
		
		vms.setinitialQuantity(antigensInAVial * muvb.getnoOfVials());
		vms.setinitialContainersCount(muvb.getnoOfVials());
		
		DAOMonthlyStats vacMonStatsDAO = new DAOMonthlyStats();
		
		//to update prev month balance and total vials
		if(vacMonStatsDAO.ifExist(vac, vms.getmonth()-1, vms.getyear() , item))
		{
			//get prev month balance for that vaccinator
			int balanceVials = vacMonStatsDAO.getMonthBalance(vac, vms.getmonth()-1, vms.getyear() , item).getbalanceContainers();
			vms.setbalanceContainers(balanceVials);
			vms.setbalanceQuantity(balanceVials*antigensInAVial);
			vms.settotalContainers(balanceVials + vms.getinitialContainers());
			vms.settotalQuantity(vms.gettotalContainers()*antigensInAVial);
			
		}
		else //if there is no prev month record for the vaccinator
		{
			vms.setbalanceContainers(0);
			vms.setbalanceQuantity(0);
			vms.settotalContainers(vms.getinitialContainers());
			vms.settotalQuantity(vms.getinitialQuantity());
		}
		DAOInventory inv = new DAOInventory();
		inv.updateInventory(vac.getlocation() ,item, vms.gettotalContainers());
		//updateInventory( , vms);
		DAOMonthlyStats vmsDAO = new DAOMonthlyStats();
		vmsDAO.save(vms);
		//balance update in main inventory
		
	}
	
	private Date convertStringToDate(String date) throws ParseException 
	{
		Date dateToday = (Date) new SimpleDateFormat("MM/dd/yyyy").parse(date);
		return dateToday;
	}
	//get location from vaccinators location	
	
}
