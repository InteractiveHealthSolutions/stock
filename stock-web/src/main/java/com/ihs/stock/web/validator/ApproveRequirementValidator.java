package com.ihs.stock.web.validator;

import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.ihs.stock.api.DAO.DAOInventory;
import com.ihs.stock.api.beans.ApproveRequirementBean;
import com.ihs.stock.api.context.ServiceContextStock;
import com.ihs.stock.api.context.SessionFactoryUtil;
import com.ihs.stock.api.model.Inventory;
import com.ihs.stock.api.model.Requisition;

public class ApproveRequirementValidator implements Validator{

	public boolean supports(Class<?> clazz) {
		
		return ApproveRequirementBean.class.isAssignableFrom(clazz);
	}

//	public void validate(Object target, Errors errors , List<Requisition> requisitions) {
//	
//		ApproveRequirementBean arb = (ApproveRequirementBean) target;
//		SessionFactory sf = SessionFactoryUtil.getSessionFactory(null, "stk-hibernate.cfg.xml");
//		ServiceContextStock scSTK = SessionFactoryUtil.getServiceContext();
//		
//		arb.getcheck()[0] = "donothing";
//		for(int i = 0 ; i < requisitions.size()  ; i++)
//		{
//			if(arb.getcheck()[i].equalsIgnoreCase("approve"))
//			{
//				Inventory inv = scSTK.inventoryDAO.getBalanceForLocationMonthItem(requisitions.get(i).getRequisitionLocation().getParentLocation(), requisitions.get(i).getitem());
//				if(inv.gettotalContainers() < requisitions.get(i).getquantity())
//				{
//					errors.reject("check["+i+"]", "NotSufficient.in.Inventory");
//				}
//			}
//		}
//		
//	}

	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
	}

}
