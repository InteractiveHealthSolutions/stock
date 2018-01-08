package com.ihs.stock.api.beans;

public class UpdateRequirementBeanMobile {

	private String item ;
	
	private Long quantity;
	
	private String comment;
	
	public void setcomment(String comments) {
		this.comment = comments;
	}
	 public String getcomment() {
		return comment;
	}
	
	 public void setquantity(Long quantity) {
		this.quantity = quantity;
	}
	 
	 public Long getquantity() {
		return quantity;
	}
	 
	 public void setitem(String item) {
		this.item = item;
	}
	 
	 public String getitem() {
		return item;
	}
	
}
