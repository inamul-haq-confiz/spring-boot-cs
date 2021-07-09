package com.test.demo.utility;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.test.demo.dtos.response.ResponseDto;

@ControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(value = {IllegalArgumentException.class, ConstraintViolationException.class})
    public ResponseEntity<ResponseDto> handle(Exception e) {
        var response = new ResponseDto();
        response.setMessage(e.getCause().getMessage());
        response.setStatusCode(HttpStatus.BAD_REQUEST.toString());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
	@ExceptionHandler(value = {UsernameNotFoundException.class})
    public ResponseEntity<ResponseDto> handleAuthenticationException(UsernameNotFoundException e) {
        var response = new ResponseDto();
        response.setMessage(e.getMessage() + " not found");
        
        response.setStatusCode(HttpStatus.NOT_FOUND.toString());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    public ResponseEntity<ResponseDto> handleAnyException(Exception e) {
        var response = new ResponseDto();
        response.setMessage(e.getMessage());
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    

   
}
