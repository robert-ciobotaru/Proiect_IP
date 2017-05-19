package com.usermanagement.DTO;

import javax.validation.constraints.Size;

import com.usermanagement.Sanitizer;


public class DeleteRemindersByIdBackendRequestDTO {
	
	private Integer notificationId;
	@Size(max=1024, message="Invalid Length")
	private String method = "removeNotification";
	
	public Integer getNotificationId() {
		return notificationId;
	}
	
	public void setNotificationId(int notificationId) {
		this.notificationId = notificationId;
	}
	public String getMethod() {
		return method;
	}
//	public void setMethod(String method) {
//		this.method = Sanitizer.escapeSql(method);
//	}


}
