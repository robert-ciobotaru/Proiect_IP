package com.usermanagement.DTO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GetRemindersResponseFromBackend {

	public List<NotificationDto> notifications = new ArrayList<NotificationDto>();
	public String error;
	
	public List<NotificationDto> getNotifications() {
		return notifications;
	}
	public void setNotifications(List<NotificationDto> notifications) {
		this.notifications = notifications;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
	public boolean validate(){
		Iterator<NotificationDto> itr = notifications.iterator();
		while (itr.hasNext()) {
			if(itr.next().validate()==false)
				return false;
		}
		if(error == null)
			return false;
		
	 return true;
	}
}
