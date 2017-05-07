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
import com.usermanagement.DTO.GetNotificationTriggeredDto;
import com.usermanagement.DTO.GetNotificationTriggeredResultDto;
import com.usermanagement.DTO.GetNotificationsDto;
import com.usermanagement.DTO.GetNotificationsResultDto;
import com.usermanagement.DTO.GetNotificationsResultFromBackEnd;
import com.usermanagement.DTO.GetTriggeredNotificationMethodDto;
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
    	  if(notificationCreate.getInterval()==null || notificationCreate.getText()==null || notificationCreate.getTime()==null || notificationCreate.isRepeatable()== null)
    	  {
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
    	 
    	 if(response != null)
    	 {
    		 backendResult=response.getBody();
    		 if(backendResult.getId() == null || backendResult.getError() == null )
    		 {
    			 ErrorDto error =  new ErrorDto();
    	         error.setError("Internal server error");
    	       return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
    		 }
    	 }
    	 
    	NotificationDto frontendResult= null;
    	
    	 if(backendResult.getError().length()>0)
    	 {
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
		return new ResponseEntity<>((ResponseInterfaceDto)frontendResult, HttpStatus.OK);
  	
 
    }
    @RequestMapping(value = "/{userId}/notifications", method = RequestMethod.GET)
    public ResponseEntity<Object> getNotifications(@PathVariable("userId") Integer userId){
    	GetNotificationsDto getNotifications = new GetNotificationsDto();
    	String url = new String(backEndUrlPath);
    	RestTemplate rest = new RestTemplate();
    	getNotifications.setId(userId);
    	ResponseEntity<GetNotificationsResultFromBackEnd> response = null;
    	
    	try{
       	  response = rest.postForEntity(url,getNotifications,GetNotificationsResultFromBackEnd.class);
       	 }
       	 catch (Exception e) {
       		 ErrorDto error = new ErrorDto();
       		 error.setError("The server is currently unavailable");
       		
       		 return new ResponseEntity<>(error,HttpStatus.SERVICE_UNAVAILABLE);
       		 
       	 }
  
    	
    	GetNotificationsResultFromBackEnd notificationsResult=null;
    	
    	List<NotificationDto> notificationsList = new ArrayList<>();
    	
    	if(response !=null){
    	
    		notificationsResult = response.getBody();
    	
	    	if(notificationsResult.getError()==null || notificationsResult.getNotifications() == null){
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
    
    
//    @RequestMapping(value = "/{userId}/notifications/{notificationId}", method = RequestMethod.GET)
//    public ResponseEntity<NotificationDto> getNotificationsSpecificated(@PathVariable("userId") Long userId ,@PathVariable("notificationId") Long notificationId ) {
//    	NotificationRequestDto notification = new NotificationRequestDto();
//    	String url = new String("http://localhost:9000");
//   	     RestTemplate rest = new RestTemplate();
//    	notification.setUserid(userId);
//    	notification.setNotificationid(notificationId);
//    	try{
//       	 ResponseEntity<NotificationDto>response = rest.postForEntity(url,notification,NotificationDto.class);
//       	 }
//       	 catch (Exception e) {
//       		 System.out.println(e);
//       	 }
//    	NotificationDto notificationResponse = new NotificationDto();
//    	notificationResponse.setId((int) (long)notification.getNotificationid());
//        return new ResponseEntity<>(notificationResponse, HttpStatus.OK);
//     
//    }
    @RequestMapping(value ="/{userId}/notifications/{notificationId}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> removeNotification(@PathVariable("userId") Integer userId ,@PathVariable("notificationId") Integer notificationId){
    	NotificationRemoveDto removeNotification = new NotificationRemoveDto();
    	String url = new String(backEndUrlPath);
    	RestTemplate rest = new RestTemplate();
    	removeNotification.setId(notificationId);
    	removeNotification.setMethod("removeNotification");
    	
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
    	   if(removeNotificationResult.getError() ==null || removeNotificationResult.getId()==null){
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
    
       RemoveNotificationReturnDto removeResponse = null;

		if(removeNotificationResult.getError().length()>0){
			ErrorDto error = new ErrorDto();
			error.setError(removeNotificationResult.getError());
			
			return new ResponseEntity<>(error,HttpStatus.UNPROCESSABLE_ENTITY);
		}
		else{
		    removeResponse = new RemoveNotificationReturnDto();	
		    removeResponse.setId(removeNotificationResult.getId());
		      
			return new ResponseEntity<>(removeResponse, HttpStatus.OK);
		}
	}
    

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> removeUser(@PathVariable("userId") Integer userId) {
    	RemoveUserMethodDto removeUserMethod = new RemoveUserMethodDto();
    	String url = new String(backEndUrlPath);
   	 	RestTemplate rest = new RestTemplate();
   	 	removeUserMethod.setId(userId);
   	 	removeUserMethod.setMethod("removeUser");
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
    		if(removeUser.getError()==null || removeUser.getId()==null){
    			ErrorDto error = new ErrorDto();
        		error.setError("Internal Server Error");
        		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    		}
    	}
    	else{
    		removeUser = new RemoveUserDto();
    		ErrorDto error = new ErrorDto();
    		error.setError("Internal Server Error");
    		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    	
    	RemoveUserReturnDto removeResponse = null;
    	
    	if(removeUser.getError().length()>0){
    		ErrorDto error = new ErrorDto();
    		error.setError(removeUser.getError());
    		return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    	}
    	else{
    		removeResponse = new RemoveUserReturnDto ();	
    		removeResponse.setId(removeUser.getId());
    		return new ResponseEntity<>(removeResponse, HttpStatus.OK);
    	}
    }
    
     
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Object> postUser(@RequestBody UserCreateDto userCreate){
    	 if(userCreate.getCity() == null || userCreate.getCountry() == null || userCreate.getEmail() == null || userCreate.isHazzardCrawler() == null || userCreate.isNewsCrawler() == null || userCreate.isWeatherCrawler() == null){
    		ErrorDto error1 = new ErrorDto();
			error1.setError("Data for creating new user is invalid");
			return new ResponseEntity<>(error1,HttpStatus.BAD_REQUEST);
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
             ErrorDto error2 = new ErrorDto();
             error2.setError("The server is currently unavailable");
             return new ResponseEntity<>(error2, HttpStatus.SERVICE_UNAVAILABLE);
    	 }
    	 
    	 UserCreateResponseFromBackEnd userCreateResponse = null;
    	 
    	 if ( response != null ){
     		
    		 userCreateResponse = response.getBody();
    		 if(userCreateResponse.getError() == null || userCreateResponse.getId() == null){
    	    		ErrorDto error3 = new ErrorDto();
    				error3.setError("Internal server error");
    				return new ResponseEntity<>(error3,HttpStatus.INTERNAL_SERVER_ERROR);
    	    	 }
      	 }
      	 else{
	    	ErrorDto error3 = new ErrorDto();
			error3.setError("Internal server error");
			return new ResponseEntity<>(error3,HttpStatus.INTERNAL_SERVER_ERROR);
      	 }

    	
//        if(userCreate.getEmail().equals("manole.catalin@gmail.com")){
//        	userCreateResponse.setError("Data for creating new user is invalid");
//     	   
//        }
//        else
//        {
//        	userCreateResponse.setError("");
//        }

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
    @RequestMapping(value = "/{userId}/notifications/triggered-notifications", method = RequestMethod.GET)
    public ResponseEntity<Object> triggeredNotification(@PathVariable("userId") Integer userId){
    	
    	GetTriggeredNotificationMethodDto triggeredNotificationMethod = new GetTriggeredNotificationMethodDto();
    	String url = new String(backEndUrlPath);
    	RestTemplate rest = new RestTemplate();
    	triggeredNotificationMethod.setId(userId);
    	triggeredNotificationMethod.setMethod("getExpiredNotifications");
    	
    	ResponseEntity<GetNotificationTriggeredDto> responseFromBackend = null;
    	try{
    		responseFromBackend = rest.postForEntity(url,triggeredNotificationMethod,GetNotificationTriggeredDto.class);
    	}
    	catch (Exception e){
    		System.out.println(e);
    		ErrorDto error =  new ErrorDto();
    		error.setError("The server is currently unavailable");
    		return new ResponseEntity<>(error,HttpStatus.SERVICE_UNAVAILABLE);
    	}
    	
    	GetNotificationTriggeredDto notification = null;
    	if (responseFromBackend != null){
    		notification = responseFromBackend.getBody();
    		if(notification.getType() == null || notification.getData() ==  null || notification.getData().getId() == null || notification.getData().getInterval() == null
    				|| notification.getData().getText() == null || notification.getData().getTime() == null || notification.getData().isRepeatable() == false ){
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
    	
		GetNotificationTriggeredResultDto result = new GetNotificationTriggeredResultDto();
		result.setType(notification.getType());
		result.setData(notification.getData());
		return new ResponseEntity<>(result, HttpStatus.OK);

    }  
}