package com.usermanagement.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.usermanagement.DTO.ErrorDTO;

public abstract class AbstractController {
	protected static String TOO_MANY_REQUESTS = "TOO MANY REQUESTS";
	public String backEndUrlPath = " http://104.198.38.180:8769";
	
	public void setBackEndUrlPath(String backEndUrlPath) {
		this.backEndUrlPath = backEndUrlPath; 
	}
	
	@ExceptionHandler({HttpMessageNotReadableException.class})
	public ResponseEntity<Object> messageNotReadableExceptionHandler(HttpServletRequest req, HttpMessageNotReadableException exception) {
		ErrorDTO error = new ErrorDTO();
		error.setError("The specified request is not readable");
	  
	    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	 
	@ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorDTO handleException(MethodArgumentNotValidException exception) {
    	ErrorDTO error = new ErrorDTO();
    	error.setError("Input provided does not meet the requirements");
        
    	return error;
    }
}
