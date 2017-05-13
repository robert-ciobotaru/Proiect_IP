package com.usermanagement.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import com.usermanagement.DTO.ErrorDTO;
import com.usermanagement.DTO.GetNotificationsByIdFrontendResponseDTO;
import com.usermanagement.DTO.GetNotificationsByIdBackendRequestDTO;
import com.usermanagement.DTO.GetNotificationsByIdBackendResponseDTO;
import com.usermanagement.DTO.HazzardDTO;
import com.usermanagement.DTO.HazzardNotificationsDTO;
import com.usermanagement.requestmonitor.RequestMonitor;

@RestController
@RequestMapping("v1/users")
public class NotificationController extends AbstractController {
	
	@RequestMapping(value = "/{userId}/notifications", method = RequestMethod.GET)
    public ResponseEntity<Object> getNotifications(HttpServletRequest request, @PathVariable("userId") Integer userId){
    	
    	if(!RequestMonitor.getRequestMonitorInstance().allowRequest(request.getRemoteAddr())){
    		 ErrorDTO error = new ErrorDTO();
    		 error.setError(TOO_MANY_REQUESTS);    		
    		 return new ResponseEntity<>(error,HttpStatus.TOO_MANY_REQUESTS);
    		 
    	 }
    	
    	GetNotificationsByIdBackendRequestDTO getNotifications = new GetNotificationsByIdBackendRequestDTO();
    	String url = new String(backEndUrlPath);
    	RestTemplate rest = new RestTemplate();
    	getNotifications.setUserId(userId);
    	getNotifications.setMethod("getNotifications");
    	
    	ResponseEntity<GetNotificationsByIdBackendResponseDTO> response = null;
    	 
    	GetNotificationsByIdBackendResponseDTO getNotificationResponse = new GetNotificationsByIdBackendResponseDTO();
    	
    	try{
    		response = rest.postForEntity(url,getNotifications,GetNotificationsByIdBackendResponseDTO.class);
			getNotificationResponse = response.getBody();
			
			if(getNotificationResponse == null){
				ErrorDTO error = new ErrorDTO();
		   		error.setError("Service response is invalid");
		   		return new ResponseEntity<>(error, HttpStatus.SERVICE_UNAVAILABLE);
			}
			
			if(getNotificationResponse.validate()==false){
				 	ErrorDTO error = new ErrorDTO();
			   		error.setError("Service response is invalid");
			   		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
			   		
			}
       	 }
    	 
    	catch (ResourceAccessException e) {
       		
    		 ErrorDTO error = new ErrorDTO();
       		 error.setError("The server is currently unavailable");    		
       		 return new ResponseEntity<>(error,HttpStatus.SERVICE_UNAVAILABLE);
       		 
       	 }
    	
    	catch (RestClientResponseException e) {
    		
      		 ErrorDTO error = new ErrorDTO();
      		 error.setError("Service response is invalid");    		
      		 return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
      		 
      	 }
    	
    		
       	 catch (Exception e) {
       	
       		 ErrorDTO error = new ErrorDTO();
       		 error.setError("Unknown error occured");    		
       		 return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
       		 
       	 }
    
	
	
    	if(getNotificationResponse.getError().length()>0){
    		 ErrorDTO error = new ErrorDTO();
	       		error.setError("Internal Server Error");
	       		return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    	}
    	
    	GetNotificationsByIdFrontendResponseDTO getNotificationReturn = new GetNotificationsByIdFrontendResponseDTO();
    	HazzardDTO getHazzardNotificationReturn2 = new HazzardDTO();
    	getHazzardNotificationReturn2.setCyclonesList(getNotificationResponse.getCyclonesList());
    	getHazzardNotificationReturn2.setFloodsList(getNotificationResponse.getFloodsList());
    	
    	HazzardNotificationsDTO getHazzardNotificationReturn = new HazzardNotificationsDTO();
    	getHazzardNotificationReturn.setEarthquakesList(getNotificationResponse.getEarthquakesList());
    	getHazzardNotificationReturn.setHazzard(getHazzardNotificationReturn2);
    	
    	getNotificationReturn.setUserNotificationsList(getNotificationResponse.getUserNotifications());
    	getNotificationReturn.setWeatherNotificationsList(getNotificationResponse.getWeatherNotificationsList());
    	getNotificationReturn.setHazzardNotifications(getHazzardNotificationReturn);
    	getNotificationReturn.setNewsNotificationsList(getNotificationResponse.getNewsNotificationsList());
    	return new ResponseEntity<>(getNotificationReturn, HttpStatus.OK);
    }
}
