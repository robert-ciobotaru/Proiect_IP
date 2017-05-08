package com.usermanagement.DTO;

public class LocationDto {
	String city;
	String country;
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public boolean validate(){
		if(this.city==null || this.country == null)
			return false;
		return true;
	}

}
