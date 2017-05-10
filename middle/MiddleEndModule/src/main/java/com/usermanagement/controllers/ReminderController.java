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

import com.usermanagement.DTO.PostRemindersBackendRequestDTO;
import com.usermanagement.DTO.PostUsersBackendRequestDTO;
import com.usermanagement.DTO.ErrorDTO;
import com.usermanagement.DTO.GetNotificationsByIdFrontendResponseDTO;
import com.usermanagement.DTO.GetNotificationTriggeredDto;
import com.usermanagement.DTO.GetNotificationTriggeredResultDto;
import com.usermanagement.DTO.GetNotificationsByIdBackendRequestDTO;
import com.usermanagement.DTO.GetNotificationsResultDto;
import com.usermanagement.DTO.GetNotificationsByIdBackendResponseDTO;
import com.usermanagement.DTO.GetRemindersByIdBackendRequestDTO;
import com.usermanagement.DTO.GetRemindersByIdBackendResponseDTO;
import com.usermanagement.DTO.HazzardDTO;
import com.usermanagement.DTO.HazzardNotificationsDTO;
import com.usermanagement.DTO.PostRemindersFrontendRequestDTO;
import com.usermanagement.DTO.PostRemindersFrontendResponseDTO;
import com.usermanagement.DTO.DeleteRemindersByIdBackendRequestDTO;
import com.usermanagement.DTO.NotificationRequestDto;
import com.usermanagement.DTO.GetRemindersByIdFrontendResponseDTO;
import com.usermanagement.DTO.PostRemindersBackendResponseDTO;
import com.usermanagement.DTO.DeleteRemindersByIdBackendResponseDTO;
import com.usermanagement.DTO.DeleteRemindersByIdFrontendResponseDTO;
import com.usermanagement.DTO.DeleteUsersByIdBackendResponseDTO;
import com.usermanagement.DTO.DeleteUsersByIdBackendRequestDTO;
import com.usermanagement.DTO.DeleteUsersByIdFrontendResponseDTO;
import com.usermanagement.DTO.ResponseInterfaceDto;
import com.usermanagement.DTO.PostUsersFrontendRequestDTO;
import com.usermanagement.DTO.PostUsersBackendResponseDTO;
import com.usermanagement.DTO.UserCreateReturn;
import com.usermanagement.DTO.PostUsersFrontendResponseDTO;
import com.usermanagement.requestmonitor.RequestMonitor;

@RestController
@RequestMapping("v1/users")
public class ReminderController {
	

	 public RequestMonitor getRequestMonitor(){
		 return requestMonitor;
	 }
	  
	 private static String TOO_MANY_REQUESTS = "TOO MANY REQUESTS";
	 RequestMonitor requestMonitor= RequestMonitor.getRequestMonitorInstance(100);
	String backEndUrlPath = "http://localhost:9001";
	
	public void setBackEndUrlPath(String backEndUrlPath) {
		this.backEndUrlPath = backEndUrlPath; 
	}
	
