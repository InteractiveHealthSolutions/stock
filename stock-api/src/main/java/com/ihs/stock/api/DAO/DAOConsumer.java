package com.ihs.stock.api.DAO;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.ihs.stock.api.model.Consumer;

public class DAOConsumer {

	private Session s;
	
	public DAOConsumer(Session session)
	{
		this.s = session;
	}
	public Consumer getById(int id) {
	
		Query query = s.createQuery("from Consumer where consumerId = :i");
		query.setParameter("i", id);
		Consumer con = (Consumer) query.uniqueResult();
		
		return con;

	}
	
	public Consumer save(Consumer con)
	{
		try
		{
			
			s.save(con);
		
		}
		catch(HibernateException e)
		{
			
			e.printStackTrace();
		}
		return con;
	}
	
	public List<Consumer> getAllConsumers()
	{
		Query query = s.createQuery("from Consumer where dateVoided = null");
		List<Consumer> consumerList = query.list();
		return consumerList;
	}
	
	public Consumer update(Consumer con)
	{
		try
		{
			s.update(con);
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
		}
		return con;
	}

}
