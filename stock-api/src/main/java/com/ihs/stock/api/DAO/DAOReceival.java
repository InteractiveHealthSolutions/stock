package com.ihs.stock.api.DAO;

import com.ihs.stock.api.model.Receival;
import com.ihs.stock.api.service.SessionFactoryUtil;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class DAOReceival {
	
	private SessionFactory sf = SessionFactoryUtil.getSessionFactory();
	private Session s ;
	private Transaction tx;
	
	public void save(Receival req)
	{
		try
		{
			s = sf.getCurrentSession();
			tx = s.beginTransaction();
			s.save(req);
			tx.commit();
		}
		catch(HibernateException e)
		{
			tx.rollback();
			e.printStackTrace();
		}
	}
	

}
