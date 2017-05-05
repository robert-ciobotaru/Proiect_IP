package com.usermanagement.controllers;

import java.util.List;

import javax.xml.ws.Response;

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
import com.usermanagement.DTO.ErrorDto;
import com.usermanagement.DTO.GetNotificationsDto;
import com.usermanagement.DTO.GetNotificationsResultDto;
import com.usermanagement.DTO.GetNotificationsResultFromBackEnd;
import com.usermanagement.DTO.NotificationCreateDto;
import com.usermanagement.DTO.NotificationDto;
import com.usermanagement.DTO.NotificationRemoveDto;
import com.usermanagement.DTO.NotificationRequestDto;
import com.usermanagement.DTO.NotificationsListDto;
import com.usermanagement.DTO.RemoveNotificationResultDto;
import com.usermanagement.DTO.RemoveNotificationReturnDto;
import com.usermanagement.DTO.ResponseInterfaceDto;
import com.usermanagement.DTO.UserDto;

//@RefreshScope
@RestController
@RequestMapping("v1/users")
public class UsersRestController {

/*    @RequestMapping(value = "/{userId}/notifications", method = RequestMethod.GET)
    public ResponseEntity<UserDto> getNotifications(@PathVariable("userId") Long userId) {
    	UserDto user = new UserDto();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
*/
  
	@RequestMapping(value = "/{userId}/notifications", method = RequestMethod.POST)
    public ResponseEntity<NotificationDto> postNotifications(@PathVariable("userId") Integer userId,@RequestBody NotificationCreateDto notificationCreate){
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
    	 notification.setTime(notificationCreate.getTime());
    	 notification.setId(3);
    	 notification.setInterval(notificationCreate.getInterval());
    	 notification.setText(notificationCreate.getText());
    	 notification.setRepeatable(notificationCreate.isRepeatable());
    	return new ResponseEntity<>(notification, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/{userId}/notifications", method = RequestMethod.GET)
    public ResponseEntity<ResponseInterfaceDto> getNotifications(@PathVariable("userId") Integer userId){
    	GetNotificationsDto getNotifications = new GetNotificationsDto();
    	String url = new String("https://www.triburile.ro/");
    	RestTemplate rest = new RestTemplate();
    	getNotifications.setId(userId);
    	ResponseEntity<GetNotificationsResultFromBackEnd> response = null;
    	
    	try{
       	  response = rest.postForEntity(url,getNotifications,GetNotificationsResultFromBackEnd.class);
       	 }
       	 catch (Exception e) {
       		 System.out.println(e);
       	 }
  
    	
    	GetNotificationsResultFromBackEnd notificationsResult=null;
    	
    	List<NotificationDto> notificationsList = new ArrayList<>();
    	
    	if(response !=null)
    	
    		notificationsResult = response.getBody();
    	else
    		 notificationsResult = new GetNotificationsResultFromBackEnd();
    	
    	
    	NotificationDto notification1 = new NotificationDto();
    	NotificationDto notification2 = new NotificationDto();
    	
    	notification1.setId(23);
    	notification2.setId(24);
    	notification1.setText("Wake me up");
    	notification2.setText("Get the kid");
    	notification1.setInterval(300);
    	notification2.setInterval(400);
    	notification1.setTime(1231245);;
    	notification2.setTime(123245);
    	notification1.setRepeatable(true);
    	notification2.setRepeatable(false);
    	
    	notificationsList.add(notification1);
    	notificationsList.add(notification2);
    	
    
    	notificationsResult.setNotifications(notificationsList);
    	
    	 
    

    	if(userId==1){
    		notificationsResult.setError("EroorHappens");
     	   
        }
        else
        {
        	notificationsResult.setError("");
        }
        GetNotificationsResultDto getNotificantionsResultForFrontEnd = null;

 		if(notificationsResult.getError().length()>0){
 			ErrorDto error = new ErrorDto();
 			error.setError(notificationsResult.getError());
 			
 			return new ResponseEntity<>((ResponseInterfaceDto)error,HttpStatus.UNPROCESSABLE_ENTITY);
 		}
 		else{
 			getNotificantionsResultForFrontEnd = new GetNotificationsResultDto ();	
 			getNotificantionsResultForFrontEnd.setNotifications(notificationsResult.getNotifications());
 		      
 			return new ResponseEntity<>((ResponseInterfaceDto)getNotificantionsResultForFrontEnd, HttpStatus.OK);
 		}
    }
    
    
    @RequestMapping(value = "/{userId}/notifications/{notificationId}", method = RequestMethod.GET)
    public ResponseEntity<NotificationDto> getNotificationsSpecificated(@PathVariable("userId") Long userId ,@PathVariable("notificationId") Long notificationId ) {
    	NotificationRequestDto notification = new NotificationRequestDto();
    	String url = new String("https://www.youtube.com/");
   	     RestTemplate rest = new RestTemplate();
    	notification.setUserid(userId);
    	notification.setNotificationid(notificationId);
    	try{
       	 ResponseEntity<NotificationDto>response = rest.postForEntity(url,notification,NotificationDto.class);
       	 }
       	 catch (Exception e) {
       		 System.out.println(e);
       	 }
    	NotificationDto notificationResponse = new NotificationDto();
    	notificationResponse.setId((int) (long)notification.getNotificationid());
        return new ResponseEntity<>(notificationResponse, HttpStatus.OK);
     
    }
    @RequestMapping(value ="/{userId}/notifications/{notificationId}", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseInterfaceDto> removeNotification(@PathVariable("userId") Integer userId ,@PathVariable("notificationId") Integer notificationId){
    	NotificationRemoveDto removeNotification = new NotificationRemoveDto();
    	String url = new String("https://www.MANOLEIHATEYOU.VERYMUCH/");
    	RestTemplate rest = new RestTemplate();
    	removeNotification.setId(userId);
    	removeNotification.setMethod("removeNotification");
    	
    	ResponseEntity<RemoveNotificationResultDto> response = null;
    	
    	try{
       	 	response = rest.postForEntity(url,removeNotification,RemoveNotificationResultDto.class);
       	 }
       	 catch (Exception e) {
       		 System.out.println(e);
       	 }
    	
    	RemoveNotificationResultDto removeNotificationResult = null;
    	if ( response != null ){
    		
    	   removeNotificationResult = response.getBody();	
    	}
    	else{
    	
    	   removeNotificationResult = new RemoveNotificationResultDto();
    	}
    	
    	removeNotificationResult.setId(23);
    	removeNotificationResult.setError("Invalid notification id");
    	
       if(userId==1){
    	   removeNotificationResult.setError("EroorHappens");
    	   
       }
       else
       {
    	   removeNotificationResult.setError("");
       }
       RemoveNotificationReturnDto removeResponse = null;

		if(removeNotificationResult.getError().length()>0){
			ErrorDto error = new ErrorDto();
			error.setError(removeNotificationResult.getError());
			
			return new ResponseEntity<>((ResponseInterfaceDto)error,HttpStatus.UNPROCESSABLE_ENTITY);
		}
		else{
		    removeResponse = new RemoveNotificationReturnDto ();	
		    removeResponse.setId(removeNotificationResult.getId());
		      
			return new ResponseEntity<>((ResponseInterfaceDto)removeResponse, HttpStatus.OK);
		}
		}
    
    
}