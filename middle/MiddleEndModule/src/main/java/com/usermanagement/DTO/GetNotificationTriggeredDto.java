package com.usermanagement.DTO;

import com.usermanagement.Sanitizer;

public class GetNotificationTriggeredDto {
 
	public String type;
    public PostRemindersFrontendResponseDTO data;
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
public PostRemindersFrontendResponseDTO getData() {
	return data;
}
public void setData(PostRemindersFrontendResponseDTO data) {
	this.data = data;
}

}
