package com.test.demo.exceptions;

public class UserAlreadyExistsException extends RuntimeException {

	    public UserAlreadyExistsException(String message){
	        super(message);
	    }	
}
