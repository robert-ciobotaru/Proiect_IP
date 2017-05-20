package com.usermanagement.DTO;

import javax.validation.constraints.Size;


public class PostUsersBackendRequestDTO {
	
	@Size(max=1024, message="Invalid Length")
	private String method = "addUser";
	private PostUsersFrontendRequestDTO data;	

	public PostUsersFrontendRequestDTO getData() {
		return data;
	}

	public void setData(PostUsersFrontendRequestDTO data) {
		this.data = data;
	}

	public String getMethod() {
		return method;
	}

//	public void setMethod(String method) {
//		this.method = Sanitizer.escapeSql(method);
//	}
   
}
