package com.test.demo.services.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import com.test.demo.constants.Messages;
import com.test.demo.dtos.UserDto;
import com.test.demo.exceptions.UserAlreadyExistsException;
import com.test.demo.model.User;
import com.test.demo.repository.UserRepository;
import com.test.demo.services.AccountsService;

@SpringBootTest
public class AccountsServiceTests {
   
    @Autowired
    private AccountsService service;
    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @MockBean
    private UserRepository repository;   
   
    
    @Test
    @DisplayName("Test register Success")
    void testRegister() {
    	var user =  new UserDto("username", "password", "fullname");
    	when(repository.findByUsername(any())).thenReturn(null);
    	when(bCryptPasswordEncoder.encode(any())).thenReturn(any());
    	
        // Execute the service call
        var response = service.register(user);
        
        // Assert the response
        Assertions.assertTrue(response);
    }    
    
    @Test
    @DisplayName("Test register Duplicate User")
    void testRegisterDuplicateUser() {
    	var user =  new UserDto("username", "password", "fullname");
    	var savedUser = new User("id","username", "password", "fullname");
    	when(repository.findByUsername(any())).thenReturn(savedUser);
    	
        // Execute the service call
    	 var exception = assertThrows(UserAlreadyExistsException.class, () -> service.register(user));
         
        // Assert the response
         Assertions.assertEquals(exception.getMessage(), Messages.USERNAME_ALREADY_TAKEN);
    }
}