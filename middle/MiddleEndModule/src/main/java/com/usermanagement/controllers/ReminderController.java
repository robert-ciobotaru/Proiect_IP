package com.usermanagement.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.usermanagement.DTO.PostRemindersBackendRequestDTO;
import com.usermanagement.DTO.ErrorDTO;
import com.usermanagement.DTO.GetRemindersByIdBackendRequestDTO;
import com.usermanagement.DTO.GetRemindersByIdBackendResponseDTO;
import com.usermanagement.DTO.PostRemindersFrontendRequestDTO;
import com.usermanagement.DTO.PostRemindersFrontendResponseDTO;
import com.usermanagement.DTO.DeleteRemindersByIdBackendRequestDTO;
import com.usermanagement.DTO.GetRemindersByIdFrontendResponseDTO;
import com.usermanagement.DTO.PostRemindersBackendResponseDTO;
import com.usermanagement.DTO.DeleteRemindersByIdBackendResponseDTO;
import com.usermanagement.DTO.DeleteRemindersByIdFrontendResponseDTO;
import com.usermanagement.requestmonitor.RequestMonitor;

@RestController
@RequestMapping("v1/users")
public class ReminderController extends AbstractController {
	
	public ReminderController() {
		RequestMonitor.getRequestMonitorInstance();
	}
		 	 
