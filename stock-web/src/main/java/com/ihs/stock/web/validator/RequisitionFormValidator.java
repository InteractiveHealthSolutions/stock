package com.ihs.stock.web.validator;

import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.ihs.stock.api.beans.UpdateRequirementBean;

public class RequisitionFormValidator implements Validator{

	public boolean supports(Class<?> clazz) {
		
		return UpdateRequirementBean.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		
		Pattern patternForQuantity = Pattern.compile(".*[0-9].*");
		Pattern patternForComment = Pattern.compile("[a-zA-Z\\s']+");
		
		UpdateRequirementBean urb = (UpdateRequirementBean) target;
		
		for(int i = 0 ; i < urb.getquantity().size() ; i++)
		{
			if(!urb.getquantity().get(i).isEmpty())
			{
				if(!patternForQuantity.matcher(urb.getquantity().get(i)).matches())
				{
					errors.rejectValue("quantity["+i+"]", "Value.NotValid");
				}
				else
				{
					if(Integer.parseInt(urb.getquantity().get(i)) > 15000)
					{
					
						errors.rejectValue("quantity["+i+"]", "Value.NotValid");
					}
				}
				
			}
			if(urb.getquantity().get(i).isEmpty() && !urb.getcomments().get(i).isEmpty())
			{
				errors.rejectValue("comments["+i+"]", "Comment.Only");
			}
			if(!patternForComment.matcher(urb.getquantity().get(i)).matches())
			{
				errors.rejectValue("comments["+i+"]", "Value.NotValid");
			}
		}
		
		
		
	}

}
