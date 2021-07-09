package com.test.demo.controller;

import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.test.demo.dtos.TokenDto;
import com.test.demo.dtos.UserDto;
import com.test.demo.dtos.response.ResponseDto;
import com.test.demo.services.AccountService;
import com.test.demo.services.AuthenticationService;
import io.swagger.annotations.ApiOperation;

import javax.security.auth.login.CredentialNotFoundException;
import javax.validation.Valid;

@RestController
public class AccountsController {
	
	@Autowired
	private AccountService accountService; 
	@Autowired 
	private AuthenticationService authenticationService;
	@PostMapping("api/login")
	public ResponseEntity<TokenDto> login(@RequestParam("username") String username, @RequestParam("password") String password) throws CredentialNotFoundException 
	{	
		var token = authenticationService.authenticate(username, password);			
		return new ResponseEntity<>(token, HttpStatus.OK);		
	}

	@PostMapping("api/register")
    @ApiOperation("This method is used to register the new user in the application")    
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseDto> register(@Valid @RequestBody UserDto userDto) {
        accountService.register(userDto);
        return new ResponseEntity<>(new ResponseDto("User registered successfully", HttpStatus.CREATED.toString(), null), HttpStatus.CREATED);
    }
	
	

}
