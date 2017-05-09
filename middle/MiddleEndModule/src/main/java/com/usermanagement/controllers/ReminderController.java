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
import com.usermanagement.DTO.HazzardDto;
import com.usermanagement.DTO.HazzardNotificationsDto;
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
import com.usermanagement.requestmonitor.RequestMonitor;

@RestController
@RequestMapping("v1/users")
public class ReminderController {
	
	public void setRequestMonitor(RequestMonitor monitor){
		 this.requestMonitor = monitor;
	 }
	 public RequestMonitor getRequestMonitor(){
		 return requestMonitor;
	 }
	 
	 private static String TOO_MANY_REQUESTS = "TOO MANY REQUESTS";
	 RequestMonitor requestMonitor = new RequestMonitor(5);
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
	
	@RequestMapping(value = "/{userId}/reminders", method = RequestMethod.POST)
    public ResponseEntity<Object> postReminder(HttpServletRequest request, @PathVariable("userId") Integer userId,@RequestBody NotificationCreateDto createReminders){
		if(!requestMonitor.allowRequest(request.getRemoteAddr())){
    		 ErrorDto error = new ErrorDto();
    		 error.setError(TOO_MANY_REQUESTS);    		
    		 return new ResponseEntity<>(error,HttpStatus.TOO_MANY_REQUESTS);
    		 
    	 }
		
		AddNotificationDto addNotification = new AddNotificationDto();
    	String url = new String(backEndUrlPath);
    	RestTemplate rest = new RestTemplate();
    	if(createReminders.getInterval()==null || createReminders.getText()==null || createReminders.getTime()==null || 
    			createReminders.isRepeatable()== null){
		      
    		ErrorDto error = new ErrorDto();
    		error.setError("Input criteria not correct");
    		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    	}
 
    	addNotification.setUserId(userId);
    	addNotification.setNotification(createReminders);
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
    		if(backendResult.getNotificationId() == null || backendResult.getError() == null ){
				 
    			ErrorDto error =  new ErrorDto();
			    error.setError("Internal server error");
			    return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
			}
    	}
 
    	NotificationDto frontendResult= null;

    	if(backendResult.getError().length()>0){
    		ErrorDto error = new ErrorDto();
    		error.setError(backendResult.getError());
	
    		return new ResponseEntity<>(error,HttpStatus.UNPROCESSABLE_ENTITY);
    	}

		frontendResult = new NotificationDto ();	
		frontendResult.setId(backendResult.getNotificationId());
		frontendResult.setInterval(createReminders.getInterval());
		frontendResult.setRepeatable(createReminders.isRepeatable());
		frontendResult.setText(createReminders.getText());
		frontendResult.setTime(createReminders.getTime());    
		
		return new ResponseEntity<>(frontendResult, HttpStatus.CREATED);
		  	 
    }
	
	@RequestMapping(value ="/{userId}/reminders/{reminderId}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> removeReminder(HttpServletRequest request, @PathVariable("userId") Integer userId ,@PathVariable("reminderId") Integer reminderId){
    	
    	if(!requestMonitor.allowRequest(request.getRemoteAddr())){
    		 ErrorDto error = new ErrorDto();
    		 error.setError(TOO_MANY_REQUESTS);    		
    		 return new ResponseEntity<>(error,HttpStatus.TOO_MANY_REQUESTS);
    		 
    	 }
    	
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
    
	@RequestMapping(value = "/{userId}/reminders", method = RequestMethod.GET)
    public ResponseEntity<Object> getReminders(HttpServletRequest request, @PathVariable("userId") Integer userId){
    	
    	if(!requestMonitor.allowRequest(request.getRemoteAddr())){
    		 ErrorDto error = new ErrorDto();
    		 error.setError(TOO_MANY_REQUESTS);    		
    		 return new ResponseEntity<>(error,HttpStatus.TOO_MANY_REQUESTS);
    		 
    	 }
    	
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
    	
    	
    	if(notification.getError().length() > 0){
    		
    		ErrorDto error = new ErrorDto();
 			error.setError(notification.getError());
 			return new ResponseEntity<>((ResponseInterfaceDto)error,HttpStatus.UNPROCESSABLE_ENTITY);
    		
    	}
    	
    	NotificationsListDto remindersList = new NotificationsListDto();
    	remindersList.setNotifications(notification.getNotifications());
		return new ResponseEntity<>(remindersList, HttpStatus.OK);

    }  
}
