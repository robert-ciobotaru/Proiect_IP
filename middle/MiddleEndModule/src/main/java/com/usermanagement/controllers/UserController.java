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
import com.usermanagement.DTO.PostUsersBackendRequestDTO;
import com.usermanagement.DTO.ErrorDTO;
import com.usermanagement.DTO.DeleteUsersByIdBackendResponseDTO;
import com.usermanagement.DTO.DeleteUsersByIdBackendRequestDTO;
import com.usermanagement.DTO.DeleteUsersByIdFrontendResponseDTO;
import com.usermanagement.DTO.PostUsersFrontendRequestDTO;
import com.usermanagement.DTO.PostUsersBackendResponseDTO;
import com.usermanagement.DTO.PostUsersFrontendResponseDTO;
import com.usermanagement.requestmonitor.RequestMonitor;

@RestController
@RequestMapping("v1/users")
public class UserController extends AbstractController {

	@RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteUser(HttpServletRequest request, @PathVariable("userId") Integer userId) {
    	
    	if(!RequestMonitor.getRequestMonitorInstance().allowRequest(request.getRemoteAddr())){
    		 ErrorDTO error = new ErrorDTO();
    		 error.setError(TOO_MANY_REQUESTS);    		
    		 
    		 return new ResponseEntity<>(error,HttpStatus.TOO_MANY_REQUESTS);
    		 
    	 }
    	
    	DeleteUsersByIdBackendRequestDTO removeUserMethod = new DeleteUsersByIdBackendRequestDTO();
    	String url = new String(backEndUrlPath);
   	 	RestTemplate rest = new RestTemplate();
   	 	removeUserMethod.setUserId(userId);
   	 
   	 	ResponseEntity<DeleteUsersByIdBackendResponseDTO> response = null;
   	 	DeleteUsersByIdBackendResponseDTO removeUser = null;
   	 	
   	 	try{
   	 		response = rest.postForEntity(url,removeUserMethod,DeleteUsersByIdBackendResponseDTO.class);
   	 		
	   	 	removeUser = response.getBody();
	   	 
	   	 	if(removeUser == null){
	   	 		ErrorDTO error =  new ErrorDTO();
			    error.setError("Service response is invalid");
			    
			    return new ResponseEntity<>(error,HttpStatus.SERVICE_UNAVAILABLE);
	   	 	}
			
			if(removeUser.getError() == null ){
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
    	
    	DeleteUsersByIdFrontendResponseDTO removeResponse = null;
    	
    	if(removeUser.getError().length()>0){
    		
    		ErrorDTO error = new ErrorDTO();
    		error.setError(removeUser.getError());
    		
    		return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    	}
    	
		removeResponse = new DeleteUsersByIdFrontendResponseDTO ();	
		removeResponse.setId(userId);
		return new ResponseEntity<>(removeResponse, HttpStatus.OK);

    }
	
	@RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Object> postUser(HttpServletRequest request, @Valid @RequestBody PostUsersFrontendRequestDTO userCreate){
    	
    	if(!RequestMonitor.getRequestMonitorInstance().allowRequest(request.getRemoteAddr())){
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
    	addUser.setData(userCreate);
    	ResponseEntity<PostUsersBackendResponseDTO> response = null;
    	
    	PostUsersBackendResponseDTO userCreateResponse = null;
    	
    	try{
		    response = rest.postForEntity(url,addUser,PostUsersBackendResponseDTO.class);
		    
		    userCreateResponse = response.getBody();
		    
		    if(userCreateResponse == null){
	 			ErrorDTO error =  new ErrorDTO();
				error.setError("Service response is invalid");
				
				return new ResponseEntity<>(error,HttpStatus.SERVICE_UNAVAILABLE);
		   	 }
		    
		    if(userCreateResponse.getError() == null){
				ErrorDTO error = new ErrorDTO();
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

		 if(userCreateResponse.getError().length()>0){
			 ErrorDTO error = new ErrorDTO();
			 error.setError(userCreateResponse.getError());		
			 
			 return new ResponseEntity<>(error,HttpStatus.UNPROCESSABLE_ENTITY);
		 }
		 
		 if (userCreateResponse.getUserId() == null){
			 ErrorDTO error = new ErrorDTO();
			 error.setError("Service response is invalid");
			 
			 return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
		 }
		 
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
