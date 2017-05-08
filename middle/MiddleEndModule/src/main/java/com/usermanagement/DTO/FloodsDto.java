package com.usermanagement.DTO;

public class FloodsDto {
	public String alertLevel;
	public LocationDto location;
	public String time;
	public String url;
	public String title;
	public String description;
	public String getAlertLevel() {
		return alertLevel;
	}
	public void setAlertLevel(String alertLevel) {
		this.alertLevel = alertLevel;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean validate(){
		if(this.alertLevel==null || this.location.validate()== false || this.time==null || this.url == null || this.description ==null || this.title==null)
			return false;
		return true;
	}
	


}
