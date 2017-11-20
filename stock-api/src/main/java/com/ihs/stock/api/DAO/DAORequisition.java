package com.ihs.stock.api.DAO;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


import com.ihs.stock.api.model.Requisition;
import com.ihs.stock.api.service.SessionFactoryUtil;

public class DAORequisition {

	private SessionFactory sf = SessionFactoryUtil.getSessionFactory();
	
	private Session s;
	
	private Transaction tx;
	
	public Requisition save(Requisition req)
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
		return req;
	}
}
