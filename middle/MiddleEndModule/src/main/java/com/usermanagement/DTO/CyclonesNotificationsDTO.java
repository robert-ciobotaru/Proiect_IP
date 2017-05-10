package com.usermanagement.DTO;

import com.usermanagement.Sanitizer;

public class CyclonesNotificationsDTO {
	public String alertLevel;
	public LocationCountryDTO location;
	public String time;
	public String url;
	public String title;
	public String description;
	public String getAlertLevel() {
		return alertLevel;
	}
	public void setAlertLevel(String alertLevel) {
		this.alertLevel = Sanitizer.escapeSql(alertLevel);
	}
	public LocationCountryDTO getLocation() {
		return location;
	}
	public void setLocation(LocationCountryDTO location) {
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = Sanitizer.escapeSql(description);
	}
	public boolean validate(){
		if(this.alertLevel==null || this.location.validate()== false || this.time==null || this.url == null || this.description ==null || this.title==null)
			return false;
		return true;
	}
	
	


}
