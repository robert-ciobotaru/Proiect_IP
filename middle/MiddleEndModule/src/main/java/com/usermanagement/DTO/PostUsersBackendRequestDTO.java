package com.usermanagement.DTO;

import javax.validation.constraints.Size;

import com.usermanagement.Sanitizer;


public class PostUsersBackendRequestDTO {
	
	@Size(max=1024, message="Invalid Length")
	private String method = "addUser";
	private PostUsersFrontendRequestDTO user;	

	public PostUsersFrontendRequestDTO getUser() {
		return user;
	}

	public void setUser(PostUsersFrontendRequestDTO user) {
		this.user = user;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = Sanitizer.escapeSql(method);
	}
   
}
