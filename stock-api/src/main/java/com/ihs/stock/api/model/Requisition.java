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

import org.hibernate.annotations.ForeignKey;

import com.ihs.locationmanagement.api.model.Location;
@Entity
@Table(name="requisition")
public class Requisition {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="requisition_id")
	private Integer requisitionId;
	
	@ManyToOne(targetEntity = Item.class , fetch = FetchType.EAGER)
	@JoinColumn(name = "item")
	@ForeignKey(name = "requirement_itemId_item_mappedId_FK")
	private Item item;
	
	@ManyToOne(targetEntity = Location.class , fetch = FetchType.EAGER)
	@JoinColumn(name = "consumer_location")
	@ForeignKey(name = "requirement_locationId_location_mappedId_FK")
	private Location consumerlocation;
	
	@Column(name = "quantity")
	private Integer quantity;
	
	@Column(name = "comments")
	private String comments;
	
	@Column(name="day")
	private Integer day;
	
	@Column(name="month")
	private Integer month;
	
	@Column(name="year")
	private Integer year;
	
	@Column(name="voided")
	private boolean voided;
	
	@Column(name="voided_by")
	private Integer voidedBy;
	
	@Column(name="requisition_by")
	private Integer requisitionBy;//this will be a user id who's entering the the data in th form
	
	@ManyToOne(targetEntity = Inventory.class , fetch = FetchType.EAGER)
	@JoinColumn(name = "approval_reference")
	@ForeignKey(name = "requisition_inventoryId_inventory_mappedId_FK")
	private Inventory approvalReference;
	
	@Column(name="approval_status")
	private String approvalStatus;
	
	@ManyToOne(targetEntity = Location.class , fetch = FetchType.EAGER)
	@JoinColumn(name = "requisition_location")
	@ForeignKey(name = "requisition_locationId_location_mappedId_FK")
	private Location requisitionLocation ;
	
	@Column(name="date_created")
	@Temporal(TemporalType.DATE)
	private Date dateCreated ;
	
	@Column(name="date_edited")
	@Temporal(TemporalType.DATE)
	private Date dateEdited;
	
	@Column(name="date_voided")
	@Temporal(TemporalType.DATE)
	private Date dateVoided;
	
	
	public void setcomments(String comments) {
		this.comments = comments;
	}
	
	public void setitem(Item item) {
		this.item = item;
	}
	
	public void setquantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	public Item getitem() {
		return item;
	}
	
	public Integer getquantity() {
		return quantity;
	}
	
	public String getcomments() {
		return comments;
	}
	
	public Date getdateCreated() {
		return dateCreated;
	}
	
	public Date getdateEdited() {
		return dateEdited;
	}
	
	public Date getdateVoided() {
		return dateVoided;
	}
	public void setdateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	 
	public void setdateEdited(Date dateEdited) {
		this.dateEdited = dateEdited;
	}
	 
	public void setdateVoided(Date dateVoided) {
		this.dateVoided = dateVoided;
	}
	
	public void setmonth(Integer month) {
		this.month = month;
	}
	 public Integer getmonth() {
		return month;
	}
	 
	public void setyear(Integer year) {
		this.year = year;
	}
	
	public Integer getyear() {
		return year;
	}
	
	public Location getconsumerLocation() {
		return consumerlocation;
	}
	
	public void setconsumerLocation(Location location) {
		this.consumerlocation = location;
	}
	
	public Integer getDay() {
		return day;
	}
	
	public void setDay(Integer day) {
		this.day = day;
	}
	
    public void setVoided(boolean voided) {
		this.voided = voided;
	}
    
    public boolean getVoided()
    {
    	return voided;
    }
    
    public void setVoidedBy(Integer voidedBy) {
		this.voidedBy = voidedBy;
	}
    
    public Integer getVoidedBy() {
		return voidedBy;
	}
    
    public void setRequisitionBy(Integer requisitionBy) {
		this.requisitionBy = requisitionBy;
	}
    
    public Integer getRequisitionBy() {
		return requisitionBy;
	}
    
    public void setApprovalReference(Inventory approvalReference) {
		this.approvalReference = approvalReference;
	}
    
    public Inventory getApprovalReference() {
		return approvalReference;
	}
    
    public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
    
    public String getApprovalStatus() {
		return approvalStatus;
	}
    
    public Location getRequisitionLocation() {
		return requisitionLocation;
	}
    
    public void setRequisitionLocation(Location requisitionLocation) {
		this.requisitionLocation = requisitionLocation;
	}
	
	
	
	 
}
