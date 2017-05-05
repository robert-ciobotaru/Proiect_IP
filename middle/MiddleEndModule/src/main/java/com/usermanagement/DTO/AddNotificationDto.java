package com.usermanagement.DTO;

public class AddNotificationDto {
	
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
