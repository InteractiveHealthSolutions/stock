package com.ihs.stock.api.context;

import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;



import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;


public class SessionFactoryUtil {

	private static SessionFactory sessionFactory;

	public synchronized static SessionFactory getSessionFactory(Properties properties , String fileName) {

		Configuration configuration = new Configuration();
		if(properties!=null)
		{
			configuration.setProperties(properties);
		}
	    if(fileName != null)
	    {
	    	configuration.configure(fileName);
	    }
	    else
	    {
	    	configuration.configure();
	    }
		//configuration.addAnnotatedClass(Item.class);
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).buildServiceRegistry();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		//sessionFactory = new Configuration().configure().buildSessionFactory();
		return sessionFactory;

	}
	
	public static Session getNewSession() {
		return sessionFactory.openSession();
	}
	
	public static void setServiceContext(SessionFactory sf)
	{
		sessionFactory = sf;
	}
	public static ServiceContextStock getServiceContext()
	{
		return new ServiceContextStock(sessionFactory);
	}

	
}
