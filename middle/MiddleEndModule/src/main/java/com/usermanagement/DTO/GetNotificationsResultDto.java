package com.usermanagement.DTO;

import java.util.ArrayList;
import java.util.List;

public class GetNotificationsResultDto implements ResponseInterfaceDto {
	public List<NotificationDto> notifications = new ArrayList<NotificationDto>();
	
	public List<NotificationDto> getNotifications() {
		return notifications;
	}
	public void setNotifications(List<NotificationDto> notifications) {
		this.notifications = notifications;
	}
	
}