package com.ihs.stock.api.DAO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.management.InstanceAlreadyExistsException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.ihs.stock.api.model.Inventory;
import com.ihs.stock.api.model.Item;
import com.ihs.locationmanagement.api.context.Context;
import com.ihs.locationmanagement.api.context.ServiceContext;
import com.ihs.locationmanagement.api.model.Location;
import com.ihs.locationmanagement.mode.dao.hibernatedimpl.DAOLocationImpl;
import com.ihs.stock.api.service.SessionFactoryUtil;

public class DAOInventory {

	private SessionFactory sf = SessionFactoryUtil.getSessionFactory();

	private Session s;

	private Transaction tx;

	public List<Inventory> getByLocationForCurrentYear(Location loc) {
		s = sf.getCurrentSession();
		tx = s.beginTransaction();
		Query query = s.createQuery("from Inventory where consumerLocation = :locations AND year = :yr");
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);

		query.setParameter("locations", loc);
		query.setParameter("yr", year);

		List<Inventory> inventory = query.list();
		tx.commit();
		return inventory;
	}

	/*
	 * get the balance for a particular item in a particular location for the
	 * current month
	 */
	public Inventory getBalanceForLocationMonthItem(Location loc, Item item) {
		s = sf.getCurrentSession();
		tx = s.beginTransaction();
		Query query = s.createQuery(
				"from Inventory where consumerLocation = :locations AND month = :mon AND year = :yr AND item = :item AND voided = :v");

		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);

		query.setParameter("locations", loc);
		query.setParameter("item", item);
		query.setParameter("mon", month);
		query.setParameter("yr", year);
        query.setParameter("v", false);
		List<?> inventory = query.list();
		tx.commit();
		return (Inventory) inventory.get(0);
	}

	public List<?> getBalanceForLocationAllItems(Location loc) {
		s = sf.getCurrentSession();
		tx = s.beginTransaction();
		Query query = s
				.createQuery("from Inventory where consumerLocation = :locations AND month = :mon AND year = :yr");

		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);

		query.setParameter("locations", loc);
		query.setParameter("mon", month);
		query.setParameter("yr", year);

		List<?> Inventory = query.list();
		tx.commit();
		return Inventory;
	}

	public Inventory getPrevMonthInventory(Location consumerLoc , Item item) {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		s = sf.getCurrentSession();
		tx = s.beginTransaction();
		Query query = s.createQuery("from Inventory where consumerLocation = :con AND month = :mon AND year = :yr AND item = :i");
		query.setParameter("con", consumerLoc);
		query.setParameter("mon", month);
		query.setParameter("yr", year);
		query.setParameter("i", item);
		Inventory inventory = (Inventory) query.uniqueResult();
		return inventory;

	}

	public boolean prevMonthExist(Location consumerloc , Item item) {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		s = sf.getCurrentSession();
		tx = s.beginTransaction();
		Query query = s.createQuery("from Inventory where consumerLocation = :con AND month = :mon AND year = :yr AND item = :i");
		query.setParameter("con", consumerloc);
		query.setParameter("mon", month);
		query.setParameter("yr", year);
		query.setParameter("i", item);
		List<Inventory> inventory = (List<Inventory>) query.list();
		tx.commit();
		return inventory.size() > 0 ? true : false;

	}

	/*
	 * update balance in inventory for an item in a location for current month
	 * this method is triggered whenever a vaccinator's monthly balance is
	 * updated for that location
	 */
	public void save(Inventory inv) {
		try {
			s = sf.getCurrentSession();
			tx = s.beginTransaction();
			s.save(inv);
			tx.commit();
		} catch (HibernateException e) {
			tx.rollback();
			e.printStackTrace();
		}
	}

	public void update(Inventory inv) {
		try {
			s = sf.getCurrentSession();
			tx = s.beginTransaction();
			s.update(inv);
			tx.commit();
		} catch (HibernateException e) {
			tx.rollback();
			e.printStackTrace();
		}
	}

	public List<Integer> getDistinctYears() {
		s = sf.getCurrentSession();
		tx = s.beginTransaction();
		Query query = s.createQuery("select distinct year from Inventory order by year desc");
		List<Integer> years = query.list();
		tx.commit();
		return years;
	}

	public List<Item> getDistinctItems() {
		s = sf.getCurrentSession();
		tx = s.beginTransaction();
		Query query = s.createQuery("select distinct item from Inventory");
		List<Item> items = query.list();
		tx.commit();
		return items;
	}

	public List<Location> getDistinctLocation() {
		s = sf.getCurrentSession();
		tx = s.beginTransaction();
		Query query = s.createQuery("select distinct consumerLocation from Inventory");
		List<Location> loc = query.list();
		tx.commit();
		return loc;
	}

	public List<Integer> getDistinctMonths() {
		s = sf.getCurrentSession();
		tx = s.beginTransaction();

		Query query = s.createQuery("select distinct month from Inventory");

		List<Integer> months = query.list();
		tx.commit();
		return months;
	}

	public List<?> getAllMonthsInventory(int year) {
		s = sf.getCurrentSession();
		tx = s.beginTransaction();
		Query query = s.createQuery("from Inventory where year= :yr order by year");
		query.setParameter("yr", year);
		List<Inventory> inv = query.list();
		tx.commit();
		return inv;
	}
