package com.usermanagement.DTO;

import javax.validation.constraints.Size;

import com.usermanagement.Sanitizer;

public class NotificationCreateDto {
	
	@Size(max=1024, message="Invalid Length")
	public String text;
	public Integer time;
	public Boolean repeatable;
	public Integer interval;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = Sanitizer.escapeSql(text);
	}
	public Integer getTime() {
		return time;
	}
	public void setTime(Integer time) {
		this.time = time;
	}
	public Boolean isRepeatable() {
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
