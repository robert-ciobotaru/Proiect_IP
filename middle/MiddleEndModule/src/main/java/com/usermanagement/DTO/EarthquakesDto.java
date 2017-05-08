package com.usermanagement.DTO;

public class EarthquakesDto {
	public String magnitude;
	public LocationDto location;
	public String time;
	public String url;
	public String title;
	public String getMagnitude() {
		return magnitude;
	}
	public void setMagnitude(String magnitude) {
		this.magnitude = magnitude;
	}
	public LocationDto getLocation() {
		return location;
	}
	public void setLocation(LocationDto location) {
		this.location = location;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public boolean validate(){
		if(this.magnitude==null || this.location.validate()==false || this.time==null || this.url ==null || this.title==null)
			return false;
		return true;
	}


}
