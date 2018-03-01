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

public class DAOItem {
	
	private Session s ;
	
	public DAOItem(Session session)
	{
		this.s = session;
	}
	public Item getByName(String itemName)
	{
		Query query = s.createQuery("from Item where name = :in");
		query.setParameter("in", itemName);
		Item item = (Item) query.uniqueResult();
		return item != null? item:null;
	}
	
	public Item getById(Integer itemId)
	{
		Query query = s.createQuery("from Item where itemId = :id ");
		query.setParameter("id", itemId);
		Item item = (Item) query.uniqueResult();
		return item != null? item:null;
	}
	
	public List<?> getAllItemNames()
	{
		Query query = s.createQuery("from Item");
		List<Item> items = query.list();
		List<String> itemName = new ArrayList<String>();
		for(int i = 0 ; i < items.size() ; i++)
		{
			
			itemName.add(items.get(i).getname());
		}
		return itemName;
		
	}
	public List<?> getallItems()
	{
	
		Query query = s.createQuery("from Item where dateVoided = null and itemType=1");
		
		List<Item> items = query.list();
		
		return items;
		
	}
	
	public boolean exist(String itemName)
	{
		Query query = s.createQuery("from Item where name = :in");
		query.setParameter("in", itemName);
		List<?> item = query.list();
		return item.size() > 0 ? true : false;
	}
	
	public void save(Item item)
	{
		try
		{
			s.save(item);
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
		}
	}
	
	public void update(Item item)
	{
		try{
			s.update(item);
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
		}
	}
	
	public void delete(Item item)
	{
		try {
			s.delete(item);
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
		}
	}

}
