package com.ihs.stock.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="item_attribute_type")
public class ItemAttributeType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="item_attribute_typeId")
	public Integer itemAttributeTypeId;
	
	@Column(name="attribute_name")
	public String attributeName;

	@Column(name="display_name")
	public String displayName;

	@Column(name="description")
	public String description;

	@Column(name="category")
	public String category;
	
	public void setattributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	
	public String getattributeName() {
		return attributeName;
	}
	
	public void setitemAttributeTypeId(Integer itemAttributeTypeId) {
		this.itemAttributeTypeId = itemAttributeTypeId;
	}
	
	public Integer getitemAttributeTypeId() {
		return itemAttributeTypeId;
	}
	
	public void setdisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public String getdisplayName() {
		return displayName;
	}
	
	public String getdescription() {
		return description;
	}
	
	public void setdescription(String description) {
		this.description = description;
	}
	
	public String getcategory() {
		return category;
	}
	
	public void setcategory(String category) {
		this.category = category;
	} 

}
