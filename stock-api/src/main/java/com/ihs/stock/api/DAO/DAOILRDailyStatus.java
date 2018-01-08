package com.ihs.stock.api.DAO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.management.InstanceAlreadyExistsException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.ird.unfepi.context.LocationContext;
import org.ird.unfepi.context.LocationServiceContext;
import org.ird.unfepi.model.Location;

import com.ihs.stock.api.model.ILRDailyStatus;

public class DAOILRDailyStatus {

	private Session s;

	public DAOILRDailyStatus(Session session) {
	
		this.s = session;
	}
	public void save(ILRDailyStatus ilr) {
		try {
			s.save(ilr);
		
		} catch (HibernateException e) {
			e.printStackTrace();

		}
	}

	public ILRDailyStatus getMorningStatus(int con , int loc , Date date) throws ParseException {
		
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date);
		Query query = s.createQuery("from ILRDailyStatus where day(dateToday) = :d AND MONTH(dateToday) = :m AND YEAR(dateToday) = :y AND user = :con AND location = :l AND voided= :v");
		query.setParameter("d", cal1.get(Calendar.DAY_OF_MONTH));
		query.setParameter("m", cal1.get(Calendar.MONTH)+1);
		query.setParameter("y", cal1.get(Calendar.YEAR));
		query.setParameter("con", con);
		query.setParameter("l", loc);
		query.setParameter("v", false);
		ILRDailyStatus ilr = new ILRDailyStatus();
		ilr = (ILRDailyStatus) query.uniqueResult();
		return ilr;

	}

	public void update(ILRDailyStatus ilr) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(sdf.format(new Date()));
		ilr.setdateEdited(date);

		try {
			s.update(ilr);
			
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}

	public List<ILRDailyStatus> getForYearMonthLocation(Integer locationId , Integer month , Integer year) throws InstanceAlreadyExistsException
	{
	     LocationServiceContext sc = LocationContext.getServices();
	     try
	     {
	    	 Location location = (Location) sc.getLocationService().findLocationById(locationId, false, null);
	    	 
		     Query query = s.createQuery("from ILRDailyStatus where month = :mon AND year = :yr AND location = :loc AND voided = :v");
		     query.setParameter("mon", month);
		     query.setParameter("yr", year);
		     query.setParameter("loc", location.getLocationId());
		     query.setParameter("v", false);
		     List<ILRDailyStatus> ilrDailyStatus = query.list();
		     return ilrDailyStatus;
		     
	     }
	     catch(Exception e)
	     {
	    	 e.printStackTrace();
	     }
	     finally
	     {
	    	 sc.closeSession();
	     }
	   return null;
	    
	}
	
	public List<ILRDailyStatus> existForToday(int day ,int month , int year , Integer loc)
	{
		Query query = s.createQuery("from ILRDailyStatus where day = :d AND month = :mon AND year = :yr AND location = :loc AND voided = :v");
	     query.setParameter("mon", month);
	     query.setParameter("yr", year);
	     query.setParameter("loc", loc);
	     query.setParameter("d", day);
	     query.setParameter("v", false);
	     List<ILRDailyStatus> ilrDailyStatus = query.list();
	     return ilrDailyStatus;
	}
	public List<Integer> getDistinctYears() {
		Query query = s.createQuery("select distinct year from ILRDailyStatus");
		List<Integer> years = query.list();
		return years;
	}


	public List<Integer> getDistinctMonths() {
		
		Query query = s.createQuery("select distinct month from ILRDailyStatus");

		List<Integer> months = query.list();
		return months;
	}

}
