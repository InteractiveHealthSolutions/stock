package com.ihs.stock.web.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.ihs.stock.api.beans.AddItemBean;

public class AddItemValidator implements Validator{
	
	private Pattern pattern;
	private Matcher matcher;
    private String numberOnly = "[0-9]+";
    
	public boolean supports(Class<?> clazz) {
		
		return AddItemBean.class.isAssignableFrom(clazz);
		
	}

	public void validate(Object obj, Errors errors) {
		
		AddItemBean aib = (AddItemBean) obj;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "EmptyField.Invalid");
		ValidationUtils.rejectIfEmpty(errors, "type", "EmptyField.Invalid");
		ValidationUtils.rejectIfEmpty(errors, "enclosedQuantity", "EmptyField.Invalid");
		ValidationUtils.rejectIfEmpty(errors, "expirationDurationAfterOpening", "EmptyField.Invalid");
		ValidationUtils.rejectIfEmpty(errors, "expiryUnit", "EmptyField.Invalid");
		
		if(aib.getenclosedQuantity() != null)
		{
			pattern = Pattern.compile(numberOnly);
			matcher = pattern.matcher(aib.getenclosedQuantity().toString());
			if(!matcher.matches())
			{
				errors.rejectValue("enclosedQuantity", "Not.A.Number");
			}
		}
	}

}
