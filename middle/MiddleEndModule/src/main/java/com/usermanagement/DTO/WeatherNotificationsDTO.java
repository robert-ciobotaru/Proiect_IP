package com.usermanagement.DTO;

import com.usermanagement.Sanitizer;

public class WeatherNotificationsDTO {
	public LocationDTO location;
	public String text;
public LocationDTO getLocation() {
	return location;
}
public void setLocation(LocationDTO location) {
	this.location = location;
}
public String getText() {
	return text;
}
public void setText(String text) {
	this.text = Sanitizer.escapeSql(text);
}
public boolean validate(){
	if(location.validate()==false || this.text==null)
		return false;
	return true;
	
	
}
	
}
