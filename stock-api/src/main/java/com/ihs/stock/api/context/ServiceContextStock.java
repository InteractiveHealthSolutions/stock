package com.ihs.stock.api.context;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.service.jdbc.connections.internal.DatasourceConnectionProviderImpl;

import com.ihs.stock.api.DAO.*;

public class ServiceContextStock {

	private Session session;

	private Transaction transaction;
	
	public DAOConsumer consumerDAO;
	
	public DAODailyStats dailyStatsDAO;
	
	public DAOILRDailyStatus ilrDailyStatusDAO;
	
	public DAOInventory inventoryDAO;
	
	public DAOItem itemDAO;
	
	public DAOItemAttribute itemAttributeDAO;
	
	public DAOItemAttributeType itemAttributeTypeDAO;
	
	public DAOItemType itemTypeDAO;

	public DAORequisition requisitionDAO;



	ServiceContextStock(SessionFactory sf)
	{
		session =sf.openSession();
		transaction = session.beginTransaction();
		consumerDAO = new DAOConsumer(session);
		dailyStatsDAO = new DAODailyStats(session);
		ilrDailyStatusDAO = new DAOILRDailyStatus(session);
		inventoryDAO = new DAOInventory(session);
		itemDAO = new DAOItem(session);
		itemAttributeDAO = new DAOItemAttribute(session);
		itemAttributeTypeDAO = new DAOItemAttributeType(session);
		itemTypeDAO = new DAOItemType(session);
		requisitionDAO = new DAORequisition(session);
		
		
		
	}
	public void beginTransaction()
	{
		if (transaction == null || !transaction.isActive())
		{
			transaction = session.beginTransaction();
		}
	}
	
	public void commitTransaction()
	{
		transaction.commit();
	}

	public void rollbackTransaction()
	{
		if (transaction != null)
		{
			transaction.rollback();
		}
	}

	public void closeSession()
	{
		try
		{
			session.close();
		}
		catch (Exception e)
		{
		}
	}
}
