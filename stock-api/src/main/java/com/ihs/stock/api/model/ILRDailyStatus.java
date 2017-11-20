package com.ihs.stock.api.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name="ilr_daily_status")
public class ILRDailyStatus {

	public enum Status{
		
		ILRNonFunctional ,
		ILRNotAvailable ,
		TempratureMonitorNotFunctionl ,
		TempratureMonitorNotAvailable,
		TempratureRecorded
		
	};
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name="ilr_identifier")
	private String ilrIdentifier;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_today")
	private Date dateToday;
	
	@Column(name = "opening_temprature")
	private Double openingTemprature;
	
	@Column(name = "closing_temprature")
	private Double closingTemprature;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 30)
	private Status ilrStatus;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date")
	private Date date;
	
	@Column(name="voided")
	private boolean voided;

	@Column(name="voided_by")
	private Integer voidedBy;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_created")
	private Date dateCreated;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_edited")
	private Date dateEdited;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_voided")
	private Date dateVoided;
	
	@ManyToOne(targetEntity = Location.class , fetch = FetchType.EAGER)
	@JoinColumn(name="location_id")
	@ForeignKey(name="ILRdailyStatus_id_location_mappedId_Fk")
	private Location location;
	
	@ManyToOne(targetEntity = Consumer.class , fetch = FetchType.EAGER)
	@JoinColumn(name="consumer_id")
	@ForeignKey(name="ILRdailyStatus_id_consumer_mappedId_Fk")
	private Consumer consumer;
	
	public void setconsumer(Consumer consumer) {
		this.consumer = consumer;
	}
	
	public Consumer getconsumer() {
		return consumer;
	}

	public void setid(int id) {
		this.id = id;
	}

	public int getid() {
		return id;
	}

	public void setdateToday(Date date) {
		this.dateToday = date;
	}

	public Date getdateToday() {
		return dateToday;
	}

	public void setopeningTemprature(Double temp) {
		this.openingTemprature = temp;
	}

	public Double getopeningTemprature() {
		return openingTemprature;
	}

	public void setclosingTemprature(Double temp) {
		this.closingTemprature = temp;
	}

	public Double getclosingTemprature() {
		return closingTemprature;
	}

	public void setilrStatus(Status s) {
		this.ilrStatus = s;
	}

	public Status getilrStatus() {
		return ilrStatus;
	}
	public void setlocation(Location location) {
		this.location = location;
	}
	
	public Location getlocation() {
		return location;
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
	
	public Integer getvoidedBy() {
		return voidedBy;
	}
	
	public void setvoidedBy(Integer voidedBy) {
		this.voidedBy = voidedBy;
	}
	
	public void setvoided(boolean voided) {
		this.voided = voided;
	}
	
	public boolean getvoided() {
		return voided;
	}
	
	public Date getdate() {
		return date;
	}
	public void setdate(Date date) {
		this.date = date;
	}
}
