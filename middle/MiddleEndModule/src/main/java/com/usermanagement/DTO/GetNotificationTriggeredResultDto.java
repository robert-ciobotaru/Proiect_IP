package com.usermanagement.DTO;

public class GetNotificationTriggeredResultDto {
	public String type;
    public NotificationDto data;
	
    public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public NotificationDto getData() {
		return data;
	}
	public void setData(NotificationDto data) {
		this.data = data;
	}
}
