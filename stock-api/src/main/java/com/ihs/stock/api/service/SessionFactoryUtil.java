package com.ihs.stock.api.service;

import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.ihs.stock.api.model.Item;

import org.hibernate.SessionFactory;


public class SessionFactoryUtil {

	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {

		
		Configuration configuration = new Configuration();
		configuration.configure();
		//configuration.addAnnotatedClass(Item.class);
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).buildServiceRegistry();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		//sessionFactory = new Configuration().configure().buildSessionFactory();
		return sessionFactory;

	}
	

	
}
