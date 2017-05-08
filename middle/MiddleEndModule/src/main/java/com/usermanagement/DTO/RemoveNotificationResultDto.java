package com.usermanagement.DTO;

import javax.validation.constraints.Size;

public class RemoveNotificationResultDto{
	
	@Size(max=1024, message="Invalid Length")
	private String error;
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}

}
