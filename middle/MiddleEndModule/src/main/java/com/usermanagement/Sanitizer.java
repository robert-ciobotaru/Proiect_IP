package com.usermanagement;

import org.springframework.util.StringUtils;

public class Sanitizer {
	public static String escapeSql(String str) {
		if (str == null) {
		    return null;
		}
		return StringUtils.replace(str, "'", "''");
	}
}
