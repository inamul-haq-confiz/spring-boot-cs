package com.test.demo.services;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.test.demo.constants.Messages;
import com.test.demo.dtos.ProductDto;
import com.test.demo.dtos.UserDto;
import com.test.demo.model.Product;
import com.test.demo.model.User;
import com.test.demo.repository.ProductRepository;
import com.test.demo.repository.UserRepository;

@Service
public class AccountService{

	@Autowired
	private UserRepository repository;
	@Autowired
	private ModelMapper mapper;
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

	public boolean register(UserDto user){
		var existingUser = repository.findByUsername(user.getUsername());
		if (existingUser != null) {
            throw new RuntimeException("Username already taken.");
        }		
		var userToSave = mapToModel(user);		
		userToSave.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		repository.save(userToSave);
		return true;
	}
//
//	
//	public ProductDto addProduct(ProductDto product) {		
//		var productToInsert = mapToModel(product);
//		var newProduct = repository.save(productToInsert);		
//		return mapToDto(newProduct);
//	}
//
//	
//	public ProductDto updateProduct(ProductDto product) {		
//		var productToUpdate = mapToModel(product);
//		var newProduct = repository.save(productToUpdate);	
//		return mapToDto(newProduct);	
//		}
//
//	
//	public boolean deleteProduct(String id) {
//		if(repository.findById(id) == null)
//		{throw new RuntimeException(Messages.PRODUCT_NOT_FOUND);}
//		repository.deleteById(id);
//		return true;
//	}
	
	private UserDto mapToDto(User user)
	{
		return mapper.map(user, UserDto.class);
	}
	private User mapToModel(UserDto user)
	{
		return mapper.map(user, User.class);
	}

}
