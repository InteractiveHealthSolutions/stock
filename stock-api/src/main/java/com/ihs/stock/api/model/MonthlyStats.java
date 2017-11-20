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

import org.hibernate.annotations.ForeignKey;

@Entity 
@Table(name = "monthly_Stats")

public class MonthlyStats {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mon_id")
	private int monId;
	
	
	@Column(name = "month")
	private Integer month;
	
	@Column(name = "year")
	private Integer year;
	
	@Column(name = "initial_quantity")
	private Integer initialQuantity;
	
	@Column(name = "balance_quantity")
	private Integer balanceQuantity;
	
	@Column(name = "initial_containers")
	private Integer initialContainers;
	
	@Column(name = "balance_containers")
	private Integer balanceContainers; //remaining from next month
	
	@Column(name = "total_containers")
	private Integer totalContainers; //last month's balance + current month's initials
	
	@Column(name = "total_quantity")
	private Integer totalQuantity;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "expiry_date")
	private Date expiryDate ;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "manufacture_date")
	private Date manufactureDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_created")
	private Date dateCreated;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_edited")
	private Date dateEdited;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_voided")
	private Date dateVoided;
	
	
	@ManyToOne(targetEntity = Consumer.class , fetch = FetchType.EAGER)
	@JoinColumn(name = "consumer", nullable=false)
	@ForeignKey(name = "monthlystats_consumerId_consumer_mappedId_Fk")
	private Consumer consumer ;
	
	@ManyToOne(targetEntity = Item.class , fetch = FetchType.EAGER)
	@JoinColumn(name = "item")
	@ForeignKey(name = "monthlystats_itemId_item_mappedId_FK")
	private Item item;
	
	public void setmonId(int id)
	{
		this.monId = id;
	}
	
	public int getmonId()
	{
		return monId;
	}
	
	
	public void setinitialQuantity(Integer ini_antigens)
	{
		this.initialQuantity = ini_antigens;
	}
	
	public Integer getinitialQuantity()
	{
		return initialQuantity;
	}
	
	public void setbalanceQuantity(Integer balance)
	{
		this.balanceQuantity = balance;
	}
	
	public Integer getbalanceQuantity()
	{
		return balanceQuantity;
	}
	
	public Integer getinitialContainers()
	{
		return initialContainers;
	}
	
	public void setinitialContainersCount(Integer ini_vials)
	{
		this.initialContainers = ini_vials;
	}
	
	public Integer getbalanceContainers()
	{
		return balanceContainers;
	}
	
	public void setbalanceContainers(Integer balance)
	{
		this.balanceContainers = balance;
	}
	
	public Integer gettotalQuantity()
	{
		return totalQuantity;
	}
	
	public void settotalQuantity(Integer total_antigens)
	{
		this.totalQuantity = total_antigens; 
	}
	
	public Integer gettotalContainers()
	{
		return totalContainers;
	}
	
	public void settotalContainers(Integer vials)
	{
		this.totalContainers = vials;
	}
	public void setwastedVials(Integer total_vials)
	{
		this.totalContainers = total_vials;
	}
	
	public Consumer getconsumer()
	{
		return consumer;
	}
	
	public void setconsumer(Consumer vac)
	{
		this.consumer = vac;
	}
	
	public Item getitem()
	{
		return item;
	}
	
	public void setitem(Item vac)
	{
		this.item = vac;
	}
	
	public Integer getmonth()
	{
		return month;
	}
	
	public void setmonth(Integer mon)
	{
		month = mon;
	}
	
	public void setyear(Integer yr)
	{
		year = yr;
	}
	public Integer getyear()
	{
		return year;
	}
	
	public void setexpiryDate(Date date)
	{
		this.expiryDate = date;
	}
	
	public void setmanufactureDate(Date date)
	{
		this.manufactureDate = date;
	}
	
	public Date getexpiryDate()
	{
		return expiryDate;
	}
	
	public Date getmanufactureDate()
	{
		return manufactureDate;
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
	public Date getdateVoided() {
		return dateVoided;
	}
	public void setdateVoided(Date dateVoided) {
		this.dateVoided = dateVoided;
	}
}
