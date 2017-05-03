package com.usermanagement.DTO;

public class NotificationDto {
	
	public Integer id = 2;
	public String text = "Trezirea de dimineata";
	public String period = "none";
	public Integer day_timestamp = 2315;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public Integer getDay_timestamp() {
		return day_timestamp;
	}
	public void setDay_timestamp(Integer day_timestamp) {
		this.day_timestamp = day_timestamp;
	}
}
