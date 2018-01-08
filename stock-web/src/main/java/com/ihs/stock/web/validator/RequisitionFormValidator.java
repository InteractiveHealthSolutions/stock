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
		
		
		UpdateRequirementBean urb = (UpdateRequirementBean) target;
	int j = 0;
		for(int i = 0 ; i < urb.getquantity().size() ; i++)
		{
			if(!urb.getquantity().get(i).isEmpty())
			{
				boolean check = false;
				for(int x = 0 ; x < urb.getquantity().get(i).length() ; x++)
				{
					if(!Character.isDigit(urb.getquantity().get(i).charAt(x)))
					{
						check = true;
					}
				}
				if(check == true)
				{
					errors.rejectValue("quantity["+i+"]", "Value.NotValid");
				}
				else
				{
					if(urb.getquantity().get(i).length() >= 20)
					{
						errors.rejectValue("quantity["+i+"]", "Invalid.Quantity");
					}
					else
					{
						if(/*Long.parseLong(urb.getquantity().get(i)) > 15000 || */Long.parseLong(urb.getquantity().get(i)) < 1)
						{
							errors.rejectValue("quantity["+i+"]", "Value.NotValid");
						}
					}
				}
				
			}
			else
			{
				j++;
			}
//			if(urb.getquantity().get(i).isEmpty() && !urb.getcomments().get(i).isEmpty())
//			{
//				errors.rejectValue("comments["+i+"]", "Comment.Only");
//			}
			if(!urb.getcomments().get(i).isEmpty())
			{
				boolean check = false;
				for(int x = 0 ; x < urb.getcomments().get(i).length() ; x++)
				{
					if(!Character.isDigit(urb.getcomments().get(i).charAt(x)))
					{
						check = true;
					}
				}
				if(check == false)
				{
					errors.rejectValue("comments["+i+"]", "Invalid.Comment");
				}
				if(urb.getcomments().get(i).length() > 500)
				{
					errors.rejectValue("comments["+i+"]", "Invalid.Comment");
				}
			}
			
		}
		if(j == urb.getquantity().size())
		{
			errors.rejectValue("quantity[0]", "All.Empty");
		}
		
		
		
	}

}
