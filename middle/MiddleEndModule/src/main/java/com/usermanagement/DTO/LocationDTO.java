package com.usermanagement.DTO;

import com.usermanagement.Sanitizer;

public class LocationDTO {
	String city;
	String country;
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = Sanitizer.escapeSql(city);
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = Sanitizer.escapeSql(country);
	}
	public boolean validate(){
		if(this.city==null || this.country == null)
			return false;
		return true;
	}

}
