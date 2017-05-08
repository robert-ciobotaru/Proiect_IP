package com.usermanagement.DTO;

import java.util.ArrayList;
import java.util.List;

public class NotificationsListDto {
	public List<NotificationDto> remindersList = new ArrayList<NotificationDto>();

	public List<NotificationDto> getNotifications() {
		return remindersList;
	}

	public void setNotifications(List<NotificationDto> notifications) {
		this.remindersList = notifications;
	}
	
}
