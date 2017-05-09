package com.usermanagement.DTO;

import javax.validation.constraints.Size;

import com.usermanagement.Sanitizer;


public class AddUserDto {
	
	@Size(max=1024, message="Invalid Length")
	private String method = "addUser";
	private UserCreateDto user;	

	public UserCreateDto getUser() {
		return user;
	}

	public void setUser(UserCreateDto user) {
		this.user = user;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = Sanitizer.escapeSql(method);
	}
   
}
