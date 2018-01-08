package com.ihs.stock.api.DAO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.ihs.stock.api.model.Item;
import com.ihs.stock.api.model.ItemAttribute;
import com.ihs.stock.api.model.ItemAttributeType;

public class DAOItemAttribute {

	private Session s ;
	
	public DAOItemAttribute(Session session) {
		this.s = session;
	}
	public List<ItemAttribute> getByItem(Item item)
	{
		Query query = s.createQuery("from ItemAttribute where item = :i");
		query.setParameter("i",item);
		List<ItemAttribute> attributes = query.list();
		return  attributes;
	}
	
//	public ItemAttribute createObject(ItemAttributeType iat , Item item , String value) throws ParseException
//	{
//		ItemAttribute itemat = new ItemAttribute();
//		itemat.setitem(item);
//		itemat.setitemAttributeType(iat);
//		itemat.setValue(value);
//		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		Date date = sdf.parse(sdf.format(new Date()));
//		
//		itemat.setdateCreated(date);
//		return itemat;
//		
//	}
//	public void AddColumn(ArrayList<String> columnName)
//	{
//		s = sf.getCurrentSession();
//		tx = s.beginTransaction();
//		for(int i = 0 ; i < columnName.size() ; i++)
//		{
//			columnName.get(i).replaceAll(" ", "");
//			Query query = s.createSQLQuery("Alter Table item_attribute ADD "+columnName.get(i)+" Varchar(30)");
//			//query.setParameter("col", columnName.get(i));
//			query.executeUpdate();
//		}
//		tx.commit();
//		
//	}
	
//	public List<?> getColumnNames()
//	{
//		s = sf.getCurrentSession();
//		tx = s.beginTransaction();
//		Query query = s.createSQLQuery("SELECT column_name FROM information_schema.columns WHERE table_name = :i");
//		query.setParameter("i", "item_attribute");
//		List<?> list = query.list();
//		tx.commit();
//		return list;
//	}
//	public void update(Item item , String columnName , String columnDescription)
//	{
//		s = sf.getCurrentSession();
//		tx = s.beginTransaction();
//		Query query = s.createQuery("Update ItemAttributes set " + columnName + " = :des where item = :item");
//		query.setParameter("des", columnDescription);
//		query.setParameter("item", item);
//		query.executeUpdate();
//		tx.commit();
//	}
//	public void bulkUpdate(Item item , ArrayList<String> columnName, ArrayList<String> columnDescription)
//	{
//		s = sf.getCurrentSession();
//		tx = s.beginTransaction();
//		for(int i = 0 ; i < columnName.size() ; i++)
//		{
//			
//			Query query = s.createQuery("Update ItemAttributes set "+columnName.get(i)+" = :des where item = :item");
//			//query.setParameter("col", columnName.get(i));
//			query.setParameter("des",columnDescription.get(i));
//			query.setParameter("item", item);
//			query.executeUpdate();
//			
//		}
//		tx.commit();
//		
//		
//		
//	}
	public void save(ItemAttribute iA)
	{
		try
		{
			s.save(iA);
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
		}
	}
}
