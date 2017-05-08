package com.usermanagement.DTO;

import javax.validation.constraints.Size;


public class RemoveUserMethodDto{
	
    Integer id;
	@Size(max=1024)
	String method = "removeUser";
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMethod() {
		return method;
	}
}
