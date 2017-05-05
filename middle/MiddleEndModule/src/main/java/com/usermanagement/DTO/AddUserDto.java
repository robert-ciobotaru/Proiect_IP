package com.usermanagement.DTO;

public class AddUserDto {
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
