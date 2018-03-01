package com.ihs.stock.api.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.management.InstanceAlreadyExistsException;
import javax.tools.DocumentationTool.Location;

import org.hibernate.SessionFactory;

import com.ihs.stock.api.beans.DayEndEntryBean;
import com.ihs.stock.api.beans.DayEndEntryBeanMobile;
import com.ihs.stock.api.context.ServiceContextStock;
import com.ihs.stock.api.context.SessionFactoryUtil;
import com.ihs.stock.api.model.DailyStats;
import com.ihs.stock.api.model.ILRDailyStatus;
import com.ihs.stock.api.model.Inventory;
import com.ihs.stock.api.model.Item;

public class DailyEntryService {

	DailyStats dailyStats;

	public void morningEntryILR(ILRDailyStatus ilrDailyStatus) throws ParseException, InstanceAlreadyExistsException {
		// ilrDailyStatus = new ILRDailyStatus();

	   
		ServiceContextStock sc = SessionFactoryUtil.getServiceContext();
		try {
			sc.beginTransaction();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(sdf.format(new Date()));
			ilrDailyStatus.setdateCreated(date);

			ilrDailyStatus.setlocation(ilrDailyStatus.getlocation());
			Calendar cal = Calendar.getInstance();
			// ilrDailyStatus.setdateToday(date);
			cal.setTime(ilrDailyStatus.getdateToday());
			int month = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR);
			int day = cal.get(Calendar.DAY_OF_MONTH);
			cal.setTime(ilrDailyStatus.getmorningTime());
			cal.add(Calendar.HOUR, -5);
			ilrDailyStatus.setmorningTime(cal.getTime());
			ilrDailyStatus.setmonth(month);
			ilrDailyStatus.setday(day);
			ilrDailyStatus.setyear(year);
			List<ILRDailyStatus> ilrList = sc.ilrDailyStatusDAO.existForToday(day, month, year,
					ilrDailyStatus.getlocation());
			if (ilrList.size() > 0) {
				ilrList.get(0).setvoided(true);
				ilrList.get(0).setdateVoided(date);
				sc.ilrDailyStatusDAO.update(ilrList.get(0));
			}
			sc.ilrDailyStatusDAO.save(ilrDailyStatus);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sc.commitTransaction();
			sc.closeSession();
		}

	}

	@SuppressWarnings("deprecation")
	public void dailyStatsEntry(List<DayEndEntryBean> deb) throws ParseException, InstanceAlreadyExistsException {
		
		ServiceContextStock sc = SessionFactoryUtil.getServiceContext();

		try {
			//dailyStats = new DailyStats();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			

			Item item;
			for (int i = 0; i < deb.size(); i++) {

				try {
					Date date = sdf.parse(sdf.format(new Date()));
					
					dailyStats = new DailyStats();
					dailyStats.setdateCreated(date);
					date = sdf.parse(deb.get(i).getdateToday());
					item = sc.itemDAO.getByName(deb.get(i).getitemName());
					dailyStats.setitem(item.getitemId());
					dailyStats.setuser(deb.get(i).getvaccinatorId());
					dailyStats.setdateToday(date);
					dailyStats.setuserLocation(deb.get(i).getlocation());
					dailyStats.setwastedQuantity(deb.get(i).getwastedQuantityCount());
					dailyStats.setusedQuantity(deb.get(i).getwastedQuantityCount());
					int quantityInAContainer = item.getenclosedQuantity();
					int containerUsed = 0;
					int containerWasted = 0;
					int quantityUsed = deb.get(i).getusedQuantityCount();
					int quantityWasted = deb.get(i).getwastedQuantityCount();
					if (quantityWasted > quantityInAContainer) {
						if ((quantityWasted % quantityInAContainer) == 0) {
							containerWasted = quantityWasted / quantityInAContainer;
							dailyStats.setwastedContainers(containerWasted);
						} else {
							containerWasted = (quantityWasted / quantityInAContainer) + 1;
							dailyStats.setwastedContainers(containerWasted);
						}
						if ((quantityUsed % quantityInAContainer) == 0) {
							containerUsed = quantityUsed / quantityInAContainer;
							dailyStats.setusedContainers(containerUsed);
						} else {
							containerUsed = (quantityUsed / quantityInAContainer) + 1;
							dailyStats.setusedContainers(containerUsed);
						}
					} else {
						if (((quantityWasted + quantityUsed) % quantityInAContainer) == 0) {
							containerUsed = (quantityWasted + quantityUsed) / quantityInAContainer;
							dailyStats.setusedContainers(containerUsed);
						} else {
							containerUsed = ((quantityWasted + quantityUsed) / quantityInAContainer) + 1;
							dailyStats.setusedContainers(containerUsed);
						}
						dailyStats.setwastedContainers(0);
					}

					sc.dailyStatsDAO.save(dailyStats);
					/*int totalContainers = containerUsed + containerWasted;
					int totalQuantity = quantityUsed + quantityWasted;

					Inventory inventory = sc.inventoryDAO.getBalanceForLocationMonthItem(deb.get(i).getlocation(),
							item.getitemId());
					inventory.setwastedContainers(inventory.getwastedContainers() + containerWasted);
					inventory.setusedContainers(inventory.getusedContainers() + containerUsed);
					inventory.settotalContainers(inventory.gettotalContainers() - totalContainers);
					sc.inventoryDAO.update(inventory);*/

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} finally {
			sc.commitTransaction();
			sc.closeSession();

		}

	}

	public void dayEndEntryILR(ILRDailyStatus ilrDailyStats) throws ParseException, InstanceAlreadyExistsException {

		ServiceContextStock sc = SessionFactoryUtil.getServiceContext();
		try {
			sc.beginTransaction();
			ILRDailyStatus ilrDailyStatus = sc.ilrDailyStatusDAO.getMorningStatus(ilrDailyStats.getuser(),
					ilrDailyStats.getlocation(), ilrDailyStats.getdateToday());
			if (ilrDailyStats.getdayendILRStatus() != null) {
				ilrDailyStatus.setdayendILRStatus(ilrDailyStats.getdayendILRStatus());
			}
			if (ilrDailyStats.getdayendTMStatus() != null) {
				ilrDailyStatus.setdayendTMStatus(ilrDailyStats.getdayendTMStatus());
			}
			if (ilrDailyStats.getclosingTemprature() != null) {
				ilrDailyStatus.setclosingTemprature(ilrDailyStats.getclosingTemprature());
			}
			if (ilrDailyStats.getdayendTime() != null) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(ilrDailyStats.getdayendTime());
				cal.add(Calendar.HOUR, -5);
				ilrDailyStats.setdayendTime(cal.getTime());
				ilrDailyStatus.setdayendTime(ilrDailyStats.getdayendTime());
			}

			sc.ilrDailyStatusDAO.update(ilrDailyStatus);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			sc.commitTransaction();
			sc.closeSession();
		}

	}

	// public void dayEndEntry(List<DayEndEntryBeanMobile> deb, int user)
	// throws ParseException, InstanceAlreadyExistsException {
	//
	// ServiceContext sc = Context.getServices();
	//
	// // User user = sc.getUserService().findUser(userId);
	// try {
	//
	// // User user = sc.getUserService().findUser(userId);
	// List<Vaccinator> vaccinator = sc.getCustomQueryService()
	// .getDataByHQL("from Vaccinator where mappedId = " + user);
	// Location location = sc.getLocationService()
	// .findLocationById(vaccinator.get(0).getVaccinationCenter().getMappedId(),
	// false, null);
	// // sc.commitTransaction();
	// // SessionFactory sf = SessionFactoryUtil.getSessionFactory(null,
	// // "stk-hibernate.cfg.xml");
	// // ServiceContextStock scSTK =
	// // SessionFactoryUtil.getServiceContext();
	// //
	// SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	// Date date = sdf.parse(sdf.format(new Date()));
	//
	// Item item;
	// for (int i = 0; i < deb.size(); i++) {
	// dailyStats = new DailyStats();
	// item = sc.itemDAO.getByName(deb.get(i).getitem());
	// if (item == null) {
	// item = sc.itemDAO.getById(Integer.parseInt(deb.get(i).getitem()));
	// }
	// dailyStats.setitem(item);
	// dailyStats.setvaccinator(vaccinator.get(0));
	// dailyStats.setlocation(vaccinator.get(0).getVaccinationCenter());
	// dailyStats.setusedQuantity(deb.get(i).getquantityUsed());
	// dailyStats.setusedContainers(deb.get(i).getquantityWasted());
	// int quantityInAContainer = item.getenclosedQuantity();
	// int containerUsed = 0;
	// int containerWasted = 0;
	// int quantityUsed = deb.get(i).getquantityUsed();
	// int quantityWasted = deb.get(i).getquantityWasted();
	// if (quantityWasted > quantityInAContainer) {
	// if ((quantityWasted % quantityInAContainer) == 0) {
	// containerWasted = quantityWasted / quantityInAContainer;
	// dailyStats.setwastedContainers(containerWasted);
	// } else {
	// containerWasted = (quantityWasted / quantityInAContainer) + 1;
	// dailyStats.setwastedContainers(containerWasted);
	// }
	// if ((quantityUsed % quantityInAContainer) == 0) {
	// containerUsed = quantityUsed / quantityInAContainer;
	// dailyStats.setusedContainers(containerUsed);
	// } else {
	// containerUsed = (quantityUsed / quantityInAContainer) + 1;
	// dailyStats.setusedContainers(containerUsed);
	// }
	// } else {
	// if (((quantityWasted + quantityUsed) % quantityInAContainer) == 0) {
	// containerUsed = (quantityWasted + quantityUsed) / quantityInAContainer;
	// dailyStats.setusedContainers(containerUsed);
	// } else {
	// containerUsed = ((quantityWasted + quantityUsed) / quantityInAContainer)
	// + 1;
	// dailyStats.setusedContainers(containerUsed);
	// }
	// dailyStats.setwastedContainers(0);
	// }
	// dailyStats.setdateCreated(date);
	// sc.dailyStatsDAO.save(dailyStats);
	// int totalContainers = containerUsed + containerWasted;
	// int totalQuantity = quantityUsed + quantityWasted;
	//
	// Inventory inventory =
	// sc.inventoryDAO.getBalanceForLocationMonthItem((Location) location,
	// item);
	// inventory.setwastedContainers(inventory.getwastedContainers() +
	// containerWasted);
	// inventory.setusedContainers(inventory.getusedContainers() +
	// containerUsed);
	// inventory.settotalContainers(inventory.gettotalContainers() -
	// totalContainers);
	//
	// sc.inventoryDAO.update(inventory);
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// } finally {
	// sc.closeSession();
	//
	// }
	//
	// }
	public void morningEntryILRWEB(ILRDailyStatus ilrDailyStatus, int user, int location)
			throws ParseException, InstanceAlreadyExistsException {
		// ilrDailyStatus = new ILRDailyStatus();

		ServiceContextStock sc = SessionFactoryUtil.getServiceContext();
		try {
			sc.beginTransaction();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(sdf.format(new Date()));
			ilrDailyStatus.setdateCreated(date);

			ilrDailyStatus.setlocation(ilrDailyStatus.getlocation());
			Calendar cal = Calendar.getInstance();

			cal.setTime(ilrDailyStatus.getdateToday());
			int month = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR);
			int day = cal.get(Calendar.DAY_OF_MONTH);
			ilrDailyStatus.setmonth(month);
			ilrDailyStatus.setday(day);
			ilrDailyStatus.setyear(year);
			ilrDailyStatus.setuser(user);
			ilrDailyStatus.setlocation(location);
			List<ILRDailyStatus> ilrList = sc.ilrDailyStatusDAO.existForToday(day, month, year,
					ilrDailyStatus.getlocation());
			if (ilrList.size() > 0) {
				ilrList.get(0).setvoided(true);
				ilrList.get(0).setdateVoided(date);
				sc.ilrDailyStatusDAO.update(ilrList.get(0));
			}
			sc.ilrDailyStatusDAO.save(ilrDailyStatus);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sc.commitTransaction();
			sc.closeSession();
		}

	}

}
