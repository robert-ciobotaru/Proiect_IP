package com.usermanagement.DTO;

import java.util.ArrayList;
import java.util.List;

public class GetNotificationResponseDto {
	public List<NotificationDto> userNotificationsList = new ArrayList<NotificationDto>();
	public List<WeatherNotificationsDto> weatherNotificationsList = new ArrayList<WeatherNotificationsDto>();
   public HazzardNotificationsDto hazzardNotifications;
	public List<NewsNotificationsDto> newsNotificationsList = new ArrayList<NewsNotificationsDto>();
	public List<NotificationDto> getUserNotificationsList() {
		return userNotificationsList;
	}
	public void setUserNotificationsList(List<NotificationDto> userNotificationsList) {
		this.userNotificationsList = userNotificationsList;
	}
	public List<WeatherNotificationsDto> getWeatherNotificationsList() {
		return weatherNotificationsList;
	}
	public void setWeatherNotificationsList(List<WeatherNotificationsDto> weatherNotificationsList) {
		this.weatherNotificationsList = weatherNotificationsList;
	}
	
	public HazzardNotificationsDto getHazzardNotifications() {
		return hazzardNotifications;
	}
	public void setHazzardNotifications(HazzardNotificationsDto hazzardNotifications) {
		this.hazzardNotifications = hazzardNotifications;
	}
	public List<NewsNotificationsDto> getNewsNotificationsList() {
		return newsNotificationsList;
	}
	public void setNewsNotificationsList(List<NewsNotificationsDto> newsNotificationsList) {
		this.newsNotificationsList = newsNotificationsList;
	}

}
