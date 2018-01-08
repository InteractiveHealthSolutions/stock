package com.ihs.stock.api.DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.ihs.stock.api.model.ItemType;

public class DAOItemType {
	
	private Session s;
	
	public DAOItemType(Session session) {
		this.s = session;
		}
	public List<?> getAllItemTypes()
	{
		Query query = s.createQuery("from ItemType");
		List<ItemType> itemTypes = query.list();
		List<String> typeNames = new ArrayList<String>();
		for(int i = 0 ; i < itemTypes.size() ; i++)
		{
			typeNames.add(itemTypes.get(i).getitemType());
		}
		return typeNames;
	}
	
	public ItemType getByName(String name)
	{
		Query query = s.createQuery("from ItemType where itemType = :it");
		query.setParameter("it", name);
		ItemType itemT = (ItemType) query.uniqueResult();
		return itemT;
	}

}
