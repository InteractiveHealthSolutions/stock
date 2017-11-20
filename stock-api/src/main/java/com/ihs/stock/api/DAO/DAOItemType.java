package com.ihs.stock.api.DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.ihs.stock.api.model.ItemType;
import com.ihs.stock.api.service.SessionFactoryUtil;

public class DAOItemType {
	
	private SessionFactory sf = SessionFactoryUtil.getSessionFactory();
	
	private Session s;
	
	private Transaction tx;
	
	public List<?> getAllItemTypes()
	{
		s = sf.getCurrentSession();
		tx = s.beginTransaction();
		Query query = s.createQuery("from ItemType");
		List<ItemType> itemTypes = query.list();
		List<String> typeNames = new ArrayList<String>();
		for(int i = 0 ; i < itemTypes.size() ; i++)
		{
			typeNames.add(itemTypes.get(i).getitemType());
		}
		tx.commit();
		return typeNames;
	}
	
	public ItemType getByName(String name)
	{
		s = sf.getCurrentSession();
		tx = s.beginTransaction();
		Query query = s.createQuery("from ItemType where itemType = :it");
		query.setParameter("it", name);
		ItemType itemT = (ItemType) query.uniqueResult();
		tx.commit();
		return itemT;
	}

}
