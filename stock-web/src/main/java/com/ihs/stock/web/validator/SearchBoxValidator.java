package com.ihs.stock.web.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.ihs.stock.api.beans.SearchBean;



public class SearchBoxValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return SearchBean.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
	    SearchBean sib = (SearchBean) target;
	    if(sib.getyear() == null && sib.getmonth() == null && sib.getlocationName().isEmpty())
	    {
	    	errors.reject("Nothing.Selected");
	    }
	    
	}

}
