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
public class UserController {


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
	 
	@RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> removeUser(HttpServletRequest request, @PathVariable("userId") Integer userId) {
    	
    	if(!requestMonitor.allowRequest(request.getRemoteAddr())){
    		 ErrorDTO error = new ErrorDTO();
    		 error.setError(TOO_MANY_REQUESTS);    		
    		 return new ResponseEntity<>(error,HttpStatus.TOO_MANY_REQUESTS);
    		 
    	 }
    	
    	DeleteUsersByIdBackendRequestDTO removeUserMethod = new DeleteUsersByIdBackendRequestDTO();
    	String url = new String(backEndUrlPath);
   	 	RestTemplate rest = new RestTemplate();
   	 	removeUserMethod.setId(userId);
   	 
   	 	ResponseEntity<DeleteUsersByIdBackendResponseDTO> response = null;
   	 	try{
   	 		response = rest.postForEntity(url,removeUserMethod,DeleteUsersByIdBackendResponseDTO.class);
       	    }
       	catch (Exception e) {
       		ErrorDTO error = new ErrorDTO();
    		error.setError("The server is currently unavailable");
    		return new ResponseEntity<>(error, HttpStatus.SERVICE_UNAVAILABLE);
       		 
       	}
   	 	
    	DeleteUsersByIdBackendResponseDTO removeUser = null;
    	
    	if(response != null){
    		removeUser = response.getBody();
    		
    		if(removeUser.getError() == null ){
    			ErrorDTO error = new ErrorDTO();
        		error.setError("Internal Server Error");
        		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    		}
    	}
    	
    	
    	DeleteUsersByIdFrontendResponseDTO removeResponse = null;
    	
    	if(removeUser.getError().length()>0){
    		
    		ErrorDTO error = new ErrorDTO();
    		error.setError(removeUser.getError());
    		return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    		
    	}
    	else{
    		
    		removeResponse = new DeleteUsersByIdFrontendResponseDTO ();	
    		removeResponse.setId(userId);
    		return new ResponseEntity<>(removeResponse, HttpStatus.OK);
    	}
    }
	
	@RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Object> postUser(HttpServletRequest request, @RequestBody PostUsersFrontendRequestDTO userCreate){
    	
    	if(!requestMonitor.allowRequest(request.getRemoteAddr())){
    		 ErrorDTO error = new ErrorDTO();
    		 error.setError(TOO_MANY_REQUESTS);    		
    		 return new ResponseEntity<>(error,HttpStatus.TOO_MANY_REQUESTS);
    		 
    	 }
    	
    	if(userCreate.getCity() == null || userCreate.getCountry() == null || userCreate.getEmail() == null || userCreate.isHazzardCrawler() == null 
    			|| userCreate.isNewsCrawler() == null || userCreate.isWeatherCrawler() == null){
			
    		ErrorDTO error = new ErrorDTO();
			error.setError("Data for creating new user is invalid");
			
			return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    	}
			
    	PostUsersBackendRequestDTO addUser = new PostUsersBackendRequestDTO();
    	String url = new String(backEndUrlPath);
    	RestTemplate rest = new RestTemplate();
    	addUser.setUser(userCreate);
    	ResponseEntity<PostUsersBackendResponseDTO> response = null;
    	try{
		     response = rest.postForEntity(url,addUser,PostUsersBackendResponseDTO.class);
		}
		catch (Exception e) {
			
			System.out.println("CATCH PATH:" + e);
	        ErrorDTO error = new ErrorDTO();
	        error.setError("The server is currently unavailable");
	        
	        return new ResponseEntity<>(error, HttpStatus.SERVICE_UNAVAILABLE);
		 }
		 
		 PostUsersBackendResponseDTO userCreateResponse = null;
		 
		 if ( response != null ){
 		
			userCreateResponse = response.getBody();
			if(userCreateResponse.getError() == null || userCreateResponse.getUserId() == null){
		    	
				ErrorDTO error = new ErrorDTO();
				error.setError("Internal server error");
				
				return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
		    }
	  	 }
	  	 
		 if(userCreateResponse.getError().length()>0){
			
			 ErrorDTO error = new ErrorDTO();
			 error.setError(userCreateResponse.getError());		
			 
			 return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
		 }
		 else{
			PostUsersFrontendResponseDTO returnUser = new PostUsersFrontendResponseDTO();
			returnUser.setId(userCreateResponse.getUserId());
			returnUser.setCity(userCreate.getCity());
			returnUser.setCountry(userCreate.getCountry());
			returnUser.setEmail(userCreate.getEmail());
			returnUser.setHazzardCrawler(userCreate.isHazzardCrawler());
			returnUser.setNewsCrawler(userCreate.isNewsCrawler());
			returnUser.setWeatherCrawler(userCreate.isWeatherCrawler());
		      
			return new ResponseEntity<>(returnUser, HttpStatus.CREATED);
		 }
    }
}
