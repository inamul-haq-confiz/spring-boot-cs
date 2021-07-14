package com.test.demo.services.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.Mockito.when;

import javax.security.auth.login.CredentialNotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import com.test.demo.constants.Messages;
import com.test.demo.model.User;
import com.test.demo.services.AuthenticationService;

@SpringBootTest
class AuthenticationServiceTests {
   
    @Autowired
    private AuthenticationService authService;
    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @MockBean
    private UserDetailsService userDetailsService;   
   
    
    @Test
    @DisplayName("Test authenticate Success")
    void testAuthenticate() throws CredentialNotFoundException {
    	var validUser =  new User("1","fullname","username","testpass");
    	var userDetails = org.springframework.security.core.userdetails.User.withUsername("username").password("testpass").authorities("testuser").build();
    	when(userDetailsService.loadUserByUsername(any())).thenReturn(userDetails);
    	when(bCryptPasswordEncoder.matches(any(),any())).thenReturn(true);
    	
        // Execute the service call
        var response = authService.authenticate(validUser.getUsername(),validUser.getPassword());
        
        // Assert the response
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getToken());
    }    
    
    @Test
    @DisplayName("Test authenticate Invalid User")
    void testAuthenticateInvalidUser() throws CredentialNotFoundException {
    	when(userDetailsService.loadUserByUsername(any())).thenReturn(null);
    	
        // Execute the service call
        var exception = assertThrows(UsernameNotFoundException.class, () ->  authService.authenticate("inam@confiz.com","password"));
        
        // Assert the response
        Assertions.assertEquals(exception.getClass(), UsernameNotFoundException.class);
        Assertions.assertTrue(exception.getMessage().contains(Messages.USERNAME_NOT_FOUND));
    }    
    @Test
    @DisplayName("Test authenticate Invalid Credentials")
    void testAuthenticateInvalidCredentials() throws CredentialNotFoundException {
    	var user =  new User("1","fullname","inam@confiz.com","testpass");
    	var userDetails = org.springframework.security.core.userdetails.User.withUsername("inam@confiz.com").password("testpass").authorities("testuser").build();
    	when(userDetailsService.loadUserByUsername(any())).thenReturn(userDetails);
    	when(bCryptPasswordEncoder.matches(any(),any())).thenReturn(false);
    	
        // Execute the service call
        var exception = assertThrows(CredentialNotFoundException.class, () ->  authService.authenticate(user.getUsername(),user.getPassword()));
        
        // Assert the response
        Assertions.assertEquals(CredentialNotFoundException.class, exception.getClass());
        Assertions.assertTrue(exception.getMessage().contains(Messages.CREDENTIALS_DIDN_T_MATCH));
    }   
    

}