package com.usermanagement.DTO;

import java.util.ArrayList;
import java.util.List;

public class GetRemindersByIdFrontendResponseDTO {
	public List<PostRemindersFrontendResponseDTO> remindersList = new ArrayList<PostRemindersFrontendResponseDTO>();

	public List<PostRemindersFrontendResponseDTO> getNotifications() {
		return remindersList;
	}

	public void setNotifications(List<PostRemindersFrontendResponseDTO> notifications) {
		this.remindersList = notifications;
	}
	
}
