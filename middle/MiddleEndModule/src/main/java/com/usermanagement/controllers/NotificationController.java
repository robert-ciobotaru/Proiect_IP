package com.usermanagement.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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
public class NotificationController {
	

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
	
	@RequestMapping(value = "/{userId}/notifications", method = RequestMethod.GET)
    public ResponseEntity<Object> getNotifications(HttpServletRequest request, @PathVariable("userId") Integer userId){
    	
    	if(!requestMonitor.allowRequest(request.getRemoteAddr())){
    		 ErrorDTO error = new ErrorDTO();
    		 error.setError(TOO_MANY_REQUESTS);    		
    		 return new ResponseEntity<>(error,HttpStatus.TOO_MANY_REQUESTS);
    		 
    	 }
    	
    	GetNotificationsByIdBackendRequestDTO getNotifications = new GetNotificationsByIdBackendRequestDTO();
    	String url = new String(backEndUrlPath);
    	RestTemplate rest = new RestTemplate();
    	getNotifications.setId(userId);
    	getNotifications.setMethod("getNotifications");
    	
    	ResponseEntity<GetNotificationsByIdBackendResponseDTO> response = null;
    	 
    	GetNotificationsByIdBackendResponseDTO getNotificationResponse = new GetNotificationsByIdBackendResponseDTO();
    	
    	try{
    		response = rest.postForEntity(url,getNotifications,GetNotificationsByIdBackendResponseDTO.class);
       	  
			getNotificationResponse = response.getBody();
			if(getNotificationResponse.validate()==false){
				 ErrorDTO error = new ErrorDTO();
			   		error.setError("Internal Server Error");
			   		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
			}
       	 }
       	 catch (Exception e) {
       		 ErrorDTO error = new ErrorDTO();
       		 error.setError("The server is currently unavailable");    		
       		 return new ResponseEntity<>(error,HttpStatus.SERVICE_UNAVAILABLE);
       		 
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
