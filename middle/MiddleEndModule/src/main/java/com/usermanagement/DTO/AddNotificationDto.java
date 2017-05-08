package com.usermanagement.DTO;


import javax.validation.constraints.Size;



public class AddNotificationDto {
	
	@Size(max=1024, message="Invalid Length")
	String method = "addNotification";
	Integer id;
	NotificationCreateDto notification;
	
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public NotificationCreateDto getNotification() {
		return notification;
	}
	public void setNotification(NotificationCreateDto notification) {
		this.notification = notification;
	}
	
	
	
}
