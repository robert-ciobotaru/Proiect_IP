package com.usermanagement.DTO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GetNotificationsByIdBackendResponseDTO {

	public List<PostRemindersFrontendResponseDTO> userNotifications = new ArrayList<PostRemindersFrontendResponseDTO>();
	public List<WeatherNotificationsDTO> weatherNotificationsList = new ArrayList<WeatherNotificationsDTO>();
	public List<EarthquakesNotificationsDTO> earthquakesList = new ArrayList<EarthquakesNotificationsDTO>();
	public List<FloodsNotificationsDTO> floodsList = new ArrayList<FloodsNotificationsDTO>();
	public List<CyclonesNotificationsDTO> cyclonesList = new ArrayList<CyclonesNotificationsDTO>();
	public List<NewsNotificationsDTO> newsNotificationsList = new ArrayList<NewsNotificationsDTO>();
    public String error;
	
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public List<PostRemindersFrontendResponseDTO> getUserNotifications() {
		return userNotifications;
	}
	public void setUserNotifications(List<PostRemindersFrontendResponseDTO> userNotifications) {
		this.userNotifications = userNotifications;
	}
	public List<WeatherNotificationsDTO> getWeatherNotificationsList() {
		return weatherNotificationsList;
	}
	public void setWeatherNotificationsList(List<WeatherNotificationsDTO> weatherNotificationsList) {
		this.weatherNotificationsList = weatherNotificationsList;
	}
	public List<EarthquakesNotificationsDTO> getEarthquakesList() {
		return earthquakesList;
	}
	public void setEarthquakesList(List<EarthquakesNotificationsDTO> earthquakesList) {
		this.earthquakesList = earthquakesList;
	}
	public List<FloodsNotificationsDTO> getFloodsList() {
		return floodsList;
	}
	public void setFloodsList(List<FloodsNotificationsDTO> floodsList) {
		this.floodsList = floodsList;
	}
	public List<CyclonesNotificationsDTO> getCyclonesList() {
		return cyclonesList;
	}
	public void setCyclonesList(List<CyclonesNotificationsDTO> cyclonesList) {
		this.cyclonesList = cyclonesList;
	}
	public List<NewsNotificationsDTO> getNewsNotificationsList() {
		return newsNotificationsList;
	}
	public void setNewsNotificationsList(List<NewsNotificationsDTO> newsNotificationsList) {
		this.newsNotificationsList = newsNotificationsList;
	}
	public boolean validate(){
		Iterator<PostRemindersFrontendResponseDTO> itr = userNotifications.iterator();
		while (itr.hasNext()) {
			if(itr.next().validate()==false)
				return false;
		}
		Iterator<WeatherNotificationsDTO> itr1 = weatherNotificationsList.iterator();
		while (itr1.hasNext()) {
			if(itr1.next().validate()==false)
				return false;
		}
		Iterator<EarthquakesNotificationsDTO> itr2 = earthquakesList.iterator();
		while (itr2.hasNext()) {
			if(itr2.next().validate()==false)
				return false;
		}
		Iterator<FloodsNotificationsDTO> itr3 = floodsList.iterator();
		while (itr3.hasNext()) {
			if(itr3.next().validate()==false)
				return false;
		}
		Iterator<CyclonesNotificationsDTO> itr4 = cyclonesList.iterator();
		while (itr4.hasNext()) {
			if(itr4.next().validate()==false)
				return false;
		}
		Iterator<NewsNotificationsDTO> itr5 = newsNotificationsList.iterator();
		while (itr5.hasNext()) {
			if(itr5.next().validate()==false)
				return false;
		}
		return true;
	}
	

}
