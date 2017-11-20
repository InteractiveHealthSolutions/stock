package com.ihs.stock.api.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.ForeignKey;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Column;

@Entity
@Table(name="item_attribute")
public class ItemAttribute {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="attribute_id")
	int attributeId;
	
	@Column(name= "value")
	private String value;
	
	@ManyToOne(targetEntity = Item.class , fetch = FetchType.EAGER)
	@JoinColumn(name="item")
	@ForeignKey(name = "itemattribute_itemid_item_mappedId_FK")
	Item item;
	
	@ManyToOne(targetEntity = ItemAttributeType.class , fetch = FetchType.EAGER)
	@JoinColumn(name="item_attribute_type")
	@ForeignKey(name = "itemattribute_attributetypeid_itemattributetype_mappedId_FK")
	ItemAttributeType itemAttributeType;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_created")
	private Date dateCreated;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_edited")
	private Date dateEdited;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_voided")
	private Date dateVoided;
	
	public Item getitem() {
		return item;
	}
	
	public void setitem(Item item) {
		this.item = item;
	}
	
	public int getattributeId() {
		return attributeId;
	}
	
	public void setattributeId(int attributeId) {
		this.attributeId = attributeId;
	}
	
	public void setitemAttributeType(ItemAttributeType itemAttributeType) {
		this.itemAttributeType = itemAttributeType;
	}
	
	public ItemAttributeType getitemAttributeType() {
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
