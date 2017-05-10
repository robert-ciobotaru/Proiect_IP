package com.usermanagement.DTO;

import javax.validation.constraints.Size;

import com.usermanagement.Sanitizer;


public class GetNotificationsByIdBackendRequestDTO {
	
	@Size(max=1024, message="Invalid Length")
	public String method;
	
	public Integer id;
	
	public void setMethod(String method) {
		this.method = Sanitizer.escapeSql(method);
	}
	
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