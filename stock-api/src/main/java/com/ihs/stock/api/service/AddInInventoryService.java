package com.ihs.stock.api.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.management.InstanceAlreadyExistsException;

import org.hibernate.SessionFactory;
import org.ird.unfepi.context.LocationContext;
import org.ird.unfepi.context.LocationServiceContext;
import org.ird.unfepi.model.Location;

import com.ihs.stock.api.DAO.DAOConsumer;
import com.ihs.stock.api.DAO.DAOInventory;
import com.ihs.stock.api.DAO.DAOItem;

import com.ihs.stock.api.DAO.DAORequisition;
import com.ihs.stock.api.beans.ApproveRequirementBean;
import com.ihs.stock.api.beans.InventoryBean;
import com.ihs.stock.api.beans.UpdateRequirementBean;
import com.ihs.stock.api.beans.UpdateRequirementBeanMobile;
import com.ihs.stock.api.context.ServiceContextStock;
import com.ihs.stock.api.context.SessionFactoryUtil;
import com.ihs.stock.api.model.Consumer;
import com.ihs.stock.api.model.Inventory;
import com.ihs.stock.api.model.Item;

import com.ihs.stock.api.model.Requisition;

public class AddInInventoryService {

	Inventory inventory = new Inventory();

	public Inventory insertInInventory(InventoryBean ib) throws Exception {
		LocationServiceContext sc = LocationContext.getServices();
		String consumerlocationName = ib.getconsumerLocation();
		String parentlocationName = ib.getparentLocation();
		Location location = null;
		try {
			location = sc.getLocationService().findLocationByName(parentlocationName, false, null);
			inventory.setparentLocation(location.getLocationId());
			location = sc.getLocationService().findLocationByName(consumerlocationName, false, null);
			inventory.setconsumerLocation(location.getLocationId());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sc.closeSession();
		}
		String itemName = ib.getitemName();
		ServiceContextStock scSTK = SessionFactoryUtil.getServiceContext();
		try {
			scSTK.beginTransaction();
			Item item = scSTK.itemDAO.getByName(itemName);
			inventory.setitem(item.getitemId());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(sdf.format(new Date()));
			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR);
			int day = cal.get(Calendar.DAY_OF_MONTH);

			Integer initial = 0;

			Inventory parentInventory = scSTK.inventoryDAO.getBalanceForLocationMonthItem(location.getLocationId(),
					item.getitemId());

			if (scSTK.inventoryDAO.prevMonthExist(location.getLocationId(), item.getitemId())) {
				Inventory inv = scSTK.inventoryDAO.getPrevMonthInventory(location.getLocationId(), item.getitemId());
				inventory.setbalanceContainer(inv.getprevMonthBalance());
				inventory.settotalContainers(inv.getprevMonthBalance() + initial);
			} else {
				inventory.setbalanceContainer(0);
				inventory.settotalContainers(initial);
			}

			inventory.setcurrentMonthContainers(ib.getinventoryInitialVialsCount());

			inventory.setyear(year);
			inventory.setmonth(month);
			inventory.setday(day);
			inventory.setdateCreated(date);

			scSTK.inventoryDAO.save(inventory);

			parentInventory.settotalContainers(parentInventory.gettotalContainers() - initial);
			scSTK.inventoryDAO.update(parentInventory);
			return inventory;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			scSTK.commitTransaction();
			scSTK.closeSession();
		}

