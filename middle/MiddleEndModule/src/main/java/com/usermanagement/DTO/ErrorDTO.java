package com.usermanagement.DTO;

import javax.validation.constraints.Size;

import com.usermanagement.Sanitizer;

public class ErrorDTO {
	
	@Size(max=1024, message="Invalid Length")
	public String error;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = Sanitizer.escapeSql(error);
	}
	
}
