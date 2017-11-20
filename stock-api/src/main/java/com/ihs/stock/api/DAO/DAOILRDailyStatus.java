package com.ihs.stock.api.DAO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.ihs.stock.api.model.Consumer;
import com.ihs.stock.api.model.ILRDailyStatus;
import com.ihs.stock.api.service.SessionFactoryUtil;

public class DAOILRDailyStatus {

	private SessionFactory sf = SessionFactoryUtil.getSessionFactory();

	private Session s;

	private Transaction tx;

	public void save(ILRDailyStatus ilr) {
		try {
			s = sf.getCurrentSession();
			tx = s.beginTransaction();
			s.save(ilr);
			tx.commit();
		} catch (HibernateException e) {
			tx.rollback();
			e.printStackTrace();

		}
	}

	public ILRDailyStatus getMorningStatus(Consumer con) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(sdf.format(new Date()));

		s = sf.getCurrentSession();
		tx = s.beginTransaction();
		Query query = s.createQuery("from ILRDailyStatus where dateCreated = :d AND consumer = :con");
		query.setParameter("d", date);
		query.setParameter("con", con);
		ILRDailyStatus ilr = new ILRDailyStatus();
		ilr = (ILRDailyStatus) query.uniqueResult();
		tx.commit();
		return ilr;

	}

	public void update(ILRDailyStatus ilr) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(sdf.format(new Date()));
		ilr.setdateEdited(date);

		try {
			s = sf.getCurrentSession();
			tx = s.beginTransaction();
			s.update(ilr);
			tx.commit();

		} catch (HibernateException e) {
			tx.rollback();
			e.printStackTrace();
		}
	}

}
