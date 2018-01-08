package com.ihs.stock.api.DAO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.parsing.QualifierEntry;

import com.ihs.stock.api.model.Consumer;
import com.ihs.stock.api.model.DailyStats;
import com.ihs.stock.api.model.Item;

public class DAODailyStats {

	private Session s;

	public DAODailyStats(Session session)
	{
		this.s = session;
	}
	public void save(DailyStats ds) {
		try {
		s.save(ds);
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}

	public List<DailyStats> getMorningEntry(Integer item, Integer con , Date date) throws ParseException {
		
		Query query = s.createQuery("from DailyStats where item = :i AND user = :v AND dateToday = :d");
		query.setParameter("i", item);
		query.setParameter("v", con);
		query.setParameter("d", date);
		List<DailyStats> dailyStats = query.list();
		return (List<DailyStats>) dailyStats;

	}

	public void update(DailyStats dailyStats) {
		try {
			s.update(dailyStats);
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}

	public List<?> getDailyStatsForToday(int vac , Date date) throws ParseException {
		
		Query query = s.createQuery("from DailyStats where user= :v AND dateToday= :d ");
		query.setParameter("v", vac);
		query.setParameter("d", date);
		List<DailyStats> dailyStats = query.list();
		// for(int i = 0 ; i < dailyStats.size() ; i++)
		// {
		// if(dailyStats.get(i).getdateCreated().getDay() != date.getDay() &&
		// dailyStats.get(i).getdateCreated().getMonth() != date.getMonth()+1)
		// {
		// dailyStats.remove(i);
		// }
		// }
		if (dailyStats.size() == 0) {
			throw new HibernateException("You haven't entered any daily statistics for today");
		}
		return dailyStats;

	}

	public boolean existSumWastedQuantity(Integer loc, Integer item) {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		Query query = s.createQuery(
				"from DailyStats where userLocation= :l AND item = :i" + "  AND month = :mon AND year = :yr");
		query.setParameter("l", loc);
		query.setParameter("i", item);
		query.setParameter("mon", month);
		query.setParameter("yr", year);
		List wastedSum = query.list();
		return wastedSum.size() > 0 ? true : false;

	}

	public String getSumWastedQuantity(Integer loc, Integer item) {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		Query query = s
				.createQuery("select sum(wastedQuantity) from DailyStats where userLocation= :l AND item = :i"
						+ " AND month = :mon AND year = :yr");
		query.setParameter("l", loc);
		query.setParameter("i", item);
		query.setParameter("mon", month);
		query.setParameter("yr", year);
		Long wastedSum = (Long) query.uniqueResult();
		return wastedSum.toString();
	}

	public String getSumUsedQuantity(Integer loc, Integer item) {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		Query query = s.createQuery("select sum(usedQuantity) from DailyStats where userLocation= :l AND "
				+ "item = :i AND month = :mon AND year = :yr");
		query.setParameter("l", loc);
		query.setParameter("i", item);
		query.setParameter("mon", month);
		query.setParameter("yr", year);
		Long usedSum = (Long) query.uniqueResult();
		return usedSum.toString();
	}

	public boolean existSumUsedQuantity(Integer loc, Integer item) {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		Query query = s.createQuery(
				"from DailyStats where userLocation= :l AND item = :i AND month = :mon AND year = :yr");
		query.setParameter("l", loc);
		query.setParameter("i", item);
		query.setParameter("mon", month);
		query.setParameter("yr", year);
		List usedSum = query.list();
		return usedSum.size() > 0 ? true : false;
	}
	
	public DailyStats getDailyStatsbyId(int id)
	{
		Query query =s.createQuery("from DailyStats where dailyStats = :i");
		query.setParameter("i", id);
		DailyStats ds = (DailyStats) query.uniqueResult();
		return ds;
	}
	
	public List<DailyStats> getAllDailyStats()
	{
		Query query = s.createQuery("from DailyStats where dateVoided = null");
		List<DailyStats> dailyStats = query.list();
		return dailyStats;
	}
}
