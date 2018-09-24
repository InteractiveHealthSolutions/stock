package com.ihs.stock.api.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "ilr_daily_status")
public class ILRDailyStatus {

	public enum Status {

		ILRNonFunctional, ILRNotAvailable, TempratureMonitorNotFunctional, TempratureMonitorNotAvailable, TempratureRecorded

	};

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "ilr_identifier")
	private String ilrIdentifier;

	@JsonFormat(pattern = "yyyy-MM-dd", locale = "en_PK")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dateToday")
	private Date dateToday;

	@JsonFormat(pattern = "HH:mm:ss")
	@Temporal(TemporalType.TIME)
	@Column(name = "morningTime")
	private Date morningTime;

	@JsonFormat(pattern = "HH:mm:ss")
	@Temporal(TemporalType.TIME)
	@Column(name = "dayendTime")
	private Date dayendTime;

	@Column(name = "opening_temprature")
	private Double openingTemprature;

	@Column(name = "closing_temprature")
	private Double closingTemprature;

	@Enumerated(EnumType.STRING)
	@Column(length = 30)
	private Status morningILRStatus;

	@Enumerated(EnumType.STRING)
	@Column(length = 30)
	private Status dayendTMStatus;

	@Enumerated(EnumType.STRING)
	@Column(length = 30)
	private Status morningTMStatus;

	@Enumerated(EnumType.STRING)
	@Column(length = 30)
	private Status dayendILRStatus;
	@Column(name = "day")
	private Integer day;

	@Column(name = "month")
	private Integer month;

	@Column(name = "year")
	private Integer year;
	@Column(name = "voided")
	private boolean voided;

	@Column(name = "voided_by")
	private Integer voidedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_created")
	private Date dateCreated;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_edited")
	private Date dateEdited;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_voided")
	private Date dateVoided;

	@Column(name = "location_id")
	private Integer location;

	@Column(name = "user")
	private Integer user;

	public void setuser(Integer consumer) {
		this.user = consumer;
	}

	public Integer getuser() {
		return user;
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

	public Status getMorningILRStatus() {
		return morningILRStatus;
	}

	public void setMorningILRStatus(Status morningStatus) {
		this.morningILRStatus = morningStatus;
	}

	public Status getdayendTMStatus() {
		return dayendTMStatus;
	}

	public void setdayendTMStatus(Status dayendTMStatus) {
		this.dayendTMStatus = dayendTMStatus;
	}

	public void setmorningTMStatus(Status morningTMILRStatus) {
		this.morningTMStatus = morningTMILRStatus;
	}

	public Status getmorningTMStatus() {
		return morningTMStatus;
	}

	public Status getdayendILRStatus() {
		return dayendILRStatus;
	}

	public void setlocation(Integer location) {
		this.location = location;
	}

	public Integer getlocation() {
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

	public Integer getmonth() {
		return month;
	}

	public Integer getyear() {
		return year;
	}

	public void setmonth(Integer month) {

		this.month = month;
	}

	public void setyear(Integer year) {
		this.year = year;
	}

	public Integer getday() {
		return day;
	}

	public void setday(Integer day) {
		this.day = day;
	}

	public void setdateVoided(Date dateVoided) {
		this.dateVoided = dateVoided;
	}

	public Date getdateVoided() {
		return dateVoided;
	}

	public void setdayendTime(Date dayendTime) {
		this.dayendTime = dayendTime;
	}

	public Date getdayendTime() {
		return dayendTime;
	}

	public void setmorningTime(Date morningTime) {
		this.morningTime = morningTime;
	}

	public Date getmorningTime() {
		return morningTime;
	}

	public void setdayendILRStatus(Status dayendILRStatus) {
		this.dayendILRStatus = dayendILRStatus;
	}
}
