package com.test.demo.controllers.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.mockito.Mockito.when;

import java.util.Date;
import javax.security.auth.login.CredentialNotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import com.test.demo.constants.Messages;
import com.test.demo.controller.AccountsController;
import com.test.demo.dtos.TokenDto;
import com.test.demo.dtos.UserDto;
import com.test.demo.exceptions.UserAlreadyExistsException;
import com.test.demo.services.AccountsService;
import com.test.demo.services.AuthenticationService;

@SpringBootTest
public class AccountsControllerTests {
   
	@MockBean
    private AuthenticationService authService;	
	@MockBean
	private AccountsService accService;   
	
    @Autowired
    private AccountsController controller;  
    
    @Test
    @DisplayName("Test login Success")
    void testLogin() throws CredentialNotFoundException {
    	
    	var token = new TokenDto("tokenxyz@test", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()+ 10000));
    	
    	when(authService.authenticate(any(), any())).thenReturn(token);

        // Execute the service call
        var response = controller.login(any(),any());

        // Assert the response

        Assertions.assertNotNull(response, "No user found");
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(response.getBody().getStatusCode(), HttpStatus.OK.toString());

    }
    
    @Test
    @DisplayName("Test register Success")
    void testRegister() {
    	
    	var user =  new UserDto("username", "password", "fullname");
    	when(accService.register(any())).thenReturn(true);

        // Execute the service call
        var response = controller.register(user);

        // Assert the response
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(response.getBody().getStatusCode(), HttpStatus.CREATED.toString());
        Assertions.assertEquals(response.getBody().getMessage(), Messages.USER_REGISTERED_SUCCESSFULLY);
    }    
    
    @Test
    @DisplayName("Test register Duplicate User")
    void testRegisterDuplicateUser() {
    	
    	var user =  new UserDto("username", "password", "fullname");
    	when(accService.register(any())).thenThrow(new UserAlreadyExistsException(Messages.USERNAME_ALREADY_TAKEN));
        // Execute the service call

        var exception = assertThrows(UserAlreadyExistsException.class, ()->  controller.register(user));      
        Assertions.assertEquals(exception.getMessage(), Messages.USERNAME_ALREADY_TAKEN);
    }   
    
    @Test
    @DisplayName("Test login Invalid Username")
    void testLoginInvalidUserName() throws CredentialNotFoundException {
    	
    	when(authService.authenticate(any(),any())).thenThrow(new UsernameNotFoundException(Messages.USERNAME_NOT_FOUND));
        // Execute the service call
        var exception = assertThrows(UsernameNotFoundException.class, ()->  controller.login(any(),any()));      
        Assertions.assertEquals(exception.getMessage(), Messages.USERNAME_NOT_FOUND);
    } 
    @Test
    @DisplayName("Test login Invalid Credentials")
    void testLoginInvalidUserCredentials() throws CredentialNotFoundException {
    	
    	when(authService.authenticate(any(),any())).thenThrow(new UsernameNotFoundException(Messages.CREDENTIALS_DIDN_T_MATCH));
        // Execute the service call
        var exception = assertThrows(UsernameNotFoundException.class, ()->  controller.login(any(),any()));      
        Assertions.assertEquals(exception.getMessage(), Messages.CREDENTIALS_DIDN_T_MATCH);
    }
}