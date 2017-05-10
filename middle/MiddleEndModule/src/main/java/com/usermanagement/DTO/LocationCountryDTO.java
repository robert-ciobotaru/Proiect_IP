package com.usermanagement.DTO;

import com.usermanagement.Sanitizer;

public class LocationCountryDTO {
	String country;
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = Sanitizer.escapeSql(country);
	}
	public boolean validate(){
		if(this.country == null)
			return false;
		return true;
	}

}
