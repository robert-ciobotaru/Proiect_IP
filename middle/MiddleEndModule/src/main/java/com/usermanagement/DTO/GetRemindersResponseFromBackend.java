package com.usermanagement.DTO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GetRemindersResponseFromBackend {

	public List<NotificationDto> notificationsList = new ArrayList<NotificationDto>();
	public String error;
	
	public List<NotificationDto> getNotifications() {
		return notificationsList;
	}
	public void setNotifications(List<NotificationDto> notifications) {
		this.notificationsList = notifications;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
	public boolean validate(){
		Iterator<NotificationDto> itr = notificationsList.iterator();
		while (itr.hasNext()) {
			if(itr.next().validate()==false)
				return false;
		}
		if(error == null)
			return false;
		
	 return true;
	}
}
