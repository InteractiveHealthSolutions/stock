package com.ihs.stock.web.utils;

public class StringUtilities {

	public static <T> T coalesce(T ...items) {
		for (T i : items) if (i != null && i != "" && i != " ") return i;
		return null;
	}
	
}

