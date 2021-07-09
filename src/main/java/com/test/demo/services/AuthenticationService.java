package com.test.demo.services;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.security.auth.login.CredentialNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.test.demo.constants.Messages;
import com.test.demo.dtos.ProductDto;
import com.test.demo.dtos.TokenDto;
import com.test.demo.dtos.UserDto;
import com.test.demo.model.Product;
import com.test.demo.model.User;
import com.test.demo.repository.ProductRepository;
import com.test.demo.repository.UserRepository;

import ch.qos.logback.core.subst.Token;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class AuthenticationService{

	@Autowired
	private UserDetailsService service;
	@Autowired
	private ModelMapper mapper;
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

	public TokenDto authenticate(String username, String password) throws CredentialNotFoundException{
		var user = service.loadUserByUsername(username);
		if (user == null) {
            throw new UsernameNotFoundException("Username doesn't exist.");
        }
		if(!bCryptPasswordEncoder.matches(password, user.getPassword()))
		{
            throw new CredentialNotFoundException("Credentials didn't match.");
		}
		return getJWTToken(user.getUsername());
	}
	private TokenDto getJWTToken(String username) {
		String secretKey = "atdx4od";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_USER");
		
		var issuedAt = new Date(System.currentTimeMillis());
		var expireAt = new Date(System.currentTimeMillis() + 600000);
		var token = Jwts
				.builder()
				.setId("softtekJWT")
				.setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(issuedAt)
				.setExpiration(expireAt)
				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();

		return new TokenDto(token,issuedAt, expireAt);
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
