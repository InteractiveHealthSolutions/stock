package com.ihs.stock.api.model;


import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.ihs.locationmanagement.api.model.Location;
import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name ="inventory")

public class Inventory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "inventory_id")
	private int inventoryId;
	
	@Column(name = "current_month_container")
	private Integer currentMonthContainers;
	
	@Column(name = "prev_month_balance")
	private Integer prevMonthBalance;
	
	@Column(name = "total_container")
	private Integer totalContainers;
	
	@Column(name="used_containers")
	private Integer usedContainers;
	
	@Column(name="wasted_containers")
	private Integer wastedContainers;
	
	@Column(name="day")
	private Integer day;
	
	@Column(name = "month")
	private Integer month ;
	
	@Column(name = "year")
	private Integer year;
	
	@Column(name="voided")
	private boolean voided;
	
	@Column(name="voided_by")
	private Integer voidedBy;//this will be changed to type user and mapped as foreign key from user table
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_created")
	private Date dateCreated;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_edited")
	private Date dateEdited;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_voided")
	private Date dateVoided;
	
	@ManyToOne(targetEntity = Item.class , fetch = FetchType.EAGER)
	@JoinColumn(name = "item")
	@ForeignKey(name = "inventory_itemid_item_mappedId_FK")
	private Item item;
	
	@ManyToOne(targetEntity = Location.class , fetch = FetchType.EAGER)
	@JoinColumn(name = "consumer_location")
	@ForeignKey(name = "inventory_tolocationid_location_mappedId_FK")
	private Location consumerLocation;
	
	@ManyToOne(targetEntity = Location.class , fetch = FetchType.EAGER)
	@JoinColumn(name = "parent_location")
	@ForeignKey(name = "inventory_fromlocationid_location_mappedId_FK")
	private Location parentLocation;
	
	public void setinventoryId(int id)
	{
		this.inventoryId = id;
	}
	
	public int getinventoryID()
	{
		return inventoryId;
	}
	
	public Integer getcurrentMonthContainers()
	{
		return currentMonthContainers;
	}
	
	public void setcurrentMonthContainers(Integer initialQuantity)
	{
		this.currentMonthContainers = initialQuantity;
	}
	
	public Integer getprevMonthBalance()
	{
		return prevMonthBalance;
	}
	
	public Item getitem()
	{
		return item;
	}
	
	public void setitem(Item item_inStock)
	{
		this.item = item_inStock;
	}
	
	public Location getconsumerLocation()
	{
		return consumerLocation;
	}
	
	public void setconsumerLocation(Location loc)
	{
		this.consumerLocation = loc;
	}
	
	public Location getparentLocation()
	{
		return parentLocation;
	}
	
	public void setparentLocation(Location loc)
	{
		this.parentLocation = loc;
	}
	
	public void setbalanceContainer(Integer balanceQuantity)
	{
		this.prevMonthBalance = balanceQuantity;
	}
	
	public Integer getmonth()
	{
		 return month;
	}
	
	public Integer getyear()
	{
		return year;
	}
	public void setmonth(Integer month)
	{
		
		this.month = month;
	}
	
	public void setyear(Integer year)
	{
		this.year = year;
	}
	
	public void settotalContainers(Integer tContainers)
	{
		this.totalContainers = tContainers;
	}
	
	public Integer gettotalContainers()
	{
		return totalContainers;
	}
	public Date getdateCreated() {
		return dateCreated;
	}
	public void setdateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Date getdateEdited() {
		return dateEdited;
	}
	public void setdateEdited(Date dateEdited) {
		this.dateEdited = dateEdited;
	}
	
	public void setdateVoided(Date dateVoided) {
		this.dateVoided = dateVoided;
	}
	
	public Date getdateVoided() {
		return dateVoided;
	}
	
	public Integer getday() {
		return day;
	}
	
	public void setday(Integer day) {
		this.day = day;
	}
	public void setvoided(boolean voided) {
		this.voided = voided;
	}
	
	public Integer getvoided() {
		return voidedBy;
	}
	
	public Integer getusedContainers() {
		return usedContainers;
	}
	
	public void setusedContainers(Integer usedContainers) {
		this.usedContainers = usedContainers;
	}
	
	public Integer getwastedContainers() {
		return wastedContainers;
	}
	public void setwastedContainers(Integer wastedContainers) {
		this.wastedContainers = wastedContainers;
	}
}
