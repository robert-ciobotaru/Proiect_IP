package com.usermanagement.DTO;

public class GetTriggeredNotificationsDto {
	
	String method = "getTriggeredNotifications";
	Long id = 2L;
	
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
}