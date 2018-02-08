package com.ihs.web.utils;

import java.util.List;

public class StringUtilities {

	public static String getListAsString(List<String> collection,String separator, String encapsulator){
		StringBuilder strb=new StringBuilder();
		int len=collection.size();
		
		int index=0;
		for (String string : collection) {
			if(encapsulator != null){
				string = encapsulator+string+encapsulator;
			}
			
			if(index==len-1){
				strb.append(string);
			}else{
				strb.append(string+separator);
			}
			index++;
		}
		return strb.toString();
	}
	
	public static String getArrayAsString(String[] collection,String separator, String encapsulator){
		StringBuilder strb=new StringBuilder();
		int len=collection.length;
		
		int index=0;
		for (String string : collection) {
			if(encapsulator != null){
				string = encapsulator+string+encapsulator;
			}
			
			if(index==len-1){
				strb.append(string);
			}else{
				strb.append(string+separator);
			}
			index++;
		}
		return strb.toString();
	}
	public static <T> T coalesce(T ...items) {
		for (T i : items) if (i != null && i != "" && i != " ") return i;
		return null;
	}
	
}
