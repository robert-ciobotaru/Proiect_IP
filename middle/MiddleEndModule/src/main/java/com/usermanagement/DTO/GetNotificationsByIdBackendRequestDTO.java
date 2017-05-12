package com.usermanagement.DTO;

import javax.validation.constraints.Size;

import com.usermanagement.Sanitizer;


public class GetNotificationsByIdBackendRequestDTO {
	
	@Size(max=1024, message="Invalid Length")
	public String method;
	
	public Integer userId;
	
	public void setMethod(String method) {
		this.method = Sanitizer.escapeSql(method);
	}
	
	public String getMethod() {
		return method;
	}
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}