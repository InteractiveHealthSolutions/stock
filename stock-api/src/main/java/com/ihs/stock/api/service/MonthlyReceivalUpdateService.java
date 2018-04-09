package com.ihs.stock.api.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


import com.ihs.stock.api.beans.MonthlyReceivalFormBean;
import com.ihs.stock.api.context.ServiceContextStock;
import com.ihs.stock.api.context.SessionFactoryUtil;

import com.ihs.stock.api.model.Item;
import com.ihs.stock.api.model.MonthlyStats;
import com.mysql.jdbc.StringUtils;

public class MonthlyReceivalUpdateService {

	public MonthlyStats updateMonthlyReceival(MonthlyReceivalFormBean mfb) throws ParseException {
		MonthlyStats ms = new MonthlyStats();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ServiceContextStock scSTK = SessionFactoryUtil.getServiceContext();
		Item item = null;
		try {
			try
			{
				Date date = sdf.parse(sdf.format(new Date()));
				ms.setdateCreated(date);
				ms.setuser(mfb.getuserId());
				ms.setlocation(mfb.getlocationId());
				
				if(!mfb.getexpiryDate().equals(""))
				{
					date = sdf.parse(mfb.getexpiryDate());
					ms.setexpiryDate(date);
				}
				if(!mfb.getmanufactureDate().equals(""))
				{
					date = sdf.parse(mfb.getmanufactureDate());
					ms.setmanufactureDate(date);
				}
				
				date = sdf.parse(mfb.getreceivalDate());
				ms.setreceivalDate(date);
				item = scSTK.itemDAO.getByName(mfb.getitemName());
				// Inventory inv =
				// scSTK.inventoryDAO.getBalanceForLocationMonthItem(mfb.getlocationId(),item.getitemId());
				// ms.setinventoryReferral(inv.getinventoryID());
				// inv.settotalContainers(inv.gettotalContainers()-mfb.getnoOfVials());
				// scSTK.inventoryDAO.update(inv);
				ms.setitem(item.getitemId());
				ms.setinitialContainersCount(mfb.getvialsReceived());
				ms.setinitialQuantity(mfb.gettotalDosesReceived());
				Integer mon = null;
				Integer year = null;
				ms.setreferenceNumber(mfb.getreferenceNumber());
				ms.setreceivedFrom(mfb.getreceivedFrom());
				
				if (!StringUtils.isEmptyOrWhitespaceOnly(mfb.getreceivalDate())) {
					date = sdf.parse(mfb.getreceivalDate());
					Calendar cal = Calendar.getInstance();
					cal.setTime(date);
					mon = cal.get(Calendar.MONTH) + 1;
					year = cal.get(Calendar.YEAR);
				
				}

				boolean check = scSTK.monthlyStatsDAO.existMonthBalance(mfb.getuserId(), mon - 1, year, item.getitemId());
				if (check == true) {
					MonthlyStats msP = scSTK.monthlyStatsDAO.getMonthBalance(mfb.getuserId(), mon - 1, year, item.getitemId());
					if(msP.getbalanceQuantity() != null)
					{
						ms.settotalQuantity(msP.getbalanceQuantity() + mfb.gettotalDosesReceived());
					}
					else
					{
						ms.settotalQuantity(mfb.gettotalDosesReceived());
					}
					if(msP.getbalanceContainer() != null)
					{
						ms.settotalContainer(msP.getbalanceContainer() +mfb.getvialsReceived());
					}
					else
					{
						ms.settotalContainer(mfb.getvialsReceived());
					}
					
					
				} 
				else
				{
					ms.settotalQuantity(mfb.gettotalDosesReceived());
					ms.settotalContainer(mfb.getvialsReceived());
					
				}
			//	scSTK.monthlyStatsDAO.save(ms);
				return ms;
				
			}
		    catch(Exception e)
			{
		    	e.printStackTrace();
		    	return null;
			}

			
		} 
		finally {
		//	scSTK.commitTransaction();
			scSTK.closeSession();
			
		}
		
	}
}
