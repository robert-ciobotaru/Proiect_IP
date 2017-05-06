package com.usermanagement.DTO;


import javax.validation.constraints.Size;


public class GetTriggeredNotificationMethodDto {
 
	public Integer id;
	@Size(max=1024)
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
	this.method = method;
}
}
