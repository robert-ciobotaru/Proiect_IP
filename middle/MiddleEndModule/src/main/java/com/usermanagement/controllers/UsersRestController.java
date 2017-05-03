package com.usermanagement.controllers;

import java.util.List;
import java.net.URL;
import java.util.ArrayList;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.usermanagement.DTO.AddNotificationDto;
import com.usermanagement.DTO.GetTriggeredNotificationsDto;
import com.usermanagement.DTO.NotificationCreateDto;
import com.usermanagement.DTO.NotificationDto;
import com.usermanagement.DTO.NotificationsListDto;
import com.usermanagement.DTO.UserDto;

//@RefreshScope
@RestController
@RequestMapping("v1/users")
public class UsersRestController {

    @RequestMapping(value = "/{userId}/notifications", method = RequestMethod.GET)
    public ResponseEntity<UserDto> getNotifications(@PathVariable("userId") Long userId) {
    	UserDto user = new UserDto();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/{userId}/notifications", method = RequestMethod.POST)
    public ResponseEntity<NotificationDto> postNotifications(@PathVariable("userId") Long userId,@RequestBody NotificationCreateDto notificationCreate){
    	NotificationDto notification = new NotificationDto();
    	AddNotificationDto addNotification = new AddNotificationDto();
    	 String url = new String("https://www.youtube.com/");
    	 RestTemplate rest = new RestTemplate();
    	 
    	 addNotification.setId(userId);
    	 addNotification.setNotification(notificationCreate);
    	 try{
    	 ResponseEntity<NotificationDto> response = rest.postForEntity(url,addNotification,NotificationDto.class);
    	 }
    	 catch (Exception e) {
    		 System.out.println(e);
    	 }
    	 notification.setDay_timestamp(notificationCreate.getDay_timestamp());
    	 notification.setId(3);
    	 notification.setPeriod(notificationCreate.getPeriod());
    	 notification.setText(notificationCreate.getText());
    	return new ResponseEntity<>(notification, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/{userId}/notifications/triggered-notifications", method = RequestMethod.GET)
    public ResponseEntity<NotificationsListDto> getTriggeredNotifications(@PathVariable("userId") Long userId){
    	GetTriggeredNotificationsDto getTriggeredNotification = new GetTriggeredNotificationsDto();
    	String url = new String("https://www.triburile.ro/");
    	RestTemplate rest = new RestTemplate();
    	getTriggeredNotification.setId(userId);
    	
    	try{
       	 ResponseEntity<NotificationDto> response = rest.postForEntity(url,getTriggeredNotification,NotificationDto.class);
       	 }
       	 catch (Exception e) {
       		 System.out.println(e);
       	 }  
    	
    	NotificationsListDto notificationsList = new NotificationsListDto();
    	NotificationCreateDto notification1 = new NotificationCreateDto();
    	NotificationCreateDto notification2 = new NotificationCreateDto();
    	notification1.setText("Trezirea de dimineata");
    	notification2.setText("Alarma de la 9:15");
    	notification1.setPeriod("none");
    	notification2.setPeriod("daily");
    	notification1.setDay_timestamp(2315);
    	notification2.setDay_timestamp(2315);
    	notificationsList.notifications.add(notification1);
    	notificationsList.notifications.add(notification2);
    	
        return new ResponseEntity<>(notificationsList, HttpStatus.OK);
    }
}