package com.ihs.stock.api.model;

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

import java.util.Date;
import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "consumer")
public class Consumer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "consumer_id")
	private int consumerId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name="location")
	private Integer location;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_created")
	private Date dateCreated;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_edited")
	private Date dateEdited;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_voided")
	private Date dateVoided;
	
	
	public int getconsumerId()
	{
		return consumerId;
	}
	
	public void setconsumerId(int id)
	{
		this.consumerId = id;
	}
	
	public String getname()
	{
		return name;
	}
	
	public Integer getlocation()
	{
		return location;
	}
	
	public void setlocation(Integer loc)
	{
		this.location = loc ;
	}
	public Date getdateCreated() {
		return dateCreated;
	}
	
	public void setdateCreated(Date dateCreated) 
	{
		this.dateCreated = dateCreated;
	}
	public Date getdateEdited()
	{
		return dateEdited;
	}
	public void setdateEdited(Date dateEdited) 
	{
		this.dateEdited = dateEdited;
	}
	public void setdateVoided(Date dateVoided) 
	{
		this.dateVoided = dateVoided;
	}
	public Date getdateVoided()
	{
		return dateVoided;
	}

	public void setname(String name) {
		this.name = name;
	}
	

}
