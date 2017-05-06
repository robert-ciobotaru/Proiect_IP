package com.usermanagement.DTO;

import javax.validation.constraints.Size;


public class GetNotificationsDto {
	
	@Size(max=1024)
	public String method = "getNotifications";
	public Integer id;
	
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