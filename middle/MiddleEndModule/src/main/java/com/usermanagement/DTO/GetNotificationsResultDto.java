package com.usermanagement.DTO;

import java.util.ArrayList;
import java.util.List;

public class GetNotificationsResultDto implements ResponseInterfaceDto {
	public List<PostRemindersFrontendResponseDTO> notifications = new ArrayList<PostRemindersFrontendResponseDTO>();
	
	public List<PostRemindersFrontendResponseDTO> getNotifications() {
		return notifications;
	}
	public void setNotifications(List<PostRemindersFrontendResponseDTO> notifications) {
		this.notifications = notifications;
	}
	
	
}
