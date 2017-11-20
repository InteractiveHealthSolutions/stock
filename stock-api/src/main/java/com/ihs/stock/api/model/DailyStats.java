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
@Table(name = "daily_stats")
public class DailyStats {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "daily_statsId")
	private int dailyStats;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_created")
	private Date dateCreated;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_edited")
	private Date dateEdited;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_voided")
	private Date dateVoided;
	
	@Column(name = "used_quantity")
	private Integer usedQuantity;
	
	@Column(name = "wasted_quantity")
	private Integer wastedQuantity;
	
	@Column(name = "used_containers")
	private Integer usedContainers;
	
	@Column(name = "wasted_containers")
	private Integer wastedContainers;
	
	@Column(name="day")
	private Integer day;
	
	@Column(name = "month")
	private Integer month;
	
	@Column(name = "year")
	private Integer year;
	
	@Column(name= "voided")
	private boolean voided;
	
	@Column(name="voided_by")
	private Integer voidedBy;
	
	@ManyToOne(targetEntity = Consumer.class , fetch = FetchType.EAGER)
	@JoinColumn(name = "consumer")
	@ForeignKey(name = "dailyStats_consumerID_consumer_mappedId_FK")
	private Consumer consumer;
	
	@ManyToOne(targetEntity = Item.class , fetch = FetchType.EAGER)
	@JoinColumn(name = "item")
	@ForeignKey(name = "dailyStats_itemId_item_mappedId_FK")
	private Item item;
	
	  
	public int getDailyStats()
	{
		return dailyStats;
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
	public void setusedQuantity(Integer antigens)
	{
		this.usedQuantity = antigens;
	}
	
	public Integer getusedQuantity()
	{
		return usedQuantity;
	}
	
	public void setwastedQuantity(Integer antigens)
	{
		this.wastedQuantity = antigens;
	}
	
	public Integer getwastedQuantity()
	{
		return wastedQuantity;
	}
	
	
	public void setusedContainers(Integer vials)
	{
		this.usedContainers = vials;
	}
	
	public Integer getusedContainers()
	{
		return usedContainers;
	}
	
	public void setwastedContainers(Integer vials)
	{
		this.wastedContainers = vials;
	}
	
	public Integer getwastedContainers()
	{
		return wastedContainers;
	}

	public void setitem(Item vac)
	{
		this.item = vac;
	}
	
	public Item getitem()
	{
		return item;
	}
	
	public Consumer getconsumer()
	{
		return consumer;
	}
	
	public void setconsumer(Consumer vac)
	{
		this.consumer = vac ;
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
	
	private boolean getvoided()
	{
		return voided;
	}
	
	public Integer getvoidedBy() {
		return voidedBy;
	}
	
	public void setvoidedBy(Integer voidedBy) {
		this.voidedBy = voidedBy;
	}
	
}
