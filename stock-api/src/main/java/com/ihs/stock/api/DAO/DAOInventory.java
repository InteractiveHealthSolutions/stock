package com.ihs.stock.api.DAO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.management.InstanceAlreadyExistsException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.ird.unfepi.context.LocationContext;
import org.ird.unfepi.context.LocationServiceContext;
import org.ird.unfepi.model.Location;

import com.ihs.stock.api.model.Inventory;
import com.ihs.stock.api.model.Item;


public class DAOInventory {

	private Session s;

	public DAOInventory(Session session)
	{
		this.s = session ;
	}
	public List<Inventory> getByLocationForCurrentYear(Integer loc) {
	
		Query query = s.createQuery("from Inventory where consumerLocation = :locations AND year = :yr");
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);

		query.setParameter("locations", loc);
		query.setParameter("yr", year);

		List<Inventory> inventory = query.list();
		return inventory;
	}

	/*
	 * get the balance for a particular item in a particular location for the
	 * current month
	 */
	public Inventory getBalanceForLocationMonthItem(Integer loc, Integer item) {
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
		return (Inventory) inventory.get(0);
	}

	public List<?> getBalanceForLocationAllItems(Integer loc) {
		Query query = s
				.createQuery("from Inventory where consumerLocation = :locations AND month = :mon AND year = :yr");

		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);

		query.setParameter("locations", loc);
		query.setParameter("mon", month);
		query.setParameter("yr", year);

		List<?> Inventory = query.list();
		return Inventory;
	}

	public Inventory getPrevMonthInventory(Integer consumerLoc , Integer item) {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		Query query = s.createQuery("from Inventory where consumerLocation = :con AND month = :mon AND year = :yr AND item = :i");
		query.setParameter("con", consumerLoc);
		query.setParameter("mon", month);
		query.setParameter("yr", year);
		query.setParameter("i", item);
		Inventory inventory = (Inventory) query.uniqueResult();
		return inventory;

	}

	public boolean prevMonthExist(Integer consumerloc , Integer item) {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		Query query = s.createQuery("from Inventory where consumerLocation = :con AND month = :mon AND year = :yr AND item = :i");
		query.setParameter("con", consumerloc);
		query.setParameter("mon", month);
		query.setParameter("yr", year);
		query.setParameter("i", item);
		List<Inventory> inventory = (List<Inventory>) query.list();
		return inventory.size() > 0 ? true : false;

	}

	/*
	 * update balance in inventory for an item in a location for current month
	 * this method is triggered whenever a vaccinator's monthly balance is
	 * updated for that location
	 */
	public void save(Inventory inv) {
		try {
			s.save(inv);
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}

	public void update(Inventory inv) {
		try {
			s.update(inv);
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}

	public List<Integer> getDistinctYears() {
		Query query = s.createQuery("select distinct year from Inventory order by year desc");
		List<Integer> years = query.list();
		return years;
	}

	public List<Item> getDistinctItems() {
		Query query = s.createQuery("select distinct item from Inventory");
		List<Item> items = query.list();
		return items;
	}

	public List<Location> getDistinctLocation() {
		Query query = s.createQuery("select distinct consumerLocation from Inventory");
		LocationServiceContext sc = LocationContext.getServices();
		List<Integer> locId = query.list();
		List<Location> loc = new ArrayList<Location>() ;
		try
		{
		  for(int i = 0 ; i < locId.size() ; i++)
		  {
			  loc.add(sc.getLocationService().findLocationById(locId.get(i), false, null));
		  }
		  return loc ;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			sc.closeSession();
		}
		
		return null;
	}

	public List<Integer> getDistinctMonths() {
		
		Query query = s.createQuery("select distinct month from Inventory");

		List<Integer> months = query.list();
		return months;
	}

	public List<?> getAllMonthsInventory(int year) {
		Query query = s.createQuery("from Inventory where year= :yr order by year");
		query.setParameter("yr", year);
		List<Inventory> inv = query.list();
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
	public List<?> getCurrentMonthItems(Integer loc) {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(cal.MONTH) + 1;
		int year = cal.get(cal.YEAR);
		Query query = s
				.createQuery("select item from Inventory where consumer_location = :l AND month = :mon AND year = :yr");
		query.setParameter("l", loc);
		query.setParameter("mon", month);
		query.setParameter("yr", year);
		List<Item> item = query.list();
				return item;

	}

	public void updateInventory(Integer loc, Integer item, int vmsTotalVialsCount) {

		Inventory inventory = getBalanceForLocationMonthItem(loc, item);
		int newBalance = inventory.gettotalContainers() - vmsTotalVialsCount;
		inventory.settotalContainers(newBalance);
		try {
			s.update(inventory);
		
		} catch (HibernateException e) {

			e.printStackTrace();
		}

	}

	public boolean inventoryForMonthExist(Integer item, Integer loc) throws InstanceAlreadyExistsException {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
	    Query query = s.createQuery(
				"from Inventory where consumerLocation = :l AND item = :i AND month = :mon AND year = :yr");
		query.setParameter("l", loc);
		query.setParameter("i", item);
		query.setParameter("mon", month);
		query.setParameter("yr", year);
		List<?> list = query.list();
		return list.size() > 0 ? true : false;

	}
	
	public Inventory getPrevForSameMonth(Integer item , Integer loc) throws InstanceAlreadyExistsException
	{
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
	
		Query query = s.createQuery(
				"FROM Inventory where item = :i AND consumer_location = :l AND month = :mon AND year = :yr AND day ="+ 
"(Select max(day) from Inventory where item = :i AND consumer_location = :l AND month = :mon AND year = :yr )");
		query.setParameter("l", loc);
		query.setParameter("i", item);
		query.setParameter("mon", month);
		query.setParameter("yr", year);
		List<?> list = query.list();
		return (Inventory) list.get(0);
	}

	public List<Inventory> getForLocationMonthYear(Integer loc, int month, int year) {
		Query query = s.createQuery("from Inventory where consumerLocation = :l AND month = :m AND year = :yr order by year");
		query.setParameter("l", loc);
		query.setParameter("m", month);
		query.setParameter("yr", year);
		List<Inventory> inventory = query.list();
		return inventory;

	}

	public List<Inventory> getForYearMonth(int month, int year) {
		Query query = s.createQuery("from Inventory where month = :m AND year = :yr order by year");
		query.setParameter("m", month);
		query.setParameter("yr", year);
		List<Inventory> inventory = query.list();
		return inventory;
	}

	public List<Inventory> getForYearLocation(Integer loc, int year) {
		Query query = s.createQuery("from Inventory where consumerLocation = :loc AND year = :yr order by year");
		query.setParameter("loc", loc);
		query.setParameter("yr", year);
		List<Inventory> inventory = query.list();
		return inventory;
	}

	public List<Inventory> getForMonthLocation(Integer loc, int month) {
		Query query = s.createQuery("from Inventory where consumerLocation = :loc AND month = :mon order by month");
		query.setParameter("loc", loc);
		query.setParameter("mon", month);
		List<Inventory> inventory = query.list();
		return inventory;
	}

	public List<Inventory> getForLocation(Integer loc) {
		Query query = s.createQuery("from Inventory where consumerLocation = :loc order by year desc");
		query.setParameter("loc", loc);
		List<Inventory> inventory = query.list();
		return inventory;
	}

	public List<Inventory> getForMonth(int month) {
		Query query = s.createQuery("from Inventory where month = :mon order by month");
		query.setParameter("mon", month);
		List<Inventory> inventory = query.list();
		return inventory;
	}
	
	public List<Inventory> getAllInventory()
	{
		Query query = s.createQuery("from Inventory where dateVoided = null");
		List<Inventory> inventories = query.list();
		return inventories;
	}
	
	public List<Inventory> getById(int id) {
		Query query = s.createQuery("from Inventory where inventoryId = :i");
		query.setParameter("i", id);
		List<Inventory> inventory = query.list();
		return inventory;
	}
	

}
