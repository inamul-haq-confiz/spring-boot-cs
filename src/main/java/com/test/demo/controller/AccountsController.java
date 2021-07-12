package com.test.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.test.demo.constants.Messages;
import com.test.demo.dtos.UserDto;
import com.test.demo.dtos.response.ResponseDto;
import com.test.demo.services.AccountsService;
import com.test.demo.services.AuthenticationService;
import io.swagger.annotations.ApiOperation;

import javax.security.auth.login.CredentialNotFoundException;
import javax.validation.Valid;

@RestController
@RequestMapping("api/accounts")
public class AccountsController {
	
	@Autowired
	private AccountsService accountService; 
	@Autowired 
	private AuthenticationService authenticationService;
	@PostMapping("/login")
	public ResponseEntity<ResponseDto> login(@RequestParam("username") String username, @RequestParam("password") String password) throws CredentialNotFoundException 
	{	
		var token = authenticationService.authenticate(username, password);			
		return new ResponseEntity<>(new ResponseDto("Success", HttpStatus.OK.toString(), token), HttpStatus.OK);		
	}

	@PostMapping("/register")
    @ApiOperation("This method is used to register the new user in the application")    
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseDto> register(@Valid @RequestBody UserDto userDto) {
        accountService.register(userDto);
        return new ResponseEntity<>(new ResponseDto(Messages.USER_REGISTERED_SUCCESSFULLY, HttpStatus.CREATED.toString(), null), HttpStatus.CREATED);
    }
	
	

}
