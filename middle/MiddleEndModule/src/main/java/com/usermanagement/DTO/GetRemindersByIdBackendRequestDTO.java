package com.usermanagement.DTO;


import javax.validation.constraints.Size;

import com.usermanagement.Sanitizer;


public class GetRemindersByIdBackendRequestDTO {
 
	public Integer userId;
	@Size(max=1024, message="Invalid Length")
	public String method;

 public Integer getUserId() {
	return userId;
}
public void setUserId(Integer userId) {
	this.userId = userId;
}
public String getMethod() {
	return method;
}
public void setMethod(String method) {
	this.method = Sanitizer.escapeSql(method);
}
}
