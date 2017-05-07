package com.usermanagement.DTO;

public class UserCreateResponseFromBackEnd {
	public Integer id;
	public String error;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
}
