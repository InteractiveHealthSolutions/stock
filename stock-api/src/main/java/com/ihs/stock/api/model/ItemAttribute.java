package com.ihs.stock.api.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

import javax.persistence.Column;

@Entity
@Table(name = "item_attribute")
public class ItemAttribute {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "attribute_id")
	int attributeId;

	@Column(name = "value")
	private String value;

	private Integer item;

	private Integer itemAttributeType;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_created")
	private Date dateCreated;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_edited")
	private Date dateEdited;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_voided")
	private Date dateVoided;

	public Integer getitem() {
		return item;
	}

	public void setitem(Integer item) {
		this.item = item;
	}

	public int getattributeId() {
		return attributeId;
	}

	public void setattributeId(int attributeId) {
		this.attributeId = attributeId;
	}

	public void setitemAttributeType(Integer itemAttributeType) {
		this.itemAttributeType = itemAttributeType;
	}

	public Integer getitemAttributeType() {
		return itemAttributeType;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
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
