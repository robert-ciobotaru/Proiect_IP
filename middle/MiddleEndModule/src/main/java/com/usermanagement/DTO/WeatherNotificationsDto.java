package com.usermanagement.DTO;

public class WeatherNotificationsDto {
	public LocationDto location;
	public String text;
public LocationDto getLocation() {
	return location;
}
public void setLocation(LocationDto location) {
	this.location = location;
}
public String getText() {
	return text;
}
public void setText(String text) {
	this.text = text;
}
public boolean validate(){
	if(location.validate()==false || this.text==null)
		return false;
	return true;
	
	
}
	
}