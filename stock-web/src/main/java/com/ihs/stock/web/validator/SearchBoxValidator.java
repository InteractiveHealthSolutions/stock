package com.ihs.stock.web.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.ihs.stock.api.beans.SearchInventoryBean;

public class SearchBoxValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return SearchInventoryBean.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
	    SearchInventoryBean sib = (SearchInventoryBean) target;
	    if(sib.getyear() == null && sib.getmonth() == null && sib.getlocationName().isEmpty())
	    {
	    	errors.reject("Nothing.Selected");
	    }
	    
	}

}
