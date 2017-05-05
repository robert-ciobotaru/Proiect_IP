package com.usermanagement.DTO;

public class NotificationCreateDto {
		
	public String text;
	public Integer time;
	public boolean repeatable;
	public Integer interval;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Integer getTime() {
		return time;
	}
	public void setTime(Integer time) {
		this.time = time;
	}
	public boolean isRepeatable() {
		return repeatable;
	}
	public void setRepeatable(boolean repeatable) {
		this.repeatable = repeatable;
	}
	public Integer getInterval() {
		return interval;
	}
	public void setInterval(Integer interval) {
		this.interval = interval;
	}
	
	
}
