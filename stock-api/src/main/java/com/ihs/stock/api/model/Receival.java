package com.ihs.stock.api.model;

import java.util.Date;

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
@Table(name = "receival")
public class Receival {
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "receival_id")
	private int receivalId;
	
    
    @Column(name = "received_amount")
	private Integer receivedAmount;
    
    @Column(name= "required_amount")
    private Integer requiredAmount;
    
    @Column(name= "month")
    private Integer month;
    
    @Column(name="year")
    private Integer year;
    
    @Column(name="status")
    private String status;
    
    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_created")
	private Date dateCreated;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_edited")
	private Date dateEdited;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_voided")
	private Date dateVoided;
	
	@ManyToOne(targetEntity = Item.class , fetch = FetchType.EAGER)
	@JoinColumn(name = "item")
	@ForeignKey(name = "receival_itemId_item_mappedId_FK")
	private Item item;
	
	@ManyToOne(targetEntity = Location.class , fetch = FetchType.EAGER)
	@JoinColumn(name = "location")
	@ForeignKey(name = "receival_locationId_location_mappedId_FK")
	private Location location;
	
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
	
	public int getreceivalId() {
		return receivalId;
	}
	
	public void setreceivalId(int reqId) {
		this.receivalId = reqId;
	}
	
	public Item getitem() {
		return item;
	}
	
	public void setitem(Item item) {
		this.item = item;
	}
	
	public void setlocation(Location location) {
		this.location = location;
	}
	
	public Location getlocation() {
		return location;
	}
	
	public void setmonth(Integer month) {
		this.month = month;
	}
	
	public Integer getmonth() {
		return month;
	}
	
	public Integer getyear() {
		return year;
	}
	
	public void setyear(Integer year) {
		this.year = year;
	}
	
	
	public Integer getreceivedAmount() {
		return receivedAmount;
	}
	
	public void setreceivedAmount(Integer receivedAmount) {
		this.receivedAmount = receivedAmount;
	}
	public void setstatus(String status) {
		this.status = status;
	}
	
	public String getstatus() {
		return status;
	}
	public Integer getrequiredAmount() {
		return requiredAmount;
	}
	
	public void setrequiredAmount(Integer requiredAmount) {
		this.requiredAmount = requiredAmount;
	}
} 
