package com.ihs.stock.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import com.ihs.stock.api.model.ILRDailyStatus;
@Component
public class DailyMorningEntryValidator implements Validator {

	public boolean supports(Class<?> clazz)
	{
		return ILRDailyStatus.class.isAssignableFrom(clazz);
	}

	public void validate(Object obj, Errors error) 
	{
		ILRDailyStatus ilr = (ILRDailyStatus) obj;
		
		if(ilr.getopeningTemprature() == null && ilr.getMorningILRStatus().name().equals("TempratureRecorded"))
		{
			error.rejectValue("openingTemprature", "ILR.input");
		}
		ValidationUtils.rejectIfEmpty(error, "morningILRStatus", "EmptyField.Invalid");
	}

}
