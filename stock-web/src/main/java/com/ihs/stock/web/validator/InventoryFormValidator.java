package com.ihs.stock.web.validator;

import javax.management.InstanceAlreadyExistsException;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.ihs.stock.api.DAO.DAOInventory;
import com.ihs.stock.api.DAO.DAOItem;

import com.ihs.stock.api.beans.InventoryBean;

@Component
public class InventoryFormValidator implements Validator {


	public boolean supports(Class<?> clazz) {
		
		return InventoryBean.class.isAssignableFrom(clazz);
		
	}

	public void validate(Object obj, Errors error) {
		
		
		InventoryBean inventoryBean = (InventoryBean) obj;

		ValidationUtils.rejectIfEmptyOrWhitespace(error, "inventoryInitialVialsCount", "EmptyField.Invalid");
		ValidationUtils.rejectIfEmpty(error, "itemName", "Value.NotValid");
		ValidationUtils.rejectIfEmptyOrWhitespace(error, "consumerLocation", "Value.NotValid");
		if(inventoryBean.getinventoryInitialVialsCount() != null)
		{
			if(inventoryBean.getinventoryInitialVialsCount() < 0 || inventoryBean.getinventoryInitialVialsCount() == 0)
			{
				error.reject("inventoryInitialVialsCount", "Value.NotValid");
			}
		}
		else
		{
			error.reject("inventoryInitialVialsCount" , "EmptyField.Invalid");
		}
		if(inventoryBean.getconsumerLocation().equals(inventoryBean.getparentLocation()))
		{
			error.reject("consumerLocation" , "Invalid.Transaction");
		}
	
		
	
	}

}
