package com.usermanagement.DTO;

import java.util.ArrayList;
import java.util.List;

public class GetNotificationsByIdFrontendResponseDTO {
	public List<PostRemindersFrontendResponseDTO> userNotificationsList = new ArrayList<PostRemindersFrontendResponseDTO>();
	public List<WeatherNotificationsDTO> weatherNotificationsList = new ArrayList<WeatherNotificationsDTO>();
   public HazzardNotificationsDTO hazzardNotifications;
	public List<NewsNotificationsDTO> newsNotificationsList = new ArrayList<NewsNotificationsDTO>();
	public List<PostRemindersFrontendResponseDTO> getUserNotificationsList() {
		return userNotificationsList;
	}
	public void setUserNotificationsList(List<PostRemindersFrontendResponseDTO> userNotificationsList) {
		this.userNotificationsList = userNotificationsList;
	}
	public List<WeatherNotificationsDTO> getWeatherNotificationsList() {
		return weatherNotificationsList;
	}
	public void setWeatherNotificationsList(List<WeatherNotificationsDTO> weatherNotificationsList) {
		this.weatherNotificationsList = weatherNotificationsList;
	}
	
	public HazzardNotificationsDTO getHazzardNotifications() {
		return hazzardNotifications;
	}
	public void setHazzardNotifications(HazzardNotificationsDTO hazzardNotifications) {
		this.hazzardNotifications = hazzardNotifications;
	}
	public List<NewsNotificationsDTO> getNewsNotificationsList() {
		return newsNotificationsList;
	}
	public void setNewsNotificationsList(List<NewsNotificationsDTO> newsNotificationsList) {
		this.newsNotificationsList = newsNotificationsList;
	}

}