	@ExceptionHandler({HttpMessageNotReadableException.class})
	public ResponseEntity<Object> messageNotReadableExceptionHandler(HttpServletRequest req, HttpMessageNotReadableException exception) {
	  
		ErrorDTO error = new ErrorDTO();
		error.setError("The specified request is not readable");
	  
	    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/{userId}/reminders", method = RequestMethod.POST)
    public ResponseEntity<Object> postReminder(HttpServletRequest request, @PathVariable("userId") Integer userId,@RequestBody PostRemindersFrontendRequestDTO createReminders){
		if(!requestMonitor.allowRequest(request.getRemoteAddr())){
    		 ErrorDTO error = new ErrorDTO();
    		 error.setError(TOO_MANY_REQUESTS);    		
    		 return new ResponseEntity<>(error,HttpStatus.TOO_MANY_REQUESTS);
    		 
    	 }
		
		PostRemindersBackendRequestDTO addNotification = new PostRemindersBackendRequestDTO();
    	String url = new String(backEndUrlPath);
    	RestTemplate rest = new RestTemplate();
    	if(createReminders.getInterval()==null || createReminders.getText()==null || createReminders.getTime()==null || 
    			createReminders.isRepeatable()== null){
		      
    		ErrorDTO error = new ErrorDTO();
    		error.setError("Input criteria not correct");
    		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    	}
 
    	addNotification.setUserId(userId);
    	addNotification.setNotification(createReminders);
    	ResponseEntity<PostRemindersBackendResponseDTO> response =null;
 
    	try{
    		response = rest.postForEntity(url,addNotification,PostRemindersBackendResponseDTO.class);
    	}
    	catch (Exception e) {
    		System.out.println(e);
    		ErrorDTO error =  new ErrorDTO();
			error.setError("The server is currently unavailable");
			return new ResponseEntity<>(error,HttpStatus.SERVICE_UNAVAILABLE );
			 
    	}
    	
    	PostRemindersBackendResponseDTO backendResult=null;
    	
    	if(response != null){
    		backendResult=response.getBody();
    		if(backendResult.getNotificationId() == null || backendResult.getError() == null ){
				 
    			ErrorDTO error =  new ErrorDTO();
			    error.setError("Internal server error");
			    return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
			}
    	}
 
    	PostRemindersFrontendResponseDTO frontendResult= null;

    	if(backendResult.getError().length()>0){
    		ErrorDTO error = new ErrorDTO();
    		error.setError(backendResult.getError());
	
    		return new ResponseEntity<>(error,HttpStatus.UNPROCESSABLE_ENTITY);
    	}

		frontendResult = new PostRemindersFrontendResponseDTO ();	
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
    		 ErrorDTO error = new ErrorDTO();
    		 error.setError(TOO_MANY_REQUESTS);    		
    		 return new ResponseEntity<>(error,HttpStatus.TOO_MANY_REQUESTS);
    		 
    	 }
    	
    	DeleteRemindersByIdBackendRequestDTO removeNotification = new DeleteRemindersByIdBackendRequestDTO();
    	String url = new String(backEndUrlPath);
    	RestTemplate rest = new RestTemplate();
    	removeNotification.setId(reminderId);
    	ResponseEntity<DeleteRemindersByIdBackendResponseDTO> response = null;
    	
    	try{
       	 	response = rest.postForEntity(url,removeNotification,DeleteRemindersByIdBackendResponseDTO.class);
       	 }
       	 catch (Exception e) {
       		ErrorDTO error = new ErrorDTO();
    		error.setError("The server is currently unavailable");
    		return new ResponseEntity<>(error, HttpStatus.SERVICE_UNAVAILABLE);
       		 
       	 }
    	
    	
    	
    	DeleteRemindersByIdBackendResponseDTO removeNotificationResult = null;
    	if ( response != null ){
    		
    	   removeNotificationResult = response.getBody();
    	   if(removeNotificationResult.getError()==null ){
    		   ErrorDTO error = new ErrorDTO();
       		 error.setError("Internal Server Error");
       		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    		   
    	   }
    	}
    
       DeleteRemindersByIdFrontendResponseDTO removeResponse = null;

		if(removeNotificationResult.getError().length()>0){
			ErrorDTO error = new ErrorDTO();
			error.setError(removeNotificationResult.getError());
			return new ResponseEntity<>(error,HttpStatus.UNPROCESSABLE_ENTITY);
		}
		else{
		    removeResponse = new DeleteRemindersByIdFrontendResponseDTO();	
		    removeResponse.setId(reminderId);  
			return new ResponseEntity<>(removeResponse, HttpStatus.OK);
		}
	}
    
	@RequestMapping(value = "/{userId}/reminders", method = RequestMethod.GET)
    public ResponseEntity<Object> getReminders(HttpServletRequest request, @PathVariable("userId") Integer userId){
    	
    	if(!requestMonitor.allowRequest(request.getRemoteAddr())){
    		 ErrorDTO error = new ErrorDTO();
    		 error.setError(TOO_MANY_REQUESTS);    		
    		 return new ResponseEntity<>(error,HttpStatus.TOO_MANY_REQUESTS);
    		 
    	 }
    	
    	GetRemindersByIdBackendRequestDTO getRemindersMethod = new GetRemindersByIdBackendRequestDTO();
    	String url = new String(backEndUrlPath);
    	RestTemplate rest = new RestTemplate();
    	getRemindersMethod.setId(userId);
    	getRemindersMethod.setMethod("getReminders");
    	
    	ResponseEntity<GetRemindersByIdBackendResponseDTO> responseFromBackend = null;
    	try{
    		responseFromBackend = rest.postForEntity(url,getRemindersMethod,GetRemindersByIdBackendResponseDTO.class);
    	}
    	catch (Exception e){
    		System.out.println(e);
    		ErrorDTO error =  new ErrorDTO();
    		error.setError("The server is currently unavailable");
    		return new ResponseEntity<>(error,HttpStatus.SERVICE_UNAVAILABLE);
    	}
    	
    	GetRemindersByIdBackendResponseDTO notification = null;
    	if (responseFromBackend != null){
    		notification = responseFromBackend.getBody();
    		if(notification.validate() == false){
    			ErrorDTO error =  new ErrorDTO();
        		error.setError("Internal server error");
    			return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
    		}
    		
    	}
    	
    	
    	if(notification.getError().length() > 0){
    		
    		ErrorDTO error = new ErrorDTO();
 			error.setError(notification.getError());
 			return new ResponseEntity<>((ResponseInterfaceDto)error,HttpStatus.UNPROCESSABLE_ENTITY);
    		
    	}
    	
    	GetRemindersByIdFrontendResponseDTO remindersList = new GetRemindersByIdFrontendResponseDTO();
    	remindersList.setNotifications(notification.getNotifications());
		return new ResponseEntity<>(remindersList, HttpStatus.OK);

    }  
}
