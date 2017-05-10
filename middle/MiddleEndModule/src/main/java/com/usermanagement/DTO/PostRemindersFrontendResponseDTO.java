package com.usermanagement.DTO;

import com.usermanagement.Sanitizer;

public class PostRemindersFrontendResponseDTO {
	
	public Integer id;
	public String text;
	public Integer time;
	public Boolean repeatable;
	public Integer interval;
	
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
	public boolean validate(){
		if(this.id==null || this.text==null || this.time==null || this.repeatable == null || this.interval == null)
			return false;
		
		return true;
	}
	
}
