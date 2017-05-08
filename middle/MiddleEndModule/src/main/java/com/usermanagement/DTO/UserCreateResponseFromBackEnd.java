package com.usermanagement.DTO;

import javax.validation.constraints.Size;

public class UserCreateResponseFromBackEnd {
	private Integer id;
	@Size(max=1024, message="Invalid Length")
	private String error;

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
