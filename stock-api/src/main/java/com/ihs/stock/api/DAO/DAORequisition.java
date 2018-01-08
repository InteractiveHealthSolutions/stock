package com.ihs.stock.api.DAO;

import java.util.Calendar;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.ird.unfepi.model.Location;

import com.ihs.stock.api.model.Item;
import com.ihs.stock.api.model.Requisition;

public class DAORequisition {

	private Session s;
	
	public DAORequisition(Session session) {
	
		this.s = session;
	}
	public Requisition save(Requisition req)
	{
		try
		{
			s.save(req);
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
		}
		return req;
	}
	
	public Requisition getById(Integer id)
	{
		org.hibernate.Query query = s.createQuery("from Requisition where requisitionId = :req");
		query.setParameter("req", id);
		Requisition req = (Requisition) query.uniqueResult();
		return req;
		
	}
	public List<Requisition> getAll()
	{
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH)+1;
		int year = cal.get(Calendar.YEAR);
		Query query = s.createQuery("from Requisition where month=:mon AND year=:yr ");
		query.setParameter("mon",month);
		query.setParameter("yr", year);
		List<Requisition> req = query.list();
		return req;
	
	}
	
	public List<Requisition> getForLocation(Integer loc)
	{
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH)+1;
		int year = cal.get(Calendar.YEAR);
		Query query = s.createQuery("from Requisition where month=:mon AND year=:yr and requisitionLocation = :loc ");
		query.setParameter("mon",month);
		query.setParameter("yr", year);
		query.setParameter("loc", loc);
		List<Requisition> req = query.list();
		return req;
	
	}
	public List<Requisition> getForLocationUnApproved(Integer loc)
	{
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH)+1;
		int year = cal.get(Calendar.YEAR);
		Query query = s.createQuery("from Requisition where month=:mon AND year=:yr and requisitionLocation = :loc and approvalStatus != :as ");
		query.setParameter("mon",month);
		query.setParameter("yr", year);
		query.setParameter("loc", loc);
		query.setParameter("as", "Approved");
		
		List<Requisition> req = query.list();
		return req;
	
	}
	public List<Requisition> getForLocationPending(Integer loc)
	{
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH)+1;
		int year = cal.get(Calendar.YEAR);
		Query query = s.createQuery("from Requisition where month=:mon AND year=:yr and requisitionLocation = :loc and approvalStatus = :as ");
		query.setParameter("mon",month);
		query.setParameter("yr", year);
		query.setParameter("loc", loc);
		query.setParameter("as", "Pending");
		
		List<Requisition> req = query.list();
		return req;
	
	}
	public List<Requisition> getForLocationAllMonths(Integer loc)
	{
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH)+1;
		int year = cal.get(Calendar.YEAR);
		Query query = s.createQuery("from Requisition where requisitionLocation = :loc");
		query.setParameter("loc", loc);
		List<Requisition> req = query.list();
		return req;
	
	}
	
	
	public Requisition getForLocationItem(Integer loc , Integer item)
	{
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH)+1;
		int year = cal.get(Calendar.YEAR);
		Query query = s.createQuery("from Requisition where requisitionLocation = :loc AND item = :i AND "
				+ "month = :mon AND year = :yr AND voided = :v");
		query.setParameter("loc", loc);
		query.setParameter("i", item);
		query.setParameter("mon", month);
		query.setParameter("yr", year);
		query.setParameter("v", false);
		Requisition req = (Requisition) query.uniqueResult();
		return req == null? null:req;
		
	}
	


	public Requisition update(Requisition requisition) {
		try{
			s.update(requisition);
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
		}
		return requisition;
		
	}

	
}
