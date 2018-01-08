package com.ihs.stock.web.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.ihs.stock.api.beans.SearchBean;

public class ILRValidator implements Validator {

	public boolean supports(Class<?> clazz) {
	
		return SearchBean.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
	
	    SearchBean sib = (SearchBean) target;
	    if(sib.getlocationName().isEmpty() && sib.getmonth() == null && sib.getyear() == null)
	    {
	    	errors.reject("All.Parameters");
	    }
	    else if(sib.getlocationName().isEmpty())
	    {
	    	errors.rejectValue("locationName", "EmptyField.Invalid");
	    }
	    else if(sib.getmonth() == null)
	    {
	    	errors.rejectValue("month", "EmptyField.Invalid");
	    }
	    else if(sib.getyear() == null)
	    {
	    	errors.rejectValue("year", "EmptyField.Invalid");
	    }
	    
	    
	}

}