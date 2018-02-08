package com.ihs.stock.web.dwr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.directwebremoting.WebContextFactory;

import com.ihs.stock.api.context.ServiceContextStock;
import com.ihs.stock.api.context.SessionFactoryUtil;
import com.ihs.web.utils.StringUtilities;


public class DWRLocationService {

	public List<Map<String, String>> getLocationList (String[] locationTypes, Integer parentId) {
		
		ServiceContextStock sc = SessionFactoryUtil.getServiceContext();
		try{
		String qry ="SELECT locationId, name, fullName, otherIdentifier FROM location l JOIN locationtype lt ON l.locationType=lt.locationTypeId WHERE lt.typeName IN ("+StringUtilities.getArrayAsString(locationTypes, ",", "'")+", null)"+(parentId==null?"":" AND (parentLocation IS NULL || parentLocation="+parentId+")")+" ORDER BY lt.locationTypeId,l.fullname desc";
		List list = sc.customQueryDAO.getDataBySQL(qry);
		System.out.println(qry);
		List<Map<String, String>> locations = new ArrayList<Map<String,String>>();

		for (Object object : list) {
			HashMap<String, String> loc = new HashMap<String, String>();
			Object[] oar = (Object[]) object;
			loc.put("locationId", oar[0].toString());
			loc.put("name", oar[1].toString());
			loc.put("fullname", oar[2].toString());
			loc.put("otherIdentifier", oar[3]==null?"":oar[3].toString());
			locations.add(loc);
		}		
		return locations;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			sc.closeSession();
		}
		return null;
	}
	
}