	@RequestMapping(value = "/{userId}/reminders", method = RequestMethod.POST)
    public ResponseEntity<Object> postReminder(HttpServletRequest request, @PathVariable("userId") Integer userId, @Valid @RequestBody PostRemindersFrontendRequestDTO createReminders){
		if(!RequestMonitor.getRequestMonitorInstance().allowRequest(request.getRemoteAddr())){
    		 ErrorDTO error = new ErrorDTO();
    		 error.setError(TOO_MANY_REQUESTS);    	
    		 System.out.println("MONITOR: " + RequestMonitor.getRequestMonitorInstance().getMaxRequestCount());
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
    	addNotification.setMethod("addNotification");
    	addNotification.setNotification(createReminders);
    	ResponseEntity<PostRemindersBackendResponseDTO> response =null;
 
    	PostRemindersBackendResponseDTO backendResult=null;
    	
    	try{
    		response = rest.postForEntity(url,addNotification,PostRemindersBackendResponseDTO.class);
    		backendResult=response.getBody();
    		
    		if(backendResult == null){
    			ErrorDTO error =  new ErrorDTO();
			    error.setError("Service response is invalid");
			    return new ResponseEntity<>(error,HttpStatus.SERVICE_UNAVAILABLE);
    		}
    		
    		if(backendResult.getNotificationId() == null || backendResult.getError() == null ){				 
    			ErrorDTO error =  new ErrorDTO();
			    error.setError("Service response is invalid");
			    return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
			}
    		
    	}
    	catch (RestClientException e) {
       		
   		 ErrorDTO error = new ErrorDTO();
      		 error.setError("The server is currently unavailable");    		
      		 return new ResponseEntity<>(error,HttpStatus.SERVICE_UNAVAILABLE);
      		 
      	 }
		
		
		 catch (Exception e) {
			 ErrorDTO error = new ErrorDTO();
			 error.setError("Unknown error occured");    		
			 return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
			 
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
    public ResponseEntity<Object> deleteReminder(HttpServletRequest request, @PathVariable("userId") Integer userId ,@PathVariable("reminderId") Integer reminderId){
    	
    	if(!RequestMonitor.getRequestMonitorInstance().allowRequest(request.getRemoteAddr())){
    		 ErrorDTO error = new ErrorDTO();
    		 error.setError(TOO_MANY_REQUESTS);    		
    		 return new ResponseEntity<>(error,HttpStatus.TOO_MANY_REQUESTS);
    		 
    	 }
    	
    	DeleteRemindersByIdBackendRequestDTO removeNotification = new DeleteRemindersByIdBackendRequestDTO();
    	String url = new String(backEndUrlPath);
    	RestTemplate rest = new RestTemplate();
    	removeNotification.setNotificationId(reminderId);
    	ResponseEntity<DeleteRemindersByIdBackendResponseDTO> response = null;
    	
    	DeleteRemindersByIdBackendResponseDTO removeNotificationResult = null;
    	
    	try{
    		response = rest.postForEntity(url,removeNotification,DeleteRemindersByIdBackendResponseDTO.class);
    		removeNotificationResult = response.getBody();
    		
    		if(removeNotificationResult == null){
    			ErrorDTO error =  new ErrorDTO();
			    error.setError("Service response is invalid");
			    return new ResponseEntity<>(error,HttpStatus.SERVICE_UNAVAILABLE);
    		}
    		
    		if(removeNotificationResult.getError()==null ){
    			ErrorDTO error = new ErrorDTO();
    			error.setError("Service response is invalid");
    			return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    		}
       	 }
    	
    	catch (RestClientException e) {
       		
   		 ErrorDTO error = new ErrorDTO();
      		 error.setError("The server is currently unavailable");    		
      		 return new ResponseEntity<>(error,HttpStatus.SERVICE_UNAVAILABLE);
      		 
      	 }
		
		
		 catch (Exception e) {
			 ErrorDTO error = new ErrorDTO();
			 error.setError("Unknown error occured");    		
			 return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
			 
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
    	
    	if(!RequestMonitor.getRequestMonitorInstance().allowRequest(request.getRemoteAddr())){
    		 ErrorDTO error = new ErrorDTO();
    		 error.setError(TOO_MANY_REQUESTS);    		
    		 return new ResponseEntity<>(error,HttpStatus.TOO_MANY_REQUESTS);
    		 
    	 }
    	
    	GetRemindersByIdBackendRequestDTO getRemindersMethod = new GetRemindersByIdBackendRequestDTO();
    	String url = new String(backEndUrlPath);
    	RestTemplate rest = new RestTemplate();
    	getRemindersMethod.setId(userId);
    	getRemindersMethod.setMethod("getUserNotifications");
    	
    	ResponseEntity<GetRemindersByIdBackendResponseDTO> responseFromBackend = null;
    	
    	GetRemindersByIdBackendResponseDTO notification = null;
    	try{
    		responseFromBackend = rest.postForEntity(url,getRemindersMethod,GetRemindersByIdBackendResponseDTO.class);
    		
    		notification = responseFromBackend.getBody();
    		
    		if(notification == null){
    			ErrorDTO error =  new ErrorDTO();
			    error.setError("Service response is invalid");
			    return new ResponseEntity<>(error,HttpStatus.SERVICE_UNAVAILABLE);
    		}
    		
    		if(notification.validate() == false){
    			ErrorDTO error =  new ErrorDTO();
        		error.setError("Service response is invalid");
    			return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
    		}
    	}
    	
    	catch (RestClientException e) {
       		
   		 ErrorDTO error = new ErrorDTO();
      		 error.setError("The server is currently unavailable");    		
      		 return new ResponseEntity<>(error,HttpStatus.SERVICE_UNAVAILABLE);
      		 
      	 }
		
		
		 catch (Exception e) {
			 ErrorDTO error = new ErrorDTO();
			 error.setError("Unknown error occured");    		
			 return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
			 
		 }
    	
    	if(notification.getError().length() > 0){
    		
    		ErrorDTO error = new ErrorDTO();
 			error.setError(notification.getError());
 			return new ResponseEntity<>(error,HttpStatus.UNPROCESSABLE_ENTITY);
    		
    	}
    	
    	GetRemindersByIdFrontendResponseDTO remindersList = new GetRemindersByIdFrontendResponseDTO();
    	remindersList.setNotifications(notification.getNotifications());
		return new ResponseEntity<>(remindersList, HttpStatus.OK);

    }  
}
