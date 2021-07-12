package com.test.demo.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.test.demo.constants.Messages;
import com.test.demo.dtos.UserDto;
import com.test.demo.exceptions.UserAlreadyExistsException;
import com.test.demo.model.User;
import com.test.demo.repository.UserRepository;

@Service
public class AccountsService extends BaseService{

	@Autowired
	private UserRepository repository;
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

	public boolean register(UserDto user){
		var existingUser = repository.findByUsername(user.getUsername());
		if (existingUser != null) {
            throw new UserAlreadyExistsException(Messages.USERNAME_ALREADY_TAKEN);
        }		
		var userToSave = mapTo(user,User.class);		
		userToSave.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		repository.save(userToSave);
		return true;
	}
}
