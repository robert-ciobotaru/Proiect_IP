package com.usermanagement.DTO;

import javax.validation.constraints.Size;

public class ErrorDto implements ResponseInterfaceDto  {
	
	@Size(max=1024)
	public String error;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
}
