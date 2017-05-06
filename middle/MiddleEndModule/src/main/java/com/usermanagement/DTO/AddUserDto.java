package com.usermanagement.DTO;

import javax.validation.constraints.Size;


public class AddUserDto {
	
	@Size(max=1024)
	public String method = "addUser";
	public UserCreateDto user;	

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
		this.method = method;
	}
   
}
