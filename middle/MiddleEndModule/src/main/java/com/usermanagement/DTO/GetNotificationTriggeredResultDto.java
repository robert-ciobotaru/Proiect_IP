package com.usermanagement.DTO;

import com.usermanagement.Sanitizer;

public class GetNotificationTriggeredResultDto {
	public String type;
    public NotificationDto data;
	
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