/*
	public List<?> aggregateBalanceYearly(int year) {
		s = sf.getCurrentSession();
		tx = s.beginTransaction();
		Query query = s.createQuery("from Inventory where year= :yr");
		query.setParameter("yr", year);
		List<Inventory> inv = query.list();
		tx.commit();
		Integer initial = 0;
		Integer balance = 0;
		Integer total = 0;

		for (int i = 0; i < inv.size(); i++) {
			initial = initial + inv.get(i).getcurrentMonthContainers();
			balance = balance + inv.get(i).getprevMonthBalance();
			total = total + inv.get(i).gettotalContainers();
		}
		List<Integer> stats = new ArrayList<Integer>();
		stats.add(initial);
		stats.add(balance);
		stats.add(total);
		return stats;

	}
*/
	public List<?> getCurrentMonthItems(Location loc) {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(cal.MONTH) + 1;
		int year = cal.get(cal.YEAR);
		s = sf.getCurrentSession();
		tx = s.beginTransaction();
		Query query = s
				.createQuery("select item from Inventory where consumer_location = :l AND month = :mon AND year = :yr");
		query.setParameter("l", loc);
		query.setParameter("mon", month);
		query.setParameter("yr", year);
		List<Item> item = query.list();
		tx.commit();

		return item;

	}

	public void updateInventory(Location loc, Item item, int vmsTotalVialsCount) {

		Inventory inventory = getBalanceForLocationMonthItem(loc, item);
		int newBalance = inventory.gettotalContainers() - vmsTotalVialsCount;
		inventory.settotalContainers(newBalance);
		try {
			s = sf.getCurrentSession();
			tx = s.beginTransaction();
			s.update(inventory);
			tx.commit();

		} catch (HibernateException e) {

			tx.rollback();
			e.printStackTrace();
		}

	}

	public boolean inventoryForMonthExist(Item item, Location loc) throws InstanceAlreadyExistsException {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		DAOItem itemDAO = new DAOItem();
		s = sf.getCurrentSession();
		tx = s.beginTransaction();
		Query query = s.createQuery(
				"from Inventory where consumerLocation = :l AND item = :i AND month = :mon AND year = :yr");
		query.setParameter("l", loc);
		query.setParameter("i", item);
		query.setParameter("mon", month);
		query.setParameter("yr", year);
		List<?> list = query.list();
		tx.commit();
		return list.size() > 0 ? true : false;

	}
	
	public Inventory getPrevForSameMonth(Item item , Location loc) throws InstanceAlreadyExistsException
	{
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		DAOItem itemDAO = new DAOItem();
		s = sf.getCurrentSession();
		tx = s.beginTransaction();
		Query query = s.createQuery(
				"FROM Inventory where item = :i AND consumer_location = :l AND month = :mon AND year = :yr AND day ="+ 
"(Select max(day) from Inventory where item = :i AND consumer_location = :l AND month = :mon AND year = :yr )");
		query.setParameter("l", loc);
		query.setParameter("i", item);
		query.setParameter("mon", month);
		query.setParameter("yr", year);
		List<?> list = query.list();
		tx.commit();
		return (Inventory) list.get(0);
	}

	public List<Inventory> getForLocationMonthYear(Location loc, int month, int year) {
		s = sf.getCurrentSession();
		tx = s.beginTransaction();
		Query query = s.createQuery("from Inventory where consumerLocation = :l AND month = :m AND year = :yr order by year");
		query.setParameter("l", loc);
		query.setParameter("m", month);
		query.setParameter("yr", year);
		List<Inventory> inventory = query.list();
		tx.commit();
		return inventory;

	}

	public List<Inventory> getForYearMonth(int month, int year) {
		s = sf.getCurrentSession();
		tx = s.beginTransaction();
		Query query = s.createQuery("from Inventory where month = :m AND year = :yr order by year");
		query.setParameter("m", month);
		query.setParameter("yr", year);
		List<Inventory> inventory = query.list();
		tx.commit();
		return inventory;
	}

	public List<Inventory> getForYearLocation(Location loc, int year) {
		s = sf.getCurrentSession();
		tx = s.beginTransaction();
		Query query = s.createQuery("from Inventory where consumerLocation = :loc AND year = :yr order by year");
		query.setParameter("loc", loc);
		query.setParameter("yr", year);
		List<Inventory> inventory = query.list();
		tx.commit();
		return inventory;
	}

	public List<Inventory> getForMonthLocation(Location loc, int month) {
		s = sf.getCurrentSession();
		tx = s.beginTransaction();
		Query query = s.createQuery("from Inventory where consumerLocation = :loc AND month = :mon order by month");
		query.setParameter("loc", loc);
		query.setParameter("mon", month);
		List<Inventory> inventory = query.list();
		tx.commit();
		return inventory;
	}

	public List<Inventory> getForLocation(Location loc) {
		s = sf.getCurrentSession();
		tx = s.beginTransaction();
		Query query = s.createQuery("from Inventory where consumerLocation = :loc order by year desc");
		query.setParameter("loc", loc);
		List<Inventory> inventory = query.list();
		tx.commit();
		return inventory;
	}

	public List<Inventory> getForMonth(int month) {
		s = sf.getCurrentSession();
		tx = s.beginTransaction();
		Query query = s.createQuery("from Inventory where month = :mon order by month");
		query.setParameter("mon", month);
		List<Inventory> inventory = query.list();
		tx.commit();
		return inventory;
	}
	
	public List<Inventory> getAllInventory()
	{
		s = sf.getCurrentSession();
		tx = s.beginTransaction();
		Query query = s.createQuery("from Inventory where dateVoided = null");
		List<Inventory> inventories = query.list();
		tx.commit();
		return inventories;
	}
	
	public List<Inventory> getById(int id) {
		s = sf.getCurrentSession();
		tx = s.beginTransaction();
		Query query = s.createQuery("from Inventory where inventoryId = :i");
		query.setParameter("i", id);
		List<Inventory> inventory = query.list();
		tx.commit();
		return inventory;
	}
	

}
