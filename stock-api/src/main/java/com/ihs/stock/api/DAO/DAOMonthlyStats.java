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

	private Session s;
	
	public DAOMonthlyStats(Session s) {
		this.s = s;
	}
	/*
	 * this method checks if vaccinator_monthly stats table contains any given month
	 * record of a particular vaccinator for a particular item
	 */
	public boolean ifExist(Consumer con , int month , int year , Item vaccine)
	{
		Query query = s.createQuery("from MonthlyStats where month = :mon AND year= :yr AND consumer = :v AND item = :vac");
		query.setParameter("mon", month);
		query.setParameter("yr", year);
		query.setParameter("v", con);
		query.setParameter("vac", vaccine);
		List<?> vacMonStats = query.list();
		return vacMonStats.size() > 0 ? true : false;
	}
	
	/*
	 * this method return current month balance of a vaccinator for a particular item 
	 */
	public MonthlyStats getMonthBalance(Integer con , int month , int year , Integer vaccine )
	{
		Query query = s.createQuery("from MonthlyStats where MONTH(receivalDate) = :mon AND YEAR(receivalDate)= :yr AND user = :v AND item = :vac");
		query.setParameter("mon", month);
		query.setParameter("yr", year);
		query.setParameter("v", con);
		query.setParameter("vac", vaccine);
		List<MonthlyStats> vacMonStats =  (List<MonthlyStats>)query.list();
		
		return vacMonStats.size() > 0 ? vacMonStats.get(0):null;
		
	}
	public boolean existMonthBalance(Integer con , int month , int year , Integer vaccine )
	{
		Query query = s.createQuery("from MonthlyStats where MONTH(receivalDate) = :mon AND YEAR(receivalDate)= :yr AND user = :v AND item = :vac");
		query.setParameter("mon", month);
		query.setParameter("yr", year);
		query.setParameter("v", con);
		query.setParameter("vac", vaccine);
		List<MonthlyStats> vacMonStats =  (List<MonthlyStats>)query.list();
		
		return vacMonStats.size() > 0 ? true:false;
		
	}
	/*
	 * this method returns current month/ any month record of a vaccinator for all items
	 */
	public List<MonthlyStats> getMonthlyRecordForAllItems(Consumer con , int month , int year)
	{
		Query query = s.createQuery("from MonthlyStats where month = :mon AND year = :yr AND consumer = :v");
		query.setParameter("mon", month);
		query.setParameter("yr", year);
		query.setParameter("v", con);
		List<MonthlyStats> vacMonStats = (List<MonthlyStats>) query.list();
		if(vacMonStats.size() == 0)
		{
			throw new HibernateException("You havent entered any monthly stats for current month");
		}
		return vacMonStats;
		
	}
	
	public void save(MonthlyStats vms)
	{
		s.save(vms);
	}
	
	public void update(MonthlyStats monthlyStats)
	{
		s.saveOrUpdate(monthlyStats);
	}
	
	public MonthlyStats getById(int id)
	{
		Query query = s.createQuery("from MonthlyStats where monId = :i");
		query.setParameter("i", id);
		MonthlyStats ms = (MonthlyStats) query.uniqueResult();
		return ms;
		
	}
	
	public List<MonthlyStats> getAllMonthLyStats()
	{
		 Query query = s.createQuery("from MonthlyStats where dateVoided = null");
		 List<MonthlyStats> list = query.list();
		 return list;
	}
}
