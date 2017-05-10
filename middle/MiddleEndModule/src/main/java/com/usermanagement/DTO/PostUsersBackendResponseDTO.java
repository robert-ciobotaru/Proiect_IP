package com.usermanagement.DTO;

import javax.validation.constraints.Size;

public class PostUsersBackendResponseDTO {
	private Integer userId;
	@Size(max=1024, message="Invalid Length")
	private String error;

	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
}
