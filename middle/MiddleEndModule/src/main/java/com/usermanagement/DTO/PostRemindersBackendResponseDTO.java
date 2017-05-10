package com.usermanagement.DTO;

import com.usermanagement.Sanitizer;

public class PostRemindersBackendResponseDTO {
 
  public Integer notificationId;
  public String error;
  
public Integer getNotificationId() {
	return notificationId;
}

public void setNotificationId(Integer notificationId) {
	this.notificationId = notificationId;
}

public String getError() {
	return error;
}

public void setError(String error) {
	this.error = error;
}
  
}