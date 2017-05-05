package com.usermanagement.DTO;

public class UserCreateResponseFromBackEnd {
	public UserDto user;
	public String error;

	public UserDto getUser() {
		return user;
	}
	public void setUser(UserDto user) {
		this.user = user;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
}
