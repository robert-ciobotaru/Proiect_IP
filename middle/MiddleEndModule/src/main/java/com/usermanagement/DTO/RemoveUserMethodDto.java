package com.usermanagement.DTO;

import javax.validation.constraints.Size;

import com.usermanagement.Sanitizer;


public class RemoveUserMethodDto{
	
    Integer id;
    @Size(max=1024, message="Invalid Length")
	String method = "removeUser";
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMethod() {
		return Sanitizer.escapeSql(method);
	}
}
