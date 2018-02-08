/*package com.ihs.stock.web.validator;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import com.ihs.stock.api.beans.MonthlyUpdateVaccinatorBean;
import com.ihs.stock.api.service.DatabaseValidationsService;

@Component
public class VaccinatorMonthlyUpdateValidator implements Validator {

	public boolean supports(Class<?> clazz) {

		return MonthlyUpdateVaccinatorBean.class.isAssignableFrom(clazz);
	}

	public void validate(Object obj, Errors errors) {

		MonthlyUpdateVaccinatorBean mvb = (MonthlyUpdateVaccinatorBean) obj;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "noOfVials", "EmptyField.Invalid");
		ValidationUtils.rejectIfEmpty(errors, "itemName", "EmptyField.Invalid");
		ValidationUtils.rejectIfEmpty(errors, "expiryDate", "EmptyField.Invalid");
		ValidationUtils.rejectIfEmpty(errors, "manufactureDate", "EmptyField.Invalid");
		if (!mvb.getitemName().isEmpty() && mvb.getnoOfVials() != null) 
		{
			if (!DatabaseValidationsService.validateNoofVials(1, mvb.getitemName(), mvb.getnoOfVials()))
			{
				System.out.println("------------------- in 1");
				errors.rejectValue("noOfVials", "NotSufficient.in.Inventory");
			}
			if (DatabaseValidationsService.vacMonStatsExist(1, mvb.getitemName()))
			{
				System.out.println("------------------- in 2");
				errors.rejectValue("itemName", "Entry.Already.Exist");
			}
		}
        if(mvb.getnoOfVials() != null)
        {
        	if (mvb.getnoOfVials() < 0 || mvb.getnoOfVials() == 0)
    		{
    			errors.rejectValue("noOfVials", "Value.NotValid");
    		}
        }
		
        
		// if(mvb.getexpiryDate().isEqual(LocalDate.now()) ||
		// mvb.getexpiryDate().)
		// {
		// errors.rejectValue("expiryDate", "Invalid.ExpiryDate");
		// }
		//
		// if(mvb.getmanufactureDate().isAfter(LocalDate.now()))
		// {
		// errors.reject("manufactureDate", "Invalid.ManufactureDate");
		// }
		//
		// if(mvb.getexpiryDate().isBefore(LocalDate.now()))
		// {
		// errors.reject("expiryDate", "Invalid.ExpiryDate");
		// }
		//
	}

}
*/