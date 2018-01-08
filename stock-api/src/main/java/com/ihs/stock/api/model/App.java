package com.ihs.stock.api.model;


import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate4.HibernateTemplate;

import com.ihs.stock.api.DAO.DAOItemAttribute;
import com.ihs.stock.api.beans.UpdateRequirementBean;
import com.ihs.stock.api.context.ServiceContextStock;
import com.ihs.stock.api.context.SessionFactoryUtil;

public class App {
public static HashMap<String , String> dynamicColumns = new HashMap<String,String>();
	//public enum ExpiryUnit{ month , hours , days};
	//static HibernateTemplate hb = new HibernateTemplate(SessionFactoryUtil.getSessionFactory());
	public static void main(String[] args) throws ParseException, UnsupportedEncodingException {
//		Item item = new Item();
//		Location loc = new Location();
//		Vaccinator vac = new Vaccinator();
//		Inventory inv = new Inventory();
//		Order ord = new Order();
//		Vaccinator_DailyStats vacDailyStats = new Vaccinator_DailyStats();
//		Vaccinator_MonthlyStats vacMonStats = new Vaccinator_MonthlyStats();    SessionFactory sf = SessionFactoryUtil.getSessionFactory();
//	    Session ses = sf.openSession();	    
//	    Transaction tx = ses.beginTransaction();
//	    Item item = (Item) ses.get(Item.class, 1);
//	    item.setitemName("i m testing 2");
//	    ses.update(item);
//	    DAOInventory invDAO = new DAOInventory();
//	    List<Inventory> monthlyInventory = (List<Inventory>) invDAO.getAllMonthsInventory(2016);
//	    System.out.println(monthlyInventory.size());
//	    tx.commit();
//	    ses.close();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		Date dateWithoutTime = sdf.parse(sdf.format(new Date()));
//	    System.out.println(dateWithoutTime);
	    
//	    ArrayList<String> test = new ArrayList<String>();
//	    test.add("test1");
//	    test.add("test2");
//	    test.add("test3");
//	    Item item = new Item();
//	    DAOItem itemDAO = new DAOItem();
//	    item = itemDAO.getByName("PCV");
////	    
//	    ItemAttributes iA = new ItemAttributes();
//	    iA.setitem(item);
//    DAOItemAttribute d = new DAOItemAttribute();
//    System.out.println(d.getColumnNames().toString());
////	   // d.save(iA);
	    //d.Update(item, test, test);
////	    //ExpiryUnit[] eu = ExpiryUnit.values();
//	    List<ExpiryUnit> arrayList = new ArrayList<ExpiryUnit>();
//	    for(ExpiryUnit eu : ExpiryUnit.values())
//	    {
//	    	arrayList.add(eu);
//	    	System.out.println(eu);
//	    }
//	    
	    	
	  
	   //SessionFactoryUtil.getSessionFactory();
	    ///hb.setCheckWriteOperations(false);
//	 //   hb.saveOrUpdate(item);
//	   // hb.flush();
//		//System.out.println("hello");
//		DAOItem i = new DAOItem();
//		Item item = i.getByName("BCG");
//		System.out.println(item.getexpiryAfterOpening());
//		DAOLocation d = new DAOLocation();
//		Locations loc = d.getByName("abc");
//		System.out.println(loc.get_locationName());
//		//item.setdosesInaVial(2);
		//hb.setCheckWriteOperations(false);
		//hb.save(item);
		
//		
		//AddInitialDataDB add = new AddInitialDataDB();
		
		//add.addItems();
		//DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
//		LocalDate localDate = LocalDate.now();
//		Calendar cal = Calendar.getInstance();
//		int month = cal.get(Calendar.MONTH)+1;
//		int year = cal.get(Calendar.YEAR);
//		//System.out.println(dtf.format(localDate)); //2016/11/16
//		System.out.println(year);
	//getBalanceForLocationAllItems();
//		
//    DAOInventory invD = new DAOInventory();
//  
//    System.out.println(invD.aggragateBalanceYearly(2017).toString() );
//	    SimpleDateFormat sdf = new SimpleDateFormat();
//		Date date = sdf.parse(sdf.format(new Date()));
//		date.getDay();
//		DAOItem d = new DAOItem();
//		Item item = d.getById(1);
//	DAOLocation locationDAO = new DAOLocation();
//	Location loc = locationDAO.getByName("Sindh");
//	DAOItem itemDAO = new DAOItem();
//	Item item = itemDAO.getByName("Penta");
//	DAODailyStats ds = new DAODailyStats();
//    String wasted = ds.getSumWastedQuantity(loc, item);
//	System.out.println(wasted);
		Calendar cal = Calendar.getInstance();
		System.out.println(cal.get(Calendar.DAY_OF_MONTH));
//		String month = new DateFormatSymbols().getMonths()[(cal.get(Calendar.MONTH)+1)-1];
//		System.out.println(month);
//	System.out.println(loc.getLocationID());
//	DAOInventory daoInventory = new DAOInventory();
//	List<Item> inv = (List<Item>) daoInventory.getCurrentMonthItems(loc);
//	System.out.println(inv.get(0).getname());
//	List<Location> location = daoInventory.getDistinctLocation();
//	System.out.println(location.get(0).getname());
//	System.out.println(location.get(1).getname());
//	
	
//		//Integer i1 = Integer.parseInt(date);
//		
//		Date dateToday = new Date(date);
//		cal.setTime(dateToday);
//		System.out.println(dateToday);
//		System.out.println(cal.get(Calendar.MONTH)+1);
//		System.out.println(cal.get(Calendar.DAY_OF_MONTH));
//		System.out.println("year "+cal.get(Calendar.YEAR));
		Double a = -1.0;
		Double b = 9.0;
		System.out.println(a*b);
		
		@SuppressWarnings("deprecation")
		Date temp = new Date();
		System.out.println(temp.toString());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		System.out.println(sdf.format(temp));
		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(sdf.format(temp));
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		System.out.println(cal1.get(Calendar.MONTH)+1);
		System.out.println(cal1.get(Calendar.YEAR));
		System.out.println(cal1.get(Calendar.DAY_OF_MONTH));
		
	//	Pattern patternForComment = Pattern.compile("(?!^\\d+$)^.+$");
//	   if(patternForComment.matcher("123").matches())
//	   {
//		   System.out.println("true");
//	   }
	//   SessionFactory sf = SessionFactoryUtil.getSessionFactory(null, null);
//		ServiceContextStock scSTK = SessionFactoryUtil.getServiceContext();
//		Item item = scSTK.itemDAO.getByName("BCG");
//		System.out.println(item.getname());
//		scSTK.commitTransaction();
//		scSTK.closeSession();
//		Date date = null;
//		System.out.println(date);
//	   SessionFactory sf = SessionFactoryUtil.getSessionFactory();
//		Session s = sf.getCurrentSession();
//		Transaction tx = s.beginTransaction();
//		DAOLocationImpl daoLocationImpl = new DAOLocationImpl(s);
//		List<Location> loc = daoLocationImpl.getAll(true, null);
//		tx.commit();
//		System.out.println(loc.size());
		
//		ILRDailyStatus ilr = SessionFactoryUtil.getServiceContext().ilrDailyStatusDAO.getMorningStatus(1, 1, new Date("2018-01-04 11:15:00"));
	}
//	public void AddItems(Item item)
//	{
//		//SessionFactoryUtil.getSessionFactory();
//	    hb.setCheckWriteOperations(false);
//		hb.saveOrUpdate(item);
//		
//	}
//	
//	public static void printInventory(Inventory inv)
//	{
//		System.out.println(inv.getinventoryID());
//		//System.out.println(inv.getbalanceVialsQuantity());
//		System.out.println();
//	}
//
//	public static void getBalanceForLocationAllItems()
//	{
//		String query = "from Inventory where month = :mon AND year = :yr";
//		String[] params = { "mon", "yr"};
//		Calendar cal = Calendar.getInstance();
//		int month = cal.get(Calendar.MONTH)+1;
//		int year = cal.get(Calendar.YEAR);
//		int loc = 1;
//		Object[] values = { month , year};
//		List<Inventory> inventory = (List<Inventory>) hb.findByNamedParam(query,params, values);
//		
//		System.out.println(inventory.get(0).getlocation().getlocationName());;
//	}
	
}
