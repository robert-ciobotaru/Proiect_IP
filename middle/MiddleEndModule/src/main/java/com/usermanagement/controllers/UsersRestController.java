package com.usermanagement.controllers;

import java.net.URL;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.usermanagement.DTO.AddNotificationDto;
import com.usermanagement.DTO.NotificationCreateDto;
import com.usermanagement.DTO.NotificationDto;
import com.usermanagement.DTO.UserDto;

//@RefreshScope
@RestController
@RequestMapping("v1/users")
class UsersRestController {

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
}