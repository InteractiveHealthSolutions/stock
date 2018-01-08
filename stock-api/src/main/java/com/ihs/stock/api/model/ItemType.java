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
@Table(name = "item_type")
public class ItemType {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "type_id")
	private int typeId;
	
	@Column(name="item_type"  , unique = true)
	private String itemType;
	
	@Column(name="description")
	private String description;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_created")
	private Date dateCreated;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_edited")
	private Date dateEdited;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_voided")
	private Date dateVoided;
	
	public void settypeId(int id)
	{
		this.typeId = id;
	}
	
	public int gettypeId()
	{
		return typeId;
	}
	
	public void setitemType(String type)
	{
		this.itemType = type;
	}
	
	public String getitemType()
	{
		return itemType;
	}
	
	public String getdescription() {
		return description;
	}
	
	public void setdescription(String description) {
		this.description = description;
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
