package com.usermanagement.controllers;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import javax.xml.ws.Response;

import java.net.URL;
import java.util.ArrayList;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.WebRequest;

import com.usermanagement.DTO.AddNotificationDto;
import com.usermanagement.DTO.AddUserDto;
import com.usermanagement.DTO.ErrorDto;
import com.usermanagement.DTO.GetNotificationResponseDto;
import com.usermanagement.DTO.GetNotificationTriggeredDto;
import com.usermanagement.DTO.GetNotificationTriggeredResultDto;
import com.usermanagement.DTO.GetNotificationsDto;
import com.usermanagement.DTO.GetNotificationsResultDto;
import com.usermanagement.DTO.GetNotificationsResultFromBackEnd;
import com.usermanagement.DTO.GetRemindersMethodDto;
import com.usermanagement.DTO.GetRemindersResponseFromBackend;
import com.usermanagement.DTO.NotificationCreateDto;
import com.usermanagement.DTO.NotificationDto;
import com.usermanagement.DTO.NotificationRemoveDto;
import com.usermanagement.DTO.NotificationRequestDto;
import com.usermanagement.DTO.NotificationsListDto;
import com.usermanagement.DTO.PostNotificationResultDto;
import com.usermanagement.DTO.RemoveNotificationResultDto;
import com.usermanagement.DTO.RemoveNotificationReturnDto;
import com.usermanagement.DTO.RemoveUserDto;
import com.usermanagement.DTO.RemoveUserMethodDto;
import com.usermanagement.DTO.RemoveUserReturnDto;
import com.usermanagement.DTO.ResponseInterfaceDto;
import com.usermanagement.DTO.UserCreateDto;
import com.usermanagement.DTO.UserCreateResponseFromBackEnd;
import com.usermanagement.DTO.UserCreateReturn;
import com.usermanagement.DTO.UserDto;

//@RefreshScope
@RestController
@RequestMapping("v1/users")
public class UsersRestController {
	
	String backEndUrlPath = "http://localhost:9001";
	
	public void setBackEndUrlPath(String backEndUrlPath) {
		this.backEndUrlPath = backEndUrlPath; 
	}
	
