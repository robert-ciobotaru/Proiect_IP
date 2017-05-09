package com.usermanagement.DTO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.usermanagement.Sanitizer;

public class GetNotificationsResultFromBackEnd {

	public List<NotificationDto> userNotifications = new ArrayList<NotificationDto>();
	public List<WeatherNotificationsDto> weatherNotificationsList = new ArrayList<WeatherNotificationsDto>();
	public List<EarthquakesDto> earthquakesList = new ArrayList<EarthquakesDto>();
	public List<FloodsDto> floodsList = new ArrayList<FloodsDto>();
	public List<CyclonesDto> cyclonesList = new ArrayList<CyclonesDto>();
	public List<NewsNotificationsDto> newsNotificationsList = new ArrayList<NewsNotificationsDto>();
    public String error;
	
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public List<NotificationDto> getUserNotifications() {
		return userNotifications;
	}
	public void setUserNotifications(List<NotificationDto> userNotifications) {
		this.userNotifications = userNotifications;
	}
	public List<WeatherNotificationsDto> getWeatherNotificationsList() {
		return weatherNotificationsList;
	}
	public void setWeatherNotificationsList(List<WeatherNotificationsDto> weatherNotificationsList) {
		this.weatherNotificationsList = weatherNotificationsList;
	}
	public List<EarthquakesDto> getEarthquakesList() {
		return earthquakesList;
	}
	public void setEarthquakesList(List<EarthquakesDto> earthquakesList) {
		this.earthquakesList = earthquakesList;
	}
	public List<FloodsDto> getFloodsList() {
		return floodsList;
	}
	public void setFloodsList(List<FloodsDto> floodsList) {
		this.floodsList = floodsList;
	}
	public List<CyclonesDto> getCyclonesList() {
		return cyclonesList;
	}
	public void setCyclonesList(List<CyclonesDto> cyclonesList) {
		this.cyclonesList = cyclonesList;
	}
	public List<NewsNotificationsDto> getNewsNotificationsList() {
		return newsNotificationsList;
	}
	public void setNewsNotificationsList(List<NewsNotificationsDto> newsNotificationsList) {
		this.newsNotificationsList = newsNotificationsList;
	}
	public boolean validate(){
		Iterator<NotificationDto> itr = userNotifications.iterator();
		while (itr.hasNext()) {
			if(itr.next().validate()==false)
				return false;
		}
		Iterator<WeatherNotificationsDto> itr1 = weatherNotificationsList.iterator();
		while (itr1.hasNext()) {
			if(itr1.next().validate()==false)
				return false;
		}
		Iterator<EarthquakesDto> itr2 = earthquakesList.iterator();
		while (itr2.hasNext()) {
			if(itr2.next().validate()==false)
				return false;
		}
		Iterator<FloodsDto> itr3 = floodsList.iterator();
		while (itr3.hasNext()) {
			if(itr3.next().validate()==false)
				return false;
		}
		Iterator<CyclonesDto> itr4 = cyclonesList.iterator();
		while (itr4.hasNext()) {
			if(itr4.next().validate()==false)
				return false;
		}
		Iterator<NewsNotificationsDto> itr5 = newsNotificationsList.iterator();
		while (itr5.hasNext()) {
			if(itr5.next().validate()==false)
				return false;
		}
		if(this.error==null)
			return false;
		return true;
	}
	

}
