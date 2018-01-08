package com.ihs.stock.api.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import javax.persistence.Id;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GenerationType;

@Entity
@Table(name ="item")
public class Item {

	public enum ExpiryUnit{ months , hours , days , years};
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "item_id")
	private int itemId ;
	
	@Column(name="barcode" , unique = true)
	private String barcode;
	
	@Column(name = "name" , unique = true)
	private String name ;
	// identifier
	@Column(name = "short_name")
	private String shortName;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "brand")
	private String brand;
	
	@Column(name = "expiry_after_Opening")//rename this
	private Integer expiryAfterOpening ; //number of hours
	
	@Column(name = "enclosed_quantity")//rename 
	private Integer enclosedQuantity ;
	
	@Column(name= "manufacturer")
	private String manufacturer;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "expiry_unit")
	private ExpiryUnit expiryUnit;
	
	@Column(name="type_id")
	private Integer itemType;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_created")
	private Date dateCreated;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_edited")
	private Date dateEdited;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_voided")
	private Date dateVoided;
	
	public int getitemId()
	{
		return itemId;
	}
	
	public void setitemId(int id)
	{
		this.itemId = id ;
	}
	
	public String getname()
	{
		return name; 
	}
	
	public void setbarcode(String barcode)
	{
		this.barcode = barcode;
	}
	
	public String getbarcode()
	{
		return barcode;
	}
	
	public void setname(String name)
	{
		this.name = name ;
	}
	
	public String getshortName()
	{
		return shortName;
	}
	
	public void setshortName(String shortname)
	{
		this.shortName = shortname;
	}
	
	public String getbrand()
	{
		return brand;
	}
	
	public void setbrand(String brand )
	{
		this.brand = brand;
	}
	
	public String getdescription()
	{
		return description ;
	}
	public void setexpiryUnit(ExpiryUnit unit)
	{
		this.expiryUnit = unit;
	}
	
	public ExpiryUnit getexpiryUnit()
	{
		return expiryUnit;
	}
	
	public void setdescription(String descr)
	{
		this.description = descr ;
	}
	
	public String getmanufacturer()
	{
		return manufacturer;
	}
	
	public void setmanufacturer(String manufacturer)
	{
		this.manufacturer = manufacturer;
	}
	
	public Integer getexpiryAfterOpening()
	{
		return expiryAfterOpening;
	}
	
	public void setexpiryAfterOpening(Integer expiry)
	{
		this.expiryAfterOpening = expiry ;
	}
	
	public void setenclosedQuantity(Integer quantity)
	{
		this.enclosedQuantity = quantity;
	}
	
	public Integer getenclosedQuantity()
	{
		return enclosedQuantity;
	}
	
	public void setitemType(Integer type)
	{
		this.itemType = type;
	}
	
	public Integer getitemType()
	{
		return itemType;
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

}
