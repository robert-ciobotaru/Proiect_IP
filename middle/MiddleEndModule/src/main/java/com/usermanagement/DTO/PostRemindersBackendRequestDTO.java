package com.usermanagement.DTO;


import javax.validation.constraints.Size;

import com.usermanagement.Sanitizer;



public class PostRemindersBackendRequestDTO {
	
	@Size(max=1024, message="Invalid Length")
	String method;
	Integer userId;
	PostRemindersFrontendRequestDTO notification;
	
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = Sanitizer.escapeSql(method);
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public PostRemindersFrontendRequestDTO getNotification() {
		return notification;
	}
	public void setNotification(PostRemindersFrontendRequestDTO notification) {
		this.notification = notification;
	}
	
	
	
}
