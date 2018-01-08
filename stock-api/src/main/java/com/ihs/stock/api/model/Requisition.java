package com.ihs.stock.api.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="requisition")
public class Requisition {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="requisition_id")
	private Integer requisitionId;
	
	@Column(name="item")
	private Integer item;
	
	@Column(name="user_location")
	private Integer userlocation;
	
	@Column(name = "quantity")
	private Long quantity;
	
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
	

	@Column(name="approval_reference")
    private Integer approvalReferenceInventory;
	
	@Column(name="approval_status")
	private String approvalStatus;
	
	@Column(name="requisition_location")
	private Integer requisitionLocation ;
	
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
	
	public void setitem(Integer item) {
		this.item = item;
	}
	
	public void setquantity(Long quantity) {
		this.quantity = quantity;
	}
	
	public Integer getitem() {
		return item;
	}
	
	public Long getquantity() {
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
	
	public Integer getuserLocation() {
		return userlocation;
	}
	
	public void setuserLocation(Integer location) {
		this.userlocation = location;
	}
	
	public Integer getDay() {
		return day;
	}
	
	public void setDay(Integer day) {
		this.day = day;
	}
	
    public void setvoided(boolean voided) {
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
    
    public void setapprovalReferenceInventory(Integer approvalReference) {
		this.approvalReferenceInventory = approvalReference;
	}
    
    public Integer getapprovalReferenceInventory() {
		return approvalReferenceInventory;
	}
    
    public void setapprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
    
    public String getapprovalStatus() {
		return approvalStatus;
	}
    
    public Integer getRequisitionLocation() {
		return requisitionLocation;
	}
    
    public void setRequisitionLocation(Integer requisitionLocation) {
		this.requisitionLocation = requisitionLocation;
	}
	
	
	
	 
}
