package com.ihs.stock.api.DAO;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.ihs.stock.api.model.ItemAttributeType;
import com.ihs.stock.api.service.SessionFactoryUtil;

public class DAOItemAttributeType {
	
	private SessionFactory sf = SessionFactoryUtil.getSessionFactory();
	private Session s;
	private Transaction tx;
	
	public ItemAttributeType save(ItemAttributeType type)
	{
		try
		{
			s = sf.getCurrentSession();
			tx = s.beginTransaction();
			s.save(type);
			tx.commit();
		}
		catch(HibernateException e)
		{
			tx.rollback();
			e.printStackTrace();
		}
		return type;
	}
	
	public List<ItemAttributeType> getAllAttributesType()
	{
		s = sf.getCurrentSession();
		tx = s.beginTransaction();
		Query query = s.createQuery("from ItemAttributeType ");
		List<ItemAttributeType> attributeTypes = query.list();
		tx.commit();
		return attributeTypes;
	}
	
	public List<ItemAttributeType> getById(String name)
	{
		s = sf.getCurrentSession();
		tx = s.beginTransaction();
		Query query = s.createQuery("from ItemAttributeType where displayName= :i");
		query.setParameter("i", name);
		List<ItemAttributeType> attributeTypes = query.list();
		tx.commit();
		return attributeTypes;
	}

}
