package com.usermanagement.DTO;

import com.usermanagement.Sanitizer;

public class GetNotificationTriggeredDto {
 
	public String type;
    public NotificationDto data;
    public String error;

 public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = Sanitizer.escapeSql(error);
	}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = Sanitizer.escapeSql(type);
}
public NotificationDto getData() {
	return data;
}
public void setData(NotificationDto data) {
	this.data = data;
}

}
