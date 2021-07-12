package com.test.demo.services;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.security.auth.login.CredentialNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.test.demo.constants.Messages;
import com.test.demo.dtos.TokenDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class AuthenticationService{

	@Autowired
	private UserDetailsService service;

	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

	public TokenDto authenticate(String username, String password) throws CredentialNotFoundException{
		var user = service.loadUserByUsername(username);
		if (user == null) {
            throw new UsernameNotFoundException(Messages.USERNAME_NOT_FOUND);
        }
		if(!bCryptPasswordEncoder.matches(password, user.getPassword()))
		{
            throw new CredentialNotFoundException(Messages.CREDENTIALS_DIDN_T_MATCH);
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
}
