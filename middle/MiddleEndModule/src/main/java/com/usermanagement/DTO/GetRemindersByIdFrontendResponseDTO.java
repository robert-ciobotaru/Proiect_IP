package com.usermanagement.DTO;

import java.util.List;

public class GetRemindersByIdFrontendResponseDTO {
	public List<PostRemindersFrontendResponseDTO> remindersList;

	public List<PostRemindersFrontendResponseDTO> getRemindersList() {
		return remindersList;
	}

	public void setNotifications(List<PostRemindersFrontendResponseDTO> remindersList) {
		this.remindersList = remindersList;
	}
	
}
