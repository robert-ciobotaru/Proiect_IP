package com.usermanagement.DTO;

import java.util.ArrayList;
import java.util.List;

public class GetNotificationsResultFromBackEnd {

	public List<NotificationDto> notifications = new ArrayList<NotificationDto>();
	public String error;
	
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public List<NotificationDto> getNotifications() {
		return notifications;
	}
	public void setNotifications(List<NotificationDto> notifications) {
		this.notifications = notifications;
	}
}
