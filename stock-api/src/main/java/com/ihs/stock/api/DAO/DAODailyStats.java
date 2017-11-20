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
import com.ihs.locationmanagement.api.model.Location;
import com.ihs.stock.api.service.SessionFactoryUtil;

public class DAODailyStats {

	private Session s;
	private SessionFactory sf = SessionFactoryUtil.getSessionFactory();
	private Transaction tx;

	public void save(DailyStats ds) {
		try {
			s = sf.getCurrentSession();
			tx = s.beginTransaction();
			s.save(ds);
			tx.commit();
		} catch (HibernateException e) {
			tx.rollback();
			e.printStackTrace();
		}
	}

	public List<DailyStats> getMorningEntry(Item item, Consumer con) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(sdf.format(new Date()));
		s = sf.getCurrentSession();
		tx = s.beginTransaction();

		Query query = s.createQuery("from DailyStats where item = :i AND consumer = :v AND dateCreated = :d");
		query.setParameter("i", item);
		query.setParameter("v", con);
		query.setParameter("d", date);
		List<DailyStats> dailyStats = query.list();
		tx.commit();
		return (List<DailyStats>) dailyStats;

	}

	public void update(DailyStats dailyStats) {
		try {
			s = sf.getCurrentSession();
			tx = s.beginTransaction();
			s.update(dailyStats);
			tx.commit();
		} catch (HibernateException e) {
			tx.rollback();
			e.printStackTrace();
		}
	}

	public List<?> getDailyStatsForToday(int vac) throws ParseException {
		s = sf.getCurrentSession();
		tx = s.beginTransaction();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		Date date = sdf.parse(sdf.format(new Date()));
		Query query = s.createQuery("from DailyStats where consumer.consumerId= :v AND dateCreated= :d ");
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

	public boolean existSumWastedQuantity(Location loc, Item item) {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		s = sf.getCurrentSession();
		tx = s.beginTransaction();

		Query query = s.createQuery(
				"from DailyStats where consumer.location= :l AND item = :i" + "  AND month = :mon AND year = :yr");
		query.setParameter("l", loc);
		query.setParameter("i", item);
		query.setParameter("mon", month);
		query.setParameter("yr", year);
		List wastedSum = query.list();
		tx.commit();
		return wastedSum.size() > 0 ? true : false;

	}

	public String getSumWastedQuantity(Location loc, Item item) {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		s = sf.getCurrentSession();
		tx = s.beginTransaction();
		Query query = s
				.createQuery("select sum(wastedQuantity) from DailyStats where consumer.location= :l AND item = :i"
						+ " AND month = :mon AND year = :yr");
		query.setParameter("l", loc);
		query.setParameter("i", item);
		query.setParameter("mon", month);
		query.setParameter("yr", year);
		Long wastedSum = (Long) query.uniqueResult();
		tx.commit();
		return wastedSum.toString();
	}

	public String getSumUsedQuantity(Location loc, Item item) {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		s = sf.getCurrentSession();
		tx = s.beginTransaction();
		Query query = s.createQuery("select sum(usedQuantity) from DailyStats where consumer.location= :l AND "
				+ "item = :i AND month = :mon AND year = :yr");
		query.setParameter("l", loc);
		query.setParameter("i", item);
		query.setParameter("mon", month);
		query.setParameter("yr", year);
		Long usedSum = (Long) query.uniqueResult();
		tx.commit();
		return usedSum.toString();
	}

	public boolean existSumUsedQuantity(Location loc, Item item) {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		s = sf.getCurrentSession();
		tx = s.beginTransaction();
		Query query = s.createQuery(
				"from DailyStats where consumer.location= :l AND item = :i AND month = :mon AND year = :yr");
		query.setParameter("l", loc);
		query.setParameter("i", item);
		query.setParameter("mon", month);
		query.setParameter("yr", year);
		List usedSum = query.list();
		tx.commit();
		return usedSum.size() > 0 ? true : false;
	}
	
	public DailyStats getDailyStatsbyId(int id)
	{
		s = sf.getCurrentSession();
		tx = s.beginTransaction();
		Query query =s.createQuery("from DailyStats where dailyStats = :i");
		query.setParameter("i", id);
		DailyStats ds = (DailyStats) query.uniqueResult();
		tx.commit();
		return ds;
	}
	
	public List<DailyStats> getAllDailyStats()
	{
		s = sf.getCurrentSession();
		tx = s.beginTransaction();
		Query query = s.createQuery("from DailyStats where dateVoided = null");
		List<DailyStats> dailyStats = query.list();
		tx.commit();
		return dailyStats;
	}
}
