package com.usermanagement.DTO;


import javax.validation.constraints.Size;

import com.usermanagement.Sanitizer;


public class GetRemindersMethodDto {
 
	public Integer id;
	@Size(max=1024, message="Invalid Length")
	public String method;

 public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public String getMethod() {
	return method;
}
public void setMethod(String method) {
	this.method = Sanitizer.escapeSql(method);
}
}
