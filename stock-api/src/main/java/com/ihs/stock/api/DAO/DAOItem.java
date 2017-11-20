package com.ihs.stock.api.DAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate4.HibernateTemplate;

import com.ihs.stock.api.model.Item;
import com.ihs.stock.api.service.SessionFactoryUtil;

public class DAOItem {
	
	private Session s ;
	private SessionFactory sf = SessionFactoryUtil.getSessionFactory();
	private Transaction tx;
	
	public Item getByName(String itemName)
	{
		s = sf.getCurrentSession();
		tx = s.beginTransaction();
		Query query = s.createQuery("from Item where name = :in");
		query.setParameter("in", itemName);
		Item item = (Item) query.uniqueResult();
		tx.commit();
		return item != null? item:null;
	}
	
	public Item getById(int itemId)
	{
		s = sf.getCurrentSession();
		tx = s.beginTransaction();
		Query query = s.createQuery("from Item where itemId = :id ");
		query.setParameter("id", itemId);
		Item item = (Item) query.uniqueResult();
		tx.commit();
		return item != null? item:null;
	}
	
	public List<?> getAllItems()
	{
		s = sf.getCurrentSession();
		tx = s.beginTransaction();
		Query query = s.createQuery("from Item");
		List<Item> items = query.list();
		tx.commit();
		List<String> itemName = new ArrayList<String>();
		for(int i = 0 ; i < items.size() ; i++)
		{
			
			itemName.add(items.get(i).getname());
		}
		
		return itemName;
		
	}
	public List<?> getallItems()
	{
		s = sf.getCurrentSession();
		tx = s.beginTransaction();
		Date date = null;
		Query query = s.createQuery("from Item where dateVoided = null");
		
		List<Item> items = query.list();
		tx.commit();
		
		return items;
		
	}
	
	public boolean exist(String itemName)
	{
		s = sf.getCurrentSession();
		tx = s.beginTransaction();
		Query query = s.createQuery("from Item where name = :in");
		query.setParameter("in", itemName);
		List<?> item = query.list();
		tx.commit();
		return item.size() > 0 ? true : false;
		
		
	}
	
	public void save(Item item)
	{
		try
		{
			s = sf.getCurrentSession();
			tx = s.beginTransaction();
			s.save(item);
			tx.commit();
		}
		catch(HibernateException e)
		{
			tx.rollback();
			e.printStackTrace();
		}
	}
	
	public void update(Item item)
	{
		try{
			s = sf.getCurrentSession();
			tx = s.beginTransaction();
			s.update(item);
			tx.commit();
		}
		catch(HibernateException e)
		{
			tx.rollback();
			e.printStackTrace();
		}
	}
	
	public void delete(Item item)
	{
		try {
			s = sf.getCurrentSession();
			tx = s.beginTransaction();
			s.delete(item);
			tx.commit();
		}
		catch(HibernateException e)
		{
			tx.rollback();
			e.printStackTrace();
		}
	}

}
