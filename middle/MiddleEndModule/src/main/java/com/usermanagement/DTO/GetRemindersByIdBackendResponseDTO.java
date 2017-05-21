package com.usermanagement.DTO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GetRemindersByIdBackendResponseDTO {

	public List<PostRemindersFrontendResponseDTO> notificationsList;
	public String error;
	
	public List<PostRemindersFrontendResponseDTO> getNotifications() {
		return notificationsList;
	}
//	public void setNotifications(List<PostRemindersFrontendResponseDTO> notifications) {
//		this.notificationsList = notifications;
//	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
	public boolean validate(){
		Iterator<PostRemindersFrontendResponseDTO> itr = notificationsList.iterator();
		while (itr.hasNext()) {
			if(itr.next().validate()==false)
				return false;
		}

		
	 return true;
	}
}
