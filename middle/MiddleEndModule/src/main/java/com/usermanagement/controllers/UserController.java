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
public class UserController {

	public void setRequestMonitor(RequestMonitor monitor){
		 this.requestMonitor = monitor;
	 }
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
	  
		ErrorDto error = new ErrorDto();
		error.setError("The specified request is not readable");
	  
	    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> removeUser(HttpServletRequest request, @PathVariable("userId") Integer userId) {
    	
    	if(!requestMonitor.allowRequest(request.getRemoteAddr())){
    		 ErrorDto error = new ErrorDto();
    		 error.setError(TOO_MANY_REQUESTS);    		
    		 return new ResponseEntity<>(error,HttpStatus.TOO_MANY_REQUESTS);
    		 
    	 }
    	
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
    public ResponseEntity<Object> postUser(HttpServletRequest request, @RequestBody UserCreateDto userCreate){
    	
    	if(!requestMonitor.allowRequest(request.getRemoteAddr())){
    		 ErrorDto error = new ErrorDto();
    		 error.setError(TOO_MANY_REQUESTS);    		
    		 return new ResponseEntity<>(error,HttpStatus.TOO_MANY_REQUESTS);
    		 
    	 }
    	
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
			if(userCreateResponse.getError() == null || userCreateResponse.getUserId() == null){
		    	
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
			UserDto returnUser = new UserDto();
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
