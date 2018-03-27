package com.ihs.stock.api.beans;

public class DayEndEntryBeanMobile {

	private String item;
	
	private Integer quantityWasted;
	
	private Integer quantityUsed;
	private Integer vialsConsumed;
	public Integer getquantityUsed() {
		return quantityUsed;
	}
	
	public void setquantityUsed(Integer quantityUsed) {
		this.quantityUsed = quantityUsed;
	}
	public Integer getquantityWasted() {
		return quantityWasted;
	}
	
	public void setquantityWasted(Integer quantityWasted) {
		this.quantityWasted = quantityWasted;
	}
	public void setitem(String item) {
		this.item = item;
	}
	
	public String getitem() {
		return item;
	}
	public Integer getvialsConsumed() {
		return vialsConsumed;
	}
	
	public void setvialsConsumed(Integer vialsConsumed) {
		this.vialsConsumed = vialsConsumed;
	}
}
