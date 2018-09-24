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
@Table(name = "monthly_stats")
public class MonthlyStats {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mon_id")
	private int monId;

	@Column(name = "opening_balance")
	private Integer openingBalance;

	@Column(name = "balance_quantity")
	private Integer balanceQuantity;

	@Column(name = "balance_container")
	private Integer balanceContainer;

	@Column(name = "initial_containers")
	private Integer initialContainers;

	@Column(name = "total_quantity")
	private Integer totalQuantity;

	@Column(name = "doses_wasted")
	private Integer dosesWasted;

	@Column(name = "doses_consumed")
	private Integer consumedDoses;

	@Column(name = "doses_received")
	private Integer dosesReceived;

	@Column(name = "total_container")
	private Integer totalContainer;

	@Column(name = "closing_balance")
	private Integer closingBalance;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "expiry_date")
	private Date expiryDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "manufacture_date")
	private Date manufactureDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "receival_date")
	private Date receivalDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_created")
	private Date dateCreated;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_edited")
	private Date dateEdited;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_voided")
	private Date dateVoided;

	@Column(name = "user")
	private Integer user;

	@Column(name = "item")
	private Integer item;

	@Column(name = "location")
	private Integer location;

	@Column(name = "inventory_referral")
	private Integer inventoryReferral;

	@Column(name = "reference_number")
	private String referenceNumber;

	@Column(name = "received_from")
	private String receivedFrom;

	public void setmonId(int id) {
		this.monId = id;
	}

	public int getmonId() {
		return monId;
	}

	public void setbalanceQuantity(Integer balance) {
		this.balanceQuantity = balance;
	}

	public Integer getbalanceQuantity() {
		return balanceQuantity;
	}

	public Integer getinitialContainers() {
		return initialContainers;
	}

	public void setinitialContainersCount(Integer ini_vials) {
		this.initialContainers = ini_vials;
	}

	public Integer gettotalQuantity() {
		return totalQuantity;
	}

	public void settotalQuantity(Integer total_antigens) {
		this.totalQuantity = total_antigens;
	}

	public Integer getuser() {
		return user;
	}

	public void setuser(Integer vac) {
		this.user = vac;
	}

	public Integer getitem() {
		return item;
	}

	public void setitem(Integer item) {
		this.item = item;
	}

	public Date getRreceivalDate() {
		return receivalDate;
	}

	public void setreceivalDate(Date receivalDate) {
		this.receivalDate = receivalDate;
	}

	public void setexpiryDate(Date date) {
		this.expiryDate = date;
	}

	public void setmanufactureDate(Date date) {
		this.manufactureDate = date;
	}

	public Date getexpiryDate() {
		return expiryDate;
	}

	public Date getmanufactureDate() {
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

	public void setClosingBalance(Integer closingBalance) {
		this.closingBalance = closingBalance;
	}

	public Integer getClosingBalance() {
		return closingBalance;
	}

	public void setDosesReceived(Integer dosesReceived) {
		this.dosesReceived = dosesReceived;
	}

	public void setDosesWasted(Integer dosesWasted) {
		this.dosesWasted = dosesWasted;
	}

	public void setConsumedDoses(Integer consumedDoses) {
		this.consumedDoses = consumedDoses;
	}

	public Integer getDosesReceived() {
		return dosesReceived;
	}

	public Integer getDosesWasted() {
		return dosesWasted;
	}

	public Integer getConsumedDoses() {
		return consumedDoses;
	}

	public Integer getOpeningBalance() {
		return openingBalance;
	}

	public void setOpeningBalance(Integer openingBalance) {
		this.openingBalance = openingBalance;
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

	public Integer getinventoryReferral() {
		return inventoryReferral;
	}

	public void setinventoryReferral(Integer inventoryReferral) {
		this.inventoryReferral = inventoryReferral;
	}

	public Integer getlocation() {
		return location;
	}

	public void setlocation(Integer location) {
		this.location = location;
	}

	public String getreferenceNumber() {
		return referenceNumber;
	}

	public void setreferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public String getreceivedFrom() {
		return receivedFrom;
	}

	public void setreceivedFrom(String receivedFrom) {
		this.receivedFrom = receivedFrom;
	}

	public void setbalanceContainer(Integer balanceContainer) {
		this.balanceContainer = balanceContainer;
	}

	public Integer getbalanceContainer() {
		return balanceContainer;
	}

	public void settotalContainer(Integer totalContainer) {
		this.totalContainer = totalContainer;
	}

	public Integer gettotalContainer() {
		return totalContainer;
	}
}
