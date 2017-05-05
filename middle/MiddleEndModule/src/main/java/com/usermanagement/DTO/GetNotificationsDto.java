package com.usermanagement.DTO;

public class GetNotificationsDto {
	
	String method = "getNotifications";
	Integer id;
	
	public String getMethod() {
		return method;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}