	@ExceptionHandler({HttpMessageNotReadableException.class})
	public ResponseEntity<Object> messageNotReadableExceptionHandler(HttpServletRequest req, HttpMessageNotReadableException exception) {
	  
		ErrorDto error = new ErrorDto();
		error.setError("The specified request is not readable");
	  
	    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
/*    @RequestMapping(value = "/{userId}/notifications", method = RequestMethod.GET)
    public ResponseEntity<UserDto> getNotifications(@PathVariable("userId") Long userId) {
    	UserDto user = new UserDto();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
*/
  
	@RequestMapping(value = "/{userId}/notifications", method = RequestMethod.POST)
    public ResponseEntity<Object> postNotifications(@PathVariable("userId") Integer userId,@RequestBody NotificationCreateDto notificationCreate){
		AddNotificationDto addNotification = new AddNotificationDto();
    	String url = new String(backEndUrlPath);
    	RestTemplate rest = new RestTemplate();
    	if(notificationCreate.getInterval()==null || notificationCreate.getText()==null || notificationCreate.getTime()==null || 
    			notificationCreate.isRepeatable()== null){
		      
    		ErrorDto error = new ErrorDto();
    		error.setError("Input criteria not correct");
    		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    	}
 
    	addNotification.setId(userId);
    	addNotification.setNotification(notificationCreate);
    	ResponseEntity<PostNotificationResultDto> response =null;
 
    	try{
    		response = rest.postForEntity(url,addNotification,PostNotificationResultDto.class);
    	}
    	catch (Exception e) {
    		System.out.println(e);
    		ErrorDto error =  new ErrorDto();
			error.setError("The server is currently unavailable");
			return new ResponseEntity<>(error,HttpStatus.SERVICE_UNAVAILABLE );
			 
    	}
    	
    	PostNotificationResultDto backendResult=null;
    	
    	if(response != null){
    		backendResult=response.getBody();
    		if(backendResult.getId() == null || backendResult.getError() == null ){
				 
    			ErrorDto error =  new ErrorDto();
			    error.setError("Internal server error");
			    return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
			}
    	}
 
    	NotificationDto frontendResult= null;

    	if(backendResult.getError().length()>0){
    		ErrorDto error = new ErrorDto();
    		error.setError(backendResult.getError());
	
    		return new ResponseEntity<>((ResponseInterfaceDto)error,HttpStatus.UNPROCESSABLE_ENTITY);
    	}

		frontendResult = new NotificationDto ();	
		frontendResult.setId(backendResult.getId());
		frontendResult.setInterval(notificationCreate.getInterval());
		frontendResult.setRepeatable(notificationCreate.isRepeatable());
		frontendResult.setText(notificationCreate.getText());
		frontendResult.setTime(notificationCreate.getTime());    
		
		return new ResponseEntity<>((ResponseInterfaceDto)frontendResult, HttpStatus.CREATED);
		  	 
    }
    @RequestMapping(value = "/{userId}/notifications", method = RequestMethod.GET)
    public ResponseEntity<Object> getNotifications(@PathVariable("userId") Integer userId){
    	GetNotificationsDto getNotifications = new GetNotificationsDto();
    	String url = new String(backEndUrlPath);
    	RestTemplate rest = new RestTemplate();
    	getNotifications.setId(userId);
    	getNotifications.setMethod("getNotifications");
    	
    	ResponseEntity<GetNotificationsResultFromBackEnd> response = null;
    	
    	try{
       	  response = rest.postForEntity(url,getNotifications,GetNotificationsResultFromBackEnd.class);
       	 }
       	 catch (Exception e) {
       		 ErrorDto error = new ErrorDto();
       		 error.setError("The server is currently unavailable");    		
       		 return new ResponseEntity<>(error,HttpStatus.SERVICE_UNAVAILABLE);
       		 
       	 }
    	GetNotificationsResultFromBackEnd getNotificationResponse = new GetNotificationsResultFromBackEnd();
    	if (response != null){
    		getNotificationResponse = response.getBody();
    		if(getNotificationResponse.validate()==false){
    			 ErrorDto error = new ErrorDto();
    	       		error.setError("Internal Server Error");
    	       		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    		}
    		
    	}
    	else{
    		ErrorDto error = new ErrorDto();
       		error.setError("Internal Server Error");
       		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    		
    	}
    	if(getNotificationResponse.getError().length()>0){
    		 ErrorDto error = new ErrorDto();
	       		error.setError("Internal Server Error");
	       		return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    	}
    	
    	GetNotificationResponseDto getNotificationReturn = new GetNotificationResponseDto();
    	getNotificationReturn.setUserNotificationsList(getNotificationResponse.getUserNotifications());
    	getNotificationReturn.setWeatherNotificationsList(getNotificationResponse.getWeatherNotificationsList());
    	getNotificationReturn.getHazzardNotifications().setEarthquakesList(getNotificationResponse.getEarthquakesList());
    	getNotificationReturn.getHazzardNotifications().getHazzard().setCyclonesList(getNotificationResponse.getCyclonesList());
    	getNotificationReturn.getHazzardNotifications().getHazzard().setFloodsList(getNotificationResponse.getFloodsList());
    	getNotificationReturn.setNewsNotificationsList(getNotificationResponse.getNewsNotificationsList());
    	return new ResponseEntity<>(getNotificationReturn, HttpStatus.OK);
    	
    	
    
    }
  
    @RequestMapping(value ="/{userId}/reminders/{reminderId}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> removeNotification(@PathVariable("userId") Integer userId ,@PathVariable("reminderId") Integer reminderId){
    	NotificationRemoveDto removeNotification = new NotificationRemoveDto();
    	String url = new String(backEndUrlPath);
    	RestTemplate rest = new RestTemplate();
    	removeNotification.setId(reminderId);
    	ResponseEntity<RemoveNotificationResultDto> response = null;
    	
    	try{
       	 	response = rest.postForEntity(url,removeNotification,RemoveNotificationResultDto.class);
       	 }
       	 catch (Exception e) {
       		ErrorDto error = new ErrorDto();
    		error.setError("The server is currently unavailable");
    		return new ResponseEntity<>(error, HttpStatus.SERVICE_UNAVAILABLE);
       		 
       	 }
    	
    	
    	
    	RemoveNotificationResultDto removeNotificationResult = null;
    	if ( response != null ){
    		
    	   removeNotificationResult = response.getBody();
    	   if(removeNotificationResult.getError()==null ){
    		   ErrorDto error = new ErrorDto();
       		 error.setError("Internal Server Error");
       		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    		   
    	   }
    	}
    
       RemoveNotificationReturnDto removeResponse = null;

		if(removeNotificationResult.getError().length()>0){
			ErrorDto error = new ErrorDto();
			error.setError(removeNotificationResult.getError());
			return new ResponseEntity<>(error,HttpStatus.UNPROCESSABLE_ENTITY);
		}
		else{
		    removeResponse = new RemoveNotificationReturnDto();	
		    removeResponse.setId(reminderId);  
			return new ResponseEntity<>(removeResponse, HttpStatus.OK);
		}
	}
    

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> removeUser(@PathVariable("userId") Integer userId) {
    	RemoveUserMethodDto removeUserMethod = new RemoveUserMethodDto();
    	String url = new String(backEndUrlPath);
   	 	RestTemplate rest = new RestTemplate();
   	 	removeUserMethod.setId(userId);
   	 
   	 	ResponseEntity<RemoveUserDto> response = null;
   	 	try{
   	 		response = rest.postForEntity(url,removeUserMethod,RemoveUserDto.class);
       	    }
       	catch (Exception e) {
       		ErrorDto error = new ErrorDto();
    		error.setError("The server is currently unavailable");
    		return new ResponseEntity<>(error, HttpStatus.SERVICE_UNAVAILABLE);
       		 
       	}
   	 	
    	RemoveUserDto removeUser = null;
    	
    	if(response != null){
    		removeUser = response.getBody();
    		
    		if(removeUser.getError() == null ){
    			ErrorDto error = new ErrorDto();
        		error.setError("Internal Server Error");
        		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    		}
    	}
    	
    	
    	RemoveUserReturnDto removeResponse = null;
    	
    	if(removeUser.getError().length()>0){
    		
    		ErrorDto error = new ErrorDto();
    		error.setError(removeUser.getError());
    		return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    		
    	}
    	else{
    		
    		removeResponse = new RemoveUserReturnDto ();	
    		removeResponse.setId(userId);
    		return new ResponseEntity<>(removeResponse, HttpStatus.OK);
    	}
    }
    
     
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Object> postUser(@RequestBody UserCreateDto userCreate){
    	if(userCreate.getCity() == null || userCreate.getCountry() == null || userCreate.getEmail() == null || userCreate.isHazzardCrawler() == null 
    			|| userCreate.isNewsCrawler() == null || userCreate.isWeatherCrawler() == null){
			
    		ErrorDto error = new ErrorDto();
			error.setError("Data for creating new user is invalid");
			
			return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    	}
			
    	AddUserDto addUser = new AddUserDto();
    	String url = new String(backEndUrlPath);
    	RestTemplate rest = new RestTemplate();
    	addUser.setUser(userCreate);
    	ResponseEntity<UserCreateResponseFromBackEnd> response = null;
    	try{
		     response = rest.postForEntity(url,addUser,UserCreateResponseFromBackEnd.class);
		}
		catch (Exception e) {
			
			System.out.println("CATCH PATH:" + e);
	        ErrorDto error = new ErrorDto();
	        error.setError("The server is currently unavailable");
	        
	        return new ResponseEntity<>(error, HttpStatus.SERVICE_UNAVAILABLE);
		 }
		 
		 UserCreateResponseFromBackEnd userCreateResponse = null;
		 
		 if ( response != null ){
 		
			userCreateResponse = response.getBody();
			if(userCreateResponse.getError() == null || userCreateResponse.getId() == null){
		    	
				ErrorDto error = new ErrorDto();
				error.setError("Internal server error");
				
				return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
		    }
	  	 }
	  	 
		 if(userCreateResponse.getError().length()>0){
			
			 ErrorDto error = new ErrorDto();
			 error.setError(userCreateResponse.getError());		
			 
			 return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
		 }
		 else{
			UserCreateReturn returnUser = new UserCreateReturn();
			returnUser.getUser().setId(userCreateResponse.getId());
			returnUser.getUser().setCity(userCreate.getCity());
			returnUser.getUser().setCountry(userCreate.getCountry());
			returnUser.getUser().setEmail(userCreate.getEmail());
			returnUser.getUser().setHazzardCrawler(userCreate.isHazzardCrawler());
			returnUser.getUser().setNewsCrawler(userCreate.isNewsCrawler());
			returnUser.getUser().setWeatherCrawler(userCreate.isWeatherCrawler());
		      
			return new ResponseEntity<>(returnUser, HttpStatus.CREATED);
		 }
    }
    
    @RequestMapping(value = "/{userId}/reminders", method = RequestMethod.GET)
    public ResponseEntity<Object> getReminders(@PathVariable("userId") Integer userId){
    	
    	GetRemindersMethodDto getRemindersMethod = new GetRemindersMethodDto();
    	String url = new String(backEndUrlPath);
    	RestTemplate rest = new RestTemplate();
    	getRemindersMethod.setId(userId);
    	getRemindersMethod.setMethod("getReminders");
    	
    	ResponseEntity<GetRemindersResponseFromBackend> responseFromBackend = null;
    	try{
    		responseFromBackend = rest.postForEntity(url,getRemindersMethod,GetRemindersResponseFromBackend.class);
    	}
    	catch (Exception e){
    		System.out.println(e);
    		ErrorDto error =  new ErrorDto();
    		error.setError("The server is currently unavailable");
    		return new ResponseEntity<>(error,HttpStatus.SERVICE_UNAVAILABLE);
    	}
    	
    	GetRemindersResponseFromBackend notification = null;
    	if (responseFromBackend != null){
    		notification = responseFromBackend.getBody();
    		if(notification.validate() == false){
    			ErrorDto error =  new ErrorDto();
        		error.setError("Internal server error");
    			return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
    		}
    		
    	}
    	
    	else{
    	ErrorDto error = new ErrorDto();
   		error.setError("Internal Server Error");
   		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    	
    	if(notification.getError().length() >= 0){
    		
    		ErrorDto error = new ErrorDto();
 			error.setError(notification.getError());
 			return new ResponseEntity<>((ResponseInterfaceDto)error,HttpStatus.UNPROCESSABLE_ENTITY);
    		
    	}
    	
    	NotificationsListDto remindersList = new NotificationsListDto();
    	remindersList.setNotifications(notification.getNotifications());
		return new ResponseEntity<>(remindersList, HttpStatus.OK);

    }  
}