package com.ihs.stock.api.DAO;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.ihs.stock.api.model.Consumer;
import com.ihs.stock.api.service.SessionFactoryUtil;

public class DAOConsumer {

	private Session s;
	private SessionFactory sf = SessionFactoryUtil.getSessionFactory();
	Transaction tx;

	public Consumer getById(int id) {
		s = sf.getCurrentSession();
		tx = s.beginTransaction();
		Query query = s.createQuery("from Consumer where consumerId = :i");
		query.setParameter("i", id);
		Consumer con = (Consumer) query.uniqueResult();
		tx.commit();
		return con;

	}
	
	public Consumer save(Consumer con)
	{
		try
		{
			s = sf.getCurrentSession();
			tx = s.beginTransaction();
			s.save(con);
			tx.commit();
		}
		catch(HibernateException e)
		{
			tx.rollback();
			e.printStackTrace();
		}
		return con;
	}
	
	public List<Consumer> getAllConsumers()
	{
		s = sf.getCurrentSession();
		tx = s.beginTransaction();
		Query query = s.createQuery("from Consumer where dateVoided = null");
		List<Consumer> consumerList = query.list();
		tx.commit();
		return consumerList;
	}
	
	public Consumer update(Consumer con)
	{
		try
		{
			s = sf.getCurrentSession();
			tx = s.beginTransaction();
			s.update(con);
			tx.commit();
		}
		catch(HibernateException e)
		{
			tx.rollback();
			e.printStackTrace();
		}
		return con;
	}

}
