package com.ihs.stock.api.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.ihs.stock.api.DAO.DAOItem;
import com.ihs.stock.api.DAO.DAOItemAttribute;
import com.ihs.stock.api.DAO.DAOItemAttributeType;
import com.ihs.stock.api.DAO.DAOItemType;
import com.ihs.stock.api.beans.AddItemBean;
import com.ihs.stock.api.model.App;
import com.ihs.stock.api.model.Item;
import com.ihs.stock.api.model.ItemAttribute;
import com.ihs.stock.api.model.ItemAttributeType;
import com.ihs.stock.api.model.ItemType;

public class AddItemsService {
	
	
	public Item AddItems(AddItemBean aib) throws ParseException
	{
		DAOItemType itemTypeDAO = new DAOItemType();
		ItemType itemType = itemTypeDAO.getByName(aib.gettype());
		
		Item item = new Item();
		
		item.setname(aib.getname());
		item.setshortName(aib.getshortName());
		item.setbrand(aib.getbrand());
		item.setmanufacturer(aib.getmanufacturer());
		item.setexpiryAfterOpening(aib.getexpirationDurationAfterOpening());
		item.setenclosedQuantity(aib.getenclosedQuantity());
		item.setexpiryUnit(aib.getexpiryUnit());
		item.setitemType(itemType);
		item.setbarcode(aib.getbarcode());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(sdf.format(new Date()));
		
		item.setdateCreated(date);
		
		DAOItem itemDAO = new DAOItem();
		itemDAO.save(item);
		//item = itemDAO.getByName(item.getname());
		//generateDynamicColumns(item,aib.getcolumnName(),aib.getcolumnValue());
		return item;
		
	}
	
	public ItemAttributeType addItemAttributeType(ItemAttributeType atype)
	{
		DAOItemAttributeType type = new DAOItemAttributeType();
		type.save(atype);
		return atype;
	}
	
    /*private void generateDynamicColumns(Item item ,ArrayList<String> columnName , ArrayList<String> columnDescription)
	{
    	
    	DAOItemAttribute itemAttributeDAO = new DAOItemAttribute();
    	List<?> existingColumns = itemAttributeDAO.getColumnNames();
    	
    	ItemAttributes ia = new ItemAttributes();
		ia.setitem(item);
		itemAttributeDAO.save(ia);
		
		//System.out.println(columnName.toString());
		//System.out.println(columnDescription.toString());
		for(int i = 0 ; i < columnName.size() ; i++)
    	{
    		if(existingColumns.contains(columnName.get(i)))
    		{
    			itemAttributeDAO.update(item, columnName.get(i), columnDescription.get(i));
    			columnName.remove(i);
    			columnDescription.remove(i);
    		}
    	}
	
		if(columnName.size() > 0)
		{
			itemAttributeDAO.AddColumn(columnName);
			itemAttributeDAO.bulkUpdate(item, columnName, columnDescription);
		}
		
	}*/

}
