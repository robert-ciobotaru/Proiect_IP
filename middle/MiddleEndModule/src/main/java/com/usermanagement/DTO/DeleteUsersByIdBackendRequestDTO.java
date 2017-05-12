package com.usermanagement.DTO;

import javax.validation.constraints.Size;

import com.usermanagement.Sanitizer;


public class DeleteUsersByIdBackendRequestDTO{
	
    Integer userId;
    @Size(max=1024, message="Invalid Length")
	String method = "removeUser";
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getMethod() {
		return Sanitizer.escapeSql(method);
	}
}
