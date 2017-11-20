package com.ihs.stock.api.DAO;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.ihs.stock.api.model.Consumer;
import com.ihs.stock.api.model.Item;

import com.ihs.stock.api.model.MonthlyStats;
import com.ihs.stock.api.service.SessionFactoryUtil;

public class DAOMonthlyStats {

	private SessionFactory sf = SessionFactoryUtil.getSessionFactory();
	private Session s;
	private Transaction tx;
	/*
	 * this method checks if vaccinator_monthly stats table contains any given month
	 * record of a particular vaccinator for a particular item
	 */
	public boolean ifExist(Consumer con , int month , int year , Item vaccine)
	{
		s = sf.getCurrentSession();
		tx = s.beginTransaction();
		Query query = s.createQuery("from MonthlyStats where month = :mon AND year= :yr AND consumer = :v AND item = :vac");
		query.setParameter("mon", month);
		query.setParameter("yr", year);
		query.setParameter("v", con);
		query.setParameter("vac", vaccine);
		List<?> vacMonStats = query.list();
		tx.commit();
		return vacMonStats.size() > 0 ? true : false;
	}
	
	/*
	 * this method return current month balance of a vaccinator for a particular item 
	 */
	public MonthlyStats getMonthBalance(Consumer con , int month , int year , Item vaccine )
	{
		s = sf.getCurrentSession();
		tx = s.beginTransaction();
		Query query = s.createQuery("from MonthlyStats where month = :mon AND year= :yr AND consumer = :v AND item = :vac");
		query.setParameter("mon", month);
		query.setParameter("yr", year);
		query.setParameter("v", con);
		query.setParameter("vac", vaccine);
		List<MonthlyStats> vacMonStats =  (List<MonthlyStats>)query.list();
		tx.commit();
		return vacMonStats.get(0);
		
	}
	/*
	 * this method returns current month/ any month record of a vaccinator for all items
	 */
	public List<MonthlyStats> getMonthlyRecordForAllItems(Consumer con , int month , int year)
	{
		s = sf.getCurrentSession();
		tx = s.beginTransaction();
		Query query = s.createQuery("from MonthlyStats where month = :mon AND year = :yr AND consumer = :v");
		query.setParameter("mon", month);
		query.setParameter("yr", year);
		query.setParameter("v", con);
		List<MonthlyStats> vacMonStats = (List<MonthlyStats>) query.list();
		tx.commit();
		if(vacMonStats.size() == 0)
		{
			throw new HibernateException("You havent entered any monthly stats for current month");
		}
		return vacMonStats;
		
	}
	
	public void save(MonthlyStats vms)
	{
		try{
			s = sf.getCurrentSession();
			tx = s.beginTransaction();
		    s.save(vms);
		    tx.commit();
		}
		catch(HibernateException e)
		{
			tx.rollback();
			e.printStackTrace();
		}	
		
	}
	
	public void update(MonthlyStats monthlyStats)
	{
		try
		{
			s = sf.getCurrentSession();
			tx = s.beginTransaction();
			s.update(monthlyStats);
			tx.commit();
		}
		catch(HibernateException e)
		{
			tx.rollback();
			e.printStackTrace();
		}
	}
	
	public MonthlyStats getById(int id)
	{
		s = sf.getCurrentSession();
		tx = s.beginTransaction();
		Query query = s.createQuery("from MonthlyStats where monId = :i");
		query.setParameter("i", id);
		MonthlyStats ms = (MonthlyStats) query.uniqueResult();
		tx.commit();
		return ms;
		
	}
	
	public List<MonthlyStats> getAllMonthLyStats()
	{
		 s = sf.getCurrentSession();
		 tx = s.beginTransaction();
		 Query query = s.createQuery("from MonthlyStats where dateVoided = null");
		 List<MonthlyStats> list = query.list();
		 tx.commit();
		 return list;
	}
}
