package com.ihs.stock.api.DAO;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.ihs.stock.api.model.ItemAttributeType;

public class DAOItemAttributeType {
	
	private Session s;

	public DAOItemAttributeType(Session session) {
	 this.s = session;
	}
	
	public ItemAttributeType save(ItemAttributeType type)
	{
		try
		{
			s.save(type);
			}
		catch(HibernateException e)
		{
			e.printStackTrace();
		}
		return type;
	}
	
	public List<ItemAttributeType> getAllAttributesType()
	{
		Query query = s.createQuery("from ItemAttributeType ");
		List<ItemAttributeType> attributeTypes = query.list();
		return attributeTypes;
	}
	
	public List<ItemAttributeType> getById(String name)
	{
		Query query = s.createQuery("from ItemAttributeType where displayName= :i");
		query.setParameter("i", name);
		List<ItemAttributeType> attributeTypes = query.list();
		return attributeTypes;
	}

}
