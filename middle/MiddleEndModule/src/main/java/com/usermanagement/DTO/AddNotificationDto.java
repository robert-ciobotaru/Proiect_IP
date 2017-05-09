package com.usermanagement.DTO;


import javax.validation.constraints.Size;

import com.usermanagement.Sanitizer;



public class AddNotificationDto {
	
	@Size(max=1024, message="Invalid Length")
	String method = "addNotification";
	Integer userId;
	NotificationCreateDto notification;
	
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
	public NotificationCreateDto getNotification() {
		return notification;
	}
	public void setNotification(NotificationCreateDto notification) {
		this.notification = notification;
	}
	
	
	
}
