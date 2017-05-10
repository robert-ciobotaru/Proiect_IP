package com.usermanagement.DTO;

import com.usermanagement.Sanitizer;

public class GetNotificationTriggeredResultDto {
	public String type;
    public PostRemindersFrontendResponseDTO data;
	
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
