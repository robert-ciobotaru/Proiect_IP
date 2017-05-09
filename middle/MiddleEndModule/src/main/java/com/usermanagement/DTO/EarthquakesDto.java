package com.usermanagement.DTO;

import com.usermanagement.Sanitizer;

public class EarthquakesDto {
	public Integer magnitude;
	public LocationDto location;
	public String time;
	public String url;
	public String title;
	public Integer getMagnitude() {
		return magnitude;
	}
	public void setMagnitude(Integer magnitude) {
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
		this.time = Sanitizer.escapeSql(time);
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = Sanitizer.escapeSql(url);
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = Sanitizer.escapeSql(title);
	}
	public boolean validate(){
		if(this.magnitude==null || this.location.validate()==false || this.time==null || this.url ==null || this.title==null)
			return false;
		return true;
	}


}
