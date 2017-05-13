package com.usermanagement.DTO;

import com.usermanagement.Sanitizer;

public class EarthquakesNotificationsDTO {
	public Integer magnitude;
	public String place;
	public String time;
	public String url;
	public String title;
	public Integer getMagnitude() {
		return magnitude;
	}
	public void setMagnitude(Integer magnitude) {
		this.magnitude = magnitude;
	}

	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
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
		if(this.magnitude==null || this.place == null || this.time==null || this.url ==null || this.title==null)
			return false;
		return true;
	}


}
