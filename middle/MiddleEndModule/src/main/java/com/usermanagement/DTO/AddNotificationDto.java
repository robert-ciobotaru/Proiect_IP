package com.usermanagement.DTO;

public class AddNotificationDto {
	
	String method = "addNotification";
	Long id = 2L;
	NotificationCreateDto notification;
	
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public NotificationCreateDto getNotification() {
		return notification;
	}
	public void setNotification(NotificationCreateDto notification) {
		this.notification = notification;
	}
	
}