		return null;
	}

	public Inventory insertInParentInventory(InventoryBean ib) throws ParseException, InstanceAlreadyExistsException {
		ServiceContextStock scSTK = SessionFactoryUtil.getServiceContext();
		String locationName = ib.getconsumerLocation();

		LocationServiceContext sc = LocationContext.getServices();
		// DAOLocationImpl locationDAO = new DAOLocationImpl(sc.);
		try {
			Location location = (Location) sc.getLocationService().findLocationByName(locationName, false, null);
			scSTK.beginTransaction();
			String itemName = ib.getitemName();
			Item item = scSTK.itemDAO.getByName(itemName);
			Inventory prevEntry;
			if (scSTK.inventoryDAO.inventoryForMonthExist(item.getitemId(), location.getLocationId())) {
				prevEntry = scSTK.inventoryDAO.getPrevForSameMonth(item.getitemId(), location.getLocationId());
				inventory.settotalContainers(prevEntry.gettotalContainers());
				prevEntry.setvoided(true);
			}

			inventory.setitem(item.getitemId());

			inventory.setconsumerLocation(location.getLocationId());

			Integer initial = ib.getinventoryInitialVialsCount();

			if (scSTK.inventoryDAO.prevMonthExist(location.getLocationId(), item.getitemId())) {
				Inventory inv = scSTK.inventoryDAO.getPrevMonthInventory(location.getLocationId(), item.getitemId());
				inventory.setbalanceContainer(inv.getprevMonthBalance());
				inventory.settotalContainers(inventory.gettotalContainers() + inv.getprevMonthBalance()
						+ ib.getinventoryInitialVialsCount());
			} else {
				inventory.setbalanceContainer(0);
				inventory.settotalContainers(inventory.gettotalContainers() + initial);
			}

			inventory.setcurrentMonthContainers(ib.getinventoryInitialVialsCount());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(sdf.format(new Date()));
			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR);
			int day = cal.get(Calendar.DAY_OF_MONTH);
			inventory.setday(day);
			inventory.setyear(year);
			inventory.setmonth(month);
			inventory.setdateCreated(date);

			scSTK.inventoryDAO.save(inventory);
			return inventory;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			sc.closeSession();
			scSTK.closeSession();
		}

		return null;

	}

	public void updateRequirement(UpdateRequirementBean urb, int userid, int locationId)
			throws InstanceAlreadyExistsException, ParseException {
		Requisition req;
		Location location = null;
		LocationServiceContext sc = LocationContext.getServices();
		try {
			location = (Location) sc.getLocationService().findLocationByName(urb.getlocation(), false, null);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sc.closeSession();
		}

		ServiceContextStock scSTK = SessionFactoryUtil.getServiceContext();

		try {
			List<Item> items = (List<Item>) scSTK.itemDAO.getallItems();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(sdf.format(new Date()));
			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR);
			int day = cal.get(Calendar.DAY_OF_MONTH);
			for (int i = 0; i < urb.getquantity().size(); i++) {
				if (!urb.getquantity().get(i).isEmpty()) {
					req = new Requisition();
					req.setitem(items.get(i).getitemId());
					// req.setuserLocation(location.getLocationId());//location
					// of user submitting the form from web
					req.setcomments(urb.getcomments().get(i));
					req.setRequisitionBy(userid);
					req.setRequisitionLocation(location.getLocationId());// selected
																			// from
																			// dropdown
					req.setquantity(Long.parseLong(urb.getquantity().get(i)));
					req.setdateCreated(date);
					req.setmonth(month);
					req.setyear(year);
					req.setDay(day);
					req.setapprovalStatus("Pending");
					scSTK.requisitionDAO.save(req);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			scSTK.commitTransaction();
			scSTK.closeSession();
		}

	}

	public void updateRequirementMobile(List<UpdateRequirementBeanMobile> urb, int userId, int locationId)
			throws InstanceAlreadyExistsException, ParseException {
		Requisition req;

		ServiceContextStock scSTK = SessionFactoryUtil.getServiceContext();

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(sdf.format(new Date()));
			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR);
			int day = cal.get(Calendar.DAY_OF_MONTH);
			for (int i = 0; i < urb.size(); i++) {
				req = new Requisition();
				Item item = scSTK.itemDAO.getByName(urb.get(i).getitem());
				if (item == null) {
					item = scSTK.itemDAO.getById(Integer.parseInt(urb.get(i).getitem()));
				}
				req.setitem(item.getitemId());
				req.setRequisitionBy(userId);
				// req.setuserLocation(locationId);
				req.setcomments(urb.get(i).getcomment());
				req.setRequisitionBy(userId);
				req.setRequisitionLocation(locationId);
				req.setquantity((urb.get(i)).getquantity());
				req.setdateCreated(date);
				req.setmonth(month);
				req.setyear(year);
				req.setDay(day);
				req.setapprovalStatus("Pending");
				scSTK.requisitionDAO.save(req);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			scSTK.commitTransaction();
			scSTK.closeSession();
		}

	}

	public void ApproveReq(ApproveRequirementBean arb, List<Requisition> requisitions)
			throws InstanceAlreadyExistsException {

		ServiceContextStock scSTK = SessionFactoryUtil.getServiceContext();
		//Location location = null;
	//	LocationServiceContext sc = LocationContext.getServices();

		try {
			for (int i = 0; i < requisitions.size(); i++) {

				if (arb.getcheck()[i].equalsIgnoreCase("approve")) {
					// location = (Location)
					// sc.getLocationService().findLocationById(requisitions.get(i).getRequisitionLocation(),
					// false, null);
					// Inventory inv =
					// scSTK.inventoryDAO.getBalanceForLocationMonthItem(
					// location.getParentLocation().getLocationId(),
					// requisitions.get(i).getitem());
					// if (inv.gettotalContainers() >
					// requisitions.get(i).getquantity()) {
					// inv.settotalContainers((int) (inv.gettotalContainers() -
					// requisitions.get(i).getquantity()));
					// scSTK.inventoryDAO.update(inv);
					// requisitions.get(i).setapprovalReferenceInventory(inv.getinventoryID());
					// requisitions.get(i).setapprovalStatus("Approved");
					// scSTK.requisitionDAO.update(requisitions.get(i));
					// } else {
					// // requisitions.get(i).setcomments("Amount Unavailable");
					// requisitions.get(i).setapprovalStatus("Amount
					// Unavailable");
					// scSTK.requisitionDAO.update(requisitions.get(i));
					// }
					// this code is only for time being because we are ignoring
					// inventory check
					requisitions.get(i).setapprovalStatus("Approved");
					scSTK.requisitionDAO.update(requisitions.get(i));

				}
				if (arb.getcheck()[i].equalsIgnoreCase("unapprove")) {
					requisitions.get(i).setapprovalStatus("Unapproved");
					scSTK.requisitionDAO.update(requisitions.get(i));
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//sc.closeSession();
			scSTK.commitTransaction();
			scSTK.closeSession();
		}

	}

}
