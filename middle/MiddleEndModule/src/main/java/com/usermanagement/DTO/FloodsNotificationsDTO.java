package com.usermanagement.DTO;

import com.usermanagement.Sanitizer;

public class FloodsNotificationsDTO {
	public String alertLevel;
    public String country;
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

	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
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
		if(this.alertLevel==null || this.country == null || this.time==null || this.url == null || this.description ==null || this.title==null)
			return false;
		return true;
	}
	


}
