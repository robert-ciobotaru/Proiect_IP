package com.usermanagement.DTO;

import javax.validation.constraints.Size;


public class NotificationRemoveDto {
	
	private Integer id;
	@Size(max=1024, message="Invalid Length")
	private String method = "removeNotification";
	
	public Integer getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}


}
