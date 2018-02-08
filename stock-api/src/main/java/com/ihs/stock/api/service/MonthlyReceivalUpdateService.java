package com.ihs.stock.api.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ihs.stock.api.beans.MonthlyReceivalFormBean;
import com.ihs.stock.api.context.ServiceContextStock;
import com.ihs.stock.api.context.SessionFactoryUtil;
import com.ihs.stock.api.model.Inventory;
import com.ihs.stock.api.model.Item;
import com.ihs.stock.api.model.MonthlyStats;

public class MonthlyReceivalUpdateService {

	
	public void updateMonthlyReceival(MonthlyReceivalFormBean mfb) throws ParseException
	{
	   MonthlyStats ms = new MonthlyStats();
	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	   ServiceContextStock scSTK = SessionFactoryUtil.getServiceContext();
	   try
	   {
		   Date date = sdf.parse(sdf.format(new Date()));
		   ms.setdateCreated(date);
		   ms.setuser(mfb.getuserId());
		   ms.setlocation(mfb.getlocationId());
		   date = sdf.parse(mfb.getexpiryDate());
		   ms.setexpiryDate(date);
		   date = sdf.parse(mfb.getmanufactureDate());
		   ms.setmanufactureDate(date);
		   date = sdf.parse(mfb.getreceivalDate());
		   ms.setreceivalDate(date);
		   Item item = scSTK.itemDAO.getByName(mfb.getitemName());
		   Inventory inv = scSTK.inventoryDAO.getBalanceForLocationMonthItem(mfb.getlocationId(),item.getitemId());
		   ms.setinventoryReferral(inv.getinventoryID());
		   inv.settotalContainers(inv.gettotalContainers()-mfb.getnoOfVials());
		   scSTK.inventoryDAO.update(inv);
		   ms.setinitialContainersCount(mfb.getnoOfVials());
		   ms.setinitialQuantity(mfb.gettotalDosesReceived());
		   ms.settotalQuantity(ms.getbalanceQuantity()+mfb.gettotalDosesReceived());
		   scSTK.monthlyStatsDAO.save(ms);
	   }
	   finally
	   {
		   scSTK.closeSession();
	   }
	   
	}
}
