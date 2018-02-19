package com.ihs.stock.web.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.ihs.stock.api.beans.DayEndEntryBean;

public class DailyDayEndEntryValidator implements Validator{

	public boolean supports(Class<?> clazz) {
		
		return DayEndEntryBean.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		
		DayEndEntryBean deb = (DayEndEntryBean) target;
		
		ValidationUtils.rejectIfEmpty(errors, "closingTempratutre", "EmptyField.Invalid");
		ValidationUtils.rejectIfEmpty(errors, "usedQuantityCount", "EmptyField.Invalid");
		ValidationUtils.rejectIfEmpty(errors, "itemName", "EmptyField.Invalid");
		ValidationUtils.rejectIfEmpty(errors, "wastedQuantityCount", "EmptyField.Invalid");
//		if(deb.getclosingTemprature() < 0)
//		{
//			errors.rejectValue("openingTempratutre", "Value.NotValid");
//		}
		
		
	}

}
